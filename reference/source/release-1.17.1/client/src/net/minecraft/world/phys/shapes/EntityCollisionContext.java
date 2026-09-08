package net.minecraft.world.phys.shapes;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class EntityCollisionContext implements CollisionContext {
   protected static final CollisionContext EMPTY = new EntityCollisionContext(
      false, -Double.MAX_VALUE, ItemStack.EMPTY, ItemStack.EMPTY, var0 -> false, Optional.empty()
   ) {
      @Override
      public boolean isAbove(VoxelShape var1, BlockPos var2, boolean var3) {
         return â˜ƒ;
      }
   };
   private final boolean descending;
   private final double entityBottom;
   private final ItemStack heldItem;
   private final ItemStack footItem;
   private final Predicate<Fluid> canStandOnFluid;
   private final Optional<Entity> entity;

   protected EntityCollisionContext(boolean var1, double var2, ItemStack var4, ItemStack var5, Predicate<Fluid> var6, Optional<Entity> var7) {
      this.descending = â˜ƒ;
      this.entityBottom = â˜ƒ;
      this.footItem = â˜ƒ;
      this.heldItem = â˜ƒ;
      this.canStandOnFluid = â˜ƒ;
      this.entity = â˜ƒ;
   }

   @Deprecated
   protected EntityCollisionContext(Entity var1) {
      this(
         â˜ƒ.isDescending(),
         â˜ƒ.getY(),
         â˜ƒ instanceof LivingEntity ? ((LivingEntity)â˜ƒ).getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY,
         â˜ƒ instanceof LivingEntity ? ((LivingEntity)â˜ƒ).getMainHandItem() : ItemStack.EMPTY,
         â˜ƒ instanceof LivingEntity ? ((LivingEntity)â˜ƒ)::canStandOnFluid : var0 -> false,
         Optional.of(â˜ƒ)
      );
   }

   @Override
   public boolean hasItemOnFeet(Item var1) {
      return this.footItem.is(â˜ƒ);
   }

   @Override
   public boolean isHoldingItem(Item var1) {
      return this.heldItem.is(â˜ƒ);
   }

   @Override
   public boolean canStandOnFluid(FluidState var1, FlowingFluid var2) {
      return this.canStandOnFluid.test(â˜ƒ) && !â˜ƒ.getType().isSame(â˜ƒ);
   }

   @Override
   public boolean isDescending() {
      return this.descending;
   }

   @Override
   public boolean isAbove(VoxelShape var1, BlockPos var2, boolean var3) {
      return this.entityBottom > (double)â˜ƒ.getY() + â˜ƒ.max(Direction.Axis.Y) - 1.0E-5F;
   }

   public Optional<Entity> getEntity() {
      return this.entity;
   }
}
