package aqua.aquatask;

import java.time.LocalDateTime;
import java.util.Optional;

import aqua.util.DateUtils;


/** Implementation of AquaTask to represent an Event */
public class AquaEvent extends AquaTask {
    /** Tag of {@code from} argument when parsing. */
    public static final String TAG_FROM = "from";
    /** Tag of {@code to} argument when parsing. */
    public static final String TO_TAG = "to";

    /** If the event is completed. */
    private final boolean isComplete;
    /** Time the event starts. */
    private final LocalDateTime from;
    /** Time the event ends. */
    private final LocalDateTime to;


    /**
     * Constructs an AquaEvent from the given parameters while setting the
     * event completion status to {@code false}.
     * 
     * @param name - the name of the event.
     * @param from - the time the event starts.
     * @param to - the time the event ends.
     */
    public AquaEvent(String name, LocalDateTime from, LocalDateTime to) {
        this(name, false, from, to);
    }

    /**
     * Constructs an AquaEvent from the given parameters.
     * 
     * @param name - the name of the event.
     * @param isCompelte - if the event is completed.
     * @param from - the time the event starts.
     * @param to - the time the event ends.
     */
    public AquaEvent(String name, boolean isComplete, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.isComplete = isComplete;
        this.from = from;
        this.to = to;
    }


    @Override
    public AquaEvent mark(boolean isComplete) {
        return new AquaEvent(getName(), isComplete, from, to);
    }


    @Override
    public boolean isComplete() {
        return isComplete;
    }


    @Override
    public Optional<LocalDateTime> getStart() {
        return Optional.ofNullable(from);
    }


    @Override
    public Optional<LocalDateTime> getEnd() {
        return Optional.ofNullable(to);
    }

    
    @Override
    public String getReloadString() {
        return String.format("event %s /%s %s /%s %s /%s %s",
                getName(),
                TAG_FROM, from,
                TO_TAG, to,
                TAG_IS_COMPLETE, isComplete);
    }


    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                DateUtils.formatNice(from),
                DateUtils.formatNice(to));
    }
}
