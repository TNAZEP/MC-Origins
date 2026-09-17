package net.minecraft.src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Shared Beta lookup/direction rules. Each side supplies its original class registrations. */
final class BetaPacketRegistry {
	private Map packetIdToClassMap = new HashMap();
	private Map packetClassToIdMap = new HashMap();
	private Set clientPacketIdList = new HashSet();
	private Set serverPacketIdList = new HashSet();

	void addIdClassMapping(int var0, boolean var1, boolean var2, Class var3) {
		if(packetIdToClassMap.containsKey(Integer.valueOf(var0))) {
			throw new IllegalArgumentException("Duplicate packet id:" + var0);
		} else if(packetClassToIdMap.containsKey(var3)) {
			throw new IllegalArgumentException("Duplicate packet class:" + var3);
		} else {
			packetIdToClassMap.put(Integer.valueOf(var0), var3);
			packetClassToIdMap.put(var3, Integer.valueOf(var0));
			if(var1) {
				clientPacketIdList.add(Integer.valueOf(var0));
			}

			if(var2) {
				serverPacketIdList.add(Integer.valueOf(var0));
			}

		}
	}

	Class getPacketClass(int id) {
		return (Class)packetIdToClassMap.get(Integer.valueOf(id));
	}

	int getPacketId(Class type) {
		return ((Integer)packetClassToIdMap.get(type)).intValue();
	}

	boolean accepts(int id, boolean serverHandler) {
		return serverHandler ? serverPacketIdList.contains(Integer.valueOf(id)) : clientPacketIdList.contains(Integer.valueOf(id));
	}
}
