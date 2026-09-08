package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockAnvil extends BlockFalling {
   private static final Logger field_185762_e = LogManager.getLogger();
   public static final DirectionProperty field_176506_a = BlockHorizontal.field_185512_D;
   private static final VoxelShape field_196436_c = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
   private static final VoxelShape field_196439_y = Block.func_208617_a(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
   private static final VoxelShape field_196440_z = Block.func_208617_a(4.0, 5.0, 6.0, 12.0, 10.0, 10.0);
   private static final VoxelShape field_196434_A = Block.func_208617_a(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
   private static final VoxelShape field_196435_B = Block.func_208617_a(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
   private static final VoxelShape field_196437_C = Block.func_208617_a(6.0, 5.0, 4.0, 10.0, 10.0, 12.0);
   private static final VoxelShape field_196438_D = Block.func_208617_a(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
   private static final VoxelShape field_185760_c = VoxelShapes.func_197872_a(
      field_196436_c, VoxelShapes.func_197872_a(field_196439_y, VoxelShapes.func_197872_a(field_196440_z, field_196434_A))
   );
   private static final VoxelShape field_185761_d = VoxelShapes.func_197872_a(
      field_196436_c, VoxelShapes.func_197872_a(field_196435_B, VoxelShapes.func_197872_a(field_196437_C, field_196438_D))
   );

   public BlockAnvil(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176506_a, EnumFacing.NORTH));
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_176506_a, ☃.func_195992_f().func_176746_e());
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (!☃.field_72995_K) {
         ☃.func_180468_a(new BlockAnvil.Anvil(☃, ☃));
      }

      return true;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_176506_a);
      return ☃.func_176740_k() == EnumFacing.Axis.X ? field_185760_c : field_185761_d;
   }

   @Override
   protected void func_149829_a(EntityFallingBlock var1) {
      ☃.func_145806_a(true);
   }

   @Override
   public void func_176502_a_(World var1, BlockPos var2, IBlockState var3, IBlockState var4) {
      ☃.func_175718_b(1031, ☃, 0);
   }

   @Override
   public void func_190974_b(World var1, BlockPos var2) {
      ☃.func_175718_b(1029, ☃, 0);
   }

   @Nullable
   public static IBlockState func_196433_f(IBlockState var0) {
      Block ☃ = ☃.func_177230_c();
      if (☃ == Blocks.field_150467_bQ) {
         return Blocks.field_196717_eY.func_176223_P().func_206870_a(field_176506_a, ☃.func_177229_b(field_176506_a));
      } else {
         return ☃ == Blocks.field_196717_eY ? Blocks.field_196718_eZ.func_176223_P().func_206870_a(field_176506_a, ☃.func_177229_b(field_176506_a)) : null;
      }
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176506_a, ☃.func_185831_a(☃.func_177229_b(field_176506_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176506_a);
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }

   public static class Anvil implements IInteractionObject {
      private final World field_175130_a;
      private final BlockPos field_175129_b;

      public Anvil(World var1, BlockPos var2) {
         this.field_175130_a = ☃;
         this.field_175129_b = ☃;
      }

      @Override
      public ITextComponent func_200200_C_() {
         return new TextComponentTranslation(Blocks.field_150467_bQ.func_149739_a());
      }

      @Override
      public boolean func_145818_k_() {
         return false;
      }

      @Nullable
      @Override
      public ITextComponent func_200201_e() {
         return null;
      }

      @Override
      public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
         return new ContainerRepair(☃, this.field_175130_a, this.field_175129_b, ☃);
      }

      @Override
      public String func_174875_k() {
         return "minecraft:anvil";
      }
   }
}
