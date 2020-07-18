package io.github.apace100.origins.origin;

import io.github.apace100.origins.Origins;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class OriginRegistry {

    private static HashMap<Identifier, Origin> idToOrigin = new HashMap<>();

    public static Origin register(Origin origin) {
        return register(origin.getIdentifier(), origin);
    }

    public static Origin register(Identifier id, Origin origin) {
        if(idToOrigin.containsKey(id)) {
            Origins.LOGGER.error("Duplicate origin id tried to register: '" + id.toString() + "'");
            return null;
        }
        idToOrigin.put(id, origin);
        return origin;
    }

    protected static Origin update(Identifier id, Origin origin) {
        if(idToOrigin.containsKey(id)) {
            Origin old = idToOrigin.get(id);
            idToOrigin.remove(id);
        }
        return register(id, origin);
    }

    public static int size() {
        return idToOrigin.size();
    }

    public static Stream<Identifier> identifiers() {
        return idToOrigin.keySet().stream();
    }

    public static Iterable<Map.Entry<Identifier, Origin>> entries() {
        return idToOrigin.entrySet();
    }

    public static Iterable<Origin> values() {
        return idToOrigin.values();
    }

    public static Origin get(Identifier id) {
        if(!idToOrigin.containsKey(id)) {
            Origins.LOGGER.error("Could not get origin from id '" + id.toString() + "', as it was not registered!");
            return Origin.EMPTY;
        }
        Origin origin = idToOrigin.get(id);
        return origin;
    }

    public static boolean contains(Identifier id) {
        return idToOrigin.containsKey(id);
    }

    public static boolean contains(Origin origin) {
        return contains(origin.getIdentifier());
    }

    public static void clear() {
        idToOrigin.clear();
    }

    public static void reset() {
        clear();
        register(Origin.EMPTY);
        register(Origin.HUMAN);
    }
}
