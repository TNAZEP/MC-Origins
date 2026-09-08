package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class CustomNameStringToComponentItem extends DataFix {
   public CustomNameStringToComponentItem(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   private Dynamic<?> func_209621_a(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> ☃ = ☃.get("display");
      if (☃.isPresent()) {
         Dynamic<?> ☃x = (Dynamic)☃.get();
         Optional<String> ☃xx = ☃x.get("Name").flatMap(Dynamic::getStringValue);
         if (☃xx.isPresent()) {
            ☃x = ☃x.set("Name", ☃x.createString(ITextComponent.Serializer.func_150696_a(new TextComponentString((String)☃xx.get()))));
         } else {
            Optional<String> ☃x = ☃x.get("LocName").flatMap(Dynamic::getStringValue);
            if (☃x.isPresent()) {
               ☃x = ☃x.set("Name", ☃x.createString(ITextComponent.Serializer.func_150696_a(new TextComponentTranslation((String)☃x.get()))));
               ☃x = ☃x.remove("LocName");
            }
         }

         return ☃.set("display", ☃x);
      } else {
         return ☃;
      }
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211295_k);
      OpticFinder<?> ☃x = ☃.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemCustomNameToComponentFix", ☃, var2x -> var2x.updateTyped(☃, var1x -> var1x.update(DSL.remainderFinder(), this::func_209621_a))
      );
   }
}
