package net.minecraft.world.gen.feature.template;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TemplateManager implements IResourceManagerReloadListener {
   private static final Logger field_195431_a = LogManager.getLogger();
   private final Map<ResourceLocation, Template> field_186240_a = Maps.<ResourceLocation, Template>newHashMap();
   private final DataFixer field_191154_c;
   private final MinecraftServer field_195432_d;
   private final Path field_195433_e;

   public TemplateManager(MinecraftServer var1, File var2, DataFixer var3) {
      this.field_195432_d = ☃;
      this.field_191154_c = ☃;
      this.field_195433_e = ☃.toPath().resolve("generated").normalize();
      ☃.func_195570_aG().func_199006_a(this);
   }

   public Template func_200220_a(ResourceLocation var1) {
      Template ☃ = this.func_200219_b(☃);
      if (☃ == null) {
         ☃ = new Template();
         this.field_186240_a.put(☃, ☃);
      }

      return ☃;
   }

   @Nullable
   public Template func_200219_b(ResourceLocation var1) {
      return (Template)this.field_186240_a.computeIfAbsent(☃, var1x -> {
         Template ☃ = this.func_195428_d(var1x);
         return ☃ != null ? ☃ : this.func_209201_e(var1x);
      });
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      this.field_186240_a.clear();
   }

   @Nullable
   private Template func_209201_e(ResourceLocation var1) {
      ResourceLocation ☃ = new ResourceLocation(☃.func_110624_b(), "structures/" + ☃.func_110623_a() + ".nbt");

      try {
         IResource ☃x = this.field_195432_d.func_195570_aG().func_199002_a(☃);
         Throwable var4 = null;

         Template var5;
         try {
            var5 = this.func_209205_a(☃x.func_199027_b());
         } catch (Throwable var16) {
            var4 = var16;
            throw var16;
         } finally {
            if (☃x != null) {
               if (var4 != null) {
                  try {
                     ☃x.close();
                  } catch (Throwable var15) {
                     var4.addSuppressed(var15);
                  }
               } else {
                  ☃x.close();
               }
            }
         }

         return var5;
      } catch (FileNotFoundException var18) {
         return null;
      } catch (Throwable var19) {
         field_195431_a.error("Couldn't load structure {}: {}", ☃, var19.toString());
         return null;
      }
   }

   @Nullable
   private Template func_195428_d(ResourceLocation var1) {
      if (!this.field_195433_e.toFile().isDirectory()) {
         return null;
      } else {
         Path ☃ = this.func_209510_b(☃, ".nbt");

         try {
            InputStream ☃x = new FileInputStream(☃.toFile());
            Throwable var4 = null;

            Template var5;
            try {
               var5 = this.func_209205_a(☃x);
            } catch (Throwable var16) {
               var4 = var16;
               throw var16;
            } finally {
               if (☃x != null) {
                  if (var4 != null) {
                     try {
                        ☃x.close();
                     } catch (Throwable var15) {
                        var4.addSuppressed(var15);
                     }
                  } else {
                     ☃x.close();
                  }
               }
            }

            return var5;
         } catch (FileNotFoundException var18) {
            return null;
         } catch (IOException var19) {
            field_195431_a.error("Couldn't load structure from {}", ☃, var19);
            return null;
         }
      }
   }

   private Template func_209205_a(InputStream var1) throws IOException {
      NBTTagCompound ☃ = CompressedStreamTools.func_74796_a(☃);
      if (!☃.func_150297_b("DataVersion", 99)) {
         ☃.func_74768_a("DataVersion", 500);
      }

      Template ☃ = new Template();
      ☃.func_186256_b(NBTUtil.func_210822_a(this.field_191154_c, DataFixTypes.STRUCTURE, ☃, ☃.func_74762_e("DataVersion")));
      return ☃;
   }

   public boolean func_195429_b(ResourceLocation var1) {
      Template ☃ = (Template)this.field_186240_a.get(☃);
      if (☃ == null) {
         return false;
      } else {
         Path ☃ = this.func_209510_b(☃, ".nbt");
         Path ☃x = ☃.getParent();
         if (☃x == null) {
            return false;
         } else {
            try {
               Files.createDirectories(Files.exists(☃x, new LinkOption[0]) ? ☃x.toRealPath() : ☃x);
            } catch (IOException var19) {
               field_195431_a.error("Failed to create parent directory: {}", ☃x);
               return false;
            }

            NBTTagCompound ☃ = ☃.func_189552_a(new NBTTagCompound());

            try {
               OutputStream ☃x = new FileOutputStream(☃.toFile());
               Throwable var7 = null;

               try {
                  CompressedStreamTools.func_74799_a(☃, ☃x);
               } catch (Throwable var18) {
                  var7 = var18;
                  throw var18;
               } finally {
                  if (☃x != null) {
                     if (var7 != null) {
                        try {
                           ☃x.close();
                        } catch (Throwable var17) {
                           var7.addSuppressed(var17);
                        }
                     } else {
                        ☃x.close();
                     }
                  }
               }

               return true;
            } catch (Throwable var21) {
               return false;
            }
         }
      }
   }

   private Path func_209509_a(ResourceLocation var1, String var2) {
      try {
         Path ☃ = this.field_195433_e.resolve(☃.func_110624_b());
         Path ☃x = ☃.resolve("structures");
         return Util.func_209535_a(☃x, ☃.func_110623_a(), ☃);
      } catch (InvalidPathException var5) {
         throw new ResourceLocationException("Invalid resource path: " + ☃, var5);
      }
   }

   private Path func_209510_b(ResourceLocation var1, String var2) {
      if (☃.func_110623_a().contains("//")) {
         throw new ResourceLocationException("Invalid resource path: " + ☃);
      } else {
         Path ☃ = this.func_209509_a(☃, ☃);
         if (☃.startsWith(this.field_195433_e) && Util.func_209537_a(☃) && Util.func_209536_b(☃)) {
            return ☃;
         } else {
            throw new ResourceLocationException("Invalid resource path: " + ☃);
         }
      }
   }

   public void func_189941_a(ResourceLocation var1) {
      this.field_186240_a.remove(☃);
   }
}
