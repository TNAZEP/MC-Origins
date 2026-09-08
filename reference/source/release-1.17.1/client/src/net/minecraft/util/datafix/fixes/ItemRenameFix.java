package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class ItemRenameFix extends DataFix {
   private final String name;

   public ItemRenameFix(Schema var1, String var2) {
      super(â˜ƒ, false);
      this.name = â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<Pair<String, String>> â˜ƒ = DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString());
      if (!Objects.equals(this.getInputSchema().getType(References.ITEM_NAME), â˜ƒ)) {
         throw new IllegalStateException("item name type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.name, â˜ƒ, var1x -> var1xx -> var1xx.mapSecond(this::fixItem));
      }
   }

   protected abstract String fixItem(String var1);

   public static DataFix create(Schema var0, String var1, final Function<String, String> var2) {
      return new ItemRenameFix(â˜ƒ, â˜ƒ) {
         @Override
         protected String fixItem(String var1) {
            return (String)â˜ƒ.apply(â˜ƒ);
         }
      };
   }
}
