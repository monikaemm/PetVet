package com.github.monikaemm;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        Visit visit1 = new Visit();
        visit1.setDate(LocalDateTime.of(2016,4,10,15,30));
        visit1.setName("Surname1");
        visit1.setSpecies("dog");
        visit1.setPurpose("recognition");

        Visit visit2 = new Visit();
        visit2.setDate(LocalDateTime.of(2016,4,9,10,30));
        visit2.setName("Surname2");
        visit2.setSpecies("cat");
        visit2.setPurpose("recognition");

        Visit visit3 = new Visit();
        visit3.setDate(LocalDateTime.of(2016,4,13,14,0));
        visit3.setName("Surname1");
        visit3.setSpecies("dog");
        visit3.setPurpose("recognition");



        List<Visit> visitList = new ArrayList<>();
        visitList.add(visit1);
        visitList.add(visit2);
        visitList.add(visit3);
        visitList.sort((v1, v2) -> v1.getDate().compareTo(v2.getDate()));

  //      get("/visit", (reg,res) -> visitList);
        get("/registering_visit", (req, res) -> {
            Map<String, Object> visitModel = new HashMap<>();
            visitModel.put("registering_visits", visit1);
            return new ModelAndView(visitModel, "registering_visit");
        }, templateEngine);

        get("/visit", (req, res) -> {
            Map<String, Object> registering_visitModel = new HashMap<>();
            registering_visitModel.put("visits", visitList);
            return new ModelAndView(registering_visitModel, "visit");
        }, templateEngine);
    }
}
