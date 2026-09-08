package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockPistonBase extends BlockDirectional {
   public static final BooleanProperty field_176320_b = BlockStateProperties.field_208181_h;
   protected static final VoxelShape field_185648_b = Block.func_208617_a(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   protected static final VoxelShape field_185649_c = Block.func_208617_a(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185650_d = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
   protected static final VoxelShape field_185651_e = Block.func_208617_a(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185652_f = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   protected static final VoxelShape field_185653_g = Block.func_208617_a(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
   private final boolean field_150082_a;

   public BlockPistonBase(boolean var1, Block.Properties var2) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176387_N, EnumFacing.NORTH).func_206870_a(field_176320_b, Boolean.valueOf(false))
      );
      this.field_150082_a = ☃;
   }

   @Override
   public boolean func_176214_u(IBlockState var1) {
      return !☃.func_177229_b(field_176320_b);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      if (☃.func_177229_b(field_176320_b)) {
         switch((EnumFacing)☃.func_177229_b(field_176387_N)) {
            case DOWN:
               return field_185653_g;
            case UP:
            default:
               return field_185652_f;
            case NORTH:
               return field_185651_e;
            case SOUTH:
               return field_185650_d;
            case WEST:
               return field_185649_c;
            case EAST:
               return field_185648_b;
         }
      } else {
         return VoxelShapes.func_197868_b();
      }
   }

   @Override
   public boolean func_185481_k(IBlockState var1) {
      return !☃.func_177229_b(field_176320_b) || ☃.func_177229_b(field_176387_N) == EnumFacing.DOWN;
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (!☃.field_72995_K) {
         this.func_176316_e(☃, ☃, ☃);
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         this.func_176316_e(☃, ☃, ☃);
      }
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         if (!☃.field_72995_K && ☃.func_175625_s(☃) == null) {
            this.func_176316_e(☃, ☃, ☃);
         }
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_176387_N, ☃.func_196010_d().func_176734_d()).func_206870_a(field_176320_b, Boolean.valueOf(false));
   }

   private void func_176316_e(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_176387_N);
      boolean ☃x = this.func_176318_b(☃, ☃, ☃);
      if (☃x && !☃.func_177229_b(field_176320_b)) {
         if (new BlockPistonStructureHelper(☃, ☃, ☃, true).func_177253_a()) {
            ☃.func_175641_c(☃, this, 0, ☃.func_176745_a());
         }
      } else if (!☃x && ☃.func_177229_b(field_176320_b)) {
         BlockPos ☃ = ☃.func_177967_a(☃, 2);
         IBlockState ☃x = ☃.func_180495_p(☃);
         int ☃xx = 1;
         if (☃x.func_177230_c() == Blocks.field_196603_bb && ☃x.func_177229_b(field_176387_N) == ☃) {
            TileEntity ☃xxx = ☃.func_175625_s(☃);
            if (☃xxx instanceof TileEntityPiston) {
               TileEntityPiston ☃xxxx = (TileEntityPiston)☃xxx;
               if (☃xxxx.func_145868_b()
                  && (☃xxxx.func_145860_a(0.0F) < 0.5F || ☃.func_82737_E() == ☃xxxx.func_211146_k() || ((WorldServer)☃).func_211158_j_())) {
                  ☃xx = 2;
               }
            }
         }

         ☃.func_175641_c(☃, this, ☃xx, ☃.func_176745_a());
      }
   }

   private boolean func_176318_b(World var1, BlockPos var2, EnumFacing var3) {
      for(EnumFacing ☃ : EnumFacing.values()) {
         if (☃ != ☃ && ☃.func_175709_b(☃.func_177972_a(☃), ☃)) {
            return true;
         }
      }

      if (☃.func_175709_b(☃, EnumFacing.DOWN)) {
         return true;
      } else {
         BlockPos ☃ = ☃.func_177984_a();

         for(EnumFacing ☃x : EnumFacing.values()) {
            if (☃x != EnumFacing.DOWN && ☃.func_175709_b(☃.func_177972_a(☃x), ☃x)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean func_189539_a(IBlockState var1, World var2, BlockPos var3, int var4, int var5) {
      EnumFacing ☃ = ☃.func_177229_b(field_176387_N);
      if (!☃.field_72995_K) {
         boolean ☃x = this.func_176318_b(☃, ☃, ☃);
         if (☃x && (☃ == 1 || ☃ == 2)) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_176320_b, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!☃x && ☃ == 0) {
            return false;
         }
      }

      if (☃ == 0) {
         if (!this.func_176319_a(☃, ☃, ☃, true)) {
            return false;
         }

         ☃.func_180501_a(☃, ☃.func_206870_a(field_176320_b, Boolean.valueOf(true)), 67);
         ☃.func_184133_a(null, ☃, SoundEvents.field_187715_dR, SoundCategory.BLOCKS, 0.5F, ☃.field_73012_v.nextFloat() * 0.25F + 0.6F);
      } else if (☃ == 1 || ☃ == 2) {
         TileEntity ☃ = ☃.func_175625_s(☃.func_177972_a(☃));
         if (☃ instanceof TileEntityPiston) {
            ((TileEntityPiston)☃).func_145866_f();
         }

         ☃.func_180501_a(
            ☃,
            Blocks.field_196603_bb
               .func_176223_P()
               .func_206870_a(BlockPistonMoving.field_196344_a, ☃)
               .func_206870_a(BlockPistonMoving.field_196345_b, this.field_150082_a ? PistonType.STICKY : PistonType.DEFAULT),
            3
         );
         ☃.func_175690_a(☃, BlockPistonMoving.func_196343_a(this.func_176223_P().func_206870_a(field_176387_N, EnumFacing.func_82600_a(☃ & 7)), ☃, false, true));
         if (this.field_150082_a) {
            BlockPos ☃ = ☃.func_177982_a(☃.func_82601_c() * 2, ☃.func_96559_d() * 2, ☃.func_82599_e() * 2);
            IBlockState ☃x = ☃.func_180495_p(☃);
            Block ☃xx = ☃x.func_177230_c();
            boolean ☃xxx = false;
            if (☃xx == Blocks.field_196603_bb) {
               TileEntity ☃xxxx = ☃.func_175625_s(☃);
               if (☃xxxx instanceof TileEntityPiston) {
                  TileEntityPiston ☃xxxxx = (TileEntityPiston)☃xxxx;
                  if (☃xxxxx.func_212363_d() == ☃ && ☃xxxxx.func_145868_b()) {
                     ☃xxxxx.func_145866_f();
                     ☃xxx = true;
                  }
               }
            }

            if (!☃xxx) {
               if (☃ != 1
                  || ☃x.func_196958_f()
                  || !func_185646_a(☃x, ☃, ☃, ☃.func_176734_d(), false, ☃)
                  || ☃x.func_185905_o() != EnumPushReaction.NORMAL && ☃xx != Blocks.field_150331_J && ☃xx != Blocks.field_150320_F) {
                  ☃.func_175698_g(☃.func_177972_a(☃));
               } else {
                  this.func_176319_a(☃, ☃, ☃, false);
               }
            }
         } else {
            ☃.func_175698_g(☃.func_177972_a(☃));
         }

         ☃.func_184133_a(null, ☃, SoundEvents.field_187712_dQ, SoundCategory.BLOCKS, 0.5F, ☃.field_73012_v.nextFloat() * 0.15F + 0.6F);
      }

      return true;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   public static boolean func_185646_a(IBlockState var0, World var1, BlockPos var2, EnumFacing var3, boolean var4, EnumFacing var5) {
      Block ☃ = ☃.func_177230_c();
      if (☃ == Blocks.field_150343_Z) {
         return false;
      } else if (!☃.func_175723_af().func_177746_a(☃)) {
         return false;
      } else if (☃.func_177956_o() >= 0 && (☃ != EnumFacing.DOWN || ☃.func_177956_o() != 0)) {
         if (☃.func_177956_o() <= ☃.func_72800_K() - 1 && (☃ != EnumFacing.UP || ☃.func_177956_o() != ☃.func_72800_K() - 1)) {
            if (☃ != Blocks.field_150331_J && ☃ != Blocks.field_150320_F) {
               if (☃.func_185887_b(☃, ☃) == -1.0F) {
                  return false;
               }

               switch(☃.func_185905_o()) {
                  case BLOCK:
                     return false;
                  case DESTROY:
                     return ☃;
                  case PUSH_ONLY:
                     return ☃ == ☃;
               }
            } else if (☃.func_177229_b(field_176320_b)) {
               return false;
            }

            return !☃.func_149716_u();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean func_176319_a(World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      BlockPos ☃ = ☃.func_177972_a(☃);
      if (!☃ && ☃.func_180495_p(☃).func_177230_c() == Blocks.field_150332_K) {
         ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 20);
      }

      BlockPistonStructureHelper ☃ = new BlockPistonStructureHelper(☃, ☃, ☃, ☃);
      if (!☃.func_177253_a()) {
         return false;
      } else {
         List<BlockPos> ☃ = ☃.func_177254_c();
         List<IBlockState> ☃x = Lists.<IBlockState>newArrayList();

         for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
            BlockPos ☃xxx = (BlockPos)☃.get(☃xx);
            ☃x.add(☃.func_180495_p(☃xxx));
         }

         List<BlockPos> ☃xx = ☃.func_177252_d();
         int ☃xxx = ☃.size() + ☃xx.size();
         IBlockState[] ☃xxxx = new IBlockState[☃xxx];
         EnumFacing ☃xxxxx = ☃ ? ☃ : ☃.func_176734_d();
         Set<BlockPos> ☃xxxxxx = Sets.<BlockPos>newHashSet(☃);

         for(int ☃xxxxxxx = ☃xx.size() - 1; ☃xxxxxxx >= 0; --☃xxxxxxx) {
            BlockPos ☃xxxxxxxx = (BlockPos)☃xx.get(☃xxxxxxx);
            IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxxxxx);
            ☃xxxxxxxxx.func_196949_c(☃, ☃xxxxxxxx, 0);
            ☃.func_180501_a(☃xxxxxxxx, Blocks.field_150350_a.func_176223_P(), 18);
            --☃xxx;
            ☃xxxx[☃xxx] = ☃xxxxxxxxx;
         }

         for(int ☃xxxxxxx = ☃.size() - 1; ☃xxxxxxx >= 0; --☃xxxxxxx) {
            BlockPos ☃xxxxxxxx = (BlockPos)☃.get(☃xxxxxxx);
            IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxxxxx);
            ☃xxxxxxxx = ☃xxxxxxxx.func_177972_a(☃xxxxx);
            ☃xxxxxx.remove(☃xxxxxxxx);
            ☃.func_180501_a(☃xxxxxxxx, Blocks.field_196603_bb.func_176223_P().func_206870_a(field_176387_N, ☃), 68);
            ☃.func_175690_a(☃xxxxxxxx, BlockPistonMoving.func_196343_a((IBlockState)☃x.get(☃xxxxxxx), ☃, ☃, false));
            --☃xxx;
            ☃xxxx[☃xxx] = ☃xxxxxxxxx;
         }

         if (☃) {
            PistonType ☃xxxxxxx = this.field_150082_a ? PistonType.STICKY : PistonType.DEFAULT;
            IBlockState ☃xxxxxxxx = Blocks.field_150332_K
               .func_176223_P()
               .func_206870_a(BlockPistonExtension.field_176387_N, ☃)
               .func_206870_a(BlockPistonExtension.field_176325_b, ☃xxxxxxx);
            IBlockState ☃xxxxxxxxx = Blocks.field_196603_bb
               .func_176223_P()
               .func_206870_a(BlockPistonMoving.field_196344_a, ☃)
               .func_206870_a(BlockPistonMoving.field_196345_b, this.field_150082_a ? PistonType.STICKY : PistonType.DEFAULT);
            ☃xxxxxx.remove(☃);
            ☃.func_180501_a(☃, ☃xxxxxxxxx, 68);
            ☃.func_175690_a(☃, BlockPistonMoving.func_196343_a(☃xxxxxxxx, ☃, true, true));
         }

         for(BlockPos ☃xxxxxxx : ☃xxxxxx) {
            ☃.func_180501_a(☃xxxxxxx, Blocks.field_150350_a.func_176223_P(), 66);
         }

         for(int ☃xxxxxxx = ☃xx.size() - 1; ☃xxxxxxx >= 0; --☃xxxxxxx) {
            IBlockState ☃xxxxxxxx = ☃xxxx[☃xxx++];
            BlockPos ☃xxxxxxxxx = (BlockPos)☃xx.get(☃xxxxxxx);
            ☃xxxxxxxx.func_196948_b(☃, ☃xxxxxxxxx, 2);
            ☃.func_195593_d(☃xxxxxxxxx, ☃xxxxxxxx.func_177230_c());
         }

         for(int ☃xxxxxxx = ☃.size() - 1; ☃xxxxxxx >= 0; --☃xxxxxxx) {
            ☃.func_195593_d((BlockPos)☃.get(☃xxxxxxx), ☃xxxx[☃xxx++].func_177230_c());
         }

         if (☃) {
            ☃.func_195593_d(☃, Blocks.field_150332_K);
         }

         return true;
      }
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176387_N, ☃.func_185831_a(☃.func_177229_b(field_176387_N)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176387_N)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176387_N, field_176320_b);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176387_N) != ☃.func_176734_d() && ☃.func_177229_b(field_176320_b) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return 0;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
