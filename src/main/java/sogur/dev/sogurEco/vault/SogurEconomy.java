package sogur.dev.sogurEco.vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import sogur.dev.sogurEco.SogurEco;
import sogur.dev.sogurEco.dto.TransactionContext;
import sogur.dev.sogurEco.dto.TransactionResult;
import sogur.dev.sogurEco.service.EconomyService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

public class SogurEconomy implements Economy {

    private final SogurEco plugin;
    private final EconomyService service;

    public SogurEconomy(SogurEco plugin, EconomyService service) {

        this.plugin = plugin;
        this.service = service;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Sogur";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return plugin.getConfig().getInt("currency.decimals");
    }

    @Override
    public String format(double v) {
        int decimals = fractionalDigits();
        String singular = currencyNameSingular();
        String plural = currencyNamePlural();
        BigDecimal bd = BigDecimal.valueOf(v).setScale(decimals, RoundingMode.DOWN);
        String amountStr = bd.toPlainString();
        boolean isOne = bd.compareTo(BigDecimal.ONE) == 0;
        return amountStr + " " + (isOne ? singular : plural);
    }

    @Override
    public String currencyNamePlural() {
        return plugin.getConfig().getString("currency.currencyNamePlural");
    }

    @Override
    public String currencyNameSingular() {
        return plugin.getConfig().getString("currency.currencyName");
    }

    @Override
    public boolean hasAccount(String s) {
        UUID uuid = resolveUuid(s);
        return uuid != null && service.ensureAccount(uuid);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return offlinePlayer != null && service.ensureAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer);
    }

    @Override
    public double getBalance(String s) {
        UUID uuid = resolveUuid(s);
        if (uuid == null) return 0.0D;
        BigDecimal bal = service.getBalance(uuid);
        return toDouble(bal);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        if (offlinePlayer == null) return 0.0D;
        BigDecimal bal = service.getBalance(offlinePlayer.getUniqueId());
        return toDouble(bal);
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        UUID uuid = resolveUuid(s);
        return uuid != null && service.has(uuid, BigDecimal.valueOf(v));
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return offlinePlayer != null && service.has(offlinePlayer.getUniqueId(), BigDecimal.valueOf(v));
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        UUID uuid = resolveUuid(s);
        if (uuid == null) {
            return new EconomyResponse(v, 0.0D, ResponseType.FAILURE, "Player not found");
        }
        TransactionResult result = service.withdraw(uuid, BigDecimal.valueOf(v), TransactionContext.none());
        return toResponse(result, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        if (offlinePlayer == null) {
            return new EconomyResponse(v, 0.0D, ResponseType.FAILURE, "Player not found");
        }
        TransactionResult result = service.withdraw(offlinePlayer.getUniqueId(), BigDecimal.valueOf(v), TransactionContext.none());
        return toResponse(result, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        UUID uuid = resolveUuid(s);
        if (uuid == null) {
            return new EconomyResponse(v, 0.0D, ResponseType.FAILURE, "Player not found");
        }
        TransactionResult result = service.deposit(uuid, BigDecimal.valueOf(v), TransactionContext.none());
        return toResponse(result, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        if (offlinePlayer == null) {
            return new EconomyResponse(v, 0.0D, ResponseType.FAILURE, "Player not found");
        }
        TransactionResult result = service.deposit(offlinePlayer.getUniqueId(), BigDecimal.valueOf(v), TransactionContext.none());
        return toResponse(result, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return notImplementedBank();
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return notImplementedBank();
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        UUID uuid = resolveUuid(s);
        return uuid != null && service.ensureAccount(uuid);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return offlinePlayer != null && service.ensureAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return createPlayerAccount(s);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer);
    }

    // Helpers

    private UUID resolveUuid(String name) {
        if (name == null || name.isEmpty()) return null;
        OfflinePlayer op = Bukkit.getOfflinePlayer(name);
        return op != null ? op.getUniqueId() : null;
    }

    private double toDouble(BigDecimal bd) {
        if (bd == null) return 0.0D;
        return bd.setScale(fractionalDigits(), RoundingMode.DOWN).doubleValue();
    }

    private EconomyResponse toResponse(TransactionResult result, double amountRequested) {
        if (result == null) {
            return new EconomyResponse(amountRequested, 0.0D, ResponseType.FAILURE, "No result from economy service");
        }
        ResponseType type = result.isSuccess() ? ResponseType.SUCCESS : ResponseType.FAILURE;
        double newBalance = Double.isNaN(result.getBalanceAfter()) ? 0.0D : result.getBalanceAfter();
        String msg = result.getMessage() == null ? "" : result.getMessage();
        return new EconomyResponse(amountRequested, newBalance, type, msg);
    }

    private EconomyResponse notImplementedBank() {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank features are not supported");
    }
}
