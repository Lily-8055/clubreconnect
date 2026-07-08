package com.clubreconnect.controller;

import com.clubreconnect.entity.User;
import com.clubreconnect.service.ClubService;
import com.clubreconnect.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private ClubService clubService;

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        model.addAttribute("user", user);
        model.addAttribute("clubs", clubService.getAllClubs());
        model.addAttribute("events", eventService.getAllEvents());
        return "dashboard";
    }
}
