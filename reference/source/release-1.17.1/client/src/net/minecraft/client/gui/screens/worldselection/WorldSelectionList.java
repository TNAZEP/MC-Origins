package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ErrorScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSelectionList extends ObjectSelectionList<WorldSelectionList.WorldListEntry> {
   static final Logger LOGGER = LogManager.getLogger();
   static final DateFormat DATE_FORMAT = new SimpleDateFormat();
   static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
   static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/world_selection.png");
   static final Component FROM_NEWER_TOOLTIP_1 = new TranslatableComponent("selectWorld.tooltip.fromNewerVersion1").withStyle(ChatFormatting.RED);
   static final Component FROM_NEWER_TOOLTIP_2 = new TranslatableComponent("selectWorld.tooltip.fromNewerVersion2").withStyle(ChatFormatting.RED);
   static final Component SNAPSHOT_TOOLTIP_1 = new TranslatableComponent("selectWorld.tooltip.snapshot1").withStyle(ChatFormatting.GOLD);
   static final Component SNAPSHOT_TOOLTIP_2 = new TranslatableComponent("selectWorld.tooltip.snapshot2").withStyle(ChatFormatting.GOLD);
   static final Component WORLD_LOCKED_TOOLTIP = new TranslatableComponent("selectWorld.locked").withStyle(ChatFormatting.RED);
   static final Component WORLD_PRE_WORLDHEIGHT_TOOLTIP = new TranslatableComponent("selectWorld.pre_worldheight").withStyle(ChatFormatting.RED);
   private final SelectWorldScreen screen;
   @Nullable
   private List<LevelSummary> cachedList;

   public WorldSelectionList(
      SelectWorldScreen var1, Minecraft var2, int var3, int var4, int var5, int var6, int var7, Supplier<String> var8, @Nullable WorldSelectionList var9
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.screen = â˜ƒ;
      if (â˜ƒ != null) {
         this.cachedList = â˜ƒ.cachedList;
      }

      this.refreshList(â˜ƒ, false);
   }

   public void refreshList(Supplier<String> var1, boolean var2) {
      this.clearEntries();
      LevelStorageSource â˜ƒ = this.minecraft.getLevelSource();
      if (this.cachedList == null || â˜ƒ) {
         try {
            this.cachedList = â˜ƒ.getLevelList();
         } catch (LevelStorageException var7) {
            LOGGER.error("Couldn't load level list", var7);
            this.minecraft.setScreen(new ErrorScreen(new TranslatableComponent("selectWorld.unable_to_load"), new TextComponent(var7.getMessage())));
            return;
         }

         Collections.sort(this.cachedList);
      }

      if (this.cachedList.isEmpty()) {
         this.minecraft.setScreen(CreateWorldScreen.create(null));
      } else {
         String â˜ƒ = ((String)â˜ƒ.get()).toLowerCase(Locale.ROOT);

         for(LevelSummary â˜ƒx : this.cachedList) {
            if (â˜ƒx.getLevelName().toLowerCase(Locale.ROOT).contains(â˜ƒ) || â˜ƒx.getLevelId().toLowerCase(Locale.ROOT).contains(â˜ƒ)) {
               this.addEntry(new WorldSelectionList.WorldListEntry(this, â˜ƒx));
            }
         }
      }
   }

   @Override
   protected int getScrollbarPosition() {
      return super.getScrollbarPosition() + 20;
   }

   @Override
   public int getRowWidth() {
      return super.getRowWidth() + 50;
   }

   @Override
   protected boolean isFocused() {
      return this.screen.getFocused() == this;
   }

   public void setSelected(@Nullable WorldSelectionList.WorldListEntry var1) {
      super.setSelected(â˜ƒ);
      this.screen.updateButtonStatus(â˜ƒ != null && !â˜ƒ.summary.isDisabled());
   }

   @Override
   protected void moveSelection(AbstractSelectionList.SelectionDirection var1) {
      this.moveSelection(â˜ƒ, var0 -> !var0.summary.isDisabled());
   }

   public Optional<WorldSelectionList.WorldListEntry> getSelectedOpt() {
      return Optional.ofNullable(this.getSelected());
   }

   public SelectWorldScreen getScreen() {
      return this.screen;
   }

   public final class WorldListEntry extends ObjectSelectionList.Entry<WorldSelectionList.WorldListEntry> implements AutoCloseable {
      private static final int ICON_WIDTH = 32;
      private static final int ICON_HEIGHT = 32;
      private static final int ICON_OVERLAY_X_JOIN = 0;
      private static final int ICON_OVERLAY_X_JOIN_WITH_NOTIFY = 32;
      private static final int ICON_OVERLAY_X_WARNING = 64;
      private static final int ICON_OVERLAY_X_ERROR = 96;
      private static final int ICON_OVERLAY_Y_UNSELECTED = 0;
      private static final int ICON_OVERLAY_Y_SELECTED = 32;
      private final Minecraft minecraft;
      private final SelectWorldScreen screen;
      final LevelSummary summary;
      private final ResourceLocation iconLocation;
      private File iconFile;
      @Nullable
      private final DynamicTexture icon;
      private long lastClickTime;

      public WorldListEntry(WorldSelectionList var2, LevelSummary var3) {
         this.screen = â˜ƒ.getScreen();
         this.summary = â˜ƒ;
         this.minecraft = Minecraft.getInstance();
         String â˜ƒ = â˜ƒ.getLevelId();
         this.iconLocation = new ResourceLocation(
            "minecraft", "worlds/" + Util.sanitizeName(â˜ƒ, ResourceLocation::validPathChar) + "/" + Hashing.sha1().hashUnencodedChars(â˜ƒ) + "/icon"
         );
         this.iconFile = â˜ƒ.getIcon();
         if (!this.iconFile.isFile()) {
            this.iconFile = null;
         }

         this.icon = this.loadServerIcon();
      }

      @Override
      public Component getNarration() {
         TranslatableComponent â˜ƒx = new TranslatableComponent(
            "narrator.select.world",
            this.summary.getLevelName(),
            new Date(this.summary.getLastPlayed()),
            this.summary.isHardcore()
               ? new TranslatableComponent("gameMode.hardcore")
               : new TranslatableComponent("gameMode." + this.summary.getGameMode().getName()),
            this.summary.hasCheats() ? new TranslatableComponent("selectWorld.cheats") : TextComponent.EMPTY,
            this.summary.getWorldVersionName()
         );
         Component â˜ƒ;
         if (this.summary.isLocked()) {
            â˜ƒ = CommonComponents.joinForNarration(â˜ƒx, WorldSelectionList.WORLD_LOCKED_TOOLTIP);
         } else if (this.summary.isIncompatibleWorldHeight()) {
            â˜ƒ = CommonComponents.joinForNarration(â˜ƒx, WorldSelectionList.WORLD_PRE_WORLDHEIGHT_TOOLTIP);
         } else {
            â˜ƒ = â˜ƒx;
         }

         return new TranslatableComponent("narrator.select", â˜ƒ);
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         String â˜ƒ = this.summary.getLevelName();
         String â˜ƒx = this.summary.getLevelId() + " (" + WorldSelectionList.DATE_FORMAT.format(new Date(this.summary.getLastPlayed())) + ")";
         if (StringUtils.isEmpty(â˜ƒ)) {
            â˜ƒ = I18n.get("selectWorld.world") + " " + (â˜ƒ + 1);
         }

         Component â˜ƒ = this.summary.getInfo();
         this.minecraft.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 1), 16777215);
         this.minecraft.font.draw(â˜ƒ, â˜ƒx, (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 9 + 3), 8421504);
         this.minecraft.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 9 + 9 + 3), 8421504);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShaderTexture(0, this.icon != null ? this.iconLocation : WorldSelectionList.ICON_MISSING);
         RenderSystem.enableBlend();
         GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.disableBlend();
         if (this.minecraft.options.touchscreen || â˜ƒ) {
            RenderSystem.setShaderTexture(0, WorldSelectionList.ICON_OVERLAY_LOCATION);
            GuiComponent.fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 32, â˜ƒ + 32, -1601138544);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int â˜ƒx = â˜ƒ - â˜ƒ;
            boolean â˜ƒxx = â˜ƒx < 32;
            int â˜ƒxxx = â˜ƒxx ? 32 : 0;
            if (this.summary.isLocked()) {
               GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 96.0F, (float)â˜ƒxxx, 32, 32, 256, 256);
               if (â˜ƒxx) {
                  this.screen.setToolTip(this.minecraft.font.split(WorldSelectionList.WORLD_LOCKED_TOOLTIP, 175));
               }
            } else if (this.summary.isIncompatibleWorldHeight()) {
               GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 96.0F, 32.0F, 32, 32, 256, 256);
               if (â˜ƒxx) {
                  this.screen.setToolTip(this.minecraft.font.split(WorldSelectionList.WORLD_PRE_WORLDHEIGHT_TOOLTIP, 175));
               }
            } else if (this.summary.markVersionInList()) {
               GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 32.0F, (float)â˜ƒxxx, 32, 32, 256, 256);
               if (this.summary.askToOpenWorld()) {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 96.0F, (float)â˜ƒxxx, 32, 32, 256, 256);
                  if (â˜ƒxx) {
                     this.screen
                        .setToolTip(
                           ImmutableList.of(
                              WorldSelectionList.FROM_NEWER_TOOLTIP_1.getVisualOrderText(), WorldSelectionList.FROM_NEWER_TOOLTIP_2.getVisualOrderText()
                           )
                        );
                  }
               } else if (!SharedConstants.getCurrentVersion().isStable()) {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 64.0F, (float)â˜ƒxxx, 32, 32, 256, 256);
                  if (â˜ƒxx) {
                     this.screen
                        .setToolTip(
                           ImmutableList.of(
                              WorldSelectionList.SNAPSHOT_TOOLTIP_1.getVisualOrderText(), WorldSelectionList.SNAPSHOT_TOOLTIP_2.getVisualOrderText()
                           )
                        );
                  }
               }
            } else {
               GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, (float)â˜ƒxxx, 32, 32, 256, 256);
            }
         }
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (this.summary.isDisabled()) {
            return true;
         } else {
            WorldSelectionList.this.setSelected(this);
            this.screen.updateButtonStatus(WorldSelectionList.this.getSelectedOpt().isPresent());
            if (â˜ƒ - (double)WorldSelectionList.this.getRowLeft() <= 32.0) {
               this.joinWorld();
               return true;
            } else if (Util.getMillis() - this.lastClickTime < 250L) {
               this.joinWorld();
               return true;
            } else {
               this.lastClickTime = Util.getMillis();
               return false;
            }
         }
      }

      public void joinWorld() {
         if (!this.summary.isDisabled()) {
            LevelSummary.BackupStatus â˜ƒ = this.summary.backupStatus();
            if (â˜ƒ.shouldBackup()) {
               String â˜ƒx = "selectWorld.backupQuestion." + â˜ƒ.getTranslationKey();
               String â˜ƒxx = "selectWorld.backupWarning." + â˜ƒ.getTranslationKey();
               MutableComponent â˜ƒxxx = new TranslatableComponent(â˜ƒx);
               if (â˜ƒ.isSevere()) {
                  â˜ƒxxx.withStyle(ChatFormatting.BOLD, ChatFormatting.RED);
               }

               Component â˜ƒx = new TranslatableComponent(â˜ƒxx, this.summary.getWorldVersionName(), SharedConstants.getCurrentVersion().getName());
               this.minecraft.setScreen(new BackupConfirmScreen(this.screen, (var1x, var2x) -> {
                  if (var1x) {
                     String â˜ƒ = this.summary.getLevelId();

                     try (LevelStorageSource.LevelStorageAccess â˜ƒx = this.minecraft.getLevelSource().createAccess(â˜ƒ)) {
                        EditWorldScreen.makeBackupAndShowToast(â˜ƒx);
                     } catch (IOException var9) {
                        SystemToast.onWorldAccessFailure(this.minecraft, â˜ƒ);
                        WorldSelectionList.LOGGER.error("Failed to backup level {}", â˜ƒ, var9);
                     }
                  }

                  this.loadWorld();
               }, â˜ƒxxx, â˜ƒx, false));
            } else if (this.summary.askToOpenWorld()) {
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        var1x -> {
                           if (var1x) {
                              try {
                                 this.loadWorld();
                              } catch (Exception var3xx) {
                                 WorldSelectionList.LOGGER.error("Failure to open 'future world'", var3xx);
                                 this.minecraft
                                    .setScreen(
                                       new AlertScreen(
                                          () -> this.minecraft.setScreen(this.screen),
                                          new TranslatableComponent("selectWorld.futureworld.error.title"),
                                          new TranslatableComponent("selectWorld.futureworld.error.text")
                                       )
                                    );
                              }
                           } else {
                              this.minecraft.setScreen(this.screen);
                           }
                        },
                        new TranslatableComponent("selectWorld.versionQuestion"),
                        new TranslatableComponent("selectWorld.versionWarning", this.summary.getWorldVersionName()),
                        new TranslatableComponent("selectWorld.versionJoinButton"),
                        CommonComponents.GUI_CANCEL
                     )
                  );
            } else {
               this.loadWorld();
            }
         }
      }

      public void deleteWorld() {
         this.minecraft
            .setScreen(
               new ConfirmScreen(
                  var1 -> {
                     if (var1) {
                        this.minecraft.setScreen(new ProgressScreen(true));
                        this.doDeleteWorld();
                     }
         
                     this.minecraft.setScreen(this.screen);
                  },
                  new TranslatableComponent("selectWorld.deleteQuestion"),
                  new TranslatableComponent("selectWorld.deleteWarning", this.summary.getLevelName()),
                  new TranslatableComponent("selectWorld.deleteButton"),
                  CommonComponents.GUI_CANCEL
               )
            );
      }

      public void doDeleteWorld() {
         LevelStorageSource â˜ƒ = this.minecraft.getLevelSource();
         String â˜ƒx = this.summary.getLevelId();

         try (LevelStorageSource.LevelStorageAccess â˜ƒxx = â˜ƒ.createAccess(â˜ƒx)) {
            â˜ƒxx.deleteLevel();
         } catch (IOException var8) {
            SystemToast.onWorldDeleteFailure(this.minecraft, â˜ƒx);
            WorldSelectionList.LOGGER.error("Failed to delete world {}", â˜ƒx, var8);
         }

         WorldSelectionList.this.refreshList(() -> this.screen.searchBox.getValue(), true);
      }

      public void editWorld() {
         String â˜ƒ = this.summary.getLevelId();

         try {
            LevelStorageSource.LevelStorageAccess â˜ƒx = this.minecraft.getLevelSource().createAccess(â˜ƒ);
            this.minecraft.setScreen(new EditWorldScreen(var3x -> {
               try {
                  â˜ƒ.close();
               } catch (IOException var5) {
                  WorldSelectionList.LOGGER.error("Failed to unlock level {}", â˜ƒ, var5);
               }

               if (var3x) {
                  WorldSelectionList.this.refreshList(() -> this.screen.searchBox.getValue(), true);
               }

               this.minecraft.setScreen(this.screen);
            }, â˜ƒx));
         } catch (IOException var3) {
            SystemToast.onWorldAccessFailure(this.minecraft, â˜ƒ);
            WorldSelectionList.LOGGER.error("Failed to access level {}", â˜ƒ, var3);
            WorldSelectionList.this.refreshList(() -> this.screen.searchBox.getValue(), true);
         }
      }

      public void recreateWorld() {
         this.queueLoadScreen();
         RegistryAccess.RegistryHolder â˜ƒ = RegistryAccess.builtin();

         try (
            LevelStorageSource.LevelStorageAccess â˜ƒx = this.minecraft.getLevelSource().createAccess(this.summary.getLevelId());
            Minecraft.ServerStem â˜ƒxx = this.minecraft.makeServerStem(â˜ƒ, Minecraft::loadDataPacks, Minecraft::loadWorldData, false, â˜ƒx);
         ) {
            LevelSettings â˜ƒxxx = â˜ƒxx.worldData().getLevelSettings();
            DataPackConfig â˜ƒxxxx = â˜ƒxxx.getDataPackConfig();
            WorldGenSettings â˜ƒxxxxx = â˜ƒxx.worldData().worldGenSettings();
            Path â˜ƒxxxxxx = CreateWorldScreen.createTempDataPackDirFromExistingWorld(â˜ƒx.getLevelPath(LevelResource.DATAPACK_DIR), this.minecraft);
            if (â˜ƒxxxxx.isOldCustomizedWorld()) {
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        var6x -> this.minecraft.setScreen((Screen)(var6x ? new CreateWorldScreen(this.screen, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : this.screen)),
                        new TranslatableComponent("selectWorld.recreate.customized.title"),
                        new TranslatableComponent("selectWorld.recreate.customized.text"),
                        CommonComponents.GUI_PROCEED,
                        CommonComponents.GUI_CANCEL
                     )
                  );
            } else {
               this.minecraft.setScreen(new CreateWorldScreen(this.screen, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxx, â˜ƒ));
            }
         } catch (Exception var12) {
            WorldSelectionList.LOGGER.error("Unable to recreate world", var12);
            this.minecraft
               .setScreen(
                  new AlertScreen(
                     () -> this.minecraft.setScreen(this.screen),
                     new TranslatableComponent("selectWorld.recreate.error.title"),
                     new TranslatableComponent("selectWorld.recreate.error.text")
                  )
               );
         }
      }

      private void loadWorld() {
         this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         if (this.minecraft.getLevelSource().levelExists(this.summary.getLevelId())) {
            this.queueLoadScreen();
            this.minecraft.loadLevel(this.summary.getLevelId());
         }
      }

      private void queueLoadScreen() {
         this.minecraft.forceSetScreen(new GenericDirtMessageScreen(new TranslatableComponent("selectWorld.data_read")));
      }

      @Nullable
      private DynamicTexture loadServerIcon() {
         boolean â˜ƒ = this.iconFile != null && this.iconFile.isFile();
         if (â˜ƒ) {
            try {
               InputStream â˜ƒx = new FileInputStream(this.iconFile);

               DynamicTexture var5;
               try {
                  NativeImage â˜ƒxx = NativeImage.read(â˜ƒx);
                  Validate.validState(â˜ƒxx.getWidth() == 64, "Must be 64 pixels wide");
                  Validate.validState(â˜ƒxx.getHeight() == 64, "Must be 64 pixels high");
                  DynamicTexture â˜ƒxxx = new DynamicTexture(â˜ƒxx);
                  this.minecraft.getTextureManager().register(this.iconLocation, â˜ƒxxx);
                  var5 = â˜ƒxxx;
               } catch (Throwable var7) {
                  try {
                     â˜ƒx.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }

                  throw var7;
               }

               â˜ƒx.close();
               return var5;
            } catch (Throwable var8) {
               WorldSelectionList.LOGGER.error("Invalid icon for world {}", this.summary.getLevelId(), var8);
               this.iconFile = null;
               return null;
            }
         } else {
            this.minecraft.getTextureManager().release(this.iconLocation);
            return null;
         }
      }

      public void close() {
         if (this.icon != null) {
            this.icon.close();
         }
      }

      public String getLevelName() {
         return this.summary.getLevelName();
      }
   }
}
