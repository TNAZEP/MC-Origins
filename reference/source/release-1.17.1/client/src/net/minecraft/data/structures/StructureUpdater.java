package net.minecraft.data.structures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureUpdater implements SnbtToNbt.Filter {
   private static final Logger LOGGER = LogManager.getLogger();

   @Override
   public CompoundTag apply(String var1, CompoundTag var2) {
      return â˜ƒ.startsWith("data/minecraft/structures/") ? update(â˜ƒ, â˜ƒ) : â˜ƒ;
   }

   public static CompoundTag update(String var0, CompoundTag var1) {
      return updateStructure(â˜ƒ, patchVersion(â˜ƒ));
   }

   private static CompoundTag patchVersion(CompoundTag var0) {
      if (!â˜ƒ.contains("DataVersion", 99)) {
         â˜ƒ.putInt("DataVersion", 500);
      }

      return â˜ƒ;
   }

   private static CompoundTag updateStructure(String var0, CompoundTag var1) {
      StructureTemplate â˜ƒ = new StructureTemplate();
      int â˜ƒx = â˜ƒ.getInt("DataVersion");
      int â˜ƒxx = 2678;
      if (â˜ƒx < 2678) {
         LOGGER.warn("SNBT Too old, do not forget to update: {} < {}: {}", â˜ƒx, 2678, â˜ƒ);
      }

      CompoundTag â˜ƒ = NbtUtils.update(DataFixers.getDataFixer(), DataFixTypes.STRUCTURE, â˜ƒ, â˜ƒx);
      â˜ƒ.load(â˜ƒ);
      return â˜ƒ.save(new CompoundTag());
   }
}
