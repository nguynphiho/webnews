package com.webnewsproject.controller;

import com.webnewsproject.domain.Roles;
import com.webnewsproject.domain.Users;
import com.webnewsproject.service.RolesService;
import com.webnewsproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class SignUpController {
    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user",new Users());
        return "signupform";
    }

    @PostMapping("/signup")
    public String doSignup(@ModelAttribute("user") Users user, Model model){
        Users newuser = userService.findByUsername(user.getUsername());
        if (newuser == null){
            //set default role
            Set<Roles> rolesSet = new HashSet<>();
            rolesSet.add(rolesService.findByName("ROLE_USER"));
            user.setRoles(rolesSet);

            //enable account
            user.setEnable(true);

            //encode password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncode = encoder.encode(user.getPassword());
            user.setPassword(passwordEncode);

            //create new account
            userService.save(user);
            model.addAttribute("message","Create new account successfully");
            return "login";
        }

        // handle error if account existed
        model.addAttribute("error","UserName existed!");
        return "signupform";
    }
}
