package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.util.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientPackSource implements RepositorySource {
   private static final PackMetadataSection BUILT_IN = new PackMetadataSection(
      new TranslatableComponent("resourcePack.vanilla.description"), PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion())
   );
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Pattern SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
   private static final int MAX_WEB_FILESIZE = 104857600;
   private static final int MAX_KEPT_PACKS = 10;
   private static final String VANILLA_ID = "vanilla";
   private static final String SERVER_ID = "server";
   private static final String PROGRAMMER_ART_ID = "programer_art";
   private static final String PROGRAMMER_ART_NAME = "Programmer Art";
   private static final Component APPLYING_PACK_TEXT = new TranslatableComponent("multiplayer.applyingPack");
   private final VanillaPackResources vanillaPack;
   private final File serverPackDir;
   private final ReentrantLock downloadLock = new ReentrantLock();
   private final AssetIndex assetIndex;
   @Nullable
   private CompletableFuture<?> currentDownload;
   @Nullable
   private Pack serverPack;

   public ClientPackSource(File var1, AssetIndex var2) {
      this.serverPackDir = â˜ƒ;
      this.assetIndex = â˜ƒ;
      this.vanillaPack = new DefaultClientPackResources(BUILT_IN, â˜ƒ);
   }

   @Override
   public void loadPacks(Consumer<Pack> var1, Pack.PackConstructor var2) {
      Pack â˜ƒ = Pack.create("vanilla", true, () -> this.vanillaPack, â˜ƒ, Pack.Position.BOTTOM, PackSource.BUILT_IN);
      if (â˜ƒ != null) {
         â˜ƒ.accept(â˜ƒ);
      }

      if (this.serverPack != null) {
         â˜ƒ.accept(this.serverPack);
      }

      Pack â˜ƒ = this.createProgrammerArtPack(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.accept(â˜ƒ);
      }
   }

   public VanillaPackResources getVanillaPack() {
      return this.vanillaPack;
   }

   private static Map<String, String> getDownloadHeaders() {
      Map<String, String> â˜ƒ = Maps.newHashMap();
      â˜ƒ.put("X-Minecraft-Username", Minecraft.getInstance().getUser().getName());
      â˜ƒ.put("X-Minecraft-UUID", Minecraft.getInstance().getUser().getUuid());
      â˜ƒ.put("X-Minecraft-Version", SharedConstants.getCurrentVersion().getName());
      â˜ƒ.put("X-Minecraft-Version-ID", SharedConstants.getCurrentVersion().getId());
      â˜ƒ.put("X-Minecraft-Pack-Format", String.valueOf(PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion())));
      â˜ƒ.put("User-Agent", "Minecraft Java/" + SharedConstants.getCurrentVersion().getName());
      return â˜ƒ;
   }

   public CompletableFuture<?> downloadAndSelectResourcePack(String var1, String var2, boolean var3) {
      String â˜ƒ = DigestUtils.sha1Hex(â˜ƒ);
      String â˜ƒx = SHA1.matcher(â˜ƒ).matches() ? â˜ƒ : "";
      this.downloadLock.lock();

      CompletableFuture var14;
      try {
         this.clearServerPack();
         this.clearOldDownloads();
         File â˜ƒxxx = new File(this.serverPackDir, â˜ƒ);
         CompletableFuture<?> â˜ƒxx;
         if (â˜ƒxxx.exists()) {
            â˜ƒxx = CompletableFuture.completedFuture("");
         } else {
            ProgressScreen â˜ƒxx = new ProgressScreen(â˜ƒ);
            Map<String, String> â˜ƒxxx = getDownloadHeaders();
            Minecraft â˜ƒxxxx = Minecraft.getInstance();
            â˜ƒxxxx.executeBlocking(() -> â˜ƒ.setScreen(â˜ƒ));
            â˜ƒxx = HttpUtil.downloadTo(â˜ƒxxx, â˜ƒ, â˜ƒxxx, 104857600, â˜ƒxx, â˜ƒxxxx.getProxy());
         }

         this.currentDownload = â˜ƒxx.thenCompose(var4x -> {
               if (!this.checkHash(â˜ƒ, â˜ƒ)) {
                  return Util.failedFuture(new RuntimeException("Hash check failure for file " + â˜ƒ + ", see log"));
               } else {
                  Minecraft â˜ƒ = Minecraft.getInstance();
                  â˜ƒ.execute(() -> {
                     if (!â˜ƒ) {
                        â˜ƒ.setScreen(new GenericDirtMessageScreen(APPLYING_PACK_TEXT));
                     }
                  });
                  return this.setServerPack(â˜ƒ, PackSource.SERVER);
               }
            })
            .whenComplete(
               (var1x, var2x) -> {
                  if (var2x != null) {
                     LOGGER.warn("Pack application failed: {}, deleting file {}", var2x.getMessage(), â˜ƒ);
                     deleteQuietly(â˜ƒ);
                     Minecraft â˜ƒ = Minecraft.getInstance();
                     â˜ƒ.execute(
                        () -> â˜ƒ.setScreen(
                              new ConfirmScreen(
                                 var1xx -> {
                                    if (var1xx) {
                                       â˜ƒ.setScreen(null);
                                    } else {
                                       ClientPacketListener â˜ƒ = â˜ƒ.getConnection();
                                       if (â˜ƒ != null) {
                                          â˜ƒ.getConnection().disconnect(new TranslatableComponent("connect.aborted"));
                                       }
                                    }
                                 },
                                 new TranslatableComponent("multiplayer.texturePrompt.failure.line1"),
                                 new TranslatableComponent("multiplayer.texturePrompt.failure.line2"),
                                 CommonComponents.GUI_PROCEED,
                                 new TranslatableComponent("menu.disconnect")
                              )
                           )
                     );
                  }
               }
            );
         var14 = this.currentDownload;
      } finally {
         this.downloadLock.unlock();
      }

      return var14;
   }

   private static void deleteQuietly(File var0) {
      try {
         Files.delete(â˜ƒ.toPath());
      } catch (IOException var2) {
         LOGGER.warn("Failed to delete file {}: {}", â˜ƒ, var2.getMessage());
      }
   }

   public void clearServerPack() {
      this.downloadLock.lock();

      try {
         if (this.currentDownload != null) {
            this.currentDownload.cancel(true);
         }

         this.currentDownload = null;
         if (this.serverPack != null) {
            this.serverPack = null;
            Minecraft.getInstance().delayTextureReload();
         }
      } finally {
         this.downloadLock.unlock();
      }
   }

   private boolean checkHash(String var1, File var2) {
      try {
         FileInputStream â˜ƒ = new FileInputStream(â˜ƒ);

         String â˜ƒ;
         try {
            â˜ƒ = DigestUtils.sha1Hex(â˜ƒ);
         } catch (Throwable var8) {
            try {
               â˜ƒ.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         â˜ƒ.close();
         if (â˜ƒ.isEmpty()) {
            LOGGER.info("Found file {} without verification hash", â˜ƒ);
            return true;
         }

         if (â˜ƒ.toLowerCase(Locale.ROOT).equals(â˜ƒ.toLowerCase(Locale.ROOT))) {
            LOGGER.info("Found file {} matching requested hash {}", â˜ƒ, â˜ƒ);
            return true;
         }

         LOGGER.warn("File {} had wrong hash (expected {}, found {}).", â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (IOException var9) {
         LOGGER.warn("File {} couldn't be hashed.", â˜ƒ, var9);
      }

      return false;
   }

   private void clearOldDownloads() {
      try {
         List<File> â˜ƒ = Lists.newArrayList(FileUtils.listFiles(this.serverPackDir, TrueFileFilter.TRUE, null));
         â˜ƒ.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         int â˜ƒx = 0;

         for(File â˜ƒxx : â˜ƒ) {
            if (â˜ƒx++ >= 10) {
               LOGGER.info("Deleting old server resource pack {}", â˜ƒxx.getName());
               FileUtils.deleteQuietly(â˜ƒxx);
            }
         }
      } catch (IllegalArgumentException var5) {
         LOGGER.error("Error while deleting old server resource pack : {}", var5.getMessage());
      }
   }

   public CompletableFuture<Void> setServerPack(File var1, PackSource var2) {
      PackMetadataSection â˜ƒ;
      try (FilePackResources â˜ƒ = new FilePackResources(â˜ƒ)) {
         â˜ƒ = â˜ƒ.getMetadataSection(PackMetadataSection.SERIALIZER);
      } catch (IOException var9) {
         return Util.failedFuture(new IOException(String.format("Invalid resourcepack at %s", â˜ƒ), var9));
      }

      LOGGER.info("Applying server pack {}", â˜ƒ);
      this.serverPack = new Pack(
         "server",
         true,
         () -> new FilePackResources(â˜ƒ),
         new TranslatableComponent("resourcePack.server.name"),
         â˜ƒ.getDescription(),
         PackCompatibility.forMetadata(â˜ƒ, PackType.CLIENT_RESOURCES),
         Pack.Position.TOP,
         true,
         â˜ƒ
      );
      return Minecraft.getInstance().delayTextureReload();
   }

   @Nullable
   private Pack createProgrammerArtPack(Pack.PackConstructor var1) {
      Pack â˜ƒ = null;
      File â˜ƒx = this.assetIndex.getFile(new ResourceLocation("resourcepacks/programmer_art.zip"));
      if (â˜ƒx != null && â˜ƒx.isFile()) {
         â˜ƒ = createProgrammerArtPack(â˜ƒ, () -> createProgrammerArtZipPack(â˜ƒ));
      }

      if (â˜ƒ == null && SharedConstants.IS_RUNNING_IN_IDE) {
         File â˜ƒ = this.assetIndex.getRootFile("../resourcepacks/programmer_art");
         if (â˜ƒ != null && â˜ƒ.isDirectory()) {
            â˜ƒ = createProgrammerArtPack(â˜ƒ, () -> createProgrammerArtDirPack(â˜ƒ));
         }
      }

      return â˜ƒ;
   }

   @Nullable
   private static Pack createProgrammerArtPack(Pack.PackConstructor var0, Supplier<PackResources> var1) {
      return Pack.create("programer_art", false, â˜ƒ, â˜ƒ, Pack.Position.TOP, PackSource.BUILT_IN);
   }

   private static FolderPackResources createProgrammerArtDirPack(File var0) {
      return new FolderPackResources(â˜ƒ) {
         @Override
         public String getName() {
            return "Programmer Art";
         }
      };
   }

   private static PackResources createProgrammerArtZipPack(File var0) {
      return new FilePackResources(â˜ƒ) {
         @Override
         public String getName() {
            return "Programmer Art";
         }
      };
   }
}
