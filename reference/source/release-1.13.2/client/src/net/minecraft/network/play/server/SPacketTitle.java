package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketTitle implements Packet<INetHandlerPlayClient> {
   private SPacketTitle.Type field_179812_a;
   private ITextComponent field_179810_b;
   private int field_179811_c;
   private int field_179808_d;
   private int field_179809_e;

   public SPacketTitle() {
   }

   public SPacketTitle(SPacketTitle.Type var1, ITextComponent var2) {
      this(☃, ☃, -1, -1, -1);
   }

   public SPacketTitle(int var1, int var2, int var3) {
      this(SPacketTitle.Type.TIMES, null, ☃, ☃, ☃);
   }

   public SPacketTitle(SPacketTitle.Type var1, @Nullable ITextComponent var2, int var3, int var4, int var5) {
      this.field_179812_a = ☃;
      this.field_179810_b = ☃;
      this.field_179811_c = ☃;
      this.field_179808_d = ☃;
      this.field_179809_e = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179812_a = ☃.func_179257_a(SPacketTitle.Type.class);
      if (this.field_179812_a == SPacketTitle.Type.TITLE
         || this.field_179812_a == SPacketTitle.Type.SUBTITLE
         || this.field_179812_a == SPacketTitle.Type.ACTIONBAR) {
         this.field_179810_b = ☃.func_179258_d();
      }

      if (this.field_179812_a == SPacketTitle.Type.TIMES) {
         this.field_179811_c = ☃.readInt();
         this.field_179808_d = ☃.readInt();
         this.field_179809_e = ☃.readInt();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179249_a(this.field_179812_a);
      if (this.field_179812_a == SPacketTitle.Type.TITLE
         || this.field_179812_a == SPacketTitle.Type.SUBTITLE
         || this.field_179812_a == SPacketTitle.Type.ACTIONBAR) {
         ☃.func_179256_a(this.field_179810_b);
      }

      if (this.field_179812_a == SPacketTitle.Type.TIMES) {
         ☃.writeInt(this.field_179811_c);
         ☃.writeInt(this.field_179808_d);
         ☃.writeInt(this.field_179809_e);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_175099_a(this);
   }

   public SPacketTitle.Type func_179807_a() {
      return this.field_179812_a;
   }

   public ITextComponent func_179805_b() {
      return this.field_179810_b;
   }

   public int func_179806_c() {
      return this.field_179811_c;
   }

   public int func_179804_d() {
      return this.field_179808_d;
   }

   public int func_179803_e() {
      return this.field_179809_e;
   }

   public static enum Type {
      TITLE,
      SUBTITLE,
      ACTIONBAR,
      TIMES,
      CLEAR,
      RESET;
   }
}
