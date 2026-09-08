package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.util.datafix.TypeReferences;

public abstract class TypedEntityRenameHelper extends DataFix {
   private final String field_211312_a;

   public TypedEntityRenameHelper(String var1, Schema var2, boolean var3) {
      super(☃, ☃);
      this.field_211312_a = ☃;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> ☃ = this.getInputSchema().findChoiceType(TypeReferences.field_211299_o);
      TaggedChoiceType<String> ☃x = this.getOutputSchema().findChoiceType(TypeReferences.field_211299_o);
      Type<Pair<String, String>> ☃xx = DSL.named(TypeReferences.field_211297_m.typeName(), DSL.namespacedString());
      if (!Objects.equals(this.getOutputSchema().getType(TypeReferences.field_211297_m), ☃xx)) {
         throw new IllegalStateException("Entity name type is not what was expected.");
      } else {
         return TypeRewriteRule.seq(this.fixTypeEverywhere(this.field_211312_a, ☃, ☃x, var3x -> var3xx -> var3xx.mapFirst(var3xxx -> {
                  String ☃ = this.func_211311_a(var3xxx);
                  Type<?> ☃x = (Type)☃.types().get(var3xxx);
                  Type<?> ☃xx = (Type)☃.types().get(☃);
                  if (!☃xx.equals(☃x, true, true)) {
                     throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", ☃xx, ☃x));
                  } else {
                     return ☃;
                  }
               })), this.fixTypeEverywhere(this.field_211312_a + " for entity name", ☃xx, var1x -> var1xx -> var1xx.mapSecond(this::func_211311_a)));
      }
   }

   protected abstract String func_211311_a(String var1);
}
