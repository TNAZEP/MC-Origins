package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.biome.Biome;

public class EntityAIMoveIndoors extends EntityAIBase {
   private final EntityCreature field_75424_a;
   private VillageDoorInfo field_75422_b;
   private int field_75423_c = -1;
   private int field_75421_d = -1;

   public EntityAIMoveIndoors(EntityCreature var1) {
      this.field_75424_a = ☃;
      this.func_75248_a(1);
   }

   @Override
   public boolean func_75250_a() {
      BlockPos ☃ = new BlockPos(this.field_75424_a);
      if ((
            !this.field_75424_a.field_70170_p.func_72935_r()
               || this.field_75424_a.field_70170_p.func_72896_J() && this.field_75424_a.field_70170_p.func_180494_b(☃).func_201851_b() != Biome.RainType.RAIN
         )
         && this.field_75424_a.field_70170_p.field_73011_w.func_191066_m()) {
         if (this.field_75424_a.func_70681_au().nextInt(50) != 0) {
            return false;
         } else if (this.field_75423_c != -1
            && this.field_75424_a.func_70092_e((double)this.field_75423_c, this.field_75424_a.field_70163_u, (double)this.field_75421_d) < 4.0) {
            return false;
         } else {
            Village ☃x = this.field_75424_a.field_70170_p.func_175714_ae().func_176056_a(☃, 14);
            if (☃x == null) {
               return false;
            } else {
               this.field_75422_b = ☃x.func_179863_c(☃);
               return this.field_75422_b != null;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean func_75253_b() {
      return !this.field_75424_a.func_70661_as().func_75500_f();
   }

   @Override
   public void func_75249_e() {
      this.field_75423_c = -1;
      BlockPos ☃ = this.field_75422_b.func_179856_e();
      int ☃x = ☃.func_177958_n();
      int ☃xx = ☃.func_177956_o();
      int ☃xxx = ☃.func_177952_p();
      if (this.field_75424_a.func_174818_b(☃) > 256.0) {
         Vec3d ☃xxxx = RandomPositionGenerator.func_75464_a(this.field_75424_a, 14, 3, new Vec3d((double)☃x + 0.5, (double)☃xx, (double)☃xxx + 0.5));
         if (☃xxxx != null) {
            this.field_75424_a.func_70661_as().func_75492_a(☃xxxx.field_72450_a, ☃xxxx.field_72448_b, ☃xxxx.field_72449_c, 1.0);
         }
      } else {
         this.field_75424_a.func_70661_as().func_75492_a((double)☃x + 0.5, (double)☃xx, (double)☃xxx + 0.5, 1.0);
      }
   }

   @Override
   public void func_75251_c() {
      this.field_75423_c = this.field_75422_b.func_179856_e().func_177958_n();
      this.field_75421_d = this.field_75422_b.func_179856_e().func_177952_p();
      this.field_75422_b = null;
   }
}
