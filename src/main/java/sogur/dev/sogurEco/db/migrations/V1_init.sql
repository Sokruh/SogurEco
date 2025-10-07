CREATE TABLE IF NOT EXISTS accounts (
                                        uuid VARCHAR(36) PRIMARY KEY,
    balance_cents BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS transactions (
                                            id SERIAL PRIMARY KEY,
                                            sender_uuid VARCHAR(36),
    receiver_uuid VARCHAR(36),
    amount_cents BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_uuid) REFERENCES accounts(uuid) ON DELETE SET NULL,
    FOREIGN KEY (receiver_uuid) REFERENCES accounts(uuid) ON DELETE SET NULL
    );
