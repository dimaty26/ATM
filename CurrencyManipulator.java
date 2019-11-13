package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {

    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public boolean hasMoney() {
        return denominations.size() != 0;
    }

    public int getTotalAmount() {

        int result = 0;

        for (Map.Entry<Integer, Integer> m : denominations.entrySet()) {
            result += m.getKey() * m.getValue();
        }

        return result;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            int cnt = denominations.get(denomination);
            cnt += count;
            denominations.put(denomination, cnt);
        } else denominations.put(denomination, count);
    }

    public boolean isAmountAvailable(int expectedAmount){
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {

        //SortedMap<Integer, Integer> sortedMap = new TreeMap<>(denominations);
        LinkedHashMap<Integer, Integer> reverseSortedMap = new LinkedHashMap<>();

        denominations.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        Map<Integer, Integer> goalsMap = new TreeMap<>();
        int num;

        for (Map.Entry<Integer, Integer> m : reverseSortedMap.entrySet()) {
            if (m.getKey() <= expectedAmount) {
                num = expectedAmount / m.getKey();
                if (num <= m.getValue()) {
                    goalsMap.put(m.getKey(), num);
                    denominations.put(m.getKey(), m.getValue() - num);
                    expectedAmount -= num * m.getKey();
                }
            }
        }
        if (expectedAmount != 0) {
            throw new NotEnoughMoneyException();
        }

        LinkedHashMap<Integer, Integer> resultSortedMap = new LinkedHashMap<>();
        goalsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEachOrdered(x -> resultSortedMap.put(x.getKey(), x.getValue()));

        return resultSortedMap;
    }
}
