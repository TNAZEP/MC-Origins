package net.minecraft.world.entity.item;

import java.util.function.Predicate;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FallingBlockEntity extends Entity {
   private BlockState blockState = Blocks.SAND.defaultBlockState();
   public int time;
   public boolean dropItem = true;
   private boolean cancelDrop;
   private boolean hurtEntities;
   private int fallDamageMax = 40;
   private float fallDamagePerDistance;
   public CompoundTag blockData;
   protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FallingBlockEntity.class, EntityDataSerializers.BLOCK_POS);

   public FallingBlockEntity(EntityType<? extends FallingBlockEntity> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public FallingBlockEntity(Level var1, double var2, double var4, double var6, BlockState var8) {
      this(EntityType.FALLING_BLOCK, â˜ƒ);
      this.blockState = â˜ƒ;
      this.blocksBuilding = true;
      this.setPos(â˜ƒ, â˜ƒ + (double)((1.0F - this.getBbHeight()) / 2.0F), â˜ƒ);
      this.setDeltaMovement(Vec3.ZERO);
      this.xo = â˜ƒ;
      this.yo = â˜ƒ;
      this.zo = â˜ƒ;
      this.setStartPos(this.blockPosition());
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   public void setStartPos(BlockPos var1) {
      this.entityData.set(DATA_START_POS, â˜ƒ);
   }

   public BlockPos getStartPos() {
      return this.entityData.get(DATA_START_POS);
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(DATA_START_POS, BlockPos.ZERO);
   }

   @Override
   public boolean isPickable() {
      return !this.isRemoved();
   }

   @Override
   public void tick() {
      if (this.blockState.isAir()) {
         this.discard();
      } else {
         Block â˜ƒ = this.blockState.getBlock();
         if (this.time++ == 0) {
            BlockPos â˜ƒx = this.blockPosition();
            if (this.level.getBlockState(â˜ƒx).is(â˜ƒ)) {
               this.level.removeBlock(â˜ƒx, false);
            } else if (!this.level.isClientSide) {
               this.discard();
               return;
            }
         }

         if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
         if (!this.level.isClientSide) {
            BlockPos â˜ƒ = this.blockPosition();
            boolean â˜ƒx = this.blockState.getBlock() instanceof ConcretePowderBlock;
            boolean â˜ƒxx = â˜ƒx && this.level.getFluidState(â˜ƒ).is(FluidTags.WATER);
            double â˜ƒxxx = this.getDeltaMovement().lengthSqr();
            if (â˜ƒx && â˜ƒxxx > 1.0) {
               BlockHitResult â˜ƒxxxx = this.level
                  .clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
               if (â˜ƒxxxx.getType() != HitResult.Type.MISS && this.level.getFluidState(â˜ƒxxxx.getBlockPos()).is(FluidTags.WATER)) {
                  â˜ƒ = â˜ƒxxxx.getBlockPos();
                  â˜ƒxx = true;
               }
            }

            if (this.onGround || â˜ƒxx) {
               BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
               this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
               if (!â˜ƒ.is(Blocks.MOVING_PISTON)) {
                  if (!this.cancelDrop) {
                     boolean â˜ƒx = â˜ƒ.canBeReplaced(new DirectionalPlaceContext(this.level, â˜ƒ, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                     boolean â˜ƒxx = FallingBlock.isFree(this.level.getBlockState(â˜ƒ.below())) && (!â˜ƒx || !â˜ƒxx);
                     boolean â˜ƒxxx = this.blockState.canSurvive(this.level, â˜ƒ) && !â˜ƒxx;
                     if (â˜ƒx && â˜ƒxxx) {
                        if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(â˜ƒ).getType() == Fluids.WATER) {
                           this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                        }

                        if (this.level.setBlock(â˜ƒ, this.blockState, 3)) {
                           ((ServerLevel)this.level)
                              .getChunkSource()
                              .chunkMap
                              .broadcast(this, new ClientboundBlockUpdatePacket(â˜ƒ, this.level.getBlockState(â˜ƒ)));
                           this.discard();
                           if (â˜ƒ instanceof Fallable) {
                              ((Fallable)â˜ƒ).onLand(this.level, â˜ƒ, this.blockState, â˜ƒ, this);
                           }

                           if (this.blockData != null && this.blockState.hasBlockEntity()) {
                              BlockEntity â˜ƒxxxx = this.level.getBlockEntity(â˜ƒ);
                              if (â˜ƒxxxx != null) {
                                 CompoundTag â˜ƒxxxxx = â˜ƒxxxx.save(new CompoundTag());

                                 for(String â˜ƒxxxxxx : this.blockData.getAllKeys()) {
                                    Tag â˜ƒxxxxxxx = this.blockData.get(â˜ƒxxxxxx);
                                    if (!"x".equals(â˜ƒxxxxxx) && !"y".equals(â˜ƒxxxxxx) && !"z".equals(â˜ƒxxxxxx)) {
                                       â˜ƒxxxxx.put(â˜ƒxxxxxx, â˜ƒxxxxxxx.copy());
                                    }
                                 }

                                 try {
                                    â˜ƒxxxx.load(â˜ƒxxxxx);
                                 } catch (Exception var16) {
                                    LOGGER.error("Failed to load block entity from falling block", var16);
                                 }

                                 â˜ƒxxxx.setChanged();
                              }
                           }
                        } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                           this.discard();
                           this.callOnBrokenAfterFall(â˜ƒ, â˜ƒ);
                           this.spawnAtLocation(â˜ƒ);
                        }
                     } else {
                        this.discard();
                        if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                           this.callOnBrokenAfterFall(â˜ƒ, â˜ƒ);
                           this.spawnAtLocation(â˜ƒ);
                        }
                     }
                  } else {
                     this.discard();
                     this.callOnBrokenAfterFall(â˜ƒ, â˜ƒ);
                  }
               }
            } else if (!this.level.isClientSide
               && (this.time > 100 && (â˜ƒ.getY() <= this.level.getMinBuildHeight() || â˜ƒ.getY() > this.level.getMaxBuildHeight()) || this.time > 600)) {
               if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                  this.spawnAtLocation(â˜ƒ);
               }

               this.discard();
            }
         }

         this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
      }
   }

   public void callOnBrokenAfterFall(Block var1, BlockPos var2) {
      if (â˜ƒ instanceof Fallable) {
         ((Fallable)â˜ƒ).onBrokenAfterFall(this.level, â˜ƒ, this);
      }
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      if (!this.hurtEntities) {
         return false;
      } else {
         int â˜ƒ = Mth.ceil(â˜ƒ - 1.0F);
         if (â˜ƒ < 0) {
            return false;
         } else {
            Predicate<Entity> â˜ƒx;
            DamageSource â˜ƒxx;
            if (this.blockState.getBlock() instanceof Fallable â˜ƒ) {
               â˜ƒx = â˜ƒ.getHurtsEntitySelector();
               â˜ƒxx = â˜ƒ.getFallDamageSource();
            } else {
               â˜ƒx = EntitySelector.NO_SPECTATORS;
               â˜ƒxx = DamageSource.FALLING_BLOCK;
            }

            float â˜ƒ = (float)Math.min(Mth.floor((float)â˜ƒ * this.fallDamagePerDistance), this.fallDamageMax);
            this.level.getEntities(this, this.getBoundingBox(), â˜ƒx).forEach(var2x -> var2x.hurt(â˜ƒ, â˜ƒ));
            boolean â˜ƒx = this.blockState.is(BlockTags.ANVIL);
            if (â˜ƒx && â˜ƒ > 0.0F && this.random.nextFloat() < 0.05F + (float)â˜ƒ * 0.05F) {
               BlockState â˜ƒxx = AnvilBlock.damage(this.blockState);
               if (â˜ƒxx == null) {
                  this.cancelDrop = true;
               } else {
                  this.blockState = â˜ƒxx;
               }
            }

            return false;
         }
      }
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.put("BlockState", NbtUtils.writeBlockState(this.blockState));
      â˜ƒ.putInt("Time", this.time);
      â˜ƒ.putBoolean("DropItem", this.dropItem);
      â˜ƒ.putBoolean("HurtEntities", this.hurtEntities);
      â˜ƒ.putFloat("FallHurtAmount", this.fallDamagePerDistance);
      â˜ƒ.putInt("FallHurtMax", this.fallDamageMax);
      if (this.blockData != null) {
         â˜ƒ.put("TileEntityData", this.blockData);
      }
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      this.blockState = NbtUtils.readBlockState(â˜ƒ.getCompound("BlockState"));
      this.time = â˜ƒ.getInt("Time");
      if (â˜ƒ.contains("HurtEntities", 99)) {
         this.hurtEntities = â˜ƒ.getBoolean("HurtEntities");
         this.fallDamagePerDistance = â˜ƒ.getFloat("FallHurtAmount");
         this.fallDamageMax = â˜ƒ.getInt("FallHurtMax");
      } else if (this.blockState.is(BlockTags.ANVIL)) {
         this.hurtEntities = true;
      }

      if (â˜ƒ.contains("DropItem", 99)) {
         this.dropItem = â˜ƒ.getBoolean("DropItem");
      }

      if (â˜ƒ.contains("TileEntityData", 10)) {
         this.blockData = â˜ƒ.getCompound("TileEntityData");
      }

      if (this.blockState.isAir()) {
         this.blockState = Blocks.SAND.defaultBlockState();
      }
   }

   public Level getLevel() {
      return this.level;
   }

   public void setHurtsEntities(float var1, int var2) {
      this.hurtEntities = true;
      this.fallDamagePerDistance = â˜ƒ;
      this.fallDamageMax = â˜ƒ;
   }

   @Override
   public boolean displayFireAnimation() {
      return false;
   }

   @Override
   public void fillCrashReportCategory(CrashReportCategory var1) {
      super.fillCrashReportCategory(â˜ƒ);
      â˜ƒ.setDetail("Immitating BlockState", this.blockState.toString());
   }

   public BlockState getBlockState() {
      return this.blockState;
   }

   @Override
   public boolean onlyOpCanSetNbt() {
      return true;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
   }

   @Override
   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      this.blockState = Block.stateById(â˜ƒ.getData());
      this.blocksBuilding = true;
      double â˜ƒ = â˜ƒ.getX();
      double â˜ƒx = â˜ƒ.getY();
      double â˜ƒxx = â˜ƒ.getZ();
      this.setPos(â˜ƒ, â˜ƒx + (double)((1.0F - this.getBbHeight()) / 2.0F), â˜ƒxx);
      this.setStartPos(this.blockPosition());
   }
}
