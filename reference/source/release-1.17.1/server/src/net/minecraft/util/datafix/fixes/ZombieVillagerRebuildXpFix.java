package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ZombieVillagerRebuildXpFix extends NamedEntityFix {
   public ZombieVillagerRebuildXpFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "Zombie Villager XP rebuild", References.ENTITY, "minecraft:zombie_villager");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), var0 -> {
         Optional<Number> â˜ƒ = var0.get("Xp").asNumber().result();
         if (!â˜ƒ.isPresent()) {
            int â˜ƒx = var0.get("VillagerData").get("level").asInt(1);
            return var0.set("Xp", var0.createInt(VillagerRebuildLevelAndXpFix.getMinXpPerLevel(â˜ƒx)));
         } else {
            return var0;
         }
      });
   }
}
