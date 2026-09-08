package net.minecraft.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class BlockRailBase extends Block {
   protected static final VoxelShape field_185590_a = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   protected static final VoxelShape field_190959_b = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private final boolean field_196277_c;

   public static boolean func_208488_a(World var0, BlockPos var1) {
      return func_208487_j(☃.func_180495_p(☃));
   }

   public static boolean func_208487_j(IBlockState var0) {
      return ☃.func_203425_a(BlockTags.field_203437_y);
   }

   protected BlockRailBase(boolean var1, Block.Properties var2) {
      super(☃);
      this.field_196277_c = ☃;
   }

   public boolean func_208490_b() {
      return this.field_196277_c;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      RailShape ☃ = ☃.func_177230_c() == this ? ☃.func_177229_b(this.func_176560_l()) : null;
      return ☃ != null && ☃.func_208092_c() ? field_190959_b : field_185590_a;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return ☃.func_180495_p(☃.func_177977_b()).func_185896_q();
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         if (!☃.field_72995_K) {
            ☃ = this.func_208489_a(☃, ☃, ☃, true);
            if (this.field_196277_c) {
               ☃.func_189546_a(☃, ☃, this, ☃);
            }
         }
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         RailShape ☃ = ☃.func_177229_b(this.func_176560_l());
         boolean ☃x = false;
         if (!☃.func_180495_p(☃.func_177977_b()).func_185896_q()) {
            ☃x = true;
         }

         if (☃ == RailShape.ASCENDING_EAST && !☃.func_180495_p(☃.func_177974_f()).func_185896_q()) {
            ☃x = true;
         } else if (☃ == RailShape.ASCENDING_WEST && !☃.func_180495_p(☃.func_177976_e()).func_185896_q()) {
            ☃x = true;
         } else if (☃ == RailShape.ASCENDING_NORTH && !☃.func_180495_p(☃.func_177978_c()).func_185896_q()) {
            ☃x = true;
         } else if (☃ == RailShape.ASCENDING_SOUTH && !☃.func_180495_p(☃.func_177968_d()).func_185896_q()) {
            ☃x = true;
         }

         if (☃x && !☃.func_175623_d(☃)) {
            ☃.func_196941_a(☃, ☃, 1.0F, 0);
            ☃.func_175698_g(☃);
         } else {
            this.func_189541_b(☃, ☃, ☃, ☃);
         }
      }
   }

   protected void func_189541_b(IBlockState var1, World var2, BlockPos var3, Block var4) {
   }

   protected IBlockState func_208489_a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return ☃.field_72995_K ? ☃ : new BlockRailState(☃, ☃, ☃).func_208511_a(☃.func_175640_z(☃), ☃).func_196916_c();
   }

   @Override
   public EnumPushReaction func_149656_h(IBlockState var1) {
      return EnumPushReaction.NORMAL;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃) {
         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
         if (((RailShape)☃.func_177229_b(this.func_176560_l())).func_208092_c()) {
            ☃.func_195593_d(☃.func_177984_a(), this);
         }

         if (this.field_196277_c) {
            ☃.func_195593_d(☃, this);
            ☃.func_195593_d(☃.func_177977_b(), this);
         }
      }
   }

   public abstract IProperty<RailShape> func_176560_l();
}
