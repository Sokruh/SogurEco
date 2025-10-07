package sogur.dev.sogurEco.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.java.JavaPlugin;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private final JavaPlugin plugin;
    private final DatabaseType type;
    private HikariDataSource dataSource;

    public DatabaseManager(JavaPlugin plugin, DatabaseType type) {
        this.plugin = plugin;
        this.type = type;
    }

    public void init() {
        HikariConfig config = new HikariConfig();

        // 🔧 Build JDBC connection string based on type
        switch (type) {
            case MYSQL, MARIADB -> {
                String host = plugin.getConfig().getString("database.host");
                int port = plugin.getConfig().getInt("database.port");
                String db = plugin.getConfig().getString("database.name");
                String user = plugin.getConfig().getString("database.user");
                String pass = plugin.getConfig().getString("database.password");

                String jdbcType = (type == DatabaseType.MARIADB ? "mariadb" : "mysql");
                config.setJdbcUrl("jdbc:" + jdbcType + "://" + host + ":" + port + "/" + db +
                        "?useSSL=false&autoReconnect=true&allowPublicKeyRetrieval=true");
                config.setUsername(user);
                config.setPassword(pass);
            }

            case POSTGRESQL -> {
                String host = plugin.getConfig().getString("database.host");
                int port = plugin.getConfig().getInt("database.port");
                String db = plugin.getConfig().getString("database.name");
                String user = plugin.getConfig().getString("database.user");
                String pass = plugin.getConfig().getString("database.password");

                config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + db);
                config.setUsername(user);
                config.setPassword(pass);
            }

            case SQLITE -> {
                File dbFile = new File(plugin.getDataFolder(), "economy.db");
                config.setJdbcUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());
            }
        }

        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(10000);
        config.setLeakDetectionThreshold(60000);
        config.setPoolName("SogurEcoPool");

        // 🧩 Initialize with retry logic
        int maxRetries = 3;
        int retryDelay = 5000; // milliseconds

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                this.dataSource = new HikariDataSource(config);

                plugin.getLogger().info("Attempting to connect to " + type + " database... (Attempt " + attempt + ")");
                if (testConnection()) {
                    plugin.getLogger().info("Connected to " + type + " database successfully!");
                    break;
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Database connection attempt " + attempt + " failed: " + e.getMessage());
            }

            if (attempt < maxRetries) {
                plugin.getLogger().info("Retrying in " + (retryDelay / 1000) + " seconds...");
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ignored) {}
            } else {
                plugin.getLogger().severe("❌ Failed to connect to the database after " + maxRetries + " attempts!");
                return;
            }
        }

        // 🪶 Run Flyway migrations after successful connection
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations("classpath:me/sokruh/sogureco/database/migrations")
                    .load();
            flyway.migrate();
            plugin.getLogger().info("✅ Flyway migrations completed.");
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed to run Flyway migrations!");
            e.printStackTrace();
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public boolean testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(2);
        } catch (SQLException e) {
            plugin.getLogger().warning("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
