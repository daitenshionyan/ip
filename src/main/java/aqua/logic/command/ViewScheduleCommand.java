package aqua.logic.command;

import java.time.LocalDateTime;

import aqua.graphic.TaskView;
import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionDisplayerTask;
import aqua.logic.ExecutionService;
import aqua.logic.ExecutionTask;
import aqua.manager.IoManager;
import aqua.manager.LogicManager;
import aqua.manager.TaskFilterReport;
import aqua.util.DateUtils;


public class ViewScheduleCommand extends CommandController {

    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager manager) {
        return ExecutionService.of(new ExecutionTask<DisplayData>(args, manager) {
            @Override
            protected DisplayData process(ArgumentMap args, LogicManager manager) {
                return filterTasks(args, manager);
            }
        });
    }


    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager logicManager, IoManager ioManager) {
        return ExecutionService.of(new ExecutionDisplayerTask<DisplayData>(args, logicManager, ioManager) {
            @Override
            protected DisplayData process(ArgumentMap args, LogicManager manager) {
                return filterTasks(args, manager);
            }

            @Override
            protected void display(DisplayData data, IoManager manager) {
                manager.popup(new TaskView(data.startTime, data.report).getRoot());
            }
        });
    }


    private DisplayData filterTasks(ArgumentMap args, LogicManager manager) {
        LocalDateTime start = DateUtils.getStartOfWeek(LocalDateTime.now());
        LocalDateTime end = start.plusDays(7);
        TaskFilterReport report = manager.getTaskManager().filterWithin(start, end);
        return new DisplayData(report, start);
    }





    private class DisplayData {
        final TaskFilterReport report;
        final LocalDateTime startTime;


        public DisplayData(TaskFilterReport report, LocalDateTime startTime) {
            this.report = report;
            this.startTime = startTime;
        }
    }
}
