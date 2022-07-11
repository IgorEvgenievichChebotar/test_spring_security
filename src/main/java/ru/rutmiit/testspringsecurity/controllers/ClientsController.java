package ru.rutmiit.testspringsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.testspringsecurity.models.Client;
import ru.rutmiit.testspringsecurity.services.ClientsDetailsService;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsDetailsService clientsDetailsService;

    @Autowired
    public ClientsController(ClientsDetailsService clientsDetailsService) {
        this.clientsDetailsService = clientsDetailsService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("clients", clientsDetailsService.findAll());
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Client client = clientsDetailsService.findById(id).orElse(null);

        model.addAttribute("client", client);

        return "clients/show";
    }
}
