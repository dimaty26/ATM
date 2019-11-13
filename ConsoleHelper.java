package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static void printExitMessage() {
        System.out.println("Bye!");
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString("choose.operation"));
        String operation;
        Operation result;
        while (true) {
            try {
                operation = readString();
                if (Integer.parseInt(operation) == 0) throw new IllegalArgumentException();
                result = Operation.getAllowableOperationByOrdinal(Integer.parseInt(operation));
            } catch (IllegalArgumentException e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return result;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        String code;
        writeMessage(res.getString("choose.currency.code"));
        while (true) {
            code = readString();
            if (code.length() == 3)
                break;
            else {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return code.toUpperCase();

    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {

        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        String[] input;
        while (true) {
            input = readString().split(" ");

            int nominal = 0;
            int total = 0;
            if (input.length > 2) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            try {
                nominal = Integer.parseInt(input[0]);
                total = Integer.parseInt(input[1]);
            } catch (Exception e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            if (nominal <= 0 || total <= 0) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return input;

    }

    public static String readString() throws InterruptOperationException {
        String result = "";
        try {
            result = bis.readLine();
            if (result.equalsIgnoreCase("EXIT")) {
                throw new InterruptOperationException();
            }
        } catch (IOException e) {}

        return result;
    }
}
