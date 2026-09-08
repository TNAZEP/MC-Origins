package net.minecraft.util.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Util;

public interface ITextComponent extends Message, Iterable<ITextComponent> {
   ITextComponent func_150255_a(Style var1);

   Style func_150256_b();

   default ITextComponent func_150258_a(String var1) {
      return this.func_150257_a(new TextComponentString(☃));
   }

   ITextComponent func_150257_a(ITextComponent var1);

   String func_150261_e();

   @Override
   default String getString() {
      StringBuilder ☃ = new StringBuilder();
      this.func_212640_c().forEach(var1x -> ☃.append(var1x.func_150261_e()));
      return ☃.toString();
   }

   default String func_212636_a(int var1) {
      StringBuilder ☃ = new StringBuilder();
      Iterator<ITextComponent> ☃x = this.func_212640_c().iterator();

      while(☃x.hasNext()) {
         int ☃xx = ☃ - ☃.length();
         if (☃xx <= 0) {
            break;
         }

         String ☃xx = ((ITextComponent)☃x.next()).func_150261_e();
         ☃.append(☃xx.length() <= ☃xx ? ☃xx : ☃xx.substring(0, ☃xx));
      }

      return ☃.toString();
   }

   default String func_150254_d() {
      StringBuilder ☃ = new StringBuilder();
      String ☃x = "";
      Iterator<ITextComponent> ☃xx = this.func_212640_c().iterator();

      while(☃xx.hasNext()) {
         ITextComponent ☃xxx = (ITextComponent)☃xx.next();
         String ☃xxxx = ☃xxx.func_150261_e();
         if (!☃xxxx.isEmpty()) {
            String ☃xxxxx = ☃xxx.func_150256_b().func_150218_j();
            if (!☃xxxxx.equals(☃x)) {
               if (!☃x.isEmpty()) {
                  ☃.append(TextFormatting.RESET);
               }

               ☃.append(☃xxxxx);
               ☃x = ☃xxxxx;
            }

            ☃.append(☃xxxx);
         }
      }

      if (!☃x.isEmpty()) {
         ☃.append(TextFormatting.RESET);
      }

      return ☃.toString();
   }

   List<ITextComponent> func_150253_a();

   Stream<ITextComponent> func_212640_c();

   default Stream<ITextComponent> func_212637_f() {
      return this.func_212640_c().map(ITextComponent::func_212639_b);
   }

   default Iterator<ITextComponent> iterator() {
      return this.func_212637_f().iterator();
   }

   ITextComponent func_150259_f();

   default ITextComponent func_212638_h() {
      ITextComponent ☃ = this.func_150259_f();
      ☃.func_150255_a(this.func_150256_b().func_150232_l());

      for(ITextComponent ☃x : this.func_150253_a()) {
         ☃.func_150257_a(☃x.func_212638_h());
      }

      return ☃;
   }

   default ITextComponent func_211710_a(Consumer<Style> var1) {
      ☃.accept(this.func_150256_b());
      return this;
   }

   default ITextComponent func_211709_a(TextFormatting... var1) {
      for(TextFormatting ☃ : ☃) {
         this.func_211708_a(☃);
      }

      return this;
   }

   default ITextComponent func_211708_a(TextFormatting var1) {
      Style ☃ = this.func_150256_b();
      if (☃.func_96302_c()) {
         ☃.func_150238_a(☃);
      }

      if (☃.func_96301_b()) {
         switch(☃) {
            case OBFUSCATED:
               ☃.func_150237_e(true);
               break;
            case BOLD:
               ☃.func_150227_a(true);
               break;
            case STRIKETHROUGH:
               ☃.func_150225_c(true);
               break;
            case UNDERLINE:
               ☃.func_150228_d(true);
               break;
            case ITALIC:
               ☃.func_150217_b(true);
         }
      }

      return this;
   }

