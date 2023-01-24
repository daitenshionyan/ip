package aqua.aquatask;

import java.time.LocalDateTime;
import java.util.Optional;

import aqua.storage.Reloadable;

public abstract class AquaTask implements Reloadable {
    public static final String TAG_IS_COMPLETE = "completed";

    private final String name;


    public AquaTask(String name) {
        this.name = name;
    }


    public AquaTask(AquaTask task) {
        this(task.name);
    }


    public abstract AquaTask mark(boolean isComplete);
    
    public abstract boolean isComplete();


    public String getName() {
        return name;
    }


    public Optional<LocalDateTime> getStart() {
        return Optional.empty();
    }


    public boolean isStarted() {
        return getStart()
                .map(time -> LocalDateTime.now().isAfter(time))
                .orElse(true);
    }


    public Optional<LocalDateTime> getEnd() {
        return Optional.empty();
    }


    public boolean isEnded() {
        return getEnd()
                .map(time -> LocalDateTime.now().isAfter(time))
                .orElse(false);
    }


    @Override
    public String toString() {
        return String.format("%s%s %s",
                getStatusString(),
                getMarkString(),
                getName());
    }


    private String getMarkString() {
        return (isComplete()) ? "[X]" : "[ ]";
    }


    private String getStatusString() {
        if (isEnded()) {
            return "[E]";
        } else if (isStarted()) {
            return "[O]";
        }
        return "[B]";
    }
}
