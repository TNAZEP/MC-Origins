package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.Biomes;
import net.minecraft.init.Fluids;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.ServerTickList;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkPrimerTickList;
import net.minecraft.world.chunk.ChunkPrimerWrapper;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.LegacyStructureDataUtil;
import net.minecraft.world.gen.feature.structure.StructureIO;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.SessionLockException;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.minecraft.world.storage.WorldSavedDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
   private static final Logger field_151505_a = LogManager.getLogger();
   private final Map<ChunkPos, NBTTagCompound> field_75828_a = Maps.<ChunkPos, NBTTagCompound>newHashMap();
   private final File field_75825_d;
   private final DataFixer field_193416_e;
   private LegacyStructureDataUtil field_208031_e;
   private boolean field_183014_e;

   public AnvilChunkLoader(File var1, DataFixer var2) {
      this.field_75825_d = ☃;
      this.field_193416_e = ☃;
   }

   @Nullable
   private NBTTagCompound func_208030_a(IWorld var1, int var2, int var3) throws IOException {
      return this.func_212146_a(☃.func_201675_m().func_186058_p(), ☃.func_175693_T(), ☃, ☃);
   }

   @Nullable
   private NBTTagCompound func_212146_a(DimensionType var1, @Nullable WorldSavedDataStorage var2, int var3, int var4) throws IOException {
      NBTTagCompound ☃ = (NBTTagCompound)this.field_75828_a.get(new ChunkPos(☃, ☃));
      if (☃ != null) {
         return ☃;
      } else {
         DataInputStream ☃ = RegionFileCache.func_76549_c(this.field_75825_d, ☃, ☃);
         if (☃ == null) {
            return null;
         } else {
            NBTTagCompound ☃ = CompressedStreamTools.func_74794_a(☃);
            ☃.close();
            int ☃x = ☃.func_150297_b("DataVersion", 99) ? ☃.func_74762_e("DataVersion") : -1;
            if (☃x < 1493) {
               ☃ = NBTUtil.func_210821_a(this.field_193416_e, DataFixTypes.CHUNK, ☃, ☃x, 1493);
               if (☃.func_74775_l("Level").func_74767_n("hasLegacyStructureData")) {
                  this.func_212429_a(☃, ☃);
                  ☃ = this.field_208031_e.func_212181_a(☃);
               }
            }

            ☃ = NBTUtil.func_210822_a(this.field_193416_e, DataFixTypes.CHUNK, ☃, Math.max(1493, ☃x));
            if (☃x < 1631) {
               ☃.func_74768_a("DataVersion", 1631);
               this.func_75824_a(new ChunkPos(☃, ☃), ☃);
            }

            return ☃;
         }
      }
   }

   public void func_212429_a(DimensionType var1, @Nullable WorldSavedDataStorage var2) {
      if (this.field_208031_e == null) {
         this.field_208031_e = LegacyStructureDataUtil.func_212183_a(☃, ☃);
      }
   }

   @Nullable
   @Override
   public Chunk func_199813_a(IWorld var1, int var2, int var3, Consumer<Chunk> var4) throws IOException {
      NBTTagCompound ☃ = this.func_208030_a(☃, ☃, ☃);
      if (☃ == null) {
         return null;
      } else {
         Chunk ☃ = this.func_75822_a(☃, ☃, ☃, ☃);
         if (☃ != null) {
            ☃.accept(☃);
            this.func_199814_a(☃.func_74775_l("Level"), ☃);
         }

         return ☃;
      }
   }

   @Nullable
   @Override
   public ChunkPrimer func_202152_b(IWorld var1, int var2, int var3, Consumer<IChunk> var4) throws IOException {
      NBTTagCompound ☃;
      try {
         ☃ = this.func_208030_a(☃, ☃, ☃);
      } catch (ReportedException var7) {
         if (var7.getCause() instanceof IOException) {
            throw (IOException)var7.getCause();
         }

         throw var7;
      }

      if (☃ == null) {
         return null;
      } else {
         ChunkPrimer ☃ = this.func_202165_b(☃, ☃, ☃, ☃);
         if (☃ != null) {
            ☃.accept(☃);
         }

         return ☃;
      }
   }

   @Nullable
   protected Chunk func_75822_a(IWorld var1, int var2, int var3, NBTTagCompound var4) {
      if (☃.func_150297_b("Level", 10) && ☃.func_74775_l("Level").func_150297_b("Status", 8)) {
         ChunkStatus.Type ☃ = this.func_202161_a(☃);
         if (☃ != ChunkStatus.Type.LEVELCHUNK) {
            return null;
         } else {
            NBTTagCompound ☃ = ☃.func_74775_l("Level");
            if (!☃.func_150297_b("Sections", 9)) {
               field_151505_a.error("Chunk file at {},{} is missing block data, skipping", ☃, ☃);
               return null;
            } else {
               Chunk ☃ = this.func_75823_a(☃, ☃);
               if (!☃.func_76600_a(☃, ☃)) {
                  field_151505_a.error(
                     "Chunk file at {},{} is in the wrong location; relocating. (Expected {}, {}, got {}, {})", ☃, ☃, ☃, ☃, ☃.field_76635_g, ☃.field_76647_h
                  );
                  ☃.func_74768_a("xPos", ☃);
                  ☃.func_74768_a("zPos", ☃);
                  ☃ = this.func_75823_a(☃, ☃);
               }

               return ☃;
            }
         }
      } else {
         field_151505_a.error("Chunk file at {},{} is missing level data, skipping", ☃, ☃);
         return null;
      }
   }

   @Nullable
   protected ChunkPrimer func_202165_b(IWorld var1, int var2, int var3, NBTTagCompound var4) {
      if (☃.func_150297_b("Level", 10) && ☃.func_74775_l("Level").func_150297_b("Status", 8)) {
         ChunkStatus.Type ☃ = this.func_202161_a(☃);
         if (☃ == ChunkStatus.Type.LEVELCHUNK) {
            return new ChunkPrimerWrapper(this.func_75822_a(☃, ☃, ☃, ☃));
         } else {
            NBTTagCompound ☃ = ☃.func_74775_l("Level");
            return this.func_202155_b(☃, ☃);
         }
      } else {
         field_151505_a.error("Chunk file at {},{} is missing level data, skipping", ☃, ☃);
         return null;
      }
   }

   @Override
   public void func_75816_a(World var1, IChunk var2) throws IOException, SessionLockException {
      ☃.func_72906_B();

      try {
         NBTTagCompound ☃ = new NBTTagCompound();
         NBTTagCompound ☃x = new NBTTagCompound();
         ☃.func_74768_a("DataVersion", 1631);
         ChunkPos ☃xx = ☃.func_76632_l();
         ☃.func_74782_a("Level", ☃x);
         if (☃.func_201589_g().func_202129_d() == ChunkStatus.Type.LEVELCHUNK) {
            this.func_75820_a((Chunk)☃, ☃, ☃x);
         } else {
            NBTTagCompound ☃ = this.func_208030_a(☃, ☃xx.field_77276_a, ☃xx.field_77275_b);
            if (☃ != null && this.func_202161_a(☃) == ChunkStatus.Type.LEVELCHUNK) {
               return;
            }

            this.func_202156_a((ChunkPrimer)☃, ☃, ☃x);
         }

         this.func_75824_a(☃xx, ☃);
      } catch (Exception var7) {
         field_151505_a.error("Failed to save chunk", var7);
      }
   }

   protected void func_75824_a(ChunkPos var1, NBTTagCompound var2) {
      this.field_75828_a.put(☃, ☃);
      ThreadedFileIOBase.func_178779_a().func_75735_a(this);
   }

   @Override
   public boolean func_75814_c() {
      Iterator<Entry<ChunkPos, NBTTagCompound>> ☃ = this.field_75828_a.entrySet().iterator();
      if (!☃.hasNext()) {
         if (this.field_183014_e) {
            field_151505_a.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", this.field_75825_d.getName());
         }

         return false;
      } else {
         Entry<ChunkPos, NBTTagCompound> ☃ = (Entry)☃.next();
         ☃.remove();
         ChunkPos ☃x = (ChunkPos)☃.getKey();
         NBTTagCompound ☃xx = (NBTTagCompound)☃.getValue();
         if (☃xx == null) {
            return true;
         } else {
            try {
               DataOutputStream ☃ = RegionFileCache.func_76552_d(this.field_75825_d, ☃x.field_77276_a, ☃x.field_77275_b);
               CompressedStreamTools.func_74800_a(☃xx, ☃);
               ☃.close();
               if (this.field_208031_e != null) {
                  this.field_208031_e.func_208216_a(☃x.func_201841_a());
               }
            } catch (Exception var6) {
               field_151505_a.error("Failed to save chunk", var6);
            }

            return true;
         }
      }
   }

   private ChunkStatus.Type func_202161_a(@Nullable NBTTagCompound var1) {
      if (☃ != null) {
         ChunkStatus ☃ = ChunkStatus.func_202127_a(☃.func_74775_l("Level").func_74779_i("Status"));
         if (☃ != null) {
            return ☃.func_202129_d();
         }
      }

      return ChunkStatus.Type.PROTOCHUNK;
   }

   @Override
   public void func_75818_b() {
      try {
         this.field_183014_e = true;

         while(this.func_75814_c()) {
         }
      } finally {
         this.field_183014_e = false;
      }
   }

   private void func_202156_a(ChunkPrimer var1, World var2, NBTTagCompound var3) {
      int ☃ = ☃.func_76632_l().field_77276_a;
      int ☃x = ☃.func_76632_l().field_77275_b;
      ☃.func_74768_a("xPos", ☃);
      ☃.func_74768_a("zPos", ☃x);
      ☃.func_74772_a("LastUpdate", ☃.func_82737_E());
      ☃.func_74772_a("InhabitedTime", ☃.func_209216_m());
      ☃.func_74778_a("Status", ☃.func_201589_g().func_202125_b());
      UpgradeData ☃xx = ☃.func_201631_p();
      if (!☃xx.func_196988_a()) {
         ☃.func_74782_a("UpgradeData", ☃xx.func_196992_b());
      }

      ChunkSection[] ☃ = ☃.func_76587_i();
      NBTTagList ☃x = this.func_202159_a(☃, ☃);
      ☃.func_74782_a("Sections", ☃x);
      Biome[] ☃xx = ☃.func_201590_e();
      int[] ☃xxx = ☃xx != null ? new int[☃xx.length] : new int[0];
      if (☃xx != null) {
         for(int ☃xxxx = 0; ☃xxxx < ☃xx.length; ++☃xxxx) {
            ☃xxx[☃xxxx] = IRegistry.field_212624_m.func_148757_b(☃xx[☃xxxx]);
         }
      }

      ☃.func_74783_a("Biomes", ☃xxx);
      NBTTagList ☃ = new NBTTagList();

      for(NBTTagCompound ☃x : ☃.func_201652_l()) {
         ☃.add((INBTBase)☃x);
      }

      ☃.func_74782_a("Entities", ☃);
      NBTTagList ☃x = new NBTTagList();

      for(BlockPos ☃xx : ☃.func_201638_j()) {
         TileEntity ☃xxx = ☃.func_175625_s(☃xx);
         if (☃xxx != null) {
            NBTTagCompound ☃xxxx = new NBTTagCompound();
            ☃xxx.func_189515_b(☃xxxx);
            ☃x.add((INBTBase)☃xxxx);
         } else {
            ☃x.add((INBTBase)☃.func_201579_g(☃xx));
         }
      }

      ☃.func_74782_a("TileEntities", ☃x);
      ☃.func_74782_a("Lights", func_202163_a(☃.func_201647_i()));
      ☃.func_74782_a("PostProcessing", func_202163_a(☃.func_201645_n()));
      ☃.func_74782_a("ToBeTicked", ☃.func_205218_i_().func_205379_a());
      ☃.func_74782_a("LiquidsToBeTicked", ☃.func_212247_j().func_205379_a());
      NBTTagCompound ☃xx = new NBTTagCompound();

      for(Heightmap.Type ☃xxx : ☃.func_201634_m()) {
         ☃xx.func_74782_a(☃xxx.func_203500_b(), new NBTTagLongArray(☃.func_201642_a(☃xxx).func_202269_a()));
      }

      ☃.func_74782_a("Heightmaps", ☃xx);
      NBTTagCompound ☃xxx = new NBTTagCompound();

      for(GenerationStage.Carving ☃xxxx : GenerationStage.Carving.values()) {
         ☃xxx.func_74773_a(☃xxxx.toString(), ☃.func_205749_a(☃xxxx).toByteArray());
      }

      ☃.func_74782_a("CarvingMasks", ☃xxx);
      ☃.func_74782_a("Structures", this.func_202160_a(☃, ☃x, ☃.func_201609_c(), ☃.func_201604_d()));
   }

   private void func_75820_a(Chunk var1, World var2, NBTTagCompound var3) {
      ☃.func_74768_a("xPos", ☃.field_76635_g);
      ☃.func_74768_a("zPos", ☃.field_76647_h);
      ☃.func_74772_a("LastUpdate", ☃.func_82737_E());
      ☃.func_74772_a("InhabitedTime", ☃.func_177416_w());
      ☃.func_74778_a("Status", ☃.func_201589_g().func_202125_b());
      UpgradeData ☃ = ☃.func_196966_y();
      if (!☃.func_196988_a()) {
         ☃.func_74782_a("UpgradeData", ☃.func_196992_b());
      }

      ChunkSection[] ☃ = ☃.func_76587_i();
      NBTTagList ☃x = this.func_202159_a(☃, ☃);
      ☃.func_74782_a("Sections", ☃x);
      Biome[] ☃xx = ☃.func_201590_e();
      int[] ☃xxx = new int[☃xx.length];

      for(int ☃xxxx = 0; ☃xxxx < ☃xx.length; ++☃xxxx) {
         ☃xxx[☃xxxx] = IRegistry.field_212624_m.func_148757_b(☃xx[☃xxxx]);
      }

      ☃.func_74783_a("Biomes", ☃xxx);
      ☃.func_177409_g(false);
      NBTTagList ☃xxxx = new NBTTagList();

      for(int ☃xxxxx = 0; ☃xxxxx < ☃.func_177429_s().length; ++☃xxxxx) {
         for(Entity ☃xxxxxx : ☃.func_177429_s()[☃xxxxx]) {
            NBTTagCompound ☃xxxxxxx = new NBTTagCompound();
            if (☃xxxxxx.func_70039_c(☃xxxxxxx)) {
               ☃.func_177409_g(true);
               ☃xxxx.add((INBTBase)☃xxxxxxx);
            }
         }
      }

      ☃.func_74782_a("Entities", ☃xxxx);
      NBTTagList ☃xxxxx = new NBTTagList();

      for(BlockPos ☃xxxxxx : ☃.func_203066_o()) {
         TileEntity ☃xxxxxxx = ☃.func_175625_s(☃xxxxxx);
         if (☃xxxxxxx != null) {
            NBTTagCompound ☃xxxxxxxx = new NBTTagCompound();
            ☃xxxxxxx.func_189515_b(☃xxxxxxxx);
            ☃xxxxxxxx.func_74757_a("keepPacked", false);
            ☃xxxxx.add((INBTBase)☃xxxxxxxx);
         } else {
            NBTTagCompound ☃xxxxxxx = ☃.func_201579_g(☃xxxxxx);
            if (☃xxxxxxx != null) {
               ☃xxxxxxx.func_74757_a("keepPacked", true);
               ☃xxxxx.add((INBTBase)☃xxxxxxx);
            }
         }
      }

      ☃.func_74782_a("TileEntities", ☃xxxxx);
      if (☃.func_205220_G_() instanceof ServerTickList) {
         ☃.func_74782_a("TileTicks", ((ServerTickList)☃.func_205220_G_()).func_205363_a(☃));
      }

      if (☃.func_205219_F_() instanceof ServerTickList) {
         ☃.func_74782_a("LiquidTicks", ((ServerTickList)☃.func_205219_F_()).func_205363_a(☃));
      }

      ☃.func_74782_a("PostProcessing", func_202163_a(☃.func_201614_D()));
      if (☃.func_205218_i_() instanceof ChunkPrimerTickList) {
         ☃.func_74782_a("ToBeTicked", ((ChunkPrimerTickList)☃.func_205218_i_()).func_205379_a());
      }

      if (☃.func_212247_j() instanceof ChunkPrimerTickList) {
         ☃.func_74782_a("LiquidsToBeTicked", ((ChunkPrimerTickList)☃.func_212247_j()).func_205379_a());
      }

      NBTTagCompound ☃xxxxxx = new NBTTagCompound();

      for(Heightmap.Type ☃xxxxxxx : ☃.func_201615_v()) {
         if (☃xxxxxxx.func_207512_c() == Heightmap.Usage.LIVE_WORLD) {
            ☃xxxxxx.func_74782_a(☃xxxxxxx.func_203500_b(), new NBTTagLongArray(☃.func_201608_a(☃xxxxxxx).func_202269_a()));
         }
      }

      ☃.func_74782_a("Heightmaps", ☃xxxxxx);
      ☃.func_74782_a("Structures", this.func_202160_a(☃.field_76635_g, ☃.field_76647_h, ☃.func_201609_c(), ☃.func_201604_d()));
   }

   private Chunk func_75823_a(IWorld var1, NBTTagCompound var2) {
      int ☃ = ☃.func_74762_e("xPos");
      int ☃x = ☃.func_74762_e("zPos");
      Biome[] ☃xx = new Biome[256];
      BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();
      if (☃.func_150297_b("Biomes", 11)) {
         int[] ☃xxxx = ☃.func_74759_k("Biomes");

         for(int ☃xxxxx = 0; ☃xxxxx < ☃xxxx.length; ++☃xxxxx) {
            ☃xx[☃xxxxx] = IRegistry.field_212624_m.func_148754_a(☃xxxx[☃xxxxx]);
            if (☃xx[☃xxxxx] == null) {
               ☃xx[☃xxxxx] = ☃.func_72863_F()
                  .func_201711_g()
                  .func_202090_b()
                  .func_180300_a(☃xxx.func_181079_c((☃xxxxx & 15) + (☃ << 4), 0, (☃xxxxx >> 4 & 15) + (☃x << 4)), Biomes.field_76772_c);
            }
         }
      } else {
         for(int ☃ = 0; ☃ < ☃xx.length; ++☃) {
            ☃xx[☃] = ☃.func_72863_F()
               .func_201711_g()
               .func_202090_b()
               .func_180300_a(☃xxx.func_181079_c((☃ & 15) + (☃ << 4), 0, (☃ >> 4 & 15) + (☃x << 4)), Biomes.field_76772_c);
         }
      }

      UpgradeData ☃ = ☃.func_150297_b("UpgradeData", 10) ? new UpgradeData(☃.func_74775_l("UpgradeData")) : UpgradeData.field_196994_a;
      ChunkPrimerTickList<Block> ☃x = new ChunkPrimerTickList<>(
         var0 -> var0.func_176223_P().func_196958_f(), IRegistry.field_212618_g::func_177774_c, IRegistry.field_212618_g::func_82594_a, new ChunkPos(☃, ☃x)
      );
      ChunkPrimerTickList<Fluid> ☃xx = new ChunkPrimerTickList<>(
         var0 -> var0 == Fluids.field_204541_a, IRegistry.field_212619_h::func_177774_c, IRegistry.field_212619_h::func_82594_a, new ChunkPos(☃, ☃x)
      );
      long ☃xxx = ☃.func_74763_f("InhabitedTime");
      Chunk ☃xxxx = new Chunk(☃.func_201672_e(), ☃, ☃x, ☃xx, ☃, ☃x, ☃xx, ☃xxx);
      ☃xxxx.func_201613_c(☃.func_74779_i("Status"));
      NBTTagList ☃xxxxx = ☃.func_150295_c("Sections", 10);
      ☃xxxx.func_76602_a(this.func_202158_a(☃, ☃xxxxx));
      NBTTagCompound ☃xxxxxx = ☃.func_74775_l("Heightmaps");

      for(Heightmap.Type ☃xxxxxxx : Heightmap.Type.values()) {
         if (☃xxxxxxx.func_207512_c() == Heightmap.Usage.LIVE_WORLD) {
            String ☃xxxxxxxx = ☃xxxxxxx.func_203500_b();
            if (☃xxxxxx.func_150297_b(☃xxxxxxxx, 12)) {
               ☃xxxx.func_201607_a(☃xxxxxxx, ☃xxxxxx.func_197645_o(☃xxxxxxxx));
            } else {
               ☃xxxx.func_201608_a(☃xxxxxxx).func_202266_a();
            }
         }
      }

      NBTTagCompound ☃xxxxxxx = ☃.func_74775_l("Structures");
      ☃xxxx.func_201612_a(this.func_202162_c(☃, ☃xxxxxxx));
      ☃xxxx.func_201606_b(this.func_202167_b(☃xxxxxxx));
      NBTTagList ☃xxxxxxxx = ☃.func_150295_c("PostProcessing", 9);

      for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃xxxxxxxx.size(); ++☃xxxxxxxxx) {
         NBTTagList ☃xxxxxxxxxx = ☃xxxxxxxx.func_202169_e(☃xxxxxxxxx);

         for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < ☃xxxxxxxxxx.size(); ++☃xxxxxxxxxxx) {
            ☃xxxx.func_201610_a(☃xxxxxxxxxx.func_202170_f(☃xxxxxxxxxxx), ☃xxxxxxxxx);
         }
      }

      ☃x.func_205380_a(☃.func_150295_c("ToBeTicked", 9));
      ☃xx.func_205380_a(☃.func_150295_c("LiquidsToBeTicked", 9));
      if (☃.func_74767_n("shouldSave")) {
         ☃xxxx.func_177427_f(true);
      }

      return ☃xxxx;
   }

   private void func_199814_a(NBTTagCompound var1, Chunk var2) {
      NBTTagList ☃ = ☃.func_150295_c("Entities", 10);
      World ☃x = ☃.func_177412_p();

      for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
         NBTTagCompound ☃xxx = ☃.func_150305_b(☃xx);
         func_186050_a(☃xxx, ☃x, ☃);
         ☃.func_177409_g(true);
      }

      NBTTagList ☃xx = ☃.func_150295_c("TileEntities", 10);

      for(int ☃xxx = 0; ☃xxx < ☃xx.size(); ++☃xxx) {
         NBTTagCompound ☃xxxx = ☃xx.func_150305_b(☃xxx);
         boolean ☃xxxxx = ☃xxxx.func_74767_n("keepPacked");
         if (☃xxxxx) {
            ☃.func_201591_a(☃xxxx);
         } else {
            TileEntity ☃xxxx = TileEntity.func_203403_c(☃xxxx);
            if (☃xxxx != null) {
               ☃.func_150813_a(☃xxxx);
            }
         }
      }

      if (☃.func_150297_b("TileTicks", 9) && ☃x.func_205220_G_() instanceof ServerTickList) {
         ((ServerTickList)☃x.func_205220_G_()).func_205369_a(☃.func_150295_c("TileTicks", 10));
      }

      if (☃.func_150297_b("LiquidTicks", 9) && ☃x.func_205219_F_() instanceof ServerTickList) {
         ((ServerTickList)☃x.func_205219_F_()).func_205369_a(☃.func_150295_c("LiquidTicks", 10));
      }
   }

   private ChunkPrimer func_202155_b(IWorld var1, NBTTagCompound var2) {
      int ☃ = ☃.func_74762_e("xPos");
      int ☃x = ☃.func_74762_e("zPos");
      Biome[] ☃xx = new Biome[256];
      BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();
      if (☃.func_150297_b("Biomes", 11)) {
         int[] ☃xxxx = ☃.func_74759_k("Biomes");

         for(int ☃xxxxx = 0; ☃xxxxx < ☃xxxx.length; ++☃xxxxx) {
            ☃xx[☃xxxxx] = IRegistry.field_212624_m.func_148754_a(☃xxxx[☃xxxxx]);
            if (☃xx[☃xxxxx] == null) {
               ☃xx[☃xxxxx] = ☃.func_72863_F()
                  .func_201711_g()
                  .func_202090_b()
                  .func_180300_a(☃xxx.func_181079_c((☃xxxxx & 15) + (☃ << 4), 0, (☃xxxxx >> 4 & 15) + (☃x << 4)), Biomes.field_76772_c);
            }
         }
      } else {
         for(int ☃ = 0; ☃ < ☃xx.length; ++☃) {
            ☃xx[☃] = ☃.func_72863_F()
               .func_201711_g()
               .func_202090_b()
               .func_180300_a(☃xxx.func_181079_c((☃ & 15) + (☃ << 4), 0, (☃ >> 4 & 15) + (☃x << 4)), Biomes.field_76772_c);
         }
      }

      UpgradeData ☃ = ☃.func_150297_b("UpgradeData", 10) ? new UpgradeData(☃.func_74775_l("UpgradeData")) : UpgradeData.field_196994_a;
      ChunkPrimer ☃x = new ChunkPrimer(☃, ☃x, ☃);
      ☃x.func_201577_a(☃xx);
      ☃x.func_209215_b(☃.func_74763_f("InhabitedTime"));
      ☃x.func_201650_c(☃.func_74779_i("Status"));
      NBTTagList ☃xx = ☃.func_150295_c("Sections", 10);
      ☃x.func_201630_a(this.func_202158_a(☃, ☃xx));
      NBTTagList ☃xxx = ☃.func_150295_c("Entities", 10);

      for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
         ☃x.func_201626_b(☃xxx.func_150305_b(☃xxxx));
      }

      NBTTagList ☃xxxx = ☃.func_150295_c("TileEntities", 10);

      for(int ☃xxxxx = 0; ☃xxxxx < ☃xxxx.size(); ++☃xxxxx) {
         NBTTagCompound ☃xxxxxx = ☃xxxx.func_150305_b(☃xxxxx);
         ☃x.func_201591_a(☃xxxxxx);
      }

      NBTTagList ☃xxxxx = ☃.func_150295_c("Lights", 9);

      for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxxx.size(); ++☃xxxxxx) {
         NBTTagList ☃xxxxxxx = ☃xxxxx.func_202169_e(☃xxxxxx);

         for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xxxxxxx.size(); ++☃xxxxxxxx) {
            ☃x.func_201646_a(☃xxxxxxx.func_202170_f(☃xxxxxxxx), ☃xxxxxx);
         }
      }

      NBTTagList ☃xxxxxx = ☃.func_150295_c("PostProcessing", 9);

      for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxxx.size(); ++☃xxxxxxx) {
         NBTTagList ☃xxxxxxxx = ☃xxxxxx.func_202169_e(☃xxxxxxx);

         for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃xxxxxxxx.size(); ++☃xxxxxxxxx) {
            ☃x.func_201636_b(☃xxxxxxxx.func_202170_f(☃xxxxxxxxx), ☃xxxxxxx);
         }
      }

      ☃x.func_205218_i_().func_205380_a(☃.func_150295_c("ToBeTicked", 9));
      ☃x.func_212247_j().func_205380_a(☃.func_150295_c("LiquidsToBeTicked", 9));
      NBTTagCompound ☃xxxxxxx = ☃.func_74775_l("Heightmaps");

      for(String ☃xxxxxxxx : ☃xxxxxxx.func_150296_c()) {
         ☃x.func_201643_a(Heightmap.Type.func_203501_a(☃xxxxxxxx), ☃xxxxxxx.func_197645_o(☃xxxxxxxx));
      }

      NBTTagCompound ☃xxxxxxxx = ☃.func_74775_l("Structures");
      ☃x.func_201648_a(this.func_202162_c(☃, ☃xxxxxxxx));
      ☃x.func_201641_b(this.func_202167_b(☃xxxxxxxx));
      NBTTagCompound ☃xxxxxxxxx = ☃.func_74775_l("CarvingMasks");

      for(String ☃xxxxxxxxxx : ☃xxxxxxxxx.func_150296_c()) {
         GenerationStage.Carving ☃xxxxxxxxxxx = GenerationStage.Carving.valueOf(☃xxxxxxxxxx);
         ☃x.func_205767_a(☃xxxxxxxxxxx, BitSet.valueOf(☃xxxxxxxxx.func_74770_j(☃xxxxxxxxxx)));
      }

      return ☃x;
   }

   private NBTTagList func_202159_a(World var1, ChunkSection[] var2) {
      NBTTagList ☃ = new NBTTagList();
      boolean ☃x = ☃.field_73011_w.func_191066_m();

      for(ChunkSection ☃xx : ☃) {
         if (☃xx != Chunk.field_186036_a) {
            NBTTagCompound ☃xxx = new NBTTagCompound();
            ☃xxx.func_74774_a("Y", (byte)(☃xx.func_76662_d() >> 4 & 0xFF));
            ☃xx.func_186049_g().func_196963_b(☃xxx, "Palette", "BlockStates");
            ☃xxx.func_74773_a("BlockLight", ☃xx.func_76661_k().func_177481_a());
            if (☃x) {
               ☃xxx.func_74773_a("SkyLight", ☃xx.func_76671_l().func_177481_a());
            } else {
               ☃xxx.func_74773_a("SkyLight", new byte[☃xx.func_76661_k().func_177481_a().length]);
            }

            ☃.add((INBTBase)☃xxx);
         }
      }

      return ☃;
   }

   private ChunkSection[] func_202158_a(IWorldReaderBase var1, NBTTagList var2) {
      int ☃ = 16;
      ChunkSection[] ☃x = new ChunkSection[16];
      boolean ☃xx = ☃.func_201675_m().func_191066_m();

      for(int ☃xxx = 0; ☃xxx < ☃.size(); ++☃xxx) {
         NBTTagCompound ☃xxxx = ☃.func_150305_b(☃xxx);
         int ☃xxxxx = ☃xxxx.func_74771_c("Y");
         ChunkSection ☃xxxxxx = new ChunkSection(☃xxxxx << 4, ☃xx);
         ☃xxxxxx.func_186049_g().func_196964_a(☃xxxx, "Palette", "BlockStates");
         ☃xxxxxx.func_76659_c(new NibbleArray(☃xxxx.func_74770_j("BlockLight")));
         if (☃xx) {
            ☃xxxxxx.func_76666_d(new NibbleArray(☃xxxx.func_74770_j("SkyLight")));
         }

         ☃xxxxxx.func_76672_e();
         ☃x[☃xxxxx] = ☃xxxxxx;
      }

      return ☃x;
   }

   private NBTTagCompound func_202160_a(int var1, int var2, Map<String, StructureStart> var3, Map<String, LongSet> var4) {
      NBTTagCompound ☃ = new NBTTagCompound();
      NBTTagCompound ☃x = new NBTTagCompound();

      for(Entry<String, StructureStart> ☃xx : ☃.entrySet()) {
         ☃x.func_74782_a((String)☃xx.getKey(), ((StructureStart)☃xx.getValue()).func_143021_a(☃, ☃));
      }

      ☃.func_74782_a("Starts", ☃x);
      NBTTagCompound ☃xx = new NBTTagCompound();

      for(Entry<String, LongSet> ☃xxx : ☃.entrySet()) {
         ☃xx.func_74782_a((String)☃xxx.getKey(), new NBTTagLongArray((LongSet)☃xxx.getValue()));
      }

      ☃.func_74782_a("References", ☃xx);
      return ☃;
   }

   private Map<String, StructureStart> func_202162_c(IWorld var1, NBTTagCompound var2) {
      Map<String, StructureStart> ☃ = Maps.newHashMap();
      NBTTagCompound ☃x = ☃.func_74775_l("Starts");

      for(String ☃xx : ☃x.func_150296_c()) {
         ☃.put(☃xx, StructureIO.func_202602_a(☃x.func_74775_l(☃xx), ☃));
      }

      return ☃;
   }

   private Map<String, LongSet> func_202167_b(NBTTagCompound var1) {
      Map<String, LongSet> ☃ = Maps.newHashMap();
      NBTTagCompound ☃x = ☃.func_74775_l("References");

      for(String ☃xx : ☃x.func_150296_c()) {
         ☃.put(☃xx, new LongOpenHashSet(☃x.func_197645_o(☃xx)));
      }

      return ☃;
   }

   public static NBTTagList func_202163_a(ShortList[] var0) {
      NBTTagList ☃ = new NBTTagList();

      for(ShortList ☃x : ☃) {
         NBTTagList ☃xx = new NBTTagList();
         if (☃x != null) {
            for(Short ☃xxx : ☃x) {
               ☃xx.add((INBTBase)(new NBTTagShort(☃xxx)));
            }
         }

         ☃.add((INBTBase)☃xx);
      }

      return ☃;
   }

   @Nullable
   private static Entity func_206240_a(NBTTagCompound var0, World var1, Function<Entity, Entity> var2) {
      Entity ☃ = func_186053_a(☃, ☃);
      if (☃ == null) {
         return null;
      } else {
         ☃ = (Entity)☃.apply(☃);
         if (☃ != null && ☃.func_150297_b("Passengers", 9)) {
            NBTTagList ☃ = ☃.func_150295_c("Passengers", 10);

            for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
               Entity ☃xx = func_206240_a(☃.func_150305_b(☃x), ☃, ☃);
               if (☃xx != null) {
                  ☃xx.func_184205_a(☃, true);
               }
            }
         }

         return ☃;
      }
   }

   @Nullable
   public static Entity func_186050_a(NBTTagCompound var0, World var1, Chunk var2) {
      return func_206240_a(☃, ☃, var1x -> {
         ☃.func_76612_a(var1x);
         return var1x;
      });
   }

   @Nullable
   public static Entity func_186054_a(NBTTagCompound var0, World var1, double var2, double var4, double var6, boolean var8) {
      return func_206240_a(☃, ☃, var8x -> {
         var8x.func_70012_b(☃, ☃, ☃, var8x.field_70177_z, var8x.field_70125_A);
         return ☃ && !☃.func_72838_d(var8x) ? null : var8x;
      });
   }

   @Nullable
   public static Entity func_186051_a(NBTTagCompound var0, World var1, boolean var2) {
      return func_206240_a(☃, ☃, var2x -> ☃ && !☃.func_72838_d(var2x) ? null : var2x);
   }

   @Nullable
   protected static Entity func_186053_a(NBTTagCompound var0, World var1) {
      try {
         return EntityType.func_200716_a(☃, ☃);
      } catch (RuntimeException var3) {
         field_151505_a.warn("Exception loading entity: ", var3);
         return null;
      }
   }

   public static void func_186052_a(Entity var0, IWorld var1) {
      if (☃.func_72838_d(☃) && ☃.func_184207_aI()) {
         for(Entity ☃ : ☃.func_184188_bt()) {
            func_186052_a(☃, ☃);
         }
      }
   }

   public boolean func_212147_a(ChunkPos var1, DimensionType var2, WorldSavedDataStorage var3) {
      boolean ☃ = false;

      try {
         this.func_212146_a(☃, ☃, ☃.field_77276_a, ☃.field_77275_b);

         while(this.func_75814_c()) {
            ☃ = true;
         }
      } catch (IOException var6) {
      }

      return ☃;
   }
}
