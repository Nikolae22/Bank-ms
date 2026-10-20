
create table if not exists customer (
    customer_id int auto_increment primary key,
    name varchar(100) not null,
    email varchar(20) not null,
    created_at date not null,
    created_by varchar(20) not null,
    updated_at date default null,
    updated_by varchar(20) default null
);

CREATE TABLE IF NOT EXISTS `accounts` (
    `customer_id` int NOT NULL,
    `account_number` int AUTO_INCREMENT  PRIMARY KEY,
    `account_type` varchar(100) NOT NULL,
    `branch_address` varchar(200) NOT NULL,
    `communication_sw` BOOLEAN,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
    );