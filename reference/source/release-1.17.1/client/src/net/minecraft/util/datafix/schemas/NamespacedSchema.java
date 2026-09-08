package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const.PrimitiveType;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.resources.ResourceLocation;

public class NamespacedSchema extends Schema {
   public static final PrimitiveCodec<String> NAMESPACED_STRING_CODEC = new PrimitiveCodec<String>() {
      @Override
      public <T> DataResult<String> read(DynamicOps<T> var1, T var2) {
         return â˜ƒ.getStringValue(â˜ƒ).map(NamespacedSchema::ensureNamespaced);
      }

      public <T> T write(DynamicOps<T> var1, String var2) {
         return â˜ƒ.createString(â˜ƒ);
      }

      public String toString() {
         return "NamespacedString";
      }
   };
   private static final Type<String> NAMESPACED_STRING = new PrimitiveType(NAMESPACED_STRING_CODEC);

   public NamespacedSchema(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public static String ensureNamespaced(String var0) {
      ResourceLocation â˜ƒ = ResourceLocation.tryParse(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.toString() : â˜ƒ;
   }

   public static Type<String> namespacedString() {
      return NAMESPACED_STRING;
   }

   @Override
   public Type<?> getChoiceType(TypeReference var1, String var2) {
      return super.getChoiceType(â˜ƒ, ensureNamespaced(â˜ƒ));
   }
}
