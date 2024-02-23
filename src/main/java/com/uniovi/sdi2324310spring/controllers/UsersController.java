package com.uniovi.sdi2324310spring.controllers;

import com.uniovi.sdi2324310spring.services.RolesService;
import com.uniovi.sdi2324310spring.services.SecurityService;
import com.uniovi.sdi2324310spring.validators.SignUpFormValidator;
import com.uniovi.sdi2324310spring.validators.UsersValidator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.sdi2324310spring.entities.*;
import com.uniovi.sdi2324310spring.services.UsersService;
@Controller
public class UsersController {
    @Autowired
    private final UsersService usersService;
    @Autowired
    private final SecurityService securityService;
    @Autowired
    private final RolesService rolesService;
    @Autowired
    private final SignUpFormValidator signUpFormValidator;
    @Autowired
    private final UsersValidator usersValidator;
    public UsersController(UsersService usersService, SecurityService securityService, RolesService rolesService, SignUpFormValidator
            signUpFormValidator, UsersValidator usersValidator) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.rolesService = rolesService;
        this.signUpFormValidator = signUpFormValidator;
        this.usersValidator = usersValidator;
    }
    @RequestMapping("/user/list")
    public String getList(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "fragments/list :: markList";
    }
    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("usersList", usersService.getUsers());
        model.addAttribute("user", new User());
        return "user/add";
    }
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@Validated User user, BindingResult result, Model model) {
        usersValidator.validate(user,result);
        usersService.addUser(user);
        if(result.hasErrors()){
            model.addAttribute("usersList", usersService.getUsers());
            model.addAttribute("user", user);
            return "/user/add";
        }
        usersService.addUser(user);
        return "redirect:/user/list";
    }
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }
    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        User u=usersService.getUser(user.getId());
        u.setDni(user.getDni());
        u.setName(user.getName());
        u.setLastName(user.getLastName());
        usersService.addUser(u);
        return "redirect:/user/details/" + id;
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user,result);
        usersService.addUser(user);
        if(result.hasErrors()){
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(),user.getPassword());
        return "redirect:home";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list :: tableUsers";
    }
}