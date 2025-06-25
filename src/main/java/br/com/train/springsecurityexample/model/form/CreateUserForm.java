package br.com.train.springsecurityexample.model.form;

import java.util.List;

public record CreateUserForm(String userName, String fullname, String password, List<String> roles) {
}
