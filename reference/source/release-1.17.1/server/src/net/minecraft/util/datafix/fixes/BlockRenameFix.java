package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class BlockRenameFix extends DataFix {
   private final String name;

   public BlockRenameFix(Schema var1, String var2) {
      super(â˜ƒ, false);
      this.name = â˜ƒ;
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.BLOCK_NAME);
      Type<Pair<String, String>> â˜ƒx = DSL.named(References.BLOCK_NAME.typeName(), NamespacedSchema.namespacedString());
      if (!Objects.equals(â˜ƒ, â˜ƒx)) {
         throw new IllegalStateException("block type is not what was expected.");
      } else {
         TypeRewriteRule â˜ƒ = this.fixTypeEverywhere(this.name + " for block", â˜ƒx, var1x -> var1xx -> var1xx.mapSecond(this::fixBlock));
         TypeRewriteRule â˜ƒx = this.fixTypeEverywhereTyped(
            this.name + " for block_state", this.getInputSchema().getType(References.BLOCK_STATE), var1x -> var1x.update(DSL.remainderFinder(), var1xx -> {
                  Optional<String> â˜ƒ = var1xx.get("Name").asString().result();
                  return â˜ƒ.isPresent() ? var1xx.set("Name", var1xx.createString(this.fixBlock((String)â˜ƒ.get()))) : var1xx;
               })
         );
         return TypeRewriteRule.seq(â˜ƒ, â˜ƒx);
      }
   }

   protected abstract String fixBlock(String var1);

   public static DataFix create(Schema var0, String var1, final Function<String, String> var2) {
      return new BlockRenameFix(â˜ƒ, â˜ƒ) {
         @Override
         protected String fixBlock(String var1) {
            return (String)â˜ƒ.apply(â˜ƒ);
         }
      };
   }
}
