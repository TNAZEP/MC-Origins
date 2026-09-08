package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class EntityHealthFix extends DataFix {
   private static final Set<String> ENTITIES = Sets.newHashSet(
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

   public EntityHealthFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      Optional<Number> â˜ƒx = â˜ƒ.get("HealF").asNumber().result();
      Optional<Number> â˜ƒxx = â˜ƒ.get("Health").asNumber().result();
      float â˜ƒ;
      if (â˜ƒx.isPresent()) {
         â˜ƒ = ((Number)â˜ƒx.get()).floatValue();
         â˜ƒ = â˜ƒ.remove("HealF");
      } else {
         if (!â˜ƒxx.isPresent()) {
            return â˜ƒ;
         }

         â˜ƒ = ((Number)â˜ƒxx.get()).floatValue();
      }

      return â˜ƒ.set("Health", â˜ƒ.createFloat(â˜ƒ));
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityHealthFix", this.getInputSchema().getType(References.ENTITY), var1 -> var1.update(DSL.remainderFinder(), this::fixTag)
      );
   }
}
