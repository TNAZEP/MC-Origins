package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class BedItemColor extends DataFix {
   public BedItemColor(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> ☃ = DSL.fieldFinder("id", DSL.named(TypeReferences.field_211301_q.typeName(), DSL.namespacedString()));
      return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(TypeReferences.field_211295_k), var1x -> {
         Optional<Pair<String, String>> ☃ = var1x.getOptional(☃);
         if (☃.isPresent() && Objects.equals(((Pair)☃.get()).getSecond(), "minecraft:bed")) {
            Dynamic<?> ☃x = var1x.get(DSL.remainderFinder());
            if (☃x.getShort("Damage") == 0) {
               return var1x.set(DSL.remainderFinder(), ☃x.set("Damage", ☃x.createShort((short)14)));
            }
         }

         return var1x;
      });
   }
}
