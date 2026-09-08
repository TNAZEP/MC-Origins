package net.minecraft.client.multiplayer;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.particles.IParticleData;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EmptyTickList;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.GameType;
import net.minecraft.world.ITickList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

public class WorldClient extends World {
   private final NetHandlerPlayClient field_73035_a;
   private ChunkProviderClient field_73033_b;
   private final Set<Entity> field_73032_d = Sets.<Entity>newHashSet();
   private final Set<Entity> field_73036_L = Sets.<Entity>newHashSet();
   private final Minecraft field_73037_M = Minecraft.func_71410_x();
   private final Set<ChunkPos> field_73038_N = Sets.<ChunkPos>newHashSet();
   private int field_184158_M = this.field_73012_v.nextInt(12000);
   protected Set<ChunkPos> field_184157_a = Sets.<ChunkPos>newHashSet();
   private Scoreboard field_200261_M = new Scoreboard();

   public WorldClient(NetHandlerPlayClient var1, WorldSettings var2, DimensionType var3, EnumDifficulty var4, Profiler var5) {
      super(new SaveHandlerMP(), new SaveDataMemoryStorage(), new WorldInfo(☃, "MpServer"), ☃.func_186070_d(), ☃, true);
      this.field_73035_a = ☃;
      this.func_72912_H().func_176144_a(☃);
      this.func_175652_B(new BlockPos(8, 64, 8));
      this.field_73011_w.func_76558_a(this);
      this.field_73020_y = this.func_72970_h();
      this.func_72966_v();
      this.func_72947_a();
   }

   @Override
   public void func_72835_b(BooleanSupplier var1) {
      super.func_72835_b(☃);
      this.func_82738_a(this.func_82737_E() + 1L);
      if (this.func_82736_K().func_82766_b("doDaylightCycle")) {
         this.func_72877_b(this.func_72820_D() + 1L);
      }

      this.field_72984_F.func_76320_a("reEntryProcessing");

      for(int ☃ = 0; ☃ < 10 && !this.field_73036_L.isEmpty(); ++☃) {
         Entity ☃x = (Entity)this.field_73036_L.iterator().next();
         this.field_73036_L.remove(☃x);
         if (!this.field_72996_f.contains(☃x)) {
            this.func_72838_d(☃x);
         }
      }

      this.field_72984_F.func_76318_c("chunkCache");
      this.field_73033_b.func_73156_b(☃);
      this.field_72984_F.func_76318_c("blocks");
      this.func_147456_g();
      this.field_72984_F.func_76319_b();
   }

   @Override
   protected IChunkProvider func_72970_h() {
      this.field_73033_b = new ChunkProviderClient(this);
      return this.field_73033_b;
   }

   @Override
   public boolean func_175680_a(int var1, int var2, boolean var3) {
      return ☃ || this.func_72863_F().func_186025_d(☃, ☃, true, false) != null;
   }

   protected void func_184154_a() {
      this.field_184157_a.clear();
      int ☃ = this.field_73037_M.field_71474_y.field_151451_c;
      this.field_72984_F.func_76320_a("buildList");
      int ☃x = MathHelper.func_76128_c(this.field_73037_M.field_71439_g.field_70165_t / 16.0);
      int ☃xx = MathHelper.func_76128_c(this.field_73037_M.field_71439_g.field_70161_v / 16.0);

      for(int ☃xxx = -☃; ☃xxx <= ☃; ++☃xxx) {
         for(int ☃xxxx = -☃; ☃xxxx <= ☃; ++☃xxxx) {
            this.field_184157_a.add(new ChunkPos(☃xxx + ☃x, ☃xxxx + ☃xx));
         }
      }

      this.field_72984_F.func_76319_b();
   }

   @Override
   protected void func_147456_g() {
      this.func_184154_a();
      if (this.field_184158_M > 0) {
         --this.field_184158_M;
      }

      this.field_73038_N.retainAll(this.field_184157_a);
      if (this.field_73038_N.size() == this.field_184157_a.size()) {
         this.field_73038_N.clear();
      }

      int ☃ = 0;

      for(ChunkPos ☃x : this.field_184157_a) {
         if (!this.field_73038_N.contains(☃x)) {
            int ☃xx = ☃x.field_77276_a * 16;
            int ☃xxx = ☃x.field_77275_b * 16;
            this.field_72984_F.func_76320_a("getChunk");
            Chunk ☃xxxx = this.func_72964_e(☃x.field_77276_a, ☃x.field_77275_b);
            this.func_147467_a(☃xx, ☃xxx, ☃xxxx);
            this.field_72984_F.func_76319_b();
            this.field_73038_N.add(☃x);
            if (++☃ >= 10) {
               return;
            }
         }
      }
   }

