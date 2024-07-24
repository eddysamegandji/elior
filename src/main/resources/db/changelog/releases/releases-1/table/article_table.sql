CREATE TABLE IF NOT EXISTS Article (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    quantity INT,
    purchasePrice DOUBLE default 0.0,
    sellingPrice DOUBLE default 0.0,
    discount DOUBLE default 0.0,
    benefit DOUBLE default 0.0,
    date date,
    accounting BIGINT NOT NULL,
    primary key (id),
    FOREIGN KEY (accounting) REFERENCES Accounting(id)
);