create database employee_management_system;
use employee_management_system;
select * from employee_management_system.employees;

create database sms;
select * from sms.students;
drop database sms;

create database jwt_security;

select user();
select current_user();
select current_role();
SHOW CHARACTER SET;
SELECT * FROM INFORMATION_SCHEMA.CHARACTER_SETS;
show create user root@localhost;
SELECT SQL_CALC_FOUND_ROWS * FROM employee_management_system.employees WHERE id < 100 LIMIT 3;
SELECT FOUND_ROWS();
