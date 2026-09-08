package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Random;

public class EntityZombieVillagerTypeFix extends NamedEntityFix {
   private static final int PROFESSION_MAX = 6;
   private static final Random RANDOM = new Random();

   public EntityZombieVillagerTypeFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "EntityZombieVillagerTypeFix", References.ENTITY, "Zombie");
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      if (â˜ƒ.get("IsVillager").asBoolean(false)) {
         if (!â˜ƒ.get("ZombieType").result().isPresent()) {
            int â˜ƒ = this.getVillagerProfession(â˜ƒ.get("VillagerProfession").asInt(-1));
            if (â˜ƒ == -1) {
               â˜ƒ = this.getVillagerProfession(RANDOM.nextInt(6));
            }

            â˜ƒ = â˜ƒ.set("ZombieType", â˜ƒ.createInt(â˜ƒ));
         }

         â˜ƒ = â˜ƒ.remove("IsVillager");
      }

      return â˜ƒ;
   }

   private int getVillagerProfession(int var1) {
      return â˜ƒ >= 0 && â˜ƒ < 6 ? â˜ƒ : -1;
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }
}
