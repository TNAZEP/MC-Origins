package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractContainerScreen<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {
   public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation("textures/gui/container/inventory.png");
   private static final float SNAPBACK_SPEED = 100.0F;
   private static final int QUICKDROP_DELAY = 500;
   private static final int DOUBLECLICK_SPEED = 250;
   public static final int SLOT_ITEM_BLIT_OFFSET = 100;
   private static final int HOVER_ITEM_BLIT_OFFSET = 200;
   protected int imageWidth = 176;
   protected int imageHeight = 166;
   protected int titleLabelX;
   protected int titleLabelY;
   protected int inventoryLabelX;
   protected int inventoryLabelY;
   protected final T menu;
   protected final Component playerInventoryTitle;
   @Nullable
   protected Slot hoveredSlot;
   @Nullable
   private Slot clickedSlot;
   @Nullable
   private Slot snapbackEnd;
   @Nullable
   private Slot quickdropSlot;
   @Nullable
   private Slot lastClickSlot;
   protected int leftPos;
   protected int topPos;
   private boolean isSplittingStack;
   private ItemStack draggingItem = ItemStack.EMPTY;
   private int snapbackStartX;
   private int snapbackStartY;
   private long snapbackTime;
   private ItemStack snapbackItem = ItemStack.EMPTY;
   private long quickdropTime;
   protected final Set<Slot> quickCraftSlots = Sets.<Slot>newHashSet();
   protected boolean isQuickCrafting;
   private int quickCraftingType;
   private int quickCraftingButton;
   private boolean skipNextRelease;
   private int quickCraftingRemainder;
   private long lastClickTime;
   private int lastClickButton;
   private boolean doubleclick;
   private ItemStack lastQuickMoved = ItemStack.EMPTY;

   public AbstractContainerScreen(T var1, Inventory var2, Component var3) {
      super(â˜ƒ);
      this.menu = â˜ƒ;
      this.playerInventoryTitle = â˜ƒ.getDisplayName();
      this.skipNextRelease = true;
      this.titleLabelX = 8;
      this.titleLabelY = 6;
      this.inventoryLabelX = 8;
      this.inventoryLabelY = this.imageHeight - 94;
   }

   @Override
   protected void init() {
      super.init();
      this.leftPos = (this.width - this.imageWidth) / 2;
      this.topPos = (this.height - this.imageHeight) / 2;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      int â˜ƒ = this.leftPos;
      int â˜ƒx = this.topPos;
      this.renderBg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.disableDepthTest();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      PoseStack â˜ƒxx = RenderSystem.getModelViewStack();
      â˜ƒxx.pushPose();
      â˜ƒxx.translate((double)â˜ƒ, (double)â˜ƒx, 0.0);
      RenderSystem.applyModelViewMatrix();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      this.hoveredSlot = null;
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

      for(int â˜ƒxxx = 0; â˜ƒxxx < this.menu.slots.size(); ++â˜ƒxxx) {
         Slot â˜ƒxxxx = this.menu.slots.get(â˜ƒxxx);
         if (â˜ƒxxxx.isActive()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            this.renderSlot(â˜ƒ, â˜ƒxxxx);
         }

         if (this.isHovering(â˜ƒxxxx, (double)â˜ƒ, (double)â˜ƒ) && â˜ƒxxxx.isActive()) {
            this.hoveredSlot = â˜ƒxxxx;
            int â˜ƒxxxx = â˜ƒxxxx.x;
            int â˜ƒxxxxx = â˜ƒxxxx.y;
            renderSlotHighlight(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx, this.getBlitOffset());
         }
      }

      this.renderLabels(â˜ƒ, â˜ƒ, â˜ƒ);
      ItemStack â˜ƒxxx = this.draggingItem.isEmpty() ? this.menu.getCarried() : this.draggingItem;
      if (!â˜ƒxxx.isEmpty()) {
         int â˜ƒxxxx = 8;
         int â˜ƒxxxxx = this.draggingItem.isEmpty() ? 8 : 16;
         String â˜ƒxxxxxx = null;
         if (!this.draggingItem.isEmpty() && this.isSplittingStack) {
            â˜ƒxxx = â˜ƒxxx.copy();
            â˜ƒxxx.setCount(Mth.ceil((float)â˜ƒxxx.getCount() / 2.0F));
         } else if (this.isQuickCrafting && this.quickCraftSlots.size() > 1) {
            â˜ƒxxx = â˜ƒxxx.copy();
            â˜ƒxxx.setCount(this.quickCraftingRemainder);
            if (â˜ƒxxx.isEmpty()) {
               â˜ƒxxxxxx = ChatFormatting.YELLOW + "0";
            }
         }

         this.renderFloatingItem(â˜ƒxxx, â˜ƒ - â˜ƒ - 8, â˜ƒ - â˜ƒx - â˜ƒxxxxx, â˜ƒxxxxxx);
      }

      if (!this.snapbackItem.isEmpty()) {
         float â˜ƒxxx = (float)(Util.getMillis() - this.snapbackTime) / 100.0F;
         if (â˜ƒxxx >= 1.0F) {
            â˜ƒxxx = 1.0F;
            this.snapbackItem = ItemStack.EMPTY;
         }

         int â˜ƒxxx = this.snapbackEnd.x - this.snapbackStartX;
         int â˜ƒxxxx = this.snapbackEnd.y - this.snapbackStartY;
         int â˜ƒxxxxx = this.snapbackStartX + (int)((float)â˜ƒxxx * â˜ƒxxx);
         int â˜ƒxxxxxx = this.snapbackStartY + (int)((float)â˜ƒxxxx * â˜ƒxxx);
         this.renderFloatingItem(this.snapbackItem, â˜ƒxxxxx, â˜ƒxxxxxx, null);
      }

      â˜ƒxx.popPose();
      RenderSystem.applyModelViewMatrix();
      RenderSystem.enableDepthTest();
   }

   public static void renderSlotHighlight(PoseStack var0, int var1, int var2, int var3) {
      RenderSystem.disableDepthTest();
      RenderSystem.colorMask(true, true, true, false);
      fillGradient(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 16, â˜ƒ + 16, -2130706433, -2130706433, â˜ƒ);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.enableDepthTest();
   }

   protected void renderTooltip(PoseStack var1, int var2, int var3) {
      if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
         this.renderTooltip(â˜ƒ, this.hoveredSlot.getItem(), â˜ƒ, â˜ƒ);
      }
   }

   private void renderFloatingItem(ItemStack var1, int var2, int var3, String var4) {
      PoseStack â˜ƒ = RenderSystem.getModelViewStack();
      â˜ƒ.translate(0.0, 0.0, 32.0);
      RenderSystem.applyModelViewMatrix();
      this.setBlitOffset(200);
      this.itemRenderer.blitOffset = 200.0F;
      this.itemRenderer.renderAndDecorateItem(â˜ƒ, â˜ƒ, â˜ƒ);
      this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒ, â˜ƒ, â˜ƒ - (this.draggingItem.isEmpty() ? 0 : 8), â˜ƒ);
      this.setBlitOffset(0);
      this.itemRenderer.blitOffset = 0.0F;
   }

   protected void renderLabels(PoseStack var1, int var2, int var3) {
      this.font.draw(â˜ƒ, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
      this.font.draw(â˜ƒ, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
   }

   protected abstract void renderBg(PoseStack var1, float var2, int var3, int var4);

   private void renderSlot(PoseStack var1, Slot var2) {
      int â˜ƒ = â˜ƒ.x;
      int â˜ƒx = â˜ƒ.y;
      ItemStack â˜ƒxx = â˜ƒ.getItem();
      boolean â˜ƒxxx = false;
      boolean â˜ƒxxxx = â˜ƒ == this.clickedSlot && !this.draggingItem.isEmpty() && !this.isSplittingStack;
      ItemStack â˜ƒxxxxx = this.menu.getCarried();
      String â˜ƒxxxxxx = null;
      if (â˜ƒ == this.clickedSlot && !this.draggingItem.isEmpty() && this.isSplittingStack && !â˜ƒxx.isEmpty()) {
         â˜ƒxx = â˜ƒxx.copy();
         â˜ƒxx.setCount(â˜ƒxx.getCount() / 2);
      } else if (this.isQuickCrafting && this.quickCraftSlots.contains(â˜ƒ) && !â˜ƒxxxxx.isEmpty()) {
         if (this.quickCraftSlots.size() == 1) {
            return;
         }

         if (AbstractContainerMenu.canItemQuickReplace(â˜ƒ, â˜ƒxxxxx, true) && this.menu.canDragTo(â˜ƒ)) {
            â˜ƒxx = â˜ƒxxxxx.copy();
            â˜ƒxxx = true;
            AbstractContainerMenu.getQuickCraftSlotCount(
               this.quickCraftSlots, this.quickCraftingType, â˜ƒxx, â˜ƒ.getItem().isEmpty() ? 0 : â˜ƒ.getItem().getCount()
            );
            int â˜ƒ = Math.min(â˜ƒxx.getMaxStackSize(), â˜ƒ.getMaxStackSize(â˜ƒxx));
            if (â˜ƒxx.getCount() > â˜ƒ) {
               â˜ƒxxxxxx = ChatFormatting.YELLOW.toString() + â˜ƒ;
               â˜ƒxx.setCount(â˜ƒ);
            }
         } else {
            this.quickCraftSlots.remove(â˜ƒ);
            this.recalculateQuickCraftRemaining();
         }
      }

      this.setBlitOffset(100);
      this.itemRenderer.blitOffset = 100.0F;
      if (â˜ƒxx.isEmpty() && â˜ƒ.isActive()) {
         Pair<ResourceLocation, ResourceLocation> â˜ƒ = â˜ƒ.getNoItemIcon();
         if (â˜ƒ != null) {
            TextureAtlasSprite â˜ƒx = (TextureAtlasSprite)this.minecraft.getTextureAtlas(â˜ƒ.getFirst()).apply(â˜ƒ.getSecond());
            RenderSystem.setShaderTexture(0, â˜ƒx.atlas().location());
            blit(â˜ƒ, â˜ƒ, â˜ƒx, this.getBlitOffset(), 16, 16, â˜ƒx);
            â˜ƒxxxx = true;
         }
      }

      if (!â˜ƒxxxx) {
         if (â˜ƒxxx) {
            fill(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ + 16, â˜ƒx + 16, -2130706433);
         }

         RenderSystem.enableDepthTest();
         this.itemRenderer.renderAndDecorateItem(this.minecraft.player, â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒ.x + â˜ƒ.y * this.imageWidth);
         this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒxxxxxx);
      }

      this.itemRenderer.blitOffset = 0.0F;
      this.setBlitOffset(0);
   }

   private void recalculateQuickCraftRemaining() {
      ItemStack â˜ƒ = this.menu.getCarried();
      if (!â˜ƒ.isEmpty() && this.isQuickCrafting) {
         if (this.quickCraftingType == 2) {
            this.quickCraftingRemainder = â˜ƒ.getMaxStackSize();
         } else {
            this.quickCraftingRemainder = â˜ƒ.getCount();

            for(Slot â˜ƒx : this.quickCraftSlots) {
               ItemStack â˜ƒxx = â˜ƒ.copy();
               ItemStack â˜ƒxxx = â˜ƒx.getItem();
               int â˜ƒxxxx = â˜ƒxxx.isEmpty() ? 0 : â˜ƒxxx.getCount();
               AbstractContainerMenu.getQuickCraftSlotCount(this.quickCraftSlots, this.quickCraftingType, â˜ƒxx, â˜ƒxxxx);
               int â˜ƒxxxxx = Math.min(â˜ƒxx.getMaxStackSize(), â˜ƒx.getMaxStackSize(â˜ƒxx));
               if (â˜ƒxx.getCount() > â˜ƒxxxxx) {
                  â˜ƒxx.setCount(â˜ƒxxxxx);
               }

               this.quickCraftingRemainder -= â˜ƒxx.getCount() - â˜ƒxxxx;
            }
         }
      }
   }

   @Nullable
   private Slot findSlot(double var1, double var3) {
      for(int â˜ƒ = 0; â˜ƒ < this.menu.slots.size(); ++â˜ƒ) {
         Slot â˜ƒx = this.menu.slots.get(â˜ƒ);
         if (this.isHovering(â˜ƒx, â˜ƒ, â˜ƒ) && â˜ƒx.isActive()) {
            return â˜ƒx;
         }
      }

      return null;
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         boolean â˜ƒ = this.minecraft.options.keyPickItem.matchesMouse(â˜ƒ);
         Slot â˜ƒx = this.findSlot(â˜ƒ, â˜ƒ);
         long â˜ƒxx = Util.getMillis();
         this.doubleclick = this.lastClickSlot == â˜ƒx && â˜ƒxx - this.lastClickTime < 250L && this.lastClickButton == â˜ƒ;
         this.skipNextRelease = false;
         if (â˜ƒ != 0 && â˜ƒ != 1 && !â˜ƒ) {
            this.checkHotbarMouseClicked(â˜ƒ);
         } else {
            int â˜ƒ = this.leftPos;
            int â˜ƒx = this.topPos;
            boolean â˜ƒxx = this.hasClickedOutside(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
            int â˜ƒxxx = -1;
            if (â˜ƒx != null) {
               â˜ƒxxx = â˜ƒx.index;
            }

            if (â˜ƒxx) {
               â˜ƒxxx = -999;
            }

            if (this.minecraft.options.touchscreen && â˜ƒxx && this.menu.getCarried().isEmpty()) {
               this.minecraft.setScreen(null);
               return true;
            }

            if (â˜ƒxxx != -1) {
               if (this.minecraft.options.touchscreen) {
                  if (â˜ƒx != null && â˜ƒx.hasItem()) {
                     this.clickedSlot = â˜ƒx;
                     this.draggingItem = ItemStack.EMPTY;
                     this.isSplittingStack = â˜ƒ == 1;
                  } else {
                     this.clickedSlot = null;
                  }
               } else if (!this.isQuickCrafting) {
                  if (this.menu.getCarried().isEmpty()) {
                     if (this.minecraft.options.keyPickItem.matchesMouse(â˜ƒ)) {
                        this.slotClicked(â˜ƒx, â˜ƒxxx, â˜ƒ, ClickType.CLONE);
                     } else {
                        boolean â˜ƒ = â˜ƒxxx != -999
                           && (
                              InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340)
                                 || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344)
                           );
                        ClickType â˜ƒx = ClickType.PICKUP;
                        if (â˜ƒ) {
                           this.lastQuickMoved = â˜ƒx != null && â˜ƒx.hasItem() ? â˜ƒx.getItem().copy() : ItemStack.EMPTY;
                           â˜ƒx = ClickType.QUICK_MOVE;
                        } else if (â˜ƒxxx == -999) {
                           â˜ƒx = ClickType.THROW;
                        }

                        this.slotClicked(â˜ƒx, â˜ƒxxx, â˜ƒ, â˜ƒx);
                     }

                     this.skipNextRelease = true;
                  } else {
                     this.isQuickCrafting = true;
                     this.quickCraftingButton = â˜ƒ;
                     this.quickCraftSlots.clear();
                     if (â˜ƒ == 0) {
                        this.quickCraftingType = 0;
                     } else if (â˜ƒ == 1) {
                        this.quickCraftingType = 1;
                     } else if (this.minecraft.options.keyPickItem.matchesMouse(â˜ƒ)) {
                        this.quickCraftingType = 2;
                     }
                  }
               }
            }
         }

         this.lastClickSlot = â˜ƒx;
         this.lastClickTime = â˜ƒxx;
         this.lastClickButton = â˜ƒ;
         return true;
      }
   }

   private void checkHotbarMouseClicked(int var1) {
      if (this.hoveredSlot != null && this.menu.getCarried().isEmpty()) {
         if (this.minecraft.options.keySwapOffhand.matchesMouse(â˜ƒ)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 40, ClickType.SWAP);
            return;
         }

         for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
            if (this.minecraft.options.keyHotbarSlots[â˜ƒ].matchesMouse(â˜ƒ)) {
               this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, â˜ƒ, ClickType.SWAP);
            }
         }
      }
   }

   protected boolean hasClickedOutside(double var1, double var3, int var5, int var6, int var7) {
      return â˜ƒ < (double)â˜ƒ || â˜ƒ < (double)â˜ƒ || â˜ƒ >= (double)(â˜ƒ + this.imageWidth) || â˜ƒ >= (double)(â˜ƒ + this.imageHeight);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      Slot â˜ƒ = this.findSlot(â˜ƒ, â˜ƒ);
      ItemStack â˜ƒx = this.menu.getCarried();
      if (this.clickedSlot != null && this.minecraft.options.touchscreen) {
         if (â˜ƒ == 0 || â˜ƒ == 1) {
            if (this.draggingItem.isEmpty()) {
               if (â˜ƒ != this.clickedSlot && !this.clickedSlot.getItem().isEmpty()) {
                  this.draggingItem = this.clickedSlot.getItem().copy();
               }
            } else if (this.draggingItem.getCount() > 1 && â˜ƒ != null && AbstractContainerMenu.canItemQuickReplace(â˜ƒ, this.draggingItem, false)) {
               long â˜ƒxx = Util.getMillis();
               if (this.quickdropSlot == â˜ƒ) {
                  if (â˜ƒxx - this.quickdropTime > 500L) {
                     this.slotClicked(this.clickedSlot, this.clickedSlot.index, 0, ClickType.PICKUP);
                     this.slotClicked(â˜ƒ, â˜ƒ.index, 1, ClickType.PICKUP);
                     this.slotClicked(this.clickedSlot, this.clickedSlot.index, 0, ClickType.PICKUP);
                     this.quickdropTime = â˜ƒxx + 750L;
                     this.draggingItem.shrink(1);
                  }
               } else {
                  this.quickdropSlot = â˜ƒ;
                  this.quickdropTime = â˜ƒxx;
               }
            }
         }
      } else if (this.isQuickCrafting
         && â˜ƒ != null
         && !â˜ƒx.isEmpty()
         && (â˜ƒx.getCount() > this.quickCraftSlots.size() || this.quickCraftingType == 2)
         && AbstractContainerMenu.canItemQuickReplace(â˜ƒ, â˜ƒx, true)
         && â˜ƒ.mayPlace(â˜ƒx)
         && this.menu.canDragTo(â˜ƒ)) {
         this.quickCraftSlots.add(â˜ƒ);
         this.recalculateQuickCraftRemaining();
      }

      return true;
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      Slot â˜ƒ = this.findSlot(â˜ƒ, â˜ƒ);
      int â˜ƒx = this.leftPos;
      int â˜ƒxx = this.topPos;
      boolean â˜ƒxxx = this.hasClickedOutside(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ);
      int â˜ƒxxxx = -1;
      if (â˜ƒ != null) {
         â˜ƒxxxx = â˜ƒ.index;
      }

      if (â˜ƒxxx) {
         â˜ƒxxxx = -999;
      }

      if (this.doubleclick && â˜ƒ != null && â˜ƒ == 0 && this.menu.canTakeItemForPickAll(ItemStack.EMPTY, â˜ƒ)) {
         if (hasShiftDown()) {
            if (!this.lastQuickMoved.isEmpty()) {
               for(Slot â˜ƒ : this.menu.slots) {
                  if (â˜ƒ != null
                     && â˜ƒ.mayPickup(this.minecraft.player)
                     && â˜ƒ.hasItem()
                     && â˜ƒ.container == â˜ƒ.container
                     && AbstractContainerMenu.canItemQuickReplace(â˜ƒ, this.lastQuickMoved, true)) {
                     this.slotClicked(â˜ƒ, â˜ƒ.index, â˜ƒ, ClickType.QUICK_MOVE);
                  }
               }
            }
         } else {
            this.slotClicked(â˜ƒ, â˜ƒxxxx, â˜ƒ, ClickType.PICKUP_ALL);
         }

         this.doubleclick = false;
         this.lastClickTime = 0L;
      } else {
         if (this.isQuickCrafting && this.quickCraftingButton != â˜ƒ) {
            this.isQuickCrafting = false;
            this.quickCraftSlots.clear();
            this.skipNextRelease = true;
            return true;
         }

         if (this.skipNextRelease) {
            this.skipNextRelease = false;
            return true;
         }

         if (this.clickedSlot != null && this.minecraft.options.touchscreen) {
            if (â˜ƒ == 0 || â˜ƒ == 1) {
               if (this.draggingItem.isEmpty() && â˜ƒ != this.clickedSlot) {
                  this.draggingItem = this.clickedSlot.getItem();
               }

               boolean â˜ƒ = AbstractContainerMenu.canItemQuickReplace(â˜ƒ, this.draggingItem, false);
               if (â˜ƒxxxx != -1 && !this.draggingItem.isEmpty() && â˜ƒ) {
                  this.slotClicked(this.clickedSlot, this.clickedSlot.index, â˜ƒ, ClickType.PICKUP);
                  this.slotClicked(â˜ƒ, â˜ƒxxxx, 0, ClickType.PICKUP);
                  if (this.menu.getCarried().isEmpty()) {
                     this.snapbackItem = ItemStack.EMPTY;
                  } else {
                     this.slotClicked(this.clickedSlot, this.clickedSlot.index, â˜ƒ, ClickType.PICKUP);
                     this.snapbackStartX = Mth.floor(â˜ƒ - (double)â˜ƒx);
                     this.snapbackStartY = Mth.floor(â˜ƒ - (double)â˜ƒxx);
                     this.snapbackEnd = this.clickedSlot;
                     this.snapbackItem = this.draggingItem;
                     this.snapbackTime = Util.getMillis();
                  }
               } else if (!this.draggingItem.isEmpty()) {
                  this.snapbackStartX = Mth.floor(â˜ƒ - (double)â˜ƒx);
                  this.snapbackStartY = Mth.floor(â˜ƒ - (double)â˜ƒxx);
                  this.snapbackEnd = this.clickedSlot;
                  this.snapbackItem = this.draggingItem;
                  this.snapbackTime = Util.getMillis();
               }

               this.draggingItem = ItemStack.EMPTY;
               this.clickedSlot = null;
            }
         } else if (this.isQuickCrafting && !this.quickCraftSlots.isEmpty()) {
            this.slotClicked(null, -999, AbstractContainerMenu.getQuickcraftMask(0, this.quickCraftingType), ClickType.QUICK_CRAFT);

            for(Slot â˜ƒ : this.quickCraftSlots) {
               this.slotClicked(â˜ƒ, â˜ƒ.index, AbstractContainerMenu.getQuickcraftMask(1, this.quickCraftingType), ClickType.QUICK_CRAFT);
            }

            this.slotClicked(null, -999, AbstractContainerMenu.getQuickcraftMask(2, this.quickCraftingType), ClickType.QUICK_CRAFT);
         } else if (!this.menu.getCarried().isEmpty()) {
            if (this.minecraft.options.keyPickItem.matchesMouse(â˜ƒ)) {
               this.slotClicked(â˜ƒ, â˜ƒxxxx, â˜ƒ, ClickType.CLONE);
            } else {
               boolean â˜ƒ = â˜ƒxxxx != -999
                  && (
                     InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340)
                        || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344)
                  );
               if (â˜ƒ) {
                  this.lastQuickMoved = â˜ƒ != null && â˜ƒ.hasItem() ? â˜ƒ.getItem().copy() : ItemStack.EMPTY;
               }

               this.slotClicked(â˜ƒ, â˜ƒxxxx, â˜ƒ, â˜ƒ ? ClickType.QUICK_MOVE : ClickType.PICKUP);
            }
         }
      }

      if (this.menu.getCarried().isEmpty()) {
         this.lastClickTime = 0L;
      }

      this.isQuickCrafting = false;
      return true;
   }

   private boolean isHovering(Slot var1, double var2, double var4) {
      return this.isHovering(â˜ƒ.x, â˜ƒ.y, 16, 16, â˜ƒ, â˜ƒ);
   }

   protected boolean isHovering(int var1, int var2, int var3, int var4, double var5, double var7) {
      int â˜ƒ = this.leftPos;
      int â˜ƒx = this.topPos;
      â˜ƒ -= (double)â˜ƒ;
      â˜ƒ -= (double)â˜ƒx;
      return â˜ƒ >= (double)(â˜ƒ - 1) && â˜ƒ < (double)(â˜ƒ + â˜ƒ + 1) && â˜ƒ >= (double)(â˜ƒ - 1) && â˜ƒ < (double)(â˜ƒ + â˜ƒ + 1);
   }

   protected void slotClicked(Slot var1, int var2, int var3, ClickType var4) {
      if (â˜ƒ != null) {
         â˜ƒ = â˜ƒ.index;
      }

      this.minecraft.gameMode.handleInventoryMouseClick(this.menu.containerId, â˜ƒ, â˜ƒ, â˜ƒ, this.minecraft.player);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (this.minecraft.options.keyInventory.matches(â˜ƒ, â˜ƒ)) {
         this.onClose();
         return true;
      } else {
         this.checkHotbarKeyPressed(â˜ƒ, â˜ƒ);
         if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            if (this.minecraft.options.keyPickItem.matches(â˜ƒ, â˜ƒ)) {
               this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ClickType.CLONE);
            } else if (this.minecraft.options.keyDrop.matches(â˜ƒ, â˜ƒ)) {
               this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
            }
         }

         return true;
      }
   }

   protected boolean checkHotbarKeyPressed(int var1, int var2) {
      if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null) {
         if (this.minecraft.options.keySwapOffhand.matches(â˜ƒ, â˜ƒ)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 40, ClickType.SWAP);
            return true;
         }

         for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
            if (this.minecraft.options.keyHotbarSlots[â˜ƒ].matches(â˜ƒ, â˜ƒ)) {
               this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, â˜ƒ, ClickType.SWAP);
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void removed() {
      if (this.minecraft.player != null) {
         this.menu.removed(this.minecraft.player);
      }
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public final void tick() {
      super.tick();
      if (this.minecraft.player.isAlive() && !this.minecraft.player.isRemoved()) {
         this.containerTick();
      } else {
         this.minecraft.player.closeContainer();
      }
   }

   protected void containerTick() {
   }

   @Override
   public T getMenu() {
      return this.menu;
   }

   @Override
   public void onClose() {
      this.minecraft.player.closeContainer();
      super.onClose();
   }
}
