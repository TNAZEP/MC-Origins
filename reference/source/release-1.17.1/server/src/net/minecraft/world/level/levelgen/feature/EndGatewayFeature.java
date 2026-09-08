package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;

public class EndGatewayFeature extends Feature<EndGatewayConfiguration> {
   public EndGatewayFeature(Codec<EndGatewayConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<EndGatewayConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      EndGatewayConfiguration â˜ƒxx = â˜ƒ.config();

      for(BlockPos â˜ƒxxx : BlockPos.betweenClosed(â˜ƒ.offset(-1, -2, -1), â˜ƒ.offset(1, 2, 1))) {
         boolean â˜ƒxxxx = â˜ƒxxx.getX() == â˜ƒ.getX();
         boolean â˜ƒxxxxx = â˜ƒxxx.getY() == â˜ƒ.getY();
         boolean â˜ƒxxxxxx = â˜ƒxxx.getZ() == â˜ƒ.getZ();
         boolean â˜ƒxxxxxxx = Math.abs(â˜ƒxxx.getY() - â˜ƒ.getY()) == 2;
         if (â˜ƒxxxx && â˜ƒxxxxx && â˜ƒxxxxxx) {
            BlockPos â˜ƒxxxxxxxx = â˜ƒxxx.immutable();
            this.setBlock(â˜ƒx, â˜ƒxxxxxxxx, Blocks.END_GATEWAY.defaultBlockState());
            â˜ƒxx.getExit().ifPresent(var3x -> {
               BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
               if (â˜ƒx instanceof TheEndGatewayBlockEntity â˜ƒ) {
                  â˜ƒ.setExitPosition(var3x, â˜ƒ.isExitExact());
                  â˜ƒx.setChanged();
               }
            });
         } else if (â˜ƒxxxxx) {
            this.setBlock(â˜ƒx, â˜ƒxxx, Blocks.AIR.defaultBlockState());
         } else if (â˜ƒxxxxxxx && â˜ƒxxxx && â˜ƒxxxxxx) {
            this.setBlock(â˜ƒx, â˜ƒxxx, Blocks.BEDROCK.defaultBlockState());
         } else if ((â˜ƒxxxx || â˜ƒxxxxxx) && !â˜ƒxxxxxxx) {
            this.setBlock(â˜ƒx, â˜ƒxxx, Blocks.BEDROCK.defaultBlockState());
         } else {
            this.setBlock(â˜ƒx, â˜ƒxxx, Blocks.AIR.defaultBlockState());
         }
      }

      return true;
   }
}
