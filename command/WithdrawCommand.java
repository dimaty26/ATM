package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        String theCount;
        Map<Integer, Integer> map;

        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            theCount = ConsoleHelper.readString();

            try {
                int count = Integer.parseInt(theCount);
                if (count > 0) {
                    if (manipulator.isAmountAvailable(count)) {
                        map = manipulator.withdrawAmount(count);
                        for (Map.Entry<Integer, Integer> m : map.entrySet()) {
                            System.out.println("<" + code + ">" + m.getKey() + " - " + m.getValue());

                            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), count, code));
                        }
                        break;
                    } else ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                } else throw new NumberFormatException();
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                continue;
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            }
        }
    }
}
