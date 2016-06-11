package com.github.monikaemm;

import org.springframework.jdbc.core.JdbcTemplate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
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

    public ModelAndView registrationView(Request req, Response res){
        Map<String, Object> loginModel = new HashMap<>();
        return new ModelAndView(loginModel, "registration");
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
