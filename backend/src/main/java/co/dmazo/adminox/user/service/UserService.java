package co.dmazo.adminox.user.service;

import co.dmazo.adminox.user.domain.UserDto;
import co.dmazo.adminox.user.domain.UserReport;

import java.util.List;

public interface UserService {

    List<UserReport> getAllUsers();

    UserReport save(UserDto userDto);
}
