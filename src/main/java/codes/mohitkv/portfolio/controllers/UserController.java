package codes.mohitkv.portfolio.controllers;

import codes.mohitkv.portfolio.data.models.User;
import codes.mohitkv.portfolio.data.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/create")
    public void create(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

}
