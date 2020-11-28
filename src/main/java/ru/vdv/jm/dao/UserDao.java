package ru.vdv.jm.dao;

import ru.vdv.jm.models.User;

import java.util.List;

public interface UserDao {

    public List<User> getAllUsers();

    public void addUser(User user);

    public void deleteUser(int id);

    public void updateUser(User user);

    public User getUserById(int id);

    public User getUserByUsername(String name);
}
