package net.minecraft.client.gui.screens.worldselection;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectWorldScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final Screen lastScreen;
   private List<FormattedCharSequence> toolTip;
   private Button deleteButton;
   private Button selectButton;
   private Button renameButton;
   private Button copyButton;
   protected EditBox searchBox;
   private WorldSelectionList list;

   public SelectWorldScreen(Screen var1) {
      super(new TranslatableComponent("selectWorld.title"));
      this.lastScreen = â˜ƒ;
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      return super.mouseScrolled(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick() {
      this.searchBox.tick();
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.searchBox = new EditBox(this.font, this.width / 2 - 100, 22, 200, 20, this.searchBox, new TranslatableComponent("selectWorld.search"));
      this.searchBox.setResponder(var1 -> this.list.refreshList(() -> var1, false));
      this.list = new WorldSelectionList(this, this.minecraft, this.width, this.height, 48, this.height - 64, 36, () -> this.searchBox.getValue(), this.list);
      this.addWidget(this.searchBox);
      this.addWidget(this.list);
      this.selectButton = this.addRenderableWidget(
         new Button(
            this.width / 2 - 154,
            this.height - 52,
            150,
            20,
            new TranslatableComponent("selectWorld.select"),
            var1 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::joinWorld)
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 + 4,
            this.height - 52,
            150,
            20,
            new TranslatableComponent("selectWorld.create"),
            var1 -> this.minecraft.setScreen(CreateWorldScreen.create(this))
         )
      );
      this.renameButton = this.addRenderableWidget(
         new Button(
            this.width / 2 - 154,
            this.height - 28,
            72,
            20,
            new TranslatableComponent("selectWorld.edit"),
            var1 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::editWorld)
         )
      );
      this.deleteButton = this.addRenderableWidget(
         new Button(
            this.width / 2 - 76,
            this.height - 28,
            72,
            20,
            new TranslatableComponent("selectWorld.delete"),
            var1 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::deleteWorld)
         )
      );
      this.copyButton = this.addRenderableWidget(
         new Button(
            this.width / 2 + 4,
            this.height - 28,
            72,
            20,
            new TranslatableComponent("selectWorld.recreate"),
            var1 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::recreateWorld)
         )
      );
      this.addRenderableWidget(
         new Button(this.width / 2 + 82, this.height - 28, 72, 20, CommonComponents.GUI_CANCEL, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
      this.updateButtonStatus(false);
      this.setInitialFocus(this.searchBox);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ) ? true : this.searchBox.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void onClose() {
      this.minecraft.setScreen(this.lastScreen);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      return this.searchBox.charTyped(â˜ƒ, â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.toolTip = null;
      this.list.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.searchBox.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 8, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.toolTip != null) {
         this.renderTooltip(â˜ƒ, this.toolTip, â˜ƒ, â˜ƒ);
      }
   }

   public void setToolTip(List<FormattedCharSequence> var1) {
      this.toolTip = â˜ƒ;
   }

   public void updateButtonStatus(boolean var1) {
      this.selectButton.active = â˜ƒ;
      this.deleteButton.active = â˜ƒ;
      this.renameButton.active = â˜ƒ;
      this.copyButton.active = â˜ƒ;
   }

   @Override
   public void removed() {
      if (this.list != null) {
         this.list.children().forEach(WorldSelectionList.WorldListEntry::close);
      }
   }
}
