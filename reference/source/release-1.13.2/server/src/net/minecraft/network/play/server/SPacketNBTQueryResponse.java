package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketNBTQueryResponse implements Packet<INetHandlerPlayClient> {
   private int field_211714_a;
   @Nullable
   private NBTTagCompound field_211715_b;

   public SPacketNBTQueryResponse() {
   }

   public SPacketNBTQueryResponse(int var1, @Nullable NBTTagCompound var2) {
      this.field_211714_a = ☃;
      this.field_211715_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_211714_a = ☃.func_150792_a();
      this.field_211715_b = ☃.func_150793_b();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_211714_a);
      ☃.func_150786_a(this.field_211715_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_211522_a(this);
   }

   @Override
   public boolean func_211402_a() {
      return true;
   }
}
