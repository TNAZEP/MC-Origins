package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V705 extends NamespacedSchema {
   protected static final HookFunction ADD_NAMES = new HookFunction() {
      @Override
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return V99.addNames(new Dynamic<>(â˜ƒ, â˜ƒ), V704.ITEM_TO_BLOCKENTITY, "minecraft:armor_stand");
      }
   };

   public V705(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static void registerMob(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> V100.equipment(â˜ƒ)));
   }

   protected static void registerThrowableProjectile(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ))));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = Maps.newHashMap();
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:area_effect_cloud");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:armor_stand");
      â˜ƒ.register(â˜ƒ, "minecraft:arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ))));
      registerMob(â˜ƒ, â˜ƒ, "minecraft:bat");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:blaze");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:boat");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:cave_spider");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:chest_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", References.BLOCK_NAME.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))
            ))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:chicken");
      â˜ƒ.register(
         â˜ƒ, "minecraft:commandblock_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ)))
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
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "minecraft:egg");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:elder_guardian");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:ender_crystal");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:ender_dragon");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:enderman",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("carried", References.BLOCK_NAME.in(â˜ƒ), V100.equipment(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:endermite");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "minecraft:ender_pearl");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:eye_of_ender_signal");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:falling_block",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Block", References.BLOCK_NAME.in(â˜ƒ), "TileEntityData", References.BLOCK_ENTITY.in(â˜ƒ)))
      );
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "minecraft:fireball");
      â˜ƒ.register(
         â˜ƒ, "minecraft:fireworks_rocket", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("FireworksItem", References.ITEM_STACK.in(â˜ƒ)))
      );
      â˜ƒ.register(
         â˜ƒ, "minecraft:furnace_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:ghast");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:giant");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:guardian");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:hopper_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "DisplayTile", References.BLOCK_NAME.in(â˜ƒ), "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ))
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
      â˜ƒ.register(â˜ƒ, "minecraft:item", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.register(â˜ƒ, "minecraft:item_frame", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Item", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:leash_knot");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:magma_cube");
      â˜ƒ.register(â˜ƒ, "minecraft:minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ))));
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
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:potion",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Potion", References.ITEM_STACK.in(â˜ƒ), "inTile", References.BLOCK_NAME.in(â˜ƒ)))
      );
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
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "minecraft:small_fireball");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "minecraft:snowball");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:snowman");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:spawner_minecart",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ), References.UNTAGGED_SPAWNER.in(â˜ƒ)))
      );
      â˜ƒ.register(â˜ƒ, "minecraft:spectral_arrow", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("inTile", References.BLOCK_NAME.in(â˜ƒ))));
      registerMob(â˜ƒ, â˜ƒ, "minecraft:spider");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:squid");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:stray");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:tnt");
      â˜ƒ.register(â˜ƒ, "minecraft:tnt_minecart", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in(â˜ƒ))));
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
      registerMob(â˜ƒ, â˜ƒ, "minecraft:witch");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:wither");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:wither_skeleton");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "minecraft:wither_skull");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:wolf");
      registerThrowableProjectile(â˜ƒ, â˜ƒ, "minecraft:xp_bottle");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:xp_orb");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:zombie");
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:zombie_horse",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)))
      );
      registerMob(â˜ƒ, â˜ƒ, "minecraft:zombie_pigman");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:zombie_villager");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:evocation_fangs");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:evocation_illager");
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:illusion_illager");
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
      registerMob(â˜ƒ, â˜ƒ, "minecraft:vex");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:vindication_illager");
      return â˜ƒ;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
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
               ADD_NAMES,
               HookFunction.IDENTITY
            )
      );
   }
}
