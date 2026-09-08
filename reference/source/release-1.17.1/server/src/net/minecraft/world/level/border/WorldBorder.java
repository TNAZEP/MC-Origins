package net.minecraft.world.level.border;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WorldBorder {
   public static final double MAX_SIZE = 5.999997E7F;
   private final List<BorderChangeListener> listeners = Lists.<BorderChangeListener>newArrayList();
   private double damagePerBlock = 0.2;
   private double damageSafeZone = 5.0;
   private int warningTime = 15;
   private int warningBlocks = 5;
   private double centerX;
   private double centerZ;
   int absoluteMaxSize = 29999984;
   private WorldBorder.BorderExtent extent = new WorldBorder.StaticBorderExtent(5.999997E7F);
   public static final WorldBorder.Settings DEFAULT_SETTINGS = new WorldBorder.Settings(0.0, 0.0, 0.2, 5.0, 5, 15, 5.999997E7F, 0L, 0.0);

   public boolean isWithinBounds(BlockPos var1) {
      return (double)(â˜ƒ.getX() + 1) > this.getMinX()
         && (double)â˜ƒ.getX() < this.getMaxX()
         && (double)(â˜ƒ.getZ() + 1) > this.getMinZ()
         && (double)â˜ƒ.getZ() < this.getMaxZ();
   }

   public boolean isWithinBounds(ChunkPos var1) {
      return (double)â˜ƒ.getMaxBlockX() > this.getMinX()
         && (double)â˜ƒ.getMinBlockX() < this.getMaxX()
         && (double)â˜ƒ.getMaxBlockZ() > this.getMinZ()
         && (double)â˜ƒ.getMinBlockZ() < this.getMaxZ();
   }

   public boolean isWithinBounds(double var1, double var3) {
      return â˜ƒ > this.getMinX() && â˜ƒ < this.getMaxX() && â˜ƒ > this.getMinZ() && â˜ƒ < this.getMaxZ();
   }

   public boolean isWithinBounds(AABB var1) {
      return â˜ƒ.maxX > this.getMinX() && â˜ƒ.minX < this.getMaxX() && â˜ƒ.maxZ > this.getMinZ() && â˜ƒ.minZ < this.getMaxZ();
   }

   public double getDistanceToBorder(Entity var1) {
      return this.getDistanceToBorder(â˜ƒ.getX(), â˜ƒ.getZ());
   }

   public VoxelShape getCollisionShape() {
      return this.extent.getCollisionShape();
   }

   public double getDistanceToBorder(double var1, double var3) {
      double â˜ƒ = â˜ƒ - this.getMinZ();
      double â˜ƒx = this.getMaxZ() - â˜ƒ;
      double â˜ƒxx = â˜ƒ - this.getMinX();
      double â˜ƒxxx = this.getMaxX() - â˜ƒ;
      double â˜ƒxxxx = Math.min(â˜ƒxx, â˜ƒxxx);
      â˜ƒxxxx = Math.min(â˜ƒxxxx, â˜ƒ);
      return Math.min(â˜ƒxxxx, â˜ƒx);
   }

   public BorderStatus getStatus() {
      return this.extent.getStatus();
   }

   public double getMinX() {
      return this.extent.getMinX();
   }

   public double getMinZ() {
      return this.extent.getMinZ();
   }

   public double getMaxX() {
      return this.extent.getMaxX();
   }

   public double getMaxZ() {
      return this.extent.getMaxZ();
   }

   public double getCenterX() {
      return this.centerX;
   }

   public double getCenterZ() {
      return this.centerZ;
   }

   public void setCenter(double var1, double var3) {
      this.centerX = â˜ƒ;
      this.centerZ = â˜ƒ;
      this.extent.onCenterChange();

      for(BorderChangeListener â˜ƒ : this.getListeners()) {
         â˜ƒ.onBorderCenterSet(this, â˜ƒ, â˜ƒ);
      }
   }

   public double getSize() {
      return this.extent.getSize();
   }

   public long getLerpRemainingTime() {
      return this.extent.getLerpRemainingTime();
   }

   public double getLerpTarget() {
      return this.extent.getLerpTarget();
   }

   public void setSize(double var1) {
      this.extent = new WorldBorder.StaticBorderExtent(â˜ƒ);

      for(BorderChangeListener â˜ƒ : this.getListeners()) {
         â˜ƒ.onBorderSizeSet(this, â˜ƒ);
      }
   }

   public void lerpSizeBetween(double var1, double var3, long var5) {
      this.extent = (WorldBorder.BorderExtent)(â˜ƒ == â˜ƒ ? new WorldBorder.StaticBorderExtent(â˜ƒ) : new WorldBorder.MovingBorderExtent(â˜ƒ, â˜ƒ, â˜ƒ));

      for(BorderChangeListener â˜ƒ : this.getListeners()) {
         â˜ƒ.onBorderSizeLerping(this, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected List<BorderChangeListener> getListeners() {
      return Lists.<BorderChangeListener>newArrayList(this.listeners);
   }

   public void addListener(BorderChangeListener var1) {
      this.listeners.add(â˜ƒ);
   }

   public void removeListener(BorderChangeListener var1) {
      this.listeners.remove(â˜ƒ);
   }

   public void setAbsoluteMaxSize(int var1) {
      this.absoluteMaxSize = â˜ƒ;
      this.extent.onAbsoluteMaxSizeChange();
   }

   public int getAbsoluteMaxSize() {
      return this.absoluteMaxSize;
   }

   public double getDamageSafeZone() {
      return this.damageSafeZone;
   }

   public void setDamageSafeZone(double var1) {
      this.damageSafeZone = â˜ƒ;

      for(BorderChangeListener â˜ƒ : this.getListeners()) {
         â˜ƒ.onBorderSetDamageSafeZOne(this, â˜ƒ);
      }
   }

   public double getDamagePerBlock() {
      return this.damagePerBlock;
   }

   public void setDamagePerBlock(double var1) {
      this.damagePerBlock = â˜ƒ;

      for(BorderChangeListener â˜ƒ : this.getListeners()) {
         â˜ƒ.onBorderSetDamagePerBlock(this, â˜ƒ);
      }
   }

   public double getLerpSpeed() {
      return this.extent.getLerpSpeed();
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public void setWarningTime(int var1) {
      this.warningTime = â˜ƒ;

      for(BorderChangeListener â˜ƒ : this.getListeners()) {
         â˜ƒ.onBorderSetWarningTime(this, â˜ƒ);
      }
   }

   public int getWarningBlocks() {
      return this.warningBlocks;
   }

   public void setWarningBlocks(int var1) {
      this.warningBlocks = â˜ƒ;

      for(BorderChangeListener â˜ƒ : this.getListeners()) {
         â˜ƒ.onBorderSetWarningBlocks(this, â˜ƒ);
      }
   }

   public void tick() {
      this.extent = this.extent.update();
   }

   public WorldBorder.Settings createSettings() {
      return new WorldBorder.Settings(this);
   }

   public void applySettings(WorldBorder.Settings var1) {
      this.setCenter(â˜ƒ.getCenterX(), â˜ƒ.getCenterZ());
      this.setDamagePerBlock(â˜ƒ.getDamagePerBlock());
      this.setDamageSafeZone(â˜ƒ.getSafeZone());
      this.setWarningBlocks(â˜ƒ.getWarningBlocks());
      this.setWarningTime(â˜ƒ.getWarningTime());
      if (â˜ƒ.getSizeLerpTime() > 0L) {
         this.lerpSizeBetween(â˜ƒ.getSize(), â˜ƒ.getSizeLerpTarget(), â˜ƒ.getSizeLerpTime());
      } else {
         this.setSize(â˜ƒ.getSize());
      }
   }

   interface BorderExtent {
      double getMinX();

      double getMaxX();

      double getMinZ();

      double getMaxZ();

      double getSize();

      double getLerpSpeed();

      long getLerpRemainingTime();

      double getLerpTarget();

      BorderStatus getStatus();

      void onAbsoluteMaxSizeChange();

      void onCenterChange();

      WorldBorder.BorderExtent update();

      VoxelShape getCollisionShape();
   }

   class MovingBorderExtent implements WorldBorder.BorderExtent {
      private final double from;
      private final double to;
      private final long lerpEnd;
      private final long lerpBegin;
      private final double lerpDuration;

      MovingBorderExtent(double var2, double var4, long var6) {
         this.from = â˜ƒ;
         this.to = â˜ƒ;
         this.lerpDuration = (double)â˜ƒ;
         this.lerpBegin = Util.getMillis();
         this.lerpEnd = this.lerpBegin + â˜ƒ;
      }

      @Override
      public double getMinX() {
         return Mth.clamp(
            WorldBorder.this.getCenterX() - this.getSize() / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
      }

      @Override
      public double getMinZ() {
         return Mth.clamp(
            WorldBorder.this.getCenterZ() - this.getSize() / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
      }

      @Override
      public double getMaxX() {
         return Mth.clamp(
            WorldBorder.this.getCenterX() + this.getSize() / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
      }

      @Override
      public double getMaxZ() {
         return Mth.clamp(
            WorldBorder.this.getCenterZ() + this.getSize() / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
      }

      @Override
      public double getSize() {
         double â˜ƒ = (double)(Util.getMillis() - this.lerpBegin) / this.lerpDuration;
         return â˜ƒ < 1.0 ? Mth.lerp(â˜ƒ, this.from, this.to) : this.to;
      }

      @Override
      public double getLerpSpeed() {
         return Math.abs(this.from - this.to) / (double)(this.lerpEnd - this.lerpBegin);
      }

      @Override
      public long getLerpRemainingTime() {
         return this.lerpEnd - Util.getMillis();
      }

      @Override
      public double getLerpTarget() {
         return this.to;
      }

      @Override
      public BorderStatus getStatus() {
         return this.to < this.from ? BorderStatus.SHRINKING : BorderStatus.GROWING;
      }

      @Override
      public void onCenterChange() {
      }

      @Override
      public void onAbsoluteMaxSizeChange() {
      }

      @Override
      public WorldBorder.BorderExtent update() {
         return (WorldBorder.BorderExtent)(this.getLerpRemainingTime() <= 0L ? WorldBorder.this.new StaticBorderExtent(this.to) : this);
      }

      @Override
      public VoxelShape getCollisionShape() {
         return Shapes.join(
            Shapes.INFINITY,
            Shapes.box(
               Math.floor(this.getMinX()),
               Double.NEGATIVE_INFINITY,
               Math.floor(this.getMinZ()),
               Math.ceil(this.getMaxX()),
               Double.POSITIVE_INFINITY,
               Math.ceil(this.getMaxZ())
            ),
            BooleanOp.ONLY_FIRST
         );
      }
   }

   public static class Settings {
      private final double centerX;
      private final double centerZ;
      private final double damagePerBlock;
      private final double safeZone;
      private final int warningBlocks;
      private final int warningTime;
      private final double size;
      private final long sizeLerpTime;
      private final double sizeLerpTarget;

      Settings(double var1, double var3, double var5, double var7, int var9, int var10, double var11, long var13, double var15) {
         this.centerX = â˜ƒ;
         this.centerZ = â˜ƒ;
         this.damagePerBlock = â˜ƒ;
         this.safeZone = â˜ƒ;
         this.warningBlocks = â˜ƒ;
         this.warningTime = â˜ƒ;
         this.size = â˜ƒ;
         this.sizeLerpTime = â˜ƒ;
         this.sizeLerpTarget = â˜ƒ;
      }

      Settings(WorldBorder var1) {
         this.centerX = â˜ƒ.getCenterX();
         this.centerZ = â˜ƒ.getCenterZ();
         this.damagePerBlock = â˜ƒ.getDamagePerBlock();
         this.safeZone = â˜ƒ.getDamageSafeZone();
         this.warningBlocks = â˜ƒ.getWarningBlocks();
         this.warningTime = â˜ƒ.getWarningTime();
         this.size = â˜ƒ.getSize();
         this.sizeLerpTime = â˜ƒ.getLerpRemainingTime();
         this.sizeLerpTarget = â˜ƒ.getLerpTarget();
      }

      public double getCenterX() {
         return this.centerX;
      }

      public double getCenterZ() {
         return this.centerZ;
      }

      public double getDamagePerBlock() {
         return this.damagePerBlock;
      }

      public double getSafeZone() {
         return this.safeZone;
      }

      public int getWarningBlocks() {
         return this.warningBlocks;
      }

      public int getWarningTime() {
         return this.warningTime;
      }

      public double getSize() {
         return this.size;
      }

      public long getSizeLerpTime() {
         return this.sizeLerpTime;
      }

      public double getSizeLerpTarget() {
         return this.sizeLerpTarget;
      }

      public static WorldBorder.Settings read(DynamicLike<?> var0, WorldBorder.Settings var1) {
         double â˜ƒ = â˜ƒ.get("BorderCenterX").asDouble(â˜ƒ.centerX);
         double â˜ƒx = â˜ƒ.get("BorderCenterZ").asDouble(â˜ƒ.centerZ);
         double â˜ƒxx = â˜ƒ.get("BorderSize").asDouble(â˜ƒ.size);
         long â˜ƒxxx = â˜ƒ.get("BorderSizeLerpTime").asLong(â˜ƒ.sizeLerpTime);
         double â˜ƒxxxx = â˜ƒ.get("BorderSizeLerpTarget").asDouble(â˜ƒ.sizeLerpTarget);
         double â˜ƒxxxxx = â˜ƒ.get("BorderSafeZone").asDouble(â˜ƒ.safeZone);
         double â˜ƒxxxxxx = â˜ƒ.get("BorderDamagePerBlock").asDouble(â˜ƒ.damagePerBlock);
         int â˜ƒxxxxxxx = â˜ƒ.get("BorderWarningBlocks").asInt(â˜ƒ.warningBlocks);
         int â˜ƒxxxxxxxx = â˜ƒ.get("BorderWarningTime").asInt(â˜ƒ.warningTime);
         return new WorldBorder.Settings(â˜ƒ, â˜ƒx, â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }

      public void write(CompoundTag var1) {
         â˜ƒ.putDouble("BorderCenterX", this.centerX);
         â˜ƒ.putDouble("BorderCenterZ", this.centerZ);
         â˜ƒ.putDouble("BorderSize", this.size);
         â˜ƒ.putLong("BorderSizeLerpTime", this.sizeLerpTime);
         â˜ƒ.putDouble("BorderSafeZone", this.safeZone);
         â˜ƒ.putDouble("BorderDamagePerBlock", this.damagePerBlock);
         â˜ƒ.putDouble("BorderSizeLerpTarget", this.sizeLerpTarget);
         â˜ƒ.putDouble("BorderWarningBlocks", (double)this.warningBlocks);
         â˜ƒ.putDouble("BorderWarningTime", (double)this.warningTime);
      }
   }

   class StaticBorderExtent implements WorldBorder.BorderExtent {
      private final double size;
      private double minX;
      private double minZ;
      private double maxX;
      private double maxZ;
      private VoxelShape shape;

      public StaticBorderExtent(double var2) {
         this.size = â˜ƒ;
         this.updateBox();
      }

      @Override
      public double getMinX() {
         return this.minX;
      }

      @Override
      public double getMaxX() {
         return this.maxX;
      }

      @Override
      public double getMinZ() {
         return this.minZ;
      }

      @Override
      public double getMaxZ() {
         return this.maxZ;
      }

      @Override
      public double getSize() {
         return this.size;
      }

      @Override
      public BorderStatus getStatus() {
         return BorderStatus.STATIONARY;
      }

      @Override
      public double getLerpSpeed() {
         return 0.0;
      }

      @Override
      public long getLerpRemainingTime() {
         return 0L;
      }

      @Override
      public double getLerpTarget() {
         return this.size;
      }

      private void updateBox() {
         this.minX = Mth.clamp(
            WorldBorder.this.getCenterX() - this.size / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
         this.minZ = Mth.clamp(
            WorldBorder.this.getCenterZ() - this.size / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
         this.maxX = Mth.clamp(
            WorldBorder.this.getCenterX() + this.size / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
         this.maxZ = Mth.clamp(
            WorldBorder.this.getCenterZ() + this.size / 2.0, (double)(-WorldBorder.this.absoluteMaxSize), (double)WorldBorder.this.absoluteMaxSize
         );
         this.shape = Shapes.join(
            Shapes.INFINITY,
            Shapes.box(
               Math.floor(this.getMinX()),
               Double.NEGATIVE_INFINITY,
               Math.floor(this.getMinZ()),
               Math.ceil(this.getMaxX()),
               Double.POSITIVE_INFINITY,
               Math.ceil(this.getMaxZ())
            ),
            BooleanOp.ONLY_FIRST
         );
      }

      @Override
      public void onAbsoluteMaxSizeChange() {
         this.updateBox();
      }

      @Override
      public void onCenterChange() {
         this.updateBox();
      }

      @Override
      public WorldBorder.BorderExtent update() {
         return this;
      }

      @Override
      public VoxelShape getCollisionShape() {
         return this.shape;
      }
   }
}
