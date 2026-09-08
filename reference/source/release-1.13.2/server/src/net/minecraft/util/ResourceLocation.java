package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextComponentTranslation;

public class ResourceLocation implements Comparable<ResourceLocation> {
   private static final SimpleCommandExceptionType field_200118_c = new SimpleCommandExceptionType(new TextComponentTranslation("argument.id.invalid"));
   protected final String field_110626_a;
   protected final String field_110625_b;

   protected ResourceLocation(String[] var1) {
      this.field_110626_a = org.apache.commons.lang3.StringUtils.isEmpty(☃[0]) ? "minecraft" : ☃[0];
      this.field_110625_b = ☃[1];
      if (!this.field_110626_a.chars().allMatch(var0 -> var0 == 95 || var0 == 45 || var0 >= 97 && var0 <= 122 || var0 >= 48 && var0 <= 57 || var0 == 46)) {
         throw new ResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + this.field_110626_a + ':' + this.field_110625_b);
      } else if (!this.field_110625_b
         .chars()
         .allMatch(var0 -> var0 == 95 || var0 == 45 || var0 >= 97 && var0 <= 122 || var0 >= 48 && var0 <= 57 || var0 == 47 || var0 == 46)) {
         throw new ResourceLocationException("Non [a-z0-9/._-] character in path of location: " + this.field_110626_a + ':' + this.field_110625_b);
      }
   }

   public ResourceLocation(String var1) {
      this(func_195823_b(☃, ':'));
   }

   public ResourceLocation(String var1, String var2) {
      this(new String[]{☃, ☃});
   }

   public static ResourceLocation func_195828_a(String var0, char var1) {
      return new ResourceLocation(func_195823_b(☃, ☃));
   }

   @Nullable
   public static ResourceLocation func_208304_a(String var0) {
      try {
         return new ResourceLocation(☃);
      } catch (ResourceLocationException var2) {
         return null;
      }
   }

   protected static String[] func_195823_b(String var0, char var1) {
      String[] ☃ = new String[]{"minecraft", ☃};
      int ☃x = ☃.indexOf(☃);
      if (☃x >= 0) {
         ☃[1] = ☃.substring(☃x + 1, ☃.length());
         if (☃x >= 1) {
            ☃[0] = ☃.substring(0, ☃x);
         }
      }

      return ☃;
   }

   public String func_110623_a() {
      return this.field_110625_b;
   }

   public String func_110624_b() {
      return this.field_110626_a;
   }

   public String toString() {
      return this.field_110626_a + ':' + this.field_110625_b;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof ResourceLocation)) {
         return false;
      } else {
         ResourceLocation ☃ = (ResourceLocation)☃;
         return this.field_110626_a.equals(☃.field_110626_a) && this.field_110625_b.equals(☃.field_110625_b);
      }
   }

   public int hashCode() {
      return 31 * this.field_110626_a.hashCode() + this.field_110625_b.hashCode();
   }

   public int compareTo(ResourceLocation var1) {
      int ☃ = this.field_110625_b.compareTo(☃.field_110625_b);
      if (☃ == 0) {
         ☃ = this.field_110626_a.compareTo(☃.field_110626_a);
      }

      return ☃;
   }

   public static ResourceLocation func_195826_a(StringReader var0) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();

      while(☃.canRead() && func_195824_a(☃.peek())) {
         ☃.skip();
      }

      String ☃x = ☃.getString().substring(☃, ☃.getCursor());

      try {
         return new ResourceLocation(☃x);
      } catch (ResourceLocationException var4) {
         ☃.setCursor(☃);
         throw field_200118_c.createWithContext(☃);
      }
   }

   public static boolean func_195824_a(char var0) {
      return ☃ >= '0' && ☃ <= '9' || ☃ >= 'a' && ☃ <= 'z' || ☃ == '_' || ☃ == ':' || ☃ == '/' || ☃ == '.' || ☃ == '-';
   }

   public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation> {
      public ResourceLocation deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return new ResourceLocation(JsonUtils.func_151206_a(☃, "location"));
      }

      public JsonElement serialize(ResourceLocation var1, Type var2, JsonSerializationContext var3) {
         return new JsonPrimitive(☃.toString());
      }
   }
}
