package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
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
import java.util.Optional;
import net.minecraft.FileUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String STRUCTURE_DIRECTORY_NAME = "structures";
   private static final String STRUCTURE_FILE_EXTENSION = ".nbt";
   private static final String STRUCTURE_TEXT_FILE_EXTENSION = ".snbt";
   private final Map<ResourceLocation, Optional<StructureTemplate>> structureRepository = Maps.newConcurrentMap();
   private final DataFixer fixerUpper;
   private ResourceManager resourceManager;
   private final Path generatedDir;

   public StructureManager(ResourceManager var1, LevelStorageSource.LevelStorageAccess var2, DataFixer var3) {
      this.resourceManager = â˜ƒ;
      this.fixerUpper = â˜ƒ;
      this.generatedDir = â˜ƒ.getLevelPath(LevelResource.GENERATED_DIR).normalize();
   }

   public StructureTemplate getOrCreate(ResourceLocation var1) {
      Optional<StructureTemplate> â˜ƒ = this.get(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         return (StructureTemplate)â˜ƒ.get();
      } else {
         StructureTemplate â˜ƒ = new StructureTemplate();
         this.structureRepository.put(â˜ƒ, Optional.of(â˜ƒ));
         return â˜ƒ;
      }
   }

   public Optional<StructureTemplate> get(ResourceLocation var1) {
      return (Optional<StructureTemplate>)this.structureRepository.computeIfAbsent(â˜ƒ, var1x -> {
         Optional<StructureTemplate> â˜ƒ = this.loadFromGenerated(var1x);
         return â˜ƒ.isPresent() ? â˜ƒ : this.loadFromResource(var1x);
      });
   }

   public void onResourceManagerReload(ResourceManager var1) {
      this.resourceManager = â˜ƒ;
      this.structureRepository.clear();
   }

   private Optional<StructureTemplate> loadFromResource(ResourceLocation var1) {
      ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ.getNamespace(), "structures/" + â˜ƒ.getPath() + ".nbt");

      try {
         Resource â˜ƒx = this.resourceManager.getResource(â˜ƒ);

         Optional var4;
         try {
            var4 = Optional.of(this.readStructure(â˜ƒx.getInputStream()));
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

         return var4;
      } catch (FileNotFoundException var8) {
         return Optional.empty();
      } catch (Throwable var9) {
         LOGGER.error("Couldn't load structure {}: {}", â˜ƒ, var9.toString());
         return Optional.empty();
      }
   }

   private Optional<StructureTemplate> loadFromGenerated(ResourceLocation var1) {
      if (!this.generatedDir.toFile().isDirectory()) {
         return Optional.empty();
      } else {
         Path â˜ƒ = this.createAndValidatePathToStructure(â˜ƒ, ".nbt");

         try {
            InputStream â˜ƒx = new FileInputStream(â˜ƒ.toFile());

            Optional var4;
            try {
               var4 = Optional.of(this.readStructure(â˜ƒx));
            } catch (Throwable var7) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }

               throw var7;
            }

            â˜ƒx.close();
            return var4;
         } catch (FileNotFoundException var8) {
            return Optional.empty();
         } catch (IOException var9) {
            LOGGER.error("Couldn't load structure from {}", â˜ƒ, var9);
            return Optional.empty();
         }
      }
   }

   private StructureTemplate readStructure(InputStream var1) throws IOException {
      CompoundTag â˜ƒ = NbtIo.readCompressed(â˜ƒ);
      return this.readStructure(â˜ƒ);
   }

   public StructureTemplate readStructure(CompoundTag var1) {
      if (!â˜ƒ.contains("DataVersion", 99)) {
         â˜ƒ.putInt("DataVersion", 500);
      }

      StructureTemplate â˜ƒ = new StructureTemplate();
      â˜ƒ.load(NbtUtils.update(this.fixerUpper, DataFixTypes.STRUCTURE, â˜ƒ, â˜ƒ.getInt("DataVersion")));
      return â˜ƒ;
   }

   public boolean save(ResourceLocation var1) {
      Optional<StructureTemplate> â˜ƒ = (Optional)this.structureRepository.get(â˜ƒ);
      if (!â˜ƒ.isPresent()) {
         return false;
      } else {
         StructureTemplate â˜ƒ = (StructureTemplate)â˜ƒ.get();
         Path â˜ƒx = this.createAndValidatePathToStructure(â˜ƒ, ".nbt");
         Path â˜ƒxx = â˜ƒx.getParent();
         if (â˜ƒxx == null) {
            return false;
         } else {
            try {
               Files.createDirectories(Files.exists(â˜ƒxx, new LinkOption[0]) ? â˜ƒxx.toRealPath() : â˜ƒxx);
            } catch (IOException var13) {
               LOGGER.error("Failed to create parent directory: {}", â˜ƒxx);
               return false;
            }

            CompoundTag â˜ƒ = â˜ƒ.save(new CompoundTag());

            try {
               OutputStream â˜ƒx = new FileOutputStream(â˜ƒx.toFile());

               try {
                  NbtIo.writeCompressed(â˜ƒ, â˜ƒx);
               } catch (Throwable var11) {
                  try {
                     â˜ƒx.close();
                  } catch (Throwable var10) {
                     var11.addSuppressed(var10);
                  }

                  throw var11;
               }

               â˜ƒx.close();
               return true;
            } catch (Throwable var12) {
               return false;
            }
         }
      }
   }

   public Path createPathToStructure(ResourceLocation var1, String var2) {
      try {
         Path â˜ƒ = this.generatedDir.resolve(â˜ƒ.getNamespace());
         Path â˜ƒx = â˜ƒ.resolve("structures");
         return FileUtil.createPathToResource(â˜ƒx, â˜ƒ.getPath(), â˜ƒ);
      } catch (InvalidPathException var5) {
         throw new ResourceLocationException("Invalid resource path: " + â˜ƒ, var5);
      }
   }

   private Path createAndValidatePathToStructure(ResourceLocation var1, String var2) {
      if (â˜ƒ.getPath().contains("//")) {
         throw new ResourceLocationException("Invalid resource path: " + â˜ƒ);
      } else {
         Path â˜ƒ = this.createPathToStructure(â˜ƒ, â˜ƒ);
         if (â˜ƒ.startsWith(this.generatedDir) && FileUtil.isPathNormalized(â˜ƒ) && FileUtil.isPathPortable(â˜ƒ)) {
            return â˜ƒ;
         } else {
            throw new ResourceLocationException("Invalid resource path: " + â˜ƒ);
         }
      }
   }

   public void remove(ResourceLocation var1) {
      this.structureRepository.remove(â˜ƒ);
   }
}
