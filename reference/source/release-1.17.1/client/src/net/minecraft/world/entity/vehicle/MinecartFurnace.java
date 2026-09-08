package net.minecraft.world.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MinecartFurnace extends AbstractMinecart {
   private static final EntityDataAccessor<Boolean> DATA_ID_FUEL = SynchedEntityData.defineId(MinecartFurnace.class, EntityDataSerializers.BOOLEAN);
   private int fuel;
   public double xPush;
   public double zPush;
   private static final Ingredient INGREDIENT = Ingredient.of(Items.COAL, Items.CHARCOAL);

   public MinecartFurnace(EntityType<? extends MinecartFurnace> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public MinecartFurnace(Level var1, double var2, double var4, double var6) {
      super(EntityType.FURNACE_MINECART, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public AbstractMinecart.Type getMinecartType() {
      return AbstractMinecart.Type.FURNACE;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ID_FUEL, false);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level.isClientSide()) {
         if (this.fuel > 0) {
            --this.fuel;
         }

         if (this.fuel <= 0) {
            this.xPush = 0.0;
            this.zPush = 0.0;
         }

         this.setHasFuel(this.fuel > 0);
      }

      if (this.hasFuel() && this.random.nextInt(4) == 0) {
         this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.8, this.getZ(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected double getMaxSpeed() {
      return (this.isInWater() ? 3.0 : 4.0) / 20.0;
   }

   @Override
   public void destroy(DamageSource var1) {
      super.destroy(â˜ƒ);
      if (!â˜ƒ.isExplosion() && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         this.spawnAtLocation(Blocks.FURNACE);
      }
   }

   @Override
   protected void moveAlongTrack(BlockPos var1, BlockState var2) {
      double â˜ƒ = 1.0E-4;
      double â˜ƒx = 0.001;
      super.moveAlongTrack(â˜ƒ, â˜ƒ);
      Vec3 â˜ƒxx = this.getDeltaMovement();
      double â˜ƒxxx = â˜ƒxx.horizontalDistanceSqr();
      double â˜ƒxxxx = this.xPush * this.xPush + this.zPush * this.zPush;
      if (â˜ƒxxxx > 1.0E-4 && â˜ƒxxx > 0.001) {
         double â˜ƒxxxxx = Math.sqrt(â˜ƒxxx);
         double â˜ƒxxxxxx = Math.sqrt(â˜ƒxxxx);
         this.xPush = â˜ƒxx.x / â˜ƒxxxxx * â˜ƒxxxxxx;
         this.zPush = â˜ƒxx.z / â˜ƒxxxxx * â˜ƒxxxxxx;
      }
   }

   @Override
   protected void applyNaturalSlowdown() {
      double â˜ƒ = this.xPush * this.xPush + this.zPush * this.zPush;
      if (â˜ƒ > 1.0E-7) {
         â˜ƒ = Math.sqrt(â˜ƒ);
         this.xPush /= â˜ƒ;
         this.zPush /= â˜ƒ;
         Vec3 â˜ƒx = this.getDeltaMovement().multiply(0.8, 0.0, 0.8).add(this.xPush, 0.0, this.zPush);
         if (this.isInWater()) {
            â˜ƒx = â˜ƒx.scale(0.1);
         }

         this.setDeltaMovement(â˜ƒx);
      } else {
         this.setDeltaMovement(this.getDeltaMovement().multiply(0.98, 0.0, 0.98));
      }

      super.applyNaturalSlowdown();
   }

   @Override
   public InteractionResult interact(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (INGREDIENT.test(â˜ƒ) && this.fuel + 3600 <= 32000) {
         if (!â˜ƒ.getAbilities().instabuild) {
            â˜ƒ.shrink(1);
         }

         this.fuel += 3600;
      }

      if (this.fuel > 0) {
         this.xPush = this.getX() - â˜ƒ.getX();
         this.zPush = this.getZ() - â˜ƒ.getZ();
      }

      return InteractionResult.sidedSuccess(this.level.isClientSide);
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putDouble("PushX", this.xPush);
      â˜ƒ.putDouble("PushZ", this.zPush);
      â˜ƒ.putShort("Fuel", (short)this.fuel);
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.xPush = â˜ƒ.getDouble("PushX");
      this.zPush = â˜ƒ.getDouble("PushZ");
      this.fuel = â˜ƒ.getShort("Fuel");
   }

   protected boolean hasFuel() {
      return this.entityData.get(DATA_ID_FUEL);
   }

   protected void setHasFuel(boolean var1) {
      this.entityData.set(DATA_ID_FUEL, â˜ƒ);
   }

   @Override
   public BlockState getDefaultDisplayBlockState() {
      return Blocks.FURNACE.defaultBlockState().setValue(FurnaceBlock.FACING, Direction.NORTH).setValue(FurnaceBlock.LIT, Boolean.valueOf(this.hasFuel()));
   }
}
