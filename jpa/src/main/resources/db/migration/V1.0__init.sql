CREATE TABLE delivery_company
(
    id bigint auto_increment PRIMARY KEY
);

CREATE TABLE delivery_strike_zipcode
(
    id                  bigint auto_increment PRIMARY KEY,
    delivery_company_id int NOT NULL,
    zipcode             varchar(100) DEFAULT '' NOT NULL,
    active              tinyint(1) DEFAULT 1 NOT NULL,
    CONSTRAINT zipcode UNIQUE (zipcode),
    CONSTRAINT fk_delivery_strike_zipcode_delivery_company foreign key (delivery_company_id) references delivery_company (id)
);

create table sample_entity (
    id bigint not null auto_increment,
    address varchar(255),
    name varchar(255),
    primary key (id)
);

insert into sample_entity (address, name) values ( '경기도 고양시 1', '김태환' );
insert into sample_entity (address, name) values ( '경기도 고양시 2', '홍길동' );
insert into sample_entity (address, name) values ( '경기도 고양시 3', '차승원' );
insert into sample_entity (address, name) values ( '경기도 고양시 4', '태사자' );
commit;