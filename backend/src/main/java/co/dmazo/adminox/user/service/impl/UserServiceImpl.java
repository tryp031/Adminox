package co.dmazo.adminox.user.service.impl;

import co.dmazo.adminox.user.dao.UserDao;
import co.dmazo.adminox.user.domain.UserDto;
import co.dmazo.adminox.user.domain.UserFilterDto;
import co.dmazo.adminox.user.domain.UserReport;
import co.dmazo.adminox.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
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
}
