package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1451_3 extends NamespacedSchema {
   public V1451_3(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:egg");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:ender_pearl");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:fireball");
      â˜ƒ.register(â˜ƒ, "minecraft:potion", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Potion", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:small_fireball");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:snowball");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:wither_skull");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:xp_bottle");
      â˜ƒ.register(â˜ƒ, "minecraft:arrow", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in(â˜ƒ))));
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:enderman",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("carriedBlockState", References.BLOCK_STATE.in(â˜ƒ), V100.equipment(â˜ƒ)))
      );
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:falling_block",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("BlockState", References.BLOCK_STATE.in(â˜ƒ), "TileEntityData", References.BLOCK_ENTITY.in(â˜ƒ)))
      );
      â˜ƒ.register(â˜ƒ, "minecraft:spectral_arrow", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in(â˜ƒ))));
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:chest_minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))))
      );
      â˜ƒ.register(â˜ƒ, "minecraft:commandblock_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ))));
      â˜ƒ.register(â˜ƒ, "minecraft:furnace_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ))));
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:hopper_minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))))
      );
      â˜ƒ.register(â˜ƒ, "minecraft:minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ))));
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:spawner_minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ), References.UNTAGGED_SPAWNER.in(â˜ƒ)))
      );
      â˜ƒ.register(â˜ƒ, "minecraft:tnt_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ))));
      return â˜ƒ;
   }
}
