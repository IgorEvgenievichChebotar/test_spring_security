package ru.rutmiit.testspringsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.testspringsecurity.models.Client;
import ru.rutmiit.testspringsecurity.services.ClientsDetailsService;
import ru.rutmiit.testspringsecurity.utils.ClientValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final ClientValidator clientValidator;

    private final ClientsDetailsService clientsDetailsService;

    @Autowired
    public AuthController(ClientValidator clientValidator, ClientsDetailsService clientsDetailsService) {
        this.clientValidator = clientValidator;
        this.clientsDetailsService = clientsDetailsService;
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "auth/login";
    }

    @GetMapping("/reg")
    public String showRegPage(@ModelAttribute("client") Client client){
        return "auth/reg";
    }

    @PostMapping("/reg")
    public String processReg(@ModelAttribute("client") @Valid Client client,
                             BindingResult bindingResult){

        clientValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/reg";
        }

        clientsDetailsService.save(client);

        return "redirect:/auth/login";
    }
}
