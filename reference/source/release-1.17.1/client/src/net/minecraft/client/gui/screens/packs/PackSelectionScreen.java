package net.minecraft.client.gui.screens.packs;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PackSelectionScreen extends Screen {
   static final Logger LOGGER = LogManager.getLogger();
   private static final int LIST_WIDTH = 200;
   private static final Component DRAG_AND_DROP = new TranslatableComponent("pack.dropInfo").withStyle(ChatFormatting.GRAY);
   static final Component DIRECTORY_BUTTON_TOOLTIP = new TranslatableComponent("pack.folderInfo");
   private static final int RELOAD_COOLDOWN = 20;
   private static final ResourceLocation DEFAULT_ICON = new ResourceLocation("textures/misc/unknown_pack.png");
   private final PackSelectionModel model;
   private final Screen lastScreen;
   @Nullable
   private PackSelectionScreen.Watcher watcher;
   private long ticksToReload;
   private TransferableSelectionList availablePackList;
   private TransferableSelectionList selectedPackList;
   private final File packDir;
   private Button doneButton;
   private final Map<String, ResourceLocation> packIcons = Maps.newHashMap();

   public PackSelectionScreen(Screen var1, PackRepository var2, Consumer<PackRepository> var3, File var4, Component var5) {
      super(â˜ƒ);
      this.lastScreen = â˜ƒ;
      this.model = new PackSelectionModel(this::populateLists, this::getPackIcon, â˜ƒ, â˜ƒ);
      this.packDir = â˜ƒ;
      this.watcher = PackSelectionScreen.Watcher.create(â˜ƒ);
   }

   @Override
   public void onClose() {
      this.model.commit();
      this.minecraft.setScreen(this.lastScreen);
      this.closeWatcher();
   }

   private void closeWatcher() {
      if (this.watcher != null) {
         try {
            this.watcher.close();
            this.watcher = null;
         } catch (Exception var2) {
         }
      }
   }

   @Override
   protected void init() {
      this.doneButton = this.addRenderableWidget(new Button(this.width / 2 + 4, this.height - 48, 150, 20, CommonComponents.GUI_DONE, var1 -> this.onClose()));
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 154,
            this.height - 48,
            150,
            20,
            new TranslatableComponent("pack.openFolder"),
            var1 -> Util.getPlatform().openFile(this.packDir),
            new Button.OnTooltip() {
               @Override
               public void onTooltip(Button var1, PoseStack var2, int var3, int var4) {
                  PackSelectionScreen.this.renderTooltip(â˜ƒ, PackSelectionScreen.DIRECTORY_BUTTON_TOOLTIP, â˜ƒ, â˜ƒ);
               }
      
               @Override
               public void narrateTooltip(Consumer<Component> var1) {
                  â˜ƒ.accept(PackSelectionScreen.DIRECTORY_BUTTON_TOOLTIP);
               }
            }
         )
      );
      this.availablePackList = new TransferableSelectionList(this.minecraft, 200, this.height, new TranslatableComponent("pack.available.title"));
      this.availablePackList.setLeftPos(this.width / 2 - 4 - 200);
      this.addWidget(this.availablePackList);
      this.selectedPackList = new TransferableSelectionList(this.minecraft, 200, this.height, new TranslatableComponent("pack.selected.title"));
      this.selectedPackList.setLeftPos(this.width / 2 + 4);
      this.addWidget(this.selectedPackList);
      this.reload();
   }

   @Override
   public void tick() {
      if (this.watcher != null) {
         try {
            if (this.watcher.pollForChanges()) {
               this.ticksToReload = 20L;
            }
         } catch (IOException var2) {
            LOGGER.warn("Failed to poll for directory {} changes, stopping", this.packDir);
            this.closeWatcher();
         }
      }

      if (this.ticksToReload > 0L && --this.ticksToReload == 0L) {
         this.reload();
      }
   }

   private void populateLists() {
      this.updateList(this.selectedPackList, this.model.getSelected());
      this.updateList(this.availablePackList, this.model.getUnselected());
      this.doneButton.active = !this.selectedPackList.children().isEmpty();
   }

   private void updateList(TransferableSelectionList var1, Stream<PackSelectionModel.Entry> var2) {
      â˜ƒ.children().clear();
      â˜ƒ.forEach(var2x -> â˜ƒ.children().add(new TransferableSelectionList.PackEntry(this.minecraft, â˜ƒ, this, var2x)));
   }

   private void reload() {
      this.model.findNewPacks();
      this.populateLists();
      this.ticksToReload = 0L;
      this.packIcons.clear();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderDirtBackground(0);
      this.availablePackList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.selectedPackList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 8, 16777215);
      drawCenteredString(â˜ƒ, this.font, DRAG_AND_DROP, this.width / 2, 20, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected static void copyPacks(Minecraft var0, List<Path> var1, Path var2) {
      MutableBoolean â˜ƒ = new MutableBoolean();
      â˜ƒ.forEach(var2x -> {
         try {
            Stream<Path> â˜ƒ = Files.walk(var2x);

            try {
               â˜ƒ.forEach(var3x -> {
                  try {
                     Util.copyBetweenDirs(var2x.getParent(), â˜ƒ, var3x);
                  } catch (IOException var5) {
                     LOGGER.warn("Failed to copy datapack file  from {} to {}", var3x, â˜ƒ, var5);
                     â˜ƒ.setTrue();
                  }
               });
            } catch (Throwable var7) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }
         } catch (IOException var8) {
            LOGGER.warn("Failed to copy datapack file from {} to {}", var2x, â˜ƒ);
            â˜ƒ.setTrue();
         }
      });
      if (â˜ƒ.isTrue()) {
         SystemToast.onPackCopyFailure(â˜ƒ, â˜ƒ.toString());
      }
   }

   @Override
   public void onFilesDrop(List<Path> var1) {
      String â˜ƒ = (String)â˜ƒ.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
      this.minecraft.setScreen(new ConfirmScreen(var2x -> {
         if (var2x) {
            copyPacks(this.minecraft, â˜ƒ, this.packDir.toPath());
            this.reload();
         }

         this.minecraft.setScreen(this);
      }, new TranslatableComponent("pack.dropConfirm"), new TextComponent(â˜ƒ)));
   }

   private ResourceLocation loadPackIcon(TextureManager var1, Pack var2) {
      try {
         ResourceLocation var8;
         try (PackResources â˜ƒ = â˜ƒ.open()) {
            InputStream â˜ƒx = â˜ƒ.getRootResource("pack.png");

            label95: {
               ResourceLocation var5;
               try {
                  if (â˜ƒx != null) {
                     String â˜ƒxx = â˜ƒ.getId();
                     ResourceLocation â˜ƒxxx = new ResourceLocation(
                        "minecraft",
                        "pack/" + Util.sanitizeName(â˜ƒxx, ResourceLocation::validPathChar) + "/" + Hashing.sha1().hashUnencodedChars(â˜ƒxx) + "/icon"
                     );
                     NativeImage â˜ƒxxxx = NativeImage.read(â˜ƒx);
                     â˜ƒ.register(â˜ƒxxx, new DynamicTexture(â˜ƒxxxx));
                     var8 = â˜ƒxxx;
                     break label95;
                  }

                  var5 = DEFAULT_ICON;
               } catch (Throwable var11) {
                  if (â˜ƒx != null) {
                     try {
                        â˜ƒx.close();
                     } catch (Throwable var10) {
                        var11.addSuppressed(var10);
                     }
                  }

                  throw var11;
               }

               if (â˜ƒx != null) {
                  â˜ƒx.close();
               }

               return var5;
            }

            if (â˜ƒx != null) {
               â˜ƒx.close();
            }
         }

         return var8;
      } catch (FileNotFoundException var13) {
      } catch (Exception var14) {
         LOGGER.warn("Failed to load icon from pack {}", â˜ƒ.getId(), var14);
      }

      return DEFAULT_ICON;
   }

   private ResourceLocation getPackIcon(Pack var1) {
      return (ResourceLocation)this.packIcons.computeIfAbsent(â˜ƒ.getId(), var2 -> this.loadPackIcon(this.minecraft.getTextureManager(), â˜ƒ));
   }

   static class Watcher implements AutoCloseable {
      private final WatchService watcher;
      private final Path packPath;

      public Watcher(File var1) throws IOException {
         this.packPath = â˜ƒ.toPath();
         this.watcher = this.packPath.getFileSystem().newWatchService();

         try {
            this.watchDir(this.packPath);
            DirectoryStream<Path> â˜ƒ = Files.newDirectoryStream(this.packPath);

            try {
               for(Path â˜ƒx : â˜ƒ) {
                  if (Files.isDirectory(â˜ƒx, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                     this.watchDir(â˜ƒx);
                  }
               }
            } catch (Throwable var6) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }
         } catch (Exception var7) {
            this.watcher.close();
            throw var7;
         }
      }

      @Nullable
      public static PackSelectionScreen.Watcher create(File var0) {
         try {
            return new PackSelectionScreen.Watcher(â˜ƒ);
         } catch (IOException var2) {
            PackSelectionScreen.LOGGER.warn("Failed to initialize pack directory {} monitoring", â˜ƒ, var2);
            return null;
         }
      }

      private void watchDir(Path var1) throws IOException {
         â˜ƒ.register(this.watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
      }

      public boolean pollForChanges() throws IOException {
         boolean â˜ƒ = false;

         WatchKey â˜ƒ;
         while((â˜ƒ = this.watcher.poll()) != null) {
            for(WatchEvent<?> â˜ƒx : â˜ƒ.pollEvents()) {
               â˜ƒ = true;
               if (â˜ƒ.watchable() == this.packPath && â˜ƒx.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  Path â˜ƒxx = this.packPath.resolve((Path)â˜ƒx.context());
                  if (Files.isDirectory(â˜ƒxx, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                     this.watchDir(â˜ƒxx);
                  }
               }
            }

            â˜ƒ.reset();
         }

         return â˜ƒ;
      }

      public void close() throws IOException {
         this.watcher.close();
      }
   }
}
