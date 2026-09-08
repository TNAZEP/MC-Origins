package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1451_2 extends NamespacedSchema {
   public V1451_2(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerBlockEntities(☃);
      ☃.register(☃, "minecraft:piston", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("blockState", TypeReferences.field_211296_l.in(☃))));
      return ☃;
   }
}
