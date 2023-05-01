-- liquibase formatted sql

-- changeSet Vladislav:1
create table order(
    id int generated by default as identity primary key,
    person_id int references person(id),
    address varchar,
    order_date date,
    status varchar
)
-- rollback drop table order;