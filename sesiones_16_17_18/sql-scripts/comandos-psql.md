

## login 

psql -U postgres

## ayuda 

\? 

## Salir

\q

## ver bases de datos

\l

## ver tablas

\dt

## conectarse a una base de datos estando ya logado

\c nombre_base_datos

## login directamente sobre una base de datos

psql -U postgres -d ob_hibernate

## ver roles

\du

## ver columnas de una tabla

\d table_name


# Backups

## Restaurar una base de datos

psql -c "CREATE DATABASE \"flights\";" -U postgres

psql -U postgres -d flights < flights.sql

psql -c "CREATE DATABASE \"ob_hibernate\";" -U postgres

psql -U postgres -d ob_hibernate < ob_hibernate.sql



Para trabajar con servidores:

psql -h 127.0.0.1 -p 5432 -U postgres -d demo < backup.sql

## Crear backup de una base de datos

pg_dump -U postgres -d ob_hibernate > ob_hibernate.sql

## Realizar backup de todas las bases de datos

pg_dumpall -U postgres > total_backup.sql

## Backup de una sola tabla

pg_dump -h localhost -p 5432 -U postgres -d mydb -t my_table > backup.sql

## Backup con split

Partir en 2mb

Backup: pg_dump -h localhost -p 5432 -U postgres -d mydb | split -b 2m – backup.sql

Restore: cat backup.sql* | psql -h localhost -p 5432 -U postgres -d mydb


## Herramientas

* Barman: https://github.com/EnterpriseDB/barman
* pg_probackup: https://github.com/postgrespro/pg_probackup
* pgBackRest: https://pgbackrest.org/
* pghoard: https://github.com/aiven/pghoard

## Bases de datos demo

https://postgrespro.com/education/demodb

https://github.com/datacharmer/test_db

