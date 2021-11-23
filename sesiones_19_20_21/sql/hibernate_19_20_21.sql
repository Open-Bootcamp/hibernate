/*
CTEs - Common Table Expressions

* Resultado temporal tomado de una sentencia SQL
* Es una manera e crear tablas temporales para consultar datos en lugar de utilizar subqueries en una cláusula FROM.
* Las CTEs son una alternativa a las subqueries
* A diferencia de la subqueries, las CTE pueden ser referenciadas múliples veces desde múltiples partes de una misma sentencia SQL.
* Mejora la legibilidad de las sentencias
* El ciclo de vida de las CTEs es el mismo que el de una sentencia SQL
* Se utilizan en conjunto a las funciones ventana
* 2 tipos: no recursiva, recursiva

Sintaxis:
WITH cte_name (column_list) AS (
	cte_query_definition
)
statement;
*/

-- EJEMPLO 1
WITH numbers AS (
	SELECT * FROM generate_series(1,10) AS id
)
SELECT * FROM numbers;

WITH numbers AS (
	SELECT * FROM generate_series(1,10) AS id
)
SELECT * FROM numbers where id > 5;

-- EJEMPLO 2
WITH dates AS (
	SELECT * FROM generate_series(
	'2021-01-01 00:00'::timestamp,
	'2021-12-20 00:00',
	'6 hours'
	) AS creation_date
)
SELECT * FROM dates;

-- EJEMPLO 3 - base de datos: demo, schema: ticket_flights

select * from ticket_flights;

WITH cte_flights AS (
	SELECT flight_id, fare_conditions, amount, (
		CASE
			WHEN amount < 10000 THEN 'CHEAP'
			WHEN amount < 30000 THEN 'MEDIUM'
			ELSE 'EXPENSIVE'
		END
	) as price_cat FROM ticket_flights
)
SELECT * FROM cte_flights WHERE price_cat = 'CHEAP';

-- EJEMPLO 4: borrar datos de una tabla original hacia una tabla histórico

select * from seats;
select * from seats_archive;
drop table if exists seats;
drop table if exists seats_archive;

CREATE TABLE seats_archive AS SELECT * FROM seats LIMIT 0;

INSERT INTO seats_archive
SELECT * FROM seats;

WITH cte_seats_archive_aicraft AS (
	DELETE FROM seats
	WHERE aircraft_code = '319'
	RETURNING *
)
INSERT INTO seats_archive
SELECT * FROM cte_seats_archive_aicraft;

/*
Funciones agregadas 
* Las funciones agregadas (COUNT, AVG, SUM...) agregan datos de un conjunto de filas en una sola fila (1 resultado) realizando un cálculo.

Funciones ventana
* Las funciones ventana permiten realizar cálculos sobre un conjunto de filas relacionadas con la fila actual. 
* No agrupan los datos en un único resultado
* Permiten realizar cálculos sin perder detalle ni reducir el número de resultados como ocurre con las funciones agregadas.
* Se crean agregaciones sobre las propias filas, sin reducir el número de resultados

Sintaxis:
OVER ()
PARTITION BY()
ORDER BY()

ROW_NUMBER()
RANK()
DENSE_RANK()
FIRST_VALUE()
LAST_VALUE()
LAG()
LEAD()
*/
SELECT * from products;
SELECT * from orders;

SELECT x FROM generate_series(1,10) as x;
SELECT array_agg(x) FROM generate_series(1,10) as x;
SELECT SUM(x) FROM generate_series(1,10) as x;

select *, array_agg(x) OVER () AS frame from generate_series(1,7) as x;

select *, array_agg(x) OVER (ORDER BY x) AS frame from generate_series(1,7) as x;

select *, array_agg(x) OVER (
	ORDER BY x ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
) AS frame from generate_series(1,7) as x;

