package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class PathFinder {
   private final PathHeap field_75866_b = new PathHeap();
   private final Set<PathPoint> field_186337_b = Sets.<PathPoint>newHashSet();
   private final PathPoint[] field_75864_d = new PathPoint[32];
   private NodeProcessor field_176190_c;

   public PathFinder(NodeProcessor var1) {
      this.field_176190_c = ☃;
   }

   @Nullable
   public Path func_186333_a(IBlockReader var1, EntityLiving var2, Entity var3, float var4) {
      return this.func_186334_a(☃, ☃, ☃.field_70165_t, ☃.func_174813_aQ().field_72338_b, ☃.field_70161_v, ☃);
   }

   @Nullable
   public Path func_186336_a(IBlockReader var1, EntityLiving var2, BlockPos var3, float var4) {
      return this.func_186334_a(
         ☃, ☃, (double)((float)☃.func_177958_n() + 0.5F), (double)((float)☃.func_177956_o() + 0.5F), (double)((float)☃.func_177952_p() + 0.5F), ☃
      );
   }

   @Nullable
   private Path func_186334_a(IBlockReader var1, EntityLiving var2, double var3, double var5, double var7, float var9) {
      this.field_75866_b.func_75848_a();
      this.field_176190_c.func_186315_a(☃, ☃);
      PathPoint ☃ = this.field_176190_c.func_186318_b();
      PathPoint ☃x = this.field_176190_c.func_186325_a(☃, ☃, ☃);
      Path ☃xx = this.func_186335_a(☃, ☃x, ☃);
      this.field_176190_c.func_176163_a();
      return ☃xx;
   }

   @Nullable
   private Path func_186335_a(PathPoint var1, PathPoint var2, float var3) {
      ☃.field_75836_e = 0.0F;
      ☃.field_75833_f = ☃.func_186281_c(☃);
      ☃.field_75834_g = ☃.field_75833_f;
      this.field_75866_b.func_75848_a();
      this.field_186337_b.clear();
      this.field_75866_b.func_75849_a(☃);
      PathPoint ☃ = ☃;
      int ☃x = 0;

      while(!this.field_75866_b.func_75845_e()) {
         if (++☃x >= 200) {
            break;
         }

         PathPoint ☃xx = this.field_75866_b.func_75844_c();
         if (☃xx.equals(☃)) {
            ☃ = ☃;
            break;
         }

         if (☃xx.func_186281_c(☃) < ☃.func_186281_c(☃)) {
            ☃ = ☃xx;
         }

         ☃xx.field_75842_i = true;
         int ☃xx = this.field_176190_c.func_186320_a(this.field_75864_d, ☃xx, ☃, ☃);

         for(int ☃xxx = 0; ☃xxx < ☃xx; ++☃xxx) {
            PathPoint ☃xxxx = this.field_75864_d[☃xxx];
            float ☃xxxxx = ☃xx.func_186281_c(☃xxxx);
            ☃xxxx.field_186284_j = ☃xx.field_186284_j + ☃xxxxx;
            ☃xxxx.field_186285_k = ☃xxxxx + ☃xxxx.field_186286_l;
            float ☃xxxxxx = ☃xx.field_75836_e + ☃xxxx.field_186285_k;
            if (☃xxxx.field_186284_j < ☃ && (!☃xxxx.func_75831_a() || ☃xxxxxx < ☃xxxx.field_75836_e)) {
               ☃xxxx.field_75841_h = ☃xx;
               ☃xxxx.field_75836_e = ☃xxxxxx;
               ☃xxxx.field_75833_f = ☃xxxx.func_186281_c(☃) + ☃xxxx.field_186286_l;
               if (☃xxxx.func_75831_a()) {
                  this.field_75866_b.func_75850_a(☃xxxx, ☃xxxx.field_75836_e + ☃xxxx.field_75833_f);
               } else {
                  ☃xxxx.field_75834_g = ☃xxxx.field_75836_e + ☃xxxx.field_75833_f;
                  this.field_75866_b.func_75849_a(☃xxxx);
               }
            }
         }
      }

      return ☃ == ☃ ? null : this.func_75853_a(☃, ☃);
   }

   private Path func_75853_a(PathPoint var1, PathPoint var2) {
      int ☃ = 1;

      for(PathPoint ☃x = ☃; ☃x.field_75841_h != null; ☃x = ☃x.field_75841_h) {
         ++☃;
      }

      PathPoint[] ☃x = new PathPoint[☃];
      PathPoint var7 = ☃;
      --☃;

      for(☃x[☃] = ☃; var7.field_75841_h != null; ☃x[☃] = var7) {
         var7 = var7.field_75841_h;
         --☃;
      }

      return new Path(☃x);
   }
}
