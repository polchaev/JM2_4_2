package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"users", "/"})
    public String userList(ModelMap model) {
        List<User> userList = userService.getAll();
        model.addAttribute("userList", userList);
        return "users";
    }

    @GetMapping(value = "users/add")
    public String addUser(User user, ModelMap model) {
        List<Role> roles = roleService.allRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "addUser";
    }

    @PostMapping(value = "users/add")
    public String addUser(@RequestParam("roles") Long[] roleId, @ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        for (Long rId : roleId) {
            roles.add(roleService.getById(rId));
        }
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin/";
    }

    @GetMapping(value = "users/{userId}/edit")
    public String editUser(@PathVariable("userId") Long id, ModelMap model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        List<Role> roles = roleService.allRoles();
        model.addAttribute("roles", roles);
        return "user-edit";
    }

    @PostMapping(value = "users/{userId}/edit")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("userId") Long id, @RequestParam("roles") Long[] roleId) {
        Set<Role> roles = new HashSet<>();
        for (Long rId : roleId) {
            roles.add(roleService.getById(rId));
        }
        user.setId(id);
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin/";
    }

    @GetMapping(value = {"/users/{userId}/delete"})
    public String deleteUser(@PathVariable("userId") long userId) {
        userService.deleteById(userId);
        return "redirect:/admin/users";
    }
}
