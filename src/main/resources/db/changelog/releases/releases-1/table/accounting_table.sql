CREATE TABLE IF NOT EXISTS Accounting (
    id BIGINT NOT NULL AUTO_INCREMENT,
    eventName VARCHAR(255),
    eventDate DATE,
    employeeName VARCHAR(255),
    flooz DOUBLE default 0.0,
    tmoney DOUBLE default 0.0,
    unpaid DOUBLE default 0.0,
    totalPurcharsePrice DOUBLE default 0.0,
    totalSellingPrice DOUBLE default 0.0,
    eventPercent DOUBLE default 0.0,
    totalBenefit DOUBLE default 0.0,
    realBenefit DOUBLE default 0.0,
    pureExpense DOUBLE default 0.0,
    totalExpense DOUBLE default 0.0,
    PRIMARY KEY (id)
);