package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.TypeReferences;

public class V0100 extends Schema {
   public V0100(int var1, Schema var2) {
      super(☃, ☃);
   }

   protected static TypeTemplate func_206605_a(Schema var0) {
      return DSL.optionalFields("ArmorItems", DSL.list(TypeReferences.field_211295_k.in(☃)), "HandItems", DSL.list(TypeReferences.field_211295_k.in(☃)));
   }

   protected static void func_206611_a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> func_206605_a(☃)));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerEntities(☃);
      func_206611_a(☃, ☃, "ArmorStand");
      func_206611_a(☃, ☃, "Creeper");
      func_206611_a(☃, ☃, "Skeleton");
      func_206611_a(☃, ☃, "Spider");
      func_206611_a(☃, ☃, "Giant");
      func_206611_a(☃, ☃, "Zombie");
      func_206611_a(☃, ☃, "Slime");
      func_206611_a(☃, ☃, "Ghast");
      func_206611_a(☃, ☃, "PigZombie");
      ☃.register(☃, "Enderman", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("carried", TypeReferences.field_211300_p.in(☃), func_206605_a(☃))));
      func_206611_a(☃, ☃, "CaveSpider");
      func_206611_a(☃, ☃, "Silverfish");
      func_206611_a(☃, ☃, "Blaze");
      func_206611_a(☃, ☃, "LavaSlime");
      func_206611_a(☃, ☃, "EnderDragon");
      func_206611_a(☃, ☃, "WitherBoss");
      func_206611_a(☃, ☃, "Bat");
      func_206611_a(☃, ☃, "Witch");
      func_206611_a(☃, ☃, "Endermite");
      func_206611_a(☃, ☃, "Guardian");
      func_206611_a(☃, ☃, "Pig");
      func_206611_a(☃, ☃, "Sheep");
      func_206611_a(☃, ☃, "Cow");
      func_206611_a(☃, ☃, "Chicken");
      func_206611_a(☃, ☃, "Squid");
      func_206611_a(☃, ☃, "Wolf");
      func_206611_a(☃, ☃, "MushroomCow");
      func_206611_a(☃, ☃, "SnowMan");
      func_206611_a(☃, ☃, "Ozelot");
      func_206611_a(☃, ☃, "VillagerGolem");
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
               func_206605_a(☃)
            ))
      );
      func_206611_a(☃, ☃, "Rabbit");
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
               func_206605_a(☃)
            ))
      );
      func_206611_a(☃, ☃, "Shulker");
      ☃.registerSimple(☃, "AreaEffectCloud");
      ☃.registerSimple(☃, "ShulkerBullet");
      return ☃;
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
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
      ☃.registerType(false, TypeReferences.field_211296_l, DSL::remainder);
   }
}
