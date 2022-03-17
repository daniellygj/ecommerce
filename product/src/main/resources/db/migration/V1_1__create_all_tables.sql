CREATE TABLE product_category
(
    id          serial PRIMARY KEY,
    name        varchar,
    description varchar,
    created_at  timestamp,
    modified_at timestamp,
    deleted_at  timestamp
);

CREATE TABLE product_inventory
(
    id          serial PRIMARY KEY,
    quantity    int,
    created_at  timestamp,
    modified_at timestamp,
    deleted_at  timestamp
);

CREATE TABLE discount
(
    id               serial PRIMARY KEY,
    name             varchar,
    description      varchar,
    discount_percent float,
    active           boolean,
    created_at       timestamp,
    modified_at      timestamp,
    deleted_at       timestamp
);

CREATE TABLE product
(
    id           serial PRIMARY KEY,
    name         varchar,
    description  varchar,
    category_id  serial,
    inventory_id serial,
    price        float,
    discount_id  serial,
    created_at   timestamp,
    modified_at  timestamp,
    deleted_at   timestamp
);

ALTER TABLE product
    ADD FOREIGN KEY (category_id) REFERENCES product_category (id);

ALTER TABLE product
    ADD FOREIGN KEY (inventory_id) REFERENCES product_inventory (id);

ALTER TABLE product
    ADD FOREIGN KEY (discount_id) REFERENCES discount (id);