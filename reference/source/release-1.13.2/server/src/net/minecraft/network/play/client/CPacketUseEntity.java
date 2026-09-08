package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CPacketUseEntity implements Packet<INetHandlerPlayServer> {
   private int field_149567_a;
   private CPacketUseEntity.Action field_149566_b;
   private Vec3d field_179713_c;
   private EnumHand field_186995_d;

   public CPacketUseEntity() {
   }

   public CPacketUseEntity(Entity var1) {
      this.field_149567_a = ☃.func_145782_y();
      this.field_149566_b = CPacketUseEntity.Action.ATTACK;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149567_a = ☃.func_150792_a();
      this.field_149566_b = ☃.func_179257_a(CPacketUseEntity.Action.class);
      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         this.field_179713_c = new Vec3d((double)☃.readFloat(), (double)☃.readFloat(), (double)☃.readFloat());
      }

      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT || this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         this.field_186995_d = ☃.func_179257_a(EnumHand.class);
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149567_a);
      ☃.func_179249_a(this.field_149566_b);
      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         ☃.writeFloat((float)this.field_179713_c.field_72450_a);
         ☃.writeFloat((float)this.field_179713_c.field_72448_b);
         ☃.writeFloat((float)this.field_179713_c.field_72449_c);
      }

      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT || this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         ☃.func_179249_a(this.field_186995_d);
      }
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147340_a(this);
   }

   @Nullable
   public Entity func_149564_a(World var1) {
      return ☃.func_73045_a(this.field_149567_a);
   }

   public CPacketUseEntity.Action func_149565_c() {
      return this.field_149566_b;
   }

   public EnumHand func_186994_b() {
      return this.field_186995_d;
   }

   public Vec3d func_179712_b() {
      return this.field_179713_c;
   }

   public static enum Action {
      INTERACT,
      ATTACK,
      INTERACT_AT;
   }
}
