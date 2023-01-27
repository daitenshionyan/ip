package aqua.logic.command;

import java.util.LinkedHashMap;
import java.util.Map;

import aqua.aquatask.AquaTask;
import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionService;
import aqua.logic.ExecutionTask;
import aqua.manager.LogicManager;


public class FilterCommand extends Command {
    @Override
    public ExecutionService getDispatcher(ArgumentMap args, LogicManager manager, boolean isLoading) {
        return ExecutionService.of(new ExecutionTask<LinkedHashMap<Integer, AquaTask>>(args, manager) {
            @Override
            public LinkedHashMap<Integer, AquaTask> process(ArgumentMap args, LogicManager manager) {
                return manager.getTaskManager().filter(args.getMainInput().orElse(""));
            }

            @Override
            public String getDataDisplay(LinkedHashMap<Integer, AquaTask> map, LogicManager manager) {
                return String.format("Here are your matching tasks:\n%s", formatMap(map));
            }
        });
    }


    private static String formatMap(LinkedHashMap<Integer, AquaTask> map) {
        if (map.isEmpty()) {
            return "Nothing!!";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, AquaTask> entry : map.entrySet()) {
            builder.append(String.format("%d. %s\n", entry.getKey(), entry.getValue()));
        }
        return builder.toString().strip();
    }
}
