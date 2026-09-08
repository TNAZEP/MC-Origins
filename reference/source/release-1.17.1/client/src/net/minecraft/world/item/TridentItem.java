package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TridentItem extends Item implements Vanishable {
   public static final int THROW_THRESHOLD_TIME = 10;
   public static final float BASE_DAMAGE = 8.0F;
   public static final float SHOOT_POWER = 2.5F;
   private final Multimap<Attribute, AttributeModifier> defaultModifiers;

   public TridentItem(Item.Properties var1) {
      super(â˜ƒ);
      Builder<Attribute, AttributeModifier> â˜ƒ = ImmutableMultimap.builder();
      â˜ƒ.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 8.0, AttributeModifier.Operation.ADDITION));
      â˜ƒ.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.9F, AttributeModifier.Operation.ADDITION));
      this.defaultModifiers = â˜ƒ.build();
   }

   @Override
   public boolean canAttackBlock(BlockState var1, Level var2, BlockPos var3, Player var4) {
      return !â˜ƒ.isCreative();
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.SPEAR;
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return 72000;
   }

   @Override
   public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
      if (â˜ƒ instanceof Player) {
         Player â˜ƒ = (Player)â˜ƒ;
         int â˜ƒx = this.getUseDuration(â˜ƒ) - â˜ƒ;
         if (â˜ƒx >= 10) {
            int â˜ƒxx = EnchantmentHelper.getRiptide(â˜ƒ);
            if (â˜ƒxx <= 0 || â˜ƒ.isInWaterOrRain()) {
               if (!â˜ƒ.isClientSide) {
                  â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ.getUsedItemHand()));
                  if (â˜ƒxx == 0) {
                     ThrownTrident â˜ƒxxx = new ThrownTrident(â˜ƒ, â˜ƒ, â˜ƒ);
                     â˜ƒxxx.shootFromRotation(â˜ƒ, â˜ƒ.getXRot(), â˜ƒ.getYRot(), 0.0F, 2.5F + (float)â˜ƒxx * 0.5F, 1.0F);
                     if (â˜ƒ.getAbilities().instabuild) {
                        â˜ƒxxx.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                     }

                     â˜ƒ.addFreshEntity(â˜ƒxxx);
                     â˜ƒ.playSound(null, â˜ƒxxx, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                     if (!â˜ƒ.getAbilities().instabuild) {
                        â˜ƒ.getInventory().removeItem(â˜ƒ);
                     }
                  }
               }

               â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
               if (â˜ƒxx > 0) {
                  float â˜ƒxxx = â˜ƒ.getYRot();
                  float â˜ƒxxxx = â˜ƒ.getXRot();
                  float â˜ƒxxxxx = -Mth.sin(â˜ƒxxx * (float) (Math.PI / 180.0)) * Mth.cos(â˜ƒxxxx * (float) (Math.PI / 180.0));
                  float â˜ƒxxxxxx = -Mth.sin(â˜ƒxxxx * (float) (Math.PI / 180.0));
                  float â˜ƒxxxxxxx = Mth.cos(â˜ƒxxx * (float) (Math.PI / 180.0)) * Mth.cos(â˜ƒxxxx * (float) (Math.PI / 180.0));
                  float â˜ƒxxxxxxxx = Mth.sqrt(â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxx * â˜ƒxxxxxxx);
                  float â˜ƒxxxxxxxxx = 3.0F * ((1.0F + (float)â˜ƒxx) / 4.0F);
                  â˜ƒxxxxx *= â˜ƒxxxxxxxxx / â˜ƒxxxxxxxx;
                  â˜ƒxxxxxx *= â˜ƒxxxxxxxxx / â˜ƒxxxxxxxx;
                  â˜ƒxxxxxxx *= â˜ƒxxxxxxxxx / â˜ƒxxxxxxxx;
                  â˜ƒ.push((double)â˜ƒxxxxx, (double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx);
                  â˜ƒ.startAutoSpinAttack(20);
                  if (â˜ƒ.isOnGround()) {
                     float â˜ƒxxxxxxxxxx = 1.1999999F;
                     â˜ƒ.move(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0));
                  }

                  SoundEvent â˜ƒxxx;
                  if (â˜ƒxx >= 3) {
                     â˜ƒxxx = SoundEvents.TRIDENT_RIPTIDE_3;
                  } else if (â˜ƒxx == 2) {
                     â˜ƒxxx = SoundEvents.TRIDENT_RIPTIDE_2;
                  } else {
                     â˜ƒxxx = SoundEvents.TRIDENT_RIPTIDE_1;
                  }

                  â˜ƒ.playSound(null, â˜ƒ, â˜ƒxxx, SoundSource.PLAYERS, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.getDamageValue() >= â˜ƒ.getMaxDamage() - 1) {
         return InteractionResultHolder.fail(â˜ƒ);
      } else if (EnchantmentHelper.getRiptide(â˜ƒ) > 0 && !â˜ƒ.isInWaterOrRain()) {
         return InteractionResultHolder.fail(â˜ƒ);
      } else {
         â˜ƒ.startUsingItem(â˜ƒ);
         return InteractionResultHolder.consume(â˜ƒ);
      }
   }

   @Override
   public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
      â˜ƒ.hurtAndBreak(1, â˜ƒ, var0 -> var0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      return true;
   }

   @Override
   public boolean mineBlock(ItemStack var1, Level var2, BlockState var3, BlockPos var4, LivingEntity var5) {
      if ((double)â˜ƒ.getDestroySpeed(â˜ƒ, â˜ƒ) != 0.0) {
         â˜ƒ.hurtAndBreak(2, â˜ƒ, var0 -> var0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      }

      return true;
   }

   @Override
   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot var1) {
      return â˜ƒ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(â˜ƒ);
   }

   @Override
   public int getEnchantmentValue() {
      return 1;
   }
}
