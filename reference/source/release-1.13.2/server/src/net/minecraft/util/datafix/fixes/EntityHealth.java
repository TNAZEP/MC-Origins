package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.datafix.TypeReferences;

public class EntityHealth extends DataFix {
   private static final Set<String> field_188218_a = Sets.newHashSet(
      "ArmorStand",
      "Bat",
      "Blaze",
      "CaveSpider",
      "Chicken",
      "Cow",
      "Creeper",
      "EnderDragon",
      "Enderman",
      "Endermite",
      "EntityHorse",
      "Ghast",
      "Giant",
      "Guardian",
      "LavaSlime",
      "MushroomCow",
      "Ozelot",
      "Pig",
      "PigZombie",
      "Rabbit",
      "Sheep",
      "Shulker",
      "Silverfish",
      "Skeleton",
      "Slime",
      "SnowMan",
      "Spider",
      "Squid",
      "Villager",
      "VillagerGolem",
      "Witch",
      "WitherBoss",
      "Wolf",
      "Zombie"
   );

   public EntityHealth(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   public Dynamic<?> func_209743_a(Dynamic<?> var1) {
      Optional<Number> ☃x = ☃.get("HealF").flatMap(Dynamic::getNumberValue);
      Optional<Number> ☃xx = ☃.get("Health").flatMap(Dynamic::getNumberValue);
      float ☃;
      if (☃x.isPresent()) {
         ☃ = ((Number)☃x.get()).floatValue();
         ☃ = ☃.remove("HealF");
      } else {
         if (!☃xx.isPresent()) {
            return ☃;
         }

         ☃ = ((Number)☃xx.get()).floatValue();
      }

      return ☃.set("Health", ☃.createFloat(☃));
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityHealthFix", this.getInputSchema().getType(TypeReferences.field_211299_o), var1 -> var1.update(DSL.remainderFinder(), this::func_209743_a)
      );
   }
}
