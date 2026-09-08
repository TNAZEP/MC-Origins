package net.minecraft.fluid;

import com.google.common.collect.ImmutableMap;
import net.minecraft.state.AbstractStateHolder;
import net.minecraft.state.IProperty;

public class FluidState extends AbstractStateHolder<Fluid, IFluidState> implements IFluidState {
   public FluidState(Fluid var1, ImmutableMap<IProperty<?>, Comparable<?>> var2) {
      super(☃, ☃);
   }

   @Override
   public Fluid func_206886_c() {
      return this.field_206876_a;
   }
}
