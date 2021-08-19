package org.maintask;

import java.util.*;
import java.util.stream.Collectors;

public class Factory {

    public static Detail createRandomDetail() {
        return Detail.getRandomDetail();
    }

    public static void main(String[] args) {
        List<Detail> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(createRandomDetail());
        }
        Map<Detail, Long> collect = list.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        collect.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}
