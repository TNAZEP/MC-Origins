package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockCommandBlock extends BlockContainer {
   private static final Logger field_193388_c = LogManager.getLogger();
   public static final DirectionProperty field_185564_a = BlockDirectional.field_176387_N;
   public static final BooleanProperty field_185565_b = BlockStateProperties.field_208176_c;

   public BlockCommandBlock(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_185564_a, EnumFacing.NORTH).func_206870_a(field_185565_b, Boolean.valueOf(false))
      );
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      TileEntityCommandBlock ☃ = new TileEntityCommandBlock();
      ☃.func_184253_b(this == Blocks.field_185777_dd);
      return ☃;
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityCommandBlock) {
            TileEntityCommandBlock ☃x = (TileEntityCommandBlock)☃;
            boolean ☃xx = ☃.func_175640_z(☃);
            boolean ☃xxx = ☃x.func_184255_d();
            ☃x.func_184250_a(☃xx);
            if (!☃xxx && !☃x.func_184254_e() && ☃x.func_184251_i() != TileEntityCommandBlock.Mode.SEQUENCE) {
               if (☃xx) {
                  ☃x.func_184249_c();
                  ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
               }
            }
         }
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityCommandBlock) {
            TileEntityCommandBlock ☃x = (TileEntityCommandBlock)☃;
            CommandBlockBaseLogic ☃xx = ☃x.func_145993_a();
            boolean ☃xxx = !StringUtils.func_151246_b(☃xx.func_145753_i());
            TileEntityCommandBlock.Mode ☃xxxx = ☃x.func_184251_i();
            boolean ☃xxxxx = ☃x.func_184256_g();
            if (☃xxxx == TileEntityCommandBlock.Mode.AUTO) {
               ☃x.func_184249_c();
               if (☃xxxxx) {
                  this.func_193387_a(☃, ☃, ☃, ☃xx, ☃xxx);
               } else if (☃x.func_184258_j()) {
                  ☃xx.func_184167_a(0);
               }

               if (☃x.func_184255_d() || ☃x.func_184254_e()) {
                  ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
               }
            } else if (☃xxxx == TileEntityCommandBlock.Mode.REDSTONE) {
               if (☃xxxxx) {
                  this.func_193387_a(☃, ☃, ☃, ☃xx, ☃xxx);
               } else if (☃x.func_184258_j()) {
                  ☃xx.func_184167_a(0);
               }
            }

            ☃.func_175666_e(☃, this);
         }
      }
   }

   private void func_193387_a(IBlockState var1, World var2, BlockPos var3, CommandBlockBaseLogic var4, boolean var5) {
      if (☃) {
         ☃.func_145755_a(☃);
      } else {
         ☃.func_184167_a(0);
      }

      func_193386_c(☃, ☃, ☃.func_177229_b(field_185564_a));
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 1;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntityCommandBlock && ☃.func_195070_dx()) {
         ☃.func_184824_a((TileEntityCommandBlock)☃);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      return ☃ instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock)☃).func_145993_a().func_145760_g() : 0;
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntityCommandBlock) {
         TileEntityCommandBlock ☃x = (TileEntityCommandBlock)☃;
         CommandBlockBaseLogic ☃xx = ☃x.func_145993_a();
         if (☃.func_82837_s()) {
            ☃xx.func_207405_b(☃.func_200301_q());
         }

         if (!☃.field_72995_K) {
            if (☃.func_179543_a("BlockEntityTag") == null) {
               ☃xx.func_175573_a(☃.func_82736_K().func_82766_b("sendCommandFeedback"));
               ☃x.func_184253_b(this == Blocks.field_185777_dd);
            }

            if (☃x.func_184251_i() == TileEntityCommandBlock.Mode.SEQUENCE) {
               boolean ☃x = ☃.func_175640_z(☃);
               ☃x.func_184250_a(☃x);
            }
         }
      }
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_185564_a, ☃.func_185831_a(☃.func_177229_b(field_185564_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_185564_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185564_a, field_185565_b);
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_185564_a, ☃.func_196010_d().func_176734_d());
   }

   private static void func_193386_c(World var0, BlockPos var1, EnumFacing var2) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos(☃);
      GameRules ☃x = ☃.func_82736_K();

      int ☃;
      IBlockState ☃;
      for(☃ = ☃x.func_180263_c("maxCommandChainLength"); ☃-- > 0; ☃ = ☃.func_177229_b(field_185564_a)) {
         ☃.func_189536_c(☃);
         ☃ = ☃.func_180495_p(☃);
         Block ☃xx = ☃.func_177230_c();
         if (☃xx != Blocks.field_185777_dd) {
            break;
         }

         TileEntity ☃xx = ☃.func_175625_s(☃);
         if (!(☃xx instanceof TileEntityCommandBlock)) {
            break;
         }

         TileEntityCommandBlock ☃xx = (TileEntityCommandBlock)☃xx;
         if (☃xx.func_184251_i() != TileEntityCommandBlock.Mode.SEQUENCE) {
            break;
         }

         if (☃xx.func_184255_d() || ☃xx.func_184254_e()) {
            CommandBlockBaseLogic ☃xx = ☃xx.func_145993_a();
            if (☃xx.func_184249_c()) {
               if (!☃xx.func_145755_a(☃)) {
                  break;
               }

               ☃.func_175666_e(☃, ☃xx);
            } else if (☃xx.func_184258_j()) {
               ☃xx.func_184167_a(0);
            }
         }
      }

      if (☃ <= 0) {
         int ☃xx = Math.max(☃x.func_180263_c("maxCommandChainLength"), 0);
         field_193388_c.warn("Command Block chain tried to execute more than {} steps!", ☃xx);
      }
   }
}
