package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class TntBlock extends Block {
   public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

   public TntBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, Boolean.valueOf(false)));
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.hasNeighborSignal(â˜ƒ)) {
            explode(â˜ƒ, â˜ƒ);
            â˜ƒ.removeBlock(â˜ƒ, false);
         }
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (â˜ƒ.hasNeighborSignal(â˜ƒ)) {
         explode(â˜ƒ, â˜ƒ);
         â˜ƒ.removeBlock(â˜ƒ, false);
      }
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      if (!â˜ƒ.isClientSide() && !â˜ƒ.isCreative() && â˜ƒ.getValue(UNSTABLE)) {
         explode(â˜ƒ, â˜ƒ);
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void wasExploded(Level var1, BlockPos var2, Explosion var3) {
      if (!â˜ƒ.isClientSide) {
         PrimedTnt â˜ƒ = new PrimedTnt(â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, â˜ƒ.getSourceMob());
         int â˜ƒx = â˜ƒ.getFuse();
         â˜ƒ.setFuse((short)(â˜ƒ.random.nextInt(â˜ƒx / 4) + â˜ƒx / 8));
         â˜ƒ.addFreshEntity(â˜ƒ);
      }
   }

   public static void explode(Level var0, BlockPos var1) {
      explode(â˜ƒ, â˜ƒ, null);
   }

   private static void explode(Level var0, BlockPos var1, @Nullable LivingEntity var2) {
      if (!â˜ƒ.isClientSide) {
         PrimedTnt â˜ƒ = new PrimedTnt(â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, â˜ƒ);
         â˜ƒ.addFreshEntity(â˜ƒ);
         â˜ƒ.playSound(null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.PRIME_FUSE, â˜ƒ);
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!â˜ƒ.is(Items.FLINT_AND_STEEL) && !â˜ƒ.is(Items.FIRE_CHARGE)) {
         return super.use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         explode(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 11);
         Item â˜ƒ = â˜ƒ.getItem();
         if (!â˜ƒ.isCreative()) {
            if (â˜ƒ.is(Items.FLINT_AND_STEEL)) {
               â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
            } else {
               â˜ƒ.shrink(1);
            }
         }

         â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒ));
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      if (!â˜ƒ.isClientSide) {
         BlockPos â˜ƒ = â˜ƒ.getBlockPos();
         Entity â˜ƒx = â˜ƒ.getOwner();
         if (â˜ƒ.isOnFire() && â˜ƒ.mayInteract(â˜ƒ, â˜ƒ)) {
            explode(â˜ƒ, â˜ƒ, â˜ƒx instanceof LivingEntity ? (LivingEntity)â˜ƒx : null);
            â˜ƒ.removeBlock(â˜ƒ, false);
         }
      }
   }

   @Override
   public boolean dropFromExplosion(Explosion var1) {
      return false;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(UNSTABLE);
   }
}
