package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V1510 extends NamespacedSchema {
   public V1510(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
      â˜ƒ.put("minecraft:command_block_minecart", (Supplier)â˜ƒ.remove("minecraft:commandblock_minecart"));
      â˜ƒ.put("minecraft:end_crystal", (Supplier)â˜ƒ.remove("minecraft:ender_crystal"));
      â˜ƒ.put("minecraft:snow_golem", (Supplier)â˜ƒ.remove("minecraft:snowman"));
      â˜ƒ.put("minecraft:evoker", (Supplier)â˜ƒ.remove("minecraft:evocation_illager"));
      â˜ƒ.put("minecraft:evoker_fangs", (Supplier)â˜ƒ.remove("minecraft:evocation_fangs"));
      â˜ƒ.put("minecraft:illusioner", (Supplier)â˜ƒ.remove("minecraft:illusion_illager"));
      â˜ƒ.put("minecraft:vindicator", (Supplier)â˜ƒ.remove("minecraft:vindication_illager"));
      â˜ƒ.put("minecraft:iron_golem", (Supplier)â˜ƒ.remove("minecraft:villager_golem"));
      â˜ƒ.put("minecraft:experience_orb", (Supplier)â˜ƒ.remove("minecraft:xp_orb"));
      â˜ƒ.put("minecraft:experience_bottle", (Supplier)â˜ƒ.remove("minecraft:xp_bottle"));
      â˜ƒ.put("minecraft:eye_of_ender", (Supplier)â˜ƒ.remove("minecraft:eye_of_ender_signal"));
      â˜ƒ.put("minecraft:firework_rocket", (Supplier)â˜ƒ.remove("minecraft:fireworks_rocket"));
      return â˜ƒ;
   }
}
