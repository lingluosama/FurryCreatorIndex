CREATE USER IF NOT EXISTS 'repl_user'@'%' IDENTIFIED WITH mysql_native_password BY 'repl_pass';
GRANT REPLICATION SLAVE ON *.* TO 'repl_user'@'%';
FLUSH PRIVILEGES;
SET GLOBAL transaction_isolation = 'REPEATABLE-READ';

CREATE DATABASE IF NOT EXISTS nacos_config
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 创建 root 远程访问权限 (生产环境应使用专用用户)
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '12345';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;

USE testdb;


create table user
(
    id       bigint not null,
    name     text   null,
    password text   null,
    slat     text   null,
    avatar   text   null,
    email    text   null
);
