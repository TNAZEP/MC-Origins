package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity {
   private static final Logger field_145852_a = LogManager.getLogger();
   private final TileEntityType<?> field_200663_e;
   protected World field_145850_b;
   protected BlockPos field_174879_c = BlockPos.field_177992_a;
   protected boolean field_145846_f;
   @Nullable
   private IBlockState field_195045_e;

   public TileEntity(TileEntityType<?> var1) {
      this.field_200663_e = ☃;
   }

   @Nullable
   public World func_145831_w() {
      return this.field_145850_b;
   }

   public void func_145834_a(World var1) {
      this.field_145850_b = ☃;
   }

   public boolean func_145830_o() {
      return this.field_145850_b != null;
   }

   public void func_145839_a(NBTTagCompound var1) {
      this.field_174879_c = new BlockPos(☃.func_74762_e("x"), ☃.func_74762_e("y"), ☃.func_74762_e("z"));
   }

   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      return this.func_189516_d(☃);
   }

   private NBTTagCompound func_189516_d(NBTTagCompound var1) {
      ResourceLocation ☃ = TileEntityType.func_200969_a(this.func_200662_C());
      if (☃ == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         ☃.func_74778_a("id", ☃.toString());
         ☃.func_74768_a("x", this.field_174879_c.func_177958_n());
         ☃.func_74768_a("y", this.field_174879_c.func_177956_o());
         ☃.func_74768_a("z", this.field_174879_c.func_177952_p());
         return ☃;
      }
   }

   @Nullable
   public static TileEntity func_203403_c(NBTTagCompound var0) {
      TileEntity ☃ = null;
      String ☃x = ☃.func_74779_i("id");

      try {
         ☃ = TileEntityType.func_200967_a(☃x);
      } catch (Throwable var5) {
         field_145852_a.error("Failed to create block entity {}", ☃x, var5);
      }

      if (☃ != null) {
         try {
            ☃.func_145839_a(☃);
         } catch (Throwable var4) {
            field_145852_a.error("Failed to load data for block entity {}", ☃x, var4);
            ☃ = null;
         }
      } else {
         field_145852_a.warn("Skipping BlockEntity with id {}", ☃x);
      }

      return ☃;
   }

   public void func_70296_d() {
      if (this.field_145850_b != null) {
         this.field_195045_e = this.field_145850_b.func_180495_p(this.field_174879_c);
         this.field_145850_b.func_175646_b(this.field_174879_c, this);
         if (!this.field_195045_e.func_196958_f()) {
            this.field_145850_b.func_175666_e(this.field_174879_c, this.field_195045_e.func_177230_c());
         }
      }
   }

   public BlockPos func_174877_v() {
      return this.field_174879_c;
   }

   public IBlockState func_195044_w() {
      if (this.field_195045_e == null) {
         this.field_195045_e = this.field_145850_b.func_180495_p(this.field_174879_c);
      }

      return this.field_195045_e;
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return null;
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189516_d(new NBTTagCompound());
   }

   public boolean func_145837_r() {
      return this.field_145846_f;
   }

   public void func_145843_s() {
      this.field_145846_f = true;
   }

   public void func_145829_t() {
      this.field_145846_f = false;
   }

   public boolean func_145842_c(int var1, int var2) {
      return false;
   }

   public void func_145836_u() {
      this.field_195045_e = null;
   }

   public void func_145828_a(CrashReportCategory var1) {
      ☃.func_189529_a("Name", () -> IRegistry.field_212626_o.func_177774_c(this.func_200662_C()) + " // " + this.getClass().getCanonicalName());
      if (this.field_145850_b != null) {
         CrashReportCategory.func_175750_a(☃, this.field_174879_c, this.func_195044_w());
         CrashReportCategory.func_175750_a(☃, this.field_174879_c, this.field_145850_b.func_180495_p(this.field_174879_c));
      }
   }

   public void func_174878_a(BlockPos var1) {
      this.field_174879_c = ☃.func_185334_h();
   }

   public boolean func_183000_F() {
      return false;
   }

   public void func_189667_a(Rotation var1) {
   }

   public void func_189668_a(Mirror var1) {
   }

   public TileEntityType<?> func_200662_C() {
      return this.field_200663_e;
   }
}
