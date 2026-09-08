package net.minecraft.entity.ai;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.EntityCreature;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIWanderAvoidWaterFlying extends EntityAIWanderAvoidWater {
   public EntityAIWanderAvoidWaterFlying(EntityCreature var1, double var2) {
      super(☃, ☃);
   }

   @Nullable
   @Override
   protected Vec3d func_190864_f() {
      Vec3d ☃ = null;
      if (this.field_75457_a.func_70090_H()) {
         ☃ = RandomPositionGenerator.func_191377_b(this.field_75457_a, 15, 15);
      }

      if (this.field_75457_a.func_70681_au().nextFloat() >= this.field_190865_h) {
         ☃ = this.func_192385_j();
      }

      return ☃ == null ? super.func_190864_f() : ☃;
   }

   @Nullable
   private Vec3d func_192385_j() {
      BlockPos ☃ = new BlockPos(this.field_75457_a);
      BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();

      for(BlockPos ☃xxx : BlockPos.MutableBlockPos.func_191531_b(
         MathHelper.func_76128_c(this.field_75457_a.field_70165_t - 3.0),
         MathHelper.func_76128_c(this.field_75457_a.field_70163_u - 6.0),
         MathHelper.func_76128_c(this.field_75457_a.field_70161_v - 3.0),
         MathHelper.func_76128_c(this.field_75457_a.field_70165_t + 3.0),
         MathHelper.func_76128_c(this.field_75457_a.field_70163_u + 6.0),
         MathHelper.func_76128_c(this.field_75457_a.field_70161_v + 3.0)
      )) {
         if (!☃.equals(☃xxx)) {
            Block ☃xxxx = this.field_75457_a.field_70170_p.func_180495_p(☃xx.func_189533_g(☃xxx).func_189536_c(EnumFacing.DOWN)).func_177230_c();
            boolean ☃xxxxx = ☃xxxx instanceof BlockLeaves || ☃xxxx.func_203417_a(BlockTags.field_200031_h);
            if (☃xxxxx
               && this.field_75457_a.field_70170_p.func_175623_d(☃xxx)
               && this.field_75457_a.field_70170_p.func_175623_d(☃x.func_189533_g(☃xxx).func_189536_c(EnumFacing.UP))) {
               return new Vec3d((double)☃xxx.func_177958_n(), (double)☃xxx.func_177956_o(), (double)☃xxx.func_177952_p());
            }
         }
      }

      return null;
   }
}
