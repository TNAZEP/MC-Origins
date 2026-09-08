package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.LockIconButton;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.Difficulty;

public class OptionsScreen extends Screen {
   private static final Option[] OPTION_SCREEN_OPTIONS = new Option[]{Option.FOV};
   private final Screen lastScreen;
   private final Options options;
   private CycleButton<Difficulty> difficultyButton;
   private LockIconButton lockButton;

   public OptionsScreen(Screen var1, Options var2) {
      super(new TranslatableComponent("options.title"));
      this.lastScreen = â˜ƒ;
      this.options = â˜ƒ;
   }

   @Override
   protected void init() {
      int â˜ƒ = 0;

      for(Option â˜ƒx : OPTION_SCREEN_OPTIONS) {
         int â˜ƒxx = this.width / 2 - 155 + â˜ƒ % 2 * 160;
         int â˜ƒxxx = this.height / 6 - 12 + 24 * (â˜ƒ >> 1);
         this.addRenderableWidget(â˜ƒx.createButton(this.minecraft.options, â˜ƒxx, â˜ƒxxx, 150));
         ++â˜ƒ;
      }

      if (this.minecraft.level != null) {
         this.difficultyButton = this.addRenderableWidget(
            CycleButton.builder(Difficulty::getDisplayName)
               .withValues(Difficulty.values())
               .withInitialValue(this.minecraft.level.getDifficulty())
               .create(
                  this.width / 2 - 155 + â˜ƒ % 2 * 160,
                  this.height / 6 - 12 + 24 * (â˜ƒ >> 1),
                  150,
                  20,
                  new TranslatableComponent("options.difficulty"),
                  (var1x, var2) -> this.minecraft.getConnection().send(new ServerboundChangeDifficultyPacket(var2))
               )
         );
         if (this.minecraft.hasSingleplayerServer() && !this.minecraft.level.getLevelData().isHardcore()) {
            this.difficultyButton.setWidth(this.difficultyButton.getWidth() - 20);
            this.lockButton = this.addRenderableWidget(
               new LockIconButton(
                  this.difficultyButton.x + this.difficultyButton.getWidth(),
                  this.difficultyButton.y,
                  var1x -> this.minecraft
                        .setScreen(
                           new ConfirmScreen(
                              this::lockCallback,
                              new TranslatableComponent("difficulty.lock.title"),
                              new TranslatableComponent("difficulty.lock.question", this.minecraft.level.getLevelData().getDifficulty().getDisplayName())
                           )
                        )
               )
            );
            this.lockButton.setLocked(this.minecraft.level.getLevelData().isDifficultyLocked());
            this.lockButton.active = !this.lockButton.isLocked();
            this.difficultyButton.active = !this.lockButton.isLocked();
         } else {
            this.difficultyButton.active = false;
         }
      } else {
         this.addRenderableWidget(
            Option.REALMS_NOTIFICATIONS.createButton(this.options, this.width / 2 - 155 + â˜ƒ % 2 * 160, this.height / 6 - 12 + 24 * (â˜ƒ >> 1), 150)
         );
      }

      this.addRenderableWidget(
         new Button(
            this.width / 2 - 155,
            this.height / 6 + 48 - 6,
            150,
            20,
            new TranslatableComponent("options.skinCustomisation"),
            var1x -> this.minecraft.setScreen(new SkinCustomizationScreen(this, this.options))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 + 5,
            this.height / 6 + 48 - 6,
            150,
            20,
            new TranslatableComponent("options.sounds"),
            var1x -> this.minecraft.setScreen(new SoundOptionsScreen(this, this.options))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 155,
            this.height / 6 + 72 - 6,
            150,
            20,
            new TranslatableComponent("options.video"),
            var1x -> this.minecraft.setScreen(new VideoSettingsScreen(this, this.options))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 + 5,
            this.height / 6 + 72 - 6,
            150,
            20,
            new TranslatableComponent("options.controls"),
            var1x -> this.minecraft.setScreen(new ControlsScreen(this, this.options))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 155,
            this.height / 6 + 96 - 6,
            150,
            20,
            new TranslatableComponent("options.language"),
            var1x -> this.minecraft.setScreen(new LanguageSelectScreen(this, this.options, this.minecraft.getLanguageManager()))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 + 5,
            this.height / 6 + 96 - 6,
            150,
            20,
            new TranslatableComponent("options.chat.title"),
            var1x -> this.minecraft.setScreen(new ChatOptionsScreen(this, this.options))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 155,
            this.height / 6 + 120 - 6,
            150,
            20,
            new TranslatableComponent("options.resourcepack"),
            var1x -> this.minecraft
                  .setScreen(
                     new PackSelectionScreen(
                        this,
                        this.minecraft.getResourcePackRepository(),
                        this::updatePackList,
                        this.minecraft.getResourcePackDirectory(),
                        new TranslatableComponent("resourcePack.title")
                     )
                  )
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 + 5,
            this.height / 6 + 120 - 6,
            150,
            20,
            new TranslatableComponent("options.accessibility.title"),
            var1x -> this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.options))
         )
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, var1x -> this.minecraft.setScreen(this.lastScreen))
      );
   }

   private void updatePackList(PackRepository var1) {
      List<String> â˜ƒ = ImmutableList.copyOf(this.options.resourcePacks);
      this.options.resourcePacks.clear();
      this.options.incompatibleResourcePacks.clear();

      for(Pack â˜ƒx : â˜ƒ.getSelectedPacks()) {
         if (!â˜ƒx.isFixedPosition()) {
            this.options.resourcePacks.add(â˜ƒx.getId());
            if (!â˜ƒx.getCompatibility().isCompatible()) {
               this.options.incompatibleResourcePacks.add(â˜ƒx.getId());
            }
         }
      }

      this.options.save();
      List<String> â˜ƒx = ImmutableList.copyOf(this.options.resourcePacks);
      if (!â˜ƒx.equals(â˜ƒ)) {
         this.minecraft.reloadResourcePacks();
      }
   }

   private void lockCallback(boolean var1) {
      this.minecraft.setScreen(this);
      if (â˜ƒ && this.minecraft.level != null) {
         this.minecraft.getConnection().send(new ServerboundLockDifficultyPacket(true));
         this.lockButton.setLocked(true);
         this.lockButton.active = false;
         this.difficultyButton.active = false;
      }
   }

   @Override
   public void removed() {
      this.options.save();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 15, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
