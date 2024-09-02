CREATE TABLE IF NOT EXISTS Product (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255) NOT NULL unique,
    unit_purchase_price DOUBLE PRECISION DEFAULT 0.0,
    PRIMARY KEY (id)
);