package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.ChatVisiblity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatComponent extends GuiComponent {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int MAX_CHAT_HISTORY = 100;
   private final Minecraft minecraft;
   private final List<String> recentChat = Lists.newArrayList();
   private final List<GuiMessage<Component>> allMessages = Lists.<GuiMessage<Component>>newArrayList();
   private final List<GuiMessage<FormattedCharSequence>> trimmedMessages = Lists.<GuiMessage<FormattedCharSequence>>newArrayList();
   private final Deque<Component> chatQueue = Queues.<Component>newArrayDeque();
   private int chatScrollbarPos;
   private boolean newMessageSinceScroll;
   private long lastMessage;

   public ChatComponent(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void render(PoseStack var1, int var2) {
      if (!this.isChatHidden()) {
         this.processPendingMessages();
         int â˜ƒ = this.getLinesPerPage();
         int â˜ƒx = this.trimmedMessages.size();
         if (â˜ƒx > 0) {
            boolean â˜ƒxx = false;
            if (this.isChatFocused()) {
               â˜ƒxx = true;
            }

            float â˜ƒxx = (float)this.getScale();
            int â˜ƒxxx = Mth.ceil((float)this.getWidth() / â˜ƒxx);
            â˜ƒ.pushPose();
            â˜ƒ.translate(4.0, 8.0, 0.0);
            â˜ƒ.scale(â˜ƒxx, â˜ƒxx, 1.0F);
            double â˜ƒxxxx = this.minecraft.options.chatOpacity * 0.9F + 0.1F;
            double â˜ƒxxxxx = this.minecraft.options.textBackgroundOpacity;
            double â˜ƒxxxxxx = 9.0 * (this.minecraft.options.chatLineSpacing + 1.0);
            double â˜ƒxxxxxxx = -8.0 * (this.minecraft.options.chatLineSpacing + 1.0) + 4.0 * this.minecraft.options.chatLineSpacing;
            int â˜ƒxxxxxxxx = 0;

            for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx + this.chatScrollbarPos < this.trimmedMessages.size() && â˜ƒxxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxxx) {
               GuiMessage<FormattedCharSequence> â˜ƒxxxxxxxxxx = (GuiMessage)this.trimmedMessages.get(â˜ƒxxxxxxxxx + this.chatScrollbarPos);
               if (â˜ƒxxxxxxxxxx != null) {
                  int â˜ƒxxxxxxxxxxx = â˜ƒ - â˜ƒxxxxxxxxxx.getAddedTime();
                  if (â˜ƒxxxxxxxxxxx < 200 || â˜ƒxx) {
                     double â˜ƒxxxxxxxxxxxx = â˜ƒxx ? 1.0 : getTimeFactor(â˜ƒxxxxxxxxxxx);
                     int â˜ƒxxxxxxxxxxxxx = (int)(255.0 * â˜ƒxxxxxxxxxxxx * â˜ƒxxxx);
                     int â˜ƒxxxxxxxxxxxxxx = (int)(255.0 * â˜ƒxxxxxxxxxxxx * â˜ƒxxxxx);
                     ++â˜ƒxxxxxxxx;
                     if (â˜ƒxxxxxxxxxxxxx > 3) {
                        int â˜ƒxxxxxxxxxxxxxxx = 0;
                        double â˜ƒxxxxxxxxxxxxxxxx = (double)(-â˜ƒxxxxxxxxx) * â˜ƒxxxxxx;
                        â˜ƒ.pushPose();
                        â˜ƒ.translate(0.0, 0.0, 50.0);
                        fill(â˜ƒ, -4, (int)(â˜ƒxxxxxxxxxxxxxxxx - â˜ƒxxxxxx), 0 + â˜ƒxxx + 4, (int)â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx << 24);
                        RenderSystem.enableBlend();
                        â˜ƒ.translate(0.0, 0.0, 50.0);
                        this.minecraft
                           .font
                           .drawShadow(
                              â˜ƒ, â˜ƒxxxxxxxxxx.getMessage(), 0.0F, (float)((int)(â˜ƒxxxxxxxxxxxxxxxx + â˜ƒxxxxxxx)), 16777215 + (â˜ƒxxxxxxxxxxxxx << 24)
                           );
                        RenderSystem.disableBlend();
                        â˜ƒ.popPose();
                     }
                  }
               }
            }

            if (!this.chatQueue.isEmpty()) {
               int â˜ƒxxxxxxxxx = (int)(128.0 * â˜ƒxxxx);
               int â˜ƒxxxxxxxxxx = (int)(255.0 * â˜ƒxxxxx);
               â˜ƒ.pushPose();
               â˜ƒ.translate(0.0, 0.0, 50.0);
               fill(â˜ƒ, -2, 0, â˜ƒxxx + 4, 9, â˜ƒxxxxxxxxxx << 24);
               RenderSystem.enableBlend();
               â˜ƒ.translate(0.0, 0.0, 50.0);
               this.minecraft.font.drawShadow(â˜ƒ, new TranslatableComponent("chat.queue", this.chatQueue.size()), 0.0F, 1.0F, 16777215 + (â˜ƒxxxxxxxxx << 24));
               â˜ƒ.popPose();
               RenderSystem.disableBlend();
            }

            if (â˜ƒxx) {
               int â˜ƒxxxxxxxxx = 9;
               int â˜ƒxxxxxxxxxx = â˜ƒx * â˜ƒxxxxxxxxx;
               int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx * â˜ƒxxxxxxxxx;
               int â˜ƒxxxxxxxxxxxx = this.chatScrollbarPos * â˜ƒxxxxxxxxxxx / â˜ƒx;
               int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx / â˜ƒxxxxxxxxxx;
               if (â˜ƒxxxxxxxxxx != â˜ƒxxxxxxxxxxx) {
                  int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx > 0 ? 170 : 96;
                  int â˜ƒxxxxxxxxxxxxxxx = this.newMessageSinceScroll ? 13382451 : 3355562;
                  â˜ƒ.translate(-4.0, 0.0, 0.0);
                  fill(â˜ƒ, 0, -â˜ƒxxxxxxxxxxxx, 2, -â˜ƒxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx + (â˜ƒxxxxxxxxxxxxxx << 24));
                  fill(â˜ƒ, 2, -â˜ƒxxxxxxxxxxxx, 1, -â˜ƒxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxx, 13421772 + (â˜ƒxxxxxxxxxxxxxx << 24));
               }
            }

            â˜ƒ.popPose();
         }
      }
   }

   private boolean isChatHidden() {
      return this.minecraft.options.chatVisibility == ChatVisiblity.HIDDEN;
   }

   private static double getTimeFactor(int var0) {
      double â˜ƒ = (double)â˜ƒ / 200.0;
      â˜ƒ = 1.0 - â˜ƒ;
      â˜ƒ *= 10.0;
      â˜ƒ = Mth.clamp(â˜ƒ, 0.0, 1.0);
      return â˜ƒ * â˜ƒ;
   }

   public void clearMessages(boolean var1) {
      this.chatQueue.clear();
      this.trimmedMessages.clear();
      this.allMessages.clear();
      if (â˜ƒ) {
         this.recentChat.clear();
      }
   }

   public void addMessage(Component var1) {
      this.addMessage(â˜ƒ, 0);
   }

   private void addMessage(Component var1, int var2) {
      this.addMessage(â˜ƒ, â˜ƒ, this.minecraft.gui.getGuiTicks(), false);
      LOGGER.info("[CHAT] {}", â˜ƒ.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
   }

   private void addMessage(Component var1, int var2, int var3, boolean var4) {
      if (â˜ƒ != 0) {
         this.removeById(â˜ƒ);
      }

      int â˜ƒ = Mth.floor((double)this.getWidth() / this.getScale());
      List<FormattedCharSequence> â˜ƒx = ComponentRenderUtils.wrapComponents(â˜ƒ, â˜ƒ, this.minecraft.font);
      boolean â˜ƒxx = this.isChatFocused();

      for(FormattedCharSequence â˜ƒxxx : â˜ƒx) {
         if (â˜ƒxx && this.chatScrollbarPos > 0) {
            this.newMessageSinceScroll = true;
            this.scrollChat(1.0);
         }

         this.trimmedMessages.add(0, new GuiMessage<>(â˜ƒ, â˜ƒxxx, â˜ƒ));
      }

      while(this.trimmedMessages.size() > 100) {
         this.trimmedMessages.remove(this.trimmedMessages.size() - 1);
      }

      if (!â˜ƒ) {
         this.allMessages.add(0, new GuiMessage<>(â˜ƒ, â˜ƒ, â˜ƒ));

         while(this.allMessages.size() > 100) {
            this.allMessages.remove(this.allMessages.size() - 1);
         }
      }
   }

   public void rescaleChat() {
      this.trimmedMessages.clear();
      this.resetChatScroll();

      for(int â˜ƒ = this.allMessages.size() - 1; â˜ƒ >= 0; --â˜ƒ) {
         GuiMessage<Component> â˜ƒx = (GuiMessage)this.allMessages.get(â˜ƒ);
         this.addMessage(â˜ƒx.getMessage(), â˜ƒx.getId(), â˜ƒx.getAddedTime(), true);
      }
   }

   public List<String> getRecentChat() {
      return this.recentChat;
   }

   public void addRecentChat(String var1) {
      if (this.recentChat.isEmpty() || !((String)this.recentChat.get(this.recentChat.size() - 1)).equals(â˜ƒ)) {
         this.recentChat.add(â˜ƒ);
      }
   }

   public void resetChatScroll() {
      this.chatScrollbarPos = 0;
      this.newMessageSinceScroll = false;
   }

   public void scrollChat(double var1) {
      this.chatScrollbarPos = (int)((double)this.chatScrollbarPos + â˜ƒ);
      int â˜ƒ = this.trimmedMessages.size();
      if (this.chatScrollbarPos > â˜ƒ - this.getLinesPerPage()) {
         this.chatScrollbarPos = â˜ƒ - this.getLinesPerPage();
      }

      if (this.chatScrollbarPos <= 0) {
         this.chatScrollbarPos = 0;
         this.newMessageSinceScroll = false;
      }
   }

   public boolean handleChatQueueClicked(double var1, double var3) {
      if (this.isChatFocused() && !this.minecraft.options.hideGui && !this.isChatHidden() && !this.chatQueue.isEmpty()) {
         double â˜ƒ = â˜ƒ - 2.0;
         double â˜ƒx = (double)this.minecraft.getWindow().getGuiScaledHeight() - â˜ƒ - 40.0;
         if (â˜ƒ <= (double)Mth.floor((double)this.getWidth() / this.getScale()) && â˜ƒx < 0.0 && â˜ƒx > (double)Mth.floor(-9.0 * this.getScale())) {
            this.addMessage((Component)this.chatQueue.remove());
            this.lastMessage = System.currentTimeMillis();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   public Style getClickedComponentStyleAt(double var1, double var3) {
      if (this.isChatFocused() && !this.minecraft.options.hideGui && !this.isChatHidden()) {
         double â˜ƒ = â˜ƒ - 2.0;
         double â˜ƒx = (double)this.minecraft.getWindow().getGuiScaledHeight() - â˜ƒ - 40.0;
         â˜ƒ = (double)Mth.floor(â˜ƒ / this.getScale());
         â˜ƒx = (double)Mth.floor(â˜ƒx / (this.getScale() * (this.minecraft.options.chatLineSpacing + 1.0)));
         if (!(â˜ƒ < 0.0) && !(â˜ƒx < 0.0)) {
            int â˜ƒxx = Math.min(this.getLinesPerPage(), this.trimmedMessages.size());
            if (â˜ƒ <= (double)Mth.floor((double)this.getWidth() / this.getScale()) && â˜ƒx < (double)(9 * â˜ƒxx + â˜ƒxx)) {
               int â˜ƒxxx = (int)(â˜ƒx / 9.0 + (double)this.chatScrollbarPos);
               if (â˜ƒxxx >= 0 && â˜ƒxxx < this.trimmedMessages.size()) {
                  GuiMessage<FormattedCharSequence> â˜ƒxxxx = (GuiMessage)this.trimmedMessages.get(â˜ƒxxx);
                  return this.minecraft.font.getSplitter().componentStyleAtWidth(â˜ƒxxxx.getMessage(), (int)â˜ƒ);
               }
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean isChatFocused() {
      return this.minecraft.screen instanceof ChatScreen;
   }

   private void removeById(int var1) {
      this.trimmedMessages.removeIf(var1x -> var1x.getId() == â˜ƒ);
      this.allMessages.removeIf(var1x -> var1x.getId() == â˜ƒ);
   }

   public int getWidth() {
      return getWidth(this.minecraft.options.chatWidth);
   }

   public int getHeight() {
      return getHeight(
         (this.isChatFocused() ? this.minecraft.options.chatHeightFocused : this.minecraft.options.chatHeightUnfocused)
            / (this.minecraft.options.chatLineSpacing + 1.0)
      );
   }

   public double getScale() {
      return this.minecraft.options.chatScale;
   }

   public static int getWidth(double var0) {
      int â˜ƒ = 320;
      int â˜ƒx = 40;
      return Mth.floor(â˜ƒ * 280.0 + 40.0);
   }

   public static int getHeight(double var0) {
      int â˜ƒ = 180;
      int â˜ƒx = 20;
      return Mth.floor(â˜ƒ * 160.0 + 20.0);
   }

   public int getLinesPerPage() {
      return this.getHeight() / 9;
   }

   private long getChatRateMillis() {
      return (long)(this.minecraft.options.chatDelay * 1000.0);
   }

   private void processPendingMessages() {
      if (!this.chatQueue.isEmpty()) {
         long â˜ƒ = System.currentTimeMillis();
         if (â˜ƒ - this.lastMessage >= this.getChatRateMillis()) {
            this.addMessage((Component)this.chatQueue.remove());
            this.lastMessage = â˜ƒ;
         }
      }
   }

   public void enqueueMessage(Component var1) {
      if (this.minecraft.options.chatDelay <= 0.0) {
         this.addMessage(â˜ƒ);
      } else {
         long â˜ƒ = System.currentTimeMillis();
         if (â˜ƒ - this.lastMessage >= this.getChatRateMillis()) {
            this.addMessage(â˜ƒ);
            this.lastMessage = â˜ƒ;
         } else {
            this.chatQueue.add(â˜ƒ);
         }
      }
   }
}
