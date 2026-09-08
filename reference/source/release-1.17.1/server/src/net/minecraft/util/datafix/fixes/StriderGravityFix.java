package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class StriderGravityFix extends NamedEntityFix {
   public StriderGravityFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "StriderGravityFix", References.ENTITY, "minecraft:strider");
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      return â˜ƒ.get("NoGravity").asBoolean(false) ? â˜ƒ.set("NoGravity", â˜ƒ.createBoolean(false)) : â˜ƒ;
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }
}
