package sogur.dev.sogurEco.dto;

import java.time.Instant;
import java.util.UUID;

public class Account {

    private final UUID uuid;
    private final long balanceCents;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Account(UUID uuid, long balanceCents, Instant createdAt, Instant updatedAt) {
        this.uuid = uuid;
        this.balanceCents = balanceCents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getUuid() {
        return uuid;
    }
    public long getBalanceCents() {
        return balanceCents;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Account{" +
                "uuid=" + uuid +
                ", balanceCents=" + balanceCents +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
