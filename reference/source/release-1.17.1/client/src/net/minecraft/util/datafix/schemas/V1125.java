package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1125 extends NamespacedSchema {
   public V1125(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerBlockEntities(â˜ƒ);
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:bed");
      return â˜ƒ;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerType(
         false,
         References.ADVANCEMENTS,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(References.BIOME.in(â˜ƒ), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(References.ENTITY_NAME.in(â˜ƒ), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(References.ENTITY_NAME.in(â˜ƒ), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(References.ENTITY_NAME.in(â˜ƒ), DSL.constType(DSL.string())))
            )
      );
      â˜ƒ.registerType(false, References.BIOME, () -> DSL.constType(namespacedString()));
      â˜ƒ.registerType(false, References.ENTITY_NAME, () -> DSL.constType(namespacedString()));
   }
}
