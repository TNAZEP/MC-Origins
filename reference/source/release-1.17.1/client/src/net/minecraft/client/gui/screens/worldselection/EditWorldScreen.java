package net.minecraft.client.gui.screens.worldselection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditWorldScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson WORLD_GEN_SETTINGS_GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
   private static final Component NAME_LABEL = new TranslatableComponent("selectWorld.enterName");
   private Button renameButton;
   private final BooleanConsumer callback;
   private EditBox nameEdit;
   private final LevelStorageSource.LevelStorageAccess levelAccess;

   public EditWorldScreen(BooleanConsumer var1, LevelStorageSource.LevelStorageAccess var2) {
      super(new TranslatableComponent("selectWorld.edit.title"));
      this.callback = â˜ƒ;
      this.levelAccess = â˜ƒ;
   }

   @Override
   public void tick() {
      this.nameEdit.tick();
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      Button â˜ƒ = this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 0 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.resetIcon"), var1x -> {
            this.levelAccess.getIconFile().ifPresent(var0 -> FileUtils.deleteQuietly(var0.toFile()));
            var1x.active = false;
         })
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100,
            this.height / 4 + 24 + 5,
            200,
            20,
            new TranslatableComponent("selectWorld.edit.openFolder"),
            var1x -> Util.getPlatform().openFile(this.levelAccess.getLevelPath(LevelResource.ROOT).toFile())
         )
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 48 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.backup"), var1x -> {
            boolean â˜ƒ = makeBackupAndShowToast(this.levelAccess);
            this.callback.accept(!â˜ƒ);
         })
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 72 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.backupFolder"), var1x -> {
            LevelStorageSource â˜ƒ = this.minecraft.getLevelSource();
            Path â˜ƒx = â˜ƒ.getBackupPath();
   
            try {
               Files.createDirectories(Files.exists(â˜ƒx, new LinkOption[0]) ? â˜ƒx.toRealPath() : â˜ƒx);
            } catch (IOException var5) {
               throw new RuntimeException(var5);
            }
   
            Util.getPlatform().openFile(â˜ƒx.toFile());
         })
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100,
            this.height / 4 + 96 + 5,
            200,
            20,
            new TranslatableComponent("selectWorld.edit.optimize"),
            var1x -> this.minecraft.setScreen(new BackupConfirmScreen(this, (var1xx, var2x) -> {
                  if (var1xx) {
                     makeBackupAndShowToast(this.levelAccess);
                  }
      
                  this.minecraft.setScreen(OptimizeWorldScreen.create(this.minecraft, this.callback, this.minecraft.getFixerUpper(), this.levelAccess, var2x));
               }, new TranslatableComponent("optimizeWorld.confirm.title"), new TranslatableComponent("optimizeWorld.confirm.description"), true))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100,
            this.height / 4 + 120 + 5,
            200,
            20,
            new TranslatableComponent("selectWorld.edit.export_worldgen_settings"),
            var1x -> {
               RegistryAccess.RegistryHolder â˜ƒ = RegistryAccess.builtin();
      
               DataResult<String> â˜ƒ;
               try (Minecraft.ServerStem â˜ƒx = this.minecraft.makeServerStem(â˜ƒ, Minecraft::loadDataPacks, Minecraft::loadWorldData, false, this.levelAccess)) {
                  DynamicOps<JsonElement> â˜ƒxx = RegistryWriteOps.create(JsonOps.INSTANCE, â˜ƒ);
                  DataResult<JsonElement> â˜ƒxxx = WorldGenSettings.CODEC.encodeStart(â˜ƒxx, â˜ƒx.worldData().worldGenSettings());
                  â˜ƒ = â˜ƒxxx.flatMap(var1xx -> {
                     Path â˜ƒ = this.levelAccess.getLevelPath(LevelResource.ROOT).resolve("worldgen_settings_export.json");
      
                     try {
                        JsonWriter â˜ƒx = WORLD_GEN_SETTINGS_GSON.newJsonWriter(Files.newBufferedWriter(â˜ƒ, StandardCharsets.UTF_8));
      
                        try {
                           WORLD_GEN_SETTINGS_GSON.toJson(var1xx, â˜ƒx);
                        } catch (Throwable var7) {
                           if (â˜ƒx != null) {
                              try {
                                 â˜ƒx.close();
                              } catch (Throwable var6xx) {
                                 var7.addSuppressed(var6xx);
                              }
                           }
      
                           throw var7;
                        }
      
                        if (â˜ƒx != null) {
                           â˜ƒx.close();
                        }
                     } catch (JsonIOException | IOException var8) {
                        return DataResult.error("Error writing file: " + var8.getMessage());
                     }
      
                     return DataResult.success(â˜ƒ.toString());
                  });
               } catch (Exception var9) {
                  LOGGER.warn("Could not parse level data", var9);
                  â˜ƒ = DataResult.error("Could not parse level data: " + var9.getMessage());
               }
      
               Component â˜ƒx = new TextComponent(â˜ƒ.get().map(Function.identity(), PartialResult::message));
               Component â˜ƒxx = new TranslatableComponent(
                  â˜ƒ.result().isPresent() ? "selectWorld.edit.export_worldgen_settings.success" : "selectWorld.edit.export_worldgen_settings.failure"
               );
               â˜ƒ.error().ifPresent(var0 -> LOGGER.error("Error exporting world settings: {}", var0));
               this.minecraft.getToasts().addToast(SystemToast.multiline(this.minecraft, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, â˜ƒxx, â˜ƒx));
            }
         )
      );
      this.renameButton = this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 144 + 5, 98, 20, new TranslatableComponent("selectWorld.edit.save"), var1x -> this.onRename())
      );
      this.addRenderableWidget(
         new Button(this.width / 2 + 2, this.height / 4 + 144 + 5, 98, 20, CommonComponents.GUI_CANCEL, var1x -> this.callback.accept(false))
      );
      â˜ƒ.active = this.levelAccess.getIconFile().filter(var0 -> Files.isRegularFile(var0, new LinkOption[0])).isPresent();
      LevelSummary â˜ƒx = this.levelAccess.getSummary();
      String â˜ƒxx = â˜ƒx == null ? "" : â˜ƒx.getLevelName();
      this.nameEdit = new EditBox(this.font, this.width / 2 - 100, 38, 200, 20, new TranslatableComponent("selectWorld.enterName"));
      this.nameEdit.setValue(â˜ƒxx);
      this.nameEdit.setResponder(var1x -> this.renameButton.active = !var1x.trim().isEmpty());
      this.addWidget(this.nameEdit);
      this.setInitialFocus(this.nameEdit);
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.nameEdit.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.nameEdit.setValue(â˜ƒ);
   }

   @Override
   public void onClose() {
      this.callback.accept(false);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   private void onRename() {
      try {
         this.levelAccess.renameLevel(this.nameEdit.getValue().trim());
         this.callback.accept(true);
      } catch (IOException var2) {
         LOGGER.error("Failed to access world '{}'", this.levelAccess.getLevelId(), var2);
         SystemToast.onWorldAccessFailure(this.minecraft, this.levelAccess.getLevelId());
         this.callback.accept(true);
      }
   }

   public static void makeBackupAndShowToast(LevelStorageSource var0, String var1) {
      boolean â˜ƒ = false;

      try (LevelStorageSource.LevelStorageAccess â˜ƒx = â˜ƒ.createAccess(â˜ƒ)) {
         â˜ƒ = true;
         makeBackupAndShowToast(â˜ƒx);
      } catch (IOException var8) {
         if (!â˜ƒ) {
            SystemToast.onWorldAccessFailure(Minecraft.getInstance(), â˜ƒ);
         }

         LOGGER.warn("Failed to create backup of level {}", â˜ƒ, var8);
      }
   }

   public static boolean makeBackupAndShowToast(LevelStorageSource.LevelStorageAccess var0) {
      long â˜ƒ = 0L;
      IOException â˜ƒx = null;

      try {
         â˜ƒ = â˜ƒ.makeWorldBackup();
      } catch (IOException var6) {
         â˜ƒx = var6;
      }

      if (â˜ƒx != null) {
         Component â˜ƒxx = new TranslatableComponent("selectWorld.edit.backupFailed");
         Component â˜ƒxxx = new TextComponent(â˜ƒx.getMessage());
         Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.WORLD_BACKUP, â˜ƒxx, â˜ƒxxx));
         return false;
      } else {
         Component â˜ƒxx = new TranslatableComponent("selectWorld.edit.backupCreated", â˜ƒ.getLevelId());
         Component â˜ƒxxx = new TranslatableComponent("selectWorld.edit.backupSize", Mth.ceil((double)â˜ƒ / 1048576.0));
         Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.WORLD_BACKUP, â˜ƒxx, â˜ƒxxx));
         return true;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 15, 16777215);
      drawString(â˜ƒ, this.font, NAME_LABEL, this.width / 2 - 100, 24, 10526880);
      this.nameEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
