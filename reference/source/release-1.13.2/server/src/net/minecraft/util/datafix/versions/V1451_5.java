package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;

public class V1451_5 extends NamespacedSchema {
   public V1451_5(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerBlockEntities(☃);
      ☃.remove("minecraft:flower_pot");
      ☃.remove("minecraft:noteblock");
      return ☃;
   }
}
