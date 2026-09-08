package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1460 extends NamespacedSchema {
   public V1460(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static void registerMob(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> V100.equipment(â˜ƒ)));
   }

   protected static void registerInventory(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)))));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = Maps.newHashMap();
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:area_effect_cloud");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:armor_stand");
      â˜ƒ.register(â˜ƒ, "minecraft:arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in(â˜ƒ))));
      registerMob(â˜ƒ, â˜ƒ, "minecraft:bat");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:blaze");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:boat");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:cave_spider");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:chest_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayState", References.BLOCK_STATE.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))
            ))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:chicken");
      â˜ƒ.register(
         â˜ƒ, "minecraft:commandblock_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:cow");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:creeper");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:donkey",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)
            ))
      );
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:dragon_fireball");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:egg");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:elder_guardian");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:ender_crystal");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:ender_dragon");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:enderman",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("carriedBlockState", References.BLOCK_STATE.in(â˜ƒ), V100.equipment(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:endermite");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:ender_pearl");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:evocation_fangs");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:evocation_illager");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:eye_of_ender_signal");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:falling_block",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "BlockState", References.BLOCK_STATE.in(â˜ƒ), "TileEntityData", References.BLOCK_ENTITY.in(â˜ƒ)
            ))
      );
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:fireball");
      â˜ƒ.register(
         â˜ƒ, "minecraft:fireworks_rocket", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("FireworksItem", References.ITEM_STACK.in(â˜ƒ)))
      );
      â˜ƒ.register(
         â˜ƒ, "minecraft:furnace_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:ghast");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:giant");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:guardian");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:hopper_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayState", References.BLOCK_STATE.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))
            ))
      );
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "ArmorItem", References.ITEM_STACK.in(â˜ƒ), "SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)
            ))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:husk");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:illusion_illager");
      â˜ƒ.register(â˜ƒ, "minecraft:item", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.register(â˜ƒ, "minecraft:item_frame", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:leash_knot");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:llama",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "SaddleItem",
               References.ITEM_STACK.in(â˜ƒ),
               "DecorItem",
               References.ITEM_STACK.in(â˜ƒ),
               V100.equipment(â˜ƒ)
            ))
      );
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:llama_spit");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:magma_cube");
      â˜ƒ.register(â˜ƒ, "minecraft:minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ))));
      registerMob(â˜ƒ, â˜ƒ, "minecraft:mooshroom");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:mule",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)
            ))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:ocelot");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:painting");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:parrot");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:pig");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:polar_bear");
      â˜ƒ.register(â˜ƒ, "minecraft:potion", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Potion", References.ITEM_STACK.in(â˜ƒ))));
      registerMob(â˜ƒ, â˜ƒ, "minecraft:rabbit");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:sheep");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:shulker");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:shulker_bullet");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:silverfish");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:skeleton");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:skeleton_horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:slime");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:small_fireball");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:snowball");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:snowman");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:spawner_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ), References.UNTAGGED_SPAWNER.in(â˜ƒ)))
      );
      â˜ƒ.register(
         â˜ƒ, "minecraft:spectral_arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:spider");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:squid");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:stray");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:tnt");
      â˜ƒ.register(â˜ƒ, "minecraft:tnt_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in(â˜ƒ))));
      registerMob(â˜ƒ, â˜ƒ, "minecraft:vex");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:villager",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "Offers",
               DSL.optionalFields(
                  "Recipes",
                  DSL.list(
                     DSL.optionalFields("buy", References.ITEM_STACK.in(â˜ƒ), "buyB", References.ITEM_STACK.in(â˜ƒ), "sell", References.ITEM_STACK.in(â˜ƒ))
                  )
               ),
               V100.equipment(â˜ƒ)
            ))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:villager_golem");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:vindication_illager");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:witch");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:wither");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:wither_skeleton");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:wither_skull");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:wolf");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:xp_bottle");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:xp_orb");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:zombie");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:zombie_horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:zombie_pigman");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:zombie_villager");
      return â˜ƒ;
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = Maps.newHashMap();
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:furnace");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:chest");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:trapped_chest");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:ender_chest");
      â˜ƒ.register(â˜ƒ, "minecraft:jukebox", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("RecordItem", References.ITEM_STACK.in(â˜ƒ))));
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:dispenser");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:dropper");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:sign");
      â˜ƒ.register(â˜ƒ, "minecraft:mob_spawner", (Function<String, TypeTemplate>)(var1x -> References.UNTAGGED_SPAWNER.in(â˜ƒ)));
      â˜ƒ.register(â˜ƒ, "minecraft:piston", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("blockState", References.BLOCK_STATE.in(â˜ƒ))));
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:brewing_stand");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:enchanting_table");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:end_portal");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:beacon");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:skull");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:daylight_detector");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:hopper");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:comparator");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:banner");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:structure_block");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:end_gateway");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:command_block");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:shulker_box");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:bed");
      return â˜ƒ;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      â˜ƒ.registerType(false, References.LEVEL, DSL::remainder);
      â˜ƒ.registerType(false, References.RECIPE, () -> DSL.constType(namespacedString()));
      â˜ƒ.registerType(
         false,
         References.PLAYER,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", References.ENTITY_TREE.in(â˜ƒ)),
               "Inventory",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "EnderItems",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  References.ENTITY_TREE.in(â˜ƒ),
                  "ShoulderEntityRight",
                  References.ENTITY_TREE.in(â˜ƒ),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(References.RECIPE.in(â˜ƒ)), "toBeDisplayed", DSL.list(References.RECIPE.in(â˜ƒ)))
               )
            )
      );
      â˜ƒ.registerType(
         false,
         References.CHUNK,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(References.ENTITY_TREE.in(â˜ƒ)),
                  "TileEntities",
                  DSL.list(References.BLOCK_ENTITY.in(â˜ƒ)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", References.BLOCK_NAME.in(â˜ƒ))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(References.BLOCK_STATE.in(â˜ƒ))))
               )
            )
      );
      â˜ƒ.registerType(true, References.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", namespacedString(), â˜ƒ));
      â˜ƒ.registerType(
         true, References.ENTITY_TREE, () -> DSL.optionalFields("Passengers", DSL.list(References.ENTITY_TREE.in(â˜ƒ)), References.ENTITY.in(â˜ƒ))
      );
      â˜ƒ.registerType(true, References.ENTITY, () -> DSL.taggedChoiceLazy("id", namespacedString(), â˜ƒ));
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
               V705.ADD_NAMES,
               HookFunction.IDENTITY
            )
      );
      â˜ƒ.registerType(false, References.HOTBAR, () -> DSL.compoundList(DSL.list(References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.registerType(false, References.OPTIONS, DSL::remainder);
      â˜ƒ.registerType(
         false,
         References.STRUCTURE,
         () -> DSL.optionalFields(
               "entities",
               DSL.list(DSL.optionalFields("nbt", References.ENTITY_TREE.in(â˜ƒ))),
               "blocks",
               DSL.list(DSL.optionalFields("nbt", References.BLOCK_ENTITY.in(â˜ƒ))),
               "palette",
               DSL.list(References.BLOCK_STATE.in(â˜ƒ))
            )
      );
      â˜ƒ.registerType(false, References.BLOCK_NAME, () -> DSL.constType(namespacedString()));
      â˜ƒ.registerType(false, References.ITEM_NAME, () -> DSL.constType(namespacedString()));
      â˜ƒ.registerType(false, References.BLOCK_STATE, DSL::remainder);
      Supplier<TypeTemplate> â˜ƒ = () -> DSL.compoundList(References.ITEM_NAME.in(â˜ƒ), DSL.constType(DSL.intType()));
      â˜ƒ.registerType(
         false,
         References.STATS,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(References.BLOCK_NAME.in(â˜ƒ), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  (TypeTemplate)â˜ƒ.get(),
                  "minecraft:used",
                  (TypeTemplate)â˜ƒ.get(),
                  "minecraft:broken",
                  (TypeTemplate)â˜ƒ.get(),
                  "minecraft:picked_up",
                  (TypeTemplate)â˜ƒ.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     (TypeTemplate)â˜ƒ.get(),
                     "minecraft:killed",
                     DSL.compoundList(References.ENTITY_NAME.in(â˜ƒ), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(References.ENTITY_NAME.in(â˜ƒ), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(namespacedString()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
      â˜ƒ.registerType(
         false,
         References.SAVED_DATA,
         () -> DSL.optionalFields(
               "data",
               DSL.optionalFields(
                  "Features",
                  DSL.compoundList(References.STRUCTURE_FEATURE.in(â˜ƒ)),
                  "Objectives",
                  DSL.list(References.OBJECTIVE.in(â˜ƒ)),
                  "Teams",
                  DSL.list(References.TEAM.in(â˜ƒ))
               )
            )
      );
      â˜ƒ.registerType(
         false,
         References.STRUCTURE_FEATURE,
         () -> DSL.optionalFields(
               "Children",
               DSL.list(
                  DSL.optionalFields(
                     "CA",
                     References.BLOCK_STATE.in(â˜ƒ),
                     "CB",
                     References.BLOCK_STATE.in(â˜ƒ),
                     "CC",
                     References.BLOCK_STATE.in(â˜ƒ),
                     "CD",
                     References.BLOCK_STATE.in(â˜ƒ)
                  )
               )
            )
      );
      Map<String, Supplier<TypeTemplate>> â˜ƒx = V1451_6.createCriterionTypes(â˜ƒ);
      â˜ƒ.registerType(
         false,
         References.OBJECTIVE,
         () -> DSL.hook(
               DSL.optionalFields("CriteriaType", DSL.taggedChoiceLazy("type", DSL.string(), â˜ƒ)), V1451_6.UNPACK_OBJECTIVE_ID, V1451_6.REPACK_OBJECTIVE_ID
            )
      );
      â˜ƒ.registerType(false, References.TEAM, DSL::remainder);
      â˜ƒ.registerType(
         true,
         References.UNTAGGED_SPAWNER,
         () -> DSL.optionalFields(
               "SpawnPotentials", DSL.list(DSL.fields("Entity", References.ENTITY_TREE.in(â˜ƒ))), "SpawnData", References.ENTITY_TREE.in(â˜ƒ)
            )
      );
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
      â˜ƒ.registerType(false, References.POI_CHUNK, DSL::remainder);
      â˜ƒ.registerType(true, References.WORLD_GEN_SETTINGS, DSL::remainder);
      â˜ƒ.registerType(false, References.ENTITY_CHUNK, () -> DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in(â˜ƒ))));
   }
}
