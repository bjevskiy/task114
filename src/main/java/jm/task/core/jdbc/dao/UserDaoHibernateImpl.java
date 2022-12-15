package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT " +
            "EXISTS public.users" +
            "(id BIGSERIAL PRIMARY KEY, name TEXT, lastName TEXT," +
            "age INTEGER)";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " +
            "public.users";
    private static final String CLEAN_USERS_TABLE_QUERY = "DELETE FROM public.users";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_QUERY).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.format("Hibernate Exception: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE_QUERY).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.format("Hibernate Exception: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            System.err.format("Hibernate Exception: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            System.err.format("Hibernate Exception: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            List<User> users =
                    session.createQuery("From User").list();
            return users;
        } catch (Exception e) {
            System.err.format("Hibernate Exception: " + e.getMessage());
        }
        return List.of();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_USERS_TABLE_QUERY).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.format("Hibernate Exception: " + e.getMessage());
        }
    }
}
