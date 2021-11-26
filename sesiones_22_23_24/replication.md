
# Replicación en PostgreSQL

Requisitos: tener docker instalado.
Recomendación: sistema operativo Linux.

Instalación docker en linux: https://docs.docker.com/engine/install/ubuntu/

docker rm server1 server2

docker run -itd --name=server1 --net host ubuntu:20.04

docker run -itd --name=server2 --net host ubuntu:20.04

## Configuración server1 

docker exec -it server1 bash

apt-get update

apt-get upgrade

apt-get install lsb-release wget nano postgresql -y

su postgres

psql

\password postgres

Nos pide contraseña y le asignamos la que queramos, por ejemplo: admin

create database sales;

\c sales

CREATE TABLE guestbook (visitor_email text, vistor_id serial, date timestamp, message text);

INSERT INTO guestbook (visitor_email, date, message) VALUES ( 'jim@gmail.com', current_date, 'This is a test.');

CREATE USER replication REPLICATION LOGIN CONNECTION LIMIT 5 ENCRYPTED PASSWORD 'admin';

\q 

nano /etc/postgresql/12/main/pg_hba.conf

Añadir al final la siguiente línea:

host    replication     replication     127.0.0.1/32            md5

nano /etc/postgresql/12/main/postgresql.conf

listen_addresses = 'localhost,127.0.0.1'           
wal_level = 'replica'
archive_mode = on
archive_command = 'cd .'
max_wal_senders = 5
primary_conninfo = 'host=127.0.0.1 port=5433 user=replication password=admin'
hot_standby = on

service postgresql restart

## Configuración server2

docker exec -it server2 bash

apt-get update

apt-get upgrade

apt-get install lsb-release wget nano postgresql -y


nano /etc/postgresql/12/main/pg_hba.conf

host    replication     replication     127.0.0.1/32            md5

nano /etc/postgresql/12/main/postgresql.conf

Cambiar el puerto

port = 5433

Añadir: 

listen_addresses = 'localhost,127.0.0.1'           
wal_level = 'replica'
archive_mode = on
archive_command = 'cd .'
max_wal_senders = 5
primary_conninfo = 'host=127.0.0.1 port=5432 user=replication password=admin'
hot_standby = on


su postgres

mv /var/lib/postgresql/12/main /var/lib/postgresql/12/main_old

pg_basebackup -h 127.0.0.1 -D /var/lib/postgresql/12/main -U replication -P -v

## Comenzar proceso de replicación

touch /var/lib/postgresql/12/main/standby.signal

service postgresql restart

Comprobar que aparecen los mismos datos en ambas instalaciones postgresql.

