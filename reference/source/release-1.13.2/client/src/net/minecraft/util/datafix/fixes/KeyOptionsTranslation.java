package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.TypeReferences;

public class KeyOptionsTranslation extends DataFix {
   public KeyOptionsTranslation(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsKeyTranslationFix",
         this.getInputSchema().getType(TypeReferences.field_211289_e),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> (Dynamic)var0x.getMapValues()
                     .map(var1 -> var0x.createMap((Map<? extends Dynamic<?>, ? extends Dynamic<?>>)var1.entrySet().stream().map(var1x -> {
                           if (((String)((Dynamic)var1x.getKey()).getStringValue().orElse("")).startsWith("key_")) {
                              String ☃ = (String)((Dynamic)var1x.getValue()).getStringValue().orElse("");
                              if (!☃.startsWith("key.mouse") && !☃.startsWith("scancode.")) {
                                 return Pair.of(var1x.getKey(), var0x.createString("key.keyboard." + ☃.substring("key.".length())));
                              }
                           }
         
                           return Pair.of(var1x.getKey(), var1x.getValue());
                        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))))
                     .orElse(var0x)
            )
      );
   }
}
