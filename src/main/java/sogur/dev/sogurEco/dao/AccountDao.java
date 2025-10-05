package sogur.dev.sogurEco.dao;




import sogur.dev.sogurEco.dto.Account;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO for accessing and modifying player account data.
 * Implementations should be thread-safe and handle SQL exceptions internally.
 */
public interface AccountDao {

    /**
     * Retrieves an account by UUID.
     *
     * @param uuid the player's UUID
     * @return Optional containing the account if found, otherwise empty
     */
    Optional<Account> findByUuid(UUID uuid);

    /**
     * Creates a new account with zero balance.
     *
     * @param uuid the player's UUID
     * @return the created Account, or null if already exists or failed
     */
     Account createAccount(UUID uuid);

    /**
     * Updates the balance of an existing account.
     *
     * @param uuid    the player's UUID
     * @param balance the new balance to set
     * @return true if update was successful
     */
    boolean updateBalance(UUID uuid, BigDecimal balance);

    /**
     * Gets the current balance of a player.
     * Shortcut for findByUuid().map(Account::getBalance)
     *
     * @param uuid the player's UUID
     * @return the balance, or BigDecimal.ZERO if account does not exist
     */
    BigDecimal getBalance(UUID uuid);

    /**
     * Deletes an account (and optionally cascades transactions depending on schema).
     *
     * @param uuid the player's UUID
     * @return true if deletion succeeded
     */
    boolean deleteAccount(UUID uuid);
}
