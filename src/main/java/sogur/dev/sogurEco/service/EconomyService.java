package sogur.dev.sogurEco.service;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface EconomyService {

    // Account lifecycle

    /**
     * Ensures that an account exists for the given UUID.
     * @param uuid The UUID to check for.
     * @return True if the account exists, false otherwise.
     */
    boolean ensureAccountExists(UUID uuid);

    /**
     * Removes the account for the given UUID.
     * @param uuid The UUID to remove.
     * @return The balance of the account, if it existed.
     */
    Optional<BigDecimal> removeAccount(UUID uuid);

    /**
     * Checks if an account exists for the given UUID.
     * @param uuid The UUID to check for.
     * @return True if the account exists, false otherwise.
     */
    boolean hasAccount(UUID uuid);

    // Read operations

    /**
     * Gets the balance of the account for the given UUID.
     * @param uuid The UUID to get the balance for.
     * @return The balance of the account, if it exists.
     */
    BigDecimal getBalance(UUID uuid);

}
