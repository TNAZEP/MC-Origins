package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public abstract class NamedEntityFix extends DataFix {
   private final String field_206373_a;
   private final String field_206374_b;
   private final TypeReference field_206375_c;

   public NamedEntityFix(Schema var1, boolean var2, String var3, TypeReference var4, String var5) {
      super(☃, ☃);
      this.field_206373_a = ☃;
      this.field_206375_c = ☃;
      this.field_206374_b = ☃;
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<?> ☃ = DSL.namedChoice(this.field_206374_b, this.getInputSchema().getChoiceType(this.field_206375_c, this.field_206374_b));
      return this.fixTypeEverywhereTyped(
         this.field_206373_a,
         this.getInputSchema().getType(this.field_206375_c),
         this.getOutputSchema().getType(this.field_206375_c),
         var2 -> var2.updateTyped(☃, this.getOutputSchema().getChoiceType(this.field_206375_c, this.field_206374_b), this::func_207419_a)
      );
   }

   protected abstract Typed<?> func_207419_a(Typed<?> var1);
}
