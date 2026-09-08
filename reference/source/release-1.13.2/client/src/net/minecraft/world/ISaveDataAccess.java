package net.minecraft.world;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.storage.WorldSavedDataStorage;

public interface ISaveDataAccess {
   @Nullable
   WorldSavedDataStorage func_175693_T();

   @Nullable
   default <T extends WorldSavedData> T func_212411_a(DimensionType var1, Function<String, T> var2, String var3) {
      WorldSavedDataStorage ☃ = this.func_175693_T();
      return ☃ == null ? null : ☃.func_212426_a(☃, ☃, ☃);
   }

   default void func_212409_a(DimensionType var1, String var2, WorldSavedData var3) {
      WorldSavedDataStorage ☃ = this.func_175693_T();
      if (☃ != null) {
         ☃.func_212424_a(☃, ☃, ☃);
      }
   }

   default int func_212410_a(DimensionType var1, String var2) {
      return this.func_175693_T().func_212425_a(☃, ☃);
   }
}
