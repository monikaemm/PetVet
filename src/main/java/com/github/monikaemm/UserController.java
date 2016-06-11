package com.github.monikaemm;

import org.springframework.jdbc.core.JdbcTemplate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monika Hołysz on 11.06.2016.
 */
public class UserController {

    public ModelAndView loginView(Request req, Response res) {
        Map<String, Object> loginModel = new HashMap<>();
        return new ModelAndView(loginModel, "login");
    }

    public String registerUser(Request req, Response res) {
        User user = new User();
        user.setName(req.queryParams("name"));
        user.setSurname(req.queryParams("surname"));
        user.setLog(req.queryParams("log"));
        user.setPassword(req.queryParams("password"));
        saveToDbLogin(user);
        res.redirect("/visit");
        return null;
    }

    public ModelAndView registrationView(Request req, Response res) {
        Map<String, Object> loginModel = new HashMap<>();
        return new ModelAndView(loginModel, "registration");
    }

    public Object authenticateUser(Request req, Response res) {
        JdbcTemplate template = DbAccess.getTemplate();
        List<User> listUsers = template.query("select id, name, surname, log, type from users where log = ? and password = ?;", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt(1));
            user.setName(rs.getString(2));
            user.setSurname(rs.getString(3));
            user.setLog(rs.getString(4));
            String typeName = rs.getString(5);
            User.Type type = User.Type.forString(typeName);
            user.setType(type);
            return user;
        }, req.queryParams("log"),req.queryParams("password"));
        if(!listUsers.isEmpty()) {
            req.session(true).attribute("user",listUsers.get(0));
            res.redirect("/visit");
        }
        else{
            res.redirect("/login");
        }
        return null;
    }

    private static void saveToDbLogin(User user) {
        JdbcTemplate template = DbAccess.getTemplate();
        template.update("insert into logins(name, surname, log, password) values(?,?,?,?)",
                user.getName(),
                user.getSurname(),
                user.getLog(),
                user.getPassword());
    }
}
