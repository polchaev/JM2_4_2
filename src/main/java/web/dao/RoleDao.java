package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {

    List<Role> allRoles();

    void add(Role role);

    void edit(Role role);

    void delete(Role role);

    Role getById(Long id);

    Role getByName(String name);
}
