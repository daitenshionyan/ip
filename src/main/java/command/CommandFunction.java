package command;

import exception.DukeIllegalArgumentException;

@FunctionalInterface
public interface CommandFunction {
    public String apply(CommandInput input) throws DukeIllegalArgumentException;
}
