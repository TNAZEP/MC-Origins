package net.minecraft.entity.monster;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPhantom extends EntityFlying implements IMob {
   private static final DataParameter<Integer> field_203035_a = EntityDataManager.func_187226_a(EntityPhantom.class, DataSerializers.field_187192_b);
   private Vec3d field_203036_b = Vec3d.field_186680_a;
   private BlockPos field_203037_c = BlockPos.field_177992_a;
   private EntityPhantom.AttackPhase field_203038_bx = EntityPhantom.AttackPhase.CIRCLE;

   public EntityPhantom(World var1) {
      super(EntityType.field_203097_aH, ☃);
      this.field_70728_aV = 5;
      this.func_70105_a(0.9F, 0.5F);
      this.field_70765_h = new EntityPhantom.MoveHelper(this);
      this.field_70749_g = new EntityPhantom.LookHelper(this);
   }

   @Override
   protected EntityBodyHelper func_184650_s() {
      return new EntityPhantom.BodyHelper(this);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityPhantom.AIPickAttack());
      this.field_70714_bg.func_75776_a(2, new EntityPhantom.AISweepAttack());
      this.field_70714_bg.func_75776_a(3, new EntityPhantom.AIOrbitPoint());
      this.field_70715_bh.func_75776_a(1, new EntityPhantom.AIAttackPlayer());
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_203035_a, 0);
   }

   public void func_203034_a(int var1) {
      if (☃ < 0) {
         ☃ = 0;
      } else if (☃ > 64) {
         ☃ = 64;
      }

      this.field_70180_af.func_187227_b(field_203035_a, ☃);
      this.func_203033_m();
   }

   public void func_203033_m() {
      int ☃ = this.field_70180_af.func_187225_a(field_203035_a);
      this.func_70105_a(0.9F + 0.2F * (float)☃, 0.5F + 0.1F * (float)☃);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a((double)(6 + ☃));
   }

   public int func_203032_dq() {
      return this.field_70180_af.func_187225_a(field_203035_a);
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O * 0.35F;
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_203035_a.equals(☃)) {
         this.func_203033_m();
      }

      super.func_184206_a(☃);
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K) {
         float ☃ = MathHelper.func_76134_b((float)(this.func_145782_y() * 3 + this.field_70173_aa) * 0.13F + (float) Math.PI);
         float ☃x = MathHelper.func_76134_b((float)(this.func_145782_y() * 3 + this.field_70173_aa + 1) * 0.13F + (float) Math.PI);
         if (☃ > 0.0F && ☃x <= 0.0F) {
            this.field_70170_p
               .func_184134_a(
                  this.field_70165_t,
                  this.field_70163_u,
                  this.field_70161_v,
                  SoundEvents.field_206944_gn,
                  this.func_184176_by(),
                  0.95F + this.field_70146_Z.nextFloat() * 0.05F,
                  0.95F + this.field_70146_Z.nextFloat() * 0.05F,
                  false
               );
         }

         int ☃ = this.func_203032_dq();
         float ☃x = MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)☃);
         float ☃xx = MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)☃);
         float ☃xxx = (0.3F + ☃ * 0.45F) * ((float)☃ * 0.2F + 1.0F);
         this.field_70170_p
            .func_195594_a(
               Particles.field_197596_G, this.field_70165_t + (double)☃x, this.field_70163_u + (double)☃xxx, this.field_70161_v + (double)☃xx, 0.0, 0.0, 0.0
            );
         this.field_70170_p
            .func_195594_a(
               Particles.field_197596_G, this.field_70165_t - (double)☃x, this.field_70163_u + (double)☃xxx, this.field_70161_v - (double)☃xx, 0.0, 0.0, 0.0
            );
      }

      if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
         this.func_70106_y();
      }
   }

   @Override
   public void func_70636_d() {
      if (this.func_204609_dp()) {
         this.func_70015_d(8);
      }

      super.func_70636_d();
   }

   @Override
   protected void func_70619_bc() {
      super.func_70619_bc();
   }

   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      this.field_203037_c = new BlockPos(this).func_177981_b(5);
      this.func_203034_a(0);
      return super.func_204210_a(☃, ☃, ☃);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_74764_b("AX")) {
         this.field_203037_c = new BlockPos(☃.func_74762_e("AX"), ☃.func_74762_e("AY"), ☃.func_74762_e("AZ"));
      }

      this.func_203034_a(☃.func_74762_e("Size"));
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("AX", this.field_203037_c.func_177958_n());
      ☃.func_74768_a("AY", this.field_203037_c.func_177956_o());
      ☃.func_74768_a("AZ", this.field_203037_c.func_177952_p());
      ☃.func_74768_a("Size", this.func_203032_dq());
   }

   @Override
   public boolean func_70112_a(double var1) {
      return true;
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_203256_ft;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_203259_fw;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_203258_fv;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_203250_E;
   }

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.UNDEAD;
   }

   @Override
   protected float func_70599_aP() {
      return 1.0F;
   }

   @Override
   public boolean func_70686_a(Class<? extends EntityLivingBase> var1) {
      return true;
   }

   class AIAttackPlayer extends EntityAIBase {
      private int field_203142_b = 20;

      private AIAttackPlayer() {
      }

      @Override
      public boolean func_75250_a() {
         if (this.field_203142_b > 0) {
            --this.field_203142_b;
            return false;
         } else {
            this.field_203142_b = 60;
            AxisAlignedBB ☃ = EntityPhantom.this.func_174813_aQ().func_72314_b(16.0, 64.0, 16.0);
            List<EntityPlayer> ☃x = EntityPhantom.this.field_70170_p.func_72872_a(EntityPlayer.class, ☃);
            if (!☃x.isEmpty()) {
               ☃x.sort((var0, var1x) -> var0.field_70163_u > var1x.field_70163_u ? -1 : 1);

               for(EntityPlayer ☃xx : ☃x) {
                  if (EntityAITarget.func_179445_a(EntityPhantom.this, ☃xx, false, false)) {
                     EntityPhantom.this.func_70624_b(☃xx);
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public boolean func_75253_b() {
         return EntityAITarget.func_179445_a(EntityPhantom.this, EntityPhantom.this.func_70638_az(), false, false);
      }
   }

   abstract class AIMove extends EntityAIBase {
      public AIMove() {
         this.func_75248_a(1);
      }

      protected boolean func_203146_f() {
         return EntityPhantom.this.field_203036_b
               .func_186679_c(EntityPhantom.this.field_70165_t, EntityPhantom.this.field_70163_u, EntityPhantom.this.field_70161_v)
            < 4.0;
      }
   }

   class AIOrbitPoint extends EntityPhantom.AIMove {
      private float field_203150_c;
      private float field_203151_d;
      private float field_203152_e;
      private float field_203153_f;

      private AIOrbitPoint() {
      }

      @Override
      public boolean func_75250_a() {
         return EntityPhantom.this.func_70638_az() == null || EntityPhantom.this.field_203038_bx == EntityPhantom.AttackPhase.CIRCLE;
      }

      @Override
      public void func_75249_e() {
         this.field_203151_d = 5.0F + EntityPhantom.this.field_70146_Z.nextFloat() * 10.0F;
         this.field_203152_e = -4.0F + EntityPhantom.this.field_70146_Z.nextFloat() * 9.0F;
         this.field_203153_f = EntityPhantom.this.field_70146_Z.nextBoolean() ? 1.0F : -1.0F;
         this.func_203148_i();
      }

      @Override
      public void func_75246_d() {
         if (EntityPhantom.this.field_70146_Z.nextInt(350) == 0) {
            this.field_203152_e = -4.0F + EntityPhantom.this.field_70146_Z.nextFloat() * 9.0F;
         }

         if (EntityPhantom.this.field_70146_Z.nextInt(250) == 0) {
            ++this.field_203151_d;
            if (this.field_203151_d > 15.0F) {
               this.field_203151_d = 5.0F;
               this.field_203153_f = -this.field_203153_f;
            }
         }

         if (EntityPhantom.this.field_70146_Z.nextInt(450) == 0) {
            this.field_203150_c = EntityPhantom.this.field_70146_Z.nextFloat() * 2.0F * (float) Math.PI;
            this.func_203148_i();
         }

         if (this.func_203146_f()) {
            this.func_203148_i();
         }

         if (EntityPhantom.this.field_203036_b.field_72448_b < EntityPhantom.this.field_70163_u
            && !EntityPhantom.this.field_70170_p.func_175623_d(new BlockPos(EntityPhantom.this).func_177979_c(1))) {
            this.field_203152_e = Math.max(1.0F, this.field_203152_e);
            this.func_203148_i();
         }

         if (EntityPhantom.this.field_203036_b.field_72448_b > EntityPhantom.this.field_70163_u
            && !EntityPhantom.this.field_70170_p.func_175623_d(new BlockPos(EntityPhantom.this).func_177981_b(1))) {
            this.field_203152_e = Math.min(-1.0F, this.field_203152_e);
            this.func_203148_i();
         }
      }

      private void func_203148_i() {
         if (BlockPos.field_177992_a.equals(EntityPhantom.this.field_203037_c)) {
            EntityPhantom.this.field_203037_c = new BlockPos(EntityPhantom.this);
         }

         this.field_203150_c += this.field_203153_f * 15.0F * (float) (Math.PI / 180.0);
         EntityPhantom.this.field_203036_b = new Vec3d(EntityPhantom.this.field_203037_c)
            .func_72441_c(
               (double)(this.field_203151_d * MathHelper.func_76134_b(this.field_203150_c)),
               (double)(-4.0F + this.field_203152_e),
               (double)(this.field_203151_d * MathHelper.func_76126_a(this.field_203150_c))
            );
      }
   }

   class AIPickAttack extends EntityAIBase {
      private int field_203145_b;

      private AIPickAttack() {
      }

      @Override
      public boolean func_75250_a() {
         return EntityAITarget.func_179445_a(EntityPhantom.this, EntityPhantom.this.func_70638_az(), false, false);
      }

      @Override
      public void func_75249_e() {
         this.field_203145_b = 10;
         EntityPhantom.this.field_203038_bx = EntityPhantom.AttackPhase.CIRCLE;
         this.func_203143_f();
      }

      @Override
      public void func_75251_c() {
         EntityPhantom.this.field_203037_c = EntityPhantom.this.field_70170_p
            .func_205770_a(Heightmap.Type.MOTION_BLOCKING, EntityPhantom.this.field_203037_c)
            .func_177981_b(10 + EntityPhantom.this.field_70146_Z.nextInt(20));
      }

      @Override
      public void func_75246_d() {
         if (EntityPhantom.this.field_203038_bx == EntityPhantom.AttackPhase.CIRCLE) {
            --this.field_203145_b;
            if (this.field_203145_b <= 0) {
               EntityPhantom.this.field_203038_bx = EntityPhantom.AttackPhase.SWOOP;
               this.func_203143_f();
               this.field_203145_b = (8 + EntityPhantom.this.field_70146_Z.nextInt(4)) * 20;
               EntityPhantom.this.func_184185_a(SoundEvents.field_203260_fx, 10.0F, 0.95F + EntityPhantom.this.field_70146_Z.nextFloat() * 0.1F);
            }
         }
      }

      private void func_203143_f() {
         EntityPhantom.this.field_203037_c = new BlockPos(EntityPhantom.this.func_70638_az()).func_177981_b(20 + EntityPhantom.this.field_70146_Z.nextInt(20));
         if (EntityPhantom.this.field_203037_c.func_177956_o() < EntityPhantom.this.field_70170_p.func_181545_F()) {
            EntityPhantom.this.field_203037_c = new BlockPos(
               EntityPhantom.this.field_203037_c.func_177958_n(),
               EntityPhantom.this.field_70170_p.func_181545_F() + 1,
               EntityPhantom.this.field_203037_c.func_177952_p()
            );
         }
      }
   }

   class AISweepAttack extends EntityPhantom.AIMove {
      private AISweepAttack() {
      }

      @Override
      public boolean func_75250_a() {
         return EntityPhantom.this.func_70638_az() != null && EntityPhantom.this.field_203038_bx == EntityPhantom.AttackPhase.SWOOP;
      }

      @Override
      public boolean func_75253_b() {
         EntityLivingBase ☃ = EntityPhantom.this.func_70638_az();
         if (☃ == null) {
            return false;
         } else if (!☃.func_70089_S()) {
            return false;
         } else {
            return !(☃ instanceof EntityPlayer) || !((EntityPlayer)☃).func_175149_v() && !((EntityPlayer)☃).func_184812_l_() ? this.func_75250_a() : false;
         }
      }

      @Override
      public void func_75249_e() {
      }

      @Override
      public void func_75251_c() {
         EntityPhantom.this.func_70624_b(null);
         EntityPhantom.this.field_203038_bx = EntityPhantom.AttackPhase.CIRCLE;
      }

      @Override
      public void func_75246_d() {
         EntityLivingBase ☃ = EntityPhantom.this.func_70638_az();
         EntityPhantom.this.field_203036_b = new Vec3d(☃.field_70165_t, ☃.field_70163_u + (double)☃.field_70131_O * 0.5, ☃.field_70161_v);
         if (EntityPhantom.this.func_174813_aQ().func_186662_g(0.2F).func_72326_a(☃.func_174813_aQ())) {
            EntityPhantom.this.func_70652_k(☃);
            EntityPhantom.this.field_203038_bx = EntityPhantom.AttackPhase.CIRCLE;
            EntityPhantom.this.field_70170_p.func_175718_b(1039, new BlockPos(EntityPhantom.this), 0);
         } else if (EntityPhantom.this.field_70123_F || EntityPhantom.this.field_70737_aN > 0) {
            EntityPhantom.this.field_203038_bx = EntityPhantom.AttackPhase.CIRCLE;
         }
      }
   }

   static enum AttackPhase {
      CIRCLE,
      SWOOP;
   }

   class BodyHelper extends EntityBodyHelper {
      public BodyHelper(EntityLivingBase var2) {
         super(☃);
      }

      @Override
      public void func_75664_a() {
         EntityPhantom.this.field_70759_as = EntityPhantom.this.field_70761_aq;
         EntityPhantom.this.field_70761_aq = EntityPhantom.this.field_70177_z;
      }
   }

   class LookHelper extends EntityLookHelper {
      public LookHelper(EntityLiving var2) {
         super(☃);
      }

      @Override
      public void func_75649_a() {
      }
   }

   class MoveHelper extends EntityMoveHelper {
      private float field_203105_j = 0.1F;

      public MoveHelper(EntityLiving var2) {
         super(☃);
      }

      @Override
      public void func_75641_c() {
         if (EntityPhantom.this.field_70123_F) {
            EntityPhantom.this.field_70177_z += 180.0F;
            this.field_203105_j = 0.1F;
         }

         float ☃ = (float)(EntityPhantom.this.field_203036_b.field_72450_a - EntityPhantom.this.field_70165_t);
         float ☃x = (float)(EntityPhantom.this.field_203036_b.field_72448_b - EntityPhantom.this.field_70163_u);
         float ☃xx = (float)(EntityPhantom.this.field_203036_b.field_72449_c - EntityPhantom.this.field_70161_v);
         double ☃xxx = (double)MathHelper.func_76129_c(☃ * ☃ + ☃xx * ☃xx);
         double ☃xxxx = 1.0 - (double)MathHelper.func_76135_e(☃x * 0.7F) / ☃xxx;
         ☃ = (float)((double)☃ * ☃xxxx);
         ☃xx = (float)((double)☃xx * ☃xxxx);
         ☃xxx = (double)MathHelper.func_76129_c(☃ * ☃ + ☃xx * ☃xx);
         double ☃xxxxx = (double)MathHelper.func_76129_c(☃ * ☃ + ☃xx * ☃xx + ☃x * ☃x);
         float ☃xxxxxx = EntityPhantom.this.field_70177_z;
         float ☃xxxxxxx = (float)MathHelper.func_181159_b((double)☃xx, (double)☃);
         float ☃xxxxxxxx = MathHelper.func_76142_g(EntityPhantom.this.field_70177_z + 90.0F);
         float ☃xxxxxxxxx = MathHelper.func_76142_g(☃xxxxxxx * (180.0F / (float)Math.PI));
         EntityPhantom.this.field_70177_z = MathHelper.func_203303_c(☃xxxxxxxx, ☃xxxxxxxxx, 4.0F) - 90.0F;
         EntityPhantom.this.field_70761_aq = EntityPhantom.this.field_70177_z;
         if (MathHelper.func_203301_d(☃xxxxxx, EntityPhantom.this.field_70177_z) < 3.0F) {
            this.field_203105_j = MathHelper.func_203300_b(this.field_203105_j, 1.8F, 0.005F * (1.8F / this.field_203105_j));
         } else {
            this.field_203105_j = MathHelper.func_203300_b(this.field_203105_j, 0.2F, 0.025F);
         }

         float ☃ = (float)(-(MathHelper.func_181159_b((double)(-☃x), ☃xxx) * 180.0F / (float)Math.PI));
         EntityPhantom.this.field_70125_A = ☃;
         float ☃x = EntityPhantom.this.field_70177_z + 90.0F;
         double ☃xx = (double)(this.field_203105_j * MathHelper.func_76134_b(☃x * (float) (Math.PI / 180.0))) * Math.abs((double)☃ / ☃xxxxx);
         double ☃xxx = (double)(this.field_203105_j * MathHelper.func_76126_a(☃x * (float) (Math.PI / 180.0))) * Math.abs((double)☃xx / ☃xxxxx);
         double ☃xxxx = (double)(this.field_203105_j * MathHelper.func_76126_a(☃ * (float) (Math.PI / 180.0))) * Math.abs((double)☃x / ☃xxxxx);
         EntityPhantom.this.field_70159_w += (☃xx - EntityPhantom.this.field_70159_w) * 0.2;
         EntityPhantom.this.field_70181_x += (☃xxxx - EntityPhantom.this.field_70181_x) * 0.2;
         EntityPhantom.this.field_70179_y += (☃xxx - EntityPhantom.this.field_70179_y) * 0.2;
      }
   }
}
