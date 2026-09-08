package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.TypeReferences;

public class ShulkerBoxTileColor extends NamedEntityFix {
   public ShulkerBoxTileColor(Schema var1, boolean var2) {
      super(☃, ☃, "BlockEntityShulkerBoxColorFix", TypeReferences.field_211294_j, "minecraft:shulker_box");
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), var0 -> var0.remove("Color"));
   }
}
