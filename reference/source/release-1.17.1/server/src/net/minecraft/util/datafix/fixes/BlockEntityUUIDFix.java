package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class BlockEntityUUIDFix extends AbstractUUIDFix {
   public BlockEntityUUIDFix(Schema var1) {
      super(â˜ƒ, References.BLOCK_ENTITY);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.typeReference), var1 -> {
         var1 = this.updateNamedChoice(var1, "minecraft:conduit", this::updateConduit);
         return this.updateNamedChoice(var1, "minecraft:skull", this::updateSkull);
      });
   }

   private Dynamic<?> updateSkull(Dynamic<?> var1) {
      return (Dynamic<?>)â˜ƒ.get("Owner")
         .get()
         .map(var0 -> (Dynamic)replaceUUIDString(var0, "Id", "Id").orElse(var0))
         .map(var1x -> â˜ƒ.remove("Owner").set("SkullOwner", var1x))
         .result()
         .orElse(â˜ƒ);
   }

   private Dynamic<?> updateConduit(Dynamic<?> var1) {
      return (Dynamic<?>)replaceUUIDMLTag(â˜ƒ, "target_uuid", "Target").orElse(â˜ƒ);
   }
}
