package net.minecraft.world.dimension;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.CheckerboardBiomeProvider;
import net.minecraft.world.biome.provider.CheckerboardBiomeProviderSettings;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkGeneratorNether;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.DebugGenSettings;
import net.minecraft.world.gen.EndGenSettings;
import net.minecraft.world.gen.FlatGenSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraft.world.gen.OverworldGenSettings;

public class OverworldDimension extends Dimension {
   @Override
   public DimensionType func_186058_p() {
      return DimensionType.OVERWORLD;
   }

   @Override
   public boolean func_186056_c(int var1, int var2) {
      return !this.field_76579_a.func_72916_c(☃, ☃) && super.func_186056_c(☃, ☃);
   }

   @Override
   protected void func_76572_b() {
      this.field_191067_f = true;
   }

   @Override
   public IChunkGenerator<? extends IChunkGenSettings> func_186060_c() {
      WorldType ☃ = this.field_76579_a.func_72912_H().func_76067_t();
      ChunkGeneratorType<FlatGenSettings, ChunkGeneratorFlat> ☃x = ChunkGeneratorType.field_205489_f;
      ChunkGeneratorType<DebugGenSettings, ChunkGeneratorDebug> ☃xx = ChunkGeneratorType.field_205488_e;
      ChunkGeneratorType<NetherGenSettings, ChunkGeneratorNether> ☃xxx = ChunkGeneratorType.field_206912_c;
      ChunkGeneratorType<EndGenSettings, ChunkGeneratorEnd> ☃xxxx = ChunkGeneratorType.field_206913_d;
      ChunkGeneratorType<OverworldGenSettings, ChunkGeneratorOverworld> ☃xxxxx = ChunkGeneratorType.field_206911_b;
      BiomeProviderType<SingleBiomeProviderSettings, SingleBiomeProvider> ☃xxxxxx = BiomeProviderType.field_205461_c;
      BiomeProviderType<OverworldBiomeProviderSettings, OverworldBiomeProvider> ☃xxxxxxx = BiomeProviderType.field_206859_d;
      BiomeProviderType<CheckerboardBiomeProviderSettings, CheckerboardBiomeProvider> ☃xxxxxxxx = BiomeProviderType.field_205460_b;
      if (☃ == WorldType.field_77138_c) {
         FlatGenSettings ☃xxxxxxxxx = FlatGenSettings.func_210835_a(
            new Dynamic<>(NBTDynamicOps.field_210820_a, this.field_76579_a.func_72912_H().func_211027_A())
         );
         SingleBiomeProviderSettings ☃xxxxxxxxxx = ☃xxxxxx.func_205458_a().func_205436_a(☃xxxxxxxxx.func_82648_a());
         return ☃x.create(this.field_76579_a, ☃xxxxxx.func_205457_a(☃xxxxxxxxxx), ☃xxxxxxxxx);
      } else if (☃ == WorldType.field_180272_g) {
         SingleBiomeProviderSettings ☃ = ☃xxxxxx.func_205458_a().func_205436_a(Biomes.field_76772_c);
         return ☃xx.create(this.field_76579_a, ☃xxxxxx.func_205457_a(☃), ☃xx.func_205483_a());
      } else if (☃ != WorldType.field_205394_h) {
         OverworldGenSettings ☃ = ☃xxxxx.func_205483_a();
         OverworldBiomeProviderSettings ☃x = ☃xxxxxxx.func_205458_a().func_205439_a(this.field_76579_a.func_72912_H()).func_205441_a(☃);
         return ☃xxxxx.create(this.field_76579_a, ☃xxxxxxx.func_205457_a(☃x), ☃);
      } else {
         BiomeProvider ☃ = null;
         JsonElement ☃x = Dynamic.convert(NBTDynamicOps.field_210820_a, JsonOps.INSTANCE, this.field_76579_a.func_72912_H().func_211027_A());
         JsonObject ☃xx = ☃x.getAsJsonObject();
         if (☃xx.has("biome_source") && ☃xx.getAsJsonObject("biome_source").has("type") && ☃xx.getAsJsonObject("biome_source").has("options")) {
            ResourceLocation ☃xxx = new ResourceLocation(☃xx.getAsJsonObject("biome_source").getAsJsonPrimitive("type").getAsString());
            JsonObject ☃xxxx = ☃xx.getAsJsonObject("biome_source").getAsJsonObject("options");
            Biome[] ☃xxxxx = new Biome[]{Biomes.field_76771_b};
            if (☃xxxx.has("biomes")) {
               JsonArray ☃xxxxxx = ☃xxxx.getAsJsonArray("biomes");
               ☃xxxxx = ☃xxxxxx.size() > 0 ? new Biome[☃xxxxxx.size()] : new Biome[]{Biomes.field_76771_b};

               for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxxx.size(); ++☃xxxxxxx) {
                  Biome ☃xxxxxxxx = IRegistry.field_212624_m.func_212608_b(new ResourceLocation(☃xxxxxx.get(☃xxxxxxx).getAsString()));
                  ☃xxxxx[☃xxxxxxx] = ☃xxxxxxxx != null ? ☃xxxxxxxx : Biomes.field_76771_b;
               }
            }

            if (BiomeProviderType.field_205461_c.func_206858_b().equals(☃xxx)) {
               SingleBiomeProviderSettings ☃xxx = ☃xxxxxx.func_205458_a().func_205436_a(☃xxxxx[0]);
               ☃ = ☃xxxxxx.func_205457_a(☃xxx);
            }

            if (BiomeProviderType.field_205460_b.func_206858_b().equals(☃xxx)) {
               int ☃xxx = ☃xxxx.has("size") ? ☃xxxx.getAsJsonPrimitive("size").getAsInt() : 2;
               CheckerboardBiomeProviderSettings ☃xxxx = ☃xxxxxxxx.func_205458_a().func_206860_a(☃xxxxx).func_206861_a(☃xxx);
               ☃ = ☃xxxxxxxx.func_205457_a(☃xxxx);
            }

            if (BiomeProviderType.field_206859_d.func_206858_b().equals(☃xxx)) {
               OverworldBiomeProviderSettings ☃xxx = ☃xxxxxxx.func_205458_a()
                  .func_205441_a(new OverworldGenSettings())
                  .func_205439_a(this.field_76579_a.func_72912_H());
               ☃ = ☃xxxxxxx.func_205457_a(☃xxx);
            }
         }

         if (☃ == null) {
            ☃ = ☃xxxxxx.func_205457_a(☃xxxxxx.func_205458_a().func_205436_a(Biomes.field_76771_b));
         }

         IBlockState ☃ = Blocks.field_150348_b.func_176223_P();
         IBlockState ☃x = Blocks.field_150355_j.func_176223_P();
         if (☃xx.has("chunk_generator") && ☃xx.getAsJsonObject("chunk_generator").has("options")) {
            if (☃xx.getAsJsonObject("chunk_generator").getAsJsonObject("options").has("default_block")) {
               String ☃xx = ☃xx.getAsJsonObject("chunk_generator").getAsJsonObject("options").getAsJsonPrimitive("default_block").getAsString();
               Block ☃xxx = IRegistry.field_212618_g.func_82594_a(new ResourceLocation(☃xx));
               if (☃xxx != null) {
                  ☃ = ☃xxx.func_176223_P();
               }
            }

            if (☃xx.getAsJsonObject("chunk_generator").getAsJsonObject("options").has("default_fluid")) {
               String ☃xx = ☃xx.getAsJsonObject("chunk_generator").getAsJsonObject("options").getAsJsonPrimitive("default_fluid").getAsString();
               Block ☃xxx = IRegistry.field_212618_g.func_82594_a(new ResourceLocation(☃xx));
               if (☃xxx != null) {
                  ☃x = ☃xxx.func_176223_P();
               }
            }
         }

         if (☃xx.has("chunk_generator") && ☃xx.getAsJsonObject("chunk_generator").has("type")) {
            ResourceLocation ☃ = new ResourceLocation(☃xx.getAsJsonObject("chunk_generator").getAsJsonPrimitive("type").getAsString());
            if (ChunkGeneratorType.field_206912_c.func_205482_c().equals(☃)) {
               NetherGenSettings ☃x = ☃xxx.func_205483_a();
               ☃x.func_205535_a(☃);
               ☃x.func_205534_b(☃x);
               return ☃xxx.create(this.field_76579_a, ☃, ☃x);
            }

            if (ChunkGeneratorType.field_206913_d.func_205482_c().equals(☃)) {
               EndGenSettings ☃ = ☃xxxx.func_205483_a();
               ☃.func_205538_a(new BlockPos(0, 64, 0));
               ☃.func_205535_a(☃);
               ☃.func_205534_b(☃x);
               return ☃xxxx.create(this.field_76579_a, ☃, ☃);
            }
         }

         OverworldGenSettings ☃ = ☃xxxxx.func_205483_a();
         ☃.func_205535_a(☃);
         ☃.func_205534_b(☃x);
         return ☃xxxxx.create(this.field_76579_a, ☃, ☃);
      }
   }

   @Nullable
   @Override
   public BlockPos func_206920_a(ChunkPos var1, boolean var2) {
      for(int ☃ = ☃.func_180334_c(); ☃ <= ☃.func_180332_e(); ++☃) {
         for(int ☃x = ☃.func_180333_d(); ☃x <= ☃.func_180330_f(); ++☃x) {
            BlockPos ☃xx = this.func_206921_a(☃, ☃x, ☃);
            if (☃xx != null) {
               return ☃xx;
            }
         }
      }

      return null;
   }

   @Nullable
   @Override
   public BlockPos func_206921_a(int var1, int var2, boolean var3) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos(☃, 0, ☃);
      Biome ☃x = this.field_76579_a.func_180494_b(☃);
      IBlockState ☃xx = ☃x.func_203944_q().func_204108_a();
      if (☃ && !☃xx.func_177230_c().func_203417_a(BlockTags.field_205599_H)) {
         return null;
      } else {
         Chunk ☃ = this.field_76579_a.func_72964_e(☃ >> 4, ☃ >> 4);
         int ☃x = ☃.func_201576_a(Heightmap.Type.MOTION_BLOCKING, ☃ & 15, ☃ & 15);
         if (☃x < 0) {
            return null;
         } else if (☃.func_201576_a(Heightmap.Type.WORLD_SURFACE, ☃ & 15, ☃ & 15) > ☃.func_201576_a(Heightmap.Type.OCEAN_FLOOR, ☃ & 15, ☃ & 15)) {
            return null;
         } else {
            for(int ☃ = ☃x + 1; ☃ >= 0; --☃) {
               ☃.func_181079_c(☃, ☃, ☃);
               IBlockState ☃x = this.field_76579_a.func_180495_p(☃);
               if (!☃x.func_204520_s().func_206888_e()) {
                  break;
               }

               if (☃x.equals(☃xx)) {
                  return ☃.func_177984_a().func_185334_h();
               }
            }

            return null;
         }
      }
   }

   @Override
   public float func_76563_a(long var1, float var3) {
      int ☃ = (int)(☃ % 24000L);
      float ☃x = ((float)☃ + ☃) / 24000.0F - 0.25F;
      if (☃x < 0.0F) {
         ++☃x;
      }

      if (☃x > 1.0F) {
         --☃x;
      }

      float var7 = 1.0F - (float)((Math.cos((double)☃x * Math.PI) + 1.0) / 2.0);
      return ☃x + (var7 - ☃x) / 3.0F;
   }

   @Override
   public boolean func_76569_d() {
      return true;
   }

   @Override
   public Vec3d func_76562_b(float var1, float var2) {
      float ☃ = MathHelper.func_76134_b(☃ * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      float ☃x = 0.7529412F;
      float ☃xx = 0.84705883F;
      float ☃xxx = 1.0F;
      ☃x *= ☃ * 0.94F + 0.06F;
      ☃xx *= ☃ * 0.94F + 0.06F;
      ☃xxx *= ☃ * 0.91F + 0.09F;
      return new Vec3d((double)☃x, (double)☃xx, (double)☃xxx);
   }

   @Override
   public boolean func_76567_e() {
      return true;
   }

   @Override
   public boolean func_76568_b(int var1, int var2) {
      return false;
   }
}
