package sogur.dev.sogurEco.dto;

public class TransactionContext {
    private final String actor; //  "console", "user", "system"
    private final String note; // reason for transaction
    private final boolean audited;

    public TransactionContext(String actor, String note, boolean audited) {
        this.actor = actor;
        this.note = note;
        this.audited = audited;
    }

    public String getActor() {
        return actor;
    }
    public String getNote() {
        return note;
    }
    public boolean isAudited() {
        return audited;
    }
}
