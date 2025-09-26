package sogur.dev.sogurEco.dto;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class TransactionContext {

    private static final TransactionContext NONE =
            new TransactionContext(null, null, "", Instant.EPOCH);

    private final UUID actorUuid;     // Who initiated the transaction (optional)
    private final String actorName;   // Display name of the actor (optional)
    private final String note;        // Free-form note or reason (optional)
    private final Instant timestamp;  // When the context was created

    private TransactionContext(UUID actorUuid, String actorName, String note, Instant timestamp) {
        this.actorUuid = actorUuid;
        this.actorName = actorName;
        this.note = note == null ? "" : note;
        this.timestamp = Objects.requireNonNullElseGet(timestamp, Instant::now);
    }

    public static TransactionContext none() {
        return NONE;
    }

    public static TransactionContext of(UUID actorUuid, String actorName, String note) {
        return new TransactionContext(actorUuid, actorName, note, Instant.now());
    }

    public static TransactionContext withNote(String note) {
        return new TransactionContext(null, null, note, Instant.now());
    }

    public static TransactionContext system(String note) {
        return new TransactionContext(null, "SYSTEM", note, Instant.now());
    }

    public UUID getActorUuid() {
        return actorUuid;
    }

    public String getActorName() {
        return actorName;
    }

    public String getNote() {
        return note;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public boolean isSystem() {
        return actorUuid == null && "SYSTEM".equals(actorName);
    }

    @Override
    public String toString() {
        return "TransactionContext{" +
                "actorUuid=" + actorUuid +
                ", actorName='" + actorName + '\'' +
                ", note='" + note + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
