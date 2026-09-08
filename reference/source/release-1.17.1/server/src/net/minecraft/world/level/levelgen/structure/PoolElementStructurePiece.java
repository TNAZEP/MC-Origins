package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.structures.JigsawJunction;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoolElementStructurePiece extends StructurePiece {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final StructurePoolElement element;
   protected BlockPos position;
   private final int groundLevelDelta;
   protected final Rotation rotation;
   private final List<JigsawJunction> junctions = Lists.<JigsawJunction>newArrayList();
   private final StructureManager structureManager;

   public PoolElementStructurePiece(StructureManager var1, StructurePoolElement var2, BlockPos var3, int var4, Rotation var5, BoundingBox var6) {
      super(StructurePieceType.JIGSAW, 0, â˜ƒ);
      this.structureManager = â˜ƒ;
      this.element = â˜ƒ;
      this.position = â˜ƒ;
      this.groundLevelDelta = â˜ƒ;
      this.rotation = â˜ƒ;
   }

   public PoolElementStructurePiece(ServerLevel var1, CompoundTag var2) {
      super(StructurePieceType.JIGSAW, â˜ƒ);
      this.structureManager = â˜ƒ.getStructureManager();
      this.position = new BlockPos(â˜ƒ.getInt("PosX"), â˜ƒ.getInt("PosY"), â˜ƒ.getInt("PosZ"));
      this.groundLevelDelta = â˜ƒ.getInt("ground_level_delta");
      RegistryReadOps<Tag> â˜ƒ = RegistryReadOps.create(NbtOps.INSTANCE, â˜ƒ.getServer().getResourceManager(), â˜ƒ.getServer().registryAccess());
      this.element = (StructurePoolElement)StructurePoolElement.CODEC
         .parse(â˜ƒ, â˜ƒ.getCompound("pool_element"))
         .resultOrPartial(LOGGER::error)
         .orElseThrow(() -> new IllegalStateException("Invalid pool element found"));
      this.rotation = Rotation.valueOf(â˜ƒ.getString("rotation"));
      this.boundingBox = this.element.getBoundingBox(this.structureManager, this.position, this.rotation);
      ListTag â˜ƒx = â˜ƒ.getList("junctions", 10);
      this.junctions.clear();
      â˜ƒx.forEach(var2x -> this.junctions.add(JigsawJunction.deserialize(new Dynamic<>(â˜ƒ, var2x))));
   }

   @Override
   protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      â˜ƒ.putInt("PosX", this.position.getX());
      â˜ƒ.putInt("PosY", this.position.getY());
      â˜ƒ.putInt("PosZ", this.position.getZ());
      â˜ƒ.putInt("ground_level_delta", this.groundLevelDelta);
      RegistryWriteOps<Tag> â˜ƒ = RegistryWriteOps.create(NbtOps.INSTANCE, â˜ƒ.getServer().registryAccess());
      StructurePoolElement.CODEC.encodeStart(â˜ƒ, this.element).resultOrPartial(LOGGER::error).ifPresent(var1x -> â˜ƒ.put("pool_element", var1x));
      â˜ƒ.putString("rotation", this.rotation.name());
      ListTag â˜ƒx = new ListTag();

      for(JigsawJunction â˜ƒxx : this.junctions) {
         â˜ƒx.add(â˜ƒxx.serialize(â˜ƒ).getValue());
      }

      â˜ƒ.put("junctions", â˜ƒx);
   }

   @Override
   public boolean postProcess(
      WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
   ) {
      return this.place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public boolean place(WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, BlockPos var6, boolean var7) {
      return this.element.place(this.structureManager, â˜ƒ, â˜ƒ, â˜ƒ, this.position, â˜ƒ, this.rotation, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void move(int var1, int var2, int var3) {
      super.move(â˜ƒ, â˜ƒ, â˜ƒ);
      this.position = this.position.offset(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public Rotation getRotation() {
      return this.rotation;
   }

   public String toString() {
      return String.format("<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.position, this.rotation, this.element);
   }

   public StructurePoolElement getElement() {
      return this.element;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public int getGroundLevelDelta() {
      return this.groundLevelDelta;
   }

   public void addJunction(JigsawJunction var1) {
      this.junctions.add(â˜ƒ);
   }

   public List<JigsawJunction> getJunctions() {
      return this.junctions;
   }
}
