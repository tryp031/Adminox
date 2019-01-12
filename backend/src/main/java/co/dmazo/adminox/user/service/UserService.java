package co.dmazo.adminox.user.service;

import co.dmazo.adminox.user.domain.UserDto;
import co.dmazo.adminox.user.domain.UserFilterDto;
import co.dmazo.adminox.user.domain.UserReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserReport> getAllUsers(UserFilterDto userFilterDto);

    UserReport save(UserDto userDto);

    UserReport updateStatus(int userId);

    UserReport uploadProfile(MultipartFile fileProfile, int userId);
}
