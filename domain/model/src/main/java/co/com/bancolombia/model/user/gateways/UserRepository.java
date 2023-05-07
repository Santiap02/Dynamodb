package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User getById(Long id);

    List<User> findByType(String type);

    List<User> findByName(String name, String type);

}
