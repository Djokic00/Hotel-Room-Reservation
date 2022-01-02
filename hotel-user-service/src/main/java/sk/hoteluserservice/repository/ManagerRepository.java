package sk.hoteluserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hoteluserservice.domain.Manager;
import sk.hoteluserservice.domain.User;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findUserByUsernameAndPassword(String username, String password);
}