   @Override
   public boolean func_72838_d(Entity var1) {
      boolean ☃ = super.func_72838_d(☃);
      this.field_73032_d.add(☃);
      if (☃) {
         if (☃ instanceof EntityMinecart) {
            this.field_73037_M.func_147118_V().func_147682_a(new MovingSoundMinecart((EntityMinecart)☃));
         }
      } else {
         this.field_73036_L.add(☃);
      }

      return ☃;
   }

   @Override
   public void func_72900_e(Entity var1) {
      super.func_72900_e(☃);
      this.field_73032_d.remove(☃);
   }

   @Override
   protected void func_72923_a(Entity var1) {
      super.func_72923_a(☃);
      if (this.field_73036_L.contains(☃)) {
         this.field_73036_L.remove(☃);
      }
   }

   @Override
   protected void func_72847_b(Entity var1) {
      super.func_72847_b(☃);
      if (this.field_73032_d.contains(☃)) {
         if (☃.func_70089_S()) {
            this.field_73036_L.add(☃);
         } else {
            this.field_73032_d.remove(☃);
         }
      }
   }

   public void func_73027_a(int var1, Entity var2) {
      Entity ☃ = this.func_73045_a(☃);
      if (☃ != null) {
         this.func_72900_e(☃);
      }

      this.field_73032_d.add(☃);
      ☃.func_145769_d(☃);
      if (!this.func_72838_d(☃)) {
         this.field_73036_L.add(☃);
      }

      this.field_175729_l.func_76038_a(☃, ☃);
   }

   @Nullable
   @Override
   public Entity func_73045_a(int var1) {
      return (Entity)(☃ == this.field_73037_M.field_71439_g.func_145782_y() ? this.field_73037_M.field_71439_g : super.func_73045_a(☃));
   }

   public Entity func_73028_b(int var1) {
      Entity ☃ = this.field_175729_l.func_76049_d(☃);
      if (☃ != null) {
         this.field_73032_d.remove(☃);
         this.func_72900_e(☃);
      }

      return ☃;
   }

   public void func_195597_b(BlockPos var1, IBlockState var2) {
      this.func_180501_a(☃, ☃, 19);
   }

   @Override
   public void func_72882_A() {
      this.field_73035_a.func_147298_b().func_150718_a(new TextComponentTranslation("multiplayer.status.quitting"));
   }

   @Override
   protected void func_72979_l() {
   }

   @Override
   protected void func_147467_a(int var1, int var2, Chunk var3) {
      super.func_147467_a(☃, ☃, ☃);
      if (this.field_184158_M == 0) {
         this.field_73005_l = this.field_73005_l * 3 + 1013904223;
         int ☃ = this.field_73005_l >> 2;
         int ☃x = ☃ & 15;
         int ☃xx = ☃ >> 8 & 15;
         int ☃xxx = ☃ >> 16 & 0xFF;
         BlockPos ☃xxxx = new BlockPos(☃x + ☃, ☃xxx, ☃xx + ☃);
         IBlockState ☃xxxxx = ☃.func_180495_p(☃xxxx);
         ☃x += ☃;
         ☃xx += ☃;
         if (☃xxxxx.func_196958_f() && this.func_201669_a(☃xxxx, 0) <= this.field_73012_v.nextInt(8) && this.func_175642_b(EnumLightType.SKY, ☃xxxx) <= 0) {
            double ☃xxxxxx = this.field_73037_M.field_71439_g.func_70092_e((double)☃x + 0.5, (double)☃xxx + 0.5, (double)☃xx + 0.5);
            if (this.field_73037_M.field_71439_g != null && ☃xxxxxx > 4.0 && ☃xxxxxx < 256.0) {
               this.func_184134_a(
                  (double)☃x + 0.5,
                  (double)☃xxx + 0.5,
                  (double)☃xx + 0.5,
                  SoundEvents.field_187674_a,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.8F + this.field_73012_v.nextFloat() * 0.2F,
                  false
               );
               this.field_184158_M = this.field_73012_v.nextInt(12000) + 6000;
            }
         }
      }
   }

