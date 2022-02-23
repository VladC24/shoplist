-- Shoplist schema

CREATE SEQUENCE product_id_seq;
CREATE TABLE IF NOT EXISTS product (
    id integer NOT NULL DEFAULT nextval('product_id_seq'),
    label VARCHAR(255),
    price VARCHAR(255)
);
