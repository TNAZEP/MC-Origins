package net.minecraft.entity.ai;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIWanderSwim extends EntityAIWander {
   public EntityAIWanderSwim(EntityCreature var1, double var2, int var4) {
      super(☃, ☃, ☃);
   }

   @Nullable
   @Override
   protected Vec3d func_190864_f() {
      Vec3d ☃ = RandomPositionGenerator.func_75463_a(this.field_75457_a, 10, 7);
      int ☃x = 0;

      while(
         ☃ != null
            && !this.field_75457_a
               .field_70170_p
               .func_180495_p(new BlockPos(☃))
               .func_196957_g(this.field_75457_a.field_70170_p, new BlockPos(☃), PathType.WATER)
            && ☃x++ < 10
      ) {
         ☃ = RandomPositionGenerator.func_75463_a(this.field_75457_a, 10, 7);
      }

      return ☃;
   }
}
