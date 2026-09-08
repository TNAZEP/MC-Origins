package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

public class HopperBlockEntity extends RandomizableContainerBlockEntity implements Hopper {
   public static final int MOVE_ITEM_SPEED = 8;
   public static final int HOPPER_CONTAINER_SIZE = 5;
   private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
   private int cooldownTime = -1;
   private long tickedGameTime;

   public HopperBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.HOPPER, â˜ƒ, â˜ƒ);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (!this.tryLoadLootTable(â˜ƒ)) {
         ContainerHelper.loadAllItems(â˜ƒ, this.items);
      }

      this.cooldownTime = â˜ƒ.getInt("TransferCooldown");
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (!this.trySaveLootTable(â˜ƒ)) {
         ContainerHelper.saveAllItems(â˜ƒ, this.items);
      }

      â˜ƒ.putInt("TransferCooldown", this.cooldownTime);
      return â˜ƒ;
   }

   @Override
   public int getContainerSize() {
      return this.items.size();
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      this.unpackLootTable(null);
      return ContainerHelper.removeItem(this.getItems(), â˜ƒ, â˜ƒ);
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      this.unpackLootTable(null);
      this.getItems().set(â˜ƒ, â˜ƒ);
      if (â˜ƒ.getCount() > this.getMaxStackSize()) {
         â˜ƒ.setCount(this.getMaxStackSize());
      }
   }

   @Override
   protected Component getDefaultName() {
      return new TranslatableComponent("container.hopper");
   }

   public static void pushItemsTick(Level var0, BlockPos var1, BlockState var2, HopperBlockEntity var3) {
      --â˜ƒ.cooldownTime;
      â˜ƒ.tickedGameTime = â˜ƒ.getGameTime();
      if (!â˜ƒ.isOnCooldown()) {
         â˜ƒ.setCooldown(0);
         tryMoveItems(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, () -> suckInItems(â˜ƒ, â˜ƒ));
      }
   }

   private static boolean tryMoveItems(Level var0, BlockPos var1, BlockState var2, HopperBlockEntity var3, BooleanSupplier var4) {
      if (â˜ƒ.isClientSide) {
         return false;
      } else {
         if (!â˜ƒ.isOnCooldown() && â˜ƒ.getValue(HopperBlock.ENABLED)) {
            boolean â˜ƒ = false;
            if (!â˜ƒ.isEmpty()) {
               â˜ƒ = ejectItems(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }

            if (!â˜ƒ.inventoryFull()) {
               â˜ƒ |= â˜ƒ.getAsBoolean();
            }

            if (â˜ƒ) {
               â˜ƒ.setCooldown(8);
               setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
               return true;
            }
         }

         return false;
      }
   }

   private boolean inventoryFull() {
      for(ItemStack â˜ƒ : this.items) {
         if (â˜ƒ.isEmpty() || â˜ƒ.getCount() != â˜ƒ.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   private static boolean ejectItems(Level var0, BlockPos var1, BlockState var2, Container var3) {
      Container â˜ƒ = getAttachedContainer(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ == null) {
         return false;
      } else {
         Direction â˜ƒ = ((Direction)â˜ƒ.getValue(HopperBlock.FACING)).getOpposite();
         if (isFullContainer(â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getContainerSize(); ++â˜ƒ) {
               if (!â˜ƒ.getItem(â˜ƒ).isEmpty()) {
                  ItemStack â˜ƒx = â˜ƒ.getItem(â˜ƒ).copy();
                  ItemStack â˜ƒxx = addItem(â˜ƒ, â˜ƒ, â˜ƒ.removeItem(â˜ƒ, 1), â˜ƒ);
                  if (â˜ƒxx.isEmpty()) {
                     â˜ƒ.setChanged();
                     return true;
                  }

                  â˜ƒ.setItem(â˜ƒ, â˜ƒx);
               }
            }

            return false;
         }
      }
   }

   private static IntStream getSlots(Container var0, Direction var1) {
      return â˜ƒ instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)â˜ƒ).getSlotsForFace(â˜ƒ)) : IntStream.range(0, â˜ƒ.getContainerSize());
   }

   private static boolean isFullContainer(Container var0, Direction var1) {
      return getSlots(â˜ƒ, â˜ƒ).allMatch(var1x -> {
         ItemStack â˜ƒ = â˜ƒ.getItem(var1x);
         return â˜ƒ.getCount() >= â˜ƒ.getMaxStackSize();
      });
   }

   private static boolean isEmptyContainer(Container var0, Direction var1) {
      return getSlots(â˜ƒ, â˜ƒ).allMatch(var1x -> â˜ƒ.getItem(var1x).isEmpty());
   }

   public static boolean suckInItems(Level var0, Hopper var1) {
      Container â˜ƒ = getSourceContainer(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         Direction â˜ƒx = Direction.DOWN;
         return isEmptyContainer(â˜ƒ, â˜ƒx) ? false : getSlots(â˜ƒ, â˜ƒx).anyMatch(var3 -> tryTakeInItemFromSlot(â˜ƒ, â˜ƒ, var3, â˜ƒ));
      } else {
         for(ItemEntity â˜ƒ : getItemsAtAndAbove(â˜ƒ, â˜ƒ)) {
            if (addItem(â˜ƒ, â˜ƒ)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean tryTakeInItemFromSlot(Hopper var0, Container var1, int var2, Direction var3) {
      ItemStack â˜ƒ = â˜ƒ.getItem(â˜ƒ);
      if (!â˜ƒ.isEmpty() && canTakeItemFromContainer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         ItemStack â˜ƒx = â˜ƒ.copy();
         ItemStack â˜ƒxx = addItem(â˜ƒ, â˜ƒ, â˜ƒ.removeItem(â˜ƒ, 1), null);
         if (â˜ƒxx.isEmpty()) {
            â˜ƒ.setChanged();
            return true;
         }

         â˜ƒ.setItem(â˜ƒ, â˜ƒx);
      }

      return false;
   }

   public static boolean addItem(Container var0, ItemEntity var1) {
      boolean â˜ƒ = false;
      ItemStack â˜ƒx = â˜ƒ.getItem().copy();
      ItemStack â˜ƒxx = addItem(null, â˜ƒ, â˜ƒx, null);
      if (â˜ƒxx.isEmpty()) {
         â˜ƒ = true;
         â˜ƒ.discard();
      } else {
         â˜ƒ.setItem(â˜ƒxx);
      }

      return â˜ƒ;
   }

   public static ItemStack addItem(@Nullable Container var0, Container var1, ItemStack var2, @Nullable Direction var3) {
      if (â˜ƒ instanceof WorldlyContainer â˜ƒ && â˜ƒ != null) {
         int[] â˜ƒx = â˜ƒ.getSlotsForFace(â˜ƒ);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.length && !â˜ƒ.isEmpty(); ++â˜ƒxx) {
            â˜ƒ = tryMoveInItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx[â˜ƒxx], â˜ƒ);
         }
      } else {
         int â˜ƒ = â˜ƒ.getContainerSize();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ && !â˜ƒ.isEmpty(); ++â˜ƒx) {
            â˜ƒ = tryMoveInItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
         }
      }

      return â˜ƒ;
   }

   private static boolean canPlaceItemInContainer(Container var0, ItemStack var1, int var2, @Nullable Direction var3) {
      if (!â˜ƒ.canPlaceItem(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         return !(â˜ƒ instanceof WorldlyContainer) || ((WorldlyContainer)â˜ƒ).canPlaceItemThroughFace(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static boolean canTakeItemFromContainer(Container var0, ItemStack var1, int var2, Direction var3) {
      return !(â˜ƒ instanceof WorldlyContainer) || ((WorldlyContainer)â˜ƒ).canTakeItemThroughFace(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static ItemStack tryMoveInItem(@Nullable Container var0, Container var1, ItemStack var2, int var3, @Nullable Direction var4) {
      ItemStack â˜ƒ = â˜ƒ.getItem(â˜ƒ);
      if (canPlaceItemInContainer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         boolean â˜ƒx = false;
         boolean â˜ƒxx = â˜ƒ.isEmpty();
         if (â˜ƒ.isEmpty()) {
            â˜ƒ.setItem(â˜ƒ, â˜ƒ);
            â˜ƒ = ItemStack.EMPTY;
            â˜ƒx = true;
         } else if (canMergeItems(â˜ƒ, â˜ƒ)) {
            int â˜ƒx = â˜ƒ.getMaxStackSize() - â˜ƒ.getCount();
            int â˜ƒxx = Math.min(â˜ƒ.getCount(), â˜ƒx);
            â˜ƒ.shrink(â˜ƒxx);
            â˜ƒ.grow(â˜ƒxx);
            â˜ƒx = â˜ƒxx > 0;
         }

         if (â˜ƒx) {
            if (â˜ƒxx && â˜ƒ instanceof HopperBlockEntity â˜ƒx && !â˜ƒx.isOnCustomCooldown()) {
               int â˜ƒxxx = 0;
               if (â˜ƒ instanceof HopperBlockEntity â˜ƒxx && â˜ƒx.tickedGameTime >= â˜ƒxx.tickedGameTime) {
                  â˜ƒxxx = 1;
               }

               â˜ƒx.setCooldown(8 - â˜ƒxxx);
            }

            â˜ƒ.setChanged();
         }
      }

      return â˜ƒ;
   }

   @Nullable
   private static Container getAttachedContainer(Level var0, BlockPos var1, BlockState var2) {
      Direction â˜ƒ = â˜ƒ.getValue(HopperBlock.FACING);
      return getContainerAt(â˜ƒ, â˜ƒ.relative(â˜ƒ));
   }

   @Nullable
   private static Container getSourceContainer(Level var0, Hopper var1) {
      return getContainerAt(â˜ƒ, â˜ƒ.getLevelX(), â˜ƒ.getLevelY() + 1.0, â˜ƒ.getLevelZ());
   }

   public static List<ItemEntity> getItemsAtAndAbove(Level var0, Hopper var1) {
      return (List<ItemEntity>)â˜ƒ.getSuckShape()
         .toAabbs()
         .stream()
         .flatMap(
            var2 -> â˜ƒ.getEntitiesOfClass(
                     ItemEntity.class, var2.move(â˜ƒ.getLevelX() - 0.5, â˜ƒ.getLevelY() - 0.5, â˜ƒ.getLevelZ() - 0.5), EntitySelector.ENTITY_STILL_ALIVE
                  )
                  .stream()
         )
         .collect(Collectors.toList());
   }

   @Nullable
   public static Container getContainerAt(Level var0, BlockPos var1) {
      return getContainerAt(â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5);
   }

   @Nullable
   private static Container getContainerAt(Level var0, double var1, double var3, double var5) {
      Container â˜ƒ = null;
      BlockPos â˜ƒx = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      Block â˜ƒxxx = â˜ƒxx.getBlock();
      if (â˜ƒxxx instanceof WorldlyContainerHolder) {
         â˜ƒ = ((WorldlyContainerHolder)â˜ƒxxx).getContainer(â˜ƒxx, â˜ƒ, â˜ƒx);
      } else if (â˜ƒxx.hasBlockEntity()) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒx);
         if (â˜ƒ instanceof Container) {
            â˜ƒ = (Container)â˜ƒ;
            if (â˜ƒ instanceof ChestBlockEntity && â˜ƒxxx instanceof ChestBlock) {
               â˜ƒ = ChestBlock.getContainer((ChestBlock)â˜ƒxxx, â˜ƒxx, â˜ƒ, â˜ƒx, true);
            }
         }
      }

      if (â˜ƒ == null) {
         List<Entity> â˜ƒ = â˜ƒ.getEntities(
            (Entity)null, new AABB(â˜ƒ - 0.5, â˜ƒ - 0.5, â˜ƒ - 0.5, â˜ƒ + 0.5, â˜ƒ + 0.5, â˜ƒ + 0.5), EntitySelector.CONTAINER_ENTITY_SELECTOR
         );
         if (!â˜ƒ.isEmpty()) {
            â˜ƒ = (Container)â˜ƒ.get(â˜ƒ.random.nextInt(â˜ƒ.size()));
         }
      }

      return â˜ƒ;
   }

   private static boolean canMergeItems(ItemStack var0, ItemStack var1) {
      if (!â˜ƒ.is(â˜ƒ.getItem())) {
         return false;
      } else if (â˜ƒ.getDamageValue() != â˜ƒ.getDamageValue()) {
         return false;
      } else if (â˜ƒ.getCount() > â˜ƒ.getMaxStackSize()) {
         return false;
      } else {
         return ItemStack.tagMatches(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public double getLevelX() {
      return (double)this.worldPosition.getX() + 0.5;
   }

   @Override
   public double getLevelY() {
      return (double)this.worldPosition.getY() + 0.5;
   }

   @Override
   public double getLevelZ() {
      return (double)this.worldPosition.getZ() + 0.5;
   }

   private void setCooldown(int var1) {
      this.cooldownTime = â˜ƒ;
   }

   private boolean isOnCooldown() {
      return this.cooldownTime > 0;
   }

   private boolean isOnCustomCooldown() {
      return this.cooldownTime > 8;
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> var1) {
      this.items = â˜ƒ;
   }

   public static void entityInside(Level var0, BlockPos var1, BlockState var2, Entity var3, HopperBlockEntity var4) {
      if (â˜ƒ instanceof ItemEntity
         && Shapes.joinIsNotEmpty(
            Shapes.create(â˜ƒ.getBoundingBox().move((double)(-â˜ƒ.getX()), (double)(-â˜ƒ.getY()), (double)(-â˜ƒ.getZ()))), â˜ƒ.getSuckShape(), BooleanOp.AND
         )) {
         tryMoveItems(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, () -> addItem(â˜ƒ, (ItemEntity)â˜ƒ));
      }
   }

   @Override
   protected AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return new HopperMenu(â˜ƒ, â˜ƒ, this);
   }
}
