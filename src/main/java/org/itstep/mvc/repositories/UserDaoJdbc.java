package org.itstep.mvc.repositories;

import org.itstep.mvc.core.annotation.Component;
import org.itstep.mvc.core.db.JdbcTemplate;
import org.itstep.mvc.entities.User;

import java.util.Collections;
import java.util.List;

@Component
public class UserDaoJdbc implements UsersDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public List<User> getUsers() {
//        return jdbcTemplate.query("SELECT * FROM users", row -> new User(
//                        row.getString("id"),
//                        row.getString("name")
//                ));

        return jdbcTemplate.query("SELECT * FROM users", new JdbcTemplate.PropertyBeanRowMapper<>(User.class));
    }

    @Override
    public void addUser(User u) {
        System.out.println(u);
        jdbcTemplate.update("INSERT INTO users (name) VALUES (?)", Collections.singletonList(u.getName()));
    }

    @Override
    public void delUser(String uid) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", Collections.singletonList(uid));
    }
}
