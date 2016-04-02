package com.github.monikaemm;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika Hołysz on 02.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("test");

        get("/hello", (req,res) -> "Hello World");
    }
}
