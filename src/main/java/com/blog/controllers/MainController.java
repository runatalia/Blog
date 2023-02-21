/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    @GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("title", "блогер");
		return "home";
	}
            @GetMapping("/about")
	public String aboutPage(Model model) {
		model.addAttribute("title", "Про нас");
		return "about";
	}
}
