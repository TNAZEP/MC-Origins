package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

public class SPacketChunkData implements Packet<INetHandlerPlayClient> {
   private int field_149284_a;
   private int field_149282_b;
   private int field_186948_c;
   private byte[] field_186949_d;
   private List<NBTTagCompound> field_189557_e;
   private boolean field_149279_g;

   public SPacketChunkData() {
   }

   public SPacketChunkData(Chunk var1, int var2) {
      this.field_149284_a = ☃.field_76635_g;
      this.field_149282_b = ☃.field_76647_h;
      this.field_149279_g = ☃ == 65535;
      boolean ☃ = ☃.func_177412_p().field_73011_w.func_191066_m();
      this.field_186949_d = new byte[this.func_189556_a(☃, ☃, ☃)];
      this.field_186948_c = this.func_189555_a(new PacketBuffer(this.func_186945_f()), ☃, ☃, ☃);
      this.field_189557_e = Lists.<NBTTagCompound>newArrayList();

      for(Entry<BlockPos, TileEntity> ☃x : ☃.func_177434_r().entrySet()) {
         BlockPos ☃xx = (BlockPos)☃x.getKey();
         TileEntity ☃xxx = (TileEntity)☃x.getValue();
         int ☃xxxx = ☃xx.func_177956_o() >> 4;
         if (this.func_149274_i() || (☃ & 1 << ☃xxxx) != 0) {
            NBTTagCompound ☃xxxxx = ☃xxx.func_189517_E_();
            this.field_189557_e.add(☃xxxxx);
         }
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149284_a = ☃.readInt();
      this.field_149282_b = ☃.readInt();
      this.field_149279_g = ☃.readBoolean();
      this.field_186948_c = ☃.func_150792_a();
      int ☃ = ☃.func_150792_a();
      if (☃ > 2097152) {
         throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
      } else {
         this.field_186949_d = new byte[☃];
         ☃.readBytes(this.field_186949_d);
         int ☃ = ☃.func_150792_a();
         this.field_189557_e = Lists.<NBTTagCompound>newArrayList();

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            this.field_189557_e.add(☃.func_150793_b());
         }
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_149284_a);
      ☃.writeInt(this.field_149282_b);
      ☃.writeBoolean(this.field_149279_g);
      ☃.func_150787_b(this.field_186948_c);
      ☃.func_150787_b(this.field_186949_d.length);
      ☃.writeBytes(this.field_186949_d);
      ☃.func_150787_b(this.field_189557_e.size());

      for(NBTTagCompound ☃ : this.field_189557_e) {
         ☃.func_150786_a(☃);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147263_a(this);
   }

   private ByteBuf func_186945_f() {
      ByteBuf ☃ = Unpooled.wrappedBuffer(this.field_186949_d);
      ☃.writerIndex(0);
      return ☃;
   }

   public int func_189555_a(PacketBuffer var1, Chunk var2, boolean var3, int var4) {
      int ☃ = 0;
      ChunkSection[] ☃x = ☃.func_76587_i();
      int ☃xx = 0;

      for(int ☃xxx = ☃x.length; ☃xx < ☃xxx; ++☃xx) {
         ChunkSection ☃xxxx = ☃x[☃xx];
         if (☃xxxx != Chunk.field_186036_a && (!this.func_149274_i() || !☃xxxx.func_76663_a()) && (☃ & 1 << ☃xx) != 0) {
            ☃ |= 1 << ☃xx;
            ☃xxxx.func_186049_g().func_186009_b(☃);
            ☃.writeBytes(☃xxxx.func_76661_k().func_177481_a());
            if (☃) {
               ☃.writeBytes(☃xxxx.func_76671_l().func_177481_a());
            }
         }
      }

      if (this.func_149274_i()) {
         Biome[] ☃xxx = ☃.func_201590_e();

         for(int ☃xxxx = 0; ☃xxxx < ☃xxx.length; ++☃xxxx) {
            ☃.writeInt(IRegistry.field_212624_m.func_148757_b(☃xxx[☃xxxx]));
         }
      }

      return ☃;
   }

   protected int func_189556_a(Chunk var1, boolean var2, int var3) {
      int ☃ = 0;
      ChunkSection[] ☃x = ☃.func_76587_i();
      int ☃xx = 0;

      for(int ☃xxx = ☃x.length; ☃xx < ☃xxx; ++☃xx) {
         ChunkSection ☃xxxx = ☃x[☃xx];
         if (☃xxxx != Chunk.field_186036_a && (!this.func_149274_i() || !☃xxxx.func_76663_a()) && (☃ & 1 << ☃xx) != 0) {
            ☃ += ☃xxxx.func_186049_g().func_186018_a();
            ☃ += ☃xxxx.func_76661_k().func_177481_a().length;
            if (☃) {
               ☃ += ☃xxxx.func_76671_l().func_177481_a().length;
            }
         }
      }

      if (this.func_149274_i()) {
         ☃ += ☃.func_201590_e().length * 4;
      }

      return ☃;
   }

   public boolean func_149274_i() {
      return this.field_149279_g;
   }
}
