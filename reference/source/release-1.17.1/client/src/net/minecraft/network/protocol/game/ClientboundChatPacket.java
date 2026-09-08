package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundChatPacket implements Packet<ClientGamePacketListener> {
   private final Component message;
   private final ChatType type;
   private final UUID sender;

   public ClientboundChatPacket(Component var1, ChatType var2, UUID var3) {
      this.message = â˜ƒ;
      this.type = â˜ƒ;
      this.sender = â˜ƒ;
   }

   public ClientboundChatPacket(FriendlyByteBuf var1) {
      this.message = â˜ƒ.readComponent();
      this.type = ChatType.getForIndex(â˜ƒ.readByte());
      this.sender = â˜ƒ.readUUID();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeComponent(this.message);
      â˜ƒ.writeByte(this.type.getIndex());
      â˜ƒ.writeUUID(this.sender);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleChat(this);
   }

   public Component getMessage() {
      return this.message;
   }

   public ChatType getType() {
      return this.type;
   }

   public UUID getSender() {
      return this.sender;
   }

   @Override
   public boolean isSkippable() {
      return true;
   }
}
