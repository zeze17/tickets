package com.tickets.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class index {

	@GetMapping("/")
    public String indexTemplate(Model model) {
        return "index";
    }
}