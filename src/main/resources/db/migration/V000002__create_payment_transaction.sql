CREATE TABLE payment_transaction (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    trx_id VARCHAR(50),
    customer_id VARCHAR(50),
    merchant VARCHAR(100),
    amount DECIMAL(19,2),
    status VARCHAR(20),
    created_date TIMESTAMP,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(customer_id)
);