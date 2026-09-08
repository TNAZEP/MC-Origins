package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BedItemColorFix extends DataFix {
   public BedItemColorFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> â˜ƒ = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(References.ITEM_STACK), var1x -> {
         Optional<Pair<String, String>> â˜ƒ = var1x.getOptional(â˜ƒ);
         if (â˜ƒ.isPresent() && Objects.equals(((Pair)â˜ƒ.get()).getSecond(), "minecraft:bed")) {
            Dynamic<?> â˜ƒx = var1x.get(DSL.remainderFinder());
            if (â˜ƒx.get("Damage").asInt(0) == 0) {
               return var1x.set(DSL.remainderFinder(), â˜ƒx.set("Damage", â˜ƒx.createShort((short)14)));
            }
         }

         return var1x;
      });
   }
}
