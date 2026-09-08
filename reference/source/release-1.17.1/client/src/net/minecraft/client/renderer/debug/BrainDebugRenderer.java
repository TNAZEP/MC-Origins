package net.minecraft.client.renderer.debug;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BrainDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final boolean SHOW_NAME_FOR_ALL = true;
   private static final boolean SHOW_PROFESSION_FOR_ALL = false;
   private static final boolean SHOW_BEHAVIORS_FOR_ALL = false;
   private static final boolean SHOW_ACTIVITIES_FOR_ALL = false;
   private static final boolean SHOW_INVENTORY_FOR_ALL = false;
   private static final boolean SHOW_GOSSIPS_FOR_ALL = false;
   private static final boolean SHOW_PATH_FOR_ALL = false;
   private static final boolean SHOW_HEALTH_FOR_ALL = false;
   private static final boolean SHOW_WANTS_GOLEM_FOR_ALL = true;
   private static final boolean SHOW_NAME_FOR_SELECTED = true;
   private static final boolean SHOW_PROFESSION_FOR_SELECTED = true;
   private static final boolean SHOW_BEHAVIORS_FOR_SELECTED = true;
   private static final boolean SHOW_ACTIVITIES_FOR_SELECTED = true;
   private static final boolean SHOW_MEMORIES_FOR_SELECTED = true;
   private static final boolean SHOW_INVENTORY_FOR_SELECTED = true;
   private static final boolean SHOW_GOSSIPS_FOR_SELECTED = true;
   private static final boolean SHOW_PATH_FOR_SELECTED = true;
   private static final boolean SHOW_HEALTH_FOR_SELECTED = true;
   private static final boolean SHOW_WANTS_GOLEM_FOR_SELECTED = true;
   private static final boolean SHOW_POI_INFO = true;
   private static final int MAX_RENDER_DIST_FOR_BRAIN_INFO = 30;
   private static final int MAX_RENDER_DIST_FOR_POI_INFO = 30;
   private static final int MAX_TARGETING_DIST = 8;
   private static final float TEXT_SCALE = 0.02F;
   private static final int WHITE = -1;
   private static final int YELLOW = -256;
   private static final int CYAN = -16711681;
   private static final int GREEN = -16711936;
   private static final int GRAY = -3355444;
   private static final int PINK = -98404;
   private static final int RED = -65536;
   private static final int ORANGE = -23296;
   private final Minecraft minecraft;
   private final Map<BlockPos, BrainDebugRenderer.PoiInfo> pois = Maps.<BlockPos, BrainDebugRenderer.PoiInfo>newHashMap();
   private final Map<UUID, BrainDebugRenderer.BrainDump> brainDumpsPerEntity = Maps.newHashMap();
   @Nullable
   private UUID lastLookedAtUuid;

   public BrainDebugRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void clear() {
      this.pois.clear();
      this.brainDumpsPerEntity.clear();
      this.lastLookedAtUuid = null;
   }

   public void addPoi(BrainDebugRenderer.PoiInfo var1) {
      this.pois.put(â˜ƒ.pos, â˜ƒ);
   }

   public void removePoi(BlockPos var1) {
      this.pois.remove(â˜ƒ);
   }

   public void setFreeTicketCount(BlockPos var1, int var2) {
      BrainDebugRenderer.PoiInfo â˜ƒ = (BrainDebugRenderer.PoiInfo)this.pois.get(â˜ƒ);
      if (â˜ƒ == null) {
         LOGGER.warn("Strange, setFreeTicketCount was called for an unknown POI: {}", â˜ƒ);
      } else {
         â˜ƒ.freeTicketCount = â˜ƒ;
      }
   }

   public void addOrUpdateBrainDump(BrainDebugRenderer.BrainDump var1) {
      this.brainDumpsPerEntity.put(â˜ƒ.uuid, â˜ƒ);
   }

   public void removeBrainDump(int var1) {
      this.brainDumpsPerEntity.values().removeIf(var1x -> var1x.id == â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.clearRemovedEntities();
      this.doRender(â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      if (!this.minecraft.player.isSpectator()) {
         this.updateLastLookedAtUuid();
      }
   }

   private void clearRemovedEntities() {
      this.brainDumpsPerEntity.entrySet().removeIf(var1 -> {
         Entity â˜ƒ = this.minecraft.level.getEntity(((BrainDebugRenderer.BrainDump)var1.getValue()).id);
         return â˜ƒ == null || â˜ƒ.isRemoved();
      });
   }

   private void doRender(double var1, double var3, double var5) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.brainDumpsPerEntity.values().forEach(var7x -> {
         if (this.isPlayerCloseEnoughToMob(var7x)) {
            this.renderBrainInfo(var7x, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      });

      for(BlockPos â˜ƒx : this.pois.keySet()) {
         if (â˜ƒ.closerThan(â˜ƒx, 30.0)) {
            highlightPoi(â˜ƒx);
         }
      }

      this.pois.values().forEach(var2 -> {
         if (â˜ƒ.closerThan(var2.pos, 30.0)) {
            this.renderPoiInfo(var2);
         }
      });
      this.getGhostPois().forEach((var2, var3x) -> {
         if (â˜ƒ.closerThan(var2, 30.0)) {
            this.renderGhostPoi(var2, var3x);
         }
      });
   }

   private static void highlightPoi(BlockPos var0) {
      float â˜ƒ = 0.05F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      DebugRenderer.renderFilledBox(â˜ƒ, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void renderGhostPoi(BlockPos var1, List<String> var2) {
      float â˜ƒ = 0.05F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      DebugRenderer.renderFilledBox(â˜ƒ, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      renderTextOverPos(â˜ƒ + "", â˜ƒ, 0, -256);
      renderTextOverPos("Ghost POI", â˜ƒ, 1, -65536);
   }

   private void renderPoiInfo(BrainDebugRenderer.PoiInfo var1) {
      int â˜ƒ = 0;
      Set<String> â˜ƒx = this.getTicketHolderNames(â˜ƒ);
      if (â˜ƒx.size() < 4) {
         renderTextOverPoi("Owners: " + â˜ƒx, â˜ƒ, â˜ƒ, -256);
      } else {
         renderTextOverPoi(â˜ƒx.size() + " ticket holders", â˜ƒ, â˜ƒ, -256);
      }

      ++â˜ƒ;
      Set<String> â˜ƒ = this.getPotentialTicketHolderNames(â˜ƒ);
      if (â˜ƒ.size() < 4) {
         renderTextOverPoi("Candidates: " + â˜ƒ, â˜ƒ, â˜ƒ, -23296);
      } else {
         renderTextOverPoi(â˜ƒ.size() + " potential owners", â˜ƒ, â˜ƒ, -23296);
      }

      renderTextOverPoi("Free tickets: " + â˜ƒ.freeTicketCount, â˜ƒ, ++â˜ƒ, -256);
      renderTextOverPoi(â˜ƒ.type, â˜ƒ, ++â˜ƒ, -1);
   }

   private void renderPath(BrainDebugRenderer.BrainDump var1, double var2, double var4, double var6) {
      if (â˜ƒ.path != null) {
         PathfindingRenderer.renderPath(â˜ƒ.path, 0.5F, false, false, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void renderBrainInfo(BrainDebugRenderer.BrainDump var1, double var2, double var4, double var6) {
      boolean â˜ƒ = this.isMobSelected(â˜ƒ);
      int â˜ƒx = 0;
      renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ.name, -1, 0.03F);
      ++â˜ƒx;
      if (â˜ƒ) {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ.profession + " " + â˜ƒ.xp + " xp", -1, 0.02F);
         ++â˜ƒx;
      }

      if (â˜ƒ) {
         int â˜ƒ = â˜ƒ.health < â˜ƒ.maxHealth ? -23296 : -1;
         renderTextOverMob(â˜ƒ.pos, â˜ƒx, "health: " + String.format("%.1f", â˜ƒ.health) + " / " + String.format("%.1f", â˜ƒ.maxHealth), â˜ƒ, 0.02F);
         ++â˜ƒx;
      }

      if (â˜ƒ && !â˜ƒ.inventory.equals("")) {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ.inventory, -98404, 0.02F);
         ++â˜ƒx;
      }

      if (â˜ƒ) {
         for(String â˜ƒ : â˜ƒ.behaviors) {
            renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ, -16711681, 0.02F);
            ++â˜ƒx;
         }
      }

      if (â˜ƒ) {
         for(String â˜ƒ : â˜ƒ.activities) {
            renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ, -16711936, 0.02F);
            ++â˜ƒx;
         }
      }

      if (â˜ƒ.wantsGolem) {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx, "Wants Golem", -23296, 0.02F);
         ++â˜ƒx;
      }

      if (â˜ƒ) {
         for(String â˜ƒ : â˜ƒ.gossips) {
            if (â˜ƒ.startsWith(â˜ƒ.name)) {
               renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ, -1, 0.02F);
            } else {
               renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ, -23296, 0.02F);
            }

            ++â˜ƒx;
         }
      }

      if (â˜ƒ) {
         for(String â˜ƒ : Lists.reverse(â˜ƒ.memories)) {
            renderTextOverMob(â˜ƒ.pos, â˜ƒx, â˜ƒ, -3355444, 0.02F);
            ++â˜ƒx;
         }
      }

      if (â˜ƒ) {
         this.renderPath(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static void renderTextOverPoi(String var0, BrainDebugRenderer.PoiInfo var1, int var2, int var3) {
      BlockPos â˜ƒ = â˜ƒ.pos;
      renderTextOverPos(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void renderTextOverPos(String var0, BlockPos var1, int var2, int var3) {
      double â˜ƒ = 1.3;
      double â˜ƒx = 0.2;
      double â˜ƒxx = (double)â˜ƒ.getX() + 0.5;
      double â˜ƒxxx = (double)â˜ƒ.getY() + 1.3 + (double)â˜ƒ * 0.2;
      double â˜ƒxxxx = (double)â˜ƒ.getZ() + 0.5;
      DebugRenderer.renderFloatingText(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒ, 0.02F, true, 0.0F, true);
   }

   private static void renderTextOverMob(Position var0, int var1, String var2, int var3, float var4) {
      double â˜ƒ = 2.4;
      double â˜ƒx = 0.25;
      BlockPos â˜ƒxx = new BlockPos(â˜ƒ);
      double â˜ƒxxx = (double)â˜ƒxx.getX() + 0.5;
      double â˜ƒxxxx = â˜ƒ.y() + 2.4 + (double)â˜ƒ * 0.25;
      double â˜ƒxxxxx = (double)â˜ƒxx.getZ() + 0.5;
      float â˜ƒxxxxxx = 0.5F;
      DebugRenderer.renderFloatingText(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ, false, 0.5F, true);
   }

   private Set<String> getTicketHolderNames(BrainDebugRenderer.PoiInfo var1) {
      return (Set<String>)this.getTicketHolders(â˜ƒ.pos).stream().map(DebugEntityNameGenerator::getEntityName).collect(Collectors.toSet());
   }

   private Set<String> getPotentialTicketHolderNames(BrainDebugRenderer.PoiInfo var1) {
      return (Set<String>)this.getPotentialTicketHolders(â˜ƒ.pos).stream().map(DebugEntityNameGenerator::getEntityName).collect(Collectors.toSet());
   }

   private boolean isMobSelected(BrainDebugRenderer.BrainDump var1) {
      return Objects.equals(this.lastLookedAtUuid, â˜ƒ.uuid);
   }

   private boolean isPlayerCloseEnoughToMob(BrainDebugRenderer.BrainDump var1) {
      Player â˜ƒ = this.minecraft.player;
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getX(), â˜ƒ.pos.y(), â˜ƒ.getZ());
      BlockPos â˜ƒxx = new BlockPos(â˜ƒ.pos);
      return â˜ƒx.closerThan(â˜ƒxx, 30.0);
   }

   private Collection<UUID> getTicketHolders(BlockPos var1) {
      return (Collection<UUID>)this.brainDumpsPerEntity
         .values()
         .stream()
         .filter(var1x -> var1x.hasPoi(â˜ƒ))
         .map(BrainDebugRenderer.BrainDump::getUuid)
         .collect(Collectors.toSet());
   }

   private Collection<UUID> getPotentialTicketHolders(BlockPos var1) {
      return (Collection<UUID>)this.brainDumpsPerEntity
         .values()
         .stream()
         .filter(var1x -> var1x.hasPotentialPoi(â˜ƒ))
         .map(BrainDebugRenderer.BrainDump::getUuid)
         .collect(Collectors.toSet());
   }

   private Map<BlockPos, List<String>> getGhostPois() {
      Map<BlockPos, List<String>> â˜ƒ = Maps.newHashMap();

      for(BrainDebugRenderer.BrainDump â˜ƒx : this.brainDumpsPerEntity.values()) {
         for(BlockPos â˜ƒxx : Iterables.concat(â˜ƒx.pois, â˜ƒx.potentialPois)) {
            if (!this.pois.containsKey(â˜ƒxx)) {
               ((List)â˜ƒ.computeIfAbsent(â˜ƒxx, var0 -> Lists.newArrayList())).add(â˜ƒx.name);
            }
         }
      }

      return â˜ƒ;
   }

   private void updateLastLookedAtUuid() {
      DebugRenderer.getTargetedEntity(this.minecraft.getCameraEntity(), 8).ifPresent(var1 -> this.lastLookedAtUuid = var1.getUUID());
   }

   public static class BrainDump {
      public final UUID uuid;
      public final int id;
      public final String name;
      public final String profession;
      public final int xp;
      public final float health;
      public final float maxHealth;
      public final Position pos;
      public final String inventory;
      public final Path path;
      public final boolean wantsGolem;
      public final List<String> activities = Lists.newArrayList();
      public final List<String> behaviors = Lists.newArrayList();
      public final List<String> memories = Lists.newArrayList();
      public final List<String> gossips = Lists.newArrayList();
      public final Set<BlockPos> pois = Sets.<BlockPos>newHashSet();
      public final Set<BlockPos> potentialPois = Sets.<BlockPos>newHashSet();

      public BrainDump(
         UUID var1, int var2, String var3, String var4, int var5, float var6, float var7, Position var8, String var9, @Nullable Path var10, boolean var11
      ) {
         this.uuid = â˜ƒ;
         this.id = â˜ƒ;
         this.name = â˜ƒ;
         this.profession = â˜ƒ;
         this.xp = â˜ƒ;
         this.health = â˜ƒ;
         this.maxHealth = â˜ƒ;
         this.pos = â˜ƒ;
         this.inventory = â˜ƒ;
         this.path = â˜ƒ;
         this.wantsGolem = â˜ƒ;
      }

      boolean hasPoi(BlockPos var1) {
         return this.pois.stream().anyMatch(â˜ƒ::equals);
      }

      boolean hasPotentialPoi(BlockPos var1) {
         return this.potentialPois.contains(â˜ƒ);
      }

      public UUID getUuid() {
         return this.uuid;
      }
   }

   public static class PoiInfo {
      public final BlockPos pos;
      public String type;
      public int freeTicketCount;

      public PoiInfo(BlockPos var1, String var2, int var3) {
         this.pos = â˜ƒ;
         this.type = â˜ƒ;
         this.freeTicketCount = â˜ƒ;
      }
   }
}
