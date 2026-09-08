package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1460 extends NamespacedSchema {
   public V1460(int var1, Schema var2) {
      super(☃, ☃);
   }

   protected static void func_206557_a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> V0100.func_206605_a(☃)));
   }

   protected static void func_206531_b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(TypeReferences.field_211295_k.in(☃)))));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = Maps.newHashMap();
      ☃.registerSimple(☃, "minecraft:area_effect_cloud");
      func_206557_a(☃, ☃, "minecraft:armor_stand");
      ☃.register(☃, "minecraft:arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inBlockState", TypeReferences.field_211296_l.in(☃))));
      func_206557_a(☃, ☃, "minecraft:bat");
      func_206557_a(☃, ☃, "minecraft:blaze");
      ☃.registerSimple(☃, "minecraft:boat");
      func_206557_a(☃, ☃, "minecraft:cave_spider");
      ☃.register(
         ☃,
         "minecraft:chest_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayState", TypeReferences.field_211296_l.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      func_206557_a(☃, ☃, "minecraft:chicken");
      ☃.register(
         ☃,
         "minecraft:commandblock_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃)))
      );
      func_206557_a(☃, ☃, "minecraft:cow");
      func_206557_a(☃, ☃, "minecraft:creeper");
      ☃.register(
         ☃,
         "minecraft:donkey",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items", DSL.list(TypeReferences.field_211295_k.in(☃)), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      ☃.registerSimple(☃, "minecraft:dragon_fireball");
      ☃.registerSimple(☃, "minecraft:egg");
      func_206557_a(☃, ☃, "minecraft:elder_guardian");
      ☃.registerSimple(☃, "minecraft:ender_crystal");
      func_206557_a(☃, ☃, "minecraft:ender_dragon");
      ☃.register(
         ☃,
         "minecraft:enderman",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("carriedBlockState", TypeReferences.field_211296_l.in(☃), V0100.func_206605_a(☃)))
      );
      func_206557_a(☃, ☃, "minecraft:endermite");
      ☃.registerSimple(☃, "minecraft:ender_pearl");
      ☃.registerSimple(☃, "minecraft:evocation_fangs");
      func_206557_a(☃, ☃, "minecraft:evocation_illager");
      ☃.registerSimple(☃, "minecraft:eye_of_ender_signal");
      ☃.register(
         ☃,
         "minecraft:falling_block",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "BlockState", TypeReferences.field_211296_l.in(☃), "TileEntityData", TypeReferences.field_211294_j.in(☃)
            ))
      );
      ☃.registerSimple(☃, "minecraft:fireball");
      ☃.register(
         ☃, "minecraft:fireworks_rocket", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("FireworksItem", TypeReferences.field_211295_k.in(☃)))
      );
      ☃.register(
         ☃, "minecraft:furnace_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃)))
      );
      func_206557_a(☃, ☃, "minecraft:ghast");
      func_206557_a(☃, ☃, "minecraft:giant");
      func_206557_a(☃, ☃, "minecraft:guardian");
      ☃.register(
         ☃,
         "minecraft:hopper_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayState", TypeReferences.field_211296_l.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      ☃.register(
         ☃,
         "minecraft:horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "ArmorItem", TypeReferences.field_211295_k.in(☃), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      func_206557_a(☃, ☃, "minecraft:husk");
      ☃.registerSimple(☃, "minecraft:illusion_illager");
      ☃.register(☃, "minecraft:item", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", TypeReferences.field_211295_k.in(☃))));
      ☃.register(☃, "minecraft:item_frame", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", TypeReferences.field_211295_k.in(☃))));
      ☃.registerSimple(☃, "minecraft:leash_knot");
      ☃.register(
         ☃,
         "minecraft:llama",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items",
               DSL.list(TypeReferences.field_211295_k.in(☃)),
               "SaddleItem",
               TypeReferences.field_211295_k.in(☃),
               "DecorItem",
               TypeReferences.field_211295_k.in(☃),
               V0100.func_206605_a(☃)
            ))
      );
      ☃.registerSimple(☃, "minecraft:llama_spit");
      func_206557_a(☃, ☃, "minecraft:magma_cube");
      ☃.register(☃, "minecraft:minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃))));
      func_206557_a(☃, ☃, "minecraft:mooshroom");
      ☃.register(
         ☃,
         "minecraft:mule",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items", DSL.list(TypeReferences.field_211295_k.in(☃)), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      func_206557_a(☃, ☃, "minecraft:ocelot");
      ☃.registerSimple(☃, "minecraft:painting");
      ☃.registerSimple(☃, "minecraft:parrot");
      func_206557_a(☃, ☃, "minecraft:pig");
      func_206557_a(☃, ☃, "minecraft:polar_bear");
      ☃.register(☃, "minecraft:potion", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Potion", TypeReferences.field_211295_k.in(☃))));
      func_206557_a(☃, ☃, "minecraft:rabbit");
      func_206557_a(☃, ☃, "minecraft:sheep");
      func_206557_a(☃, ☃, "minecraft:shulker");
      ☃.registerSimple(☃, "minecraft:shulker_bullet");
      func_206557_a(☃, ☃, "minecraft:silverfish");
      func_206557_a(☃, ☃, "minecraft:skeleton");
      ☃.register(
         ☃,
         "minecraft:skeleton_horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)))
      );
      func_206557_a(☃, ☃, "minecraft:slime");
      ☃.registerSimple(☃, "minecraft:small_fireball");
      ☃.registerSimple(☃, "minecraft:snowball");
      func_206557_a(☃, ☃, "minecraft:snowman");
      ☃.register(
         ☃,
         "minecraft:spawner_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃), TypeReferences.field_211302_r.in(☃)))
      );
      ☃.register(
         ☃, "minecraft:spectral_arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inBlockState", TypeReferences.field_211296_l.in(☃)))
      );
      func_206557_a(☃, ☃, "minecraft:spider");
      func_206557_a(☃, ☃, "minecraft:squid");
      func_206557_a(☃, ☃, "minecraft:stray");
      ☃.registerSimple(☃, "minecraft:tnt");
      ☃.register(
         ☃, "minecraft:tnt_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayState", TypeReferences.field_211296_l.in(☃)))
      );
      func_206557_a(☃, ☃, "minecraft:vex");
      ☃.register(
         ☃,
         "minecraft:villager",
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
               V0100.func_206605_a(☃)
            ))
      );
      func_206557_a(☃, ☃, "minecraft:villager_golem");
      func_206557_a(☃, ☃, "minecraft:vindication_illager");
      func_206557_a(☃, ☃, "minecraft:witch");
      func_206557_a(☃, ☃, "minecraft:wither");
      func_206557_a(☃, ☃, "minecraft:wither_skeleton");
      ☃.registerSimple(☃, "minecraft:wither_skull");
      func_206557_a(☃, ☃, "minecraft:wolf");
      ☃.registerSimple(☃, "minecraft:xp_bottle");
      ☃.registerSimple(☃, "minecraft:xp_orb");
      func_206557_a(☃, ☃, "minecraft:zombie");
      ☃.register(
         ☃,
         "minecraft:zombie_horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)))
      );
      func_206557_a(☃, ☃, "minecraft:zombie_pigman");
      func_206557_a(☃, ☃, "minecraft:zombie_villager");
      return ☃;
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = Maps.newHashMap();
      func_206531_b(☃, ☃, "minecraft:furnace");
      func_206531_b(☃, ☃, "minecraft:chest");
      func_206531_b(☃, ☃, "minecraft:trapped_chest");
      ☃.registerSimple(☃, "minecraft:ender_chest");
      ☃.register(☃, "minecraft:jukebox", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("RecordItem", TypeReferences.field_211295_k.in(☃))));
      func_206531_b(☃, ☃, "minecraft:dispenser");
      func_206531_b(☃, ☃, "minecraft:dropper");
      ☃.registerSimple(☃, "minecraft:sign");
      ☃.register(☃, "minecraft:mob_spawner", (Function<String, TypeTemplate>)(var1x -> TypeReferences.field_211302_r.in(☃)));
      ☃.register(☃, "minecraft:piston", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("blockState", TypeReferences.field_211296_l.in(☃))));
      func_206531_b(☃, ☃, "minecraft:brewing_stand");
      ☃.registerSimple(☃, "minecraft:enchanting_table");
      ☃.registerSimple(☃, "minecraft:end_portal");
      ☃.registerSimple(☃, "minecraft:beacon");
      ☃.registerSimple(☃, "minecraft:skull");
      ☃.registerSimple(☃, "minecraft:daylight_detector");
      func_206531_b(☃, ☃, "minecraft:hopper");
      ☃.registerSimple(☃, "minecraft:comparator");
      ☃.registerSimple(☃, "minecraft:banner");
      ☃.registerSimple(☃, "minecraft:structure_block");
      ☃.registerSimple(☃, "minecraft:end_gateway");
      ☃.registerSimple(☃, "minecraft:command_block");
      func_206531_b(☃, ☃, "minecraft:shulker_box");
      ☃.registerSimple(☃, "minecraft:bed");
      return ☃;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      ☃.registerType(false, TypeReferences.field_211285_a, DSL::remainder);
      ☃.registerType(false, TypeReferences.field_211304_t, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(
         false,
         TypeReferences.field_211286_b,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", TypeReferences.field_211298_n.in(☃)),
               "Inventory",
               DSL.list(TypeReferences.field_211295_k.in(☃)),
               "EnderItems",
               DSL.list(TypeReferences.field_211295_k.in(☃)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  TypeReferences.field_211298_n.in(☃),
                  "ShoulderEntityRight",
                  TypeReferences.field_211298_n.in(☃),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(TypeReferences.field_211304_t.in(☃)), "toBeDisplayed", DSL.list(TypeReferences.field_211304_t.in(☃)))
               )
            )
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
                  DSL.list(DSL.fields("i", TypeReferences.field_211300_p.in(☃))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(TypeReferences.field_211296_l.in(☃))))
               )
            )
      );
      ☃.registerType(true, TypeReferences.field_211294_j, () -> DSL.taggedChoiceLazy("id", DSL.namespacedString(), ☃));
      ☃.registerType(
         true,
         TypeReferences.field_211298_n,
         () -> DSL.optionalFields("Passengers", DSL.list(TypeReferences.field_211298_n.in(☃)), TypeReferences.field_211299_o.in(☃))
      );
      ☃.registerType(true, TypeReferences.field_211299_o, () -> DSL.taggedChoiceLazy("id", DSL.namespacedString(), ☃));
      ☃.registerType(
         true,
         TypeReferences.field_211295_k,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  TypeReferences.field_211301_q.in(☃),
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
               V0705.field_206597_b,
               HookFunction.IDENTITY
            )
      );
      ☃.registerType(false, TypeReferences.field_211288_d, () -> DSL.compoundList(DSL.list(TypeReferences.field_211295_k.in(☃))));
      ☃.registerType(false, TypeReferences.field_211289_e, DSL::remainder);
      ☃.registerType(
         false,
         TypeReferences.field_211290_f,
         () -> DSL.optionalFields(
               "entities",
               DSL.list(DSL.optionalFields("nbt", TypeReferences.field_211298_n.in(☃))),
               "blocks",
               DSL.list(DSL.optionalFields("nbt", TypeReferences.field_211294_j.in(☃))),
               "palette",
               DSL.list(TypeReferences.field_211296_l.in(☃))
            )
      );
      ☃.registerType(false, TypeReferences.field_211300_p, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(false, TypeReferences.field_211301_q, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(false, TypeReferences.field_211296_l, DSL::remainder);
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
      ☃.registerType(
         false,
         TypeReferences.field_211303_s,
         () -> DSL.optionalFields(
               "Children",
               DSL.list(
                  DSL.optionalFields(
                     "CA",
                     TypeReferences.field_211296_l.in(☃),
                     "CB",
                     TypeReferences.field_211296_l.in(☃),
                     "CC",
                     TypeReferences.field_211296_l.in(☃),
                     "CD",
                     TypeReferences.field_211296_l.in(☃)
                  )
               )
            )
      );
      ☃.registerType(false, TypeReferences.field_211873_t, DSL::remainder);
      ☃.registerType(false, TypeReferences.field_211874_u, DSL::remainder);
      ☃.registerType(
         true,
         TypeReferences.field_211302_r,
         () -> DSL.optionalFields(
               "SpawnPotentials", DSL.list(DSL.fields("Entity", TypeReferences.field_211298_n.in(☃))), "SpawnData", TypeReferences.field_211298_n.in(☃)
            )
      );
      ☃.registerType(
         false,
         TypeReferences.field_211293_i,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211305_u.in(☃), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.field_211297_m.in(☃), DSL.constType(DSL.string())))
            )
      );
      ☃.registerType(false, TypeReferences.field_211305_u, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(false, TypeReferences.field_211297_m, () -> DSL.constType(DSL.namespacedString()));
   }
}
