package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ValidateNearbyPoi extends Behavior<LivingEntity> {
   private static final int MAX_DISTANCE = 16;
   private final MemoryModuleType<GlobalPos> memoryType;
   private final Predicate<PoiType> poiPredicate;

   public ValidateNearbyPoi(PoiType var1, MemoryModuleType<GlobalPos> var2) {
      super(ImmutableMap.of(â˜ƒ, MemoryStatus.VALUE_PRESENT));
      this.poiPredicate = â˜ƒ.getPredicate();
      this.memoryType = â˜ƒ;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      GlobalPos â˜ƒ = (GlobalPos)â˜ƒ.getBrain().getMemory(this.memoryType).get();
      return â˜ƒ.dimension() == â˜ƒ.dimension() && â˜ƒ.pos().closerThan(â˜ƒ.position(), 16.0);
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      GlobalPos â˜ƒx = (GlobalPos)â˜ƒ.getMemory(this.memoryType).get();
      BlockPos â˜ƒxx = â˜ƒx.pos();
      ServerLevel â˜ƒxxx = â˜ƒ.getServer().getLevel(â˜ƒx.dimension());
      if (â˜ƒxxx == null || this.poiDoesntExist(â˜ƒxxx, â˜ƒxx)) {
         â˜ƒ.eraseMemory(this.memoryType);
      } else if (this.bedIsOccupied(â˜ƒxxx, â˜ƒxx, â˜ƒ)) {
         â˜ƒ.eraseMemory(this.memoryType);
         â˜ƒ.getPoiManager().release(â˜ƒxx);
         DebugPackets.sendPoiTicketCountPacket(â˜ƒ, â˜ƒxx);
      }
   }

   private boolean bedIsOccupied(ServerLevel var1, BlockPos var2, LivingEntity var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ.is(BlockTags.BEDS) && â˜ƒ.getValue(BedBlock.OCCUPIED) && !â˜ƒ.isSleeping();
   }

   private boolean poiDoesntExist(ServerLevel var1, BlockPos var2) {
      return !â˜ƒ.getPoiManager().exists(â˜ƒ, this.poiPredicate);
   }
}
