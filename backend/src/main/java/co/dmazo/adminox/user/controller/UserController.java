package co.dmazo.adminox.user.controller;

import co.dmazo.adminox.user.domain.UserDto;
import co.dmazo.adminox.user.domain.UserFilterDto;
import co.dmazo.adminox.user.domain.UserReport;
import co.dmazo.adminox.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/getAllUser")
    public @ResponseBody List<UserReport> getAllUser(@RequestBody UserFilterDto userFilterDto) {
        return userService.getAllUsers(userFilterDto);
    }

    @RequestMapping(path = "/save")
    public @ResponseBody UserReport save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @RequestMapping(path = "/updateStatus/{userId}")
    public @ResponseBody UserReport updateStatus(@PathVariable("userId") int userId) {
        return userService.updateStatus(userId);
    }

    @PostMapping("/uploadFile")
    public UserReport uploadProfile(@RequestParam("file") MultipartFile fileProfile, @RequestParam("userId") int userId ) {
        return userService.uploadProfile(fileProfile, userId);
    }
}
