package com.github.monikaemm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika Hołysz on 02.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("test");

        List<String> myList = new ArrayList<>();
        myList.add("Hello");
        myList.add("World");
        myList.stream()
                .map(s -> s.toUpperCase())
                .forEach(s -> System.out.println(s));
    }
}
