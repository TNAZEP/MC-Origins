package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class InfestedBlock extends Block {
   private final Block hostBlock;
   private static final Map<Block, Block> BLOCK_BY_HOST_BLOCK = Maps.<Block, Block>newIdentityHashMap();
   private static final Map<BlockState, BlockState> HOST_TO_INFESTED_STATES = Maps.<BlockState, BlockState>newIdentityHashMap();
   private static final Map<BlockState, BlockState> INFESTED_TO_HOST_STATES = Maps.<BlockState, BlockState>newIdentityHashMap();

   public InfestedBlock(Block var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ.destroyTime(â˜ƒ.defaultDestroyTime() / 2.0F).explosionResistance(0.75F));
      this.hostBlock = â˜ƒ;
      BLOCK_BY_HOST_BLOCK.put(â˜ƒ, this);
   }

   public Block getHostBlock() {
      return this.hostBlock;
   }

   public static boolean isCompatibleHostBlock(BlockState var0) {
      return BLOCK_BY_HOST_BLOCK.containsKey(â˜ƒ.getBlock());
   }

   private void spawnInfestation(ServerLevel var1, BlockPos var2) {
      Silverfish â˜ƒ = EntityType.SILVERFISH.create(â˜ƒ);
      â˜ƒ.moveTo((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, 0.0F, 0.0F);
      â˜ƒ.addFreshEntity(â˜ƒ);
      â˜ƒ.spawnAnim();
   }

   @Override
   public void spawnAfterBreak(BlockState var1, ServerLevel var2, BlockPos var3, ItemStack var4) {
      super.spawnAfterBreak(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, â˜ƒ) == 0) {
         this.spawnInfestation(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void wasExploded(Level var1, BlockPos var2, Explosion var3) {
      if (â˜ƒ instanceof ServerLevel) {
         this.spawnInfestation((ServerLevel)â˜ƒ, â˜ƒ);
      }
   }

   public static BlockState infestedStateByHost(BlockState var0) {
      return getNewStateWithProperties(HOST_TO_INFESTED_STATES, â˜ƒ, () -> ((Block)BLOCK_BY_HOST_BLOCK.get(â˜ƒ.getBlock())).defaultBlockState());
   }

   public BlockState hostStateByInfested(BlockState var1) {
      return getNewStateWithProperties(INFESTED_TO_HOST_STATES, â˜ƒ, () -> this.getHostBlock().defaultBlockState());
   }

   private static BlockState getNewStateWithProperties(Map<BlockState, BlockState> var0, BlockState var1, Supplier<BlockState> var2) {
      return (BlockState)â˜ƒ.computeIfAbsent(â˜ƒ, var1x -> {
         BlockState â˜ƒ = (BlockState)â˜ƒ.get();

         for(Property â˜ƒx : var1x.getProperties()) {
            â˜ƒ = â˜ƒ.hasProperty(â˜ƒx) ? â˜ƒ.setValue(â˜ƒx, var1x.getValue(â˜ƒx)) : â˜ƒ;
         }

         return â˜ƒ;
      });
   }
}
