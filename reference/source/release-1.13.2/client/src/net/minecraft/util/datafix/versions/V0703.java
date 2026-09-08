package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.TypeReferences;

public class V0703 extends Schema {
   public V0703(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerEntities(☃);
      ☃.remove("EntityHorse");
      ☃.register(
         ☃,
         "Horse",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "ArmorItem", TypeReferences.field_211295_k.in(☃), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      ☃.register(
         ☃,
         "Donkey",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "Items", DSL.list(TypeReferences.field_211295_k.in(☃)), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      ☃.register(
         ☃,
         "Mule",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "Items", DSL.list(TypeReferences.field_211295_k.in(☃)), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      ☃.register(
         ☃, "ZombieHorse", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)))
      );
      ☃.register(
         ☃, "SkeletonHorse", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)))
      );
      return ☃;
   }
}
