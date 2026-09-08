package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.FullscreenResolutionProgressOption;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;

public class VideoSettingsScreen extends OptionsSubScreen {
   private static final Component FABULOUS = new TranslatableComponent("options.graphics.fabulous").withStyle(ChatFormatting.ITALIC);
   private static final Component WARNING_MESSAGE = new TranslatableComponent("options.graphics.warning.message", FABULOUS, FABULOUS);
   private static final Component WARNING_TITLE = new TranslatableComponent("options.graphics.warning.title").withStyle(ChatFormatting.RED);
   private static final Component BUTTON_ACCEPT = new TranslatableComponent("options.graphics.warning.accept");
   private static final Component BUTTON_CANCEL = new TranslatableComponent("options.graphics.warning.cancel");
   private static final Component NEW_LINE = new TextComponent("\n");
   private static final Option[] OPTIONS = new Option[]{
      Option.GRAPHICS,
      Option.RENDER_DISTANCE,
      Option.AMBIENT_OCCLUSION,
      Option.FRAMERATE_LIMIT,
      Option.ENABLE_VSYNC,
      Option.VIEW_BOBBING,
      Option.GUI_SCALE,
      Option.ATTACK_INDICATOR,
      Option.GAMMA,
      Option.RENDER_CLOUDS,
      Option.USE_FULLSCREEN,
      Option.PARTICLES,
      Option.MIPMAP_LEVELS,
      Option.ENTITY_SHADOWS,
      Option.SCREEN_EFFECTS_SCALE,
      Option.ENTITY_DISTANCE_SCALING,
      Option.FOV_EFFECTS_SCALE
   };
   private OptionsList list;
   private final GpuWarnlistManager gpuWarnlistManager;
   private final int oldMipmaps;

   public VideoSettingsScreen(Screen var1, Options var2) {
      super(â˜ƒ, â˜ƒ, new TranslatableComponent("options.videoTitle"));
      this.gpuWarnlistManager = â˜ƒ.minecraft.getGpuWarnlistManager();
      this.gpuWarnlistManager.resetWarnings();
      if (â˜ƒ.graphicsMode == GraphicsStatus.FABULOUS) {
         this.gpuWarnlistManager.dismissWarning();
      }

      this.oldMipmaps = â˜ƒ.mipmapLevels;
   }

   @Override
   protected void init() {
      this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
      this.list.addBig(new FullscreenResolutionProgressOption(this.minecraft.getWindow()));
      this.list.addBig(Option.BIOME_BLEND_RADIUS);
      this.list.addSmall(OPTIONS);
      this.addWidget(this.list);
      this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, var1 -> {
         this.minecraft.options.save();
         this.minecraft.getWindow().changeFullscreenVideoMode();
         this.minecraft.setScreen(this.lastScreen);
      }));
   }

   @Override
   public void removed() {
      if (this.options.mipmapLevels != this.oldMipmaps) {
         this.minecraft.updateMaxMipLevel(this.options.mipmapLevels);
         this.minecraft.delayTextureReload();
      }

      super.removed();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      int â˜ƒ = this.options.guiScale;
      if (super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         if (this.options.guiScale != â˜ƒ) {
            this.minecraft.resizeDisplay();
         }

         if (this.gpuWarnlistManager.isShowingWarning()) {
            List<Component> â˜ƒx = Lists.<Component>newArrayList(WARNING_MESSAGE, NEW_LINE);
            String â˜ƒxx = this.gpuWarnlistManager.getRendererWarnings();
            if (â˜ƒxx != null) {
               â˜ƒx.add(NEW_LINE);
               â˜ƒx.add(new TranslatableComponent("options.graphics.warning.renderer", â˜ƒxx).withStyle(ChatFormatting.GRAY));
            }

            String â˜ƒx = this.gpuWarnlistManager.getVendorWarnings();
            if (â˜ƒx != null) {
               â˜ƒx.add(NEW_LINE);
               â˜ƒx.add(new TranslatableComponent("options.graphics.warning.vendor", â˜ƒx).withStyle(ChatFormatting.GRAY));
            }

            String â˜ƒx = this.gpuWarnlistManager.getVersionWarnings();
            if (â˜ƒx != null) {
               â˜ƒx.add(NEW_LINE);
               â˜ƒx.add(new TranslatableComponent("options.graphics.warning.version", â˜ƒx).withStyle(ChatFormatting.GRAY));
            }

            this.minecraft.setScreen(new PopupScreen(WARNING_TITLE, â˜ƒx, ImmutableList.of(new PopupScreen.ButtonOption(BUTTON_ACCEPT, var1x -> {
               this.options.graphicsMode = GraphicsStatus.FABULOUS;
               Minecraft.getInstance().levelRenderer.allChanged();
               this.gpuWarnlistManager.dismissWarning();
               this.minecraft.setScreen(this);
            }), new PopupScreen.ButtonOption(BUTTON_CANCEL, var1x -> {
               this.gpuWarnlistManager.dismissWarningAndSkipFabulous();
               this.minecraft.setScreen(this);
            }))));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      int â˜ƒ = this.options.guiScale;
      if (super.mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (this.list.mouseReleased(â˜ƒ, â˜ƒ, â˜ƒ)) {
         if (this.options.guiScale != â˜ƒ) {
            this.minecraft.resizeDisplay();
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.list.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 5, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      List<FormattedCharSequence> â˜ƒ = tooltipAt(this.list, â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
