package net.minecraft.entity.ai;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;

public class EntityAIPanic extends EntityAIBase {
   protected final EntityCreature field_75267_a;
   protected double field_75265_b;
   protected double field_75266_c;
   protected double field_75263_d;
   protected double field_75264_e;

   public EntityAIPanic(EntityCreature var1, double var2) {
      this.field_75267_a = ☃;
      this.field_75265_b = ☃;
      this.func_75248_a(1);
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_75267_a.func_70643_av() == null && !this.field_75267_a.func_70027_ad()) {
         return false;
      } else {
         if (this.field_75267_a.func_70027_ad()) {
            BlockPos ☃ = this.func_188497_a(this.field_75267_a.field_70170_p, this.field_75267_a, 5, 4);
            if (☃ != null) {
               this.field_75266_c = (double)☃.func_177958_n();
               this.field_75263_d = (double)☃.func_177956_o();
               this.field_75264_e = (double)☃.func_177952_p();
               return true;
            }
         }

         return this.func_190863_f();
      }
   }

   protected boolean func_190863_f() {
      Vec3d ☃ = RandomPositionGenerator.func_75463_a(this.field_75267_a, 5, 4);
      if (☃ == null) {
         return false;
      } else {
         this.field_75266_c = ☃.field_72450_a;
         this.field_75263_d = ☃.field_72448_b;
         this.field_75264_e = ☃.field_72449_c;
         return true;
      }
   }

   @Override
   public void func_75249_e() {
      this.field_75267_a.func_70661_as().func_75492_a(this.field_75266_c, this.field_75263_d, this.field_75264_e, this.field_75265_b);
   }

   @Override
   public boolean func_75253_b() {
      return !this.field_75267_a.func_70661_as().func_75500_f();
   }

   @Nullable
   protected BlockPos func_188497_a(IBlockReader var1, Entity var2, int var3, int var4) {
      BlockPos ☃ = new BlockPos(☃);
      int ☃x = ☃.func_177958_n();
      int ☃xx = ☃.func_177956_o();
      int ☃xxx = ☃.func_177952_p();
      float ☃xxxx = (float)(☃ * ☃ * ☃ * 2);
      BlockPos ☃xxxxx = null;
      BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxxx = ☃x - ☃; ☃xxxxxxx <= ☃x + ☃; ++☃xxxxxxx) {
         for(int ☃xxxxxxxx = ☃xx - ☃; ☃xxxxxxxx <= ☃xx + ☃; ++☃xxxxxxxx) {
            for(int ☃xxxxxxxxx = ☃xxx - ☃; ☃xxxxxxxxx <= ☃xxx + ☃; ++☃xxxxxxxxx) {
               ☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx);
               if (☃.func_204610_c(☃xxxxxx).func_206884_a(FluidTags.field_206959_a)) {
                  float ☃xxxxxxxxxx = (float)(
                     (☃xxxxxxx - ☃x) * (☃xxxxxxx - ☃x) + (☃xxxxxxxx - ☃xx) * (☃xxxxxxxx - ☃xx) + (☃xxxxxxxxx - ☃xxx) * (☃xxxxxxxxx - ☃xxx)
                  );
                  if (☃xxxxxxxxxx < ☃xxxx) {
                     ☃xxxx = ☃xxxxxxxxxx;
                     ☃xxxxx = new BlockPos(☃xxxxxx);
                  }
               }
            }
         }
      }

      return ☃xxxxx;
   }
}
