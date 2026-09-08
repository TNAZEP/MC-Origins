package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class MemoryExpiryDataFix extends NamedEntityFix {
   public MemoryExpiryDataFix(Schema var1, String var2) {
      super(â˜ƒ, false, "Memory expiry data fix (" + â˜ƒ + ")", References.ENTITY, â˜ƒ);
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      return â˜ƒ.update("Brain", this::updateBrain);
   }

   private Dynamic<?> updateBrain(Dynamic<?> var1) {
      return â˜ƒ.update("memories", this::updateMemories);
   }

   private Dynamic<?> updateMemories(Dynamic<?> var1) {
      return â˜ƒ.updateMapValues(this::updateMemoryEntry);
   }

   private Pair<Dynamic<?>, Dynamic<?>> updateMemoryEntry(Pair<Dynamic<?>, Dynamic<?>> var1) {
      return â˜ƒ.mapSecond(this::wrapMemoryValue);
   }

   private Dynamic<?> wrapMemoryValue(Dynamic<?> var1) {
      return â˜ƒ.createMap(ImmutableMap.of(â˜ƒ.createString("value"), â˜ƒ));
   }
}
