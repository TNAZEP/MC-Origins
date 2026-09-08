package net.minecraft.realms;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;

public class RealmsAnvilLevelStorageSource {
   private final ISaveFormat levelStorageSource;

   public RealmsAnvilLevelStorageSource(ISaveFormat var1) {
      this.levelStorageSource = ☃;
   }

   public String getName() {
      return this.levelStorageSource.func_207741_a();
   }

   public boolean levelExists(String var1) {
      return this.levelStorageSource.func_90033_f(☃);
   }

   public boolean convertLevel(String var1, IProgressUpdate var2) {
      return this.levelStorageSource.func_75805_a(☃, ☃);
   }

   public boolean requiresConversion(String var1) {
      return this.levelStorageSource.func_75801_b(☃);
   }

   public boolean isNewLevelIdAcceptable(String var1) {
      return this.levelStorageSource.func_207742_d(☃);
   }

   public boolean deleteLevel(String var1) {
      return this.levelStorageSource.func_75802_e(☃);
   }

   public boolean isConvertible(String var1) {
      return this.levelStorageSource.func_207743_a(☃);
   }

   public void renameLevel(String var1, String var2) {
      this.levelStorageSource.func_75806_a(☃, ☃);
   }

   public void clearAll() {
      this.levelStorageSource.func_75800_d();
   }

   public List<RealmsLevelSummary> getLevelList() throws AnvilConverterException {
      List<RealmsLevelSummary> ☃ = Lists.<RealmsLevelSummary>newArrayList();

      for(WorldSummary ☃x : this.levelStorageSource.func_75799_b()) {
         ☃.add(new RealmsLevelSummary(☃x));
      }

      return ☃;
   }
}
