package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ItemCustomNameToComponentFix extends DataFix {
   public ItemCustomNameToComponentFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private Dynamic<?> fixTag(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> â˜ƒ = â˜ƒ.get("display").result();
      if (â˜ƒ.isPresent()) {
         Dynamic<?> â˜ƒx = (Dynamic)â˜ƒ.get();
         Optional<String> â˜ƒxx = â˜ƒx.get("Name").asString().result();
         if (â˜ƒxx.isPresent()) {
            â˜ƒx = â˜ƒx.set("Name", â˜ƒx.createString(Component.Serializer.toJson(new TextComponent((String)â˜ƒxx.get()))));
         } else {
            Optional<String> â˜ƒx = â˜ƒx.get("LocName").asString().result();
            if (â˜ƒx.isPresent()) {
               â˜ƒx = â˜ƒx.set("Name", â˜ƒx.createString(Component.Serializer.toJson(new TranslatableComponent((String)â˜ƒx.get()))));
               â˜ƒx = â˜ƒx.remove("LocName");
            }
         }

         return â˜ƒ.set("display", â˜ƒx);
      } else {
         return â˜ƒ;
      }
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemCustomNameToComponentFix", â˜ƒ, var2x -> var2x.updateTyped(â˜ƒ, var1x -> var1x.update(DSL.remainderFinder(), this::fixTag))
      );
   }
}
