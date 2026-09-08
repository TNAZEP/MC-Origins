package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.datafix.TypeReferences;

public abstract class EntityRename extends DataFix {
   protected final String field_211313_a;

   public EntityRename(String var1, Schema var2, boolean var3) {
      super(☃, ☃);
      this.field_211313_a = ☃;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> ☃ = this.getInputSchema().findChoiceType(TypeReferences.field_211299_o);
      TaggedChoiceType<String> ☃x = this.getOutputSchema().findChoiceType(TypeReferences.field_211299_o);
      return this.fixTypeEverywhere(this.field_211313_a, ☃, ☃x, var3 -> var4 -> {
            String ☃ = (String)var4.getFirst();
            Type<?> ☃x = (Type)☃.types().get(☃);
            Pair<String, Typed<?>> ☃xx = this.func_209149_a(☃, this.func_209757_a(var4.getSecond(), var3, ☃x));
            Type<?> ☃xxx = (Type)☃.types().get(☃xx.getFirst());
            if (!☃xxx.equals(☃xx.getSecond().getType(), true, true)) {
               throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", ☃xxx, ☃xx.getSecond().getType()));
            } else {
               return Pair.of(☃xx.getFirst(), ☃xx.getSecond().getValue());
            }
         });
   }

   private <A> Typed<A> func_209757_a(Object var1, DynamicOps<?> var2, Type<A> var3) {
      return new Typed<>(☃, ☃, (A)☃);
   }

   protected abstract Pair<String, Typed<?>> func_209149_a(String var1, Typed<?> var2);
}
