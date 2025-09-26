package sogur.dev.sogurEco.service;

import sogur.dev.sogurEco.dto.TransactionContext;
import sogur.dev.sogurEco.dto.TransactionResult;

import java.math.BigDecimal;
import java.util.UUID;

public class EconomyServiceImpl implements EconomyService{
    @Override
    public boolean ensureAccount(UUID playerUuid) {
        return false;
    }

    @Override
    public BigDecimal getBalance(UUID playerUuid) {
        return null;
    }

    @Override
    public TransactionResult deposit(UUID playerUuid, BigDecimal amount, TransactionContext context) {
        return null;
    }

    @Override
    public TransactionResult withdraw(UUID playerUuid, BigDecimal amount, TransactionContext context) {
        return null;
    }

    @Override
    public TransactionResult transfer(UUID fromPlayer, UUID toPlayer, BigDecimal amount, TransactionContext context) {
        return null;
    }

    @Override
    public boolean has(UUID playerUuid, BigDecimal amount) {
        return false;
    }
}
