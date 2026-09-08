package net.minecraft.resources;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.function.Supplier;

public class FolderPackFinder implements IPackFinder {
   private static final FileFilter field_195735_a = var0 -> {
      boolean ☃ = var0.isFile() && var0.getName().endsWith(".zip");
      boolean ☃x = var0.isDirectory() && new File(var0, "pack.mcmeta").isFile();
      return ☃ || ☃x;
   };
   private final File field_195736_b;

   public FolderPackFinder(File var1) {
      this.field_195736_b = ☃;
   }

   @Override
   public <T extends ResourcePackInfo> void func_195730_a(Map<String, T> var1, ResourcePackInfo.IFactory<T> var2) {
      if (!this.field_195736_b.isDirectory()) {
         this.field_195736_b.mkdirs();
      }

      File[] ☃ = this.field_195736_b.listFiles(field_195735_a);
      if (☃ != null) {
         for(File ☃x : ☃) {
            String ☃xx = "file/" + ☃x.getName();
            T ☃xxx = ResourcePackInfo.func_195793_a(☃xx, false, this.func_195733_a(☃x), ☃, ResourcePackInfo.Priority.TOP);
            if (☃xxx != null) {
               ☃.put(☃xx, ☃xxx);
            }
         }
      }
   }

   private Supplier<IResourcePack> func_195733_a(File var1) {
      return ☃.isDirectory() ? () -> new FolderPack(☃) : () -> new FilePack(☃);
   }
}
