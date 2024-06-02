CREATE TABLE alerts (
    id SERIAL PRIMARY KEY,
    symbol VARCHAR(50) NOT NULL,
    basis VARCHAR(50) NOT NULL,
    ma_length INTEGER,
    value DOUBLE PRECISION NOT NULL,
    direction VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL
);
