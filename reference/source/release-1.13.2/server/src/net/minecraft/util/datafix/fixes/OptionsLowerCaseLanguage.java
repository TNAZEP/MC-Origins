package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class OptionsLowerCaseLanguage extends DataFix {
   public OptionsLowerCaseLanguage(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsLowerCaseLanguageFix", this.getInputSchema().getType(TypeReferences.field_211289_e), var0 -> var0.update(DSL.remainderFinder(), var0x -> {
               Optional<String> ☃ = var0x.get("lang").flatMap(Dynamic::getStringValue);
               return ☃.isPresent() ? var0x.set("lang", var0x.createString(((String)☃.get()).toLowerCase(Locale.ROOT))) : var0x;
            })
      );
   }
}
