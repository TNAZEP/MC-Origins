package net.minecraft.world.level.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public class BeaconBlockEntity extends BlockEntity implements MenuProvider {
   private static final int MAX_LEVELS = 4;
   public static final MobEffect[][] BEACON_EFFECTS = new MobEffect[][]{
      {MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED}, {MobEffects.DAMAGE_RESISTANCE, MobEffects.JUMP}, {MobEffects.DAMAGE_BOOST}, {MobEffects.REGENERATION}
   };
   private static final Set<MobEffect> VALID_EFFECTS = (Set<MobEffect>)Arrays.stream(BEACON_EFFECTS).flatMap(Arrays::stream).collect(Collectors.toSet());
   public static final int DATA_LEVELS = 0;
   public static final int DATA_PRIMARY = 1;
   public static final int DATA_SECONDARY = 2;
   public static final int NUM_DATA_VALUES = 3;
   private static final int BLOCKS_CHECK_PER_TICK = 10;
   List<BeaconBlockEntity.BeaconBeamSection> beamSections = Lists.<BeaconBlockEntity.BeaconBeamSection>newArrayList();
   private List<BeaconBlockEntity.BeaconBeamSection> checkingBeamSections = Lists.<BeaconBlockEntity.BeaconBeamSection>newArrayList();
   int levels;
   private int lastCheckY;
   @Nullable
   MobEffect primaryPower;
   @Nullable
   MobEffect secondaryPower;
   @Nullable
   private Component name;
   private LockCode lockKey = LockCode.NO_LOCK;
   private final ContainerData dataAccess = new ContainerData() {
      @Override
      public int get(int var1) {
         switch(â˜ƒ) {
            case 0:
               return BeaconBlockEntity.this.levels;
            case 1:
               return MobEffect.getId(BeaconBlockEntity.this.primaryPower);
            case 2:
               return MobEffect.getId(BeaconBlockEntity.this.secondaryPower);
            default:
               return 0;
         }
      }

      @Override
      public void set(int var1, int var2) {
         switch(â˜ƒ) {
            case 0:
               BeaconBlockEntity.this.levels = â˜ƒ;
               break;
            case 1:
               if (!BeaconBlockEntity.this.level.isClientSide && !BeaconBlockEntity.this.beamSections.isEmpty()) {
                  BeaconBlockEntity.playSound(BeaconBlockEntity.this.level, BeaconBlockEntity.this.worldPosition, SoundEvents.BEACON_POWER_SELECT);
               }

               BeaconBlockEntity.this.primaryPower = BeaconBlockEntity.getValidEffectById(â˜ƒ);
               break;
            case 2:
               BeaconBlockEntity.this.secondaryPower = BeaconBlockEntity.getValidEffectById(â˜ƒ);
         }
      }

      @Override
      public int getCount() {
         return 3;
      }
   };

   public BeaconBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.BEACON, â˜ƒ, â˜ƒ);
   }

   public static void tick(Level var0, BlockPos var1, BlockState var2, BeaconBlockEntity var3) {
      int â˜ƒx = â˜ƒ.getX();
      int â˜ƒxx = â˜ƒ.getY();
      int â˜ƒxxx = â˜ƒ.getZ();
      BlockPos â˜ƒ;
      if (â˜ƒ.lastCheckY < â˜ƒxx) {
         â˜ƒ = â˜ƒ;
         â˜ƒ.checkingBeamSections = Lists.<BeaconBlockEntity.BeaconBeamSection>newArrayList();
         â˜ƒ.lastCheckY = â˜ƒ.getY() - 1;
      } else {
         â˜ƒ = new BlockPos(â˜ƒx, â˜ƒ.lastCheckY + 1, â˜ƒxxx);
      }

      BeaconBlockEntity.BeaconBeamSection â˜ƒ = â˜ƒ.checkingBeamSections.isEmpty()
         ? null
         : (BeaconBlockEntity.BeaconBeamSection)â˜ƒ.checkingBeamSections.get(â˜ƒ.checkingBeamSections.size() - 1);
      int â˜ƒx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒx, â˜ƒxxx);

      for(int â˜ƒxx = 0; â˜ƒxx < 10 && â˜ƒ.getY() <= â˜ƒx; ++â˜ƒxx) {
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒ);
         Block â˜ƒxxxx = â˜ƒxxx.getBlock();
         if (â˜ƒxxxx instanceof BeaconBeamBlock) {
            float[] â˜ƒxxxxx = ((BeaconBeamBlock)â˜ƒxxxx).getColor().getTextureDiffuseColors();
            if (â˜ƒ.checkingBeamSections.size() <= 1) {
               â˜ƒ = new BeaconBlockEntity.BeaconBeamSection(â˜ƒxxxxx);
               â˜ƒ.checkingBeamSections.add(â˜ƒ);
            } else if (â˜ƒ != null) {
               if (Arrays.equals(â˜ƒxxxxx, â˜ƒ.color)) {
                  â˜ƒ.increaseHeight();
               } else {
                  â˜ƒ = new BeaconBlockEntity.BeaconBeamSection(
                     new float[]{(â˜ƒ.color[0] + â˜ƒxxxxx[0]) / 2.0F, (â˜ƒ.color[1] + â˜ƒxxxxx[1]) / 2.0F, (â˜ƒ.color[2] + â˜ƒxxxxx[2]) / 2.0F}
                  );
                  â˜ƒ.checkingBeamSections.add(â˜ƒ);
               }
            }
         } else {
            if (â˜ƒ == null || â˜ƒxxx.getLightBlock(â˜ƒ, â˜ƒ) >= 15 && !â˜ƒxxx.is(Blocks.BEDROCK)) {
               â˜ƒ.checkingBeamSections.clear();
               â˜ƒ.lastCheckY = â˜ƒx;
               break;
            }

            â˜ƒ.increaseHeight();
         }

         â˜ƒ = â˜ƒ.above();
         ++â˜ƒ.lastCheckY;
      }

      int â˜ƒxx = â˜ƒ.levels;
      if (â˜ƒ.getGameTime() % 80L == 0L) {
         if (!â˜ƒ.beamSections.isEmpty()) {
            â˜ƒ.levels = updateBase(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }

         if (â˜ƒ.levels > 0 && !â˜ƒ.beamSections.isEmpty()) {
            applyEffects(â˜ƒ, â˜ƒ, â˜ƒ.levels, â˜ƒ.primaryPower, â˜ƒ.secondaryPower);
            playSound(â˜ƒ, â˜ƒ, SoundEvents.BEACON_AMBIENT);
         }
      }

      if (â˜ƒ.lastCheckY >= â˜ƒx) {
         â˜ƒ.lastCheckY = â˜ƒ.getMinBuildHeight() - 1;
         boolean â˜ƒxx = â˜ƒxx > 0;
         â˜ƒ.beamSections = â˜ƒ.checkingBeamSections;
         if (!â˜ƒ.isClientSide) {
            boolean â˜ƒxxx = â˜ƒ.levels > 0;
            if (!â˜ƒxx && â˜ƒxxx) {
               playSound(â˜ƒ, â˜ƒ, SoundEvents.BEACON_ACTIVATE);

               for(ServerPlayer â˜ƒxxxx : â˜ƒ.getEntitiesOfClass(
                  ServerPlayer.class,
                  new AABB((double)â˜ƒx, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒx, (double)(â˜ƒxx - 4), (double)â˜ƒxxx).inflate(10.0, 5.0, 10.0)
               )) {
                  CriteriaTriggers.CONSTRUCT_BEACON.trigger(â˜ƒxxxx, â˜ƒ.levels);
               }
            } else if (â˜ƒxx && !â˜ƒxxx) {
               playSound(â˜ƒ, â˜ƒ, SoundEvents.BEACON_DEACTIVATE);
            }
         }
      }
   }

   private static int updateBase(Level var0, int var1, int var2, int var3) {
      int â˜ƒ = 0;

      for(int â˜ƒx = 1; â˜ƒx <= 4; â˜ƒ = â˜ƒx++) {
         int â˜ƒxx = â˜ƒ - â˜ƒx;
         if (â˜ƒxx < â˜ƒ.getMinBuildHeight()) {
            break;
         }

         boolean â˜ƒxx = true;

         for(int â˜ƒxxx = â˜ƒ - â˜ƒx; â˜ƒxxx <= â˜ƒ + â˜ƒx && â˜ƒxx; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = â˜ƒ - â˜ƒx; â˜ƒxxxx <= â˜ƒ + â˜ƒx; ++â˜ƒxxxx) {
               if (!â˜ƒ.getBlockState(new BlockPos(â˜ƒxxx, â˜ƒxx, â˜ƒxxxx)).is(BlockTags.BEACON_BASE_BLOCKS)) {
                  â˜ƒxx = false;
                  break;
               }
            }
         }

         if (!â˜ƒxx) {
            break;
         }
      }

      return â˜ƒ;
   }

   @Override
   public void setRemoved() {
      playSound(this.level, this.worldPosition, SoundEvents.BEACON_DEACTIVATE);
      super.setRemoved();
   }

   private static void applyEffects(Level var0, BlockPos var1, int var2, @Nullable MobEffect var3, @Nullable MobEffect var4) {
      if (!â˜ƒ.isClientSide && â˜ƒ != null) {
         double â˜ƒ = (double)(â˜ƒ * 10 + 10);
         int â˜ƒx = 0;
         if (â˜ƒ >= 4 && â˜ƒ == â˜ƒ) {
            â˜ƒx = 1;
         }

         int â˜ƒ = (9 + â˜ƒ * 2) * 20;
         AABB â˜ƒx = new AABB(â˜ƒ).inflate(â˜ƒ).expandTowards(0.0, (double)â˜ƒ.getHeight(), 0.0);
         List<Player> â˜ƒxx = â˜ƒ.getEntitiesOfClass(Player.class, â˜ƒx);

         for(Player â˜ƒxxx : â˜ƒxx) {
            â˜ƒxxx.addEffect(new MobEffectInstance(â˜ƒ, â˜ƒ, â˜ƒx, true, true));
         }

         if (â˜ƒ >= 4 && â˜ƒ != â˜ƒ && â˜ƒ != null) {
            for(Player â˜ƒxxx : â˜ƒxx) {
               â˜ƒxxx.addEffect(new MobEffectInstance(â˜ƒ, â˜ƒ, 0, true, true));
            }
         }
      }
   }

   public static void playSound(Level var0, BlockPos var1, SoundEvent var2) {
      â˜ƒ.playSound(null, â˜ƒ, â˜ƒ, SoundSource.BLOCKS, 1.0F, 1.0F);
   }

   public List<BeaconBlockEntity.BeaconBeamSection> getBeamSections() {
      return (List<BeaconBlockEntity.BeaconBeamSection>)(this.levels == 0 ? ImmutableList.of() : this.beamSections);
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 3, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   @Nullable
   static MobEffect getValidEffectById(int var0) {
      MobEffect â˜ƒ = MobEffect.byId(â˜ƒ);
      return VALID_EFFECTS.contains(â˜ƒ) ? â˜ƒ : null;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.primaryPower = getValidEffectById(â˜ƒ.getInt("Primary"));
      this.secondaryPower = getValidEffectById(â˜ƒ.getInt("Secondary"));
      if (â˜ƒ.contains("CustomName", 8)) {
         this.name = Component.Serializer.fromJson(â˜ƒ.getString("CustomName"));
      }

      this.lockKey = LockCode.fromTag(â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.putInt("Primary", MobEffect.getId(this.primaryPower));
      â˜ƒ.putInt("Secondary", MobEffect.getId(this.secondaryPower));
      â˜ƒ.putInt("Levels", this.levels);
      if (this.name != null) {
         â˜ƒ.putString("CustomName", Component.Serializer.toJson(this.name));
      }

      this.lockKey.addToTag(â˜ƒ);
      return â˜ƒ;
   }

   public void setCustomName(@Nullable Component var1) {
      this.name = â˜ƒ;
   }

   @Nullable
   @Override
   public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
      return BaseContainerBlockEntity.canUnlock(â˜ƒ, this.lockKey, this.getDisplayName())
         ? new BeaconMenu(â˜ƒ, â˜ƒ, this.dataAccess, ContainerLevelAccess.create(this.level, this.getBlockPos()))
         : null;
   }

   @Override
   public Component getDisplayName() {
      return (Component)(this.name != null ? this.name : new TranslatableComponent("container.beacon"));
   }

   @Override
   public void setLevel(Level var1) {
      super.setLevel(â˜ƒ);
      this.lastCheckY = â˜ƒ.getMinBuildHeight() - 1;
   }

   public static class BeaconBeamSection {
      final float[] color;
      private int height;

      public BeaconBeamSection(float[] var1) {
         this.color = â˜ƒ;
         this.height = 1;
      }

      protected void increaseHeight() {
         ++this.height;
      }

      public float[] getColor() {
         return this.color;
      }

      public int getHeight() {
         return this.height;
      }
   }
}
