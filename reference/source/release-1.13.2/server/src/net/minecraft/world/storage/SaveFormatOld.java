package net.minecraft.world.storage;

import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld implements ISaveFormat {
   private static final Logger field_151479_b = LogManager.getLogger();
   protected final Path field_75808_a;
   protected final Path field_197717_b;
   protected final DataFixer field_186354_b;

   public SaveFormatOld(Path var1, Path var2, DataFixer var3) {
      this.field_186354_b = ☃;

      try {
         Files.createDirectories(Files.exists(☃, new LinkOption[0]) ? ☃.toRealPath() : ☃);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      this.field_75808_a = ☃;
      this.field_197717_b = ☃;
   }

   @Nullable
   @Override
   public WorldInfo func_75803_c(String var1) {
      File ☃ = new File(this.field_75808_a.toFile(), ☃);
      if (!☃.exists()) {
         return null;
      } else {
         File ☃ = new File(☃, "level.dat");
         if (☃.exists()) {
            WorldInfo ☃x = func_186353_a(☃, this.field_186354_b);
            if (☃x != null) {
               return ☃x;
            }
         }

         ☃ = new File(☃, "level.dat_old");
         return ☃.exists() ? func_186353_a(☃, this.field_186354_b) : null;
      }
   }

   @Nullable
   public static WorldInfo func_186353_a(File var0, DataFixer var1) {
      try {
         NBTTagCompound ☃ = CompressedStreamTools.func_74796_a(new FileInputStream(☃));
         NBTTagCompound ☃x = ☃.func_74775_l("Data");
         NBTTagCompound ☃xx = ☃x.func_150297_b("Player", 10) ? ☃x.func_74775_l("Player") : null;
         ☃x.func_82580_o("Player");
         int ☃xxx = ☃x.func_150297_b("DataVersion", 99) ? ☃x.func_74762_e("DataVersion") : -1;
         return new WorldInfo(NBTUtil.func_210822_a(☃, DataFixTypes.LEVEL, ☃x, ☃xxx), ☃, ☃xxx, ☃xx);
      } catch (Exception var6) {
         field_151479_b.error("Exception reading {}", ☃, var6);
         return null;
      }
   }

   @Override
   public ISaveHandler func_197715_a(String var1, @Nullable MinecraftServer var2) {
      return new SaveHandler(this.field_75808_a.toFile(), ☃, ☃, this.field_186354_b);
   }

   @Override
   public boolean func_75801_b(String var1) {
      return false;
   }

   @Override
   public boolean func_75805_a(String var1, IProgressUpdate var2) {
      return false;
   }

   @Override
   public File func_186352_b(String var1, String var2) {
      return this.field_75808_a.resolve(☃).resolve(☃).toFile();
   }
}
