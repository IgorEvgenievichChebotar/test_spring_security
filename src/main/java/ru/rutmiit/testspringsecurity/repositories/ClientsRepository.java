package ru.rutmiit.testspringsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.testspringsecurity.models.Client;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ClientsRepository extends JpaRepository<Client,Integer> {

    Optional<Client> findByUsername(String username);
}
