CREATE TABLE IF NOT EXISTS Article (
    id BIGINT NOT NULL AUTO_INCREMENT,
    quantity INT,
    total_article_purchase_price DOUBLE default 0.0,
    selling_price DOUBLE default 0.0,
    discount DOUBLE default 0.0,
    benefit DOUBLE default 0.0,
    date date,
    product_id BIGINT NOT NULL,
    accounting_id BIGINT,
    primary key (id),
    FOREIGN KEY (product_id) REFERENCES Product(id),
    FOREIGN KEY (accounting_id) REFERENCES Accounting(id)
);