package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class V0099 extends Schema {
   private static final Logger field_206692_c = LogManager.getLogger();
   private static final Map<String, String> field_206693_d = DataFixUtils.make(Maps.newHashMap(), var0 -> {
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
   protected static final HookFunction field_206691_b = new HookFunction() {
      @Override
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return V0099.func_209869_a(new Dynamic<>(☃, ☃), V0099.field_206693_d, "ArmorStand");
      }
   };

   public V0099(int var1, Schema var2) {
      super(☃, ☃);
   }

   protected static TypeTemplate func_206658_a(Schema var0) {
      return DSL.optionalFields("Equipment", DSL.list(TypeReferences.field_211295_k.in(☃)));
   }

   protected static void func_206690_a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> func_206658_a(☃)));
   }

   protected static void func_206668_b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inTile", TypeReferences.field_211300_p.in(☃))));
   }

   protected static void func_206674_c(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", TypeReferences.field_211300_p.in(☃))));
   }

   protected static void func_206680_d(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(TypeReferences.field_211295_k.in(☃)))));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = Maps.newHashMap();
      ☃.register(☃, "Item", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", TypeReferences.field_211295_k.in(☃))));
      ☃.registerSimple(☃, "XPOrb");
      func_206668_b(☃, ☃, "ThrownEgg");
      ☃.registerSimple(☃, "LeashKnot");
      ☃.registerSimple(☃, "Painting");
      ☃.register(☃, "Arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", TypeReferences.field_211300_p.in(☃))));
      ☃.register(☃, "TippedArrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", TypeReferences.field_211300_p.in(☃))));
      ☃.register(☃, "SpectralArrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", TypeReferences.field_211300_p.in(☃))));
      func_206668_b(☃, ☃, "Snowball");
      func_206668_b(☃, ☃, "Fireball");
      func_206668_b(☃, ☃, "SmallFireball");
      func_206668_b(☃, ☃, "ThrownEnderpearl");
      ☃.registerSimple(☃, "EyeOfEnderSignal");
      ☃.register(
         ☃,
         "ThrownPotion",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "inTile", TypeReferences.field_211300_p.in(☃), "Potion", TypeReferences.field_211295_k.in(☃)
            ))
      );
      func_206668_b(☃, ☃, "ThrownExpBottle");
      ☃.register(☃, "ItemFrame", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", TypeReferences.field_211295_k.in(☃))));
      func_206668_b(☃, ☃, "WitherSkull");
      ☃.registerSimple(☃, "PrimedTnt");
      ☃.register(
         ☃,
         "FallingSand",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Block", TypeReferences.field_211300_p.in(☃), "TileEntityData", TypeReferences.field_211294_j.in(☃)
            ))
      );
      ☃.register(
         ☃, "FireworksRocketEntity", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("FireworksItem", TypeReferences.field_211295_k.in(☃)))
      );
      ☃.registerSimple(☃, "Boat");
      ☃.register(
         ☃,
         "Minecart",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "DisplayTile", TypeReferences.field_211300_p.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      func_206674_c(☃, ☃, "MinecartRideable");
      ☃.register(
         ☃,
         "MinecartChest",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", TypeReferences.field_211300_p.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      func_206674_c(☃, ☃, "MinecartFurnace");
      func_206674_c(☃, ☃, "MinecartTNT");
      ☃.register(
         ☃,
         "MinecartSpawner",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", TypeReferences.field_211300_p.in(☃), TypeReferences.field_211302_r.in(☃)))
      );
      ☃.register(
         ☃,
         "MinecartHopper",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", TypeReferences.field_211300_p.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      func_206674_c(☃, ☃, "MinecartCommandBlock");
      func_206690_a(☃, ☃, "ArmorStand");
      func_206690_a(☃, ☃, "Creeper");
      func_206690_a(☃, ☃, "Skeleton");
      func_206690_a(☃, ☃, "Spider");
      func_206690_a(☃, ☃, "Giant");
      func_206690_a(☃, ☃, "Zombie");
      func_206690_a(☃, ☃, "Slime");
      func_206690_a(☃, ☃, "Ghast");
      func_206690_a(☃, ☃, "PigZombie");
      ☃.register(☃, "Enderman", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("carried", TypeReferences.field_211300_p.in(☃), func_206658_a(☃))));
      func_206690_a(☃, ☃, "CaveSpider");
      func_206690_a(☃, ☃, "Silverfish");
      func_206690_a(☃, ☃, "Blaze");
      func_206690_a(☃, ☃, "LavaSlime");
      func_206690_a(☃, ☃, "EnderDragon");
      func_206690_a(☃, ☃, "WitherBoss");
      func_206690_a(☃, ☃, "Bat");
      func_206690_a(☃, ☃, "Witch");
      func_206690_a(☃, ☃, "Endermite");
      func_206690_a(☃, ☃, "Guardian");
      func_206690_a(☃, ☃, "Pig");
      func_206690_a(☃, ☃, "Sheep");
      func_206690_a(☃, ☃, "Cow");
      func_206690_a(☃, ☃, "Chicken");
      func_206690_a(☃, ☃, "Squid");
      func_206690_a(☃, ☃, "Wolf");
      func_206690_a(☃, ☃, "MushroomCow");
      func_206690_a(☃, ☃, "SnowMan");
      func_206690_a(☃, ☃, "Ozelot");
      func_206690_a(☃, ☃, "VillagerGolem");
      ☃.register(
         ☃,
         "EntityHorse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items",
               DSL.list(TypeReferences.field_211295_k.in(☃)),
               "ArmorItem",
               TypeReferences.field_211295_k.in(☃),
               "SaddleItem",
               TypeReferences.field_211295_k.in(☃),
               func_206658_a(☃)
            ))
      );
      func_206690_a(☃, ☃, "Rabbit");
      ☃.register(
         ☃,
         "Villager",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(TypeReferences.field_211295_k.in(☃)),
               "Offers",
               DSL.optionalFields(
                  "Recipes",
                  DSL.list(
                     DSL.optionalFields(
                        "buy", TypeReferences.field_211295_k.in(☃), "buyB", TypeReferences.field_211295_k.in(☃), "sell", TypeReferences.field_211295_k.in(☃)
                     )
                  )
               ),
               func_206658_a(☃)
            ))
      );
      ☃.registerSimple(☃, "EnderCrystal");
      ☃.registerSimple(☃, "AreaEffectCloud");
      ☃.registerSimple(☃, "ShulkerBullet");
      func_206690_a(☃, ☃, "Shulker");
      return ☃;
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = Maps.newHashMap();
      func_206680_d(☃, ☃, "Furnace");
      func_206680_d(☃, ☃, "Chest");
      ☃.registerSimple(☃, "EnderChest");
      ☃.register(☃, "RecordPlayer", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("RecordItem", TypeReferences.field_211295_k.in(☃))));
      func_206680_d(☃, ☃, "Trap");
      func_206680_d(☃, ☃, "Dropper");
      ☃.registerSimple(☃, "Sign");
      ☃.register(☃, "MobSpawner", (Function<String, TypeTemplate>)(var1x -> TypeReferences.field_211302_r.in(☃)));
      ☃.registerSimple(☃, "Music");
      ☃.registerSimple(☃, "Piston");
      func_206680_d(☃, ☃, "Cauldron");
      ☃.registerSimple(☃, "EnchantTable");
      ☃.registerSimple(☃, "Airportal");
      ☃.registerSimple(☃, "Control");
      ☃.registerSimple(☃, "Beacon");
      ☃.registerSimple(☃, "Skull");
      ☃.registerSimple(☃, "DLDetector");
      func_206680_d(☃, ☃, "Hopper");
      ☃.registerSimple(☃, "Comparator");
      ☃.register(
         ☃,
         "FlowerPot",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), TypeReferences.field_211301_q.in(☃))))
      );
      ☃.registerSimple(☃, "Banner");
      ☃.registerSimple(☃, "Structure");
      ☃.registerSimple(☃, "EndGateway");
      return ☃;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      ☃.registerType(false, TypeReferences.field_211285_a, DSL::remainder);
      ☃.registerType(
         false,
         TypeReferences.field_211286_b,
         () -> DSL.optionalFields("Inventory", DSL.list(TypeReferences.field_211295_k.in(☃)), "EnderItems", DSL.list(TypeReferences.field_211295_k.in(☃)))
      );
      ☃.registerType(
         false,
         TypeReferences.field_211287_c,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(TypeReferences.field_211298_n.in(☃)),
                  "TileEntities",
                  DSL.list(TypeReferences.field_211294_j.in(☃)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", TypeReferences.field_211300_p.in(☃)))
               )
            )
      );
      ☃.registerType(true, TypeReferences.field_211294_j, () -> DSL.taggedChoiceLazy("id", DSL.string(), ☃));
      ☃.registerType(
         true, TypeReferences.field_211298_n, () -> DSL.optionalFields("Riding", TypeReferences.field_211298_n.in(☃), TypeReferences.field_211299_o.in(☃))
      );
      ☃.registerType(false, TypeReferences.field_211297_m, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(true, TypeReferences.field_211299_o, () -> DSL.taggedChoiceLazy("id", DSL.string(), ☃));
      ☃.registerType(
         true,
         TypeReferences.field_211295_k,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  DSL.or(DSL.constType(DSL.intType()), TypeReferences.field_211301_q.in(☃)),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     TypeReferences.field_211298_n.in(☃),
                     "BlockEntityTag",
                     TypeReferences.field_211294_j.in(☃),
                     "CanDestroy",
                     DSL.list(TypeReferences.field_211300_p.in(☃)),
                     "CanPlaceOn",
                     DSL.list(TypeReferences.field_211300_p.in(☃))
                  )
               ),
               field_206691_b,
               HookFunction.IDENTITY
            )
      );
      ☃.registerType(false, TypeReferences.field_211289_e, DSL::remainder);
      ☃.registerType(false, TypeReferences.field_211300_p, () -> DSL.or(DSL.constType(DSL.intType()), DSL.constType(DSL.namespacedString())));
      ☃.registerType(false, TypeReferences.field_211301_q, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(false, TypeReferences.field_211291_g, DSL::remainder);
      ☃.registerType(
         false,
         TypeReferences.field_211292_h,
         () -> DSL.optionalFields(
               "data",
               DSL.optionalFields(
                  "Features",
                  DSL.compoundList(TypeReferences.field_211303_s.in(☃)),
                  "Objectives",
                  DSL.list(TypeReferences.field_211873_t.in(☃)),
                  "Teams",
                  DSL.list(TypeReferences.field_211874_u.in(☃))
               )
            )
      );
      ☃.registerType(false, TypeReferences.field_211303_s, DSL::remainder);
      ☃.registerType(false, TypeReferences.field_211873_t, DSL::remainder);
      ☃.registerType(false, TypeReferences.field_211874_u, DSL::remainder);
      ☃.registerType(true, TypeReferences.field_211302_r, DSL::remainder);
   }

   protected static <T> T func_209869_a(Dynamic<T> var0, Map<String, String> var1, String var2) {
      return ☃.update("tag", var3 -> var3.update("BlockEntityTag", var2x -> {
            String ☃ = ☃.getString("id");
            String ☃x = (String)☃.get(NamespacedSchema.func_206477_f(☃));
            if (☃x == null) {
               field_206692_c.warn("Unable to resolve BlockEntity for ItemStack: {}", ☃);
               return var2x;
            } else {
               return var2x.set("id", ☃.createString(☃x));
            }
         }).update("EntityTag", var2x -> {
            String ☃ = ☃.getString("id");
            return Objects.equals(NamespacedSchema.func_206477_f(☃), "minecraft:armor_stand") ? var2x.set("id", ☃.createString(☃)) : var2x;
         })).getValue();
   }
}
