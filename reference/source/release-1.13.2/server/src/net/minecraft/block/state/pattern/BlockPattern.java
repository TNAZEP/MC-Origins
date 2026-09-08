package net.minecraft.block.state.pattern;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorldReaderBase;

public class BlockPattern {
   private final Predicate<BlockWorldState>[][][] field_177689_a;
   private final int field_177687_b;
   private final int field_177688_c;
   private final int field_177686_d;

   public BlockPattern(Predicate<BlockWorldState>[][][] var1) {
      this.field_177689_a = ☃;
      this.field_177687_b = ☃.length;
      if (this.field_177687_b > 0) {
         this.field_177688_c = ☃[0].length;
         if (this.field_177688_c > 0) {
            this.field_177686_d = ☃[0][0].length;
         } else {
            this.field_177686_d = 0;
         }
      } else {
         this.field_177688_c = 0;
         this.field_177686_d = 0;
      }
   }

   public int func_185922_a() {
      return this.field_177687_b;
   }

   public int func_177685_b() {
      return this.field_177688_c;
   }

   public int func_177684_c() {
      return this.field_177686_d;
   }

   @Nullable
   private BlockPattern.PatternHelper func_177682_a(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache<BlockPos, BlockWorldState> var4) {
      for(int ☃ = 0; ☃ < this.field_177686_d; ++☃) {
         for(int ☃x = 0; ☃x < this.field_177688_c; ++☃x) {
            for(int ☃xx = 0; ☃xx < this.field_177687_b; ++☃xx) {
               if (!this.field_177689_a[☃xx][☃x][☃].test(☃.getUnchecked(func_177683_a(☃, ☃, ☃, ☃, ☃x, ☃xx)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.PatternHelper(☃, ☃, ☃, ☃, this.field_177686_d, this.field_177688_c, this.field_177687_b);
   }

   @Nullable
   public BlockPattern.PatternHelper func_177681_a(IWorldReaderBase var1, BlockPos var2) {
      LoadingCache<BlockPos, BlockWorldState> ☃ = func_181627_a(☃, false);
      int ☃x = Math.max(Math.max(this.field_177686_d, this.field_177688_c), this.field_177687_b);

      for(BlockPos ☃xx : BlockPos.func_177980_a(☃, ☃.func_177982_a(☃x - 1, ☃x - 1, ☃x - 1))) {
         for(EnumFacing ☃xxx : EnumFacing.values()) {
            for(EnumFacing ☃xxxx : EnumFacing.values()) {
               if (☃xxxx != ☃xxx && ☃xxxx != ☃xxx.func_176734_d()) {
                  BlockPattern.PatternHelper ☃xxxxx = this.func_177682_a(☃xx, ☃xxx, ☃xxxx, ☃);
                  if (☃xxxxx != null) {
                     return ☃xxxxx;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPos, BlockWorldState> func_181627_a(IWorldReaderBase var0, boolean var1) {
      return CacheBuilder.newBuilder().build(new BlockPattern.CacheLoader(☃, ☃));
   }

   protected static BlockPos func_177683_a(BlockPos var0, EnumFacing var1, EnumFacing var2, int var3, int var4, int var5) {
      if (☃ != ☃ && ☃ != ☃.func_176734_d()) {
         Vec3i ☃ = new Vec3i(☃.func_82601_c(), ☃.func_96559_d(), ☃.func_82599_e());
         Vec3i ☃x = new Vec3i(☃.func_82601_c(), ☃.func_96559_d(), ☃.func_82599_e());
         Vec3i ☃xx = ☃.func_177955_d(☃x);
         return ☃.func_177982_a(
            ☃x.func_177958_n() * -☃ + ☃xx.func_177958_n() * ☃ + ☃.func_177958_n() * ☃,
            ☃x.func_177956_o() * -☃ + ☃xx.func_177956_o() * ☃ + ☃.func_177956_o() * ☃,
            ☃x.func_177952_p() * -☃ + ☃xx.func_177952_p() * ☃ + ☃.func_177952_p() * ☃
         );
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class CacheLoader extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState> {
      private final IWorldReaderBase field_177680_a;
      private final boolean field_181626_b;

      public CacheLoader(IWorldReaderBase var1, boolean var2) {
         this.field_177680_a = ☃;
         this.field_181626_b = ☃;
      }

      public BlockWorldState load(BlockPos var1) throws Exception {
         return new BlockWorldState(this.field_177680_a, ☃, this.field_181626_b);
      }
   }

   public static class PatternHelper {
      private final BlockPos field_177674_a;
      private final EnumFacing field_177672_b;
      private final EnumFacing field_177673_c;
      private final LoadingCache<BlockPos, BlockWorldState> field_177671_d;
      private final int field_181120_e;
      private final int field_181121_f;
      private final int field_181122_g;

      public PatternHelper(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache<BlockPos, BlockWorldState> var4, int var5, int var6, int var7) {
         this.field_177674_a = ☃;
         this.field_177672_b = ☃;
         this.field_177673_c = ☃;
         this.field_177671_d = ☃;
         this.field_181120_e = ☃;
         this.field_181121_f = ☃;
         this.field_181122_g = ☃;
      }

      public BlockPos func_181117_a() {
         return this.field_177674_a;
      }

      public EnumFacing func_177669_b() {
         return this.field_177672_b;
      }

      public EnumFacing func_177668_c() {
         return this.field_177673_c;
      }

      public int func_181118_d() {
         return this.field_181120_e;
      }

      public int func_181119_e() {
         return this.field_181121_f;
      }

      public BlockWorldState func_177670_a(int var1, int var2, int var3) {
         return this.field_177671_d.getUnchecked(BlockPattern.func_177683_a(this.field_177674_a, this.func_177669_b(), this.func_177668_c(), ☃, ☃, ☃));
      }

      public String toString() {
         return MoreObjects.toStringHelper(this)
            .add("up", this.field_177673_c)
            .add("forwards", this.field_177672_b)
            .add("frontTopLeft", this.field_177674_a)
            .toString();
      }
   }
}
