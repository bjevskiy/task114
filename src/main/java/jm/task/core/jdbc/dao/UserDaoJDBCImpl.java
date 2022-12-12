package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS public.users" +
                "(id BIGSERIAL PRIMARY KEY, name TEXT, lastName TEXT," +
                "age INTEGER)";
        try (PreparedStatement statement = Util.getPreparedStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS public.users";
        try (PreparedStatement statement = Util.getPreparedStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO public.users (name, lastName, age) VALUES " +
                "(?, ?, ?)";
        try (PreparedStatement statement = Util.getPreparedStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM public.users WHERE id = ?";
        try (PreparedStatement statement = Util.getPreparedStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>();
        String sql = "SELECT * FROM public.users";
        try (PreparedStatement statement = Util.getPreparedStatement(sql);
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
        String sql = "DELETE FROM public.users";
        try (PreparedStatement statement = Util.getPreparedStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}
