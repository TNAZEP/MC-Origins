package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ClientboundSetEntityMotionPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   private final int xa;
   private final int ya;
   private final int za;

   public ClientboundSetEntityMotionPacket(Entity var1) {
      this(â˜ƒ.getId(), â˜ƒ.getDeltaMovement());
   }

   public ClientboundSetEntityMotionPacket(int var1, Vec3 var2) {
      this.id = â˜ƒ;
      double â˜ƒ = 3.9;
      double â˜ƒx = Mth.clamp(â˜ƒ.x, -3.9, 3.9);
      double â˜ƒxx = Mth.clamp(â˜ƒ.y, -3.9, 3.9);
      double â˜ƒxxx = Mth.clamp(â˜ƒ.z, -3.9, 3.9);
      this.xa = (int)(â˜ƒx * 8000.0);
      this.ya = (int)(â˜ƒxx * 8000.0);
      this.za = (int)(â˜ƒxxx * 8000.0);
   }

   public ClientboundSetEntityMotionPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.xa = â˜ƒ.readShort();
      this.ya = â˜ƒ.readShort();
      this.za = â˜ƒ.readShort();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeShort(this.xa);
      â˜ƒ.writeShort(this.ya);
      â˜ƒ.writeShort(this.za);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetEntityMotion(this);
   }

   public int getId() {
      return this.id;
   }

   public int getXa() {
      return this.xa;
   }

   public int getYa() {
      return this.ya;
   }

   public int getZa() {
      return this.za;
   }
}
