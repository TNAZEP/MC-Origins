package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityAIFindWater extends EntityAIBase {
   private final EntityCreature field_205152_a;

   public EntityAIFindWater(EntityCreature var1) {
      this.field_205152_a = ☃;
   }

   @Override
   public boolean func_75250_a() {
      return this.field_205152_a.field_70122_E
         && !this.field_205152_a.field_70170_p.func_204610_c(new BlockPos(this.field_205152_a)).func_206884_a(FluidTags.field_206959_a);
   }

   @Override
   public void func_75249_e() {
      BlockPos ☃ = null;

      for(BlockPos ☃x : BlockPos.MutableBlockPos.func_191531_b(
         MathHelper.func_76128_c(this.field_205152_a.field_70165_t - 2.0),
         MathHelper.func_76128_c(this.field_205152_a.field_70163_u - 2.0),
         MathHelper.func_76128_c(this.field_205152_a.field_70161_v - 2.0),
         MathHelper.func_76128_c(this.field_205152_a.field_70165_t + 2.0),
         MathHelper.func_76128_c(this.field_205152_a.field_70163_u),
         MathHelper.func_76128_c(this.field_205152_a.field_70161_v + 2.0)
      )) {
         if (this.field_205152_a.field_70170_p.func_204610_c(☃x).func_206884_a(FluidTags.field_206959_a)) {
            ☃ = ☃x;
            break;
         }
      }

      if (☃ != null) {
         this.field_205152_a.func_70605_aq().func_75642_a((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p(), 1.0);
      }
   }
}
