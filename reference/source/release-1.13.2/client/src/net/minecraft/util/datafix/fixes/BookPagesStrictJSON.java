package net.minecraft.util.datafix.fixes;

import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.stream.Stream;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.StringUtils;

public class BookPagesStrictJSON extends DataFix {
   public BookPagesStrictJSON(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   public Dynamic<?> func_209633_a(Dynamic<?> var1) {
      return ☃.update("pages", var1x -> DataFixUtils.orElse(var1x.getStream().map(var0x -> var0x.map(var0xx -> {
               if (!var0xx.getStringValue().isPresent()) {
                  return var0xx;
               } else {
                  String ☃ = (String)var0xx.getStringValue().get();
                  ITextComponent ☃x = null;
                  if (!"null".equals(☃) && !StringUtils.isEmpty(☃)) {
                     if (☃.charAt(0) == '"' && ☃.charAt(☃.length() - 1) == '"' || ☃.charAt(0) == '{' && ☃.charAt(☃.length() - 1) == '}') {
                        try {
                           ☃x = JsonUtils.func_188176_a(SignStrictJSON.field_188225_a, ☃, ITextComponent.class, true);
                           if (☃x == null) {
                              ☃x = new TextComponentString("");
                           }
                        } catch (JsonParseException var6) {
                        }

                        if (☃x == null) {
                           try {
                              ☃x = ITextComponent.Serializer.func_150699_a(☃);
                           } catch (JsonParseException var5) {
                           }
                        }

                        if (☃x == null) {
                           try {
                              ☃x = ITextComponent.Serializer.func_186877_b(☃);
                           } catch (JsonParseException var4) {
                           }
                        }

                        if (☃x == null) {
                           ☃x = new TextComponentString(☃);
                        }
                     } else {
                        ☃x = new TextComponentString(☃);
                     }
                  } else {
                     ☃x = new TextComponentString("");
                  }

                  return var0xx.createString(ITextComponent.Serializer.func_150696_a(☃x));
               }
            })).map(☃::createList), ☃.emptyList()));
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211295_k);
      OpticFinder<?> ☃x = ☃.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWrittenBookPagesStrictJsonFix", ☃, var2x -> var2x.updateTyped(☃, var1x -> var1x.update(DSL.remainderFinder(), this::func_209633_a))
      );
   }
}
