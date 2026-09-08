package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class ItemLoreFix extends DataFix {
   public ItemLoreFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("tag");
      return this.fixTypeEverywhereTyped(
         "Item Lore componentize",
         â˜ƒ,
         var1x -> var1x.updateTyped(
               â˜ƒ,
               var0x -> var0x.update(
                     DSL.remainderFinder(),
                     var0xx -> var0xx.update(
                           "display",
                           var0xxx -> var0xxx.update(
                                 "Lore",
                                 var0xxxx -> DataFixUtils.orElse(
                                       var0xxxx.asStreamOpt().map(ItemLoreFix::fixLoreList).map(var0xxxx::createList).result(), var0xxxx
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static <T> Stream<Dynamic<T>> fixLoreList(Stream<Dynamic<T>> var0) {
      return â˜ƒ.map(var0x -> DataFixUtils.orElse(var0x.asString().map(ItemLoreFix::fixLoreEntry).map(var0x::createString).result(), var0x));
   }

   private static String fixLoreEntry(String var0) {
      return Component.Serializer.toJson(new TextComponent(â˜ƒ));
   }
}
