package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.pathfinder.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugPackets {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void sendGameTestAddMarker(ServerLevel var0, BlockPos var1, String var2, int var3, int var4) {
      FriendlyByteBuf â˜ƒ = new FriendlyByteBuf(Unpooled.buffer());
      â˜ƒ.writeBlockPos(â˜ƒ);
      â˜ƒ.writeInt(â˜ƒ);
      â˜ƒ.writeUtf(â˜ƒ);
      â˜ƒ.writeInt(â˜ƒ);
      sendPacketToAllPlayers(â˜ƒ, â˜ƒ, ClientboundCustomPayloadPacket.DEBUG_GAME_TEST_ADD_MARKER);
   }

   public static void sendGameTestClearPacket(ServerLevel var0) {
      FriendlyByteBuf â˜ƒ = new FriendlyByteBuf(Unpooled.buffer());
      sendPacketToAllPlayers(â˜ƒ, â˜ƒ, ClientboundCustomPayloadPacket.DEBUG_GAME_TEST_CLEAR);
   }

   public static void sendPoiPacketsForChunk(ServerLevel var0, ChunkPos var1) {
   }

   public static void sendPoiAddedPacket(ServerLevel var0, BlockPos var1) {
      sendVillageSectionsPacket(â˜ƒ, â˜ƒ);
   }

   public static void sendPoiRemovedPacket(ServerLevel var0, BlockPos var1) {
      sendVillageSectionsPacket(â˜ƒ, â˜ƒ);
   }

   public static void sendPoiTicketCountPacket(ServerLevel var0, BlockPos var1) {
      sendVillageSectionsPacket(â˜ƒ, â˜ƒ);
   }

   private static void sendVillageSectionsPacket(ServerLevel var0, BlockPos var1) {
   }

   public static void sendPathFindingPacket(Level var0, Mob var1, @Nullable Path var2, float var3) {
   }

   public static void sendNeighborsUpdatePacket(Level var0, BlockPos var1) {
   }

   public static void sendStructurePacket(WorldGenLevel var0, StructureStart<?> var1) {
   }

   public static void sendGoalSelector(Level var0, Mob var1, GoalSelector var2) {
      if (â˜ƒ instanceof ServerLevel) {
         ;
      }
   }

   public static void sendRaids(ServerLevel var0, Collection<Raid> var1) {
   }

   public static void sendEntityBrain(LivingEntity var0) {
   }

   public static void sendBeeInfo(Bee var0) {
   }

   public static void sendGameEventInfo(Level var0, GameEvent var1, BlockPos var2) {
   }

   public static void sendGameEventListenerInfo(Level var0, GameEventListener var1) {
   }

   public static void sendHiveInfo(Level var0, BlockPos var1, BlockState var2, BeehiveBlockEntity var3) {
   }

   private static void writeBrain(LivingEntity var0, FriendlyByteBuf var1) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      long â˜ƒx = â˜ƒ.level.getGameTime();
      if (â˜ƒ instanceof InventoryCarrier) {
         Container â˜ƒxx = ((InventoryCarrier)â˜ƒ).getInventory();
         â˜ƒ.writeUtf(â˜ƒxx.isEmpty() ? "" : â˜ƒxx.toString());
      } else {
         â˜ƒ.writeUtf("");
      }

      if (â˜ƒ.hasMemoryValue(MemoryModuleType.PATH)) {
         â˜ƒ.writeBoolean(true);
         Path â˜ƒ = (Path)â˜ƒ.getMemory(MemoryModuleType.PATH).get();
         â˜ƒ.writeToStream(â˜ƒ);
      } else {
         â˜ƒ.writeBoolean(false);
      }

      if (â˜ƒ instanceof Villager â˜ƒ) {
         boolean â˜ƒx = â˜ƒ.wantsToSpawnGolem(â˜ƒx);
         â˜ƒ.writeBoolean(â˜ƒx);
      } else {
         â˜ƒ.writeBoolean(false);
      }

      â˜ƒ.writeCollection(â˜ƒ.getActiveActivities(), (var0x, var1x) -> var0x.writeUtf(var1x.getName()));
      Set<String> â˜ƒ = (Set)â˜ƒ.getRunningBehaviors().stream().map(Behavior::toString).collect(Collectors.toSet());
      â˜ƒ.writeCollection(â˜ƒ, FriendlyByteBuf::writeUtf);
      â˜ƒ.writeCollection(getMemoryDescriptions(â˜ƒ, â˜ƒx), (var0x, var1x) -> {
         String â˜ƒ = StringUtil.truncateStringIfNecessary(var1x, 255, true);
         var0x.writeUtf(â˜ƒ);
      });
      if (â˜ƒ instanceof Villager) {
         Set<BlockPos> â˜ƒx = (Set)Stream.of(MemoryModuleType.JOB_SITE, MemoryModuleType.HOME, MemoryModuleType.MEETING_POINT)
            .map(â˜ƒ::getMemory)
            .flatMap(Util::toStream)
            .map(GlobalPos::pos)
            .collect(Collectors.toSet());
         â˜ƒ.writeCollection(â˜ƒx, FriendlyByteBuf::writeBlockPos);
      } else {
         â˜ƒ.writeVarInt(0);
      }

      if (â˜ƒ instanceof Villager) {
         Set<BlockPos> â˜ƒ = (Set)Stream.of(MemoryModuleType.POTENTIAL_JOB_SITE)
            .map(â˜ƒ::getMemory)
            .flatMap(Util::toStream)
            .map(GlobalPos::pos)
            .collect(Collectors.toSet());
         â˜ƒ.writeCollection(â˜ƒ, FriendlyByteBuf::writeBlockPos);
      } else {
         â˜ƒ.writeVarInt(0);
      }

      if (â˜ƒ instanceof Villager) {
         Map<UUID, Object2IntMap<GossipType>> â˜ƒ = ((Villager)â˜ƒ).getGossips().getGossipEntries();
         List<String> â˜ƒx = Lists.newArrayList();
         â˜ƒ.forEach((var1x, var2x) -> {
            String â˜ƒ = DebugEntityNameGenerator.getEntityName(var1x);
            var2x.forEach((var2xx, var3x) -> â˜ƒ.add(â˜ƒ + ": " + var2xx + ": " + var3x));
         });
         â˜ƒ.writeCollection(â˜ƒx, FriendlyByteBuf::writeUtf);
      } else {
         â˜ƒ.writeVarInt(0);
      }
   }

   private static List<String> getMemoryDescriptions(LivingEntity var0, long var1) {
      Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> â˜ƒ = â˜ƒ.getBrain().getMemories();
      List<String> â˜ƒx = Lists.newArrayList();

      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> â˜ƒxx : â˜ƒ.entrySet()) {
         MemoryModuleType<?> â˜ƒxxxx = (MemoryModuleType)â˜ƒxx.getKey();
         Optional<? extends ExpirableValue<?>> â˜ƒxxxxx = (Optional)â˜ƒxx.getValue();
         String â˜ƒxxx;
         if (â˜ƒxxxxx.isPresent()) {
            ExpirableValue<?> â˜ƒxxxxxx = (ExpirableValue)â˜ƒxxxxx.get();
            Object â˜ƒxxxxxxx = â˜ƒxxxxxx.getValue();
            if (â˜ƒxxxx == MemoryModuleType.HEARD_BELL_TIME) {
               long â˜ƒxxxxxxxx = â˜ƒ - (Long)â˜ƒxxxxxxx;
               â˜ƒxxx = â˜ƒxxxxxxxx + " ticks ago";
            } else if (â˜ƒxxxxxx.canExpire()) {
               â˜ƒxxx = getShortDescription((ServerLevel)â˜ƒ.level, â˜ƒxxxxxxx) + " (ttl: " + â˜ƒxxxxxx.getTimeToLive() + ")";
            } else {
               â˜ƒxxx = getShortDescription((ServerLevel)â˜ƒ.level, â˜ƒxxxxxxx);
            }
         } else {
            â˜ƒxxx = "-";
         }

         â˜ƒx.add(Registry.MEMORY_MODULE_TYPE.getKey(â˜ƒxxxx).getPath() + ": " + â˜ƒxxx);
      }

      â˜ƒx.sort(String::compareTo);
      return â˜ƒx;
   }

   private static String getShortDescription(ServerLevel var0, @Nullable Object var1) {
      if (â˜ƒ == null) {
         return "-";
      } else if (â˜ƒ instanceof UUID) {
         return getShortDescription(â˜ƒ, â˜ƒ.getEntity((UUID)â˜ƒ));
      } else if (â˜ƒ instanceof LivingEntity) {
         Entity â˜ƒ = (Entity)â˜ƒ;
         return DebugEntityNameGenerator.getEntityName(â˜ƒ);
      } else if (â˜ƒ instanceof Nameable) {
         return ((Nameable)â˜ƒ).getName().getString();
      } else if (â˜ƒ instanceof WalkTarget) {
         return getShortDescription(â˜ƒ, ((WalkTarget)â˜ƒ).getTarget());
      } else if (â˜ƒ instanceof EntityTracker) {
         return getShortDescription(â˜ƒ, ((EntityTracker)â˜ƒ).getEntity());
      } else if (â˜ƒ instanceof GlobalPos) {
         return getShortDescription(â˜ƒ, ((GlobalPos)â˜ƒ).pos());
      } else if (â˜ƒ instanceof BlockPosTracker) {
         return getShortDescription(â˜ƒ, ((BlockPosTracker)â˜ƒ).currentBlockPosition());
      } else if (â˜ƒ instanceof EntityDamageSource) {
         Entity â˜ƒ = ((EntityDamageSource)â˜ƒ).getEntity();
         return â˜ƒ == null ? â˜ƒ.toString() : getShortDescription(â˜ƒ, â˜ƒ);
      } else if (!(â˜ƒ instanceof Collection)) {
         return â˜ƒ.toString();
      } else {
         List<String> â˜ƒ = Lists.newArrayList();

         for(Object â˜ƒx : (Iterable)â˜ƒ) {
            â˜ƒ.add(getShortDescription(â˜ƒ, â˜ƒx));
         }

         return â˜ƒ.toString();
      }
   }

   private static void sendPacketToAllPlayers(ServerLevel var0, FriendlyByteBuf var1, ResourceLocation var2) {
      Packet<?> â˜ƒ = new ClientboundCustomPayloadPacket(â˜ƒ, â˜ƒ);

      for(Player â˜ƒx : â˜ƒ.getLevel().players()) {
         ((ServerPlayer)â˜ƒx).connection.send(â˜ƒ);
      }
   }
}
