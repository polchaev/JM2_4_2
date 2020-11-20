package web.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = getById(id);
        delete(user);
    }

    @Override
    public void update(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        entityManager.merge(user);
    }


    @Override
    @SuppressWarnings("unchecked")
    public User getUserByName(String login) {
        return entityManager.createQuery("from User where login = :login", User.class).setParameter("login", login).getSingleResult();//.find(User.class, login);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Role getRoleByName(String role) {
        return (Role) entityManager.createQuery("from Role where role = :role", Role.class).setParameter("role", role).getSingleResult();///
    }
}
