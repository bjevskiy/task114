package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT " +
            "EXISTS public.users" +
            "(id BIGSERIAL PRIMARY KEY, name TEXT, lastName TEXT," +
            "age INTEGER)";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " +
            "public.users";
    private static final String SAVE_USER_QUERY = "INSERT INTO public.users " +
            "(name, lastName, age) " +
            "VALUES " +
            "(?, ?, ?)";
    private static final String REMOVE_BY_ID_QUERY = "DELETE FROM public.users " +
            "WHERE id" +
            " = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM public.users";
    private static final String CLEAN_USERS_TABLE_QUERY = "DELETE FROM public.users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement statement = Util.getPreparedStatement(CREATE_TABLE_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = Util.getPreparedStatement(DROP_TABLE_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = Util.getPreparedStatement(SAVE_USER_QUERY)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getPreparedStatement(REMOVE_BY_ID_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>();
        try (PreparedStatement statement = Util.getPreparedStatement(GET_ALL_USERS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = Util.getPreparedStatement(CLEAN_USERS_TABLE_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}
