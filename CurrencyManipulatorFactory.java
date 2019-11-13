package com.javarush.task.task26.task2613;

import java.util.*;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private static CurrencyManipulatorFactory ourInstance = new CurrencyManipulatorFactory();

    private CurrencyManipulatorFactory() {}

    public static CurrencyManipulatorFactory getInstance() {
        return ourInstance;
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        if (map.containsKey(currencyCode.toLowerCase())) {
            return map.get(currencyCode.toLowerCase());
        }
        CurrencyManipulator currencyManipulator = new CurrencyManipulator(currencyCode);
        map.put(currencyCode.toLowerCase(), currencyManipulator);

        return currencyManipulator;
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {

        Set<CurrencyManipulator> collection = new HashSet<>();

        for (Map.Entry<String, CurrencyManipulator> m : map.entrySet()) {
            collection.add(m.getValue());
        }
        return collection;
    }
}
