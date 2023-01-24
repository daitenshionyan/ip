package aqua.logic;

import java.util.HashMap;
import java.util.Optional;


/**
 * An encapsulation of a {@code HashMap<String, String>} to store arguemnt tags
 * and their associated value.
 */
public class ArgumentMap {
    /** The tag of the main input. */
    public static final String TAG_MAIN_INPUT = "MAIN_INPUT";

    /** Hash map to contain the tag-value pairs. */
    private final HashMap<String, String> inputMap;


    /**
     * Constructs an ArgumentMap containing tag-value mappings similar to the
     * given hash map.
     * 
     * @param inputMap - the hash map to construct the argument map from.
     */
    public ArgumentMap(HashMap<String, String> inputMap) {
        this.inputMap = new HashMap<>(inputMap);
    }


    /**
     * Returns the argument's main input value wrapped in an {@code Optional}.
     * If there are no mappings to this tag, {@code Optional.empty} is
     * returned.
     * 
     * @return the main input value wrapped in an {@code Optional}.
     */
    public Optional<String> getMainInput() {
        return get(TAG_MAIN_INPUT);
    }


    /**
     * Returns the value that is mapped to the specified tag wrapped in an
     * {@code Optional}. If there are no mappings to the tag,
     * {@code Optional.empty} is returned.
     * 
     * @param key - the tag of the value to return.
     * @return the associated value of the tag specified wrapped in an
     *      {@code Optional}.
     */
    public Optional<String> get(String key) {
        return Optional.ofNullable(inputMap.get(key));
    }
}
