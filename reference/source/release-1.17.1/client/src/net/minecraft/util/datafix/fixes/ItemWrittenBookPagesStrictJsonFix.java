package net.minecraft.util.datafix.fixes;

import com.google.gson.JsonParseException;
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
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;

public class ItemWrittenBookPagesStrictJsonFix extends DataFix {
   public ItemWrittenBookPagesStrictJsonFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      return â˜ƒ.update("pages", var1x -> DataFixUtils.orElse(var1x.asStreamOpt().map(var0x -> var0x.map(var0xx -> {
               if (!var0xx.asString().result().isPresent()) {
                  return var0xx;
               } else {
                  String â˜ƒ = var0xx.asString("");
                  Component â˜ƒx = null;
                  if (!"null".equals(â˜ƒ) && !StringUtils.isEmpty(â˜ƒ)) {
                     if (â˜ƒ.charAt(0) == '"' && â˜ƒ.charAt(â˜ƒ.length() - 1) == '"' || â˜ƒ.charAt(0) == '{' && â˜ƒ.charAt(â˜ƒ.length() - 1) == '}') {
                        try {
                           â˜ƒx = GsonHelper.fromJson(BlockEntitySignTextStrictJsonFix.GSON, â˜ƒ, Component.class, true);
                           if (â˜ƒx == null) {
                              â˜ƒx = TextComponent.EMPTY;
                           }
                        } catch (JsonParseException var6) {
                        }

                        if (â˜ƒx == null) {
                           try {
                              â˜ƒx = Component.Serializer.fromJson(â˜ƒ);
                           } catch (JsonParseException var5) {
                           }
                        }

                        if (â˜ƒx == null) {
                           try {
                              â˜ƒx = Component.Serializer.fromJsonLenient(â˜ƒ);
                           } catch (JsonParseException var4) {
                           }
                        }

                        if (â˜ƒx == null) {
                           â˜ƒx = new TextComponent(â˜ƒ);
                        }
                     } else {
                        â˜ƒx = new TextComponent(â˜ƒ);
                     }
                  } else {
                     â˜ƒx = TextComponent.EMPTY;
                  }

                  return var0xx.createString(Component.Serializer.toJson(â˜ƒx));
               }
            })).map(â˜ƒ::createList).result(), â˜ƒ.emptyList()));
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWrittenBookPagesStrictJsonFix", â˜ƒ, var2x -> var2x.updateTyped(â˜ƒ, var1x -> var1x.update(DSL.remainderFinder(), this::fixTag))
      );
   }
}
