package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1451_3 extends NamespacedSchema {
   public V1451_3(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerEntities(☃);
      ☃.registerSimple(☃, "minecraft:egg");
      ☃.registerSimple(☃, "minecraft:ender_pearl");
      ☃.registerSimple(☃, "minecraft:fireball");
      ☃.register(☃, "minecraft:potion", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Potion", TypeReferences.field_211295_k.in(☃))));
      ☃.registerSimple(☃, "minecraft:small_fireball");
      ☃.registerSimple(☃, "minecraft:snowball");
      ☃.registerSimple(☃, "minecraft:wither_skull");
      ☃.registerSimple(☃, "minecraft:xp_bottle");
      ☃.register(☃, "minecraft:arrow", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inBlockState", TypeReferences.field_211296_l.in(☃))));
      ☃.register(
         ☃,
         "minecraft:enderman",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("carriedBlockState", TypeReferences.field_211296_l.in(☃), V0100.func_206605_a(☃)))
      );
      ☃.register(
         ☃,
         "minecraft:falling_block",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "BlockState", TypeReferences.field_211296_l.in(☃), "TileEntityData", TypeReferences.field_211294_j.in(☃)
            ))
      );
      ☃.register(☃, "minecraft:spectral_arrow", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inBlockState", TypeReferences.field_211296_l.in(☃))));
      ☃.register(
         ☃,
         "minecraft:chest_minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "DisplayState", TypeReferences.field_211296_l.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      ☃.register(☃, "minecraft:commandblock_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃))));
      ☃.register(☃, "minecraft:furnace_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃))));
      ☃.register(
         ☃,
         "minecraft:hopper_minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "DisplayState", TypeReferences.field_211296_l.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      ☃.register(☃, "minecraft:minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃))));
      ☃.register(
         ☃,
         "minecraft:spawner_minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃), TypeReferences.field_211302_r.in(☃)))
      );
      ☃.register(☃, "minecraft:tnt_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃))));
      return ☃;
   }
}
