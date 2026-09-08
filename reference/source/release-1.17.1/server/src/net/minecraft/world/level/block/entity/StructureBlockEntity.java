package net.minecraft.world.level.block.entity;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class StructureBlockEntity extends BlockEntity {
   private static final int SCAN_CORNER_BLOCKS_RANGE = 5;
   public static final int MAX_OFFSET_PER_AXIS = 48;
   public static final int MAX_SIZE_PER_AXIS = 48;
   public static final String AUTHOR_TAG = "author";
   private ResourceLocation structureName;
   private String author = "";
   private String metaData = "";
   private BlockPos structurePos = new BlockPos(0, 1, 0);
   private Vec3i structureSize = Vec3i.ZERO;
   private Mirror mirror = Mirror.NONE;
   private Rotation rotation = Rotation.NONE;
   private StructureMode mode;
   private boolean ignoreEntities = true;
   private boolean powered;
   private boolean showAir;
   private boolean showBoundingBox = true;
   private float integrity = 1.0F;
   private long seed;

   public StructureBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.STRUCTURE_BLOCK, â˜ƒ, â˜ƒ);
      this.mode = â˜ƒ.getValue(StructureBlock.MODE);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.putString("name", this.getStructureName());
      â˜ƒ.putString("author", this.author);
      â˜ƒ.putString("metadata", this.metaData);
      â˜ƒ.putInt("posX", this.structurePos.getX());
      â˜ƒ.putInt("posY", this.structurePos.getY());
      â˜ƒ.putInt("posZ", this.structurePos.getZ());
      â˜ƒ.putInt("sizeX", this.structureSize.getX());
      â˜ƒ.putInt("sizeY", this.structureSize.getY());
      â˜ƒ.putInt("sizeZ", this.structureSize.getZ());
      â˜ƒ.putString("rotation", this.rotation.toString());
      â˜ƒ.putString("mirror", this.mirror.toString());
      â˜ƒ.putString("mode", this.mode.toString());
      â˜ƒ.putBoolean("ignoreEntities", this.ignoreEntities);
      â˜ƒ.putBoolean("powered", this.powered);
      â˜ƒ.putBoolean("showair", this.showAir);
      â˜ƒ.putBoolean("showboundingbox", this.showBoundingBox);
      â˜ƒ.putFloat("integrity", this.integrity);
      â˜ƒ.putLong("seed", this.seed);
      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.setStructureName(â˜ƒ.getString("name"));
      this.author = â˜ƒ.getString("author");
      this.metaData = â˜ƒ.getString("metadata");
      int â˜ƒ = Mth.clamp(â˜ƒ.getInt("posX"), -48, 48);
      int â˜ƒx = Mth.clamp(â˜ƒ.getInt("posY"), -48, 48);
      int â˜ƒxx = Mth.clamp(â˜ƒ.getInt("posZ"), -48, 48);
      this.structurePos = new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
      int â˜ƒxxx = Mth.clamp(â˜ƒ.getInt("sizeX"), 0, 48);
      int â˜ƒxxxx = Mth.clamp(â˜ƒ.getInt("sizeY"), 0, 48);
      int â˜ƒxxxxx = Mth.clamp(â˜ƒ.getInt("sizeZ"), 0, 48);
      this.structureSize = new Vec3i(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);

      try {
         this.rotation = Rotation.valueOf(â˜ƒ.getString("rotation"));
      } catch (IllegalArgumentException var11) {
         this.rotation = Rotation.NONE;
      }

      try {
         this.mirror = Mirror.valueOf(â˜ƒ.getString("mirror"));
      } catch (IllegalArgumentException var10) {
         this.mirror = Mirror.NONE;
      }

      try {
         this.mode = StructureMode.valueOf(â˜ƒ.getString("mode"));
      } catch (IllegalArgumentException var9) {
         this.mode = StructureMode.DATA;
      }

      this.ignoreEntities = â˜ƒ.getBoolean("ignoreEntities");
      this.powered = â˜ƒ.getBoolean("powered");
      this.showAir = â˜ƒ.getBoolean("showair");
      this.showBoundingBox = â˜ƒ.getBoolean("showboundingbox");
      if (â˜ƒ.contains("integrity")) {
         this.integrity = â˜ƒ.getFloat("integrity");
      } else {
         this.integrity = 1.0F;
      }

      this.seed = â˜ƒ.getLong("seed");
      this.updateBlockState();
   }

   private void updateBlockState() {
      if (this.level != null) {
         BlockPos â˜ƒ = this.getBlockPos();
         BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
         if (â˜ƒx.is(Blocks.STRUCTURE_BLOCK)) {
            this.level.setBlock(â˜ƒ, â˜ƒx.setValue(StructureBlock.MODE, this.mode), 2);
         }
      }
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 7, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   public boolean usedBy(Player var1) {
      if (!â˜ƒ.canUseGameMasterBlocks()) {
         return false;
      } else {
         if (â˜ƒ.getCommandSenderWorld().isClientSide) {
            â˜ƒ.openStructureBlock(this);
         }

         return true;
      }
   }

   public String getStructureName() {
      return this.structureName == null ? "" : this.structureName.toString();
   }

   public String getStructurePath() {
      return this.structureName == null ? "" : this.structureName.getPath();
   }

   public boolean hasStructureName() {
      return this.structureName != null;
   }

   public void setStructureName(@Nullable String var1) {
      this.setStructureName(StringUtil.isNullOrEmpty(â˜ƒ) ? null : ResourceLocation.tryParse(â˜ƒ));
   }

   public void setStructureName(@Nullable ResourceLocation var1) {
      this.structureName = â˜ƒ;
   }

   public void createdBy(LivingEntity var1) {
      this.author = â˜ƒ.getName().getString();
   }

   public BlockPos getStructurePos() {
      return this.structurePos;
   }

   public void setStructurePos(BlockPos var1) {
      this.structurePos = â˜ƒ;
   }

   public Vec3i getStructureSize() {
      return this.structureSize;
   }

   public void setStructureSize(Vec3i var1) {
      this.structureSize = â˜ƒ;
   }

   public Mirror getMirror() {
      return this.mirror;
   }

   public void setMirror(Mirror var1) {
      this.mirror = â˜ƒ;
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public void setRotation(Rotation var1) {
      this.rotation = â˜ƒ;
   }

   public String getMetaData() {
      return this.metaData;
   }

   public void setMetaData(String var1) {
      this.metaData = â˜ƒ;
   }

   public StructureMode getMode() {
      return this.mode;
   }

   public void setMode(StructureMode var1) {
      this.mode = â˜ƒ;
      BlockState â˜ƒ = this.level.getBlockState(this.getBlockPos());
      if (â˜ƒ.is(Blocks.STRUCTURE_BLOCK)) {
         this.level.setBlock(this.getBlockPos(), â˜ƒ.setValue(StructureBlock.MODE, â˜ƒ), 2);
      }
   }

   public boolean isIgnoreEntities() {
      return this.ignoreEntities;
   }

   public void setIgnoreEntities(boolean var1) {
      this.ignoreEntities = â˜ƒ;
   }

   public float getIntegrity() {
      return this.integrity;
   }

   public void setIntegrity(float var1) {
      this.integrity = â˜ƒ;
   }

   public long getSeed() {
      return this.seed;
   }

   public void setSeed(long var1) {
      this.seed = â˜ƒ;
   }

   public boolean detectSize() {
      if (this.mode != StructureMode.SAVE) {
         return false;
      } else {
         BlockPos â˜ƒ = this.getBlockPos();
         int â˜ƒx = 80;
         BlockPos â˜ƒxx = new BlockPos(â˜ƒ.getX() - 80, this.level.getMinBuildHeight(), â˜ƒ.getZ() - 80);
         BlockPos â˜ƒxxx = new BlockPos(â˜ƒ.getX() + 80, this.level.getMaxBuildHeight() - 1, â˜ƒ.getZ() + 80);
         Stream<BlockPos> â˜ƒxxxx = this.getRelatedCorners(â˜ƒxx, â˜ƒxxx);
         return calculateEnclosingBoundingBox(â˜ƒ, â˜ƒxxxx).filter(var2x -> {
            int â˜ƒ = var2x.maxX() - var2x.minX();
            int â˜ƒx = var2x.maxY() - var2x.minY();
            int â˜ƒxx = var2x.maxZ() - var2x.minZ();
            if (â˜ƒ > 1 && â˜ƒx > 1 && â˜ƒxx > 1) {
               this.structurePos = new BlockPos(var2x.minX() - â˜ƒ.getX() + 1, var2x.minY() - â˜ƒ.getY() + 1, var2x.minZ() - â˜ƒ.getZ() + 1);
               this.structureSize = new Vec3i(â˜ƒ - 1, â˜ƒx - 1, â˜ƒxx - 1);
               this.setChanged();
               BlockState â˜ƒxxx = this.level.getBlockState(â˜ƒ);
               this.level.sendBlockUpdated(â˜ƒ, â˜ƒxxx, â˜ƒxxx, 3);
               return true;
            } else {
               return false;
            }
         }).isPresent();
      }
   }

   private Stream<BlockPos> getRelatedCorners(BlockPos var1, BlockPos var2) {
      return BlockPos.betweenClosedStream(â˜ƒ, â˜ƒ)
         .filter(var1x -> this.level.getBlockState(var1x).is(Blocks.STRUCTURE_BLOCK))
         .map(this.level::getBlockEntity)
         .filter(var0 -> var0 instanceof StructureBlockEntity)
         .map(var0 -> (StructureBlockEntity)var0)
         .filter(var1x -> var1x.mode == StructureMode.CORNER && Objects.equals(this.structureName, var1x.structureName))
         .map(BlockEntity::getBlockPos);
   }

   private static Optional<BoundingBox> calculateEnclosingBoundingBox(BlockPos var0, Stream<BlockPos> var1) {
      Iterator<BlockPos> â˜ƒ = â˜ƒ.iterator();
      if (!â˜ƒ.hasNext()) {
         return Optional.empty();
      } else {
         BlockPos â˜ƒ = (BlockPos)â˜ƒ.next();
         BoundingBox â˜ƒx = new BoundingBox(â˜ƒ);
         if (â˜ƒ.hasNext()) {
            â˜ƒ.forEachRemaining(â˜ƒx::encapsulate);
         } else {
            â˜ƒx.encapsulate(â˜ƒ);
         }

         return Optional.of(â˜ƒx);
      }
   }

   public boolean saveStructure() {
      return this.saveStructure(true);
   }

   public boolean saveStructure(boolean var1) {
      if (this.mode == StructureMode.SAVE && !this.level.isClientSide && this.structureName != null) {
         BlockPos â˜ƒ = this.getBlockPos().offset(this.structurePos);
         ServerLevel â˜ƒx = (ServerLevel)this.level;
         StructureManager â˜ƒxx = â˜ƒx.getStructureManager();

         StructureTemplate â˜ƒ;
         try {
            â˜ƒ = â˜ƒxx.getOrCreate(this.structureName);
         } catch (ResourceLocationException var8) {
            return false;
         }

         â˜ƒ.fillFromWorld(this.level, â˜ƒ, this.structureSize, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
         â˜ƒ.setAuthor(this.author);
         if (â˜ƒ) {
            try {
               return â˜ƒxx.save(this.structureName);
            } catch (ResourceLocationException var7) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean loadStructure(ServerLevel var1) {
      return this.loadStructure(â˜ƒ, true);
   }

   private static Random createRandom(long var0) {
      return â˜ƒ == 0L ? new Random(Util.getMillis()) : new Random(â˜ƒ);
   }

   public boolean loadStructure(ServerLevel var1, boolean var2) {
      if (this.mode == StructureMode.LOAD && this.structureName != null) {
         StructureManager â˜ƒ = â˜ƒ.getStructureManager();

         Optional<StructureTemplate> â˜ƒ;
         try {
            â˜ƒ = â˜ƒ.get(this.structureName);
         } catch (ResourceLocationException var6) {
            return false;
         }

         return !â˜ƒ.isPresent() ? false : this.loadStructure(â˜ƒ, â˜ƒ, (StructureTemplate)â˜ƒ.get());
      } else {
         return false;
      }
   }

   public boolean loadStructure(ServerLevel var1, boolean var2, StructureTemplate var3) {
      BlockPos â˜ƒ = this.getBlockPos();
      if (!StringUtil.isNullOrEmpty(â˜ƒ.getAuthor())) {
         this.author = â˜ƒ.getAuthor();
      }

      Vec3i â˜ƒ = â˜ƒ.getSize();
      boolean â˜ƒx = this.structureSize.equals(â˜ƒ);
      if (!â˜ƒx) {
         this.structureSize = â˜ƒ;
         this.setChanged();
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
         â˜ƒ.sendBlockUpdated(â˜ƒ, â˜ƒxx, â˜ƒxx, 3);
      }

      if (â˜ƒ && !â˜ƒx) {
         return false;
      } else {
         StructurePlaceSettings â˜ƒ = new StructurePlaceSettings().setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities);
         if (this.integrity < 1.0F) {
            â˜ƒ.clearProcessors().addProcessor(new BlockRotProcessor(Mth.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.seed));
         }

         BlockPos â˜ƒ = â˜ƒ.offset(this.structurePos);
         â˜ƒ.placeInWorld(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, createRandom(this.seed), 2);
         return true;
      }
   }

   public void unloadStructure() {
      if (this.structureName != null) {
         ServerLevel â˜ƒ = (ServerLevel)this.level;
         StructureManager â˜ƒx = â˜ƒ.getStructureManager();
         â˜ƒx.remove(this.structureName);
      }
   }

   public boolean isStructureLoadable() {
      if (this.mode == StructureMode.LOAD && !this.level.isClientSide && this.structureName != null) {
         ServerLevel â˜ƒ = (ServerLevel)this.level;
         StructureManager â˜ƒx = â˜ƒ.getStructureManager();

         try {
            return â˜ƒx.get(this.structureName).isPresent();
         } catch (ResourceLocationException var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isPowered() {
      return this.powered;
   }

   public void setPowered(boolean var1) {
      this.powered = â˜ƒ;
   }

   public boolean getShowAir() {
      return this.showAir;
   }

   public void setShowAir(boolean var1) {
      this.showAir = â˜ƒ;
   }

   public boolean getShowBoundingBox() {
      return this.showBoundingBox;
   }

   public void setShowBoundingBox(boolean var1) {
      this.showBoundingBox = â˜ƒ;
   }

   public static enum UpdateType {
      UPDATE_DATA,
      SAVE_AREA,
      LOAD_AREA,
      SCAN_AREA;
   }
}
