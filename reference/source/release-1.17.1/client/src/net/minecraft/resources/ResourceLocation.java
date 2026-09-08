package net.minecraft.resources;

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
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;

public class ResourceLocation implements Comparable<ResourceLocation> {
   public static final Codec<ResourceLocation> CODEC = Codec.STRING.<ResourceLocation>comapFlatMap(ResourceLocation::read, ResourceLocation::toString).stable();
   private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType(new TranslatableComponent("argument.id.invalid"));
   public static final char NAMESPACE_SEPARATOR = ':';
   public static final String DEFAULT_NAMESPACE = "minecraft";
   public static final String REALMS_NAMESPACE = "realms";
   protected final String namespace;
   protected final String path;

   protected ResourceLocation(String[] var1) {
      this.namespace = StringUtils.isEmpty(â˜ƒ[0]) ? "minecraft" : â˜ƒ[0];
      this.path = â˜ƒ[1];
      if (!isValidNamespace(this.namespace)) {
         throw new ResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + this.namespace + ":" + this.path);
      } else if (!isValidPath(this.path)) {
         throw new ResourceLocationException("Non [a-z0-9/._-] character in path of location: " + this.namespace + ":" + this.path);
      }
   }

   public ResourceLocation(String var1) {
      this(decompose(â˜ƒ, ':'));
   }

   public ResourceLocation(String var1, String var2) {
      this(new String[]{â˜ƒ, â˜ƒ});
   }

   public static ResourceLocation of(String var0, char var1) {
      return new ResourceLocation(decompose(â˜ƒ, â˜ƒ));
   }

   @Nullable
   public static ResourceLocation tryParse(String var0) {
      try {
         return new ResourceLocation(â˜ƒ);
      } catch (ResourceLocationException var2) {
         return null;
      }
   }

   protected static String[] decompose(String var0, char var1) {
      String[] â˜ƒ = new String[]{"minecraft", â˜ƒ};
      int â˜ƒx = â˜ƒ.indexOf(â˜ƒ);
      if (â˜ƒx >= 0) {
         â˜ƒ[1] = â˜ƒ.substring(â˜ƒx + 1, â˜ƒ.length());
         if (â˜ƒx >= 1) {
            â˜ƒ[0] = â˜ƒ.substring(0, â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   private static DataResult<ResourceLocation> read(String var0) {
      try {
         return DataResult.success(new ResourceLocation(â˜ƒ));
      } catch (ResourceLocationException var2) {
         return DataResult.error("Not a valid resource location: " + â˜ƒ + " " + var2.getMessage());
      }
   }

   public String getPath() {
      return this.path;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String toString() {
      return this.namespace + ":" + this.path;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof ResourceLocation)) {
         return false;
      } else {
         ResourceLocation â˜ƒ = (ResourceLocation)â˜ƒ;
         return this.namespace.equals(â˜ƒ.namespace) && this.path.equals(â˜ƒ.path);
      }
   }

   public int hashCode() {
      return 31 * this.namespace.hashCode() + this.path.hashCode();
   }

   public int compareTo(ResourceLocation var1) {
      int â˜ƒ = this.path.compareTo(â˜ƒ.path);
      if (â˜ƒ == 0) {
         â˜ƒ = this.namespace.compareTo(â˜ƒ.namespace);
      }

      return â˜ƒ;
   }

   public String toDebugFileName() {
      return this.toString().replace('/', '_').replace(':', '_');
   }

   public static ResourceLocation read(StringReader var0) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();

      while(â˜ƒ.canRead() && isAllowedInResourceLocation(â˜ƒ.peek())) {
         â˜ƒ.skip();
      }

      String â˜ƒx = â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor());

      try {
         return new ResourceLocation(â˜ƒx);
      } catch (ResourceLocationException var4) {
         â˜ƒ.setCursor(â˜ƒ);
         throw ERROR_INVALID.createWithContext(â˜ƒ);
      }
   }

   public static boolean isAllowedInResourceLocation(char var0) {
      return â˜ƒ >= '0' && â˜ƒ <= '9' || â˜ƒ >= 'a' && â˜ƒ <= 'z' || â˜ƒ == '_' || â˜ƒ == ':' || â˜ƒ == '/' || â˜ƒ == '.' || â˜ƒ == '-';
   }

   private static boolean isValidPath(String var0) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length(); ++â˜ƒ) {
         if (!validPathChar(â˜ƒ.charAt(â˜ƒ))) {
            return false;
         }
      }

      return true;
   }

   private static boolean isValidNamespace(String var0) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length(); ++â˜ƒ) {
         if (!validNamespaceChar(â˜ƒ.charAt(â˜ƒ))) {
            return false;
         }
      }

      return true;
   }

   public static boolean validPathChar(char var0) {
      return â˜ƒ == '_' || â˜ƒ == '-' || â˜ƒ >= 'a' && â˜ƒ <= 'z' || â˜ƒ >= '0' && â˜ƒ <= '9' || â˜ƒ == '/' || â˜ƒ == '.';
   }

   private static boolean validNamespaceChar(char var0) {
      return â˜ƒ == '_' || â˜ƒ == '-' || â˜ƒ >= 'a' && â˜ƒ <= 'z' || â˜ƒ >= '0' && â˜ƒ <= '9' || â˜ƒ == '.';
   }

   public static boolean isValidResourceLocation(String var0) {
      String[] â˜ƒ = decompose(â˜ƒ, ':');
      return isValidNamespace(StringUtils.isEmpty(â˜ƒ[0]) ? "minecraft" : â˜ƒ[0]) && isValidPath(â˜ƒ[1]);
   }

   public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation> {
      public ResourceLocation deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return new ResourceLocation(GsonHelper.convertToString(â˜ƒ, "location"));
      }

      public JsonElement serialize(ResourceLocation var1, Type var2, JsonSerializationContext var3) {
         return new JsonPrimitive(â˜ƒ.toString());
      }
   }
}
