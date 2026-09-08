package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1451_6 extends NamespacedSchema {
   public V1451_6(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
      Supplier<TypeTemplate> ☃ = () -> DSL.compoundList(TypeReferences.field_211301_q.in(☃), DSL.constType(DSL.intType()));
      ☃.registerType(
         false,
         TypeReferences.field_211291_g,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(TypeReferences.field_211300_p.in(☃), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  (TypeTemplate)☃.get(),
                  "minecraft:used",
                  (TypeTemplate)☃.get(),
                  "minecraft:broken",
                  (TypeTemplate)☃.get(),
                  "minecraft:picked_up",
                  (TypeTemplate)☃.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     (TypeTemplate)☃.get(),
                     "minecraft:killed",
                     DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(DSL.namespacedString()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
   }
}
