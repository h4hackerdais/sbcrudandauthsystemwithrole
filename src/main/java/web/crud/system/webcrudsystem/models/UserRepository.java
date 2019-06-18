package web.crud.system.webcrudsystem.models;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByEmailAndPassword(String email, boolean password);
    User findUserByEmail(String email);

}
