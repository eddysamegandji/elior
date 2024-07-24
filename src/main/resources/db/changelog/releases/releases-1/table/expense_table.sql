CREATE TABLE IF NOT EXISTS Expense (
   id BIGINT NOT NULL AUTO_INCREMENT,
   denomination VARCHAR(255) NOT NULL,
   price DOUBLE default 0.0,
   expenseDate date,
   accounting BIGINT NOT NULL,
   primary key (id),
   FOREIGN KEY (accounting) REFERENCES Accounting(id)
);