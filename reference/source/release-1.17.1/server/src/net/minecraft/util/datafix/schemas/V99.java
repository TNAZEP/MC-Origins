package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class V99 extends Schema {
   private static final Logger LOGGER = LogManager.getLogger();
   static final Map<String, String> ITEM_TO_BLOCKENTITY = DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:furnace", "Furnace");
      var0.put("minecraft:lit_furnace", "Furnace");
      var0.put("minecraft:chest", "Chest");
      var0.put("minecraft:trapped_chest", "Chest");
      var0.put("minecraft:ender_chest", "EnderChest");
      var0.put("minecraft:jukebox", "RecordPlayer");
      var0.put("minecraft:dispenser", "Trap");
      var0.put("minecraft:dropper", "Dropper");
      var0.put("minecraft:sign", "Sign");
      var0.put("minecraft:mob_spawner", "MobSpawner");
      var0.put("minecraft:noteblock", "Music");
      var0.put("minecraft:brewing_stand", "Cauldron");
      var0.put("minecraft:enhanting_table", "EnchantTable");
      var0.put("minecraft:command_block", "CommandBlock");
      var0.put("minecraft:beacon", "Beacon");
      var0.put("minecraft:skull", "Skull");
      var0.put("minecraft:daylight_detector", "DLDetector");
      var0.put("minecraft:hopper", "Hopper");
      var0.put("minecraft:banner", "Banner");
      var0.put("minecraft:flower_pot", "FlowerPot");
      var0.put("minecraft:repeating_command_block", "CommandBlock");
      var0.put("minecraft:chain_command_block", "CommandBlock");
      var0.put("minecraft:standing_sign", "Sign");
      var0.put("minecraft:wall_sign", "Sign");
      var0.put("minecraft:piston_head", "Piston");
      var0.put("minecraft:daylight_detector_inverted", "DLDetector");
      var0.put("minecraft:unpowered_comparator", "Comparator");
      var0.put("minecraft:powered_comparator", "Comparator");
      var0.put("minecraft:wall_banner", "Banner");
      var0.put("minecraft:standing_banner", "Banner");
      var0.put("minecraft:structure_block", "Structure");
      var0.put("minecraft:end_portal", "Airportal");
      var0.put("minecraft:end_gateway", "EndGateway");
      var0.put("minecraft:shield", "Banner");
   });
   protected static final HookFunction ADD_NAMES = new HookFunction() {
      @Override
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return V99.addNames(new Dynamic<>(â˜ƒ, â˜ƒ), V99.ITEM_TO_BLOCKENTITY, "ArmorStand");
      }
   };

   public V99(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static TypeTemplate equipment(Schema var0) {
      return DSL.optionalFields("Equipment", DSL.list(References.ITEM_STACK.in(â˜ƒ)));
   }

   protected static void registerMob(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> equipment(â˜ƒ)));
   }

   protected static void registerThrowableProjectile(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ))));
   }

   protected static void registerMinecart(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ))));
   }

   protected static void registerInventory(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)))));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = Maps.newHashMap();
      â˜ƒ.register(â˜ƒ, "Item", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.registerSimple(â˜ƒ, "XPOrb");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "ThrownEgg");
      â˜ƒ.registerSimple(â˜ƒ, "LeashKnot");
      â˜ƒ.registerSimple(â˜ƒ, "Painting");
      â˜ƒ.register(â˜ƒ, "Arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ))));
      â˜ƒ.register(â˜ƒ, "TippedArrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ))));
      â˜ƒ.register(â˜ƒ, "SpectralArrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ))));
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "Snowball");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "Fireball");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "SmallFireball");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "ThrownEnderpearl");
      â˜ƒ.registerSimple(â˜ƒ, "EyeOfEnderSignal");
      â˜ƒ.register(
         â˜ƒ,
         "ThrownPotion",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ), "Potion", References.ITEM_STACK.in(â˜ƒ)))
      );
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "ThrownExpBottle");
      â˜ƒ.register(â˜ƒ, "ItemFrame", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", References.ITEM_STACK.in(â˜ƒ))));
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "WitherSkull");
      â˜ƒ.registerSimple(â˜ƒ, "PrimedTnt");
      â˜ƒ.register(
         â˜ƒ,
         "FallingSand",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Block", References.BLOCK_NAME.in(â˜ƒ), "TileEntityData", References.BLOCK_ENTITY.in(â˜ƒ)))
      );
      â˜ƒ.register(â˜ƒ, "FireworksRocketEntity", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("FireworksItem", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.registerSimple(â˜ƒ, "Boat");
      â˜ƒ.register(
         â˜ƒ,
         "Minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))))
      );
      registerMinecart(â˜ƒ, â˜ƒ, "MinecartRideable");
      â˜ƒ.register(
         â˜ƒ,
         "MinecartChest",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", References.BLOCK_NAME.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))
            ))
      );
      registerMinecart(â˜ƒ, â˜ƒ, "MinecartFurnace");
      registerMinecart(â˜ƒ, â˜ƒ, "MinecartTNT");
      â˜ƒ.register(
         â˜ƒ,
         "MinecartSpawner",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ), References.UNTAGGED_SPAWNER.in(â˜ƒ)))
      );
      â˜ƒ.register(
         â˜ƒ,
         "MinecartHopper",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", References.BLOCK_NAME.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))
            ))
      );
      registerMinecart(â˜ƒ, â˜ƒ, "MinecartCommandBlock");
      registerMob(â˜ƒ, â˜ƒ, "ArmorStand");
      registerMob(â˜ƒ, â˜ƒ, "Creeper");
      registerMob(â˜ƒ, â˜ƒ, "Skeleton");
      registerMob(â˜ƒ, â˜ƒ, "Spider");
      registerMob(â˜ƒ, â˜ƒ, "Giant");
      registerMob(â˜ƒ, â˜ƒ, "Zombie");
      registerMob(â˜ƒ, â˜ƒ, "Slime");
      registerMob(â˜ƒ, â˜ƒ, "Ghast");
      registerMob(â˜ƒ, â˜ƒ, "PigZombie");
      â˜ƒ.register(â˜ƒ, "Enderman", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("carried", References.BLOCK_NAME.in(â˜ƒ), equipment(â˜ƒ))));
      registerMob(â˜ƒ, â˜ƒ, "CaveSpider");
      registerMob(â˜ƒ, â˜ƒ, "Silverfish");
      registerMob(â˜ƒ, â˜ƒ, "Blaze");
      registerMob(â˜ƒ, â˜ƒ, "LavaSlime");
      registerMob(â˜ƒ, â˜ƒ, "EnderDragon");
      registerMob(â˜ƒ, â˜ƒ, "WitherBoss");
      registerMob(â˜ƒ, â˜ƒ, "Bat");
      registerMob(â˜ƒ, â˜ƒ, "Witch");
      registerMob(â˜ƒ, â˜ƒ, "Endermite");
      registerMob(â˜ƒ, â˜ƒ, "Guardian");
      registerMob(â˜ƒ, â˜ƒ, "Pig");
      registerMob(â˜ƒ, â˜ƒ, "Sheep");
      registerMob(â˜ƒ, â˜ƒ, "Cow");
      registerMob(â˜ƒ, â˜ƒ, "Chicken");
      registerMob(â˜ƒ, â˜ƒ, "Squid");
      registerMob(â˜ƒ, â˜ƒ, "Wolf");
      registerMob(â˜ƒ, â˜ƒ, "MushroomCow");
      registerMob(â˜ƒ, â˜ƒ, "SnowMan");
      registerMob(â˜ƒ, â˜ƒ, "Ozelot");
      registerMob(â˜ƒ, â˜ƒ, "VillagerGolem");
      â˜ƒ.register(
         â˜ƒ,
         "EntityHorse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "ArmorItem",
               References.ITEM_STACK.in(â˜ƒ),
               "SaddleItem",
               References.ITEM_STACK.in(â˜ƒ),
               equipment(â˜ƒ)
            ))
      );
      registerMob(â˜ƒ, â˜ƒ, "Rabbit");
      â˜ƒ.register(
         â˜ƒ,
         "Villager",
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
               equipment(â˜ƒ)
            ))
      );
      â˜ƒ.registerSimple(â˜ƒ, "EnderCrystal");
      â˜ƒ.registerSimple(â˜ƒ, "AreaEffectCloud");
      â˜ƒ.registerSimple(â˜ƒ, "ShulkerBullet");
      registerMob(â˜ƒ, â˜ƒ, "Shulker");
      return â˜ƒ;
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = Maps.newHashMap();
      registerInventory(â˜ƒ, â˜ƒ, "Furnace");
      registerInventory(â˜ƒ, â˜ƒ, "Chest");
      â˜ƒ.registerSimple(â˜ƒ, "EnderChest");
      â˜ƒ.register(â˜ƒ, "RecordPlayer", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("RecordItem", References.ITEM_STACK.in(â˜ƒ))));
      registerInventory(â˜ƒ, â˜ƒ, "Trap");
      registerInventory(â˜ƒ, â˜ƒ, "Dropper");
      â˜ƒ.registerSimple(â˜ƒ, "Sign");
      â˜ƒ.register(â˜ƒ, "MobSpawner", (Function<String, TypeTemplate>)(var1x -> References.UNTAGGED_SPAWNER.in(â˜ƒ)));
      â˜ƒ.registerSimple(â˜ƒ, "Music");
      â˜ƒ.registerSimple(â˜ƒ, "Piston");
      registerInventory(â˜ƒ, â˜ƒ, "Cauldron");
      â˜ƒ.registerSimple(â˜ƒ, "EnchantTable");
      â˜ƒ.registerSimple(â˜ƒ, "Airportal");
      â˜ƒ.registerSimple(â˜ƒ, "Control");
      â˜ƒ.registerSimple(â˜ƒ, "Beacon");
      â˜ƒ.registerSimple(â˜ƒ, "Skull");
      â˜ƒ.registerSimple(â˜ƒ, "DLDetector");
      registerInventory(â˜ƒ, â˜ƒ, "Hopper");
      â˜ƒ.registerSimple(â˜ƒ, "Comparator");
      â˜ƒ.register(
         â˜ƒ,
         "FlowerPot",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), References.ITEM_NAME.in(â˜ƒ))))
      );
      â˜ƒ.registerSimple(â˜ƒ, "Banner");
      â˜ƒ.registerSimple(â˜ƒ, "Structure");
      â˜ƒ.registerSimple(â˜ƒ, "EndGateway");
      return â˜ƒ;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      â˜ƒ.registerType(false, References.LEVEL, DSL::remainder);
      â˜ƒ.registerType(
         false,
         References.PLAYER,
         () -> DSL.optionalFields("Inventory", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "EnderItems", DSL.list(References.ITEM_STACK.in(â˜ƒ)))
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
                  DSL.list(DSL.fields("i", References.BLOCK_NAME.in(â˜ƒ)))
               )
            )
      );
      â˜ƒ.registerType(true, References.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", DSL.string(), â˜ƒ));
      â˜ƒ.registerType(true, References.ENTITY_TREE, () -> DSL.optionalFields("Riding", References.ENTITY_TREE.in(â˜ƒ), References.ENTITY.in(â˜ƒ)));
      â˜ƒ.registerType(false, References.ENTITY_NAME, () -> DSL.constType(NamespacedSchema.namespacedString()));
      â˜ƒ.registerType(true, References.ENTITY, () -> DSL.taggedChoiceLazy("id", DSL.string(), â˜ƒ));
      â˜ƒ.registerType(
         true,
         References.ITEM_STACK,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  DSL.or(DSL.constType(DSL.intType()), References.ITEM_NAME.in(â˜ƒ)),
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
      â˜ƒ.registerType(false, References.OPTIONS, DSL::remainder);
      â˜ƒ.registerType(false, References.BLOCK_NAME, () -> DSL.or(DSL.constType(DSL.intType()), DSL.constType(NamespacedSchema.namespacedString())));
      â˜ƒ.registerType(false, References.ITEM_NAME, () -> DSL.constType(NamespacedSchema.namespacedString()));
      â˜ƒ.registerType(false, References.STATS, DSL::remainder);
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
      â˜ƒ.registerType(false, References.STRUCTURE_FEATURE, DSL::remainder);
      â˜ƒ.registerType(false, References.OBJECTIVE, DSL::remainder);
      â˜ƒ.registerType(false, References.TEAM, DSL::remainder);
      â˜ƒ.registerType(true, References.UNTAGGED_SPAWNER, DSL::remainder);
      â˜ƒ.registerType(false, References.POI_CHUNK, DSL::remainder);
      â˜ƒ.registerType(true, References.WORLD_GEN_SETTINGS, DSL::remainder);
      â˜ƒ.registerType(false, References.ENTITY_CHUNK, () -> DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in(â˜ƒ))));
   }

   protected static <T> T addNames(Dynamic<T> var0, Map<String, String> var1, String var2) {
      return â˜ƒ.update("tag", var3 -> var3.update("BlockEntityTag", var2x -> {
            String â˜ƒ = (String)â˜ƒ.get("id").asString().result().map(NamespacedSchema::ensureNamespaced).orElse("minecraft:air");
            if (!"minecraft:air".equals(â˜ƒ)) {
               String â˜ƒx = (String)â˜ƒ.get(â˜ƒ);
               if (â˜ƒx != null) {
                  return var2x.set("id", â˜ƒ.createString(â˜ƒx));
               }

               LOGGER.warn("Unable to resolve BlockEntity for ItemStack: {}", â˜ƒ);
            }

            return var2x;
         }).update("EntityTag", var2x -> {
            String â˜ƒ = â˜ƒ.get("id").asString("");
            return "minecraft:armor_stand".equals(NamespacedSchema.ensureNamespaced(â˜ƒ)) ? var2x.set("id", â˜ƒ.createString(â˜ƒ)) : var2x;
         })).getValue();
   }
}
