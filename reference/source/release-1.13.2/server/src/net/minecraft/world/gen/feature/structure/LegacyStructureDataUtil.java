package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.storage.WorldSavedDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LegacyStructureDataUtil {
   private static final Logger field_208219_a = LogManager.getLogger();
   private static final Map<String, String> field_208220_b = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      var0.put("Village", "Village");
      var0.put("Mineshaft", "Mineshaft");
      var0.put("Mansion", "Mansion");
      var0.put("Igloo", "Temple");
      var0.put("Desert_Pyramid", "Temple");
      var0.put("Jungle_Pyramid", "Temple");
      var0.put("Swamp_Hut", "Temple");
      var0.put("Stronghold", "Stronghold");
      var0.put("Monument", "Monument");
      var0.put("Fortress", "Fortress");
      var0.put("EndCity", "EndCity");
   });
   private static final Map<String, String> field_208221_c = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      var0.put("Iglu", "Igloo");
      var0.put("TeDP", "Desert_Pyramid");
      var0.put("TeJP", "Jungle_Pyramid");
      var0.put("TeSH", "Swamp_Hut");
   });
   private final boolean field_208222_d;
   private final Map<String, Long2ObjectMap<NBTTagCompound>> field_208223_e = Maps.newHashMap();
   private final Map<String, StructureIndexesSavedData> field_208224_f = Maps.newHashMap();

   public LegacyStructureDataUtil(@Nullable WorldSavedDataStorage var1) {
      this.func_212184_a(☃);
      boolean ☃ = false;

      for(String ☃x : this.func_208218_b()) {
         ☃ |= this.field_208223_e.get(☃x) != null;
      }

      this.field_208222_d = ☃;
   }

   public void func_208216_a(long var1) {
      for(String ☃ : this.func_208214_a()) {
         StructureIndexesSavedData ☃x = (StructureIndexesSavedData)this.field_208224_f.get(☃);
         if (☃x != null && ☃x.func_208023_c(☃)) {
            ☃x.func_201762_c(☃);
            ☃x.func_76185_a();
         }
      }
   }

   public NBTTagCompound func_212181_a(NBTTagCompound var1) {
      NBTTagCompound ☃ = ☃.func_74775_l("Level");
      ChunkPos ☃x = new ChunkPos(☃.func_74762_e("xPos"), ☃.func_74762_e("zPos"));
      if (this.func_208209_a(☃x.field_77276_a, ☃x.field_77275_b)) {
         ☃ = this.func_212182_a(☃, ☃x);
      }

      NBTTagCompound ☃ = ☃.func_74775_l("Structures");
      NBTTagCompound ☃x = ☃.func_74775_l("References");

      for(String ☃xx : this.func_208218_b()) {
         Structure<?> ☃xxx = (Structure)Feature.field_202300_at.get(☃xx.toLowerCase(Locale.ROOT));
         if (!☃x.func_150297_b(☃xx, 12) && ☃xxx != null) {
            int ☃xxxx = ☃xxx.func_202367_b();
            LongList ☃xxxxx = new LongArrayList();

            for(int ☃xxxxxx = ☃x.field_77276_a - ☃xxxx; ☃xxxxxx <= ☃x.field_77276_a + ☃xxxx; ++☃xxxxxx) {
               for(int ☃xxxxxxx = ☃x.field_77275_b - ☃xxxx; ☃xxxxxxx <= ☃x.field_77275_b + ☃xxxx; ++☃xxxxxxx) {
                  if (this.func_208211_a(☃xxxxxx, ☃xxxxxxx, ☃xx)) {
                     ☃xxxxx.add(ChunkPos.func_77272_a(☃xxxxxx, ☃xxxxxxx));
                  }
               }
            }

            ☃x.func_202168_c(☃xx, ☃xxxxx);
         }
      }

      ☃.func_74782_a("References", ☃x);
      ☃.func_74782_a("Structures", ☃);
      ☃.func_74782_a("Level", ☃);
      return ☃;
   }

   protected abstract String[] func_208214_a();

   protected abstract String[] func_208218_b();

   private boolean func_208211_a(int var1, int var2, String var3) {
      if (!this.field_208222_d) {
         return false;
      } else {
         return this.field_208223_e.get(☃) != null
            && ((StructureIndexesSavedData)this.field_208224_f.get(field_208220_b.get(☃))).func_208024_b(ChunkPos.func_77272_a(☃, ☃));
      }
   }

   private boolean func_208209_a(int var1, int var2) {
      if (!this.field_208222_d) {
         return false;
      } else {
         for(String ☃ : this.func_208218_b()) {
            if (this.field_208223_e.get(☃) != null
               && ((StructureIndexesSavedData)this.field_208224_f.get(field_208220_b.get(☃))).func_208023_c(ChunkPos.func_77272_a(☃, ☃))) {
               return true;
            }
         }

         return false;
      }
   }

   private NBTTagCompound func_212182_a(NBTTagCompound var1, ChunkPos var2) {
      NBTTagCompound ☃ = ☃.func_74775_l("Level");
      NBTTagCompound ☃x = ☃.func_74775_l("Structures");
      NBTTagCompound ☃xx = ☃x.func_74775_l("Starts");

      for(String ☃xxx : this.func_208218_b()) {
         Long2ObjectMap<NBTTagCompound> ☃xxxx = (Long2ObjectMap)this.field_208223_e.get(☃xxx);
         if (☃xxxx != null) {
            long ☃xxxxx = ☃.func_201841_a();
            if (((StructureIndexesSavedData)this.field_208224_f.get(field_208220_b.get(☃xxx))).func_208023_c(☃xxxxx)) {
               NBTTagCompound ☃xxxxxx = ☃xxxx.get(☃xxxxx);
               if (☃xxxxxx != null) {
                  ☃xx.func_74782_a(☃xxx, ☃xxxxxx);
               }
            }
         }
      }

      ☃x.func_74782_a("Starts", ☃xx);
      ☃.func_74782_a("Structures", ☃x);
      ☃.func_74782_a("Level", ☃);
      return ☃;
   }

   private void func_212184_a(@Nullable WorldSavedDataStorage var1) {
      if (☃ != null) {
         for(String ☃ : this.func_208214_a()) {
            NBTTagCompound ☃x = new NBTTagCompound();

            try {
               ☃x = ☃.func_208028_a(☃, 1493).func_74775_l("data").func_74775_l("Features");
               if (☃x.isEmpty()) {
                  continue;
               }
            } catch (IOException var15) {
            }

            for(String ☃xx : ☃x.func_150296_c()) {
               NBTTagCompound ☃xxx = ☃x.func_74775_l(☃xx);
               long ☃xxxx = ChunkPos.func_77272_a(☃xxx.func_74762_e("ChunkX"), ☃xxx.func_74762_e("ChunkZ"));
               NBTTagList ☃xxxxx = ☃xxx.func_150295_c("Children", 10);
               if (!☃xxxxx.isEmpty()) {
                  String ☃xxxxxx = ☃xxxxx.func_150305_b(0).func_74779_i("id");
                  String ☃xxxxxxx = (String)field_208221_c.get(☃xxxxxx);
                  if (☃xxxxxxx != null) {
                     ☃xxx.func_74778_a("id", ☃xxxxxxx);
                  }
               }

               String ☃xxx = ☃xxx.func_74779_i("id");
               ((Long2ObjectMap)this.field_208223_e.computeIfAbsent(☃xxx, var0 -> new Long2ObjectOpenHashMap())).put(☃xxxx, ☃xxx);
            }

            String ☃xx = ☃ + "_index";
            StructureIndexesSavedData ☃xxx = ☃.func_212426_a(DimensionType.OVERWORLD, StructureIndexesSavedData::new, ☃xx);
            if (☃xxx != null && !☃xxx.func_208025_a().isEmpty()) {
               this.field_208224_f.put(☃, ☃xxx);
            } else {
               StructureIndexesSavedData ☃xx = new StructureIndexesSavedData(☃xx);
               this.field_208224_f.put(☃, ☃xx);

               for(String ☃xxx : ☃x.func_150296_c()) {
                  NBTTagCompound ☃xxxx = ☃x.func_74775_l(☃xxx);
                  ☃xx.func_201763_a(ChunkPos.func_77272_a(☃xxxx.func_74762_e("ChunkX"), ☃xxxx.func_74762_e("ChunkZ")));
               }

               ☃.func_212424_a(DimensionType.OVERWORLD, ☃xx, ☃xx);
               ☃xx.func_76185_a();
            }
         }
      }
   }

   public static LegacyStructureDataUtil func_212183_a(DimensionType var0, @Nullable WorldSavedDataStorage var1) {
      if (☃ == DimensionType.OVERWORLD) {
         return new LegacyStructureDataUtil.Overworld(☃);
      } else if (☃ == DimensionType.NETHER) {
         return new LegacyStructureDataUtil.Nether(☃);
      } else if (☃ == DimensionType.THE_END) {
         return new LegacyStructureDataUtil.End(☃);
      } else {
         throw new RuntimeException(String.format("Unknown dimension type : %s", ☃));
      }
   }

   public static class End extends LegacyStructureDataUtil {
      private static final String[] field_208227_a = new String[]{"EndCity"};

      public End(@Nullable WorldSavedDataStorage var1) {
         super(☃);
      }

      @Override
      protected String[] func_208214_a() {
         return field_208227_a;
      }

      @Override
      protected String[] func_208218_b() {
         return field_208227_a;
      }
   }

   public static class Nether extends LegacyStructureDataUtil {
      private static final String[] field_208228_a = new String[]{"Fortress"};

      public Nether(@Nullable WorldSavedDataStorage var1) {
         super(☃);
      }

      @Override
      protected String[] func_208214_a() {
         return field_208228_a;
      }

      @Override
      protected String[] func_208218_b() {
         return field_208228_a;
      }
   }

   public static class Overworld extends LegacyStructureDataUtil {
      private static final String[] field_208225_a = new String[]{"Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"};
      private static final String[] field_208226_b = new String[]{
         "Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument"
      };

      public Overworld(@Nullable WorldSavedDataStorage var1) {
         super(☃);
      }

      @Override
      protected String[] func_208214_a() {
         return field_208225_a;
      }

      @Override
      protected String[] func_208218_b() {
         return field_208226_b;
      }
   }
}
