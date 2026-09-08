package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class EntityShulkerColorFix extends NamedEntityFix {
   public EntityShulkerColorFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "EntityShulkerColorFix", References.ENTITY, "minecraft:shulker");
   }

   public Dynamic<?> fixTag(Dynamic<?> var1) {
      return !â˜ƒ.get("Color").map(Dynamic::asNumber).result().isPresent() ? â˜ƒ.set("Color", â˜ƒ.createByte((byte)10)) : â˜ƒ;
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), this::fixTag);
   }
}
