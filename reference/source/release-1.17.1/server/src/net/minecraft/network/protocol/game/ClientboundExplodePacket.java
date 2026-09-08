package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ClientboundExplodePacket implements Packet<ClientGamePacketListener> {
   private final double x;
   private final double y;
   private final double z;
   private final float power;
   private final List<BlockPos> toBlow;
   private final float knockbackX;
   private final float knockbackY;
   private final float knockbackZ;

   public ClientboundExplodePacket(double var1, double var3, double var5, float var7, List<BlockPos> var8, @Nullable Vec3 var9) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.power = â˜ƒ;
      this.toBlow = Lists.<BlockPos>newArrayList(â˜ƒ);
      if (â˜ƒ != null) {
         this.knockbackX = (float)â˜ƒ.x;
         this.knockbackY = (float)â˜ƒ.y;
         this.knockbackZ = (float)â˜ƒ.z;
      } else {
         this.knockbackX = 0.0F;
         this.knockbackY = 0.0F;
         this.knockbackZ = 0.0F;
      }
   }

   public ClientboundExplodePacket(FriendlyByteBuf var1) {
      this.x = (double)â˜ƒ.readFloat();
      this.y = (double)â˜ƒ.readFloat();
      this.z = (double)â˜ƒ.readFloat();
      this.power = â˜ƒ.readFloat();
      int â˜ƒ = Mth.floor(this.x);
      int â˜ƒx = Mth.floor(this.y);
      int â˜ƒxx = Mth.floor(this.z);
      this.toBlow = â˜ƒ.readList(var3x -> {
         int â˜ƒ = var3x.readByte() + â˜ƒ;
         int â˜ƒx = var3x.readByte() + â˜ƒ;
         int â˜ƒxx = var3x.readByte() + â˜ƒ;
         return new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
      });
      this.knockbackX = â˜ƒ.readFloat();
      this.knockbackY = â˜ƒ.readFloat();
      this.knockbackZ = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeFloat((float)this.x);
      â˜ƒ.writeFloat((float)this.y);
      â˜ƒ.writeFloat((float)this.z);
      â˜ƒ.writeFloat(this.power);
      int â˜ƒ = Mth.floor(this.x);
      int â˜ƒx = Mth.floor(this.y);
      int â˜ƒxx = Mth.floor(this.z);
      â˜ƒ.writeCollection(this.toBlow, (var3x, var4x) -> {
         int â˜ƒ = var4x.getX() - â˜ƒ;
         int â˜ƒx = var4x.getY() - â˜ƒ;
         int â˜ƒxx = var4x.getZ() - â˜ƒ;
         var3x.writeByte(â˜ƒ);
         var3x.writeByte(â˜ƒx);
         var3x.writeByte(â˜ƒxx);
      });
      â˜ƒ.writeFloat(this.knockbackX);
      â˜ƒ.writeFloat(this.knockbackY);
      â˜ƒ.writeFloat(this.knockbackZ);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleExplosion(this);
   }

   public float getKnockbackX() {
      return this.knockbackX;
   }

   public float getKnockbackY() {
      return this.knockbackY;
   }

   public float getKnockbackZ() {
      return this.knockbackZ;
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

   public float getPower() {
      return this.power;
   }

   public List<BlockPos> getToBlow() {
      return this.toBlow;
   }
}
