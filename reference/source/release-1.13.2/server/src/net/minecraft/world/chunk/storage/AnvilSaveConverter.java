package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter extends SaveFormatOld {
   private static final Logger field_151480_b = LogManager.getLogger();

   public AnvilSaveConverter(Path var1, Path var2, DataFixer var3) {
      super(☃, ☃, ☃);
   }

   protected int func_75812_c() {
      return 19133;
   }

   @Override
   public ISaveHandler func_197715_a(String var1, @Nullable MinecraftServer var2) {
      return new AnvilSaveHandler(this.field_75808_a.toFile(), ☃, ☃, this.field_186354_b);
   }

   @Override
   public boolean func_75801_b(String var1) {
      WorldInfo ☃ = this.func_75803_c(☃);
      return ☃ != null && ☃.func_76088_k() != this.func_75812_c();
   }

   @Override
   public boolean func_75805_a(String var1, IProgressUpdate var2) {
      ☃.func_73718_a(0);
      List<File> ☃ = Lists.newArrayList();
      List<File> ☃x = Lists.newArrayList();
      List<File> ☃xx = Lists.newArrayList();
      File ☃xxx = new File(this.field_75808_a.toFile(), ☃);
      File ☃xxxx = DimensionType.NETHER.func_212679_a(☃xxx);
      File ☃xxxxx = DimensionType.THE_END.func_212679_a(☃xxx);
      field_151480_b.info("Scanning folders...");
      this.func_75810_a(☃xxx, ☃);
      if (☃xxxx.exists()) {
         this.func_75810_a(☃xxxx, ☃x);
      }

      if (☃xxxxx.exists()) {
         this.func_75810_a(☃xxxxx, ☃xx);
      }

      int ☃x = ☃.size() + ☃x.size() + ☃xx.size();
      field_151480_b.info("Total conversion count is {}", ☃x);
      WorldInfo ☃xx = this.func_75803_c(☃);
      BiomeProviderType<SingleBiomeProviderSettings, SingleBiomeProvider> ☃xxx = BiomeProviderType.field_205461_c;
      BiomeProviderType<OverworldBiomeProviderSettings, OverworldBiomeProvider> ☃xxxx = BiomeProviderType.field_206859_d;
      BiomeProvider ☃;
      if (☃xx != null && ☃xx.func_76067_t() == WorldType.field_77138_c) {
         ☃ = ☃xxx.func_205457_a(☃xxx.func_205458_a().func_205436_a(Biomes.field_76772_c));
      } else {
         ☃ = ☃xxxx.func_205457_a(☃xxxx.func_205458_a().func_205439_a(☃xx).func_205441_a(ChunkGeneratorType.field_206911_b.func_205483_a()));
      }

      this.func_75813_a(new File(☃xxx, "region"), ☃, ☃, 0, ☃x, ☃);
      this.func_75813_a(new File(☃xxxx, "region"), ☃x, ☃xxx.func_205457_a(☃xxx.func_205458_a().func_205436_a(Biomes.field_76778_j)), ☃.size(), ☃x, ☃);
      this.func_75813_a(
         new File(☃xxxxx, "region"), ☃xx, ☃xxx.func_205457_a(☃xxx.func_205458_a().func_205436_a(Biomes.field_76779_k)), ☃.size() + ☃x.size(), ☃x, ☃
      );
      ☃xx.func_76078_e(19133);
      if (☃xx.func_76067_t() == WorldType.field_77136_e) {
         ☃xx.func_76085_a(WorldType.field_77137_b);
      }

      this.func_75809_f(☃);
      ISaveHandler ☃ = this.func_197715_a(☃, null);
      ☃.func_75761_a(☃xx);
      return true;
   }

   private void func_75809_f(String var1) {
      File ☃ = new File(this.field_75808_a.toFile(), ☃);
      if (!☃.exists()) {
         field_151480_b.warn("Unable to create level.dat_mcr backup");
      } else {
         File ☃ = new File(☃, "level.dat");
         if (!☃.exists()) {
            field_151480_b.warn("Unable to create level.dat_mcr backup");
         } else {
            File ☃ = new File(☃, "level.dat_mcr");
            if (!☃.renameTo(☃)) {
               field_151480_b.warn("Unable to create level.dat_mcr backup");
            }
         }
      }
   }

   private void func_75813_a(File var1, Iterable<File> var2, BiomeProvider var3, int var4, int var5, IProgressUpdate var6) {
      for(File ☃ : ☃) {
         this.func_75811_a(☃, ☃, ☃, ☃, ☃, ☃);
         ++☃;
         int ☃x = (int)Math.round(100.0 * (double)☃ / (double)☃);
         ☃.func_73718_a(☃x);
      }
   }

   private void func_75811_a(File var1, File var2, BiomeProvider var3, int var4, int var5, IProgressUpdate var6) {
      try {
         String ☃ = ☃.getName();
         RegionFile ☃x = new RegionFile(☃);
         RegionFile ☃xx = new RegionFile(new File(☃, ☃.substring(0, ☃.length() - ".mcr".length()) + ".mca"));

         for(int ☃xxx = 0; ☃xxx < 32; ++☃xxx) {
            for(int ☃xxxx = 0; ☃xxxx < 32; ++☃xxxx) {
               if (☃x.func_76709_c(☃xxx, ☃xxxx) && !☃xx.func_76709_c(☃xxx, ☃xxxx)) {
                  DataInputStream ☃xxxxx = ☃x.func_76704_a(☃xxx, ☃xxxx);
                  if (☃xxxxx == null) {
                     field_151480_b.warn("Failed to fetch input stream");
                  } else {
                     NBTTagCompound ☃xxxxx = CompressedStreamTools.func_74794_a(☃xxxxx);
                     ☃xxxxx.close();
                     NBTTagCompound ☃xxxxxx = ☃xxxxx.func_74775_l("Level");
                     ChunkLoader.AnvilConverterData ☃xxxxxxx = ChunkLoader.func_76691_a(☃xxxxxx);
                     NBTTagCompound ☃xxxxxxxx = new NBTTagCompound();
                     NBTTagCompound ☃xxxxxxxxx = new NBTTagCompound();
                     ☃xxxxxxxx.func_74782_a("Level", ☃xxxxxxxxx);
                     ChunkLoader.func_76690_a(☃xxxxxxx, ☃xxxxxxxxx, ☃);
                     DataOutputStream ☃xxxxxxxxxx = ☃xx.func_76710_b(☃xxx, ☃xxxx);
                     CompressedStreamTools.func_74800_a(☃xxxxxxxx, ☃xxxxxxxxxx);
                     ☃xxxxxxxxxx.close();
                  }
               }
            }

            int ☃xxxx = (int)Math.round(100.0 * (double)(☃ * 1024) / (double)(☃ * 1024));
            int ☃xxxxx = (int)Math.round(100.0 * (double)((☃xxx + 1) * 32 + ☃ * 1024) / (double)(☃ * 1024));
            if (☃xxxxx > ☃xxxx) {
               ☃.func_73718_a(☃xxxxx);
            }
         }

         ☃x.func_76708_c();
         ☃xx.func_76708_c();
      } catch (IOException var19) {
         var19.printStackTrace();
      }
   }

   private void func_75810_a(File var1, Collection<File> var2) {
      File ☃ = new File(☃, "region");
      File[] ☃x = ☃.listFiles((var0, var1x) -> var1x.endsWith(".mcr"));
      if (☃x != null) {
         Collections.addAll(☃, ☃x);
      }
   }
}
