package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.TypeReferences;

public class ShulkerBoxEntityColor extends NamedEntityFix {
   public ShulkerBoxEntityColor(Schema var1, boolean var2) {
      super(☃, ☃, "EntityShulkerColorFix", TypeReferences.field_211299_o, "minecraft:shulker");
   }

   public Dynamic<?> func_209653_a(Dynamic<?> var1) {
      return !☃.get("Color").map(Dynamic::getNumberValue).isPresent() ? ☃.set("Color", ☃.createByte((byte)10)) : ☃;
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), this::func_209653_a);
   }
}
