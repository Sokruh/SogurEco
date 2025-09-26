package sogur.dev.sogurEco.dto;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class TransactionResult {

    public enum Status {
        SUCCESS,
        FAILED
    }

    public enum Reason {
        NONE,
        INSUFFICIENT_FUNDS,
        ACCOUNT_NOT_FOUND,
        INVALID_AMOUNT,
        INTERNAL_ERROR,
        NOT_ALLOWED
    }

    private final UUID transactionId;
    private final Status status;
    private final Reason reason;
    private final String message;
    private final double amount;        // Amount that was attempted/applied
    private final double balanceAfter;  // Balance after transaction (or current balance on failure, if known)
    private final Instant timestamp;

    private TransactionResult(
            UUID transactionId,
            Status status,
            Reason reason,
            String message,
            double amount,
            double balanceAfter,
            Instant timestamp
    ) {
        this.transactionId = transactionId == null ? UUID.randomUUID() : transactionId;
        this.status = Objects.requireNonNull(status, "status");
        this.reason = reason == null ? Reason.NONE : reason;
        this.message = message == null ? "" : message;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = timestamp == null ? Instant.now() : timestamp;
    }

    // Factory methods: success
    public static TransactionResult success(double amount, double balanceAfter) {
        return new TransactionResult(UUID.randomUUID(), Status.SUCCESS, Reason.NONE, "", amount, balanceAfter, Instant.now());
    }

    public static TransactionResult success(double amount, double balanceAfter, String message) {
        return new TransactionResult(UUID.randomUUID(), Status.SUCCESS, Reason.NONE, message, amount, balanceAfter, Instant.now());
    }

    // Factory methods: failure
    public static TransactionResult failure(Reason reason, String message) {
        return new TransactionResult(UUID.randomUUID(), Status.FAILED, reason, message, 0.0, Double.NaN, Instant.now());
    }

    public static TransactionResult failure(Reason reason, String message, double currentBalance) {
        return new TransactionResult(UUID.randomUUID(), Status.FAILED, reason, message, 0.0, currentBalance, Instant.now());
    }

    // Accessors
    public UUID getTransactionId() {
        return transactionId;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public Reason getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "TransactionResult{" +
                "transactionId=" + transactionId +
                ", status=" + status +
                ", reason=" + reason +
                ", message='" + message + '\'' +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                ", timestamp=" + timestamp +
                '}';
    }
}
