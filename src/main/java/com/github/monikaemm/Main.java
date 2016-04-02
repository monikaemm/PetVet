package com.github.monikaemm;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Created by Monika Hołysz on 02.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        int portNumber = Optional.ofNullable(System.getProperty("server.port"))
                .map(Integer::parseInt)
                .orElse(4567);
        port(portNumber);

        ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine();

        get("/hello", (req, res) -> {
            Map<String, Object> helloModel = new HashMap<>();
            helloModel.put("message", "Hello, World!");
            return new ModelAndView(helloModel, "hello");
        }, templateEngine);
    }
}
