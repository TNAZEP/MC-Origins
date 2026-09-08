package net.minecraft.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public enum EnumHandSide {
   LEFT(new TextComponentTranslation("options.mainHand.left")),
   RIGHT(new TextComponentTranslation("options.mainHand.right"));

   private final ITextComponent field_188471_c;

   private EnumHandSide(ITextComponent var3) {
      this.field_188471_c = ☃;
   }

   public EnumHandSide func_188468_a() {
      return this == LEFT ? RIGHT : LEFT;
   }

   public String toString() {
      return this.field_188471_c.getString();
   }
}
