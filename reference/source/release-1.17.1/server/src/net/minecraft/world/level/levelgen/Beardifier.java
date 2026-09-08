package net.minecraft.world.level.levelgen;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.Util;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.structures.JigsawJunction;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class Beardifier {
   public static final Beardifier NO_BEARDS = new Beardifier();
   public static final int BEARD_KERNEL_RADIUS = 12;
   private static final int BEARD_KERNEL_SIZE = 24;
   private static final float[] BEARD_KERNEL = Util.make(new float[13824], var0 -> {
      for(int â˜ƒ = 0; â˜ƒ < 24; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 24; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < 24; ++â˜ƒxx) {
               var0[â˜ƒ * 24 * 24 + â˜ƒx * 24 + â˜ƒxx] = (float)computeBeardContribution(â˜ƒx - 12, â˜ƒxx - 12, â˜ƒ - 12);
            }
         }
      }
   });
   private final ObjectList<StructurePiece> rigids;
   private final ObjectList<JigsawJunction> junctions;
   private final ObjectListIterator<StructurePiece> pieceIterator;
   private final ObjectListIterator<JigsawJunction> junctionIterator;

   protected Beardifier(StructureFeatureManager var1, ChunkAccess var2) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      int â˜ƒx = â˜ƒ.getMinBlockX();
      int â˜ƒxx = â˜ƒ.getMinBlockZ();
      this.junctions = new ObjectArrayList<>(32);
      this.rigids = new ObjectArrayList<>(10);

      for(StructureFeature<?> â˜ƒxxx : StructureFeature.NOISE_AFFECTING_FEATURES) {
         â˜ƒ.startsForFeature(SectionPos.bottomOf(â˜ƒ), â˜ƒxxx).forEach(var4x -> {
            for(StructurePiece â˜ƒ : var4x.getPieces()) {
               if (â˜ƒ.isCloseToChunk(â˜ƒ, 12)) {
                  if (â˜ƒ instanceof PoolElementStructurePiece â˜ƒx) {
                     StructureTemplatePool.Projection â˜ƒxx = â˜ƒx.getElement().getProjection();
                     if (â˜ƒxx == StructureTemplatePool.Projection.RIGID) {
                        this.rigids.add(â˜ƒx);
                     }

                     for(JigsawJunction â˜ƒxx : â˜ƒx.getJunctions()) {
                        int â˜ƒxxx = â˜ƒxx.getSourceX();
                        int â˜ƒxxxx = â˜ƒxx.getSourceZ();
                        if (â˜ƒxxx > â˜ƒ - 12 && â˜ƒxxxx > â˜ƒ - 12 && â˜ƒxxx < â˜ƒ + 15 + 12 && â˜ƒxxxx < â˜ƒ + 15 + 12) {
                           this.junctions.add(â˜ƒxx);
                        }
                     }
                  } else {
                     this.rigids.add(â˜ƒ);
                  }
               }
            }
         });
      }

      this.pieceIterator = this.rigids.iterator();
      this.junctionIterator = this.junctions.iterator();
   }

   private Beardifier() {
      this.junctions = new ObjectArrayList<>();
      this.rigids = new ObjectArrayList<>();
      this.pieceIterator = this.rigids.iterator();
      this.junctionIterator = this.junctions.iterator();
   }

   protected double beardifyOrBury(int var1, int var2, int var3) {
      double â˜ƒ = 0.0;

      while(this.pieceIterator.hasNext()) {
         StructurePiece â˜ƒx = (StructurePiece)this.pieceIterator.next();
         BoundingBox â˜ƒxx = â˜ƒx.getBoundingBox();
         int â˜ƒxxx = Math.max(0, Math.max(â˜ƒxx.minX() - â˜ƒ, â˜ƒ - â˜ƒxx.maxX()));
         int â˜ƒxxxx = â˜ƒ - (â˜ƒxx.minY() + (â˜ƒx instanceof PoolElementStructurePiece ? ((PoolElementStructurePiece)â˜ƒx).getGroundLevelDelta() : 0));
         int â˜ƒxxxxx = Math.max(0, Math.max(â˜ƒxx.minZ() - â˜ƒ, â˜ƒ - â˜ƒxx.maxZ()));
         NoiseEffect â˜ƒxxxxxx = â˜ƒx.getNoiseEffect();
         if (â˜ƒxxxxxx == NoiseEffect.BURY) {
            â˜ƒ += getBuryContribution(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         } else if (â˜ƒxxxxxx == NoiseEffect.BEARD) {
            â˜ƒ += getBeardContribution(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx) * 0.8;
         }
      }

      this.pieceIterator.back(this.rigids.size());

      while(this.junctionIterator.hasNext()) {
         JigsawJunction â˜ƒx = (JigsawJunction)this.junctionIterator.next();
         int â˜ƒxx = â˜ƒ - â˜ƒx.getSourceX();
         int â˜ƒxxx = â˜ƒ - â˜ƒx.getSourceGroundY();
         int â˜ƒxxxx = â˜ƒ - â˜ƒx.getSourceZ();
         â˜ƒ += getBeardContribution(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx) * 0.4;
      }

      this.junctionIterator.back(this.junctions.size());
      return â˜ƒ;
   }

   private static double getBuryContribution(int var0, int var1, int var2) {
      double â˜ƒ = Mth.length(â˜ƒ, (double)â˜ƒ / 2.0, â˜ƒ);
      return Mth.clampedMap(â˜ƒ, 0.0, 6.0, 1.0, 0.0);
   }

   private static double getBeardContribution(int var0, int var1, int var2) {
      int â˜ƒ = â˜ƒ + 12;
      int â˜ƒx = â˜ƒ + 12;
      int â˜ƒxx = â˜ƒ + 12;
      if (â˜ƒ < 0 || â˜ƒ >= 24) {
         return 0.0;
      } else if (â˜ƒx < 0 || â˜ƒx >= 24) {
         return 0.0;
      } else {
         return â˜ƒxx >= 0 && â˜ƒxx < 24 ? (double)BEARD_KERNEL[â˜ƒxx * 24 * 24 + â˜ƒ * 24 + â˜ƒx] : 0.0;
      }
   }

   private static double computeBeardContribution(int var0, int var1, int var2) {
      double â˜ƒ = (double)(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
      double â˜ƒx = (double)â˜ƒ + 0.5;
      double â˜ƒxx = â˜ƒx * â˜ƒx;
      double â˜ƒxxx = Math.pow(Math.E, -(â˜ƒxx / 16.0 + â˜ƒ / 16.0));
      double â˜ƒxxxx = -â˜ƒx * Mth.fastInvSqrt(â˜ƒxx / 2.0 + â˜ƒ / 2.0) / 2.0;
      return â˜ƒxxxx * â˜ƒxxx;
   }
}
