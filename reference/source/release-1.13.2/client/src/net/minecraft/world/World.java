package net.minecraft.world;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.particles.IParticleData;
import net.minecraft.pathfinding.PathWorldListener;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SessionLockException;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class World implements IEntityReader, IWorld, IWorldReader, AutoCloseable {
   protected static final Logger field_195596_d = LogManager.getLogger();
   private static final EnumFacing[] field_200007_a = EnumFacing.values();
   private int field_181546_a = 63;
   public final List<Entity> field_72996_f = Lists.<Entity>newArrayList();
   protected final List<Entity> field_72997_g = Lists.<Entity>newArrayList();
   public final List<TileEntity> field_147482_g = Lists.<TileEntity>newArrayList();
   public final List<TileEntity> field_175730_i = Lists.<TileEntity>newArrayList();
   private final List<TileEntity> field_147484_a = Lists.<TileEntity>newArrayList();
   private final List<TileEntity> field_147483_b = Lists.<TileEntity>newArrayList();
   public final List<EntityPlayer> field_73010_i = Lists.<EntityPlayer>newArrayList();
   public final List<Entity> field_73007_j = Lists.<Entity>newArrayList();
   protected final IntHashMap<Entity> field_175729_l = new IntHashMap<>();
   private final long field_73001_c = 16777215L;
   private int field_73008_k;
   protected int field_73005_l = new Random().nextInt();
   protected final int field_73006_m = 1013904223;
   protected float field_73003_n;
   protected float field_73004_o;
   protected float field_73018_p;
   protected float field_73017_q;
   private int field_73016_r;
   public final Random field_73012_v = new Random();
   public final Dimension field_73011_w;
   protected PathWorldListener field_184152_t = new PathWorldListener();
   protected List<IWorldEventListener> field_73021_x = Lists.<IWorldEventListener>newArrayList(this.field_184152_t);
   protected IChunkProvider field_73020_y;
   protected final ISaveHandler field_73019_z;
   protected WorldInfo field_72986_A;
   @Nullable
   private final WorldSavedDataStorage field_72988_C;
   protected VillageCollection field_72982_D;
   public final Profiler field_72984_F;
   public final boolean field_72995_K;
   protected boolean field_72985_G = true;
   protected boolean field_72992_H = true;
   private boolean field_147481_N;
   private final WorldBorder field_175728_M;
   int[] field_72994_J = new int[32768];

   protected World(ISaveHandler var1, @Nullable WorldSavedDataStorage var2, WorldInfo var3, Dimension var4, Profiler var5, boolean var6) {
      this.field_73019_z = ☃;
      this.field_72988_C = ☃;
      this.field_72984_F = ☃;
      this.field_72986_A = ☃;
      this.field_73011_w = ☃;
      this.field_72995_K = ☃;
      this.field_175728_M = ☃.func_177501_r();
   }

   @Override
   public Biome func_180494_b(BlockPos var1) {
      if (this.func_175667_e(☃)) {
         Chunk ☃ = this.func_175726_f(☃);

         try {
            return ☃.func_201600_k(☃);
         } catch (Throwable var6) {
            CrashReport ☃x = CrashReport.func_85055_a(var6, "Getting biome");
            CrashReportCategory ☃xx = ☃x.func_85058_a("Coordinates of biome request");
            ☃xx.func_189529_a("Location", () -> CrashReportCategory.func_180522_a(☃));
            throw new ReportedException(☃x);
         }
      } else {
         return this.field_73020_y.func_201711_g().func_202090_b().func_180300_a(☃, Biomes.field_76772_c);
      }
   }

   protected abstract IChunkProvider func_72970_h();

   public void func_72963_a(WorldSettings var1) {
      this.field_72986_A.func_76091_d(true);
   }

   @Override
   public boolean func_201670_d() {
      return this.field_72995_K;
   }

   @Nullable
   public MinecraftServer func_73046_m() {
      return null;
   }

   public void func_72974_f() {
      this.func_175652_B(new BlockPos(8, 64, 8));
   }

   public IBlockState func_184141_c(BlockPos var1) {
      BlockPos ☃ = new BlockPos(☃.func_177958_n(), this.func_181545_F(), ☃.func_177952_p());

      while(!this.func_175623_d(☃.func_177984_a())) {
         ☃ = ☃.func_177984_a();
      }

      return this.func_180495_p(☃);
   }

   public static boolean func_175701_a(BlockPos var0) {
      return !func_189509_E(☃)
         && ☃.func_177958_n() >= -30000000
         && ☃.func_177952_p() >= -30000000
         && ☃.func_177958_n() < 30000000
         && ☃.func_177952_p() < 30000000;
   }

   public static boolean func_189509_E(BlockPos var0) {
      return ☃.func_177956_o() < 0 || ☃.func_177956_o() >= 256;
   }

   @Override
   public boolean func_175623_d(BlockPos var1) {
      return this.func_180495_p(☃).func_196958_f();
   }

   public Chunk func_175726_f(BlockPos var1) {
      return this.func_72964_e(☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4);
   }

   public Chunk func_72964_e(int var1, int var2) {
      Chunk ☃ = this.field_73020_y.func_186025_d(☃, ☃, true, true);
      if (☃ == null) {
         throw new IllegalStateException("Should always be able to create a chunk!");
      } else {
         return ☃;
      }
   }

   @Override
   public boolean func_180501_a(BlockPos var1, IBlockState var2, int var3) {
      if (func_189509_E(☃)) {
         return false;
      } else if (!this.field_72995_K && this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
         return false;
      } else {
         Chunk ☃ = this.func_175726_f(☃);
         Block ☃x = ☃.func_177230_c();
         IBlockState ☃xx = ☃.func_177436_a(☃, ☃, (☃ & 64) != 0);
         if (☃xx == null) {
            return false;
         } else {
            IBlockState ☃ = this.func_180495_p(☃);
            if (☃.func_200016_a(this, ☃) != ☃xx.func_200016_a(this, ☃) || ☃.func_185906_d() != ☃xx.func_185906_d()) {
               this.field_72984_F.func_76320_a("checkLight");
               this.func_175664_x(☃);
               this.field_72984_F.func_76319_b();
            }

            if (☃ == ☃) {
               if (☃xx != ☃) {
                  this.func_175704_b(☃, ☃);
               }

               if ((☃ & 2) != 0 && (!this.field_72995_K || (☃ & 4) == 0) && ☃.func_150802_k()) {
                  this.func_184138_a(☃, ☃xx, ☃, ☃);
               }

               if (!this.field_72995_K && (☃ & 1) != 0) {
                  this.func_195592_c(☃, ☃xx.func_177230_c());
                  if (☃.func_185912_n()) {
                     this.func_175666_e(☃, ☃x);
                  }
               }

               if ((☃ & 16) == 0) {
                  int ☃ = ☃ & -2;
                  ☃xx.func_196948_b(this, ☃, ☃);
                  ☃.func_196946_a(this, ☃, ☃);
                  ☃.func_196948_b(this, ☃, ☃);
               }
            }

            return true;
         }
      }
   }

   @Override
   public boolean func_175698_g(BlockPos var1) {
      IFluidState ☃ = this.func_204610_c(☃);
      return this.func_180501_a(☃, ☃.func_206883_i(), 3);
   }

   @Override
   public boolean func_175655_b(BlockPos var1, boolean var2) {
      IBlockState ☃ = this.func_180495_p(☃);
      if (☃.func_196958_f()) {
         return false;
      } else {
         IFluidState ☃ = this.func_204610_c(☃);
         this.func_175718_b(2001, ☃, Block.func_196246_j(☃));
         if (☃) {
            ☃.func_196949_c(this, ☃, 0);
         }

         return this.func_180501_a(☃, ☃.func_206883_i(), 3);
      }
   }

   public boolean func_175656_a(BlockPos var1, IBlockState var2) {
      return this.func_180501_a(☃, ☃, 3);
   }

   public void func_184138_a(BlockPos var1, IBlockState var2, IBlockState var3, int var4) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_184376_a(this, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public void func_195592_c(BlockPos var1, Block var2) {
      if (this.field_72986_A.func_76067_t() != WorldType.field_180272_g) {
         this.func_195593_d(☃, ☃);
      }
   }

   public void func_72975_g(int var1, int var2, int var3, int var4) {
      if (☃ > ☃) {
         int ☃ = ☃;
         ☃ = ☃;
         ☃ = ☃;
      }

      if (this.field_73011_w.func_191066_m()) {
         for(int ☃ = ☃; ☃ <= ☃; ++☃) {
            this.func_180500_c(EnumLightType.SKY, new BlockPos(☃, ☃, ☃));
         }
      }

      this.func_147458_c(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public void func_175704_b(BlockPos var1, BlockPos var2) {
      this.func_147458_c(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   public void func_147458_c(int var1, int var2, int var3, int var4, int var5, int var6) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_147585_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public void func_195593_d(BlockPos var1, Block var2) {
      this.func_190524_a(☃.func_177976_e(), ☃, ☃);
      this.func_190524_a(☃.func_177974_f(), ☃, ☃);
      this.func_190524_a(☃.func_177977_b(), ☃, ☃);
      this.func_190524_a(☃.func_177984_a(), ☃, ☃);
      this.func_190524_a(☃.func_177978_c(), ☃, ☃);
      this.func_190524_a(☃.func_177968_d(), ☃, ☃);
   }

   public void func_175695_a(BlockPos var1, Block var2, EnumFacing var3) {
      if (☃ != EnumFacing.WEST) {
         this.func_190524_a(☃.func_177976_e(), ☃, ☃);
      }

      if (☃ != EnumFacing.EAST) {
         this.func_190524_a(☃.func_177974_f(), ☃, ☃);
      }

      if (☃ != EnumFacing.DOWN) {
         this.func_190524_a(☃.func_177977_b(), ☃, ☃);
      }

      if (☃ != EnumFacing.UP) {
         this.func_190524_a(☃.func_177984_a(), ☃, ☃);
      }

      if (☃ != EnumFacing.NORTH) {
         this.func_190524_a(☃.func_177978_c(), ☃, ☃);
      }

      if (☃ != EnumFacing.SOUTH) {
         this.func_190524_a(☃.func_177968_d(), ☃, ☃);
      }
   }

   public void func_190524_a(BlockPos var1, Block var2, BlockPos var3) {
      if (!this.field_72995_K) {
         IBlockState ☃ = this.func_180495_p(☃);

         try {
            ☃.func_189546_a(this, ☃, ☃, ☃);
         } catch (Throwable var8) {
            CrashReport ☃x = CrashReport.func_85055_a(var8, "Exception while updating neighbours");
            CrashReportCategory ☃xx = ☃x.func_85058_a("Block being updated");
            ☃xx.func_189529_a("Source block type", () -> {
               try {
                  return String.format("ID #%s (%s // %s)", IRegistry.field_212618_g.func_177774_c(☃), ☃.func_149739_a(), ☃.getClass().getCanonicalName());
               } catch (Throwable var2xx) {
                  return "ID #" + IRegistry.field_212618_g.func_177774_c(☃);
               }
            });
            CrashReportCategory.func_175750_a(☃xx, ☃, ☃);
            throw new ReportedException(☃x);
         }
      }
   }

   @Override
   public boolean func_175678_i(BlockPos var1) {
      return this.func_175726_f(☃).func_177444_d(☃);
   }

   @Override
   public int func_201669_a(BlockPos var1, int var2) {
      if (☃.func_177958_n() < -30000000 || ☃.func_177952_p() < -30000000 || ☃.func_177958_n() >= 30000000 || ☃.func_177952_p() >= 30000000) {
         return 15;
      } else if (☃.func_177956_o() < 0) {
         return 0;
      } else {
         if (☃.func_177956_o() >= 256) {
            ☃ = new BlockPos(☃.func_177958_n(), 255, ☃.func_177952_p());
         }

         return this.func_175726_f(☃).func_177443_a(☃, ☃);
      }
   }

   @Override
   public int func_201676_a(Heightmap.Type var1, int var2, int var3) {
      int ☃;
      if (☃ >= -30000000 && ☃ >= -30000000 && ☃ < 30000000 && ☃ < 30000000) {
         if (this.func_175680_a(☃ >> 4, ☃ >> 4, true)) {
            ☃ = this.func_72964_e(☃ >> 4, ☃ >> 4).func_201576_a(☃, ☃ & 15, ☃ & 15) + 1;
         } else {
            ☃ = 0;
         }
      } else {
         ☃ = this.func_181545_F() + 1;
      }

      return ☃;
   }

   @Deprecated
   public int func_82734_g(int var1, int var2) {
      if (☃ >= -30000000 && ☃ >= -30000000 && ☃ < 30000000 && ☃ < 30000000) {
         if (!this.func_175680_a(☃ >> 4, ☃ >> 4, true)) {
            return 0;
         } else {
            Chunk ☃ = this.func_72964_e(☃ >> 4, ☃ >> 4);
            return ☃.func_177442_v();
         }
      } else {
         return this.func_181545_F() + 1;
      }
   }

   public int func_175705_a(EnumLightType var1, BlockPos var2) {
      if (!this.field_73011_w.func_191066_m() && ☃ == EnumLightType.SKY) {
         return 0;
      } else {
         if (☃.func_177956_o() < 0) {
            ☃ = new BlockPos(☃.func_177958_n(), 0, ☃.func_177952_p());
         }

         if (!func_175701_a(☃)) {
            return ☃.field_77198_c;
         } else if (!this.func_175667_e(☃)) {
            return ☃.field_77198_c;
         } else if (this.func_180495_p(☃).func_200130_c(this, ☃)) {
            int ☃ = this.func_175642_b(☃, ☃.func_177984_a());
            int ☃x = this.func_175642_b(☃, ☃.func_177974_f());
            int ☃xx = this.func_175642_b(☃, ☃.func_177976_e());
            int ☃xxx = this.func_175642_b(☃, ☃.func_177968_d());
            int ☃xxxx = this.func_175642_b(☃, ☃.func_177978_c());
            if (☃x > ☃) {
               ☃ = ☃x;
            }

            if (☃xx > ☃) {
               ☃ = ☃xx;
            }

            if (☃xxx > ☃) {
               ☃ = ☃xxx;
            }

            if (☃xxxx > ☃) {
               ☃ = ☃xxxx;
            }

            return ☃;
         } else {
            return this.func_175726_f(☃).func_177413_a(☃, ☃);
         }
      }
   }

   @Override
   public int func_175642_b(EnumLightType var1, BlockPos var2) {
      if (☃.func_177956_o() < 0) {
         ☃ = new BlockPos(☃.func_177958_n(), 0, ☃.func_177952_p());
      }

      if (!func_175701_a(☃)) {
         return ☃.field_77198_c;
      } else {
         return !this.func_175667_e(☃) ? ☃.field_77198_c : this.func_175726_f(☃).func_177413_a(☃, ☃);
      }
   }

   @Override
   public void func_175653_a(EnumLightType var1, BlockPos var2, int var3) {
      if (func_175701_a(☃)) {
         if (this.func_175667_e(☃)) {
            this.func_175726_f(☃).func_177431_a(☃, ☃, ☃);
            this.func_175679_n(☃);
         }
      }
   }

   public void func_175679_n(BlockPos var1) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_174959_b(☃);
      }
   }

   @Override
   public int func_175626_b(BlockPos var1, int var2) {
      int ☃ = this.func_175705_a(EnumLightType.SKY, ☃);
      int ☃x = this.func_175705_a(EnumLightType.BLOCK, ☃);
      if (☃x < ☃) {
         ☃x = ☃;
      }

      return ☃ << 20 | ☃x << 4;
   }

   @Override
   public IBlockState func_180495_p(BlockPos var1) {
      if (func_189509_E(☃)) {
         return Blocks.field_201940_ji.func_176223_P();
      } else {
         Chunk ☃ = this.func_175726_f(☃);
         return ☃.func_180495_p(☃);
      }
   }

   @Override
   public IFluidState func_204610_c(BlockPos var1) {
      if (func_189509_E(☃)) {
         return Fluids.field_204541_a.func_207188_f();
      } else {
         Chunk ☃ = this.func_175726_f(☃);
         return ☃.func_204610_c(☃);
      }
   }

   public boolean func_72935_r() {
      return this.field_73008_k < 4;
   }

   @Nullable
   public RayTraceResult func_72933_a(Vec3d var1, Vec3d var2) {
      return this.func_200259_a(☃, ☃, RayTraceFluidMode.NEVER, false, false);
   }

   @Nullable
   public RayTraceResult func_200260_a(Vec3d var1, Vec3d var2, RayTraceFluidMode var3) {
      return this.func_200259_a(☃, ☃, ☃, false, false);
   }

   @Nullable
   public RayTraceResult func_200259_a(Vec3d var1, Vec3d var2, RayTraceFluidMode var3, boolean var4, boolean var5) {
      double ☃ = ☃.field_72450_a;
      double ☃x = ☃.field_72448_b;
      double ☃xx = ☃.field_72449_c;
      if (Double.isNaN(☃) || Double.isNaN(☃x) || Double.isNaN(☃xx)) {
         return null;
      } else if (!Double.isNaN(☃.field_72450_a) && !Double.isNaN(☃.field_72448_b) && !Double.isNaN(☃.field_72449_c)) {
         int ☃ = MathHelper.func_76128_c(☃.field_72450_a);
         int ☃x = MathHelper.func_76128_c(☃.field_72448_b);
         int ☃xx = MathHelper.func_76128_c(☃.field_72449_c);
         int ☃xxx = MathHelper.func_76128_c(☃);
         int ☃xxxx = MathHelper.func_76128_c(☃x);
         int ☃xxxxx = MathHelper.func_76128_c(☃xx);
         BlockPos ☃xxxxxx = new BlockPos(☃xxx, ☃xxxx, ☃xxxxx);
         IBlockState ☃xxxxxxx = this.func_180495_p(☃xxxxxx);
         IFluidState ☃xxxxxxxx = this.func_204610_c(☃xxxxxx);
         if (!☃ || !☃xxxxxxx.func_196952_d(this, ☃xxxxxx).func_197766_b()) {
            boolean ☃xxxxxxxxx = ☃xxxxxxx.func_177230_c().func_200293_a(☃xxxxxxx);
            boolean ☃xxxxxxxxxx = ☃.field_209544_d.test(☃xxxxxxxx);
            if (☃xxxxxxxxx || ☃xxxxxxxxxx) {
               RayTraceResult ☃xxxxxxxxxxx = null;
               if (☃xxxxxxxxx) {
                  ☃xxxxxxxxxxx = Block.func_180636_a(☃xxxxxxx, this, ☃xxxxxx, ☃, ☃);
               }

               if (☃xxxxxxxxxxx == null && ☃xxxxxxxxxx) {
                  ☃xxxxxxxxxxx = VoxelShapes.func_197873_a(0.0, 0.0, 0.0, 1.0, (double)☃xxxxxxxx.func_206885_f(), 1.0).func_212433_a(☃, ☃, ☃xxxxxx);
               }

               if (☃xxxxxxxxxxx != null) {
                  return ☃xxxxxxxxxxx;
               }
            }
         }

         RayTraceResult ☃ = null;
         int ☃x = 200;

         while(☃x-- >= 0) {
            if (Double.isNaN(☃) || Double.isNaN(☃x) || Double.isNaN(☃xx)) {
               return null;
            }

            if (☃xxx == ☃ && ☃xxxx == ☃x && ☃xxxxx == ☃xx) {
               return ☃ ? ☃ : null;
            }

            boolean ☃xx = true;
            boolean ☃xxx = true;
            boolean ☃xxxx = true;
            double ☃xxxxx = 999.0;
            double ☃xxxxxx = 999.0;
            double ☃xxxxxxx = 999.0;
            if (☃ > ☃xxx) {
               ☃xxxxx = (double)☃xxx + 1.0;
            } else if (☃ < ☃xxx) {
               ☃xxxxx = (double)☃xxx + 0.0;
            } else {
               ☃xx = false;
            }

            if (☃x > ☃xxxx) {
               ☃xxxxxx = (double)☃xxxx + 1.0;
            } else if (☃x < ☃xxxx) {
               ☃xxxxxx = (double)☃xxxx + 0.0;
            } else {
               ☃xxx = false;
            }

            if (☃xx > ☃xxxxx) {
               ☃xxxxxxx = (double)☃xxxxx + 1.0;
            } else if (☃xx < ☃xxxxx) {
               ☃xxxxxxx = (double)☃xxxxx + 0.0;
            } else {
               ☃xxxx = false;
            }

            double ☃xx = 999.0;
            double ☃xxx = 999.0;
            double ☃xxxx = 999.0;
            double ☃xxxxx = ☃.field_72450_a - ☃;
            double ☃xxxxxx = ☃.field_72448_b - ☃x;
            double ☃xxxxxxx = ☃.field_72449_c - ☃xx;
            if (☃xx) {
               ☃xx = (☃xxxxx - ☃) / ☃xxxxx;
            }

            if (☃xxx) {
               ☃xxx = (☃xxxxxx - ☃x) / ☃xxxxxx;
            }

            if (☃xxxx) {
               ☃xxxx = (☃xxxxxxx - ☃xx) / ☃xxxxxxx;
            }

            if (☃xx == -0.0) {
               ☃xx = -1.0E-4;
            }

            if (☃xxx == -0.0) {
               ☃xxx = -1.0E-4;
            }

            if (☃xxxx == -0.0) {
               ☃xxxx = -1.0E-4;
            }

            EnumFacing ☃xx;
            if (☃xx < ☃xxx && ☃xx < ☃xxxx) {
               ☃xx = ☃ > ☃xxx ? EnumFacing.WEST : EnumFacing.EAST;
               ☃ = ☃xxxxx;
               ☃x += ☃xxxxxx * ☃xx;
               ☃xx += ☃xxxxxxx * ☃xx;
            } else if (☃xxx < ☃xxxx) {
               ☃xx = ☃x > ☃xxxx ? EnumFacing.DOWN : EnumFacing.UP;
               ☃ += ☃xxxxx * ☃xxx;
               ☃x = ☃xxxxxx;
               ☃xx += ☃xxxxxxx * ☃xxx;
            } else {
               ☃xx = ☃xx > ☃xxxxx ? EnumFacing.NORTH : EnumFacing.SOUTH;
               ☃ += ☃xxxxx * ☃xxxx;
               ☃x += ☃xxxxxx * ☃xxxx;
               ☃xx = ☃xxxxxxx;
            }

            ☃xxx = MathHelper.func_76128_c(☃) - (☃xx == EnumFacing.EAST ? 1 : 0);
            ☃xxxx = MathHelper.func_76128_c(☃x) - (☃xx == EnumFacing.UP ? 1 : 0);
            ☃xxxxx = MathHelper.func_76128_c(☃xx) - (☃xx == EnumFacing.SOUTH ? 1 : 0);
            ☃xxxxxx = new BlockPos(☃xxx, ☃xxxx, ☃xxxxx);
            IBlockState ☃xx = this.func_180495_p(☃xxxxxx);
            IFluidState ☃xxx = this.func_204610_c(☃xxxxxx);
            if (!☃ || ☃xx.func_185904_a() == Material.field_151567_E || !☃xx.func_196952_d(this, ☃xxxxxx).func_197766_b()) {
               boolean ☃xxxx = ☃xx.func_177230_c().func_200293_a(☃xx);
               boolean ☃xxxxx = ☃.field_209544_d.test(☃xxx);
               if (!☃xxxx && !☃xxxxx) {
                  ☃ = new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(☃, ☃x, ☃xx), ☃xx, ☃xxxxxx);
               } else {
                  RayTraceResult ☃xxxx = null;
                  if (☃xxxx) {
                     ☃xxxx = Block.func_180636_a(☃xx, this, ☃xxxxxx, ☃, ☃);
                  }

                  if (☃xxxx == null && ☃xxxxx) {
                     ☃xxxx = VoxelShapes.func_197873_a(0.0, 0.0, 0.0, 1.0, (double)☃xxx.func_206885_f(), 1.0).func_212433_a(☃, ☃, ☃xxxxxx);
                  }

                  if (☃xxxx != null) {
                     return ☃xxxx;
                  }
               }
            }
         }

         return ☃ ? ☃ : null;
      } else {
         return null;
      }
   }

   @Override
   public void func_184133_a(@Nullable EntityPlayer var1, BlockPos var2, SoundEvent var3, SoundCategory var4, float var5, float var6) {
      this.func_184148_a(☃, (double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.5, (double)☃.func_177952_p() + 0.5, ☃, ☃, ☃, ☃);
   }

   public void func_184148_a(@Nullable EntityPlayer var1, double var2, double var4, double var6, SoundEvent var8, SoundCategory var9, float var10, float var11) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_184375_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public void func_184134_a(double var1, double var3, double var5, SoundEvent var7, SoundCategory var8, float var9, float var10, boolean var11) {
   }

   public void func_184149_a(BlockPos var1, @Nullable SoundEvent var2) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_184377_a(☃, ☃);
      }
   }

   @Override
   public void func_195594_a(IParticleData var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_195461_a(☃, ☃.func_197554_b().func_197575_f(), ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public void func_195590_a(IParticleData var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_195461_a(☃, ☃.func_197554_b().func_197575_f() || ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public void func_195589_b(IParticleData var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_195462_a(☃, false, true, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public boolean func_72942_c(Entity var1) {
      this.field_73007_j.add(☃);
      return true;
   }

   @Override
   public boolean func_72838_d(Entity var1) {
      int ☃ = MathHelper.func_76128_c(☃.field_70165_t / 16.0);
      int ☃x = MathHelper.func_76128_c(☃.field_70161_v / 16.0);
      boolean ☃xx = ☃.field_98038_p;
      if (☃ instanceof EntityPlayer) {
         ☃xx = true;
      }

      if (!☃xx && !this.func_175680_a(☃, ☃x, false)) {
         return false;
      } else {
         if (☃ instanceof EntityPlayer) {
            EntityPlayer ☃ = (EntityPlayer)☃;
            this.field_73010_i.add(☃);
            this.func_72854_c();
         }

         this.func_72964_e(☃, ☃x).func_76612_a(☃);
         this.field_72996_f.add(☃);
         this.func_72923_a(☃);
         return true;
      }
   }

   protected void func_72923_a(Entity var1) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_72703_a(☃);
      }
   }

   protected void func_72847_b(Entity var1) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_72709_b(☃);
      }
   }

   public void func_72900_e(Entity var1) {
      if (☃.func_184207_aI()) {
         ☃.func_184226_ay();
      }

      if (☃.func_184218_aH()) {
         ☃.func_184210_p();
      }

      ☃.func_70106_y();
      if (☃ instanceof EntityPlayer) {
         this.field_73010_i.remove(☃);
         this.func_72854_c();
         this.func_72847_b(☃);
      }
   }

   public void func_72973_f(Entity var1) {
      ☃.func_184174_b(false);
      ☃.func_70106_y();
      if (☃ instanceof EntityPlayer) {
         this.field_73010_i.remove(☃);
         this.func_72854_c();
      }

      int ☃ = ☃.field_70176_ah;
      int ☃x = ☃.field_70164_aj;
      if (☃.field_70175_ag && this.func_175680_a(☃, ☃x, true)) {
         this.func_72964_e(☃, ☃x).func_76622_b(☃);
      }

      this.field_72996_f.remove(☃);
      this.func_72847_b(☃);
   }

   public void func_72954_a(IWorldEventListener var1) {
      this.field_73021_x.add(☃);
   }

   public void func_72848_b(IWorldEventListener var1) {
      this.field_73021_x.remove(☃);
   }

   public int func_72967_a(float var1) {
      float ☃ = this.func_72826_c(☃);
      float ☃x = 1.0F - (MathHelper.func_76134_b(☃ * (float) (Math.PI * 2)) * 2.0F + 0.5F);
      ☃x = MathHelper.func_76131_a(☃x, 0.0F, 1.0F);
      ☃x = 1.0F - ☃x;
      ☃x = (float)((double)☃x * (1.0 - (double)(this.func_72867_j(☃) * 5.0F) / 16.0));
      ☃x = (float)((double)☃x * (1.0 - (double)(this.func_72819_i(☃) * 5.0F) / 16.0));
      ☃x = 1.0F - ☃x;
      return (int)(☃x * 11.0F);
   }

   public float func_72971_b(float var1) {
      float ☃ = this.func_72826_c(☃);
      float ☃x = 1.0F - (MathHelper.func_76134_b(☃ * (float) (Math.PI * 2)) * 2.0F + 0.2F);
      ☃x = MathHelper.func_76131_a(☃x, 0.0F, 1.0F);
      ☃x = 1.0F - ☃x;
      ☃x = (float)((double)☃x * (1.0 - (double)(this.func_72867_j(☃) * 5.0F) / 16.0));
      ☃x = (float)((double)☃x * (1.0 - (double)(this.func_72819_i(☃) * 5.0F) / 16.0));
      return ☃x * 0.8F + 0.2F;
   }

   public Vec3d func_72833_a(Entity var1, float var2) {
      float ☃ = this.func_72826_c(☃);
      float ☃x = MathHelper.func_76134_b(☃ * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      ☃x = MathHelper.func_76131_a(☃x, 0.0F, 1.0F);
      int ☃xx = MathHelper.func_76128_c(☃.field_70165_t);
      int ☃xxx = MathHelper.func_76128_c(☃.field_70163_u);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_70161_v);
      BlockPos ☃xxxxx = new BlockPos(☃xx, ☃xxx, ☃xxxx);
      Biome ☃xxxxxx = this.func_180494_b(☃xxxxx);
      float ☃xxxxxxx = ☃xxxxxx.func_180626_a(☃xxxxx);
      int ☃xxxxxxxx = ☃xxxxxx.func_76731_a(☃xxxxxxx);
      float ☃xxxxxxxxx = (float)(☃xxxxxxxx >> 16 & 0xFF) / 255.0F;
      float ☃xxxxxxxxxx = (float)(☃xxxxxxxx >> 8 & 0xFF) / 255.0F;
      float ☃xxxxxxxxxxx = (float)(☃xxxxxxxx & 0xFF) / 255.0F;
      ☃xxxxxxxxx *= ☃x;
      ☃xxxxxxxxxx *= ☃x;
      ☃xxxxxxxxxxx *= ☃x;
      float ☃xxxxxxxxxxxx = this.func_72867_j(☃);
      if (☃xxxxxxxxxxxx > 0.0F) {
         float ☃xxxxxxxxxxxxx = (☃xxxxxxxxx * 0.3F + ☃xxxxxxxxxx * 0.59F + ☃xxxxxxxxxxx * 0.11F) * 0.6F;
         float ☃xxxxxxxxxxxxxx = 1.0F - ☃xxxxxxxxxxxx * 0.75F;
         ☃xxxxxxxxx = ☃xxxxxxxxx * ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx * (1.0F - ☃xxxxxxxxxxxxxx);
         ☃xxxxxxxxxx = ☃xxxxxxxxxx * ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx * (1.0F - ☃xxxxxxxxxxxxxx);
         ☃xxxxxxxxxxx = ☃xxxxxxxxxxx * ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx * (1.0F - ☃xxxxxxxxxxxxxx);
      }

      float ☃ = this.func_72819_i(☃);
      if (☃ > 0.0F) {
         float ☃x = (☃xxxxxxxxx * 0.3F + ☃xxxxxxxxxx * 0.59F + ☃xxxxxxxxxxx * 0.11F) * 0.2F;
         float ☃xx = 1.0F - ☃ * 0.75F;
         ☃xxxxxxxxx = ☃xxxxxxxxx * ☃xx + ☃x * (1.0F - ☃xx);
         ☃xxxxxxxxxx = ☃xxxxxxxxxx * ☃xx + ☃x * (1.0F - ☃xx);
         ☃xxxxxxxxxxx = ☃xxxxxxxxxxx * ☃xx + ☃x * (1.0F - ☃xx);
      }

      if (this.field_73016_r > 0) {
         float ☃ = (float)this.field_73016_r - ☃;
         if (☃ > 1.0F) {
            ☃ = 1.0F;
         }

         ☃ *= 0.45F;
         ☃xxxxxxxxx = ☃xxxxxxxxx * (1.0F - ☃) + 0.8F * ☃;
         ☃xxxxxxxxxx = ☃xxxxxxxxxx * (1.0F - ☃) + 0.8F * ☃;
         ☃xxxxxxxxxxx = ☃xxxxxxxxxxx * (1.0F - ☃) + 1.0F * ☃;
      }

      return new Vec3d((double)☃xxxxxxxxx, (double)☃xxxxxxxxxx, (double)☃xxxxxxxxxxx);
   }

   public float func_72929_e(float var1) {
      float ☃ = this.func_72826_c(☃);
      return ☃ * (float) (Math.PI * 2);
   }

   public Vec3d func_72824_f(float var1) {
      float ☃ = this.func_72826_c(☃);
      float ☃x = MathHelper.func_76134_b(☃ * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      ☃x = MathHelper.func_76131_a(☃x, 0.0F, 1.0F);
      float ☃xx = 1.0F;
      float ☃xxx = 1.0F;
      float ☃xxxx = 1.0F;
      float ☃xxxxx = this.func_72867_j(☃);
      if (☃xxxxx > 0.0F) {
         float ☃xxxxxx = (☃xx * 0.3F + ☃xxx * 0.59F + ☃xxxx * 0.11F) * 0.6F;
         float ☃xxxxxxx = 1.0F - ☃xxxxx * 0.95F;
         ☃xx = ☃xx * ☃xxxxxxx + ☃xxxxxx * (1.0F - ☃xxxxxxx);
         ☃xxx = ☃xxx * ☃xxxxxxx + ☃xxxxxx * (1.0F - ☃xxxxxxx);
         ☃xxxx = ☃xxxx * ☃xxxxxxx + ☃xxxxxx * (1.0F - ☃xxxxxxx);
      }

      ☃xx *= ☃x * 0.9F + 0.1F;
      ☃xxx *= ☃x * 0.9F + 0.1F;
      ☃xxxx *= ☃x * 0.85F + 0.15F;
      float ☃ = this.func_72819_i(☃);
      if (☃ > 0.0F) {
         float ☃x = (☃xx * 0.3F + ☃xxx * 0.59F + ☃xxxx * 0.11F) * 0.2F;
         float ☃xx = 1.0F - ☃ * 0.95F;
         ☃xx = ☃xx * ☃xx + ☃x * (1.0F - ☃xx);
         ☃xxx = ☃xxx * ☃xx + ☃x * (1.0F - ☃xx);
         ☃xxxx = ☃xxxx * ☃xx + ☃x * (1.0F - ☃xx);
      }

      return new Vec3d((double)☃xx, (double)☃xxx, (double)☃xxxx);
   }

   public Vec3d func_72948_g(float var1) {
      float ☃ = this.func_72826_c(☃);
      return this.field_73011_w.func_76562_b(☃, ☃);
   }

   public float func_72880_h(float var1) {
      float ☃ = this.func_72826_c(☃);
      float ☃x = 1.0F - (MathHelper.func_76134_b(☃ * (float) (Math.PI * 2)) * 2.0F + 0.25F);
      ☃x = MathHelper.func_76131_a(☃x, 0.0F, 1.0F);
      return ☃x * ☃x * 0.5F;
   }

   public void func_72939_s() {
      this.field_72984_F.func_76320_a("entities");
      this.field_72984_F.func_76320_a("global");

      for(int ☃ = 0; ☃ < this.field_73007_j.size(); ++☃) {
         Entity ☃x = (Entity)this.field_73007_j.get(☃);

         try {
            ++☃x.field_70173_aa;
            ☃x.func_70071_h_();
         } catch (Throwable var9) {
            CrashReport ☃xx = CrashReport.func_85055_a(var9, "Ticking entity");
            CrashReportCategory ☃xxx = ☃xx.func_85058_a("Entity being ticked");
            if (☃x == null) {
               ☃xxx.func_71507_a("Entity", "~~NULL~~");
            } else {
               ☃x.func_85029_a(☃xxx);
            }

            throw new ReportedException(☃xx);
         }

         if (☃x.field_70128_L) {
            this.field_73007_j.remove(☃--);
         }
      }

      this.field_72984_F.func_76318_c("remove");
      this.field_72996_f.removeAll(this.field_72997_g);

      for(int ☃ = 0; ☃ < this.field_72997_g.size(); ++☃) {
         Entity ☃x = (Entity)this.field_72997_g.get(☃);
         int ☃xx = ☃x.field_70176_ah;
         int ☃xxx = ☃x.field_70164_aj;
         if (☃x.field_70175_ag && this.func_175680_a(☃xx, ☃xxx, true)) {
            this.func_72964_e(☃xx, ☃xxx).func_76622_b(☃x);
         }
      }

      for(int ☃ = 0; ☃ < this.field_72997_g.size(); ++☃) {
         this.func_72847_b((Entity)this.field_72997_g.get(☃));
      }

      this.field_72997_g.clear();
      this.func_184147_l();
      this.field_72984_F.func_76318_c("regular");

      for(int ☃ = 0; ☃ < this.field_72996_f.size(); ++☃) {
         Entity ☃x = (Entity)this.field_72996_f.get(☃);
         Entity ☃xx = ☃x.func_184187_bx();
         if (☃xx != null) {
            if (!☃xx.field_70128_L && ☃xx.func_184196_w(☃x)) {
               continue;
            }

            ☃x.func_184210_p();
         }

         this.field_72984_F.func_76320_a("tick");
         if (!☃x.field_70128_L && !(☃x instanceof EntityPlayerMP)) {
            try {
               this.func_72870_g(☃x);
            } catch (Throwable var8) {
               CrashReport ☃x = CrashReport.func_85055_a(var8, "Ticking entity");
               CrashReportCategory ☃xx = ☃x.func_85058_a("Entity being ticked");
               ☃x.func_85029_a(☃xx);
               throw new ReportedException(☃x);
            }
         }

         this.field_72984_F.func_76319_b();
         this.field_72984_F.func_76320_a("remove");
         if (☃x.field_70128_L) {
            int ☃x = ☃x.field_70176_ah;
            int ☃xx = ☃x.field_70164_aj;
            if (☃x.field_70175_ag && this.func_175680_a(☃x, ☃xx, true)) {
               this.func_72964_e(☃x, ☃xx).func_76622_b(☃x);
            }

            this.field_72996_f.remove(☃--);
            this.func_72847_b(☃x);
         }

         this.field_72984_F.func_76319_b();
      }

      this.field_72984_F.func_76318_c("blockEntities");
      if (!this.field_147483_b.isEmpty()) {
         this.field_175730_i.removeAll(this.field_147483_b);
         this.field_147482_g.removeAll(this.field_147483_b);
         this.field_147483_b.clear();
      }

      this.field_147481_N = true;
      Iterator<TileEntity> ☃ = this.field_175730_i.iterator();

      while(☃.hasNext()) {
         TileEntity ☃x = (TileEntity)☃.next();
         if (!☃x.func_145837_r() && ☃x.func_145830_o()) {
            BlockPos ☃xx = ☃x.func_174877_v();
            if (this.func_175667_e(☃xx) && this.field_175728_M.func_177746_a(☃xx)) {
               try {
                  this.field_72984_F.func_194340_a(() -> String.valueOf(TileEntityType.func_200969_a(☃.func_200662_C())));
                  ((ITickable)☃x).func_73660_a();
                  this.field_72984_F.func_76319_b();
               } catch (Throwable var7) {
                  CrashReport ☃xxx = CrashReport.func_85055_a(var7, "Ticking block entity");
                  CrashReportCategory ☃xxxx = ☃xxx.func_85058_a("Block entity being ticked");
                  ☃x.func_145828_a(☃xxxx);
                  throw new ReportedException(☃xxx);
               }
            }
         }

         if (☃x.func_145837_r()) {
            ☃.remove();
            this.field_147482_g.remove(☃x);
            if (this.func_175667_e(☃x.func_174877_v())) {
               this.func_175726_f(☃x.func_174877_v()).func_177425_e(☃x.func_174877_v());
            }
         }
      }

      this.field_147481_N = false;
      this.field_72984_F.func_76318_c("pendingBlockEntities");
      if (!this.field_147484_a.isEmpty()) {
         for(int ☃x = 0; ☃x < this.field_147484_a.size(); ++☃x) {
            TileEntity ☃xx = (TileEntity)this.field_147484_a.get(☃x);
            if (!☃xx.func_145837_r()) {
               if (!this.field_147482_g.contains(☃xx)) {
                  this.func_175700_a(☃xx);
               }

               if (this.func_175667_e(☃xx.func_174877_v())) {
                  Chunk ☃xxx = this.func_175726_f(☃xx.func_174877_v());
                  IBlockState ☃xxxx = ☃xxx.func_180495_p(☃xx.func_174877_v());
                  ☃xxx.func_177426_a(☃xx.func_174877_v(), ☃xx);
                  this.func_184138_a(☃xx.func_174877_v(), ☃xxxx, ☃xxxx, 3);
               }
            }
         }

         this.field_147484_a.clear();
      }

      this.field_72984_F.func_76319_b();
      this.field_72984_F.func_76319_b();
   }

   protected void func_184147_l() {
   }

   public boolean func_175700_a(TileEntity var1) {
      boolean ☃ = this.field_147482_g.add(☃);
      if (☃ && ☃ instanceof ITickable) {
         this.field_175730_i.add(☃);
      }

      if (this.field_72995_K) {
         BlockPos ☃ = ☃.func_174877_v();
         IBlockState ☃x = this.func_180495_p(☃);
         this.func_184138_a(☃, ☃x, ☃x, 2);
      }

      return ☃;
   }

   public void func_147448_a(Collection<TileEntity> var1) {
      if (this.field_147481_N) {
         this.field_147484_a.addAll(☃);
      } else {
         for(TileEntity ☃ : ☃) {
            this.func_175700_a(☃);
         }
      }
   }

   public void func_72870_g(Entity var1) {
      this.func_72866_a(☃, true);
   }

   public void func_72866_a(Entity var1, boolean var2) {
      if (!(☃ instanceof EntityPlayer)) {
         int ☃ = MathHelper.func_76128_c(☃.field_70165_t);
         int ☃x = MathHelper.func_76128_c(☃.field_70161_v);
         int ☃xx = 32;
         if (☃ && !this.func_175663_a(☃ - 32, 0, ☃x - 32, ☃ + 32, 0, ☃x + 32, true)) {
            return;
         }
      }

      ☃.field_70142_S = ☃.field_70165_t;
      ☃.field_70137_T = ☃.field_70163_u;
      ☃.field_70136_U = ☃.field_70161_v;
      ☃.field_70126_B = ☃.field_70177_z;
      ☃.field_70127_C = ☃.field_70125_A;
      if (☃ && ☃.field_70175_ag) {
         ++☃.field_70173_aa;
         if (☃.func_184218_aH()) {
            ☃.func_70098_U();
         } else {
            this.field_72984_F.func_194340_a(() -> IRegistry.field_212629_r.func_177774_c(☃.func_200600_R()).toString());
            ☃.func_70071_h_();
            this.field_72984_F.func_76319_b();
         }
      }

      this.field_72984_F.func_76320_a("chunkCheck");
      if (Double.isNaN(☃.field_70165_t) || Double.isInfinite(☃.field_70165_t)) {
         ☃.field_70165_t = ☃.field_70142_S;
      }

      if (Double.isNaN(☃.field_70163_u) || Double.isInfinite(☃.field_70163_u)) {
         ☃.field_70163_u = ☃.field_70137_T;
      }

      if (Double.isNaN(☃.field_70161_v) || Double.isInfinite(☃.field_70161_v)) {
         ☃.field_70161_v = ☃.field_70136_U;
      }

      if (Double.isNaN((double)☃.field_70125_A) || Double.isInfinite((double)☃.field_70125_A)) {
         ☃.field_70125_A = ☃.field_70127_C;
      }

      if (Double.isNaN((double)☃.field_70177_z) || Double.isInfinite((double)☃.field_70177_z)) {
         ☃.field_70177_z = ☃.field_70126_B;
      }

      int ☃ = MathHelper.func_76128_c(☃.field_70165_t / 16.0);
      int ☃x = MathHelper.func_76128_c(☃.field_70163_u / 16.0);
      int ☃xx = MathHelper.func_76128_c(☃.field_70161_v / 16.0);
      if (!☃.field_70175_ag || ☃.field_70176_ah != ☃ || ☃.field_70162_ai != ☃x || ☃.field_70164_aj != ☃xx) {
         if (☃.field_70175_ag && this.func_175680_a(☃.field_70176_ah, ☃.field_70164_aj, true)) {
            this.func_72964_e(☃.field_70176_ah, ☃.field_70164_aj).func_76608_a(☃, ☃.field_70162_ai);
         }

         if (!☃.func_184189_br() && !this.func_175680_a(☃, ☃xx, true)) {
            ☃.field_70175_ag = false;
         } else {
            this.func_72964_e(☃, ☃xx).func_76612_a(☃);
         }
      }

      this.field_72984_F.func_76319_b();
      if (☃ && ☃.field_70175_ag) {
         for(Entity ☃ : ☃.func_184188_bt()) {
            if (!☃.field_70128_L && ☃.func_184187_bx() == ☃) {
               this.func_72870_g(☃);
            } else {
               ☃.func_184210_p();
            }
         }
      }
   }

   @Override
   public boolean func_195585_a(@Nullable Entity var1, VoxelShape var2) {
      if (☃.func_197766_b()) {
         return true;
      } else {
         List<Entity> ☃ = this.func_72839_b(null, ☃.func_197752_a());

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            Entity ☃xx = (Entity)☃.get(☃x);
            if (!☃xx.field_70128_L
               && ☃xx.field_70156_m
               && ☃xx != ☃
               && (☃ == null || !☃xx.func_184223_x(☃))
               && VoxelShapes.func_197879_c(☃, VoxelShapes.func_197881_a(☃xx.func_174813_aQ()), IBooleanFunction.AND)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean func_72829_c(AxisAlignedBB var1) {
      int ☃ = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃x = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xx = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xxx = MathHelper.func_76143_f(☃.field_72337_e);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxx = MathHelper.func_76143_f(☃.field_72334_f);

      try (BlockPos.PooledMutableBlockPos ☃xxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(int ☃xxxxxxx = ☃; ☃xxxxxxx < ☃x; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = ☃xx; ☃xxxxxxxx < ☃xxx; ++☃xxxxxxxx) {
               for(int ☃xxxxxxxxx = ☃xxxx; ☃xxxxxxxxx < ☃xxxxx; ++☃xxxxxxxxx) {
                  IBlockState ☃xxxxxxxxxx = this.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx));
                  if (!☃xxxxxxxxxx.func_196958_f()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean func_147470_e(AxisAlignedBB var1) {
      int ☃ = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃x = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xx = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xxx = MathHelper.func_76143_f(☃.field_72337_e);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxx = MathHelper.func_76143_f(☃.field_72334_f);
      if (this.func_175663_a(☃, ☃xx, ☃xxxx, ☃x, ☃xxx, ☃xxxxx, true)) {
         try (BlockPos.PooledMutableBlockPos ☃xxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
            for(int ☃xxxxxxx = ☃; ☃xxxxxxx < ☃x; ++☃xxxxxxx) {
               for(int ☃xxxxxxxx = ☃xx; ☃xxxxxxxx < ☃xxx; ++☃xxxxxxxx) {
                  for(int ☃xxxxxxxxx = ☃xxxx; ☃xxxxxxxxx < ☃xxxxx; ++☃xxxxxxxxx) {
                     Block ☃xxxxxxxxxx = this.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx)).func_177230_c();
                     if (☃xxxxxxxxxx == Blocks.field_150480_ab || ☃xxxxxxxxxx == Blocks.field_150353_l) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   @Nullable
   public IBlockState func_203067_a(AxisAlignedBB var1, Block var2) {
      int ☃ = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃x = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xx = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xxx = MathHelper.func_76143_f(☃.field_72337_e);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxx = MathHelper.func_76143_f(☃.field_72334_f);
      if (this.func_175663_a(☃, ☃xx, ☃xxxx, ☃x, ☃xxx, ☃xxxxx, true)) {
         try (BlockPos.PooledMutableBlockPos ☃xxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
            for(int ☃xxxxxxx = ☃; ☃xxxxxxx < ☃x; ++☃xxxxxxx) {
               for(int ☃xxxxxxxx = ☃xx; ☃xxxxxxxx < ☃xxx; ++☃xxxxxxxx) {
                  for(int ☃xxxxxxxxx = ☃xxxx; ☃xxxxxxxxx < ☃xxxxx; ++☃xxxxxxxxx) {
                     IBlockState ☃xxxxxxxxxx = this.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx));
                     if (☃xxxxxxxxxx.func_177230_c() == ☃) {
                        return ☃xxxxxxxxxx;
                     }
                  }
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public boolean func_72875_a(AxisAlignedBB var1, Material var2) {
      int ☃ = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃x = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xx = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xxx = MathHelper.func_76143_f(☃.field_72337_e);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxx = MathHelper.func_76143_f(☃.field_72334_f);
      BlockMaterialMatcher ☃xxxxxx = BlockMaterialMatcher.func_189886_a(☃);

      try (BlockPos.PooledMutableBlockPos ☃xxxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(int ☃xxxxxxxx = ☃; ☃xxxxxxxx < ☃x; ++☃xxxxxxxx) {
            for(int ☃xxxxxxxxx = ☃xx; ☃xxxxxxxxx < ☃xxx; ++☃xxxxxxxxx) {
               for(int ☃xxxxxxxxxx = ☃xxxx; ☃xxxxxxxxxx < ☃xxxxx; ++☃xxxxxxxxxx) {
                  if (☃xxxxxx.test(this.func_180495_p(☃xxxxxxx.func_181079_c(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx)))) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public Explosion func_72876_a(@Nullable Entity var1, double var2, double var4, double var6, float var8, boolean var9) {
      return this.func_211529_a(☃, null, ☃, ☃, ☃, ☃, false, ☃);
   }

   public Explosion func_72885_a(@Nullable Entity var1, double var2, double var4, double var6, float var8, boolean var9, boolean var10) {
      return this.func_211529_a(☃, null, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public Explosion func_211529_a(
      @Nullable Entity var1, @Nullable DamageSource var2, double var3, double var5, double var7, float var9, boolean var10, boolean var11
   ) {
      Explosion ☃ = new Explosion(this, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (☃ != null) {
         ☃.func_199592_a(☃);
      }

      ☃.func_77278_a();
      ☃.func_77279_a(true);
      return ☃;
   }

   public float func_72842_a(Vec3d var1, AxisAlignedBB var2) {
      double ☃ = 1.0 / ((☃.field_72336_d - ☃.field_72340_a) * 2.0 + 1.0);
      double ☃x = 1.0 / ((☃.field_72337_e - ☃.field_72338_b) * 2.0 + 1.0);
      double ☃xx = 1.0 / ((☃.field_72334_f - ☃.field_72339_c) * 2.0 + 1.0);
      double ☃xxx = (1.0 - Math.floor(1.0 / ☃) * ☃) / 2.0;
      double ☃xxxx = (1.0 - Math.floor(1.0 / ☃xx) * ☃xx) / 2.0;
      if (!(☃ < 0.0) && !(☃x < 0.0) && !(☃xx < 0.0)) {
         int ☃xxxxx = 0;
         int ☃xxxxxx = 0;

         for(float ☃xxxxxxx = 0.0F; ☃xxxxxxx <= 1.0F; ☃xxxxxxx = (float)((double)☃xxxxxxx + ☃)) {
            for(float ☃xxxxxxxx = 0.0F; ☃xxxxxxxx <= 1.0F; ☃xxxxxxxx = (float)((double)☃xxxxxxxx + ☃x)) {
               for(float ☃xxxxxxxxx = 0.0F; ☃xxxxxxxxx <= 1.0F; ☃xxxxxxxxx = (float)((double)☃xxxxxxxxx + ☃xx)) {
                  double ☃xxxxxxxxxx = ☃.field_72340_a + (☃.field_72336_d - ☃.field_72340_a) * (double)☃xxxxxxx;
                  double ☃xxxxxxxxxxx = ☃.field_72338_b + (☃.field_72337_e - ☃.field_72338_b) * (double)☃xxxxxxxx;
                  double ☃xxxxxxxxxxxx = ☃.field_72339_c + (☃.field_72334_f - ☃.field_72339_c) * (double)☃xxxxxxxxx;
                  if (this.func_72933_a(new Vec3d(☃xxxxxxxxxx + ☃xxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx + ☃xxxx), ☃) == null) {
                     ++☃xxxxx;
                  }

                  ++☃xxxxxx;
               }
            }
         }

         return (float)☃xxxxx / (float)☃xxxxxx;
      } else {
         return 0.0F;
      }
   }

   public boolean func_175719_a(@Nullable EntityPlayer var1, BlockPos var2, EnumFacing var3) {
      ☃ = ☃.func_177972_a(☃);
      if (this.func_180495_p(☃).func_177230_c() == Blocks.field_150480_ab) {
         this.func_180498_a(☃, 1009, ☃, 0);
         this.func_175698_g(☃);
         return true;
      } else {
         return false;
      }
   }

   public String func_72981_t() {
      return "All: " + this.field_72996_f.size();
   }

   public String func_72827_u() {
      return this.field_73020_y.func_73148_d();
   }

   @Nullable
   @Override
   public TileEntity func_175625_s(BlockPos var1) {
      if (func_189509_E(☃)) {
         return null;
      } else {
         TileEntity ☃ = null;
         if (this.field_147481_N) {
            ☃ = this.func_189508_F(☃);
         }

         if (☃ == null) {
            ☃ = this.func_175726_f(☃).func_177424_a(☃, Chunk.EnumCreateEntityType.IMMEDIATE);
         }

         if (☃ == null) {
            ☃ = this.func_189508_F(☃);
         }

         return ☃;
      }
   }

   @Nullable
   private TileEntity func_189508_F(BlockPos var1) {
      for(int ☃ = 0; ☃ < this.field_147484_a.size(); ++☃) {
         TileEntity ☃x = (TileEntity)this.field_147484_a.get(☃);
         if (!☃x.func_145837_r() && ☃x.func_174877_v().equals(☃)) {
            return ☃x;
         }
      }

      return null;
   }

   public void func_175690_a(BlockPos var1, @Nullable TileEntity var2) {
      if (!func_189509_E(☃)) {
         if (☃ != null && !☃.func_145837_r()) {
            if (this.field_147481_N) {
               ☃.func_174878_a(☃);
               Iterator<TileEntity> ☃ = this.field_147484_a.iterator();

               while(☃.hasNext()) {
                  TileEntity ☃x = (TileEntity)☃.next();
                  if (☃x.func_174877_v().equals(☃)) {
                     ☃x.func_145843_s();
                     ☃.remove();
                  }
               }

               this.field_147484_a.add(☃);
            } else {
               this.func_175726_f(☃).func_177426_a(☃, ☃);
               this.func_175700_a(☃);
            }
         }
      }
   }

   public void func_175713_t(BlockPos var1) {
      TileEntity ☃ = this.func_175625_s(☃);
      if (☃ != null && this.field_147481_N) {
         ☃.func_145843_s();
         this.field_147484_a.remove(☃);
      } else {
         if (☃ != null) {
            this.field_147484_a.remove(☃);
            this.field_147482_g.remove(☃);
            this.field_175730_i.remove(☃);
         }

         this.func_175726_f(☃).func_177425_e(☃);
      }
   }

   public void func_147457_a(TileEntity var1) {
      this.field_147483_b.add(☃);
   }

   public boolean func_175665_u(BlockPos var1) {
      return Block.func_208062_a(this.func_180495_p(☃).func_196952_d(this, ☃));
   }

   public boolean func_195588_v(BlockPos var1) {
      if (func_189509_E(☃)) {
         return false;
      } else {
         Chunk ☃ = this.field_73020_y.func_186025_d(☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4, false, false);
         return ☃ != null && !☃.func_76621_g();
      }
   }

   public boolean func_195595_w(BlockPos var1) {
      return this.func_195588_v(☃) && this.func_180495_p(☃).func_185896_q();
   }

   public void func_72966_v() {
      int ☃ = this.func_72967_a(1.0F);
      if (☃ != this.field_73008_k) {
         this.field_73008_k = ☃;
      }
   }

   public void func_72891_a(boolean var1, boolean var2) {
      this.field_72985_G = ☃;
      this.field_72992_H = ☃;
   }

   public void func_72835_b(BooleanSupplier var1) {
      this.field_175728_M.func_212673_r();
      this.func_72979_l();
   }

   protected void func_72947_a() {
      if (this.field_72986_A.func_76059_o()) {
         this.field_73004_o = 1.0F;
         if (this.field_72986_A.func_76061_m()) {
            this.field_73017_q = 1.0F;
         }
      }
   }

   public void close() {
      this.field_73020_y.close();
   }

   protected void func_72979_l() {
      if (this.field_73011_w.func_191066_m()) {
         if (!this.field_72995_K) {
            boolean ☃ = this.func_82736_K().func_82766_b("doWeatherCycle");
            if (☃) {
               int ☃x = this.field_72986_A.func_176133_A();
               if (☃x > 0) {
                  this.field_72986_A.func_176142_i(--☃x);
                  this.field_72986_A.func_76090_f(this.field_72986_A.func_76061_m() ? 1 : 2);
                  this.field_72986_A.func_76080_g(this.field_72986_A.func_76059_o() ? 1 : 2);
               }

               int ☃x = this.field_72986_A.func_76071_n();
               if (☃x <= 0) {
                  if (this.field_72986_A.func_76061_m()) {
                     this.field_72986_A.func_76090_f(this.field_73012_v.nextInt(12000) + 3600);
                  } else {
                     this.field_72986_A.func_76090_f(this.field_73012_v.nextInt(168000) + 12000);
                  }
               } else {
                  this.field_72986_A.func_76090_f(--☃x);
                  if (☃x <= 0) {
                     this.field_72986_A.func_76069_a(!this.field_72986_A.func_76061_m());
                  }
               }

               int ☃x = this.field_72986_A.func_76083_p();
               if (☃x <= 0) {
                  if (this.field_72986_A.func_76059_o()) {
                     this.field_72986_A.func_76080_g(this.field_73012_v.nextInt(12000) + 12000);
                  } else {
                     this.field_72986_A.func_76080_g(this.field_73012_v.nextInt(168000) + 12000);
                  }
               } else {
                  this.field_72986_A.func_76080_g(--☃x);
                  if (☃x <= 0) {
                     this.field_72986_A.func_76084_b(!this.field_72986_A.func_76059_o());
                  }
               }
            }

            this.field_73018_p = this.field_73017_q;
            if (this.field_72986_A.func_76061_m()) {
               this.field_73017_q = (float)((double)this.field_73017_q + 0.01);
            } else {
               this.field_73017_q = (float)((double)this.field_73017_q - 0.01);
            }

            this.field_73017_q = MathHelper.func_76131_a(this.field_73017_q, 0.0F, 1.0F);
            this.field_73003_n = this.field_73004_o;
            if (this.field_72986_A.func_76059_o()) {
               this.field_73004_o = (float)((double)this.field_73004_o + 0.01);
            } else {
               this.field_73004_o = (float)((double)this.field_73004_o - 0.01);
            }

            this.field_73004_o = MathHelper.func_76131_a(this.field_73004_o, 0.0F, 1.0F);
         }
      }
   }

   protected void func_147467_a(int var1, int var2, Chunk var3) {
      ☃.func_76594_o();
   }

   protected void func_147456_g() {
   }

   public boolean func_175664_x(BlockPos var1) {
      boolean ☃ = false;
      if (this.field_73011_w.func_191066_m()) {
         ☃ |= this.func_180500_c(EnumLightType.SKY, ☃);
      }

      return ☃ | this.func_180500_c(EnumLightType.BLOCK, ☃);
   }

   private int func_175638_a(BlockPos var1, EnumLightType var2) {
      if (☃ == EnumLightType.SKY && this.func_175678_i(☃)) {
         return 15;
      } else {
         IBlockState ☃ = this.func_180495_p(☃);
         int ☃x = ☃ == EnumLightType.SKY ? 0 : ☃.func_185906_d();
         int ☃xx = ☃.func_200016_a(this, ☃);
         if (☃xx >= 15 && ☃.func_185906_d() > 0) {
            ☃xx = 1;
         }

         if (☃xx < 1) {
            ☃xx = 1;
         }

         if (☃xx >= 15) {
            return 0;
         } else if (☃x >= 14) {
            return ☃x;
         } else {
            try (BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_185346_s()) {
               for(EnumFacing ☃x : field_200007_a) {
                  ☃.func_189533_g(☃).func_189536_c(☃x);
                  int ☃xx = this.func_175642_b(☃, ☃) - ☃xx;
                  if (☃xx > ☃x) {
                     ☃x = ☃xx;
                  }

                  if (☃x >= 14) {
                     return ☃x;
                  }
               }

               return ☃x;
            }
         }
      }
   }

   public boolean func_180500_c(EnumLightType var1, BlockPos var2) {
      if (!this.func_175648_a(☃, 17, false)) {
         return false;
      } else {
         int ☃ = 0;
         int ☃x = 0;
         this.field_72984_F.func_76320_a("getBrightness");
         int ☃xx = this.func_175642_b(☃, ☃);
         int ☃xxx = this.func_175638_a(☃, ☃);
         int ☃xxxx = ☃.func_177958_n();
         int ☃xxxxx = ☃.func_177956_o();
         int ☃xxxxxx = ☃.func_177952_p();
         if (☃xxx > ☃xx) {
            this.field_72994_J[☃x++] = 133152;
         } else if (☃xxx < ☃xx) {
            this.field_72994_J[☃x++] = 133152 | ☃xx << 18;

            while(☃ < ☃x) {
               int ☃ = this.field_72994_J[☃++];
               int ☃x = (☃ & 63) - 32 + ☃xxxx;
               int ☃xx = (☃ >> 6 & 63) - 32 + ☃xxxxx;
               int ☃xxx = (☃ >> 12 & 63) - 32 + ☃xxxxxx;
               int ☃xxxx = ☃ >> 18 & 15;
               BlockPos ☃xxxxx = new BlockPos(☃x, ☃xx, ☃xxx);
               int ☃xxxxxx = this.func_175642_b(☃, ☃xxxxx);
               if (☃xxxxxx == ☃xxxx) {
                  this.func_175653_a(☃, ☃xxxxx, 0);
                  if (☃xxxx > 0) {
                     int ☃xxxxxxx = MathHelper.func_76130_a(☃x - ☃xxxx);
                     int ☃xxxxxxxx = MathHelper.func_76130_a(☃xx - ☃xxxxx);
                     int ☃xxxxxxxxx = MathHelper.func_76130_a(☃xxx - ☃xxxxxx);
                     if (☃xxxxxxx + ☃xxxxxxxx + ☃xxxxxxxxx < 17) {
                        try (BlockPos.PooledMutableBlockPos ☃xxxxxxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
                           for(EnumFacing ☃xxxxxxxxxxx : field_200007_a) {
                              int ☃xxxxxxxxxxxx = ☃x + ☃xxxxxxxxxxx.func_82601_c();
                              int ☃xxxxxxxxxxxxx = ☃xx + ☃xxxxxxxxxxx.func_96559_d();
                              int ☃xxxxxxxxxxxxxx = ☃xxx + ☃xxxxxxxxxxx.func_82599_e();
                              ☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx);
                              int ☃xxxxxxxxxxxxxxx = Math.max(1, this.func_180495_p(☃xxxxxxxxxx).func_200016_a(this, ☃xxxxxxxxxx));
                              ☃xxxxxx = this.func_175642_b(☃, ☃xxxxxxxxxx);
                              if (☃xxxxxx == ☃xxxx - ☃xxxxxxxxxxxxxxx && ☃x < this.field_72994_J.length) {
                                 this.field_72994_J[☃x++] = ☃xxxxxxxxxxxx - ☃xxxx + 32
                                    | ☃xxxxxxxxxxxxx - ☃xxxxx + 32 << 6
                                    | ☃xxxxxxxxxxxxxx - ☃xxxxxx + 32 << 12
                                    | ☃xxxx - ☃xxxxxxxxxxxxxxx << 18;
                              }
                           }
                        }
                     }
                  }
               }
            }

            ☃ = 0;
         }

         this.field_72984_F.func_76319_b();
         this.field_72984_F.func_76320_a("checkedPosition < toCheckCount");

         while(☃ < ☃x) {
            int ☃ = this.field_72994_J[☃++];
            int ☃x = (☃ & 63) - 32 + ☃xxxx;
            int ☃xx = (☃ >> 6 & 63) - 32 + ☃xxxxx;
            int ☃xxx = (☃ >> 12 & 63) - 32 + ☃xxxxxx;
            BlockPos ☃xxxx = new BlockPos(☃x, ☃xx, ☃xxx);
            int ☃xxxxx = this.func_175642_b(☃, ☃xxxx);
            int ☃xxxxxx = this.func_175638_a(☃xxxx, ☃);
            if (☃xxxxxx != ☃xxxxx) {
               this.func_175653_a(☃, ☃xxxx, ☃xxxxxx);
               if (☃xxxxxx > ☃xxxxx) {
                  int ☃xxxxxxx = Math.abs(☃x - ☃xxxx);
                  int ☃xxxxxxxx = Math.abs(☃xx - ☃xxxxx);
                  int ☃xxxxxxxxx = Math.abs(☃xxx - ☃xxxxxx);
                  boolean ☃xxxxxxxxxx = ☃x < this.field_72994_J.length - 6;
                  if (☃xxxxxxx + ☃xxxxxxxx + ☃xxxxxxxxx < 17 && ☃xxxxxxxxxx) {
                     if (this.func_175642_b(☃, ☃xxxx.func_177976_e()) < ☃xxxxxx) {
                        this.field_72994_J[☃x++] = ☃x - 1 - ☃xxxx + 32 + (☃xx - ☃xxxxx + 32 << 6) + (☃xxx - ☃xxxxxx + 32 << 12);
                     }

                     if (this.func_175642_b(☃, ☃xxxx.func_177974_f()) < ☃xxxxxx) {
                        this.field_72994_J[☃x++] = ☃x + 1 - ☃xxxx + 32 + (☃xx - ☃xxxxx + 32 << 6) + (☃xxx - ☃xxxxxx + 32 << 12);
                     }

                     if (this.func_175642_b(☃, ☃xxxx.func_177977_b()) < ☃xxxxxx) {
                        this.field_72994_J[☃x++] = ☃x - ☃xxxx + 32 + (☃xx - 1 - ☃xxxxx + 32 << 6) + (☃xxx - ☃xxxxxx + 32 << 12);
                     }

                     if (this.func_175642_b(☃, ☃xxxx.func_177984_a()) < ☃xxxxxx) {
                        this.field_72994_J[☃x++] = ☃x - ☃xxxx + 32 + (☃xx + 1 - ☃xxxxx + 32 << 6) + (☃xxx - ☃xxxxxx + 32 << 12);
                     }

                     if (this.func_175642_b(☃, ☃xxxx.func_177978_c()) < ☃xxxxxx) {
                        this.field_72994_J[☃x++] = ☃x - ☃xxxx + 32 + (☃xx - ☃xxxxx + 32 << 6) + (☃xxx - 1 - ☃xxxxxx + 32 << 12);
                     }

                     if (this.func_175642_b(☃, ☃xxxx.func_177968_d()) < ☃xxxxxx) {
                        this.field_72994_J[☃x++] = ☃x - ☃xxxx + 32 + (☃xx - ☃xxxxx + 32 << 6) + (☃xxx + 1 - ☃xxxxxx + 32 << 12);
                     }
                  }
               }
            }
         }

         this.field_72984_F.func_76319_b();
         return true;
      }
   }

   @Override
   public Stream<VoxelShape> func_212392_a(@Nullable Entity var1, VoxelShape var2, VoxelShape var3, Set<Entity> var4) {
      Stream<VoxelShape> ☃ = IWorld.super.func_212392_a(☃, ☃, ☃, ☃);
      return ☃ == null ? ☃ : Stream.concat(☃, this.func_211155_a(☃, ☃, ☃));
   }

   @Override
   public List<Entity> func_175674_a(@Nullable Entity var1, AxisAlignedBB var2, @Nullable Predicate<? super Entity> var3) {
      List<Entity> ☃ = Lists.<Entity>newArrayList();
      int ☃x = MathHelper.func_76128_c((☃.field_72340_a - 2.0) / 16.0);
      int ☃xx = MathHelper.func_76128_c((☃.field_72336_d + 2.0) / 16.0);
      int ☃xxx = MathHelper.func_76128_c((☃.field_72339_c - 2.0) / 16.0);
      int ☃xxxx = MathHelper.func_76128_c((☃.field_72334_f + 2.0) / 16.0);

      for(int ☃xxxxx = ☃x; ☃xxxxx <= ☃xx; ++☃xxxxx) {
         for(int ☃xxxxxx = ☃xxx; ☃xxxxxx <= ☃xxxx; ++☃xxxxxx) {
            if (this.func_175680_a(☃xxxxx, ☃xxxxxx, true)) {
               this.func_72964_e(☃xxxxx, ☃xxxxxx).func_177414_a(☃, ☃, ☃, ☃);
            }
         }
      }

      return ☃;
   }

   public <T extends Entity> List<T> func_175644_a(Class<? extends T> var1, Predicate<? super T> var2) {
      List<T> ☃ = Lists.<T>newArrayList();

      for(Entity ☃x : this.field_72996_f) {
         if (☃.isAssignableFrom(☃x.getClass()) && ☃.test(☃x)) {
            ☃.add(☃x);
         }
      }

      return ☃;
   }

   public <T extends Entity> List<T> func_175661_b(Class<? extends T> var1, Predicate<? super T> var2) {
      List<T> ☃ = Lists.<T>newArrayList();

      for(Entity ☃x : this.field_73010_i) {
         if (☃.isAssignableFrom(☃x.getClass()) && ☃.test(☃x)) {
            ☃.add(☃x);
         }
      }

      return ☃;
   }

   public <T extends Entity> List<T> func_72872_a(Class<? extends T> var1, AxisAlignedBB var2) {
      return this.func_175647_a(☃, ☃, EntitySelectors.field_180132_d);
   }

   public <T extends Entity> List<T> func_175647_a(Class<? extends T> var1, AxisAlignedBB var2, @Nullable Predicate<? super T> var3) {
      int ☃ = MathHelper.func_76128_c((☃.field_72340_a - 2.0) / 16.0);
      int ☃x = MathHelper.func_76143_f((☃.field_72336_d + 2.0) / 16.0);
      int ☃xx = MathHelper.func_76128_c((☃.field_72339_c - 2.0) / 16.0);
      int ☃xxx = MathHelper.func_76143_f((☃.field_72334_f + 2.0) / 16.0);
      List<T> ☃xxxx = Lists.<T>newArrayList();

      for(int ☃xxxxx = ☃; ☃xxxxx < ☃x; ++☃xxxxx) {
         for(int ☃xxxxxx = ☃xx; ☃xxxxxx < ☃xxx; ++☃xxxxxx) {
            if (this.func_175680_a(☃xxxxx, ☃xxxxxx, true)) {
               this.func_72964_e(☃xxxxx, ☃xxxxxx).func_177430_a(☃, ☃, ☃xxxx, ☃);
            }
         }
      }

      return ☃xxxx;
   }

   @Nullable
   public <T extends Entity> T func_72857_a(Class<? extends T> var1, AxisAlignedBB var2, T var3) {
      List<T> ☃ = this.func_72872_a(☃, ☃);
      T ☃x = null;
      double ☃xx = Double.MAX_VALUE;

      for(int ☃xxx = 0; ☃xxx < ☃.size(); ++☃xxx) {
         T ☃xxxx = (T)☃.get(☃xxx);
         if (☃xxxx != ☃ && EntitySelectors.field_180132_d.test(☃xxxx)) {
            double ☃xxxxx = ☃.func_70068_e(☃xxxx);
            if (!(☃xxxxx > ☃xx)) {
               ☃x = ☃xxxx;
               ☃xx = ☃xxxxx;
            }
         }
      }

      return ☃x;
   }

   @Nullable
   public Entity func_73045_a(int var1) {
      return this.field_175729_l.func_76041_a(☃);
   }

   public int func_212419_R() {
      return this.field_72996_f.size();
   }

   public void func_175646_b(BlockPos var1, TileEntity var2) {
      if (this.func_175667_e(☃)) {
         this.func_175726_f(☃).func_76630_e();
      }
   }

   public int func_72907_a(Class<?> var1, int var2) {
      int ☃ = 0;

      for(Entity ☃x : this.field_72996_f) {
         if (!(☃x instanceof EntityLiving) || !((EntityLiving)☃x).func_104002_bU()) {
            if (☃.isAssignableFrom(☃x.getClass())) {
               ++☃;
            }

            if (☃ > ☃) {
               return ☃;
            }
         }
      }

      return ☃;
   }

   public void func_212420_a(Stream<Entity> var1) {
      ☃.forEach(var1x -> {
         this.field_72996_f.add(var1x);
         this.func_72923_a(var1x);
      });
   }

   public void func_175681_c(Collection<Entity> var1) {
      this.field_72997_g.addAll(☃);
   }

   @Override
   public int func_181545_F() {
      return this.field_181546_a;
   }

   @Override
   public World func_201672_e() {
      return this;
   }

   public void func_181544_b(int var1) {
      this.field_181546_a = ☃;
   }

   @Override
   public int func_175627_a(BlockPos var1, EnumFacing var2) {
      return this.func_180495_p(☃).func_185893_b(this, ☃, ☃);
   }

   public WorldType func_175624_G() {
      return this.field_72986_A.func_76067_t();
   }

   public int func_175676_y(BlockPos var1) {
      int ☃ = 0;
      ☃ = Math.max(☃, this.func_175627_a(☃.func_177977_b(), EnumFacing.DOWN));
      if (☃ >= 15) {
         return ☃;
      } else {
         ☃ = Math.max(☃, this.func_175627_a(☃.func_177984_a(), EnumFacing.UP));
         if (☃ >= 15) {
            return ☃;
         } else {
            ☃ = Math.max(☃, this.func_175627_a(☃.func_177978_c(), EnumFacing.NORTH));
            if (☃ >= 15) {
               return ☃;
            } else {
               ☃ = Math.max(☃, this.func_175627_a(☃.func_177968_d(), EnumFacing.SOUTH));
               if (☃ >= 15) {
                  return ☃;
               } else {
                  ☃ = Math.max(☃, this.func_175627_a(☃.func_177976_e(), EnumFacing.WEST));
                  if (☃ >= 15) {
                     return ☃;
                  } else {
                     ☃ = Math.max(☃, this.func_175627_a(☃.func_177974_f(), EnumFacing.EAST));
                     return ☃ >= 15 ? ☃ : ☃;
                  }
               }
            }
         }
      }
   }

   public boolean func_175709_b(BlockPos var1, EnumFacing var2) {
      return this.func_175651_c(☃, ☃) > 0;
   }

   public int func_175651_c(BlockPos var1, EnumFacing var2) {
      IBlockState ☃ = this.func_180495_p(☃);
      return ☃.func_185915_l() ? this.func_175676_y(☃) : ☃.func_185911_a(this, ☃, ☃);
   }

   public boolean func_175640_z(BlockPos var1) {
      if (this.func_175651_c(☃.func_177977_b(), EnumFacing.DOWN) > 0) {
         return true;
      } else if (this.func_175651_c(☃.func_177984_a(), EnumFacing.UP) > 0) {
         return true;
      } else if (this.func_175651_c(☃.func_177978_c(), EnumFacing.NORTH) > 0) {
         return true;
      } else if (this.func_175651_c(☃.func_177968_d(), EnumFacing.SOUTH) > 0) {
         return true;
      } else if (this.func_175651_c(☃.func_177976_e(), EnumFacing.WEST) > 0) {
         return true;
      } else {
         return this.func_175651_c(☃.func_177974_f(), EnumFacing.EAST) > 0;
      }
   }

   public int func_175687_A(BlockPos var1) {
      int ☃ = 0;

      for(EnumFacing ☃x : field_200007_a) {
         int ☃xx = this.func_175651_c(☃.func_177972_a(☃x), ☃x);
         if (☃xx >= 15) {
            return 15;
         }

         if (☃xx > ☃) {
            ☃ = ☃xx;
         }
      }

      return ☃;
   }

   @Nullable
   @Override
   public EntityPlayer func_190525_a(double var1, double var3, double var5, double var7, Predicate<Entity> var9) {
      double ☃ = -1.0;
      EntityPlayer ☃x = null;

      for(int ☃xx = 0; ☃xx < this.field_73010_i.size(); ++☃xx) {
         EntityPlayer ☃xxx = (EntityPlayer)this.field_73010_i.get(☃xx);
         if (☃.test(☃xxx)) {
            double ☃xxxx = ☃xxx.func_70092_e(☃, ☃, ☃);
            if ((☃ < 0.0 || ☃xxxx < ☃ * ☃) && (☃ == -1.0 || ☃xxxx < ☃)) {
               ☃ = ☃xxxx;
               ☃x = ☃xxx;
            }
         }
      }

      return ☃x;
   }

   public boolean func_175636_b(double var1, double var3, double var5, double var7) {
      for(int ☃ = 0; ☃ < this.field_73010_i.size(); ++☃) {
         EntityPlayer ☃x = (EntityPlayer)this.field_73010_i.get(☃);
         if (EntitySelectors.field_180132_d.test(☃x)) {
            double ☃xx = ☃x.func_70092_e(☃, ☃, ☃);
            if (☃ < 0.0 || ☃xx < ☃ * ☃) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean func_212417_b(double var1, double var3, double var5, double var7) {
      for(EntityPlayer ☃ : this.field_73010_i) {
         if (EntitySelectors.field_180132_d.test(☃) && EntitySelectors.field_212545_b.test(☃)) {
            double ☃x = ☃.func_70092_e(☃, ☃, ☃);
            if (☃ < 0.0 || ☃x < ☃ * ☃) {
               return true;
            }
         }
      }

      return false;
   }

   @Nullable
   public EntityPlayer func_212817_a(double var1, double var3, double var5) {
      double ☃ = -1.0;
      EntityPlayer ☃x = null;

      for(int ☃xx = 0; ☃xx < this.field_73010_i.size(); ++☃xx) {
         EntityPlayer ☃xxx = (EntityPlayer)this.field_73010_i.get(☃xx);
         if (EntitySelectors.field_180132_d.test(☃xxx)) {
            double ☃xxxx = ☃xxx.func_70092_e(☃, ☃xxx.field_70163_u, ☃);
            if ((☃ < 0.0 || ☃xxxx < ☃ * ☃) && (☃ == -1.0 || ☃xxxx < ☃)) {
               ☃ = ☃xxxx;
               ☃x = ☃xxx;
            }
         }
      }

      return ☃x;
   }

   @Nullable
   public EntityPlayer func_184142_a(Entity var1, double var2, double var4) {
      return this.func_184150_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃, ☃, null, null);
   }

   @Nullable
   public EntityPlayer func_184139_a(BlockPos var1, double var2, double var4) {
      return this.func_184150_a(
         (double)((float)☃.func_177958_n() + 0.5F), (double)((float)☃.func_177956_o() + 0.5F), (double)((float)☃.func_177952_p() + 0.5F), ☃, ☃, null, null
      );
   }

   @Nullable
   public EntityPlayer func_184150_a(
      double var1, double var3, double var5, double var7, double var9, @Nullable Function<EntityPlayer, Double> var11, @Nullable Predicate<EntityPlayer> var12
   ) {
      double ☃ = -1.0;
      EntityPlayer ☃x = null;

      for(int ☃xx = 0; ☃xx < this.field_73010_i.size(); ++☃xx) {
         EntityPlayer ☃xxx = (EntityPlayer)this.field_73010_i.get(☃xx);
         if (!☃xxx.field_71075_bZ.field_75102_a && ☃xxx.func_70089_S() && !☃xxx.func_175149_v() && (☃ == null || ☃.test(☃xxx))) {
            double ☃xxxx = ☃xxx.func_70092_e(☃, ☃xxx.field_70163_u, ☃);
            double ☃xxxxx = ☃;
            if (☃xxx.func_70093_af()) {
               ☃xxxxx = ☃ * 0.8F;
            }

            if (☃xxx.func_82150_aj()) {
               float ☃xxxx = ☃xxx.func_82243_bO();
               if (☃xxxx < 0.1F) {
                  ☃xxxx = 0.1F;
               }

               ☃xxxxx *= (double)(0.7F * ☃xxxx);
            }

            if (☃ != null) {
               ☃xxxxx *= MoreObjects.firstNonNull(☃.apply(☃xxx), 1.0);
            }

            if ((☃ < 0.0 || Math.abs(☃xxx.field_70163_u - ☃) < ☃ * ☃) && (☃ < 0.0 || ☃xxxx < ☃xxxxx * ☃xxxxx) && (☃ == -1.0 || ☃xxxx < ☃)) {
               ☃ = ☃xxxx;
               ☃x = ☃xxx;
            }
         }
      }

      return ☃x;
   }

   @Nullable
   public EntityPlayer func_72924_a(String var1) {
      for(int ☃ = 0; ☃ < this.field_73010_i.size(); ++☃) {
         EntityPlayer ☃x = (EntityPlayer)this.field_73010_i.get(☃);
         if (☃.equals(☃x.func_200200_C_().getString())) {
            return ☃x;
         }
      }

      return null;
   }

   @Nullable
   public EntityPlayer func_152378_a(UUID var1) {
      for(int ☃ = 0; ☃ < this.field_73010_i.size(); ++☃) {
         EntityPlayer ☃x = (EntityPlayer)this.field_73010_i.get(☃);
         if (☃.equals(☃x.func_110124_au())) {
            return ☃x;
         }
      }

      return null;
   }

   public void func_72882_A() {
   }

   public void func_72906_B() throws SessionLockException {
      this.field_73019_z.func_75762_c();
   }

   public void func_82738_a(long var1) {
      this.field_72986_A.func_82572_b(☃);
   }

   @Override
   public long func_72905_C() {
      return this.field_72986_A.func_76063_b();
   }

   public long func_82737_E() {
      return this.field_72986_A.func_82573_f();
   }

   public long func_72820_D() {
      return this.field_72986_A.func_76073_f();
   }

   public void func_72877_b(long var1) {
      this.field_72986_A.func_76068_b(☃);
   }

   @Override
   public BlockPos func_175694_M() {
      BlockPos ☃ = new BlockPos(this.field_72986_A.func_76079_c(), this.field_72986_A.func_76075_d(), this.field_72986_A.func_76074_e());
      if (!this.func_175723_af().func_177746_a(☃)) {
         ☃ = this.func_205770_a(Heightmap.Type.MOTION_BLOCKING, new BlockPos(this.func_175723_af().func_177731_f(), 0.0, this.func_175723_af().func_177721_g()));
      }

      return ☃;
   }

   public void func_175652_B(BlockPos var1) {
      this.field_72986_A.func_176143_a(☃);
   }

   public void func_72897_h(Entity var1) {
      int ☃ = MathHelper.func_76128_c(☃.field_70165_t / 16.0);
      int ☃x = MathHelper.func_76128_c(☃.field_70161_v / 16.0);
      int ☃xx = 2;

      for(int ☃xxx = -2; ☃xxx <= 2; ++☃xxx) {
         for(int ☃xxxx = -2; ☃xxxx <= 2; ++☃xxxx) {
            this.func_72964_e(☃ + ☃xxx, ☃x + ☃xxxx);
         }
      }

      if (!this.field_72996_f.contains(☃)) {
         this.field_72996_f.add(☃);
      }
   }

   public boolean func_175660_a(EntityPlayer var1, BlockPos var2) {
      return true;
   }

   public void func_72960_a(Entity var1, byte var2) {
   }

   @Override
   public IChunkProvider func_72863_F() {
      return this.field_73020_y;
   }

   public void func_175641_c(BlockPos var1, Block var2, int var3, int var4) {
      this.func_180495_p(☃).func_189547_a(this, ☃, ☃, ☃);
   }

   @Override
   public ISaveHandler func_72860_G() {
      return this.field_73019_z;
   }

   @Override
   public WorldInfo func_72912_H() {
      return this.field_72986_A;
   }

   public GameRules func_82736_K() {
      return this.field_72986_A.func_82574_x();
   }

   public void func_72854_c() {
   }

   public float func_72819_i(float var1) {
      return (this.field_73018_p + (this.field_73017_q - this.field_73018_p) * ☃) * this.func_72867_j(☃);
   }

   public void func_147442_i(float var1) {
      this.field_73018_p = ☃;
      this.field_73017_q = ☃;
   }

   public float func_72867_j(float var1) {
      return this.field_73003_n + (this.field_73004_o - this.field_73003_n) * ☃;
   }

   public void func_72894_k(float var1) {
      this.field_73003_n = ☃;
      this.field_73004_o = ☃;
   }

   public boolean func_72911_I() {
      if (this.field_73011_w.func_191066_m() && !this.field_73011_w.func_177495_o()) {
         return (double)this.func_72819_i(1.0F) > 0.9;
      } else {
         return false;
      }
   }

   public boolean func_72896_J() {
      return (double)this.func_72867_j(1.0F) > 0.2;
   }

   public boolean func_175727_C(BlockPos var1) {
      if (!this.func_72896_J()) {
         return false;
      } else if (!this.func_175678_i(☃)) {
         return false;
      } else if (this.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃).func_177956_o() > ☃.func_177956_o()) {
         return false;
      } else {
         return this.func_180494_b(☃).func_201851_b() == Biome.RainType.RAIN;
      }
   }

   public boolean func_180502_D(BlockPos var1) {
      Biome ☃ = this.func_180494_b(☃);
      return ☃.func_76736_e();
   }

   @Nullable
   @Override
   public WorldSavedDataStorage func_175693_T() {
      return this.field_72988_C;
   }

   public void func_175669_a(int var1, BlockPos var2, int var3) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         ((IWorldEventListener)this.field_73021_x.get(☃)).func_180440_a(☃, ☃, ☃);
      }
   }

   public void func_175718_b(int var1, BlockPos var2, int var3) {
      this.func_180498_a(null, ☃, ☃, ☃);
   }

   public void func_180498_a(@Nullable EntityPlayer var1, int var2, BlockPos var3, int var4) {
      try {
         for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
            ((IWorldEventListener)this.field_73021_x.get(☃)).func_180439_a(☃, ☃, ☃, ☃);
         }
      } catch (Throwable var8) {
         CrashReport ☃ = CrashReport.func_85055_a(var8, "Playing level event");
         CrashReportCategory ☃x = ☃.func_85058_a("Level event being played");
         ☃x.func_71507_a("Block coordinates", CrashReportCategory.func_180522_a(☃));
         ☃x.func_71507_a("Event source", ☃);
         ☃x.func_71507_a("Event type", ☃);
         ☃x.func_71507_a("Event data", ☃);
         throw new ReportedException(☃);
      }
   }

   public int func_72800_K() {
      return 256;
   }

   public int func_72940_L() {
      return this.field_73011_w.func_177495_o() ? 128 : 256;
   }

   public double func_72919_O() {
      return this.field_72986_A.func_76067_t() == WorldType.field_77138_c ? 0.0 : 63.0;
   }

   public CrashReportCategory func_72914_a(CrashReport var1) {
      CrashReportCategory ☃ = ☃.func_85057_a("Affected level", 1);
      ☃.func_71507_a("Level name", this.field_72986_A == null ? "????" : this.field_72986_A.func_76065_j());
      ☃.func_189529_a("All players", () -> this.field_73010_i.size() + " total; " + this.field_73010_i);
      ☃.func_189529_a("Chunk stats", () -> this.field_73020_y.func_73148_d());

      try {
         this.field_72986_A.func_85118_a(☃);
      } catch (Throwable var4) {
         ☃.func_71499_a("Level Data Unobtainable", var4);
      }

      return ☃;
   }

   public void func_175715_c(int var1, BlockPos var2, int var3) {
      for(int ☃ = 0; ☃ < this.field_73021_x.size(); ++☃) {
         IWorldEventListener ☃x = (IWorldEventListener)this.field_73021_x.get(☃);
         ☃x.func_180441_b(☃, ☃, ☃);
      }
   }

   public void func_92088_a(double var1, double var3, double var5, double var7, double var9, double var11, @Nullable NBTTagCompound var13) {
   }

   public abstract Scoreboard func_96441_U();

   public void func_175666_e(BlockPos var1, Block var2) {
      for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
         BlockPos ☃x = ☃.func_177972_a(☃);
         if (this.func_175667_e(☃x)) {
            IBlockState ☃xx = this.func_180495_p(☃x);
            if (☃xx.func_177230_c() == Blocks.field_196762_fd) {
               ☃xx.func_189546_a(this, ☃x, ☃, ☃);
            } else if (☃xx.func_185915_l()) {
               ☃x = ☃x.func_177972_a(☃);
               ☃xx = this.func_180495_p(☃x);
               if (☃xx.func_177230_c() == Blocks.field_196762_fd) {
                  ☃xx.func_189546_a(this, ☃x, ☃, ☃);
               }
            }
         }
      }
   }

   @Override
   public DifficultyInstance func_175649_E(BlockPos var1) {
      long ☃ = 0L;
      float ☃x = 0.0F;
      if (this.func_175667_e(☃)) {
         ☃x = this.func_130001_d();
         ☃ = this.func_175726_f(☃).func_177416_w();
      }

      return new DifficultyInstance(this.func_175659_aa(), this.func_72820_D(), ☃, ☃x);
   }

   @Override
   public int func_175657_ab() {
      return this.field_73008_k;
   }

   public void func_175692_b(int var1) {
      this.field_73008_k = ☃;
   }

   public int func_175658_ac() {
      return this.field_73016_r;
   }

   public void func_175702_c(int var1) {
      this.field_73016_r = ☃;
   }

   public VillageCollection func_175714_ae() {
      return this.field_72982_D;
   }

   @Override
   public WorldBorder func_175723_af() {
      return this.field_175728_M;
   }

   public boolean func_72916_c(int var1, int var2) {
      BlockPos ☃ = this.func_175694_M();
      int ☃x = ☃ * 16 + 8 - ☃.func_177958_n();
      int ☃xx = ☃ * 16 + 8 - ☃.func_177952_p();
      int ☃xxx = 128;
      return ☃x >= -128 && ☃x <= 128 && ☃xx >= -128 && ☃xx <= 128;
   }

   public LongSet func_212412_ag() {
      ForcedChunksSaveData ☃ = this.func_212411_a(this.field_73011_w.func_186058_p(), ForcedChunksSaveData::new, "chunks");
      return (LongSet)(☃ != null ? LongSets.unmodifiable(☃.func_212438_a()) : LongSets.EMPTY_SET);
   }

   public boolean func_212416_f(int var1, int var2) {
      ForcedChunksSaveData ☃ = this.func_212411_a(this.field_73011_w.func_186058_p(), ForcedChunksSaveData::new, "chunks");
      return ☃ != null && ☃.func_212438_a().contains(ChunkPos.func_77272_a(☃, ☃));
   }

   public boolean func_212414_b(int var1, int var2, boolean var3) {
      String ☃ = "chunks";
      ForcedChunksSaveData ☃x = this.func_212411_a(this.field_73011_w.func_186058_p(), ForcedChunksSaveData::new, "chunks");
      if (☃x == null) {
         ☃x = new ForcedChunksSaveData("chunks");
         this.func_212409_a(this.field_73011_w.func_186058_p(), "chunks", ☃x);
      }

      long ☃x = ChunkPos.func_77272_a(☃, ☃);
      boolean ☃;
      if (☃) {
         ☃ = ☃x.func_212438_a().add(☃x);
         if (☃) {
            this.func_72964_e(☃, ☃);
         }
      } else {
         ☃ = ☃x.func_212438_a().remove(☃x);
      }

      ☃x.func_76186_a(☃);
      return ☃;
   }

   public void func_184135_a(Packet<?> var1) {
      throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
   }

   @Nullable
   public BlockPos func_211157_a(String var1, BlockPos var2, int var3, boolean var4) {
      return null;
   }

   @Override
   public Dimension func_201675_m() {
      return this.field_73011_w;
   }

   @Override
   public Random func_201674_k() {
      return this.field_73012_v;
   }

   public abstract RecipeManager func_199532_z();

   public abstract NetworkTagManager func_205772_D();
}
