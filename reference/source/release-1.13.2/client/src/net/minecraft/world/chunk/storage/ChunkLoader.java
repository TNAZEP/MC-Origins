package net.minecraft.world.chunk.storage;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader {
   public static ChunkLoader.AnvilConverterData func_76691_a(NBTTagCompound var0) {
      int ☃ = ☃.func_74762_e("xPos");
      int ☃x = ☃.func_74762_e("zPos");
      ChunkLoader.AnvilConverterData ☃xx = new ChunkLoader.AnvilConverterData(☃, ☃x);
      ☃xx.field_76693_g = ☃.func_74770_j("Blocks");
      ☃xx.field_76692_f = new NibbleArrayReader(☃.func_74770_j("Data"), 7);
      ☃xx.field_76695_e = new NibbleArrayReader(☃.func_74770_j("SkyLight"), 7);
      ☃xx.field_76694_d = new NibbleArrayReader(☃.func_74770_j("BlockLight"), 7);
      ☃xx.field_76697_c = ☃.func_74770_j("HeightMap");
      ☃xx.field_76696_b = ☃.func_74767_n("TerrainPopulated");
      ☃xx.field_76702_h = ☃.func_150295_c("Entities", 10);
      ☃xx.field_151564_i = ☃.func_150295_c("TileEntities", 10);
      ☃xx.field_151563_j = ☃.func_150295_c("TileTicks", 10);

      try {
         ☃xx.field_76698_a = ☃.func_74763_f("LastUpdate");
      } catch (ClassCastException var5) {
         ☃xx.field_76698_a = (long)☃.func_74762_e("LastUpdate");
      }

      return ☃xx;
   }

   public static void func_76690_a(ChunkLoader.AnvilConverterData var0, NBTTagCompound var1, BiomeProvider var2) {
      ☃.func_74768_a("xPos", ☃.field_76701_k);
      ☃.func_74768_a("zPos", ☃.field_76699_l);
      ☃.func_74772_a("LastUpdate", ☃.field_76698_a);
      int[] ☃ = new int[☃.field_76697_c.length];

      for(int ☃x = 0; ☃x < ☃.field_76697_c.length; ++☃x) {
         ☃[☃x] = ☃.field_76697_c[☃x];
      }

      ☃.func_74783_a("HeightMap", ☃);
      ☃.func_74757_a("TerrainPopulated", ☃.field_76696_b);
      NBTTagList ☃x = new NBTTagList();

      for(int ☃xx = 0; ☃xx < 8; ++☃xx) {
         boolean ☃xxx = true;

         for(int ☃xxxx = 0; ☃xxxx < 16 && ☃xxx; ++☃xxxx) {
            for(int ☃xxxxx = 0; ☃xxxxx < 16 && ☃xxx; ++☃xxxxx) {
               for(int ☃xxxxxx = 0; ☃xxxxxx < 16; ++☃xxxxxx) {
                  int ☃xxxxxxx = ☃xxxx << 11 | ☃xxxxxx << 7 | ☃xxxxx + (☃xx << 4);
                  int ☃xxxxxxxx = ☃.field_76693_g[☃xxxxxxx];
                  if (☃xxxxxxxx != 0) {
                     ☃xxx = false;
                     break;
                  }
               }
            }
         }

         if (!☃xxx) {
            byte[] ☃xxxx = new byte[4096];
            NibbleArray ☃xxxxx = new NibbleArray();
            NibbleArray ☃xxxxxx = new NibbleArray();
            NibbleArray ☃xxxxxxx = new NibbleArray();

            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 16; ++☃xxxxxxxx) {
               for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < 16; ++☃xxxxxxxxx) {
                  for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < 16; ++☃xxxxxxxxxx) {
                     int ☃xxxxxxxxxxx = ☃xxxxxxxx << 11 | ☃xxxxxxxxxx << 7 | ☃xxxxxxxxx + (☃xx << 4);
                     int ☃xxxxxxxxxxxx = ☃.field_76693_g[☃xxxxxxxxxxx];
                     ☃xxxx[☃xxxxxxxxx << 8 | ☃xxxxxxxxxx << 4 | ☃xxxxxxxx] = (byte)(☃xxxxxxxxxxxx & 0xFF);
                     ☃xxxxx.func_76581_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃.field_76692_f.func_76686_a(☃xxxxxxxx, ☃xxxxxxxxx + (☃xx << 4), ☃xxxxxxxxxx));
                     ☃xxxxxx.func_76581_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃.field_76695_e.func_76686_a(☃xxxxxxxx, ☃xxxxxxxxx + (☃xx << 4), ☃xxxxxxxxxx));
                     ☃xxxxxxx.func_76581_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃.field_76694_d.func_76686_a(☃xxxxxxxx, ☃xxxxxxxxx + (☃xx << 4), ☃xxxxxxxxxx));
                  }
               }
            }

            NBTTagCompound ☃xxxxxxxx = new NBTTagCompound();
            ☃xxxxxxxx.func_74774_a("Y", (byte)(☃xx & 0xFF));
            ☃xxxxxxxx.func_74773_a("Blocks", ☃xxxx);
            ☃xxxxxxxx.func_74773_a("Data", ☃xxxxx.func_177481_a());
            ☃xxxxxxxx.func_74773_a("SkyLight", ☃xxxxxx.func_177481_a());
            ☃xxxxxxxx.func_74773_a("BlockLight", ☃xxxxxxx.func_177481_a());
            ☃x.add((INBTBase)☃xxxxxxxx);
         }
      }

      ☃.func_74782_a("Sections", ☃x);
      byte[] ☃xx = new byte[256];
      BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxx = 0; ☃xxxx < 16; ++☃xxxx) {
         for(int ☃xxxxx = 0; ☃xxxxx < 16; ++☃xxxxx) {
            ☃xxx.func_181079_c(☃.field_76701_k << 4 | ☃xxxx, 0, ☃.field_76699_l << 4 | ☃xxxxx);
            ☃xx[☃xxxxx << 4 | ☃xxxx] = (byte)(IRegistry.field_212624_m.func_148757_b(☃.func_180300_a(☃xxx, Biomes.field_180279_ad)) & 0xFF);
         }
      }

      ☃.func_74773_a("Biomes", ☃xx);
      ☃.func_74782_a("Entities", ☃.field_76702_h);
      ☃.func_74782_a("TileEntities", ☃.field_151564_i);
      if (☃.field_151563_j != null) {
         ☃.func_74782_a("TileTicks", ☃.field_151563_j);
      }

      ☃.func_74757_a("convertedFromAlphaFormat", true);
   }

   public static class AnvilConverterData {
      public long field_76698_a;
      public boolean field_76696_b;
      public byte[] field_76697_c;
      public NibbleArrayReader field_76694_d;
      public NibbleArrayReader field_76695_e;
      public NibbleArrayReader field_76692_f;
      public byte[] field_76693_g;
      public NBTTagList field_76702_h;
      public NBTTagList field_151564_i;
      public NBTTagList field_151563_j;
      public final int field_76701_k;
      public final int field_76699_l;

      public AnvilConverterData(int var1, int var2) {
         this.field_76701_k = ☃;
         this.field_76699_l = ☃;
      }
   }
}
