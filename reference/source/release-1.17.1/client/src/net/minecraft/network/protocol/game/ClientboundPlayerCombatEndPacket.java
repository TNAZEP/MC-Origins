package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.CombatTracker;

public class ClientboundPlayerCombatEndPacket implements Packet<ClientGamePacketListener> {
   private final int killerId;
   private final int duration;

   public ClientboundPlayerCombatEndPacket(CombatTracker var1) {
      this(â˜ƒ.getKillerId(), â˜ƒ.getCombatDuration());
   }

   public ClientboundPlayerCombatEndPacket(int var1, int var2) {
      this.killerId = â˜ƒ;
      this.duration = â˜ƒ;
   }

   public ClientboundPlayerCombatEndPacket(FriendlyByteBuf var1) {
      this.duration = â˜ƒ.readVarInt();
      this.killerId = â˜ƒ.readInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.duration);
      â˜ƒ.writeInt(this.killerId);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handlePlayerCombatEnd(this);
   }
}
