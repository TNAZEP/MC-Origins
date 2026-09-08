package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class ChatScreen extends Screen {
   public static final int MOUSE_SCROLL_SPEED = 7;
   private static final Component USAGE_TEXT = new TranslatableComponent("chat_screen.usage");
   private String historyBuffer = "";
   private int historyPos = -1;
   protected EditBox input;
   private final String initial;
   CommandSuggestions commandSuggestions;

   public ChatScreen(String var1) {
      super(new TranslatableComponent("chat_screen.title"));
      this.initial = â˜ƒ;
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.historyPos = this.minecraft.gui.getChat().getRecentChat().size();
      this.input = new EditBox(this.font, 4, this.height - 12, this.width - 4, 12, new TranslatableComponent("chat.editBox")) {
         @Override
         protected MutableComponent createNarrationMessage() {
            return super.createNarrationMessage().append(ChatScreen.this.commandSuggestions.getNarrationMessage());
         }
      };
      this.input.setMaxLength(256);
      this.input.setBordered(false);
      this.input.setValue(this.initial);
      this.input.setResponder(this::onEdited);
      this.addWidget(this.input);
      this.commandSuggestions = new CommandSuggestions(this.minecraft, this, this.input, this.font, false, false, 1, 10, true, -805306368);
      this.commandSuggestions.updateCommandInfo();
      this.setInitialFocus(this.input);
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.input.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setChatLine(â˜ƒ);
      this.commandSuggestions.updateCommandInfo();
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
      this.minecraft.gui.getChat().resetChatScroll();
   }

   @Override
   public void tick() {
      this.input.tick();
   }

   private void onEdited(String var1) {
      String â˜ƒ = this.input.getValue();
      this.commandSuggestions.setAllowSuggestions(!â˜ƒ.equals(this.initial));
      this.commandSuggestions.updateCommandInfo();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.commandSuggestions.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (â˜ƒ == 256) {
         this.minecraft.setScreen(null);
         return true;
      } else if (â˜ƒ == 257 || â˜ƒ == 335) {
         String â˜ƒ = this.input.getValue().trim();
         if (!â˜ƒ.isEmpty()) {
            this.sendMessage(â˜ƒ);
         }

         this.minecraft.setScreen(null);
         return true;
      } else if (â˜ƒ == 265) {
         this.moveInHistory(-1);
         return true;
      } else if (â˜ƒ == 264) {
         this.moveInHistory(1);
         return true;
      } else if (â˜ƒ == 266) {
         this.minecraft.gui.getChat().scrollChat((double)(this.minecraft.gui.getChat().getLinesPerPage() - 1));
         return true;
      } else if (â˜ƒ == 267) {
         this.minecraft.gui.getChat().scrollChat((double)(-this.minecraft.gui.getChat().getLinesPerPage() + 1));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      if (â˜ƒ > 1.0) {
         â˜ƒ = 1.0;
      }

      if (â˜ƒ < -1.0) {
         â˜ƒ = -1.0;
      }

      if (this.commandSuggestions.mouseScrolled(â˜ƒ)) {
         return true;
      } else {
         if (!hasShiftDown()) {
            â˜ƒ *= 7.0;
         }

         this.minecraft.gui.getChat().scrollChat(â˜ƒ);
         return true;
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.commandSuggestions.mouseClicked((double)((int)â˜ƒ), (double)((int)â˜ƒ), â˜ƒ)) {
         return true;
      } else {
         if (â˜ƒ == 0) {
            ChatComponent â˜ƒ = this.minecraft.gui.getChat();
            if (â˜ƒ.handleChatQueueClicked(â˜ƒ, â˜ƒ)) {
               return true;
            }

            Style â˜ƒ = â˜ƒ.getClickedComponentStyleAt(â˜ƒ, â˜ƒ);
            if (â˜ƒ != null && this.handleComponentClicked(â˜ƒ)) {
               return true;
            }
         }

         return this.input.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ) ? true : super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void insertText(String var1, boolean var2) {
      if (â˜ƒ) {
         this.input.setValue(â˜ƒ);
      } else {
         this.input.insertText(â˜ƒ);
      }
   }

   public void moveInHistory(int var1) {
      int â˜ƒ = this.historyPos + â˜ƒ;
      int â˜ƒx = this.minecraft.gui.getChat().getRecentChat().size();
      â˜ƒ = Mth.clamp(â˜ƒ, 0, â˜ƒx);
      if (â˜ƒ != this.historyPos) {
         if (â˜ƒ == â˜ƒx) {
            this.historyPos = â˜ƒx;
            this.input.setValue(this.historyBuffer);
         } else {
            if (this.historyPos == â˜ƒx) {
               this.historyBuffer = this.input.getValue();
            }

            this.input.setValue((String)this.minecraft.gui.getChat().getRecentChat().get(â˜ƒ));
            this.commandSuggestions.setAllowSuggestions(false);
            this.historyPos = â˜ƒ;
         }
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.setFocused(this.input);
      this.input.setFocus(true);
      fill(â˜ƒ, 2, this.height - 14, this.width - 2, this.height - 2, this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE));
      this.input.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.commandSuggestions.render(â˜ƒ, â˜ƒ, â˜ƒ);
      Style â˜ƒ = this.minecraft.gui.getChat().getClickedComponentStyleAt((double)â˜ƒ, (double)â˜ƒ);
      if (â˜ƒ != null && â˜ƒ.getHoverEvent() != null) {
         this.renderComponentHoverEffect(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   private void setChatLine(String var1) {
      this.input.setValue(â˜ƒ);
   }

   @Override
   protected void updateNarrationState(NarrationElementOutput var1) {
      â˜ƒ.add(NarratedElementType.TITLE, this.getTitle());
      â˜ƒ.add(NarratedElementType.USAGE, USAGE_TEXT);
      String â˜ƒ = this.input.getValue();
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.nest().add(NarratedElementType.TITLE, new TranslatableComponent("chat_screen.message", â˜ƒ));
      }
   }
}
