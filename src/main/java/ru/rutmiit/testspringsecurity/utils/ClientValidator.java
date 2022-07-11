package ru.rutmiit.testspringsecurity.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rutmiit.testspringsecurity.models.Client;
import ru.rutmiit.testspringsecurity.services.ClientsDetailsService;

@Component
public class ClientValidator implements Validator {
    private final ClientsDetailsService clientsDetailsService;

    @Autowired
    public ClientValidator(ClientsDetailsService clientsDetailsService) {
        this.clientsDetailsService = clientsDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Client.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;

        try {
            clientsDetailsService.loadUserByUsername(client.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "Человек с таким ником уже существует");
    }
}
