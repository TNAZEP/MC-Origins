package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

public class AbstractArrowPickupFix extends DataFix {
   public AbstractArrowPickupFix(Schema var1) {
      super(â˜ƒ, false);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Schema â˜ƒ = this.getInputSchema();
      return this.fixTypeEverywhereTyped("AbstractArrowPickupFix", â˜ƒ.getType(References.ENTITY), this::updateProjectiles);
   }

   private Typed<?> updateProjectiles(Typed<?> var1) {
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:arrow", AbstractArrowPickupFix::updatePickup);
      â˜ƒ = this.updateEntity(â˜ƒ, "minecraft:spectral_arrow", AbstractArrowPickupFix::updatePickup);
      return this.updateEntity(â˜ƒ, "minecraft:trident", AbstractArrowPickupFix::updatePickup);
   }

   private static Dynamic<?> updatePickup(Dynamic<?> var0) {
      if (â˜ƒ.get("pickup").result().isPresent()) {
         return â˜ƒ;
      } else {
         boolean â˜ƒ = â˜ƒ.get("player").asBoolean(true);
         return â˜ƒ.set("pickup", â˜ƒ.createByte((byte)(â˜ƒ ? 1 : 0))).remove("player");
      }
   }

   private Typed<?> updateEntity(Typed<?> var1, String var2, Function<Dynamic<?>, Dynamic<?>> var3) {
      Type<?> â˜ƒ = this.getInputSchema().getChoiceType(References.ENTITY, â˜ƒ);
      Type<?> â˜ƒx = this.getOutputSchema().getChoiceType(References.ENTITY, â˜ƒ);
      return â˜ƒ.updateTyped(DSL.namedChoice(â˜ƒ, â˜ƒ), â˜ƒx, var1x -> var1x.update(DSL.remainderFinder(), â˜ƒ));
   }
}
