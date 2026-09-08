package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

public class SPacketMultiBlockChange implements Packet<INetHandlerPlayClient> {
   private ChunkPos field_148925_b;
   private SPacketMultiBlockChange.BlockUpdateData[] field_179845_b;

   public SPacketMultiBlockChange() {
   }

   public SPacketMultiBlockChange(int var1, short[] var2, Chunk var3) {
      this.field_148925_b = new ChunkPos(☃.field_76635_g, ☃.field_76647_h);
      this.field_179845_b = new SPacketMultiBlockChange.BlockUpdateData[☃];

      for(int ☃ = 0; ☃ < this.field_179845_b.length; ++☃) {
         this.field_179845_b[☃] = new SPacketMultiBlockChange.BlockUpdateData(☃[☃], ☃);
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148925_b = new ChunkPos(☃.readInt(), ☃.readInt());
      this.field_179845_b = new SPacketMultiBlockChange.BlockUpdateData[☃.func_150792_a()];

      for(int ☃ = 0; ☃ < this.field_179845_b.length; ++☃) {
         this.field_179845_b[☃] = new SPacketMultiBlockChange.BlockUpdateData(☃.readShort(), Block.field_176229_d.func_148745_a(☃.func_150792_a()));
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_148925_b.field_77276_a);
      ☃.writeInt(this.field_148925_b.field_77275_b);
      ☃.func_150787_b(this.field_179845_b.length);

      for(SPacketMultiBlockChange.BlockUpdateData ☃ : this.field_179845_b) {
         ☃.writeShort(☃.func_180089_b());
         ☃.func_150787_b(Block.func_196246_j(☃.func_180088_c()));
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147287_a(this);
   }

   public class BlockUpdateData {
      private final short field_180091_b;
      private final IBlockState field_180092_c;

      public BlockUpdateData(short var2, IBlockState var3) {
         this.field_180091_b = ☃;
         this.field_180092_c = ☃;
      }

      public BlockUpdateData(short var2, Chunk var3) {
         this.field_180091_b = ☃;
         this.field_180092_c = ☃.func_180495_p(this.func_180090_a());
      }

      public BlockPos func_180090_a() {
         return new BlockPos(
            SPacketMultiBlockChange.this.field_148925_b.func_180331_a(this.field_180091_b >> 12 & 15, this.field_180091_b & 255, this.field_180091_b >> 8 & 15)
         );
      }

      public short func_180089_b() {
         return this.field_180091_b;
      }

      public IBlockState func_180088_c() {
         return this.field_180092_c;
      }
   }
}
