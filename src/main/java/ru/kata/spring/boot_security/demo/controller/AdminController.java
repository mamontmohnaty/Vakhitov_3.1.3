package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

        //Создание пользователя с ролью ADMIN (Username = admin, Password = admin)
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        roles.add(new Role("ROLE_ADMIN"));
        User admin = new User("admin", "admin", (byte) 20, "admin@mail.com", "admin");
        admin.setRoles(roles);
        userService.saveUser(admin);
    }

    @GetMapping(value = "/")
    String getAllUsers(Model model, Principal principal) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", userService.findUserByName(principal.getName()));
        return "users";
    }

//    @GetMapping(value = "/add")
//    String showAddUser(Model model) {
//        model.addAttribute("user", new User());
//        return "add";
//    }

    @PostMapping(value = "/add")
    String saveUser(@RequestParam(value = "rolesId") String[] roles,
                    @ModelAttribute("user") @Valid User user,
                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/users";
        }
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }

//    @GetMapping("/edit/{id}")
//    public String updateUserForm(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.findById(id));
//        return "edit";
//    }

    @PatchMapping("/{id}")
    public String update(@RequestParam(value = "editRoles") String[] roles,
                         @ModelAttribute("user") User user) {
//        if (bindingResult.hasErrors()) {
//            return "/edit";
//        }
        user.setRoles(roleService.getSetRoles(roles));
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }
}