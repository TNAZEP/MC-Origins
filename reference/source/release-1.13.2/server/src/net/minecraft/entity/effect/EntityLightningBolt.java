package net.minecraft.entity.effect;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityLightningBolt extends EntityWeatherEffect {
   private int field_70262_b;
   public long field_70264_a;
   private int field_70263_c;
   private final boolean field_184529_d;
   @Nullable
   private EntityPlayerMP field_204810_e;

   public EntityLightningBolt(World var1, double var2, double var4, double var6, boolean var8) {
      super(EntityType.field_200728_aG, ☃);
      this.func_70012_b(☃, ☃, ☃, 0.0F, 0.0F);
      this.field_70262_b = 2;
      this.field_70264_a = this.field_70146_Z.nextLong();
      this.field_70263_c = this.field_70146_Z.nextInt(3) + 1;
      this.field_184529_d = ☃;
      EnumDifficulty ☃ = ☃.func_175659_aa();
      if (☃ == EnumDifficulty.NORMAL || ☃ == EnumDifficulty.HARD) {
         this.func_195053_a(4);
      }
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.WEATHER;
   }

   public void func_204809_d(@Nullable EntityPlayerMP var1) {
      this.field_204810_e = ☃;
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70262_b == 2) {
         this.field_70170_p
            .func_184148_a(
               null,
               this.field_70165_t,
               this.field_70163_u,
               this.field_70161_v,
               SoundEvents.field_187754_de,
               SoundCategory.WEATHER,
               10000.0F,
               0.8F + this.field_70146_Z.nextFloat() * 0.2F
            );
         this.field_70170_p
            .func_184148_a(
               null,
               this.field_70165_t,
               this.field_70163_u,
               this.field_70161_v,
               SoundEvents.field_187752_dd,
               SoundCategory.WEATHER,
               2.0F,
               0.5F + this.field_70146_Z.nextFloat() * 0.2F
            );
      }

      --this.field_70262_b;
      if (this.field_70262_b < 0) {
         if (this.field_70263_c == 0) {
            this.func_70106_y();
         } else if (this.field_70262_b < -this.field_70146_Z.nextInt(10)) {
            --this.field_70263_c;
            this.field_70262_b = 1;
            this.field_70264_a = this.field_70146_Z.nextLong();
            this.func_195053_a(0);
         }
      }

      if (this.field_70262_b >= 0) {
         if (this.field_70170_p.field_72995_K) {
            this.field_70170_p.func_175702_c(2);
         } else if (!this.field_184529_d) {
            double ☃ = 3.0;
            List<Entity> ☃x = this.field_70170_p
               .func_72839_b(
                  this,
                  new AxisAlignedBB(
                     this.field_70165_t - 3.0,
                     this.field_70163_u - 3.0,
                     this.field_70161_v - 3.0,
                     this.field_70165_t + 3.0,
                     this.field_70163_u + 6.0 + 3.0,
                     this.field_70161_v + 3.0
                  )
               );

            for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
               Entity ☃xxx = (Entity)☃x.get(☃xx);
               ☃xxx.func_70077_a(this);
            }

            if (this.field_204810_e != null) {
               CriteriaTriggers.field_204812_E.func_204814_a(this.field_204810_e, ☃x);
            }
         }
      }
   }

   private void func_195053_a(int var1) {
      if (!this.field_184529_d && !this.field_70170_p.field_72995_K && this.field_70170_p.func_82736_K().func_82766_b("doFireTick")) {
         IBlockState ☃ = Blocks.field_150480_ab.func_176223_P();
         BlockPos ☃x = new BlockPos(this);
         if (this.field_70170_p.func_205050_e(☃x, 10) && this.field_70170_p.func_180495_p(☃x).func_196958_f() && ☃.func_196955_c(this.field_70170_p, ☃x)) {
            this.field_70170_p.func_175656_a(☃x, ☃);
         }

         for(int ☃ = 0; ☃ < ☃; ++☃) {
            BlockPos ☃x = ☃x.func_177982_a(this.field_70146_Z.nextInt(3) - 1, this.field_70146_Z.nextInt(3) - 1, this.field_70146_Z.nextInt(3) - 1);
            if (this.field_70170_p.func_180495_p(☃x).func_196958_f() && ☃.func_196955_c(this.field_70170_p, ☃x)) {
               this.field_70170_p.func_175656_a(☃x, ☃);
            }
         }
      }
   }

   @Override
   protected void func_70088_a() {
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
   }
}
