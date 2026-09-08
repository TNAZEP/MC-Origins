package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class ItemFilledMapMetadata extends DataFix {
   public ItemFilledMapMetadata(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211295_k);
      OpticFinder<Pair<String, String>> ☃x = DSL.fieldFinder("id", DSL.named(TypeReferences.field_211301_q.typeName(), DSL.namespacedString()));
      OpticFinder<?> ☃xx = ☃.findField("tag");
      return this.fixTypeEverywhereTyped("ItemInstanceMapIdFix", ☃, var2x -> {
         Optional<Pair<String, String>> ☃ = var2x.getOptional(☃);
         if (☃.isPresent() && Objects.equals(((Pair)☃.get()).getSecond(), "minecraft:filled_map")) {
            Dynamic<?> ☃x = var2x.get(DSL.remainderFinder());
            Typed<?> ☃xx = var2x.getOrCreateTyped(☃);
            Dynamic<?> ☃xxx = ☃xx.get(DSL.remainderFinder());
            ☃xxx = ☃xxx.set("map", ☃xxx.createInt(☃x.getInt("Damage")));
            return var2x.set(☃, ☃xx.set(DSL.remainderFinder(), ☃xxx));
         } else {
            return var2x;
         }
      });
   }
}
