package net.minecraft.block;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockRailDetector extends BlockRailBase {
   public static final EnumProperty<RailShape> field_176573_b = BlockStateProperties.field_208166_S;
   public static final BooleanProperty field_176574_M = BlockStateProperties.field_208194_u;

   public BlockRailDetector(Block.Properties var1) {
      super(true, ☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176574_M, Boolean.valueOf(false)).func_206870_a(field_176573_b, RailShape.NORTH_SOUTH)
      );
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 20;
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      if (!☃.field_72995_K) {
         if (!☃.func_177229_b(field_176574_M)) {
            this.func_176570_e(☃, ☃, ☃);
         }
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K && ☃.func_177229_b(field_176574_M)) {
         this.func_176570_e(☃, ☃, ☃);
      }
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176574_M) ? 15 : 0;
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      if (!☃.func_177229_b(field_176574_M)) {
         return 0;
      } else {
         return ☃ == EnumFacing.UP ? 15 : 0;
      }
   }

   private void func_176570_e(World var1, BlockPos var2, IBlockState var3) {
      boolean ☃ = ☃.func_177229_b(field_176574_M);
      boolean ☃x = false;
      List<EntityMinecart> ☃xx = this.func_200878_a(☃, ☃, EntityMinecart.class, null);
      if (!☃xx.isEmpty()) {
         ☃x = true;
      }

      if (☃x && !☃) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176574_M, Boolean.valueOf(true)), 3);
         this.func_185592_b(☃, ☃, ☃, true);
         ☃.func_195593_d(☃, this);
         ☃.func_195593_d(☃.func_177977_b(), this);
         ☃.func_175704_b(☃, ☃);
      }

      if (!☃x && ☃) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176574_M, Boolean.valueOf(false)), 3);
         this.func_185592_b(☃, ☃, ☃, false);
         ☃.func_195593_d(☃, this);
         ☃.func_195593_d(☃.func_177977_b(), this);
         ☃.func_175704_b(☃, ☃);
      }

      if (☃x) {
         ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
      }

      ☃.func_175666_e(☃, this);
   }

   protected void func_185592_b(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      BlockRailState ☃ = new BlockRailState(☃, ☃, ☃);

      for(BlockPos ☃x : ☃.func_196907_a()) {
         IBlockState ☃xx = ☃.func_180495_p(☃x);
         ☃xx.func_189546_a(☃, ☃x, ☃xx.func_177230_c(), ☃);
      }
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         super.func_196259_b(☃, ☃, ☃, ☃);
         this.func_176570_e(☃, ☃, ☃);
      }
   }

   @Override
   public IProperty<RailShape> func_176560_l() {
      return field_176573_b;
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      if (☃.func_177229_b(field_176574_M)) {
         List<EntityMinecartCommandBlock> ☃ = this.func_200878_a(☃, ☃, EntityMinecartCommandBlock.class, null);
         if (!☃.isEmpty()) {
            return ((EntityMinecartCommandBlock)☃.get(0)).func_145822_e().func_145760_g();
         }

         List<EntityMinecart> ☃ = this.func_200878_a(☃, ☃, EntityMinecart.class, EntitySelectors.field_96566_b);
         if (!☃.isEmpty()) {
            return Container.func_94526_b((IInventory)☃.get(0));
         }
      }

      return 0;
   }

   protected <T extends EntityMinecart> List<T> func_200878_a(World var1, BlockPos var2, Class<T> var3, @Nullable Predicate<Entity> var4) {
      return ☃.func_175647_a(☃, this.func_176572_a(☃), ☃);
   }

   private AxisAlignedBB func_176572_a(BlockPos var1) {
      float ☃ = 0.2F;
      return new AxisAlignedBB(
         (double)((float)☃.func_177958_n() + 0.2F),
         (double)☃.func_177956_o(),
         (double)((float)☃.func_177952_p() + 0.2F),
         (double)((float)(☃.func_177958_n() + 1) - 0.2F),
         (double)((float)(☃.func_177956_o() + 1) - 0.2F),
         (double)((float)(☃.func_177952_p() + 1) - 0.2F)
      );
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case CLOCKWISE_180:
            switch((RailShape)☃.func_177229_b(field_176573_b)) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_WEST);
            }
         case COUNTERCLOCKWISE_90:
            switch((RailShape)☃.func_177229_b(field_176573_b)) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_NORTH);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_WEST);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_EAST);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_WEST);
               case NORTH_SOUTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.EAST_WEST);
               case EAST_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_SOUTH);
            }
         case CLOCKWISE_90:
            switch((RailShape)☃.func_177229_b(field_176573_b)) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_NORTH);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_EAST);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_WEST);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_EAST);
               case NORTH_SOUTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.EAST_WEST);
               case EAST_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_SOUTH);
            }
         default:
            return ☃;
      }
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      RailShape ☃ = ☃.func_177229_b(field_176573_b);
      switch(☃) {
         case LEFT_RIGHT:
            switch(☃) {
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_EAST);
               default:
                  return super.func_185471_a(☃, ☃);
            }
         case FRONT_BACK:
            switch(☃) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
               case ASCENDING_SOUTH:
               default:
                  break;
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176573_b, RailShape.NORTH_WEST);
            }
      }

      return super.func_185471_a(☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176573_b, field_176574_M);
   }
}
