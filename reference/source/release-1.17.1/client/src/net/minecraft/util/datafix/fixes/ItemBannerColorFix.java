package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
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
import java.util.stream.Stream;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemBannerColorFix extends DataFix {
   public ItemBannerColorFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<Pair<String, String>> â˜ƒx = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      OpticFinder<?> â˜ƒxx = â˜ƒ.findField("tag");
      OpticFinder<?> â˜ƒxxx = â˜ƒxx.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemBannerColorFix",
         â˜ƒ,
         var3x -> {
            Optional<Pair<String, String>> â˜ƒ = var3x.getOptional(â˜ƒ);
            if (â˜ƒ.isPresent() && Objects.equals(((Pair)â˜ƒ.get()).getSecond(), "minecraft:banner")) {
               Dynamic<?> â˜ƒx = var3x.get(DSL.remainderFinder());
               Optional<? extends Typed<?>> â˜ƒxx = var3x.getOptionalTyped(â˜ƒ);
               if (â˜ƒxx.isPresent()) {
                  Typed<?> â˜ƒxxx = (Typed)â˜ƒxx.get();
                  Optional<? extends Typed<?>> â˜ƒxxxx = â˜ƒxxx.getOptionalTyped(â˜ƒ);
                  if (â˜ƒxxxx.isPresent()) {
                     Typed<?> â˜ƒxxxxx = (Typed)â˜ƒxxxx.get();
                     Dynamic<?> â˜ƒxxxxxx = â˜ƒxxx.get(DSL.remainderFinder());
                     Dynamic<?> â˜ƒxxxxxxx = â˜ƒxxxxx.getOrCreate(DSL.remainderFinder());
                     if (â˜ƒxxxxxxx.get("Base").asNumber().result().isPresent()) {
                        â˜ƒx = â˜ƒx.set("Damage", â˜ƒx.createShort((short)(â˜ƒxxxxxxx.get("Base").asInt(0) & 15)));
                        Optional<? extends Dynamic<?>> â˜ƒxxxxxxxx = â˜ƒxxxxxx.get("display").result();
                        if (â˜ƒxxxxxxxx.isPresent()) {
                           Dynamic<?> â˜ƒxxxxxxxxx = (Dynamic)â˜ƒxxxxxxxx.get();
                           Dynamic<?> â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.createMap(
                              ImmutableMap.of(â˜ƒxxxxxxxxx.createString("Lore"), â˜ƒxxxxxxxxx.createList(Stream.of(â˜ƒxxxxxxxxx.createString("(+NBT"))))
                           );
                           if (Objects.equals(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx)) {
                              return var3x.set(DSL.remainderFinder(), â˜ƒx);
                           }
                        }
   
                        â˜ƒxxxxxxx.remove("Base");
                        return var3x.set(DSL.remainderFinder(), â˜ƒx).set(â˜ƒ, â˜ƒxxx.set(â˜ƒ, â˜ƒxxxxx.set(DSL.remainderFinder(), â˜ƒxxxxxxx)));
                     }
                  }
               }
   
               return var3x.set(DSL.remainderFinder(), â˜ƒx);
            } else {
               return var3x;
            }
         }
      );
   }
}
