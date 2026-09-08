package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public interface LevelHeightAccessor {
   int getHeight();

   int getMinBuildHeight();

   default int getMaxBuildHeight() {
      return this.getMinBuildHeight() + this.getHeight();
   }

   default int getSectionsCount() {
      return this.getMaxSection() - this.getMinSection();
   }

   default int getMinSection() {
      return SectionPos.blockToSectionCoord(this.getMinBuildHeight());
   }

   default int getMaxSection() {
      return SectionPos.blockToSectionCoord(this.getMaxBuildHeight() - 1) + 1;
   }

   default boolean isOutsideBuildHeight(BlockPos var1) {
      return this.isOutsideBuildHeight(â˜ƒ.getY());
   }

   default boolean isOutsideBuildHeight(int var1) {
      return â˜ƒ < this.getMinBuildHeight() || â˜ƒ >= this.getMaxBuildHeight();
   }

   default int getSectionIndex(int var1) {
      return this.getSectionIndexFromSectionY(SectionPos.blockToSectionCoord(â˜ƒ));
   }

   default int getSectionIndexFromSectionY(int var1) {
      return â˜ƒ - this.getMinSection();
   }

   default int getSectionYFromSectionIndex(int var1) {
      return â˜ƒ + this.getMinSection();
   }
}
