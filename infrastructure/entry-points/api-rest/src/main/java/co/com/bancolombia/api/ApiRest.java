package co.com.bancolombia.api;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.database.DatabaseUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/dynamo", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final DatabaseUseCase databaseUseCase;

    @PostMapping(path = "/save")
    public User save(@RequestBody User user) {
        return databaseUseCase.save(user);
    }

    @GetMapping(path = "/{id}")
    public User findById(@PathVariable("id") Long id) {
        return databaseUseCase.getById(id);
    }

    @GetMapping(path = "/")
    public List<User> findByType(@RequestParam("type") String type) {
        return databaseUseCase.findByType(type);
    }

    @GetMapping(path = "/name")
    public List<User> findByName(@RequestParam("type") String type,@RequestParam("name") String name) {
        return databaseUseCase.findByName(name, type);
    }

}
