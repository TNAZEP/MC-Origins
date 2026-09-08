package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class Inventory implements Container, Nameable {
   public static final int POP_TIME_DURATION = 5;
   public static final int INVENTORY_SIZE = 36;
   private static final int SELECTION_SIZE = 9;
   public static final int SLOT_OFFHAND = 40;
   public static final int NOT_FOUND_INDEX = -1;
   public static final int[] ALL_ARMOR_SLOTS = new int[]{0, 1, 2, 3};
   public static final int[] HELMET_SLOT_ONLY = new int[]{3};
   public final NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
   public final NonNullList<ItemStack> armor = NonNullList.withSize(4, ItemStack.EMPTY);
   public final NonNullList<ItemStack> offhand = NonNullList.withSize(1, ItemStack.EMPTY);
   private final List<NonNullList<ItemStack>> compartments = ImmutableList.of(this.items, this.armor, this.offhand);
   public int selected;
   public final Player player;
   private int timesChanged;

   public Inventory(Player var1) {
      this.player = â˜ƒ;
   }

   public ItemStack getSelected() {
      return isHotbarSlot(this.selected) ? this.items.get(this.selected) : ItemStack.EMPTY;
   }

   public static int getSelectionSize() {
      return 9;
   }

   private boolean hasRemainingSpaceForItem(ItemStack var1, ItemStack var2) {
      return !â˜ƒ.isEmpty()
         && ItemStack.isSameItemSameTags(â˜ƒ, â˜ƒ)
         && â˜ƒ.isStackable()
         && â˜ƒ.getCount() < â˜ƒ.getMaxStackSize()
         && â˜ƒ.getCount() < this.getMaxStackSize();
   }

   public int getFreeSlot() {
      for(int â˜ƒ = 0; â˜ƒ < this.items.size(); ++â˜ƒ) {
         if (this.items.get(â˜ƒ).isEmpty()) {
            return â˜ƒ;
         }
      }

      return -1;
   }

   public void setPickedItem(ItemStack var1) {
      int â˜ƒ = this.findSlotMatchingItem(â˜ƒ);
      if (isHotbarSlot(â˜ƒ)) {
         this.selected = â˜ƒ;
      } else {
         if (â˜ƒ == -1) {
            this.selected = this.getSuitableHotbarSlot();
            if (!this.items.get(this.selected).isEmpty()) {
               int â˜ƒ = this.getFreeSlot();
               if (â˜ƒ != -1) {
                  this.items.set(â˜ƒ, this.items.get(this.selected));
               }
            }

            this.items.set(this.selected, â˜ƒ);
         } else {
            this.pickSlot(â˜ƒ);
         }
      }
   }

   public void pickSlot(int var1) {
      this.selected = this.getSuitableHotbarSlot();
      ItemStack â˜ƒ = this.items.get(this.selected);
      this.items.set(this.selected, this.items.get(â˜ƒ));
      this.items.set(â˜ƒ, â˜ƒ);
   }

   public static boolean isHotbarSlot(int var0) {
      return â˜ƒ >= 0 && â˜ƒ < 9;
   }

   public int findSlotMatchingItem(ItemStack var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.items.size(); ++â˜ƒ) {
         if (!this.items.get(â˜ƒ).isEmpty() && ItemStack.isSameItemSameTags(â˜ƒ, this.items.get(â˜ƒ))) {
            return â˜ƒ;
         }
      }

      return -1;
   }

   public int findSlotMatchingUnusedItem(ItemStack var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.items.size(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.items.get(â˜ƒ);
         if (!this.items.get(â˜ƒ).isEmpty()
            && ItemStack.isSameItemSameTags(â˜ƒ, this.items.get(â˜ƒ))
            && !this.items.get(â˜ƒ).isDamaged()
            && !â˜ƒx.isEnchanted()
            && !â˜ƒx.hasCustomHoverName()) {
            return â˜ƒ;
         }
      }

      return -1;
   }

   public int getSuitableHotbarSlot() {
      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         int â˜ƒx = (this.selected + â˜ƒ) % 9;
         if (this.items.get(â˜ƒx).isEmpty()) {
            return â˜ƒx;
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         int â˜ƒx = (this.selected + â˜ƒ) % 9;
         if (!this.items.get(â˜ƒx).isEnchanted()) {
            return â˜ƒx;
         }
      }

      return this.selected;
   }

   public void swapPaint(double var1) {
      if (â˜ƒ > 0.0) {
         â˜ƒ = 1.0;
      }

      if (â˜ƒ < 0.0) {
         â˜ƒ = -1.0;
      }

      this.selected = (int)((double)this.selected - â˜ƒ);

      while(this.selected < 0) {
         this.selected += 9;
      }

      while(this.selected >= 9) {
         this.selected -= 9;
      }
   }

   public int clearOrCountMatchingItems(Predicate<ItemStack> var1, int var2, Container var3) {
      int â˜ƒ = 0;
      boolean â˜ƒx = â˜ƒ == 0;
      â˜ƒ += ContainerHelper.clearOrCountMatchingItems(this, â˜ƒ, â˜ƒ - â˜ƒ, â˜ƒx);
      â˜ƒ += ContainerHelper.clearOrCountMatchingItems(â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ, â˜ƒx);
      ItemStack â˜ƒxx = this.player.containerMenu.getCarried();
      â˜ƒ += ContainerHelper.clearOrCountMatchingItems(â˜ƒxx, â˜ƒ, â˜ƒ - â˜ƒ, â˜ƒx);
      if (â˜ƒxx.isEmpty()) {
         this.player.containerMenu.setCarried(ItemStack.EMPTY);
      }

      return â˜ƒ;
   }

   private int addResource(ItemStack var1) {
      int â˜ƒ = this.getSlotWithRemainingSpace(â˜ƒ);
      if (â˜ƒ == -1) {
         â˜ƒ = this.getFreeSlot();
      }

      return â˜ƒ == -1 ? â˜ƒ.getCount() : this.addResource(â˜ƒ, â˜ƒ);
   }

   private int addResource(int var1, ItemStack var2) {
      Item â˜ƒ = â˜ƒ.getItem();
      int â˜ƒx = â˜ƒ.getCount();
      ItemStack â˜ƒxx = this.getItem(â˜ƒ);
      if (â˜ƒxx.isEmpty()) {
         â˜ƒxx = new ItemStack(â˜ƒ, 0);
         if (â˜ƒ.hasTag()) {
            â˜ƒxx.setTag(â˜ƒ.getTag().copy());
         }

         this.setItem(â˜ƒ, â˜ƒxx);
      }

      int â˜ƒ = â˜ƒx;
      if (â˜ƒx > â˜ƒxx.getMaxStackSize() - â˜ƒxx.getCount()) {
         â˜ƒ = â˜ƒxx.getMaxStackSize() - â˜ƒxx.getCount();
      }

      if (â˜ƒ > this.getMaxStackSize() - â˜ƒxx.getCount()) {
         â˜ƒ = this.getMaxStackSize() - â˜ƒxx.getCount();
      }

      if (â˜ƒ == 0) {
         return â˜ƒx;
      } else {
         â˜ƒx -= â˜ƒ;
         â˜ƒxx.grow(â˜ƒ);
         â˜ƒxx.setPopTime(5);
         return â˜ƒx;
      }
   }

   public int getSlotWithRemainingSpace(ItemStack var1) {
      if (this.hasRemainingSpaceForItem(this.getItem(this.selected), â˜ƒ)) {
         return this.selected;
      } else if (this.hasRemainingSpaceForItem(this.getItem(40), â˜ƒ)) {
         return 40;
      } else {
         for(int â˜ƒ = 0; â˜ƒ < this.items.size(); ++â˜ƒ) {
            if (this.hasRemainingSpaceForItem(this.items.get(â˜ƒ), â˜ƒ)) {
               return â˜ƒ;
            }
         }

         return -1;
      }
   }

   public void tick() {
      for(NonNullList<ItemStack> â˜ƒ : this.compartments) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            if (!â˜ƒ.get(â˜ƒx).isEmpty()) {
               â˜ƒ.get(â˜ƒx).inventoryTick(this.player.level, this.player, â˜ƒx, this.selected == â˜ƒx);
            }
         }
      }
   }

   public boolean add(ItemStack var1) {
      return this.add(-1, â˜ƒ);
   }

   public boolean add(int var1, ItemStack var2) {
      if (â˜ƒ.isEmpty()) {
         return false;
      } else {
         try {
            if (â˜ƒ.isDamaged()) {
               if (â˜ƒ == -1) {
                  â˜ƒ = this.getFreeSlot();
               }

               if (â˜ƒ >= 0) {
                  this.items.set(â˜ƒ, â˜ƒ.copy());
                  this.items.get(â˜ƒ).setPopTime(5);
                  â˜ƒ.setCount(0);
                  return true;
               } else if (this.player.getAbilities().instabuild) {
                  â˜ƒ.setCount(0);
                  return true;
               } else {
                  return false;
               }
            } else {
               int â˜ƒ;
               do {
                  â˜ƒ = â˜ƒ.getCount();
                  if (â˜ƒ == -1) {
                     â˜ƒ.setCount(this.addResource(â˜ƒ));
                  } else {
                     â˜ƒ.setCount(this.addResource(â˜ƒ, â˜ƒ));
                  }
               } while(!â˜ƒ.isEmpty() && â˜ƒ.getCount() < â˜ƒ);

               if (â˜ƒ.getCount() == â˜ƒ && this.player.getAbilities().instabuild) {
                  â˜ƒ.setCount(0);
                  return true;
               } else {
                  return â˜ƒ.getCount() < â˜ƒ;
               }
            }
         } catch (Throwable var6) {
            CrashReport â˜ƒ = CrashReport.forThrowable(var6, "Adding item to inventory");
            CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Item being added");
            â˜ƒx.setDetail("Item ID", Item.getId(â˜ƒ.getItem()));
            â˜ƒx.setDetail("Item data", â˜ƒ.getDamageValue());
            â˜ƒx.setDetail("Item name", (CrashReportDetail<String>)(() -> â˜ƒ.getHoverName().getString()));
            throw new ReportedException(â˜ƒ);
         }
      }
   }

   public void placeItemBackInInventory(ItemStack var1) {
      this.placeItemBackInInventory(â˜ƒ, true);
   }

   public void placeItemBackInInventory(ItemStack var1, boolean var2) {
      while(!â˜ƒ.isEmpty()) {
         int â˜ƒ = this.getSlotWithRemainingSpace(â˜ƒ);
         if (â˜ƒ == -1) {
            â˜ƒ = this.getFreeSlot();
         }

         if (â˜ƒ == -1) {
            this.player.drop(â˜ƒ, false);
            break;
         }

         int â˜ƒ = â˜ƒ.getMaxStackSize() - this.getItem(â˜ƒ).getCount();
         if (this.add(â˜ƒ, â˜ƒ.split(â˜ƒ)) && â˜ƒ && this.player instanceof ServerPlayer) {
            ((ServerPlayer)this.player).connection.send(new ClientboundContainerSetSlotPacket(-2, 0, â˜ƒ, this.getItem(â˜ƒ)));
         }
      }
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      List<ItemStack> â˜ƒ = null;

      for(NonNullList<ItemStack> â˜ƒx : this.compartments) {
         if (â˜ƒ < â˜ƒx.size()) {
            â˜ƒ = â˜ƒx;
            break;
         }

         â˜ƒ -= â˜ƒx.size();
      }

      return â˜ƒ != null && !((ItemStack)â˜ƒ.get(â˜ƒ)).isEmpty() ? ContainerHelper.removeItem(â˜ƒ, â˜ƒ, â˜ƒ) : ItemStack.EMPTY;
   }

   public void removeItem(ItemStack var1) {
      for(NonNullList<ItemStack> â˜ƒ : this.compartments) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            if (â˜ƒ.get(â˜ƒx) == â˜ƒ) {
               â˜ƒ.set(â˜ƒx, ItemStack.EMPTY);
               break;
            }
         }
      }
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      NonNullList<ItemStack> â˜ƒ = null;

      for(NonNullList<ItemStack> â˜ƒx : this.compartments) {
         if (â˜ƒ < â˜ƒx.size()) {
            â˜ƒ = â˜ƒx;
            break;
         }

         â˜ƒ -= â˜ƒx.size();
      }

      if (â˜ƒ != null && !â˜ƒ.get(â˜ƒ).isEmpty()) {
         ItemStack â˜ƒx = â˜ƒ.get(â˜ƒ);
         â˜ƒ.set(â˜ƒ, ItemStack.EMPTY);
         return â˜ƒx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      NonNullList<ItemStack> â˜ƒ = null;

      for(NonNullList<ItemStack> â˜ƒx : this.compartments) {
         if (â˜ƒ < â˜ƒx.size()) {
            â˜ƒ = â˜ƒx;
            break;
         }

         â˜ƒ -= â˜ƒx.size();
      }

      if (â˜ƒ != null) {
         â˜ƒ.set(â˜ƒ, â˜ƒ);
      }
   }

   public float getDestroySpeed(BlockState var1) {
      return this.items.get(this.selected).getDestroySpeed(â˜ƒ);
   }

   public ListTag save(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.items.size(); ++â˜ƒ) {
         if (!this.items.get(â˜ƒ).isEmpty()) {
            CompoundTag â˜ƒx = new CompoundTag();
            â˜ƒx.putByte("Slot", (byte)â˜ƒ);
            this.items.get(â˜ƒ).save(â˜ƒx);
            â˜ƒ.add(â˜ƒx);
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < this.armor.size(); ++â˜ƒ) {
         if (!this.armor.get(â˜ƒ).isEmpty()) {
            CompoundTag â˜ƒx = new CompoundTag();
            â˜ƒx.putByte("Slot", (byte)(â˜ƒ + 100));
            this.armor.get(â˜ƒ).save(â˜ƒx);
            â˜ƒ.add(â˜ƒx);
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < this.offhand.size(); ++â˜ƒ) {
         if (!this.offhand.get(â˜ƒ).isEmpty()) {
            CompoundTag â˜ƒx = new CompoundTag();
            â˜ƒx.putByte("Slot", (byte)(â˜ƒ + 150));
            this.offhand.get(â˜ƒ).save(â˜ƒx);
            â˜ƒ.add(â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   public void load(ListTag var1) {
      this.items.clear();
      this.armor.clear();
      this.offhand.clear();

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound(â˜ƒ);
         int â˜ƒxx = â˜ƒx.getByte("Slot") & 255;
         ItemStack â˜ƒxxx = ItemStack.of(â˜ƒx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxx >= 0 && â˜ƒxx < this.items.size()) {
               this.items.set(â˜ƒxx, â˜ƒxxx);
            } else if (â˜ƒxx >= 100 && â˜ƒxx < this.armor.size() + 100) {
               this.armor.set(â˜ƒxx - 100, â˜ƒxxx);
            } else if (â˜ƒxx >= 150 && â˜ƒxx < this.offhand.size() + 150) {
               this.offhand.set(â˜ƒxx - 150, â˜ƒxxx);
            }
         }
      }
   }

   @Override
   public int getContainerSize() {
      return this.items.size() + this.armor.size() + this.offhand.size();
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.items) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      for(ItemStack â˜ƒ : this.armor) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      for(ItemStack â˜ƒ : this.offhand) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getItem(int var1) {
      List<ItemStack> â˜ƒ = null;

      for(NonNullList<ItemStack> â˜ƒx : this.compartments) {
         if (â˜ƒ < â˜ƒx.size()) {
            â˜ƒ = â˜ƒx;
            break;
         }

         â˜ƒ -= â˜ƒx.size();
      }

      return â˜ƒ == null ? ItemStack.EMPTY : (ItemStack)â˜ƒ.get(â˜ƒ);
   }

   @Override
   public Component getName() {
      return new TranslatableComponent("container.inventory");
   }

   public ItemStack getArmor(int var1) {
      return this.armor.get(â˜ƒ);
   }

   public void hurtArmor(DamageSource var1, float var2, int[] var3) {
      if (!(â˜ƒ <= 0.0F)) {
         â˜ƒ /= 4.0F;
         if (â˜ƒ < 1.0F) {
            â˜ƒ = 1.0F;
         }

         for(int â˜ƒ : â˜ƒ) {
            ItemStack â˜ƒx = this.armor.get(â˜ƒ);
            if ((!â˜ƒ.isFire() || !â˜ƒx.getItem().isFireResistant()) && â˜ƒx.getItem() instanceof ArmorItem) {
               â˜ƒx.hurtAndBreak((int)â˜ƒ, this.player, var1x -> var1x.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, â˜ƒ)));
            }
         }
      }
   }

   public void dropAll() {
      for(List<ItemStack> â˜ƒ : this.compartments) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            ItemStack â˜ƒxx = (ItemStack)â˜ƒ.get(â˜ƒx);
            if (!â˜ƒxx.isEmpty()) {
               this.player.drop(â˜ƒxx, true, false);
               â˜ƒ.set(â˜ƒx, ItemStack.EMPTY);
            }
         }
      }
   }

   @Override
   public void setChanged() {
      ++this.timesChanged;
   }

   public int getTimesChanged() {
      return this.timesChanged;
   }

   @Override
   public boolean stillValid(Player var1) {
      if (this.player.isRemoved()) {
         return false;
      } else {
         return !(â˜ƒ.distanceToSqr(this.player) > 64.0);
      }
   }

   public boolean contains(ItemStack var1) {
      for(List<ItemStack> â˜ƒ : this.compartments) {
         for(ItemStack â˜ƒx : â˜ƒ) {
            if (!â˜ƒx.isEmpty() && â˜ƒx.sameItem(â˜ƒ)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean contains(Tag<Item> var1) {
      for(List<ItemStack> â˜ƒ : this.compartments) {
         for(ItemStack â˜ƒx : â˜ƒ) {
            if (!â˜ƒx.isEmpty() && â˜ƒx.is(â˜ƒ)) {
               return true;
            }
         }
      }

      return false;
   }

   public void replaceWith(Inventory var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.getContainerSize(); ++â˜ƒ) {
         this.setItem(â˜ƒ, â˜ƒ.getItem(â˜ƒ));
      }

      this.selected = â˜ƒ.selected;
   }

   @Override
   public void clearContent() {
      for(List<ItemStack> â˜ƒ : this.compartments) {
         â˜ƒ.clear();
      }
   }

   public void fillStackedContents(StackedContents var1) {
      for(ItemStack â˜ƒ : this.items) {
         â˜ƒ.accountSimpleStack(â˜ƒ);
      }
   }

   public ItemStack removeFromSelected(boolean var1) {
      ItemStack â˜ƒ = this.getSelected();
      return â˜ƒ.isEmpty() ? ItemStack.EMPTY : this.removeItem(this.selected, â˜ƒ ? â˜ƒ.getCount() : 1);
   }
}
