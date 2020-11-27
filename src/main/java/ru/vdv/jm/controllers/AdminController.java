package ru.vdv.jm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vdv.jm.models.Role;
import ru.vdv.jm.models.User;
import ru.vdv.jm.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user_list";
    }

    @GetMapping(value = "/add")
    public String showAddUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user_ed";
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(required = false) boolean adminCheck,
                          @RequestParam(required = false) boolean userCheck) {
        List<Role> roleList = new ArrayList<>();
        if (adminCheck) {
            roleList.add(new Role("ADMIN"));
            user.setRoles(roleList);
        }
        if (userCheck) {
            roleList.add(new Role("USER"));
            user.setRoles(roleList);
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditUser(Model model, @PathVariable int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateUser(@PathVariable int id,
                             @ModelAttribute("user") User user,
                             @RequestParam(required = false) boolean adminCheck,
                             @RequestParam(required = false) boolean userCheck) {
        user.setId(id);
        List<Role> roleList = new ArrayList<>();
        if (adminCheck) {
            roleList.add(new Role("ADMIN"));
            user.setRoles(roleList);
        }
        if (userCheck) {
            roleList.add(new Role("USER"));
            user.setRoles(roleList);
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}")
    public String getUserById(Model model, @PathVariable int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user_delete";
    }

    @GetMapping(value = "/delete/{id}")
    public String showDeleteUserById(Model model, @PathVariable int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user_delete";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteUserById(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

