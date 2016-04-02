package com.github.monikaemm;
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

        get("/hello", (req,res) -> "Hello World");
    }
}
