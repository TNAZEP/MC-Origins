package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class CatTypeFix extends NamedEntityFix {
   public CatTypeFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "CatTypeFix", References.ENTITY, "minecraft:cat");
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      return â˜ƒ.get("CatType").asInt(0) == 9 ? â˜ƒ.set("CatType", â˜ƒ.createInt(10)) : â˜ƒ;
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }
}
