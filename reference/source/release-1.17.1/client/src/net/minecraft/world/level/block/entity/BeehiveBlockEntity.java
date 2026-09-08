package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BeehiveBlockEntity extends BlockEntity {
   public static final String TAG_FLOWER_POS = "FlowerPos";
   public static final String MIN_OCCUPATION_TICKS = "MinOccupationTicks";
   public static final String ENTITY_DATA = "EntityData";
   public static final String TICKS_IN_HIVE = "TicksInHive";
   public static final String HAS_NECTAR = "HasNectar";
   public static final String BEES = "Bees";
   private static final List<String> IGNORED_BEE_TAGS = Arrays.asList(
      "Air",
      "ArmorDropChances",
      "ArmorItems",
      "Brain",
      "CanPickUpLoot",
      "DeathTime",
      "FallDistance",
      "FallFlying",
      "Fire",
      "HandDropChances",
      "HandItems",
      "HurtByTimestamp",
      "HurtTime",
      "LeftHanded",
      "Motion",
      "NoGravity",
      "OnGround",
      "PortalCooldown",
      "Pos",
      "Rotation",
      "CannotEnterHiveTicks",
      "TicksSincePollination",
      "CropsGrownSincePollination",
      "HivePos",
      "Passengers",
      "Leash",
      "UUID"
   );
   public static final int MAX_OCCUPANTS = 3;
   private static final int MIN_TICKS_BEFORE_REENTERING_HIVE = 400;
   private static final int MIN_OCCUPATION_TICKS_NECTAR = 2400;
   public static final int MIN_OCCUPATION_TICKS_NECTARLESS = 600;
   private final List<BeehiveBlockEntity.BeeData> stored = Lists.<BeehiveBlockEntity.BeeData>newArrayList();
   @Nullable
   private BlockPos savedFlowerPos;

   public BeehiveBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.BEEHIVE, â˜ƒ, â˜ƒ);
   }

   @Override
   public void setChanged() {
      if (this.isFireNearby()) {
         this.emptyAllLivingFromHive(null, this.level.getBlockState(this.getBlockPos()), BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
      }

      super.setChanged();
   }

   public boolean isFireNearby() {
      if (this.level == null) {
         return false;
      } else {
         for(BlockPos â˜ƒ : BlockPos.betweenClosed(this.worldPosition.offset(-1, -1, -1), this.worldPosition.offset(1, 1, 1))) {
            if (this.level.getBlockState(â˜ƒ).getBlock() instanceof FireBlock) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isEmpty() {
      return this.stored.isEmpty();
   }

   public boolean isFull() {
      return this.stored.size() == 3;
   }

   public void emptyAllLivingFromHive(@Nullable Player var1, BlockState var2, BeehiveBlockEntity.BeeReleaseStatus var3) {
      List<Entity> â˜ƒ = this.releaseAllOccupants(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         for(Entity â˜ƒx : â˜ƒ) {
            if (â˜ƒx instanceof Bee â˜ƒxx && â˜ƒ.position().distanceToSqr(â˜ƒx.position()) <= 16.0) {
               if (!this.isSedated()) {
                  â˜ƒxx.setTarget(â˜ƒ);
               } else {
                  â˜ƒxx.setStayOutOfHiveCountdown(400);
               }
            }
         }
      }
   }

   private List<Entity> releaseAllOccupants(BlockState var1, BeehiveBlockEntity.BeeReleaseStatus var2) {
      List<Entity> â˜ƒ = Lists.<Entity>newArrayList();
      this.stored.removeIf(var4 -> releaseOccupant(this.level, this.worldPosition, â˜ƒ, var4, â˜ƒ, â˜ƒ, this.savedFlowerPos));
      return â˜ƒ;
   }

   public void addOccupant(Entity var1, boolean var2) {
      this.addOccupantWithPresetTicks(â˜ƒ, â˜ƒ, 0);
   }

   @VisibleForDebug
   public int getOccupantCount() {
      return this.stored.size();
   }

   public static int getHoneyLevel(BlockState var0) {
      return â˜ƒ.getValue(BeehiveBlock.HONEY_LEVEL);
   }

   @VisibleForDebug
   public boolean isSedated() {
      return CampfireBlock.isSmokeyPos(this.level, this.getBlockPos());
   }

   public void addOccupantWithPresetTicks(Entity var1, boolean var2, int var3) {
      if (this.stored.size() < 3) {
         â˜ƒ.stopRiding();
         â˜ƒ.ejectPassengers();
         CompoundTag â˜ƒ = new CompoundTag();
         â˜ƒ.save(â˜ƒ);
         this.storeBee(â˜ƒ, â˜ƒ, â˜ƒ);
         if (this.level != null) {
            if (â˜ƒ instanceof Bee â˜ƒx && â˜ƒx.hasSavedFlowerPos() && (!this.hasSavedFlowerPos() || this.level.random.nextBoolean())) {
               this.savedFlowerPos = â˜ƒx.getSavedFlowerPos();
            }

            BlockPos â˜ƒx = this.getBlockPos();
            this.level
               .playSound(null, (double)â˜ƒx.getX(), (double)â˜ƒx.getY(), (double)â˜ƒx.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
         }

         â˜ƒ.discard();
      }
   }

   public void storeBee(CompoundTag var1, int var2, boolean var3) {
      this.stored.add(new BeehiveBlockEntity.BeeData(â˜ƒ, â˜ƒ, â˜ƒ ? 2400 : 600));
   }

   private static boolean releaseOccupant(
      Level var0,
      BlockPos var1,
      BlockState var2,
      BeehiveBlockEntity.BeeData var3,
      @Nullable List<Entity> var4,
      BeehiveBlockEntity.BeeReleaseStatus var5,
      @Nullable BlockPos var6
   ) {
      if ((â˜ƒ.isNight() || â˜ƒ.isRaining()) && â˜ƒ != BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY) {
         return false;
      } else {
         CompoundTag â˜ƒ = â˜ƒ.entityData;
         removeIgnoredBeeTags(â˜ƒ);
         â˜ƒ.put("HivePos", NbtUtils.writeBlockPos(â˜ƒ));
         â˜ƒ.putBoolean("NoGravity", true);
         Direction â˜ƒx = â˜ƒ.getValue(BeehiveBlock.FACING);
         BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         boolean â˜ƒxxx = !â˜ƒ.getBlockState(â˜ƒxx).getCollisionShape(â˜ƒ, â˜ƒxx).isEmpty();
         if (â˜ƒxxx && â˜ƒ != BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY) {
            return false;
         } else {
            Entity â˜ƒ = EntityType.loadEntityRecursive(â˜ƒ, â˜ƒ, var0x -> var0x);
            if (â˜ƒ != null) {
               if (!â˜ƒ.getType().is(EntityTypeTags.BEEHIVE_INHABITORS)) {
                  return false;
               } else {
                  if (â˜ƒ instanceof Bee â˜ƒx) {
                     if (â˜ƒ != null && !â˜ƒx.hasSavedFlowerPos() && â˜ƒ.random.nextFloat() < 0.9F) {
                        â˜ƒx.setSavedFlowerPos(â˜ƒ);
                     }

                     if (â˜ƒ == BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED) {
                        â˜ƒx.dropOffNectar();
                        if (â˜ƒ.is(BlockTags.BEEHIVES)) {
                           int â˜ƒxx = getHoneyLevel(â˜ƒ);
                           if (â˜ƒxx < 5) {
                              int â˜ƒxxx = â˜ƒ.random.nextInt(100) == 0 ? 2 : 1;
                              if (â˜ƒxx + â˜ƒxxx > 5) {
                                 --â˜ƒxxx;
                              }

                              â˜ƒ.setBlockAndUpdate(â˜ƒ, â˜ƒ.setValue(BeehiveBlock.HONEY_LEVEL, Integer.valueOf(â˜ƒxx + â˜ƒxxx)));
                           }
                        }
                     }

                     setBeeReleaseData(â˜ƒ.ticksInHive, â˜ƒx);
                     if (â˜ƒ != null) {
                        â˜ƒ.add(â˜ƒx);
                     }

                     float â˜ƒxx = â˜ƒ.getBbWidth();
                     double â˜ƒxxx = â˜ƒxxx ? 0.0 : 0.55 + (double)(â˜ƒxx / 2.0F);
                     double â˜ƒxxxx = (double)â˜ƒ.getX() + 0.5 + â˜ƒxxx * (double)â˜ƒx.getStepX();
                     double â˜ƒxxxxx = (double)â˜ƒ.getY() + 0.5 - (double)(â˜ƒ.getBbHeight() / 2.0F);
                     double â˜ƒxxxxxx = (double)â˜ƒ.getZ() + 0.5 + â˜ƒxxx * (double)â˜ƒx.getStepZ();
                     â˜ƒ.moveTo(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ.getYRot(), â˜ƒ.getXRot());
                  }

                  â˜ƒ.playSound(null, â˜ƒ, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                  return â˜ƒ.addFreshEntity(â˜ƒ);
               }
            } else {
               return false;
            }
         }
      }
   }

   static void removeIgnoredBeeTags(CompoundTag var0) {
      for(String â˜ƒ : IGNORED_BEE_TAGS) {
         â˜ƒ.remove(â˜ƒ);
      }
   }

   private static void setBeeReleaseData(int var0, Bee var1) {
      int â˜ƒ = â˜ƒ.getAge();
      if (â˜ƒ < 0) {
         â˜ƒ.setAge(Math.min(0, â˜ƒ + â˜ƒ));
      } else if (â˜ƒ > 0) {
         â˜ƒ.setAge(Math.max(0, â˜ƒ - â˜ƒ));
      }

      â˜ƒ.setInLoveTime(Math.max(0, â˜ƒ.getInLoveTime() - â˜ƒ));
   }

   private boolean hasSavedFlowerPos() {
      return this.savedFlowerPos != null;
   }

   private static void tickOccupants(Level var0, BlockPos var1, BlockState var2, List<BeehiveBlockEntity.BeeData> var3, @Nullable BlockPos var4) {
      BeehiveBlockEntity.BeeData â˜ƒ;
      for(Iterator<BeehiveBlockEntity.BeeData> â˜ƒ = â˜ƒ.iterator(); â˜ƒ.hasNext(); ++â˜ƒ.ticksInHive) {
         â˜ƒ = (BeehiveBlockEntity.BeeData)â˜ƒ.next();
         if (â˜ƒ.ticksInHive > â˜ƒ.minOccupationTicks) {
            BeehiveBlockEntity.BeeReleaseStatus â˜ƒx = â˜ƒ.entityData.getBoolean("HasNectar")
               ? BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED
               : BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED;
            if (releaseOccupant(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, null, â˜ƒx, â˜ƒ)) {
               â˜ƒ.remove();
            }
         }
      }
   }

   public static void serverTick(Level var0, BlockPos var1, BlockState var2, BeehiveBlockEntity var3) {
      tickOccupants(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.stored, â˜ƒ.savedFlowerPos);
      if (!â˜ƒ.stored.isEmpty() && â˜ƒ.getRandom().nextDouble() < 0.005) {
         double â˜ƒ = (double)â˜ƒ.getX() + 0.5;
         double â˜ƒx = (double)â˜ƒ.getY();
         double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5;
         â˜ƒ.playSound(null, â˜ƒ, â˜ƒx, â˜ƒxx, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1.0F, 1.0F);
      }

      DebugPackets.sendHiveInfo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.stored.clear();
      ListTag â˜ƒ = â˜ƒ.getList("Bees", 10);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
         BeehiveBlockEntity.BeeData â˜ƒxxx = new BeehiveBlockEntity.BeeData(
            â˜ƒxx.getCompound("EntityData"), â˜ƒxx.getInt("TicksInHive"), â˜ƒxx.getInt("MinOccupationTicks")
         );
         this.stored.add(â˜ƒxxx);
      }

      this.savedFlowerPos = null;
      if (â˜ƒ.contains("FlowerPos")) {
         this.savedFlowerPos = NbtUtils.readBlockPos(â˜ƒ.getCompound("FlowerPos"));
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.put("Bees", this.writeBees());
      if (this.hasSavedFlowerPos()) {
         â˜ƒ.put("FlowerPos", NbtUtils.writeBlockPos(this.savedFlowerPos));
      }

      return â˜ƒ;
   }

   public ListTag writeBees() {
      ListTag â˜ƒ = new ListTag();

      for(BeehiveBlockEntity.BeeData â˜ƒx : this.stored) {
         â˜ƒx.entityData.remove("UUID");
         CompoundTag â˜ƒxx = new CompoundTag();
         â˜ƒxx.put("EntityData", â˜ƒx.entityData);
         â˜ƒxx.putInt("TicksInHive", â˜ƒx.ticksInHive);
         â˜ƒxx.putInt("MinOccupationTicks", â˜ƒx.minOccupationTicks);
         â˜ƒ.add(â˜ƒxx);
      }

      return â˜ƒ;
   }

   static class BeeData {
      final CompoundTag entityData;
      int ticksInHive;
      final int minOccupationTicks;

      BeeData(CompoundTag var1, int var2, int var3) {
         BeehiveBlockEntity.removeIgnoredBeeTags(â˜ƒ);
         this.entityData = â˜ƒ;
         this.ticksInHive = â˜ƒ;
         this.minOccupationTicks = â˜ƒ;
      }
   }

   public static enum BeeReleaseStatus {
      HONEY_DELIVERED,
      BEE_RELEASED,
      EMERGENCY;
   }
}
