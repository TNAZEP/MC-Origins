package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemSpawnEggFix extends DataFix {
   private static final String[] ID_TO_ENTITY = DataFixUtils.make(new String[256], var0 -> {
      var0[1] = "Item";
      var0[2] = "XPOrb";
      var0[7] = "ThrownEgg";
      var0[8] = "LeashKnot";
      var0[9] = "Painting";
      var0[10] = "Arrow";
      var0[11] = "Snowball";
      var0[12] = "Fireball";
      var0[13] = "SmallFireball";
      var0[14] = "ThrownEnderpearl";
      var0[15] = "EyeOfEnderSignal";
      var0[16] = "ThrownPotion";
      var0[17] = "ThrownExpBottle";
      var0[18] = "ItemFrame";
      var0[19] = "WitherSkull";
      var0[20] = "PrimedTnt";
      var0[21] = "FallingSand";
      var0[22] = "FireworksRocketEntity";
      var0[23] = "TippedArrow";
      var0[24] = "SpectralArrow";
      var0[25] = "ShulkerBullet";
      var0[26] = "DragonFireball";
      var0[30] = "ArmorStand";
      var0[41] = "Boat";
      var0[42] = "MinecartRideable";
      var0[43] = "MinecartChest";
      var0[44] = "MinecartFurnace";
      var0[45] = "MinecartTNT";
      var0[46] = "MinecartHopper";
      var0[47] = "MinecartSpawner";
      var0[40] = "MinecartCommandBlock";
      var0[48] = "Mob";
      var0[49] = "Monster";
      var0[50] = "Creeper";
      var0[51] = "Skeleton";
      var0[52] = "Spider";
      var0[53] = "Giant";
      var0[54] = "Zombie";
      var0[55] = "Slime";
      var0[56] = "Ghast";
      var0[57] = "PigZombie";
      var0[58] = "Enderman";
      var0[59] = "CaveSpider";
      var0[60] = "Silverfish";
      var0[61] = "Blaze";
      var0[62] = "LavaSlime";
      var0[63] = "EnderDragon";
      var0[64] = "WitherBoss";
      var0[65] = "Bat";
      var0[66] = "Witch";
      var0[67] = "Endermite";
      var0[68] = "Guardian";
      var0[69] = "Shulker";
      var0[90] = "Pig";
      var0[91] = "Sheep";
      var0[92] = "Cow";
      var0[93] = "Chicken";
      var0[94] = "Squid";
      var0[95] = "Wolf";
      var0[96] = "MushroomCow";
      var0[97] = "SnowMan";
      var0[98] = "Ozelot";
      var0[99] = "VillagerGolem";
      var0[100] = "EntityHorse";
      var0[101] = "Rabbit";
      var0[120] = "Villager";
      var0[200] = "EnderCrystal";
   });

   public ItemSpawnEggFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Schema â˜ƒ = this.getInputSchema();
      Type<?> â˜ƒx = â˜ƒ.getType(References.ITEM_STACK);
      OpticFinder<Pair<String, String>> â˜ƒxx = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      OpticFinder<String> â˜ƒxxx = DSL.fieldFinder("id", DSL.string());
      OpticFinder<?> â˜ƒxxxx = â˜ƒx.findField("tag");
      OpticFinder<?> â˜ƒxxxxx = â˜ƒxxxx.type().findField("EntityTag");
      OpticFinder<?> â˜ƒxxxxxx = DSL.typeFinder(â˜ƒ.getTypeRaw(References.ENTITY));
      Type<?> â˜ƒxxxxxxx = this.getOutputSchema().getTypeRaw(References.ENTITY);
      return this.fixTypeEverywhereTyped(
         "ItemSpawnEggFix",
         â˜ƒx,
         var6x -> {
            Optional<Pair<String, String>> â˜ƒ = var6x.getOptional(â˜ƒ);
            if (â˜ƒ.isPresent() && Objects.equals(((Pair)â˜ƒ.get()).getSecond(), "minecraft:spawn_egg")) {
               Dynamic<?> â˜ƒx = var6x.get(DSL.remainderFinder());
               short â˜ƒxx = â˜ƒx.get("Damage").asShort((short)0);
               Optional<? extends Typed<?>> â˜ƒxxx = var6x.getOptionalTyped(â˜ƒ);
               Optional<? extends Typed<?>> â˜ƒxxxx = â˜ƒxxx.flatMap(var1x -> var1x.getOptionalTyped(â˜ƒ));
               Optional<? extends Typed<?>> â˜ƒxxxxx = â˜ƒxxxx.flatMap(var1x -> var1x.getOptionalTyped(â˜ƒ));
               Optional<String> â˜ƒxxxxxx = â˜ƒxxxxx.flatMap(var1x -> var1x.getOptional(â˜ƒ));
               Typed<?> â˜ƒxxxxxxx = var6x;
               String â˜ƒxxxxxxxx = ID_TO_ENTITY[â˜ƒxx & 255];
               if (â˜ƒxxxxxxxx != null && (!â˜ƒxxxxxx.isPresent() || !Objects.equals(â˜ƒxxxxxx.get(), â˜ƒxxxxxxxx))) {
                  Typed<?> â˜ƒxxxxxxxxx = var6x.getOrCreateTyped(â˜ƒ);
                  Typed<?> â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.getOrCreateTyped(â˜ƒ);
                  Typed<?> â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.getOrCreateTyped(â˜ƒ);
                  Dynamic<?> â˜ƒxxxxxxxxxxxx = â˜ƒx;
                  Typed<?> â˜ƒxxxxxxxxxxxxx = (Typed)((Pair)â˜ƒxxxxxxxxxxx.write()
                        .flatMap(var3x -> â˜ƒ.readTyped(var3x.set("id", â˜ƒ.createString(â˜ƒ))))
                        .result()
                        .orElseThrow(() -> new IllegalStateException("Could not parse new entity")))
                     .getFirst();
                  â˜ƒxxxxxxx = var6x.set(â˜ƒ, â˜ƒxxxxxxxxx.set(â˜ƒ, â˜ƒxxxxxxxxxx.set(â˜ƒ, â˜ƒxxxxxxxxxxxxx)));
               }
   
               if (â˜ƒxx != 0) {
                  â˜ƒx = â˜ƒx.set("Damage", â˜ƒx.createShort((short)0));
                  â˜ƒxxxxxxx = â˜ƒxxxxxxx.set(DSL.remainderFinder(), â˜ƒx);
               }
   
               return â˜ƒxxxxxxx;
            } else {
               return var6x;
            }
         }
      );
   }
}
