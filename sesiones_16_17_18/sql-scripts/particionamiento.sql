/*
Particionamiento

1. rango: BY RANGE
2. lista: BY LIST
3. hash: BY HASH

*/
drop table if exists employees;

CREATE TABLE employees(
	id BIGSERIAL,
	register_date DATE NOT NULL,
	first_name varchar(20) NOT null,
	primary key (id, register_date)
) PARTITION BY RANGE (register_date);

CREATE TABLE employees_2019 PARTITION OF employees
FOR VALUES FROM ('2019-01-01') TO ('2020-01-01');

CREATE TABLE employees_2020 PARTITION OF employees
FOR VALUES FROM ('2020-01-01') TO ('2021-01-01');

CREATE TABLE employees_2021 PARTITION OF employees
FOR VALUES FROM ('2021-01-01') TO ('2022-01-01');

INSERT INTO employees (register_date, first_name) values ('2019-03-01', 'Employee 1');
INSERT INTO employees (register_date, first_name) values ('2019-04-01', 'Employee 2');

INSERT INTO employees (register_date, first_name) values ('2020-01-01', 'Employee 3');
INSERT INTO employees (register_date, first_name) values ('2020-11-01', 'Employee 4');

INSERT INTO employees (register_date, first_name) values ('2021-05-01', 'Employee 5');
INSERT INTO employees (register_date, first_name) values ('2021-07-01', 'Employee 6');

select * from employees;

EXPLAIN ANALYZE select * from employees;

select * from employees where register_date = '2021-07-01';

EXPLAIN ANALYZE select * from employees where register_date = '2021-07-01';

EXPLAIN ANALYZE select * from employees where register_date = '2020-11-01';

EXPLAIN ANALYZE select * from employees where register_date >= '2020-01-01' AND register_date <= '2021-06-30';

-- 2. Particionamiento por lista

drop table if exists employees;

CREATE TABLE employees(
	id BIGSERIAL,
	register_date DATE NOT NULL,
	first_name varchar(20) NOT null,
	country_code varchar(2) NOT NULL,
	primary key(id, country_code)
) PARTITION BY LIST(country_code);

CREATE TABLE employees_north PARTITION OF employees 
FOR VALUES IN ('EE', 'FI');

CREATE TABLE employees_south PARTITION OF employees 
FOR VALUES IN ('ES', 'FR', 'DE');

INSERT INTO employees (register_date, first_name, country_code) values ('2019-03-01', 'Employee 1', 'EE');
INSERT INTO employees (register_date, first_name, country_code) values ('2019-04-01', 'Employee 2', 'FI');
INSERT INTO employees (register_date, first_name, country_code) values ('2019-04-01', 'Employee 3', 'ES');
INSERT INTO employees (register_date, first_name, country_code) values ('2019-04-01', 'Employee 4', 'FR');
INSERT INTO employees (register_date, first_name, country_code) values ('2019-04-01', 'Employee 5', 'DE');

select * from employees;

EXPLAIN ANALYZE select * from employees;

select * from employees where country_code = 'ES';

EXPLAIN ANALYZE select * from employees where country_code = 'ES';

EXPLAIN ANALYZE select * from employees where country_code IN ('ES', 'EE');

-- 3. Particion por hash

drop table if exists dept;

CREATE TABLE dept (
id int primary key
) PARTITION BY HASH(id);

CREATE TABLE dept_hash1 PARTITION OF dept
FOR VALUES WITH (MODULUS 4, remainder 0);

CREATE TABLE dept_hash2 PARTITION OF dept
FOR VALUES WITH (MODULUS 4, remainder 1);

CREATE TABLE dept_hash3 PARTITION OF dept
FOR VALUES WITH (MODULUS 4, remainder 2);

CREATE TABLE dept_hash4 PARTITION OF dept
FOR VALUES WITH (MODULUS 4, remainder 3);

INSERT INTO dept (SELECT generate_series(0,200000));

select * from dept;

select count(*) from dept_hash1;
select count(*) from dept_hash2;
select count(*) from dept_hash3;
select count(*) from dept_hash4;

select * from dept_hash3;

select * from dept where id = 47;

EXPLAIN ANALYZE select * from dept where id = 47;