package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockSixWay extends Block {
   private static final EnumFacing[] field_196494_D = EnumFacing.values();
   public static final BooleanProperty field_196488_a = BlockStateProperties.field_208151_D;
   public static final BooleanProperty field_196490_b = BlockStateProperties.field_208152_E;
   public static final BooleanProperty field_196492_c = BlockStateProperties.field_208153_F;
   public static final BooleanProperty field_196495_y = BlockStateProperties.field_208154_G;
   public static final BooleanProperty field_196496_z = BlockStateProperties.field_208149_B;
   public static final BooleanProperty field_196489_A = BlockStateProperties.field_208150_C;
   public static final Map<EnumFacing, BooleanProperty> field_196491_B = Util.func_200696_a(Maps.newEnumMap(EnumFacing.class), var0 -> {
      var0.put(EnumFacing.NORTH, field_196488_a);
      var0.put(EnumFacing.EAST, field_196490_b);
      var0.put(EnumFacing.SOUTH, field_196492_c);
      var0.put(EnumFacing.WEST, field_196495_y);
      var0.put(EnumFacing.UP, field_196496_z);
      var0.put(EnumFacing.DOWN, field_196489_A);
   });
   protected final VoxelShape[] field_196493_C;

   protected BlockSixWay(float var1, Block.Properties var2) {
      super(☃);
      this.field_196493_C = this.func_196487_d(☃);
   }

   private VoxelShape[] func_196487_d(float var1) {
      float ☃ = 0.5F - ☃;
      float ☃x = 0.5F + ☃;
      VoxelShape ☃xx = Block.func_208617_a(
         (double)(☃ * 16.0F), (double)(☃ * 16.0F), (double)(☃ * 16.0F), (double)(☃x * 16.0F), (double)(☃x * 16.0F), (double)(☃x * 16.0F)
      );
      VoxelShape[] ☃xxx = new VoxelShape[field_196494_D.length];

      for(int ☃xxxx = 0; ☃xxxx < field_196494_D.length; ++☃xxxx) {
         EnumFacing ☃xxxxx = field_196494_D[☃xxxx];
         ☃xxx[☃xxxx] = VoxelShapes.func_197873_a(
            0.5 + Math.min((double)(-☃), (double)☃xxxxx.func_82601_c() * 0.5),
            0.5 + Math.min((double)(-☃), (double)☃xxxxx.func_96559_d() * 0.5),
            0.5 + Math.min((double)(-☃), (double)☃xxxxx.func_82599_e() * 0.5),
            0.5 + Math.max((double)☃, (double)☃xxxxx.func_82601_c() * 0.5),
            0.5 + Math.max((double)☃, (double)☃xxxxx.func_96559_d() * 0.5),
            0.5 + Math.max((double)☃, (double)☃xxxxx.func_82599_e() * 0.5)
         );
      }

      VoxelShape[] ☃xxxx = new VoxelShape[64];

      for(int ☃xxxxx = 0; ☃xxxxx < 64; ++☃xxxxx) {
         VoxelShape ☃xxxxxx = ☃xx;

         for(int ☃xxxxxxx = 0; ☃xxxxxxx < field_196494_D.length; ++☃xxxxxxx) {
            if ((☃xxxxx & 1 << ☃xxxxxxx) != 0) {
               ☃xxxxxx = VoxelShapes.func_197872_a(☃xxxxxx, ☃xxx[☃xxxxxxx]);
            }
         }

         ☃xxxx[☃xxxxx] = ☃xxxxxx;
      }

      return ☃xxxx;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return this.field_196493_C[this.func_196486_i(☃)];
   }

   protected int func_196486_i(IBlockState var1) {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < field_196494_D.length; ++☃x) {
         if (☃.func_177229_b((IProperty)field_196491_B.get(field_196494_D[☃x]))) {
            ☃ |= 1 << ☃x;
         }
      }

      return ☃;
   }
}
