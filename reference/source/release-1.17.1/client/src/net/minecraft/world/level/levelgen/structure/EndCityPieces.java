package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class EndCityPieces {
   private static final int MAX_GEN_DEPTH = 8;
   static final EndCityPieces.SectionGenerator HOUSE_TOWER_GENERATOR = new EndCityPieces.SectionGenerator() {
      @Override
      public void init() {
      }

      @Override
      public boolean generate(StructureManager var1, int var2, EndCityPieces.EndCityPiece var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         if (â˜ƒ > 8) {
            return false;
         } else {
            Rotation â˜ƒ = â˜ƒ.placeSettings.getRotation();
            EndCityPieces.EndCityPiece â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒ, â˜ƒ, "base_floor", â˜ƒ, true));
            int â˜ƒxx = â˜ƒ.nextInt(3);
            if (â˜ƒxx == 0) {
               â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(-1, 4, -1), "base_roof", â˜ƒ, true));
            } else if (â˜ƒxx == 1) {
               â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(-1, 0, -1), "second_floor_2", â˜ƒ, false));
               â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(-1, 8, -1), "second_roof", â˜ƒ, false));
               EndCityPieces.recursiveChildren(â˜ƒ, EndCityPieces.TOWER_GENERATOR, â˜ƒ + 1, â˜ƒx, null, â˜ƒ, â˜ƒ);
            } else if (â˜ƒxx == 2) {
               â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(-1, 0, -1), "second_floor_2", â˜ƒ, false));
               â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(-1, 4, -1), "third_floor_2", â˜ƒ, false));
               â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(-1, 8, -1), "third_roof", â˜ƒ, true));
               EndCityPieces.recursiveChildren(â˜ƒ, EndCityPieces.TOWER_GENERATOR, â˜ƒ + 1, â˜ƒx, null, â˜ƒ, â˜ƒ);
            }

            return true;
         }
      }
   };
   static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.<Tuple<Rotation, BlockPos>>newArrayList(
      new Tuple<>(Rotation.NONE, new BlockPos(1, -1, 0)),
      new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)),
      new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)),
      new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6))
   );
   static final EndCityPieces.SectionGenerator TOWER_GENERATOR = new EndCityPieces.SectionGenerator() {
      @Override
      public void init() {
      }

      @Override
      public boolean generate(StructureManager var1, int var2, EndCityPieces.EndCityPiece var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         Rotation â˜ƒ = â˜ƒ.placeSettings.getRotation();
         EndCityPieces.EndCityPiece var8 = EndCityPieces.addHelper(
            â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒ, new BlockPos(3 + â˜ƒ.nextInt(2), -3, 3 + â˜ƒ.nextInt(2)), "tower_base", â˜ƒ, true)
         );
         var8 = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, var8, new BlockPos(0, 7, 0), "tower_piece", â˜ƒ, true));
         EndCityPieces.EndCityPiece â˜ƒx = â˜ƒ.nextInt(3) == 0 ? var8 : null;
         int â˜ƒxx = 1 + â˜ƒ.nextInt(3);

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
            var8 = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, var8, new BlockPos(0, 4, 0), "tower_piece", â˜ƒ, true));
            if (â˜ƒxxx < â˜ƒxx - 1 && â˜ƒ.nextBoolean()) {
               â˜ƒx = var8;
            }
         }

         if (â˜ƒx != null) {
            for(Tuple<Rotation, BlockPos> â˜ƒxxx : EndCityPieces.TOWER_BRIDGES) {
               if (â˜ƒ.nextBoolean()) {
                  EndCityPieces.EndCityPiece â˜ƒxxxx = EndCityPieces.addHelper(
                     â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, â˜ƒxxx.getB(), "bridge_end", â˜ƒ.getRotated((Rotation)â˜ƒxxx.getA()), true)
                  );
                  EndCityPieces.recursiveChildren(â˜ƒ, EndCityPieces.TOWER_BRIDGE_GENERATOR, â˜ƒ + 1, â˜ƒxxxx, null, â˜ƒ, â˜ƒ);
               }
            }

            var8 = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, var8, new BlockPos(-1, 4, -1), "tower_top", â˜ƒ, true));
         } else {
            if (â˜ƒ != 7) {
               return EndCityPieces.recursiveChildren(â˜ƒ, EndCityPieces.FAT_TOWER_GENERATOR, â˜ƒ + 1, var8, null, â˜ƒ, â˜ƒ);
            }

            var8 = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, var8, new BlockPos(-1, 4, -1), "tower_top", â˜ƒ, true));
         }

         return true;
      }
   };
   static final EndCityPieces.SectionGenerator TOWER_BRIDGE_GENERATOR = new EndCityPieces.SectionGenerator() {
      public boolean shipCreated;

      @Override
      public void init() {
         this.shipCreated = false;
      }

      @Override
      public boolean generate(StructureManager var1, int var2, EndCityPieces.EndCityPiece var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         Rotation â˜ƒ = â˜ƒ.placeSettings.getRotation();
         int â˜ƒx = â˜ƒ.nextInt(4) + 1;
         EndCityPieces.EndCityPiece â˜ƒxx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒ, new BlockPos(0, 0, -4), "bridge_piece", â˜ƒ, true));
         â˜ƒxx.genDepth = -1;
         int â˜ƒxxx = 0;

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒx; ++â˜ƒxxxx) {
            if (â˜ƒ.nextBoolean()) {
               â˜ƒxx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒxx, new BlockPos(0, â˜ƒxxx, -4), "bridge_piece", â˜ƒ, true));
               â˜ƒxxx = 0;
            } else {
               if (â˜ƒ.nextBoolean()) {
                  â˜ƒxx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒxx, new BlockPos(0, â˜ƒxxx, -4), "bridge_steep_stairs", â˜ƒ, true));
               } else {
                  â˜ƒxx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒxx, new BlockPos(0, â˜ƒxxx, -8), "bridge_gentle_stairs", â˜ƒ, true));
               }

               â˜ƒxxx = 4;
            }
         }

         if (!this.shipCreated && â˜ƒ.nextInt(10 - â˜ƒ) == 0) {
            EndCityPieces.addHelper(
               â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒxx, new BlockPos(-8 + â˜ƒ.nextInt(8), â˜ƒxxx, -70 + â˜ƒ.nextInt(10)), "ship", â˜ƒ, true)
            );
            this.shipCreated = true;
         } else if (!EndCityPieces.recursiveChildren(â˜ƒ, EndCityPieces.HOUSE_TOWER_GENERATOR, â˜ƒ + 1, â˜ƒxx, new BlockPos(-3, â˜ƒxxx + 1, -11), â˜ƒ, â˜ƒ)) {
            return false;
         }

         â˜ƒxx = EndCityPieces.addHelper(
            â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒxx, new BlockPos(4, â˜ƒxxx, 0), "bridge_end", â˜ƒ.getRotated(Rotation.CLOCKWISE_180), true)
         );
         â˜ƒxx.genDepth = -1;
         return true;
      }
   };
   static final List<Tuple<Rotation, BlockPos>> FAT_TOWER_BRIDGES = Lists.<Tuple<Rotation, BlockPos>>newArrayList(
      new Tuple<>(Rotation.NONE, new BlockPos(4, -1, 0)),
      new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)),
      new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)),
      new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12))
   );
   static final EndCityPieces.SectionGenerator FAT_TOWER_GENERATOR = new EndCityPieces.SectionGenerator() {
      @Override
      public void init() {
      }

      @Override
      public boolean generate(StructureManager var1, int var2, EndCityPieces.EndCityPiece var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         Rotation â˜ƒ = â˜ƒ.placeSettings.getRotation();
         EndCityPieces.EndCityPiece â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒ, new BlockPos(-3, 4, -3), "fat_tower_base", â˜ƒ, true));
         â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(0, 4, 0), "fat_tower_middle", â˜ƒ, true));

         for(int â˜ƒxx = 0; â˜ƒxx < 2 && â˜ƒ.nextInt(3) != 0; ++â˜ƒxx) {
            â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(0, 8, 0), "fat_tower_middle", â˜ƒ, true));

            for(Tuple<Rotation, BlockPos> â˜ƒxxx : EndCityPieces.FAT_TOWER_BRIDGES) {
               if (â˜ƒ.nextBoolean()) {
                  EndCityPieces.EndCityPiece â˜ƒxxxx = EndCityPieces.addHelper(
                     â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, â˜ƒxxx.getB(), "bridge_end", â˜ƒ.getRotated((Rotation)â˜ƒxxx.getA()), true)
                  );
                  EndCityPieces.recursiveChildren(â˜ƒ, EndCityPieces.TOWER_BRIDGE_GENERATOR, â˜ƒ + 1, â˜ƒxxxx, null, â˜ƒ, â˜ƒ);
               }
            }
         }

         â˜ƒx = EndCityPieces.addHelper(â˜ƒ, EndCityPieces.addPiece(â˜ƒ, â˜ƒx, new BlockPos(-2, 8, -2), "fat_tower_top", â˜ƒ, true));
         return true;
      }
   };

   static EndCityPieces.EndCityPiece addPiece(StructureManager var0, EndCityPieces.EndCityPiece var1, BlockPos var2, String var3, Rotation var4, boolean var5) {
      EndCityPieces.EndCityPiece â˜ƒ = new EndCityPieces.EndCityPiece(â˜ƒ, â˜ƒ, â˜ƒ.templatePosition, â˜ƒ, â˜ƒ);
      BlockPos â˜ƒx = â˜ƒ.template.calculateConnectedPosition(â˜ƒ.placeSettings, â˜ƒ, â˜ƒ.placeSettings, BlockPos.ZERO);
      â˜ƒ.move(â˜ƒx.getX(), â˜ƒx.getY(), â˜ƒx.getZ());
      return â˜ƒ;
   }

   public static void startHouseTower(StructureManager var0, BlockPos var1, Rotation var2, List<StructurePiece> var3, Random var4) {
      FAT_TOWER_GENERATOR.init();
      HOUSE_TOWER_GENERATOR.init();
      TOWER_BRIDGE_GENERATOR.init();
      TOWER_GENERATOR.init();
      EndCityPieces.EndCityPiece â˜ƒ = addHelper(â˜ƒ, new EndCityPieces.EndCityPiece(â˜ƒ, "base_floor", â˜ƒ, â˜ƒ, true));
      â˜ƒ = addHelper(â˜ƒ, addPiece(â˜ƒ, â˜ƒ, new BlockPos(-1, 0, -1), "second_floor_1", â˜ƒ, false));
      â˜ƒ = addHelper(â˜ƒ, addPiece(â˜ƒ, â˜ƒ, new BlockPos(-1, 4, -1), "third_floor_1", â˜ƒ, false));
      â˜ƒ = addHelper(â˜ƒ, addPiece(â˜ƒ, â˜ƒ, new BlockPos(-1, 8, -1), "third_roof", â˜ƒ, true));
      recursiveChildren(â˜ƒ, TOWER_GENERATOR, 1, â˜ƒ, null, â˜ƒ, â˜ƒ);
   }

   static EndCityPieces.EndCityPiece addHelper(List<StructurePiece> var0, EndCityPieces.EndCityPiece var1) {
      â˜ƒ.add(â˜ƒ);
      return â˜ƒ;
   }

   static boolean recursiveChildren(
      StructureManager var0,
      EndCityPieces.SectionGenerator var1,
      int var2,
      EndCityPieces.EndCityPiece var3,
      BlockPos var4,
      List<StructurePiece> var5,
      Random var6
   ) {
      if (â˜ƒ > 8) {
         return false;
      } else {
         List<StructurePiece> â˜ƒ = Lists.<StructurePiece>newArrayList();
         if (â˜ƒ.generate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            boolean â˜ƒx = false;
            int â˜ƒxx = â˜ƒ.nextInt();

            for(StructurePiece â˜ƒxxx : â˜ƒ) {
               â˜ƒxxx.genDepth = â˜ƒxx;
               StructurePiece â˜ƒxxxx = StructureStart.findCollisionPiece(â˜ƒ, â˜ƒxxx.getBoundingBox());
               if (â˜ƒxxxx != null && â˜ƒxxxx.genDepth != â˜ƒ.genDepth) {
                  â˜ƒx = true;
                  break;
               }
            }

            if (!â˜ƒx) {
               â˜ƒ.addAll(â˜ƒ);
               return true;
            }
         }

         return false;
      }
   }

   public static class EndCityPiece extends TemplateStructurePiece {
      public EndCityPiece(StructureManager var1, String var2, BlockPos var3, Rotation var4, boolean var5) {
         super(StructurePieceType.END_CITY_PIECE, 0, â˜ƒ, makeResourceLocation(â˜ƒ), â˜ƒ, makeSettings(â˜ƒ, â˜ƒ), â˜ƒ);
      }

      public EndCityPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.END_CITY_PIECE, â˜ƒ, â˜ƒ, var1x -> makeSettings(â˜ƒ.getBoolean("OW"), Rotation.valueOf(â˜ƒ.getString("Rot"))));
      }

      private static StructurePlaceSettings makeSettings(boolean var0, Rotation var1) {
         BlockIgnoreProcessor â˜ƒ = â˜ƒ ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
         return new StructurePlaceSettings().setIgnoreEntities(true).addProcessor(â˜ƒ).setRotation(â˜ƒ);
      }

      @Override
      protected ResourceLocation makeTemplateLocation() {
         return makeResourceLocation(this.templateName);
      }

      private static ResourceLocation makeResourceLocation(String var0) {
         return new ResourceLocation("end_city/" + â˜ƒ);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putString("Rot", this.placeSettings.getRotation().name());
         â˜ƒ.putBoolean("OW", this.placeSettings.getProcessors().get(0) == BlockIgnoreProcessor.STRUCTURE_BLOCK);
      }

      @Override
      protected void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5) {
         if (â˜ƒ.startsWith("Chest")) {
            BlockPos â˜ƒ = â˜ƒ.below();
            if (â˜ƒ.isInside(â˜ƒ)) {
               RandomizableContainerBlockEntity.setLootTable(â˜ƒ, â˜ƒ, â˜ƒ, BuiltInLootTables.END_CITY_TREASURE);
            }
         } else if (â˜ƒ.isInside(â˜ƒ) && Level.isInSpawnableBounds(â˜ƒ)) {
            if (â˜ƒ.startsWith("Sentry")) {
               Shulker â˜ƒ = EntityType.SHULKER.create(â˜ƒ.getLevel());
               â˜ƒ.setPos((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5);
               â˜ƒ.addFreshEntity(â˜ƒ);
            } else if (â˜ƒ.startsWith("Elytra")) {
               ItemFrame â˜ƒ = new ItemFrame(â˜ƒ.getLevel(), â˜ƒ, this.placeSettings.getRotation().rotate(Direction.SOUTH));
               â˜ƒ.setItem(new ItemStack(Items.ELYTRA), false);
               â˜ƒ.addFreshEntity(â˜ƒ);
            }
         }
      }
   }

   interface SectionGenerator {
      void init();

      boolean generate(StructureManager var1, int var2, EndCityPieces.EndCityPiece var3, BlockPos var4, List<StructurePiece> var5, Random var6);
   }
}
