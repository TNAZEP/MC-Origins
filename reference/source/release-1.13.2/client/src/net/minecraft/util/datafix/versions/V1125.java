package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1125 extends NamespacedSchema {
   public V1125(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerBlockEntities(☃);
      ☃.registerSimple(☃, "minecraft:bed");
      return ☃;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
      ☃.registerType(
         false,
         TypeReferences.field_211293_i,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211305_u.in(☃), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.string())))
            )
      );
      ☃.registerType(false, TypeReferences.field_211305_u, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(false, TypeReferences.field_211297_m, () -> DSL.constType(DSL.namespacedString()));
   }
}
