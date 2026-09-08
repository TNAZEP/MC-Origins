package net.minecraft.entity.passive;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPufferFish extends AbstractFish {
   private static final DataParameter<Integer> field_203716_b = EntityDataManager.func_187226_a(EntityPufferFish.class, DataSerializers.field_187192_b);
   private int field_203717_c;
   private int field_203718_bx;
   private static final Predicate<EntityLivingBase> field_205724_bA = var0 -> {
      if (var0 == null) {
         return false;
      } else if (!(var0 instanceof EntityPlayer) || !((EntityPlayer)var0).func_175149_v() && !((EntityPlayer)var0).func_184812_l_()) {
         return var0.func_70668_bt() != CreatureAttribute.field_203100_e;
      } else {
         return false;
      }
   };
   private float field_205722_bB = -1.0F;
   private float field_205723_bC;

   public EntityPufferFish(World var1) {
      super(EntityType.field_203779_Z, ☃);
      this.func_70105_a(0.7F, 0.7F);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_203716_b, 0);
   }

   public int func_203715_dA() {
      return this.field_70180_af.func_187225_a(field_203716_b);
   }

   public void func_203714_a(int var1) {
      this.field_70180_af.func_187227_b(field_203716_b, ☃);
      this.func_205718_b(☃);
   }

   private void func_205718_b(int var1) {
      float ☃ = 1.0F;
      if (☃ == 1) {
         ☃ = 0.7F;
      } else if (☃ == 0) {
         ☃ = 0.5F;
      }

      this.func_205717_a(☃);
   }

   @Override
   protected final void func_70105_a(float var1, float var2) {
      boolean ☃ = this.field_205722_bB > 0.0F;
      this.field_205722_bB = ☃;
      this.field_205723_bC = ☃;
      if (!☃) {
         this.func_205717_a(1.0F);
      }
   }

   private void func_205717_a(float var1) {
      super.func_70105_a(this.field_205722_bB * ☃, this.field_205723_bC * ☃);
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      this.func_205718_b(this.func_203715_dA());
      super.func_184206_a(☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("PuffState", this.func_203715_dA());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_203714_a(☃.func_74762_e("PuffState"));
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_203812_az;
   }

   @Override
   protected ItemStack func_203707_dx() {
      return new ItemStack(Items.field_203795_aL);
   }

   @Override
   protected void func_184651_r() {
      super.func_184651_r();
      this.field_70714_bg.func_75776_a(1, new EntityPufferFish.AIPuff(this));
   }

   @Override
   public void func_70071_h_() {
      if (this.func_70089_S() && !this.field_70170_p.field_72995_K) {
         if (this.field_203717_c > 0) {
            if (this.func_203715_dA() == 0) {
               this.func_184185_a(SoundEvents.field_203826_go, this.func_70599_aP(), this.func_70647_i());
               this.func_203714_a(1);
            } else if (this.field_203717_c > 40 && this.func_203715_dA() == 1) {
               this.func_184185_a(SoundEvents.field_203826_go, this.func_70599_aP(), this.func_70647_i());
               this.func_203714_a(2);
            }

            ++this.field_203717_c;
         } else if (this.func_203715_dA() != 0) {
            if (this.field_203718_bx > 60 && this.func_203715_dA() == 2) {
               this.func_184185_a(SoundEvents.field_203825_gn, this.func_70599_aP(), this.func_70647_i());
               this.func_203714_a(1);
            } else if (this.field_203718_bx > 100 && this.func_203715_dA() == 1) {
               this.func_184185_a(SoundEvents.field_203825_gn, this.func_70599_aP(), this.func_70647_i());
               this.func_203714_a(0);
            }

            ++this.field_203718_bx;
         }
      }

      super.func_70071_h_();
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      if (this.func_203715_dA() > 0) {
         for(EntityLiving ☃ : this.field_70170_p.func_175647_a(EntityLiving.class, this.func_174813_aQ().func_186662_g(0.3), field_205724_bA)) {
            if (☃.func_70089_S()) {
               this.func_205719_a(☃);
            }
         }
      }
   }

   private void func_205719_a(EntityLiving var1) {
      int ☃ = this.func_203715_dA();
      if (☃.func_70097_a(DamageSource.func_76358_a(this), (float)(1 + ☃))) {
         ☃.func_195064_c(new PotionEffect(MobEffects.field_76436_u, 60 * ☃, 0));
         this.func_184185_a(SoundEvents.field_203830_gs, 1.0F, 1.0F);
      }
   }

   @Override
   public void func_70100_b_(EntityPlayer var1) {
      int ☃ = this.func_203715_dA();
      if (☃ instanceof EntityPlayerMP && ☃ > 0 && ☃.func_70097_a(DamageSource.func_76358_a(this), (float)(1 + ☃))) {
         ((EntityPlayerMP)☃).field_71135_a.func_147359_a(new SPacketChangeGameState(9, 0.0F));
         ☃.func_195064_c(new PotionEffect(MobEffects.field_76436_u, 60 * ☃, 0));
      }
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_203824_gm;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_203827_gp;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_203829_gr;
   }

   @Override
   protected SoundEvent func_203701_dz() {
      return SoundEvents.field_203828_gq;
   }

   static class AIPuff extends EntityAIBase {
      private final EntityPufferFish field_203789_a;

      public AIPuff(EntityPufferFish var1) {
         this.field_203789_a = ☃;
      }

      @Override
      public boolean func_75250_a() {
         List<EntityLivingBase> ☃ = this.field_203789_a
            .field_70170_p
            .func_175647_a(EntityLivingBase.class, this.field_203789_a.func_174813_aQ().func_186662_g(2.0), EntityPufferFish.field_205724_bA);
         return !☃.isEmpty();
      }

      @Override
      public void func_75249_e() {
         this.field_203789_a.field_203717_c = 1;
         this.field_203789_a.field_203718_bx = 0;
      }

      @Override
      public void func_75251_c() {
         this.field_203789_a.field_203717_c = 0;
      }

      @Override
      public boolean func_75253_b() {
         List<EntityLivingBase> ☃ = this.field_203789_a
            .field_70170_p
            .func_175647_a(EntityLivingBase.class, this.field_203789_a.func_174813_aQ().func_186662_g(2.0), EntityPufferFish.field_205724_bA);
         return !☃.isEmpty();
      }
   }
}
