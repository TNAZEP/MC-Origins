package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketOpenWindow implements Packet<INetHandlerPlayClient> {
   private int field_148909_a;
   private String field_148907_b;
   private ITextComponent field_148908_c;
   private int field_148905_d;
   private int field_148904_f;

   public SPacketOpenWindow() {
   }

   public SPacketOpenWindow(int var1, String var2, ITextComponent var3) {
      this(☃, ☃, ☃, 0);
   }

   public SPacketOpenWindow(int var1, String var2, ITextComponent var3, int var4) {
      this.field_148909_a = ☃;
      this.field_148907_b = ☃;
      this.field_148908_c = ☃;
      this.field_148905_d = ☃;
   }

   public SPacketOpenWindow(int var1, String var2, ITextComponent var3, int var4, int var5) {
      this(☃, ☃, ☃, ☃);
      this.field_148904_f = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147265_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148909_a = ☃.readUnsignedByte();
      this.field_148907_b = ☃.func_150789_c(32);
      this.field_148908_c = ☃.func_179258_d();
      this.field_148905_d = ☃.readUnsignedByte();
      if (this.field_148907_b.equals("EntityHorse")) {
         this.field_148904_f = ☃.readInt();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_148909_a);
      ☃.func_180714_a(this.field_148907_b);
      ☃.func_179256_a(this.field_148908_c);
      ☃.writeByte(this.field_148905_d);
      if (this.field_148907_b.equals("EntityHorse")) {
         ☃.writeInt(this.field_148904_f);
      }
   }

   public int func_148901_c() {
      return this.field_148909_a;
   }

   public String func_148902_e() {
      return this.field_148907_b;
   }

   public ITextComponent func_179840_c() {
      return this.field_148908_c;
   }

   public int func_148898_f() {
      return this.field_148905_d;
   }

   public int func_148897_h() {
      return this.field_148904_f;
   }

   public boolean func_148900_g() {
      return this.field_148905_d > 0;
   }
}
