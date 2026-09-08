package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V1483 extends NamespacedSchema {
   public V1483(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
      â˜ƒ.put("minecraft:pufferfish", (Supplier)â˜ƒ.remove("minecraft:puffer_fish"));
      return â˜ƒ;
   }
}
