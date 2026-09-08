package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class RedstoneWireConnectionsFix extends DataFix {
   public RedstoneWireConnectionsFix(Schema var1) {
      super(â˜ƒ, false);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Schema â˜ƒ = this.getInputSchema();
      return this.fixTypeEverywhereTyped(
         "RedstoneConnectionsFix", â˜ƒ.getType(References.BLOCK_STATE), var1x -> var1x.update(DSL.remainderFinder(), this::updateRedstoneConnections)
      );
   }

   private <T> Dynamic<T> updateRedstoneConnections(Dynamic<T> var1) {
      boolean â˜ƒ = â˜ƒ.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
      return !â˜ƒ
         ? â˜ƒ
         : â˜ƒ.update(
            "Properties",
            var0 -> {
               String â˜ƒ = var0.get("east").asString("none");
               String â˜ƒx = var0.get("west").asString("none");
               String â˜ƒxx = var0.get("north").asString("none");
               String â˜ƒxxx = var0.get("south").asString("none");
               boolean â˜ƒxxxx = isConnected(â˜ƒ) || isConnected(â˜ƒx);
               boolean â˜ƒxxxxx = isConnected(â˜ƒxx) || isConnected(â˜ƒxxx);
               String â˜ƒxxxxxx = !isConnected(â˜ƒ) && !â˜ƒxxxxx ? "side" : â˜ƒ;
               String â˜ƒxxxxxxx = !isConnected(â˜ƒx) && !â˜ƒxxxxx ? "side" : â˜ƒx;
               String â˜ƒxxxxxxxx = !isConnected(â˜ƒxx) && !â˜ƒxxxx ? "side" : â˜ƒxx;
               String â˜ƒxxxxxxxxx = !isConnected(â˜ƒxxx) && !â˜ƒxxxx ? "side" : â˜ƒxxx;
               return var0.update("east", var1x -> var1x.createString(â˜ƒ))
                  .update("west", var1x -> var1x.createString(â˜ƒ))
                  .update("north", var1x -> var1x.createString(â˜ƒ))
                  .update("south", var1x -> var1x.createString(â˜ƒ));
            }
         );
   }

   private static boolean isConnected(String var0) {
      return !"none".equals(â˜ƒ);
   }
}
