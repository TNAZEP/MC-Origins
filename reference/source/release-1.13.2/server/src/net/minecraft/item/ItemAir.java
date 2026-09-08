package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemAir extends Item {
   private final Block field_190904_a;

   public ItemAir(Block var1, Item.Properties var2) {
      super(☃);
      this.field_190904_a = ☃;
   }

   @Override
   public String func_77658_a() {
      return this.field_190904_a.func_149739_a();
   }
}
