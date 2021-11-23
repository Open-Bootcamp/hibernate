
# Rendimiento

1. pgtune: https://pgtune.leopard.in.ua/#/ la configuración está en el archivo postgresql.conf 

2. work_mem permite optimizar las consultas con ORDER BY

3. `EXPLAIN ANALYZE` en las consultas para saber cómo se están ejecutando.

4. Ver configuración: 
  * SHOW work_mem;
  * SHOW ALL;

5. Índices: estructura de datos separada a las tablas y que permite optimizar la recuperación de datos.
    * una columna
    * varias columnas
    * único
    * parcial (WHERE)
    * implícito (se crea automáticamente sobre la primary key)

Tipos de índices: 
* B-tree
* Hash
* GiST 
* SP-GiST
* GIN
* BRIN

CREATE INDEX idx_name ON table_name(col1);

* Permiten optimizar las consultas 
* Tienen el coste adicional de que cada vez que se insertan datos se tendrán que realizar más operaciones de escritura para mantener el índice actualizado. 

6. Particionamiento de tablas:
   * Por rango 
   * Por lista
   * Por hash

7. Vacuum 

A medida que se van realizando operaciones de escritura van quedando huecos:
Live rows 
Dead rows - filas cuyos datos han sido marcados para ser sobrescritos

VACUUM ANALYZE employee;

8. Hibernate:
 * Utilizar DTOs para las consultas.


Otros conceptos: 
CTE
Funciones ventana
Vistas y vistas materializadas