package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.ServerResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWorldScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String TEMP_WORLD_PREFIX = "mcworld-";
   private static final Component GAME_MODEL_LABEL = new TranslatableComponent("selectWorld.gameMode");
   private static final Component SEED_LABEL = new TranslatableComponent("selectWorld.enterSeed");
   private static final Component SEED_INFO = new TranslatableComponent("selectWorld.seedInfo");
   private static final Component NAME_LABEL = new TranslatableComponent("selectWorld.enterName");
   private static final Component OUTPUT_DIR_INFO = new TranslatableComponent("selectWorld.resultFolder");
   private static final Component COMMANDS_INFO = new TranslatableComponent("selectWorld.allowCommands.info");
   private final Screen lastScreen;
   private EditBox nameEdit;
   String resultFolder;
   private CreateWorldScreen.SelectedGameMode gameMode = CreateWorldScreen.SelectedGameMode.SURVIVAL;
   @Nullable
   private CreateWorldScreen.SelectedGameMode oldGameMode;
   private Difficulty difficulty = Difficulty.NORMAL;
   private boolean commands;
   private boolean commandsChanged;
   public boolean hardCore;
   protected DataPackConfig dataPacks;
   @Nullable
   private Path tempDataPackDir;
   @Nullable
   private PackRepository tempDataPackRepository;
   private boolean worldGenSettingsVisible;
   private Button createButton;
   private CycleButton<CreateWorldScreen.SelectedGameMode> modeButton;
   private CycleButton<Difficulty> difficultyButton;
   private Button moreOptionsButton;
   private Button gameRulesButton;
   private Button dataPacksButton;
   private CycleButton<Boolean> commandsButton;
   private Component gameModeHelp1;
   private Component gameModeHelp2;
   private String initName;
   private GameRules gameRules = new GameRules();
   public final WorldGenSettingsComponent worldGenSettingsComponent;

   public CreateWorldScreen(
      @Nullable Screen var1, LevelSettings var2, WorldGenSettings var3, @Nullable Path var4, DataPackConfig var5, RegistryAccess.RegistryHolder var6
   ) {
      this(â˜ƒ, â˜ƒ, new WorldGenSettingsComponent(â˜ƒ, â˜ƒ, WorldPreset.of(â˜ƒ), OptionalLong.of(â˜ƒ.seed())));
      this.initName = â˜ƒ.levelName();
      this.commands = â˜ƒ.allowCommands();
      this.commandsChanged = true;
      this.difficulty = â˜ƒ.difficulty();
      this.gameRules.assignFrom(â˜ƒ.gameRules(), null);
      if (â˜ƒ.hardcore()) {
         this.gameMode = CreateWorldScreen.SelectedGameMode.HARDCORE;
      } else if (â˜ƒ.gameType().isSurvival()) {
         this.gameMode = CreateWorldScreen.SelectedGameMode.SURVIVAL;
      } else if (â˜ƒ.gameType().isCreative()) {
         this.gameMode = CreateWorldScreen.SelectedGameMode.CREATIVE;
      }

      this.tempDataPackDir = â˜ƒ;
   }

   public static CreateWorldScreen create(@Nullable Screen var0) {
      RegistryAccess.RegistryHolder â˜ƒ = RegistryAccess.builtin();
      return new CreateWorldScreen(
         â˜ƒ,
         DataPackConfig.DEFAULT,
         new WorldGenSettingsComponent(
            â˜ƒ,
            WorldGenSettings.makeDefault(
               â˜ƒ.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY),
               â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY),
               â˜ƒ.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY)
            ),
            Optional.of(WorldPreset.NORMAL),
            OptionalLong.empty()
         )
      );
   }

   private CreateWorldScreen(@Nullable Screen var1, DataPackConfig var2, WorldGenSettingsComponent var3) {
      super(new TranslatableComponent("selectWorld.create"));
      this.lastScreen = â˜ƒ;
      this.initName = I18n.get("selectWorld.newWorld");
      this.dataPacks = â˜ƒ;
      this.worldGenSettingsComponent = â˜ƒ;
   }

   @Override
   public void tick() {
      this.nameEdit.tick();
      this.worldGenSettingsComponent.tick();
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.nameEdit = new EditBox(this.font, this.width / 2 - 100, 60, 200, 20, new TranslatableComponent("selectWorld.enterName")) {
         @Override
         protected MutableComponent createNarrationMessage() {
            return CommonComponents.joinForNarration(super.createNarrationMessage(), new TranslatableComponent("selectWorld.resultFolder"))
               .append(" ")
               .append(CreateWorldScreen.this.resultFolder);
         }
      };
      this.nameEdit.setValue(this.initName);
      this.nameEdit.setResponder(var1x -> {
         this.initName = var1x;
         this.createButton.active = !this.nameEdit.getValue().isEmpty();
         this.updateResultFolder();
      });
      this.addWidget(this.nameEdit);
      int â˜ƒ = this.width / 2 - 155;
      int â˜ƒx = this.width / 2 + 5;
      this.modeButton = this.addRenderableWidget(
         CycleButton.builder(CreateWorldScreen.SelectedGameMode::getDisplayName)
            .withValues(CreateWorldScreen.SelectedGameMode.SURVIVAL, CreateWorldScreen.SelectedGameMode.HARDCORE, CreateWorldScreen.SelectedGameMode.CREATIVE)
            .withInitialValue(this.gameMode)
            .withCustomNarration(
               var1x -> AbstractWidget.wrapDefaultNarrationMessage(var1x.getMessage())
                     .append(CommonComponents.NARRATION_SEPARATOR)
                     .append(this.gameModeHelp1)
                     .append(" ")
                     .append(this.gameModeHelp2)
            )
            .create(â˜ƒ, 100, 150, 20, GAME_MODEL_LABEL, (var1x, var2x) -> this.setGameMode(var2x))
      );
      this.difficultyButton = this.addRenderableWidget(
         CycleButton.builder(Difficulty::getDisplayName)
            .withValues(Difficulty.values())
            .withInitialValue(this.getEffectiveDifficulty())
            .create(â˜ƒx, 100, 150, 20, new TranslatableComponent("options.difficulty"), (var1x, var2x) -> this.difficulty = var2x)
      );
      this.commandsButton = this.addRenderableWidget(
         CycleButton.onOffBuilder(this.commands && !this.hardCore)
            .withCustomNarration(
               var0 -> CommonComponents.joinForNarration(var0.createDefaultNarrationMessage(), new TranslatableComponent("selectWorld.allowCommands.info"))
            )
            .create(â˜ƒ, 151, 150, 20, new TranslatableComponent("selectWorld.allowCommands"), (var1x, var2x) -> {
               this.commandsChanged = true;
               this.commands = var2x;
            })
      );
      this.dataPacksButton = this.addRenderableWidget(
         new Button(â˜ƒx, 151, 150, 20, new TranslatableComponent("selectWorld.dataPacks"), var1x -> this.openDataPackSelectionScreen())
      );
      this.gameRulesButton = this.addRenderableWidget(
         new Button(
            â˜ƒ,
            185,
            150,
            20,
            new TranslatableComponent("selectWorld.gameRules"),
            var1x -> this.minecraft.setScreen(new EditGameRulesScreen(this.gameRules.copy(), var1xx -> {
                  this.minecraft.setScreen(this);
                  var1xx.ifPresent(var1xxx -> this.gameRules = var1xxx);
               }))
         )
      );
      this.worldGenSettingsComponent.init(this, this.minecraft, this.font);
      this.moreOptionsButton = this.addRenderableWidget(
         new Button(â˜ƒx, 185, 150, 20, new TranslatableComponent("selectWorld.moreWorldOptions"), var1x -> this.toggleWorldGenSettingsVisibility())
      );
      this.createButton = this.addRenderableWidget(
         new Button(â˜ƒ, this.height - 28, 150, 20, new TranslatableComponent("selectWorld.create"), var1x -> this.onCreate())
      );
      this.createButton.active = !this.initName.isEmpty();
      this.addRenderableWidget(new Button(â˜ƒx, this.height - 28, 150, 20, CommonComponents.GUI_CANCEL, var1x -> this.popScreen()));
      this.refreshWorldGenSettingsVisibility();
      this.setInitialFocus(this.nameEdit);
      this.setGameMode(this.gameMode);
      this.updateResultFolder();
   }

   private Difficulty getEffectiveDifficulty() {
      return this.gameMode == CreateWorldScreen.SelectedGameMode.HARDCORE ? Difficulty.HARD : this.difficulty;
   }

   private void updateGameModeHelp() {
      this.gameModeHelp1 = new TranslatableComponent("selectWorld.gameMode." + this.gameMode.name + ".line1");
      this.gameModeHelp2 = new TranslatableComponent("selectWorld.gameMode." + this.gameMode.name + ".line2");
   }

   private void updateResultFolder() {
      this.resultFolder = this.nameEdit.getValue().trim();
      if (this.resultFolder.isEmpty()) {
         this.resultFolder = "World";
      }

      try {
         this.resultFolder = FileUtil.findAvailableName(this.minecraft.getLevelSource().getBaseDir(), this.resultFolder, "");
      } catch (Exception var4) {
         this.resultFolder = "World";

         try {
            this.resultFolder = FileUtil.findAvailableName(this.minecraft.getLevelSource().getBaseDir(), this.resultFolder, "");
         } catch (Exception var3) {
            throw new RuntimeException("Could not create save folder", var3);
         }
      }
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   private void onCreate() {
      this.minecraft.forceSetScreen(new GenericDirtMessageScreen(new TranslatableComponent("createWorld.preparing")));
      if (this.copyTempDataPackDirToNewWorld()) {
         this.cleanupTempResources();
         WorldGenSettings â˜ƒx = this.worldGenSettingsComponent.makeSettings(this.hardCore);
         LevelSettings â˜ƒ;
         if (â˜ƒx.isDebug()) {
            GameRules â˜ƒxx = new GameRules();
            â˜ƒxx.getRule(GameRules.RULE_DAYLIGHT).set(false, null);
            â˜ƒ = new LevelSettings(this.nameEdit.getValue().trim(), GameType.SPECTATOR, false, Difficulty.PEACEFUL, true, â˜ƒxx, DataPackConfig.DEFAULT);
         } else {
            â˜ƒ = new LevelSettings(
               this.nameEdit.getValue().trim(),
               this.gameMode.gameType,
               this.hardCore,
               this.getEffectiveDifficulty(),
               this.commands && !this.hardCore,
               this.gameRules,
               this.dataPacks
            );
         }

         this.minecraft.createLevel(this.resultFolder, â˜ƒ, this.worldGenSettingsComponent.registryHolder(), â˜ƒx);
      }
   }

   private void toggleWorldGenSettingsVisibility() {
      this.setWorldGenSettingsVisible(!this.worldGenSettingsVisible);
   }

   private void setGameMode(CreateWorldScreen.SelectedGameMode var1) {
      if (!this.commandsChanged) {
         this.commands = â˜ƒ == CreateWorldScreen.SelectedGameMode.CREATIVE;
         this.commandsButton.setValue(this.commands);
      }

      if (â˜ƒ == CreateWorldScreen.SelectedGameMode.HARDCORE) {
         this.hardCore = true;
         this.commandsButton.active = false;
         this.commandsButton.setValue(false);
         this.worldGenSettingsComponent.switchToHardcore();
         this.difficultyButton.setValue(Difficulty.HARD);
         this.difficultyButton.active = false;
      } else {
         this.hardCore = false;
         this.commandsButton.active = true;
         this.commandsButton.setValue(this.commands);
         this.worldGenSettingsComponent.switchOutOfHardcode();
         this.difficultyButton.setValue(this.difficulty);
         this.difficultyButton.active = true;
      }

      this.gameMode = â˜ƒ;
      this.updateGameModeHelp();
   }

   public void refreshWorldGenSettingsVisibility() {
      this.setWorldGenSettingsVisible(this.worldGenSettingsVisible);
   }

   private void setWorldGenSettingsVisible(boolean var1) {
      this.worldGenSettingsVisible = â˜ƒ;
      this.modeButton.visible = !â˜ƒ;
      this.difficultyButton.visible = !â˜ƒ;
      if (this.worldGenSettingsComponent.isDebug()) {
         this.dataPacksButton.visible = false;
         this.modeButton.active = false;
         if (this.oldGameMode == null) {
            this.oldGameMode = this.gameMode;
         }

         this.setGameMode(CreateWorldScreen.SelectedGameMode.DEBUG);
         this.commandsButton.visible = false;
      } else {
         this.modeButton.active = true;
         if (this.oldGameMode != null) {
            this.setGameMode(this.oldGameMode);
         }

         this.commandsButton.visible = !â˜ƒ;
         this.dataPacksButton.visible = !â˜ƒ;
      }

      this.worldGenSettingsComponent.setVisibility(â˜ƒ);
      this.nameEdit.setVisible(!â˜ƒ);
      if (â˜ƒ) {
         this.moreOptionsButton.setMessage(CommonComponents.GUI_DONE);
      } else {
         this.moreOptionsButton.setMessage(new TranslatableComponent("selectWorld.moreWorldOptions"));
      }

      this.gameRulesButton.visible = !â˜ƒ;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (â˜ƒ != 257 && â˜ƒ != 335) {
         return false;
      } else {
         this.onCreate();
         return true;
      }
   }

   @Override
   public void onClose() {
      if (this.worldGenSettingsVisible) {
         this.setWorldGenSettingsVisible(false);
      } else {
         this.popScreen();
      }
   }

   public void popScreen() {
      this.minecraft.setScreen(this.lastScreen);
      this.cleanupTempResources();
   }

   private void cleanupTempResources() {
      if (this.tempDataPackRepository != null) {
         this.tempDataPackRepository.close();
      }

      this.removeTempDataPackDir();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, -1);
      if (this.worldGenSettingsVisible) {
         drawString(â˜ƒ, this.font, SEED_LABEL, this.width / 2 - 100, 47, -6250336);
         drawString(â˜ƒ, this.font, SEED_INFO, this.width / 2 - 100, 85, -6250336);
         this.worldGenSettingsComponent.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         drawString(â˜ƒ, this.font, NAME_LABEL, this.width / 2 - 100, 47, -6250336);
         drawString(â˜ƒ, this.font, new TextComponent("").append(OUTPUT_DIR_INFO).append(" ").append(this.resultFolder), this.width / 2 - 100, 85, -6250336);
         this.nameEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         drawString(â˜ƒ, this.font, this.gameModeHelp1, this.width / 2 - 150, 122, -6250336);
         drawString(â˜ƒ, this.font, this.gameModeHelp2, this.width / 2 - 150, 134, -6250336);
         if (this.commandsButton.visible) {
            drawString(â˜ƒ, this.font, COMMANDS_INFO, this.width / 2 - 150, 172, -6250336);
         }
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected <T extends GuiEventListener & NarratableEntry> T addWidget(T var1) {
      return super.addWidget(â˜ƒ);
   }

   @Override
   protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T var1) {
      return super.addRenderableWidget(â˜ƒ);
   }

   @Nullable
   protected Path getTempDataPackDir() {
      if (this.tempDataPackDir == null) {
         try {
            this.tempDataPackDir = Files.createTempDirectory("mcworld-");
         } catch (IOException var2) {
            LOGGER.warn("Failed to create temporary dir", var2);
            SystemToast.onPackCopyFailure(this.minecraft, this.resultFolder);
            this.popScreen();
         }
      }

      return this.tempDataPackDir;
   }

   private void openDataPackSelectionScreen() {
      Pair<File, PackRepository> â˜ƒ = this.getDataPackSelectionSettings();
      if (â˜ƒ != null) {
         this.minecraft
            .setScreen(
               new PackSelectionScreen(this, â˜ƒ.getSecond(), this::tryApplyNewDataPacks, (File)â˜ƒ.getFirst(), new TranslatableComponent("dataPack.title"))
            );
      }
   }

   private void tryApplyNewDataPacks(PackRepository var1) {
      List<String> â˜ƒ = ImmutableList.copyOf(â˜ƒ.getSelectedIds());
      List<String> â˜ƒx = (List)â˜ƒ.getAvailableIds().stream().filter(var1x -> !â˜ƒ.contains(var1x)).collect(ImmutableList.toImmutableList());
      DataPackConfig â˜ƒxx = new DataPackConfig(â˜ƒ, â˜ƒx);
      if (â˜ƒ.equals(this.dataPacks.getEnabled())) {
         this.dataPacks = â˜ƒxx;
      } else {
         this.minecraft.tell(() -> this.minecraft.setScreen(new GenericDirtMessageScreen(new TranslatableComponent("dataPack.validation.working"))));
         ServerResources.loadResources(
               â˜ƒ.openAllSelected(),
               this.worldGenSettingsComponent.registryHolder(),
               Commands.CommandSelection.INTEGRATED,
               2,
               Util.backgroundExecutor(),
               this.minecraft
            )
            .thenAcceptAsync(var2x -> {
               this.dataPacks = â˜ƒ;
               this.worldGenSettingsComponent.updateDataPacks(var2x);
               var2x.close();
            }, this.minecraft)
            .handle(
               (var1x, var2x) -> {
                  if (var2x != null) {
                     LOGGER.warn("Failed to validate datapack", var2x);
                     this.minecraft
                        .tell(
                           () -> this.minecraft
                                 .setScreen(
                                    new ConfirmScreen(
                                       var1xx -> {
                                          if (var1xx) {
                                             this.openDataPackSelectionScreen();
                                          } else {
                                             this.dataPacks = DataPackConfig.DEFAULT;
                                             this.minecraft.setScreen(this);
                                          }
                                       },
                                       new TranslatableComponent("dataPack.validation.failed"),
                                       TextComponent.EMPTY,
                                       new TranslatableComponent("dataPack.validation.back"),
                                       new TranslatableComponent("dataPack.validation.reset")
                                    )
                                 )
                        );
                  } else {
                     this.minecraft.tell(() -> this.minecraft.setScreen(this));
                  }
      
                  return null;
               }
            );
      }
   }

   private void removeTempDataPackDir() {
      if (this.tempDataPackDir != null) {
         try {
            Stream<Path> â˜ƒ = Files.walk(this.tempDataPackDir);

            try {
               â˜ƒ.sorted(Comparator.reverseOrder()).forEach(var0 -> {
                  try {
                     Files.delete(var0);
                  } catch (IOException var2) {
                     LOGGER.warn("Failed to remove temporary file {}", var0, var2);
                  }
               });
            } catch (Throwable var5) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var4) {
                     var5.addSuppressed(var4);
                  }
               }

               throw var5;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }
         } catch (IOException var6) {
            LOGGER.warn("Failed to list temporary dir {}", this.tempDataPackDir);
         }

         this.tempDataPackDir = null;
      }
   }

   private static void copyBetweenDirs(Path var0, Path var1, Path var2) {
      try {
         Util.copyBetweenDirs(â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (IOException var4) {
         LOGGER.warn("Failed to copy datapack file from {} to {}", â˜ƒ, â˜ƒ);
         throw new CreateWorldScreen.OperationFailedException(var4);
      }
   }

   private boolean copyTempDataPackDirToNewWorld() {
      if (this.tempDataPackDir != null) {
         try (LevelStorageSource.LevelStorageAccess â˜ƒ = this.minecraft.getLevelSource().createAccess(this.resultFolder)) {
            Stream<Path> â˜ƒx = Files.walk(this.tempDataPackDir);

            try {
               Path â˜ƒxx = â˜ƒ.getLevelPath(LevelResource.DATAPACK_DIR);
               Files.createDirectories(â˜ƒxx);
               â˜ƒx.filter(var1x -> !var1x.equals(this.tempDataPackDir)).forEach(var2x -> copyBetweenDirs(this.tempDataPackDir, â˜ƒ, var2x));
            } catch (Throwable var7) {
               if (â˜ƒx != null) {
                  try {
                     â˜ƒx.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (â˜ƒx != null) {
               â˜ƒx.close();
            }
         } catch (CreateWorldScreen.OperationFailedException | IOException var9) {
            LOGGER.warn("Failed to copy datapacks to world {}", this.resultFolder, var9);
            SystemToast.onPackCopyFailure(this.minecraft, this.resultFolder);
            this.popScreen();
            return false;
         }
      }

      return true;
   }

   @Nullable
   public static Path createTempDataPackDirFromExistingWorld(Path var0, Minecraft var1) {
      MutableObject<Path> â˜ƒ = new MutableObject();

      try {
         Stream<Path> â˜ƒx = Files.walk(â˜ƒ);

         try {
            â˜ƒx.filter(var1x -> !var1x.equals(â˜ƒ)).forEach(var2x -> {
               Path â˜ƒ = (Path)â˜ƒ.getValue();
               if (â˜ƒ == null) {
                  try {
                     â˜ƒ = Files.createTempDirectory("mcworld-");
                  } catch (IOException var5) {
                     LOGGER.warn("Failed to create temporary dir");
                     throw new CreateWorldScreen.OperationFailedException(var5);
                  }

                  â˜ƒ.setValue(â˜ƒ);
               }

               copyBetweenDirs(â˜ƒ, â˜ƒ, var2x);
            });
         } catch (Throwable var7) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }
      } catch (CreateWorldScreen.OperationFailedException | IOException var8) {
         LOGGER.warn("Failed to copy datapacks from world {}", â˜ƒ, var8);
         SystemToast.onPackCopyFailure(â˜ƒ, â˜ƒ.toString());
         return null;
      }

      return (Path)â˜ƒ.getValue();
   }

   @Nullable
   private Pair<File, PackRepository> getDataPackSelectionSettings() {
      Path â˜ƒ = this.getTempDataPackDir();
      if (â˜ƒ != null) {
         File â˜ƒx = â˜ƒ.toFile();
         if (this.tempDataPackRepository == null) {
            this.tempDataPackRepository = new PackRepository(
               PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(â˜ƒx, PackSource.DEFAULT)
            );
            this.tempDataPackRepository.reload();
         }

         this.tempDataPackRepository.setSelected(this.dataPacks.getEnabled());
         return Pair.of(â˜ƒx, this.tempDataPackRepository);
      } else {
         return null;
      }
   }

   static class OperationFailedException extends RuntimeException {
      public OperationFailedException(Throwable var1) {
         super(â˜ƒ);
      }
   }

   static enum SelectedGameMode {
      SURVIVAL("survival", GameType.SURVIVAL),
      HARDCORE("hardcore", GameType.SURVIVAL),
      CREATIVE("creative", GameType.CREATIVE),
      DEBUG("spectator", GameType.SPECTATOR);

      final String name;
      final GameType gameType;
      private final Component displayName;

      private SelectedGameMode(String var3, GameType var4) {
         this.name = â˜ƒ;
         this.gameType = â˜ƒ;
         this.displayName = new TranslatableComponent("selectWorld.gameMode." + â˜ƒ);
      }

      public Component getDisplayName() {
         return this.displayName;
      }
   }
}
