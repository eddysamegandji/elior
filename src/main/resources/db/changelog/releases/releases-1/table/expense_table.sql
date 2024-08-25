CREATE TABLE IF NOT EXISTS Expense (
   id BIGSERIAL NOT NULL,
   denomination VARCHAR(255) NOT NULL,
   price DOUBLE PRECISION default 0.0,
   expense_date date,
   accounting_id BIGINT,
   primary key (id),
   FOREIGN KEY (accounting_id) REFERENCES Accounting(id)
);