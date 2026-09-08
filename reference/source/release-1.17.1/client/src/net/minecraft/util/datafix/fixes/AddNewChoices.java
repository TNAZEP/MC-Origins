package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;

public class AddNewChoices extends DataFix {
   private final String name;
   private final TypeReference type;

   public AddNewChoices(Schema var1, String var2, TypeReference var3) {
      super(â˜ƒ, true);
      this.name = â˜ƒ;
      this.type = â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<?> â˜ƒ = this.getInputSchema().findChoiceType(this.type);
      TaggedChoiceType<?> â˜ƒx = this.getOutputSchema().findChoiceType(this.type);
      return this.cap(this.name, â˜ƒ, â˜ƒx);
   }

   protected final <K> TypeRewriteRule cap(String var1, TaggedChoiceType<K> var2, TaggedChoiceType<?> var3) {
      if (â˜ƒ.getKeyType() != â˜ƒ.getKeyType()) {
         throw new IllegalStateException("Could not inject: key type is not the same");
      } else {
         return this.fixTypeEverywhere(â˜ƒ, â˜ƒ, â˜ƒ, var2x -> var2xx -> {
               if (!â˜ƒ.hasType(var2xx.getFirst())) {
                  throw new IllegalArgumentException(String.format("Unknown type %s in %s ", var2xx.getFirst(), this.type));
               } else {
                  return var2xx;
               }
            });
      }
   }
}
