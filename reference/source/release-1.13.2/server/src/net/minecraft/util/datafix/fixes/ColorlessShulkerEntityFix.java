package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.TypeReferences;

public class ColorlessShulkerEntityFix extends NamedEntityFix {
   public ColorlessShulkerEntityFix(Schema var1, boolean var2) {
      super(☃, ☃, "Colorless shulker entity fix", TypeReferences.field_211299_o, "minecraft:shulker");
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), var0 -> var0.getByte("Color") == 10 ? var0.set("Color", var0.createByte((byte)16)) : var0);
   }
}
