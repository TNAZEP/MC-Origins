package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.FilePack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.VanillaPack;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadingPackFinder implements IPackFinder {
   private static final Logger field_195751_a = LogManager.getLogger();
   private static final Pattern field_195752_b = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final VanillaPack field_195753_c;
   private final File field_195754_d;
   private final ReentrantLock field_195755_e = new ReentrantLock();
   @Nullable
   private ListenableFuture<?> field_195756_f;
   @Nullable
   private ResourcePackInfoClient field_195757_g;

   public DownloadingPackFinder(File var1, ResourceIndex var2) {
      this.field_195754_d = ☃;
      this.field_195753_c = new VirtualAssetsPack(☃);
   }

   @Override
   public <T extends ResourcePackInfo> void func_195730_a(Map<String, T> var1, ResourcePackInfo.IFactory<T> var2) {
      T ☃ = ResourcePackInfo.func_195793_a("vanilla", true, () -> this.field_195753_c, ☃, ResourcePackInfo.Priority.BOTTOM);
      if (☃ != null) {
         ☃.put("vanilla", ☃);
      }

      if (this.field_195757_g != null) {
         ☃.put("server", this.field_195757_g);
      }
   }

   public VanillaPack func_195746_a() {
      return this.field_195753_c;
   }

   public static Map<String, String> func_195742_b() {
      Map<String, String> ☃ = Maps.newHashMap();
      ☃.put("X-Minecraft-Username", Minecraft.func_71410_x().func_110432_I().func_111285_a());
      ☃.put("X-Minecraft-UUID", Minecraft.func_71410_x().func_110432_I().func_148255_b());
      ☃.put("X-Minecraft-Version", "1.13.2");
      ☃.put("X-Minecraft-Pack-Format", String.valueOf(4));
      ☃.put("User-Agent", "Minecraft Java/1.13.2");
      return ☃;
   }

   public ListenableFuture<?> func_195744_a(String var1, String var2) {
      String ☃ = DigestUtils.sha1Hex(☃);
      final String ☃x = field_195752_b.matcher(☃).matches() ? ☃ : "";
      final File ☃xx = new File(this.field_195754_d, ☃);
      this.field_195755_e.lock();

      try {
         this.func_195749_c();
         if (☃xx.exists()) {
            if (this.func_195745_a(☃x, ☃xx)) {
               return this.func_195741_a(☃xx);
            }

            field_195751_a.warn("Deleting file {}", ☃xx);
            FileUtils.deleteQuietly(☃xx);
         }

         this.func_195747_e();
         GuiScreenWorking ☃xxx = new GuiScreenWorking();
         Map<String, String> ☃xxxx = func_195742_b();
         Minecraft ☃xxxxx = Minecraft.func_71410_x();
         Futures.getUnchecked(☃xxxxx.func_152344_a(() -> ☃.func_147108_a(☃)));
         final SettableFuture<Object> ☃xxxxxx = SettableFuture.create();
         this.field_195756_f = HttpUtil.func_180192_a(☃xx, ☃, ☃xxxx, 52428800, ☃xxx, ☃xxxxx.func_110437_J());
         Futures.addCallback(this.field_195756_f, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object var1) {
               if (DownloadingPackFinder.this.func_195745_a(☃, ☃)) {
                  DownloadingPackFinder.this.func_195741_a(☃);
                  ☃.set(null);
               } else {
                  DownloadingPackFinder.field_195751_a.warn("Deleting file {}", ☃);
                  FileUtils.deleteQuietly(☃);
               }
            }

            @Override
            public void onFailure(Throwable var1) {
               FileUtils.deleteQuietly(☃);
               ☃.setException(☃);
            }
         });
         return this.field_195756_f;
      } finally {
         this.field_195755_e.unlock();
      }
   }

   public void func_195749_c() {
      this.field_195755_e.lock();

      try {
         if (this.field_195756_f != null) {
            this.field_195756_f.cancel(true);
         }

         this.field_195756_f = null;
         if (this.field_195757_g != null) {
            this.field_195757_g = null;
            Minecraft.func_71410_x().func_175603_A();
         }
      } finally {
         this.field_195755_e.unlock();
      }
   }

   private boolean func_195745_a(String var1, File var2) {
      try {
         String ☃ = DigestUtils.sha1Hex(new FileInputStream(☃));
         if (☃.isEmpty()) {
            field_195751_a.info("Found file {} without verification hash", ☃);
            return true;
         }

         if (☃.toLowerCase(java.util.Locale.ROOT).equals(☃.toLowerCase(java.util.Locale.ROOT))) {
            field_195751_a.info("Found file {} matching requested hash {}", ☃, ☃);
            return true;
         }

         field_195751_a.warn("File {} had wrong hash (expected {}, found {}).", ☃, ☃, ☃);
      } catch (IOException var4) {
         field_195751_a.warn("File {} couldn't be hashed.", ☃, var4);
      }

      return false;
   }

   private void func_195747_e() {
      try {
         List<File> ☃ = Lists.newArrayList(FileUtils.listFiles(this.field_195754_d, TrueFileFilter.TRUE, null));
         ☃.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         int ☃x = 0;

         for(File ☃xx : ☃) {
            if (☃x++ >= 10) {
               field_195751_a.info("Deleting old server resource pack {}", ☃xx.getName());
               FileUtils.deleteQuietly(☃xx);
            }
         }
      } catch (IllegalArgumentException var5) {
         field_195751_a.error("Error while deleting old server resource pack : {}", var5.getMessage());
      }
   }

   public ListenableFuture<Object> func_195741_a(File var1) {
      PackMetadataSection ☃ = null;
      NativeImage ☃x = null;

      try {
         FilePack ☃xx = new FilePack(☃);
         Throwable var5 = null;

         try {
            InputStream ☃;
            try {
               ☃ = ☃xx.func_195760_a(PackMetadataSection.field_198964_a);

               try {
                  ☃ = ☃xx.func_195763_b("pack.png");
                  Throwable var7 = null;

                  try {
                     ☃x = NativeImage.func_195713_a(☃);
                  } catch (Throwable var34) {
                     var7 = var34;
                     throw var34;
                  } finally {
                     if (☃ != null) {
                        if (var7 != null) {
                           try {
                              ☃.close();
                           } catch (Throwable var33) {
                              var7.addSuppressed(var33);
                           }
                        } else {
                           ☃.close();
                        }
                     }
                  }
               } catch (IllegalArgumentException | IOException var36) {
                  ☃ = var36;
               }
            } catch (Throwable var37) {
               ☃ = var37;
               var5 = var37;
               throw var37;
            }
         } finally {
            if (☃xx != null) {
               if (var5 != null) {
                  try {
                     ☃xx.close();
                  } catch (Throwable var32) {
                     var5.addSuppressed(var32);
                  }
               } else {
                  ☃xx.close();
               }
            }
         }
      } catch (IOException var39) {
      }

      if (☃ == null) {
         return Futures.immediateFailedFuture(new RuntimeException("Invalid resourcepack"));
      } else {
         this.field_195757_g = new ResourcePackInfoClient(
            "server",
            true,
            () -> new FilePack(☃),
            new TextComponentTranslation("resourcePack.server.name"),
            ☃.func_198963_a(),
            PackCompatibility.func_198969_a(☃.func_198962_b()),
            ResourcePackInfo.Priority.TOP,
            true,
            ☃x
         );
         return Minecraft.func_71410_x().func_175603_A();
      }
   }
}
