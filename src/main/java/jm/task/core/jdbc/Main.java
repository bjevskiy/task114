package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl uService = new UserServiceImpl();
        uService.createUsersTable();
        uService.saveUser("Ivan", "Gamaz", (byte) 11);
        uService.saveUser("Gruzovik", "Kamaz", (byte) 22);
        uService.saveUser("Telekanal", "Spas", (byte) 33);
        uService.saveUser("Zolotoy", "Unitaz", (byte) 44);
        uService.saveUser("Alla", "Pugacheva", (byte) 55);
        uService.saveUser("Raisa", "Gorbacheva", (byte) 66);
        uService.saveUser("Koshka", "Kuklacheva", (byte) 77);
        uService.getAllUsers();
        uService.cleanUsersTable();
        uService.dropUsersTable();
    }
}
