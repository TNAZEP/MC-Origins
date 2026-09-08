package net.minecraft.entity.passive;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAIFollowOwnerFlying;
import net.minecraft.entity.ai.EntityAILandOnOwnersShoulder;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityParrot extends EntityShoulderRiding implements IFlyingAnimal {
   private static final DataParameter<Integer> field_192013_bG = EntityDataManager.func_187226_a(EntityParrot.class, DataSerializers.field_187192_b);
   private static final Predicate<EntityLiving> field_192014_bH = new Predicate<EntityLiving>() {
      public boolean test(@Nullable EntityLiving var1) {
         return ☃ != null && EntityParrot.field_192017_bK.containsKey(☃.func_200600_R());
      }
   };
   private static final Item field_192015_bI = Items.field_151106_aX;
   private static final Set<Item> field_192016_bJ = Sets.<Item>newHashSet(
      Items.field_151014_N, Items.field_151081_bc, Items.field_151080_bb, Items.field_185163_cU
   );
   private static final Map<EntityType<?>, SoundEvent> field_192017_bK = Util.func_200696_a(Maps.<EntityType<?>, SoundEvent>newHashMap(), var0 -> {
      var0.put(EntityType.field_200792_f, SoundEvents.field_193791_eM);
      var0.put(EntityType.field_200794_h, SoundEvents.field_193813_fc);
      var0.put(EntityType.field_200797_k, SoundEvents.field_193792_eN);
      var0.put(EntityType.field_204724_o, SoundEvents.field_206942_fI);
      var0.put(EntityType.field_200800_n, SoundEvents.field_193793_eO);
      var0.put(EntityType.field_200802_p, SoundEvents.field_193794_eP);
      var0.put(EntityType.field_200803_q, SoundEvents.field_193795_eQ);
      var0.put(EntityType.field_200804_r, SoundEvents.field_193796_eR);
      var0.put(EntityType.field_200806_t, SoundEvents.field_193797_eS);
      var0.put(EntityType.field_200811_y, SoundEvents.field_193798_eT);
      var0.put(EntityType.field_200763_C, SoundEvents.field_193799_eU);
      var0.put(EntityType.field_200764_D, SoundEvents.field_193800_eV);
      var0.put(EntityType.field_200771_K, SoundEvents.field_193801_eW);
      var0.put(EntityType.field_200785_Y, SoundEvents.field_193822_fl);
      var0.put(EntityType.field_203097_aH, SoundEvents.field_206943_fS);
      var0.put(EntityType.field_200786_Z, SoundEvents.field_193802_eX);
      var0.put(EntityType.field_200738_ad, SoundEvents.field_193803_eY);
      var0.put(EntityType.field_200740_af, SoundEvents.field_193804_eZ);
      var0.put(EntityType.field_200741_ag, SoundEvents.field_193811_fa);
      var0.put(EntityType.field_200743_ai, SoundEvents.field_193812_fb);
      var0.put(EntityType.field_200748_an, SoundEvents.field_193813_fc);
      var0.put(EntityType.field_200750_ap, SoundEvents.field_193814_fd);
      var0.put(EntityType.field_200755_au, SoundEvents.field_193815_fe);
      var0.put(EntityType.field_200758_ax, SoundEvents.field_193816_ff);
      var0.put(EntityType.field_200759_ay, SoundEvents.field_193817_fg);
      var0.put(EntityType.field_200760_az, SoundEvents.field_193818_fh);
      var0.put(EntityType.field_200722_aA, SoundEvents.field_193819_fi);
      var0.put(EntityType.field_200724_aC, SoundEvents.field_193820_fj);
      var0.put(EntityType.field_200725_aD, SoundEvents.field_193821_fk);
      var0.put(EntityType.field_200727_aF, SoundEvents.field_193823_fm);
   });
   public float field_192008_bB;
   public float field_192009_bC;
   public float field_192010_bD;
   public float field_192011_bE;
   public float field_192012_bF = 1.0F;
   private boolean field_192018_bL;
   private BlockPos field_192019_bM;

   public EntityParrot(World var1) {
      super(EntityType.field_200783_W, ☃);
      this.func_70105_a(0.5F, 0.9F);
      this.field_70765_h = new EntityFlyHelper(this);
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      this.func_191997_m(this.field_70146_Z.nextInt(5));
      return super.func_204210_a(☃, ☃, ☃);
   }

   @Override
   protected void func_184651_r() {
      this.field_70911_d = new EntityAISit(this);
      this.field_70714_bg.func_75776_a(0, new EntityAIPanic(this, 1.25));
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(2, this.field_70911_d);
      this.field_70714_bg.func_75776_a(2, new EntityAIFollowOwnerFlying(this, 1.0, 5.0F, 1.0F));
      this.field_70714_bg.func_75776_a(2, new EntityAIWanderAvoidWaterFlying(this, 1.0));
      this.field_70714_bg.func_75776_a(3, new EntityAILandOnOwnersShoulder(this));
      this.field_70714_bg.func_75776_a(3, new EntityAIFollow(this, 1.0, 3.0F, 7.0F));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_193334_e);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(6.0);
      this.func_110148_a(SharedMonsterAttributes.field_193334_e).func_111128_a(0.4F);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2F);
   }

   @Override
   protected PathNavigate func_175447_b(World var1) {
      PathNavigateFlying ☃ = new PathNavigateFlying(this, ☃);
      ☃.func_192879_a(false);
      ☃.func_212239_d(true);
      ☃.func_192878_b(true);
      return ☃;
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O * 0.6F;
   }

   @Override
   public void func_70636_d() {
      func_192006_b(this.field_70170_p, this);
      if (this.field_192019_bM == null
         || this.field_192019_bM.func_177954_c(this.field_70165_t, this.field_70163_u, this.field_70161_v) > 12.0
         || this.field_70170_p.func_180495_p(this.field_192019_bM).func_177230_c() != Blocks.field_150421_aI) {
         this.field_192018_bL = false;
         this.field_192019_bM = null;
      }

      super.func_70636_d();
      this.func_192001_dv();
   }

   @Override
   public void func_191987_a(BlockPos var1, boolean var2) {
      this.field_192019_bM = ☃;
      this.field_192018_bL = ☃;
   }

   public boolean func_192004_dr() {
      return this.field_192018_bL;
   }

   private void func_192001_dv() {
      this.field_192011_bE = this.field_192008_bB;
      this.field_192010_bD = this.field_192009_bC;
      this.field_192009_bC = (float)((double)this.field_192009_bC + (double)(this.field_70122_E ? -1 : 4) * 0.3);
      this.field_192009_bC = MathHelper.func_76131_a(this.field_192009_bC, 0.0F, 1.0F);
      if (!this.field_70122_E && this.field_192012_bF < 1.0F) {
         this.field_192012_bF = 1.0F;
      }

      this.field_192012_bF = (float)((double)this.field_192012_bF * 0.9);
      if (!this.field_70122_E && this.field_70181_x < 0.0) {
         this.field_70181_x *= 0.6;
      }

      this.field_192008_bB += this.field_192012_bF * 2.0F;
   }

   private static boolean func_192006_b(World var0, Entity var1) {
      if (!☃.func_174814_R() && ☃.field_73012_v.nextInt(50) == 0) {
         List<EntityLiving> ☃ = ☃.func_175647_a(EntityLiving.class, ☃.func_174813_aQ().func_186662_g(20.0), field_192014_bH);
         if (!☃.isEmpty()) {
            EntityLiving ☃x = (EntityLiving)☃.get(☃.field_73012_v.nextInt(☃.size()));
            if (!☃x.func_174814_R()) {
               SoundEvent ☃xx = func_200610_a(☃x.func_200600_R());
               ☃.func_184148_a(null, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃xx, ☃.func_184176_by(), 0.7F, func_192000_b(☃.field_73012_v));
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (!this.func_70909_n() && field_192016_bJ.contains(☃.func_77973_b())) {
         if (!☃.field_71075_bZ.field_75098_d) {
            ☃.func_190918_g(1);
         }

         if (!this.func_174814_R()) {
            this.field_70170_p
               .func_184148_a(
                  null,
                  this.field_70165_t,
                  this.field_70163_u,
                  this.field_70161_v,
                  SoundEvents.field_192797_eu,
                  this.func_184176_by(),
                  1.0F,
                  1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F
               );
         }

         if (!this.field_70170_p.field_72995_K) {
            if (this.field_70146_Z.nextInt(10) == 0) {
               this.func_193101_c(☃);
               this.func_70908_e(true);
               this.field_70170_p.func_72960_a(this, (byte)7);
            } else {
               this.func_70908_e(false);
               this.field_70170_p.func_72960_a(this, (byte)6);
            }
         }

         return true;
      } else if (☃.func_77973_b() == field_192015_bI) {
         if (!☃.field_71075_bZ.field_75098_d) {
            ☃.func_190918_g(1);
         }

         this.func_195064_c(new PotionEffect(MobEffects.field_76436_u, 900));
         if (☃.func_184812_l_() || !this.func_190530_aW()) {
            this.func_70097_a(DamageSource.func_76365_a(☃), Float.MAX_VALUE);
         }

         return true;
      } else {
         if (!this.field_70170_p.field_72995_K && !this.func_192002_a() && this.func_70909_n() && this.func_152114_e(☃)) {
            this.field_70911_d.func_75270_a(!this.func_70906_o());
         }

         return super.func_184645_a(☃, ☃);
      }
   }

   @Override
   public boolean func_70877_b(ItemStack var1) {
      return false;
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      int ☃ = MathHelper.func_76128_c(this.field_70165_t);
      int ☃x = MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);
      int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
      Block ☃xxxx = ☃.func_180495_p(☃xxx.func_177977_b()).func_177230_c();
      return ☃xxxx instanceof BlockLeaves
         || ☃xxxx == Blocks.field_150349_c
         || ☃xxxx instanceof BlockLog
         || ☃xxxx == Blocks.field_150350_a && super.func_205020_a(☃, ☃);
   }

   @Override
   public void func_180430_e(float var1, float var2) {
   }

   @Override
   protected void func_184231_a(double var1, boolean var3, IBlockState var4, BlockPos var5) {
   }

   @Override
   public boolean func_70878_b(EntityAnimal var1) {
      return false;
   }

   @Nullable
   @Override
   public EntityAgeable func_90011_a(EntityAgeable var1) {
      return null;
   }

   public static void func_192005_a(World var0, Entity var1) {
      if (!☃.func_174814_R() && !func_192006_b(☃, ☃) && ☃.field_73012_v.nextInt(200) == 0) {
         ☃.func_184148_a(
            null, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, func_192003_a(☃.field_73012_v), ☃.func_184176_by(), 1.0F, func_192000_b(☃.field_73012_v)
         );
      }
   }

   @Override
   public boolean func_70652_k(Entity var1) {
      return ☃.func_70097_a(DamageSource.func_76358_a(this), 3.0F);
   }

   @Nullable
   @Override
   public SoundEvent func_184639_G() {
      return func_192003_a(this.field_70146_Z);
   }

   private static SoundEvent func_192003_a(Random var0) {
      if (☃.nextInt(1000) == 0) {
         List<EntityType<?>> ☃ = Lists.<EntityType<?>>newArrayList(field_192017_bK.keySet());
         return func_200610_a((EntityType<?>)☃.get(☃.nextInt(☃.size())));
      } else {
         return SoundEvents.field_192792_ep;
      }
   }

   public static SoundEvent func_200610_a(EntityType<?> var0) {
      return (SoundEvent)field_192017_bK.getOrDefault(☃, SoundEvents.field_192792_ep);
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_192794_er;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_192793_eq;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_192795_es, 0.15F, 1.0F);
   }

   @Override
   protected float func_191954_d(float var1) {
      this.func_184185_a(SoundEvents.field_192796_et, 0.15F, 1.0F);
      return ☃ + this.field_192009_bC / 2.0F;
   }

   @Override
   protected boolean func_191957_ae() {
      return true;
   }

   @Override
   protected float func_70647_i() {
      return func_192000_b(this.field_70146_Z);
   }

   private static float func_192000_b(Random var0) {
      return (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F;
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.NEUTRAL;
   }

   @Override
   public boolean func_70104_M() {
      return true;
   }

   @Override
   protected void func_82167_n(Entity var1) {
      if (!(☃ instanceof EntityPlayer)) {
         super.func_82167_n(☃);
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         if (this.field_70911_d != null) {
            this.field_70911_d.func_75270_a(false);
         }

         return super.func_70097_a(☃, ☃);
      }
   }

   public int func_191998_ds() {
      return MathHelper.func_76125_a(this.field_70180_af.func_187225_a(field_192013_bG), 0, 4);
   }

   public void func_191997_m(int var1) {
      this.field_70180_af.func_187227_b(field_192013_bG, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_192013_bG, 0);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Variant", this.func_191998_ds());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_191997_m(☃.func_74762_e("Variant"));
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_192561_ax;
   }

   public boolean func_192002_a() {
      return !this.field_70122_E;
   }
}
