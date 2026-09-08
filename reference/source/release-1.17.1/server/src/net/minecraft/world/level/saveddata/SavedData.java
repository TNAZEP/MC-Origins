package net.minecraft.world.level.saveddata;

import java.io.File;
import java.io.IOException;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SavedData {
   private static final Logger LOGGER = LogManager.getLogger();
   private boolean dirty;

   public abstract CompoundTag save(CompoundTag var1);

   public void setDirty() {
      this.setDirty(true);
   }

   public void setDirty(boolean var1) {
      this.dirty = â˜ƒ;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public void save(File var1) {
      if (this.isDirty()) {
         CompoundTag â˜ƒ = new CompoundTag();
         â˜ƒ.put("data", this.save(new CompoundTag()));
         â˜ƒ.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());

         try {
            NbtIo.writeCompressed(â˜ƒ, â˜ƒ);
         } catch (IOException var4) {
            LOGGER.error("Could not save data {}", this, var4);
         }

         this.setDirty(false);
      }
   }
}
