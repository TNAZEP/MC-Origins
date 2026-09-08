package net.minecraft.client.gui.screens;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String DEMO_LEVEL_ID = "Demo_World";
   public static final String COPYRIGHT_TEXT = "Copyright Mojang AB. Do not distribute!";
   public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
   private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
   private static final ResourceLocation ACCESSIBILITY_TEXTURE = new ResourceLocation("textures/gui/accessibility.png");
   private final boolean minceraftEasterEgg;
   @Nullable
   private String splash;
   private Button resetDemoButton;
   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation("textures/gui/title/edition.png");
   private Screen realmsNotificationsScreen;
   private int copyrightWidth;
   private int copyrightX;
   private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
   private final boolean fading;
   private long fadeInStart;

   public TitleScreen() {
      this(false);
   }

   public TitleScreen(boolean var1) {
      super(new TranslatableComponent("narrator.screen.title"));
      this.fading = â˜ƒ;
      this.minceraftEasterEgg = (double)new Random().nextFloat() < 1.0E-4;
   }

   private boolean realmsNotificationsEnabled() {
      return this.minecraft.options.realmsNotifications && this.realmsNotificationsScreen != null;
   }

   @Override
   public void tick() {
      if (this.realmsNotificationsEnabled()) {
         this.realmsNotificationsScreen.tick();
      }
   }

   public static CompletableFuture<Void> preloadResources(TextureManager var0, Executor var1) {
      return CompletableFuture.allOf(
         â˜ƒ.preload(MINECRAFT_LOGO, â˜ƒ), â˜ƒ.preload(MINECRAFT_EDITION, â˜ƒ), â˜ƒ.preload(PANORAMA_OVERLAY, â˜ƒ), CUBE_MAP.preload(â˜ƒ, â˜ƒ)
      );
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   protected void init() {
      if (this.splash == null) {
         this.splash = this.minecraft.getSplashManager().getSplash();
      }

      this.copyrightWidth = this.font.width("Copyright Mojang AB. Do not distribute!");
      this.copyrightX = this.width - this.copyrightWidth - 2;
      int â˜ƒ = 24;
      int â˜ƒx = this.height / 4 + 48;
      if (this.minecraft.isDemo()) {
         this.createDemoMenuOptions(â˜ƒx, 24);
      } else {
         this.createNormalMenuOptions(â˜ƒx, 24);
      }

      this.addRenderableWidget(
         new ImageButton(
            this.width / 2 - 124,
            â˜ƒx + 72 + 12,
            20,
            20,
            0,
            106,
            20,
            Button.WIDGETS_LOCATION,
            256,
            256,
            var1x -> this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager())),
            new TranslatableComponent("narrator.button.language")
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100,
            â˜ƒx + 72 + 12,
            98,
            20,
            new TranslatableComponent("menu.options"),
            var1x -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options))
         )
      );
      this.addRenderableWidget(new Button(this.width / 2 + 2, â˜ƒx + 72 + 12, 98, 20, new TranslatableComponent("menu.quit"), var1x -> this.minecraft.stop()));
      this.addRenderableWidget(
         new ImageButton(
            this.width / 2 + 104,
            â˜ƒx + 72 + 12,
            20,
            20,
            0,
            0,
            20,
            ACCESSIBILITY_TEXTURE,
            32,
            64,
            var1x -> this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options)),
            new TranslatableComponent("narrator.button.accessibility")
         )
      );
      this.minecraft.setConnectedToRealms(false);
      if (this.minecraft.options.realmsNotifications && this.realmsNotificationsScreen == null) {
         this.realmsNotificationsScreen = new RealmsNotificationsScreen();
      }

      if (this.realmsNotificationsEnabled()) {
         this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
      }
   }

   private void createNormalMenuOptions(int var1, int var2) {
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100, â˜ƒ, 200, 20, new TranslatableComponent("menu.singleplayer"), var1x -> this.minecraft.setScreen(new SelectWorldScreen(this))
         )
      );
      boolean â˜ƒ = this.minecraft.allowsMultiplayer();
      Button.OnTooltip â˜ƒx = â˜ƒ ? Button.NO_TOOLTIP : new Button.OnTooltip() {
         private final Component text = new TranslatableComponent("title.multiplayer.disabled");

         @Override
         public void onTooltip(Button var1, PoseStack var2, int var3, int var4) {
            if (!â˜ƒ.active) {
               TitleScreen.this.renderTooltip(â˜ƒ, TitleScreen.this.minecraft.font.split(this.text, Math.max(TitleScreen.this.width / 2 - 43, 170)), â˜ƒ, â˜ƒ);
            }
         }

         @Override
         public void narrateTooltip(Consumer<Component> var1) {
            â˜ƒ.accept(this.text);
         }
      };
      this.addRenderableWidget(new Button(this.width / 2 - 100, â˜ƒ + â˜ƒ * 1, 200, 20, new TranslatableComponent("menu.multiplayer"), var1x -> {
         Screen â˜ƒ = (Screen)(this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this));
         this.minecraft.setScreen(â˜ƒ);
      }, â˜ƒx)).active = â˜ƒ;
      this.addRenderableWidget(
            new Button(this.width / 2 - 100, â˜ƒ + â˜ƒ * 2, 200, 20, new TranslatableComponent("menu.online"), var1x -> this.realmsButtonClicked(), â˜ƒx)
         )
         .active = â˜ƒ;
   }

   private void createDemoMenuOptions(int var1, int var2) {
      boolean â˜ƒ = this.checkDemoWorldPresence();
      this.addRenderableWidget(new Button(this.width / 2 - 100, â˜ƒ, 200, 20, new TranslatableComponent("menu.playdemo"), var2x -> {
         if (â˜ƒ) {
            this.minecraft.loadLevel("Demo_World");
         } else {
            RegistryAccess.RegistryHolder â˜ƒ = RegistryAccess.builtin();
            this.minecraft.createLevel("Demo_World", MinecraftServer.DEMO_SETTINGS, â˜ƒ, WorldGenSettings.demoSettings(â˜ƒ));
         }
      }));
      this.resetDemoButton = this.addRenderableWidget(
         new Button(
            this.width / 2 - 100,
            â˜ƒ + â˜ƒ * 1,
            200,
            20,
            new TranslatableComponent("menu.resetdemo"),
            var1x -> {
               LevelStorageSource â˜ƒ = this.minecraft.getLevelSource();
      
               try (LevelStorageSource.LevelStorageAccess â˜ƒx = â˜ƒ.createAccess("Demo_World")) {
                  LevelSummary â˜ƒxx = â˜ƒx.getSummary();
                  if (â˜ƒxx != null) {
                     this.minecraft
                        .setScreen(
                           new ConfirmScreen(
                              this::confirmDemo,
                              new TranslatableComponent("selectWorld.deleteQuestion"),
                              new TranslatableComponent("selectWorld.deleteWarning", â˜ƒxx.getLevelName()),
                              new TranslatableComponent("selectWorld.deleteButton"),
                              CommonComponents.GUI_CANCEL
                           )
                        );
                  }
               } catch (IOException var8) {
                  SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
                  LOGGER.warn("Failed to access demo world", var8);
               }
            }
         )
      );
      this.resetDemoButton.active = â˜ƒ;
   }

   private boolean checkDemoWorldPresence() {
      try {
         boolean var2;
         try (LevelStorageSource.LevelStorageAccess â˜ƒ = this.minecraft.getLevelSource().createAccess("Demo_World")) {
            var2 = â˜ƒ.getSummary() != null;
         }

         return var2;
      } catch (IOException var6) {
         SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
         LOGGER.warn("Failed to read demo world data", var6);
         return false;
      }
   }

   private void realmsButtonClicked() {
      this.minecraft.setScreen(new RealmsMainScreen(this));
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.fadeInStart == 0L && this.fading) {
         this.fadeInStart = Util.getMillis();
      }

      float â˜ƒ = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
      this.panorama.render(â˜ƒ, Mth.clamp(â˜ƒ, 0.0F, 1.0F));
      int â˜ƒx = 274;
      int â˜ƒxx = this.width / 2 - 137;
      int â˜ƒxxx = 30;
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(â˜ƒ, 0.0F, 1.0F)) : 1.0F);
      blit(â˜ƒ, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
      float â˜ƒxxxx = this.fading ? Mth.clamp(â˜ƒ - 1.0F, 0.0F, 1.0F) : 1.0F;
      int â˜ƒxxxxx = Mth.ceil(â˜ƒxxxx * 255.0F) << 24;
      if ((â˜ƒxxxxx & -67108864) != 0) {
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, MINECRAFT_LOGO);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, â˜ƒxxxx);
         if (this.minceraftEasterEgg) {
            this.blitOutlineBlack(â˜ƒxx, 30, (var2x, var3x) -> {
               this.blit(â˜ƒ, var2x + 0, var3x, 0, 0, 99, 44);
               this.blit(â˜ƒ, var2x + 99, var3x, 129, 0, 27, 44);
               this.blit(â˜ƒ, var2x + 99 + 26, var3x, 126, 0, 3, 44);
               this.blit(â˜ƒ, var2x + 99 + 26 + 3, var3x, 99, 0, 26, 44);
               this.blit(â˜ƒ, var2x + 155, var3x, 0, 45, 155, 44);
            });
         } else {
            this.blitOutlineBlack(â˜ƒxx, 30, (var2x, var3x) -> {
               this.blit(â˜ƒ, var2x + 0, var3x, 0, 0, 155, 44);
               this.blit(â˜ƒ, var2x + 155, var3x, 0, 45, 155, 44);
            });
         }

         RenderSystem.setShaderTexture(0, MINECRAFT_EDITION);
         blit(â˜ƒ, â˜ƒxx + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);
         if (this.splash != null) {
            â˜ƒ.pushPose();
            â˜ƒ.translate((double)(this.width / 2 + 90), 70.0, 0.0);
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
            float â˜ƒxxxxxx = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * (float) (Math.PI * 2)) * 0.1F);
            â˜ƒxxxxxx = â˜ƒxxxxxx * 100.0F / (float)(this.font.width(this.splash) + 32);
            â˜ƒ.scale(â˜ƒxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxx);
            drawCenteredString(â˜ƒ, this.font, this.splash, 0, -8, 16776960 | â˜ƒxxxxx);
            â˜ƒ.popPose();
         }

         String â˜ƒxxxxxx = "Minecraft " + SharedConstants.getCurrentVersion().getName();
         if (this.minecraft.isDemo()) {
            â˜ƒxxxxxx = â˜ƒxxxxxx + " Demo";
         } else {
            â˜ƒxxxxxx = â˜ƒxxxxxx + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
         }

         if (this.minecraft.isProbablyModded()) {
            â˜ƒxxxxxx = â˜ƒxxxxxx + I18n.get("menu.modded");
         }

         drawString(â˜ƒ, this.font, â˜ƒxxxxxx, 2, this.height - 10, 16777215 | â˜ƒxxxxx);
         drawString(â˜ƒ, this.font, "Copyright Mojang AB. Do not distribute!", this.copyrightX, this.height - 10, 16777215 | â˜ƒxxxxx);
         if (â˜ƒ > this.copyrightX && â˜ƒ < this.copyrightX + this.copyrightWidth && â˜ƒ > this.height - 10 && â˜ƒ < this.height) {
            fill(â˜ƒ, this.copyrightX, this.height - 1, this.copyrightX + this.copyrightWidth, this.height, 16777215 | â˜ƒxxxxx);
         }

         for(GuiEventListener â˜ƒxxxxxx : this.children()) {
            if (â˜ƒxxxxxx instanceof AbstractWidget) {
               ((AbstractWidget)â˜ƒxxxxxx).setAlpha(â˜ƒxxxx);
            }
         }

         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (this.realmsNotificationsEnabled() && â˜ƒxxxx >= 1.0F) {
            this.realmsNotificationsScreen.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (this.realmsNotificationsEnabled() && this.realmsNotificationsScreen.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         if (â˜ƒ > (double)this.copyrightX
            && â˜ƒ < (double)(this.copyrightX + this.copyrightWidth)
            && â˜ƒ > (double)(this.height - 10)
            && â˜ƒ < (double)this.height) {
            this.minecraft.setScreen(new WinScreen(false, Runnables.doNothing()));
         }

         return false;
      }
   }

   @Override
   public void removed() {
      if (this.realmsNotificationsScreen != null) {
         this.realmsNotificationsScreen.removed();
      }
   }

   private void confirmDemo(boolean var1) {
      if (â˜ƒ) {
         try (LevelStorageSource.LevelStorageAccess â˜ƒ = this.minecraft.getLevelSource().createAccess("Demo_World")) {
            â˜ƒ.deleteLevel();
         } catch (IOException var7) {
            SystemToast.onWorldDeleteFailure(this.minecraft, "Demo_World");
            LOGGER.warn("Failed to delete demo world", var7);
         }
      }

      this.minecraft.setScreen(this);
   }
}
