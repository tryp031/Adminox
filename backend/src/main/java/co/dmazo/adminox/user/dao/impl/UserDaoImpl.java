package co.dmazo.adminox.user.dao.impl;

import co.dmazo.adminox.user.dao.UserDao;
import co.dmazo.adminox.user.domain.UserReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserReport> getAllUsers() {

        List<UserReport> result = null;

        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append(" SELECT user.id, ")
                .append(" user.name, ")
                .append(" user.lastName, ")
                .append(" CONCAT(user.name, ' ', user.lastName) AS nameComplete, ")
                .append(" user.email, ")
                .append(" user.login, ")
                .append(" user.password, ")
                .append(" user.status, ")
                .append(" user.imageUrl ");
        sbQuery.append(" FROM tbluser user ");

        result = jdbcTemplate.query(sbQuery.toString(), new BeanPropertyRowMapper<>(UserReport.class));

        return result;
    }
}
