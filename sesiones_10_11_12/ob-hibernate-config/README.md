
# Hibernate 

https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html

## Introducción 

1. Java DataBase Connectivity (JDBC)

2. JPA (Java Persistence API) Especificación (javax.persistence o jakarta.persistence)
     Implementaciones:
     - Hibernate (ORM Object Relational Mapping)
     - EclipseLink

3. Spring Data JPA

CRUD - Create Retrieve Update Delete

## Descargar bases de datos y herramientas

https://dev.mysql.com/downloads/installer/

https://mariadb.org/download

https://www.postgresql.org/download/

https://dbeaver.io/

## Configuración

1. Agregar dependencias maven en el fichero pom.xml: 

* hibernate-core
* Driver de base de datos: mysql, postgresql, etc, el que se quiera.

2. Crear fichero `hibernate.cfg.xml` en la carpeta src/main/resources y configurarlo

3. Crear objeto SessionFactory desde java: clase HibernateUtil

4. Crear modelos y realizar operaciones CRUD


## Operaciones de CRUD 

Create Retrieve Update Delete 

### Operaciones de lectura (Retrieve)

`SELECT * FROM table ... `

### Operaciones de escritura (Create, Update, Delete)

Create: `INSERT INTO table () values ();`

Update: `UPDATE table SET column1=value1, ...`

Delete: `DELETE FROM table ...`

## Donde se programa el código que interactúa con la base de datos:

En clases Repository o DAO

Esquema general de una aplicación web con Hibernate:

Navegador --> Controlador --> Servicio --> DAO o Repository --> Base de datos (MySQL, PostgreSQL, H2...)

## Poblar la base de datos

Hibernate ejecuta por defecto el archivo import.sql si es que existe. 

Con la propiedad `hibernate.hbm2ddl.import_files` se puede indicar a Hibernate que ejecute 
más de un archivo SQL.

Se pueden insertar nuevos datos desde Java utilizando los métodos de la interfaz Session. 

## Asociaciones entre entidades 

* One To One
  * @OneToOne
  * @JoinColumn 
  * @JoinTable
  * @PrimaryKeyJoinColumn
  * @MapsId
* One To Many
* Many To One
* Many To Many 

## Consultas

* Consultas: 
  * HQL 
  * Nativas SQL 
  * NamedQueries
  * Criteria API
  * Métodos Hibernate/JPA: find, save, persist, delete, remove

## Gestión de eventos

* Lifecycle callbacks Events: prePersist, preUpdate, preRemove, postPersist... 
* @Audited: a nivel de clase / atributo
* Interceptores

## Migración de cambios de esquema en la base de datos

https://www.liquibase.org/

https://flywaydb.org/ 