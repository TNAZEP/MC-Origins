package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V704 extends Schema {
   protected static final Map<String, String> ITEM_TO_BLOCKENTITY = DataFixUtils.make(() -> {
      Map<String, String> â˜ƒ = Maps.newHashMap();
      â˜ƒ.put("minecraft:furnace", "minecraft:furnace");
      â˜ƒ.put("minecraft:lit_furnace", "minecraft:furnace");
      â˜ƒ.put("minecraft:chest", "minecraft:chest");
      â˜ƒ.put("minecraft:trapped_chest", "minecraft:chest");
      â˜ƒ.put("minecraft:ender_chest", "minecraft:ender_chest");
      â˜ƒ.put("minecraft:jukebox", "minecraft:jukebox");
      â˜ƒ.put("minecraft:dispenser", "minecraft:dispenser");
      â˜ƒ.put("minecraft:dropper", "minecraft:dropper");
      â˜ƒ.put("minecraft:sign", "minecraft:sign");
      â˜ƒ.put("minecraft:mob_spawner", "minecraft:mob_spawner");
      â˜ƒ.put("minecraft:spawner", "minecraft:mob_spawner");
      â˜ƒ.put("minecraft:noteblock", "minecraft:noteblock");
      â˜ƒ.put("minecraft:brewing_stand", "minecraft:brewing_stand");
      â˜ƒ.put("minecraft:enhanting_table", "minecraft:enchanting_table");
      â˜ƒ.put("minecraft:command_block", "minecraft:command_block");
      â˜ƒ.put("minecraft:beacon", "minecraft:beacon");
      â˜ƒ.put("minecraft:skull", "minecraft:skull");
      â˜ƒ.put("minecraft:daylight_detector", "minecraft:daylight_detector");
      â˜ƒ.put("minecraft:hopper", "minecraft:hopper");
      â˜ƒ.put("minecraft:banner", "minecraft:banner");
      â˜ƒ.put("minecraft:flower_pot", "minecraft:flower_pot");
      â˜ƒ.put("minecraft:repeating_command_block", "minecraft:command_block");
      â˜ƒ.put("minecraft:chain_command_block", "minecraft:command_block");
      â˜ƒ.put("minecraft:shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:white_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:green_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:red_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:black_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:bed", "minecraft:bed");
      â˜ƒ.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
      â˜ƒ.put("minecraft:banner", "minecraft:banner");
      â˜ƒ.put("minecraft:white_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:orange_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:magenta_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:light_blue_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:yellow_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:lime_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:pink_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:gray_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:silver_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:light_gray_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:cyan_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:purple_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:blue_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:brown_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:green_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:red_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:black_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:standing_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:wall_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:piston_head", "minecraft:piston");
      â˜ƒ.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
      â˜ƒ.put("minecraft:unpowered_comparator", "minecraft:comparator");
      â˜ƒ.put("minecraft:powered_comparator", "minecraft:comparator");
      â˜ƒ.put("minecraft:wall_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:standing_banner", "minecraft:banner");
      â˜ƒ.put("minecraft:structure_block", "minecraft:structure_block");
      â˜ƒ.put("minecraft:end_portal", "minecraft:end_portal");
      â˜ƒ.put("minecraft:end_gateway", "minecraft:end_gateway");
      â˜ƒ.put("minecraft:sign", "minecraft:sign");
      â˜ƒ.put("minecraft:shield", "minecraft:banner");
      â˜ƒ.put("minecraft:white_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:orange_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:magenta_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:light_blue_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:yellow_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:lime_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:pink_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:gray_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:silver_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:light_gray_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:cyan_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:purple_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:blue_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:brown_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:green_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:red_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:black_bed", "minecraft:bed");
      â˜ƒ.put("minecraft:oak_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:spruce_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:birch_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:jungle_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:acacia_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:dark_oak_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:crimson_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:warped_sign", "minecraft:sign");
      â˜ƒ.put("minecraft:skeleton_skull", "minecraft:skull");
      â˜ƒ.put("minecraft:wither_skeleton_skull", "minecraft:skull");
      â˜ƒ.put("minecraft:zombie_head", "minecraft:skull");
      â˜ƒ.put("minecraft:player_head", "minecraft:skull");
      â˜ƒ.put("minecraft:creeper_head", "minecraft:skull");
      â˜ƒ.put("minecraft:dragon_head", "minecraft:skull");
      â˜ƒ.put("minecraft:barrel", "minecraft:barrel");
      â˜ƒ.put("minecraft:conduit", "minecraft:conduit");
      â˜ƒ.put("minecraft:smoker", "minecraft:smoker");
      â˜ƒ.put("minecraft:blast_furnace", "minecraft:blast_furnace");
      â˜ƒ.put("minecraft:lectern", "minecraft:lectern");
      â˜ƒ.put("minecraft:bell", "minecraft:bell");
      â˜ƒ.put("minecraft:jigsaw", "minecraft:jigsaw");
      â˜ƒ.put("minecraft:campfire", "minecraft:campfire");
      â˜ƒ.put("minecraft:bee_nest", "minecraft:beehive");
      â˜ƒ.put("minecraft:beehive", "minecraft:beehive");
      â˜ƒ.put("minecraft:sculk_sensor", "minecraft:sculk_sensor");
      return ImmutableMap.copyOf(â˜ƒ);
   });
   protected static final HookFunction ADD_NAMES = new HookFunction() {
      @Override
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return V99.addNames(new Dynamic<>(â˜ƒ, â˜ƒ), V704.ITEM_TO_BLOCKENTITY, "ArmorStand");
      }
   };

   public V704(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static void registerInventory(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)))));
   }

   @Override
   public Type<?> getChoiceType(TypeReference var1, String var2) {
      return Objects.equals(â˜ƒ.typeName(), References.BLOCK_ENTITY.typeName())
         ? super.getChoiceType(â˜ƒ, NamespacedSchema.ensureNamespaced(â˜ƒ))
         : super.getChoiceType(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = Maps.newHashMap();
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:furnace");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:chest");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:ender_chest");
      â˜ƒ.register(â˜ƒ, "minecraft:jukebox", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("RecordItem", References.ITEM_STACK.in(â˜ƒ))));
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:dispenser");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:dropper");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:sign");
      â˜ƒ.register(â˜ƒ, "minecraft:mob_spawner", (Function<String, TypeTemplate>)(var1x -> References.UNTAGGED_SPAWNER.in(â˜ƒ)));
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:noteblock");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:piston");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:brewing_stand");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:enchanting_table");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:end_portal");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:beacon");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:skull");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:daylight_detector");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:hopper");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:comparator");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:flower_pot",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), References.ITEM_NAME.in(â˜ƒ))))
      );
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:banner");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:structure_block");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:end_gateway");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:command_block");
      return â˜ƒ;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerType(false, References.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", NamespacedSchema.namespacedString(), â˜ƒ));
      â˜ƒ.registerType(
         true,
         References.ITEM_STACK,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  References.ITEM_NAME.in(â˜ƒ),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     References.ENTITY_TREE.in(â˜ƒ),
                     "BlockEntityTag",
                     References.BLOCK_ENTITY.in(â˜ƒ),
                     "CanDestroy",
                     DSL.list(References.BLOCK_NAME.in(â˜ƒ)),
                     "CanPlaceOn",
                     DSL.list(References.BLOCK_NAME.in(â˜ƒ)),
                     "Items",
                     DSL.list(References.ITEM_STACK.in(â˜ƒ))
                  )
               ),
               ADD_NAMES,
               HookFunction.IDENTITY
            )
      );
   }
}
