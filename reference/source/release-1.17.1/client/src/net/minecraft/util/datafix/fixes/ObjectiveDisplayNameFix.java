package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class ObjectiveDisplayNameFix extends DataFix {
   public ObjectiveDisplayNameFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.OBJECTIVE);
      return this.fixTypeEverywhereTyped(
         "ObjectiveDisplayNameFix",
         â˜ƒ,
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> var0x.update(
                     "DisplayName",
                     var1x -> DataFixUtils.orElse(
                           var1x.asString().map(var0xx -> Component.Serializer.toJson(new TextComponent(var0xx))).map(var0x::createString).result(), var1x
                        )
                  )
            )
      );
   }
}
