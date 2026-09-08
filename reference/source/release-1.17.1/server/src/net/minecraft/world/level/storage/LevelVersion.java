package net.minecraft.world.level.storage;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.SharedConstants;

public class LevelVersion {
   private final int levelDataVersion;
   private final long lastPlayed;
   private final String minecraftVersionName;
   private final int minecraftVersion;
   private final boolean snapshot;

   public LevelVersion(int var1, long var2, String var4, int var5, boolean var6) {
      this.levelDataVersion = â˜ƒ;
      this.lastPlayed = â˜ƒ;
      this.minecraftVersionName = â˜ƒ;
      this.minecraftVersion = â˜ƒ;
      this.snapshot = â˜ƒ;
   }

   public static LevelVersion parse(Dynamic<?> var0) {
      int â˜ƒ = â˜ƒ.get("version").asInt(0);
      long â˜ƒx = â˜ƒ.get("LastPlayed").asLong(0L);
      OptionalDynamic<?> â˜ƒxx = â˜ƒ.get("Version");
      return â˜ƒxx.result().isPresent()
         ? new LevelVersion(
            â˜ƒ,
            â˜ƒx,
            â˜ƒxx.get("Name").asString(SharedConstants.getCurrentVersion().getName()),
            â˜ƒxx.get("Id").asInt(SharedConstants.getCurrentVersion().getWorldVersion()),
            â˜ƒxx.get("Snapshot").asBoolean(!SharedConstants.getCurrentVersion().isStable())
         )
         : new LevelVersion(â˜ƒ, â˜ƒx, "", 0, false);
   }

   public int levelDataVersion() {
      return this.levelDataVersion;
   }

   public long lastPlayed() {
      return this.lastPlayed;
   }

   public String minecraftVersionName() {
      return this.minecraftVersionName;
   }

   public int minecraftVersion() {
      return this.minecraftVersion;
   }

   public boolean snapshot() {
      return this.snapshot;
   }
}
