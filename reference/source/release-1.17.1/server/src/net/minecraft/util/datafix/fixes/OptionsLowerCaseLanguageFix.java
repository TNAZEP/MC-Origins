package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Optional;

public class OptionsLowerCaseLanguageFix extends DataFix {
   public OptionsLowerCaseLanguageFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsLowerCaseLanguageFix", this.getInputSchema().getType(References.OPTIONS), var0 -> var0.update(DSL.remainderFinder(), var0x -> {
               Optional<String> â˜ƒ = var0x.get("lang").asString().result();
               return â˜ƒ.isPresent() ? var0x.set("lang", var0x.createString(((String)â˜ƒ.get()).toLowerCase(Locale.ROOT))) : var0x;
            })
      );
   }
}
