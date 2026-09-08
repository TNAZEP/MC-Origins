package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperFullBlock extends Block implements WeatheringCopper {
   private final WeatheringCopper.WeatherState weatherState;

   public WeatheringCopperFullBlock(WeatheringCopper.WeatherState var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.weatherState = â˜ƒ;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.onRandomTick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return WeatheringCopper.getNext(â˜ƒ.getBlock()).isPresent();
   }

   public WeatheringCopper.WeatherState getAge() {
      return this.weatherState;
   }
}
