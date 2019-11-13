package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.Operation;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<Operation, Command>() {{
        put(Operation.LOGIN, new LoginCommand());
        put(Operation.INFO, new InfoCommand());
        put(Operation.DEPOSIT, new DepositCommand());
        put(Operation.WITHDRAW, new WithdrawCommand());
        put(Operation.EXIT, new ExitCommand());
    }};

    private static CommandExecutor instance = new CommandExecutor();

    private CommandExecutor() {}

    public static CommandExecutor getInstance(){
        return instance;
    }

    public static final void execute(Operation operation) throws InterruptOperationException {
        if (allKnownCommandsMap.containsKey(operation)) {
            allKnownCommandsMap.get(operation).execute();
        }
    }
}
