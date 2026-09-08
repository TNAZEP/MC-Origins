package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class OptionsKeyTranslationFix extends DataFix {
   public OptionsKeyTranslationFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsKeyTranslationFix",
         this.getInputSchema().getType(References.OPTIONS),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> (Dynamic)var0x.getMapValues()
                     .map(var1 -> var0x.createMap((Map<? extends Dynamic<?>, ? extends Dynamic<?>>)var1.entrySet().stream().map(var1x -> {
                           if (((Dynamic)var1x.getKey()).asString("").startsWith("key_")) {
                              String â˜ƒ = ((Dynamic)var1x.getValue()).asString("");
                              if (!â˜ƒ.startsWith("key.mouse") && !â˜ƒ.startsWith("scancode.")) {
                                 return Pair.of((Dynamic)var1x.getKey(), var0x.createString("key.keyboard." + â˜ƒ.substring("key.".length())));
                              }
                           }
         
                           return Pair.of((Dynamic)var1x.getKey(), (Dynamic)var1x.getValue());
                        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))))
                     .result()
                     .orElse(var0x)
            )
      );
   }
}
