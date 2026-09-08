package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityOcelot extends EntityTameable {
   private static final Ingredient field_195402_bB = Ingredient.func_199804_a(
      Items.field_196086_aW, Items.field_196087_aX, Items.field_196088_aY, Items.field_196089_aZ
   );
   private static final DataParameter<Integer> field_184757_bz = EntityDataManager.func_187226_a(EntityOcelot.class, DataSerializers.field_187192_b);
   private static final ResourceLocation field_200608_bC = new ResourceLocation("cat");
   private EntityAIAvoidEntity<EntityPlayer> field_175545_bm;
   private EntityAITempt field_70914_e;

   public EntityOcelot(World var1) {
      super(EntityType.field_200781_U, ☃);
      this.func_70105_a(0.6F, 0.7F);
   }

   @Override
   protected void func_184651_r() {
      this.field_70911_d = new EntityAISit(this);
      this.field_70914_e = new EntityAITempt(this, 0.6, field_195402_bB, true);
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, this.field_70911_d);
      this.field_70714_bg.func_75776_a(3, this.field_70914_e);
      this.field_70714_bg.func_75776_a(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 5.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAIOcelotSit(this, 0.8));
      this.field_70714_bg.func_75776_a(7, new EntityAILeapAtTarget(this, 0.3F));
      this.field_70714_bg.func_75776_a(8, new EntityAIOcelotAttack(this));
      this.field_70714_bg.func_75776_a(9, new EntityAIMate(this, 0.8));
      this.field_70714_bg.func_75776_a(10, new EntityAIWanderAvoidWater(this, 0.8, 1.0000001E-5F));
      this.field_70714_bg.func_75776_a(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.field_70715_bh.func_75776_a(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
      this.field_70715_bh.func_75776_a(1, new EntityAITargetNonTamed(this, EntityTurtle.class, false, EntityTurtle.field_203029_bx));
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184757_bz, 0);
   }

   @Override
   public void func_70619_bc() {
      if (this.func_70605_aq().func_75640_a()) {
         double ☃ = this.func_70605_aq().func_75638_b();
         if (☃ == 0.6) {
            this.func_70095_a(true);
            this.func_70031_b(false);
         } else if (☃ == 1.33) {
            this.func_70095_a(false);
            this.func_70031_b(true);
         } else {
            this.func_70095_a(false);
            this.func_70031_b(false);
         }
      } else {
         this.func_70095_a(false);
         this.func_70031_b(false);
      }
   }

   @Override
   public boolean func_70692_ba() {
      return !this.func_70909_n() && this.field_70173_aa > 2400;
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3F);
   }

   @Override
   public void func_180430_e(float var1, float var2) {
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("CatType", this.func_70913_u());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_70912_b(☃.func_74762_e("CatType"));
   }

   @Nullable
   @Override
   protected SoundEvent func_184639_G() {
      if (this.func_70909_n()) {
         if (this.func_70880_s()) {
            return SoundEvents.field_187645_R;
         } else {
            return this.field_70146_Z.nextInt(4) == 0 ? SoundEvents.field_187648_S : SoundEvents.field_187636_O;
         }
      } else {
         return null;
      }
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187642_Q;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187639_P;
   }

   @Override
   protected float func_70599_aP() {
      return 0.4F;
   }

   @Override
   public boolean func_70652_k(Entity var1) {
      return ☃.func_70097_a(DamageSource.func_76358_a(this), 3.0F);
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

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186402_J;
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (this.func_70909_n()) {
         if (this.func_152114_e(☃) && !this.field_70170_p.field_72995_K && !this.func_70877_b(☃)) {
            this.field_70911_d.func_75270_a(!this.func_70906_o());
         }
      } else if ((this.field_70914_e == null || this.field_70914_e.func_75277_f()) && field_195402_bB.test(☃) && ☃.func_70068_e(this) < 9.0) {
         if (!☃.field_71075_bZ.field_75098_d) {
            ☃.func_190918_g(1);
         }

         if (!this.field_70170_p.field_72995_K) {
            if (this.field_70146_Z.nextInt(3) == 0) {
               this.func_193101_c(☃);
               this.func_70912_b(1 + this.field_70170_p.field_73012_v.nextInt(3));
               this.func_70908_e(true);
               this.field_70911_d.func_75270_a(true);
               this.field_70170_p.func_72960_a(this, (byte)7);
            } else {
               this.func_70908_e(false);
               this.field_70170_p.func_72960_a(this, (byte)6);
            }
         }

         return true;
      }

      return super.func_184645_a(☃, ☃);
   }

   public EntityOcelot func_90011_a(EntityAgeable var1) {
      EntityOcelot ☃ = new EntityOcelot(this.field_70170_p);
      if (this.func_70909_n()) {
         ☃.func_184754_b(this.func_184753_b());
         ☃.func_70903_f(true);
         ☃.func_70912_b(this.func_70913_u());
      }

      return ☃;
   }

   @Override
   public boolean func_70877_b(ItemStack var1) {
      return field_195402_bB.test(☃);
   }

   @Override
   public boolean func_70878_b(EntityAnimal var1) {
      if (☃ == this) {
         return false;
      } else if (!this.func_70909_n()) {
         return false;
      } else if (!(☃ instanceof EntityOcelot)) {
         return false;
      } else {
         EntityOcelot ☃ = (EntityOcelot)☃;
         if (!☃.func_70909_n()) {
            return false;
         } else {
            return this.func_70880_s() && ☃.func_70880_s();
         }
      }
   }

   public int func_70913_u() {
      return this.field_70180_af.func_187225_a(field_184757_bz);
   }

   public void func_70912_b(int var1) {
      this.field_70180_af.func_187227_b(field_184757_bz, ☃);
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return this.field_70146_Z.nextInt(3) != 0;
   }

   @Override
   public boolean func_205019_a(IWorldReaderBase var1) {
      if (☃.func_195587_c(this, this.func_174813_aQ()) && ☃.func_195586_b(this, this.func_174813_aQ()) && !☃.func_72953_d(this.func_174813_aQ())) {
         BlockPos ☃ = new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v);
         if (☃.func_177956_o() < ☃.func_181545_F()) {
            return false;
         }

         IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
         Block ☃x = ☃.func_177230_c();
         if (☃x == Blocks.field_196658_i || ☃.func_203425_a(BlockTags.field_206952_E)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public ITextComponent func_200200_C_() {
      ITextComponent ☃ = this.func_200201_e();
      if (☃ != null) {
         return ☃;
      } else {
         return (ITextComponent)(this.func_70909_n() ? new TextComponentTranslation(Util.func_200697_a("entity", field_200608_bC)) : super.func_200200_C_());
      }
   }

   @Override
   protected void func_175544_ck() {
      if (this.field_175545_bm == null) {
         this.field_175545_bm = new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 0.8, 1.33);
      }

      this.field_70714_bg.func_85156_a(this.field_175545_bm);
      if (!this.func_70909_n()) {
         this.field_70714_bg.func_75776_a(4, this.field_175545_bm);
      }
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      if (this.func_70913_u() == 0 && this.field_70170_p.field_73012_v.nextInt(7) == 0) {
         for(int ☃ = 0; ☃ < 2; ++☃) {
            EntityOcelot ☃x = new EntityOcelot(this.field_70170_p);
            ☃x.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
            ☃x.func_70873_a(-24000);
            this.field_70170_p.func_72838_d(☃x);
         }
      }

      return ☃;
   }
}
