package net.minecraft.world.level.material;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class FluidState extends StateHolder<Fluid, FluidState> {
   public static final Codec<FluidState> CODEC = codec(Registry.FLUID, Fluid::defaultFluidState).stable();
   public static final int AMOUNT_MAX = 9;
   public static final int AMOUNT_FULL = 8;

   public FluidState(Fluid var1, ImmutableMap<Property<?>, Comparable<?>> var2, MapCodec<FluidState> var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Fluid getType() {
      return this.owner;
   }

   public boolean isSource() {
      return this.getType().isSource(this);
   }

   public boolean isSourceOfType(Fluid var1) {
      return this.owner == â˜ƒ && this.owner.isSource(this);
   }

   public boolean isEmpty() {
      return this.getType().isEmpty();
   }

   public float getHeight(BlockGetter var1, BlockPos var2) {
      return this.getType().getHeight(this, â˜ƒ, â˜ƒ);
   }

   public float getOwnHeight() {
      return this.getType().getOwnHeight(this);
   }

   public int getAmount() {
      return this.getType().getAmount(this);
   }

   public boolean shouldRenderBackwardUpFace(BlockGetter var1, BlockPos var2) {
      for(int â˜ƒ = -1; â˜ƒ <= 1; ++â˜ƒ) {
         for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
            BlockPos â˜ƒxx = â˜ƒ.offset(â˜ƒ, 0, â˜ƒx);
            FluidState â˜ƒxxx = â˜ƒ.getFluidState(â˜ƒxx);
            if (!â˜ƒxxx.getType().isSame(this.getType()) && !â˜ƒ.getBlockState(â˜ƒxx).isSolidRender(â˜ƒ, â˜ƒxx)) {
               return true;
            }
         }
      }

      return false;
   }

   public void tick(Level var1, BlockPos var2) {
      this.getType().tick(â˜ƒ, â˜ƒ, this);
   }

   public void animateTick(Level var1, BlockPos var2, Random var3) {
      this.getType().animateTick(â˜ƒ, â˜ƒ, this, â˜ƒ);
   }

   public boolean isRandomlyTicking() {
      return this.getType().isRandomlyTicking();
   }

   public void randomTick(Level var1, BlockPos var2, Random var3) {
      this.getType().randomTick(â˜ƒ, â˜ƒ, this, â˜ƒ);
   }

   public Vec3 getFlow(BlockGetter var1, BlockPos var2) {
      return this.getType().getFlow(â˜ƒ, â˜ƒ, this);
   }

   public BlockState createLegacyBlock() {
      return this.getType().createLegacyBlock(this);
   }

   @Nullable
   public ParticleOptions getDripParticle() {
      return this.getType().getDripParticle();
   }

   public boolean is(Tag<Fluid> var1) {
      return this.getType().is(â˜ƒ);
   }

   public float getExplosionResistance() {
      return this.getType().getExplosionResistance();
   }

   public boolean canBeReplacedWith(BlockGetter var1, BlockPos var2, Fluid var3, Direction var4) {
      return this.getType().canBeReplacedWith(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public VoxelShape getShape(BlockGetter var1, BlockPos var2) {
      return this.getType().getShape(this, â˜ƒ, â˜ƒ);
   }
}
