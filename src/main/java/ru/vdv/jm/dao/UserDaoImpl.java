package ru.vdv.jm.dao;

import org.springframework.stereotype.Repository;
import ru.vdv.jm.models.Role;
import ru.vdv.jm.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserById(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public User getUserByUsername(String name) {
        TypedQuery<User> typedQuery = entityManager.createQuery("FROM User u where u.name=:name", User.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getSingleResult();
    }

    @Override
    public Role getRoleByName(String role) {
        TypedQuery<Role> typedQuery = entityManager.createQuery( "FROM Role r WHERE r.name=:role", Role.class);
        typedQuery.setParameter("role", role);
        return typedQuery.getSingleResult();
    }
}
