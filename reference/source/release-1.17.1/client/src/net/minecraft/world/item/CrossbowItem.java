package net.minecraft.world.item;

import com.google.common.collect.Lists;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CrossbowItem extends ProjectileWeaponItem implements Vanishable {
   private static final String TAG_CHARGED = "Charged";
   private static final String TAG_CHARGED_PROJECTILES = "ChargedProjectiles";
   private static final int MAX_CHARGE_DURATION = 25;
   public static final int DEFAULT_RANGE = 8;
   private boolean startSoundPlayed = false;
   private boolean midLoadSoundPlayed = false;
   private static final float START_SOUND_PERCENT = 0.2F;
   private static final float MID_SOUND_PERCENT = 0.5F;
   private static final float ARROW_POWER = 3.15F;
   private static final float FIREWORK_POWER = 1.6F;

   public CrossbowItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public Predicate<ItemStack> getSupportedHeldProjectiles() {
      return ARROW_OR_FIREWORK;
   }

   @Override
   public Predicate<ItemStack> getAllSupportedProjectiles() {
      return ARROW_ONLY;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (isCharged(â˜ƒ)) {
         performShooting(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, getShootingPower(â˜ƒ), 1.0F);
         setCharged(â˜ƒ, false);
         return InteractionResultHolder.consume(â˜ƒ);
      } else if (!â˜ƒ.getProjectile(â˜ƒ).isEmpty()) {
         if (!isCharged(â˜ƒ)) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            â˜ƒ.startUsingItem(â˜ƒ);
         }

         return InteractionResultHolder.consume(â˜ƒ);
      } else {
         return InteractionResultHolder.fail(â˜ƒ);
      }
   }

   private static float getShootingPower(ItemStack var0) {
      return containsChargedProjectile(â˜ƒ, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
   }

   @Override
   public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
      int â˜ƒ = this.getUseDuration(â˜ƒ) - â˜ƒ;
      float â˜ƒx = getPowerForTime(â˜ƒ, â˜ƒ);
      if (â˜ƒx >= 1.0F && !isCharged(â˜ƒ) && tryLoadProjectiles(â˜ƒ, â˜ƒ)) {
         setCharged(â˜ƒ, true);
         SoundSource â˜ƒxx = â˜ƒ instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
         â˜ƒ.playSound(
            null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.CROSSBOW_LOADING_END, â˜ƒxx, 1.0F, 1.0F / (â˜ƒ.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F
         );
      }
   }

   private static boolean tryLoadProjectiles(LivingEntity var0, ItemStack var1) {
      int â˜ƒ = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, â˜ƒ);
      int â˜ƒx = â˜ƒ == 0 ? 1 : 3;
      boolean â˜ƒxx = â˜ƒ instanceof Player && ((Player)â˜ƒ).getAbilities().instabuild;
      ItemStack â˜ƒxxx = â˜ƒ.getProjectile(â˜ƒ);
      ItemStack â˜ƒxxxx = â˜ƒxxx.copy();

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒx; ++â˜ƒxxxxx) {
         if (â˜ƒxxxxx > 0) {
            â˜ƒxxx = â˜ƒxxxx.copy();
         }

         if (â˜ƒxxx.isEmpty() && â˜ƒxx) {
            â˜ƒxxx = new ItemStack(Items.ARROW);
            â˜ƒxxxx = â˜ƒxxx.copy();
         }

         if (!loadProjectile(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxxx > 0, â˜ƒxx)) {
            return false;
         }
      }

      return true;
   }

   private static boolean loadProjectile(LivingEntity var0, ItemStack var1, ItemStack var2, boolean var3, boolean var4) {
      if (â˜ƒ.isEmpty()) {
         return false;
      } else {
         boolean â˜ƒx = â˜ƒ && â˜ƒ.getItem() instanceof ArrowItem;
         ItemStack â˜ƒ;
         if (!â˜ƒx && !â˜ƒ && !â˜ƒ) {
            â˜ƒ = â˜ƒ.split(1);
            if (â˜ƒ.isEmpty() && â˜ƒ instanceof Player) {
               ((Player)â˜ƒ).getInventory().removeItem(â˜ƒ);
            }
         } else {
            â˜ƒ = â˜ƒ.copy();
         }

         addChargedProjectile(â˜ƒ, â˜ƒ);
         return true;
      }
   }

   public static boolean isCharged(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      return â˜ƒ != null && â˜ƒ.getBoolean("Charged");
   }

   public static void setCharged(ItemStack var0, boolean var1) {
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      â˜ƒ.putBoolean("Charged", â˜ƒ);
   }

   private static void addChargedProjectile(ItemStack var0, ItemStack var1) {
      CompoundTag â˜ƒx = â˜ƒ.getOrCreateTag();
      ListTag â˜ƒ;
      if (â˜ƒx.contains("ChargedProjectiles", 9)) {
         â˜ƒ = â˜ƒx.getList("ChargedProjectiles", 10);
      } else {
         â˜ƒ = new ListTag();
      }

      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.save(â˜ƒ);
      â˜ƒ.add(â˜ƒ);
      â˜ƒx.put("ChargedProjectiles", â˜ƒ);
   }

   private static List<ItemStack> getChargedProjectiles(ItemStack var0) {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayList();
      CompoundTag â˜ƒx = â˜ƒ.getTag();
      if (â˜ƒx != null && â˜ƒx.contains("ChargedProjectiles", 9)) {
         ListTag â˜ƒxx = â˜ƒx.getList("ChargedProjectiles", 10);
         if (â˜ƒxx != null) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
               CompoundTag â˜ƒxxxx = â˜ƒxx.getCompound(â˜ƒxxx);
               â˜ƒ.add(ItemStack.of(â˜ƒxxxx));
            }
         }
      }

      return â˜ƒ;
   }

   private static void clearChargedProjectiles(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null) {
         ListTag â˜ƒx = â˜ƒ.getList("ChargedProjectiles", 9);
         â˜ƒx.clear();
         â˜ƒ.put("ChargedProjectiles", â˜ƒx);
      }
   }

   public static boolean containsChargedProjectile(ItemStack var0, Item var1) {
      return getChargedProjectiles(â˜ƒ).stream().anyMatch(var1x -> var1x.is(â˜ƒ));
   }

   private static void shootProjectile(
      Level var0, LivingEntity var1, InteractionHand var2, ItemStack var3, ItemStack var4, float var5, boolean var6, float var7, float var8, float var9
   ) {
      if (!â˜ƒ.isClientSide) {
         boolean â˜ƒx = â˜ƒ.is(Items.FIREWORK_ROCKET);
         Projectile â˜ƒ;
         if (â˜ƒx) {
            â˜ƒ = new FireworkRocketEntity(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getX(), â˜ƒ.getEyeY() - 0.15F, â˜ƒ.getZ(), true);
         } else {
            â˜ƒ = getArrow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ || â˜ƒ != 0.0F) {
               ((AbstractArrow)â˜ƒ).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
         }

         if (â˜ƒ instanceof CrossbowAttackMob â˜ƒ) {
            â˜ƒ.shootCrossbowProjectile(â˜ƒ.getTarget(), â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            Vec3 â˜ƒ = â˜ƒ.getUpVector(1.0F);
            Quaternion â˜ƒx = new Quaternion(new Vector3f(â˜ƒ), â˜ƒ, true);
            Vec3 â˜ƒxx = â˜ƒ.getViewVector(1.0F);
            Vector3f â˜ƒxxx = new Vector3f(â˜ƒxx);
            â˜ƒxxx.transform(â˜ƒx);
            â˜ƒ.shoot((double)â˜ƒxxx.x(), (double)â˜ƒxxx.y(), (double)â˜ƒxxx.z(), â˜ƒ, â˜ƒ);
         }

         â˜ƒ.hurtAndBreak(â˜ƒx ? 3 : 1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
         â˜ƒ.addFreshEntity(â˜ƒ);
         â˜ƒ.playSound(null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, â˜ƒ);
      }
   }

   private static AbstractArrow getArrow(Level var0, LivingEntity var1, ItemStack var2, ItemStack var3) {
      ArrowItem â˜ƒ = (ArrowItem)(â˜ƒ.getItem() instanceof ArrowItem ? â˜ƒ.getItem() : Items.ARROW);
      AbstractArrow â˜ƒx = â˜ƒ.createArrow(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ instanceof Player) {
         â˜ƒx.setCritArrow(true);
      }

      â˜ƒx.setSoundEvent(SoundEvents.CROSSBOW_HIT);
      â˜ƒx.setShotFromCrossbow(true);
      int â˜ƒ = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, â˜ƒ);
      if (â˜ƒ > 0) {
         â˜ƒx.setPierceLevel((byte)â˜ƒ);
      }

      return â˜ƒx;
   }

   public static void performShooting(Level var0, LivingEntity var1, InteractionHand var2, ItemStack var3, float var4, float var5) {
      List<ItemStack> â˜ƒ = getChargedProjectiles(â˜ƒ);
      float[] â˜ƒx = getShotPitches(â˜ƒ.getRandom());

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = (ItemStack)â˜ƒ.get(â˜ƒxx);
         boolean â˜ƒxxxx = â˜ƒ instanceof Player && ((Player)â˜ƒ).getAbilities().instabuild;
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxx == 0) {
               shootProjectile(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒx[â˜ƒxx], â˜ƒxxxx, â˜ƒ, â˜ƒ, 0.0F);
            } else if (â˜ƒxx == 1) {
               shootProjectile(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒx[â˜ƒxx], â˜ƒxxxx, â˜ƒ, â˜ƒ, -10.0F);
            } else if (â˜ƒxx == 2) {
               shootProjectile(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒx[â˜ƒxx], â˜ƒxxxx, â˜ƒ, â˜ƒ, 10.0F);
            }
         }
      }

      onCrossbowShot(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static float[] getShotPitches(Random var0) {
      boolean â˜ƒ = â˜ƒ.nextBoolean();
      return new float[]{1.0F, getRandomShotPitch(â˜ƒ, â˜ƒ), getRandomShotPitch(!â˜ƒ, â˜ƒ)};
   }

   private static float getRandomShotPitch(boolean var0, Random var1) {
      float â˜ƒ = â˜ƒ ? 0.63F : 0.43F;
      return 1.0F / (â˜ƒ.nextFloat() * 0.5F + 1.8F) + â˜ƒ;
   }

   private static void onCrossbowShot(Level var0, LivingEntity var1, ItemStack var2) {
      if (â˜ƒ instanceof ServerPlayer â˜ƒ) {
         if (!â˜ƒ.isClientSide) {
            CriteriaTriggers.SHOT_CROSSBOW.trigger(â˜ƒ, â˜ƒ);
         }

         â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒ.getItem()));
      }

      clearChargedProjectiles(â˜ƒ);
   }

   @Override
   public void onUseTick(Level var1, LivingEntity var2, ItemStack var3, int var4) {
      if (!â˜ƒ.isClientSide) {
         int â˜ƒ = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, â˜ƒ);
         SoundEvent â˜ƒx = this.getStartSound(â˜ƒ);
         SoundEvent â˜ƒxx = â˜ƒ == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
         float â˜ƒxxx = (float)(â˜ƒ.getUseDuration() - â˜ƒ) / (float)getChargeDuration(â˜ƒ);
         if (â˜ƒxxx < 0.2F) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
         }

         if (â˜ƒxxx >= 0.2F && !this.startSoundPlayed) {
            this.startSoundPlayed = true;
            â˜ƒ.playSound(null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒx, SoundSource.PLAYERS, 0.5F, 1.0F);
         }

         if (â˜ƒxxx >= 0.5F && â˜ƒxx != null && !this.midLoadSoundPlayed) {
            this.midLoadSoundPlayed = true;
            â˜ƒ.playSound(null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒxx, SoundSource.PLAYERS, 0.5F, 1.0F);
         }
      }
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return getChargeDuration(â˜ƒ) + 3;
   }

   public static int getChargeDuration(ItemStack var0) {
      int â˜ƒ = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, â˜ƒ);
      return â˜ƒ == 0 ? 25 : 25 - 5 * â˜ƒ;
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.CROSSBOW;
   }

   private SoundEvent getStartSound(int var1) {
      switch(â˜ƒ) {
         case 1:
            return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
         case 2:
            return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
         case 3:
            return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
         default:
            return SoundEvents.CROSSBOW_LOADING_START;
      }
   }

   private static float getPowerForTime(int var0, ItemStack var1) {
      float â˜ƒ = (float)â˜ƒ / (float)getChargeDuration(â˜ƒ);
      if (â˜ƒ > 1.0F) {
         â˜ƒ = 1.0F;
      }

      return â˜ƒ;
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      List<ItemStack> â˜ƒ = getChargedProjectiles(â˜ƒ);
      if (isCharged(â˜ƒ) && !â˜ƒ.isEmpty()) {
         ItemStack â˜ƒx = (ItemStack)â˜ƒ.get(0);
         â˜ƒ.add(new TranslatableComponent("item.minecraft.crossbow.projectile").append(" ").append(â˜ƒx.getDisplayName()));
         if (â˜ƒ.isAdvanced() && â˜ƒx.is(Items.FIREWORK_ROCKET)) {
            List<Component> â˜ƒxx = Lists.<Component>newArrayList();
            Items.FIREWORK_ROCKET.appendHoverText(â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
            if (!â˜ƒxx.isEmpty()) {
               for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
                  â˜ƒxx.set(â˜ƒxxx, new TextComponent("  ").append((Component)â˜ƒxx.get(â˜ƒxxx)).withStyle(ChatFormatting.GRAY));
               }

               â˜ƒ.addAll(â˜ƒxx);
            }
         }
      }
   }

   @Override
   public boolean useOnRelease(ItemStack var1) {
      return â˜ƒ.is(this);
   }

   @Override
   public int getDefaultProjectileRange() {
      return 8;
   }
}
