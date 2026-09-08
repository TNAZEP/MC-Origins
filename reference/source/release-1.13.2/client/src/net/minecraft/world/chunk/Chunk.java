package net.minecraft.world.chunk;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.EmptyTickList;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.ITickList;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureStart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk implements IChunk {
   private static final Logger field_150817_t = LogManager.getLogger();
   public static final ChunkSection field_186036_a = null;
   private final ChunkSection[] field_76652_q = new ChunkSection[16];
   private final Biome[] field_76651_r;
   private final boolean[] field_76639_c = new boolean[256];
   private final Map<BlockPos, NBTTagCompound> field_201618_i = Maps.<BlockPos, NBTTagCompound>newHashMap();
   private boolean field_76636_d;
   private final World field_76637_e;
   private final Map<Heightmap.Type, Heightmap> field_76634_f = Maps.newEnumMap(Heightmap.Type.class);
   public final int field_76635_g;
   public final int field_76647_h;
   private boolean field_76650_s;
   private final UpgradeData field_196967_n;
   private final Map<BlockPos, TileEntity> field_150816_i = Maps.<BlockPos, TileEntity>newHashMap();
   private final ClassInheritanceMultiMap<Entity>[] field_76645_j;
   private final Map<String, StructureStart> field_201619_q = Maps.newHashMap();
   private final Map<String, LongSet> field_201620_r = Maps.newHashMap();
   private final ShortList[] field_201622_t = new ShortList[16];
   private final ITickList<Block> field_201621_s;
   private final ITickList<Fluid> field_205325_u;
   private boolean field_150815_m;
   private boolean field_76644_m;
   private long field_76641_n;
   private boolean field_76643_l;
   private int field_82912_p;
   private long field_111204_q;
   private int field_76649_t = 4096;
   private final ConcurrentLinkedQueue<BlockPos> field_177447_w = Queues.newConcurrentLinkedQueue();
   private ChunkStatus field_201616_C = ChunkStatus.EMPTY;
   private int field_201617_D;
   private final AtomicInteger field_205757_F = new AtomicInteger();
   private final ChunkPos field_212816_F;

   public Chunk(World var1, int var2, int var3, Biome[] var4) {
      this(☃, ☃, ☃, ☃, UpgradeData.field_196994_a, EmptyTickList.func_205388_a(), EmptyTickList.func_205388_a(), 0L);
   }

   public Chunk(World var1, int var2, int var3, Biome[] var4, UpgradeData var5, ITickList<Block> var6, ITickList<Fluid> var7, long var8) {
      this.field_76645_j = new ClassInheritanceMultiMap[16];
      this.field_76637_e = ☃;
      this.field_76635_g = ☃;
      this.field_76647_h = ☃;
      this.field_212816_F = new ChunkPos(☃, ☃);
      this.field_196967_n = ☃;

      for(Heightmap.Type ☃ : Heightmap.Type.values()) {
         if (☃.func_207512_c() == Heightmap.Usage.LIVE_WORLD) {
            this.field_76634_f.put(☃, new Heightmap(this, ☃));
         }
      }

      for(int ☃ = 0; ☃ < this.field_76645_j.length; ++☃) {
         this.field_76645_j[☃] = new ClassInheritanceMultiMap<>(Entity.class);
      }

      this.field_76651_r = ☃;
      this.field_201621_s = ☃;
      this.field_205325_u = ☃;
      this.field_111204_q = ☃;
   }

   public Chunk(World var1, ChunkPrimer var2, int var3, int var4) {
      this(☃, ☃, ☃, ☃.func_201590_e(), ☃.func_201631_p(), ☃.func_205218_i_(), ☃.func_212247_j(), ☃.func_209216_m());

      for(int ☃ = 0; ☃ < this.field_76652_q.length; ++☃) {
         this.field_76652_q[☃] = ☃.func_76587_i()[☃];
      }

      for(NBTTagCompound ☃ : ☃.func_201652_l()) {
         AnvilChunkLoader.func_186050_a(☃, ☃, this);
      }

      for(TileEntity ☃ : ☃.func_201627_k().values()) {
         this.func_150813_a(☃);
      }

      this.field_201618_i.putAll(☃.func_201632_q());

      for(int ☃ = 0; ☃ < ☃.func_201645_n().length; ++☃) {
         this.field_201622_t[☃] = ☃.func_201645_n()[☃];
      }

      this.func_201612_a(☃.func_201609_c());
      this.func_201606_b(☃.func_201604_d());

      for(Heightmap.Type ☃ : ☃.func_201634_m()) {
         if (☃.func_207512_c() == Heightmap.Usage.LIVE_WORLD) {
            ((Heightmap)this.field_76634_f.computeIfAbsent(☃, var1x -> new Heightmap(this, var1x))).func_202268_a(☃.func_201642_a(☃).func_202269_a());
         }
      }

      this.field_76643_l = true;
      this.func_201574_a(ChunkStatus.FULLCHUNK);
   }

   public Set<BlockPos> func_203066_o() {
      Set<BlockPos> ☃ = Sets.<BlockPos>newHashSet(this.field_201618_i.keySet());
      ☃.addAll(this.field_150816_i.keySet());
      return ☃;
   }

   public boolean func_76600_a(int var1, int var2) {
      return ☃ == this.field_76635_g && ☃ == this.field_76647_h;
   }

   @Override
   public ChunkSection[] func_76587_i() {
      return this.field_76652_q;
   }

   protected void func_76590_a() {
      for(Heightmap ☃ : this.field_76634_f.values()) {
         ☃.func_202266_a();
      }

      this.field_76643_l = true;
   }

   public void func_76603_b() {
      int ☃ = this.func_76625_h();
      this.field_82912_p = Integer.MAX_VALUE;

      for(Heightmap ☃x : this.field_76634_f.values()) {
         ☃x.func_202266_a();
      }

      for(int ☃x = 0; ☃x < 16; ++☃x) {
         for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
            if (this.field_76637_e.field_73011_w.func_191066_m()) {
               int ☃xxx = 15;
               int ☃xxxx = ☃ + 16 - 1;

               while(true) {
                  int ☃xxxxx = this.func_150808_b(☃x, ☃xxxx, ☃xx);
                  if (☃xxxxx == 0 && ☃xxx != 15) {
                     ☃xxxxx = 1;
                  }

                  ☃xxx -= ☃xxxxx;
                  if (☃xxx > 0) {
                     ChunkSection ☃xxxxx = this.field_76652_q[☃xxxx >> 4];
                     if (☃xxxxx != field_186036_a) {
                        ☃xxxxx.func_76657_c(☃x, ☃xxxx & 15, ☃xx, ☃xxx);
                        this.field_76637_e.func_175679_n(new BlockPos((this.field_76635_g << 4) + ☃x, ☃xxxx, (this.field_76647_h << 4) + ☃xx));
                     }
                  }

                  if (--☃xxxx <= 0 || ☃xxx <= 0) {
                     break;
                  }
               }
            }
         }
      }

      this.field_76643_l = true;
   }

   private void func_76595_e(int var1, int var2) {
      this.field_76639_c[☃ + ☃ * 16] = true;
      this.field_76650_s = true;
   }

   private void func_150803_c(boolean var1) {
      this.field_76637_e.field_72984_F.func_76320_a("recheckGaps");
      if (this.field_76637_e.func_205050_e(new BlockPos(this.field_76635_g * 16 + 8, 0, this.field_76647_h * 16 + 8), 16)) {
         for(int ☃ = 0; ☃ < 16; ++☃) {
            for(int ☃x = 0; ☃x < 16; ++☃x) {
               if (this.field_76639_c[☃ + ☃x * 16]) {
                  this.field_76639_c[☃ + ☃x * 16] = false;
                  int ☃xx = this.func_201576_a(Heightmap.Type.LIGHT_BLOCKING, ☃, ☃x);
                  int ☃xxx = this.field_76635_g * 16 + ☃;
                  int ☃xxxx = this.field_76647_h * 16 + ☃x;
                  int ☃xxxxx = Integer.MAX_VALUE;

                  for(EnumFacing ☃xxxxxx : EnumFacing.Plane.HORIZONTAL) {
                     ☃xxxxx = Math.min(☃xxxxx, this.field_76637_e.func_82734_g(☃xxx + ☃xxxxxx.func_82601_c(), ☃xxxx + ☃xxxxxx.func_82599_e()));
                  }

                  this.func_76599_g(☃xxx, ☃xxxx, ☃xxxxx);

                  for(EnumFacing ☃xxxxxx : EnumFacing.Plane.HORIZONTAL) {
                     this.func_76599_g(☃xxx + ☃xxxxxx.func_82601_c(), ☃xxxx + ☃xxxxxx.func_82599_e(), ☃xx);
                  }

                  if (☃) {
                     this.field_76637_e.field_72984_F.func_76319_b();
                     return;
                  }
               }
            }
         }

         this.field_76650_s = false;
      }

      this.field_76637_e.field_72984_F.func_76319_b();
   }

   private void func_76599_g(int var1, int var2, int var3) {
      int ☃ = this.field_76637_e.func_205770_a(Heightmap.Type.MOTION_BLOCKING, new BlockPos(☃, 0, ☃)).func_177956_o();
      if (☃ > ☃) {
         this.func_76609_d(☃, ☃, ☃, ☃ + 1);
      } else if (☃ < ☃) {
         this.func_76609_d(☃, ☃, ☃, ☃ + 1);
      }
   }

   private void func_76609_d(int var1, int var2, int var3, int var4) {
      if (☃ > ☃ && this.field_76637_e.func_205050_e(new BlockPos(☃, 0, ☃), 16)) {
         for(int ☃ = ☃; ☃ < ☃; ++☃) {
            this.field_76637_e.func_180500_c(EnumLightType.SKY, new BlockPos(☃, ☃, ☃));
         }

         this.field_76643_l = true;
      }
   }

   private void func_76615_h(int var1, int var2, int var3, IBlockState var4) {
      Heightmap ☃ = (Heightmap)this.field_76634_f.get(Heightmap.Type.LIGHT_BLOCKING);
      int ☃x = ☃.func_202273_a(☃ & 15, ☃ & 15) & 0xFF;
      if (☃.func_202270_a(☃, ☃, ☃, ☃)) {
         int ☃xx = ☃.func_202273_a(☃ & 15, ☃ & 15);
         int ☃xxx = this.field_76635_g * 16 + ☃;
         int ☃xxxx = this.field_76647_h * 16 + ☃;
         this.field_76637_e.func_72975_g(☃xxx, ☃xxxx, ☃xx, ☃x);
         if (this.field_76637_e.field_73011_w.func_191066_m()) {
            int ☃xxxxx = Math.min(☃x, ☃xx);
            int ☃xxxxxx = Math.max(☃x, ☃xx);
            int ☃xxxxxxx = ☃xx < ☃x ? 15 : 0;

            for(int ☃xxxxxxxx = ☃xxxxx; ☃xxxxxxxx < ☃xxxxxx; ++☃xxxxxxxx) {
               ChunkSection ☃xxxxxxxxx = this.field_76652_q[☃xxxxxxxx >> 4];
               if (☃xxxxxxxxx != field_186036_a) {
                  ☃xxxxxxxxx.func_76657_c(☃, ☃xxxxxxxx & 15, ☃, ☃xxxxxxx);
                  this.field_76637_e.func_175679_n(new BlockPos((this.field_76635_g << 4) + ☃, ☃xxxxxxxx, (this.field_76647_h << 4) + ☃));
               }
            }

            int ☃xxxxxxxx = 15;

            while(☃xx > 0 && ☃xxxxxxxx > 0) {
               int ☃xxxxxxxxx = this.func_150808_b(☃, --☃xx, ☃);
               ☃xxxxxxxxx = ☃xxxxxxxxx == 0 ? 1 : ☃xxxxxxxxx;
               ☃xxxxxxxx -= ☃xxxxxxxxx;
               ☃xxxxxxxx = Math.max(0, ☃xxxxxxxx);
               ChunkSection ☃xxxxxxxxxx = this.field_76652_q[☃xx >> 4];
               if (☃xxxxxxxxxx != field_186036_a) {
                  ☃xxxxxxxxxx.func_76657_c(☃, ☃xx & 15, ☃, ☃xxxxxxxx);
               }
            }
         }

         if (☃xx < this.field_82912_p) {
            this.field_82912_p = ☃xx;
         }

         if (this.field_76637_e.field_73011_w.func_191066_m()) {
            int ☃xx = ☃.func_202273_a(☃ & 15, ☃ & 15);
            int ☃xxx = Math.min(☃x, ☃xx);
            int ☃xxxx = Math.max(☃x, ☃xx);

            for(EnumFacing ☃xxxxx : EnumFacing.Plane.HORIZONTAL) {
               this.func_76609_d(☃xxx + ☃xxxxx.func_82601_c(), ☃xxxx + ☃xxxxx.func_82599_e(), ☃xxx, ☃xxxx);
            }

            this.func_76609_d(☃xxx, ☃xxxx, ☃xxx, ☃xxxx);
         }

         this.field_76643_l = true;
      }
   }

   private int func_150808_b(int var1, int var2, int var3) {
      return this.func_186032_a(☃, ☃, ☃).func_200016_a(this.field_76637_e, new BlockPos(☃, ☃, ☃));
   }

   @Override
   public IBlockState func_180495_p(BlockPos var1) {
      return this.func_186032_a(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   public IBlockState func_186032_a(int var1, int var2, int var3) {
      if (this.field_76637_e.func_175624_G() == WorldType.field_180272_g) {
         IBlockState ☃ = null;
         if (☃ == 60) {
            ☃ = Blocks.field_180401_cv.func_176223_P();
         }

         if (☃ == 70) {
            ☃ = ChunkGeneratorDebug.func_177461_b(☃, ☃);
         }

         return ☃ == null ? Blocks.field_150350_a.func_176223_P() : ☃;
      } else {
         try {
            if (☃ >= 0 && ☃ >> 4 < this.field_76652_q.length) {
               ChunkSection ☃ = this.field_76652_q[☃ >> 4];
               if (☃ != field_186036_a) {
                  return ☃.func_177485_a(☃ & 15, ☃ & 15, ☃ & 15);
               }
            }

            return Blocks.field_150350_a.func_176223_P();
         } catch (Throwable var7) {
            CrashReport ☃ = CrashReport.func_85055_a(var7, "Getting block state");
            CrashReportCategory ☃x = ☃.func_85058_a("Block being got");
            ☃x.func_189529_a("Location", () -> CrashReportCategory.func_184876_a(☃, ☃, ☃));
            throw new ReportedException(☃);
         }
      }
   }

   @Override
   public IFluidState func_204610_c(BlockPos var1) {
      return this.func_205751_b(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   public IFluidState func_205751_b(int var1, int var2, int var3) {
      try {
         if (☃ >= 0 && ☃ >> 4 < this.field_76652_q.length) {
            ChunkSection ☃ = this.field_76652_q[☃ >> 4];
            if (☃ != field_186036_a) {
               return ☃.func_206914_b(☃ & 15, ☃ & 15, ☃ & 15);
            }
         }

         return Fluids.field_204541_a.func_207188_f();
      } catch (Throwable var7) {
         CrashReport ☃ = CrashReport.func_85055_a(var7, "Getting fluid state");
         CrashReportCategory ☃x = ☃.func_85058_a("Block being got");
         ☃x.func_189529_a("Location", () -> CrashReportCategory.func_184876_a(☃, ☃, ☃));
         throw new ReportedException(☃);
      }
   }

   @Nullable
   @Override
   public IBlockState func_177436_a(BlockPos var1, IBlockState var2, boolean var3) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      int ☃xxx = ((Heightmap)this.field_76634_f.get(Heightmap.Type.LIGHT_BLOCKING)).func_202273_a(☃, ☃xx);
      IBlockState ☃xxxx = this.func_180495_p(☃);
      if (☃xxxx == ☃) {
         return null;
      } else {
         Block ☃ = ☃.func_177230_c();
         Block ☃x = ☃xxxx.func_177230_c();
         ChunkSection ☃xx = this.field_76652_q[☃x >> 4];
         boolean ☃xxx = false;
         if (☃xx == field_186036_a) {
            if (☃.func_196958_f()) {
               return null;
            }

            ☃xx = new ChunkSection(☃x >> 4 << 4, this.field_76637_e.field_73011_w.func_191066_m());
            this.field_76652_q[☃x >> 4] = ☃xx;
            ☃xxx = ☃x >= ☃xxx;
         }

         ☃xx.func_177484_a(☃, ☃x & 15, ☃xx, ☃);
         ((Heightmap)this.field_76634_f.get(Heightmap.Type.MOTION_BLOCKING)).func_202270_a(☃, ☃x, ☃xx, ☃);
         ((Heightmap)this.field_76634_f.get(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES)).func_202270_a(☃, ☃x, ☃xx, ☃);
         ((Heightmap)this.field_76634_f.get(Heightmap.Type.OCEAN_FLOOR)).func_202270_a(☃, ☃x, ☃xx, ☃);
         ((Heightmap)this.field_76634_f.get(Heightmap.Type.WORLD_SURFACE)).func_202270_a(☃, ☃x, ☃xx, ☃);
         if (!this.field_76637_e.field_72995_K) {
            ☃xxxx.func_196947_b(this.field_76637_e, ☃, ☃, ☃);
         } else if (☃x != ☃ && ☃x instanceof ITileEntityProvider) {
            this.field_76637_e.func_175713_t(☃);
         }

         if (☃xx.func_177485_a(☃, ☃x & 15, ☃xx).func_177230_c() != ☃) {
            return null;
         } else {
            if (☃xxx) {
               this.func_76603_b();
            } else {
               int ☃ = ☃.func_200016_a(this.field_76637_e, ☃);
               int ☃x = ☃xxxx.func_200016_a(this.field_76637_e, ☃);
               this.func_76615_h(☃, ☃x, ☃xx, ☃);
               if (☃ != ☃x && (☃ < ☃x || this.func_177413_a(EnumLightType.SKY, ☃) > 0 || this.func_177413_a(EnumLightType.BLOCK, ☃) > 0)) {
                  this.func_76595_e(☃, ☃xx);
               }
            }

            if (☃x instanceof ITileEntityProvider) {
               TileEntity ☃ = this.func_177424_a(☃, Chunk.EnumCreateEntityType.CHECK);
               if (☃ != null) {
                  ☃.func_145836_u();
               }
            }

            if (!this.field_76637_e.field_72995_K) {
               ☃.func_196945_a(this.field_76637_e, ☃, ☃xxxx);
            }

            if (☃ instanceof ITileEntityProvider) {
               TileEntity ☃ = this.func_177424_a(☃, Chunk.EnumCreateEntityType.CHECK);
               if (☃ == null) {
                  ☃ = ((ITileEntityProvider)☃).func_196283_a_(this.field_76637_e);
                  this.field_76637_e.func_175690_a(☃, ☃);
               } else {
                  ☃.func_145836_u();
               }
            }

            this.field_76643_l = true;
            return ☃xxxx;
         }
      }
   }

   public int func_177413_a(EnumLightType var1, BlockPos var2) {
      return this.func_201587_a(☃, ☃, this.field_76637_e.func_201675_m().func_191066_m());
   }

   @Override
   public int func_201587_a(EnumLightType var1, BlockPos var2, boolean var3) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      int ☃xxx = ☃x >> 4;
      if (☃xxx >= 0 && ☃xxx <= this.field_76652_q.length - 1) {
         ChunkSection ☃xxxx = this.field_76652_q[☃xxx];
         if (☃xxxx == field_186036_a) {
            return this.func_177444_d(☃) ? ☃.field_77198_c : 0;
         } else if (☃ == EnumLightType.SKY) {
            return !☃ ? 0 : ☃xxxx.func_76670_c(☃, ☃x & 15, ☃xx);
         } else {
            return ☃ == EnumLightType.BLOCK ? ☃xxxx.func_76674_d(☃, ☃x & 15, ☃xx) : ☃.field_77198_c;
         }
      } else {
         return (☃ != EnumLightType.SKY || !☃) && ☃ != EnumLightType.BLOCK ? 0 : ☃.field_77198_c;
      }
   }

   public void func_177431_a(EnumLightType var1, BlockPos var2, int var3) {
      this.func_201580_a(☃, this.field_76637_e.func_201675_m().func_191066_m(), ☃, ☃);
   }

   @Override
   public void func_201580_a(EnumLightType var1, boolean var2, BlockPos var3, int var4) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      int ☃xxx = ☃x >> 4;
      if (☃xxx < 16 && ☃xxx >= 0) {
         ChunkSection ☃xxxx = this.field_76652_q[☃xxx];
         if (☃xxxx == field_186036_a) {
            if (☃ == ☃.field_77198_c) {
               return;
            }

            ☃xxxx = new ChunkSection(☃xxx << 4, ☃);
            this.field_76652_q[☃xxx] = ☃xxxx;
            this.func_76603_b();
         }

         if (☃ == EnumLightType.SKY) {
            if (this.field_76637_e.field_73011_w.func_191066_m()) {
               ☃xxxx.func_76657_c(☃, ☃x & 15, ☃xx, ☃);
            }
         } else if (☃ == EnumLightType.BLOCK) {
            ☃xxxx.func_76677_d(☃, ☃x & 15, ☃xx, ☃);
         }

         this.field_76643_l = true;
      }
   }

   public int func_177443_a(BlockPos var1, int var2) {
      return this.func_201586_a(☃, ☃, this.field_76637_e.func_201675_m().func_191066_m());
   }

   @Override
   public int func_201586_a(BlockPos var1, int var2, boolean var3) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      int ☃xxx = ☃x >> 4;
      if (☃xxx >= 0 && ☃xxx <= this.field_76652_q.length - 1) {
         ChunkSection ☃xxxx = this.field_76652_q[☃xxx];
         if (☃xxxx != field_186036_a) {
            int ☃xxxxx = ☃ ? ☃xxxx.func_76670_c(☃, ☃x & 15, ☃xx) : 0;
            ☃xxxxx -= ☃;
            int ☃xxxxxx = ☃xxxx.func_76674_d(☃, ☃x & 15, ☃xx);
            if (☃xxxxxx > ☃xxxxx) {
               ☃xxxxx = ☃xxxxxx;
            }

            return ☃xxxxx;
         } else {
            return ☃ && ☃ < EnumLightType.SKY.field_77198_c ? EnumLightType.SKY.field_77198_c - ☃ : 0;
         }
      } else {
         return 0;
      }
   }

   @Override
   public void func_76612_a(Entity var1) {
      this.field_76644_m = true;
      int ☃ = MathHelper.func_76128_c(☃.field_70165_t / 16.0);
      int ☃x = MathHelper.func_76128_c(☃.field_70161_v / 16.0);
      if (☃ != this.field_76635_g || ☃x != this.field_76647_h) {
         field_150817_t.warn("Wrong location! ({}, {}) should be ({}, {}), {}", ☃, ☃x, this.field_76635_g, this.field_76647_h, ☃);
         ☃.func_70106_y();
      }

      int ☃ = MathHelper.func_76128_c(☃.field_70163_u / 16.0);
      if (☃ < 0) {
         ☃ = 0;
      }

      if (☃ >= this.field_76645_j.length) {
         ☃ = this.field_76645_j.length - 1;
      }

      ☃.field_70175_ag = true;
      ☃.field_70176_ah = this.field_76635_g;
      ☃.field_70162_ai = ☃;
      ☃.field_70164_aj = this.field_76647_h;
      this.field_76645_j[☃].add(☃);
   }

   public void func_201607_a(Heightmap.Type var1, long[] var2) {
      ((Heightmap)this.field_76634_f.get(☃)).func_202268_a(☃);
   }

   public void func_76622_b(Entity var1) {
      this.func_76608_a(☃, ☃.field_70162_ai);
   }

   public void func_76608_a(Entity var1, int var2) {
      if (☃ < 0) {
         ☃ = 0;
      }

      if (☃ >= this.field_76645_j.length) {
         ☃ = this.field_76645_j.length - 1;
      }

      this.field_76645_j[☃].remove(☃);
   }

   @Override
   public boolean func_177444_d(BlockPos var1) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p() & 15;
      return ☃x >= ((Heightmap)this.field_76634_f.get(Heightmap.Type.LIGHT_BLOCKING)).func_202273_a(☃, ☃xx);
   }

   @Override
   public int func_201576_a(Heightmap.Type var1, int var2, int var3) {
      return ((Heightmap)this.field_76634_f.get(☃)).func_202273_a(☃ & 15, ☃ & 15) - 1;
   }

   @Nullable
   private TileEntity func_177422_i(BlockPos var1) {
      IBlockState ☃ = this.func_180495_p(☃);
      Block ☃x = ☃.func_177230_c();
      return !☃x.func_149716_u() ? null : ((ITileEntityProvider)☃x).func_196283_a_(this.field_76637_e);
   }

   @Nullable
   @Override
   public TileEntity func_175625_s(BlockPos var1) {
      return this.func_177424_a(☃, Chunk.EnumCreateEntityType.CHECK);
   }

   @Nullable
   public TileEntity func_177424_a(BlockPos var1, Chunk.EnumCreateEntityType var2) {
      TileEntity ☃ = (TileEntity)this.field_150816_i.get(☃);
      if (☃ == null) {
         NBTTagCompound ☃x = (NBTTagCompound)this.field_201618_i.remove(☃);
         if (☃x != null) {
            TileEntity ☃xx = this.func_212815_a(☃, ☃x);
            if (☃xx != null) {
               return ☃xx;
            }
         }
      }

      if (☃ == null) {
         if (☃ == Chunk.EnumCreateEntityType.IMMEDIATE) {
            ☃ = this.func_177422_i(☃);
            this.field_76637_e.func_175690_a(☃, ☃);
         } else if (☃ == Chunk.EnumCreateEntityType.QUEUED) {
            this.field_177447_w.add(☃);
         }
      } else if (☃.func_145837_r()) {
         this.field_150816_i.remove(☃);
         return null;
      }

      return ☃;
   }

   public void func_150813_a(TileEntity var1) {
      this.func_177426_a(☃.func_174877_v(), ☃);
      if (this.field_76636_d) {
         this.field_76637_e.func_175700_a(☃);
      }
   }

   @Override
   public void func_177426_a(BlockPos var1, TileEntity var2) {
      ☃.func_145834_a(this.field_76637_e);
      ☃.func_174878_a(☃);
      if (this.func_180495_p(☃).func_177230_c() instanceof ITileEntityProvider) {
         if (this.field_150816_i.containsKey(☃)) {
            ((TileEntity)this.field_150816_i.get(☃)).func_145843_s();
         }

         ☃.func_145829_t();
         this.field_150816_i.put(☃.func_185334_h(), ☃);
      }
   }

   @Override
   public void func_201591_a(NBTTagCompound var1) {
      this.field_201618_i.put(new BlockPos(☃.func_74762_e("x"), ☃.func_74762_e("y"), ☃.func_74762_e("z")), ☃);
   }

   @Override
   public void func_177425_e(BlockPos var1) {
      if (this.field_76636_d) {
         TileEntity ☃ = (TileEntity)this.field_150816_i.remove(☃);
         if (☃ != null) {
            ☃.func_145843_s();
         }
      }
   }

   public void func_76631_c() {
      this.field_76636_d = true;
      this.field_76637_e.func_147448_a(this.field_150816_i.values());

      for(ClassInheritanceMultiMap<Entity> ☃ : this.field_76645_j) {
         this.field_76637_e.func_212420_a(☃.stream().filter(var0 -> !(var0 instanceof EntityPlayer)));
      }
   }

   public void func_76623_d() {
      this.field_76636_d = false;

      for(TileEntity ☃ : this.field_150816_i.values()) {
         this.field_76637_e.func_147457_a(☃);
      }

      for(ClassInheritanceMultiMap<Entity> ☃ : this.field_76645_j) {
         this.field_76637_e.func_175681_c(☃);
      }
   }

   public void func_76630_e() {
      this.field_76643_l = true;
   }

   public void func_177414_a(@Nullable Entity var1, AxisAlignedBB var2, List<Entity> var3, Predicate<? super Entity> var4) {
      int ☃ = MathHelper.func_76128_c((☃.field_72338_b - 2.0) / 16.0);
      int ☃x = MathHelper.func_76128_c((☃.field_72337_e + 2.0) / 16.0);
      ☃ = MathHelper.func_76125_a(☃, 0, this.field_76645_j.length - 1);
      ☃x = MathHelper.func_76125_a(☃x, 0, this.field_76645_j.length - 1);

      for(int ☃xx = ☃; ☃xx <= ☃x; ++☃xx) {
         if (!this.field_76645_j[☃xx].isEmpty()) {
            for(Entity ☃xxx : this.field_76645_j[☃xx]) {
               if (☃xxx.func_174813_aQ().func_72326_a(☃) && ☃xxx != ☃) {
                  if (☃ == null || ☃.test(☃xxx)) {
                     ☃.add(☃xxx);
                  }

                  Entity[] ☃xxxx = ☃xxx.func_70021_al();
                  if (☃xxxx != null) {
                     for(Entity ☃xxxxx : ☃xxxx) {
                        if (☃xxxxx != ☃ && ☃xxxxx.func_174813_aQ().func_72326_a(☃) && (☃ == null || ☃.test(☃xxxxx))) {
                           ☃.add(☃xxxxx);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public <T extends Entity> void func_177430_a(Class<? extends T> var1, AxisAlignedBB var2, List<T> var3, @Nullable Predicate<? super T> var4) {
      int ☃ = MathHelper.func_76128_c((☃.field_72338_b - 2.0) / 16.0);
      int ☃x = MathHelper.func_76128_c((☃.field_72337_e + 2.0) / 16.0);
      ☃ = MathHelper.func_76125_a(☃, 0, this.field_76645_j.length - 1);
      ☃x = MathHelper.func_76125_a(☃x, 0, this.field_76645_j.length - 1);

      for(int ☃xx = ☃; ☃xx <= ☃x; ++☃xx) {
         for(T ☃xxx : this.field_76645_j[☃xx].func_180215_b(☃)) {
            if (☃xxx.func_174813_aQ().func_72326_a(☃) && (☃ == null || ☃.test(☃xxx))) {
               ☃.add(☃xxx);
            }
         }
      }
   }

   public boolean func_76601_a(boolean var1) {
      if (☃) {
         if (this.field_76644_m && this.field_76637_e.func_82737_E() != this.field_76641_n || this.field_76643_l) {
            return true;
         }
      } else if (this.field_76644_m && this.field_76637_e.func_82737_E() >= this.field_76641_n + 600L) {
         return true;
      }

      return this.field_76643_l;
   }

   public boolean func_76621_g() {
      return false;
   }

   public void func_150804_b(boolean var1) {
      if (this.field_76650_s && this.field_76637_e.field_73011_w.func_191066_m() && !☃) {
         this.func_150803_c(this.field_76637_e.field_72995_K);
      }

      this.field_150815_m = true;

      while(!this.field_177447_w.isEmpty()) {
         BlockPos ☃ = (BlockPos)this.field_177447_w.poll();
         if (this.func_177424_a(☃, Chunk.EnumCreateEntityType.CHECK) == null && this.func_180495_p(☃).func_177230_c().func_149716_u()) {
            TileEntity ☃x = this.func_177422_i(☃);
            this.field_76637_e.func_175690_a(☃, ☃x);
            this.field_76637_e.func_175704_b(☃, ☃);
         }
      }
   }

   public boolean func_150802_k() {
      return this.field_201616_C.func_209003_a(ChunkStatus.POSTPROCESSED);
   }

   public boolean func_186035_j() {
      return this.field_150815_m;
   }

   @Override
   public ChunkPos func_76632_l() {
      return this.field_212816_F;
   }

   public boolean func_76606_c(int var1, int var2) {
      if (☃ < 0) {
         ☃ = 0;
      }

      if (☃ >= 256) {
         ☃ = 255;
      }

      for(int ☃ = ☃; ☃ <= ☃; ☃ += 16) {
         ChunkSection ☃x = this.field_76652_q[☃ >> 4];
         if (☃x != field_186036_a && !☃x.func_76663_a()) {
            return false;
         }
      }

      return true;
   }

   public void func_76602_a(ChunkSection[] var1) {
      if (this.field_76652_q.length != ☃.length) {
         field_150817_t.warn("Could not set level chunk sections, array length is {} instead of {}", ☃.length, this.field_76652_q.length);
      } else {
         System.arraycopy(☃, 0, this.field_76652_q, 0, this.field_76652_q.length);
      }
   }

   public void func_186033_a(PacketBuffer var1, int var2, boolean var3) {
      if (☃) {
         this.field_150816_i.clear();
      } else {
         Iterator<BlockPos> ☃ = this.field_150816_i.keySet().iterator();

         while(☃.hasNext()) {
            BlockPos ☃x = (BlockPos)☃.next();
            int ☃xx = ☃x.func_177956_o() >> 4;
            if ((☃ & 1 << ☃xx) != 0) {
               ☃.remove();
            }
         }
      }

      boolean ☃ = this.field_76637_e.field_73011_w.func_191066_m();

      for(int ☃x = 0; ☃x < this.field_76652_q.length; ++☃x) {
         ChunkSection ☃xx = this.field_76652_q[☃x];
         if ((☃ & 1 << ☃x) == 0) {
            if (☃ && ☃xx != field_186036_a) {
               this.field_76652_q[☃x] = field_186036_a;
            }
         } else {
            if (☃xx == field_186036_a) {
               ☃xx = new ChunkSection(☃x << 4, ☃);
               this.field_76652_q[☃x] = ☃xx;
            }

            ☃xx.func_186049_g().func_186010_a(☃);
            ☃.readBytes(☃xx.func_76661_k().func_177481_a());
            if (☃) {
               ☃.readBytes(☃xx.func_76671_l().func_177481_a());
            }
         }
      }

      if (☃) {
         for(int ☃x = 0; ☃x < this.field_76651_r.length; ++☃x) {
            this.field_76651_r[☃x] = IRegistry.field_212624_m.func_148754_a(☃.readInt());
         }
      }

      for(int ☃x = 0; ☃x < this.field_76652_q.length; ++☃x) {
         if (this.field_76652_q[☃x] != field_186036_a && (☃ & 1 << ☃x) != 0) {
            this.field_76652_q[☃x].func_76672_e();
         }
      }

      this.func_76590_a();

      for(TileEntity ☃x : this.field_150816_i.values()) {
         ☃x.func_145836_u();
      }
   }

   public Biome func_201600_k(BlockPos var1) {
      int ☃ = ☃.func_177958_n() & 15;
      int ☃x = ☃.func_177952_p() & 15;
      return this.field_76651_r[☃x << 4 | ☃];
   }

   @Override
   public Biome[] func_201590_e() {
      return this.field_76651_r;
   }

   public void func_76613_n() {
      this.field_76649_t = 0;
   }

   public void func_76594_o() {
      if (this.field_76649_t < 4096) {
         BlockPos ☃ = new BlockPos(this.field_76635_g << 4, 0, this.field_76647_h << 4);

         for(int ☃x = 0; ☃x < 8; ++☃x) {
            if (this.field_76649_t >= 4096) {
               return;
            }

            int ☃xx = this.field_76649_t % 16;
            int ☃xxx = this.field_76649_t / 16 % 16;
            int ☃xxxx = this.field_76649_t / 256;
            ++this.field_76649_t;

            for(int ☃xxxxx = 0; ☃xxxxx < 16; ++☃xxxxx) {
               BlockPos ☃xxxxxx = ☃.func_177982_a(☃xxx, (☃xx << 4) + ☃xxxxx, ☃xxxx);
               boolean ☃xxxxxxx = ☃xxxxx == 0 || ☃xxxxx == 15 || ☃xxx == 0 || ☃xxx == 15 || ☃xxxx == 0 || ☃xxxx == 15;
               if (this.field_76652_q[☃xx] == field_186036_a && ☃xxxxxxx
                  || this.field_76652_q[☃xx] != field_186036_a && this.field_76652_q[☃xx].func_177485_a(☃xxx, ☃xxxxx, ☃xxxx).func_196958_f()) {
                  for(EnumFacing ☃xxxxxxxx : EnumFacing.values()) {
                     BlockPos ☃xxxxxxxxx = ☃xxxxxx.func_177972_a(☃xxxxxxxx);
                     if (this.field_76637_e.func_180495_p(☃xxxxxxxxx).func_185906_d() > 0) {
                        this.field_76637_e.func_175664_x(☃xxxxxxxxx);
                     }
                  }

                  this.field_76637_e.func_175664_x(☃xxxxxx);
               }
            }
         }
      }
   }

   public boolean func_177410_o() {
      return this.field_76636_d;
   }

   public void func_177417_c(boolean var1) {
      this.field_76636_d = ☃;
   }

   public World func_177412_p() {
      return this.field_76637_e;
   }

   public Set<Heightmap.Type> func_201615_v() {
      return this.field_76634_f.keySet();
   }

   public Heightmap func_201608_a(Heightmap.Type var1) {
      return (Heightmap)this.field_76634_f.get(☃);
   }

   public Map<BlockPos, TileEntity> func_177434_r() {
      return this.field_150816_i;
   }

   public ClassInheritanceMultiMap<Entity>[] func_177429_s() {
      return this.field_76645_j;
   }

   @Override
   public NBTTagCompound func_201579_g(BlockPos var1) {
      return (NBTTagCompound)this.field_201618_i.get(☃);
   }

   @Override
   public ITickList<Block> func_205218_i_() {
      return this.field_201621_s;
   }

   @Override
   public ITickList<Fluid> func_212247_j() {
      return this.field_205325_u;
   }

   @Override
   public BitSet func_205749_a(GenerationStage.Carving var1) {
      throw new RuntimeException("Not yet implemented");
   }

   public void func_177427_f(boolean var1) {
      this.field_76643_l = ☃;
   }

   public void func_177409_g(boolean var1) {
      this.field_76644_m = ☃;
   }

   @Override
   public void func_177432_b(long var1) {
      this.field_76641_n = ☃;
   }

   @Nullable
   @Override
   public StructureStart func_201585_a(String var1) {
      return (StructureStart)this.field_201619_q.get(☃);
   }

   @Override
   public void func_201584_a(String var1, StructureStart var2) {
      this.field_201619_q.put(☃, ☃);
   }

   @Override
   public Map<String, StructureStart> func_201609_c() {
      return this.field_201619_q;
   }

   public void func_201612_a(Map<String, StructureStart> var1) {
      this.field_201619_q.clear();
      this.field_201619_q.putAll(☃);
   }

   @Nullable
   @Override
   public LongSet func_201578_b(String var1) {
      return (LongSet)this.field_201620_r.computeIfAbsent(☃, var0 -> new LongOpenHashSet());
   }

   @Override
   public void func_201583_a(String var1, long var2) {
      ((LongSet)this.field_201620_r.computeIfAbsent(☃, var0 -> new LongOpenHashSet())).add(☃);
   }

   @Override
   public Map<String, LongSet> func_201604_d() {
      return this.field_201620_r;
   }

   public void func_201606_b(Map<String, LongSet> var1) {
      this.field_201620_r.clear();
      this.field_201620_r.putAll(☃);
   }

   public int func_177442_v() {
      return this.field_82912_p;
   }

   public long func_177416_w() {
      return this.field_111204_q;
   }

   public void func_177415_c(long var1) {
      this.field_111204_q = ☃;
   }

   public void func_201595_A() {
      if (!this.field_201616_C.func_209003_a(ChunkStatus.POSTPROCESSED) && this.field_201617_D == 8) {
         ChunkPos ☃ = this.func_76632_l();

         for(int ☃x = 0; ☃x < this.field_201622_t.length; ++☃x) {
            if (this.field_201622_t[☃x] != null) {
               for(Short ☃xx : this.field_201622_t[☃x]) {
                  BlockPos ☃xxx = ChunkPrimer.func_201635_a(☃xx, ☃x, ☃);
                  IBlockState ☃xxxx = this.field_76637_e.func_180495_p(☃xxx);
                  IBlockState ☃xxxxx = Block.func_199770_b(☃xxxx, this.field_76637_e, ☃xxx);
                  this.field_76637_e.func_180501_a(☃xxx, ☃xxxxx, 20);
               }

               this.field_201622_t[☃x].clear();
            }
         }

         if (this.field_201621_s instanceof ChunkPrimerTickList) {
            ((ChunkPrimerTickList)this.field_201621_s)
               .func_205381_a(this.field_76637_e.func_205220_G_(), var1x -> this.field_76637_e.func_180495_p(var1x).func_177230_c());
         }

         if (this.field_205325_u instanceof ChunkPrimerTickList) {
            ((ChunkPrimerTickList)this.field_205325_u)
               .func_205381_a(this.field_76637_e.func_205219_F_(), var1x -> this.field_76637_e.func_204610_c(var1x).func_206886_c());
         }

         for(BlockPos ☃x : new HashSet(this.field_201618_i.keySet())) {
            this.func_175625_s(☃x);
         }

         this.field_201618_i.clear();
         this.func_201574_a(ChunkStatus.POSTPROCESSED);
         this.field_196967_n.func_196990_a(this);
      }
   }

   @Nullable
   private TileEntity func_212815_a(BlockPos var1, NBTTagCompound var2) {
      TileEntity ☃;
      if ("DUMMY".equals(☃.func_74779_i("id"))) {
         Block ☃x = this.func_180495_p(☃).func_177230_c();
         if (☃x instanceof ITileEntityProvider) {
            ☃ = ((ITileEntityProvider)☃x).func_196283_a_(this.field_76637_e);
         } else {
            ☃ = null;
            field_150817_t.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", ☃, this.func_180495_p(☃));
         }
      } else {
         ☃ = TileEntity.func_203403_c(☃);
      }

      if (☃ != null) {
         ☃.func_174878_a(☃);
         this.func_150813_a(☃);
      } else {
         field_150817_t.warn("Tried to load a block entity for block {} but failed at location {}", this.func_180495_p(☃), ☃);
      }

      return ☃;
   }

   public UpgradeData func_196966_y() {
      return this.field_196967_n;
   }

   public ShortList[] func_201614_D() {
      return this.field_201622_t;
   }

   public void func_201610_a(short var1, int var2) {
      ChunkPrimer.func_205330_a(this.field_201622_t, ☃).add(☃);
   }

   @Override
   public ChunkStatus func_201589_g() {
      return this.field_201616_C;
   }

   @Override
   public void func_201574_a(ChunkStatus var1) {
      this.field_201616_C = ☃;
   }

   public void func_201613_c(String var1) {
      this.func_201574_a(ChunkStatus.func_202127_a(☃));
   }

   public void func_201605_F() {
      ++this.field_201617_D;
      if (this.field_201617_D > 8) {
         throw new RuntimeException("Error while adding chunk to cache. Too many neighbors");
      } else {
         if (this.func_201596_H()) {
            ((IThreadListener)this.field_76637_e).func_152344_a(this::func_201595_A);
         }
      }
   }

   public void func_201611_G() {
      --this.field_201617_D;
      if (this.field_201617_D < 0) {
         throw new RuntimeException("Error while removing chunk from cache. Not enough neighbors");
      }
   }

   public boolean func_201596_H() {
      return this.field_201617_D == 8;
   }

   public static enum EnumCreateEntityType {
      IMMEDIATE,
      QUEUED,
      CHECK;
   }
}
