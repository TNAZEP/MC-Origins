package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ColorlessShulkerEntityFix extends NamedEntityFix {
   public ColorlessShulkerEntityFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "Colorless shulker entity fix", References.ENTITY, "minecraft:shulker");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), var0 -> var0.get("Color").asInt(0) == 10 ? var0.set("Color", var0.createByte((byte)16)) : var0);
   }
}
