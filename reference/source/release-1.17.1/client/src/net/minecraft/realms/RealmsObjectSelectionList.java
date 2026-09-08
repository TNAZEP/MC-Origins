package net.minecraft.realms;

import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

public abstract class RealmsObjectSelectionList<E extends ObjectSelectionList.Entry<E>> extends ObjectSelectionList<E> {
   protected RealmsObjectSelectionList(int var1, int var2, int var3, int var4, int var5) {
      super(Minecraft.getInstance(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setSelectedItem(int var1) {
      if (â˜ƒ == -1) {
         this.setSelected((E)null);
      } else if (super.getItemCount() != 0) {
         this.setSelected(this.getEntry(â˜ƒ));
      }
   }

   public void selectItem(int var1) {
      this.setSelectedItem(â˜ƒ);
   }

   public void itemClicked(int var1, int var2, double var3, double var5, int var7) {
   }

   @Override
   public int getMaxPosition() {
      return 0;
   }

   @Override
   public int getScrollbarPosition() {
      return this.getRowLeft() + this.getRowWidth();
   }

   @Override
   public int getRowWidth() {
      return (int)((double)this.width * 0.6);
   }

   @Override
   public void replaceEntries(Collection<E> var1) {
      super.replaceEntries(â˜ƒ);
   }

   @Override
   public int getItemCount() {
      return super.getItemCount();
   }

   @Override
   public int getRowTop(int var1) {
      return super.getRowTop(â˜ƒ);
   }

   @Override
   public int getRowLeft() {
      return super.getRowLeft();
   }

   public int addEntry(E var1) {
      return super.addEntry(â˜ƒ);
   }

   public void clear() {
      this.clearEntries();
   }
}
