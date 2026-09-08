package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class SpawnEggNames extends DataFix {
   private static final String[] field_188226_a = DataFixUtils.make(new String[256], var0 -> {
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

   public SpawnEggNames(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Schema ☃ = this.getInputSchema();
      Type<?> ☃x = ☃.getType(TypeReferences.field_211295_k);
      OpticFinder<Pair<String, String>> ☃xx = DSL.fieldFinder("id", DSL.named(TypeReferences.field_211301_q.typeName(), DSL.namespacedString()));
      OpticFinder<String> ☃xxx = DSL.fieldFinder("id", DSL.string());
      OpticFinder<?> ☃xxxx = ☃x.findField("tag");
      OpticFinder<?> ☃xxxxx = ☃xxxx.type().findField("EntityTag");
      OpticFinder<?> ☃xxxxxx = DSL.typeFinder(☃.getTypeRaw(TypeReferences.field_211299_o));
      return this.fixTypeEverywhereTyped(
         "ItemSpawnEggFix",
         ☃x,
         var6x -> {
            Optional<Pair<String, String>> ☃ = var6x.getOptional(☃);
            if (☃.isPresent() && Objects.equals(((Pair)☃.get()).getSecond(), "minecraft:spawn_egg")) {
               Dynamic<?> ☃x = var6x.get(DSL.remainderFinder());
               short ☃xx = ☃x.getShort("Damage");
               Optional<? extends Typed<?>> ☃xxx = var6x.getOptionalTyped(☃);
               Optional<? extends Typed<?>> ☃xxxx = ☃xxx.flatMap(var1x -> var1x.getOptionalTyped(☃));
               Optional<? extends Typed<?>> ☃xxxxx = ☃xxxx.flatMap(var1x -> var1x.getOptionalTyped(☃));
               Optional<String> ☃xxxxxx = ☃xxxxx.flatMap(var1x -> var1x.getOptional(☃));
               Typed<?> ☃xxxxxxx = var6x;
               String ☃xxxxxxxx = field_188226_a[☃xx & 255];
               if (☃xxxxxxxx != null && (!☃xxxxxx.isPresent() || !Objects.equals(☃xxxxxx.get(), ☃xxxxxxxx))) {
                  Typed<?> ☃xxxxxxxxx = var6x.getOrCreateTyped(☃);
                  Typed<?> ☃xxxxxxxxxx = ☃xxxxxxxxx.getOrCreateTyped(☃);
                  Typed<?> ☃xxxxxxxxxxx = ☃xxxxxxxxxx.getOrCreateTyped(☃);
                  Dynamic<?> ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx.write().set("id", ☃x.createString(☃xxxxxxxx));
                  Typed<?> ☃xxxxxxxxxxxxx = (Typed)((Optional)this.getOutputSchema()
                        .getTypeRaw(TypeReferences.field_211299_o)
                        .readTyped(☃xxxxxxxxxxxx)
                        .getSecond())
                     .orElseThrow(() -> new IllegalStateException("Could not parse new entity"));
                  ☃xxxxxxx = var6x.set(☃, ☃xxxxxxxxx.set(☃, ☃xxxxxxxxxx.set(☃, ☃xxxxxxxxxxxxx)));
               }
   
               if (☃xx != 0) {
                  ☃x = ☃x.set("Damage", ☃x.createShort((short)0));
                  ☃xxxxxxx = ☃xxxxxxx.set(DSL.remainderFinder(), ☃x);
               }
   
               return ☃xxxxxxx;
            } else {
               return var6x;
            }
         }
      );
   }
}
