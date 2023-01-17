package command;

import exception.DukeIllegalArgumentException;
import task.Task;


public class AddTaskFunc implements CommandFunction {
    private final TaskCreator taskCreator;

    public AddTaskFunc(TaskCreator taskCreator) {
        this.taskCreator = taskCreator;
    }


    @Override
    public String apply(CommandInput input) throws DukeIllegalArgumentException {
        Task task = taskCreator.apply(input);
        if (task.getName().isBlank()) {
            throw new DukeIllegalArgumentException("Task name cannot be blank");
        }
        input.getMainManager().getTaskManager().add(task);
        return String.format(
            "Got it. I've added this task:\n" +
            "  %s\n" +
            "Now you have %d tasks in the list.",
            task.toString(),
            input.getMainManager().getTaskManager().size()
        );
    }
}
