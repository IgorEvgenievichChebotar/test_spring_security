package ru.rutmiit.testspringsecurity.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.testspringsecurity.models.Client;
import ru.rutmiit.testspringsecurity.security.ClientDetails;

@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping
    public String show(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails = (ClientDetails) authentication.getPrincipal();
        model.addAttribute("client", clientDetails.getClient());
        return "homePage";
    }
}
