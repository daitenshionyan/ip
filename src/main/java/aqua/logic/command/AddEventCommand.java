package aqua.logic.command;

import java.time.LocalDateTime;

import aqua.aquatask.AquaEvent;
import aqua.exception.IllegalSyntaxException;
import aqua.logic.ArgumentMap;
import aqua.util.DateUtils;


/**
 * A full implementation of AddTaskCommand that creates and adds AquaEvents.
 */
public class AddEventCommand extends AddTaskCommand {
    /**
     * {@inheritDoc}
     * <p>
     * Specifically, an AquaEvent.
     * 
     * @return an AquaEvent created from the given arguments.
     */
    @Override
    public AquaEvent createTask(ArgumentMap args) throws IllegalSyntaxException {
        String name = args.getMainInput().filter(n -> !n.isBlank())
                .orElseThrow(() -> new IllegalSyntaxException("Name disappeared!"));
        String fromString = args.get("from")
                .orElseThrow(() -> new IllegalSyntaxException("[from] disappeared!"));
        LocalDateTime from = DateUtils.parse(fromString);
        String toString = args.get("to")
                .orElseThrow(() -> new IllegalSyntaxException("[to] disappeared!"));
        LocalDateTime to = DateUtils.parse(toString);
        boolean isCompleted = args.get(AquaEvent.IS_COMPLETED_TAG)
                .map(isComp -> Boolean.parseBoolean(isComp))
                .orElse(false);
        return new AquaEvent(name, isCompleted, from, to);
    }
}
