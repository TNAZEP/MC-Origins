package net.minecraft.server.packs.repository;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;

public class FolderRepositorySource implements RepositorySource {
   private static final FileFilter RESOURCEPACK_FILTER = var0 -> {
      boolean â˜ƒ = var0.isFile() && var0.getName().endsWith(".zip");
      boolean â˜ƒx = var0.isDirectory() && new File(var0, "pack.mcmeta").isFile();
      return â˜ƒ || â˜ƒx;
   };
   private final File folder;
   private final PackSource packSource;

   public FolderRepositorySource(File var1, PackSource var2) {
      this.folder = â˜ƒ;
      this.packSource = â˜ƒ;
   }

   @Override
   public void loadPacks(Consumer<Pack> var1, Pack.PackConstructor var2) {
      if (!this.folder.isDirectory()) {
         this.folder.mkdirs();
      }

      File[] â˜ƒ = this.folder.listFiles(RESOURCEPACK_FILTER);
      if (â˜ƒ != null) {
         for(File â˜ƒx : â˜ƒ) {
            String â˜ƒxx = "file/" + â˜ƒx.getName();
            Pack â˜ƒxxx = Pack.create(â˜ƒxx, false, this.createSupplier(â˜ƒx), â˜ƒ, Pack.Position.TOP, this.packSource);
            if (â˜ƒxxx != null) {
               â˜ƒ.accept(â˜ƒxxx);
            }
         }
      }
   }

   private Supplier<PackResources> createSupplier(File var1) {
      return â˜ƒ.isDirectory() ? () -> new FolderPackResources(â˜ƒ) : () -> new FilePackResources(â˜ƒ);
   }
}
