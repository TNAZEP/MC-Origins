package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class SimplestEntityRenameFix extends DataFix {
   private final String name;

   public SimplestEntityRenameFix(String var1, Schema var2, boolean var3) {
      super(â˜ƒ, â˜ƒ);
      this.name = â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> â˜ƒ = this.getInputSchema().findChoiceType(References.ENTITY);
      TaggedChoiceType<String> â˜ƒx = this.getOutputSchema().findChoiceType(References.ENTITY);
      Type<Pair<String, String>> â˜ƒxx = DSL.named(References.ENTITY_NAME.typeName(), NamespacedSchema.namespacedString());
      if (!Objects.equals(this.getOutputSchema().getType(References.ENTITY_NAME), â˜ƒxx)) {
         throw new IllegalStateException("Entity name type is not what was expected.");
      } else {
         return TypeRewriteRule.seq(this.fixTypeEverywhere(this.name, â˜ƒ, â˜ƒx, var3x -> var3xx -> var3xx.mapFirst(var3xxx -> {
                  String â˜ƒ = this.rename(var3xxx);
                  Type<?> â˜ƒx = (Type)â˜ƒ.types().get(var3xxx);
                  Type<?> â˜ƒxx = (Type)â˜ƒ.types().get(â˜ƒ);
                  if (!â˜ƒxx.equals(â˜ƒx, true, true)) {
                     throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", â˜ƒxx, â˜ƒx));
                  } else {
                     return â˜ƒ;
                  }
               })), this.fixTypeEverywhere(this.name + " for entity name", â˜ƒxx, var1x -> var1xx -> var1xx.mapSecond(this::rename)));
      }
   }

   protected abstract String rename(String var1);
}
