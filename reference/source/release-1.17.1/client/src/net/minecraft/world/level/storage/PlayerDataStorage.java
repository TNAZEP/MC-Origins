package net.minecraft.world.level.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerDataStorage {
   private static final Logger LOGGER = LogManager.getLogger();
   private final File playerDir;
   protected final DataFixer fixerUpper;

   public PlayerDataStorage(LevelStorageSource.LevelStorageAccess var1, DataFixer var2) {
      this.fixerUpper = â˜ƒ;
      this.playerDir = â˜ƒ.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
      this.playerDir.mkdirs();
   }

   public void save(Player var1) {
      try {
         CompoundTag â˜ƒ = â˜ƒ.saveWithoutId(new CompoundTag());
         File â˜ƒx = File.createTempFile(â˜ƒ.getStringUUID() + "-", ".dat", this.playerDir);
         NbtIo.writeCompressed(â˜ƒ, â˜ƒx);
         File â˜ƒxx = new File(this.playerDir, â˜ƒ.getStringUUID() + ".dat");
         File â˜ƒxxx = new File(this.playerDir, â˜ƒ.getStringUUID() + ".dat_old");
         Util.safeReplaceFile(â˜ƒxx, â˜ƒx, â˜ƒxxx);
      } catch (Exception var6) {
         LOGGER.warn("Failed to save player data for {}", â˜ƒ.getName().getString());
      }
   }

   @Nullable
   public CompoundTag load(Player var1) {
      CompoundTag â˜ƒ = null;

      try {
         File â˜ƒx = new File(this.playerDir, â˜ƒ.getStringUUID() + ".dat");
         if (â˜ƒx.exists() && â˜ƒx.isFile()) {
            â˜ƒ = NbtIo.readCompressed(â˜ƒx);
         }
      } catch (Exception var4) {
         LOGGER.warn("Failed to load player data for {}", â˜ƒ.getName().getString());
      }

      if (â˜ƒ != null) {
         int â˜ƒx = â˜ƒ.contains("DataVersion", 3) ? â˜ƒ.getInt("DataVersion") : -1;
         â˜ƒ.load(NbtUtils.update(this.fixerUpper, DataFixTypes.PLAYER, â˜ƒ, â˜ƒx));
      }

      return â˜ƒ;
   }

   public String[] getSeenPlayers() {
      String[] â˜ƒ = this.playerDir.list();
      if (â˜ƒ == null) {
         â˜ƒ = new String[0];
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; ++â˜ƒ) {
         if (â˜ƒ[â˜ƒ].endsWith(".dat")) {
            â˜ƒ[â˜ƒ] = â˜ƒ[â˜ƒ].substring(0, â˜ƒ[â˜ƒ].length() - 4);
         }
      }

      return â˜ƒ;
   }
}
