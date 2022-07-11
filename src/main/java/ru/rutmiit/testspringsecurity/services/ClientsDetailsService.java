package ru.rutmiit.testspringsecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.testspringsecurity.models.Client;
import ru.rutmiit.testspringsecurity.repositories.ClientsRepository;
import ru.rutmiit.testspringsecurity.security.ClientDetails;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class ClientsDetailsService implements UserDetailsService {
    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsDetailsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Optional<Client> findByUsername(String username){
        return clientsRepository.findByUsername(username);
    }

    public List<Client> findAll(){
        return clientsRepository.findAll(Sort.by("name"));
    }

    public Optional<Client> findById(Integer id){
        return clientsRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientsRepository.findByUsername(username);

        if(client.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new ClientDetails(client.get());
    }

    @Transactional
    public void save(Client client) {
        clientsRepository.save(client);
    }
}
