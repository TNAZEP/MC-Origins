package net.minecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarrationSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class ObjectSelectionList<E extends ObjectSelectionList.Entry<E>> extends AbstractSelectionList<E> {
   private static final Component USAGE_NARRATION = new TranslatableComponent("narration.selection.usage");
   private boolean inFocus;

   public ObjectSelectionList(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean changeFocus(boolean var1) {
      if (!this.inFocus && this.getItemCount() == 0) {
         return false;
      } else {
         this.inFocus = !this.inFocus;
         if (this.inFocus && this.getSelected() == null && this.getItemCount() > 0) {
            this.moveSelection(AbstractSelectionList.SelectionDirection.DOWN);
         } else if (this.inFocus && this.getSelected() != null) {
            this.refreshSelection();
         }

         return this.inFocus;
      }
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      E â˜ƒ = this.getHovered();
      if (â˜ƒ != null) {
         this.narrateListElementPosition(â˜ƒ.nest(), â˜ƒ);
         â˜ƒ.updateNarration(â˜ƒ);
      } else {
         E â˜ƒ = this.getSelected();
         if (â˜ƒ != null) {
            this.narrateListElementPosition(â˜ƒ.nest(), â˜ƒ);
            â˜ƒ.updateNarration(â˜ƒ);
         }
      }

      if (this.isFocused()) {
         â˜ƒ.add(NarratedElementType.USAGE, USAGE_NARRATION);
      }
   }

   public abstract static class Entry<E extends ObjectSelectionList.Entry<E>> extends AbstractSelectionList.Entry<E> implements NarrationSupplier {
      @Override
      public boolean changeFocus(boolean var1) {
         return false;
      }

      public abstract Component getNarration();

      @Override
      public void updateNarration(NarrationElementOutput var1) {
         â˜ƒ.add(NarratedElementType.TITLE, this.getNarration());
      }
   }
}