   public void func_73029_E(int var1, int var2, int var3) {
      int ☃ = 32;
      Random ☃x = new Random();
      ItemStack ☃xx = this.field_73037_M.field_71439_g.func_184614_ca();
      boolean ☃xxx = this.field_73037_M.field_71442_b.func_178889_l() == GameType.CREATIVE
         && !☃xx.func_190926_b()
         && ☃xx.func_77973_b() == Blocks.field_180401_cv.func_199767_j();
      BlockPos.MutableBlockPos ☃xxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxx = 0; ☃xxxxx < 667; ++☃xxxxx) {
         this.func_184153_a(☃, ☃, ☃, 16, ☃x, ☃xxx, ☃xxxx);
         this.func_184153_a(☃, ☃, ☃, 32, ☃x, ☃xxx, ☃xxxx);
      }
   }

   public void func_184153_a(int var1, int var2, int var3, int var4, Random var5, boolean var6, BlockPos.MutableBlockPos var7) {
      int ☃ = ☃ + this.field_73012_v.nextInt(☃) - this.field_73012_v.nextInt(☃);
      int ☃x = ☃ + this.field_73012_v.nextInt(☃) - this.field_73012_v.nextInt(☃);
      int ☃xx = ☃ + this.field_73012_v.nextInt(☃) - this.field_73012_v.nextInt(☃);
      ☃.func_181079_c(☃, ☃x, ☃xx);
      IBlockState ☃xxx = this.func_180495_p(☃);
      ☃xxx.func_177230_c().func_180655_c(☃xxx, this, ☃, ☃);
      IFluidState ☃xxxx = this.func_204610_c(☃);
      if (!☃xxxx.func_206888_e()) {
         ☃xxxx.func_206881_a(this, ☃, ☃);
         IParticleData ☃xxxxx = ☃xxxx.func_204521_c();
         if (☃xxxxx != null && this.field_73012_v.nextInt(10) == 0) {
            boolean ☃xxxxxx = ☃xxx.func_193401_d(this, ☃, EnumFacing.DOWN) == BlockFaceShape.SOLID;
            BlockPos ☃xxxxxxx = ☃.func_177977_b();
            this.func_211530_a(☃xxxxxxx, this.func_180495_p(☃xxxxxxx), ☃xxxxx, ☃xxxxxx);
         }
      }

      if (☃ && ☃xxx.func_177230_c() == Blocks.field_180401_cv) {
         this.func_195594_a(Particles.field_197610_c, (double)((float)☃ + 0.5F), (double)((float)☃x + 0.5F), (double)((float)☃xx + 0.5F), 0.0, 0.0, 0.0);
      }
   }

   private void func_211530_a(BlockPos var1, IBlockState var2, IParticleData var3, boolean var4) {
      if (☃.func_204520_s().func_206888_e()) {
         VoxelShape ☃ = ☃.func_196952_d(this, ☃);
         double ☃x = ☃.func_197758_c(EnumFacing.Axis.Y);
         if (☃x < 1.0) {
            if (☃) {
               this.func_211834_a(
                  (double)☃.func_177958_n(),
                  (double)(☃.func_177958_n() + 1),
                  (double)☃.func_177952_p(),
                  (double)(☃.func_177952_p() + 1),
                  (double)(☃.func_177956_o() + 1) - 0.05,
                  ☃
               );
            }
         } else if (!☃.func_203425_a(BlockTags.field_211923_H)) {
            double ☃ = ☃.func_197762_b(EnumFacing.Axis.Y);
            if (☃ > 0.0) {
               this.func_211835_a(☃, ☃, ☃, (double)☃.func_177956_o() + ☃ - 0.05);
            } else {
               BlockPos ☃ = ☃.func_177977_b();
               IBlockState ☃x = this.func_180495_p(☃);
               VoxelShape ☃xx = ☃x.func_196952_d(this, ☃);
               double ☃xxx = ☃xx.func_197758_c(EnumFacing.Axis.Y);
               if (☃xxx < 1.0 && ☃x.func_204520_s().func_206888_e()) {
                  this.func_211835_a(☃, ☃, ☃, (double)☃.func_177956_o() - 0.05);
               }
            }
         }
      }
   }

   private void func_211835_a(BlockPos var1, IParticleData var2, VoxelShape var3, double var4) {
      this.func_211834_a(
         (double)☃.func_177958_n() + ☃.func_197762_b(EnumFacing.Axis.X),
         (double)☃.func_177958_n() + ☃.func_197758_c(EnumFacing.Axis.X),
         (double)☃.func_177952_p() + ☃.func_197762_b(EnumFacing.Axis.Z),
         (double)☃.func_177952_p() + ☃.func_197758_c(EnumFacing.Axis.Z),
         ☃,
         ☃
      );
   }

   private void func_211834_a(double var1, double var3, double var5, double var7, double var9, IParticleData var11) {
      this.func_195594_a(☃, ☃ + (☃ - ☃) * this.field_73012_v.nextDouble(), ☃, ☃ + (☃ - ☃) * this.field_73012_v.nextDouble(), 0.0, 0.0, 0.0);
   }

   public void func_73022_a() {
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

      for(int ☃ = 0; ☃ < this.field_72996_f.size(); ++☃) {
         Entity ☃x = (Entity)this.field_72996_f.get(☃);
         Entity ☃xx = ☃x.func_184187_bx();
         if (☃xx != null) {
            if (!☃xx.field_70128_L && ☃xx.func_184196_w(☃x)) {
               continue;
            }

            ☃x.func_184210_p();
         }

         if (☃x.field_70128_L) {
            int ☃x = ☃x.field_70176_ah;
            int ☃xx = ☃x.field_70164_aj;
            if (☃x.field_70175_ag && this.func_175680_a(☃x, ☃xx, true)) {
               this.func_72964_e(☃x, ☃xx).func_76622_b(☃x);
            }

            this.field_72996_f.remove(☃--);
            this.func_72847_b(☃x);
         }
      }
   }

   @Override
   public CrashReportCategory func_72914_a(CrashReport var1) {
      CrashReportCategory ☃ = super.func_72914_a(☃);
      ☃.func_189529_a("Forced entities", () -> this.field_73032_d.size() + " total; " + this.field_73032_d);
      ☃.func_189529_a("Retry entities", () -> this.field_73036_L.size() + " total; " + this.field_73036_L);
      ☃.func_189529_a("Server brand", () -> this.field_73037_M.field_71439_g.func_142021_k());
      ☃.func_189529_a("Server type", () -> this.field_73037_M.func_71401_C() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server");
      return ☃;
   }

   @Override
   public void func_184148_a(@Nullable EntityPlayer var1, double var2, double var4, double var6, SoundEvent var8, SoundCategory var9, float var10, float var11) {
      if (☃ == this.field_73037_M.field_71439_g) {
         this.func_184134_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, false);
      }
   }

   public void func_184156_a(BlockPos var1, SoundEvent var2, SoundCategory var3, float var4, float var5, boolean var6) {
      this.func_184134_a((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.5, (double)☃.func_177952_p() + 0.5, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_184134_a(double var1, double var3, double var5, SoundEvent var7, SoundCategory var8, float var9, float var10, boolean var11) {
      double ☃ = this.field_73037_M.func_175606_aa().func_70092_e(☃, ☃, ☃);
      SimpleSound ☃x = new SimpleSound(☃, ☃, ☃, ☃, (float)☃, (float)☃, (float)☃);
      if (☃ && ☃ > 100.0) {
         double ☃xx = Math.sqrt(☃) / 40.0;
         this.field_73037_M.func_147118_V().func_147681_a(☃x, (int)(☃xx * 20.0));
      } else {
         this.field_73037_M.func_147118_V().func_147682_a(☃x);
      }
   }

   @Override
   public void func_92088_a(double var1, double var3, double var5, double var7, double var9, double var11, @Nullable NBTTagCompound var13) {
      this.field_73037_M.field_71452_i.func_78873_a(new ParticleFirework.Starter(this, ☃, ☃, ☃, ☃, ☃, ☃, this.field_73037_M.field_71452_i, ☃));
   }

   @Override
   public void func_184135_a(Packet<?> var1) {
      this.field_73035_a.func_147297_a(☃);
   }

   @Override
   public RecipeManager func_199532_z() {
      return this.field_73035_a.func_199526_e();
   }

   public void func_96443_a(Scoreboard var1) {
      this.field_200261_M = ☃;
   }

   @Override
   public void func_72877_b(long var1) {
      if (☃ < 0L) {
         ☃ = -☃;
         this.func_82736_K().func_82764_b("doDaylightCycle", "false", null);
      } else {
         this.func_82736_K().func_82764_b("doDaylightCycle", "true", null);
      }

      super.func_72877_b(☃);
   }

   @Override
   public ITickList<Block> func_205220_G_() {
      return EmptyTickList.func_205388_a();
   }

   @Override
   public ITickList<Fluid> func_205219_F_() {
      return EmptyTickList.func_205388_a();
   }

   public ChunkProviderClient func_72863_F() {
      return (ChunkProviderClient)super.func_72863_F();
   }

   @Override
   public Scoreboard func_96441_U() {
      return this.field_200261_M;
   }

   @Override
   public NetworkTagManager func_205772_D() {
      return this.field_73035_a.func_199724_l();
   }
}
