package net.minecraft.entity.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReaderBase;

public class EntityAIBreathAir extends EntityAIBase {
   private final EntityCreature field_205142_a;

   public EntityAIBreathAir(EntityCreature var1) {
      this.field_205142_a = ☃;
      this.func_75248_a(3);
   }

   @Override
   public boolean func_75250_a() {
      return this.field_205142_a.func_70086_ai() < 140;
   }

   @Override
   public boolean func_75253_b() {
      return this.func_75250_a();
   }

   @Override
   public boolean func_75252_g() {
      return false;
   }

   @Override
   public void func_75249_e() {
      this.func_205141_g();
   }

   private void func_205141_g() {
      Iterable<BlockPos.MutableBlockPos> ☃ = BlockPos.MutableBlockPos.func_191531_b(
         MathHelper.func_76128_c(this.field_205142_a.field_70165_t - 1.0),
         MathHelper.func_76128_c(this.field_205142_a.field_70163_u),
         MathHelper.func_76128_c(this.field_205142_a.field_70161_v - 1.0),
         MathHelper.func_76128_c(this.field_205142_a.field_70165_t + 1.0),
         MathHelper.func_76128_c(this.field_205142_a.field_70163_u + 8.0),
         MathHelper.func_76128_c(this.field_205142_a.field_70161_v + 1.0)
      );
      BlockPos ☃x = null;

      for(BlockPos ☃xx : ☃) {
         if (this.func_205140_a(this.field_205142_a.field_70170_p, ☃xx)) {
            ☃x = ☃xx;
            break;
         }
      }

      if (☃x == null) {
         ☃x = new BlockPos(this.field_205142_a.field_70165_t, this.field_205142_a.field_70163_u + 8.0, this.field_205142_a.field_70161_v);
      }

      this.field_205142_a.func_70661_as().func_75492_a((double)☃x.func_177958_n(), (double)(☃x.func_177956_o() + 1), (double)☃x.func_177952_p(), 1.0);
   }

   @Override
   public void func_75246_d() {
      this.func_205141_g();
      this.field_205142_a.func_191958_b(this.field_205142_a.field_70702_br, this.field_205142_a.field_70701_bs, this.field_205142_a.field_191988_bg, 0.02F);
      this.field_205142_a.func_70091_d(MoverType.SELF, this.field_205142_a.field_70159_w, this.field_205142_a.field_70181_x, this.field_205142_a.field_70179_y);
   }

   private boolean func_205140_a(IWorldReaderBase var1, BlockPos var2) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      return (☃.func_204610_c(☃).func_206888_e() || ☃.func_177230_c() == Blocks.field_203203_C) && ☃.func_196957_g(☃, ☃, PathType.LAND);
   }
}
