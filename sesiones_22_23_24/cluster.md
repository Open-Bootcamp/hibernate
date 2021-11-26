# Clusters en PostgreSQL

Requisitos: tener docker instalado.
Recomendación: sistema operativo Linux.

Instalación docker en linux: https://docs.docker.com/engine/install/ubuntu/

## Crear contenedores docker 

docker network create --subnet=172.20.0.0/16 clusternet

docker run -itd --name=node1 --net clusternet --ip 172.20.0.10 ubuntu:20.04

docker run -itd --name=node2 --net clusternet --ip 172.20.0.11 ubuntu:20.04

docker run -itd --name=node3 --net clusternet --ip 172.20.0.12 ubuntu:20.04

## Configuración node1, node2, node3 (repetir los pasos en los 3 contenedores)

docker exec -it node1 bash 

apt update

apt upgrade

### Instalación PostgreSQL 

apt-get install lsb-release wget nano postgresql -y

ln -s /usr/lib/postgresql/12/bin/* /usr/sbin/

nano /etc/postgresql/12/main/pg_hba.conf

host    all             all             172.20.0.0/16            md5

service postgresql restart

### Instalación Python

apt-get install python3-pip python3-dev libpq-dev -y

pip3 install --upgrade pip

### Instalación patroni 

pip install patroni psycopg2-binary python-etcd

## Configuración Patroni 

mkdir /etc/patroni

cd /etc/patroni/

nano config.yml

```yml 
scope: postgres
namespace: /db/
name: node1

restapi:
  listen: 172.20.0.10:8008
  connect_address: 172.20.0.10:8008
#  Certfile: /etc/ssl/certs/ssl-Cert-snakeoil.pem
#  Keyfile: /etc/ssl/private/ssl-Cert-snakeoil.key
#  Authentication:
#    Username: Username
#    Password: Password

# Ctl:
#   Insecure: False # Allow Connections to SSL Sites Without Certs
#   Certfile: /etc/ssl/certs/ssl-Cert-snakeoil.pem
#   Cacert: /etc/ssl/certs/ssl-Cacert-snakeoil.pem

etcd:
  #Provide host to do the initial discovery of the cluster topology:
  host: 172.20.0.13:2379
  #Or use "hosts" to provide multiple endpoints
  #Could be a comma separated string:
  #hosts: host1:port1,host2:port2
  #or an actual yaml list:
  #hosts:
  #- host1:port1
  #- host2:port2
  #Once discovery is complete Patroni will use the list of advertised clientURLs
  #It is possible to change this behavior through by setting:
  #use_proxies: true

#Raft:
#  data_dir: .
#  self_addr: 127.0.0.1:2222
#  partner_addrs:
#  - 127.0.0.1:2223
#  - 127.0.0.1:2224

bootstrap:
  # this section will be written into Etcd:///config after initializing new cluster
  # and all other cluster members will use it as a `global configuration`
  dcs:
    ttl: 30
    loop_wait: 10
    retry_timeout: 10
    maximum_lag_on_failover: 1048576
#    master_start_timeout: 300
#    synchronous_mode: False
    #standby_cluster:
      #host: 127.0.0.1
      #port: 1111
      #primary_slot_name: patroni
    postgresql:
      use_pg_rewind: true
#      use_slots: True
      parameters:
#        wal_level: hot_standby
#        hot_standby: "on"
#        wal_keep_segments: 8
#        max_wal_senders: 10
#        max_replication_slots: 10
#        wal_log_hints: "on"
#        archive_mode: "on"
#        archive_timeout: 1800s
#        archive_command: Mkdir -P ../wal_archive && Test ! -F ../wal_archive/%F && Cp %P ../wal_archive/%F
#      recovery_conf:
#        restore_command: Cp ../wal_archive/%F %P

  # some desired options for 'initdb'
  initdb:  # Note: It needs to be a list (some options need values, others are switches)
  - encoding: UTF8
  - data-checksums

  pg_hba:  # Add following lines to pg_hba.conf after running 'initdb'
  # For kerberos gss based connectivity (discard @.*$)
  #- host replication replicator 127.0.0.1/32 gss include_realm=0
  #- host all all 0.0.0.0/0 gss include_realm=0
  - host replication replicator 127.0.0.1/32 md5
  - host replication replicator 172.20.0.10/0 md5
  - host replication replicator 172.20.0.11/0 md5
  - host replication replicator 172.20.0.12/0 md5
  - host all all 0.0.0.0/0 md5
  - host all all 172.20.0.0/16 md5
#  - Hostssl All All 0.0.0.0/0 Md5

  # Additional script to be launched after initial cluster creation (will be passed the connection URL as parameter)
# post_init: /usr/local/bin/setup_cluster.sh

  # Some additional users users which needs to be created after initializing new cluster
  users:
    admin:
      password: admin
      options:
        - createrole
        - createdb

postgresql:
  listen: 172.20.0.10:5432
  connect_address: 172.20.0.10:5432
  data_dir: /data/patroni
#  bin_dir:
#  config_dir:
  pgpass: /tmp/pgpass0
  authentication:
    replication:
      username: replicator
      password: rep-pass
    superuser:
      username: postgres
      password: zalando
    rewind:  # Has no effect on postgres 10 and lower
      username: rewind_user
      password: rewind_password
  # Server side kerberos spn
#  Krbsrvname: Postgres
  parameters:
    # Fully qualified kerberos ticket file for the running user
    # same as KRB5CCNAME used by the GSS
#   krb_server_keyfile: /var/spool/keytabs/postgres
    unix_socket_directories: '.'

#Watchdog:
#  Mode: Automatic # Allowed Values: Off, Automatic, Required
#  Device: /dev/watchdog
#  safety_margin: 5

tags:
    nofailover: false
    noloadbalance: false
    clonefrom: false
    nosync: false
```

NOTA: para los contenedores node2 y node3 hay que actualizar el nombre y la ip en el código anterior.

mkdir -p /data/patroni

chown postgres:postgres /data/patroni

chmod 700 /data/patroni

Repetir todo el proceso para node2 y node3. 

## Configuración node4 

export NODE4=172.20.0.13

docker run \
-itd --net clusternet --ip 172.20.0.13 \
--name node4 quay.io/coreos/etcd:latest \
/usr/local/bin/etcd \
--data-dir=/etcd-data --name node4 \
--initial-advertise-peer-urls http://${NODE4}:2380 --listen-peer-urls http://0.0.0.0:2380 \
--advertise-client-urls http://${NODE4}:2379 --listen-client-urls http://0.0.0.0:2379 \
--initial-cluster node4=http://${NODE4}:2380

## Configuración node5

docker run -itd --name=node5 --net clusternet --ip 172.20.0.14 ubuntu:20.04

docker exec -it node5 bash

apt update 

apt upgrade

apt install haproxy nano -y

nano /etc/haproxy/haproxy.cfg

```
global
    maxconn 100

defaults
    log global
    mode tcp
    retries 2
    timeout client 30m
    timeout connect 4s
    timeout server 30m
    timeout check 5s

listen stats
    mode http
    bind *:7000
    stats enable
    stats uri /

listen postgres
    bind *:5000
    option httpchk
    http-check expect status 200
    default-server inter 3s fall 3 rise 2 on-marked-down shutdown-sessions
    server postgresql_172.20.0.10_5432 172.20.0.10:5432 maxconn 100 check port 8008
    server postgresql_172.20.0.11_5432 172.20.0.11:5432 maxconn 100 check port 8008
    server postgresql_172.20.0.12_5432 172.20.0.12:5432 maxconn 100 check port 8008
```

service haproxy restart 

## Ejecutar patroni 

Entrar a cada uno de los 3 contenedores node1, node2 y node3 y ejecutar el comando: 

/usr/local/bin/patroni /etc/patroni/config.yml


## Comprobar instalación

Entrar a través del navegador y verificar que aparecen los 3 nodos

http://172.20.0.14:7000

## Ejemplos de ayuda 

https://snapshooter.com/learn/postgresql/postgresql-cluster-patroni

https://www.askvikram.com/set-up-a-highly-available-postgresql-cluster-using-patroni-and-haproxy/