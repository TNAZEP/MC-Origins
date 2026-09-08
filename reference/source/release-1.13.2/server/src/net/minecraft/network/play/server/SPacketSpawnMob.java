package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.registry.IRegistry;

public class SPacketSpawnMob implements Packet<INetHandlerPlayClient> {
   private int field_149042_a;
   private UUID field_186894_b;
   private int field_149040_b;
   private double field_149041_c;
   private double field_149038_d;
   private double field_149039_e;
   private int field_149036_f;
   private int field_149037_g;
   private int field_149047_h;
   private byte field_149048_i;
   private byte field_149045_j;
   private byte field_149046_k;
   private EntityDataManager field_149043_l;
   private List<EntityDataManager.DataEntry<?>> field_149044_m;

   public SPacketSpawnMob() {
   }

   public SPacketSpawnMob(EntityLivingBase var1) {
      this.field_149042_a = ☃.func_145782_y();
      this.field_186894_b = ☃.func_110124_au();
      this.field_149040_b = IRegistry.field_212629_r.func_148757_b(☃.func_200600_R());
      this.field_149041_c = ☃.field_70165_t;
      this.field_149038_d = ☃.field_70163_u;
      this.field_149039_e = ☃.field_70161_v;
      this.field_149048_i = (byte)((int)(☃.field_70177_z * 256.0F / 360.0F));
      this.field_149045_j = (byte)((int)(☃.field_70125_A * 256.0F / 360.0F));
      this.field_149046_k = (byte)((int)(☃.field_70759_as * 256.0F / 360.0F));
      double ☃ = 3.9;
      double ☃x = ☃.field_70159_w;
      double ☃xx = ☃.field_70181_x;
      double ☃xxx = ☃.field_70179_y;
      if (☃x < -3.9) {
         ☃x = -3.9;
      }

      if (☃xx < -3.9) {
         ☃xx = -3.9;
      }

      if (☃xxx < -3.9) {
         ☃xxx = -3.9;
      }

      if (☃x > 3.9) {
         ☃x = 3.9;
      }

      if (☃xx > 3.9) {
         ☃xx = 3.9;
      }

      if (☃xxx > 3.9) {
         ☃xxx = 3.9;
      }

      this.field_149036_f = (int)(☃x * 8000.0);
      this.field_149037_g = (int)(☃xx * 8000.0);
      this.field_149047_h = (int)(☃xxx * 8000.0);
      this.field_149043_l = ☃.func_184212_Q();
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149042_a = ☃.func_150792_a();
      this.field_186894_b = ☃.func_179253_g();
      this.field_149040_b = ☃.func_150792_a();
      this.field_149041_c = ☃.readDouble();
      this.field_149038_d = ☃.readDouble();
      this.field_149039_e = ☃.readDouble();
      this.field_149048_i = ☃.readByte();
      this.field_149045_j = ☃.readByte();
      this.field_149046_k = ☃.readByte();
      this.field_149036_f = ☃.readShort();
      this.field_149037_g = ☃.readShort();
      this.field_149047_h = ☃.readShort();
      this.field_149044_m = EntityDataManager.func_187215_b(☃);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149042_a);
      ☃.func_179252_a(this.field_186894_b);
      ☃.func_150787_b(this.field_149040_b);
      ☃.writeDouble(this.field_149041_c);
      ☃.writeDouble(this.field_149038_d);
      ☃.writeDouble(this.field_149039_e);
      ☃.writeByte(this.field_149048_i);
      ☃.writeByte(this.field_149045_j);
      ☃.writeByte(this.field_149046_k);
      ☃.writeShort(this.field_149036_f);
      ☃.writeShort(this.field_149037_g);
      ☃.writeShort(this.field_149047_h);
      this.field_149043_l.func_187216_a(☃);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147281_a(this);
   }
}
