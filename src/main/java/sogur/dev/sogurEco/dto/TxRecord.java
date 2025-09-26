package sogur.dev.sogurEco.dto;

import java.time.Instant;
import java.util.UUID;

public class TxRecord {
    private final long id; //db primary key
    private final UUID accountUuid;
    private final String type; // DEPOSIT, WITHDRAWAL, TRANSFER
    private final long amountCents;
    private final long balanceAfter;
    private final String actor; //who caused it
    private final String note; // optional reason
    private final Instant createdAt;

    public TxRecord(long id, UUID accountUuid, String type, long amountCents, long balanceAfter, String actor, String note, Instant createdAt) {
        this.id = id;
        this.accountUuid = accountUuid;
        this.type = type;
        this.amountCents = amountCents;
        this.balanceAfter = balanceAfter;
        this.actor = actor;
        this.note = note;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }
    public UUID getAccountUuid() {
        return accountUuid;
    }
    public String getType() {
        return type;
    }
    public long getAmountCents() {
        return amountCents;
    }
    public long getBalanceAfter() {
        return balanceAfter;
    }
    public String getActor() {
        return actor;
    }
    public String getNote() {
        return note;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
}
