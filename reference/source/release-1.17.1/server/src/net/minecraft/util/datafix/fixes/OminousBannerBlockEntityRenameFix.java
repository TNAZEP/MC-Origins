package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class OminousBannerBlockEntityRenameFix extends NamedEntityFix {
   public OminousBannerBlockEntityRenameFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "OminousBannerBlockEntityRenameFix", References.BLOCK_ENTITY, "minecraft:banner");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }

   private Dynamic<?> fixTag(Dynamic<?> var1) {
      Optional<String> â˜ƒ = â˜ƒ.get("CustomName").asString().result();
      if (â˜ƒ.isPresent()) {
         String â˜ƒx = (String)â˜ƒ.get();
         â˜ƒx = â˜ƒx.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
         return â˜ƒ.set("CustomName", â˜ƒ.createString(â˜ƒx));
      } else {
         return â˜ƒ;
      }
   }
}
