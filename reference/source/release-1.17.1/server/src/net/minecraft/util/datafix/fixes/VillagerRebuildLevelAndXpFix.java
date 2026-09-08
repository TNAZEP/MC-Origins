package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.Mth;

public class VillagerRebuildLevelAndXpFix extends DataFix {
   private static final int TRADES_PER_LEVEL = 2;
   private static final int[] LEVEL_XP_THRESHOLDS = new int[]{0, 10, 50, 100, 150};

   public static int getMinXpPerLevel(int var0) {
      return LEVEL_XP_THRESHOLDS[Mth.clamp(â˜ƒ - 1, 0, LEVEL_XP_THRESHOLDS.length - 1)];
   }

   public VillagerRebuildLevelAndXpFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getChoiceType(References.ENTITY, "minecraft:villager");
      OpticFinder<?> â˜ƒx = DSL.namedChoice("minecraft:villager", â˜ƒ);
      OpticFinder<?> â˜ƒxx = â˜ƒ.findField("Offers");
      Type<?> â˜ƒxxx = â˜ƒxx.type();
      OpticFinder<?> â˜ƒxxxx = â˜ƒxxx.findField("Recipes");
      ListType<?> â˜ƒxxxxx = (ListType)â˜ƒxxxx.type();
      OpticFinder<?> â˜ƒxxxxxx = â˜ƒxxxxx.getElement().finder();
      return this.fixTypeEverywhereTyped(
         "Villager level and xp rebuild", this.getInputSchema().getType(References.ENTITY), var5x -> var5x.updateTyped(â˜ƒ, â˜ƒ, var3x -> {
               Dynamic<?> â˜ƒ = var3x.get(DSL.remainderFinder());
               int â˜ƒx = â˜ƒ.get("VillagerData").get("level").asInt(0);
               Typed<?> â˜ƒxx = var3x;
               if (â˜ƒx == 0 || â˜ƒx == 1) {
                  int â˜ƒxxx = var3x.getOptionalTyped(â˜ƒ).flatMap(var1x -> var1x.getOptionalTyped(â˜ƒ)).map(var1x -> var1x.getAllTyped(â˜ƒ).size()).orElse(0);
                  â˜ƒx = Mth.clamp(â˜ƒxxx / 2, 1, 5);
                  if (â˜ƒx > 1) {
                     â˜ƒxx = addLevel(var3x, â˜ƒx);
                  }
               }
   
               Optional<Number> â˜ƒ = â˜ƒ.get("Xp").asNumber().result();
               if (!â˜ƒ.isPresent()) {
                  â˜ƒxx = addXpFromLevel(â˜ƒxx, â˜ƒx);
               }
   
               return â˜ƒxx;
            })
      );
   }

   private static Typed<?> addLevel(Typed<?> var0, int var1) {
      return â˜ƒ.update(DSL.remainderFinder(), var1x -> var1x.update("VillagerData", var1xx -> var1xx.set("level", var1xx.createInt(â˜ƒ))));
   }

   private static Typed<?> addXpFromLevel(Typed<?> var0, int var1) {
      int â˜ƒ = getMinXpPerLevel(â˜ƒ);
      return â˜ƒ.update(DSL.remainderFinder(), var1x -> var1x.set("Xp", var1x.createInt(â˜ƒ)));
   }
}
