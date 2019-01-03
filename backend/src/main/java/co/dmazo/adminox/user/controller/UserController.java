package co.dmazo.adminox.user.controller;

import co.dmazo.adminox.user.domain.UserReport;
import co.dmazo.adminox.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/hello")
    public @ResponseBody String sayHello() {
        LOG.info("GET called on /hello resource");
        return "HELLO USER LOSER";
    }

    @RequestMapping(path = "/getAllUser")
    public @ResponseBody List<UserReport> getAllUser() {
        return userService.getAllUsers();
    }
}