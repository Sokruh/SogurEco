package sogur.dev.sogurEco;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import sogur.dev.sogurEco.dao.AccountDaoImpl;
import sogur.dev.sogurEco.db.DatabaseManager;
import sogur.dev.sogurEco.db.DatabaseType;

import javax.sql.DataSource;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public final class SogurEco extends JavaPlugin {

    private static Economy econ = null;
    AccountDaoImpl accountDao;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        DatabaseType dbType = DatabaseType.fromString(getConfig().getString("database.type"));
        DatabaseManager databaseManager = new DatabaseManager(this, dbType);
        databaseManager.init();

        // Example: create DAO using this dataSource
        DataSource ds = databaseManager.getDataSource();
        accountDao = new AccountDaoImpl(ds);

        Map<String, Object> dbConfig = getConfig().getConfigurationSection("database").getValues(false);
        DatabaseManager dbm = new DatabaseManager(this, dbConfig);
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info(String.format("[%s] - Enabled with Vault dependency!", getDescription().getName()));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
