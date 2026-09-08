package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;

public class EntityShulkerRotationFix extends NamedEntityFix {
   public EntityShulkerRotationFix(Schema var1) {
      super(â˜ƒ, false, "EntityShulkerRotationFix", References.ENTITY, "minecraft:shulker");
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      List<Double> â˜ƒ = â˜ƒ.get("Rotation").asList(var0 -> var0.asDouble(180.0));
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.set(0, â˜ƒ.get(0) - 180.0);
         return â˜ƒ.set("Rotation", â˜ƒ.createList(â˜ƒ.stream().map(â˜ƒ::createDouble)));
      } else {
         return â˜ƒ;
      }
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }
}
