package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class SwordItem extends TieredItem implements Vanishable {
   private final float attackDamage;
   private final Multimap<Attribute, AttributeModifier> defaultModifiers;

   public SwordItem(Tier var1, int var2, float var3, Item.Properties var4) {
      super(â˜ƒ, â˜ƒ);
      this.attackDamage = (float)â˜ƒ + â˜ƒ.getAttackDamageBonus();
      Builder<Attribute, AttributeModifier> â˜ƒ = ImmutableMultimap.builder();
      â˜ƒ.put(
         Attributes.ATTACK_DAMAGE,
         new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION)
      );
      â˜ƒ.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)â˜ƒ, AttributeModifier.Operation.ADDITION));
      this.defaultModifiers = â˜ƒ.build();
   }

   public float getDamage() {
      return this.attackDamage;
   }

   @Override
   public boolean canAttackBlock(BlockState var1, Level var2, BlockPos var3, Player var4) {
      return !â˜ƒ.isCreative();
   }

   @Override
   public float getDestroySpeed(ItemStack var1, BlockState var2) {
      if (â˜ƒ.is(Blocks.COBWEB)) {
         return 15.0F;
      } else {
         Material â˜ƒ = â˜ƒ.getMaterial();
         return â˜ƒ != Material.PLANT && â˜ƒ != Material.REPLACEABLE_PLANT && !â˜ƒ.is(BlockTags.LEAVES) && â˜ƒ != Material.VEGETABLE ? 1.0F : 1.5F;
      }
   }

   @Override
   public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
      â˜ƒ.hurtAndBreak(1, â˜ƒ, var0 -> var0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      return true;
   }

   @Override
   public boolean mineBlock(ItemStack var1, Level var2, BlockState var3, BlockPos var4, LivingEntity var5) {
      if (â˜ƒ.getDestroySpeed(â˜ƒ, â˜ƒ) != 0.0F) {
         â˜ƒ.hurtAndBreak(2, â˜ƒ, var0 -> var0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      }

      return true;
   }

   @Override
   public boolean isCorrectToolForDrops(BlockState var1) {
      return â˜ƒ.is(Blocks.COBWEB);
   }

   @Override
   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot var1) {
      return â˜ƒ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(â˜ƒ);
   }
}
