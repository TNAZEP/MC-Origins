package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AnvilScreen extends ItemCombinerScreen<AnvilMenu> {
   private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");
   private static final Component TOO_EXPENSIVE_TEXT = new TranslatableComponent("container.repair.expensive");
   private EditBox name;
   private final Player player;

   public AnvilScreen(AnvilMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, ANVIL_LOCATION);
      this.player = â˜ƒ.player;
      this.titleLabelX = 60;
   }

   @Override
   public void containerTick() {
      super.containerTick();
      this.name.tick();
   }

   @Override
   protected void subInit() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.name = new EditBox(this.font, â˜ƒ + 62, â˜ƒx + 24, 103, 12, new TranslatableComponent("container.repair"));
      this.name.setCanLoseFocus(false);
      this.name.setTextColor(-1);
      this.name.setTextColorUneditable(-1);
      this.name.setBordered(false);
      this.name.setMaxLength(50);
      this.name.setResponder(this::onNameChanged);
      this.name.setValue("");
      this.addWidget(this.name);
      this.setInitialFocus(this.name);
      this.name.setEditable(false);
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.name.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.name.setValue(â˜ƒ);
   }

   @Override
   public void removed() {
      super.removed();
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.minecraft.player.closeContainer();
      }

      return !this.name.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ) && !this.name.canConsumeInput() ? super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ) : true;
   }

   private void onNameChanged(String var1) {
      if (!â˜ƒ.isEmpty()) {
         String â˜ƒ = â˜ƒ;
         Slot â˜ƒx = this.menu.getSlot(0);
         if (â˜ƒx != null && â˜ƒx.hasItem() && !â˜ƒx.getItem().hasCustomHoverName() && â˜ƒ.equals(â˜ƒx.getItem().getHoverName().getString())) {
            â˜ƒ = "";
         }

         this.menu.setItemName(â˜ƒ);
         this.minecraft.player.connection.send(new ServerboundRenameItemPacket(â˜ƒ));
      }
   }

   @Override
   protected void renderLabels(PoseStack var1, int var2, int var3) {
      RenderSystem.disableBlend();
      super.renderLabels(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒ = this.menu.getCost();
      if (â˜ƒ > 0) {
         int â˜ƒxx = 8453920;
         Component â˜ƒx;
         if (â˜ƒ >= 40 && !this.minecraft.player.getAbilities().instabuild) {
            â˜ƒx = TOO_EXPENSIVE_TEXT;
            â˜ƒxx = 16736352;
         } else if (!this.menu.getSlot(2).hasItem()) {
            â˜ƒx = null;
         } else {
            â˜ƒx = new TranslatableComponent("container.repair.cost", â˜ƒ);
            if (!this.menu.getSlot(2).mayPickup(this.player)) {
               â˜ƒxx = 16736352;
            }
         }

         if (â˜ƒx != null) {
            int â˜ƒx = this.imageWidth - 8 - this.font.width(â˜ƒx) - 2;
            int â˜ƒxx = 69;
            fill(â˜ƒ, â˜ƒx - 2, 67, this.imageWidth - 8, 79, 1325400064);
            this.font.drawShadow(â˜ƒ, â˜ƒx, (float)â˜ƒx, 69.0F, â˜ƒxx);
         }
      }
   }

   @Override
   public void renderFg(PoseStack var1, int var2, int var3, float var4) {
      this.name.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void slotChanged(AbstractContainerMenu var1, int var2, ItemStack var3) {
      if (â˜ƒ == 0) {
         this.name.setValue(â˜ƒ.isEmpty() ? "" : â˜ƒ.getHoverName().getString());
         this.name.setEditable(!â˜ƒ.isEmpty());
         this.setFocused(this.name);
      }
   }
}
