package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class LanguageSelectScreen extends OptionsSubScreen {
   private static final Component WARNING_LABEL = new TextComponent("(")
      .append(new TranslatableComponent("options.languageWarning"))
      .append(")")
      .withStyle(ChatFormatting.GRAY);
   private LanguageSelectScreen.LanguageSelectionList packSelectionList;
   final LanguageManager languageManager;

   public LanguageSelectScreen(Screen var1, Options var2, LanguageManager var3) {
      super(â˜ƒ, â˜ƒ, new TranslatableComponent("options.language"));
      this.languageManager = â˜ƒ;
   }

   @Override
   protected void init() {
      this.packSelectionList = new LanguageSelectScreen.LanguageSelectionList(this.minecraft);
      this.addWidget(this.packSelectionList);
      this.addRenderableWidget(Option.FORCE_UNICODE_FONT.createButton(this.options, this.width / 2 - 155, this.height - 38, 150));
      this.addRenderableWidget(new Button(this.width / 2 - 155 + 160, this.height - 38, 150, 20, CommonComponents.GUI_DONE, var1 -> {
         LanguageSelectScreen.LanguageSelectionList.Entry â˜ƒ = this.packSelectionList.getSelected();
         if (â˜ƒ != null && !â˜ƒ.language.getCode().equals(this.languageManager.getSelected().getCode())) {
            this.languageManager.setSelected(â˜ƒ.language);
            this.options.languageCode = â˜ƒ.language.getCode();
            this.minecraft.reloadResourcePacks();
            this.options.save();
         }

         this.minecraft.setScreen(this.lastScreen);
      }));
      super.init();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.packSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 16, 16777215);
      drawCenteredString(â˜ƒ, this.font, WARNING_LABEL, this.width / 2, this.height - 56, 8421504);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   class LanguageSelectionList extends ObjectSelectionList<LanguageSelectScreen.LanguageSelectionList.Entry> {
      public LanguageSelectionList(Minecraft var2) {
         super(â˜ƒ, LanguageSelectScreen.this.width, LanguageSelectScreen.this.height, 32, LanguageSelectScreen.this.height - 65 + 4, 18);

         for(LanguageInfo â˜ƒ : LanguageSelectScreen.this.languageManager.getLanguages()) {
            LanguageSelectScreen.LanguageSelectionList.Entry â˜ƒx = new LanguageSelectScreen.LanguageSelectionList.Entry(â˜ƒ);
            this.addEntry(â˜ƒx);
            if (LanguageSelectScreen.this.languageManager.getSelected().getCode().equals(â˜ƒ.getCode())) {
               this.setSelected(â˜ƒx);
            }
         }

         if (this.getSelected() != null) {
            this.centerScrollOn(this.getSelected());
         }
      }

      @Override
      protected int getScrollbarPosition() {
         return super.getScrollbarPosition() + 20;
      }

      @Override
      public int getRowWidth() {
         return super.getRowWidth() + 50;
      }

      @Override
      protected void renderBackground(PoseStack var1) {
         LanguageSelectScreen.this.renderBackground(â˜ƒ);
      }

      @Override
      protected boolean isFocused() {
         return LanguageSelectScreen.this.getFocused() == this;
      }

      public class Entry extends ObjectSelectionList.Entry<LanguageSelectScreen.LanguageSelectionList.Entry> {
         final LanguageInfo language;

         public Entry(LanguageInfo var2) {
            this.language = â˜ƒ;
         }

         @Override
         public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            String â˜ƒ = this.language.toString();
            LanguageSelectScreen.this.font
               .drawShadow(
                  â˜ƒ, â˜ƒ, (float)(LanguageSelectionList.this.width / 2 - LanguageSelectScreen.this.font.width(â˜ƒ) / 2), (float)(â˜ƒ + 1), 16777215, true
               );
         }

         @Override
         public boolean mouseClicked(double var1, double var3, int var5) {
            if (â˜ƒ == 0) {
               this.select();
               return true;
            } else {
               return false;
            }
         }

         private void select() {
            LanguageSelectionList.this.setSelected(this);
         }

         @Override
         public Component getNarration() {
            return new TranslatableComponent("narrator.select", this.language);
         }
      }
   }
}
