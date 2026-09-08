package net.minecraft.entity.monster;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityCreeper extends EntityMob {
   private static final DataParameter<Integer> field_184713_a = EntityDataManager.func_187226_a(EntityCreeper.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184714_b = EntityDataManager.func_187226_a(EntityCreeper.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_184715_c = EntityDataManager.func_187226_a(EntityCreeper.class, DataSerializers.field_187198_h);
   private int field_70834_e;
   private int field_70833_d;
   private int field_82225_f = 30;
   private int field_82226_g = 3;
   private int field_175494_bm;

   public EntityCreeper(World var1) {
      super(EntityType.field_200797_k, ☃);
      this.func_70105_a(0.6F, 1.7F);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAICreeperSwell(this));
      this.field_70714_bg.func_75776_a(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0, 1.2));
      this.field_70714_bg.func_75776_a(4, new EntityAIAttackMelee(this, 1.0, false));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 0.8));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.field_70715_bh.func_75776_a(2, new EntityAIHurtByTarget(this, false));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25);
   }

   @Override
   public int func_82143_as() {
      return this.func_70638_az() == null ? 3 : 3 + (int)(this.func_110143_aJ() - 1.0F);
   }

   @Override
   public void func_180430_e(float var1, float var2) {
      super.func_180430_e(☃, ☃);
      this.field_70833_d = (int)((float)this.field_70833_d + ☃ * 1.5F);
      if (this.field_70833_d > this.field_82225_f - 5) {
         this.field_70833_d = this.field_82225_f - 5;
      }
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184713_a, -1);
      this.field_70180_af.func_187214_a(field_184714_b, false);
      this.field_70180_af.func_187214_a(field_184715_c, false);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      if (this.field_70180_af.func_187225_a(field_184714_b)) {
         ☃.func_74757_a("powered", true);
      }

      ☃.func_74777_a("Fuse", (short)this.field_82225_f);
      ☃.func_74774_a("ExplosionRadius", (byte)this.field_82226_g);
      ☃.func_74757_a("ignited", this.func_146078_ca());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.field_70180_af.func_187227_b(field_184714_b, ☃.func_74767_n("powered"));
      if (☃.func_150297_b("Fuse", 99)) {
         this.field_82225_f = ☃.func_74765_d("Fuse");
      }

      if (☃.func_150297_b("ExplosionRadius", 99)) {
         this.field_82226_g = ☃.func_74771_c("ExplosionRadius");
      }

      if (☃.func_74767_n("ignited")) {
         this.func_146079_cb();
      }
   }

   @Override
   public void func_70071_h_() {
      if (this.func_70089_S()) {
         this.field_70834_e = this.field_70833_d;
         if (this.func_146078_ca()) {
            this.func_70829_a(1);
         }

         int ☃ = this.func_70832_p();
         if (☃ > 0 && this.field_70833_d == 0) {
            this.func_184185_a(SoundEvents.field_187572_ar, 1.0F, 0.5F);
         }

         this.field_70833_d += ☃;
         if (this.field_70833_d < 0) {
            this.field_70833_d = 0;
         }

         if (this.field_70833_d >= this.field_82225_f) {
            this.field_70833_d = this.field_82225_f;
            this.func_146077_cc();
         }
      }

      super.func_70071_h_();
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187570_aq;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187568_ap;
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      super.func_70645_a(☃);
      if (this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
         if (☃.func_76346_g() instanceof EntitySkeleton) {
            this.func_199703_a(ItemRecord.func_195974_a(this.field_70146_Z));
         } else if (☃.func_76346_g() instanceof EntityCreeper
            && ☃.func_76346_g() != this
            && ((EntityCreeper)☃.func_76346_g()).func_70830_n()
            && ((EntityCreeper)☃.func_76346_g()).func_70650_aV()) {
            ((EntityCreeper)☃.func_76346_g()).func_175493_co();
            this.func_199703_a(Items.field_196185_dy);
         }
      }
   }

   @Override
   public boolean func_70652_k(Entity var1) {
      return true;
   }

   public boolean func_70830_n() {
      return this.field_70180_af.func_187225_a(field_184714_b);
   }

   public float func_70831_j(float var1) {
      return ((float)this.field_70834_e + (float)(this.field_70833_d - this.field_70834_e) * ☃) / (float)(this.field_82225_f - 2);
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186434_p;
   }

   public int func_70832_p() {
      return this.field_70180_af.func_187225_a(field_184713_a);
   }

   public void func_70829_a(int var1) {
      this.field_70180_af.func_187227_b(field_184713_a, ☃);
   }

   @Override
   public void func_70077_a(EntityLightningBolt var1) {
      super.func_70077_a(☃);
      this.field_70180_af.func_187227_b(field_184714_b, true);
   }

   @Override
   protected boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() == Items.field_151033_d) {
         this.field_70170_p
            .func_184148_a(
               ☃,
               this.field_70165_t,
               this.field_70163_u,
               this.field_70161_v,
               SoundEvents.field_187649_bu,
               this.func_184176_by(),
               1.0F,
               this.field_70146_Z.nextFloat() * 0.4F + 0.8F
            );
         ☃.func_184609_a(☃);
         if (!this.field_70170_p.field_72995_K) {
            this.func_146079_cb();
            ☃.func_77972_a(1, ☃);
            return true;
         }
      }

      return super.func_184645_a(☃, ☃);
   }

   private void func_146077_cc() {
      if (!this.field_70170_p.field_72995_K) {
         boolean ☃ = this.field_70170_p.func_82736_K().func_82766_b("mobGriefing");
         float ☃x = this.func_70830_n() ? 2.0F : 1.0F;
         this.field_70729_aU = true;
         this.field_70170_p.func_72876_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)this.field_82226_g * ☃x, ☃);
         this.func_70106_y();
         this.func_190741_do();
      }
   }

   private void func_190741_do() {
      Collection<PotionEffect> ☃ = this.func_70651_bq();
      if (!☃.isEmpty()) {
         EntityAreaEffectCloud ☃x = new EntityAreaEffectCloud(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
         ☃x.func_184483_a(2.5F);
         ☃x.func_184495_b(-0.5F);
         ☃x.func_184485_d(10);
         ☃x.func_184486_b(☃x.func_184489_o() / 2);
         ☃x.func_184487_c(-☃x.func_184490_j() / (float)☃x.func_184489_o());

         for(PotionEffect ☃xx : ☃) {
            ☃x.func_184496_a(new PotionEffect(☃xx));
         }

         this.field_70170_p.func_72838_d(☃x);
      }
   }

   public boolean func_146078_ca() {
      return this.field_70180_af.func_187225_a(field_184715_c);
   }

   public void func_146079_cb() {
      this.field_70180_af.func_187227_b(field_184715_c, true);
   }

   public boolean func_70650_aV() {
      return this.field_175494_bm < 1 && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot");
   }

   public void func_175493_co() {
      ++this.field_175494_bm;
   }
}
