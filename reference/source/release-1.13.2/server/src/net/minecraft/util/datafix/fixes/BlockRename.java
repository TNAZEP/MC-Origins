package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public abstract class BlockRename extends DataFix {
   private final String field_206310_a;

   public BlockRename(Schema var1, String var2) {
      super(☃, false);
      this.field_206310_a = ☃;
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211300_p);
      Type<Pair<String, String>> ☃x = DSL.named(TypeReferences.field_211300_p.typeName(), DSL.namespacedString());
      if (!Objects.equals(☃, ☃x)) {
         throw new IllegalStateException("block type is not what was expected.");
      } else {
         TypeRewriteRule ☃ = this.fixTypeEverywhere(this.field_206310_a + " for block", ☃x, var1x -> var1xx -> var1xx.mapSecond(this::func_206309_a));
         TypeRewriteRule ☃x = this.fixTypeEverywhereTyped(
            this.field_206310_a + " for block_state",
            this.getInputSchema().getType(TypeReferences.field_211296_l),
            var1x -> var1x.update(DSL.remainderFinder(), var1xx -> {
                  Optional<String> ☃ = var1xx.get("Name").flatMap(Dynamic::getStringValue);
                  return ☃.isPresent() ? var1xx.set("Name", var1xx.createString(this.func_206309_a((String)☃.get()))) : var1xx;
               })
         );
         return TypeRewriteRule.seq(☃, ☃x);
      }
   }

   protected abstract String func_206309_a(String var1);

   public static DataFix func_207437_a(Schema var0, String var1, final Function<String, String> var2) {
      return new BlockRename(☃, ☃) {
         @Override
         protected String func_206309_a(String var1) {
            return (String)☃.apply(☃);
         }
      };
   }
}
