package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;

public class ClientboundAddVibrationSignalPacket implements Packet<ClientGamePacketListener> {
   private final VibrationPath vibrationPath;

   public ClientboundAddVibrationSignalPacket(VibrationPath var1) {
      this.vibrationPath = â˜ƒ;
   }

   public ClientboundAddVibrationSignalPacket(FriendlyByteBuf var1) {
      this.vibrationPath = VibrationPath.read(â˜ƒ);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      VibrationPath.write(â˜ƒ, this.vibrationPath);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAddVibrationSignal(this);
   }

   public VibrationPath getVibrationPath() {
      return this.vibrationPath;
   }
}
