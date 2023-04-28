-- liquibase formatted sql

-- changeSet Vladislav:1
create table cart (
    person_id int references person(id) on delete cascade,
    item_id int references item(id) on delete cascade,
    primary key (person_id, item_id)
)
-- rollback drop table cart;