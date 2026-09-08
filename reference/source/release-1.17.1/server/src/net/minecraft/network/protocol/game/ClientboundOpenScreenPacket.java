package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.inventory.MenuType;

public class ClientboundOpenScreenPacket implements Packet<ClientGamePacketListener> {
   private final int containerId;
   private final int type;
   private final Component title;

   public ClientboundOpenScreenPacket(int var1, MenuType<?> var2, Component var3) {
      this.containerId = â˜ƒ;
      this.type = Registry.MENU.getId(â˜ƒ);
      this.title = â˜ƒ;
   }

   public ClientboundOpenScreenPacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readVarInt();
      this.type = â˜ƒ.readVarInt();
      this.title = â˜ƒ.readComponent();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.containerId);
      â˜ƒ.writeVarInt(this.type);
      â˜ƒ.writeComponent(this.title);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleOpenScreen(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   @Nullable
   public MenuType<?> getType() {
      return Registry.MENU.byId(this.type);
   }

   public Component getTitle() {
      return this.title;
   }
}
