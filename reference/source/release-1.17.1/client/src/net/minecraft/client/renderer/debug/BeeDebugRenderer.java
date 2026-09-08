package net.minecraft.client.renderer.debug;

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
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class BeeDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final boolean SHOW_GOAL_FOR_ALL_BEES = true;
   private static final boolean SHOW_NAME_FOR_ALL_BEES = true;
   private static final boolean SHOW_HIVE_FOR_ALL_BEES = true;
   private static final boolean SHOW_FLOWER_POS_FOR_ALL_BEES = true;
   private static final boolean SHOW_TRAVEL_TICKS_FOR_ALL_BEES = true;
   private static final boolean SHOW_PATH_FOR_ALL_BEES = false;
   private static final boolean SHOW_GOAL_FOR_SELECTED_BEE = true;
   private static final boolean SHOW_NAME_FOR_SELECTED_BEE = true;
   private static final boolean SHOW_HIVE_FOR_SELECTED_BEE = true;
   private static final boolean SHOW_FLOWER_POS_FOR_SELECTED_BEE = true;
   private static final boolean SHOW_TRAVEL_TICKS_FOR_SELECTED_BEE = true;
   private static final boolean SHOW_PATH_FOR_SELECTED_BEE = true;
   private static final boolean SHOW_HIVE_MEMBERS = true;
   private static final boolean SHOW_BLACKLISTS = true;
   private static final int MAX_RENDER_DIST_FOR_HIVE_OVERLAY = 30;
   private static final int MAX_RENDER_DIST_FOR_BEE_OVERLAY = 30;
   private static final int MAX_TARGETING_DIST = 8;
   private static final int HIVE_TIMEOUT = 20;
   private static final float TEXT_SCALE = 0.02F;
   private static final int WHITE = -1;
   private static final int YELLOW = -256;
   private static final int ORANGE = -23296;
   private static final int GREEN = -16711936;
   private static final int GRAY = -3355444;
   private static final int PINK = -98404;
   private static final int RED = -65536;
   private final Minecraft minecraft;
   private final Map<BlockPos, BeeDebugRenderer.HiveInfo> hives = Maps.<BlockPos, BeeDebugRenderer.HiveInfo>newHashMap();
   private final Map<UUID, BeeDebugRenderer.BeeInfo> beeInfosPerEntity = Maps.newHashMap();
   private UUID lastLookedAtUuid;

   public BeeDebugRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void clear() {
      this.hives.clear();
      this.beeInfosPerEntity.clear();
      this.lastLookedAtUuid = null;
   }

   public void addOrUpdateHiveInfo(BeeDebugRenderer.HiveInfo var1) {
      this.hives.put(â˜ƒ.pos, â˜ƒ);
   }

   public void addOrUpdateBeeInfo(BeeDebugRenderer.BeeInfo var1) {
      this.beeInfosPerEntity.put(â˜ƒ.uuid, â˜ƒ);
   }

   public void removeBeeInfo(int var1) {
      this.beeInfosPerEntity.values().removeIf(var1x -> var1x.id == â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.clearRemovedHives();
      this.clearRemovedBees();
      this.doRender();
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      if (!this.minecraft.player.isSpectator()) {
         this.updateLastLookedAtUuid();
      }
   }

   private void clearRemovedBees() {
      this.beeInfosPerEntity.entrySet().removeIf(var1 -> this.minecraft.level.getEntity(((BeeDebugRenderer.BeeInfo)var1.getValue()).id) == null);
   }

   private void clearRemovedHives() {
      long â˜ƒ = this.minecraft.level.getGameTime() - 20L;
      this.hives.entrySet().removeIf(var2 -> ((BeeDebugRenderer.HiveInfo)var2.getValue()).lastSeen < â˜ƒ);
   }

   private void doRender() {
      BlockPos â˜ƒ = this.getCamera().getBlockPosition();
      this.beeInfosPerEntity.values().forEach(var1x -> {
         if (this.isPlayerCloseEnoughToMob(var1x)) {
            this.renderBeeInfo(var1x);
         }
      });
      this.renderFlowerInfos();

      for(BlockPos â˜ƒx : this.hives.keySet()) {
         if (â˜ƒ.closerThan(â˜ƒx, 30.0)) {
            highlightHive(â˜ƒx);
         }
      }

      Map<BlockPos, Set<UUID>> â˜ƒx = this.createHiveBlacklistMap();
      this.hives.values().forEach(var3x -> {
         if (â˜ƒ.closerThan(var3x.pos, 30.0)) {
            Set<UUID> â˜ƒ = (Set)â˜ƒ.get(var3x.pos);
            this.renderHiveInfo(var3x, (Collection<UUID>)(â˜ƒ == null ? Sets.newHashSet() : â˜ƒ));
         }
      });
      this.getGhostHives().forEach((var2, var3x) -> {
         if (â˜ƒ.closerThan(var2, 30.0)) {
            this.renderGhostHive(var2, var3x);
         }
      });
   }

   private Map<BlockPos, Set<UUID>> createHiveBlacklistMap() {
      Map<BlockPos, Set<UUID>> â˜ƒ = Maps.newHashMap();
      this.beeInfosPerEntity
         .values()
         .forEach(var1x -> var1x.blacklistedHives.forEach(var2 -> ((Set)â˜ƒ.computeIfAbsent(var2, var0x -> Sets.newHashSet())).add(var1x.getUuid())));
      return â˜ƒ;
   }

   private void renderFlowerInfos() {
      Map<BlockPos, Set<UUID>> â˜ƒ = Maps.newHashMap();
      this.beeInfosPerEntity
         .values()
         .stream()
         .filter(BeeDebugRenderer.BeeInfo::hasFlower)
         .forEach(var1x -> ((Set)â˜ƒ.computeIfAbsent(var1x.flowerPos, var0x -> Sets.newHashSet())).add(var1x.getUuid()));
      â˜ƒ.entrySet().forEach(var0 -> {
         BlockPos â˜ƒ = (BlockPos)var0.getKey();
         Set<UUID> â˜ƒx = (Set)var0.getValue();
         Set<String> â˜ƒxx = (Set)â˜ƒx.stream().map(DebugEntityNameGenerator::getEntityName).collect(Collectors.toSet());
         int â˜ƒxxx = 1;
         renderTextOverPos(â˜ƒxx.toString(), â˜ƒ, â˜ƒxxx++, -256);
         renderTextOverPos("Flower", â˜ƒ, â˜ƒxxx++, -1);
         float â˜ƒxxxx = 0.05F;
         renderTransparentFilledBox(â˜ƒ, 0.05F, 0.8F, 0.8F, 0.0F, 0.3F);
      });
   }

   private static String getBeeUuidsAsString(Collection<UUID> var0) {
      if (â˜ƒ.isEmpty()) {
         return "-";
      } else {
         return â˜ƒ.size() > 3 ? â˜ƒ.size() + " bees" : ((Set)â˜ƒ.stream().map(DebugEntityNameGenerator::getEntityName).collect(Collectors.toSet())).toString();
      }
   }

   private static void highlightHive(BlockPos var0) {
      float â˜ƒ = 0.05F;
      renderTransparentFilledBox(â˜ƒ, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void renderGhostHive(BlockPos var1, List<String> var2) {
      float â˜ƒ = 0.05F;
      renderTransparentFilledBox(â˜ƒ, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      renderTextOverPos(â˜ƒ + "", â˜ƒ, 0, -256);
      renderTextOverPos("Ghost Hive", â˜ƒ, 1, -65536);
   }

   private static void renderTransparentFilledBox(BlockPos var0, float var1, float var2, float var3, float var4, float var5) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      DebugRenderer.renderFilledBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void renderHiveInfo(BeeDebugRenderer.HiveInfo var1, Collection<UUID> var2) {
      int â˜ƒ = 0;
      if (!â˜ƒ.isEmpty()) {
         renderTextOverHive("Blacklisted by " + getBeeUuidsAsString(â˜ƒ), â˜ƒ, â˜ƒ++, -65536);
      }

      renderTextOverHive("Out: " + getBeeUuidsAsString(this.getHiveMembers(â˜ƒ.pos)), â˜ƒ, â˜ƒ++, -3355444);
      if (â˜ƒ.occupantCount == 0) {
         renderTextOverHive("In: -", â˜ƒ, â˜ƒ++, -256);
      } else if (â˜ƒ.occupantCount == 1) {
         renderTextOverHive("In: 1 bee", â˜ƒ, â˜ƒ++, -256);
      } else {
         renderTextOverHive("In: " + â˜ƒ.occupantCount + " bees", â˜ƒ, â˜ƒ++, -256);
      }

      renderTextOverHive("Honey: " + â˜ƒ.honeyLevel, â˜ƒ, â˜ƒ++, -23296);
      renderTextOverHive(â˜ƒ.hiveType + (â˜ƒ.sedated ? " (sedated)" : ""), â˜ƒ, â˜ƒ++, -1);
   }

   private void renderPath(BeeDebugRenderer.BeeInfo var1) {
      if (â˜ƒ.path != null) {
         PathfindingRenderer.renderPath(
            â˜ƒ.path, 0.5F, false, false, this.getCamera().getPosition().x(), this.getCamera().getPosition().y(), this.getCamera().getPosition().z()
         );
      }
   }

   private void renderBeeInfo(BeeDebugRenderer.BeeInfo var1) {
      boolean â˜ƒ = this.isBeeSelected(â˜ƒ);
      int â˜ƒx = 0;
      renderTextOverMob(â˜ƒ.pos, â˜ƒx++, â˜ƒ.toString(), -1, 0.03F);
      if (â˜ƒ.hivePos == null) {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx++, "No hive", -98404, 0.02F);
      } else {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx++, "Hive: " + this.getPosDescription(â˜ƒ, â˜ƒ.hivePos), -256, 0.02F);
      }

      if (â˜ƒ.flowerPos == null) {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx++, "No flower", -98404, 0.02F);
      } else {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx++, "Flower: " + this.getPosDescription(â˜ƒ, â˜ƒ.flowerPos), -256, 0.02F);
      }

      for(String â˜ƒ : â˜ƒ.goals) {
         renderTextOverMob(â˜ƒ.pos, â˜ƒx++, â˜ƒ, -16711936, 0.02F);
      }

      if (â˜ƒ) {
         this.renderPath(â˜ƒ);
      }

      if (â˜ƒ.travelTicks > 0) {
         int â˜ƒ = â˜ƒ.travelTicks < 600 ? -3355444 : -23296;
         renderTextOverMob(â˜ƒ.pos, â˜ƒx++, "Travelling: " + â˜ƒ.travelTicks + " ticks", â˜ƒ, 0.02F);
      }
   }

   private static void renderTextOverHive(String var0, BeeDebugRenderer.HiveInfo var1, int var2, int var3) {
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

   private Camera getCamera() {
      return this.minecraft.gameRenderer.getMainCamera();
   }

   private Set<String> getHiveMemberNames(BeeDebugRenderer.HiveInfo var1) {
      return (Set<String>)this.getHiveMembers(â˜ƒ.pos).stream().map(DebugEntityNameGenerator::getEntityName).collect(Collectors.toSet());
   }

   private String getPosDescription(BeeDebugRenderer.BeeInfo var1, BlockPos var2) {
      double â˜ƒ = Math.sqrt(â˜ƒ.distSqr(â˜ƒ.pos.x(), â˜ƒ.pos.y(), â˜ƒ.pos.z(), true));
      double â˜ƒx = (double)Math.round(â˜ƒ * 10.0) / 10.0;
      return â˜ƒ.toShortString() + " (dist " + â˜ƒx + ")";
   }

   private boolean isBeeSelected(BeeDebugRenderer.BeeInfo var1) {
      return Objects.equals(this.lastLookedAtUuid, â˜ƒ.uuid);
   }

   private boolean isPlayerCloseEnoughToMob(BeeDebugRenderer.BeeInfo var1) {
      Player â˜ƒ = this.minecraft.player;
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getX(), â˜ƒ.pos.y(), â˜ƒ.getZ());
      BlockPos â˜ƒxx = new BlockPos(â˜ƒ.pos);
      return â˜ƒx.closerThan(â˜ƒxx, 30.0);
   }

   private Collection<UUID> getHiveMembers(BlockPos var1) {
      return (Collection<UUID>)this.beeInfosPerEntity
         .values()
         .stream()
         .filter(var1x -> var1x.hasHive(â˜ƒ))
         .map(BeeDebugRenderer.BeeInfo::getUuid)
         .collect(Collectors.toSet());
   }

   private Map<BlockPos, List<String>> getGhostHives() {
      Map<BlockPos, List<String>> â˜ƒ = Maps.newHashMap();

      for(BeeDebugRenderer.BeeInfo â˜ƒx : this.beeInfosPerEntity.values()) {
         if (â˜ƒx.hivePos != null && !this.hives.containsKey(â˜ƒx.hivePos)) {
            ((List)â˜ƒ.computeIfAbsent(â˜ƒx.hivePos, var0 -> Lists.newArrayList())).add(â˜ƒx.getName());
         }
      }

      return â˜ƒ;
   }

   private void updateLastLookedAtUuid() {
      DebugRenderer.getTargetedEntity(this.minecraft.getCameraEntity(), 8).ifPresent(var1 -> this.lastLookedAtUuid = var1.getUUID());
   }

   public static class BeeInfo {
      public final UUID uuid;
      public final int id;
      public final Position pos;
      @Nullable
      public final Path path;
      @Nullable
      public final BlockPos hivePos;
      @Nullable
      public final BlockPos flowerPos;
      public final int travelTicks;
      public final List<String> goals = Lists.newArrayList();
      public final Set<BlockPos> blacklistedHives = Sets.<BlockPos>newHashSet();

      public BeeInfo(UUID var1, int var2, Position var3, Path var4, BlockPos var5, BlockPos var6, int var7) {
         this.uuid = â˜ƒ;
         this.id = â˜ƒ;
         this.pos = â˜ƒ;
         this.path = â˜ƒ;
         this.hivePos = â˜ƒ;
         this.flowerPos = â˜ƒ;
         this.travelTicks = â˜ƒ;
      }

      public boolean hasHive(BlockPos var1) {
         return this.hivePos != null && this.hivePos.equals(â˜ƒ);
      }

      public UUID getUuid() {
         return this.uuid;
      }

      public String getName() {
         return DebugEntityNameGenerator.getEntityName(this.uuid);
      }

      public String toString() {
         return this.getName();
      }

      public boolean hasFlower() {
         return this.flowerPos != null;
      }
   }

   public static class HiveInfo {
      public final BlockPos pos;
      public final String hiveType;
      public final int occupantCount;
      public final int honeyLevel;
      public final boolean sedated;
      public final long lastSeen;

      public HiveInfo(BlockPos var1, String var2, int var3, int var4, boolean var5, long var6) {
         this.pos = â˜ƒ;
         this.hiveType = â˜ƒ;
         this.occupantCount = â˜ƒ;
         this.honeyLevel = â˜ƒ;
         this.sedated = â˜ƒ;
         this.lastSeen = â˜ƒ;
      }
   }
}
