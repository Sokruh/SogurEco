package sogur.dev.sogurEco.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Account {
    private final UUID uuid;
    private final BigDecimal balance;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Account(UUID uuid, BigDecimal balance, Instant createdAt, Instant updatedAt) {
        this.uuid = uuid;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getUuid() { return uuid; }
    public BigDecimal getBalance() { return balance; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
