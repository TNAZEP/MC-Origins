package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class OminousBannerRenameFix extends DataFix {
   public OminousBannerRenameFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private Dynamic<?> fixTag(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> â˜ƒ = â˜ƒ.get("display").result();
      if (â˜ƒ.isPresent()) {
         Dynamic<?> â˜ƒx = (Dynamic)â˜ƒ.get();
         Optional<String> â˜ƒxx = â˜ƒx.get("Name").asString().result();
         if (â˜ƒxx.isPresent()) {
            String â˜ƒxxx = (String)â˜ƒxx.get();
            â˜ƒxxx = â˜ƒxxx.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
            â˜ƒx = â˜ƒx.set("Name", â˜ƒx.createString(â˜ƒxxx));
         }

         return â˜ƒ.set("display", â˜ƒx);
      } else {
         return â˜ƒ;
      }
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<Pair<String, String>> â˜ƒx = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      OpticFinder<?> â˜ƒxx = â˜ƒ.findField("tag");
      return this.fixTypeEverywhereTyped("OminousBannerRenameFix", â˜ƒ, var3x -> {
         Optional<Pair<String, String>> â˜ƒ = var3x.getOptional(â˜ƒ);
         if (â˜ƒ.isPresent() && Objects.equals(((Pair)â˜ƒ.get()).getSecond(), "minecraft:white_banner")) {
            Optional<? extends Typed<?>> â˜ƒx = var3x.getOptionalTyped(â˜ƒ);
            if (â˜ƒx.isPresent()) {
               Typed<?> â˜ƒxx = (Typed)â˜ƒx.get();
               Dynamic<?> â˜ƒxxx = â˜ƒxx.get(DSL.remainderFinder());
               return var3x.set(â˜ƒ, â˜ƒxx.set(DSL.remainderFinder(), this.fixTag(â˜ƒxxx)));
            }
         }

         return var3x;
      });
   }
}
