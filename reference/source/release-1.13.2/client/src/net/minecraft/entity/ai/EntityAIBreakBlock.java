package net.minecraft.entity.ai;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityAIBreakBlock extends EntityAIMoveToBlock {
   private final Block field_203117_f;
   private final EntityLiving field_203118_g;
   private int field_203119_h;

   public EntityAIBreakBlock(Block var1, EntityCreature var2, double var3, int var5) {
      super(☃, ☃, 24, ☃);
      this.field_203117_f = ☃;
      this.field_203118_g = ☃;
   }

   @Override
   public boolean func_75250_a() {
      if (!this.field_203118_g.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
         return false;
      } else {
         return this.field_203118_g.func_70681_au().nextInt(20) != 0 ? false : super.func_75250_a();
      }
   }

   @Override
   protected int func_203109_a(EntityCreature var1) {
      return 0;
   }

   @Override
   public boolean func_75253_b() {
      return super.func_75253_b();
   }

   @Override
   public void func_75251_c() {
      super.func_75251_c();
      this.field_203118_g.field_70143_R = 1.0F;
   }

   @Override
   public void func_75249_e() {
      super.func_75249_e();
      this.field_203119_h = 0;
   }

   public void func_203114_b(IWorld var1, BlockPos var2) {
   }

   public void func_203116_c(World var1, BlockPos var2) {
   }

   @Override
   public void func_75246_d() {
      super.func_75246_d();
      World ☃ = this.field_203118_g.field_70170_p;
      BlockPos ☃x = new BlockPos(this.field_203118_g);
      BlockPos ☃xx = this.func_203115_a(☃x, ☃);
      Random ☃xxx = this.field_203118_g.func_70681_au();
      if (this.func_179487_f() && ☃xx != null) {
         if (this.field_203119_h > 0) {
            this.field_203118_g.field_70181_x = 0.3;
            if (!☃.field_72995_K) {
               double ☃xxxx = 0.08;
               ((WorldServer)☃)
                  .func_195598_a(
                     new ItemParticleData(Particles.field_197591_B, new ItemStack(Items.field_151110_aK)),
                     (double)☃xx.func_177958_n() + 0.5,
                     (double)☃xx.func_177956_o() + 0.7,
                     (double)☃xx.func_177952_p() + 0.5,
                     3,
                     ((double)☃xxx.nextFloat() - 0.5) * 0.08,
                     ((double)☃xxx.nextFloat() - 0.5) * 0.08,
                     ((double)☃xxx.nextFloat() - 0.5) * 0.08,
                     0.15F
                  );
            }
         }

         if (this.field_203119_h % 2 == 0) {
            this.field_203118_g.field_70181_x = -0.3;
            if (this.field_203119_h % 6 == 0) {
               this.func_203114_b(☃, this.field_179494_b);
            }
         }

         if (this.field_203119_h > 60) {
            ☃.func_175698_g(☃xx);
            if (!☃.field_72995_K) {
               for(int ☃xxxx = 0; ☃xxxx < 20; ++☃xxxx) {
                  double ☃xxxxx = ☃xxx.nextGaussian() * 0.02;
                  double ☃xxxxxx = ☃xxx.nextGaussian() * 0.02;
                  double ☃xxxxxxx = ☃xxx.nextGaussian() * 0.02;
                  ((WorldServer)☃)
                     .func_195598_a(
                        Particles.field_197598_I,
                        (double)☃xx.func_177958_n() + 0.5,
                        (double)☃xx.func_177956_o(),
                        (double)☃xx.func_177952_p() + 0.5,
                        1,
                        ☃xxxxx,
                        ☃xxxxxx,
                        ☃xxxxxxx,
                        0.15F
                     );
               }

               this.func_203116_c(☃, this.field_179494_b);
            }
         }

         ++this.field_203119_h;
      }
   }

   @Nullable
   private BlockPos func_203115_a(BlockPos var1, IBlockReader var2) {
      if (☃.func_180495_p(☃).func_177230_c() == this.field_203117_f) {
         return ☃;
      } else {
         BlockPos[] ☃ = new BlockPos[]{
            ☃.func_177977_b(), ☃.func_177976_e(), ☃.func_177974_f(), ☃.func_177978_c(), ☃.func_177968_d(), ☃.func_177977_b().func_177977_b()
         };

         for(BlockPos ☃x : ☃) {
            if (☃.func_180495_p(☃x).func_177230_c() == this.field_203117_f) {
               return ☃x;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean func_179488_a(IWorldReaderBase var1, BlockPos var2) {
      Block ☃ = ☃.func_180495_p(☃).func_177230_c();
      return ☃ == this.field_203117_f && ☃.func_180495_p(☃.func_177984_a()).func_196958_f() && ☃.func_180495_p(☃.func_177981_b(2)).func_196958_f();
   }
}
