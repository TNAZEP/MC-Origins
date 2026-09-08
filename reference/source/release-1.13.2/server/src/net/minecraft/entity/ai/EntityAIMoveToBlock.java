package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public abstract class EntityAIMoveToBlock extends EntityAIBase {
   private final EntityCreature field_179495_c;
   public double field_179492_d;
   protected int field_179496_a;
   protected int field_179493_e;
   private int field_179490_f;
   protected BlockPos field_179494_b = BlockPos.field_177992_a;
   private boolean field_179491_g;
   private final int field_179497_h;
   private final int field_203113_j;
   public int field_203112_e;

   public EntityAIMoveToBlock(EntityCreature var1, double var2, int var4) {
      this(☃, ☃, ☃, 1);
   }

   public EntityAIMoveToBlock(EntityCreature var1, double var2, int var4, int var5) {
      this.field_179495_c = ☃;
      this.field_179492_d = ☃;
      this.field_179497_h = ☃;
      this.field_203112_e = 0;
      this.field_203113_j = ☃;
      this.func_75248_a(5);
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_179496_a > 0) {
         --this.field_179496_a;
         return false;
      } else {
         this.field_179496_a = this.func_203109_a(this.field_179495_c);
         return this.func_179489_g();
      }
   }

   protected int func_203109_a(EntityCreature var1) {
      return 200 + ☃.func_70681_au().nextInt(200);
   }

   @Override
   public boolean func_75253_b() {
      return this.field_179493_e >= -this.field_179490_f
         && this.field_179493_e <= 1200
         && this.func_179488_a(this.field_179495_c.field_70170_p, this.field_179494_b);
   }

   @Override
   public void func_75249_e() {
      this.field_179495_c
         .func_70661_as()
         .func_75492_a(
            (double)((float)this.field_179494_b.func_177958_n()) + 0.5,
            (double)(this.field_179494_b.func_177956_o() + 1),
            (double)((float)this.field_179494_b.func_177952_p()) + 0.5,
            this.field_179492_d
         );
      this.field_179493_e = 0;
      this.field_179490_f = this.field_179495_c.func_70681_au().nextInt(this.field_179495_c.func_70681_au().nextInt(1200) + 1200) + 1200;
   }

   public double func_203110_f() {
      return 1.0;
   }

   @Override
   public void func_75246_d() {
      if (this.field_179495_c.func_174831_c(this.field_179494_b.func_177984_a()) > this.func_203110_f()) {
         this.field_179491_g = false;
         ++this.field_179493_e;
         if (this.func_203108_i()) {
            this.field_179495_c
               .func_70661_as()
               .func_75492_a(
                  (double)((float)this.field_179494_b.func_177958_n()) + 0.5,
                  (double)(this.field_179494_b.func_177956_o() + this.func_203111_j()),
                  (double)((float)this.field_179494_b.func_177952_p()) + 0.5,
                  this.field_179492_d
               );
         }
      } else {
         this.field_179491_g = true;
         --this.field_179493_e;
      }
   }

   public boolean func_203108_i() {
      return this.field_179493_e % 40 == 0;
   }

   public int func_203111_j() {
      return 1;
   }

   protected boolean func_179487_f() {
      return this.field_179491_g;
   }

   private boolean func_179489_g() {
      int ☃ = this.field_179497_h;
      int ☃x = this.field_203113_j;
      BlockPos ☃xx = new BlockPos(this.field_179495_c);
      BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxx = this.field_203112_e; ☃xxxx <= ☃x; ☃xxxx = ☃xxxx > 0 ? -☃xxxx : 1 - ☃xxxx) {
         for(int ☃xxxxx = 0; ☃xxxxx < ☃; ++☃xxxxx) {
            for(int ☃xxxxxx = 0; ☃xxxxxx <= ☃xxxxx; ☃xxxxxx = ☃xxxxxx > 0 ? -☃xxxxxx : 1 - ☃xxxxxx) {
               for(int ☃xxxxxxx = ☃xxxxxx < ☃xxxxx && ☃xxxxxx > -☃xxxxx ? ☃xxxxx : 0; ☃xxxxxxx <= ☃xxxxx; ☃xxxxxxx = ☃xxxxxxx > 0 ? -☃xxxxxxx : 1 - ☃xxxxxxx) {
                  ☃xxx.func_189533_g(☃xx).func_196234_d(☃xxxxxx, ☃xxxx - 1, ☃xxxxxxx);
                  if (this.field_179495_c.func_180485_d(☃xxx) && this.func_179488_a(this.field_179495_c.field_70170_p, ☃xxx)) {
                     this.field_179494_b = ☃xxx;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean func_179488_a(IWorldReaderBase var1, BlockPos var2);
}
