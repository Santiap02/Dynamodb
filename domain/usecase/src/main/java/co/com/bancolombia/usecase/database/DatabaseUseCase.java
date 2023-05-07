package co.com.bancolombia.usecase.database;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DatabaseUseCase {

    private final UserRepository userRepository;


    public User save(User user){
        return userRepository.save(user);
    }

    public User getById(Long id){
        return userRepository.getById(id);
    }

    public List<User> findByType(String type){
        return userRepository.findByType(type);
    }

    public List<User> findByName(String name, String type){
        return userRepository.findByName(name, type);
    }
}
