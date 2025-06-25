package br.com.train.springsecurityexample.repository;

import br.com.train.springsecurityexample.model.PermisionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermisionModel, Long> {

    Optional<PermisionModel> findByDescription(String description);
}
