package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class VillagerDataFix extends NamedEntityFix {
   public VillagerDataFix(Schema var1, String var2) {
      super(â˜ƒ, false, "Villager profession data fix (" + â˜ƒ + ")", References.ENTITY, â˜ƒ);
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      Dynamic<?> â˜ƒ = â˜ƒ.get(DSL.remainderFinder());
      return â˜ƒ.set(
         DSL.remainderFinder(),
         â˜ƒ.remove("Profession")
            .remove("Career")
            .remove("CareerLevel")
            .set(
               "VillagerData",
               â˜ƒ.createMap(
                  ImmutableMap.of(
                     â˜ƒ.createString("type"),
                     â˜ƒ.createString("minecraft:plains"),
                     â˜ƒ.createString("profession"),
                     â˜ƒ.createString(upgradeData(â˜ƒ.get("Profession").asInt(0), â˜ƒ.get("Career").asInt(0))),
                     â˜ƒ.createString("level"),
                     DataFixUtils.orElse(â˜ƒ.get("CareerLevel").result(), â˜ƒ.createInt(1))
                  )
               )
            )
      );
   }

   private static String upgradeData(int var0, int var1) {
      if (â˜ƒ == 0) {
         if (â˜ƒ == 2) {
            return "minecraft:fisherman";
         } else if (â˜ƒ == 3) {
            return "minecraft:shepherd";
         } else {
            return â˜ƒ == 4 ? "minecraft:fletcher" : "minecraft:farmer";
         }
      } else if (â˜ƒ == 1) {
         return â˜ƒ == 2 ? "minecraft:cartographer" : "minecraft:librarian";
      } else if (â˜ƒ == 2) {
         return "minecraft:cleric";
      } else if (â˜ƒ == 3) {
         if (â˜ƒ == 2) {
            return "minecraft:weaponsmith";
         } else {
            return â˜ƒ == 3 ? "minecraft:toolsmith" : "minecraft:armorer";
         }
      } else if (â˜ƒ == 4) {
         return â˜ƒ == 2 ? "minecraft:leatherworker" : "minecraft:butcher";
      } else {
         return â˜ƒ == 5 ? "minecraft:nitwit" : "minecraft:none";
      }
   }
}
