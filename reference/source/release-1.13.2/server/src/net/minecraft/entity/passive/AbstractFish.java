package net.minecraft.entity.passive;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWanderSwim;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class AbstractFish extends EntityWaterMob implements IAnimal {
   private static final DataParameter<Boolean> field_203711_b = EntityDataManager.func_187226_a(AbstractFish.class, DataSerializers.field_187198_h);

   public AbstractFish(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.field_70765_h = new AbstractFish.MoveHelper(this);
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O * 0.65F;
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(3.0);
   }

   @Override
   public boolean func_104002_bU() {
      return this.func_203705_dA() || super.func_104002_bU();
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      BlockPos ☃ = new BlockPos(this);
      return ☃.func_180495_p(☃).func_177230_c() == Blocks.field_150355_j && ☃.func_180495_p(☃.func_177984_a()).func_177230_c() == Blocks.field_150355_j
         ? super.func_205020_a(☃, ☃)
         : false;
   }

   @Override
   public boolean func_70692_ba() {
      return !this.func_203705_dA() && !this.func_145818_k_();
   }

   @Override
   public int func_70641_bl() {
      return 8;
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_203711_b, false);
   }

   private boolean func_203705_dA() {
      return this.field_70180_af.func_187225_a(field_203711_b);
   }

   public void func_203706_r(boolean var1) {
      this.field_70180_af.func_187227_b(field_203711_b, ☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("FromBucket", this.func_203705_dA());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_203706_r(☃.func_74767_n("FromBucket"));
   }

   @Override
   protected void func_184651_r() {
      super.func_184651_r();
      this.field_70714_bg.func_75776_a(0, new EntityAIPanic(this, 1.25));
      this.field_70714_bg.func_75776_a(2, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 1.6, 1.4, EntitySelectors.field_180132_d));
      this.field_70714_bg.func_75776_a(4, new AbstractFish.AISwim(this));
   }

   @Override
   protected PathNavigate func_175447_b(World var1) {
      return new PathNavigateSwimmer(this, ☃);
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      if (this.func_70613_aW() && this.func_70090_H()) {
         this.func_191958_b(☃, ☃, ☃, 0.01F);
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.9F;
         this.field_70181_x *= 0.9F;
         this.field_70179_y *= 0.9F;
         if (this.func_70638_az() == null) {
            this.field_70181_x -= 0.005;
         }
      } else {
         super.func_191986_a(☃, ☃, ☃);
      }
   }

   @Override
   public void func_70636_d() {
      if (!this.func_70090_H() && this.field_70122_E && this.field_70124_G) {
         this.field_70181_x += 0.4F;
         this.field_70159_w += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.05F);
         this.field_70179_y += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.05F);
         this.field_70122_E = false;
         this.field_70160_al = true;
         this.func_184185_a(this.func_203701_dz(), this.func_70599_aP(), this.func_70647_i());
      }

      super.func_70636_d();
   }

   @Override
   protected boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() == Items.field_151131_as && this.func_70089_S()) {
         this.func_184185_a(SoundEvents.field_203814_aa, 1.0F, 1.0F);
         ☃.func_190918_g(1);
         ItemStack ☃x = this.func_203707_dx();
         this.func_204211_f(☃x);
         if (!this.field_70170_p.field_72995_K) {
            CriteriaTriggers.field_204813_j.func_204817_a((EntityPlayerMP)☃, ☃x);
         }

         if (☃.func_190926_b()) {
            ☃.func_184611_a(☃, ☃x);
         } else if (!☃.field_71071_by.func_70441_a(☃x)) {
            ☃.func_71019_a(☃x, false);
         }

         this.func_70106_y();
         return true;
      } else {
         return super.func_184645_a(☃, ☃);
      }
   }

   protected void func_204211_f(ItemStack var1) {
      if (this.func_145818_k_()) {
         ☃.func_200302_a(this.func_200201_e());
      }
   }

   protected abstract ItemStack func_203707_dx();

   protected boolean func_212800_dy() {
      return true;
   }

   protected abstract SoundEvent func_203701_dz();

   @Override
   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_203817_bZ;
   }

   static class AISwim extends EntityAIWanderSwim {
      private final AbstractFish field_203788_h;

      public AISwim(AbstractFish var1) {
         super(☃, 1.0, 40);
         this.field_203788_h = ☃;
      }

      @Override
      public boolean func_75250_a() {
         return this.field_203788_h.func_212800_dy() && super.func_75250_a();
      }
   }

   static class MoveHelper extends EntityMoveHelper {
      private final AbstractFish field_203781_i;

      MoveHelper(AbstractFish var1) {
         super(☃);
         this.field_203781_i = ☃;
      }

      @Override
      public void func_75641_c() {
         if (this.field_203781_i.func_208600_a(FluidTags.field_206959_a)) {
            this.field_203781_i.field_70181_x += 0.005;
         }

         if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && !this.field_203781_i.func_70661_as().func_75500_f()) {
            double ☃ = this.field_75646_b - this.field_203781_i.field_70165_t;
            double ☃x = this.field_75647_c - this.field_203781_i.field_70163_u;
            double ☃xx = this.field_75644_d - this.field_203781_i.field_70161_v;
            double ☃xxx = (double)MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
            ☃x /= ☃xxx;
            float ☃xxxx = (float)(MathHelper.func_181159_b(☃xx, ☃) * 180.0F / (float)Math.PI) - 90.0F;
            this.field_203781_i.field_70177_z = this.func_75639_a(this.field_203781_i.field_70177_z, ☃xxxx, 90.0F);
            this.field_203781_i.field_70761_aq = this.field_203781_i.field_70177_z;
            float ☃xxxxx = (float)(this.field_75645_e * this.field_203781_i.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            this.field_203781_i.func_70659_e(this.field_203781_i.func_70689_ay() + (☃xxxxx - this.field_203781_i.func_70689_ay()) * 0.125F);
            this.field_203781_i.field_70181_x += (double)this.field_203781_i.func_70689_ay() * ☃x * 0.1;
         } else {
            this.field_203781_i.func_70659_e(0.0F);
         }
      }
   }
}
