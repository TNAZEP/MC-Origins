package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class JigsawPropertiesFix extends NamedEntityFix {
   public JigsawPropertiesFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "JigsawPropertiesFix", References.BLOCK_ENTITY, "minecraft:jigsaw");
   }

   private static Dynamic<?> fixTag(Dynamic<?> var0) {
      String â˜ƒ = â˜ƒ.get("attachement_type").asString("minecraft:empty");
      String â˜ƒx = â˜ƒ.get("target_pool").asString("minecraft:empty");
      return â˜ƒ.set("name", â˜ƒ.createString(â˜ƒ))
         .set("target", â˜ƒ.createString(â˜ƒ))
         .remove("attachement_type")
         .set("pool", â˜ƒ.createString(â˜ƒx))
         .remove("target_pool");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), JigsawPropertiesFix::fixTag);
   }
}
