package net.minecraft.client.gui.screens.controls;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.commons.lang3.ArrayUtils;

public class ControlList extends ContainerObjectSelectionList<ControlList.Entry> {
   final ControlsScreen controlsScreen;
   int maxNameWidth;

   public ControlList(ControlsScreen var1, Minecraft var2) {
      super(â˜ƒ, â˜ƒ.width + 45, â˜ƒ.height, 43, â˜ƒ.height - 32, 20);
      this.controlsScreen = â˜ƒ;
      KeyMapping[] â˜ƒ = ArrayUtils.clone(â˜ƒ.options.keyMappings);
      Arrays.sort(â˜ƒ);
      String â˜ƒx = null;

      for(KeyMapping â˜ƒxx : â˜ƒ) {
         String â˜ƒxxx = â˜ƒxx.getCategory();
         if (!â˜ƒxxx.equals(â˜ƒx)) {
            â˜ƒx = â˜ƒxxx;
            this.addEntry(new ControlList.CategoryEntry(new TranslatableComponent(â˜ƒxxx)));
         }

         Component â˜ƒxxx = new TranslatableComponent(â˜ƒxx.getName());
         int â˜ƒxxxx = â˜ƒ.font.width(â˜ƒxxx);
         if (â˜ƒxxxx > this.maxNameWidth) {
            this.maxNameWidth = â˜ƒxxxx;
         }

         this.addEntry(new ControlList.KeyEntry(â˜ƒxx, â˜ƒxxx));
      }
   }

   @Override
   protected int getScrollbarPosition() {
      return super.getScrollbarPosition() + 15;
   }

   @Override
   public int getRowWidth() {
      return super.getRowWidth() + 32;
   }

   public class CategoryEntry extends ControlList.Entry {
      final Component name;
      private final int width;

      public CategoryEntry(Component var2) {
         this.name = â˜ƒ;
         this.width = ControlList.this.minecraft.font.width(this.name);
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         ControlList.this.minecraft
            .font
            .draw(â˜ƒ, this.name, (float)(ControlList.this.minecraft.screen.width / 2 - this.width / 2), (float)(â˜ƒ + â˜ƒ - 9 - 1), 16777215);
      }

      @Override
      public boolean changeFocus(boolean var1) {
         return false;
      }

      @Override
      public List<? extends GuiEventListener> children() {
         return Collections.emptyList();
      }

      @Override
      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of(new NarratableEntry() {
            @Override
            public NarratableEntry.NarrationPriority narrationPriority() {
               return NarratableEntry.NarrationPriority.HOVERED;
            }

            @Override
            public void updateNarration(NarrationElementOutput var1) {
               â˜ƒ.add(NarratedElementType.TITLE, CategoryEntry.this.name);
            }
         });
      }
   }

   public abstract static class Entry extends ContainerObjectSelectionList.Entry<ControlList.Entry> {
   }

   public class KeyEntry extends ControlList.Entry {
      private final KeyMapping key;
      private final Component name;
      private final Button changeButton;
      private final Button resetButton;

      KeyEntry(final KeyMapping var2, final Component var3) {
         this.key = â˜ƒ;
         this.name = â˜ƒ;
         this.changeButton = new Button(0, 0, 75, 20, â˜ƒ, var2x -> ControlList.this.controlsScreen.selectedKey = â˜ƒ) {
            @Override
            protected MutableComponent createNarrationMessage() {
               return â˜ƒ.isUnbound()
                  ? new TranslatableComponent("narrator.controls.unbound", â˜ƒ)
                  : new TranslatableComponent("narrator.controls.bound", â˜ƒ, super.createNarrationMessage());
            }
         };
         this.resetButton = new Button(0, 0, 50, 20, new TranslatableComponent("controls.reset"), var2x -> {
            ControlList.this.minecraft.options.setKey(â˜ƒ, â˜ƒ.getDefaultKey());
            KeyMapping.resetMapping();
         }) {
            @Override
            protected MutableComponent createNarrationMessage() {
               return new TranslatableComponent("narrator.controls.reset", â˜ƒ);
            }
         };
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         boolean â˜ƒ = ControlList.this.controlsScreen.selectedKey == this.key;
         float var10003 = (float)(â˜ƒ + 90 - ControlList.this.maxNameWidth);
         ControlList.this.minecraft.font.draw(â˜ƒ, this.name, var10003, (float)(â˜ƒ + â˜ƒ / 2 - 9 / 2), 16777215);
         this.resetButton.x = â˜ƒ + 190;
         this.resetButton.y = â˜ƒ;
         this.resetButton.active = !this.key.isDefault();
         this.resetButton.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.changeButton.x = â˜ƒ + 105;
         this.changeButton.y = â˜ƒ;
         this.changeButton.setMessage(this.key.getTranslatedKeyMessage());
         boolean â˜ƒx = false;
         if (!this.key.isUnbound()) {
            for(KeyMapping â˜ƒxx : ControlList.this.minecraft.options.keyMappings) {
               if (â˜ƒxx != this.key && this.key.same(â˜ƒxx)) {
                  â˜ƒx = true;
                  break;
               }
            }
         }

         if (â˜ƒ) {
            this.changeButton
               .setMessage(
                  new TextComponent("> ")
                     .append(this.changeButton.getMessage().copy().withStyle(ChatFormatting.YELLOW))
                     .append(" <")
                     .withStyle(ChatFormatting.YELLOW)
               );
         } else if (â˜ƒx) {
            this.changeButton.setMessage(this.changeButton.getMessage().copy().withStyle(ChatFormatting.RED));
         }

         this.changeButton.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public List<? extends GuiEventListener> children() {
         return ImmutableList.of(this.changeButton, this.resetButton);
      }

      @Override
      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of(this.changeButton, this.resetButton);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (this.changeButton.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
            return true;
         } else {
            return this.resetButton.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Override
      public boolean mouseReleased(double var1, double var3, int var5) {
         return this.changeButton.mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ) || this.resetButton.mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