select *, array_agg(x) OVER (
	ORDER BY x ROWS BETWEEN CURRENT ROW AND UNBOUNDED FOLLOWING
) AS frame from generate_series(1,7) as x;

select *, array_agg(x) OVER (
	ORDER BY x ROWS BETWEEN UNBOUNDED PRECEDING AND 0 FOLLOWING
) AS frame from generate_series(1,7) as x;

select *, array_agg(x) OVER (
	ORDER BY x ROWS BETWEEN UNBOUNDED PRECEDING AND 2 FOLLOWING
) AS frame from generate_series(1,7) as x;

select * from products;

select product_id, product_name, category_id, category_name FROM products INNER JOIN categories USING (category_id);

-- calcular y mostrar una columna con el precio medio de los productos de una categoría

select product_id, product_name, category_id, category_name, unit_price,
AVG(unit_price) OVER(PARTITION BY category_id),
ROW_NUMBER() OVER(PARTITION BY category_id),
FROM products INNER JOIN categories USING (category_id);


/* Paginación */

EXPLAIN ANALYZE select * from employee;

EXPLAIN ANALYZE select * from employee LIMIT 20;

select * from employee order by id DESC;


/* Cifrado de datos
pgcrypto
*/

CREATE EXTENSION pgcrypto;

CREATE TABLE ob_users(
	id serial primary key,
	email varchar not null unique,
	passw varchar not null
)


select * from ob_users;

INSERT INTO ob_users(email, passw) VALUES
('user3@company.com', pgp_sym_encrypt('admin', 'secret')),
('user4@company.com', pgp_sym_encrypt('1234', 'secret'))

select id, email, pgp_sym_decrypt(passw::bytea, 'secret') as passw FROM ob_users;



/* Particionamiento mediante herencia */

-- CREACIÓN DE TABLAS
create table measurement (
 
	logdate date not null,
	peaktemp int,
	unitsales int
);

select * from measurement;

create table measurement_2006 (
	CHECK (logdate >= DATE '2006-01-01' AND logdate < DATE '2007-01-01')
) INHERITS (measurement);

create table measurement_2007 (
	CHECK (logdate >= DATE '2007-01-01' AND logdate < DATE '2008-01-01')
) INHERITS (measurement);

-- CREAR FUNCIÓN PARA TRIGGER
-- Ejemplo 1: Almacenar todos los nuevos registros en la tabla measurement_2007
CREATE OR REPLACE FUNCTION measurement_insert_trigger()
RETURNS TRIGGER AS $$
BEGIN
	INSERT INTO measurement_2007 VALUES (NEW.*);
	RETURN NULL;
END;
$$
LANGUAGE plpgsql;

-- Ejemplo 2: Detectar el año e insertar en una partición en base al año
CREATE OR REPLACE FUNCTION measurement_insert_trigger()
RETURNS TRIGGER AS $$
BEGIN
    IF ( NEW.logdate >= DATE '2020-01-01' AND
         NEW.logdate < DATE '2021-01-01' ) THEN
        INSERT INTO measurement_2020 VALUES (NEW.*);
    ELSIF ( NEW.logdate >= DATE '2021-01-01' AND
            NEW.logdate < DATE '2022-01-01' ) THEN
        INSERT INTO measurement_2021 VALUES (NEW.*);
    ELSE
        RAISE EXCEPTION 'Date out of range.  Fix the measurement_insert_trigger() function!';
    END IF;
    RETURN NULL;
END;
$$
LANGUAGE plpgsql;


CREATE TRIGGER insert_measurement_trigger
	BEFORE INSERT ON measurement
	FOR EACH ROW EXECUTE PROCEDURE measurement_insert_trigger();

INSERT INTO measurement values
(1, '2007-06-04', 5, 6),
(1, '2007-07-04', 5, 6);

select * from measurement;
select * from measurement_2006;
select * from measurement_2007;

EXPLAIN ANALYZE select * from measurement where logdate = '2007-06-04';




