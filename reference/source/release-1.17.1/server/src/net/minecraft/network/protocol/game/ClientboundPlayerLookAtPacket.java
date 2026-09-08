package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ClientboundPlayerLookAtPacket implements Packet<ClientGamePacketListener> {
   private final double x;
   private final double y;
   private final double z;
   private final int entity;
   private final EntityAnchorArgument.Anchor fromAnchor;
   private final EntityAnchorArgument.Anchor toAnchor;
   private final boolean atEntity;

   public ClientboundPlayerLookAtPacket(EntityAnchorArgument.Anchor var1, double var2, double var4, double var6) {
      this.fromAnchor = â˜ƒ;
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.entity = 0;
      this.atEntity = false;
      this.toAnchor = null;
   }

   public ClientboundPlayerLookAtPacket(EntityAnchorArgument.Anchor var1, Entity var2, EntityAnchorArgument.Anchor var3) {
      this.fromAnchor = â˜ƒ;
      this.entity = â˜ƒ.getId();
      this.toAnchor = â˜ƒ;
      Vec3 â˜ƒ = â˜ƒ.apply(â˜ƒ);
      this.x = â˜ƒ.x;
      this.y = â˜ƒ.y;
      this.z = â˜ƒ.z;
      this.atEntity = true;
   }

   public ClientboundPlayerLookAtPacket(FriendlyByteBuf var1) {
      this.fromAnchor = â˜ƒ.readEnum(EntityAnchorArgument.Anchor.class);
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.atEntity = â˜ƒ.readBoolean();
      if (this.atEntity) {
         this.entity = â˜ƒ.readVarInt();
         this.toAnchor = â˜ƒ.readEnum(EntityAnchorArgument.Anchor.class);
      } else {
         this.entity = 0;
         this.toAnchor = null;
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeEnum(this.fromAnchor);
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeBoolean(this.atEntity);
      if (this.atEntity) {
         â˜ƒ.writeVarInt(this.entity);
         â˜ƒ.writeEnum(this.toAnchor);
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleLookAt(this);
   }

   public EntityAnchorArgument.Anchor getFromAnchor() {
      return this.fromAnchor;
   }

   @Nullable
   public Vec3 getPosition(Level var1) {
      if (this.atEntity) {
         Entity â˜ƒ = â˜ƒ.getEntity(this.entity);
         return â˜ƒ == null ? new Vec3(this.x, this.y, this.z) : this.toAnchor.apply(â˜ƒ);
      } else {
         return new Vec3(this.x, this.y, this.z);
      }
   }
}
