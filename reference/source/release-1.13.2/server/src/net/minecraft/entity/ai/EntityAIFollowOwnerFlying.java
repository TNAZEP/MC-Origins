package net.minecraft.entity.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;

public class EntityAIFollowOwnerFlying extends EntityAIFollowOwner {
   public EntityAIFollowOwnerFlying(EntityTameable var1, double var2, float var4, float var5) {
      super(☃, ☃, ☃, ☃);
   }

   @Override
   protected boolean func_192381_a(int var1, int var2, int var3, int var4, int var5) {
      IBlockState ☃ = this.field_75342_a.func_180495_p(new BlockPos(☃ + ☃, ☃ - 1, ☃ + ☃));
      return (☃.func_185896_q() || ☃.func_203425_a(BlockTags.field_206952_E))
         && this.field_75342_a.func_175623_d(new BlockPos(☃ + ☃, ☃, ☃ + ☃))
         && this.field_75342_a.func_175623_d(new BlockPos(☃ + ☃, ☃ + 1, ☃ + ☃));
   }
}
