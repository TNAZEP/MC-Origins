package net.minecraft.core.dispenser;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface DispenseItemBehavior {
   Logger LOGGER = LogManager.getLogger();
   DispenseItemBehavior NOOP = (var0, var1) -> var1;

   ItemStack dispense(BlockSource var1, ItemStack var2);

   static void bootStrap() {
      DispenserBlock.registerBehavior(Items.ARROW, new AbstractProjectileDispenseBehavior() {
         @Override
         protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
            Arrow â˜ƒ = new Arrow(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
            â˜ƒ.pickup = AbstractArrow.Pickup.ALLOWED;
            return â˜ƒ;
         }
      });
      DispenserBlock.registerBehavior(Items.TIPPED_ARROW, new AbstractProjectileDispenseBehavior() {
         @Override
         protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
            Arrow â˜ƒ = new Arrow(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
            â˜ƒ.setEffectsFromItem(â˜ƒ);
            â˜ƒ.pickup = AbstractArrow.Pickup.ALLOWED;
            return â˜ƒ;
         }
      });
      DispenserBlock.registerBehavior(Items.SPECTRAL_ARROW, new AbstractProjectileDispenseBehavior() {
         @Override
         protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
            AbstractArrow â˜ƒ = new SpectralArrow(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
            â˜ƒ.pickup = AbstractArrow.Pickup.ALLOWED;
            return â˜ƒ;
         }
      });
      DispenserBlock.registerBehavior(Items.EGG, new AbstractProjectileDispenseBehavior() {
         @Override
         protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
            return Util.make(new ThrownEgg(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()), var1x -> var1x.setItem(â˜ƒ));
         }
      });
      DispenserBlock.registerBehavior(Items.SNOWBALL, new AbstractProjectileDispenseBehavior() {
         @Override
         protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
            return Util.make(new Snowball(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()), var1x -> var1x.setItem(â˜ƒ));
         }
      });
      DispenserBlock.registerBehavior(Items.EXPERIENCE_BOTTLE, new AbstractProjectileDispenseBehavior() {
         @Override
         protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
            return Util.make(new ThrownExperienceBottle(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()), var1x -> var1x.setItem(â˜ƒ));
         }

         @Override
         protected float getUncertainty() {
            return super.getUncertainty() * 0.5F;
         }

         @Override
         protected float getPower() {
            return super.getPower() * 1.25F;
         }
      });
      DispenserBlock.registerBehavior(Items.SPLASH_POTION, new DispenseItemBehavior() {
         @Override
         public ItemStack dispense(BlockSource var1, ItemStack var2) {
            return (new AbstractProjectileDispenseBehavior() {
               @Override
               protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
                  return Util.make(new ThrownPotion(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()), var1x -> var1x.setItem(â˜ƒ));
               }

               @Override
               protected float getUncertainty() {
                  return super.getUncertainty() * 0.5F;
               }

               @Override
               protected float getPower() {
                  return super.getPower() * 1.25F;
               }
            }).dispense(â˜ƒ, â˜ƒ);
         }
      });
      DispenserBlock.registerBehavior(Items.LINGERING_POTION, new DispenseItemBehavior() {
         @Override
         public ItemStack dispense(BlockSource var1, ItemStack var2) {
            return (new AbstractProjectileDispenseBehavior() {
               @Override
               protected Projectile getProjectile(Level var1, Position var2, ItemStack var3) {
                  return Util.make(new ThrownPotion(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()), var1x -> var1x.setItem(â˜ƒ));
               }

               @Override
               protected float getUncertainty() {
                  return super.getUncertainty() * 0.5F;
               }

               @Override
               protected float getPower() {
                  return super.getPower() * 1.25F;
               }
            }).dispense(â˜ƒ, â˜ƒ);
         }
      });
      DefaultDispenseItemBehavior â˜ƒ = new DefaultDispenseItemBehavior() {
         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            Direction â˜ƒ = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
            EntityType<?> â˜ƒx = ((SpawnEggItem)â˜ƒ.getItem()).getType(â˜ƒ.getTag());

            try {
               â˜ƒx.spawn(â˜ƒ.getLevel(), â˜ƒ, null, â˜ƒ.getPos().relative(â˜ƒ), MobSpawnType.DISPENSER, â˜ƒ != Direction.UP, false);
            } catch (Exception var6) {
               LOGGER.error("Error while dispensing spawn egg from dispenser at {}", â˜ƒ.getPos(), var6);
               return ItemStack.EMPTY;
            }

            â˜ƒ.shrink(1);
            â˜ƒ.getLevel().gameEvent(GameEvent.ENTITY_PLACE, â˜ƒ.getPos());
            return â˜ƒ;
         }
      };

      for(SpawnEggItem â˜ƒx : SpawnEggItem.eggs()) {
         DispenserBlock.registerBehavior(â˜ƒx, â˜ƒ);
      }

      DispenserBlock.registerBehavior(Items.ARMOR_STAND, new DefaultDispenseItemBehavior() {
         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            Direction â˜ƒ = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ);
            Level â˜ƒxx = â˜ƒ.getLevel();
            ArmorStand â˜ƒxxx = new ArmorStand(â˜ƒxx, (double)â˜ƒx.getX() + 0.5, (double)â˜ƒx.getY(), (double)â˜ƒx.getZ() + 0.5);
            EntityType.updateCustomEntityTag(â˜ƒxx, null, â˜ƒxxx, â˜ƒ.getTag());
            â˜ƒxxx.setYRot(â˜ƒ.toYRot());
            â˜ƒxx.addFreshEntity(â˜ƒxxx);
            â˜ƒ.shrink(1);
            return â˜ƒ;
         }
      });
      DispenserBlock.registerBehavior(Items.SADDLE, new OptionalDispenseItemBehavior() {
         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            BlockPos â˜ƒ = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            List<LivingEntity> â˜ƒx = â˜ƒ.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(â˜ƒ), var0 -> {
               if (!(var0 instanceof Saddleable)) {
                  return false;
               } else {
                  Saddleable â˜ƒ = (Saddleable)var0;
                  return !â˜ƒ.isSaddled() && â˜ƒ.isSaddleable();
               }
            });
            if (!â˜ƒx.isEmpty()) {
               ((Saddleable)â˜ƒx.get(0)).equipSaddle(SoundSource.BLOCKS);
               â˜ƒ.shrink(1);
               this.setSuccess(true);
               return â˜ƒ;
            } else {
               return super.execute(â˜ƒ, â˜ƒ);
            }
         }
      });
      DefaultDispenseItemBehavior â˜ƒx = new OptionalDispenseItemBehavior() {
         @Override
         protected ItemStack execute(BlockSource var1, ItemStack var2) {
            BlockPos â˜ƒ = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));

            for(AbstractHorse â˜ƒx : â˜ƒ.getLevel().getEntitiesOfClass(AbstractHorse.class, new AABB(â˜ƒ), var0 -> var0.isAlive() && var0.canWearArmor())) {
               if (â˜ƒx.isArmor(â˜ƒ) && !â˜ƒx.isWearingArmor() && â˜ƒx.isTamed()) {
                  â˜ƒx.getSlot(401).set(â˜ƒ.split(1));
                  this.setSuccess(true);
                  return â˜ƒ;
               }
            }

            return super.execute(â˜ƒ, â˜ƒ);
         }
      };
      DispenserBlock.registerBehavior(Items.LEATHER_HORSE_ARMOR, â˜ƒx);
      DispenserBlock.registerBehavior(Items.IRON_HORSE_ARMOR, â˜ƒx);
      DispenserBlock.registerBehavior(Items.GOLDEN_HORSE_ARMOR, â˜ƒx);
      DispenserBlock.registerBehavior(Items.DIAMOND_HORSE_ARMOR, â˜ƒx);
      DispenserBlock.registerBehavior(Items.WHITE_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.ORANGE_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.CYAN_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.BLUE_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.BROWN_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.BLACK_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.GRAY_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.GREEN_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.LIGHT_BLUE_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.LIGHT_GRAY_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.LIME_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.MAGENTA_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.PINK_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.PURPLE_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.RED_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(Items.YELLOW_CARPET, â˜ƒx);
      DispenserBlock.registerBehavior(
         Items.CHEST,
         new OptionalDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource var1, ItemStack var2) {
               BlockPos â˜ƒ = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
   
               for(AbstractChestedHorse â˜ƒx : â˜ƒ.getLevel()
                  .getEntitiesOfClass(AbstractChestedHorse.class, new AABB(â˜ƒ), var0 -> var0.isAlive() && !var0.hasChest())) {
                  if (â˜ƒx.isTamed() && â˜ƒx.getSlot(499).set(â˜ƒ)) {
                     â˜ƒ.shrink(1);
                     this.setSuccess(true);
                     return â˜ƒ;
                  }
               }
   
               return super.execute(â˜ƒ, â˜ƒ);
            }
         }
      );
      DispenserBlock.registerBehavior(Items.FIREWORK_ROCKET, new DefaultDispenseItemBehavior() {
         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            Direction â˜ƒ = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
            FireworkRocketEntity â˜ƒx = new FireworkRocketEntity(â˜ƒ.getLevel(), â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.x(), true);
            DispenseItemBehavior.setEntityPokingOutOfBlock(â˜ƒ, â˜ƒx, â˜ƒ);
            â˜ƒx.shoot((double)â˜ƒ.getStepX(), (double)â˜ƒ.getStepY(), (double)â˜ƒ.getStepZ(), 0.5F, 1.0F);
            â˜ƒ.getLevel().addFreshEntity(â˜ƒx);
            â˜ƒ.shrink(1);
            return â˜ƒ;
         }

         @Override
         protected void playSound(BlockSource var1) {
            â˜ƒ.getLevel().levelEvent(1004, â˜ƒ.getPos(), 0);
         }
      });
      DispenserBlock.registerBehavior(Items.FIRE_CHARGE, new DefaultDispenseItemBehavior() {
         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            Direction â˜ƒ = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
            Position â˜ƒx = DispenserBlock.getDispensePosition(â˜ƒ);
            double â˜ƒxx = â˜ƒx.x() + (double)((float)â˜ƒ.getStepX() * 0.3F);
            double â˜ƒxxx = â˜ƒx.y() + (double)((float)â˜ƒ.getStepY() * 0.3F);
            double â˜ƒxxxx = â˜ƒx.z() + (double)((float)â˜ƒ.getStepZ() * 0.3F);
            Level â˜ƒxxxxx = â˜ƒ.getLevel();
            Random â˜ƒxxxxxx = â˜ƒxxxxx.random;
            double â˜ƒxxxxxxx = â˜ƒxxxxxx.nextGaussian() * 0.05 + (double)â˜ƒ.getStepX();
            double â˜ƒxxxxxxxx = â˜ƒxxxxxx.nextGaussian() * 0.05 + (double)â˜ƒ.getStepY();
            double â˜ƒxxxxxxxxx = â˜ƒxxxxxx.nextGaussian() * 0.05 + (double)â˜ƒ.getStepZ();
            SmallFireball â˜ƒxxxxxxxxxx = new SmallFireball(â˜ƒxxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            â˜ƒxxxxx.addFreshEntity(Util.make(â˜ƒxxxxxxxxxx, var1x -> var1x.setItem(â˜ƒ)));
            â˜ƒ.shrink(1);
            return â˜ƒ;
         }

         @Override
         protected void playSound(BlockSource var1) {
            â˜ƒ.getLevel().levelEvent(1018, â˜ƒ.getPos(), 0);
         }
      });
      DispenserBlock.registerBehavior(Items.OAK_BOAT, new BoatDispenseItemBehavior(Boat.Type.OAK));
      DispenserBlock.registerBehavior(Items.SPRUCE_BOAT, new BoatDispenseItemBehavior(Boat.Type.SPRUCE));
      DispenserBlock.registerBehavior(Items.BIRCH_BOAT, new BoatDispenseItemBehavior(Boat.Type.BIRCH));
      DispenserBlock.registerBehavior(Items.JUNGLE_BOAT, new BoatDispenseItemBehavior(Boat.Type.JUNGLE));
      DispenserBlock.registerBehavior(Items.DARK_OAK_BOAT, new BoatDispenseItemBehavior(Boat.Type.DARK_OAK));
      DispenserBlock.registerBehavior(Items.ACACIA_BOAT, new BoatDispenseItemBehavior(Boat.Type.ACACIA));
      DispenseItemBehavior â˜ƒxx = new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            DispensibleContainerItem â˜ƒ = (DispensibleContainerItem)â˜ƒ.getItem();
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            Level â˜ƒxx = â˜ƒ.getLevel();
            if (â˜ƒ.emptyContents(null, â˜ƒxx, â˜ƒx, null)) {
               â˜ƒ.checkExtraContent(null, â˜ƒxx, â˜ƒ, â˜ƒx);
               return new ItemStack(Items.BUCKET);
            } else {
               return this.defaultDispenseItemBehavior.dispense(â˜ƒ, â˜ƒ);
            }
         }
      };
      DispenserBlock.registerBehavior(Items.LAVA_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.WATER_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.POWDER_SNOW_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.SALMON_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.COD_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.PUFFERFISH_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.TROPICAL_FISH_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.AXOLOTL_BUCKET, â˜ƒxx);
      DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            LevelAccessor â˜ƒ = â˜ƒ.getLevel();
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            Block â˜ƒxxx = â˜ƒxx.getBlock();
            if (â˜ƒxxx instanceof BucketPickup) {
               ItemStack â˜ƒxxxx = ((BucketPickup)â˜ƒxxx).pickupBlock(â˜ƒ, â˜ƒx, â˜ƒxx);
               if (â˜ƒxxxx.isEmpty()) {
                  return super.execute(â˜ƒ, â˜ƒ);
               } else {
                  â˜ƒ.gameEvent(null, GameEvent.FLUID_PICKUP, â˜ƒx);
                  Item â˜ƒxxxx = â˜ƒxxxx.getItem();
                  â˜ƒ.shrink(1);
                  if (â˜ƒ.isEmpty()) {
                     return new ItemStack(â˜ƒxxxx);
                  } else {
                     if (â˜ƒ.<DispenserBlockEntity>getEntity().addItem(new ItemStack(â˜ƒxxxx)) < 0) {
                        this.defaultDispenseItemBehavior.dispense(â˜ƒ, new ItemStack(â˜ƒxxxx));
                     }

                     return â˜ƒ;
                  }
               }
            } else {
               return super.execute(â˜ƒ, â˜ƒ);
            }
         }
      });
      DispenserBlock.registerBehavior(Items.FLINT_AND_STEEL, new OptionalDispenseItemBehavior() {
         @Override
         protected ItemStack execute(BlockSource var1, ItemStack var2) {
            Level â˜ƒ = â˜ƒ.getLevel();
            this.setSuccess(true);
            Direction â˜ƒx = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos â˜ƒxx = â˜ƒ.getPos().relative(â˜ƒx);
            BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
            if (BaseFireBlock.canBePlacedAt(â˜ƒ, â˜ƒxx, â˜ƒx)) {
               â˜ƒ.setBlockAndUpdate(â˜ƒxx, BaseFireBlock.getState(â˜ƒ, â˜ƒxx));
               â˜ƒ.gameEvent(null, GameEvent.BLOCK_PLACE, â˜ƒxx);
            } else if (CampfireBlock.canLight(â˜ƒxxx) || CandleBlock.canLight(â˜ƒxxx) || CandleCakeBlock.canLight(â˜ƒxxx)) {
               â˜ƒ.setBlockAndUpdate(â˜ƒxx, â˜ƒxxx.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)));
               â˜ƒ.gameEvent(null, GameEvent.BLOCK_CHANGE, â˜ƒxx);
            } else if (â˜ƒxxx.getBlock() instanceof TntBlock) {
               TntBlock.explode(â˜ƒ, â˜ƒxx);
               â˜ƒ.removeBlock(â˜ƒxx, false);
            } else {
               this.setSuccess(false);
            }

            if (this.isSuccess() && â˜ƒ.hurt(1, â˜ƒ.random, null)) {
               â˜ƒ.setCount(0);
            }

            return â˜ƒ;
         }
      });
      DispenserBlock.registerBehavior(Items.BONE_MEAL, new OptionalDispenseItemBehavior() {
         @Override
         protected ItemStack execute(BlockSource var1, ItemStack var2) {
            this.setSuccess(true);
            Level â˜ƒ = â˜ƒ.getLevel();
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            if (!BoneMealItem.growCrop(â˜ƒ, â˜ƒ, â˜ƒx) && !BoneMealItem.growWaterPlant(â˜ƒ, â˜ƒ, â˜ƒx, null)) {
               this.setSuccess(false);
            } else if (!â˜ƒ.isClientSide) {
               â˜ƒ.levelEvent(1505, â˜ƒx, 0);
            }

            return â˜ƒ;
         }
      });
      DispenserBlock.registerBehavior(Blocks.TNT, new DefaultDispenseItemBehavior() {
         @Override
         protected ItemStack execute(BlockSource var1, ItemStack var2) {
            Level â˜ƒ = â˜ƒ.getLevel();
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            PrimedTnt â˜ƒxx = new PrimedTnt(â˜ƒ, (double)â˜ƒx.getX() + 0.5, (double)â˜ƒx.getY(), (double)â˜ƒx.getZ() + 0.5, null);
            â˜ƒ.addFreshEntity(â˜ƒxx);
            â˜ƒ.playSound(null, â˜ƒxx.getX(), â˜ƒxx.getY(), â˜ƒxx.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            â˜ƒ.gameEvent(null, GameEvent.ENTITY_PLACE, â˜ƒx);
            â˜ƒ.shrink(1);
            return â˜ƒ;
         }
      });
      DispenseItemBehavior â˜ƒxxx = new OptionalDispenseItemBehavior() {
         @Override
         protected ItemStack execute(BlockSource var1, ItemStack var2) {
            this.setSuccess(ArmorItem.dispenseArmor(â˜ƒ, â˜ƒ));
            return â˜ƒ;
         }
      };
      DispenserBlock.registerBehavior(Items.CREEPER_HEAD, â˜ƒxxx);
      DispenserBlock.registerBehavior(Items.ZOMBIE_HEAD, â˜ƒxxx);
      DispenserBlock.registerBehavior(Items.DRAGON_HEAD, â˜ƒxxx);
      DispenserBlock.registerBehavior(Items.SKELETON_SKULL, â˜ƒxxx);
      DispenserBlock.registerBehavior(Items.PLAYER_HEAD, â˜ƒxxx);
      DispenserBlock.registerBehavior(
         Items.WITHER_SKELETON_SKULL,
         new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource var1, ItemStack var2) {
               Level â˜ƒ = â˜ƒ.getLevel();
               Direction â˜ƒx = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
               BlockPos â˜ƒxx = â˜ƒ.getPos().relative(â˜ƒx);
               if (â˜ƒ.isEmptyBlock(â˜ƒxx) && WitherSkullBlock.canSpawnMob(â˜ƒ, â˜ƒxx, â˜ƒ)) {
                  â˜ƒ.setBlock(
                     â˜ƒxx,
                     Blocks.WITHER_SKELETON_SKULL
                        .defaultBlockState()
                        .setValue(SkullBlock.ROTATION, Integer.valueOf(â˜ƒx.getAxis() == Direction.Axis.Y ? 0 : â˜ƒx.getOpposite().get2DDataValue() * 4)),
                     3
                  );
                  â˜ƒ.gameEvent(null, GameEvent.BLOCK_PLACE, â˜ƒxx);
                  BlockEntity â˜ƒxxx = â˜ƒ.getBlockEntity(â˜ƒxx);
                  if (â˜ƒxxx instanceof SkullBlockEntity) {
                     WitherSkullBlock.checkSpawn(â˜ƒ, â˜ƒxx, (SkullBlockEntity)â˜ƒxxx);
                  }
   
                  â˜ƒ.shrink(1);
                  this.setSuccess(true);
               } else {
                  this.setSuccess(ArmorItem.dispenseArmor(â˜ƒ, â˜ƒ));
               }
   
               return â˜ƒ;
            }
         }
      );
      DispenserBlock.registerBehavior(Blocks.CARVED_PUMPKIN, new OptionalDispenseItemBehavior() {
         @Override
         protected ItemStack execute(BlockSource var1, ItemStack var2) {
            Level â˜ƒ = â˜ƒ.getLevel();
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            CarvedPumpkinBlock â˜ƒxx = (CarvedPumpkinBlock)Blocks.CARVED_PUMPKIN;
            if (â˜ƒ.isEmptyBlock(â˜ƒx) && â˜ƒxx.canSpawnGolem(â˜ƒ, â˜ƒx)) {
               if (!â˜ƒ.isClientSide) {
                  â˜ƒ.setBlock(â˜ƒx, â˜ƒxx.defaultBlockState(), 3);
                  â˜ƒ.gameEvent(null, GameEvent.BLOCK_PLACE, â˜ƒx);
               }

               â˜ƒ.shrink(1);
               this.setSuccess(true);
            } else {
               this.setSuccess(ArmorItem.dispenseArmor(â˜ƒ, â˜ƒ));
            }

            return â˜ƒ;
         }
      });
      DispenserBlock.registerBehavior(Blocks.SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());

      for(DyeColor â˜ƒxxxx : DyeColor.values()) {
         DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(â˜ƒxxxx).asItem(), new ShulkerBoxDispenseBehavior());
      }

      DispenserBlock.registerBehavior(Items.GLASS_BOTTLE.asItem(), new OptionalDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

         private ItemStack takeLiquid(BlockSource var1, ItemStack var2, ItemStack var3) {
            â˜ƒ.shrink(1);
            if (â˜ƒ.isEmpty()) {
               â˜ƒ.getLevel().gameEvent(null, GameEvent.FLUID_PICKUP, â˜ƒ.getPos());
               return â˜ƒ.copy();
            } else {
               if (â˜ƒ.<DispenserBlockEntity>getEntity().addItem(â˜ƒ.copy()) < 0) {
                  this.defaultDispenseItemBehavior.dispense(â˜ƒ, â˜ƒ.copy());
               }

               return â˜ƒ;
            }
         }

         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            this.setSuccess(false);
            ServerLevel â˜ƒ = â˜ƒ.getLevel();
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            if (â˜ƒxx.is(BlockTags.BEEHIVES, var0 -> var0.hasProperty(BeehiveBlock.HONEY_LEVEL)) && â˜ƒxx.getValue(BeehiveBlock.HONEY_LEVEL) >= 5) {
               ((BeehiveBlock)â˜ƒxx.getBlock()).releaseBeesAndResetHoneyLevel(â˜ƒ, â˜ƒxx, â˜ƒx, null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
               this.setSuccess(true);
               return this.takeLiquid(â˜ƒ, â˜ƒ, new ItemStack(Items.HONEY_BOTTLE));
            } else if (â˜ƒ.getFluidState(â˜ƒx).is(FluidTags.WATER)) {
               this.setSuccess(true);
               return this.takeLiquid(â˜ƒ, â˜ƒ, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
            } else {
               return super.execute(â˜ƒ, â˜ƒ);
            }
         }
      });
      DispenserBlock.registerBehavior(Items.GLOWSTONE, new OptionalDispenseItemBehavior() {
         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            Direction â˜ƒ = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos â˜ƒx = â˜ƒ.getPos().relative(â˜ƒ);
            Level â˜ƒxx = â˜ƒ.getLevel();
            BlockState â˜ƒxxx = â˜ƒxx.getBlockState(â˜ƒx);
            this.setSuccess(true);
            if (â˜ƒxxx.is(Blocks.RESPAWN_ANCHOR)) {
               if (â˜ƒxxx.getValue(RespawnAnchorBlock.CHARGE) != 4) {
                  RespawnAnchorBlock.charge(â˜ƒxx, â˜ƒx, â˜ƒxxx);
                  â˜ƒ.shrink(1);
               } else {
                  this.setSuccess(false);
               }

               return â˜ƒ;
            } else {
               return super.execute(â˜ƒ, â˜ƒ);
            }
         }
      });
      DispenserBlock.registerBehavior(Items.SHEARS.asItem(), new ShearsDispenseItemBehavior());
      DispenserBlock.registerBehavior(Items.HONEYCOMB, new OptionalDispenseItemBehavior() {
         @Override
         public ItemStack execute(BlockSource var1, ItemStack var2) {
            BlockPos â˜ƒ = â˜ƒ.getPos().relative(â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
            Level â˜ƒx = â˜ƒ.getLevel();
            BlockState â˜ƒxx = â˜ƒx.getBlockState(â˜ƒ);
            Optional<BlockState> â˜ƒxxx = HoneycombItem.getWaxed(â˜ƒxx);
            if (â˜ƒxxx.isPresent()) {
               â˜ƒx.setBlockAndUpdate(â˜ƒ, (BlockState)â˜ƒxxx.get());
               â˜ƒx.levelEvent(3003, â˜ƒ, 0);
               â˜ƒ.shrink(1);
               this.setSuccess(true);
               return â˜ƒ;
            } else {
               return super.execute(â˜ƒ, â˜ƒ);
            }
         }
      });
   }

   static void setEntityPokingOutOfBlock(BlockSource var0, Entity var1, Direction var2) {
      â˜ƒ.setPos(
         â˜ƒ.x() + (double)â˜ƒ.getStepX() * (0.5000099999997474 - (double)â˜ƒ.getBbWidth() / 2.0),
         â˜ƒ.y() + (double)â˜ƒ.getStepY() * (0.5000099999997474 - (double)â˜ƒ.getBbHeight() / 2.0) - (double)â˜ƒ.getBbHeight() / 2.0,
         â˜ƒ.z() + (double)â˜ƒ.getStepZ() * (0.5000099999997474 - (double)â˜ƒ.getBbWidth() / 2.0)
      );
   }
}
