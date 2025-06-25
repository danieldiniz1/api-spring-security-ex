package br.com.train.springsecurityexample.service;

import br.com.train.springsecurityexample.model.form.CreateUserForm;

public interface UserService {

    void createUser(CreateUserForm createUserForm);
}
