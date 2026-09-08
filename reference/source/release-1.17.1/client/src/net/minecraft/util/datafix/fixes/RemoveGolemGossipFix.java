package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class RemoveGolemGossipFix extends NamedEntityFix {
   public RemoveGolemGossipFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "Remove Golem Gossip Fix", References.ENTITY, "minecraft:villager");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), RemoveGolemGossipFix::fixValue);
   }

   private static Dynamic<?> fixValue(Dynamic<?> var0) {
      return â˜ƒ.update("Gossips", var1 -> â˜ƒ.createList(var1.asStream().filter(var0x -> !var0x.get("Type").asString("").equals("golem"))));
   }
}
