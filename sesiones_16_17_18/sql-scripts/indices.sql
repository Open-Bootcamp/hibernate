
-- ÍNDICES
-- Ejecutar primero la app Spring Hibernate para generar la tabla employee con datos demo

select * from employee;

EXPLAIN ANALYZE select * from employee;

-- id
SELECT * from employee where id = 78999;

EXPLAIN ANALYZE SELECT * from employee where id = 78999;

-- name
EXPLAIN ANALYZE SELECT * from employee where name = 'Empleado 3';

CREATE INDEX idx_employee_name ON employee(name);

-- active 

select * from employee where active IS TRUE;
select * from employee where active IS FALSE;

EXPLAIN ANALYZE select * from employee where active IS TRUE;
EXPLAIN ANALYZE select * from employee where active IS FALSE;

CREATE INDEX idx_employee_active ON employee(active);

-- age

EXPLAIN ANALYZE  select * from employee where age = 40;

CREATE INDEX idx_employee_age_lt_22 ON employee(age) WHERE age >= 18 AND age <=22;

EXPLAIN ANALYZE  select * from employee where age = 20;

-- register_date
EXPLAIN ANALYZE select * from employee where EXTRACT(year FROM register_date) = 2020;

CREATE INDEX idx_employee_register_date_2020 on employee(register_date) 
where EXTRACT(year FROM register_date) = 2020;




