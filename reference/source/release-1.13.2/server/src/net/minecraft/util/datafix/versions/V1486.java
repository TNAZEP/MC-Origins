package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;

public class V1486 extends NamespacedSchema {
   public V1486(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerEntities(☃);
      ☃.put("minecraft:cod", ☃.remove("minecraft:cod_mob"));
      ☃.put("minecraft:salmon", ☃.remove("minecraft:salmon_mob"));
      return ☃;
   }
}
