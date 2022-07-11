package ru.rutmiit.testspringsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.testspringsecurity.models.Client;
import ru.rutmiit.testspringsecurity.services.ClientsDetailsService;

import javax.validation.Valid;

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

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("client", clientsDetailsService.findById(id).orElse(null));

        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute("client") @Valid Client client,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/clients/edit";

        clientsDetailsService.save(client);

        return "redirect:/clients/" + id;
    }
}
