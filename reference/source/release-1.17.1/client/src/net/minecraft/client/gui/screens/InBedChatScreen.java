package net.minecraft.client.gui.screens;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;

public class InBedChatScreen extends ChatScreen {
   public InBedChatScreen() {
      super("");
   }

   @Override
   protected void init() {
      super.init();
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height - 40, 200, 20, new TranslatableComponent("multiplayer.stopSleeping"), var1 -> this.sendWakeUp())
      );
   }

   @Override
   public void onClose() {
      this.sendWakeUp();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.sendWakeUp();
      } else if (â˜ƒ == 257 || â˜ƒ == 335) {
         String â˜ƒ = this.input.getValue().trim();
         if (!â˜ƒ.isEmpty()) {
            this.sendMessage(â˜ƒ);
         }

         this.input.setValue("");
         this.minecraft.gui.getChat().resetChatScroll();
         return true;
      }

      return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void sendWakeUp() {
      ClientPacketListener â˜ƒ = this.minecraft.player.connection;
      â˜ƒ.send(new ServerboundPlayerCommandPacket(this.minecraft.player, ServerboundPlayerCommandPacket.Action.STOP_SLEEPING));
   }
}
