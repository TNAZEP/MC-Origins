package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class CPacketPlayerTryUseItemOnBlock implements Packet<INetHandlerPlayServer> {
   private BlockPos field_179725_b;
   private EnumFacing field_149579_d;
   private EnumHand field_187027_c;
   private float field_149577_f;
   private float field_149578_g;
   private float field_149584_h;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179725_b = ☃.func_179259_c();
      this.field_149579_d = ☃.func_179257_a(EnumFacing.class);
      this.field_187027_c = ☃.func_179257_a(EnumHand.class);
      this.field_149577_f = ☃.readFloat();
      this.field_149578_g = ☃.readFloat();
      this.field_149584_h = ☃.readFloat();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179255_a(this.field_179725_b);
      ☃.func_179249_a(this.field_149579_d);
      ☃.func_179249_a(this.field_187027_c);
      ☃.writeFloat(this.field_149577_f);
      ☃.writeFloat(this.field_149578_g);
      ☃.writeFloat(this.field_149584_h);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_184337_a(this);
   }

   public BlockPos func_187023_a() {
      return this.field_179725_b;
   }

   public EnumFacing func_187024_b() {
      return this.field_149579_d;
   }

   public EnumHand func_187022_c() {
      return this.field_187027_c;
   }

   public float func_187026_d() {
      return this.field_149577_f;
   }

   public float func_187025_e() {
      return this.field_149578_g;
   }

   public float func_187020_f() {
      return this.field_149584_h;
   }
}
