package aqua.logic.command;

import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionDispatcher;
import aqua.logic.ExecutionTask;
import aqua.manager.AppManager;
import aqua.manager.TaskManager;


/**
 * An implementation of Command that will produce an ExecutionDispatcher that
 * will display the state the task manager.
 */
public class ListCommand implements Command {
    @Override
    public ExecutionDispatcher getDispatcher(ArgumentMap args, AppManager manager) {
        return ExecutionDispatcher.of(new ExecutionTask<TaskManager>(args, manager) {
            @Override
            public TaskManager process(ArgumentMap args, AppManager manager) {
                return manager.getTaskManager();
            }

            @Override
            public String getDataDisplay(TaskManager taskManager, AppManager manager) {
                return String.format(
                    "Here is your task list\n" +
                    "%s",
                    getListMessage(taskManager)
                );
            }
        });
    }


    private String getListMessage(TaskManager manager) {
        if (manager.size() > 0) {
            return manager.toString();
        }
        return "Nothing!!";
    }
}
