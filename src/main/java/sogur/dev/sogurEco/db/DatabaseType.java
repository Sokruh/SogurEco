package sogur.dev.sogurEco.db;

public enum DatabaseType {
    MYSQL,
    MARIADB,
    POSTGRESQL,
    SQLITE;

    public static DatabaseType fromString(String name) {
        for (DatabaseType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return SQLITE; // Default fallback
    }
}
