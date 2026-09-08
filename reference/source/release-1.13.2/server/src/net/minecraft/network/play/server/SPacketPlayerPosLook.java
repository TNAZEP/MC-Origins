package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketPlayerPosLook implements Packet<INetHandlerPlayClient> {
   private double field_148940_a;
   private double field_148938_b;
   private double field_148939_c;
   private float field_148936_d;
   private float field_148937_e;
   private Set<SPacketPlayerPosLook.EnumFlags> field_179835_f;
   private int field_186966_g;

   public SPacketPlayerPosLook() {
   }

   public SPacketPlayerPosLook(double var1, double var3, double var5, float var7, float var8, Set<SPacketPlayerPosLook.EnumFlags> var9, int var10) {
      this.field_148940_a = ☃;
      this.field_148938_b = ☃;
      this.field_148939_c = ☃;
      this.field_148936_d = ☃;
      this.field_148937_e = ☃;
      this.field_179835_f = ☃;
      this.field_186966_g = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148940_a = ☃.readDouble();
      this.field_148938_b = ☃.readDouble();
      this.field_148939_c = ☃.readDouble();
      this.field_148936_d = ☃.readFloat();
      this.field_148937_e = ☃.readFloat();
      this.field_179835_f = SPacketPlayerPosLook.EnumFlags.func_187044_a(☃.readUnsignedByte());
      this.field_186966_g = ☃.func_150792_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeDouble(this.field_148940_a);
      ☃.writeDouble(this.field_148938_b);
      ☃.writeDouble(this.field_148939_c);
      ☃.writeFloat(this.field_148936_d);
      ☃.writeFloat(this.field_148937_e);
      ☃.writeByte(SPacketPlayerPosLook.EnumFlags.func_187040_a(this.field_179835_f));
      ☃.func_150787_b(this.field_186966_g);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184330_a(this);
   }

   public static enum EnumFlags {
      X(0),
      Y(1),
      Z(2),
      Y_ROT(3),
      X_ROT(4);

      private final int field_187050_f;

      private EnumFlags(int var3) {
         this.field_187050_f = ☃;
      }

      private int func_187042_a() {
         return 1 << this.field_187050_f;
      }

      private boolean func_187043_b(int var1) {
         return (☃ & this.func_187042_a()) == this.func_187042_a();
      }

      public static Set<SPacketPlayerPosLook.EnumFlags> func_187044_a(int var0) {
         Set<SPacketPlayerPosLook.EnumFlags> ☃ = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);

         for(SPacketPlayerPosLook.EnumFlags ☃x : values()) {
            if (☃x.func_187043_b(☃)) {
               ☃.add(☃x);
            }
         }

         return ☃;
      }

      public static int func_187040_a(Set<SPacketPlayerPosLook.EnumFlags> var0) {
         int ☃ = 0;

         for(SPacketPlayerPosLook.EnumFlags ☃x : ☃) {
            ☃ |= ☃x.func_187042_a();
         }

         return ☃;
      }
   }
}
