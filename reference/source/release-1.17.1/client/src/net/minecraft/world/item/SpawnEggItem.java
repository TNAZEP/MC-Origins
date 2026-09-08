package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpawnEggItem extends Item {
   private static final Map<EntityType<? extends Mob>, SpawnEggItem> BY_ID = Maps.<EntityType<? extends Mob>, SpawnEggItem>newIdentityHashMap();
   private final int backgroundColor;
   private final int highlightColor;
   private final EntityType<?> defaultType;

   public SpawnEggItem(EntityType<? extends Mob> var1, int var2, int var3, Item.Properties var4) {
      super(â˜ƒ);
      this.defaultType = â˜ƒ;
      this.backgroundColor = â˜ƒ;
      this.highlightColor = â˜ƒ;
      BY_ID.put(â˜ƒ, this);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      if (!(â˜ƒ instanceof ServerLevel)) {
         return InteractionResult.SUCCESS;
      } else {
         ItemStack â˜ƒ = â˜ƒ.getItemInHand();
         BlockPos â˜ƒx = â˜ƒ.getClickedPos();
         Direction â˜ƒxx = â˜ƒ.getClickedFace();
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx);
         if (â˜ƒxxx.is(Blocks.SPAWNER)) {
            BlockEntity â˜ƒxxxx = â˜ƒ.getBlockEntity(â˜ƒx);
            if (â˜ƒxxxx instanceof SpawnerBlockEntity) {
               BaseSpawner â˜ƒxxxxx = ((SpawnerBlockEntity)â˜ƒxxxx).getSpawner();
               EntityType<?> â˜ƒxxxxxx = this.getType(â˜ƒ.getTag());
               â˜ƒxxxxx.setEntityId(â˜ƒxxxxxx);
               â˜ƒxxxx.setChanged();
               â˜ƒ.sendBlockUpdated(â˜ƒx, â˜ƒxxx, â˜ƒxxx, 3);
               â˜ƒ.shrink(1);
               return InteractionResult.CONSUME;
            }
         }

         BlockPos â˜ƒ;
         if (â˜ƒxxx.getCollisionShape(â˜ƒ, â˜ƒx).isEmpty()) {
            â˜ƒ = â˜ƒx;
         } else {
            â˜ƒ = â˜ƒx.relative(â˜ƒxx);
         }

         EntityType<?> â˜ƒ = this.getType(â˜ƒ.getTag());
         if (â˜ƒ.spawn((ServerLevel)â˜ƒ, â˜ƒ, â˜ƒ.getPlayer(), â˜ƒ, MobSpawnType.SPAWN_EGG, true, !Objects.equals(â˜ƒx, â˜ƒ) && â˜ƒxx == Direction.UP) != null) {
            â˜ƒ.shrink(1);
            â˜ƒ.gameEvent(â˜ƒ.getPlayer(), GameEvent.ENTITY_PLACE, â˜ƒx);
         }

         return InteractionResult.CONSUME;
      }
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      HitResult â˜ƒx = getPlayerPOVHitResult(â˜ƒ, â˜ƒ, ClipContext.Fluid.SOURCE_ONLY);
      if (â˜ƒx.getType() != HitResult.Type.BLOCK) {
         return InteractionResultHolder.pass(â˜ƒ);
      } else if (!(â˜ƒ instanceof ServerLevel)) {
         return InteractionResultHolder.success(â˜ƒ);
      } else {
         BlockHitResult â˜ƒ = (BlockHitResult)â˜ƒx;
         BlockPos â˜ƒx = â˜ƒ.getBlockPos();
         if (!(â˜ƒ.getBlockState(â˜ƒx).getBlock() instanceof LiquidBlock)) {
            return InteractionResultHolder.pass(â˜ƒ);
         } else if (â˜ƒ.mayInteract(â˜ƒ, â˜ƒx) && â˜ƒ.mayUseItemAt(â˜ƒx, â˜ƒ.getDirection(), â˜ƒ)) {
            EntityType<?> â˜ƒ = this.getType(â˜ƒ.getTag());
            if (â˜ƒ.spawn((ServerLevel)â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, MobSpawnType.SPAWN_EGG, false, false) == null) {
               return InteractionResultHolder.pass(â˜ƒ);
            } else {
               if (!â˜ƒ.getAbilities().instabuild) {
                  â˜ƒ.shrink(1);
               }

               â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
               â˜ƒ.gameEvent(GameEvent.ENTITY_PLACE, â˜ƒ);
               return InteractionResultHolder.consume(â˜ƒ);
            }
         } else {
            return InteractionResultHolder.fail(â˜ƒ);
         }
      }
   }

   public boolean spawnsEntity(@Nullable CompoundTag var1, EntityType<?> var2) {
      return Objects.equals(this.getType(â˜ƒ), â˜ƒ);
   }

   public int getColor(int var1) {
      return â˜ƒ == 0 ? this.backgroundColor : this.highlightColor;
   }

   @Nullable
   public static SpawnEggItem byId(@Nullable EntityType<?> var0) {
      return (SpawnEggItem)BY_ID.get(â˜ƒ);
   }

   public static Iterable<SpawnEggItem> eggs() {
      return Iterables.unmodifiableIterable(BY_ID.values());
   }

   public EntityType<?> getType(@Nullable CompoundTag var1) {
      if (â˜ƒ != null && â˜ƒ.contains("EntityTag", 10)) {
         CompoundTag â˜ƒ = â˜ƒ.getCompound("EntityTag");
         if (â˜ƒ.contains("id", 8)) {
            return (EntityType<?>)EntityType.byString(â˜ƒ.getString("id")).orElse(this.defaultType);
         }
      }

      return this.defaultType;
   }

   public Optional<Mob> spawnOffspringFromSpawnEgg(Player var1, Mob var2, EntityType<? extends Mob> var3, ServerLevel var4, Vec3 var5, ItemStack var6) {
      if (!this.spawnsEntity(â˜ƒ.getTag(), â˜ƒ)) {
         return Optional.empty();
      } else {
         Mob â˜ƒ;
         if (â˜ƒ instanceof AgeableMob) {
            â˜ƒ = ((AgeableMob)â˜ƒ).getBreedOffspring(â˜ƒ, (AgeableMob)â˜ƒ);
         } else {
            â˜ƒ = â˜ƒ.create(â˜ƒ);
         }

         if (â˜ƒ == null) {
            return Optional.empty();
         } else {
            â˜ƒ.setBaby(true);
            if (!â˜ƒ.isBaby()) {
               return Optional.empty();
            } else {
               â˜ƒ.moveTo(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z(), 0.0F, 0.0F);
               â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
               if (â˜ƒ.hasCustomHoverName()) {
                  â˜ƒ.setCustomName(â˜ƒ.getHoverName());
               }

               if (!â˜ƒ.getAbilities().instabuild) {
                  â˜ƒ.shrink(1);
               }

               return Optional.of(â˜ƒ);
            }
         }
      }
   }
}
