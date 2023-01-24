package aqua.logic.command;

import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionDispatcher;
import aqua.logic.ExecutionTask;
import aqua.manager.AppManager;
import aqua.manager.TaskManager;


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
                return String.format(String.join("\n",
                                "Here is your task list",
                                "%s"),
                        getListMessage(taskManager));
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
