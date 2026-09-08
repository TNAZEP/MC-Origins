package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class SPacketSpawnObject implements Packet<INetHandlerPlayClient> {
   private int field_149018_a;
   private UUID field_186883_b;
   private double field_149016_b;
   private double field_149017_c;
   private double field_149014_d;
   private int field_149015_e;
   private int field_149012_f;
   private int field_149013_g;
   private int field_149021_h;
   private int field_149022_i;
   private int field_149019_j;
   private int field_149020_k;

   public SPacketSpawnObject() {
   }

   public SPacketSpawnObject(Entity var1, int var2) {
      this(☃, ☃, 0);
   }

   public SPacketSpawnObject(Entity var1, int var2, int var3) {
      this.field_149018_a = ☃.func_145782_y();
      this.field_186883_b = ☃.func_110124_au();
      this.field_149016_b = ☃.field_70165_t;
      this.field_149017_c = ☃.field_70163_u;
      this.field_149014_d = ☃.field_70161_v;
      this.field_149021_h = MathHelper.func_76141_d(☃.field_70125_A * 256.0F / 360.0F);
      this.field_149022_i = MathHelper.func_76141_d(☃.field_70177_z * 256.0F / 360.0F);
      this.field_149019_j = ☃;
      this.field_149020_k = ☃;
      double ☃ = 3.9;
      this.field_149015_e = (int)(MathHelper.func_151237_a(☃.field_70159_w, -3.9, 3.9) * 8000.0);
      this.field_149012_f = (int)(MathHelper.func_151237_a(☃.field_70181_x, -3.9, 3.9) * 8000.0);
      this.field_149013_g = (int)(MathHelper.func_151237_a(☃.field_70179_y, -3.9, 3.9) * 8000.0);
   }

   public SPacketSpawnObject(Entity var1, int var2, int var3, BlockPos var4) {
      this(☃, ☃, ☃);
      this.field_149016_b = (double)☃.func_177958_n();
      this.field_149017_c = (double)☃.func_177956_o();
      this.field_149014_d = (double)☃.func_177952_p();
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149018_a = ☃.func_150792_a();
      this.field_186883_b = ☃.func_179253_g();
      this.field_149019_j = ☃.readByte();
      this.field_149016_b = ☃.readDouble();
      this.field_149017_c = ☃.readDouble();
      this.field_149014_d = ☃.readDouble();
      this.field_149021_h = ☃.readByte();
      this.field_149022_i = ☃.readByte();
      this.field_149020_k = ☃.readInt();
      this.field_149015_e = ☃.readShort();
      this.field_149012_f = ☃.readShort();
      this.field_149013_g = ☃.readShort();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149018_a);
      ☃.func_179252_a(this.field_186883_b);
      ☃.writeByte(this.field_149019_j);
      ☃.writeDouble(this.field_149016_b);
      ☃.writeDouble(this.field_149017_c);
      ☃.writeDouble(this.field_149014_d);
      ☃.writeByte(this.field_149021_h);
      ☃.writeByte(this.field_149022_i);
      ☃.writeInt(this.field_149020_k);
      ☃.writeShort(this.field_149015_e);
      ☃.writeShort(this.field_149012_f);
      ☃.writeShort(this.field_149013_g);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147235_a(this);
   }

   public int func_149001_c() {
      return this.field_149018_a;
   }

   public UUID func_186879_b() {
      return this.field_186883_b;
   }

   public double func_186880_c() {
      return this.field_149016_b;
   }

   public double func_186882_d() {
      return this.field_149017_c;
   }

   public double func_186881_e() {
      return this.field_149014_d;
   }

   public int func_149010_g() {
      return this.field_149015_e;
   }

   public int func_149004_h() {
      return this.field_149012_f;
   }

   public int func_148999_i() {
      return this.field_149013_g;
   }

   public int func_149008_j() {
      return this.field_149021_h;
   }

   public int func_149006_k() {
      return this.field_149022_i;
   }

   public int func_148993_l() {
      return this.field_149019_j;
   }

   public int func_149009_m() {
      return this.field_149020_k;
   }

   public void func_149003_d(int var1) {
      this.field_149015_e = ☃;
   }

   public void func_149000_e(int var1) {
      this.field_149012_f = ☃;
   }

   public void func_149007_f(int var1) {
      this.field_149013_g = ☃;
   }

   public void func_149002_g(int var1) {
      this.field_149020_k = ☃;
   }
}
