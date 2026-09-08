package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1470 extends NamespacedSchema {
   public V1470(int var1, Schema var2) {
      super(☃, ☃);
   }

   protected static void func_206563_a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> V0100.func_206605_a(☃)));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerEntities(☃);
      func_206563_a(☃, ☃, "minecraft:turtle");
      func_206563_a(☃, ☃, "minecraft:cod_mob");
      func_206563_a(☃, ☃, "minecraft:tropical_fish");
      func_206563_a(☃, ☃, "minecraft:salmon_mob");
      func_206563_a(☃, ☃, "minecraft:puffer_fish");
      func_206563_a(☃, ☃, "minecraft:phantom");
      func_206563_a(☃, ☃, "minecraft:dolphin");
      func_206563_a(☃, ☃, "minecraft:drowned");
      ☃.register(☃, "minecraft:trident", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inBlockState", TypeReferences.field_211296_l.in(☃))));
      return ☃;
   }
}
