package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemWaterPotionFix extends DataFix {
   public ItemWaterPotionFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<Pair<String, String>> â˜ƒx = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      OpticFinder<?> â˜ƒxx = â˜ƒ.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWaterPotionFix",
         â˜ƒ,
         var2x -> {
            Optional<Pair<String, String>> â˜ƒ = var2x.getOptional(â˜ƒ);
            if (â˜ƒ.isPresent()) {
               String â˜ƒx = (String)((Pair)â˜ƒ.get()).getSecond();
               if ("minecraft:potion".equals(â˜ƒx)
                  || "minecraft:splash_potion".equals(â˜ƒx)
                  || "minecraft:lingering_potion".equals(â˜ƒx)
                  || "minecraft:tipped_arrow".equals(â˜ƒx)) {
                  Typed<?> â˜ƒxx = var2x.getOrCreateTyped(â˜ƒ);
                  Dynamic<?> â˜ƒxxx = â˜ƒxx.get(DSL.remainderFinder());
                  if (!â˜ƒxxx.get("Potion").asString().result().isPresent()) {
                     â˜ƒxxx = â˜ƒxxx.set("Potion", â˜ƒxxx.createString("minecraft:water"));
                  }
   
                  return var2x.set(â˜ƒ, â˜ƒxx.set(DSL.remainderFinder(), â˜ƒxxx));
               }
            }
   
            return var2x;
         }
      );
   }
}