   static ITextComponent func_212639_b(ITextComponent var0) {
      ITextComponent ☃ = ☃.func_150259_f();
      ☃.func_150255_a(☃.func_150256_b().func_150206_m());
      return ☃;
   }

   public static class Serializer implements JsonDeserializer<ITextComponent>, JsonSerializer<ITextComponent> {
      private static final Gson field_150700_a = Util.func_199748_a(() -> {
         GsonBuilder ☃ = new GsonBuilder();
         ☃.registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer());
         ☃.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
         ☃.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
         return ☃.create();
      });
      private static final Field field_197674_b = Util.func_199748_a(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field ☃ = JsonReader.class.getDeclaredField("pos");
            ☃.setAccessible(true);
            return ☃;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", var1);
         }
      });
      private static final Field field_200530_c = Util.func_199748_a(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field ☃ = JsonReader.class.getDeclaredField("lineStart");
            ☃.setAccessible(true);
            return ☃;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", var1);
         }
      });

      public ITextComponent deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (☃.isJsonPrimitive()) {
            return new TextComponentString(☃.getAsString());
         } else if (!☃.isJsonObject()) {
            if (☃.isJsonArray()) {
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
         } else {
            JsonObject ☃x = ☃.getAsJsonObject();
            ITextComponent ☃;
            if (☃x.has("text")) {
               ☃ = new TextComponentString(☃x.get("text").getAsString());
            } else if (☃x.has("translate")) {
               String ☃ = ☃x.get("translate").getAsString();
               if (☃x.has("with")) {
                  JsonArray ☃x = ☃x.getAsJsonArray("with");
                  Object[] ☃xx = new Object[☃x.size()];

                  for(int ☃xxx = 0; ☃xxx < ☃xx.length; ++☃xxx) {
                     ☃xx[☃xxx] = this.deserialize(☃x.get(☃xxx), ☃, ☃);
                     if (☃xx[☃xxx] instanceof TextComponentString) {
                        TextComponentString ☃xxxx = (TextComponentString)☃xx[☃xxx];
                        if (☃xxxx.func_150256_b().func_150229_g() && ☃xxxx.func_150253_a().isEmpty()) {
                           ☃xx[☃xxx] = ☃xxxx.func_150265_g();
                        }
                     }
                  }

                  ☃ = new TextComponentTranslation(☃, ☃xx);
               } else {
                  ☃ = new TextComponentTranslation(☃);
               }
            } else if (☃x.has("score")) {
               JsonObject ☃ = ☃x.getAsJsonObject("score");
               if (!☃.has("name") || !☃.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               ☃ = new TextComponentScore(JsonUtils.func_151200_h(☃, "name"), JsonUtils.func_151200_h(☃, "objective"));
               if (☃.has("value")) {
                  ((TextComponentScore)☃).func_179997_b(JsonUtils.func_151200_h(☃, "value"));
               }
            } else if (☃x.has("selector")) {
               ☃ = new TextComponentSelector(JsonUtils.func_151200_h(☃x, "selector"));
            } else {
               if (!☃x.has("keybind")) {
                  throw new JsonParseException("Don't know how to turn " + ☃ + " into a Component");
               }

               ☃ = new TextComponentKeybind(JsonUtils.func_151200_h(☃x, "keybind"));
            }

            if (☃x.has("extra")) {
               JsonArray ☃ = ☃x.getAsJsonArray("extra");
               if (☃.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
                  ☃.func_150257_a(this.deserialize(☃.get(☃), ☃, ☃));
               }
            }

            ☃.func_150255_a(☃.deserialize(☃, Style.class));
            return ☃;
         }
      }

      private void func_150695_a(Style var1, JsonObject var2, JsonSerializationContext var3) {
         JsonElement ☃ = ☃.serialize(☃);
         if (☃.isJsonObject()) {
            JsonObject ☃x = (JsonObject)☃;

            for(Entry<String, JsonElement> ☃xx : ☃x.entrySet()) {
               ☃.add((String)☃xx.getKey(), (JsonElement)☃xx.getValue());
            }
         }
      }

      public JsonElement serialize(ITextComponent var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         if (!☃.func_150256_b().func_150229_g()) {
            this.func_150695_a(☃.func_150256_b(), ☃, ☃);
         }

         if (!☃.func_150253_a().isEmpty()) {
            JsonArray ☃ = new JsonArray();

            for(ITextComponent ☃x : ☃.func_150253_a()) {
               ☃.add(this.serialize(☃x, ☃x.getClass(), ☃));
            }

            ☃.add("extra", ☃);
         }

         if (☃ instanceof TextComponentString) {
            ☃.addProperty("text", ((TextComponentString)☃).func_150265_g());
         } else if (☃ instanceof TextComponentTranslation) {
            TextComponentTranslation ☃ = (TextComponentTranslation)☃;
            ☃.addProperty("translate", ☃.func_150268_i());
            if (☃.func_150271_j() != null && ☃.func_150271_j().length > 0) {
               JsonArray ☃x = new JsonArray();

               for(Object ☃xx : ☃.func_150271_j()) {
                  if (☃xx instanceof ITextComponent) {
                     ☃x.add(this.serialize((ITextComponent)☃xx, ☃xx.getClass(), ☃));
                  } else {
                     ☃x.add(new JsonPrimitive(String.valueOf(☃xx)));
                  }
               }

               ☃.add("with", ☃x);
            }
         } else if (☃ instanceof TextComponentScore) {
            TextComponentScore ☃ = (TextComponentScore)☃;
            JsonObject ☃x = new JsonObject();
            ☃x.addProperty("name", ☃.func_179995_g());
            ☃x.addProperty("objective", ☃.func_179994_h());
            ☃x.addProperty("value", ☃.func_150261_e());
            ☃.add("score", ☃x);
         } else if (☃ instanceof TextComponentSelector) {
            TextComponentSelector ☃ = (TextComponentSelector)☃;
            ☃.addProperty("selector", ☃.func_179992_g());
         } else {
            if (!(☃ instanceof TextComponentKeybind)) {
               throw new IllegalArgumentException("Don't know how to serialize " + ☃ + " as a Component");
            }

            TextComponentKeybind ☃ = (TextComponentKeybind)☃;
            ☃.addProperty("keybind", ☃.func_193633_h());
         }

         return ☃;
      }

      public static String func_150696_a(ITextComponent var0) {
         return field_150700_a.toJson(☃);
      }

      public static JsonElement func_200528_b(ITextComponent var0) {
         return field_150700_a.toJsonTree(☃);
      }

      @Nullable
      public static ITextComponent func_150699_a(String var0) {
         return JsonUtils.func_188176_a(field_150700_a, ☃, ITextComponent.class, false);
      }

      @Nullable
      public static ITextComponent func_197672_a(JsonElement var0) {
         return field_150700_a.fromJson(☃, ITextComponent.class);
      }

      @Nullable
      public static ITextComponent func_186877_b(String var0) {
         return JsonUtils.func_188176_a(field_150700_a, ☃, ITextComponent.class, true);
      }

      public static ITextComponent func_197671_a(com.mojang.brigadier.StringReader var0) {
         try {
            JsonReader ☃ = new JsonReader(new StringReader(☃.getRemaining()));
            ☃.setLenient(false);
            ITextComponent ☃x = field_150700_a.<ITextComponent>getAdapter(ITextComponent.class).read(☃);
            ☃.setCursor(☃.getCursor() + func_197673_a(☃));
            return ☃x;
         } catch (IOException var3) {
            throw new JsonParseException(var3);
         }
      }

      private static int func_197673_a(JsonReader var0) {
         try {
            return field_197674_b.getInt(☃) - field_200530_c.getInt(☃) + 1;
         } catch (IllegalAccessException var2) {
            throw new IllegalStateException("Couldn't read position of JsonReader", var2);
         }
      }
   }
}
