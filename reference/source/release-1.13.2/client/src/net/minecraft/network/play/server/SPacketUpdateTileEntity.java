package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketUpdateTileEntity implements Packet<INetHandlerPlayClient> {
   private BlockPos field_179824_a;
   private int field_148859_d;
   private NBTTagCompound field_148860_e;

   public SPacketUpdateTileEntity() {
   }

   public SPacketUpdateTileEntity(BlockPos var1, int var2, NBTTagCompound var3) {
      this.field_179824_a = ☃;
      this.field_148859_d = ☃;
      this.field_148860_e = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179824_a = ☃.func_179259_c();
      this.field_148859_d = ☃.readUnsignedByte();
      this.field_148860_e = ☃.func_150793_b();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179255_a(this.field_179824_a);
      ☃.writeByte((byte)this.field_148859_d);
      ☃.func_150786_a(this.field_148860_e);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147273_a(this);
   }

   public BlockPos func_179823_a() {
      return this.field_179824_a;
   }

   public int func_148853_f() {
      return this.field_148859_d;
   }

   public NBTTagCompound func_148857_g() {
      return this.field_148860_e;
   }
}
