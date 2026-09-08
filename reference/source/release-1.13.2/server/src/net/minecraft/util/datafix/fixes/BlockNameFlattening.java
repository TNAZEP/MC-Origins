package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class BlockNameFlattening extends DataFix {
   public BlockNameFlattening(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211300_p);
      Type<?> ☃x = this.getOutputSchema().getType(TypeReferences.field_211300_p);
      Type<Pair<String, Either<Integer, String>>> ☃xx = DSL.named(TypeReferences.field_211300_p.typeName(), DSL.or(DSL.intType(), DSL.namespacedString()));
      Type<Pair<String, String>> ☃xxx = DSL.named(TypeReferences.field_211300_p.typeName(), DSL.namespacedString());
      if (Objects.equals(☃, ☃xx) && Objects.equals(☃x, ☃xxx)) {
         return this.fixTypeEverywhere(
            "BlockNameFlatteningFix",
            ☃xx,
            ☃xxx,
            var0 -> var0x -> var0x.mapSecond(
                     var0xx -> var0xx.map(
                           BlockStateFlatteningMap::func_207215_a, var0xxx -> BlockStateFlatteningMap.func_199198_a(NamespacedSchema.func_206477_f(var0xxx))
                        )
                  )
         );
      } else {
         throw new IllegalStateException("Expected and actual types don't match.");
      }
   }
}
