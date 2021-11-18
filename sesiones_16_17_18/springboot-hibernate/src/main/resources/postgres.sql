/*
La seguridad está gestionada por roles.

Usuarios, roles, grupos son lo mismo, depende de cómo se utilicen que se mencionan de una manera o de otra.

Niveles de seguridad:

1. Instancia: crear usuarios, roles, bases de datos, realizar login, replicación
2. Bases de datos: conexión, crear esquemas, etc.
3. Esquema: usar el esquema, crear objetos dentro
4. Tabla: operaciones Data Manipulation Language (DML)
5. Columnas: permitir o restringir el acceso a una columna/s
6. Filas: restringir el acceso a filas
*/

select * from employee;

-- 1. Seguridad Instancia
/*
* SUPERUSER
* CREATEDB
* CREATEROLE
* LOGIN
* REPLICATION
*/
CREATE USER ob_user_1 NOSUPERUSER NOCREATEDB LOGIN PASSWORD 'admin';

GRANT ob_user_2 to ob_user_1;

-- 2. Seguridad Base de datos
/*
* CREATE
* CONNECT
* TEMP/TEMPORARY
*/
REVOKE ALL ON DATABASE ob_hibernate FROM public;
REVOKE ALL ON DATABASE ob_hibernate FROM ob_user_1;

GRANT CONNECT ON DATABASE ob_hibernate to ob_user_1;
GRANT CREATE ON DATABASE ob_hibernate to ob_user_1;

-- 3. Seguridad Schema
/*
CREATE
USAGE
*/
REVOKE ALL ON SCHEMA public FROM ob_user_1;
GRANT CREATE ON SCHEMA public to ob_user_1;
GRANT USAGE ON SCHEMA public to ob_user_1;

-- 4. Seguridad Tablas
/*
SELECT
INSERT
UPDATE
DELETE
TRUNCATE
TRIGGER
REFERENCE
*/
-- Dar permisos para todas las tablas:
-- GRANT permission_name ON ALL TABLES IN SCHEMA schema_name TO role_name;

-- Dar permisos sobre una tabla individual:
-- GRANT permission_name ON TABLE table_name TO role_name;
REVOKE ALL ON ALL TABLES IN SCHEMA public FROM ob_user_1;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO ob_user_1;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO ob_user_1;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO ob_user_1;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO ob_user_1;

-- 5. Seguridad columnas
/*
SELECT
INSERT
UPDATE
REFERENCE
*/
-- GRANT permission_name (col1, col2, ....) ON table_name TO role_name;
select * from employee;

REVOKE SELECT ON ALL TABLES IN SCHEMA public FROM ob_user_1;

GRANT SELECT (email) ON employee TO ob_user_1;

-- probar que si deja recuperar info si se hace la query: select email from employee;
-- probar que no deja recuperar info si se hace la query: select * from employee;

-- 6. Seguridad filas
/*
Se habilita a nivel de tabla, no está activo por defecto
Una vez se habilita, el valor por defecto es: Deny all
*/

ALTER TABLE employee ENABLE ROW LEVEL SECURITY;

CREATE POLICY employee_gte_19 ON employee
FOR SELECT
               TO ob_user_1
               USING (age > 19);


-- Ver privilegios
SELECT has_database_privilege('ob_hibernate', 'CREATE');

select email from employee;

select
    has_database_privilege('ob_hibernate', 'CREATE'),
    has_schema_privilege('public', 'USAGE'),
    has_schema_privilege('public', 'CREATE'),
    has_table_privilege('employee', 'SELECT'),
    has_table_privilege('employee', 'INSERT'),
    has_any_column_privilege('employee', 'SELECT')

select has_column_privilege('ob_user_1', 'employee', 'age', 'SELECT');
select has_column_privilege('ob_user_1', 'employee', 'email', 'SELECT');
