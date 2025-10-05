package sogur.dev.sogurEco.dao;

import sogur.dev.sogurEco.dto.Account;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


public class AccountDaoImpl implements  AccountDao {

    private final DataSource dataSource;

    public AccountDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<Account> findByUuid(UUID uuid) {
        String sql = "SELECT uuid, balance_cents, created_at, updated_at FROM accounts WHERE uuid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uuid.toString());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    long balanceCents = rs.getLong("balance_cents");

                    Account account = new Account(
                            UUID.fromString(rs.getString("uuid")),
                            // convert cents -> BigDecimal with 2 decimal places
                            new BigDecimal(balanceCents).movePointLeft(2),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("updated_at").toInstant()
                    );

                    return Optional.of(account);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Account createAccount(UUID uuid) {
        String sql = """
        INSERT INTO accounts (uuid, balance_cents, created_at, updated_at)
        VALUES (?, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uuid.toString());
            ps.executeUpdate();

            return new Account(uuid, BigDecimal.ZERO,
                    java.time.Instant.now(), java.time.Instant.now());

        } catch (SQLException e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("unique") || msg.contains("duplicate") || msg.contains("constraint")) {

                return findByUuid(uuid).orElse(null);
            }

            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean updateBalance(UUID uuid, BigDecimal balance) {
        long balanceCents = balance.movePointRight(2).longValueExact();

        String sql = "UPDATE accounts SET balance_cents = ?, updated_at = CURRENT_TIMESTAMP WHERE uuid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, balanceCents);
            ps.setString(2, uuid.toString());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BigDecimal getBalance(UUID uuid) {
        String sql = "SELECT balance_cents FROM accounts WHERE uuid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uuid.toString());
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    long balanceCents = rs.getLong("balance_cents");
                    return new BigDecimal(balanceCents).movePointLeft(2).setScale(2);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }


    @Override
    public boolean deleteAccount(UUID uuid) {
        String sql = "DELETE FROM accounts WHERE uuid = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uuid.toString());
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
