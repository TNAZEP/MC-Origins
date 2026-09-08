package net.minecraft.world.gen;

import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.ITickList;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldGenTickList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegion implements IWorld {
   private static final Logger field_208303_a = LogManager.getLogger();
   private final ChunkPrimer[] field_201684_a;
   private final int field_201685_b;
   private final int field_201686_c;
   private final int field_201687_d;
   private final int field_201688_e;
   private final World field_201689_f;
   private final long field_201690_g;
   private final int field_201691_h;
   private final WorldInfo field_201692_i;
   private final Random field_201693_j;
   private final Dimension field_201694_k;
   private final IChunkGenSettings field_201695_l;
   private final ITickList<Block> field_205336_m = new WorldGenTickList<>(var1x -> this.func_205771_y(var1x).func_205218_i_());
   private final ITickList<Fluid> field_205337_n = new WorldGenTickList<>(var1x -> this.func_205771_y(var1x).func_212247_j());

   public WorldGenRegion(ChunkPrimer[] var1, int var2, int var3, int var4, int var5, World var6) {
      this.field_201684_a = ☃;
      this.field_201685_b = ☃;
      this.field_201686_c = ☃;
      this.field_201687_d = ☃;
      this.field_201688_e = ☃;
      this.field_201689_f = ☃;
      this.field_201690_g = ☃.func_72905_C();
      this.field_201695_l = ☃.func_72863_F().func_201711_g().func_201496_a_();
      this.field_201691_h = ☃.func_181545_F();
      this.field_201692_i = ☃.func_72912_H();
      this.field_201693_j = ☃.func_201674_k();
      this.field_201694_k = ☃.func_201675_m();
   }

   public int func_201679_a() {
      return this.field_201685_b;
   }

   public int func_201680_b() {
      return this.field_201686_c;
   }

   public boolean func_201678_a(int var1, int var2) {
      IChunk ☃ = this.field_201684_a[0];
      IChunk ☃x = this.field_201684_a[this.field_201684_a.length - 1];
      return ☃ >= ☃.func_76632_l().field_77276_a
         && ☃ <= ☃x.func_76632_l().field_77276_a
         && ☃ >= ☃.func_76632_l().field_77275_b
         && ☃ <= ☃x.func_76632_l().field_77275_b;
   }

   @Override
   public IChunk func_72964_e(int var1, int var2) {
      if (this.func_201678_a(☃, ☃)) {
         int ☃ = ☃ - this.field_201684_a[0].func_76632_l().field_77276_a;
         int ☃x = ☃ - this.field_201684_a[0].func_76632_l().field_77275_b;
         return this.field_201684_a[☃ + ☃x * this.field_201687_d];
      } else {
         IChunk ☃ = this.field_201684_a[0];
         IChunk ☃x = this.field_201684_a[this.field_201684_a.length - 1];
         field_208303_a.error("Requested chunk : {} {}", ☃, ☃);
         field_208303_a.error(
            "Region bounds : {} {} | {} {}",
            ☃.func_76632_l().field_77276_a,
            ☃.func_76632_l().field_77275_b,
            ☃x.func_76632_l().field_77276_a,
            ☃x.func_76632_l().field_77275_b
         );
         throw new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", ☃, ☃));
      }
   }

   @Override
   public IBlockState func_180495_p(BlockPos var1) {
      return this.func_205771_y(☃).func_180495_p(☃);
   }

   @Override
   public IFluidState func_204610_c(BlockPos var1) {
      return this.func_205771_y(☃).func_204610_c(☃);
   }

   @Nullable
   @Override
   public EntityPlayer func_190525_a(double var1, double var3, double var5, double var7, Predicate<Entity> var9) {
      return null;
   }

   @Override
   public int func_175657_ab() {
      return 0;
   }

   @Override
   public boolean func_175623_d(BlockPos var1) {
      return this.func_180495_p(☃).func_196958_f();
   }

   @Override
   public Biome func_180494_b(BlockPos var1) {
      Biome ☃ = this.func_205771_y(☃).func_201590_e()[☃.func_177958_n() & 15 | (☃.func_177952_p() & 15) << 4];
      if (☃ == null) {
         throw new RuntimeException(String.format("Biome is null @ %s", ☃));
      } else {
         return ☃;
      }
   }

   @Override
   public int func_175642_b(EnumLightType var1, BlockPos var2) {
      IChunk ☃ = this.func_205771_y(☃);
      return ☃.func_201587_a(☃, ☃, this.func_201675_m().func_191066_m());
   }

   @Override
   public int func_201669_a(BlockPos var1, int var2) {
      return this.func_205771_y(☃).func_201586_a(☃, ☃, this.func_201675_m().func_191066_m());
   }

   @Override
   public boolean func_175680_a(int var1, int var2, boolean var3) {
      return this.func_201678_a(☃, ☃);
   }

   @Override
   public boolean func_175655_b(BlockPos var1, boolean var2) {
      IBlockState ☃ = this.func_180495_p(☃);
      if (☃.func_196958_f()) {
         return false;
      } else {
         if (☃) {
            ☃.func_196949_c(this.field_201689_f, ☃, 0);
         }

         return this.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 3);
      }
   }

   @Override
   public boolean func_175678_i(BlockPos var1) {
      return this.func_205771_y(☃).func_177444_d(☃);
   }

   @Nullable
   @Override
   public TileEntity func_175625_s(BlockPos var1) {
      IChunk ☃ = this.func_205771_y(☃);
      TileEntity ☃x = ☃.func_175625_s(☃);
      if (☃x != null) {
         return ☃x;
      } else {
         NBTTagCompound ☃ = ☃.func_201579_g(☃);
         if (☃ != null) {
            if ("DUMMY".equals(☃.func_74779_i("id"))) {
               ☃x = ((ITileEntityProvider)this.func_180495_p(☃).func_177230_c()).func_196283_a_(this.field_201689_f);
            } else {
               ☃x = TileEntity.func_203403_c(☃);
            }

            if (☃x != null) {
               ☃.func_177426_a(☃, ☃x);
               return ☃x;
            }
         }

         if (☃.func_180495_p(☃).func_177230_c() instanceof ITileEntityProvider) {
            field_208303_a.warn("Tried to access a block entity before it was created. {}", ☃);
         }

         return null;
      }
   }

   @Override
   public boolean func_180501_a(BlockPos var1, IBlockState var2, int var3) {
      IChunk ☃ = this.func_205771_y(☃);
      IBlockState ☃x = ☃.func_177436_a(☃, ☃, false);
      Block ☃xx = ☃.func_177230_c();
      if (☃xx.func_149716_u()) {
         if (☃.func_201589_g().func_202129_d() == ChunkStatus.Type.LEVELCHUNK) {
            ☃.func_177426_a(☃, ((ITileEntityProvider)☃xx).func_196283_a_(this));
         } else {
            NBTTagCompound ☃xxx = new NBTTagCompound();
            ☃xxx.func_74768_a("x", ☃.func_177958_n());
            ☃xxx.func_74768_a("y", ☃.func_177956_o());
            ☃xxx.func_74768_a("z", ☃.func_177952_p());
            ☃xxx.func_74778_a("id", "DUMMY");
            ☃.func_201591_a(☃xxx);
         }
      } else if (☃x != null && ☃x.func_177230_c().func_149716_u()) {
         ☃.func_177425_e(☃);
      }

      if (☃.func_202065_c(this, ☃)) {
         this.func_201683_l(☃);
      }

      return true;
   }

   private void func_201683_l(BlockPos var1) {
      this.func_205771_y(☃).func_201594_d(☃);
   }

   @Override
   public boolean func_72838_d(Entity var1) {
      int ☃ = MathHelper.func_76128_c(☃.field_70165_t / 16.0);
      int ☃x = MathHelper.func_76128_c(☃.field_70161_v / 16.0);
      this.func_72964_e(☃, ☃x).func_76612_a(☃);
      return true;
   }

   @Override
   public boolean func_175698_g(BlockPos var1) {
      return this.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 3);
   }

   @Override
   public void func_175653_a(EnumLightType var1, BlockPos var2, int var3) {
      this.func_205771_y(☃).func_201580_a(☃, this.field_201694_k.func_191066_m(), ☃, ☃);
   }

   @Override
   public WorldBorder func_175723_af() {
      return this.field_201689_f.func_175723_af();
   }

   @Override
   public boolean func_195585_a(@Nullable Entity var1, VoxelShape var2) {
      return true;
   }

   @Override
   public int func_175627_a(BlockPos var1, EnumFacing var2) {
      return this.func_180495_p(☃).func_185893_b(this, ☃, ☃);
   }

   @Override
   public boolean func_201670_d() {
      return false;
   }

   @Deprecated
   @Override
   public World func_201672_e() {
      return this.field_201689_f;
   }

   @Override
   public WorldInfo func_72912_H() {
      return this.field_201692_i;
   }

   @Override
   public DifficultyInstance func_175649_E(BlockPos var1) {
      if (!this.func_201678_a(☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4)) {
         throw new RuntimeException("We are asking a region for a chunk out of bound");
      } else {
         return new DifficultyInstance(this.field_201689_f.func_175659_aa(), this.field_201689_f.func_72820_D(), 0L, this.field_201689_f.func_130001_d());
      }
   }

   @Nullable
   @Override
   public WorldSavedDataStorage func_175693_T() {
      return this.field_201689_f.func_175693_T();
   }

   @Override
   public IChunkProvider func_72863_F() {
      return this.field_201689_f.func_72863_F();
   }

   @Override
   public ISaveHandler func_72860_G() {
      return this.field_201689_f.func_72860_G();
   }

   @Override
   public long func_72905_C() {
      return this.field_201690_g;
   }

   @Override
   public ITickList<Block> func_205220_G_() {
      return this.field_205336_m;
   }

   @Override
   public ITickList<Fluid> func_205219_F_() {
      return this.field_205337_n;
   }

   @Override
   public int func_181545_F() {
      return this.field_201691_h;
   }

   @Override
   public Random func_201674_k() {
      return this.field_201693_j;
   }

   @Override
   public void func_195592_c(BlockPos var1, Block var2) {
   }

   @Override
   public int func_201676_a(Heightmap.Type var1, int var2, int var3) {
      return this.func_72964_e(☃ >> 4, ☃ >> 4).func_201576_a(☃, ☃ & 15, ☃ & 15) + 1;
   }

   @Override
   public void func_184133_a(@Nullable EntityPlayer var1, BlockPos var2, SoundEvent var3, SoundCategory var4, float var5, float var6) {
   }

   @Override
   public void func_195594_a(IParticleData var1, double var2, double var4, double var6, double var8, double var10, double var12) {
   }

   @Override
   public BlockPos func_175694_M() {
      return this.field_201689_f.func_175694_M();
   }

   @Override
   public Dimension func_201675_m() {
      return this.field_201694_k;
   }
}
