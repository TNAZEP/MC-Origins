package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Nullable;

public class RegionFileCache {
   private static final Map<File, RegionFile> field_76553_a = Maps.newHashMap();

   public static synchronized RegionFile func_76550_a(File var0, int var1, int var2) {
      File ☃ = new File(☃, "region");
      File ☃x = new File(☃, "r." + (☃ >> 5) + "." + (☃ >> 5) + ".mca");
      RegionFile ☃xx = (RegionFile)field_76553_a.get(☃x);
      if (☃xx != null) {
         return ☃xx;
      } else {
         if (!☃.exists()) {
            ☃.mkdirs();
         }

         if (field_76553_a.size() >= 256) {
            func_76551_a();
         }

         RegionFile ☃ = new RegionFile(☃x);
         field_76553_a.put(☃x, ☃);
         return ☃;
      }
   }

   public static synchronized void func_76551_a() {
      for(RegionFile ☃ : field_76553_a.values()) {
         try {
            if (☃ != null) {
               ☃.func_76708_c();
            }
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      field_76553_a.clear();
   }

   @Nullable
   public static DataInputStream func_76549_c(File var0, int var1, int var2) {
      RegionFile ☃ = func_76550_a(☃, ☃, ☃);
      return ☃.func_76704_a(☃ & 31, ☃ & 31);
   }

   @Nullable
   public static DataOutputStream func_76552_d(File var0, int var1, int var2) {
      RegionFile ☃ = func_76550_a(☃, ☃, ☃);
      return ☃.func_76710_b(☃ & 31, ☃ & 31);
   }
}
