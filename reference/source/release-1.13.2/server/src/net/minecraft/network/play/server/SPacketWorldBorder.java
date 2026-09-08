package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

public class SPacketWorldBorder implements Packet<INetHandlerPlayClient> {
   private SPacketWorldBorder.Action field_179795_a;
   private int field_179793_b;
   private double field_179794_c;
   private double field_179791_d;
   private double field_179792_e;
   private double field_179789_f;
   private long field_179790_g;
   private int field_179796_h;
   private int field_179797_i;

   public SPacketWorldBorder() {
   }

   public SPacketWorldBorder(WorldBorder var1, SPacketWorldBorder.Action var2) {
      this.field_179795_a = ☃;
      this.field_179794_c = ☃.func_177731_f();
      this.field_179791_d = ☃.func_177721_g();
      this.field_179789_f = ☃.func_177741_h();
      this.field_179792_e = ☃.func_177751_j();
      this.field_179790_g = ☃.func_177732_i();
      this.field_179793_b = ☃.func_177722_l();
      this.field_179797_i = ☃.func_177748_q();
      this.field_179796_h = ☃.func_177740_p();
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179795_a = ☃.func_179257_a(SPacketWorldBorder.Action.class);
      switch(this.field_179795_a) {
         case SET_SIZE:
            this.field_179792_e = ☃.readDouble();
            break;
         case LERP_SIZE:
            this.field_179789_f = ☃.readDouble();
            this.field_179792_e = ☃.readDouble();
            this.field_179790_g = ☃.func_179260_f();
            break;
         case SET_CENTER:
            this.field_179794_c = ☃.readDouble();
            this.field_179791_d = ☃.readDouble();
            break;
         case SET_WARNING_BLOCKS:
            this.field_179797_i = ☃.func_150792_a();
            break;
         case SET_WARNING_TIME:
            this.field_179796_h = ☃.func_150792_a();
            break;
         case INITIALIZE:
            this.field_179794_c = ☃.readDouble();
            this.field_179791_d = ☃.readDouble();
            this.field_179789_f = ☃.readDouble();
            this.field_179792_e = ☃.readDouble();
            this.field_179790_g = ☃.func_179260_f();
            this.field_179793_b = ☃.func_150792_a();
            this.field_179797_i = ☃.func_150792_a();
            this.field_179796_h = ☃.func_150792_a();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179249_a(this.field_179795_a);
      switch(this.field_179795_a) {
         case SET_SIZE:
            ☃.writeDouble(this.field_179792_e);
            break;
         case LERP_SIZE:
            ☃.writeDouble(this.field_179789_f);
            ☃.writeDouble(this.field_179792_e);
            ☃.func_179254_b(this.field_179790_g);
            break;
         case SET_CENTER:
            ☃.writeDouble(this.field_179794_c);
            ☃.writeDouble(this.field_179791_d);
            break;
         case SET_WARNING_BLOCKS:
            ☃.func_150787_b(this.field_179797_i);
            break;
         case SET_WARNING_TIME:
            ☃.func_150787_b(this.field_179796_h);
            break;
         case INITIALIZE:
            ☃.writeDouble(this.field_179794_c);
            ☃.writeDouble(this.field_179791_d);
            ☃.writeDouble(this.field_179789_f);
            ☃.writeDouble(this.field_179792_e);
            ☃.func_179254_b(this.field_179790_g);
            ☃.func_150787_b(this.field_179793_b);
            ☃.func_150787_b(this.field_179797_i);
            ☃.func_150787_b(this.field_179796_h);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_175093_a(this);
   }

   public static enum Action {
      SET_SIZE,
      LERP_SIZE,
      SET_CENTER,
      INITIALIZE,
      SET_WARNING_TIME,
      SET_WARNING_BLOCKS;
   }
}
