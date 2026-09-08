package net.minecraft.tileentity;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityDropper extends TileEntityDispenser {
   public TileEntityDropper() {
      super(TileEntityType.field_200977_h);
   }

   @Override
   public ITextComponent func_200200_C_() {
      ITextComponent ☃ = this.func_200201_e();
      return (ITextComponent)(☃ != null ? ☃ : new TextComponentTranslation("container.dropper"));
   }

   @Override
   public String func_174875_k() {
      return "minecraft:dropper";
   }
}
