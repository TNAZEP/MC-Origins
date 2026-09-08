package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RedStoneOreBlock extends Block {
   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

   public RedStoneOreBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
   }

   @Override
   public void attack(BlockState var1, Level var2, BlockPos var3, Player var4) {
      interact(â˜ƒ, â˜ƒ, â˜ƒ);
      super.attack(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void stepOn(Level var1, BlockPos var2, BlockState var3, Entity var4) {
      interact(â˜ƒ, â˜ƒ, â˜ƒ);
      super.stepOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         spawnParticles(â˜ƒ, â˜ƒ);
      } else {
         interact(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      return â˜ƒ.getItem() instanceof BlockItem && new BlockPlaceContext(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).canPlace() ? InteractionResult.PASS : InteractionResult.SUCCESS;
   }

   private static void interact(BlockState var0, Level var1, BlockPos var2) {
      spawnParticles(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.getValue(LIT)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(LIT, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return â˜ƒ.getValue(LIT);
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(LIT, Boolean.valueOf(false)), 3);
      }
   }

   @Override
   public void spawnAfterBreak(BlockState var1, ServerLevel var2, BlockPos var3, ItemStack var4) {
      super.spawnAfterBreak(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, â˜ƒ) == 0) {
         int â˜ƒ = 1 + â˜ƒ.random.nextInt(5);
         this.popExperience(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT)) {
         spawnParticles(â˜ƒ, â˜ƒ);
      }
   }

   private static void spawnParticles(Level var0, BlockPos var1) {
      double â˜ƒ = 0.5625;
      Random â˜ƒx = â˜ƒ.random;

      for(Direction â˜ƒxx : Direction.values()) {
         BlockPos â˜ƒxxx = â˜ƒ.relative(â˜ƒxx);
         if (!â˜ƒ.getBlockState(â˜ƒxxx).isSolidRender(â˜ƒ, â˜ƒxxx)) {
            Direction.Axis â˜ƒxxxx = â˜ƒxx.getAxis();
            double â˜ƒxxxxx = â˜ƒxxxx == Direction.Axis.X ? 0.5 + 0.5625 * (double)â˜ƒxx.getStepX() : (double)â˜ƒx.nextFloat();
            double â˜ƒxxxxxx = â˜ƒxxxx == Direction.Axis.Y ? 0.5 + 0.5625 * (double)â˜ƒxx.getStepY() : (double)â˜ƒx.nextFloat();
            double â˜ƒxxxxxxx = â˜ƒxxxx == Direction.Axis.Z ? 0.5 + 0.5625 * (double)â˜ƒxx.getStepZ() : (double)â˜ƒx.nextFloat();
            â˜ƒ.addParticle(
               DustParticleOptions.REDSTONE, (double)â˜ƒ.getX() + â˜ƒxxxxx, (double)â˜ƒ.getY() + â˜ƒxxxxxx, (double)â˜ƒ.getZ() + â˜ƒxxxxxxx, 0.0, 0.0, 0.0
            );
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LIT);
   }
}
