package net.minecraft.client.gui.components;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class ContainerObjectSelectionList<E extends ContainerObjectSelectionList.Entry<E>> extends AbstractSelectionList<E> {
   private boolean hasFocus;

   public ContainerObjectSelectionList(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean changeFocus(boolean var1) {
      this.hasFocus = super.changeFocus(â˜ƒ);
      if (this.hasFocus) {
         this.ensureVisible(this.getFocused());
      }

      return this.hasFocus;
   }

   @Override
   public NarratableEntry.NarrationPriority narrationPriority() {
      return this.hasFocus ? NarratableEntry.NarrationPriority.FOCUSED : super.narrationPriority();
   }

   @Override
   protected boolean isSelectedItem(int var1) {
      return false;
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      E â˜ƒ = this.getHovered();
      if (â˜ƒ != null) {
         â˜ƒ.updateNarration(â˜ƒ.nest());
         this.narrateListElementPosition(â˜ƒ, â˜ƒ);
      } else {
         E â˜ƒ = this.getFocused();
         if (â˜ƒ != null) {
            â˜ƒ.updateNarration(â˜ƒ.nest());
            this.narrateListElementPosition(â˜ƒ, â˜ƒ);
         }
      }

      â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.component_list.usage"));
   }

   public abstract static class Entry<E extends ContainerObjectSelectionList.Entry<E>> extends AbstractSelectionList.Entry<E> implements ContainerEventHandler {
      @Nullable
      private GuiEventListener focused;
      @Nullable
      private NarratableEntry lastNarratable;
      private boolean dragging;

      @Override
      public boolean isDragging() {
         return this.dragging;
      }

      @Override
      public void setDragging(boolean var1) {
         this.dragging = â˜ƒ;
      }

      @Override
      public void setFocused(@Nullable GuiEventListener var1) {
         this.focused = â˜ƒ;
      }

      @Nullable
      @Override
      public GuiEventListener getFocused() {
         return this.focused;
      }

      public abstract List<? extends NarratableEntry> narratables();

      void updateNarration(NarrationElementOutput var1) {
         List<? extends NarratableEntry> â˜ƒ = this.narratables();
         Screen.NarratableSearchResult â˜ƒx = Screen.findNarratableWidget(â˜ƒ, this.lastNarratable);
         if (â˜ƒx != null) {
            if (â˜ƒx.priority.isTerminal()) {
               this.lastNarratable = â˜ƒx.entry;
            }

            if (â˜ƒ.size() > 1) {
               â˜ƒ.add(NarratedElementType.POSITION, new TranslatableComponent("narrator.position.object_list", â˜ƒx.index + 1, â˜ƒ.size()));
               if (â˜ƒx.priority == NarratableEntry.NarrationPriority.FOCUSED) {
                  â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.component_list.usage"));
               }
            }

            â˜ƒx.entry.updateNarration(â˜ƒ.nest());
         }
      }
   }
}
