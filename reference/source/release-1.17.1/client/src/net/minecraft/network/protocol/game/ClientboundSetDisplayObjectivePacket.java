package net.minecraft.network.protocol.game;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.scores.Objective;

public class ClientboundSetDisplayObjectivePacket implements Packet<ClientGamePacketListener> {
   private final int slot;
   private final String objectiveName;

   public ClientboundSetDisplayObjectivePacket(int var1, @Nullable Objective var2) {
      this.slot = â˜ƒ;
      if (â˜ƒ == null) {
         this.objectiveName = "";
      } else {
         this.objectiveName = â˜ƒ.getName();
      }
   }

   public ClientboundSetDisplayObjectivePacket(FriendlyByteBuf var1) {
      this.slot = â˜ƒ.readByte();
      this.objectiveName = â˜ƒ.readUtf(16);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.slot);
      â˜ƒ.writeUtf(this.objectiveName);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetDisplayObjective(this);
   }

   public int getSlot() {
      return this.slot;
   }

   @Nullable
   public String getObjectiveName() {
      return Objects.equals(this.objectiveName, "") ? null : this.objectiveName;
   }
}
