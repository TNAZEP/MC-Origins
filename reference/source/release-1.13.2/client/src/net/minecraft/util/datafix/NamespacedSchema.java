package net.minecraft.util.datafix;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.util.ResourceLocation;

public class NamespacedSchema extends Schema {
   public NamespacedSchema(int var1, Schema var2) {
      super(☃, ☃);
   }

   public static String func_206477_f(String var0) {
      ResourceLocation ☃ = ResourceLocation.func_208304_a(☃);
      return ☃ != null ? ☃.toString() : ☃;
   }

   @Override
   public Type<?> getChoiceType(TypeReference var1, String var2) {
      return super.getChoiceType(☃, func_206477_f(☃));
   }
}
