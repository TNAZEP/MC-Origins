package com.mojang.realmsclient.client;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileDownload {
   static final Logger LOGGER = LogManager.getLogger();
   volatile boolean cancelled;
   volatile boolean finished;
   volatile boolean error;
   volatile boolean extracting;
   private volatile File tempFile;
   volatile File resourcePackPath;
   private volatile HttpGet request;
   private Thread currentThread;
   private final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
   private static final String[] INVALID_FILE_NAMES = new String[]{
      "CON",
      "COM",
      "PRN",
      "AUX",
      "CLOCK$",
      "NUL",
      "COM1",
      "COM2",
      "COM3",
      "COM4",
      "COM5",
      "COM6",
      "COM7",
      "COM8",
      "COM9",
      "LPT1",
      "LPT2",
      "LPT3",
      "LPT4",
      "LPT5",
      "LPT6",
      "LPT7",
      "LPT8",
      "LPT9"
   };

   public long contentLength(String var1) {
      CloseableHttpClient â˜ƒ = null;
      HttpGet â˜ƒx = null;

      long var5;
      try {
         â˜ƒx = new HttpGet(â˜ƒ);
         â˜ƒ = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
         CloseableHttpResponse â˜ƒxx = â˜ƒ.execute(â˜ƒx);
         return Long.parseLong(â˜ƒxx.getFirstHeader("Content-Length").getValue());
      } catch (Throwable var16) {
         LOGGER.error("Unable to get content length for download");
         var5 = 0L;
      } finally {
         if (â˜ƒx != null) {
            â˜ƒx.releaseConnection();
         }

         if (â˜ƒ != null) {
            try {
               â˜ƒ.close();
            } catch (IOException var15) {
               LOGGER.error("Could not close http client", var15);
            }
         }
      }

      return var5;
   }

   public void download(WorldDownload var1, String var2, RealmsDownloadLatestWorldScreen.DownloadStatus var3, LevelStorageSource var4) {
      if (this.currentThread == null) {
         this.currentThread = new Thread(() -> {
            CloseableHttpClient â˜ƒ = null;

            try {
               this.tempFile = File.createTempFile("backup", ".tar.gz");
               this.request = new HttpGet(â˜ƒ.downloadLink);
               â˜ƒ = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
               HttpResponse â˜ƒx = â˜ƒ.execute(this.request);
               â˜ƒ.totalBytes = Long.parseLong(â˜ƒx.getFirstHeader("Content-Length").getValue());
               if (â˜ƒx.getStatusLine().getStatusCode() == 200) {
                  OutputStream â˜ƒxx = new FileOutputStream(this.tempFile);
                  FileDownload.ProgressListener â˜ƒxxx = new FileDownload.ProgressListener(â˜ƒ.trim(), this.tempFile, â˜ƒ, â˜ƒ);
                  FileDownload.DownloadCountingOutputStream â˜ƒxxxx = new FileDownload.DownloadCountingOutputStream(â˜ƒxx);
                  â˜ƒxxxx.setListener(â˜ƒxxx);
                  IOUtils.copy(â˜ƒx.getEntity().getContent(), â˜ƒxxxx);
                  return;
               }

               this.error = true;
               this.request.abort();
            } catch (Exception var93) {
               LOGGER.error("Caught exception while downloading: {}", var93.getMessage());
               this.error = true;
               return;
            } finally {
               this.request.releaseConnection();
               if (this.tempFile != null) {
                  this.tempFile.delete();
               }

               if (!this.error) {
                  if (!â˜ƒ.resourcePackUrl.isEmpty() && !â˜ƒ.resourcePackHash.isEmpty()) {
                     try {
                        this.tempFile = File.createTempFile("resources", ".tar.gz");
                        this.request = new HttpGet(â˜ƒ.resourcePackUrl);
                        HttpResponse â˜ƒx = â˜ƒ.execute(this.request);
                        â˜ƒ.totalBytes = Long.parseLong(â˜ƒx.getFirstHeader("Content-Length").getValue());
                        if (â˜ƒx.getStatusLine().getStatusCode() != 200) {
                           this.error = true;
                           this.request.abort();
                           return;
                        }

                        OutputStream â˜ƒx = new FileOutputStream(this.tempFile);
                        FileDownload.ResourcePackProgressListener â˜ƒxx = new FileDownload.ResourcePackProgressListener(this.tempFile, â˜ƒ, â˜ƒ);
                        FileDownload.DownloadCountingOutputStream â˜ƒxxx = new FileDownload.DownloadCountingOutputStream(â˜ƒx);
                        â˜ƒxxx.setListener(â˜ƒxx);
                        IOUtils.copy(â˜ƒx.getEntity().getContent(), â˜ƒxxx);
                     } catch (Exception var91) {
                        LOGGER.error("Caught exception while downloading: {}", var91.getMessage());
                        this.error = true;
                     } finally {
                        this.request.releaseConnection();
                        if (this.tempFile != null) {
                           this.tempFile.delete();
                        }
                     }
                  } else {
                     this.finished = true;
                  }
               }

               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (IOException var90) {
                     LOGGER.error("Failed to close Realms download client");
                  }
               }
            }
         });
         this.currentThread.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(LOGGER));
         this.currentThread.start();
      }
   }

   public void cancel() {
      if (this.request != null) {
         this.request.abort();
      }

      if (this.tempFile != null) {
         this.tempFile.delete();
      }

      this.cancelled = true;
   }

   public boolean isFinished() {
      return this.finished;
   }

   public boolean isError() {
      return this.error;
   }

   public boolean isExtracting() {
      return this.extracting;
   }

   public static String findAvailableFolderName(String var0) {
      â˜ƒ = â˜ƒ.replaceAll("[\\./\"]", "_");

      for(String â˜ƒ : INVALID_FILE_NAMES) {
         if (â˜ƒ.equalsIgnoreCase(â˜ƒ)) {
            â˜ƒ = "_" + â˜ƒ + "_";
         }
      }

      return â˜ƒ;
   }

   void untarGzipArchive(String var1, File var2, LevelStorageSource var3) throws IOException {
      Pattern â˜ƒ = Pattern.compile(".*-([0-9]+)$");
      int â˜ƒx = 1;

      for(char â˜ƒxx : SharedConstants.ILLEGAL_FILE_CHARACTERS) {
         â˜ƒ = â˜ƒ.replace(â˜ƒxx, '_');
      }

      if (StringUtils.isEmpty(â˜ƒ)) {
         â˜ƒ = "Realm";
      }

      â˜ƒ = findAvailableFolderName(â˜ƒ);

      try {
         for(LevelSummary â˜ƒxx : â˜ƒ.getLevelList()) {
            if (â˜ƒxx.getLevelId().toLowerCase(Locale.ROOT).startsWith(â˜ƒ.toLowerCase(Locale.ROOT))) {
               Matcher â˜ƒxxx = â˜ƒ.matcher(â˜ƒxx.getLevelId());
               if (â˜ƒxxx.matches()) {
                  if (Integer.valueOf(â˜ƒxxx.group(1)) > â˜ƒx) {
                     â˜ƒx = Integer.valueOf(â˜ƒxxx.group(1));
                  }
               } else {
                  ++â˜ƒx;
               }
            }
         }
      } catch (Exception var39) {
         LOGGER.error("Error getting level list", var39);
         this.error = true;
         return;
      }

      String â˜ƒxx;
      if (â˜ƒ.isNewLevelIdAcceptable(â˜ƒ) && â˜ƒx <= 1) {
         â˜ƒxx = â˜ƒ;
      } else {
         â˜ƒxx = â˜ƒ + (â˜ƒx == 1 ? "" : "-" + â˜ƒx);
         if (!â˜ƒ.isNewLevelIdAcceptable(â˜ƒxx)) {
            boolean â˜ƒxx = false;

            while(!â˜ƒxx) {
               ++â˜ƒx;
               â˜ƒxx = â˜ƒ + (â˜ƒx == 1 ? "" : "-" + â˜ƒx);
               if (â˜ƒ.isNewLevelIdAcceptable(â˜ƒxx)) {
                  â˜ƒxx = true;
               }
            }
         }
      }

      TarArchiveInputStream â˜ƒxx = null;
      File â˜ƒxxx = new File(Minecraft.getInstance().gameDirectory.getAbsolutePath(), "saves");

      try {
         â˜ƒxxx.mkdir();
         â˜ƒxx = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(â˜ƒ))));

         for(TarArchiveEntry â˜ƒxxxx = â˜ƒxx.getNextTarEntry(); â˜ƒxxxx != null; â˜ƒxxxx = â˜ƒxx.getNextTarEntry()) {
            File â˜ƒxxxxx = new File(â˜ƒxxx, â˜ƒxxxx.getName().replace("world", â˜ƒxx));
            if (â˜ƒxxxx.isDirectory()) {
               â˜ƒxxxxx.mkdirs();
            } else {
               â˜ƒxxxxx.createNewFile();
               FileOutputStream â˜ƒxxxxx = new FileOutputStream(â˜ƒxxxxx);

               try {
                  IOUtils.copy(â˜ƒxx, â˜ƒxxxxx);
               } catch (Throwable var34) {
                  try {
                     â˜ƒxxxxx.close();
                  } catch (Throwable var33) {
                     var34.addSuppressed(var33);
                  }

                  throw var34;
               }

               â˜ƒxxxxx.close();
            }
         }
      } catch (Exception var37) {
         LOGGER.error("Error extracting world", var37);
         this.error = true;
      } finally {
         if (â˜ƒxx != null) {
            â˜ƒxx.close();
         }

         if (â˜ƒ != null) {
            â˜ƒ.delete();
         }

         try (LevelStorageSource.LevelStorageAccess â˜ƒxxxx = â˜ƒ.createAccess(â˜ƒxx)) {
            â˜ƒxxxx.renameLevel(â˜ƒxx.trim());
            Path â˜ƒxxxxx = â˜ƒxxxx.getLevelPath(LevelResource.LEVEL_DATA_FILE);
            deletePlayerTag(â˜ƒxxxxx.toFile());
         } catch (IOException var36) {
            LOGGER.error("Failed to rename unpacked realms level {}", â˜ƒxx, var36);
         }

         this.resourcePackPath = new File(â˜ƒxxx, â˜ƒxx + File.separator + "resources.zip");
      }
   }

   private static void deletePlayerTag(File var0) {
      if (â˜ƒ.exists()) {
         try {
            CompoundTag â˜ƒ = NbtIo.readCompressed(â˜ƒ);
            CompoundTag â˜ƒx = â˜ƒ.getCompound("Data");
            â˜ƒx.remove("Player");
            NbtIo.writeCompressed(â˜ƒ, â˜ƒ);
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   class DownloadCountingOutputStream extends CountingOutputStream {
      private ActionListener listener;

      public DownloadCountingOutputStream(OutputStream var2) {
         super(â˜ƒ);
      }

      public void setListener(ActionListener var1) {
         this.listener = â˜ƒ;
      }

      @Override
      protected void afterWrite(int var1) throws IOException {
         super.afterWrite(â˜ƒ);
         if (this.listener != null) {
            this.listener.actionPerformed(new ActionEvent(this, 0, null));
         }
      }
   }

   class ProgressListener implements ActionListener {
      private final String worldName;
      private final File tempFile;
      private final LevelStorageSource levelStorageSource;
      private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;

      ProgressListener(String var2, File var3, LevelStorageSource var4, RealmsDownloadLatestWorldScreen.DownloadStatus var5) {
         this.worldName = â˜ƒ;
         this.tempFile = â˜ƒ;
         this.levelStorageSource = â˜ƒ;
         this.downloadStatus = â˜ƒ;
      }

      public void actionPerformed(ActionEvent var1) {
         this.downloadStatus.bytesWritten = ((FileDownload.DownloadCountingOutputStream)â˜ƒ.getSource()).getByteCount();
         if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled && !FileDownload.this.error) {
            try {
               FileDownload.this.extracting = true;
               FileDownload.this.untarGzipArchive(this.worldName, this.tempFile, this.levelStorageSource);
            } catch (IOException var3) {
               FileDownload.LOGGER.error("Error extracting archive", var3);
               FileDownload.this.error = true;
            }
         }
      }
   }

   class ResourcePackProgressListener implements ActionListener {
      private final File tempFile;
      private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;
      private final WorldDownload worldDownload;

      ResourcePackProgressListener(File var2, RealmsDownloadLatestWorldScreen.DownloadStatus var3, WorldDownload var4) {
         this.tempFile = â˜ƒ;
         this.downloadStatus = â˜ƒ;
         this.worldDownload = â˜ƒ;
      }

      public void actionPerformed(ActionEvent var1) {
         this.downloadStatus.bytesWritten = ((FileDownload.DownloadCountingOutputStream)â˜ƒ.getSource()).getByteCount();
         if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled) {
            try {
               String â˜ƒ = Hashing.sha1().hashBytes(Files.toByteArray(this.tempFile)).toString();
               if (â˜ƒ.equals(this.worldDownload.resourcePackHash)) {
                  FileUtils.copyFile(this.tempFile, FileDownload.this.resourcePackPath);
                  FileDownload.this.finished = true;
               } else {
                  FileDownload.LOGGER.error("Resourcepack had wrong hash (expected {}, found {}). Deleting it.", this.worldDownload.resourcePackHash, â˜ƒ);
                  FileUtils.deleteQuietly(this.tempFile);
                  FileDownload.this.error = true;
               }
            } catch (IOException var3) {
               FileDownload.LOGGER.error("Error copying resourcepack file: {}", var3.getMessage());
               FileDownload.this.error = true;
            }
         }
      }
   }
}
