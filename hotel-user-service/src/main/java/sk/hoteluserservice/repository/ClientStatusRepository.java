package sk.hoteluserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.hoteluserservice.domain.ClientStatus;

public interface ClientStatusRepository extends JpaRepository<ClientStatus, Long> {

}
