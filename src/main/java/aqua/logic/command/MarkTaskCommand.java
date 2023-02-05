package aqua.logic.command;

import aqua.aquatask.AquaTask;
import aqua.exception.IllegalSyntaxException;
import aqua.exception.ProcedureException;
import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionDisplayerTask;
import aqua.logic.ExecutionService;
import aqua.logic.ExecutionTask;
import aqua.manager.IoManager;
import aqua.manager.LogicManager;
import aqua.util.Kaomoji;

/** A {@code CommandController} to mark {@code AquaTask} as complete. */
public class MarkTaskCommand extends CommandController {
    /** Value to pass {@link AquaTask#mark(boolean)} when marking a task. */
    private boolean isCompletedMarker;


    /**
     * Constructs a MarkTaskCommand that will produce executor dispatchers that
     * will mark tasks as specified.
     *
     * @param isCompleteMarker - {@code true} to mark task as complete and
     *      {@code false} as incomplete.
     */
    public MarkTaskCommand(boolean isCompletedMarker) {
        this.isCompletedMarker = isCompletedMarker;
    }


    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager manager) {
        return ExecutionService.of(new MarkTask(args, manager));
    }


    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager logicManager, IoManager ioManager) {
        return ExecutionService.of(new MarkDisplayerTask(args, logicManager, ioManager))
                .setFollowUp(new WriteTaskCommand().getService(args, logicManager, ioManager));
    }


    @Override
    public String getSyntax() {
        return "<integer:taskNum>";
    }


    @Override
    public String getDescription() {
        if (isCompletedMarker) {
            return "Mark a task";
        }
        return "Unmark a task";
    }


    private AquaTask markTask(ArgumentMap args, LogicManager manager)
                throws IllegalSyntaxException, ProcedureException {
        try {
            // get index String
            String indexString = args.getMainInput().filter(num -> !num.isBlank())
                    .orElseThrow(() -> new IllegalSyntaxException("Task number disappered!"));

            // parse index String (minus 1 as user enters 1 based index)
            int index = Integer.parseInt(indexString) - 1;

            // mark and return marked task
            return manager.getTaskManager().mark(index, isCompletedMarker);
        } catch (NumberFormatException numEx) {
            throw new IllegalSyntaxException("Task number given was not an integer");
        } catch (IndexOutOfBoundsException oobEx) {
            throw new ProcedureException(
                    "The task number given is out of bounds of my task counting capabilities");
        }
    }





    private class MarkTask extends ExecutionTask<AquaTask> {
        MarkTask(ArgumentMap args, LogicManager manager) {
            super(args, manager);
        }


        @Override
        public AquaTask process(ArgumentMap args, LogicManager manager)
                    throws IllegalSyntaxException, ProcedureException {
            return markTask(args, manager);
        }
    }





    private class MarkDisplayerTask extends ExecutionDisplayerTask<AquaTask> {
        MarkDisplayerTask(ArgumentMap args, LogicManager logicManager, IoManager ioManager) {
            super(args, logicManager, ioManager);
        }


        @Override
        protected AquaTask process(ArgumentMap args, LogicManager manager)
                    throws IllegalSyntaxException, ProcedureException {
            return markTask(args, manager);
        }


        @Override
        protected void display(AquaTask task, IoManager manager) {
            String message;
            if (isCompletedMarker) {
                message = String.format(String.join("\n",
                                "I have marked this task:",
                                "  %s"),
                        task.toString());
            } else {
                message = String.format(String.join("\n",
                                "Uah " + Kaomoji.OH_NO + " Okay I have unmarked the task:",
                                "  %s"),
                        task.toString());
            }
            manager.reply(message);
        }
    }
}
