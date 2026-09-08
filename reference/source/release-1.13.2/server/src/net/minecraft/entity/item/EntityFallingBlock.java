package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockConcretePowder;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFallingBlock extends Entity {
   private IBlockState field_175132_d = Blocks.field_150354_m.func_176223_P();
   public int field_145812_b;
   public boolean field_145813_c = true;
   private boolean field_145808_f;
   private boolean field_145809_g;
   private int field_145815_h = 40;
   private float field_145816_i = 2.0F;
   public NBTTagCompound field_145810_d;
   protected static final DataParameter<BlockPos> field_184532_d = EntityDataManager.func_187226_a(EntityFallingBlock.class, DataSerializers.field_187200_j);

   public EntityFallingBlock(World var1) {
      super(EntityType.field_200809_w, ☃);
   }

   public EntityFallingBlock(World var1, double var2, double var4, double var6, IBlockState var8) {
      this(☃);
      this.field_175132_d = ☃;
      this.field_70156_m = true;
      this.func_70105_a(0.98F, 0.98F);
      this.func_70107_b(☃, ☃ + (double)((1.0F - this.field_70131_O) / 2.0F), ☃);
      this.field_70159_w = 0.0;
      this.field_70181_x = 0.0;
      this.field_70179_y = 0.0;
      this.field_70169_q = ☃;
      this.field_70167_r = ☃;
      this.field_70166_s = ☃;
      this.func_184530_a(new BlockPos(this));
   }

   @Override
   public boolean func_70075_an() {
      return false;
   }

   public void func_184530_a(BlockPos var1) {
      this.field_70180_af.func_187227_b(field_184532_d, ☃);
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184532_d, BlockPos.field_177992_a);
   }

   @Override
   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   @Override
   public void func_70071_h_() {
      if (this.field_175132_d.func_196958_f()) {
         this.func_70106_y();
      } else {
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         Block ☃ = this.field_175132_d.func_177230_c();
         if (this.field_145812_b++ == 0) {
            BlockPos ☃x = new BlockPos(this);
            if (this.field_70170_p.func_180495_p(☃x).func_177230_c() == ☃) {
               this.field_70170_p.func_175698_g(☃x);
            } else if (!this.field_70170_p.field_72995_K) {
               this.func_70106_y();
               return;
            }
         }

         if (!this.func_189652_ae()) {
            this.field_70181_x -= 0.04F;
         }

         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         if (!this.field_70170_p.field_72995_K) {
            BlockPos ☃ = new BlockPos(this);
            boolean ☃x = this.field_175132_d.func_177230_c() instanceof BlockConcretePowder;
            boolean ☃xx = ☃x && this.field_70170_p.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a);
            double ☃xxx = this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y;
            if (☃x && ☃xxx > 1.0) {
               RayTraceResult ☃xxxx = this.field_70170_p
                  .func_200260_a(
                     new Vec3d(this.field_70169_q, this.field_70167_r, this.field_70166_s),
                     new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v),
                     RayTraceFluidMode.SOURCE_ONLY
                  );
               if (☃xxxx != null && this.field_70170_p.func_204610_c(☃xxxx.func_178782_a()).func_206884_a(FluidTags.field_206959_a)) {
                  ☃ = ☃xxxx.func_178782_a();
                  ☃xx = true;
               }
            }

            if (!this.field_70122_E && !☃xx) {
               if (this.field_145812_b > 100 && !this.field_70170_p.field_72995_K && (☃.func_177956_o() < 1 || ☃.func_177956_o() > 256)
                  || this.field_145812_b > 600) {
                  if (this.field_145813_c && this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
                     this.func_199703_a(☃);
                  }

                  this.func_70106_y();
               }
            } else {
               IBlockState ☃ = this.field_70170_p.func_180495_p(☃);
               if (!☃xx
                  && BlockFalling.func_185759_i(
                     this.field_70170_p.func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u - 0.01F, this.field_70161_v))
                  )) {
                  this.field_70122_E = false;
                  return;
               }

               this.field_70159_w *= 0.7F;
               this.field_70179_y *= 0.7F;
               this.field_70181_x *= -0.5;
               if (☃.func_177230_c() != Blocks.field_196603_bb) {
                  this.func_70106_y();
                  if (!this.field_145808_f) {
                     if (☃.func_185904_a().func_76222_j()
                        && (☃xx || !BlockFalling.func_185759_i(this.field_70170_p.func_180495_p(☃.func_177977_b())))
                        && this.field_70170_p.func_180501_a(☃, this.field_175132_d, 3)) {
                        if (☃ instanceof BlockFalling) {
                           ((BlockFalling)☃).func_176502_a_(this.field_70170_p, ☃, this.field_175132_d, ☃);
                        }

                        if (this.field_145810_d != null && ☃ instanceof ITileEntityProvider) {
                           TileEntity ☃ = this.field_70170_p.func_175625_s(☃);
                           if (☃ != null) {
                              NBTTagCompound ☃x = ☃.func_189515_b(new NBTTagCompound());

                              for(String ☃xx : this.field_145810_d.func_150296_c()) {
                                 INBTBase ☃xxx = this.field_145810_d.func_74781_a(☃xx);
                                 if (!"x".equals(☃xx) && !"y".equals(☃xx) && !"z".equals(☃xx)) {
                                    ☃x.func_74782_a(☃xx, ☃xxx.func_74737_b());
                                 }
                              }

                              ☃.func_145839_a(☃x);
                              ☃.func_70296_d();
                           }
                        }
                     } else if (this.field_145813_c && this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
                        this.func_199703_a(☃);
                     }
                  } else if (☃ instanceof BlockFalling) {
                     ((BlockFalling)☃).func_190974_b(this.field_70170_p, ☃);
                  }
               }
            }
         }

         this.field_70159_w *= 0.98F;
         this.field_70181_x *= 0.98F;
         this.field_70179_y *= 0.98F;
      }
   }

   @Override
   public void func_180430_e(float var1, float var2) {
      if (this.field_145809_g) {
         int ☃ = MathHelper.func_76123_f(☃ - 1.0F);
         if (☃ > 0) {
            List<Entity> ☃x = Lists.<Entity>newArrayList(this.field_70170_p.func_72839_b(this, this.func_174813_aQ()));
            boolean ☃xx = this.field_175132_d.func_203425_a(BlockTags.field_200572_k);
            DamageSource ☃xxx = ☃xx ? DamageSource.field_82728_o : DamageSource.field_82729_p;

            for(Entity ☃xxxx : ☃x) {
               ☃xxxx.func_70097_a(☃xxx, (float)Math.min(MathHelper.func_76141_d((float)☃ * this.field_145816_i), this.field_145815_h));
            }

            if (☃xx && (double)this.field_70146_Z.nextFloat() < 0.05F + (double)☃ * 0.05) {
               IBlockState ☃xxxx = BlockAnvil.func_196433_f(this.field_175132_d);
               if (☃xxxx == null) {
                  this.field_145808_f = true;
               } else {
                  this.field_175132_d = ☃xxxx;
               }
            }
         }
      }
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      ☃.func_74782_a("BlockState", NBTUtil.func_190009_a(this.field_175132_d));
      ☃.func_74768_a("Time", this.field_145812_b);
      ☃.func_74757_a("DropItem", this.field_145813_c);
      ☃.func_74757_a("HurtEntities", this.field_145809_g);
      ☃.func_74776_a("FallHurtAmount", this.field_145816_i);
      ☃.func_74768_a("FallHurtMax", this.field_145815_h);
      if (this.field_145810_d != null) {
         ☃.func_74782_a("TileEntityData", this.field_145810_d);
      }
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      this.field_175132_d = NBTUtil.func_190008_d(☃.func_74775_l("BlockState"));
      this.field_145812_b = ☃.func_74762_e("Time");
      if (☃.func_150297_b("HurtEntities", 99)) {
         this.field_145809_g = ☃.func_74767_n("HurtEntities");
         this.field_145816_i = ☃.func_74760_g("FallHurtAmount");
         this.field_145815_h = ☃.func_74762_e("FallHurtMax");
      } else if (this.field_175132_d.func_203425_a(BlockTags.field_200572_k)) {
         this.field_145809_g = true;
      }

      if (☃.func_150297_b("DropItem", 99)) {
         this.field_145813_c = ☃.func_74767_n("DropItem");
      }

      if (☃.func_150297_b("TileEntityData", 10)) {
         this.field_145810_d = ☃.func_74775_l("TileEntityData");
      }

      if (this.field_175132_d.func_196958_f()) {
         this.field_175132_d = Blocks.field_150354_m.func_176223_P();
      }
   }

   public void func_145806_a(boolean var1) {
      this.field_145809_g = ☃;
   }

   @Override
   public void func_85029_a(CrashReportCategory var1) {
      super.func_85029_a(☃);
      ☃.func_71507_a("Immitating BlockState", this.field_175132_d.toString());
   }

   public IBlockState func_195054_l() {
      return this.field_175132_d;
   }

   @Override
   public boolean func_184213_bq() {
      return true;
   }
}
