package net.minecraft.world.inventory;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AbstractContainerMenu {
   public static final int SLOT_CLICKED_OUTSIDE = -999;
   public static final int QUICKCRAFT_TYPE_CHARITABLE = 0;
   public static final int QUICKCRAFT_TYPE_GREEDY = 1;
   public static final int QUICKCRAFT_TYPE_CLONE = 2;
   public static final int QUICKCRAFT_HEADER_START = 0;
   public static final int QUICKCRAFT_HEADER_CONTINUE = 1;
   public static final int QUICKCRAFT_HEADER_END = 2;
   public static final int CARRIED_SLOT_SIZE = Integer.MAX_VALUE;
   private final NonNullList<ItemStack> lastSlots = NonNullList.create();
   public final NonNullList<Slot> slots = NonNullList.create();
   private final List<DataSlot> dataSlots = Lists.<DataSlot>newArrayList();
   private ItemStack carried = ItemStack.EMPTY;
   private final NonNullList<ItemStack> remoteSlots = NonNullList.create();
   private final IntList remoteDataSlots = new IntArrayList();
   private ItemStack remoteCarried = ItemStack.EMPTY;
   private int stateId;
   @Nullable
   private final MenuType<?> menuType;
   public final int containerId;
   private int quickcraftType = -1;
   private int quickcraftStatus;
   private final Set<Slot> quickcraftSlots = Sets.<Slot>newHashSet();
   private final List<ContainerListener> containerListeners = Lists.<ContainerListener>newArrayList();
   @Nullable
   private ContainerSynchronizer synchronizer;
   private boolean suppressRemoteUpdates;

   protected AbstractContainerMenu(@Nullable MenuType<?> var1, int var2) {
      this.menuType = â˜ƒ;
      this.containerId = â˜ƒ;
   }

   protected static boolean stillValid(ContainerLevelAccess var0, Player var1, Block var2) {
      return â˜ƒ.evaluate(
         (var2x, var3) -> !var2x.getBlockState(var3).is(â˜ƒ)
               ? false
               : â˜ƒ.distanceToSqr((double)var3.getX() + 0.5, (double)var3.getY() + 0.5, (double)var3.getZ() + 0.5) <= 64.0,
         true
      );
   }

   public MenuType<?> getType() {
      if (this.menuType == null) {
         throw new UnsupportedOperationException("Unable to construct this menu by type");
      } else {
         return this.menuType;
      }
   }

   protected static void checkContainerSize(Container var0, int var1) {
      int â˜ƒ = â˜ƒ.getContainerSize();
      if (â˜ƒ < â˜ƒ) {
         throw new IllegalArgumentException("Container size " + â˜ƒ + " is smaller than expected " + â˜ƒ);
      }
   }

   protected static void checkContainerDataCount(ContainerData var0, int var1) {
      int â˜ƒ = â˜ƒ.getCount();
      if (â˜ƒ < â˜ƒ) {
         throw new IllegalArgumentException("Container data count " + â˜ƒ + " is smaller than expected " + â˜ƒ);
      }
   }

   protected Slot addSlot(Slot var1) {
      â˜ƒ.index = this.slots.size();
      this.slots.add(â˜ƒ);
      this.lastSlots.add(ItemStack.EMPTY);
      this.remoteSlots.add(ItemStack.EMPTY);
      return â˜ƒ;
   }

   protected DataSlot addDataSlot(DataSlot var1) {
      this.dataSlots.add(â˜ƒ);
      this.remoteDataSlots.add(0);
      return â˜ƒ;
   }

   protected void addDataSlots(ContainerData var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getCount(); ++â˜ƒ) {
         this.addDataSlot(DataSlot.forContainer(â˜ƒ, â˜ƒ));
      }
   }

   public void addSlotListener(ContainerListener var1) {
      if (!this.containerListeners.contains(â˜ƒ)) {
         this.containerListeners.add(â˜ƒ);
         this.broadcastChanges();
      }
   }

   public void setSynchronizer(ContainerSynchronizer var1) {
      this.synchronizer = â˜ƒ;
      this.sendAllDataToRemote();
   }

   public void sendAllDataToRemote() {
      int â˜ƒ = 0;

      for(int â˜ƒx = this.slots.size(); â˜ƒ < â˜ƒx; ++â˜ƒ) {
         this.remoteSlots.set(â˜ƒ, this.slots.get(â˜ƒ).getItem().copy());
      }

      this.remoteCarried = this.getCarried().copy();
      â˜ƒ = 0;

      for(int â˜ƒx = this.dataSlots.size(); â˜ƒ < â˜ƒx; ++â˜ƒ) {
         this.remoteDataSlots.set(â˜ƒ, ((DataSlot)this.dataSlots.get(â˜ƒ)).get());
      }

      if (this.synchronizer != null) {
         this.synchronizer.sendInitialData(this, this.remoteSlots, this.remoteCarried, this.remoteDataSlots.toIntArray());
      }
   }

   public void removeSlotListener(ContainerListener var1) {
      this.containerListeners.remove(â˜ƒ);
   }

   public NonNullList<ItemStack> getItems() {
      NonNullList<ItemStack> â˜ƒ = NonNullList.create();

      for(Slot â˜ƒx : this.slots) {
         â˜ƒ.add(â˜ƒx.getItem());
      }

      return â˜ƒ;
   }

   public void broadcastChanges() {
      for(int â˜ƒ = 0; â˜ƒ < this.slots.size(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.slots.get(â˜ƒ).getItem();
         Supplier<ItemStack> â˜ƒxx = Suppliers.memoize(â˜ƒx::copy);
         this.triggerSlotListeners(â˜ƒ, â˜ƒx, â˜ƒxx);
         this.synchronizeSlotToRemote(â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      this.synchronizeCarriedToRemote();

      for(int â˜ƒ = 0; â˜ƒ < this.dataSlots.size(); ++â˜ƒ) {
         DataSlot â˜ƒx = (DataSlot)this.dataSlots.get(â˜ƒ);
         int â˜ƒxx = â˜ƒx.get();
         if (â˜ƒx.checkAndClearUpdateFlag()) {
            this.updateDataSlotListeners(â˜ƒ, â˜ƒxx);
         }

         this.synchronizeDataSlotToRemote(â˜ƒ, â˜ƒxx);
      }
   }

   public void broadcastFullState() {
      for(int â˜ƒ = 0; â˜ƒ < this.slots.size(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.slots.get(â˜ƒ).getItem();
         this.triggerSlotListeners(â˜ƒ, â˜ƒx, â˜ƒx::copy);
      }

      for(int â˜ƒ = 0; â˜ƒ < this.dataSlots.size(); ++â˜ƒ) {
         DataSlot â˜ƒx = (DataSlot)this.dataSlots.get(â˜ƒ);
         if (â˜ƒx.checkAndClearUpdateFlag()) {
            this.updateDataSlotListeners(â˜ƒ, â˜ƒx.get());
         }
      }

      this.sendAllDataToRemote();
   }

   private void updateDataSlotListeners(int var1, int var2) {
      for(ContainerListener â˜ƒ : this.containerListeners) {
         â˜ƒ.dataChanged(this, â˜ƒ, â˜ƒ);
      }
   }

   private void triggerSlotListeners(int var1, ItemStack var2, Supplier<ItemStack> var3) {
      ItemStack â˜ƒ = this.lastSlots.get(â˜ƒ);
      if (!ItemStack.matches(â˜ƒ, â˜ƒ)) {
         ItemStack â˜ƒx = (ItemStack)â˜ƒ.get();
         this.lastSlots.set(â˜ƒ, â˜ƒx);

         for(ContainerListener â˜ƒxx : this.containerListeners) {
            â˜ƒxx.slotChanged(this, â˜ƒ, â˜ƒx);
         }
      }
   }

   private void synchronizeSlotToRemote(int var1, ItemStack var2, Supplier<ItemStack> var3) {
      if (!this.suppressRemoteUpdates) {
         ItemStack â˜ƒ = this.remoteSlots.get(â˜ƒ);
         if (!ItemStack.matches(â˜ƒ, â˜ƒ)) {
            ItemStack â˜ƒx = (ItemStack)â˜ƒ.get();
            this.remoteSlots.set(â˜ƒ, â˜ƒx);
            if (this.synchronizer != null) {
               this.synchronizer.sendSlotChange(this, â˜ƒ, â˜ƒx);
            }
         }
      }
   }

   private void synchronizeDataSlotToRemote(int var1, int var2) {
      if (!this.suppressRemoteUpdates) {
         int â˜ƒ = this.remoteDataSlots.getInt(â˜ƒ);
         if (â˜ƒ != â˜ƒ) {
            this.remoteDataSlots.set(â˜ƒ, â˜ƒ);
            if (this.synchronizer != null) {
               this.synchronizer.sendDataChange(this, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   private void synchronizeCarriedToRemote() {
      if (!this.suppressRemoteUpdates) {
         if (!ItemStack.matches(this.getCarried(), this.remoteCarried)) {
            this.remoteCarried = this.getCarried().copy();
            if (this.synchronizer != null) {
               this.synchronizer.sendCarriedChange(this, this.remoteCarried);
            }
         }
      }
   }

   public void setRemoteSlot(int var1, ItemStack var2) {
      this.remoteSlots.set(â˜ƒ, â˜ƒ.copy());
   }

   public void setRemoteSlotNoCopy(int var1, ItemStack var2) {
      this.remoteSlots.set(â˜ƒ, â˜ƒ);
   }

   public void setRemoteCarried(ItemStack var1) {
      this.remoteCarried = â˜ƒ.copy();
   }

   public boolean clickMenuButton(Player var1, int var2) {
      return false;
   }

   public Slot getSlot(int var1) {
      return this.slots.get(â˜ƒ);
   }

   public ItemStack quickMoveStack(Player var1, int var2) {
      return this.slots.get(â˜ƒ).getItem();
   }

   public void clicked(int var1, int var2, ClickType var3, Player var4) {
      try {
         this.doClick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (Exception var8) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var8, "Container click");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Click info");
         â˜ƒx.setDetail("Menu Type", (CrashReportDetail<String>)(() -> this.menuType != null ? Registry.MENU.getKey(this.menuType).toString() : "<no type>"));
         â˜ƒx.setDetail("Menu Class", (CrashReportDetail<String>)(() -> this.getClass().getCanonicalName()));
         â˜ƒx.setDetail("Slot Count", this.slots.size());
         â˜ƒx.setDetail("Slot", â˜ƒ);
         â˜ƒx.setDetail("Button", â˜ƒ);
         â˜ƒx.setDetail("Type", â˜ƒ);
         throw new ReportedException(â˜ƒ);
      }
   }

   private void doClick(int var1, int var2, ClickType var3, Player var4) {
      Inventory â˜ƒ = â˜ƒ.getInventory();
      if (â˜ƒ == ClickType.QUICK_CRAFT) {
         int â˜ƒx = this.quickcraftStatus;
         this.quickcraftStatus = getQuickcraftHeader(â˜ƒ);
         if ((â˜ƒx != 1 || this.quickcraftStatus != 2) && â˜ƒx != this.quickcraftStatus) {
            this.resetQuickCraft();
         } else if (this.getCarried().isEmpty()) {
            this.resetQuickCraft();
         } else if (this.quickcraftStatus == 0) {
            this.quickcraftType = getQuickcraftType(â˜ƒ);
            if (isValidQuickcraftType(this.quickcraftType, â˜ƒ)) {
               this.quickcraftStatus = 1;
               this.quickcraftSlots.clear();
            } else {
               this.resetQuickCraft();
            }
         } else if (this.quickcraftStatus == 1) {
            Slot â˜ƒx = this.slots.get(â˜ƒ);
            ItemStack â˜ƒxx = this.getCarried();
            if (canItemQuickReplace(â˜ƒx, â˜ƒxx, true)
               && â˜ƒx.mayPlace(â˜ƒxx)
               && (this.quickcraftType == 2 || â˜ƒxx.getCount() > this.quickcraftSlots.size())
               && this.canDragTo(â˜ƒx)) {
               this.quickcraftSlots.add(â˜ƒx);
            }
         } else if (this.quickcraftStatus == 2) {
            if (!this.quickcraftSlots.isEmpty()) {
               if (this.quickcraftSlots.size() == 1) {
                  int â˜ƒx = ((Slot)this.quickcraftSlots.iterator().next()).index;
                  this.resetQuickCraft();
                  this.doClick(â˜ƒx, this.quickcraftType, ClickType.PICKUP, â˜ƒ);
                  return;
               }

               ItemStack â˜ƒx = this.getCarried().copy();
               int â˜ƒxx = this.getCarried().getCount();

               for(Slot â˜ƒxxx : this.quickcraftSlots) {
                  ItemStack â˜ƒxxxx = this.getCarried();
                  if (â˜ƒxxx != null
                     && canItemQuickReplace(â˜ƒxxx, â˜ƒxxxx, true)
                     && â˜ƒxxx.mayPlace(â˜ƒxxxx)
                     && (this.quickcraftType == 2 || â˜ƒxxxx.getCount() >= this.quickcraftSlots.size())
                     && this.canDragTo(â˜ƒxxx)) {
                     ItemStack â˜ƒxxxxx = â˜ƒx.copy();
                     int â˜ƒxxxxxx = â˜ƒxxx.hasItem() ? â˜ƒxxx.getItem().getCount() : 0;
                     getQuickCraftSlotCount(this.quickcraftSlots, this.quickcraftType, â˜ƒxxxxx, â˜ƒxxxxxx);
                     int â˜ƒxxxxxxx = Math.min(â˜ƒxxxxx.getMaxStackSize(), â˜ƒxxx.getMaxStackSize(â˜ƒxxxxx));
                     if (â˜ƒxxxxx.getCount() > â˜ƒxxxxxxx) {
                        â˜ƒxxxxx.setCount(â˜ƒxxxxxxx);
                     }

                     â˜ƒxx -= â˜ƒxxxxx.getCount() - â˜ƒxxxxxx;
                     â˜ƒxxx.set(â˜ƒxxxxx);
                  }
               }

               â˜ƒx.setCount(â˜ƒxx);
               this.setCarried(â˜ƒx);
            }

            this.resetQuickCraft();
         } else {
            this.resetQuickCraft();
         }
      } else if (this.quickcraftStatus != 0) {
         this.resetQuickCraft();
      } else if ((â˜ƒ == ClickType.PICKUP || â˜ƒ == ClickType.QUICK_MOVE) && (â˜ƒ == 0 || â˜ƒ == 1)) {
         ClickAction â˜ƒ = â˜ƒ == 0 ? ClickAction.PRIMARY : ClickAction.SECONDARY;
         if (â˜ƒ == -999) {
            if (!this.getCarried().isEmpty()) {
               if (â˜ƒ == ClickAction.PRIMARY) {
                  â˜ƒ.drop(this.getCarried(), true);
                  this.setCarried(ItemStack.EMPTY);
               } else {
                  â˜ƒ.drop(this.getCarried().split(1), true);
               }
            }
         } else if (â˜ƒ == ClickType.QUICK_MOVE) {
            if (â˜ƒ < 0) {
               return;
            }

            Slot â˜ƒ = this.slots.get(â˜ƒ);
            if (!â˜ƒ.mayPickup(â˜ƒ)) {
               return;
            }

            ItemStack â˜ƒ = this.quickMoveStack(â˜ƒ, â˜ƒ);

            while(!â˜ƒ.isEmpty() && ItemStack.isSame(â˜ƒ.getItem(), â˜ƒ)) {
               â˜ƒ = this.quickMoveStack(â˜ƒ, â˜ƒ);
            }
         } else {
            if (â˜ƒ < 0) {
               return;
            }

            Slot â˜ƒ = this.slots.get(â˜ƒ);
            ItemStack â˜ƒx = â˜ƒ.getItem();
            ItemStack â˜ƒxx = this.getCarried();
            â˜ƒ.updateTutorialInventoryAction(â˜ƒxx, â˜ƒ.getItem(), â˜ƒ);
            if (!â˜ƒxx.overrideStackedOnOther(â˜ƒ, â˜ƒ, â˜ƒ) && !â˜ƒx.overrideOtherStackedOnMe(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, this.createCarriedSlotAccess())) {
               if (â˜ƒx.isEmpty()) {
                  if (!â˜ƒxx.isEmpty()) {
                     int â˜ƒxxx = â˜ƒ == ClickAction.PRIMARY ? â˜ƒxx.getCount() : 1;
                     this.setCarried(â˜ƒ.safeInsert(â˜ƒxx, â˜ƒxxx));
                  }
               } else if (â˜ƒ.mayPickup(â˜ƒ)) {
                  if (â˜ƒxx.isEmpty()) {
                     int â˜ƒxxx = â˜ƒ == ClickAction.PRIMARY ? â˜ƒx.getCount() : (â˜ƒx.getCount() + 1) / 2;
                     Optional<ItemStack> â˜ƒxxxx = â˜ƒ.tryRemove(â˜ƒxxx, Integer.MAX_VALUE, â˜ƒ);
                     â˜ƒxxxx.ifPresent(var3x -> {
                        this.setCarried(var3x);
                        â˜ƒ.onTake(â˜ƒ, var3x);
                     });
                  } else if (â˜ƒ.mayPlace(â˜ƒxx)) {
                     if (ItemStack.isSameItemSameTags(â˜ƒx, â˜ƒxx)) {
                        int â˜ƒxxx = â˜ƒ == ClickAction.PRIMARY ? â˜ƒxx.getCount() : 1;
                        this.setCarried(â˜ƒ.safeInsert(â˜ƒxx, â˜ƒxxx));
                     } else if (â˜ƒxx.getCount() <= â˜ƒ.getMaxStackSize(â˜ƒxx)) {
                        â˜ƒ.set(â˜ƒxx);
                        this.setCarried(â˜ƒx);
                     }
                  } else if (ItemStack.isSameItemSameTags(â˜ƒx, â˜ƒxx)) {
                     Optional<ItemStack> â˜ƒxxx = â˜ƒ.tryRemove(â˜ƒx.getCount(), â˜ƒxx.getMaxStackSize() - â˜ƒxx.getCount(), â˜ƒ);
                     â˜ƒxxx.ifPresent(var3x -> {
                        â˜ƒ.grow(var3x.getCount());
                        â˜ƒ.onTake(â˜ƒ, var3x);
                     });
                  }
               }
            }

            â˜ƒ.setChanged();
         }
      } else if (â˜ƒ == ClickType.SWAP) {
         Slot â˜ƒ = this.slots.get(â˜ƒ);
         ItemStack â˜ƒx = â˜ƒ.getItem(â˜ƒ);
         ItemStack â˜ƒxx = â˜ƒ.getItem();
         if (!â˜ƒx.isEmpty() || !â˜ƒxx.isEmpty()) {
            if (â˜ƒx.isEmpty()) {
               if (â˜ƒ.mayPickup(â˜ƒ)) {
                  â˜ƒ.setItem(â˜ƒ, â˜ƒxx);
                  â˜ƒ.onSwapCraft(â˜ƒxx.getCount());
                  â˜ƒ.set(ItemStack.EMPTY);
                  â˜ƒ.onTake(â˜ƒ, â˜ƒxx);
               }
            } else if (â˜ƒxx.isEmpty()) {
               if (â˜ƒ.mayPlace(â˜ƒx)) {
                  int â˜ƒxxx = â˜ƒ.getMaxStackSize(â˜ƒx);
                  if (â˜ƒx.getCount() > â˜ƒxxx) {
                     â˜ƒ.set(â˜ƒx.split(â˜ƒxxx));
                  } else {
                     â˜ƒ.set(â˜ƒx);
                     â˜ƒ.setItem(â˜ƒ, ItemStack.EMPTY);
                  }
               }
            } else if (â˜ƒ.mayPickup(â˜ƒ) && â˜ƒ.mayPlace(â˜ƒx)) {
               int â˜ƒxxx = â˜ƒ.getMaxStackSize(â˜ƒx);
               if (â˜ƒx.getCount() > â˜ƒxxx) {
                  â˜ƒ.set(â˜ƒx.split(â˜ƒxxx));
                  â˜ƒ.onTake(â˜ƒ, â˜ƒxx);
                  if (!â˜ƒ.add(â˜ƒxx)) {
                     â˜ƒ.drop(â˜ƒxx, true);
                  }
               } else {
                  â˜ƒ.set(â˜ƒx);
                  â˜ƒ.setItem(â˜ƒ, â˜ƒxx);
                  â˜ƒ.onTake(â˜ƒ, â˜ƒxx);
               }
            }
         }
      } else if (â˜ƒ == ClickType.CLONE && â˜ƒ.getAbilities().instabuild && this.getCarried().isEmpty() && â˜ƒ >= 0) {
         Slot â˜ƒ = this.slots.get(â˜ƒ);
         if (â˜ƒ.hasItem()) {
            ItemStack â˜ƒx = â˜ƒ.getItem().copy();
            â˜ƒx.setCount(â˜ƒx.getMaxStackSize());
            this.setCarried(â˜ƒx);
         }
      } else if (â˜ƒ == ClickType.THROW && this.getCarried().isEmpty() && â˜ƒ >= 0) {
         Slot â˜ƒ = this.slots.get(â˜ƒ);
         int â˜ƒx = â˜ƒ == 0 ? 1 : â˜ƒ.getItem().getCount();
         ItemStack â˜ƒxx = â˜ƒ.safeTake(â˜ƒx, Integer.MAX_VALUE, â˜ƒ);
         â˜ƒ.drop(â˜ƒxx, true);
      } else if (â˜ƒ == ClickType.PICKUP_ALL && â˜ƒ >= 0) {
         Slot â˜ƒ = this.slots.get(â˜ƒ);
         ItemStack â˜ƒx = this.getCarried();
         if (!â˜ƒx.isEmpty() && (!â˜ƒ.hasItem() || !â˜ƒ.mayPickup(â˜ƒ))) {
            int â˜ƒxx = â˜ƒ == 0 ? 0 : this.slots.size() - 1;
            int â˜ƒxxx = â˜ƒ == 0 ? 1 : -1;

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 2; ++â˜ƒxxxx) {
               for(int â˜ƒxxxxx = â˜ƒxx; â˜ƒxxxxx >= 0 && â˜ƒxxxxx < this.slots.size() && â˜ƒx.getCount() < â˜ƒx.getMaxStackSize(); â˜ƒxxxxx += â˜ƒxxx) {
                  Slot â˜ƒxxxxxx = this.slots.get(â˜ƒxxxxx);
                  if (â˜ƒxxxxxx.hasItem()
                     && canItemQuickReplace(â˜ƒxxxxxx, â˜ƒx, true)
                     && â˜ƒxxxxxx.mayPickup(â˜ƒ)
                     && this.canTakeItemForPickAll(â˜ƒx, â˜ƒxxxxxx)) {
                     ItemStack â˜ƒxxxxxxx = â˜ƒxxxxxx.getItem();
                     if (â˜ƒxxxx != 0 || â˜ƒxxxxxxx.getCount() != â˜ƒxxxxxxx.getMaxStackSize()) {
                        ItemStack â˜ƒxxxxxxxx = â˜ƒxxxxxx.safeTake(â˜ƒxxxxxxx.getCount(), â˜ƒx.getMaxStackSize() - â˜ƒx.getCount(), â˜ƒ);
                        â˜ƒx.grow(â˜ƒxxxxxxxx.getCount());
                     }
                  }
               }
            }
         }
      }
   }

   private SlotAccess createCarriedSlotAccess() {
      return new SlotAccess() {
         @Override
         public ItemStack get() {
            return AbstractContainerMenu.this.getCarried();
         }

         @Override
         public boolean set(ItemStack var1) {
            AbstractContainerMenu.this.setCarried(â˜ƒ);
            return true;
         }
      };
   }

   public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
      return true;
   }

   public void removed(Player var1) {
      if (â˜ƒ instanceof ServerPlayer) {
         ItemStack â˜ƒ = this.getCarried();
         if (!â˜ƒ.isEmpty()) {
            if (â˜ƒ.isAlive() && !((ServerPlayer)â˜ƒ).hasDisconnected()) {
               â˜ƒ.getInventory().placeItemBackInInventory(â˜ƒ);
            } else {
               â˜ƒ.drop(â˜ƒ, false);
            }

            this.setCarried(ItemStack.EMPTY);
         }
      }
   }

   protected void clearContainer(Player var1, Container var2) {
      if (!â˜ƒ.isAlive() || â˜ƒ instanceof ServerPlayer && ((ServerPlayer)â˜ƒ).hasDisconnected()) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getContainerSize(); ++â˜ƒ) {
            â˜ƒ.drop(â˜ƒ.removeItemNoUpdate(â˜ƒ), false);
         }
      } else {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getContainerSize(); ++â˜ƒ) {
            Inventory â˜ƒx = â˜ƒ.getInventory();
            if (â˜ƒx.player instanceof ServerPlayer) {
               â˜ƒx.placeItemBackInInventory(â˜ƒ.removeItemNoUpdate(â˜ƒ));
            }
         }
      }
   }

   public void slotsChanged(Container var1) {
      this.broadcastChanges();
   }

   public void setItem(int var1, int var2, ItemStack var3) {
      this.getSlot(â˜ƒ).set(â˜ƒ);
      this.stateId = â˜ƒ;
   }

   public void initializeContents(int var1, List<ItemStack> var2, ItemStack var3) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         this.getSlot(â˜ƒ).set((ItemStack)â˜ƒ.get(â˜ƒ));
      }

      this.carried = â˜ƒ;
      this.stateId = â˜ƒ;
   }

   public void setData(int var1, int var2) {
      ((DataSlot)this.dataSlots.get(â˜ƒ)).set(â˜ƒ);
   }

   public abstract boolean stillValid(Player var1);

   protected boolean moveItemStackTo(ItemStack var1, int var2, int var3, boolean var4) {
      boolean â˜ƒ = false;
      int â˜ƒx = â˜ƒ;
      if (â˜ƒ) {
         â˜ƒx = â˜ƒ - 1;
      }

      if (â˜ƒ.isStackable()) {
         while(!â˜ƒ.isEmpty() && (â˜ƒ ? â˜ƒx >= â˜ƒ : â˜ƒx < â˜ƒ)) {
            Slot â˜ƒ = this.slots.get(â˜ƒx);
            ItemStack â˜ƒx = â˜ƒ.getItem();
            if (!â˜ƒx.isEmpty() && ItemStack.isSameItemSameTags(â˜ƒ, â˜ƒx)) {
               int â˜ƒxx = â˜ƒx.getCount() + â˜ƒ.getCount();
               if (â˜ƒxx <= â˜ƒ.getMaxStackSize()) {
                  â˜ƒ.setCount(0);
                  â˜ƒx.setCount(â˜ƒxx);
                  â˜ƒ.setChanged();
                  â˜ƒ = true;
               } else if (â˜ƒx.getCount() < â˜ƒ.getMaxStackSize()) {
                  â˜ƒ.shrink(â˜ƒ.getMaxStackSize() - â˜ƒx.getCount());
                  â˜ƒx.setCount(â˜ƒ.getMaxStackSize());
                  â˜ƒ.setChanged();
                  â˜ƒ = true;
               }
            }

            if (â˜ƒ) {
               --â˜ƒx;
            } else {
               ++â˜ƒx;
            }
         }
      }

      if (!â˜ƒ.isEmpty()) {
         if (â˜ƒ) {
            â˜ƒx = â˜ƒ - 1;
         } else {
            â˜ƒx = â˜ƒ;
         }

         while(â˜ƒ ? â˜ƒx >= â˜ƒ : â˜ƒx < â˜ƒ) {
            Slot â˜ƒ = this.slots.get(â˜ƒx);
            ItemStack â˜ƒx = â˜ƒ.getItem();
            if (â˜ƒx.isEmpty() && â˜ƒ.mayPlace(â˜ƒ)) {
               if (â˜ƒ.getCount() > â˜ƒ.getMaxStackSize()) {
                  â˜ƒ.set(â˜ƒ.split(â˜ƒ.getMaxStackSize()));
               } else {
                  â˜ƒ.set(â˜ƒ.split(â˜ƒ.getCount()));
               }

               â˜ƒ.setChanged();
               â˜ƒ = true;
               break;
            }

            if (â˜ƒ) {
               --â˜ƒx;
            } else {
               ++â˜ƒx;
            }
         }
      }

      return â˜ƒ;
   }

   public static int getQuickcraftType(int var0) {
      return â˜ƒ >> 2 & 3;
   }

   public static int getQuickcraftHeader(int var0) {
      return â˜ƒ & 3;
   }

   public static int getQuickcraftMask(int var0, int var1) {
      return â˜ƒ & 3 | (â˜ƒ & 3) << 2;
   }

   public static boolean isValidQuickcraftType(int var0, Player var1) {
      if (â˜ƒ == 0) {
         return true;
      } else if (â˜ƒ == 1) {
         return true;
      } else {
         return â˜ƒ == 2 && â˜ƒ.getAbilities().instabuild;
      }
   }

   protected void resetQuickCraft() {
      this.quickcraftStatus = 0;
      this.quickcraftSlots.clear();
   }

   public static boolean canItemQuickReplace(@Nullable Slot var0, ItemStack var1, boolean var2) {
      boolean â˜ƒ = â˜ƒ == null || !â˜ƒ.hasItem();
      if (!â˜ƒ && ItemStack.isSameItemSameTags(â˜ƒ, â˜ƒ.getItem())) {
         return â˜ƒ.getItem().getCount() + (â˜ƒ ? 0 : â˜ƒ.getCount()) <= â˜ƒ.getMaxStackSize();
      } else {
         return â˜ƒ;
      }
   }

   public static void getQuickCraftSlotCount(Set<Slot> var0, int var1, ItemStack var2, int var3) {
      switch(â˜ƒ) {
         case 0:
            â˜ƒ.setCount(Mth.floor((float)â˜ƒ.getCount() / (float)â˜ƒ.size()));
            break;
         case 1:
            â˜ƒ.setCount(1);
            break;
         case 2:
            â˜ƒ.setCount(â˜ƒ.getItem().getMaxStackSize());
      }

      â˜ƒ.grow(â˜ƒ);
   }

   public boolean canDragTo(Slot var1) {
      return true;
   }

   public static int getRedstoneSignalFromBlockEntity(@Nullable BlockEntity var0) {
      return â˜ƒ instanceof Container ? getRedstoneSignalFromContainer((Container)â˜ƒ) : 0;
   }

   public static int getRedstoneSignalFromContainer(@Nullable Container var0) {
      if (â˜ƒ == null) {
         return 0;
      } else {
         int â˜ƒ = 0;
         float â˜ƒx = 0.0F;

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
            ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
            if (!â˜ƒxxx.isEmpty()) {
               â˜ƒx += (float)â˜ƒxxx.getCount() / (float)Math.min(â˜ƒ.getMaxStackSize(), â˜ƒxxx.getMaxStackSize());
               ++â˜ƒ;
            }
         }

         â˜ƒx /= (float)â˜ƒ.getContainerSize();
         return Mth.floor(â˜ƒx * 14.0F) + (â˜ƒ > 0 ? 1 : 0);
      }
   }

   public void setCarried(ItemStack var1) {
      this.carried = â˜ƒ;
   }

   public ItemStack getCarried() {
      return this.carried;
   }

   public void suppressRemoteUpdates() {
      this.suppressRemoteUpdates = true;
   }

   public void resumeRemoteUpdates() {
      this.suppressRemoteUpdates = false;
   }

   public void transferState(AbstractContainerMenu var1) {
      Table<Container, Integer, Integer> â˜ƒ = HashBasedTable.create();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.slots.size(); ++â˜ƒx) {
         Slot â˜ƒxx = â˜ƒ.slots.get(â˜ƒx);
         â˜ƒ.put(â˜ƒxx.container, â˜ƒxx.getContainerSlot(), â˜ƒx);
      }

      for(int â˜ƒx = 0; â˜ƒx < this.slots.size(); ++â˜ƒx) {
         Slot â˜ƒxx = this.slots.get(â˜ƒx);
         Integer â˜ƒxxx = (Integer)â˜ƒ.get(â˜ƒxx.container, â˜ƒxx.getContainerSlot());
         if (â˜ƒxxx != null) {
            this.lastSlots.set(â˜ƒx, â˜ƒ.lastSlots.get(â˜ƒxxx));
            this.remoteSlots.set(â˜ƒx, â˜ƒ.remoteSlots.get(â˜ƒxxx));
         }
      }
   }

   public OptionalInt findSlot(Container var1, int var2) {
      for(int â˜ƒ = 0; â˜ƒ < this.slots.size(); ++â˜ƒ) {
         Slot â˜ƒx = this.slots.get(â˜ƒ);
         if (â˜ƒx.container == â˜ƒ && â˜ƒ == â˜ƒx.getContainerSlot()) {
            return OptionalInt.of(â˜ƒ);
         }
      }

      return OptionalInt.empty();
   }

   public int getStateId() {
      return this.stateId;
   }

   public int incrementStateId() {
      this.stateId = this.stateId + 1 & 32767;
      return this.stateId;
   }
}
