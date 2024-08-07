CREATE TABLE IF NOT EXISTS Expense (
   id BIGINT NOT NULL AUTO_INCREMENT,
   denomination VARCHAR(255) NOT NULL,
   price DOUBLE default 0.0,
   expense_date date,
   accounting_id BIGINT,
   primary key (id),
   FOREIGN KEY (accounting_id) REFERENCES Accounting(id)
);