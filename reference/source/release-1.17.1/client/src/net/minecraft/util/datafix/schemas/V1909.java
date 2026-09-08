package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V1909 extends NamespacedSchema {
   public V1909(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerBlockEntities(â˜ƒ);
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:jigsaw");
      return â˜ƒ;
   }
}
