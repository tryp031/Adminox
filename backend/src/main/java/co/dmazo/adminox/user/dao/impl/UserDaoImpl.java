package co.dmazo.adminox.user.dao.impl;

import co.dmazo.adminox.user.dao.UserDao;
import co.dmazo.adminox.user.domain.UserDto;
import co.dmazo.adminox.user.domain.UserReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public UserReport getUsersById(int id) {
        UserReport result = null;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);

        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("  SELECT user.id,  ")
                .append(" user.name, ")
                .append(" user.lastName, ")
                .append(" CONCAT(user.name, ' ', user.lastName) AS nameComplete, ")
                .append(" user.email, ")
                .append(" user.login, ")
                .append(" user.password, ")
                .append(" user.status, ")
                .append(" user.imageUrl ");
        sbQuery.append(" FROM tbluser user ");
        sbQuery.append(" WHERE id = :id ");

        result = jdbcTemplate.queryForObject(sbQuery.toString(), parameters, BeanPropertyRowMapper.newInstance(UserReport.class));

        return result;
    }

    @Override
    @Transactional
    public int saveOrUpdate(UserDto userDto) {
        int userId = 0;

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", userDto.getId())
                .addValue("name", userDto.getName())
                .addValue("lastName", userDto.getLastName())
                .addValue("email", userDto.getEmail())
                .addValue("login", userDto.getLogin())
                .addValue("password", userDto.getPassword())
                .addValue("status", userDto.getStatus());

        if (userDto.getId() != null && userDto.getId() > 0L) {

            StringBuilder sbQueryUpdate = new StringBuilder();
            sbQueryUpdate.append(" UPDATE tbluser ")
                    .append(" SET name = :name, ")
                    .append(" lastName = :lastName, ")
                    .append(" email = :email, ")
                    .append(" password = :password, ")
                    .append(" status = :status ");
            sbQueryUpdate.append(" WHERE id = :id ");

            jdbcTemplate.update(sbQueryUpdate.toString(), parameters);
            userId = userDto.getId().intValue();
        } else {
            KeyHolder holder = new GeneratedKeyHolder();
            StringBuilder sbQueryInsert = new StringBuilder();
            sbQueryInsert.append(" INSERT INTO tbluser (name, lastName, email, login, password, status) ")
                    .append(" VALUES (:name, :lastName, :email, :login, :password, :status) ");

            jdbcTemplate.update(sbQueryInsert.toString(), parameters, holder);
            userId = holder.getKey().intValue();
        }

        return userId;
    }
}
