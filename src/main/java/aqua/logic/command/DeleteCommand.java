package aqua.logic.command;

import aqua.aquatask.AquaTask;
import aqua.exception.IllegalSyntaxException;
import aqua.exception.ProcedureExecutionException;
import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionService;
import aqua.logic.ExecutionTask;
import aqua.manager.LogicManager;


/**
 * An implementation of Command that will produce an ExecutionDispatcher that
 * will delete an AquaTask from the task manager and then save the state of the
 * task manager to hard disk.
 */
public class DeleteCommand extends Command {
    @Override
    public ExecutionService getDispatcher(ArgumentMap args, LogicManager manager, boolean isLoading) {
        return ExecutionService.of(new DeleteTask(args, manager))
                .setFollowUp(new WriteTaskCommand().getDispatcher(args, manager));
    }





    private class DeleteTask extends ExecutionTask<AquaTask> {
        DeleteTask(ArgumentMap args, LogicManager manager) {
            super(args, manager);
        }


        @Override
        public AquaTask process(ArgumentMap args, LogicManager manager)
                    throws IllegalSyntaxException, ProcedureExecutionException {
            try {
                // get task index string
                String indexString = args.getMainInput().filter(num -> !num.isBlank())
                        .orElseThrow(() -> new IllegalSyntaxException("Task number disappered!"));
                
                // parse index string
                int index = Integer.parseInt(indexString) - 1;

                // delete and return deleted task
                return manager.getTaskManager().delete(index);
            } catch (NumberFormatException numEx) {
                throw new IllegalSyntaxException("Task number given was not an integer");
            } catch (IndexOutOfBoundsException oobEx) {
                throw new ProcedureExecutionException(
                        "The task number given is out of bounds of my task counting capabilities");
            }
        }


        @Override
        public String getDataDisplay(AquaTask task, LogicManager manager) {
            return String.format(String.join("\n",
                            "I have deleted the task:",
                            "  %s",
                            "%s"),
                    task.toString(),
                    getRemainingMessage(manager));
        }


        private String getRemainingMessage(LogicManager manager) {
            int numTask = manager.getTaskManager().size();
            if (numTask > 0) {
                return String.format("You have %d task(s) left, all the best ( ง*`꒳´*)ว",
                        numTask);
            }
            return "٩ (ˊᗜˋ *) و You have no task left~ ☆";
        }
    }
}
