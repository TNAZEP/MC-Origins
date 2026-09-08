package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.IWorldWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseLightEngine implements ILightEngine {
   private static final Logger field_202672_b = LogManager.getLogger();
   private static final EnumFacing[] field_202674_d = EnumFacing.values();
   private final IntPriorityQueue field_202673_c = new IntArrayFIFOQueue(786);

   public int func_202666_a(IWorldReaderBase var1, BlockPos var2) {
      return ☃.func_175642_b(this.func_202657_a(), ☃);
   }

   public void func_202667_a(IWorldWriter var1, BlockPos var2, int var3) {
      ☃.func_175653_a(this.func_202657_a(), ☃, ☃);
   }

   protected int func_202665_b(IBlockReader var1, BlockPos var2) {
      return ☃.func_180495_p(☃).func_200016_a(☃, ☃);
   }

   protected int func_202670_c(IBlockReader var1, BlockPos var2) {
      return ☃.func_180495_p(☃).func_185906_d();
   }

   private int func_202662_a(@Nullable EnumFacing var1, int var2, int var3, int var4, int var5) {
      int ☃ = 7;
      if (☃ != null) {
         ☃ = ☃.ordinal();
      }

      return ☃ << 24 | ☃ << 18 | ☃ << 10 | ☃ << 4 | ☃ << 0;
   }

   private int func_202660_a(int var1) {
      return ☃ >> 18 & 63;
   }

   private int func_202668_b(int var1) {
      return ☃ >> 10 & 0xFF;
   }

   private int func_202658_c(int var1) {
      return ☃ >> 4 & 63;
   }

   private int func_202663_d(int var1) {
      return ☃ >> 0 & 15;
   }

   @Nullable
   private EnumFacing func_202661_e(int var1) {
      int ☃ = ☃ >> 24 & 7;
      return ☃ == 7 ? null : EnumFacing.values()[☃ >> 24 & 7];
   }

   protected void func_202664_a(IWorld var1, ChunkPos var2) {
      try (BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         while(!this.field_202673_c.isEmpty()) {
            int ☃x = this.field_202673_c.dequeueInt();
            int ☃xx = this.func_202663_d(☃x);
            int ☃xxx = this.func_202660_a(☃x) - 16;
            int ☃xxxx = this.func_202668_b(☃x);
            int ☃xxxxx = this.func_202658_c(☃x) - 16;
            EnumFacing ☃xxxxxx = this.func_202661_e(☃x);

            for(EnumFacing ☃xxxxxxx : field_202674_d) {
               if (☃xxxxxxx != ☃xxxxxx) {
                  int ☃xxxxxxxx = ☃xxx + ☃xxxxxxx.func_82601_c();
                  int ☃xxxxxxxxx = ☃xxxx + ☃xxxxxxx.func_96559_d();
                  int ☃xxxxxxxxxx = ☃xxxxx + ☃xxxxxxx.func_82599_e();
                  if (☃xxxxxxxxx <= 255 && ☃xxxxxxxxx >= 0) {
                     ☃.func_181079_c(☃xxxxxxxx + ☃.func_180334_c(), ☃xxxxxxxxx, ☃xxxxxxxxxx + ☃.func_180333_d());
                     int ☃xxxxxxxxxxx = this.func_202665_b(☃, ☃);
                     int ☃xxxxxxxxxxxx = ☃xx - Math.max(☃xxxxxxxxxxx, 1);
                     if (☃xxxxxxxxxxxx > 0 && ☃xxxxxxxxxxxx > this.func_202666_a(☃, ☃)) {
                        this.func_202667_a(☃, ☃, ☃xxxxxxxxxxxx);
                        this.func_202659_a(☃, ☃, ☃xxxxxxxxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   protected void func_202669_a(ChunkPos var1, int var2, int var3, int var4, int var5) {
      int ☃ = ☃ - ☃.func_180334_c() + 16;
      int ☃x = ☃ - ☃.func_180333_d() + 16;
      this.field_202673_c.enqueue(this.func_202662_a(null, ☃, ☃, ☃x, ☃));
   }

   protected void func_202659_a(ChunkPos var1, BlockPos var2, int var3) {
      this.func_202669_a(☃, ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃);
   }
}
