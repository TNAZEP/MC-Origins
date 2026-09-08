package net.minecraft.fluid;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IStateHolder;
import net.minecraft.tags.Tag;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public interface IFluidState extends IStateHolder<IFluidState> {
   Fluid func_206886_c();

   default boolean func_206889_d() {
      return this.func_206886_c().func_207193_c(this);
   }

   default boolean func_206888_e() {
      return this.func_206886_c().func_204538_c();
   }

   default float func_206885_f() {
      return this.func_206886_c().func_207181_a(this);
   }

   default int func_206882_g() {
      return this.func_206886_c().func_207192_d(this);
   }

   default void func_206880_a(World var1, BlockPos var2) {
      this.func_206886_c().func_207191_a(☃, ☃, this);
   }

   default boolean func_206890_h() {
      return this.func_206886_c().func_207196_h();
   }

   default void func_206891_b(World var1, BlockPos var2, Random var3) {
      this.func_206886_c().func_207186_b(☃, ☃, this, ☃);
   }

   default Vec3d func_206887_a(IWorldReaderBase var1, BlockPos var2) {
      return this.func_206886_c().func_205564_a(☃, ☃, this);
   }

   default IBlockState func_206883_i() {
      return this.func_206886_c().func_204527_a(this);
   }

   default boolean func_206884_a(Tag<Fluid> var1) {
      return this.func_206886_c().func_207185_a(☃);
   }

   default float func_210200_l() {
      return this.func_206886_c().func_210195_d();
   }

   default boolean func_211725_a(Fluid var1, EnumFacing var2) {
      return this.func_206886_c().func_211757_a(this, ☃, ☃);
   }
}
