package net.minecraft.world.item;

import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.server.level.ServerPlayer;

public class ServerItemCooldowns extends ItemCooldowns {
   private final ServerPlayer player;

   public ServerItemCooldowns(ServerPlayer var1) {
      this.player = â˜ƒ;
   }

   @Override
   protected void onCooldownStarted(Item var1, int var2) {
      super.onCooldownStarted(â˜ƒ, â˜ƒ);
      this.player.connection.send(new ClientboundCooldownPacket(â˜ƒ, â˜ƒ));
   }

   @Override
   protected void onCooldownEnded(Item var1) {
      super.onCooldownEnded(â˜ƒ);
      this.player.connection.send(new ClientboundCooldownPacket(â˜ƒ, 0));
   }
}
