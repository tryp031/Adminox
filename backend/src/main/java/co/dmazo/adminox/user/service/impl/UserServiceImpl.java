package co.dmazo.adminox.user.service.impl;

import co.dmazo.adminox.user.dao.UserDao;
import co.dmazo.adminox.user.domain.UserDto;
import co.dmazo.adminox.user.domain.UserFilterDto;
import co.dmazo.adminox.user.domain.UserReport;
import co.dmazo.adminox.user.domain.UserSecurityReport;
import co.dmazo.adminox.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public List<UserReport> getAllUsers(UserFilterDto userFilterDto) {
        return userDao.getAllUsers(userFilterDto);
    }

    @Override
    public UserReport save(UserDto userDto) {
        int userId = userDao.saveOrUpdate(userDto);
        return userDao.getUsersById(userId);
    }

    @Override
    public UserReport updateStatus(int userId) {
        userDao.updateStatus(userId);
        return userDao.getUsersById(userId);
    }

    @Override
    public UserReport uploadProfile(MultipartFile fileProfile, int userId) {
        try {
            UserDto userDto = new UserDto();
            userDto.setId(userId);
            userDto.setImageProfile(fileProfile.getBytes());

            userDao.uploadProfile(userDto);
        } catch (IOException ex) {
            System.out.println("Error load image profile");
        }
        return userDao.getUsersById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserSecurityReport userSecurityReport = userDao.getUsersByLogin(login);
        if (userSecurityReport == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(userSecurityReport.getLogin(), userSecurityReport.getPassword(), emptyList());
    }
}
