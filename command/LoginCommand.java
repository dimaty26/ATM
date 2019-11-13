package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        ConsoleHelper.writeMessage(res.getString("specify.data"));

        while (true) {

            String cardNum = ConsoleHelper.readString();
            String pin = ConsoleHelper.readString();

            if (validCreditCards.containsKey(cardNum)) {
                if (validCreditCards.getString(cardNum).equalsIgnoreCase(pin)) {
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), cardNum));
                    break;
                } else {
                    ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), pin));
                    ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                }
            } else {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), cardNum));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }
        }
    }
}
