package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class CPacketUpdateStructureBlock implements Packet<INetHandlerPlayServer> {
   private BlockPos field_210391_a;
   private TileEntityStructure.UpdateCommand field_210392_b;
   private StructureMode field_210393_c;
   private String field_210394_d;
   private BlockPos field_210395_e;
   private BlockPos field_210396_f;
   private Mirror field_210397_g;
   private Rotation field_210398_h;
   private String field_210399_i;
   private boolean field_210400_j;
   private boolean field_210401_k;
   private boolean field_210402_l;
   private float field_210403_m;
   private long field_210404_n;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_210391_a = ☃.func_179259_c();
      this.field_210392_b = ☃.func_179257_a(TileEntityStructure.UpdateCommand.class);
      this.field_210393_c = ☃.func_179257_a(StructureMode.class);
      this.field_210394_d = ☃.func_150789_c(32767);
      this.field_210395_e = new BlockPos(
         MathHelper.func_76125_a(☃.readByte(), -32, 32), MathHelper.func_76125_a(☃.readByte(), -32, 32), MathHelper.func_76125_a(☃.readByte(), -32, 32)
      );
      this.field_210396_f = new BlockPos(
         MathHelper.func_76125_a(☃.readByte(), 0, 32), MathHelper.func_76125_a(☃.readByte(), 0, 32), MathHelper.func_76125_a(☃.readByte(), 0, 32)
      );
      this.field_210397_g = ☃.func_179257_a(Mirror.class);
      this.field_210398_h = ☃.func_179257_a(Rotation.class);
      this.field_210399_i = ☃.func_150789_c(12);
      this.field_210403_m = MathHelper.func_76131_a(☃.readFloat(), 0.0F, 1.0F);
      this.field_210404_n = ☃.func_179260_f();
      int ☃ = ☃.readByte();
      this.field_210400_j = (☃ & 1) != 0;
      this.field_210401_k = (☃ & 2) != 0;
      this.field_210402_l = (☃ & 4) != 0;
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179255_a(this.field_210391_a);
      ☃.func_179249_a(this.field_210392_b);
      ☃.func_179249_a(this.field_210393_c);
      ☃.func_180714_a(this.field_210394_d);
      ☃.writeByte(this.field_210395_e.func_177958_n());
      ☃.writeByte(this.field_210395_e.func_177956_o());
      ☃.writeByte(this.field_210395_e.func_177952_p());
      ☃.writeByte(this.field_210396_f.func_177958_n());
      ☃.writeByte(this.field_210396_f.func_177956_o());
      ☃.writeByte(this.field_210396_f.func_177952_p());
      ☃.func_179249_a(this.field_210397_g);
      ☃.func_179249_a(this.field_210398_h);
      ☃.func_180714_a(this.field_210399_i);
      ☃.writeFloat(this.field_210403_m);
      ☃.func_179254_b(this.field_210404_n);
      int ☃ = 0;
      if (this.field_210400_j) {
         ☃ |= 1;
      }

      if (this.field_210401_k) {
         ☃ |= 2;
      }

      if (this.field_210402_l) {
         ☃ |= 4;
      }

      ☃.writeByte(☃);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_210157_a(this);
   }

   public BlockPos func_210380_a() {
      return this.field_210391_a;
   }

   public TileEntityStructure.UpdateCommand func_210384_b() {
      return this.field_210392_b;
   }

   public StructureMode func_210378_c() {
      return this.field_210393_c;
   }

   public String func_210377_d() {
      return this.field_210394_d;
   }

   public BlockPos func_210383_e() {
      return this.field_210395_e;
   }

   public BlockPos func_210385_f() {
      return this.field_210396_f;
   }

   public Mirror func_210386_g() {
      return this.field_210397_g;
   }

   public Rotation func_210379_h() {
      return this.field_210398_h;
   }

   public String func_210388_i() {
      return this.field_210399_i;
   }

   public boolean func_210389_j() {
      return this.field_210400_j;
   }

   public boolean func_210390_k() {
      return this.field_210401_k;
   }

   public boolean func_210387_l() {
      return this.field_210402_l;
   }

   public float func_210382_m() {
      return this.field_210403_m;
   }

   public long func_210381_n() {
      return this.field_210404_n;
   }
}
