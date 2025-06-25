package br.com.train.springsecurityexample.service.impl;

import br.com.train.springsecurityexample.model.PermisionModel;
import br.com.train.springsecurityexample.model.UserModel;
import br.com.train.springsecurityexample.model.form.CreateUserForm;
import br.com.train.springsecurityexample.repository.PermissionRepository;
import br.com.train.springsecurityexample.repository.UserRepository;
import br.com.train.springsecurityexample.service.UserService;
import br.com.train.springsecurityexample.util.EncriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultUserService implements UserDetailsService, UserService {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserService.class);
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
//    private final AuthService authService;

    public DefaultUserService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
//        this.authService = authService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching for user with username: {}", username);

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    @Override
    public void createUser(CreateUserForm form) {
        log.info("Creating user with username: {}", form.userName());

        if (userRepository.existsByUsername(form.userName())) {
            log.warn("User with username {} already exists", form.userName());
            throw new IllegalArgumentException("User already exists");
        }

        String passwordHash = EncriptUtil.generatePasswordHash(form.password());
        UserModel save = userRepository.save(UserModel.valueOf(form.userName(), form.fullname(), passwordHash, findRoles(form.roles())));


        log.info("User with username {} created successfully", form.userName());

    }

    private List<PermisionModel> findRoles(List<String> roles) {
        List<PermisionModel> roleModels = new ArrayList<>();
        roles.forEach(role -> {
            permissionRepository.findByDescription(role).ifPresent(roleModels::add);
        });
        return roleModels;
    }
}
