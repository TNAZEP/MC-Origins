package net.minecraft.network.protocol.game;

import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundPlayerPositionPacket implements Packet<ClientGamePacketListener> {
   private final double x;
   private final double y;
   private final double z;
   private final float yRot;
   private final float xRot;
   private final Set<ClientboundPlayerPositionPacket.RelativeArgument> relativeArguments;
   private final int id;
   private final boolean dismountVehicle;

   public ClientboundPlayerPositionPacket(
      double var1, double var3, double var5, float var7, float var8, Set<ClientboundPlayerPositionPacket.RelativeArgument> var9, int var10, boolean var11
   ) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.yRot = â˜ƒ;
      this.xRot = â˜ƒ;
      this.relativeArguments = â˜ƒ;
      this.id = â˜ƒ;
      this.dismountVehicle = â˜ƒ;
   }

   public ClientboundPlayerPositionPacket(FriendlyByteBuf var1) {
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.yRot = â˜ƒ.readFloat();
      this.xRot = â˜ƒ.readFloat();
      this.relativeArguments = ClientboundPlayerPositionPacket.RelativeArgument.unpack(â˜ƒ.readUnsignedByte());
      this.id = â˜ƒ.readVarInt();
      this.dismountVehicle = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeFloat(this.yRot);
      â˜ƒ.writeFloat(this.xRot);
      â˜ƒ.writeByte(ClientboundPlayerPositionPacket.RelativeArgument.pack(this.relativeArguments));
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeBoolean(this.dismountVehicle);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleMovePlayer(this);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getYRot() {
      return this.yRot;
   }

   public float getXRot() {
      return this.xRot;
   }

   public int getId() {
      return this.id;
   }

   public boolean requestDismountVehicle() {
      return this.dismountVehicle;
   }

   public Set<ClientboundPlayerPositionPacket.RelativeArgument> getRelativeArguments() {
      return this.relativeArguments;
   }

   public static enum RelativeArgument {
      X(0),
      Y(1),
      Z(2),
      Y_ROT(3),
      X_ROT(4);

      private final int bit;

      private RelativeArgument(int var3) {
         this.bit = â˜ƒ;
      }

      private int getMask() {
         return 1 << this.bit;
      }

      private boolean isSet(int var1) {
         return (â˜ƒ & this.getMask()) == this.getMask();
      }

      public static Set<ClientboundPlayerPositionPacket.RelativeArgument> unpack(int var0) {
         Set<ClientboundPlayerPositionPacket.RelativeArgument> â˜ƒ = EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class);

         for(ClientboundPlayerPositionPacket.RelativeArgument â˜ƒx : values()) {
            if (â˜ƒx.isSet(â˜ƒ)) {
               â˜ƒ.add(â˜ƒx);
            }
         }

         return â˜ƒ;
      }

      public static int pack(Set<ClientboundPlayerPositionPacket.RelativeArgument> var0) {
         int â˜ƒ = 0;

         for(ClientboundPlayerPositionPacket.RelativeArgument â˜ƒx : â˜ƒ) {
            â˜ƒ |= â˜ƒx.getMask();
         }

         return â˜ƒ;
      }
   }
}
