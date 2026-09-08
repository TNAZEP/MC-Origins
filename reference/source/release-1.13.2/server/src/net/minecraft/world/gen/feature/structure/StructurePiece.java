package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class StructurePiece {
   protected static final IBlockState field_202556_l = Blocks.field_201941_jj.func_176223_P();
   protected MutableBoundingBox field_74887_e;
   @Nullable
   private EnumFacing field_74885_f;
   private Mirror field_186168_b;
   private Rotation field_186169_c;
   protected int field_74886_g;
   private static final Set<Block> field_211413_d = ImmutableSet.<Block>builder()
      .add(Blocks.field_150386_bk)
      .add(Blocks.field_150478_aa)
      .add(Blocks.field_196591_bQ)
      .add(Blocks.field_180407_aO)
      .add(Blocks.field_180408_aP)
      .add(Blocks.field_180406_aS)
      .add(Blocks.field_180405_aT)
      .add(Blocks.field_180404_aQ)
      .add(Blocks.field_180403_aR)
      .add(Blocks.field_150468_ap)
      .add(Blocks.field_150411_aY)
      .build();

   public StructurePiece() {
   }

   protected StructurePiece(int var1) {
      this.field_74886_g = ☃;
   }

   public final NBTTagCompound func_143010_b() {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74778_a("id", StructureIO.func_143036_a(this));
      ☃.func_74782_a("BB", this.field_74887_e.func_151535_h());
      EnumFacing ☃x = this.func_186165_e();
      ☃.func_74768_a("O", ☃x == null ? -1 : ☃x.func_176736_b());
      ☃.func_74768_a("GD", this.field_74886_g);
      this.func_143012_a(☃);
      return ☃;
   }

   protected abstract void func_143012_a(NBTTagCompound var1);

   public void func_143009_a(IWorld var1, NBTTagCompound var2) {
      if (☃.func_74764_b("BB")) {
         this.field_74887_e = new MutableBoundingBox(☃.func_74759_k("BB"));
      }

      int ☃ = ☃.func_74762_e("O");
      this.func_186164_a(☃ == -1 ? null : EnumFacing.func_176731_b(☃));
      this.field_74886_g = ☃.func_74762_e("GD");
      this.func_143011_b(☃, ☃.func_72860_G().func_186340_h());
   }

   protected abstract void func_143011_b(NBTTagCompound var1, TemplateManager var2);

   public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
   }

   public abstract boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4);

   public MutableBoundingBox func_74874_b() {
      return this.field_74887_e;
   }

   public int func_74877_c() {
      return this.field_74886_g;
   }

   public static StructurePiece func_74883_a(List<StructurePiece> var0, MutableBoundingBox var1) {
      for(StructurePiece ☃ : ☃) {
         if (☃.func_74874_b() != null && ☃.func_74874_b().func_78884_a(☃)) {
            return ☃;
         }
      }

      return null;
   }

   protected boolean func_74860_a(IBlockReader var1, MutableBoundingBox var2) {
      int ☃ = Math.max(this.field_74887_e.field_78897_a - 1, ☃.field_78897_a);
      int ☃x = Math.max(this.field_74887_e.field_78895_b - 1, ☃.field_78895_b);
      int ☃xx = Math.max(this.field_74887_e.field_78896_c - 1, ☃.field_78896_c);
      int ☃xxx = Math.min(this.field_74887_e.field_78893_d + 1, ☃.field_78893_d);
      int ☃xxxx = Math.min(this.field_74887_e.field_78894_e + 1, ☃.field_78894_e);
      int ☃xxxxx = Math.min(this.field_74887_e.field_78892_f + 1, ☃.field_78892_f);
      BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxxx = ☃; ☃xxxxxxx <= ☃xxx; ++☃xxxxxxx) {
         for(int ☃xxxxxxxx = ☃xx; ☃xxxxxxxx <= ☃xxxxx; ++☃xxxxxxxx) {
            if (☃.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃x, ☃xxxxxxxx)).func_185904_a().func_76224_d()) {
               return true;
            }

            if (☃.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxx, ☃xxxxxxxx)).func_185904_a().func_76224_d()) {
               return true;
            }
         }
      }

      for(int ☃xxxxxxx = ☃; ☃xxxxxxx <= ☃xxx; ++☃xxxxxxx) {
         for(int ☃xxxxxxxx = ☃x; ☃xxxxxxxx <= ☃xxxx; ++☃xxxxxxxx) {
            if (☃.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxx, ☃xx)).func_185904_a().func_76224_d()) {
               return true;
            }

            if (☃.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxx)).func_185904_a().func_76224_d()) {
               return true;
            }
         }
      }

      for(int ☃xxxxxxx = ☃xx; ☃xxxxxxx <= ☃xxxxx; ++☃xxxxxxx) {
         for(int ☃xxxxxxxx = ☃x; ☃xxxxxxxx <= ☃xxxx; ++☃xxxxxxxx) {
            if (☃.func_180495_p(☃xxxxxx.func_181079_c(☃, ☃xxxxxxxx, ☃xxxxxxx)).func_185904_a().func_76224_d()) {
               return true;
            }

            if (☃.func_180495_p(☃xxxxxx.func_181079_c(☃xxx, ☃xxxxxxxx, ☃xxxxxxx)).func_185904_a().func_76224_d()) {
               return true;
            }
         }
      }

      return false;
   }

   protected int func_74865_a(int var1, int var2) {
      EnumFacing ☃ = this.func_186165_e();
      if (☃ == null) {
         return ☃;
      } else {
         switch(☃) {
            case NORTH:
            case SOUTH:
               return this.field_74887_e.field_78897_a + ☃;
            case WEST:
               return this.field_74887_e.field_78893_d - ☃;
            case EAST:
               return this.field_74887_e.field_78897_a + ☃;
            default:
               return ☃;
         }
      }
   }

   protected int func_74862_a(int var1) {
      return this.func_186165_e() == null ? ☃ : ☃ + this.field_74887_e.field_78895_b;
   }

   protected int func_74873_b(int var1, int var2) {
      EnumFacing ☃ = this.func_186165_e();
      if (☃ == null) {
         return ☃;
      } else {
         switch(☃) {
            case NORTH:
               return this.field_74887_e.field_78892_f - ☃;
            case SOUTH:
               return this.field_74887_e.field_78896_c + ☃;
            case WEST:
            case EAST:
               return this.field_74887_e.field_78896_c + ☃;
            default:
               return ☃;
         }
      }
   }

   protected void func_175811_a(IWorld var1, IBlockState var2, int var3, int var4, int var5, MutableBoundingBox var6) {
      BlockPos ☃ = new BlockPos(this.func_74865_a(☃, ☃), this.func_74862_a(☃), this.func_74873_b(☃, ☃));
      if (☃.func_175898_b(☃)) {
         if (this.field_186168_b != Mirror.NONE) {
            ☃ = ☃.func_185902_a(this.field_186168_b);
         }

         if (this.field_186169_c != Rotation.NONE) {
            ☃ = ☃.func_185907_a(this.field_186169_c);
         }

         ☃.func_180501_a(☃, ☃, 2);
         IFluidState ☃x = ☃.func_204610_c(☃);
         if (!☃x.func_206888_e()) {
            ☃.func_205219_F_().func_205360_a(☃, ☃x.func_206886_c(), 0);
         }

         if (field_211413_d.contains(☃.func_177230_c())) {
            ☃.func_205771_y(☃).func_201594_d(☃);
         }
      }
   }

   protected IBlockState func_175807_a(IBlockReader var1, int var2, int var3, int var4, MutableBoundingBox var5) {
      int ☃ = this.func_74865_a(☃, ☃);
      int ☃x = this.func_74862_a(☃);
      int ☃xx = this.func_74873_b(☃, ☃);
      BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
      return !☃.func_175898_b(☃xxx) ? Blocks.field_150350_a.func_176223_P() : ☃.func_180495_p(☃xxx);
   }

   protected boolean func_189916_b(IWorldReaderBase var1, int var2, int var3, int var4, MutableBoundingBox var5) {
      int ☃ = this.func_74865_a(☃, ☃);
      int ☃x = this.func_74862_a(☃ + 1);
      int ☃xx = this.func_74873_b(☃, ☃);
      BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
      if (!☃.func_175898_b(☃xxx)) {
         return false;
      } else {
         return ☃x < ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR_WG, ☃, ☃xx);
      }
   }

   protected void func_74878_a(IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      for(int ☃ = ☃; ☃ <= ☃; ++☃) {
         for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
            for(int ☃xx = ☃; ☃xx <= ☃; ++☃xx) {
               this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), ☃x, ☃, ☃xx, ☃);
            }
         }
      }
   }

   protected void func_175804_a(
      IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, IBlockState var10, boolean var11
   ) {
      for(int ☃ = ☃; ☃ <= ☃; ++☃) {
         for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
            for(int ☃xx = ☃; ☃xx <= ☃; ++☃xx) {
               if (!☃ || !this.func_175807_a(☃, ☃x, ☃, ☃xx, ☃).func_196958_f()) {
                  if (☃ != ☃ && ☃ != ☃ && ☃x != ☃ && ☃x != ☃ && ☃xx != ☃ && ☃xx != ☃) {
                     this.func_175811_a(☃, ☃, ☃x, ☃, ☃xx, ☃);
                  } else {
                     this.func_175811_a(☃, ☃, ☃x, ☃, ☃xx, ☃);
                  }
               }
            }
         }
      }
   }

   protected void func_74882_a(
      IWorld var1,
      MutableBoundingBox var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9,
      Random var10,
      StructurePiece.BlockSelector var11
   ) {
      for(int ☃ = ☃; ☃ <= ☃; ++☃) {
         for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
            for(int ☃xx = ☃; ☃xx <= ☃; ++☃xx) {
               if (!☃ || !this.func_175807_a(☃, ☃x, ☃, ☃xx, ☃).func_196958_f()) {
                  ☃.func_75062_a(☃, ☃x, ☃, ☃xx, ☃ == ☃ || ☃ == ☃ || ☃x == ☃ || ☃x == ☃ || ☃xx == ☃ || ☃xx == ☃);
                  this.func_175811_a(☃, ☃.func_180780_a(), ☃x, ☃, ☃xx, ☃);
               }
            }
         }
      }
   }

   protected void func_189914_a(
      IWorld var1,
      MutableBoundingBox var2,
      Random var3,
      float var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      IBlockState var11,
      IBlockState var12,
      boolean var13,
      boolean var14
   ) {
      for(int ☃ = ☃; ☃ <= ☃; ++☃) {
         for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
            for(int ☃xx = ☃; ☃xx <= ☃; ++☃xx) {
               if (!(☃.nextFloat() > ☃) && (!☃ || !this.func_175807_a(☃, ☃x, ☃, ☃xx, ☃).func_196958_f()) && (!☃ || this.func_189916_b(☃, ☃x, ☃, ☃xx, ☃))) {
                  if (☃ != ☃ && ☃ != ☃ && ☃x != ☃ && ☃x != ☃ && ☃xx != ☃ && ☃xx != ☃) {
                     this.func_175811_a(☃, ☃, ☃x, ☃, ☃xx, ☃);
                  } else {
                     this.func_175811_a(☃, ☃, ☃x, ☃, ☃xx, ☃);
                  }
               }
            }
         }
      }
   }

   protected void func_175809_a(IWorld var1, MutableBoundingBox var2, Random var3, float var4, int var5, int var6, int var7, IBlockState var8) {
      if (☃.nextFloat() < ☃) {
         this.func_175811_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   protected void func_180777_a(
      IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, boolean var10
   ) {
      float ☃ = (float)(☃ - ☃ + 1);
      float ☃x = (float)(☃ - ☃ + 1);
      float ☃xx = (float)(☃ - ☃ + 1);
      float ☃xxx = (float)☃ + ☃ / 2.0F;
      float ☃xxxx = (float)☃ + ☃xx / 2.0F;

      for(int ☃xxxxx = ☃; ☃xxxxx <= ☃; ++☃xxxxx) {
         float ☃xxxxxx = (float)(☃xxxxx - ☃) / ☃x;

         for(int ☃xxxxxxx = ☃; ☃xxxxxxx <= ☃; ++☃xxxxxxx) {
            float ☃xxxxxxxx = ((float)☃xxxxxxx - ☃xxx) / (☃ * 0.5F);

            for(int ☃xxxxxxxxx = ☃; ☃xxxxxxxxx <= ☃; ++☃xxxxxxxxx) {
               float ☃xxxxxxxxxx = ((float)☃xxxxxxxxx - ☃xxxx) / (☃xx * 0.5F);
               if (!☃ || !this.func_175807_a(☃, ☃xxxxxxx, ☃xxxxx, ☃xxxxxxxxx, ☃).func_196958_f()) {
                  float ☃xxxxxxxxxxx = ☃xxxxxxxx * ☃xxxxxxxx + ☃xxxxxx * ☃xxxxxx + ☃xxxxxxxxxx * ☃xxxxxxxxxx;
                  if (☃xxxxxxxxxxx <= 1.05F) {
                     this.func_175811_a(☃, ☃, ☃xxxxxxx, ☃xxxxx, ☃xxxxxxxxx, ☃);
                  }
               }
            }
         }
      }
   }

   protected void func_74871_b(IWorld var1, int var2, int var3, int var4, MutableBoundingBox var5) {
      BlockPos ☃ = new BlockPos(this.func_74865_a(☃, ☃), this.func_74862_a(☃), this.func_74873_b(☃, ☃));
      if (☃.func_175898_b(☃)) {
         while(!☃.func_175623_d(☃) && ☃.func_177956_o() < 255) {
            ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 2);
            ☃ = ☃.func_177984_a();
         }
      }
   }

   protected void func_175808_b(IWorld var1, IBlockState var2, int var3, int var4, int var5, MutableBoundingBox var6) {
      int ☃ = this.func_74865_a(☃, ☃);
      int ☃x = this.func_74862_a(☃);
      int ☃xx = this.func_74873_b(☃, ☃);
      if (☃.func_175898_b(new BlockPos(☃, ☃x, ☃xx))) {
         while((☃.func_175623_d(new BlockPos(☃, ☃x, ☃xx)) || ☃.func_180495_p(new BlockPos(☃, ☃x, ☃xx)).func_185904_a().func_76224_d()) && ☃x > 1) {
            ☃.func_180501_a(new BlockPos(☃, ☃x, ☃xx), ☃, 2);
            --☃x;
         }
      }
   }

   protected boolean func_186167_a(IWorld var1, MutableBoundingBox var2, Random var3, int var4, int var5, int var6, ResourceLocation var7) {
      BlockPos ☃ = new BlockPos(this.func_74865_a(☃, ☃), this.func_74862_a(☃), this.func_74873_b(☃, ☃));
      return this.func_191080_a(☃, ☃, ☃, ☃, ☃, null);
   }

   public static IBlockState func_197528_a(IBlockReader var0, BlockPos var1, IBlockState var2) {
      EnumFacing ☃ = null;

      for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
         BlockPos ☃xx = ☃.func_177972_a(☃x);
         IBlockState ☃xxx = ☃.func_180495_p(☃xx);
         if (☃xxx.func_177230_c() == Blocks.field_150486_ae) {
            return ☃;
         }

         if (☃xxx.func_200015_d(☃, ☃xx)) {
            if (☃ != null) {
               ☃ = null;
               break;
            }

            ☃ = ☃x;
         }
      }

      if (☃ != null) {
         return ☃.func_206870_a(BlockHorizontal.field_185512_D, ☃.func_176734_d());
      } else {
         EnumFacing ☃x = ☃.func_177229_b(BlockHorizontal.field_185512_D);
         BlockPos ☃xx = ☃.func_177972_a(☃x);
         if (☃.func_180495_p(☃xx).func_200015_d(☃, ☃xx)) {
            ☃x = ☃x.func_176734_d();
            ☃xx = ☃.func_177972_a(☃x);
         }

         if (☃.func_180495_p(☃xx).func_200015_d(☃, ☃xx)) {
            ☃x = ☃x.func_176746_e();
            ☃xx = ☃.func_177972_a(☃x);
         }

         if (☃.func_180495_p(☃xx).func_200015_d(☃, ☃xx)) {
            ☃x = ☃x.func_176734_d();
            ☃xx = ☃.func_177972_a(☃x);
         }

         return ☃.func_206870_a(BlockHorizontal.field_185512_D, ☃x);
      }
   }

   protected boolean func_191080_a(IWorld var1, MutableBoundingBox var2, Random var3, BlockPos var4, ResourceLocation var5, @Nullable IBlockState var6) {
      if (☃.func_175898_b(☃) && ☃.func_180495_p(☃).func_177230_c() != Blocks.field_150486_ae) {
         if (☃ == null) {
            ☃ = func_197528_a(☃, ☃, Blocks.field_150486_ae.func_176223_P());
         }

         ☃.func_180501_a(☃, ☃, 2);
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityChest) {
            ((TileEntityChest)☃).func_189404_a(☃, ☃.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean func_189419_a(IWorld var1, MutableBoundingBox var2, Random var3, int var4, int var5, int var6, EnumFacing var7, ResourceLocation var8) {
      BlockPos ☃ = new BlockPos(this.func_74865_a(☃, ☃), this.func_74862_a(☃), this.func_74873_b(☃, ☃));
      if (☃.func_175898_b(☃) && ☃.func_180495_p(☃).func_177230_c() != Blocks.field_150367_z) {
         this.func_175811_a(☃, Blocks.field_150367_z.func_176223_P().func_206870_a(BlockDispenser.field_176441_a, ☃), ☃, ☃, ☃, ☃);
         TileEntity ☃x = ☃.func_175625_s(☃);
         if (☃x instanceof TileEntityDispenser) {
            ((TileEntityDispenser)☃x).func_189404_a(☃, ☃.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected void func_189915_a(IWorld var1, MutableBoundingBox var2, Random var3, int var4, int var5, int var6, EnumFacing var7, BlockDoor var8) {
      this.func_175811_a(☃, ☃.func_176223_P().func_206870_a(BlockDoor.field_176520_a, ☃), ☃, ☃, ☃, ☃);
      this.func_175811_a(
         ☃, ☃.func_176223_P().func_206870_a(BlockDoor.field_176520_a, ☃).func_206870_a(BlockDoor.field_176523_O, DoubleBlockHalf.UPPER), ☃, ☃ + 1, ☃, ☃
      );
   }

   public void func_181138_a(int var1, int var2, int var3) {
      this.field_74887_e.func_78886_a(☃, ☃, ☃);
   }

   @Nullable
   public EnumFacing func_186165_e() {
      return this.field_74885_f;
   }

   public void func_186164_a(@Nullable EnumFacing var1) {
      this.field_74885_f = ☃;
      if (☃ == null) {
         this.field_186169_c = Rotation.NONE;
         this.field_186168_b = Mirror.NONE;
      } else {
         switch(☃) {
            case SOUTH:
               this.field_186168_b = Mirror.LEFT_RIGHT;
               this.field_186169_c = Rotation.NONE;
               break;
            case WEST:
               this.field_186168_b = Mirror.LEFT_RIGHT;
               this.field_186169_c = Rotation.CLOCKWISE_90;
               break;
            case EAST:
               this.field_186168_b = Mirror.NONE;
               this.field_186169_c = Rotation.CLOCKWISE_90;
               break;
            default:
               this.field_186168_b = Mirror.NONE;
               this.field_186169_c = Rotation.NONE;
         }
      }
   }

   public abstract static class BlockSelector {
      protected IBlockState field_151562_a = Blocks.field_150350_a.func_176223_P();

      protected BlockSelector() {
      }

      public abstract void func_75062_a(Random var1, int var2, int var3, int var4, boolean var5);

      public IBlockState func_180780_a() {
         return this.field_151562_a;
      }
   }
}
