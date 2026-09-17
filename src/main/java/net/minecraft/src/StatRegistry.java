package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Shared registration storage; each host's StatList still defines the built-in stats. */
final class StatRegistry {
    static final Map byId = new HashMap();
    static final List all = new ArrayList();
    static final List basic = new ArrayList();
    private StatRegistry() {}
}
