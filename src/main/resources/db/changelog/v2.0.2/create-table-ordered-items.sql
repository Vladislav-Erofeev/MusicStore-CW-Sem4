-- liquibase formatted sql

-- changeSet Vladislav:2
create table order_item (
    order_id int references order(id),
    item_id int references item(id),
    primary key(order_id, item_id)
)
-- rollback drop table cart;