package net.minecraft.world.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class EnchantmentMenu extends AbstractContainerMenu {
   private final Container enchantSlots = new SimpleContainer(2) {
      @Override
      public void setChanged() {
         super.setChanged();
         EnchantmentMenu.this.slotsChanged(this);
      }
   };
   private final ContainerLevelAccess access;
   private final Random random = new Random();
   private final DataSlot enchantmentSeed = DataSlot.standalone();
   public final int[] costs = new int[3];
   public final int[] enchantClue = new int[]{-1, -1, -1};
   public final int[] levelClue = new int[]{-1, -1, -1};

   public EnchantmentMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public EnchantmentMenu(int var1, Inventory var2, ContainerLevelAccess var3) {
      super(MenuType.ENCHANTMENT, â˜ƒ);
      this.access = â˜ƒ;
      this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return true;
         }

         @Override
         public int getMaxStackSize() {
            return 1;
         }
      });
      this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.is(Items.LAPIS_LAZULI);
         }
      });

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 9 + 9, 8 + â˜ƒx * 18, 84 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 8 + â˜ƒ * 18, 142));
      }

      this.addDataSlot(DataSlot.shared(this.costs, 0));
      this.addDataSlot(DataSlot.shared(this.costs, 1));
      this.addDataSlot(DataSlot.shared(this.costs, 2));
      this.addDataSlot(this.enchantmentSeed).set(â˜ƒ.player.getEnchantmentSeed());
      this.addDataSlot(DataSlot.shared(this.enchantClue, 0));
      this.addDataSlot(DataSlot.shared(this.enchantClue, 1));
      this.addDataSlot(DataSlot.shared(this.enchantClue, 2));
      this.addDataSlot(DataSlot.shared(this.levelClue, 0));
      this.addDataSlot(DataSlot.shared(this.levelClue, 1));
      this.addDataSlot(DataSlot.shared(this.levelClue, 2));
   }

   @Override
   public void slotsChanged(Container var1) {
      if (â˜ƒ == this.enchantSlots) {
         ItemStack â˜ƒ = â˜ƒ.getItem(0);
         if (!â˜ƒ.isEmpty() && â˜ƒ.isEnchantable()) {
            this.access.execute((var2x, var3x) -> {
               int â˜ƒ = 0;

               for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
                  for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
                     if ((â˜ƒx != 0 || â˜ƒxx != 0) && var2x.isEmptyBlock(var3x.offset(â˜ƒxx, 0, â˜ƒx)) && var2x.isEmptyBlock(var3x.offset(â˜ƒxx, 1, â˜ƒx))) {
                        if (var2x.getBlockState(var3x.offset(â˜ƒxx * 2, 0, â˜ƒx * 2)).is(Blocks.BOOKSHELF)) {
                           ++â˜ƒ;
                        }

                        if (var2x.getBlockState(var3x.offset(â˜ƒxx * 2, 1, â˜ƒx * 2)).is(Blocks.BOOKSHELF)) {
                           ++â˜ƒ;
                        }

                        if (â˜ƒxx != 0 && â˜ƒx != 0) {
                           if (var2x.getBlockState(var3x.offset(â˜ƒxx * 2, 0, â˜ƒx)).is(Blocks.BOOKSHELF)) {
                              ++â˜ƒ;
                           }

                           if (var2x.getBlockState(var3x.offset(â˜ƒxx * 2, 1, â˜ƒx)).is(Blocks.BOOKSHELF)) {
                              ++â˜ƒ;
                           }

                           if (var2x.getBlockState(var3x.offset(â˜ƒxx, 0, â˜ƒx * 2)).is(Blocks.BOOKSHELF)) {
                              ++â˜ƒ;
                           }

                           if (var2x.getBlockState(var3x.offset(â˜ƒxx, 1, â˜ƒx * 2)).is(Blocks.BOOKSHELF)) {
                              ++â˜ƒ;
                           }
                        }
                     }
                  }
               }

               this.random.setSeed((long)this.enchantmentSeed.get());

               for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
                  this.costs[â˜ƒx] = EnchantmentHelper.getEnchantmentCost(this.random, â˜ƒx, â˜ƒ, â˜ƒ);
                  this.enchantClue[â˜ƒx] = -1;
                  this.levelClue[â˜ƒx] = -1;
                  if (this.costs[â˜ƒx] < â˜ƒx + 1) {
                     this.costs[â˜ƒx] = 0;
                  }
               }

               for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
                  if (this.costs[â˜ƒx] > 0) {
                     List<EnchantmentInstance> â˜ƒxx = this.getEnchantmentList(â˜ƒ, â˜ƒx, this.costs[â˜ƒx]);
                     if (â˜ƒxx != null && !â˜ƒxx.isEmpty()) {
                        EnchantmentInstance â˜ƒxxx = (EnchantmentInstance)â˜ƒxx.get(this.random.nextInt(â˜ƒxx.size()));
                        this.enchantClue[â˜ƒx] = Registry.ENCHANTMENT.getId(â˜ƒxxx.enchantment);
                        this.levelClue[â˜ƒx] = â˜ƒxxx.level;
                     }
                  }
               }

               this.broadcastChanges();
            });
         } else {
            for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
               this.costs[â˜ƒ] = 0;
               this.enchantClue[â˜ƒ] = -1;
               this.levelClue[â˜ƒ] = -1;
            }
         }
      }
   }

   @Override
   public boolean clickMenuButton(Player var1, int var2) {
      ItemStack â˜ƒ = this.enchantSlots.getItem(0);
      ItemStack â˜ƒx = this.enchantSlots.getItem(1);
      int â˜ƒxx = â˜ƒ + 1;
      if ((â˜ƒx.isEmpty() || â˜ƒx.getCount() < â˜ƒxx) && !â˜ƒ.getAbilities().instabuild) {
         return false;
      } else if (this.costs[â˜ƒ] <= 0
         || â˜ƒ.isEmpty()
         || (â˜ƒ.experienceLevel < â˜ƒxx || â˜ƒ.experienceLevel < this.costs[â˜ƒ]) && !â˜ƒ.getAbilities().instabuild) {
         return false;
      } else {
         this.access.execute((var6, var7) -> {
            ItemStack â˜ƒ = â˜ƒ;
            List<EnchantmentInstance> â˜ƒx = this.getEnchantmentList(â˜ƒ, â˜ƒ, this.costs[â˜ƒ]);
            if (!â˜ƒx.isEmpty()) {
               â˜ƒ.onEnchantmentPerformed(â˜ƒ, â˜ƒ);
               boolean â˜ƒxx = â˜ƒ.is(Items.BOOK);
               if (â˜ƒxx) {
                  â˜ƒ = new ItemStack(Items.ENCHANTED_BOOK);
                  CompoundTag â˜ƒxxx = â˜ƒ.getTag();
                  if (â˜ƒxxx != null) {
                     â˜ƒ.setTag(â˜ƒxxx.copy());
                  }

                  this.enchantSlots.setItem(0, â˜ƒ);
               }

               for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
                  EnchantmentInstance â˜ƒxxx = (EnchantmentInstance)â˜ƒx.get(â˜ƒxx);
                  if (â˜ƒxx) {
                     EnchantedBookItem.addEnchantment(â˜ƒ, â˜ƒxxx);
                  } else {
                     â˜ƒ.enchant(â˜ƒxxx.enchantment, â˜ƒxxx.level);
                  }
               }

               if (!â˜ƒ.getAbilities().instabuild) {
                  â˜ƒ.shrink(â˜ƒ);
                  if (â˜ƒ.isEmpty()) {
                     this.enchantSlots.setItem(1, ItemStack.EMPTY);
                  }
               }

               â˜ƒ.awardStat(Stats.ENCHANT_ITEM);
               if (â˜ƒ instanceof ServerPlayer) {
                  CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)â˜ƒ, â˜ƒ, â˜ƒ);
               }

               this.enchantSlots.setChanged();
               this.enchantmentSeed.set(â˜ƒ.getEnchantmentSeed());
               this.slotsChanged(this.enchantSlots);
               var6.playSound(null, var7, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, var6.random.nextFloat() * 0.1F + 0.9F);
            }
         });
         return true;
      }
   }

   private List<EnchantmentInstance> getEnchantmentList(ItemStack var1, int var2, int var3) {
      this.random.setSeed((long)(this.enchantmentSeed.get() + â˜ƒ));
      List<EnchantmentInstance> â˜ƒ = EnchantmentHelper.selectEnchantment(this.random, â˜ƒ, â˜ƒ, false);
      if (â˜ƒ.is(Items.BOOK) && â˜ƒ.size() > 1) {
         â˜ƒ.remove(this.random.nextInt(â˜ƒ.size()));
      }

      return â˜ƒ;
   }

   public int getGoldCount() {
      ItemStack â˜ƒ = this.enchantSlots.getItem(1);
      return â˜ƒ.isEmpty() ? 0 : â˜ƒ.getCount();
   }

   public int getEnchantmentSeed() {
      return this.enchantmentSeed.get();
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.access.execute((var2, var3) -> this.clearContainer(â˜ƒ, this.enchantSlots));
   }

   @Override
   public boolean stillValid(Player var1) {
      return stillValid(this.access, â˜ƒ, Blocks.ENCHANTING_TABLE);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ == 0) {
            if (!this.moveItemStackTo(â˜ƒxx, 2, 38, true)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ == 1) {
            if (!this.moveItemStackTo(â˜ƒxx, 2, 38, true)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒxx.is(Items.LAPIS_LAZULI)) {
            if (!this.moveItemStackTo(â˜ƒxx, 1, 2, true)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(â˜ƒxx)) {
               return ItemStack.EMPTY;
            }

            ItemStack â˜ƒxx = â˜ƒxx.copy();
            â˜ƒxx.setCount(1);
            â˜ƒxx.shrink(1);
            this.slots.get(0).set(â˜ƒxx);
         }

         if (â˜ƒxx.isEmpty()) {
            â˜ƒx.set(ItemStack.EMPTY);
         } else {
            â˜ƒx.setChanged();
         }

         if (â˜ƒxx.getCount() == â˜ƒ.getCount()) {
            return ItemStack.EMPTY;
         }

         â˜ƒx.onTake(â˜ƒ, â˜ƒxx);
      }

      return â˜ƒ;
   }
}
