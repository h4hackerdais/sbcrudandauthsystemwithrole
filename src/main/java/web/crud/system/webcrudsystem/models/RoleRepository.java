package web.crud.system.webcrudsystem.models;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoles(String name);
}
