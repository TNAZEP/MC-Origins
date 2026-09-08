package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V0705 extends NamespacedSchema {
   protected static final HookFunction field_206597_b = new HookFunction() {
      @Override
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return V0099.func_209869_a(new Dynamic<>(☃, ☃), V0704.field_206647_b, "minecraft:armor_stand");
      }
   };

   public V0705(int var1, Schema var2) {
      super(☃, ☃);
   }

   protected static void func_206596_a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> V0100.func_206605_a(☃)));
   }

   protected static void func_206581_b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inTile", TypeReferences.field_211300_p.in(☃))));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = Maps.newHashMap();
      ☃.registerSimple(☃, "minecraft:area_effect_cloud");
      func_206596_a(☃, ☃, "minecraft:armor_stand");
      ☃.register(☃, "minecraft:arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", TypeReferences.field_211300_p.in(☃))));
      func_206596_a(☃, ☃, "minecraft:bat");
      func_206596_a(☃, ☃, "minecraft:blaze");
      ☃.registerSimple(☃, "minecraft:boat");
      func_206596_a(☃, ☃, "minecraft:cave_spider");
      ☃.register(
         ☃,
         "minecraft:chest_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", TypeReferences.field_211300_p.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      func_206596_a(☃, ☃, "minecraft:chicken");
      ☃.register(
         ☃,
         "minecraft:commandblock_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", TypeReferences.field_211300_p.in(☃)))
      );
      func_206596_a(☃, ☃, "minecraft:cow");
      func_206596_a(☃, ☃, "minecraft:creeper");
      ☃.register(
         ☃,
         "minecraft:donkey",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items", DSL.list(TypeReferences.field_211295_k.in(☃)), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      ☃.registerSimple(☃, "minecraft:dragon_fireball");
      func_206581_b(☃, ☃, "minecraft:egg");
      func_206596_a(☃, ☃, "minecraft:elder_guardian");
      ☃.registerSimple(☃, "minecraft:ender_crystal");
      func_206596_a(☃, ☃, "minecraft:ender_dragon");
      ☃.register(
         ☃,
         "minecraft:enderman",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("carried", TypeReferences.field_211300_p.in(☃), V0100.func_206605_a(☃)))
      );
      func_206596_a(☃, ☃, "minecraft:endermite");
      func_206581_b(☃, ☃, "minecraft:ender_pearl");
      ☃.registerSimple(☃, "minecraft:eye_of_ender_signal");
      ☃.register(
         ☃,
         "minecraft:falling_block",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Block", TypeReferences.field_211300_p.in(☃), "TileEntityData", TypeReferences.field_211294_j.in(☃)
            ))
      );
      func_206581_b(☃, ☃, "minecraft:fireball");
      ☃.register(
         ☃, "minecraft:fireworks_rocket", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("FireworksItem", TypeReferences.field_211295_k.in(☃)))
      );
      ☃.register(
         ☃, "minecraft:furnace_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", TypeReferences.field_211300_p.in(☃)))
      );
      func_206596_a(☃, ☃, "minecraft:ghast");
      func_206596_a(☃, ☃, "minecraft:giant");
      func_206596_a(☃, ☃, "minecraft:guardian");
      ☃.register(
         ☃,
         "minecraft:hopper_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", TypeReferences.field_211300_p.in(☃), "Items", DSL.list(TypeReferences.field_211295_k.in(☃))
            ))
      );
      ☃.register(
         ☃,
         "minecraft:horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "ArmorItem", TypeReferences.field_211295_k.in(☃), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      func_206596_a(☃, ☃, "minecraft:husk");
      ☃.register(☃, "minecraft:item", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", TypeReferences.field_211295_k.in(☃))));
      ☃.register(☃, "minecraft:item_frame", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", TypeReferences.field_211295_k.in(☃))));
      ☃.registerSimple(☃, "minecraft:leash_knot");
      func_206596_a(☃, ☃, "minecraft:magma_cube");
      ☃.register(☃, "minecraft:minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", TypeReferences.field_211300_p.in(☃))));
      func_206596_a(☃, ☃, "minecraft:mooshroom");
      ☃.register(
         ☃,
         "minecraft:mule",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items", DSL.list(TypeReferences.field_211295_k.in(☃)), "SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)
            ))
      );
      func_206596_a(☃, ☃, "minecraft:ocelot");
      ☃.registerSimple(☃, "minecraft:painting");
      ☃.registerSimple(☃, "minecraft:parrot");
      func_206596_a(☃, ☃, "minecraft:pig");
      func_206596_a(☃, ☃, "minecraft:polar_bear");
      ☃.register(
         ☃,
         "minecraft:potion",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Potion", TypeReferences.field_211295_k.in(☃), "inTile", TypeReferences.field_211300_p.in(☃)
            ))
      );
      func_206596_a(☃, ☃, "minecraft:rabbit");
      func_206596_a(☃, ☃, "minecraft:sheep");
      func_206596_a(☃, ☃, "minecraft:shulker");
      ☃.registerSimple(☃, "minecraft:shulker_bullet");
      func_206596_a(☃, ☃, "minecraft:silverfish");
      func_206596_a(☃, ☃, "minecraft:skeleton");
      ☃.register(
         ☃,
         "minecraft:skeleton_horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)))
      );
      func_206596_a(☃, ☃, "minecraft:slime");
      func_206581_b(☃, ☃, "minecraft:small_fireball");
      func_206581_b(☃, ☃, "minecraft:snowball");
      func_206596_a(☃, ☃, "minecraft:snowman");
      ☃.register(
         ☃,
         "minecraft:spawner_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", TypeReferences.field_211300_p.in(☃), TypeReferences.field_211302_r.in(☃)))
      );
      ☃.register(☃, "minecraft:spectral_arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", TypeReferences.field_211300_p.in(☃))));
      func_206596_a(☃, ☃, "minecraft:spider");
      func_206596_a(☃, ☃, "minecraft:squid");
      func_206596_a(☃, ☃, "minecraft:stray");
      ☃.registerSimple(☃, "minecraft:tnt");
      ☃.register(☃, "minecraft:tnt_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", TypeReferences.field_211300_p.in(☃))));
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
      func_206596_a(☃, ☃, "minecraft:villager_golem");
      func_206596_a(☃, ☃, "minecraft:witch");
      func_206596_a(☃, ☃, "minecraft:wither");
      func_206596_a(☃, ☃, "minecraft:wither_skeleton");
      func_206581_b(☃, ☃, "minecraft:wither_skull");
      func_206596_a(☃, ☃, "minecraft:wolf");
      func_206581_b(☃, ☃, "minecraft:xp_bottle");
      ☃.registerSimple(☃, "minecraft:xp_orb");
      func_206596_a(☃, ☃, "minecraft:zombie");
      ☃.register(
         ☃,
         "minecraft:zombie_horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("SaddleItem", TypeReferences.field_211295_k.in(☃), V0100.func_206605_a(☃)))
      );
      func_206596_a(☃, ☃, "minecraft:zombie_pigman");
      func_206596_a(☃, ☃, "minecraft:zombie_villager");
      ☃.registerSimple(☃, "minecraft:evocation_fangs");
      func_206596_a(☃, ☃, "minecraft:evocation_illager");
      ☃.registerSimple(☃, "minecraft:illusion_illager");
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
      func_206596_a(☃, ☃, "minecraft:vex");
      func_206596_a(☃, ☃, "minecraft:vindication_illager");
      return ☃;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
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
               field_206597_b,
               HookFunction.IDENTITY
            )
      );
   }
}
