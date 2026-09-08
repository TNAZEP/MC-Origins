package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TileEntityConduit extends TileEntity implements ITickable {
   private static final Block[] field_205042_e = new Block[]{Blocks.field_180397_cI, Blocks.field_196779_gQ, Blocks.field_180398_cJ, Blocks.field_196781_gR};
   public int field_205041_a;
   private float field_205043_f;
   private boolean field_205045_h;
   private boolean field_207738_h;
   private final List<BlockPos> field_205046_i = Lists.<BlockPos>newArrayList();
   private EntityLivingBase field_205047_j;
   private UUID field_205048_k;
   private long field_205740_k;

   public TileEntityConduit() {
      this(TileEntityType.field_205166_z);
   }

   public TileEntityConduit(TileEntityType<?> var1) {
      super(☃);
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      if (☃.func_74764_b("target_uuid")) {
         this.field_205048_k = NBTUtil.func_186860_b(☃.func_74775_l("target_uuid"));
      } else {
         this.field_205048_k = null;
      }
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      if (this.field_205047_j != null) {
         ☃.func_74782_a("target_uuid", NBTUtil.func_186862_a(this.field_205047_j.func_110124_au()));
      }

      return ☃;
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 5, this.func_189517_E_());
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   @Override
   public void func_73660_a() {
      ++this.field_205041_a;
      long ☃ = this.field_145850_b.func_82737_E();
      if (☃ % 40L == 0L) {
         this.func_205739_a(this.func_205038_d());
         if (!this.field_145850_b.field_72995_K && this.func_205039_c()) {
            this.func_205030_f();
            this.func_205031_h();
         }
      }

      if (☃ % 80L == 0L && this.func_205039_c()) {
         this.func_205738_a(SoundEvents.field_206934_aN);
      }

      if (☃ > this.field_205740_k && this.func_205039_c()) {
         this.field_205740_k = ☃ + 60L + (long)this.field_145850_b.func_201674_k().nextInt(40);
         this.func_205738_a(SoundEvents.field_206935_aO);
      }

      if (this.field_145850_b.field_72995_K) {
         this.func_205040_i();
         this.func_205037_l();
         if (this.func_205039_c()) {
            ++this.field_205043_f;
         }
      }
   }

   private boolean func_205038_d() {
      this.field_205046_i.clear();

      for(int ☃ = -1; ☃ <= 1; ++☃) {
         for(int ☃x = -1; ☃x <= 1; ++☃x) {
            for(int ☃xx = -1; ☃xx <= 1; ++☃xx) {
               BlockPos ☃xxx = this.field_174879_c.func_177982_a(☃, ☃x, ☃xx);
               if (!this.field_145850_b.func_201671_F(☃xxx)) {
                  return false;
               }
            }
         }
      }

      for(int ☃ = -2; ☃ <= 2; ++☃) {
         for(int ☃x = -2; ☃x <= 2; ++☃x) {
            for(int ☃xx = -2; ☃xx <= 2; ++☃xx) {
               int ☃xxx = Math.abs(☃);
               int ☃xxxx = Math.abs(☃x);
               int ☃xxxxx = Math.abs(☃xx);
               if ((☃xxx > 1 || ☃xxxx > 1 || ☃xxxxx > 1)
                  && (☃ == 0 && (☃xxxx == 2 || ☃xxxxx == 2) || ☃x == 0 && (☃xxx == 2 || ☃xxxxx == 2) || ☃xx == 0 && (☃xxx == 2 || ☃xxxx == 2))) {
                  BlockPos ☃xxxxxx = this.field_174879_c.func_177982_a(☃, ☃x, ☃xx);
                  IBlockState ☃xxxxxxx = this.field_145850_b.func_180495_p(☃xxxxxx);

                  for(Block ☃xxxxxxxx : field_205042_e) {
                     if (☃xxxxxxx.func_177230_c() == ☃xxxxxxxx) {
                        this.field_205046_i.add(☃xxxxxx);
                     }
                  }
               }
            }
         }
      }

      this.func_207736_b(this.field_205046_i.size() >= 42);
      return this.field_205046_i.size() >= 16;
   }

   private void func_205030_f() {
      int ☃ = this.field_205046_i.size();
      int ☃x = ☃ / 7 * 16;
      int ☃xx = this.field_174879_c.func_177958_n();
      int ☃xxx = this.field_174879_c.func_177956_o();
      int ☃xxxx = this.field_174879_c.func_177952_p();
      AxisAlignedBB ☃xxxxx = new AxisAlignedBB((double)☃xx, (double)☃xxx, (double)☃xxxx, (double)(☃xx + 1), (double)(☃xxx + 1), (double)(☃xxxx + 1))
         .func_186662_g((double)☃x)
         .func_72321_a(0.0, (double)this.field_145850_b.func_72800_K(), 0.0);
      List<EntityPlayer> ☃xxxxxx = this.field_145850_b.func_72872_a(EntityPlayer.class, ☃xxxxx);
      if (!☃xxxxxx.isEmpty()) {
         for(EntityPlayer ☃xxxxxxx : ☃xxxxxx) {
            if (this.field_174879_c.func_196233_m(new BlockPos(☃xxxxxxx)) <= (double)☃x && ☃xxxxxxx.func_70026_G()) {
               ☃xxxxxxx.func_195064_c(new PotionEffect(MobEffects.field_205136_C, 260, 0, true, true));
            }
         }
      }
   }

   private void func_205031_h() {
      EntityLivingBase ☃ = this.field_205047_j;
      int ☃x = this.field_205046_i.size();
      if (☃x < 42) {
         this.field_205047_j = null;
      } else if (this.field_205047_j == null && this.field_205048_k != null) {
         this.field_205047_j = this.func_205035_k();
         this.field_205048_k = null;
      } else if (this.field_205047_j == null) {
         List<EntityLivingBase> ☃ = this.field_145850_b
            .func_175647_a(EntityLivingBase.class, this.func_205034_j(), var0 -> var0 instanceof IMob && var0.func_70026_G());
         if (!☃.isEmpty()) {
            this.field_205047_j = (EntityLivingBase)☃.get(this.field_145850_b.field_73012_v.nextInt(☃.size()));
         }
      } else if (!this.field_205047_j.func_70089_S() || this.field_174879_c.func_196233_m(new BlockPos(this.field_205047_j)) > 8.0) {
         this.field_205047_j = null;
      }

      if (this.field_205047_j != null) {
         this.field_145850_b
            .func_184148_a(
               null,
               this.field_205047_j.field_70165_t,
               this.field_205047_j.field_70163_u,
               this.field_205047_j.field_70161_v,
               SoundEvents.field_206936_aP,
               SoundCategory.BLOCKS,
               1.0F,
               1.0F
            );
         this.field_205047_j.func_70097_a(DamageSource.field_76376_m, 4.0F);
      }

      if (☃ != this.field_205047_j) {
         IBlockState ☃ = this.func_195044_w();
         this.field_145850_b.func_184138_a(this.field_174879_c, ☃, ☃, 2);
      }
   }

   private void func_205040_i() {
      if (this.field_205048_k == null) {
         this.field_205047_j = null;
      } else if (this.field_205047_j == null || !this.field_205047_j.func_110124_au().equals(this.field_205048_k)) {
         this.field_205047_j = this.func_205035_k();
         if (this.field_205047_j == null) {
            this.field_205048_k = null;
         }
      }
   }

   private AxisAlignedBB func_205034_j() {
      int ☃ = this.field_174879_c.func_177958_n();
      int ☃x = this.field_174879_c.func_177956_o();
      int ☃xx = this.field_174879_c.func_177952_p();
      return new AxisAlignedBB((double)☃, (double)☃x, (double)☃xx, (double)(☃ + 1), (double)(☃x + 1), (double)(☃xx + 1)).func_186662_g(8.0);
   }

   @Nullable
   private EntityLivingBase func_205035_k() {
      List<EntityLivingBase> ☃ = this.field_145850_b
         .func_175647_a(EntityLivingBase.class, this.func_205034_j(), var1x -> var1x.func_110124_au().equals(this.field_205048_k));
      return ☃.size() == 1 ? (EntityLivingBase)☃.get(0) : null;
   }

   private void func_205037_l() {
      Random ☃ = this.field_145850_b.field_73012_v;
      float ☃x = MathHelper.func_76126_a((float)(this.field_205041_a + 35) * 0.1F) / 2.0F + 0.5F;
      ☃x = (☃x * ☃x + ☃x) * 0.3F;
      Vec3d ☃xx = new Vec3d(
         (double)((float)this.field_174879_c.func_177958_n() + 0.5F),
         (double)((float)this.field_174879_c.func_177956_o() + 1.5F + ☃x),
         (double)((float)this.field_174879_c.func_177952_p() + 0.5F)
      );

      for(BlockPos ☃xxx : this.field_205046_i) {
         if (☃.nextInt(50) == 0) {
            float ☃xxxx = -0.5F + ☃.nextFloat();
            float ☃xxxxx = -2.0F + ☃.nextFloat();
            float ☃xxxxxx = -0.5F + ☃.nextFloat();
            BlockPos ☃xxxxxxx = ☃xxx.func_177973_b(this.field_174879_c);
            Vec3d ☃xxxxxxxx = new Vec3d((double)☃xxxx, (double)☃xxxxx, (double)☃xxxxxx)
               .func_72441_c((double)☃xxxxxxx.func_177958_n(), (double)☃xxxxxxx.func_177956_o(), (double)☃xxxxxxx.func_177952_p());
            this.field_145850_b
               .func_195594_a(
                  Particles.field_205167_W,
                  ☃xx.field_72450_a,
                  ☃xx.field_72448_b,
                  ☃xx.field_72449_c,
                  ☃xxxxxxxx.field_72450_a,
                  ☃xxxxxxxx.field_72448_b,
                  ☃xxxxxxxx.field_72449_c
               );
         }
      }

      if (this.field_205047_j != null) {
         Vec3d ☃xxx = new Vec3d(
            this.field_205047_j.field_70165_t,
            this.field_205047_j.field_70163_u + (double)this.field_205047_j.func_70047_e(),
            this.field_205047_j.field_70161_v
         );
         float ☃xxxx = (-0.5F + ☃.nextFloat()) * (3.0F + this.field_205047_j.field_70130_N);
         float ☃xxxxx = -1.0F + ☃.nextFloat() * this.field_205047_j.field_70131_O;
         float ☃xxxxxx = (-0.5F + ☃.nextFloat()) * (3.0F + this.field_205047_j.field_70130_N);
         Vec3d ☃xxxxxxx = new Vec3d((double)☃xxxx, (double)☃xxxxx, (double)☃xxxxxx);
         this.field_145850_b
            .func_195594_a(
               Particles.field_205167_W,
               ☃xxx.field_72450_a,
               ☃xxx.field_72448_b,
               ☃xxx.field_72449_c,
               ☃xxxxxxx.field_72450_a,
               ☃xxxxxxx.field_72448_b,
               ☃xxxxxxx.field_72449_c
            );
      }
   }

   public boolean func_205039_c() {
      return this.field_205045_h;
   }

   private void func_205739_a(boolean var1) {
      if (☃ != this.field_205045_h) {
         this.func_205738_a(☃ ? SoundEvents.field_206933_aM : SoundEvents.field_206937_aQ);
      }

      this.field_205045_h = ☃;
   }

   private void func_207736_b(boolean var1) {
      this.field_207738_h = ☃;
   }

   public void func_205738_a(SoundEvent var1) {
      this.field_145850_b.func_184133_a(null, this.field_174879_c, ☃, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }
}
