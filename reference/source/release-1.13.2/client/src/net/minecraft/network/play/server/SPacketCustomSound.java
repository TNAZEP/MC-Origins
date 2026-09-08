package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class SPacketCustomSound implements Packet<INetHandlerPlayClient> {
   private ResourceLocation field_149219_a;
   private SoundCategory field_186933_b;
   private int field_186934_c;
   private int field_186935_d = Integer.MAX_VALUE;
   private int field_186936_e;
   private float field_186937_f;
   private float field_186938_g;

   public SPacketCustomSound() {
   }

   public SPacketCustomSound(ResourceLocation var1, SoundCategory var2, Vec3d var3, float var4, float var5) {
      this.field_149219_a = ☃;
      this.field_186933_b = ☃;
      this.field_186934_c = (int)(☃.field_72450_a * 8.0);
      this.field_186935_d = (int)(☃.field_72448_b * 8.0);
      this.field_186936_e = (int)(☃.field_72449_c * 8.0);
      this.field_186937_f = ☃;
      this.field_186938_g = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149219_a = ☃.func_192575_l();
      this.field_186933_b = ☃.func_179257_a(SoundCategory.class);
      this.field_186934_c = ☃.readInt();
      this.field_186935_d = ☃.readInt();
      this.field_186936_e = ☃.readInt();
      this.field_186937_f = ☃.readFloat();
      this.field_186938_g = ☃.readFloat();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_192572_a(this.field_149219_a);
      ☃.func_179249_a(this.field_186933_b);
      ☃.writeInt(this.field_186934_c);
      ☃.writeInt(this.field_186935_d);
      ☃.writeInt(this.field_186936_e);
      ☃.writeFloat(this.field_186937_f);
      ☃.writeFloat(this.field_186938_g);
   }

   public ResourceLocation func_197698_a() {
      return this.field_149219_a;
   }

   public SoundCategory func_186929_b() {
      return this.field_186933_b;
   }

   public double func_186932_c() {
      return (double)((float)this.field_186934_c / 8.0F);
   }

   public double func_186926_d() {
      return (double)((float)this.field_186935_d / 8.0F);
   }

   public double func_186925_e() {
      return (double)((float)this.field_186936_e / 8.0F);
   }

   public float func_186927_f() {
      return this.field_186937_f;
   }

   public float func_186928_g() {
      return this.field_186938_g;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184329_a(this);
   }
}
