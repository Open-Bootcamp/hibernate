
-- crear base de datos
CREATE DATABASE prueba 
WITH 
ENCODING = 'UTF-8'
OWNER = postgres
CONNECTION LIMIT = 100;

-- borrar bases de datos
DROP DATABASE IF EXISTS prueba;
DROP DATABASE IF EXISTS demo;

-- ver el peso de todas las bases de datos
SELECT
    pg_database.datname,
    pg_size_pretty(pg_database_size(pg_database.datname)) AS size
    FROM pg_database;

-- Ver el peso de una base de datos en concreto
select pg_size_pretty (pg_database_size ('northwind'));

-- Ver las 10 tablas más peso tienen en la base de datos actual
SELECT
    relname AS "relation",
    pg_size_pretty (
        pg_total_relation_size (C .oid)
    ) AS "total_size"
FROM
    pg_class C
LEFT JOIN pg_namespace N ON (N.oid = C .relnamespace)
WHERE
    nspname NOT IN (
        'pg_catalog',
        'information_schema'
    )
AND C .relkind <> 'i'
AND nspname !~ '^pg_toast'
ORDER BY
    pg_total_relation_size (C .oid) DESC
LIMIT 10;




