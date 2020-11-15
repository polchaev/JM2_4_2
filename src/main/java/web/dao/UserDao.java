package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getById(Long id);

    void add(User user);

    void delete(User user);

    void deleteById(Long id);

    void update(User user);

    User getUserByName(String name);

    Role getRoleByName(String name);


}
