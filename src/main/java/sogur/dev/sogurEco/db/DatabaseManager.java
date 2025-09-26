package sogur.dev.sogurEco.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public class DatabaseManager {
    private final Plugin plugin;
    private final HikariDataSource ds;
    private final String dbType;

    public DatabaseManager(Plugin plugin, Map<String, Object> dbConfig) {
        this.plugin = plugin;
        this.dbType = Objects.toString(dbConfig.getOrDefault("type", "sqlite"));

        HikariConfig cfg = new HikariConfig();
        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(2);
        cfg.setPoolName("MyEconomyHikariPool");

        String jdbcUrl;
        String username = null;
        String password = null;

        switch (dbType.toLowerCase()) {
            case "mysql":
            case "mariadb":
                Map<String, Object> my = (Map<String, Object>) dbConfig.get("mysql");
                String host = Objects.toString(my.get("host"), "localhost");
                int port = Integer.parseInt(Objects.toString(my.get("port"), "3306"));
                String database = Objects.toString(my.get("database"), "sogureco");
                username = Objects.toString(my.get("username"), "root");
                password = Objects.toString(my.get("password"), "");
                jdbcUrl = String.format("jdbc:mysql://%s:%d/%s?useSSL=%s&serverTimezone=UTC",
                        host, port, database, Objects.toString(my.get("useSSL"), "false"));
                cfg.setDriverClassName("com.mysql.cj.jdbc.Driver");
                break;

            case "postgres":
            case "postgresql":
                Map<String, Object> pg = (Map<String, Object>) dbConfig.get("postgres");
                host = Objects.toString(pg.get("host"), "localhost");
                port = Integer.parseInt(Objects.toString(pg.get("port"), "5432"));
                database = Objects.toString(pg.get("database"), "sogureco");
                username = Objects.toString(pg.get("username"), "postgres");
                password = Objects.toString(pg.get("password"), "");
                jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
                cfg.setDriverClassName("org.postgresql.Driver");
                break;

            case "sqlite":
            default:
                Map<String, Object> sq = (Map<String, Object>) dbConfig.get("sqlite");
                String file = Objects.toString(sq.get("file"), plugin.getDataFolder().getAbsolutePath() + "/economy.db");
                Path fp = plugin.getDataFolder().toPath().resolve(file);
                // if supplied path already absolute, use it directly:
                String pathString = fp.toAbsolutePath().toString();
                jdbcUrl = "jdbc:sqlite:" + pathString;
                // SQLite driver picks up automatically; no username/password
                break;
        }

        cfg.setJdbcUrl(jdbcUrl);
        if (username != null) cfg.setUsername(username);
        if (password != null) cfg.setPassword(password);
        // Additional Hikari settings (timeouts) can be set here
        cfg.addDataSourceProperty("cachePrepStmts", "true");
        cfg.addDataSourceProperty("prepStmtCacheSize", "250");
        cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.ds = new HikariDataSource(cfg);

        // Run Flyway migrations
        runMigrations();
    }

    private void runMigrations() {
        // Flyway locations: common + db-specific (if you organized that way)
        String[] locations = new String[] { "classpath:db/migration" };
        Flyway flyway = Flyway.configure()
                .dataSource(this.ds)
                .locations(locations)
                .load();

        plugin.getLogger().info("Running DB migrations (Flyway) for " + dbType);
        flyway.migrate();
    }

    public DataSource getDataSource() {
        return ds;
    }

    public void close() {
        if (ds != null && !ds.isClosed()) {
            ds.close();
        }
    }
}

