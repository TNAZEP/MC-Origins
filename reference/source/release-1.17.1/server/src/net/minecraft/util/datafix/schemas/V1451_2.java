package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1451_2 extends NamespacedSchema {
   public V1451_2(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerBlockEntities(â˜ƒ);
      â˜ƒ.register(â˜ƒ, "minecraft:piston", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("blockState", References.BLOCK_STATE.in(â˜ƒ))));
      return â˜ƒ;
   }
}
