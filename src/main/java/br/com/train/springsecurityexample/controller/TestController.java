package br.com.train.springsecurityexample.controller;

import br.com.train.springsecurityexample.model.form.CreateUserForm;
import br.com.train.springsecurityexample.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/test")
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint is working!");
    }

    @PostMapping("/createUser")
    public ResponseEntity <String> createUser(@RequestBody CreateUserForm createUserForm) {
        userService.createUser(createUserForm);
        return ResponseEntity.ok("User creation ");
    }
}
