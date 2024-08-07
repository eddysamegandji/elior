CREATE TABLE IF NOT EXISTS Accounting (
    id BIGINT NOT NULL AUTO_INCREMENT,
    event_name VARCHAR(255),
    event_date DATE,
    employee_name VARCHAR(255),
    flooz DOUBLE default 0.0,
    tmoney DOUBLE default 0.0,
    unpaid DOUBLE default 0.0,
    total_purcharse_price DOUBLE default 0.0,
    total_selling_price DOUBLE default 0.0,
    organizer_percent INT default 0,
    event_organizer_commission DOUBLE default 0.0,
    total_benefit DOUBLE default 0.0,
    real_benefit DOUBLE default 0.0,
    pure_expense DOUBLE default 0.0,
    total_expense DOUBLE default 0.0,
    PRIMARY KEY (id)
);