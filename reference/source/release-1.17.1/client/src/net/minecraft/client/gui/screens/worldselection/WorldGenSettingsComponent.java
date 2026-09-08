package net.minecraft.client.gui.screens.worldselection;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class WorldGenSettingsComponent implements Widget {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Component CUSTOM_WORLD_DESCRIPTION = new TranslatableComponent("generator.custom");
   private static final Component AMPLIFIED_HELP_TEXT = new TranslatableComponent("generator.amplified.info");
   private static final Component MAP_FEATURES_INFO = new TranslatableComponent("selectWorld.mapFeatures.info");
   private static final Component SELECT_FILE_PROMPT = new TranslatableComponent("selectWorld.import_worldgen_settings.select_file");
   private MultiLineLabel amplifiedWorldInfo = MultiLineLabel.EMPTY;
   private Font font;
   private int width;
   private EditBox seedEdit;
   private CycleButton<Boolean> featuresButton;
   private CycleButton<Boolean> bonusItemsButton;
   private CycleButton<WorldPreset> typeButton;
   private Button customWorldDummyButton;
   private Button customizeTypeButton;
   private Button importSettingsButton;
   private RegistryAccess.RegistryHolder registryHolder;
   private WorldGenSettings settings;
   private Optional<WorldPreset> preset;
   private OptionalLong seed;

   public WorldGenSettingsComponent(RegistryAccess.RegistryHolder var1, WorldGenSettings var2, Optional<WorldPreset> var3, OptionalLong var4) {
      this.registryHolder = â˜ƒ;
      this.settings = â˜ƒ;
      this.preset = â˜ƒ;
      this.seed = â˜ƒ;
   }

   public void init(CreateWorldScreen var1, Minecraft var2, Font var3) {
      this.font = â˜ƒ;
      this.width = â˜ƒ.width;
      this.seedEdit = new EditBox(this.font, this.width / 2 - 100, 60, 200, 20, new TranslatableComponent("selectWorld.enterSeed"));
      this.seedEdit.setValue(toString(this.seed));
      this.seedEdit.setResponder(var1x -> this.seed = this.parseSeed());
      â˜ƒ.addWidget(this.seedEdit);
      int â˜ƒ = this.width / 2 - 155;
      int â˜ƒx = this.width / 2 + 5;
      this.featuresButton = â˜ƒ.addRenderableWidget(
         CycleButton.onOffBuilder(this.settings.generateFeatures())
            .withCustomNarration(
               var0 -> CommonComponents.joinForNarration(var0.createDefaultNarrationMessage(), new TranslatableComponent("selectWorld.mapFeatures.info"))
            )
            .create(
               â˜ƒ, 100, 150, 20, new TranslatableComponent("selectWorld.mapFeatures"), (var1x, var2x) -> this.settings = this.settings.withFeaturesToggled()
            )
      );
      this.featuresButton.visible = false;
      this.typeButton = â˜ƒ.addRenderableWidget(
         CycleButton.<WorldPreset>builder(WorldPreset::description)
            .withValues(
               (List<WorldPreset>)WorldPreset.PRESETS.stream().filter(WorldPreset::isVisibleByDefault).collect(Collectors.toList()), WorldPreset.PRESETS
            )
            .withCustomNarration(
               var0 -> var0.getValue() == WorldPreset.AMPLIFIED
                     ? CommonComponents.joinForNarration(var0.createDefaultNarrationMessage(), AMPLIFIED_HELP_TEXT)
                     : var0.createDefaultNarrationMessage()
            )
            .create(â˜ƒx, 100, 150, 20, new TranslatableComponent("selectWorld.mapType"), (var2x, var3x) -> {
               this.preset = Optional.of(var3x);
               this.settings = var3x.create(this.registryHolder, this.settings.seed(), this.settings.generateFeatures(), this.settings.generateBonusChest());
               â˜ƒ.refreshWorldGenSettingsVisibility();
            })
      );
      this.preset.ifPresent(this.typeButton::setValue);
      this.typeButton.visible = false;
      this.customWorldDummyButton = â˜ƒ.addRenderableWidget(
         new Button(â˜ƒx, 100, 150, 20, CommonComponents.optionNameValue(new TranslatableComponent("selectWorld.mapType"), CUSTOM_WORLD_DESCRIPTION), var0 -> {
         })
      );
      this.customWorldDummyButton.active = false;
      this.customWorldDummyButton.visible = false;
      this.customizeTypeButton = â˜ƒ.addRenderableWidget(new Button(â˜ƒx, 120, 150, 20, new TranslatableComponent("selectWorld.customizeType"), var3x -> {
         WorldPreset.PresetEditor â˜ƒ = (WorldPreset.PresetEditor)WorldPreset.EDITORS.get(this.preset);
         if (â˜ƒ != null) {
            â˜ƒ.setScreen(â˜ƒ.createEditScreen(â˜ƒ, this.settings));
         }
      }));
      this.customizeTypeButton.visible = false;
      this.bonusItemsButton = â˜ƒ.addRenderableWidget(
         CycleButton.onOffBuilder(this.settings.generateBonusChest() && !â˜ƒ.hardCore)
            .create(
               â˜ƒ, 151, 150, 20, new TranslatableComponent("selectWorld.bonusItems"), (var1x, var2x) -> this.settings = this.settings.withBonusChestToggled()
            )
      );
      this.bonusItemsButton.visible = false;
      this.importSettingsButton = â˜ƒ.addRenderableWidget(
         new Button(
            â˜ƒ,
            185,
            150,
            20,
            new TranslatableComponent("selectWorld.import_worldgen_settings"),
            var3x -> {
               String â˜ƒ = TinyFileDialogs.tinyfd_openFileDialog(SELECT_FILE_PROMPT.getString(), null, null, null, false);
               if (â˜ƒ != null) {
                  RegistryAccess.RegistryHolder â˜ƒx = RegistryAccess.builtin();
                  PackRepository â˜ƒxx = new PackRepository(
                     PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(â˜ƒ.getTempDataPackDir().toFile(), PackSource.WORLD)
                  );
      
                  ServerResources â˜ƒ;
                  try {
                     MinecraftServer.configurePackRepository(â˜ƒxx, â˜ƒ.dataPacks, false);
                     CompletableFuture<ServerResources> â˜ƒxxx = ServerResources.loadResources(
                        â˜ƒxx.openAllSelected(), â˜ƒx, Commands.CommandSelection.INTEGRATED, 2, Util.backgroundExecutor(), â˜ƒ
                     );
                     â˜ƒ.managedBlock(â˜ƒxxx::isDone);
                     â˜ƒ = (ServerResources)â˜ƒxxx.get();
                  } catch (ExecutionException | InterruptedException var15) {
                     LOGGER.error("Error loading data packs when importing world settings", var15);
                     Component â˜ƒxxxx = new TranslatableComponent("selectWorld.import_worldgen_settings.failure");
                     Component â˜ƒxxxxx = new TextComponent(var15.getMessage());
                     â˜ƒ.getToasts().addToast(SystemToast.multiline(â˜ƒ, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, â˜ƒxxxx, â˜ƒxxxxx));
                     â˜ƒxx.close();
                     return;
                  }
      
                  RegistryReadOps<JsonElement> â˜ƒxxx = RegistryReadOps.createAndLoad(JsonOps.INSTANCE, â˜ƒ.getResourceManager(), â˜ƒx);
                  JsonParser â˜ƒxxxx = new JsonParser();
      
                  DataResult<WorldGenSettings> â˜ƒ;
                  try {
                     BufferedReader â˜ƒxxxxx = Files.newBufferedReader(Paths.get(â˜ƒ));
      
                     try {
                        JsonElement â˜ƒxxxxxx = â˜ƒxxxx.parse(â˜ƒxxxxx);
                        â˜ƒ = WorldGenSettings.CODEC.parse(â˜ƒxxx, â˜ƒxxxxxx);
                     } catch (Throwable var16) {
                        if (â˜ƒxxxxx != null) {
                           try {
                              â˜ƒxxxxx.close();
                           } catch (Throwable var14) {
                              var16.addSuppressed(var14);
                           }
                        }
      
                        throw var16;
                     }
      
                     if (â˜ƒxxxxx != null) {
                        â˜ƒxxxxx.close();
                     }
                  } catch (JsonIOException | JsonSyntaxException | IOException var17) {
                     â˜ƒ = DataResult.error("Failed to parse file: " + var17.getMessage());
                  }
      
                  if (â˜ƒ.error().isPresent()) {
                     Component â˜ƒxxxxx = new TranslatableComponent("selectWorld.import_worldgen_settings.failure");
                     String â˜ƒxxxxxx = ((PartialResult)â˜ƒ.error().get()).message();
                     LOGGER.error("Error parsing world settings: {}", â˜ƒxxxxxx);
                     Component â˜ƒxxxxxxx = new TextComponent(â˜ƒxxxxxx);
                     â˜ƒ.getToasts().addToast(SystemToast.multiline(â˜ƒ, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, â˜ƒxxxxx, â˜ƒxxxxxxx));
                  }
      
                  â˜ƒ.close();
                  Lifecycle â˜ƒxxxxx = â˜ƒ.lifecycle();
                  â˜ƒ.resultOrPartial(LOGGER::error)
                     .ifPresent(
                        var5x -> {
                           BooleanConsumer â˜ƒ = var5xx -> {
                              â˜ƒ.setScreen(â˜ƒ);
                              if (var5xx) {
                                 this.importSettings(â˜ƒ, var5x);
                              }
                           };
                           if (â˜ƒ == Lifecycle.stable()) {
                              this.importSettings(â˜ƒ, var5x);
                           } else if (â˜ƒ == Lifecycle.experimental()) {
                              â˜ƒ.setScreen(
                                 new ConfirmScreen(
                                    â˜ƒ,
                                    new TranslatableComponent("selectWorld.import_worldgen_settings.experimental.title"),
                                    new TranslatableComponent("selectWorld.import_worldgen_settings.experimental.question")
                                 )
                              );
                           } else {
                              â˜ƒ.setScreen(
                                 new ConfirmScreen(
                                    â˜ƒ,
                                    new TranslatableComponent("selectWorld.import_worldgen_settings.deprecated.title"),
                                    new TranslatableComponent("selectWorld.import_worldgen_settings.deprecated.question")
                                 )
                              );
                           }
                        }
                     );
               }
            }
         )
      );
      this.importSettingsButton.visible = false;
      this.amplifiedWorldInfo = MultiLineLabel.create(â˜ƒ, AMPLIFIED_HELP_TEXT, this.typeButton.getWidth());
   }

   private void importSettings(RegistryAccess.RegistryHolder var1, WorldGenSettings var2) {
      this.registryHolder = â˜ƒ;
      this.settings = â˜ƒ;
      this.preset = WorldPreset.of(â˜ƒ);
      this.selectWorldTypeButton(true);
      this.seed = OptionalLong.of(â˜ƒ.seed());
      this.seedEdit.setValue(toString(this.seed));
   }

   public void tick() {
      this.seedEdit.tick();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.featuresButton.visible) {
         this.font.drawShadow(â˜ƒ, MAP_FEATURES_INFO, (float)(this.width / 2 - 150), 122.0F, -6250336);
      }

      this.seedEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.preset.equals(Optional.of(WorldPreset.AMPLIFIED))) {
         this.amplifiedWorldInfo.renderLeftAligned(â˜ƒ, this.typeButton.x + 2, this.typeButton.y + 22, 9, 10526880);
      }
   }

   protected void updateSettings(WorldGenSettings var1) {
      this.settings = â˜ƒ;
   }

   private static String toString(OptionalLong var0) {
      return â˜ƒ.isPresent() ? Long.toString(â˜ƒ.getAsLong()) : "";
   }

   private static OptionalLong parseLong(String var0) {
      try {
         return OptionalLong.of(Long.parseLong(â˜ƒ));
      } catch (NumberFormatException var2) {
         return OptionalLong.empty();
      }
   }

   public WorldGenSettings makeSettings(boolean var1) {
      OptionalLong â˜ƒ = this.parseSeed();
      return this.settings.withSeed(â˜ƒ, â˜ƒ);
   }

   private OptionalLong parseSeed() {
      String â˜ƒx = this.seedEdit.getValue();
      OptionalLong â˜ƒ;
      if (StringUtils.isEmpty(â˜ƒx)) {
         â˜ƒ = OptionalLong.empty();
      } else {
         OptionalLong â˜ƒ = parseLong(â˜ƒx);
         if (â˜ƒ.isPresent() && â˜ƒ.getAsLong() != 0L) {
            â˜ƒ = â˜ƒ;
         } else {
            â˜ƒ = OptionalLong.of((long)â˜ƒx.hashCode());
         }
      }

      return â˜ƒ;
   }

   public boolean isDebug() {
      return this.settings.isDebug();
   }

   public void setVisibility(boolean var1) {
      this.selectWorldTypeButton(â˜ƒ);
      if (this.settings.isDebug()) {
         this.featuresButton.visible = false;
         this.bonusItemsButton.visible = false;
         this.customizeTypeButton.visible = false;
         this.importSettingsButton.visible = false;
      } else {
         this.featuresButton.visible = â˜ƒ;
         this.bonusItemsButton.visible = â˜ƒ;
         this.customizeTypeButton.visible = â˜ƒ && WorldPreset.EDITORS.containsKey(this.preset);
         this.importSettingsButton.visible = â˜ƒ;
      }

      this.seedEdit.setVisible(â˜ƒ);
   }

   private void selectWorldTypeButton(boolean var1) {
      if (this.preset.isPresent()) {
         this.typeButton.visible = â˜ƒ;
         this.customWorldDummyButton.visible = false;
      } else {
         this.typeButton.visible = false;
         this.customWorldDummyButton.visible = â˜ƒ;
      }
   }

   public RegistryAccess.RegistryHolder registryHolder() {
      return this.registryHolder;
   }

   void updateDataPacks(ServerResources var1) {
      RegistryAccess.RegistryHolder â˜ƒ = RegistryAccess.builtin();
      RegistryWriteOps<JsonElement> â˜ƒx = RegistryWriteOps.create(JsonOps.INSTANCE, this.registryHolder);
      RegistryReadOps<JsonElement> â˜ƒxx = RegistryReadOps.createAndLoad(JsonOps.INSTANCE, â˜ƒ.getResourceManager(), â˜ƒ);
      DataResult<WorldGenSettings> â˜ƒxxx = WorldGenSettings.CODEC.encodeStart(â˜ƒx, this.settings).flatMap(var1x -> WorldGenSettings.CODEC.parse(â˜ƒ, var1x));
      â˜ƒxxx.resultOrPartial(Util.prefix("Error parsing worldgen settings after loading data packs: ", LOGGER::error)).ifPresent(var2x -> {
         this.settings = var2x;
         this.registryHolder = â˜ƒ;
      });
   }

   public void switchToHardcore() {
      this.bonusItemsButton.active = false;
      this.bonusItemsButton.setValue(false);
   }

   public void switchOutOfHardcode() {
      this.bonusItemsButton.active = true;
      this.bonusItemsButton.setValue(this.settings.generateBonusChest());
   }
}
