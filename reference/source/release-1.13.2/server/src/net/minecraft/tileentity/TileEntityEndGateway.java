package net.minecraft.tileentity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.EndIslandFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntityEndGateway extends TileEntityEndPortal implements ITickable {
   private static final Logger field_195503_a = LogManager.getLogger();
   private long field_195504_f;
   private int field_195505_g;
   private BlockPos field_195506_h;
   private boolean field_195507_i;

   public TileEntityEndGateway() {
      super(TileEntityType.field_200991_v);
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      ☃.func_74772_a("Age", this.field_195504_f);
      if (this.field_195506_h != null) {
         ☃.func_74782_a("ExitPortal", NBTUtil.func_186859_a(this.field_195506_h));
      }

      if (this.field_195507_i) {
         ☃.func_74757_a("ExactTeleport", this.field_195507_i);
      }

      return ☃;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_195504_f = ☃.func_74763_f("Age");
      if (☃.func_150297_b("ExitPortal", 10)) {
         this.field_195506_h = NBTUtil.func_186861_c(☃.func_74775_l("ExitPortal"));
      }

      this.field_195507_i = ☃.func_74767_n("ExactTeleport");
   }

   @Override
   public void func_73660_a() {
      boolean ☃ = this.func_195499_c();
      boolean ☃x = this.func_195500_d();
      ++this.field_195504_f;
      if (☃x) {
         --this.field_195505_g;
      } else if (!this.field_145850_b.field_72995_K) {
         List<Entity> ☃ = this.field_145850_b.func_72872_a(Entity.class, new AxisAlignedBB(this.func_174877_v()));
         if (!☃.isEmpty()) {
            this.func_195496_a((Entity)☃.get(0));
         }

         if (this.field_195504_f % 2400L == 0L) {
            this.func_195490_f();
         }
      }

      if (☃ != this.func_195499_c() || ☃x != this.func_195500_d()) {
         this.func_70296_d();
      }
   }

   public boolean func_195499_c() {
      return this.field_195504_f < 200L;
   }

   public boolean func_195500_d() {
      return this.field_195505_g > 0;
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 8, this.func_189517_E_());
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public void func_195490_f() {
      if (!this.field_145850_b.field_72995_K) {
         this.field_195505_g = 40;
         this.field_145850_b.func_175641_c(this.func_174877_v(), this.func_195044_w().func_177230_c(), 1, 0);
         this.func_70296_d();
      }
   }

   @Override
   public boolean func_145842_c(int var1, int var2) {
      if (☃ == 1) {
         this.field_195505_g = 40;
         return true;
      } else {
         return super.func_145842_c(☃, ☃);
      }
   }

   public void func_195496_a(Entity var1) {
      if (!this.field_145850_b.field_72995_K && !this.func_195500_d()) {
         this.field_195505_g = 100;
         if (this.field_195506_h == null && this.field_145850_b.field_73011_w instanceof EndDimension) {
            this.func_195501_j();
         }

         if (this.field_195506_h != null) {
            BlockPos ☃ = this.field_195507_i ? this.field_195506_h : this.func_195502_i();
            ☃.func_70634_a((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.5, (double)☃.func_177952_p() + 0.5);
         }

         this.func_195490_f();
      }
   }

   private BlockPos func_195502_i() {
      BlockPos ☃ = func_195494_a(this.field_145850_b, this.field_195506_h, 5, false);
      field_195503_a.debug("Best exit position for portal at {} is {}", this.field_195506_h, ☃);
      return ☃.func_177984_a();
   }

   private void func_195501_j() {
      Vec3d ☃ = new Vec3d((double)this.func_174877_v().func_177958_n(), 0.0, (double)this.func_174877_v().func_177952_p()).func_72432_b();
      Vec3d ☃x = ☃.func_186678_a(1024.0);

      for(int ☃xx = 16; func_195495_a(this.field_145850_b, ☃x).func_76625_h() > 0 && ☃xx-- > 0; ☃x = ☃x.func_178787_e(☃.func_186678_a(-16.0))) {
         field_195503_a.debug("Skipping backwards past nonempty chunk at {}", ☃x);
      }

      for(int var5 = 16; func_195495_a(this.field_145850_b, ☃x).func_76625_h() == 0 && var5-- > 0; ☃x = ☃x.func_178787_e(☃.func_186678_a(16.0))) {
         field_195503_a.debug("Skipping forward past empty chunk at {}", ☃x);
      }

      field_195503_a.debug("Found chunk at {}", ☃x);
      Chunk ☃xx = func_195495_a(this.field_145850_b, ☃x);
      this.field_195506_h = func_195498_a(☃xx);
      if (this.field_195506_h == null) {
         this.field_195506_h = new BlockPos(☃x.field_72450_a + 0.5, 75.0, ☃x.field_72449_c + 0.5);
         field_195503_a.debug("Failed to find suitable block, settling on {}", this.field_195506_h);
         new EndIslandFeature()
            .func_212245_a(
               this.field_145850_b,
               this.field_145850_b.func_72863_F().func_201711_g(),
               new Random(this.field_195506_h.func_177986_g()),
               this.field_195506_h,
               IFeatureConfig.field_202429_e
            );
      } else {
         field_195503_a.debug("Found block at {}", this.field_195506_h);
      }

      this.field_195506_h = func_195494_a(this.field_145850_b, this.field_195506_h, 16, true);
      field_195503_a.debug("Creating portal at {}", this.field_195506_h);
      this.field_195506_h = this.field_195506_h.func_177981_b(10);
      this.func_195492_c(this.field_195506_h);
      this.func_70296_d();
   }

   private static BlockPos func_195494_a(IBlockReader var0, BlockPos var1, int var2, boolean var3) {
      BlockPos ☃ = null;

      for(int ☃x = -☃; ☃x <= ☃; ++☃x) {
         for(int ☃xx = -☃; ☃xx <= ☃; ++☃xx) {
            if (☃x != 0 || ☃xx != 0 || ☃) {
               for(int ☃xxx = 255; ☃xxx > (☃ == null ? 0 : ☃.func_177956_o()); --☃xxx) {
                  BlockPos ☃xxxx = new BlockPos(☃.func_177958_n() + ☃x, ☃xxx, ☃.func_177952_p() + ☃xx);
                  IBlockState ☃xxxxx = ☃.func_180495_p(☃xxxx);
                  if (☃xxxxx.func_185898_k() && (☃ || ☃xxxxx.func_177230_c() != Blocks.field_150357_h)) {
                     ☃ = ☃xxxx;
                     break;
                  }
               }
            }
         }
      }

      return ☃ == null ? ☃ : ☃;
   }

   private static Chunk func_195495_a(World var0, Vec3d var1) {
      return ☃.func_72964_e(MathHelper.func_76128_c(☃.field_72450_a / 16.0), MathHelper.func_76128_c(☃.field_72449_c / 16.0));
   }

   @Nullable
   private static BlockPos func_195498_a(Chunk var0) {
      BlockPos ☃ = new BlockPos(☃.field_76635_g * 16, 30, ☃.field_76647_h * 16);
      int ☃x = ☃.func_76625_h() + 16 - 1;
      BlockPos ☃xx = new BlockPos(☃.field_76635_g * 16 + 16 - 1, ☃x, ☃.field_76647_h * 16 + 16 - 1);
      BlockPos ☃xxx = null;
      double ☃xxxx = 0.0;

      for(BlockPos ☃xxxxx : BlockPos.func_177980_a(☃, ☃xx)) {
         IBlockState ☃xxxxxx = ☃.func_180495_p(☃xxxxx);
         if (☃xxxxxx.func_177230_c() == Blocks.field_150377_bs
            && !☃.func_180495_p(☃xxxxx.func_177981_b(1)).func_185898_k()
            && !☃.func_180495_p(☃xxxxx.func_177981_b(2)).func_185898_k()) {
            double ☃xxxxxxx = ☃xxxxx.func_177957_d(0.0, 0.0, 0.0);
            if (☃xxx == null || ☃xxxxxxx < ☃xxxx) {
               ☃xxx = ☃xxxxx;
               ☃xxxx = ☃xxxxxxx;
            }
         }
      }

      return ☃xxx;
   }

   private void func_195492_c(BlockPos var1) {
      Feature.field_202299_as
         .func_212245_a(this.field_145850_b, this.field_145850_b.func_72863_F().func_201711_g(), new Random(), ☃, new EndGatewayConfig(false));
      TileEntity ☃ = this.field_145850_b.func_175625_s(☃);
      if (☃ instanceof TileEntityEndGateway) {
         TileEntityEndGateway ☃x = (TileEntityEndGateway)☃;
         ☃x.field_195506_h = new BlockPos(this.func_174877_v());
         ☃x.func_70296_d();
      } else {
         field_195503_a.warn("Couldn't save exit portal at {}", ☃);
      }
   }

   public void func_195489_b(BlockPos var1) {
      this.field_195507_i = true;
      this.field_195506_h = ☃;
   }
}
