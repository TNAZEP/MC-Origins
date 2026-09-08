package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;

public class AddNewChoices extends DataFix {
   private final String field_206292_a;
   private final TypeReference field_206293_b;

   public AddNewChoices(Schema var1, String var2, TypeReference var3) {
      super(☃, true);
      this.field_206292_a = ☃;
      this.field_206293_b = ☃;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<?> ☃ = this.getInputSchema().findChoiceType(this.field_206293_b);
      TaggedChoiceType<?> ☃x = this.getOutputSchema().findChoiceType(this.field_206293_b);
      return this.func_206290_a(this.field_206292_a, ☃, ☃x);
   }

   protected final <K> TypeRewriteRule func_206290_a(String var1, TaggedChoiceType<K> var2, TaggedChoiceType<?> var3) {
      if (☃.getKeyType() != ☃.getKeyType()) {
         throw new IllegalStateException("Could not inject: key type is not the same");
      } else {
         return this.fixTypeEverywhere(☃, ☃, ☃, var2x -> var2xx -> {
               if (!☃.hasType(var2xx.getFirst())) {
                  throw new IllegalArgumentException(String.format("Unknown type %s in %s ", var2xx.getFirst(), this.field_206293_b));
               } else {
                  return var2xx;
               }
            });
      }
   }
}
