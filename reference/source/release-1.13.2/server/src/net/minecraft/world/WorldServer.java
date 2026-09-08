package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BonusChestFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SessionLockException;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedDataCallableSave;
import net.minecraft.world.storage.WorldSavedDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServer extends World implements IThreadListener {
   private static final Logger field_147491_a = LogManager.getLogger();
   private final MinecraftServer field_73061_a;
   private final EntityTracker field_73062_L;
   private final PlayerChunkMap field_73063_M;
   private final Map<UUID, Entity> field_175741_N = Maps.newHashMap();
   public boolean field_73058_d;
   private boolean field_73068_P;
   private int field_80004_Q;
   private final Teleporter field_85177_Q;
   private final WorldEntitySpawner field_175742_R = new WorldEntitySpawner();
   private final ServerTickList<Block> field_94579_S = new ServerTickList<>(
      this,
      var0 -> var0 == null || var0.func_176223_P().func_196958_f(),
      IRegistry.field_212618_g::func_177774_c,
      IRegistry.field_212618_g::func_82594_a,
      this::func_205338_b
   );
   private final ServerTickList<Fluid> field_205342_P = new ServerTickList<>(
      this,
      var0 -> var0 == null || var0 == Fluids.field_204541_a,
      IRegistry.field_212619_h::func_177774_c,
      IRegistry.field_212619_h::func_82594_a,
      this::func_205339_a
   );
   protected final VillageSiege field_175740_d = new VillageSiege(this);
   ObjectLinkedOpenHashSet<BlockEventData> field_147490_S = new ObjectLinkedOpenHashSet<>();
   private boolean field_211159_Q;

   public WorldServer(MinecraftServer var1, ISaveHandler var2, WorldSavedDataStorage var3, WorldInfo var4, DimensionType var5, Profiler var6) {
      super(☃, ☃, ☃, ☃.func_186070_d(), ☃, false);
      this.field_73061_a = ☃;
      this.field_73062_L = new EntityTracker(this);
      this.field_73063_M = new PlayerChunkMap(this);
      this.field_73011_w.func_76558_a(this);
      this.field_73020_y = this.func_72970_h();
      this.field_85177_Q = new Teleporter(this);
      this.func_72966_v();
      this.func_72947_a();
      this.func_175723_af().func_177725_a(☃.func_175580_aG());
   }

   public WorldServer func_212251_i__() {
      String ☃ = VillageCollection.func_176062_a(this.field_73011_w);
      VillageCollection ☃x = this.func_212411_a(DimensionType.OVERWORLD, VillageCollection::new, ☃);
      if (☃x == null) {
         this.field_72982_D = new VillageCollection(this);
         this.func_212409_a(DimensionType.OVERWORLD, ☃, this.field_72982_D);
      } else {
         this.field_72982_D = ☃x;
         this.field_72982_D.func_82566_a(this);
      }

      ScoreboardSaveData ☃ = this.func_212411_a(DimensionType.OVERWORLD, ScoreboardSaveData::new, "scoreboard");
      if (☃ == null) {
         ☃ = new ScoreboardSaveData();
         this.func_212409_a(DimensionType.OVERWORLD, "scoreboard", ☃);
      }

      ☃.func_96499_a(this.field_73061_a.func_200251_aP());
      this.field_73061_a.func_200251_aP().func_186684_a(new WorldSavedDataCallableSave(☃));
      this.func_175723_af().func_177739_c(this.field_72986_A.func_176120_C(), this.field_72986_A.func_176126_D());
      this.func_175723_af().func_177744_c(this.field_72986_A.func_176140_I());
      this.func_175723_af().func_177724_b(this.field_72986_A.func_176138_H());
      this.func_175723_af().func_177747_c(this.field_72986_A.func_176131_J());
      this.func_175723_af().func_177723_b(this.field_72986_A.func_176139_K());
      if (this.field_72986_A.func_176134_F() > 0L) {
         this.func_175723_af().func_177738_a(this.field_72986_A.func_176137_E(), this.field_72986_A.func_176132_G(), this.field_72986_A.func_176134_F());
      } else {
         this.func_175723_af().func_177750_a(this.field_72986_A.func_176137_E());
      }

      return this;
   }

   @Override
   public void func_72835_b(BooleanSupplier var1) {
      this.field_211159_Q = true;
      super.func_72835_b(☃);
      if (this.func_72912_H().func_76093_s() && this.func_175659_aa() != EnumDifficulty.HARD) {
         this.func_72912_H().func_176144_a(EnumDifficulty.HARD);
      }

      this.field_73020_y.func_201711_g().func_202090_b().func_73660_a();
      if (this.func_73056_e()) {
         if (this.func_82736_K().func_82766_b("doDaylightCycle")) {
            long ☃ = this.field_72986_A.func_76073_f() + 24000L;
            this.field_72986_A.func_76068_b(☃ - ☃ % 24000L);
         }

         this.func_73053_d();
      }

      this.field_72984_F.func_76320_a("spawner");
      if (this.func_82736_K().func_82766_b("doMobSpawning") && this.field_72986_A.func_76067_t() != WorldType.field_180272_g) {
         this.field_175742_R.func_77192_a(this, this.field_72985_G, this.field_72992_H, this.field_72986_A.func_82573_f() % 400L == 0L);
         this.func_72863_F().func_203082_a(this, this.field_72985_G, this.field_72992_H);
      }

      this.field_72984_F.func_76318_c("chunkSource");
      this.field_73020_y.func_73156_b(☃);
      int ☃ = this.func_72967_a(1.0F);
      if (☃ != this.func_175657_ab()) {
         this.func_175692_b(☃);
      }

      this.field_72986_A.func_82572_b(this.field_72986_A.func_82573_f() + 1L);
      if (this.func_82736_K().func_82766_b("doDaylightCycle")) {
         this.field_72986_A.func_76068_b(this.field_72986_A.func_76073_f() + 1L);
      }

      this.field_72984_F.func_76318_c("tickPending");
      this.func_72955_a();
      this.field_72984_F.func_76318_c("tickBlocks");
      this.func_147456_g();
      this.field_72984_F.func_76318_c("chunkMap");
      this.field_73063_M.func_72693_b();
      this.field_72984_F.func_76318_c("village");
      this.field_72982_D.func_75544_a();
      this.field_175740_d.func_75528_a();
      this.field_72984_F.func_76318_c("portalForcer");
      this.field_85177_Q.func_85189_a(this.func_82737_E());
      this.field_72984_F.func_76319_b();
      this.func_147488_Z();
      this.field_211159_Q = false;
   }

   public boolean func_211158_j_() {
      return this.field_211159_Q;
   }

   @Nullable
   public Biome.SpawnListEntry func_175734_a(EnumCreatureType var1, BlockPos var2) {
      List<Biome.SpawnListEntry> ☃ = this.func_72863_F().func_177458_a(☃, ☃);
      return ☃.isEmpty() ? null : WeightedRandom.func_76271_a(this.field_73012_v, ☃);
   }

   public boolean func_175732_a(EnumCreatureType var1, Biome.SpawnListEntry var2, BlockPos var3) {
      List<Biome.SpawnListEntry> ☃ = this.func_72863_F().func_177458_a(☃, ☃);
      return ☃ != null && !☃.isEmpty() ? ☃.contains(☃) : false;
   }

   @Override
   public void func_72854_c() {
      this.field_73068_P = false;
      if (!this.field_73010_i.isEmpty()) {
         int ☃ = 0;
         int ☃x = 0;

         for(EntityPlayer ☃xx : this.field_73010_i) {
            if (☃xx.func_175149_v()) {
               ++☃;
            } else if (☃xx.func_70608_bn()) {
               ++☃x;
            }
         }

         this.field_73068_P = ☃x > 0 && ☃x >= this.field_73010_i.size() - ☃;
      }
   }

   public ServerScoreboard func_96441_U() {
      return this.field_73061_a.func_200251_aP();
   }

   protected void func_73053_d() {
      this.field_73068_P = false;

      for(EntityPlayer ☃ : (List)this.field_73010_i.stream().filter(EntityPlayer::func_70608_bn).collect(Collectors.toList())) {
         ☃.func_70999_a(false, false, true);
      }

      if (this.func_82736_K().func_82766_b("doWeatherCycle")) {
         this.func_73051_P();
      }
   }

   private void func_73051_P() {
      this.field_72986_A.func_76080_g(0);
      this.field_72986_A.func_76084_b(false);
      this.field_72986_A.func_76090_f(0);
      this.field_72986_A.func_76069_a(false);
   }

   public boolean func_73056_e() {
      if (this.field_73068_P && !this.field_72995_K) {
         for(EntityPlayer ☃ : this.field_73010_i) {
            if (!☃.func_175149_v() && !☃.func_71026_bH()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean func_175680_a(int var1, int var2, boolean var3) {
      return this.func_201697_a(☃, ☃);
   }

   public boolean func_201697_a(int var1, int var2) {
      return this.func_72863_F().func_73149_a(☃, ☃);
   }

   protected void func_184162_i() {
      this.field_72984_F.func_76320_a("playerCheckLight");
      if (!this.field_73010_i.isEmpty()) {
         int ☃ = this.field_73012_v.nextInt(this.field_73010_i.size());
         EntityPlayer ☃x = (EntityPlayer)this.field_73010_i.get(☃);
         int ☃xx = MathHelper.func_76128_c(☃x.field_70165_t) + this.field_73012_v.nextInt(11) - 5;
         int ☃xxx = MathHelper.func_76128_c(☃x.field_70163_u) + this.field_73012_v.nextInt(11) - 5;
         int ☃xxxx = MathHelper.func_76128_c(☃x.field_70161_v) + this.field_73012_v.nextInt(11) - 5;
         this.func_175664_x(new BlockPos(☃xx, ☃xxx, ☃xxxx));
      }

      this.field_72984_F.func_76319_b();
   }

   @Override
   protected void func_147456_g() {
      this.func_184162_i();
      if (this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
         Iterator<Chunk> ☃ = this.field_73063_M.func_187300_b();

         while(☃.hasNext()) {
            ((Chunk)☃.next()).func_150804_b(false);
         }
      } else {
         int ☃ = this.func_82736_K().func_180263_c("randomTickSpeed");
         boolean ☃x = this.func_72896_J();
         boolean ☃xx = this.func_72911_I();
         this.field_72984_F.func_76320_a("pollingChunks");

         for(Iterator<Chunk> ☃xxx = this.field_73063_M.func_187300_b(); ☃xxx.hasNext(); this.field_72984_F.func_76319_b()) {
            this.field_72984_F.func_76320_a("getChunk");
            Chunk ☃xxxx = (Chunk)☃xxx.next();
            int ☃xxxxx = ☃xxxx.field_76635_g * 16;
            int ☃xxxxxx = ☃xxxx.field_76647_h * 16;
            this.field_72984_F.func_76318_c("checkNextLight");
            ☃xxxx.func_76594_o();
            this.field_72984_F.func_76318_c("tickChunk");
            ☃xxxx.func_150804_b(false);
            this.field_72984_F.func_76318_c("thunder");
            if (☃x && ☃xx && this.field_73012_v.nextInt(100000) == 0) {
               this.field_73005_l = this.field_73005_l * 3 + 1013904223;
               int ☃xxxxxxx = this.field_73005_l >> 2;
               BlockPos ☃xxxxxxxx = this.func_175736_a(new BlockPos(☃xxxxx + (☃xxxxxxx & 15), 0, ☃xxxxxx + (☃xxxxxxx >> 8 & 15)));
               if (this.func_175727_C(☃xxxxxxxx)) {
                  DifficultyInstance ☃xxxxxxxxx = this.func_175649_E(☃xxxxxxxx);
                  boolean ☃xxxxxxxxxx = this.func_82736_K().func_82766_b("doMobSpawning")
                     && this.field_73012_v.nextDouble() < (double)☃xxxxxxxxx.func_180168_b() * 0.01;
                  if (☃xxxxxxxxxx) {
                     EntitySkeletonHorse ☃xxxxxxxxxxx = new EntitySkeletonHorse(this);
                     ☃xxxxxxxxxxx.func_190691_p(true);
                     ☃xxxxxxxxxxx.func_70873_a(0);
                     ☃xxxxxxxxxxx.func_70107_b((double)☃xxxxxxxx.func_177958_n(), (double)☃xxxxxxxx.func_177956_o(), (double)☃xxxxxxxx.func_177952_p());
                     this.func_72838_d(☃xxxxxxxxxxx);
                  }

                  this.func_72942_c(
                     new EntityLightningBolt(
                        this, (double)☃xxxxxxxx.func_177958_n() + 0.5, (double)☃xxxxxxxx.func_177956_o(), (double)☃xxxxxxxx.func_177952_p() + 0.5, ☃xxxxxxxxxx
                     )
                  );
               }
            }

            this.field_72984_F.func_76318_c("iceandsnow");
            if (this.field_73012_v.nextInt(16) == 0) {
               this.field_73005_l = this.field_73005_l * 3 + 1013904223;
               int ☃xxxx = this.field_73005_l >> 2;
               BlockPos ☃xxxxx = this.func_205770_a(Heightmap.Type.MOTION_BLOCKING, new BlockPos(☃xxxxx + (☃xxxx & 15), 0, ☃xxxxxx + (☃xxxx >> 8 & 15)));
               BlockPos ☃xxxxxx = ☃xxxxx.func_177977_b();
               Biome ☃xxxxxxx = this.func_180494_b(☃xxxxx);
               if (☃xxxxxxx.func_201848_a(this, ☃xxxxxx)) {
                  this.func_175656_a(☃xxxxxx, Blocks.field_150432_aD.func_176223_P());
               }

               if (☃x && ☃xxxxxxx.func_201850_b(this, ☃xxxxx)) {
                  this.func_175656_a(☃xxxxx, Blocks.field_150433_aE.func_176223_P());
               }

               if (☃x && this.func_180494_b(☃xxxxxx).func_201851_b() == Biome.RainType.RAIN) {
                  this.func_180495_p(☃xxxxxx).func_177230_c().func_176224_k(this, ☃xxxxxx);
               }
            }

            this.field_72984_F.func_76318_c("tickBlocks");
            if (☃ > 0) {
               for(ChunkSection ☃xxxx : ☃xxxx.func_76587_i()) {
                  if (☃xxxx != Chunk.field_186036_a && ☃xxxx.func_206915_b()) {
                     for(int ☃xxxxx = 0; ☃xxxxx < ☃; ++☃xxxxx) {
                        this.field_73005_l = this.field_73005_l * 3 + 1013904223;
                        int ☃xxxxxx = this.field_73005_l >> 2;
                        int ☃xxxxxxx = ☃xxxxxx & 15;
                        int ☃xxxxxxxx = ☃xxxxxx >> 8 & 15;
                        int ☃xxxxxxxxx = ☃xxxxxx >> 16 & 15;
                        IBlockState ☃xxxxxxxxxx = ☃xxxx.func_177485_a(☃xxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxx);
                        IFluidState ☃xxxxxxxxxxx = ☃xxxx.func_206914_b(☃xxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxx);
                        this.field_72984_F.func_76320_a("randomTick");
                        if (☃xxxxxxxxxx.func_204519_t()) {
                           ☃xxxxxxxxxx.func_196944_b(
                              this, new BlockPos(☃xxxxxxx + ☃xxxxx, ☃xxxxxxxxx + ☃xxxx.func_76662_d(), ☃xxxxxxxx + ☃xxxxxx), this.field_73012_v
                           );
                        }

                        if (☃xxxxxxxxxxx.func_206890_h()) {
                           ☃xxxxxxxxxxx.func_206891_b(
                              this, new BlockPos(☃xxxxxxx + ☃xxxxx, ☃xxxxxxxxx + ☃xxxx.func_76662_d(), ☃xxxxxxxx + ☃xxxxxx), this.field_73012_v
                           );
                        }

                        this.field_72984_F.func_76319_b();
                     }
                  }
               }
            }
         }

         this.field_72984_F.func_76319_b();
      }
   }

   protected BlockPos func_175736_a(BlockPos var1) {
      BlockPos ☃ = this.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃);
      AxisAlignedBB ☃x = new AxisAlignedBB(☃, new BlockPos(☃.func_177958_n(), this.func_72800_K(), ☃.func_177952_p())).func_186662_g(3.0);
      List<EntityLivingBase> ☃xx = this.func_175647_a(
         EntityLivingBase.class, ☃x, var1x -> var1x != null && var1x.func_70089_S() && this.func_175678_i(var1x.func_180425_c())
      );
      if (!☃xx.isEmpty()) {
         return ((EntityLivingBase)☃xx.get(this.field_73012_v.nextInt(☃xx.size()))).func_180425_c();
      } else {
         if (☃.func_177956_o() == -1) {
            ☃ = ☃.func_177981_b(2);
         }

         return ☃;
      }
   }

   @Override
   public void func_72939_s() {
      if (this.field_73010_i.isEmpty()) {
         if (this.field_80004_Q++ >= 300) {
            return;
         }
      } else {
         this.func_82742_i();
      }

      this.field_73011_w.func_186059_r();
      super.func_72939_s();
   }

   @Override
   protected void func_184147_l() {
      super.func_184147_l();
      this.field_72984_F.func_76318_c("players");

      for(int ☃ = 0; ☃ < this.field_73010_i.size(); ++☃) {
         Entity ☃x = (Entity)this.field_73010_i.get(☃);
         Entity ☃xx = ☃x.func_184187_bx();
         if (☃xx != null) {
            if (!☃xx.field_70128_L && ☃xx.func_184196_w(☃x)) {
               continue;
            }

            ☃x.func_184210_p();
         }

         this.field_72984_F.func_76320_a("tick");
         if (!☃x.field_70128_L) {
            try {
               this.func_72870_g(☃x);
            } catch (Throwable var7) {
               CrashReport ☃x = CrashReport.func_85055_a(var7, "Ticking player");
               CrashReportCategory ☃xx = ☃x.func_85058_a("Player being ticked");
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

            this.field_72996_f.remove(☃x);
            this.func_72847_b(☃x);
         }

         this.field_72984_F.func_76319_b();
      }
   }

   public void func_82742_i() {
      this.field_80004_Q = 0;
   }

   public void func_72955_a() {
      if (this.field_72986_A.func_76067_t() != WorldType.field_180272_g) {
         this.field_94579_S.func_205365_a();
         this.field_205342_P.func_205365_a();
      }
   }

   private void func_205339_a(NextTickListEntry<Fluid> var1) {
      IFluidState ☃ = this.func_204610_c(☃.field_180282_a);
      if (☃.func_206886_c() == ☃.func_151351_a()) {
         ☃.func_206880_a(this, ☃.field_180282_a);
      }
   }

   private void func_205338_b(NextTickListEntry<Block> var1) {
      IBlockState ☃ = this.func_180495_p(☃.field_180282_a);
      if (☃.func_177230_c() == ☃.func_151351_a()) {
         ☃.func_196940_a(this, ☃.field_180282_a, this.field_73012_v);
      }
   }

   @Override
   public void func_72866_a(Entity var1, boolean var2) {
      if (!this.func_175735_ai() && (☃ instanceof EntityAnimal || ☃ instanceof EntityWaterMob)) {
         ☃.func_70106_y();
      }

      if (!this.func_175738_ah() && ☃ instanceof INpc) {
         ☃.func_70106_y();
      }

      super.func_72866_a(☃, ☃);
   }

   private boolean func_175738_ah() {
      return this.field_73061_a.func_71220_V();
   }

   private boolean func_175735_ai() {
      return this.field_73061_a.func_71268_U();
   }

   @Override
   protected IChunkProvider func_72970_h() {
      IChunkLoader ☃ = this.field_73019_z.func_75763_a(this.field_73011_w);
      return new ChunkProviderServer(this, ☃, this.field_73011_w.func_186060_c(), this.field_73061_a);
   }

   @Override
   public boolean func_175660_a(EntityPlayer var1, BlockPos var2) {
      return !this.field_73061_a.func_175579_a(this, ☃, ☃) && this.func_175723_af().func_177746_a(☃);
   }

   @Override
   public void func_72963_a(WorldSettings var1) {
      if (!this.field_72986_A.func_76070_v()) {
         try {
            this.func_73052_b(☃);
            if (this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
               this.func_175737_aj();
            }

            super.func_72963_a(☃);
         } catch (Throwable var6) {
            CrashReport ☃ = CrashReport.func_85055_a(var6, "Exception initializing level");

            try {
               this.func_72914_a(☃);
            } catch (Throwable var5) {
            }

            throw new ReportedException(☃);
         }

         this.field_72986_A.func_76091_d(true);
      }
   }

   private void func_175737_aj() {
      this.field_72986_A.func_176128_f(false);
      this.field_72986_A.func_176121_c(true);
      this.field_72986_A.func_76084_b(false);
      this.field_72986_A.func_76069_a(false);
      this.field_72986_A.func_176142_i(1000000000);
      this.field_72986_A.func_76068_b(6000L);
      this.field_72986_A.func_76060_a(GameType.SPECTATOR);
      this.field_72986_A.func_176119_g(false);
      this.field_72986_A.func_176144_a(EnumDifficulty.PEACEFUL);
      this.field_72986_A.func_180783_e(true);
      this.func_82736_K().func_82764_b("doDaylightCycle", "false", this.field_73061_a);
   }

   private void func_73052_b(WorldSettings var1) {
      if (!this.field_73011_w.func_76567_e()) {
         this.field_72986_A.func_176143_a(BlockPos.field_177992_a.func_177981_b(this.field_73020_y.func_201711_g().func_205470_d()));
      } else if (this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
         this.field_72986_A.func_176143_a(BlockPos.field_177992_a.func_177984_a());
      } else {
         BiomeProvider ☃ = this.field_73020_y.func_201711_g().func_202090_b();
         List<Biome> ☃x = ☃.func_76932_a();
         Random ☃xx = new Random(this.func_72905_C());
         BlockPos ☃xxx = ☃.func_180630_a(0, 0, 256, ☃x, ☃xx);
         ChunkPos ☃xxxx = ☃xxx == null ? new ChunkPos(0, 0) : new ChunkPos(☃xxx);
         if (☃xxx == null) {
            field_147491_a.warn("Unable to find spawn biome");
         }

         boolean ☃ = false;

         for(Block ☃x : BlockTags.field_205599_H.func_199885_a()) {
            if (☃.func_205706_b().contains(☃x.func_176223_P())) {
               ☃ = true;
               break;
            }
         }

         this.field_72986_A.func_176143_a(☃xxxx.func_206849_h().func_177982_a(8, this.field_73020_y.func_201711_g().func_205470_d(), 8));
         int ☃x = 0;
         int ☃xx = 0;
         int ☃xxx = 0;
         int ☃xxxx = -1;
         int ☃xxxxx = 32;

         for(int ☃xxxxxx = 0; ☃xxxxxx < 1024; ++☃xxxxxx) {
            if (☃x > -16 && ☃x <= 16 && ☃xx > -16 && ☃xx <= 16) {
               BlockPos ☃xxxxxxx = this.field_73011_w.func_206920_a(new ChunkPos(☃xxxx.field_77276_a + ☃x, ☃xxxx.field_77275_b + ☃xx), ☃);
               if (☃xxxxxxx != null) {
                  this.field_72986_A.func_176143_a(☃xxxxxxx);
                  break;
               }
            }

            if (☃x == ☃xx || ☃x < 0 && ☃x == -☃xx || ☃x > 0 && ☃x == 1 - ☃xx) {
               int ☃xxxxxxx = ☃xxx;
               ☃xxx = -☃xxxx;
               ☃xxxx = ☃xxxxxxx;
            }

            ☃x += ☃xxx;
            ☃xx += ☃xxxx;
         }

         if (☃.func_77167_c()) {
            this.func_73047_i();
         }
      }
   }

   protected void func_73047_i() {
      BonusChestFeature ☃ = new BonusChestFeature();

      for(int ☃x = 0; ☃x < 10; ++☃x) {
         int ☃xx = this.field_72986_A.func_76079_c() + this.field_73012_v.nextInt(6) - this.field_73012_v.nextInt(6);
         int ☃xxx = this.field_72986_A.func_76074_e() + this.field_73012_v.nextInt(6) - this.field_73012_v.nextInt(6);
         BlockPos ☃xxxx = this.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(☃xx, 0, ☃xxx)).func_177984_a();
         if (☃.func_212245_a(this, this.field_73020_y.func_201711_g(), this.field_73012_v, ☃xxxx, IFeatureConfig.field_202429_e)) {
            break;
         }
      }
   }

   @Nullable
   public BlockPos func_180504_m() {
      return this.field_73011_w.func_177496_h();
   }

   public void func_73044_a(boolean var1, @Nullable IProgressUpdate var2) throws SessionLockException {
      ChunkProviderServer ☃ = this.func_72863_F();
      if (☃.func_73157_c()) {
         if (☃ != null) {
            ☃.func_200210_a(new TextComponentTranslation("menu.savingLevel"));
         }

         this.func_73042_a();
         if (☃ != null) {
            ☃.func_200209_c(new TextComponentTranslation("menu.savingChunks"));
         }

         ☃.func_186027_a(☃);

         for(Chunk ☃x : Lists.newArrayList(☃.func_189548_a())) {
            if (☃x != null && !this.field_73063_M.func_152621_a(☃x.field_76635_g, ☃x.field_76647_h)) {
               ☃.func_189549_a(☃x);
            }
         }
      }
   }

   public void func_104140_m() {
      ChunkProviderServer ☃ = this.func_72863_F();
      if (☃.func_73157_c()) {
         ☃.func_104112_b();
      }
   }

   protected void func_73042_a() throws SessionLockException {
      this.func_72906_B();

      for(WorldServer ☃ : this.field_73061_a.func_212370_w()) {
         if (☃ instanceof WorldServerMulti) {
            ((WorldServerMulti)☃).func_184166_c();
         }
      }

      this.field_72986_A.func_176145_a(this.func_175723_af().func_177741_h());
      this.field_72986_A.func_176124_d(this.func_175723_af().func_177731_f());
      this.field_72986_A.func_176141_c(this.func_175723_af().func_177721_g());
      this.field_72986_A.func_176129_e(this.func_175723_af().func_177742_m());
      this.field_72986_A.func_176125_f(this.func_175723_af().func_177727_n());
      this.field_72986_A.func_176122_j(this.func_175723_af().func_177748_q());
      this.field_72986_A.func_176136_k(this.func_175723_af().func_177740_p());
      this.field_72986_A.func_176118_b(this.func_175723_af().func_177751_j());
      this.field_72986_A.func_176135_e(this.func_175723_af().func_177732_i());
      this.field_72986_A.func_201356_c(this.field_73061_a.func_201300_aS().func_201380_c());
      this.field_73019_z.func_75755_a(this.field_72986_A, this.field_73061_a.func_184103_al().func_72378_q());
      this.func_175693_T().func_75744_a();
   }

   @Override
   public boolean func_72838_d(Entity var1) {
      return this.func_184165_i(☃) ? super.func_72838_d(☃) : false;
   }

   @Override
   public void func_212420_a(Stream<Entity> var1) {
      ☃.forEach(var1x -> {
         if (this.func_184165_i(var1x)) {
            this.field_72996_f.add(var1x);
            this.func_72923_a(var1x);
         }
      });
   }

   private boolean func_184165_i(Entity var1) {
      if (☃.field_70128_L) {
         field_147491_a.warn("Tried to add entity {} but it was marked as removed already", EntityType.func_200718_a(☃.func_200600_R()));
         return false;
      } else {
         UUID ☃ = ☃.func_110124_au();
         if (this.field_175741_N.containsKey(☃)) {
            Entity ☃x = (Entity)this.field_175741_N.get(☃);
            if (this.field_72997_g.contains(☃x)) {
               this.field_72997_g.remove(☃x);
            } else {
               if (!(☃ instanceof EntityPlayer)) {
                  field_147491_a.warn("Keeping entity {} that already exists with UUID {}", EntityType.func_200718_a(☃x.func_200600_R()), ☃.toString());
                  return false;
               }

               field_147491_a.warn("Force-added player with duplicate UUID {}", ☃.toString());
            }

            this.func_72973_f(☃x);
         }

         return true;
      }
   }

   @Override
   protected void func_72923_a(Entity var1) {
      super.func_72923_a(☃);
      this.field_175729_l.func_76038_a(☃.func_145782_y(), ☃);
      this.field_175741_N.put(☃.func_110124_au(), ☃);
      Entity[] ☃ = ☃.func_70021_al();
      if (☃ != null) {
         for(Entity ☃x : ☃) {
            this.field_175729_l.func_76038_a(☃x.func_145782_y(), ☃x);
         }
      }
   }

   @Override
   protected void func_72847_b(Entity var1) {
      super.func_72847_b(☃);
      this.field_175729_l.func_76049_d(☃.func_145782_y());
      this.field_175741_N.remove(☃.func_110124_au());
      Entity[] ☃ = ☃.func_70021_al();
      if (☃ != null) {
         for(Entity ☃x : ☃) {
            this.field_175729_l.func_76049_d(☃x.func_145782_y());
         }
      }
   }

   @Override
   public boolean func_72942_c(Entity var1) {
      if (super.func_72942_c(☃)) {
         this.field_73061_a
            .func_184103_al()
            .func_148543_a(null, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, 512.0, this.field_73011_w.func_186058_p(), new SPacketSpawnGlobalEntity(☃));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void func_72960_a(Entity var1, byte var2) {
      this.func_73039_n().func_151248_b(☃, new SPacketEntityStatus(☃, ☃));
   }

   public ChunkProviderServer func_72863_F() {
      return (ChunkProviderServer)super.func_72863_F();
   }

   @Override
   public Explosion func_211529_a(@Nullable Entity var1, DamageSource var2, double var3, double var5, double var7, float var9, boolean var10, boolean var11) {
      Explosion ☃ = new Explosion(this, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (☃ != null) {
         ☃.func_199592_a(☃);
      }

      ☃.func_77278_a();
      ☃.func_77279_a(false);
      if (!☃) {
         ☃.func_180342_d();
      }

      for(EntityPlayer ☃ : this.field_73010_i) {
         if (☃.func_70092_e(☃, ☃, ☃) < 4096.0) {
            ((EntityPlayerMP)☃).field_71135_a.func_147359_a(new SPacketExplosion(☃, ☃, ☃, ☃, ☃.func_180343_e(), (Vec3d)☃.func_77277_b().get(☃)));
         }
      }

      return ☃;
   }

   @Override
   public void func_175641_c(BlockPos var1, Block var2, int var3, int var4) {
      this.field_147490_S.add(new BlockEventData(☃, ☃, ☃, ☃));
   }

   private void func_147488_Z() {
      while(!this.field_147490_S.isEmpty()) {
         BlockEventData ☃ = this.field_147490_S.removeFirst();
         if (this.func_147485_a(☃)) {
            this.field_73061_a
               .func_184103_al()
               .func_148543_a(
                  null,
                  (double)☃.func_180328_a().func_177958_n(),
                  (double)☃.func_180328_a().func_177956_o(),
                  (double)☃.func_180328_a().func_177952_p(),
                  64.0,
                  this.field_73011_w.func_186058_p(),
                  new SPacketBlockAction(☃.func_180328_a(), ☃.func_151337_f(), ☃.func_151339_d(), ☃.func_151338_e())
               );
         }
      }
   }

   private boolean func_147485_a(BlockEventData var1) {
      IBlockState ☃ = this.func_180495_p(☃.func_180328_a());
      return ☃.func_177230_c() == ☃.func_151337_f() ? ☃.func_189547_a(this, ☃.func_180328_a(), ☃.func_151339_d(), ☃.func_151338_e()) : false;
   }

   @Override
   public void close() {
      this.field_73019_z.func_75759_a();
      super.close();
   }

   @Override
   protected void func_72979_l() {
      boolean ☃ = this.func_72896_J();
      super.func_72979_l();
      if (this.field_73003_n != this.field_73004_o) {
         this.field_73061_a.func_184103_al().func_148537_a(new SPacketChangeGameState(7, this.field_73004_o), this.field_73011_w.func_186058_p());
      }

      if (this.field_73018_p != this.field_73017_q) {
         this.field_73061_a.func_184103_al().func_148537_a(new SPacketChangeGameState(8, this.field_73017_q), this.field_73011_w.func_186058_p());
      }

      if (☃ != this.func_72896_J()) {
         if (☃) {
            this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(2, 0.0F));
         } else {
            this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(1, 0.0F));
         }

         this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(7, this.field_73004_o));
         this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(8, this.field_73017_q));
      }
   }

   public ServerTickList<Block> func_205220_G_() {
      return this.field_94579_S;
   }

   public ServerTickList<Fluid> func_205219_F_() {
      return this.field_205342_P;
   }

   @Nonnull
   @Override
   public MinecraftServer func_73046_m() {
      return this.field_73061_a;
   }

   public EntityTracker func_73039_n() {
      return this.field_73062_L;
   }

   public PlayerChunkMap func_184164_w() {
      return this.field_73063_M;
   }

   public Teleporter func_85176_s() {
      return this.field_85177_Q;
   }

   public TemplateManager func_184163_y() {
      return this.field_73019_z.func_186340_h();
   }

   public <T extends IParticleData> int func_195598_a(
      T var1, double var2, double var4, double var6, int var8, double var9, double var11, double var13, double var15
   ) {
      SPacketParticles ☃ = new SPacketParticles(☃, false, (float)☃, (float)☃, (float)☃, (float)☃, (float)☃, (float)☃, (float)☃, ☃);
      int ☃x = 0;

      for(int ☃xx = 0; ☃xx < this.field_73010_i.size(); ++☃xx) {
         EntityPlayerMP ☃xxx = (EntityPlayerMP)this.field_73010_i.get(☃xx);
         if (this.func_195601_a(☃xxx, false, ☃, ☃, ☃, ☃)) {
            ++☃x;
         }
      }

      return ☃x;
   }

   public <T extends IParticleData> boolean func_195600_a(
      EntityPlayerMP var1, T var2, boolean var3, double var4, double var6, double var8, int var10, double var11, double var13, double var15, double var17
   ) {
      Packet<?> ☃ = new SPacketParticles(☃, ☃, (float)☃, (float)☃, (float)☃, (float)☃, (float)☃, (float)☃, (float)☃, ☃);
      return this.func_195601_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   private boolean func_195601_a(EntityPlayerMP var1, boolean var2, double var3, double var5, double var7, Packet<?> var9) {
      if (☃.func_71121_q() != this) {
         return false;
      } else {
         BlockPos ☃ = ☃.func_180425_c();
         double ☃x = ☃.func_177954_c(☃, ☃, ☃);
         if (!(☃x <= 1024.0) && (!☃ || !(☃x <= 262144.0))) {
            return false;
         } else {
            ☃.field_71135_a.func_147359_a(☃);
            return true;
         }
      }
   }

   @Nullable
   public Entity func_175733_a(UUID var1) {
      return (Entity)this.field_175741_N.get(☃);
   }

   @Override
   public ListenableFuture<Object> func_152344_a(Runnable var1) {
      return this.field_73061_a.func_152344_a(☃);
   }

   @Override
   public boolean func_152345_ab() {
      return this.field_73061_a.func_152345_ab();
   }

   @Nullable
   @Override
   public BlockPos func_211157_a(String var1, BlockPos var2, int var3, boolean var4) {
      return this.func_72863_F().func_211268_a(this, ☃, ☃, ☃, ☃);
   }

   @Override
   public RecipeManager func_199532_z() {
      return this.field_73061_a.func_199529_aN();
   }

   @Override
   public NetworkTagManager func_205772_D() {
      return this.field_73061_a.func_199731_aO();
   }
}
