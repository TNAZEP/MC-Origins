package net.minecraft.world.entity.ai.goal;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class GolemRandomStrollInVillageGoal extends RandomStrollGoal {
   private static final int POI_SECTION_SCAN_RADIUS = 2;
   private static final int VILLAGER_SCAN_RADIUS = 32;
   private static final int RANDOM_POS_XY_DISTANCE = 10;
   private static final int RANDOM_POS_Y_DISTANCE = 7;

   public GolemRandomStrollInVillageGoal(PathfinderMob var1, double var2) {
      super(â˜ƒ, â˜ƒ, 240, false);
   }

   @Nullable
   @Override
   protected Vec3 getPosition() {
      float â˜ƒ = this.mob.level.random.nextFloat();
      if (this.mob.level.random.nextFloat() < 0.3F) {
         return this.getPositionTowardsAnywhere();
      } else {
         Vec3 â˜ƒ;
         if (â˜ƒ < 0.7F) {
            â˜ƒ = this.getPositionTowardsVillagerWhoWantsGolem();
            if (â˜ƒ == null) {
               â˜ƒ = this.getPositionTowardsPoi();
            }
         } else {
            â˜ƒ = this.getPositionTowardsPoi();
            if (â˜ƒ == null) {
               â˜ƒ = this.getPositionTowardsVillagerWhoWantsGolem();
            }
         }

         return â˜ƒ == null ? this.getPositionTowardsAnywhere() : â˜ƒ;
      }
   }

   @Nullable
   private Vec3 getPositionTowardsAnywhere() {
      return LandRandomPos.getPos(this.mob, 10, 7);
   }

   @Nullable
   private Vec3 getPositionTowardsVillagerWhoWantsGolem() {
      ServerLevel â˜ƒ = (ServerLevel)this.mob.level;
      List<Villager> â˜ƒx = â˜ƒ.getEntities(EntityType.VILLAGER, this.mob.getBoundingBox().inflate(32.0), this::doesVillagerWantGolem);
      if (â˜ƒx.isEmpty()) {
         return null;
      } else {
         Villager â˜ƒ = (Villager)â˜ƒx.get(this.mob.level.random.nextInt(â˜ƒx.size()));
         Vec3 â˜ƒx = â˜ƒ.position();
         return LandRandomPos.getPosTowards(this.mob, 10, 7, â˜ƒx);
      }
   }

   @Nullable
   private Vec3 getPositionTowardsPoi() {
      SectionPos â˜ƒ = this.getRandomVillageSection();
      if (â˜ƒ == null) {
         return null;
      } else {
         BlockPos â˜ƒ = this.getRandomPoiWithinSection(â˜ƒ);
         return â˜ƒ == null ? null : LandRandomPos.getPosTowards(this.mob, 10, 7, Vec3.atBottomCenterOf(â˜ƒ));
      }
   }

   @Nullable
   private SectionPos getRandomVillageSection() {
      ServerLevel â˜ƒ = (ServerLevel)this.mob.level;
      List<SectionPos> â˜ƒx = (List)SectionPos.cube(SectionPos.of(this.mob), 2).filter(var1x -> â˜ƒ.sectionsToVillage(var1x) == 0).collect(Collectors.toList());
      return â˜ƒx.isEmpty() ? null : (SectionPos)â˜ƒx.get(â˜ƒ.random.nextInt(â˜ƒx.size()));
   }

   @Nullable
   private BlockPos getRandomPoiWithinSection(SectionPos var1) {
      ServerLevel â˜ƒ = (ServerLevel)this.mob.level;
      PoiManager â˜ƒx = â˜ƒ.getPoiManager();
      List<BlockPos> â˜ƒxx = (List)â˜ƒx.getInRange(var0 -> true, â˜ƒ.center(), 8, PoiManager.Occupancy.IS_OCCUPIED)
         .map(PoiRecord::getPos)
         .collect(Collectors.toList());
      return â˜ƒxx.isEmpty() ? null : (BlockPos)â˜ƒxx.get(â˜ƒ.random.nextInt(â˜ƒxx.size()));
   }

   private boolean doesVillagerWantGolem(Villager var1) {
      return â˜ƒ.wantsToSpawnGolem(this.mob.level.getGameTime());
   }
}
