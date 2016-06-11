package com.github.monikaemm;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javassist.NotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.*;

/**
 * Created by Monika Hołysz on 02.04.2016.
 */
public class Main {
    public static void main(String[] args) {

        int portNumber = Optional.ofNullable(System.getProperty("server.port"))
                .map(Integer::parseInt)
                .orElse(4567);
        port(portNumber);
        staticFileLocation("public");

        ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine();

        VisitController visitController = new VisitController();
        UserController userController = new UserController();

        get("/", (req, res) -> {
            res.redirect("/login");
            return null;
        });

        get("/registering_visit", visitController::registrationView, templateEngine);
        post("/registering_visit", visitController::registerVisit);
        get("/visit", visitController::visitsList, templateEngine);

        get("/login", userController::loginView, templateEngine);
        post("/registration", userController::registerUser);
        get("/registration", userController::registrationView, templateEngine);

        exception(DateTimeParseException.class, (e, req, res) -> {
            res.status(500);
            res.body("Resource not found");
        });


    }

}
