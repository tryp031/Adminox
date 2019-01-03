package co.dmazo.adminox.user.dao;

import co.dmazo.adminox.user.domain.UserReport;

import java.util.List;

public interface UserDao {

    List<UserReport> getAllUsers();
}
