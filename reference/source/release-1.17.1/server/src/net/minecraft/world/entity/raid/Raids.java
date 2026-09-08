package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

public class Raids extends SavedData {
   private static final String RAID_FILE_ID = "raids";
   private final Map<Integer, Raid> raidMap = Maps.newHashMap();
   private final ServerLevel level;
   private int nextAvailableID;
   private int tick;

   public Raids(ServerLevel var1) {
      this.level = â˜ƒ;
      this.nextAvailableID = 1;
      this.setDirty();
   }

   public Raid get(int var1) {
      return (Raid)this.raidMap.get(â˜ƒ);
   }

   public void tick() {
      ++this.tick;
      Iterator<Raid> â˜ƒ = this.raidMap.values().iterator();

      while(â˜ƒ.hasNext()) {
         Raid â˜ƒx = (Raid)â˜ƒ.next();
         if (this.level.getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS)) {
            â˜ƒx.stop();
         }

         if (â˜ƒx.isStopped()) {
            â˜ƒ.remove();
            this.setDirty();
         } else {
            â˜ƒx.tick();
         }
      }

      if (this.tick % 200 == 0) {
         this.setDirty();
      }

      DebugPackets.sendRaids(this.level, this.raidMap.values());
   }

   public static boolean canJoinRaid(Raider var0, Raid var1) {
      if (â˜ƒ != null && â˜ƒ != null && â˜ƒ.getLevel() != null) {
         return â˜ƒ.isAlive() && â˜ƒ.canJoinRaid() && â˜ƒ.getNoActionTime() <= 2400 && â˜ƒ.level.dimensionType() == â˜ƒ.getLevel().dimensionType();
      } else {
         return false;
      }
   }

   @Nullable
   public Raid createOrExtendRaid(ServerPlayer var1) {
      if (â˜ƒ.isSpectator()) {
         return null;
      } else if (this.level.getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS)) {
         return null;
      } else {
         DimensionType â˜ƒ = â˜ƒ.level.dimensionType();
         if (!â˜ƒ.hasRaids()) {
            return null;
         } else {
            BlockPos â˜ƒ = â˜ƒ.blockPosition();
            List<PoiRecord> â˜ƒx = (List)this.level
               .getPoiManager()
               .getInRange(PoiType.ALL, â˜ƒ, 64, PoiManager.Occupancy.IS_OCCUPIED)
               .collect(Collectors.toList());
            int â˜ƒxx = 0;
            Vec3 â˜ƒxxx = Vec3.ZERO;

            for(PoiRecord â˜ƒxxxx : â˜ƒx) {
               BlockPos â˜ƒxxxxx = â˜ƒxxxx.getPos();
               â˜ƒxxx = â˜ƒxxx.add((double)â˜ƒxxxxx.getX(), (double)â˜ƒxxxxx.getY(), (double)â˜ƒxxxxx.getZ());
               ++â˜ƒxx;
            }

            BlockPos â˜ƒxxxx;
            if (â˜ƒxx > 0) {
               â˜ƒxxx = â˜ƒxxx.scale(1.0 / (double)â˜ƒxx);
               â˜ƒxxxx = new BlockPos(â˜ƒxxx);
            } else {
               â˜ƒxxxx = â˜ƒ;
            }

            Raid â˜ƒxxxx = this.getOrCreateRaid(â˜ƒ.getLevel(), â˜ƒxxxx);
            boolean â˜ƒxxxxx = false;
            if (!â˜ƒxxxx.isStarted()) {
               if (!this.raidMap.containsKey(â˜ƒxxxx.getId())) {
                  this.raidMap.put(â˜ƒxxxx.getId(), â˜ƒxxxx);
               }

               â˜ƒxxxxx = true;
            } else if (â˜ƒxxxx.getBadOmenLevel() < â˜ƒxxxx.getMaxBadOmenLevel()) {
               â˜ƒxxxxx = true;
            } else {
               â˜ƒ.removeEffect(MobEffects.BAD_OMEN);
               â˜ƒ.connection.send(new ClientboundEntityEventPacket(â˜ƒ, (byte)43));
            }

            if (â˜ƒxxxxx) {
               â˜ƒxxxx.absorbBadOmen(â˜ƒ);
               â˜ƒ.connection.send(new ClientboundEntityEventPacket(â˜ƒ, (byte)43));
               if (!â˜ƒxxxx.hasFirstWaveSpawned()) {
                  â˜ƒ.awardStat(Stats.RAID_TRIGGER);
                  CriteriaTriggers.BAD_OMEN.trigger(â˜ƒ);
               }
            }

            this.setDirty();
            return â˜ƒxxxx;
         }
      }
   }

   private Raid getOrCreateRaid(ServerLevel var1, BlockPos var2) {
      Raid â˜ƒ = â˜ƒ.getRaidAt(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ : new Raid(this.getUniqueId(), â˜ƒ, â˜ƒ);
   }

   public static Raids load(ServerLevel var0, CompoundTag var1) {
      Raids â˜ƒ = new Raids(â˜ƒ);
      â˜ƒ.nextAvailableID = â˜ƒ.getInt("NextAvailableID");
      â˜ƒ.tick = â˜ƒ.getInt("Tick");
      ListTag â˜ƒx = â˜ƒ.getList("Raids", 10);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
         CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
         Raid â˜ƒxxxx = new Raid(â˜ƒ, â˜ƒxxx);
         â˜ƒ.raidMap.put(â˜ƒxxxx.getId(), â˜ƒxxxx);
      }

      return â˜ƒ;
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      â˜ƒ.putInt("NextAvailableID", this.nextAvailableID);
      â˜ƒ.putInt("Tick", this.tick);
      ListTag â˜ƒ = new ListTag();

      for(Raid â˜ƒx : this.raidMap.values()) {
         CompoundTag â˜ƒxx = new CompoundTag();
         â˜ƒx.save(â˜ƒxx);
         â˜ƒ.add(â˜ƒxx);
      }

      â˜ƒ.put("Raids", â˜ƒ);
      return â˜ƒ;
   }

   public static String getFileId(DimensionType var0) {
      return "raids" + â˜ƒ.getFileSuffix();
   }

   private int getUniqueId() {
      return ++this.nextAvailableID;
   }

   @Nullable
   public Raid getNearbyRaid(BlockPos var1, int var2) {
      Raid â˜ƒ = null;
      double â˜ƒx = (double)â˜ƒ;

      for(Raid â˜ƒxx : this.raidMap.values()) {
         double â˜ƒxxx = â˜ƒxx.getCenter().distSqr(â˜ƒ);
         if (â˜ƒxx.isActive() && â˜ƒxxx < â˜ƒx) {
            â˜ƒ = â˜ƒxx;
            â˜ƒx = â˜ƒxxx;
         }
      }

      return â˜ƒ;
   }
}
