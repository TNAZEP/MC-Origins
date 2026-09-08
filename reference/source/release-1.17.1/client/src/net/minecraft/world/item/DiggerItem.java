package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DiggerItem extends TieredItem implements Vanishable {
   private final Tag<Block> blocks;
   protected final float speed;
   private final float attackDamageBaseline;
   private final Multimap<Attribute, AttributeModifier> defaultModifiers;

   protected DiggerItem(float var1, float var2, Tier var3, Tag<Block> var4, Item.Properties var5) {
      super(â˜ƒ, â˜ƒ);
      this.blocks = â˜ƒ;
      this.speed = â˜ƒ.getSpeed();
      this.attackDamageBaseline = â˜ƒ + â˜ƒ.getAttackDamageBonus();
      Builder<Attribute, AttributeModifier> â˜ƒ = ImmutableMultimap.builder();
      â˜ƒ.put(
         Attributes.ATTACK_DAMAGE,
         new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double)this.attackDamageBaseline, AttributeModifier.Operation.ADDITION)
      );
      â˜ƒ.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)â˜ƒ, AttributeModifier.Operation.ADDITION));
      this.defaultModifiers = â˜ƒ.build();
   }

   @Override
   public float getDestroySpeed(ItemStack var1, BlockState var2) {
      return this.blocks.contains(â˜ƒ.getBlock()) ? this.speed : 1.0F;
   }

   @Override
   public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
      â˜ƒ.hurtAndBreak(2, â˜ƒ, var0 -> var0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      return true;
   }

   @Override
   public boolean mineBlock(ItemStack var1, Level var2, BlockState var3, BlockPos var4, LivingEntity var5) {
      if (!â˜ƒ.isClientSide && â˜ƒ.getDestroySpeed(â˜ƒ, â˜ƒ) != 0.0F) {
         â˜ƒ.hurtAndBreak(1, â˜ƒ, var0 -> var0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      }

      return true;
   }

   @Override
   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot var1) {
      return â˜ƒ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(â˜ƒ);
   }

   public float getAttackDamage() {
      return this.attackDamageBaseline;
   }

   @Override
   public boolean isCorrectToolForDrops(BlockState var1) {
      int â˜ƒ = this.getTier().getLevel();
      if (â˜ƒ < 3 && â˜ƒ.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
         return false;
      } else if (â˜ƒ < 2 && â˜ƒ.is(BlockTags.NEEDS_IRON_TOOL)) {
         return false;
      } else {
         return â˜ƒ < 1 && â˜ƒ.is(BlockTags.NEEDS_STONE_TOOL) ? false : â˜ƒ.is(this.blocks);
      }
   }
}
