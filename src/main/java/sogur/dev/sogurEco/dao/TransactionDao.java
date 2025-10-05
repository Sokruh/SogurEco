package sogur.dev.sogurEco.dao;



import sogur.dev.sogurEco.dto.TxRecord;

import java.util.List;
import java.util.UUID;

/**
 * DAO for reading and writing transaction history.
 * Used by the EconomyService for logging deposits, withdrawals, and transfers.
 */
public interface TransactionDao {

    /**
     * Inserts a new transaction record into the database.
     *
     * @param record the transaction record to insert
     * @return true if insert succeeded
     */
    boolean insertTransaction(TxRecord record);

    /**
     * Fetches the most recent transactions for a player.
     *
     * @param uuid  the player's UUID
     * @param limit maximum number of transactions to return
     * @return list of transactions, newest first
     */
    List<TxRecord> getRecentTransactions(UUID uuid, int limit);

    /**
     * Fetches all transactions for a player.
     * (Be careful — use getRecentTransactions() for player commands.)
     *
     * @param uuid the player's UUID
     * @return list of all transactions for that player
     */
    List<TxRecord> getAllTransactions(UUID uuid);

    /**
     * Deletes all transactions for a given player (e.g., when resetting an account).
     *
     * @param uuid the player's UUID
     * @return number of deleted records
     */
    int deleteTransactions(UUID uuid);
}
