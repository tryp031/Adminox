package co.dmazo.adminox.user.dao;

import co.dmazo.adminox.user.domain.UserDto;
import co.dmazo.adminox.user.domain.UserFilterDto;
import co.dmazo.adminox.user.domain.UserReport;

import java.util.List;

public interface UserDao {

    List<UserReport> getAllUsers(UserFilterDto userFilterDto);

    UserReport getUsersById(int id);

    int saveOrUpdate(UserDto userDto);

    void updateStatus(long userId);

    void uploadProfile(UserDto userDto);
}
