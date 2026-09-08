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
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemStackMapIdFix extends DataFix {
   public ItemStackMapIdFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<Pair<String, String>> â˜ƒx = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      OpticFinder<?> â˜ƒxx = â˜ƒ.findField("tag");
      return this.fixTypeEverywhereTyped("ItemInstanceMapIdFix", â˜ƒ, var2x -> {
         Optional<Pair<String, String>> â˜ƒ = var2x.getOptional(â˜ƒ);
         if (â˜ƒ.isPresent() && Objects.equals(((Pair)â˜ƒ.get()).getSecond(), "minecraft:filled_map")) {
            Dynamic<?> â˜ƒx = var2x.get(DSL.remainderFinder());
            Typed<?> â˜ƒxx = var2x.getOrCreateTyped(â˜ƒ);
            Dynamic<?> â˜ƒxxx = â˜ƒxx.get(DSL.remainderFinder());
            â˜ƒxxx = â˜ƒxxx.set("map", â˜ƒxxx.createInt(â˜ƒx.get("Damage").asInt(0)));
            return var2x.set(â˜ƒ, â˜ƒxx.set(DSL.remainderFinder(), â˜ƒxxx));
         } else {
            return var2x;
         }
      });
   }
}
