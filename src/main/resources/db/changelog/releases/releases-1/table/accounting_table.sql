CREATE TABLE IF NOT EXISTS Accounting (
    id BIGSERIAL NOT NULL,
    event_name VARCHAR(255),
    event_date DATE,
    employee_name VARCHAR(255),
    flooz DOUBLE PRECISION default 0.0,
    tmoney DOUBLE PRECISION default 0.0,
    unpaid DOUBLE PRECISION default 0.0,
    total_purcharse_price DOUBLE PRECISION default 0.0,
    total_selling_price DOUBLE PRECISION default 0.0,
    organizer_percent INT default 0,
    event_organizer_commission DOUBLE PRECISION default 0.0,
    total_benefit DOUBLE PRECISION default 0.0,
    real_benefit DOUBLE PRECISION default 0.0,
    pure_expense DOUBLE PRECISION default 0.0,
    total_expense DOUBLE PRECISION default 0.0,
    PRIMARY KEY (id)
);