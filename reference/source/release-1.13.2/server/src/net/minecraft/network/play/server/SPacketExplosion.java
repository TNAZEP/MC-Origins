package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SPacketExplosion implements Packet<INetHandlerPlayClient> {
   private double field_149158_a;
   private double field_149156_b;
   private double field_149157_c;
   private float field_149154_d;
   private List<BlockPos> field_149155_e;
   private float field_149152_f;
   private float field_149153_g;
   private float field_149159_h;

   public SPacketExplosion() {
   }

   public SPacketExplosion(double var1, double var3, double var5, float var7, List<BlockPos> var8, Vec3d var9) {
      this.field_149158_a = ☃;
      this.field_149156_b = ☃;
      this.field_149157_c = ☃;
      this.field_149154_d = ☃;
      this.field_149155_e = Lists.<BlockPos>newArrayList(☃);
      if (☃ != null) {
         this.field_149152_f = (float)☃.field_72450_a;
         this.field_149153_g = (float)☃.field_72448_b;
         this.field_149159_h = (float)☃.field_72449_c;
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149158_a = (double)☃.readFloat();
      this.field_149156_b = (double)☃.readFloat();
      this.field_149157_c = (double)☃.readFloat();
      this.field_149154_d = ☃.readFloat();
      int ☃ = ☃.readInt();
      this.field_149155_e = Lists.<BlockPos>newArrayListWithCapacity(☃);
      int ☃x = (int)this.field_149158_a;
      int ☃xx = (int)this.field_149156_b;
      int ☃xxx = (int)this.field_149157_c;

      for(int ☃xxxx = 0; ☃xxxx < ☃; ++☃xxxx) {
         int ☃xxxxx = ☃.readByte() + ☃x;
         int ☃xxxxxx = ☃.readByte() + ☃xx;
         int ☃xxxxxxx = ☃.readByte() + ☃xxx;
         this.field_149155_e.add(new BlockPos(☃xxxxx, ☃xxxxxx, ☃xxxxxxx));
      }

      this.field_149152_f = ☃.readFloat();
      this.field_149153_g = ☃.readFloat();
      this.field_149159_h = ☃.readFloat();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeFloat((float)this.field_149158_a);
      ☃.writeFloat((float)this.field_149156_b);
      ☃.writeFloat((float)this.field_149157_c);
      ☃.writeFloat(this.field_149154_d);
      ☃.writeInt(this.field_149155_e.size());
      int ☃ = (int)this.field_149158_a;
      int ☃x = (int)this.field_149156_b;
      int ☃xx = (int)this.field_149157_c;

      for(BlockPos ☃xxx : this.field_149155_e) {
         int ☃xxxx = ☃xxx.func_177958_n() - ☃;
         int ☃xxxxx = ☃xxx.func_177956_o() - ☃x;
         int ☃xxxxxx = ☃xxx.func_177952_p() - ☃xx;
         ☃.writeByte(☃xxxx);
         ☃.writeByte(☃xxxxx);
         ☃.writeByte(☃xxxxxx);
      }

      ☃.writeFloat(this.field_149152_f);
      ☃.writeFloat(this.field_149153_g);
      ☃.writeFloat(this.field_149159_h);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147283_a(this);
   }
}
