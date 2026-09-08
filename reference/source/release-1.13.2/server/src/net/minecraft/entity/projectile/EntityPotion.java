package net.minecraft.entity.projectile;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPotion extends EntityThrowable {
   private static final DataParameter<ItemStack> field_184545_d = EntityDataManager.func_187226_a(EntityPotion.class, DataSerializers.field_187196_f);
   private static final Logger field_184546_e = LogManager.getLogger();
   public static final Predicate<EntityLivingBase> field_190546_d = EntityPotion::func_190544_c;

   public EntityPotion(World var1) {
      super(EntityType.field_200754_at, ☃);
   }

   public EntityPotion(World var1, EntityLivingBase var2, ItemStack var3) {
      super(EntityType.field_200754_at, ☃, ☃);
      this.func_184541_a(☃);
   }

   public EntityPotion(World var1, double var2, double var4, double var6, ItemStack var8) {
      super(EntityType.field_200754_at, ☃, ☃, ☃, ☃);
      if (!☃.func_190926_b()) {
         this.func_184541_a(☃);
      }
   }

   @Override
   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184545_d, ItemStack.field_190927_a);
   }

   public ItemStack func_184543_l() {
      ItemStack ☃ = this.func_184212_Q().func_187225_a(field_184545_d);
      if (☃.func_77973_b() != Items.field_185155_bH && ☃.func_77973_b() != Items.field_185156_bI) {
         if (this.field_70170_p != null) {
            field_184546_e.error("ThrownPotion entity {} has no item?!", this.func_145782_y());
         }

         return new ItemStack(Items.field_185155_bH);
      } else {
         return ☃;
      }
   }

   public void func_184541_a(ItemStack var1) {
      this.func_184212_Q().func_187227_b(field_184545_d, ☃);
   }

   @Override
   protected float func_70185_h() {
      return 0.05F;
   }

   @Override
   protected void func_70184_a(RayTraceResult var1) {
      if (!this.field_70170_p.field_72995_K) {
         ItemStack ☃ = this.func_184543_l();
         PotionType ☃x = PotionUtils.func_185191_c(☃);
         List<PotionEffect> ☃xx = PotionUtils.func_185189_a(☃);
         boolean ☃xxx = ☃x == PotionTypes.field_185230_b && ☃xx.isEmpty();
         if (☃.field_72313_a == RayTraceResult.Type.BLOCK && ☃xxx) {
            BlockPos ☃xxxx = ☃.func_178782_a().func_177972_a(☃.field_178784_b);
            this.func_184542_a(☃xxxx, ☃.field_178784_b);

            for(EnumFacing ☃xxxxx : EnumFacing.Plane.HORIZONTAL) {
               this.func_184542_a(☃xxxx.func_177972_a(☃xxxxx), ☃xxxxx);
            }
         }

         if (☃xxx) {
            this.func_190545_n();
         } else if (!☃xx.isEmpty()) {
            if (this.func_184544_n()) {
               this.func_190542_a(☃, ☃x);
            } else {
               this.func_190543_a(☃, ☃xx);
            }
         }

         int ☃ = ☃x.func_185172_c() ? 2007 : 2002;
         this.field_70170_p.func_175718_b(☃, new BlockPos(this), PotionUtils.func_190932_c(☃));
         this.func_70106_y();
      }
   }

   private void func_190545_n() {
      AxisAlignedBB ☃ = this.func_174813_aQ().func_72314_b(4.0, 2.0, 4.0);
      List<EntityLivingBase> ☃x = this.field_70170_p.func_175647_a(EntityLivingBase.class, ☃, field_190546_d);
      if (!☃x.isEmpty()) {
         for(EntityLivingBase ☃xx : ☃x) {
            double ☃xxx = this.func_70068_e(☃xx);
            if (☃xxx < 16.0 && func_190544_c(☃xx)) {
               ☃xx.func_70097_a(DamageSource.field_76369_e, 1.0F);
            }
         }
      }
   }

   private void func_190543_a(RayTraceResult var1, List<PotionEffect> var2) {
      AxisAlignedBB ☃ = this.func_174813_aQ().func_72314_b(4.0, 2.0, 4.0);
      List<EntityLivingBase> ☃x = this.field_70170_p.func_72872_a(EntityLivingBase.class, ☃);
      if (!☃x.isEmpty()) {
         for(EntityLivingBase ☃xx : ☃x) {
            if (☃xx.func_184603_cC()) {
               double ☃xxx = this.func_70068_e(☃xx);
               if (☃xxx < 16.0) {
                  double ☃xxxx = 1.0 - Math.sqrt(☃xxx) / 4.0;
                  if (☃xx == ☃.field_72308_g) {
                     ☃xxxx = 1.0;
                  }

                  for(PotionEffect ☃xxxx : ☃) {
                     Potion ☃xxxxx = ☃xxxx.func_188419_a();
                     if (☃xxxxx.func_76403_b()) {
                        ☃xxxxx.func_180793_a(this, this.func_85052_h(), ☃xx, ☃xxxx.func_76458_c(), ☃xxxx);
                     } else {
                        int ☃xxxxx = (int)(☃xxxx * (double)☃xxxx.func_76459_b() + 0.5);
                        if (☃xxxxx > 20) {
                           ☃xx.func_195064_c(new PotionEffect(☃xxxxx, ☃xxxxx, ☃xxxx.func_76458_c(), ☃xxxx.func_82720_e(), ☃xxxx.func_188418_e()));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void func_190542_a(ItemStack var1, PotionType var2) {
      EntityAreaEffectCloud ☃ = new EntityAreaEffectCloud(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      ☃.func_184481_a(this.func_85052_h());
      ☃.func_184483_a(3.0F);
      ☃.func_184495_b(-0.5F);
      ☃.func_184485_d(10);
      ☃.func_184487_c(-☃.func_184490_j() / (float)☃.func_184489_o());
      ☃.func_184484_a(☃);

      for(PotionEffect ☃x : PotionUtils.func_185190_b(☃)) {
         ☃.func_184496_a(new PotionEffect(☃x));
      }

      NBTTagCompound ☃x = ☃.func_77978_p();
      if (☃x != null && ☃x.func_150297_b("CustomPotionColor", 99)) {
         ☃.func_184482_a(☃x.func_74762_e("CustomPotionColor"));
      }

      this.field_70170_p.func_72838_d(☃);
   }

   private boolean func_184544_n() {
      return this.func_184543_l().func_77973_b() == Items.field_185156_bI;
   }

   private void func_184542_a(BlockPos var1, EnumFacing var2) {
      if (this.field_70170_p.func_180495_p(☃).func_177230_c() == Blocks.field_150480_ab) {
         this.field_70170_p.func_175719_a(null, ☃.func_177972_a(☃), ☃.func_176734_d());
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      ItemStack ☃ = ItemStack.func_199557_a(☃.func_74775_l("Potion"));
      if (☃.func_190926_b()) {
         this.func_70106_y();
      } else {
         this.func_184541_a(☃);
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ItemStack ☃ = this.func_184543_l();
      if (!☃.func_190926_b()) {
         ☃.func_74782_a("Potion", ☃.func_77955_b(new NBTTagCompound()));
      }
   }

   private static boolean func_190544_c(EntityLivingBase var0) {
      return ☃ instanceof EntityEnderman || ☃ instanceof EntityBlaze;
   }
}
