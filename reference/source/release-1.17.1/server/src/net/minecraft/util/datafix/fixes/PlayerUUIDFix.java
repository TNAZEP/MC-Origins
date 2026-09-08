package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class PlayerUUIDFix extends AbstractUUIDFix {
   public PlayerUUIDFix(Schema var1) {
      super(â˜ƒ, References.PLAYER);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "PlayerUUIDFix",
         this.getInputSchema().getType(this.typeReference),
         var0 -> {
            OpticFinder<?> â˜ƒ = var0.getType().findField("RootVehicle");
            return var0.updateTyped(
                  â˜ƒ,
                  â˜ƒ.type(),
                  var0x -> var0x.update(DSL.remainderFinder(), var0xx -> (Dynamic)replaceUUIDLeastMost(var0xx, "Attach", "Attach").orElse(var0xx))
               )
               .update(DSL.remainderFinder(), var0x -> EntityUUIDFix.updateEntityUUID(EntityUUIDFix.updateLivingEntity(var0x)));
         }
      );
   }
}
