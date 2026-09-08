package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class EntityArmorStandSilentFix extends NamedEntityFix {
   public EntityArmorStandSilentFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "EntityArmorStandSilentFix", References.ENTITY, "ArmorStand");
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      return â˜ƒ.get("Silent").asBoolean(false) && !â˜ƒ.get("Marker").asBoolean(false) ? â˜ƒ.remove("Silent") : â˜ƒ;
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }
}
