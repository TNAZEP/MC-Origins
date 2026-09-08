package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1470 extends NamespacedSchema {
   public V1470(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static void registerMob(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> V100.equipment(â˜ƒ)));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
      registerMob(â˜ƒ, â˜ƒ, "minecraft:turtle");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:cod_mob");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:tropical_fish");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:salmon_mob");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:puffer_fish");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:phantom");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:dolphin");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:drowned");
      â˜ƒ.register(â˜ƒ, "minecraft:trident", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in(â˜ƒ))));
      return â˜ƒ;
   }
}
