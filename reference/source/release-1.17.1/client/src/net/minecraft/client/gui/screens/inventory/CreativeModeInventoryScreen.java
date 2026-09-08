package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.HotbarManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class CreativeModeInventoryScreen extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
   private static final ResourceLocation CREATIVE_TABS_LOCATION = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   private static final String GUI_CREATIVE_TAB_PREFIX = "textures/gui/container/creative_inventory/tab_";
   private static final String CUSTOM_SLOT_LOCK = "CustomCreativeLock";
   private static final int NUM_ROWS = 5;
   private static final int NUM_COLS = 9;
   private static final int TAB_WIDTH = 28;
   private static final int TAB_HEIGHT = 32;
   private static final int SCROLLER_WIDTH = 12;
   private static final int SCROLLER_HEIGHT = 15;
   static final SimpleContainer CONTAINER = new SimpleContainer(45);
   private static final Component TRASH_SLOT_TOOLTIP = new TranslatableComponent("inventory.binSlot");
   private static final int TEXT_COLOR = 16777215;
   private static int selectedTab = CreativeModeTab.TAB_BUILDING_BLOCKS.getId();
   private float scrollOffs;
   private boolean scrolling;
   private EditBox searchBox;
   @Nullable
   private List<Slot> originalSlots;
   @Nullable
   private Slot destroyItemSlot;
   private CreativeInventoryListener listener;
   private boolean ignoreTextInput;
   private boolean hasClickedOutside;
   private final Map<ResourceLocation, Tag<Item>> visibleTags = Maps.<ResourceLocation, Tag<Item>>newTreeMap();

   public CreativeModeInventoryScreen(Player var1) {
      super(new CreativeModeInventoryScreen.ItemPickerMenu(â˜ƒ), â˜ƒ.getInventory(), TextComponent.EMPTY);
      â˜ƒ.containerMenu = this.menu;
      this.passEvents = true;
      this.imageHeight = 136;
      this.imageWidth = 195;
   }

   @Override
   public void containerTick() {
      super.containerTick();
      if (!this.minecraft.gameMode.hasInfiniteItems()) {
         this.minecraft.setScreen(new InventoryScreen(this.minecraft.player));
      } else if (this.searchBox != null) {
         this.searchBox.tick();
      }
   }

   @Override
   protected void slotClicked(@Nullable Slot var1, int var2, int var3, ClickType var4) {
      if (this.isCreativeSlot(â˜ƒ)) {
         this.searchBox.moveCursorToEnd();
         this.searchBox.setHighlightPos(0);
      }

      boolean â˜ƒ = â˜ƒ == ClickType.QUICK_MOVE;
      â˜ƒ = â˜ƒ == -999 && â˜ƒ == ClickType.PICKUP ? ClickType.THROW : â˜ƒ;
      if (â˜ƒ == null && selectedTab != CreativeModeTab.TAB_INVENTORY.getId() && â˜ƒ != ClickType.QUICK_CRAFT) {
         if (!this.menu.getCarried().isEmpty() && this.hasClickedOutside) {
            if (â˜ƒ == 0) {
               this.minecraft.player.drop(this.menu.getCarried(), true);
               this.minecraft.gameMode.handleCreativeModeItemDrop(this.menu.getCarried());
               this.menu.setCarried(ItemStack.EMPTY);
            }

            if (â˜ƒ == 1) {
               ItemStack â˜ƒx = this.menu.getCarried().split(1);
               this.minecraft.player.drop(â˜ƒx, true);
               this.minecraft.gameMode.handleCreativeModeItemDrop(â˜ƒx);
            }
         }
      } else {
         if (â˜ƒ != null && !â˜ƒ.mayPickup(this.minecraft.player)) {
            return;
         }

         if (â˜ƒ == this.destroyItemSlot && â˜ƒ) {
            for(int â˜ƒ = 0; â˜ƒ < this.minecraft.player.inventoryMenu.getItems().size(); ++â˜ƒ) {
               this.minecraft.gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, â˜ƒ);
            }
         } else if (selectedTab == CreativeModeTab.TAB_INVENTORY.getId()) {
            if (â˜ƒ == this.destroyItemSlot) {
               this.menu.setCarried(ItemStack.EMPTY);
            } else if (â˜ƒ == ClickType.THROW && â˜ƒ != null && â˜ƒ.hasItem()) {
               ItemStack â˜ƒ = â˜ƒ.remove(â˜ƒ == 0 ? 1 : â˜ƒ.getItem().getMaxStackSize());
               ItemStack â˜ƒx = â˜ƒ.getItem();
               this.minecraft.player.drop(â˜ƒ, true);
               this.minecraft.gameMode.handleCreativeModeItemDrop(â˜ƒ);
               this.minecraft.gameMode.handleCreativeModeItemAdd(â˜ƒx, ((CreativeModeInventoryScreen.SlotWrapper)â˜ƒ).target.index);
            } else if (â˜ƒ == ClickType.THROW && !this.menu.getCarried().isEmpty()) {
               this.minecraft.player.drop(this.menu.getCarried(), true);
               this.minecraft.gameMode.handleCreativeModeItemDrop(this.menu.getCarried());
               this.menu.setCarried(ItemStack.EMPTY);
            } else {
               this.minecraft
                  .player
                  .inventoryMenu
                  .clicked(â˜ƒ == null ? â˜ƒ : ((CreativeModeInventoryScreen.SlotWrapper)â˜ƒ).target.index, â˜ƒ, â˜ƒ, this.minecraft.player);
               this.minecraft.player.inventoryMenu.broadcastChanges();
            }
         } else if (â˜ƒ != ClickType.QUICK_CRAFT && â˜ƒ.container == CONTAINER) {
            ItemStack â˜ƒ = this.menu.getCarried();
            ItemStack â˜ƒx = â˜ƒ.getItem();
            if (â˜ƒ == ClickType.SWAP) {
               if (!â˜ƒx.isEmpty()) {
                  ItemStack â˜ƒxx = â˜ƒx.copy();
                  â˜ƒxx.setCount(â˜ƒxx.getMaxStackSize());
                  this.minecraft.player.getInventory().setItem(â˜ƒ, â˜ƒxx);
                  this.minecraft.player.inventoryMenu.broadcastChanges();
               }

               return;
            }

            if (â˜ƒ == ClickType.CLONE) {
               if (this.menu.getCarried().isEmpty() && â˜ƒ.hasItem()) {
                  ItemStack â˜ƒ = â˜ƒ.getItem().copy();
                  â˜ƒ.setCount(â˜ƒ.getMaxStackSize());
                  this.menu.setCarried(â˜ƒ);
               }

               return;
            }

            if (â˜ƒ == ClickType.THROW) {
               if (!â˜ƒx.isEmpty()) {
                  ItemStack â˜ƒ = â˜ƒx.copy();
                  â˜ƒ.setCount(â˜ƒ == 0 ? 1 : â˜ƒ.getMaxStackSize());
                  this.minecraft.player.drop(â˜ƒ, true);
                  this.minecraft.gameMode.handleCreativeModeItemDrop(â˜ƒ);
               }

               return;
            }

            if (!â˜ƒ.isEmpty() && !â˜ƒx.isEmpty() && â˜ƒ.sameItem(â˜ƒx) && ItemStack.tagMatches(â˜ƒ, â˜ƒx)) {
               if (â˜ƒ == 0) {
                  if (â˜ƒ) {
                     â˜ƒ.setCount(â˜ƒ.getMaxStackSize());
                  } else if (â˜ƒ.getCount() < â˜ƒ.getMaxStackSize()) {
                     â˜ƒ.grow(1);
                  }
               } else {
                  â˜ƒ.shrink(1);
               }
            } else if (!â˜ƒx.isEmpty() && â˜ƒ.isEmpty()) {
               this.menu.setCarried(â˜ƒx.copy());
               â˜ƒ = this.menu.getCarried();
               if (â˜ƒ) {
                  â˜ƒ.setCount(â˜ƒ.getMaxStackSize());
               }
            } else if (â˜ƒ == 0) {
               this.menu.setCarried(ItemStack.EMPTY);
            } else {
               this.menu.getCarried().shrink(1);
            }
         } else if (this.menu != null) {
            ItemStack â˜ƒ = â˜ƒ == null ? ItemStack.EMPTY : this.menu.getSlot(â˜ƒ.index).getItem();
            this.menu.clicked(â˜ƒ == null ? â˜ƒ : â˜ƒ.index, â˜ƒ, â˜ƒ, this.minecraft.player);
            if (AbstractContainerMenu.getQuickcraftHeader(â˜ƒ) == 2) {
               for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
                  this.minecraft.gameMode.handleCreativeModeItemAdd(this.menu.getSlot(45 + â˜ƒx).getItem(), 36 + â˜ƒx);
               }
            } else if (â˜ƒ != null) {
               ItemStack â˜ƒ = this.menu.getSlot(â˜ƒ.index).getItem();
               this.minecraft.gameMode.handleCreativeModeItemAdd(â˜ƒ, â˜ƒ.index - this.menu.slots.size() + 9 + 36);
               int â˜ƒx = 45 + â˜ƒ;
               if (â˜ƒ == ClickType.SWAP) {
                  this.minecraft.gameMode.handleCreativeModeItemAdd(â˜ƒ, â˜ƒx - this.menu.slots.size() + 9 + 36);
               } else if (â˜ƒ == ClickType.THROW && !â˜ƒ.isEmpty()) {
                  ItemStack â˜ƒ = â˜ƒ.copy();
                  â˜ƒ.setCount(â˜ƒ == 0 ? 1 : â˜ƒ.getMaxStackSize());
                  this.minecraft.player.drop(â˜ƒ, true);
                  this.minecraft.gameMode.handleCreativeModeItemDrop(â˜ƒ);
               }

               this.minecraft.player.inventoryMenu.broadcastChanges();
            }
         }
      }
   }

   private boolean isCreativeSlot(@Nullable Slot var1) {
      return â˜ƒ != null && â˜ƒ.container == CONTAINER;
   }

   @Override
   protected void checkEffectRendering() {
      int â˜ƒ = this.leftPos;
      super.checkEffectRendering();
      if (this.searchBox != null && this.leftPos != â˜ƒ) {
         this.searchBox.setX(this.leftPos + 82);
      }
   }

   @Override
   protected void init() {
      if (this.minecraft.gameMode.hasInfiniteItems()) {
         super.init();
         this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
         this.searchBox = new EditBox(this.font, this.leftPos + 82, this.topPos + 6, 80, 9, new TranslatableComponent("itemGroup.search"));
         this.searchBox.setMaxLength(50);
         this.searchBox.setBordered(false);
         this.searchBox.setVisible(false);
         this.searchBox.setTextColor(16777215);
         this.addWidget(this.searchBox);
         int â˜ƒ = selectedTab;
         selectedTab = -1;
         this.selectTab(CreativeModeTab.TABS[â˜ƒ]);
         this.minecraft.player.inventoryMenu.removeSlotListener(this.listener);
         this.listener = new CreativeInventoryListener(this.minecraft);
         this.minecraft.player.inventoryMenu.addSlotListener(this.listener);
      } else {
         this.minecraft.setScreen(new InventoryScreen(this.minecraft.player));
      }
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.searchBox.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.searchBox.setValue(â˜ƒ);
      if (!this.searchBox.getValue().isEmpty()) {
         this.refreshSearchResults();
      }
   }

   @Override
   public void removed() {
      super.removed();
      if (this.minecraft.player != null && this.minecraft.player.getInventory() != null) {
         this.minecraft.player.inventoryMenu.removeSlotListener(this.listener);
      }

      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (this.ignoreTextInput) {
         return false;
      } else if (selectedTab != CreativeModeTab.TAB_SEARCH.getId()) {
         return false;
      } else {
         String â˜ƒ = this.searchBox.getValue();
         if (this.searchBox.charTyped(â˜ƒ, â˜ƒ)) {
            if (!Objects.equals(â˜ƒ, this.searchBox.getValue())) {
               this.refreshSearchResults();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      this.ignoreTextInput = false;
      if (selectedTab != CreativeModeTab.TAB_SEARCH.getId()) {
         if (this.minecraft.options.keyChat.matches(â˜ƒ, â˜ƒ)) {
            this.ignoreTextInput = true;
            this.selectTab(CreativeModeTab.TAB_SEARCH);
            return true;
         } else {
            return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      } else {
         boolean â˜ƒ = !this.isCreativeSlot(this.hoveredSlot) || this.hoveredSlot.hasItem();
         boolean â˜ƒx = InputConstants.getKey(â˜ƒ, â˜ƒ).getNumericKeyValue().isPresent();
         if (â˜ƒ && â˜ƒx && this.checkHotbarKeyPressed(â˜ƒ, â˜ƒ)) {
            this.ignoreTextInput = true;
            return true;
         } else {
            String â˜ƒ = this.searchBox.getValue();
            if (this.searchBox.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
               if (!Objects.equals(â˜ƒ, this.searchBox.getValue())) {
                  this.refreshSearchResults();
               }

               return true;
            } else {
               return this.searchBox.isFocused() && this.searchBox.isVisible() && â˜ƒ != 256 ? true : super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   @Override
   public boolean keyReleased(int var1, int var2, int var3) {
      this.ignoreTextInput = false;
      return super.keyReleased(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void refreshSearchResults() {
      this.menu.items.clear();
      this.visibleTags.clear();
      String â˜ƒ = this.searchBox.getValue();
      if (â˜ƒ.isEmpty()) {
         for(Item â˜ƒx : Registry.ITEM) {
            â˜ƒx.fillItemCategory(CreativeModeTab.TAB_SEARCH, this.menu.items);
         }
      } else {
         SearchTree<ItemStack> â˜ƒ;
         if (â˜ƒ.startsWith("#")) {
            â˜ƒ = â˜ƒ.substring(1);
            â˜ƒ = this.minecraft.getSearchTree(SearchRegistry.CREATIVE_TAGS);
            this.updateVisibleTags(â˜ƒ);
         } else {
            â˜ƒ = this.minecraft.getSearchTree(SearchRegistry.CREATIVE_NAMES);
         }

         this.menu.items.addAll(â˜ƒ.search(â˜ƒ.toLowerCase(Locale.ROOT)));
      }

      this.scrollOffs = 0.0F;
      this.menu.scrollTo(0.0F);
   }

   private void updateVisibleTags(String var1) {
      int â˜ƒx = â˜ƒ.indexOf(58);
      Predicate<ResourceLocation> â˜ƒ;
      if (â˜ƒx == -1) {
         â˜ƒ = var1x -> var1x.getPath().contains(â˜ƒ);
      } else {
         String â˜ƒ = â˜ƒ.substring(0, â˜ƒx).trim();
         String â˜ƒx = â˜ƒ.substring(â˜ƒx + 1).trim();
         â˜ƒ = var2x -> var2x.getNamespace().contains(â˜ƒ) && var2x.getPath().contains(â˜ƒ);
      }

      TagCollection<Item> â˜ƒ = ItemTags.getAllTags();
      â˜ƒ.getAvailableTags().stream().filter(â˜ƒ).forEach(var2x -> this.visibleTags.put(var2x, â˜ƒ.getTag(var2x)));
   }

   @Override
   protected void renderLabels(PoseStack var1, int var2, int var3) {
      CreativeModeTab â˜ƒ = CreativeModeTab.TABS[selectedTab];
      if (â˜ƒ.showTitle()) {
         RenderSystem.disableBlend();
         this.font.draw(â˜ƒ, â˜ƒ.getDisplayName(), 8.0F, 6.0F, 4210752);
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (â˜ƒ == 0) {
         double â˜ƒ = â˜ƒ - (double)this.leftPos;
         double â˜ƒx = â˜ƒ - (double)this.topPos;

         for(CreativeModeTab â˜ƒxx : CreativeModeTab.TABS) {
            if (this.checkTabClicked(â˜ƒxx, â˜ƒ, â˜ƒx)) {
               return true;
            }
         }

         if (selectedTab != CreativeModeTab.TAB_INVENTORY.getId() && this.insideScrollbar(â˜ƒ, â˜ƒ)) {
            this.scrolling = this.canScroll();
            return true;
         }
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (â˜ƒ == 0) {
         double â˜ƒ = â˜ƒ - (double)this.leftPos;
         double â˜ƒx = â˜ƒ - (double)this.topPos;
         this.scrolling = false;

         for(CreativeModeTab â˜ƒxx : CreativeModeTab.TABS) {
            if (this.checkTabClicked(â˜ƒxx, â˜ƒ, â˜ƒx)) {
               this.selectTab(â˜ƒxx);
               return true;
            }
         }
      }

      return super.mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private boolean canScroll() {
      return selectedTab != CreativeModeTab.TAB_INVENTORY.getId() && CreativeModeTab.TABS[selectedTab].canScroll() && this.menu.canScroll();
   }

   private void selectTab(CreativeModeTab var1) {
      int â˜ƒ = selectedTab;
      selectedTab = â˜ƒ.getId();
      this.quickCraftSlots.clear();
      this.menu.items.clear();
      if (â˜ƒ == CreativeModeTab.TAB_HOTBAR) {
         HotbarManager â˜ƒx = this.minecraft.getHotbarManager();

         for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
            Hotbar â˜ƒxxx = â˜ƒx.get(â˜ƒxx);
            if (â˜ƒxxx.isEmpty()) {
               for(int â˜ƒxxxx = 0; â˜ƒxxxx < 9; ++â˜ƒxxxx) {
                  if (â˜ƒxxxx == â˜ƒxx) {
                     ItemStack â˜ƒxxxxx = new ItemStack(Items.PAPER);
                     â˜ƒxxxxx.getOrCreateTagElement("CustomCreativeLock");
                     Component â˜ƒxxxxxx = this.minecraft.options.keyHotbarSlots[â˜ƒxx].getTranslatedKeyMessage();
                     Component â˜ƒxxxxxxx = this.minecraft.options.keySaveHotbarActivator.getTranslatedKeyMessage();
                     â˜ƒxxxxx.setHoverName(new TranslatableComponent("inventory.hotbarInfo", â˜ƒxxxxxxx, â˜ƒxxxxxx));
                     this.menu.items.add(â˜ƒxxxxx);
                  } else {
                     this.menu.items.add(ItemStack.EMPTY);
                  }
               }
            } else {
               this.menu.items.addAll(â˜ƒxxx);
            }
         }
      } else if (â˜ƒ != CreativeModeTab.TAB_SEARCH) {
         â˜ƒ.fillItemList(this.menu.items);
      }

      if (â˜ƒ == CreativeModeTab.TAB_INVENTORY) {
         AbstractContainerMenu â˜ƒ = this.minecraft.player.inventoryMenu;
         if (this.originalSlots == null) {
            this.originalSlots = ImmutableList.copyOf(this.menu.slots);
         }

         this.menu.slots.clear();

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.slots.size(); ++â˜ƒ) {
            int â˜ƒx;
            int â˜ƒxx;
            if (â˜ƒ >= 5 && â˜ƒ < 9) {
               int â˜ƒxxx = â˜ƒ - 5;
               int â˜ƒxxxx = â˜ƒxxx / 2;
               int â˜ƒxxxxx = â˜ƒxxx % 2;
               â˜ƒx = 54 + â˜ƒxxxx * 54;
               â˜ƒxx = 6 + â˜ƒxxxxx * 27;
            } else if (â˜ƒ >= 0 && â˜ƒ < 5) {
               â˜ƒx = -2000;
               â˜ƒxx = -2000;
            } else if (â˜ƒ == 45) {
               â˜ƒx = 35;
               â˜ƒxx = 20;
            } else {
               int â˜ƒx = â˜ƒ - 9;
               int â˜ƒxx = â˜ƒx % 9;
               int â˜ƒxxx = â˜ƒx / 9;
               â˜ƒx = 9 + â˜ƒxx * 18;
               if (â˜ƒ >= 36) {
                  â˜ƒxx = 112;
               } else {
                  â˜ƒxx = 54 + â˜ƒxxx * 18;
               }
            }

            Slot â˜ƒx = new CreativeModeInventoryScreen.SlotWrapper(â˜ƒ.slots.get(â˜ƒ), â˜ƒ, â˜ƒx, â˜ƒxx);
            this.menu.slots.add(â˜ƒx);
         }

         this.destroyItemSlot = new Slot(CONTAINER, 0, 173, 112);
         this.menu.slots.add(this.destroyItemSlot);
      } else if (â˜ƒ == CreativeModeTab.TAB_INVENTORY.getId()) {
         this.menu.slots.clear();
         this.menu.slots.addAll(this.originalSlots);
         this.originalSlots = null;
      }

      if (this.searchBox != null) {
         if (â˜ƒ == CreativeModeTab.TAB_SEARCH) {
            this.searchBox.setVisible(true);
            this.searchBox.setCanLoseFocus(false);
            this.searchBox.setFocus(true);
            if (â˜ƒ != â˜ƒ.getId()) {
               this.searchBox.setValue("");
            }

            this.refreshSearchResults();
         } else {
            this.searchBox.setVisible(false);
            this.searchBox.setCanLoseFocus(true);
            this.searchBox.setFocus(false);
            this.searchBox.setValue("");
         }
      }

      this.scrollOffs = 0.0F;
      this.menu.scrollTo(0.0F);
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      if (!this.canScroll()) {
         return false;
      } else {
         int â˜ƒ = (this.menu.items.size() + 9 - 1) / 9 - 5;
         this.scrollOffs = (float)((double)this.scrollOffs - â˜ƒ / (double)â˜ƒ);
         this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
         this.menu.scrollTo(this.scrollOffs);
         return true;
      }
   }

   @Override
   protected boolean hasClickedOutside(double var1, double var3, int var5, int var6, int var7) {
      boolean â˜ƒ = â˜ƒ < (double)â˜ƒ || â˜ƒ < (double)â˜ƒ || â˜ƒ >= (double)(â˜ƒ + this.imageWidth) || â˜ƒ >= (double)(â˜ƒ + this.imageHeight);
      this.hasClickedOutside = â˜ƒ && !this.checkTabClicked(CreativeModeTab.TABS[selectedTab], â˜ƒ, â˜ƒ);
      return this.hasClickedOutside;
   }

   protected boolean insideScrollbar(double var1, double var3) {
      int â˜ƒ = this.leftPos;
      int â˜ƒx = this.topPos;
      int â˜ƒxx = â˜ƒ + 175;
      int â˜ƒxxx = â˜ƒx + 18;
      int â˜ƒxxxx = â˜ƒxx + 14;
      int â˜ƒxxxxx = â˜ƒxxx + 112;
      return â˜ƒ >= (double)â˜ƒxx && â˜ƒ >= (double)â˜ƒxxx && â˜ƒ < (double)â˜ƒxxxx && â˜ƒ < (double)â˜ƒxxxxx;
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (this.scrolling) {
         int â˜ƒ = this.topPos + 18;
         int â˜ƒx = â˜ƒ + 112;
         this.scrollOffs = ((float)â˜ƒ - (float)â˜ƒ - 7.5F) / ((float)(â˜ƒx - â˜ƒ) - 15.0F);
         this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
         this.menu.scrollTo(this.scrollOffs);
         return true;
      } else {
         return super.mouseDragged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);

      for(CreativeModeTab â˜ƒ : CreativeModeTab.TABS) {
         if (this.checkTabHovering(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            break;
         }
      }

      if (this.destroyItemSlot != null
         && selectedTab == CreativeModeTab.TAB_INVENTORY.getId()
         && this.isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, (double)â˜ƒ, (double)â˜ƒ)) {
         this.renderTooltip(â˜ƒ, TRASH_SLOT_TOOLTIP, â˜ƒ, â˜ƒ);
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void renderTooltip(PoseStack var1, ItemStack var2, int var3, int var4) {
      if (selectedTab == CreativeModeTab.TAB_SEARCH.getId()) {
         List<Component> â˜ƒ = â˜ƒ.getTooltipLines(
            this.minecraft.player, this.minecraft.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL
         );
         List<Component> â˜ƒx = Lists.<Component>newArrayList(â˜ƒ);
         Item â˜ƒxx = â˜ƒ.getItem();
         CreativeModeTab â˜ƒxxx = â˜ƒxx.getItemCategory();
         if (â˜ƒxxx == null && â˜ƒ.is(Items.ENCHANTED_BOOK)) {
            Map<Enchantment, Integer> â˜ƒxxxx = EnchantmentHelper.getEnchantments(â˜ƒ);
            if (â˜ƒxxxx.size() == 1) {
               Enchantment â˜ƒxxxxx = (Enchantment)â˜ƒxxxx.keySet().iterator().next();

               for(CreativeModeTab â˜ƒxxxxxx : CreativeModeTab.TABS) {
                  if (â˜ƒxxxxxx.hasEnchantmentCategory(â˜ƒxxxxx.category)) {
                     â˜ƒxxx = â˜ƒxxxxxx;
                     break;
                  }
               }
            }
         }

         this.visibleTags.forEach((var2x, var3x) -> {
            if (â˜ƒ.is(var3x)) {
               â˜ƒ.add(1, new TextComponent("#" + var2x).withStyle(ChatFormatting.DARK_PURPLE));
            }
         });
         if (â˜ƒxxx != null) {
            â˜ƒx.add(1, â˜ƒxxx.getDisplayName().copy().withStyle(ChatFormatting.BLUE));
         }

         this.renderTooltip(â˜ƒ, â˜ƒx, â˜ƒ.getTooltipImage(), â˜ƒ, â˜ƒ);
      } else {
         super.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      CreativeModeTab â˜ƒ = CreativeModeTab.TABS[selectedTab];

      for(CreativeModeTab â˜ƒx : CreativeModeTab.TABS) {
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, CREATIVE_TABS_LOCATION);
         if (â˜ƒx.getId() != selectedTab) {
            this.renderTabButton(â˜ƒ, â˜ƒx);
         }
      }

      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/container/creative_inventory/tab_" + â˜ƒ.getBackgroundSuffix()));
      this.blit(â˜ƒ, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      this.searchBox.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int â˜ƒx = this.leftPos + 175;
      int â˜ƒxx = this.topPos + 18;
      int â˜ƒxxx = â˜ƒxx + 112;
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, CREATIVE_TABS_LOCATION);
      if (â˜ƒ.canScroll()) {
         this.blit(â˜ƒ, â˜ƒx, â˜ƒxx + (int)((float)(â˜ƒxxx - â˜ƒxx - 17) * this.scrollOffs), 232 + (this.canScroll() ? 0 : 12), 0, 12, 15);
      }

      this.renderTabButton(â˜ƒ, â˜ƒ);
      if (â˜ƒ == CreativeModeTab.TAB_INVENTORY) {
         InventoryScreen.renderEntityInInventory(
            this.leftPos + 88, this.topPos + 45, 20, (float)(this.leftPos + 88 - â˜ƒ), (float)(this.topPos + 45 - 30 - â˜ƒ), this.minecraft.player
         );
      }
   }

   protected boolean checkTabClicked(CreativeModeTab var1, double var2, double var4) {
      int â˜ƒ = â˜ƒ.getColumn();
      int â˜ƒx = 28 * â˜ƒ;
      int â˜ƒxx = 0;
      if (â˜ƒ.isAlignedRight()) {
         â˜ƒx = this.imageWidth - 28 * (6 - â˜ƒ) + 2;
      } else if (â˜ƒ > 0) {
         â˜ƒx += â˜ƒ;
      }

      if (â˜ƒ.isTopRow()) {
         â˜ƒxx -= 32;
      } else {
         â˜ƒxx += this.imageHeight;
      }

      return â˜ƒ >= (double)â˜ƒx && â˜ƒ <= (double)(â˜ƒx + 28) && â˜ƒ >= (double)â˜ƒxx && â˜ƒ <= (double)(â˜ƒxx + 32);
   }

   protected boolean checkTabHovering(PoseStack var1, CreativeModeTab var2, int var3, int var4) {
      int â˜ƒ = â˜ƒ.getColumn();
      int â˜ƒx = 28 * â˜ƒ;
      int â˜ƒxx = 0;
      if (â˜ƒ.isAlignedRight()) {
         â˜ƒx = this.imageWidth - 28 * (6 - â˜ƒ) + 2;
      } else if (â˜ƒ > 0) {
         â˜ƒx += â˜ƒ;
      }

      if (â˜ƒ.isTopRow()) {
         â˜ƒxx -= 32;
      } else {
         â˜ƒxx += this.imageHeight;
      }

      if (this.isHovering(â˜ƒx + 3, â˜ƒxx + 3, 23, 27, (double)â˜ƒ, (double)â˜ƒ)) {
         this.renderTooltip(â˜ƒ, â˜ƒ.getDisplayName(), â˜ƒ, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   protected void renderTabButton(PoseStack var1, CreativeModeTab var2) {
      boolean â˜ƒ = â˜ƒ.getId() == selectedTab;
      boolean â˜ƒx = â˜ƒ.isTopRow();
      int â˜ƒxx = â˜ƒ.getColumn();
      int â˜ƒxxx = â˜ƒxx * 28;
      int â˜ƒxxxx = 0;
      int â˜ƒxxxxx = this.leftPos + 28 * â˜ƒxx;
      int â˜ƒxxxxxx = this.topPos;
      int â˜ƒxxxxxxx = 32;
      if (â˜ƒ) {
         â˜ƒxxxx += 32;
      }

      if (â˜ƒ.isAlignedRight()) {
         â˜ƒxxxxx = this.leftPos + this.imageWidth - 28 * (6 - â˜ƒxx);
      } else if (â˜ƒxx > 0) {
         â˜ƒxxxxx += â˜ƒxx;
      }

      if (â˜ƒx) {
         â˜ƒxxxxxx -= 28;
      } else {
         â˜ƒxxxx += 64;
         â˜ƒxxxxxx += this.imageHeight - 4;
      }

      this.blit(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxx, 28, 32);
      this.itemRenderer.blitOffset = 100.0F;
      â˜ƒxxxxx += 6;
      â˜ƒxxxxxx += 8 + (â˜ƒx ? 1 : -1);
      ItemStack â˜ƒ = â˜ƒ.getIconItem();
      this.itemRenderer.renderAndDecorateItem(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx);
      this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx);
      this.itemRenderer.blitOffset = 0.0F;
   }

   public int getSelectedTab() {
      return selectedTab;
   }

   public static void handleHotbarLoadOrSave(Minecraft var0, int var1, boolean var2, boolean var3) {
      LocalPlayer â˜ƒ = â˜ƒ.player;
      HotbarManager â˜ƒx = â˜ƒ.getHotbarManager();
      Hotbar â˜ƒxx = â˜ƒx.get(â˜ƒ);
      if (â˜ƒ) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < Inventory.getSelectionSize(); ++â˜ƒxxx) {
            ItemStack â˜ƒxxxx = â˜ƒxx.get(â˜ƒxxx).copy();
            â˜ƒ.getInventory().setItem(â˜ƒxxx, â˜ƒxxxx);
            â˜ƒ.gameMode.handleCreativeModeItemAdd(â˜ƒxxxx, 36 + â˜ƒxxx);
         }

         â˜ƒ.inventoryMenu.broadcastChanges();
      } else if (â˜ƒ) {
         for(int â˜ƒ = 0; â˜ƒ < Inventory.getSelectionSize(); ++â˜ƒ) {
            â˜ƒxx.set(â˜ƒ, â˜ƒ.getInventory().getItem(â˜ƒ).copy());
         }

         Component â˜ƒ = â˜ƒ.options.keyHotbarSlots[â˜ƒ].getTranslatedKeyMessage();
         Component â˜ƒx = â˜ƒ.options.keyLoadHotbarActivator.getTranslatedKeyMessage();
         â˜ƒ.gui.setOverlayMessage(new TranslatableComponent("inventory.hotbarSaved", â˜ƒx, â˜ƒ), false);
         â˜ƒx.save();
      }
   }

   static class CustomCreativeSlot extends Slot {
      public CustomCreativeSlot(Container var1, int var2, int var3, int var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mayPickup(Player var1) {
         if (super.mayPickup(â˜ƒ) && this.hasItem()) {
            return this.getItem().getTagElement("CustomCreativeLock") == null;
         } else {
            return !this.hasItem();
         }
      }
   }

   public static class ItemPickerMenu extends AbstractContainerMenu {
      public final NonNullList<ItemStack> items = NonNullList.create();
      private final AbstractContainerMenu inventoryMenu;

      public ItemPickerMenu(Player var1) {
         super(null, 0);
         this.inventoryMenu = â˜ƒ.inventoryMenu;
         Inventory â˜ƒ = â˜ƒ.getInventory();

         for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
               this.addSlot(
                  new CreativeModeInventoryScreen.CustomCreativeSlot(CreativeModeInventoryScreen.CONTAINER, â˜ƒx * 9 + â˜ƒxx, 9 + â˜ƒxx * 18, 18 + â˜ƒx * 18)
               );
            }
         }

         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx, 9 + â˜ƒx * 18, 112));
         }

         this.scrollTo(0.0F);
      }

      @Override
      public boolean stillValid(Player var1) {
         return true;
      }

      public void scrollTo(float var1) {
         int â˜ƒ = (this.items.size() + 9 - 1) / 9 - 5;
         int â˜ƒx = (int)((double)(â˜ƒ * (float)â˜ƒ) + 0.5);
         if (â˜ƒx < 0) {
            â˜ƒx = 0;
         }

         for(int â˜ƒ = 0; â˜ƒ < 5; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
               int â˜ƒxx = â˜ƒx + (â˜ƒ + â˜ƒx) * 9;
               if (â˜ƒxx >= 0 && â˜ƒxx < this.items.size()) {
                  CreativeModeInventoryScreen.CONTAINER.setItem(â˜ƒx + â˜ƒ * 9, this.items.get(â˜ƒxx));
               } else {
                  CreativeModeInventoryScreen.CONTAINER.setItem(â˜ƒx + â˜ƒ * 9, ItemStack.EMPTY);
               }
            }
         }
      }

      public boolean canScroll() {
         return this.items.size() > 45;
      }

      @Override
      public ItemStack quickMoveStack(Player var1, int var2) {
         if (â˜ƒ >= this.slots.size() - 9 && â˜ƒ < this.slots.size()) {
            Slot â˜ƒ = this.slots.get(â˜ƒ);
            if (â˜ƒ != null && â˜ƒ.hasItem()) {
               â˜ƒ.set(ItemStack.EMPTY);
            }
         }

         return ItemStack.EMPTY;
      }

      @Override
      public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
         return â˜ƒ.container != CreativeModeInventoryScreen.CONTAINER;
      }

      @Override
      public boolean canDragTo(Slot var1) {
         return â˜ƒ.container != CreativeModeInventoryScreen.CONTAINER;
      }

      @Override
      public ItemStack getCarried() {
         return this.inventoryMenu.getCarried();
      }

      @Override
      public void setCarried(ItemStack var1) {
         this.inventoryMenu.setCarried(â˜ƒ);
      }
   }

   static class SlotWrapper extends Slot {
      final Slot target;

      public SlotWrapper(Slot var1, int var2, int var3, int var4) {
         super(â˜ƒ.container, â˜ƒ, â˜ƒ, â˜ƒ);
         this.target = â˜ƒ;
      }

      @Override
      public void onTake(Player var1, ItemStack var2) {
         this.target.onTake(â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mayPlace(ItemStack var1) {
         return this.target.mayPlace(â˜ƒ);
      }

      @Override
      public ItemStack getItem() {
         return this.target.getItem();
      }

      @Override
      public boolean hasItem() {
         return this.target.hasItem();
      }

      @Override
      public void set(ItemStack var1) {
         this.target.set(â˜ƒ);
      }

      @Override
      public void setChanged() {
         this.target.setChanged();
      }

      @Override
      public int getMaxStackSize() {
         return this.target.getMaxStackSize();
      }

      @Override
      public int getMaxStackSize(ItemStack var1) {
         return this.target.getMaxStackSize(â˜ƒ);
      }

      @Nullable
      @Override
      public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
         return this.target.getNoItemIcon();
      }

      @Override
      public ItemStack remove(int var1) {
         return this.target.remove(â˜ƒ);
      }

      @Override
      public boolean isActive() {
         return this.target.isActive();
      }

      @Override
      public boolean mayPickup(Player var1) {
         return this.target.mayPickup(â˜ƒ);
      }
   }
}
