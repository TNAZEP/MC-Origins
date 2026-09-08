package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V100 extends Schema {
   public V100(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static TypeTemplate equipment(Schema var0) {
      return DSL.optionalFields("ArmorItems", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "HandItems", DSL.list(References.ITEM_STACK.in(â˜ƒ)));
   }

   protected static void registerMob(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> equipment(â˜ƒ)));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
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
      registerMob(â˜ƒ, â˜ƒ, "Shulker");
      â˜ƒ.registerSimple(â˜ƒ, "AreaEffectCloud");
      â˜ƒ.registerSimple(â˜ƒ, "ShulkerBullet");
      return â˜ƒ;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
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
      â˜ƒ.registerType(false, References.BLOCK_STATE, DSL::remainder);
   }
}
