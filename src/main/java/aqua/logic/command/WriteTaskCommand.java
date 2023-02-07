package aqua.logic.command;

import java.io.IOException;

import aqua.exception.ProcedureException;
import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionDisplayerTask;
import aqua.logic.ExecutionService;
import aqua.logic.ExecutionTask;
import aqua.manager.IoManager;
import aqua.manager.LogicManager;
import aqua.util.Kaomoji;


/** A {@code CommandController} to save {@code UserTask} state. */
public class WriteTaskCommand extends CommandController {
    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager manager) {
        return ExecutionService.of(new ExecutionTask<Void>(args, manager) {
            @Override
            public Void process(ArgumentMap args, LogicManager manager) throws ProcedureException {
                try {
                    manager.getTaskManager().saveToFile();
                } catch (IOException ioEx) {
                    throw new ProcedureException("Failed to save data", ioEx);
                }
                return null;
            }
        });
    }


    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager logicManager, IoManager ioManager) {
        return ExecutionService.of(new ExecutionDisplayerTask<String>(args, logicManager, ioManager) {
            @Override
            public String process(ArgumentMap args, LogicManager manager) {
                try {
                    manager.getTaskManager().saveToFile();
                } catch (IOException ioEx) {
                    return String.format(String.join("\n",
                                    "I could not put your tasks into the special place to remember things!",
                                    "Please help me with this:",
                                    "  %s",
                                    "If you leave me I might forget everything!!"),
                            ioEx.getMessage());
                }
                return "Safely stored hehe " + Kaomoji.SMUG;
            }

            @Override
            protected void display(String message, IoManager manager) {
                manager.reply(message);
            }
        });
    }
}
