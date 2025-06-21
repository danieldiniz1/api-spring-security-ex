package br.com.train.springsecurityexample.repository;

import br.com.train.springsecurityexample.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT u FROM UserModel u WHERE u.username = :username")
    Optional<UserModel> findByUsername(@Param("username") String username);
}
