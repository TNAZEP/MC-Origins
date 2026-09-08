package net.minecraft.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureStart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPrimer implements IChunk {
   private static final Logger field_201653_a = LogManager.getLogger();
   private final ChunkPos field_201654_b;
   private boolean field_201655_c;
   private final AtomicInteger field_205768_d = new AtomicInteger();
   private Biome[] field_201656_d;
   private final Map<Heightmap.Type, Heightmap> field_201657_e = Maps.newEnumMap(Heightmap.Type.class);
   private volatile ChunkStatus field_201658_f = ChunkStatus.EMPTY;
   private final Map<BlockPos, TileEntity> field_201659_g = Maps.<BlockPos, TileEntity>newHashMap();
   private final Map<BlockPos, NBTTagCompound> field_201660_h = Maps.<BlockPos, NBTTagCompound>newHashMap();
   private final ChunkSection[] field_201661_i = new ChunkSection[16];
   private final List<NBTTagCompound> field_201662_j = Lists.<NBTTagCompound>newArrayList();
   private final List<BlockPos> field_201663_k = Lists.<BlockPos>newArrayList();
   private final ShortList[] field_201665_m = new ShortList[16];
   private final Map<String, StructureStart> field_201666_n = Maps.newHashMap();
   private final Map<String, LongSet> field_201667_o = Maps.newHashMap();
   private final UpgradeData field_201668_p;
   private final ChunkPrimerTickList<Block> field_201664_l;
   private final ChunkPrimerTickList<Fluid> field_205333_q;
   private long field_209217_s;
   private final Map<GenerationStage.Carving, BitSet> field_205769_s = Maps.newHashMap();
   private boolean field_207740_t;

   public ChunkPrimer(int var1, int var2, UpgradeData var3) {
      this(new ChunkPos(☃, ☃), ☃);
   }

   public ChunkPrimer(ChunkPos var1, UpgradeData var2) {
      this.field_201654_b = ☃;
      this.field_201668_p = ☃;
      this.field_201664_l = new ChunkPrimerTickList<>(
         var0 -> var0 == null || var0.func_176223_P().func_196958_f(), IRegistry.field_212618_g::func_177774_c, IRegistry.field_212618_g::func_82594_a, ☃
      );
      this.field_205333_q = new ChunkPrimerTickList<>(
         var0 -> var0 == null || var0 == Fluids.field_204541_a, IRegistry.field_212619_h::func_177774_c, IRegistry.field_212619_h::func_82594_a, ☃
      );
   }

   public static ShortList func_205330_a(ShortList[] var0, int var1) {
      if (☃[☃] == null) {
         ☃[☃] = new ShortArrayList();
      }

      return ☃[☃];
   }

   @Nullable
   @Override
   public IBlockState func_180495_p(BlockPos var1) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();
      if (☃x >= 0 && ☃x < 256) {
         return this.field_201661_i[☃x >> 4] == Chunk.field_186036_a
            ? Blocks.field_150350_a.func_176223_P()
            : this.field_201661_i[☃x >> 4].func_177485_a(☃ & 15, ☃x & 15, ☃xx & 15);
      } else {
         return Blocks.field_201940_ji.func_176223_P();
      }
   }

   @Override
   public IFluidState func_204610_c(BlockPos var1) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();
      return ☃x >= 0 && ☃x < 256 && this.field_201661_i[☃x >> 4] != Chunk.field_186036_a
         ? this.field_201661_i[☃x >> 4].func_206914_b(☃ & 15, ☃x & 15, ☃xx & 15)
         : Fluids.field_204541_a.func_207188_f();
   }

   @Override
   public List<BlockPos> func_201582_h() {
      return this.field_201663_k;
   }

   public ShortList[] func_201647_i() {
      ShortList[] ☃ = new ShortList[16];

      for(BlockPos ☃x : this.field_201663_k) {
         func_205330_a(☃, ☃x.func_177956_o() >> 4).add(func_201651_i(☃x));
      }

      return ☃;
   }

   public void func_201646_a(short var1, int var2) {
      this.func_201637_h(func_201635_a(☃, ☃, this.field_201654_b));
   }

   public void func_201637_h(BlockPos var1) {
      this.field_201663_k.add(☃);
   }

   @Nullable
   @Override
   public IBlockState func_177436_a(BlockPos var1, IBlockState var2, boolean var3) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();
      if (☃x >= 0 && ☃x < 256) {
         if (☃.func_185906_d() > 0) {
            this.field_201663_k.add(new BlockPos((☃ & 15) + this.func_76632_l().func_180334_c(), ☃x, (☃xx & 15) + this.func_76632_l().func_180333_d()));
         }

         if (this.field_201661_i[☃x >> 4] == Chunk.field_186036_a) {
            if (☃.func_177230_c() == Blocks.field_150350_a) {
               return ☃;
            }

            this.field_201661_i[☃x >> 4] = new ChunkSection(☃x >> 4 << 4, this.func_201649_r());
         }

         IBlockState ☃xxx = this.field_201661_i[☃x >> 4].func_177485_a(☃ & 15, ☃x & 15, ☃xx & 15);
         this.field_201661_i[☃x >> 4].func_177484_a(☃ & 15, ☃x & 15, ☃xx & 15, ☃);
         if (this.field_207740_t) {
            this.func_207902_c(Heightmap.Type.MOTION_BLOCKING).func_202270_a(☃ & 15, ☃x, ☃xx & 15, ☃);
            this.func_207902_c(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).func_202270_a(☃ & 15, ☃x, ☃xx & 15, ☃);
            this.func_207902_c(Heightmap.Type.OCEAN_FLOOR).func_202270_a(☃ & 15, ☃x, ☃xx & 15, ☃);
            this.func_207902_c(Heightmap.Type.WORLD_SURFACE).func_202270_a(☃ & 15, ☃x, ☃xx & 15, ☃);
         }

         return ☃xxx;
      } else {
         return Blocks.field_201940_ji.func_176223_P();
      }
   }

   @Override
   public void func_177426_a(BlockPos var1, TileEntity var2) {
      ☃.func_174878_a(☃);
      this.field_201659_g.put(☃, ☃);
   }

   public Set<BlockPos> func_201638_j() {
      Set<BlockPos> ☃ = Sets.<BlockPos>newHashSet(this.field_201660_h.keySet());
      ☃.addAll(this.field_201659_g.keySet());
      return ☃;
   }

   @Nullable
   @Override
   public TileEntity func_175625_s(BlockPos var1) {
      return (TileEntity)this.field_201659_g.get(☃);
   }

   public Map<BlockPos, TileEntity> func_201627_k() {
      return this.field_201659_g;
   }

   public void func_201626_b(NBTTagCompound var1) {
      this.field_201662_j.add(☃);
   }

   @Override
   public void func_76612_a(Entity var1) {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_70039_c(☃);
      this.func_201626_b(☃);
   }

   public List<NBTTagCompound> func_201652_l() {
      return this.field_201662_j;
   }

   @Override
   public void func_201577_a(Biome[] var1) {
      this.field_201656_d = ☃;
   }

   @Override
   public Biome[] func_201590_e() {
      return this.field_201656_d;
   }

   public void func_177427_f(boolean var1) {
      this.field_201655_c = ☃;
   }

   public boolean func_201593_f() {
      return this.field_201655_c;
   }

   @Override
   public ChunkStatus func_201589_g() {
      return this.field_201658_f;
   }

   @Override
   public void func_201574_a(ChunkStatus var1) {
      this.field_201658_f = ☃;
      this.func_177427_f(true);
   }

   public void func_201650_c(String var1) {
      this.func_201574_a(ChunkStatus.func_202127_a(☃));
   }

   @Override
   public ChunkSection[] func_76587_i() {
      return this.field_201661_i;
   }

   @Override
   public int func_201587_a(EnumLightType var1, BlockPos var2, boolean var3) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      int ☃xxx = ☃x >> 4;
      if (☃xxx >= 0 && ☃xxx <= this.field_201661_i.length - 1) {
         ChunkSection ☃xxxx = this.field_201661_i[☃xxx];
         if (☃xxxx == Chunk.field_186036_a) {
            return this.func_177444_d(☃) ? ☃.field_77198_c : 0;
         } else if (☃ == EnumLightType.SKY) {
            return !☃ ? 0 : ☃xxxx.func_76670_c(☃, ☃x & 15, ☃xx);
         } else {
            return ☃ == EnumLightType.BLOCK ? ☃xxxx.func_76674_d(☃, ☃x & 15, ☃xx) : ☃.field_77198_c;
         }
      } else {
         return 0;
      }
   }

   @Override
   public int func_201586_a(BlockPos var1, int var2, boolean var3) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      int ☃xxx = ☃x >> 4;
      if (☃xxx >= 0 && ☃xxx <= this.field_201661_i.length - 1) {
         ChunkSection ☃xxxx = this.field_201661_i[☃xxx];
         if (☃xxxx != Chunk.field_186036_a) {
            int ☃xxxxx = ☃ ? ☃xxxx.func_76670_c(☃, ☃x & 15, ☃xx) : 0;
            ☃xxxxx -= ☃;
            int ☃xxxxxx = ☃xxxx.func_76674_d(☃, ☃x & 15, ☃xx);
            if (☃xxxxxx > ☃xxxxx) {
               ☃xxxxx = ☃xxxxxx;
            }

            return ☃xxxxx;
         } else {
            return this.func_201649_r() && ☃ < EnumLightType.SKY.field_77198_c ? EnumLightType.SKY.field_77198_c - ☃ : 0;
         }
      } else {
         return 0;
      }
   }

   @Override
   public boolean func_177444_d(BlockPos var1) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      return ☃x >= this.func_201576_a(Heightmap.Type.MOTION_BLOCKING, ☃, ☃xx);
   }

   public void func_201630_a(ChunkSection[] var1) {
      if (this.field_201661_i.length != ☃.length) {
         field_201653_a.warn("Could not set level chunk sections, array length is {} instead of {}", ☃.length, this.field_201661_i.length);
      } else {
         System.arraycopy(☃, 0, this.field_201661_i, 0, this.field_201661_i.length);
      }
   }

   public Set<Heightmap.Type> func_201634_m() {
      return this.field_201657_e.keySet();
   }

   @Nullable
   public Heightmap func_201642_a(Heightmap.Type var1) {
      return (Heightmap)this.field_201657_e.get(☃);
   }

   public void func_201643_a(Heightmap.Type var1, long[] var2) {
      this.func_207902_c(☃).func_202268_a(☃);
   }

   @Override
   public void func_201588_a(Heightmap.Type... var1) {
      for(Heightmap.Type ☃ : ☃) {
         this.func_207902_c(☃);
      }
   }

   private Heightmap func_207902_c(Heightmap.Type var1) {
      return (Heightmap)this.field_201657_e.computeIfAbsent(☃, var1x -> {
         Heightmap ☃ = new Heightmap(this, var1x);
         ☃.func_202266_a();
         return ☃;
      });
   }

   @Override
   public int func_201576_a(Heightmap.Type var1, int var2, int var3) {
      Heightmap ☃ = (Heightmap)this.field_201657_e.get(☃);
      if (☃ == null) {
         this.func_201588_a(☃);
         ☃ = (Heightmap)this.field_201657_e.get(☃);
      }

      return ☃.func_202273_a(☃ & 15, ☃ & 15) - 1;
   }

   @Override
   public ChunkPos func_76632_l() {
      return this.field_201654_b;
   }

   @Override
   public void func_177432_b(long var1) {
   }

   @Nullable
   @Override
   public StructureStart func_201585_a(String var1) {
      return (StructureStart)this.field_201666_n.get(☃);
   }

   @Override
   public void func_201584_a(String var1, StructureStart var2) {
      this.field_201666_n.put(☃, ☃);
      this.field_201655_c = true;
   }

   @Override
   public Map<String, StructureStart> func_201609_c() {
      return Collections.unmodifiableMap(this.field_201666_n);
   }

   public void func_201648_a(Map<String, StructureStart> var1) {
      this.field_201666_n.clear();
      this.field_201666_n.putAll(☃);
      this.field_201655_c = true;
   }

   @Nullable
   @Override
   public LongSet func_201578_b(String var1) {
      return (LongSet)this.field_201667_o.computeIfAbsent(☃, var0 -> new LongOpenHashSet());
   }

   @Override
   public void func_201583_a(String var1, long var2) {
      ((LongSet)this.field_201667_o.computeIfAbsent(☃, var0 -> new LongOpenHashSet())).add(☃);
      this.field_201655_c = true;
   }

   @Override
   public Map<String, LongSet> func_201604_d() {
      return Collections.unmodifiableMap(this.field_201667_o);
   }

   public void func_201641_b(Map<String, LongSet> var1) {
      this.field_201667_o.clear();
      this.field_201667_o.putAll(☃);
      this.field_201655_c = true;
   }

   @Override
   public void func_201580_a(EnumLightType var1, boolean var2, BlockPos var3, int var4) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      int ☃xxx = ☃x >> 4;
      if (☃xxx < 16 && ☃xxx >= 0) {
         if (this.field_201661_i[☃xxx] == Chunk.field_186036_a) {
            if (☃ == ☃.field_77198_c) {
               return;
            }

            this.field_201661_i[☃xxx] = new ChunkSection(☃xxx << 4, this.func_201649_r());
         }

         if (☃ == EnumLightType.SKY) {
            if (☃) {
               this.field_201661_i[☃xxx].func_76657_c(☃, ☃x & 15, ☃xx, ☃);
            }
         } else if (☃ == EnumLightType.BLOCK) {
            this.field_201661_i[☃xxx].func_76677_d(☃, ☃x & 15, ☃xx, ☃);
         }
      }
   }

   public static short func_201651_i(BlockPos var0) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();
      int ☃xxx = ☃ & 15;
      int ☃xxxx = ☃x & 15;
      int ☃xxxxx = ☃xx & 15;
      return (short)(☃xxx | ☃xxxx << 4 | ☃xxxxx << 8);
   }

   public static BlockPos func_201635_a(short var0, int var1, ChunkPos var2) {
      int ☃ = (☃ & 15) + (☃.field_77276_a << 4);
      int ☃x = (☃ >>> 4 & 15) + (☃ << 4);
      int ☃xx = (☃ >>> 8 & 15) + (☃.field_77275_b << 4);
      return new BlockPos(☃, ☃x, ☃xx);
   }

   @Override
   public void func_201594_d(BlockPos var1) {
      if (!World.func_189509_E(☃)) {
         func_205330_a(this.field_201665_m, ☃.func_177956_o() >> 4).add(func_201651_i(☃));
      }
   }

   public ShortList[] func_201645_n() {
      return this.field_201665_m;
   }

   public void func_201636_b(short var1, int var2) {
      func_205330_a(this.field_201665_m, ☃).add(☃);
   }

   public ChunkPrimerTickList<Block> func_205218_i_() {
      return this.field_201664_l;
   }

   public ChunkPrimerTickList<Fluid> func_212247_j() {
      return this.field_205333_q;
   }

   private boolean func_201649_r() {
      return true;
   }

   public UpgradeData func_201631_p() {
      return this.field_201668_p;
   }

   public void func_209215_b(long var1) {
      this.field_209217_s = ☃;
   }

   public long func_209216_m() {
      return this.field_209217_s;
   }

   @Override
   public void func_201591_a(NBTTagCompound var1) {
      this.field_201660_h.put(new BlockPos(☃.func_74762_e("x"), ☃.func_74762_e("y"), ☃.func_74762_e("z")), ☃);
   }

   public Map<BlockPos, NBTTagCompound> func_201632_q() {
      return Collections.unmodifiableMap(this.field_201660_h);
   }

   @Override
   public NBTTagCompound func_201579_g(BlockPos var1) {
      return (NBTTagCompound)this.field_201660_h.get(☃);
   }

   @Override
   public void func_177425_e(BlockPos var1) {
      this.field_201659_g.remove(☃);
      this.field_201660_h.remove(☃);
   }

   @Override
   public BitSet func_205749_a(GenerationStage.Carving var1) {
      return (BitSet)this.field_205769_s.computeIfAbsent(☃, var0 -> new BitSet(65536));
   }

   public void func_205767_a(GenerationStage.Carving var1, BitSet var2) {
      this.field_205769_s.put(☃, ☃);
   }

   public void func_205747_a(int var1) {
      this.field_205768_d.addAndGet(☃);
   }

   public boolean func_205748_B() {
      return this.field_205768_d.get() > 0;
   }

   public void func_207739_b(boolean var1) {
      this.field_207740_t = ☃;
   }
}
