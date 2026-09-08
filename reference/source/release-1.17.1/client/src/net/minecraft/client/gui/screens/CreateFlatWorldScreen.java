package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public class CreateFlatWorldScreen extends Screen {
   private static final int SLOT_TEX_SIZE = 128;
   private static final int SLOT_BG_SIZE = 18;
   private static final int SLOT_STAT_HEIGHT = 20;
   private static final int SLOT_BG_X = 1;
   private static final int SLOT_BG_Y = 1;
   private static final int SLOT_FG_X = 2;
   private static final int SLOT_FG_Y = 2;
   protected final CreateWorldScreen parent;
   private final Consumer<FlatLevelGeneratorSettings> applySettings;
   FlatLevelGeneratorSettings generator;
   private Component columnType;
   private Component columnHeight;
   private CreateFlatWorldScreen.DetailsList list;
   private Button deleteLayerButton;

   public CreateFlatWorldScreen(CreateWorldScreen var1, Consumer<FlatLevelGeneratorSettings> var2, FlatLevelGeneratorSettings var3) {
      super(new TranslatableComponent("createWorld.customize.flat.title"));
      this.parent = â˜ƒ;
      this.applySettings = â˜ƒ;
      this.generator = â˜ƒ;
   }

   public FlatLevelGeneratorSettings settings() {
      return this.generator;
   }

   public void setConfig(FlatLevelGeneratorSettings var1) {
      this.generator = â˜ƒ;
   }

   @Override
   protected void init() {
      this.columnType = new TranslatableComponent("createWorld.customize.flat.tile");
      this.columnHeight = new TranslatableComponent("createWorld.customize.flat.height");
      this.list = new CreateFlatWorldScreen.DetailsList();
      this.addWidget(this.list);
      this.deleteLayerButton = this.addRenderableWidget(
         new Button(this.width / 2 - 155, this.height - 52, 150, 20, new TranslatableComponent("createWorld.customize.flat.removeLayer"), var1 -> {
            if (this.hasValidSelection()) {
               List<FlatLayerInfo> â˜ƒ = this.generator.getLayersInfo();
               int â˜ƒx = this.list.children().indexOf(this.list.getSelected());
               int â˜ƒxx = â˜ƒ.size() - â˜ƒx - 1;
               â˜ƒ.remove(â˜ƒxx);
               this.list.setSelected(â˜ƒ.isEmpty() ? null : (CreateFlatWorldScreen.DetailsList.Entry)this.list.children().get(Math.min(â˜ƒx, â˜ƒ.size() - 1)));
               this.generator.updateLayers();
               this.list.resetRows();
               this.updateButtonValidity();
            }
         })
      );
      this.addRenderableWidget(new Button(this.width / 2 + 5, this.height - 52, 150, 20, new TranslatableComponent("createWorld.customize.presets"), var1 -> {
         this.minecraft.setScreen(new PresetFlatWorldScreen(this));
         this.generator.updateLayers();
         this.updateButtonValidity();
      }));
      this.addRenderableWidget(new Button(this.width / 2 - 155, this.height - 28, 150, 20, CommonComponents.GUI_DONE, var1 -> {
         this.applySettings.accept(this.generator);
         this.minecraft.setScreen(this.parent);
         this.generator.updateLayers();
      }));
      this.addRenderableWidget(new Button(this.width / 2 + 5, this.height - 28, 150, 20, CommonComponents.GUI_CANCEL, var1 -> {
         this.minecraft.setScreen(this.parent);
         this.generator.updateLayers();
      }));
      this.generator.updateLayers();
      this.updateButtonValidity();
   }

   void updateButtonValidity() {
      this.deleteLayerButton.active = this.hasValidSelection();
   }

   private boolean hasValidSelection() {
      return this.list.getSelected() != null;
   }

   @Override
   public void onClose() {
      this.minecraft.setScreen(this.parent);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.list.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 8, 16777215);
      int â˜ƒ = this.width / 2 - 92 - 16;
      drawString(â˜ƒ, this.font, this.columnType, â˜ƒ, 32, 16777215);
      drawString(â˜ƒ, this.font, this.columnHeight, â˜ƒ + 2 + 213 - this.font.width(this.columnHeight), 32, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   class DetailsList extends ObjectSelectionList<CreateFlatWorldScreen.DetailsList.Entry> {
      public DetailsList() {
         super(
            CreateFlatWorldScreen.this.minecraft,
            CreateFlatWorldScreen.this.width,
            CreateFlatWorldScreen.this.height,
            43,
            CreateFlatWorldScreen.this.height - 60,
            24
         );

         for(int â˜ƒ = 0; â˜ƒ < CreateFlatWorldScreen.this.generator.getLayersInfo().size(); ++â˜ƒ) {
            this.addEntry(new CreateFlatWorldScreen.DetailsList.Entry());
         }
      }

      public void setSelected(@Nullable CreateFlatWorldScreen.DetailsList.Entry var1) {
         super.setSelected(â˜ƒ);
         CreateFlatWorldScreen.this.updateButtonValidity();
      }

      @Override
      protected boolean isFocused() {
         return CreateFlatWorldScreen.this.getFocused() == this;
      }

      @Override
      protected int getScrollbarPosition() {
         return this.width - 70;
      }

      public void resetRows() {
         int â˜ƒ = this.children().indexOf(this.getSelected());
         this.clearEntries();

         for(int â˜ƒx = 0; â˜ƒx < CreateFlatWorldScreen.this.generator.getLayersInfo().size(); ++â˜ƒx) {
            this.addEntry(new CreateFlatWorldScreen.DetailsList.Entry());
         }

         List<CreateFlatWorldScreen.DetailsList.Entry> â˜ƒx = this.children();
         if (â˜ƒ >= 0 && â˜ƒ < â˜ƒx.size()) {
            this.setSelected((CreateFlatWorldScreen.DetailsList.Entry)â˜ƒx.get(â˜ƒ));
         }
      }

      class Entry extends ObjectSelectionList.Entry<CreateFlatWorldScreen.DetailsList.Entry> {
         @Override
         public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            FlatLayerInfo â˜ƒx = (FlatLayerInfo)CreateFlatWorldScreen.this.generator
               .getLayersInfo()
               .get(CreateFlatWorldScreen.this.generator.getLayersInfo().size() - â˜ƒ - 1);
            BlockState â˜ƒxx = â˜ƒx.getBlockState();
            ItemStack â˜ƒxxx = this.getDisplayItem(â˜ƒxx);
            this.blitSlot(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx);
            CreateFlatWorldScreen.this.font.draw(â˜ƒ, â˜ƒxxx.getHoverName(), (float)(â˜ƒ + 18 + 5), (float)(â˜ƒ + 3), 16777215);
            Component â˜ƒ;
            if (â˜ƒ == 0) {
               â˜ƒ = new TranslatableComponent("createWorld.customize.flat.layer.top", â˜ƒx.getHeight());
            } else if (â˜ƒ == CreateFlatWorldScreen.this.generator.getLayersInfo().size() - 1) {
               â˜ƒ = new TranslatableComponent("createWorld.customize.flat.layer.bottom", â˜ƒx.getHeight());
            } else {
               â˜ƒ = new TranslatableComponent("createWorld.customize.flat.layer", â˜ƒx.getHeight());
            }

            CreateFlatWorldScreen.this.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ + 2 + 213 - CreateFlatWorldScreen.this.font.width(â˜ƒ)), (float)(â˜ƒ + 3), 16777215);
         }

         private ItemStack getDisplayItem(BlockState var1) {
            Item â˜ƒ = â˜ƒ.getBlock().asItem();
            if (â˜ƒ == Items.AIR) {
               if (â˜ƒ.is(Blocks.WATER)) {
                  â˜ƒ = Items.WATER_BUCKET;
               } else if (â˜ƒ.is(Blocks.LAVA)) {
                  â˜ƒ = Items.LAVA_BUCKET;
               }
            }

            return new ItemStack(â˜ƒ);
         }

         @Override
         public Component getNarration() {
            FlatLayerInfo â˜ƒ = (FlatLayerInfo)CreateFlatWorldScreen.this.generator
               .getLayersInfo()
               .get(CreateFlatWorldScreen.this.generator.getLayersInfo().size() - DetailsList.this.children().indexOf(this) - 1);
            ItemStack â˜ƒx = this.getDisplayItem(â˜ƒ.getBlockState());
            return (Component)(!â˜ƒx.isEmpty() ? new TranslatableComponent("narrator.select", â˜ƒx.getHoverName()) : TextComponent.EMPTY);
         }

         @Override
         public boolean mouseClicked(double var1, double var3, int var5) {
            if (â˜ƒ == 0) {
               DetailsList.this.setSelected(this);
               return true;
            } else {
               return false;
            }
         }

         private void blitSlot(PoseStack var1, int var2, int var3, ItemStack var4) {
            this.blitSlotBg(â˜ƒ, â˜ƒ + 1, â˜ƒ + 1);
            if (!â˜ƒ.isEmpty()) {
               CreateFlatWorldScreen.this.itemRenderer.renderGuiItem(â˜ƒ, â˜ƒ + 2, â˜ƒ + 2);
            }
         }

         private void blitSlotBg(PoseStack var1, int var2, int var3) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GuiComponent.STATS_ICON_LOCATION);
            GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, CreateFlatWorldScreen.this.getBlitOffset(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}
