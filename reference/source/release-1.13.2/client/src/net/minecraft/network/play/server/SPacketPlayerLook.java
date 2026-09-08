package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SPacketPlayerLook implements Packet<INetHandlerPlayClient> {
   private double field_200532_a;
   private double field_200533_b;
   private double field_200534_c;
   private int field_200535_d;
   private EntityAnchorArgument.Type field_201065_e;
   private EntityAnchorArgument.Type field_201066_f;
   private boolean field_200536_e;

   public SPacketPlayerLook() {
   }

   public SPacketPlayerLook(EntityAnchorArgument.Type var1, double var2, double var4, double var6) {
      this.field_201065_e = ☃;
      this.field_200532_a = ☃;
      this.field_200533_b = ☃;
      this.field_200534_c = ☃;
   }

   public SPacketPlayerLook(EntityAnchorArgument.Type var1, Entity var2, EntityAnchorArgument.Type var3) {
      this.field_201065_e = ☃;
      this.field_200535_d = ☃.func_145782_y();
      this.field_201066_f = ☃;
      Vec3d ☃ = ☃.func_201017_a(☃);
      this.field_200532_a = ☃.field_72450_a;
      this.field_200533_b = ☃.field_72448_b;
      this.field_200534_c = ☃.field_72449_c;
      this.field_200536_e = true;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_201065_e = ☃.func_179257_a(EntityAnchorArgument.Type.class);
      this.field_200532_a = ☃.readDouble();
      this.field_200533_b = ☃.readDouble();
      this.field_200534_c = ☃.readDouble();
      if (☃.readBoolean()) {
         this.field_200536_e = true;
         this.field_200535_d = ☃.func_150792_a();
         this.field_201066_f = ☃.func_179257_a(EntityAnchorArgument.Type.class);
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179249_a(this.field_201065_e);
      ☃.writeDouble(this.field_200532_a);
      ☃.writeDouble(this.field_200533_b);
      ☃.writeDouble(this.field_200534_c);
      ☃.writeBoolean(this.field_200536_e);
      if (this.field_200536_e) {
         ☃.func_150787_b(this.field_200535_d);
         ☃.func_179249_a(this.field_201066_f);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_200232_a(this);
   }

   public EntityAnchorArgument.Type func_201064_a() {
      return this.field_201065_e;
   }

   @Nullable
   public Vec3d func_200531_a(World var1) {
      if (this.field_200536_e) {
         Entity ☃ = ☃.func_73045_a(this.field_200535_d);
         return ☃ == null ? new Vec3d(this.field_200532_a, this.field_200533_b, this.field_200534_c) : this.field_201066_f.func_201017_a(☃);
      } else {
         return new Vec3d(this.field_200532_a, this.field_200533_b, this.field_200534_c);
      }
   }
}
