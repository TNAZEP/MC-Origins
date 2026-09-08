package net.minecraft.world.level.levelgen.structure;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TemplateStructurePiece extends StructurePiece {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final String templateName;
   protected StructureTemplate template;
   protected StructurePlaceSettings placeSettings;
   protected BlockPos templatePosition;

   public TemplateStructurePiece(
      StructurePieceType var1, int var2, StructureManager var3, ResourceLocation var4, String var5, StructurePlaceSettings var6, BlockPos var7
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ.getOrCreate(â˜ƒ).getBoundingBox(â˜ƒ, â˜ƒ));
      this.setOrientation(Direction.NORTH);
      this.templateName = â˜ƒ;
      this.templatePosition = â˜ƒ;
      this.template = â˜ƒ.getOrCreate(â˜ƒ);
      this.placeSettings = â˜ƒ;
   }

   public TemplateStructurePiece(StructurePieceType var1, CompoundTag var2, ServerLevel var3, Function<ResourceLocation, StructurePlaceSettings> var4) {
      super(â˜ƒ, â˜ƒ);
      this.setOrientation(Direction.NORTH);
      this.templateName = â˜ƒ.getString("Template");
      this.templatePosition = new BlockPos(â˜ƒ.getInt("TPX"), â˜ƒ.getInt("TPY"), â˜ƒ.getInt("TPZ"));
      ResourceLocation â˜ƒ = this.makeTemplateLocation();
      this.template = â˜ƒ.getStructureManager().getOrCreate(â˜ƒ);
      this.placeSettings = (StructurePlaceSettings)â˜ƒ.apply(â˜ƒ);
      this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
   }

   protected ResourceLocation makeTemplateLocation() {
      return new ResourceLocation(this.templateName);
   }

   @Override
   protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      â˜ƒ.putInt("TPX", this.templatePosition.getX());
      â˜ƒ.putInt("TPY", this.templatePosition.getY());
      â˜ƒ.putInt("TPZ", this.templatePosition.getZ());
      â˜ƒ.putString("Template", this.templateName);
   }

   @Override
   public boolean postProcess(
      WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
   ) {
      this.placeSettings.setBoundingBox(â˜ƒ);
      this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
      if (this.template.placeInWorld(â˜ƒ, this.templatePosition, â˜ƒ, this.placeSettings, â˜ƒ, 2)) {
         for(StructureTemplate.StructureBlockInfo â˜ƒ : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK)) {
            if (â˜ƒ.nbt != null) {
               StructureMode â˜ƒx = StructureMode.valueOf(â˜ƒ.nbt.getString("mode"));
               if (â˜ƒx == StructureMode.DATA) {
                  this.handleDataMarker(â˜ƒ.nbt.getString("metadata"), â˜ƒ.pos, â˜ƒ, â˜ƒ, â˜ƒ);
               }
            }
         }

         for(StructureTemplate.StructureBlockInfo â˜ƒ : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW)) {
            if (â˜ƒ.nbt != null) {
               String â˜ƒx = â˜ƒ.nbt.getString("final_state");
               BlockStateParser â˜ƒxx = new BlockStateParser(new StringReader(â˜ƒx), false);
               BlockState â˜ƒxxx = Blocks.AIR.defaultBlockState();

               try {
                  â˜ƒxx.parse(true);
                  BlockState â˜ƒxxxx = â˜ƒxx.getState();
                  if (â˜ƒxxxx != null) {
                     â˜ƒxxx = â˜ƒxxxx;
                  } else {
                     LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", â˜ƒx, â˜ƒ.pos);
                  }
               } catch (CommandSyntaxException var16) {
                  LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", â˜ƒx, â˜ƒ.pos);
               }

               â˜ƒ.setBlock(â˜ƒ.pos, â˜ƒxxx, 3);
            }
         }
      }

      return true;
   }

   protected abstract void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5);

   @Override
   public void move(int var1, int var2, int var3) {
      super.move(â˜ƒ, â˜ƒ, â˜ƒ);
      this.templatePosition = this.templatePosition.offset(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public Rotation getRotation() {
      return this.placeSettings.getRotation();
   }
}
