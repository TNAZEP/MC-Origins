package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.CombatTracker;

public class ClientboundPlayerCombatKillPacket implements Packet<ClientGamePacketListener> {
   private final int playerId;
   private final int killerId;
   private final Component message;

   public ClientboundPlayerCombatKillPacket(CombatTracker var1, Component var2) {
      this(â˜ƒ.getMob().getId(), â˜ƒ.getKillerId(), â˜ƒ);
   }

   public ClientboundPlayerCombatKillPacket(int var1, int var2, Component var3) {
      this.playerId = â˜ƒ;
      this.killerId = â˜ƒ;
      this.message = â˜ƒ;
   }

   public ClientboundPlayerCombatKillPacket(FriendlyByteBuf var1) {
      this.playerId = â˜ƒ.readVarInt();
      this.killerId = â˜ƒ.readInt();
      this.message = â˜ƒ.readComponent();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.playerId);
      â˜ƒ.writeInt(this.killerId);
      â˜ƒ.writeComponent(this.message);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handlePlayerCombatKill(this);
   }

   @Override
   public boolean isSkippable() {
      return true;
   }

   public int getKillerId() {
      return this.killerId;
   }

   public int getPlayerId() {
      return this.playerId;
   }

   public Component getMessage() {
      return this.message;
   }
}
