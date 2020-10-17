package ru.vdv.jm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vdv.jm.models.User;
import ru.vdv.jm.service.UserService;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @GetMapping(value = {"/users/add"})
    public String showAddUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping(value = "/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users/";
    }

    @GetMapping(value = {"/users/edit/{id}"})
    public String showEditUser(Model model, @PathVariable int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping(value = {"/users/edit/{id}"})
    public String updateUser(@PathVariable int id,
                             @ModelAttribute("user") User user) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/users/";
    }

    @GetMapping(value = "/users/{id}")
    public String getUserById(Model model, @PathVariable int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = {"/users/delete/{id}"})
    public String showDeleteUserById(Model model, @PathVariable int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping(value = {"/users/delete/{id}"})
    public String deleteUserById(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}

