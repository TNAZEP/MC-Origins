package net.minecraft.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.RegionFile;
import net.minecraft.world.dimension.DimensionType;

public class WorldChunkEnumerator {
   private static final Pattern field_212158_a = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
   private final File field_212159_b;
   private final Map<DimensionType, List<ChunkPos>> field_212162_e;

   public WorldChunkEnumerator(File var1) {
      this.field_212159_b = ☃;
      Builder<DimensionType, List<ChunkPos>> ☃ = ImmutableMap.builder();

      for(DimensionType ☃x : DimensionType.func_212681_b()) {
         ☃.put(☃x, this.func_212153_a(☃x));
      }

      this.field_212162_e = ☃.build();
   }

   private List<ChunkPos> func_212153_a(DimensionType var1) {
      ArrayList<ChunkPos> ☃ = Lists.newArrayList();
      File ☃x = ☃.func_212679_a(this.field_212159_b);
      List<File> ☃xx = this.func_212155_b(☃x);

      for(File ☃xxx : ☃xx) {
         ☃.addAll(this.func_212150_a(☃xxx));
      }

      ☃xx.sort(File::compareTo);
      return ☃;
   }

   private List<ChunkPos> func_212150_a(File var1) {
      List<ChunkPos> ☃ = Lists.<ChunkPos>newArrayList();
      RegionFile ☃x = null;

      try {
         Matcher ☃xx = field_212158_a.matcher(☃.getName());
         if (!☃xx.matches()) {
            return ☃;
         }

         int ☃xx = Integer.parseInt(☃xx.group(1)) << 5;
         int ☃xxx = Integer.parseInt(☃xx.group(2)) << 5;
         ☃x = new RegionFile(☃);

         for(int ☃xxxx = 0; ☃xxxx < 32; ++☃xxxx) {
            for(int ☃xxxxx = 0; ☃xxxxx < 32; ++☃xxxxx) {
               if (☃x.func_212167_b(☃xxxx, ☃xxxxx)) {
                  ☃.add(new ChunkPos(☃xxxx + ☃xx, ☃xxxxx + ☃xxx));
               }
            }
         }
      } catch (Throwable var18) {
         return Lists.<ChunkPos>newArrayList();
      } finally {
         if (☃x != null) {
            try {
               ☃x.func_76708_c();
            } catch (IOException var17) {
            }
         }
      }

      return ☃;
   }

   private List<File> func_212155_b(File var1) {
      File ☃ = new File(☃, "region");
      File[] ☃x = ☃.listFiles((var0, var1x) -> var1x.endsWith(".mca"));
      return ☃x != null ? Lists.newArrayList(☃x) : Lists.newArrayList();
   }

   public List<ChunkPos> func_212541_a(DimensionType var1) {
      return (List<ChunkPos>)this.field_212162_e.get(☃);
   }
}
