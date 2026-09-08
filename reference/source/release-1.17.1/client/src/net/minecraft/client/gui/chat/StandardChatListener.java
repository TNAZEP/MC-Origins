package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;

public class StandardChatListener implements ChatListener {
   private final Minecraft minecraft;

   public StandardChatListener(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void handle(ChatType var1, Component var2, UUID var3) {
      if (â˜ƒ != ChatType.CHAT) {
         this.minecraft.gui.getChat().addMessage(â˜ƒ);
      } else {
         this.minecraft.gui.getChat().enqueueMessage(â˜ƒ);
      }
   }
}
