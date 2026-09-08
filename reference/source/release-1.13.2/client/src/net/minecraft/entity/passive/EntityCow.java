package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityCow extends EntityAnimal {
   protected EntityCow(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_70105_a(0.9F, 1.4F);
   }

   public EntityCow(World var1) {
      this(EntityType.field_200796_j, ☃);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 2.0));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0));
      this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.25, Ingredient.func_199804_a(Items.field_151015_O), false));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.25));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 1.0));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2F);
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187558_ak;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187562_am;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187560_al;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_187566_ao, 0.15F, 1.0F);
   }

   @Override
   protected float func_70599_aP() {
      return 0.4F;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186399_G;
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() == Items.field_151133_ar && !☃.field_71075_bZ.field_75098_d && !this.func_70631_g_()) {
         ☃.func_184185_a(SoundEvents.field_187564_an, 1.0F, 1.0F);
         ☃.func_190918_g(1);
         if (☃.func_190926_b()) {
            ☃.func_184611_a(☃, new ItemStack(Items.field_151117_aB));
         } else if (!☃.field_71071_by.func_70441_a(new ItemStack(Items.field_151117_aB))) {
            ☃.func_71019_a(new ItemStack(Items.field_151117_aB), false);
         }

         return true;
      } else {
         return super.func_184645_a(☃, ☃);
      }
   }

   public EntityCow func_90011_a(EntityAgeable var1) {
      return new EntityCow(this.field_70170_p);
   }

   @Override
   public float func_70047_e() {
      return this.func_70631_g_() ? this.field_70131_O : 1.3F;
   }
}
