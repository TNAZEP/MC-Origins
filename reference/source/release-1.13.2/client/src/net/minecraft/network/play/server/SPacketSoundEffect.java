package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.IRegistry;
import org.apache.commons.lang3.Validate;

public class SPacketSoundEffect implements Packet<INetHandlerPlayClient> {
   private SoundEvent field_186979_a;
   private SoundCategory field_186980_b;
   private int field_149217_b;
   private int field_149218_c;
   private int field_149215_d;
   private float field_149216_e;
   private float field_149214_f;

   public SPacketSoundEffect() {
   }

   public SPacketSoundEffect(SoundEvent var1, SoundCategory var2, double var3, double var5, double var7, float var9, float var10) {
      Validate.notNull(☃, "sound");
      this.field_186979_a = ☃;
      this.field_186980_b = ☃;
      this.field_149217_b = (int)(☃ * 8.0);
      this.field_149218_c = (int)(☃ * 8.0);
      this.field_149215_d = (int)(☃ * 8.0);
      this.field_149216_e = ☃;
      this.field_149214_f = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_186979_a = IRegistry.field_212633_v.func_148754_a(☃.func_150792_a());
      this.field_186980_b = ☃.func_179257_a(SoundCategory.class);
      this.field_149217_b = ☃.readInt();
      this.field_149218_c = ☃.readInt();
      this.field_149215_d = ☃.readInt();
      this.field_149216_e = ☃.readFloat();
      this.field_149214_f = ☃.readFloat();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(IRegistry.field_212633_v.func_148757_b(this.field_186979_a));
      ☃.func_179249_a(this.field_186980_b);
      ☃.writeInt(this.field_149217_b);
      ☃.writeInt(this.field_149218_c);
      ☃.writeInt(this.field_149215_d);
      ☃.writeFloat(this.field_149216_e);
      ☃.writeFloat(this.field_149214_f);
   }

   public SoundEvent func_186978_a() {
      return this.field_186979_a;
   }

   public SoundCategory func_186977_b() {
      return this.field_186980_b;
   }

   public double func_149207_d() {
      return (double)((float)this.field_149217_b / 8.0F);
   }

   public double func_149211_e() {
      return (double)((float)this.field_149218_c / 8.0F);
   }

   public double func_149210_f() {
      return (double)((float)this.field_149215_d / 8.0F);
   }

   public float func_149208_g() {
      return this.field_149216_e;
   }

   public float func_149209_h() {
      return this.field_149214_f;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184327_a(this);
   }
}
