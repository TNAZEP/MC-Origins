package net.minecraft.world.level.block.entity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BrewingStandBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
   private static final int INGREDIENT_SLOT = 3;
   private static final int FUEL_SLOT = 4;
   private static final int[] SLOTS_FOR_UP = new int[]{3};
   private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2, 3};
   private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2, 4};
   public static final int FUEL_USES = 20;
   public static final int DATA_BREW_TIME = 0;
   public static final int DATA_FUEL_USES = 1;
   public static final int NUM_DATA_VALUES = 2;
   private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
   int brewTime;
   private boolean[] lastPotionCount;
   private Item ingredient;
   int fuel;
   protected final ContainerData dataAccess = new ContainerData() {
      @Override
      public int get(int var1) {
         switch(â˜ƒ) {
            case 0:
               return BrewingStandBlockEntity.this.brewTime;
            case 1:
               return BrewingStandBlockEntity.this.fuel;
            default:
               return 0;
         }
      }

      @Override
      public void set(int var1, int var2) {
         switch(â˜ƒ) {
            case 0:
               BrewingStandBlockEntity.this.brewTime = â˜ƒ;
               break;
            case 1:
               BrewingStandBlockEntity.this.fuel = â˜ƒ;
         }
      }

      @Override
      public int getCount() {
         return 2;
      }
   };

   public BrewingStandBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.BREWING_STAND, â˜ƒ, â˜ƒ);
   }

   @Override
   protected Component getDefaultName() {
      return new TranslatableComponent("container.brewing");
   }

   @Override
   public int getContainerSize() {
      return this.items.size();
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.items) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public static void serverTick(Level var0, BlockPos var1, BlockState var2, BrewingStandBlockEntity var3) {
      ItemStack â˜ƒ = â˜ƒ.items.get(4);
      if (â˜ƒ.fuel <= 0 && â˜ƒ.is(Items.BLAZE_POWDER)) {
         â˜ƒ.fuel = 20;
         â˜ƒ.shrink(1);
         setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      boolean â˜ƒ = isBrewable(â˜ƒ.items);
      boolean â˜ƒx = â˜ƒ.brewTime > 0;
      ItemStack â˜ƒxx = â˜ƒ.items.get(3);
      if (â˜ƒx) {
         --â˜ƒ.brewTime;
         boolean â˜ƒxxx = â˜ƒ.brewTime == 0;
         if (â˜ƒxxx && â˜ƒ) {
            doBrew(â˜ƒ, â˜ƒ, â˜ƒ.items);
            setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
         } else if (!â˜ƒ || !â˜ƒxx.is(â˜ƒ.ingredient)) {
            â˜ƒ.brewTime = 0;
            setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      } else if (â˜ƒ && â˜ƒ.fuel > 0) {
         --â˜ƒ.fuel;
         â˜ƒ.brewTime = 400;
         â˜ƒ.ingredient = â˜ƒxx.getItem();
         setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      boolean[] â˜ƒ = â˜ƒ.getPotionBits();
      if (!Arrays.equals(â˜ƒ, â˜ƒ.lastPotionCount)) {
         â˜ƒ.lastPotionCount = â˜ƒ;
         BlockState â˜ƒx = â˜ƒ;
         if (!(â˜ƒ.getBlock() instanceof BrewingStandBlock)) {
            return;
         }

         for(int â˜ƒx = 0; â˜ƒx < BrewingStandBlock.HAS_BOTTLE.length; ++â˜ƒx) {
            â˜ƒx = â˜ƒx.setValue(BrewingStandBlock.HAS_BOTTLE[â˜ƒx], Boolean.valueOf(â˜ƒ[â˜ƒx]));
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 2);
      }
   }

   private boolean[] getPotionBits() {
      boolean[] â˜ƒ = new boolean[3];

      for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
         if (!this.items.get(â˜ƒx).isEmpty()) {
            â˜ƒ[â˜ƒx] = true;
         }
      }

      return â˜ƒ;
   }

   private static boolean isBrewable(NonNullList<ItemStack> var0) {
      ItemStack â˜ƒ = â˜ƒ.get(3);
      if (â˜ƒ.isEmpty()) {
         return false;
      } else if (!PotionBrewing.isIngredient(â˜ƒ)) {
         return false;
      } else {
         for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
            ItemStack â˜ƒx = â˜ƒ.get(â˜ƒ);
            if (!â˜ƒx.isEmpty() && PotionBrewing.hasMix(â˜ƒx, â˜ƒ)) {
               return true;
            }
         }

         return false;
      }
   }

   private static void doBrew(Level var0, BlockPos var1, NonNullList<ItemStack> var2) {
      ItemStack â˜ƒ = â˜ƒ.get(3);

      for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
         â˜ƒ.set(â˜ƒx, PotionBrewing.mix(â˜ƒ, â˜ƒ.get(â˜ƒx)));
      }

      â˜ƒ.shrink(1);
      if (â˜ƒ.getItem().hasCraftingRemainingItem()) {
         ItemStack â˜ƒx = new ItemStack(â˜ƒ.getItem().getCraftingRemainingItem());
         if (â˜ƒ.isEmpty()) {
            â˜ƒ = â˜ƒx;
         } else {
            Containers.dropItemStack(â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), â˜ƒx);
         }
      }

      â˜ƒ.set(3, â˜ƒ);
      â˜ƒ.levelEvent(1035, â˜ƒ, 0);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      ContainerHelper.loadAllItems(â˜ƒ, this.items);
      this.brewTime = â˜ƒ.getShort("BrewTime");
      this.fuel = â˜ƒ.getByte("Fuel");
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.putShort("BrewTime", (short)this.brewTime);
      ContainerHelper.saveAllItems(â˜ƒ, this.items);
      â˜ƒ.putByte("Fuel", (byte)this.fuel);
      return â˜ƒ;
   }

   @Override
   public ItemStack getItem(int var1) {
      return â˜ƒ >= 0 && â˜ƒ < this.items.size() ? this.items.get(â˜ƒ) : ItemStack.EMPTY;
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      return ContainerHelper.removeItem(this.items, â˜ƒ, â˜ƒ);
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      return ContainerHelper.takeItem(this.items, â˜ƒ);
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      if (â˜ƒ >= 0 && â˜ƒ < this.items.size()) {
         this.items.set(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      if (this.level.getBlockEntity(this.worldPosition) != this) {
         return false;
      } else {
         return !(
            â˜ƒ.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5) > 64.0
         );
      }
   }

   @Override
   public boolean canPlaceItem(int var1, ItemStack var2) {
      if (â˜ƒ == 3) {
         return PotionBrewing.isIngredient(â˜ƒ);
      } else if (â˜ƒ == 4) {
         return â˜ƒ.is(Items.BLAZE_POWDER);
      } else {
         return (â˜ƒ.is(Items.POTION) || â˜ƒ.is(Items.SPLASH_POTION) || â˜ƒ.is(Items.LINGERING_POTION) || â˜ƒ.is(Items.GLASS_BOTTLE))
            && this.getItem(â˜ƒ).isEmpty();
      }
   }

   @Override
   public int[] getSlotsForFace(Direction var1) {
      if (â˜ƒ == Direction.UP) {
         return SLOTS_FOR_UP;
      } else {
         return â˜ƒ == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
      }
   }

   @Override
   public boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable Direction var3) {
      return this.canPlaceItem(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canTakeItemThroughFace(int var1, ItemStack var2, Direction var3) {
      return â˜ƒ == 3 ? â˜ƒ.is(Items.GLASS_BOTTLE) : true;
   }

   @Override
   public void clearContent() {
      this.items.clear();
   }

   @Override
   protected AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return new BrewingStandMenu(â˜ƒ, â˜ƒ, this, this.dataAccess);
   }
}
