package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public class Util {

    private static final String DRIVER_CLASS = "org.postgresql.Driver";
    private static final String DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String DB_LOG = "postgres";
    private static final String DB_PASS = "123123";
    private static SessionFactory sessionFactory;

    private Util() {}
    public static Connection JDBCGetConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_LOG, DB_PASS);
//          if (connection != null) {
//                System.out.println("Connected to the database!"+ connection);
//            } else {
//                System.out.println("Failed to make connection!");
//            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        Connection connection = JDBCGetConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.driver_class", DRIVER_CLASS);
        properties.setProperty("hibernate.connection.url", DB_URL);
        properties.setProperty("hibernate.connection.username", DB_LOG);
        properties.setProperty("hibernate.connection.password", DB_PASS);
        properties.setProperty("hibernate.dialect", DIALECT);

        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class).setProperties(properties);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
