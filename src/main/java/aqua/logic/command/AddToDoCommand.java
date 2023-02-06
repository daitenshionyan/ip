package aqua.logic.command;

import aqua.exception.SyntaxException;
import aqua.logic.ArgumentMap;
import aqua.usertask.UserTodo;


/** An {@code AddTaskCommand} to add {@code AquaToDo}. */
public class AddToDoCommand extends AddTaskCommand {
    /**
     * {@inheritDoc}
     * <p>
     * Specifically, an {@code AquaToDo}.
     */
    @Override
    public UserTodo createTask(ArgumentMap args) throws SyntaxException {
        // get name
        String name = args.getMainInput()
                .filter(n -> !n.isBlank())
                .orElseThrow(() -> new SyntaxException("Name disappeared!"));

        // get is complete
        boolean isComplete = args.get(UserTodo.TAG_IS_COMPLETE)
                    .map(isComp -> Boolean.parseBoolean(isComp))
                    .orElse(false);

        // return formed todo task
        return new UserTodo(name, isComplete);
    }


    @Override
    public String getSyntax() {
        return "<literal:name>";
    }


    @Override
    public String getDescription() {
        return "Adds a TODO";
    }
}
