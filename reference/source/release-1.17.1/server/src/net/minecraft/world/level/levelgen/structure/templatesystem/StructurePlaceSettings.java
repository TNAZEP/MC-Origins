package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class StructurePlaceSettings {
   private Mirror mirror = Mirror.NONE;
   private Rotation rotation = Rotation.NONE;
   private BlockPos rotationPivot = BlockPos.ZERO;
   private boolean ignoreEntities;
   @Nullable
   private BoundingBox boundingBox;
   private boolean keepLiquids = true;
   @Nullable
   private Random random;
   @Nullable
   private int palette;
   private final List<StructureProcessor> processors = Lists.<StructureProcessor>newArrayList();
   private boolean knownShape;
   private boolean finalizeEntities;

   public StructurePlaceSettings copy() {
      StructurePlaceSettings â˜ƒ = new StructurePlaceSettings();
      â˜ƒ.mirror = this.mirror;
      â˜ƒ.rotation = this.rotation;
      â˜ƒ.rotationPivot = this.rotationPivot;
      â˜ƒ.ignoreEntities = this.ignoreEntities;
      â˜ƒ.boundingBox = this.boundingBox;
      â˜ƒ.keepLiquids = this.keepLiquids;
      â˜ƒ.random = this.random;
      â˜ƒ.palette = this.palette;
      â˜ƒ.processors.addAll(this.processors);
      â˜ƒ.knownShape = this.knownShape;
      â˜ƒ.finalizeEntities = this.finalizeEntities;
      return â˜ƒ;
   }

   public StructurePlaceSettings setMirror(Mirror var1) {
      this.mirror = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings setRotation(Rotation var1) {
      this.rotation = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings setRotationPivot(BlockPos var1) {
      this.rotationPivot = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings setIgnoreEntities(boolean var1) {
      this.ignoreEntities = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings setBoundingBox(BoundingBox var1) {
      this.boundingBox = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings setRandom(@Nullable Random var1) {
      this.random = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings setKeepLiquids(boolean var1) {
      this.keepLiquids = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings setKnownShape(boolean var1) {
      this.knownShape = â˜ƒ;
      return this;
   }

   public StructurePlaceSettings clearProcessors() {
      this.processors.clear();
      return this;
   }

   public StructurePlaceSettings addProcessor(StructureProcessor var1) {
      this.processors.add(â˜ƒ);
      return this;
   }

   public StructurePlaceSettings popProcessor(StructureProcessor var1) {
      this.processors.remove(â˜ƒ);
      return this;
   }

   public Mirror getMirror() {
      return this.mirror;
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public BlockPos getRotationPivot() {
      return this.rotationPivot;
   }

   public Random getRandom(@Nullable BlockPos var1) {
      if (this.random != null) {
         return this.random;
      } else {
         return â˜ƒ == null ? new Random(Util.getMillis()) : new Random(Mth.getSeed(â˜ƒ));
      }
   }

   public boolean isIgnoreEntities() {
      return this.ignoreEntities;
   }

   @Nullable
   public BoundingBox getBoundingBox() {
      return this.boundingBox;
   }

   public boolean getKnownShape() {
      return this.knownShape;
   }

   public List<StructureProcessor> getProcessors() {
      return this.processors;
   }

   public boolean shouldKeepLiquids() {
      return this.keepLiquids;
   }

   public StructureTemplate.Palette getRandomPalette(List<StructureTemplate.Palette> var1, @Nullable BlockPos var2) {
      int â˜ƒ = â˜ƒ.size();
      if (â˜ƒ == 0) {
         throw new IllegalStateException("No palettes");
      } else {
         return (StructureTemplate.Palette)â˜ƒ.get(this.getRandom(â˜ƒ).nextInt(â˜ƒ));
      }
   }

   public StructurePlaceSettings setFinalizeEntities(boolean var1) {
      this.finalizeEntities = â˜ƒ;
      return this;
   }

   public boolean shouldFinalizeEntities() {
      return this.finalizeEntities;
   }
}
