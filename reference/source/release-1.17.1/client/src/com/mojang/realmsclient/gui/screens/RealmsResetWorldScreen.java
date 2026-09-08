package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import com.mojang.realmsclient.util.task.LongRunningTask;
import com.mojang.realmsclient.util.task.ResettingGeneratedWorldTask;
import com.mojang.realmsclient.util.task.ResettingTemplateWorldTask;
import com.mojang.realmsclient.util.task.SwitchSlotTask;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsResetWorldScreen extends RealmsScreen {
   static final Logger LOGGER = LogManager.getLogger();
   private final Screen lastScreen;
   private final RealmsServer serverData;
   private Component subtitle = new TranslatableComponent("mco.reset.world.warning");
   private Component buttonTitle = CommonComponents.GUI_CANCEL;
   private int subtitleColor = 16711680;
   private static final ResourceLocation SLOT_FRAME_LOCATION = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
   private static final ResourceLocation UPLOAD_LOCATION = new ResourceLocation("realms", "textures/gui/realms/upload.png");
   private static final ResourceLocation ADVENTURE_MAP_LOCATION = new ResourceLocation("realms", "textures/gui/realms/adventure.png");
   private static final ResourceLocation SURVIVAL_SPAWN_LOCATION = new ResourceLocation("realms", "textures/gui/realms/survival_spawn.png");
   private static final ResourceLocation NEW_WORLD_LOCATION = new ResourceLocation("realms", "textures/gui/realms/new_world.png");
   private static final ResourceLocation EXPERIENCE_LOCATION = new ResourceLocation("realms", "textures/gui/realms/experience.png");
   private static final ResourceLocation INSPIRATION_LOCATION = new ResourceLocation("realms", "textures/gui/realms/inspiration.png");
   WorldTemplatePaginatedList templates;
   WorldTemplatePaginatedList adventuremaps;
   WorldTemplatePaginatedList experiences;
   WorldTemplatePaginatedList inspirations;
   public int slot = -1;
   private Component resetTitle = new TranslatableComponent("mco.reset.world.resetting.screen.title");
   private final Runnable resetWorldRunnable;
   private final Runnable callback;

   public RealmsResetWorldScreen(Screen var1, RealmsServer var2, Component var3, Runnable var4, Runnable var5) {
      super(â˜ƒ);
      this.lastScreen = â˜ƒ;
      this.serverData = â˜ƒ;
      this.resetWorldRunnable = â˜ƒ;
      this.callback = â˜ƒ;
   }

   public RealmsResetWorldScreen(Screen var1, RealmsServer var2, Runnable var3, Runnable var4) {
      this(â˜ƒ, â˜ƒ, new TranslatableComponent("mco.reset.world.title"), â˜ƒ, â˜ƒ);
   }

   public RealmsResetWorldScreen(Screen var1, RealmsServer var2, Component var3, Component var4, int var5, Component var6, Runnable var7, Runnable var8) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.subtitle = â˜ƒ;
      this.subtitleColor = â˜ƒ;
      this.buttonTitle = â˜ƒ;
   }

   public void setSlot(int var1) {
      this.slot = â˜ƒ;
   }

   public void setResetTitle(Component var1) {
      this.resetTitle = â˜ƒ;
   }

   @Override
   public void init() {
      this.addRenderableWidget(new Button(this.width / 2 - 40, row(14) - 10, 80, 20, this.buttonTitle, var1 -> this.minecraft.setScreen(this.lastScreen)));
      (new Thread("Realms-reset-world-fetcher") {
         public void run() {
            RealmsClient â˜ƒ = RealmsClient.create();

            try {
               WorldTemplatePaginatedList â˜ƒx = â˜ƒ.fetchWorldTemplates(1, 10, RealmsServer.WorldType.NORMAL);
               WorldTemplatePaginatedList â˜ƒxx = â˜ƒ.fetchWorldTemplates(1, 10, RealmsServer.WorldType.ADVENTUREMAP);
               WorldTemplatePaginatedList â˜ƒxxx = â˜ƒ.fetchWorldTemplates(1, 10, RealmsServer.WorldType.EXPERIENCE);
               WorldTemplatePaginatedList â˜ƒxxxx = â˜ƒ.fetchWorldTemplates(1, 10, RealmsServer.WorldType.INSPIRATION);
               RealmsResetWorldScreen.this.minecraft.execute(() -> {
                  RealmsResetWorldScreen.this.templates = â˜ƒ;
                  RealmsResetWorldScreen.this.adventuremaps = â˜ƒ;
                  RealmsResetWorldScreen.this.experiences = â˜ƒ;
                  RealmsResetWorldScreen.this.inspirations = â˜ƒ;
               });
            } catch (RealmsServiceException var6) {
               RealmsResetWorldScreen.LOGGER.error("Couldn't fetch templates in reset world", var6);
            }
         }
      }).start();
      this.addLabel(new RealmsLabel(this.subtitle, this.width / 2, 22, this.subtitleColor));
      this.addRenderableWidget(
         new RealmsResetWorldScreen.FrameButton(
            this.frame(1),
            row(0) + 10,
            new TranslatableComponent("mco.reset.world.generate"),
            NEW_WORLD_LOCATION,
            var1 -> this.minecraft.setScreen(new RealmsResetNormalWorldScreen(this::generationSelectionCallback, this.title))
         )
      );
      this.addRenderableWidget(
         new RealmsResetWorldScreen.FrameButton(
            this.frame(2),
            row(0) + 10,
            new TranslatableComponent("mco.reset.world.upload"),
            UPLOAD_LOCATION,
            var1 -> this.minecraft
                  .setScreen(
                     new RealmsSelectFileToUploadScreen(this.serverData.id, this.slot != -1 ? this.slot : this.serverData.activeSlot, this, this.callback)
                  )
         )
      );
      this.addRenderableWidget(
         new RealmsResetWorldScreen.FrameButton(
            this.frame(3),
            row(0) + 10,
            new TranslatableComponent("mco.reset.world.template"),
            SURVIVAL_SPAWN_LOCATION,
            var1 -> this.minecraft
                  .setScreen(
                     new RealmsSelectWorldTemplateScreen(
                        new TranslatableComponent("mco.reset.world.template"), this::templateSelectionCallback, RealmsServer.WorldType.NORMAL, this.templates
                     )
                  )
         )
      );
      this.addRenderableWidget(
         new RealmsResetWorldScreen.FrameButton(
            this.frame(1),
            row(6) + 20,
            new TranslatableComponent("mco.reset.world.adventure"),
            ADVENTURE_MAP_LOCATION,
            var1 -> this.minecraft
                  .setScreen(
                     new RealmsSelectWorldTemplateScreen(
                        new TranslatableComponent("mco.reset.world.adventure"),
                        this::templateSelectionCallback,
                        RealmsServer.WorldType.ADVENTUREMAP,
                        this.adventuremaps
                     )
                  )
         )
      );
      this.addRenderableWidget(
         new RealmsResetWorldScreen.FrameButton(
            this.frame(2),
            row(6) + 20,
            new TranslatableComponent("mco.reset.world.experience"),
            EXPERIENCE_LOCATION,
            var1 -> this.minecraft
                  .setScreen(
                     new RealmsSelectWorldTemplateScreen(
                        new TranslatableComponent("mco.reset.world.experience"),
                        this::templateSelectionCallback,
                        RealmsServer.WorldType.EXPERIENCE,
                        this.experiences
                     )
                  )
         )
      );
      this.addRenderableWidget(
         new RealmsResetWorldScreen.FrameButton(
            this.frame(3),
            row(6) + 20,
            new TranslatableComponent("mco.reset.world.inspiration"),
            INSPIRATION_LOCATION,
            var1 -> this.minecraft
                  .setScreen(
                     new RealmsSelectWorldTemplateScreen(
                        new TranslatableComponent("mco.reset.world.inspiration"),
                        this::templateSelectionCallback,
                        RealmsServer.WorldType.INSPIRATION,
                        this.inspirations
                     )
                  )
         )
      );
   }

   @Override
   public Component getNarrationMessage() {
      return CommonComponents.joinForNarration(this.getTitle(), this.createLabelNarration());
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.minecraft.setScreen(this.lastScreen);
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private int frame(int var1) {
      return this.width / 2 - 130 + (â˜ƒ - 1) * 100;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 7, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   void drawFrame(PoseStack var1, int var2, int var3, Component var4, ResourceLocation var5, boolean var6, boolean var7) {
      RenderSystem.setShaderTexture(0, â˜ƒ);
      if (â˜ƒ) {
         RenderSystem.setShaderColor(0.56F, 0.56F, 0.56F, 1.0F);
      } else {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }

      GuiComponent.blit(â˜ƒ, â˜ƒ + 2, â˜ƒ + 14, 0.0F, 0.0F, 56, 56, 56, 56);
      RenderSystem.setShaderTexture(0, SLOT_FRAME_LOCATION);
      if (â˜ƒ) {
         RenderSystem.setShaderColor(0.56F, 0.56F, 0.56F, 1.0F);
      } else {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }

      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ + 12, 0.0F, 0.0F, 60, 60, 60, 60);
      int â˜ƒ = â˜ƒ ? 10526880 : 16777215;
      drawCenteredString(â˜ƒ, this.font, â˜ƒ, â˜ƒ + 30, â˜ƒ, â˜ƒ);
   }

   private void startTask(LongRunningTask var1) {
      this.minecraft.setScreen(new RealmsLongRunningMcoTaskScreen(this.lastScreen, â˜ƒ));
   }

   public void switchSlot(Runnable var1) {
      this.startTask(new SwitchSlotTask(this.serverData.id, this.slot, () -> this.minecraft.execute(â˜ƒ)));
   }

   private void templateSelectionCallback(@Nullable WorldTemplate var1) {
      this.minecraft.setScreen(this);
      if (â˜ƒ != null) {
         this.resetWorld(() -> this.startTask(new ResettingTemplateWorldTask(â˜ƒ, this.serverData.id, this.resetTitle, this.resetWorldRunnable)));
      }
   }

   private void generationSelectionCallback(@Nullable WorldGenerationInfo var1) {
      this.minecraft.setScreen(this);
      if (â˜ƒ != null) {
         this.resetWorld(() -> this.startTask(new ResettingGeneratedWorldTask(â˜ƒ, this.serverData.id, this.resetTitle, this.resetWorldRunnable)));
      }
   }

   private void resetWorld(Runnable var1) {
      if (this.slot == -1) {
         â˜ƒ.run();
      } else {
         this.switchSlot(â˜ƒ);
      }
   }

   class FrameButton extends Button {
      private final ResourceLocation image;

      public FrameButton(int var2, int var3, Component var4, ResourceLocation var5, Button.OnPress var6) {
         super(â˜ƒ, â˜ƒ, 60, 72, â˜ƒ, â˜ƒ);
         this.image = â˜ƒ;
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         RealmsResetWorldScreen.this.drawFrame(â˜ƒ, this.x, this.y, this.getMessage(), this.image, this.isHovered(), this.isMouseOver((double)â˜ƒ, (double)â˜ƒ));
      }
   }
}
