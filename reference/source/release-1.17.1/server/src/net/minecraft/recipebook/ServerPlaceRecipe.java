package net.minecraft.recipebook;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlaceRecipe<C extends Container> implements PlaceRecipe<Integer> {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected final StackedContents stackedContents = new StackedContents();
   protected Inventory inventory;
   protected RecipeBookMenu<C> menu;

   public ServerPlaceRecipe(RecipeBookMenu<C> var1) {
      this.menu = â˜ƒ;
   }

   public void recipeClicked(ServerPlayer var1, @Nullable Recipe<C> var2, boolean var3) {
      if (â˜ƒ != null && â˜ƒ.getRecipeBook().contains(â˜ƒ)) {
         this.inventory = â˜ƒ.getInventory();
         if (this.testClearGrid() || â˜ƒ.isCreative()) {
            this.stackedContents.clear();
            â˜ƒ.getInventory().fillStackedContents(this.stackedContents);
            this.menu.fillCraftSlotsStackedContents(this.stackedContents);
            if (this.stackedContents.canCraft(â˜ƒ, null)) {
               this.handleRecipeClicked(â˜ƒ, â˜ƒ);
            } else {
               this.clearGrid(true);
               â˜ƒ.connection.send(new ClientboundPlaceGhostRecipePacket(â˜ƒ.containerMenu.containerId, â˜ƒ));
            }

            â˜ƒ.getInventory().setChanged();
         }
      }
   }

   protected void clearGrid(boolean var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.menu.getSize(); ++â˜ƒ) {
         if (this.menu.shouldMoveToInventory(â˜ƒ)) {
            ItemStack â˜ƒx = this.menu.getSlot(â˜ƒ).getItem().copy();
            this.inventory.placeItemBackInInventory(â˜ƒx, false);
            this.menu.getSlot(â˜ƒ).set(â˜ƒx);
         }
      }

      this.menu.clearCraftingContent();
   }

   protected void handleRecipeClicked(Recipe<C> var1, boolean var2) {
      boolean â˜ƒ = this.menu.recipeMatches(â˜ƒ);
      int â˜ƒx = this.stackedContents.getBiggestCraftableStack(â˜ƒ, null);
      if (â˜ƒ) {
         for(int â˜ƒxx = 0; â˜ƒxx < this.menu.getGridHeight() * this.menu.getGridWidth() + 1; ++â˜ƒxx) {
            if (â˜ƒxx != this.menu.getResultSlotIndex()) {
               ItemStack â˜ƒxxx = this.menu.getSlot(â˜ƒxx).getItem();
               if (!â˜ƒxxx.isEmpty() && Math.min(â˜ƒx, â˜ƒxxx.getMaxStackSize()) < â˜ƒxxx.getCount() + 1) {
                  return;
               }
            }
         }
      }

      int â˜ƒ = this.getStackSize(â˜ƒ, â˜ƒx, â˜ƒ);
      IntList â˜ƒx = new IntArrayList();
      if (this.stackedContents.canCraft(â˜ƒ, â˜ƒx, â˜ƒ)) {
         int â˜ƒxx = â˜ƒ;

         for(int â˜ƒxxx : â˜ƒx) {
            int â˜ƒxxxx = StackedContents.fromStackingIndex(â˜ƒxxx).getMaxStackSize();
            if (â˜ƒxxxx < â˜ƒxx) {
               â˜ƒxx = â˜ƒxxxx;
            }
         }

         if (this.stackedContents.canCraft(â˜ƒ, â˜ƒx, â˜ƒxx)) {
            this.clearGrid(false);
            this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), â˜ƒ, â˜ƒx.iterator(), â˜ƒxx);
         }
      }
   }

   @Override
   public void addItemToSlot(Iterator<Integer> var1, int var2, int var3, int var4, int var5) {
      Slot â˜ƒ = this.menu.getSlot(â˜ƒ);
      ItemStack â˜ƒx = StackedContents.fromStackingIndex(â˜ƒ.next());
      if (!â˜ƒx.isEmpty()) {
         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
            this.moveItemToGrid(â˜ƒ, â˜ƒx);
         }
      }
   }

   protected int getStackSize(boolean var1, int var2, boolean var3) {
      int â˜ƒ = 1;
      if (â˜ƒ) {
         â˜ƒ = â˜ƒ;
      } else if (â˜ƒ) {
         â˜ƒ = 64;

         for(int â˜ƒ = 0; â˜ƒ < this.menu.getGridWidth() * this.menu.getGridHeight() + 1; ++â˜ƒ) {
            if (â˜ƒ != this.menu.getResultSlotIndex()) {
               ItemStack â˜ƒx = this.menu.getSlot(â˜ƒ).getItem();
               if (!â˜ƒx.isEmpty() && â˜ƒ > â˜ƒx.getCount()) {
                  â˜ƒ = â˜ƒx.getCount();
               }
            }
         }

         if (â˜ƒ < 64) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   protected void moveItemToGrid(Slot var1, ItemStack var2) {
      int â˜ƒ = this.inventory.findSlotMatchingUnusedItem(â˜ƒ);
      if (â˜ƒ != -1) {
         ItemStack â˜ƒx = this.inventory.getItem(â˜ƒ).copy();
         if (!â˜ƒx.isEmpty()) {
            if (â˜ƒx.getCount() > 1) {
               this.inventory.removeItem(â˜ƒ, 1);
            } else {
               this.inventory.removeItemNoUpdate(â˜ƒ);
            }

            â˜ƒx.setCount(1);
            if (â˜ƒ.getItem().isEmpty()) {
               â˜ƒ.set(â˜ƒx);
            } else {
               â˜ƒ.getItem().grow(1);
            }
         }
      }
   }

   private boolean testClearGrid() {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayList();
      int â˜ƒx = this.getAmountOfFreeSlotsInInventory();

      for(int â˜ƒxx = 0; â˜ƒxx < this.menu.getGridWidth() * this.menu.getGridHeight() + 1; ++â˜ƒxx) {
         if (â˜ƒxx != this.menu.getResultSlotIndex()) {
            ItemStack â˜ƒxxx = this.menu.getSlot(â˜ƒxx).getItem().copy();
            if (!â˜ƒxxx.isEmpty()) {
               int â˜ƒxxxx = this.inventory.getSlotWithRemainingSpace(â˜ƒxxx);
               if (â˜ƒxxxx == -1 && â˜ƒ.size() <= â˜ƒx) {
                  for(ItemStack â˜ƒxxxxx : â˜ƒ) {
                     if (â˜ƒxxxxx.sameItem(â˜ƒxxx)
                        && â˜ƒxxxxx.getCount() != â˜ƒxxxxx.getMaxStackSize()
                        && â˜ƒxxxxx.getCount() + â˜ƒxxx.getCount() <= â˜ƒxxxxx.getMaxStackSize()) {
                        â˜ƒxxxxx.grow(â˜ƒxxx.getCount());
                        â˜ƒxxx.setCount(0);
                        break;
                     }
                  }

                  if (!â˜ƒxxx.isEmpty()) {
                     if (â˜ƒ.size() >= â˜ƒx) {
                        return false;
                     }

                     â˜ƒ.add(â˜ƒxxx);
                  }
               } else if (â˜ƒxxxx == -1) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private int getAmountOfFreeSlotsInInventory() {
      int â˜ƒ = 0;

      for(ItemStack â˜ƒx : this.inventory.items) {
         if (â˜ƒx.isEmpty()) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }
}
