package sogur.dev.sogurEco.service;

import org.jetbrains.annotations.NotNull;
import sogur.dev.sogurEco.dto.TransactionContext;
import sogur.dev.sogurEco.dto.TransactionResult;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface EconomyService {

    /**
     * Ensures the account exists.
     *
     * @param playerUuid the UUID of the player
     * @return true if the account exists, false if the account doesn't exist
     */
    boolean ensureAccount(UUID playerUuid);

    /**
     * Gets the balance of a player in BigDecimal.
     *
     * @param playerUuid the UUID of the player
     * @return the current balance
     */
    BigDecimal getBalance(UUID playerUuid);

    /**
     * Deposits a given amount to a player's account.
     *
     * @param playerUuid the UUID of the player
     * @param amount     the amount to deposit (positive)
     * @param context    optional metadata about the transaction (actor, note)
     * @return a TransactionResult indicating success/failure and new balance
     */
    TransactionResult deposit(UUID playerUuid, BigDecimal amount, TransactionContext context);

    /**
     * Withdraws a given amount from a player's account.
     *
     * @param playerUuid the UUID of the player
     * @param amount     the amount to withdraw (positive)
     * @param context    optional metadata about the transaction (actor, note)
     * @return a TransactionResult indicating success/failure and new balance
     */
    TransactionResult withdraw(UUID playerUuid, BigDecimal amount, TransactionContext context);

    /**
     * Transfers money from one player to another.
     *
     * @param fromPlayer the UUID of the player sending money
     * @param toPlayer   the UUID of the player receiving money
     * @param amount     the amount to transfer
     * @param context    optional metadata about the transaction (actor, note)
     * @return a TransactionResult indicating success/failure
     */
    TransactionResult transfer(UUID fromPlayer, UUID toPlayer, BigDecimal amount, TransactionContext context);

    /**
     * Checks if the player has enough balance for a given amount.
     *
     * @param playerUuid the UUID of the player
     * @param amount     the amount to check
     * @return true if balance >= amount, false otherwise
     */
    boolean has(UUID playerUuid, BigDecimal amount);
}
