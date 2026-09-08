package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class LevelUUIDFix extends AbstractUUIDFix {
   public LevelUUIDFix(Schema var1) {
      super(â˜ƒ, References.LEVEL);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelUUIDFix",
         this.getInputSchema().getType(this.typeReference),
         var1 -> var1.updateTyped(DSL.remainderFinder(), var1x -> var1x.update(DSL.remainderFinder(), var1xx -> {
                  var1xx = this.updateCustomBossEvents(var1xx);
                  var1xx = this.updateDragonFight(var1xx);
                  return this.updateWanderingTrader(var1xx);
               }))
      );
   }

   private Dynamic<?> updateWanderingTrader(Dynamic<?> var1) {
      return (Dynamic<?>)replaceUUIDString(â˜ƒ, "WanderingTraderId", "WanderingTraderId").orElse(â˜ƒ);
   }

   private Dynamic<?> updateDragonFight(Dynamic<?> var1) {
      return â˜ƒ.update(
         "DimensionData",
         var0 -> var0.updateMapValues(
               var0x -> var0x.mapSecond(
                     var0xx -> var0xx.update("DragonFight", var0xxx -> (Dynamic)replaceUUIDLeastMost(var0xxx, "DragonUUID", "Dragon").orElse(var0xxx))
                  )
            )
      );
   }

   private Dynamic<?> updateCustomBossEvents(Dynamic<?> var1) {
      return â˜ƒ.update(
         "CustomBossEvents",
         var0 -> var0.updateMapValues(
               var0x -> var0x.mapSecond(
                     var0xx -> var0xx.update(
                           "Players", var1x -> var0xx.createList(var1x.asStream().map(var0xxx -> (Dynamic)createUUIDFromML(var0xxx).orElseGet(() -> {
                                    LOGGER.warn("CustomBossEvents contains invalid UUIDs.");
                                    return var0xxx;
                                 })))
                        )
                  )
            )
      );
   }
}
