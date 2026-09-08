package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class BannerItemColor extends DataFix {
   public BannerItemColor(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211295_k);
      OpticFinder<Pair<String, String>> ☃x = DSL.fieldFinder("id", DSL.named(TypeReferences.field_211301_q.typeName(), DSL.namespacedString()));
      OpticFinder<?> ☃xx = ☃.findField("tag");
      OpticFinder<?> ☃xxx = ☃xx.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemBannerColorFix",
         ☃,
         var3x -> {
            Optional<Pair<String, String>> ☃ = var3x.getOptional(☃);
            if (☃.isPresent() && Objects.equals(((Pair)☃.get()).getSecond(), "minecraft:banner")) {
               Dynamic<?> ☃x = var3x.get(DSL.remainderFinder());
               Optional<? extends Typed<?>> ☃xx = var3x.getOptionalTyped(☃);
               if (☃xx.isPresent()) {
                  Typed<?> ☃xxx = (Typed)☃xx.get();
                  Optional<? extends Typed<?>> ☃xxxx = ☃xxx.getOptionalTyped(☃);
                  if (☃xxxx.isPresent()) {
                     Typed<?> ☃xxxxx = (Typed)☃xxxx.get();
                     Dynamic<?> ☃xxxxxx = ☃xxx.get(DSL.remainderFinder());
                     Dynamic<?> ☃xxxxxxx = ☃xxxxx.getOrCreate(DSL.remainderFinder());
                     if (☃xxxxxxx.get("Base").flatMap(Dynamic::getNumberValue).isPresent()) {
                        ☃x = ☃x.set("Damage", ☃x.createShort((short)(☃xxxxxxx.getShort("Base") & 15)));
                        Optional<? extends Dynamic<?>> ☃xxxxxxxx = ☃xxxxxx.get("display");
                        if (☃xxxxxxxx.isPresent()) {
                           Dynamic<?> ☃xxxxxxxxx = (Dynamic)☃xxxxxxxx.get();
                           if (Objects.equals(
                              ☃xxxxxxxxx,
                              ☃xxxxxxxxx.emptyMap().merge(☃xxxxxxxxx.createString("Lore"), ☃xxxxxxxxx.createList(Stream.of(☃xxxxxxxxx.createString("(+NBT"))))
                           )) {
                              return var3x.set(DSL.remainderFinder(), ☃x);
                           }
                        }
   
                        ☃xxxxxxx.remove("Base");
                        return var3x.set(DSL.remainderFinder(), ☃x).set(☃, ☃xxx.set(☃, ☃xxxxx.set(DSL.remainderFinder(), ☃xxxxxxx)));
                     }
                  }
               }
   
               return var3x.set(DSL.remainderFinder(), ☃x);
            } else {
               return var3x;
            }
         }
      );
   }
}
