package sogur.dev.sogurEco.dao;

import sogur.dev.sogurEco.dto.TxRecord;

import java.util.List;
import java.util.UUID;

public class TransactionDaoImpl implements TransactionDao{
    @Override
    public boolean insertTransaction(TxRecord record) {
        return false;
    }

    @Override
    public List<TxRecord> getRecentTransactions(UUID uuid, int limit) {
        return List.of();
    }

    @Override
    public List<TxRecord> getAllTransactions(UUID uuid) {
        return List.of();
    }

    @Override
    public int deleteTransactions(UUID uuid) {
        return 0;
    }
}
