package com.github.monikaemm;

import java.time.DateTimeException;
import java.time.LocalDateTime;
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







        get("/hello", (req, res) -> {
            Map<String, Object> helloModel = new HashMap<>();
            helloModel.put("message", "Hello, World!");
            return new ModelAndView(helloModel, "hello");
        }, templateEngine);


        get("/registering_visit", (req, res) -> {
            Map<String, Object> visitModel = new HashMap<>();

            return new ModelAndView(visitModel, "registering_visit");

        }, templateEngine);
        post("/registering_visit", (req, res) -> {

            Visit visit = new Visit();
            visit.setName(req.queryParams("name"));
            visit.setSpecies(req.queryParams("species"));
            visit.setPurpose(req.queryParams("purpose"));
            visit.setDate(LocalDateTime.parse(req.queryParams("date"), Visit.formatter));

            saveToDb(visit);

            res.redirect("/visit");
            return "";

        });

        get("/visit", (req, res) -> {
            Map<String, Object> registering_visitModel = new HashMap<>();
            registering_visitModel.put("visits", readFromDb());
            return new ModelAndView(registering_visitModel, "visit");
        }, templateEngine);



        exception(NotFoundException.class, (DateTimeParseException, req, res) -> {
            res.status(500);
            res.body("Resource not found");
            res.redirect("/visit");
        });





    }

    private static List<Visit> readFromDb() {
        JdbcTemplate template = DbAccess.getTemplate();
        return template.query("select visitDate, name, species, purpose from visits order by visitDate", (rs, rowNum) -> {
            Visit visit = new Visit();
            visit.setDate(rs.getTimestamp(1).toLocalDateTime());
            visit.setName(rs.getString(2));
            visit.setSpecies(rs.getString(3));
            visit.setPurpose(rs.getString(4));
            return visit;
        });
    }

    private static void saveToDb(Visit visit) {
        JdbcTemplate template = DbAccess.getTemplate();
        template.update("insert into visits(visitDate, name, species, purpose) values(?,?,?,?)",
                visit.getDate(),
                visit.getName(),
                visit.getSpecies(),
                visit.getPurpose());
    }
}
