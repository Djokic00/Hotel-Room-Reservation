package sk.hoteluserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hoteluserservice.domain.Client;
import sk.hoteluserservice.domain.User;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findUserByUsernameAndPassword(String username, String password);
    <Optional> Client findUserByUsername(String username);
}
