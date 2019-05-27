package com.boot_project.demo.controller;

import com.boot_project.demo.model.Role;
import com.boot_project.demo.model.User;
import com.boot_project.demo.service.RoleService;
import com.boot_project.demo.service.UserService;
import org.hibernate.context.spi.CurrentSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user,
                       @AuthenticationPrincipal Principal principal,
                       HttpServletResponse response) throws IOException {

        if (user == null && principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oAuth2Authentication.getUserAuthentication();
            if (authentication.isAuthenticated())
                response.sendRedirect("/user");
            return null;
        }

        String url;

        if (user != null) {

            if (user.getRoles().contains(new Role("ADMIN"))) {
                url = "/admin/show";
            } else {
                url = "/user";
            }

            response.sendRedirect(url);
            return null;
        }

        return "login";

    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @RequestMapping(value = "/admin/show", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView show(Model model,
                             @AuthenticationPrincipal User user) {
        String username = "";
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("is_admin", user.getRoles().contains(new Role("ADMIN")));
        if (user != null) {
            username = user.getUsername();

        }

        modelAndView.addObject("username", username);
        modelAndView.setViewName("admin");

        return modelAndView;
    }


    @PostMapping(value = "/admin/add")
    public String addUserMethod(@RequestParam("username") String username,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("roles") String role) {
        Set<Role> roles = getRoles(role);

        userService.addUser(new User(username, email, password, roles));
        return "redirect:/admin/show";
    }

    @PostMapping(value = "/admin/update")
    public void updateMethod(@RequestParam("id") String id,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("roles") String role,
                             HttpServletResponse resp) throws IOException {
        Set<Role> roles = getRoles(role);
        Long user_id = Long.parseLong(id);
        userService.update(new User(user_id, username, email, password, roles));
        resp.sendRedirect("/admin/show");
    }

    @GetMapping(value = "/admin/remove")
    public void removeMethod(@RequestParam("id") String id,
                             HttpServletResponse resp) throws IOException {

        if (id.isEmpty()) {
            resp.sendRedirect("/admin/show");
            return;
        }

        long parseLong = Long.parseLong(id);
        userService.remove(parseLong);
        resp.sendRedirect("/admin/show");

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String user(@AuthenticationPrincipal User user,
                       @AuthenticationPrincipal Principal principal, Model model) {
        String name;
        Map<String, String> details;
        if (user != null) {
            model.addAttribute("is_admin", user.getRoles().contains(new Role("ADMIN")));
        }

        if (user == null && principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oAuth2Authentication.getUserAuthentication();
            details = (Map<String, String>) authentication.getDetails();

            Map<String, String> map = new LinkedHashMap<>();
            map.put("email", details.get("email"));
            map.put("username", details.get("name"));
            name = details.get("name");

            User userByEmail = userService.getUserByEmail(details.get("email"));
            if (userByEmail == null) {
                userService.addUser(new User(details.get("name"), details.get("email"), "123", getRoles("user")));
            }
        } else {
            name = user.getUsername();
        }

        model.addAttribute("username", name);

//        model.addAttribute("is_admin", user.getRoles().contains(new Role("ADMIN")));

        return "user";
    }


    @GetMapping("/403")
    public String access_denied() {
        return "access_denied";
    }

    private Set<Role> getRoles(String role) {
        Set<Role> roles = new HashSet<>();
        Role role_admin = roleService.getRoleById(1L);
        Role role_user = roleService.getRoleById(2L);

        switch (role) {
            case "admin":
                roles.add(role_admin);
                break;
            case "admin,user":
            case "user,admin":
                roles.add(role_admin);
                roles.add(role_user);
                break;
            default:
                roles.add(role_user);
                break;
        }

        return roles;
    }
}
