package net.minecraft.client.player.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public class LocalBlockIntercommunication implements IInteractionObject {
   private final String field_175126_a;
   private final ITextComponent field_175125_b;

   public LocalBlockIntercommunication(String var1, ITextComponent var2) {
      this.field_175126_a = ☃;
      this.field_175125_b = ☃;
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      throw new UnsupportedOperationException();
   }

   @Override
   public ITextComponent func_200200_C_() {
      return this.field_175125_b;
   }

   @Override
   public boolean func_145818_k_() {
      return false;
   }

   @Override
   public String func_174875_k() {
      return this.field_175126_a;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return this.field_175125_b;
   }
}
