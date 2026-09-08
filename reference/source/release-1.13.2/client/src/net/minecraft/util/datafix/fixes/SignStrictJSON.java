package net.minecraft.util.datafix.fixes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.StringUtils;

public class SignStrictJSON extends NamedEntityFix {
   public static final Gson field_188225_a = new GsonBuilder().registerTypeAdapter(ITextComponent.class, new JsonDeserializer<ITextComponent>() {
      public ITextComponent deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (☃.isJsonPrimitive()) {
            return new TextComponentString(☃.getAsString());
         } else if (☃.isJsonArray()) {
            JsonArray ☃ = ☃.getAsJsonArray();
            ITextComponent ☃x = null;

            for(JsonElement ☃xx : ☃) {
               ITextComponent ☃xxx = this.deserialize(☃xx, ☃xx.getClass(), ☃);
               if (☃x == null) {
                  ☃x = ☃xxx;
               } else {
                  ☃x.func_150257_a(☃xxx);
               }
            }

            return ☃x;
         } else {
            throw new JsonParseException("Don't know how to turn " + ☃ + " into a Component");
         }
      }
   }).create();

   public SignStrictJSON(Schema var1, boolean var2) {
      super(☃, ☃, "BlockEntitySignTextStrictJsonFix", TypeReferences.field_211294_j, "Sign");
   }

   private Dynamic<?> func_209647_a(Dynamic<?> var1, String var2) {
      String ☃ = ☃.getString(☃);
      ITextComponent ☃x = null;
      if (!"null".equals(☃) && !StringUtils.isEmpty(☃)) {
         if (☃.charAt(0) == '"' && ☃.charAt(☃.length() - 1) == '"' || ☃.charAt(0) == '{' && ☃.charAt(☃.length() - 1) == '}') {
            try {
               ☃x = JsonUtils.func_188176_a(field_188225_a, ☃, ITextComponent.class, true);
               if (☃x == null) {
                  ☃x = new TextComponentString("");
               }
            } catch (JsonParseException var8) {
            }

            if (☃x == null) {
               try {
                  ☃x = ITextComponent.Serializer.func_150699_a(☃);
               } catch (JsonParseException var7) {
               }
            }

            if (☃x == null) {
               try {
                  ☃x = ITextComponent.Serializer.func_186877_b(☃);
               } catch (JsonParseException var6) {
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

      return ☃.set(☃, ☃.createString(ITextComponent.Serializer.func_150696_a(☃x)));
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), var1x -> {
         var1x = this.func_209647_a(var1x, "Text1");
         var1x = this.func_209647_a(var1x, "Text2");
         var1x = this.func_209647_a(var1x, "Text3");
         return this.func_209647_a(var1x, "Text4");
      });
   }
}
