package net.minecraft.client;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import net.minecraft.SharedConstants;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HotbarManager {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final int NUM_HOTBAR_GROUPS = 9;
   private final File optionsFile;
   private final DataFixer fixerUpper;
   private final Hotbar[] hotbars = new Hotbar[9];
   private boolean loaded;

   public HotbarManager(File var1, DataFixer var2) {
      this.optionsFile = new File(â˜ƒ, "hotbar.nbt");
      this.fixerUpper = â˜ƒ;

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.hotbars[â˜ƒ] = new Hotbar();
      }
   }

   private void load() {
      try {
         CompoundTag â˜ƒ = NbtIo.read(this.optionsFile);
         if (â˜ƒ == null) {
            return;
         }

         if (!â˜ƒ.contains("DataVersion", 99)) {
            â˜ƒ.putInt("DataVersion", 1343);
         }

         â˜ƒ = NbtUtils.update(this.fixerUpper, DataFixTypes.HOTBAR, â˜ƒ, â˜ƒ.getInt("DataVersion"));

         for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
            this.hotbars[â˜ƒ].fromTag(â˜ƒ.getList(String.valueOf(â˜ƒ), 10));
         }
      } catch (Exception var3) {
         LOGGER.error("Failed to load creative mode options", var3);
      }
   }

   public void save() {
      try {
         CompoundTag â˜ƒ = new CompoundTag();
         â˜ƒ.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());

         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            â˜ƒ.put(String.valueOf(â˜ƒx), this.get(â˜ƒx).createTag());
         }

         NbtIo.write(â˜ƒ, this.optionsFile);
      } catch (Exception var3) {
         LOGGER.error("Failed to save creative mode options", var3);
      }
   }

   public Hotbar get(int var1) {
      if (!this.loaded) {
         this.load();
         this.loaded = true;
      }

      return this.hotbars[â˜ƒ];
   }
}
