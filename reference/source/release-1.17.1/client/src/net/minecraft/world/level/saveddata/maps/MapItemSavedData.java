package net.minecraft.world.level.saveddata.maps;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapItemSavedData extends SavedData {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int MAP_SIZE = 128;
   private static final int HALF_MAP_SIZE = 64;
   public static final int MAX_SCALE = 4;
   public static final int TRACKED_DECORATION_LIMIT = 256;
   public final int x;
   public final int z;
   public final ResourceKey<Level> dimension;
   private final boolean trackingPosition;
   private final boolean unlimitedTracking;
   public final byte scale;
   public byte[] colors = new byte[16384];
   public final boolean locked;
   private final List<MapItemSavedData.HoldingPlayer> carriedBy = Lists.<MapItemSavedData.HoldingPlayer>newArrayList();
   private final Map<Player, MapItemSavedData.HoldingPlayer> carriedByPlayers = Maps.<Player, MapItemSavedData.HoldingPlayer>newHashMap();
   private final Map<String, MapBanner> bannerMarkers = Maps.newHashMap();
   final Map<String, MapDecoration> decorations = Maps.newLinkedHashMap();
   private final Map<String, MapFrame> frameMarkers = Maps.newHashMap();
   private int trackedDecorationCount;

   private MapItemSavedData(int var1, int var2, byte var3, boolean var4, boolean var5, boolean var6, ResourceKey<Level> var7) {
      this.scale = â˜ƒ;
      this.x = â˜ƒ;
      this.z = â˜ƒ;
      this.dimension = â˜ƒ;
      this.trackingPosition = â˜ƒ;
      this.unlimitedTracking = â˜ƒ;
      this.locked = â˜ƒ;
      this.setDirty();
   }

   public static MapItemSavedData createFresh(double var0, double var2, byte var4, boolean var5, boolean var6, ResourceKey<Level> var7) {
      int â˜ƒ = 128 * (1 << â˜ƒ);
      int â˜ƒx = Mth.floor((â˜ƒ + 64.0) / (double)â˜ƒ);
      int â˜ƒxx = Mth.floor((â˜ƒ + 64.0) / (double)â˜ƒ);
      int â˜ƒxxx = â˜ƒx * â˜ƒ + â˜ƒ / 2 - 64;
      int â˜ƒxxxx = â˜ƒxx * â˜ƒ + â˜ƒ / 2 - 64;
      return new MapItemSavedData(â˜ƒxxx, â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ);
   }

   public static MapItemSavedData createForClient(byte var0, boolean var1, ResourceKey<Level> var2) {
      return new MapItemSavedData(0, 0, â˜ƒ, false, false, â˜ƒ, â˜ƒ);
   }

   public static MapItemSavedData load(CompoundTag var0) {
      ResourceKey<Level> â˜ƒ = (ResourceKey)DimensionType.parseLegacy(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ.get("dimension")))
         .resultOrPartial(LOGGER::error)
         .orElseThrow(() -> new IllegalArgumentException("Invalid map dimension: " + â˜ƒ.get("dimension")));
      int â˜ƒx = â˜ƒ.getInt("xCenter");
      int â˜ƒxx = â˜ƒ.getInt("zCenter");
      byte â˜ƒxxx = (byte)Mth.clamp(â˜ƒ.getByte("scale"), 0, 4);
      boolean â˜ƒxxxx = !â˜ƒ.contains("trackingPosition", 1) || â˜ƒ.getBoolean("trackingPosition");
      boolean â˜ƒxxxxx = â˜ƒ.getBoolean("unlimitedTracking");
      boolean â˜ƒxxxxxx = â˜ƒ.getBoolean("locked");
      MapItemSavedData â˜ƒxxxxxxx = new MapItemSavedData(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ);
      byte[] â˜ƒxxxxxxxx = â˜ƒ.getByteArray("colors");
      if (â˜ƒxxxxxxxx.length == 16384) {
         â˜ƒxxxxxxx.colors = â˜ƒxxxxxxxx;
      }

      ListTag â˜ƒ = â˜ƒ.getList("banners", 10);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         MapBanner â˜ƒxx = MapBanner.load(â˜ƒ.getCompound(â˜ƒx));
         â˜ƒxxxxxxx.bannerMarkers.put(â˜ƒxx.getId(), â˜ƒxx);
         â˜ƒxxxxxxx.addDecoration(
            â˜ƒxx.getDecoration(), null, â˜ƒxx.getId(), (double)â˜ƒxx.getPos().getX(), (double)â˜ƒxx.getPos().getZ(), 180.0, â˜ƒxx.getName()
         );
      }

      ListTag â˜ƒx = â˜ƒ.getList("frames", 10);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
         MapFrame â˜ƒxxx = MapFrame.load(â˜ƒx.getCompound(â˜ƒxx));
         â˜ƒxxxxxxx.frameMarkers.put(â˜ƒxxx.getId(), â˜ƒxxx);
         â˜ƒxxxxxxx.addDecoration(
            MapDecoration.Type.FRAME,
            null,
            "frame-" + â˜ƒxxx.getEntityId(),
            (double)â˜ƒxxx.getPos().getX(),
            (double)â˜ƒxxx.getPos().getZ(),
            (double)â˜ƒxxx.getRotation(),
            null
         );
      }

      return â˜ƒxxxxxxx;
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      ResourceLocation.CODEC
         .encodeStart(NbtOps.INSTANCE, this.dimension.location())
         .resultOrPartial(LOGGER::error)
         .ifPresent(var1x -> â˜ƒ.put("dimension", var1x));
      â˜ƒ.putInt("xCenter", this.x);
      â˜ƒ.putInt("zCenter", this.z);
      â˜ƒ.putByte("scale", this.scale);
      â˜ƒ.putByteArray("colors", this.colors);
      â˜ƒ.putBoolean("trackingPosition", this.trackingPosition);
      â˜ƒ.putBoolean("unlimitedTracking", this.unlimitedTracking);
      â˜ƒ.putBoolean("locked", this.locked);
      ListTag â˜ƒ = new ListTag();

      for(MapBanner â˜ƒx : this.bannerMarkers.values()) {
         â˜ƒ.add(â˜ƒx.save());
      }

      â˜ƒ.put("banners", â˜ƒ);
      ListTag â˜ƒx = new ListTag();

      for(MapFrame â˜ƒxx : this.frameMarkers.values()) {
         â˜ƒx.add(â˜ƒxx.save());
      }

      â˜ƒ.put("frames", â˜ƒx);
      return â˜ƒ;
   }

   public MapItemSavedData locked() {
      MapItemSavedData â˜ƒ = new MapItemSavedData(this.x, this.z, this.scale, this.trackingPosition, this.unlimitedTracking, true, this.dimension);
      â˜ƒ.bannerMarkers.putAll(this.bannerMarkers);
      â˜ƒ.decorations.putAll(this.decorations);
      â˜ƒ.trackedDecorationCount = this.trackedDecorationCount;
      System.arraycopy(this.colors, 0, â˜ƒ.colors, 0, this.colors.length);
      â˜ƒ.setDirty();
      return â˜ƒ;
   }

   public MapItemSavedData scaled(int var1) {
      return createFresh((double)this.x, (double)this.z, (byte)Mth.clamp(this.scale + â˜ƒ, 0, 4), this.trackingPosition, this.unlimitedTracking, this.dimension);
   }

   public void tickCarriedBy(Player var1, ItemStack var2) {
      if (!this.carriedByPlayers.containsKey(â˜ƒ)) {
         MapItemSavedData.HoldingPlayer â˜ƒ = new MapItemSavedData.HoldingPlayer(â˜ƒ);
         this.carriedByPlayers.put(â˜ƒ, â˜ƒ);
         this.carriedBy.add(â˜ƒ);
      }

      if (!â˜ƒ.getInventory().contains(â˜ƒ)) {
         this.removeDecoration(â˜ƒ.getName().getString());
      }

      for(int â˜ƒ = 0; â˜ƒ < this.carriedBy.size(); ++â˜ƒ) {
         MapItemSavedData.HoldingPlayer â˜ƒx = (MapItemSavedData.HoldingPlayer)this.carriedBy.get(â˜ƒ);
         String â˜ƒxx = â˜ƒx.player.getName().getString();
         if (!â˜ƒx.player.isRemoved() && (â˜ƒx.player.getInventory().contains(â˜ƒ) || â˜ƒ.isFramed())) {
            if (!â˜ƒ.isFramed() && â˜ƒx.player.level.dimension() == this.dimension && this.trackingPosition) {
               this.addDecoration(
                  MapDecoration.Type.PLAYER, â˜ƒx.player.level, â˜ƒxx, â˜ƒx.player.getX(), â˜ƒx.player.getZ(), (double)â˜ƒx.player.getYRot(), null
               );
            }
         } else {
            this.carriedByPlayers.remove(â˜ƒx.player);
            this.carriedBy.remove(â˜ƒx);
            this.removeDecoration(â˜ƒxx);
         }
      }

      if (â˜ƒ.isFramed() && this.trackingPosition) {
         ItemFrame â˜ƒ = â˜ƒ.getFrame();
         BlockPos â˜ƒx = â˜ƒ.getPos();
         MapFrame â˜ƒxx = (MapFrame)this.frameMarkers.get(MapFrame.frameId(â˜ƒx));
         if (â˜ƒxx != null && â˜ƒ.getId() != â˜ƒxx.getEntityId() && this.frameMarkers.containsKey(â˜ƒxx.getId())) {
            this.removeDecoration("frame-" + â˜ƒxx.getEntityId());
         }

         MapFrame â˜ƒ = new MapFrame(â˜ƒx, â˜ƒ.getDirection().get2DDataValue() * 90, â˜ƒ.getId());
         this.addDecoration(
            MapDecoration.Type.FRAME,
            â˜ƒ.level,
            "frame-" + â˜ƒ.getId(),
            (double)â˜ƒx.getX(),
            (double)â˜ƒx.getZ(),
            (double)(â˜ƒ.getDirection().get2DDataValue() * 90),
            null
         );
         this.frameMarkers.put(â˜ƒ.getId(), â˜ƒ);
      }

      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null && â˜ƒ.contains("Decorations", 9)) {
         ListTag â˜ƒx = â˜ƒ.getList("Decorations", 10);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
            if (!this.decorations.containsKey(â˜ƒxxx.getString("id"))) {
               this.addDecoration(
                  MapDecoration.Type.byIcon(â˜ƒxxx.getByte("type")),
                  â˜ƒ.level,
                  â˜ƒxxx.getString("id"),
                  â˜ƒxxx.getDouble("x"),
                  â˜ƒxxx.getDouble("z"),
                  â˜ƒxxx.getDouble("rot"),
                  null
               );
            }
         }
      }
   }

   private void removeDecoration(String var1) {
      MapDecoration â˜ƒ = (MapDecoration)this.decorations.remove(â˜ƒ);
      if (â˜ƒ != null && â˜ƒ.getType().shouldTrackCount()) {
         --this.trackedDecorationCount;
      }

      this.setDecorationsDirty();
   }

   public static void addTargetDecoration(ItemStack var0, BlockPos var1, String var2, MapDecoration.Type var3) {
      ListTag â˜ƒ;
      if (â˜ƒ.hasTag() && â˜ƒ.getTag().contains("Decorations", 9)) {
         â˜ƒ = â˜ƒ.getTag().getList("Decorations", 10);
      } else {
         â˜ƒ = new ListTag();
         â˜ƒ.addTagElement("Decorations", â˜ƒ);
      }

      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putByte("type", â˜ƒ.getIcon());
      â˜ƒ.putString("id", â˜ƒ);
      â˜ƒ.putDouble("x", (double)â˜ƒ.getX());
      â˜ƒ.putDouble("z", (double)â˜ƒ.getZ());
      â˜ƒ.putDouble("rot", 180.0);
      â˜ƒ.add(â˜ƒ);
      if (â˜ƒ.hasMapColor()) {
         CompoundTag â˜ƒx = â˜ƒ.getOrCreateTagElement("display");
         â˜ƒx.putInt("MapColor", â˜ƒ.getMapColor());
      }
   }

   private void addDecoration(
      MapDecoration.Type var1, @Nullable LevelAccessor var2, String var3, double var4, double var6, double var8, @Nullable Component var10
   ) {
      int â˜ƒx = 1 << this.scale;
      float â˜ƒxx = (float)(â˜ƒ - (double)this.x) / (float)â˜ƒx;
      float â˜ƒxxx = (float)(â˜ƒ - (double)this.z) / (float)â˜ƒx;
      byte â˜ƒxxxx = (byte)((int)((double)(â˜ƒxx * 2.0F) + 0.5));
      byte â˜ƒxxxxx = (byte)((int)((double)(â˜ƒxxx * 2.0F) + 0.5));
      int â˜ƒxxxxxx = 63;
      byte â˜ƒ;
      if (â˜ƒxx >= -63.0F && â˜ƒxxx >= -63.0F && â˜ƒxx <= 63.0F && â˜ƒxxx <= 63.0F) {
         â˜ƒ += â˜ƒ < 0.0 ? -8.0 : 8.0;
         â˜ƒ = (byte)((int)(â˜ƒ * 16.0 / 360.0));
         if (this.dimension == Level.NETHER && â˜ƒ != null) {
            int â˜ƒxxxxxxx = (int)(â˜ƒ.getLevelData().getDayTime() / 10L);
            â˜ƒ = (byte)(â˜ƒxxxxxxx * â˜ƒxxxxxxx * 34187121 + â˜ƒxxxxxxx * 121 >> 15 & 15);
         }
      } else {
         if (â˜ƒ != MapDecoration.Type.PLAYER) {
            this.removeDecoration(â˜ƒ);
            return;
         }

         int â˜ƒ = 320;
         if (Math.abs(â˜ƒxx) < 320.0F && Math.abs(â˜ƒxxx) < 320.0F) {
            â˜ƒ = MapDecoration.Type.PLAYER_OFF_MAP;
         } else {
            if (!this.unlimitedTracking) {
               this.removeDecoration(â˜ƒ);
               return;
            }

            â˜ƒ = MapDecoration.Type.PLAYER_OFF_LIMITS;
         }

         â˜ƒ = 0;
         if (â˜ƒxx <= -63.0F) {
            â˜ƒxxxx = -128;
         }

         if (â˜ƒxxx <= -63.0F) {
            â˜ƒxxxxx = -128;
         }

         if (â˜ƒxx >= 63.0F) {
            â˜ƒxxxx = 127;
         }

         if (â˜ƒxxx >= 63.0F) {
            â˜ƒxxxxx = 127;
         }
      }

      MapDecoration â˜ƒ = new MapDecoration(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ);
      MapDecoration â˜ƒx = (MapDecoration)this.decorations.put(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.equals(â˜ƒx)) {
         if (â˜ƒx != null && â˜ƒx.getType().shouldTrackCount()) {
            --this.trackedDecorationCount;
         }

         if (â˜ƒ.shouldTrackCount()) {
            ++this.trackedDecorationCount;
         }

         this.setDecorationsDirty();
      }
   }

   @Nullable
   public Packet<?> getUpdatePacket(int var1, Player var2) {
      MapItemSavedData.HoldingPlayer â˜ƒ = (MapItemSavedData.HoldingPlayer)this.carriedByPlayers.get(â˜ƒ);
      return â˜ƒ == null ? null : â˜ƒ.nextUpdatePacket(â˜ƒ);
   }

   private void setColorsDirty(int var1, int var2) {
      this.setDirty();

      for(MapItemSavedData.HoldingPlayer â˜ƒ : this.carriedBy) {
         â˜ƒ.markColorsDirty(â˜ƒ, â˜ƒ);
      }
   }

   private void setDecorationsDirty() {
      this.setDirty();
      this.carriedBy.forEach(MapItemSavedData.HoldingPlayer::markDecorationsDirty);
   }

   public MapItemSavedData.HoldingPlayer getHoldingPlayer(Player var1) {
      MapItemSavedData.HoldingPlayer â˜ƒ = (MapItemSavedData.HoldingPlayer)this.carriedByPlayers.get(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = new MapItemSavedData.HoldingPlayer(â˜ƒ);
         this.carriedByPlayers.put(â˜ƒ, â˜ƒ);
         this.carriedBy.add(â˜ƒ);
      }

      return â˜ƒ;
   }

   public boolean toggleBanner(LevelAccessor var1, BlockPos var2) {
      double â˜ƒ = (double)â˜ƒ.getX() + 0.5;
      double â˜ƒx = (double)â˜ƒ.getZ() + 0.5;
      int â˜ƒxx = 1 << this.scale;
      double â˜ƒxxx = (â˜ƒ - (double)this.x) / (double)â˜ƒxx;
      double â˜ƒxxxx = (â˜ƒx - (double)this.z) / (double)â˜ƒxx;
      int â˜ƒxxxxx = 63;
      if (â˜ƒxxx >= -63.0 && â˜ƒxxxx >= -63.0 && â˜ƒxxx <= 63.0 && â˜ƒxxxx <= 63.0) {
         MapBanner â˜ƒxxxxxx = MapBanner.fromWorld(â˜ƒ, â˜ƒ);
         if (â˜ƒxxxxxx == null) {
            return false;
         }

         if (this.bannerMarkers.remove(â˜ƒxxxxxx.getId(), â˜ƒxxxxxx)) {
            this.removeDecoration(â˜ƒxxxxxx.getId());
            return true;
         }

         if (!this.isTrackedCountOverLimit(256)) {
            this.bannerMarkers.put(â˜ƒxxxxxx.getId(), â˜ƒxxxxxx);
            this.addDecoration(â˜ƒxxxxxx.getDecoration(), â˜ƒ, â˜ƒxxxxxx.getId(), â˜ƒ, â˜ƒx, 180.0, â˜ƒxxxxxx.getName());
            return true;
         }
      }

      return false;
   }

   public void checkBanners(BlockGetter var1, int var2, int var3) {
      Iterator<MapBanner> â˜ƒ = this.bannerMarkers.values().iterator();

      while(â˜ƒ.hasNext()) {
         MapBanner â˜ƒx = (MapBanner)â˜ƒ.next();
         if (â˜ƒx.getPos().getX() == â˜ƒ && â˜ƒx.getPos().getZ() == â˜ƒ) {
            MapBanner â˜ƒxx = MapBanner.fromWorld(â˜ƒ, â˜ƒx.getPos());
            if (!â˜ƒx.equals(â˜ƒxx)) {
               â˜ƒ.remove();
               this.removeDecoration(â˜ƒx.getId());
            }
         }
      }
   }

   public Collection<MapBanner> getBanners() {
      return this.bannerMarkers.values();
   }

   public void removedFromFrame(BlockPos var1, int var2) {
      this.removeDecoration("frame-" + â˜ƒ);
      this.frameMarkers.remove(MapFrame.frameId(â˜ƒ));
   }

   public boolean updateColor(int var1, int var2, byte var3) {
      byte â˜ƒ = this.colors[â˜ƒ + â˜ƒ * 128];
      if (â˜ƒ != â˜ƒ) {
         this.setColor(â˜ƒ, â˜ƒ, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   public void setColor(int var1, int var2, byte var3) {
      this.colors[â˜ƒ + â˜ƒ * 128] = â˜ƒ;
      this.setColorsDirty(â˜ƒ, â˜ƒ);
   }

   public boolean isExplorationMap() {
      for(MapDecoration â˜ƒ : this.decorations.values()) {
         if (â˜ƒ.getType() == MapDecoration.Type.MANSION || â˜ƒ.getType() == MapDecoration.Type.MONUMENT) {
            return true;
         }
      }

      return false;
   }

   public void addClientSideDecorations(List<MapDecoration> var1) {
      this.decorations.clear();
      this.trackedDecorationCount = 0;

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         MapDecoration â˜ƒx = (MapDecoration)â˜ƒ.get(â˜ƒ);
         this.decorations.put("icon-" + â˜ƒ, â˜ƒx);
         if (â˜ƒx.getType().shouldTrackCount()) {
            ++this.trackedDecorationCount;
         }
      }
   }

   public Iterable<MapDecoration> getDecorations() {
      return this.decorations.values();
   }

   public boolean isTrackedCountOverLimit(int var1) {
      return this.trackedDecorationCount >= â˜ƒ;
   }

   public class HoldingPlayer {
      public final Player player;
      private boolean dirtyData = true;
      private int minDirtyX;
      private int minDirtyY;
      private int maxDirtyX = 127;
      private int maxDirtyY = 127;
      private boolean dirtyDecorations = true;
      private int tick;
      public int step;

      HoldingPlayer(Player var2) {
         this.player = â˜ƒ;
      }

      private MapItemSavedData.MapPatch createPatch() {
         int â˜ƒ = this.minDirtyX;
         int â˜ƒx = this.minDirtyY;
         int â˜ƒxx = this.maxDirtyX + 1 - this.minDirtyX;
         int â˜ƒxxx = this.maxDirtyY + 1 - this.minDirtyY;
         byte[] â˜ƒxxxx = new byte[â˜ƒxx * â˜ƒxxx];

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxx; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxx) {
               â˜ƒxxxx[â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxx] = MapItemSavedData.this.colors[â˜ƒ + â˜ƒxxxxx + (â˜ƒx + â˜ƒxxxxxx) * 128];
            }
         }

         return new MapItemSavedData.MapPatch(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }

      @Nullable
      Packet<?> nextUpdatePacket(int var1) {
         MapItemSavedData.MapPatch â˜ƒ;
         if (this.dirtyData) {
            this.dirtyData = false;
            â˜ƒ = this.createPatch();
         } else {
            â˜ƒ = null;
         }

         Collection<MapDecoration> â˜ƒ;
         if (this.dirtyDecorations && this.tick++ % 5 == 0) {
            this.dirtyDecorations = false;
            â˜ƒ = MapItemSavedData.this.decorations.values();
         } else {
            â˜ƒ = null;
         }

         return â˜ƒ == null && â˜ƒ == null ? null : new ClientboundMapItemDataPacket(â˜ƒ, MapItemSavedData.this.scale, MapItemSavedData.this.locked, â˜ƒ, â˜ƒ);
      }

      void markColorsDirty(int var1, int var2) {
         if (this.dirtyData) {
            this.minDirtyX = Math.min(this.minDirtyX, â˜ƒ);
            this.minDirtyY = Math.min(this.minDirtyY, â˜ƒ);
            this.maxDirtyX = Math.max(this.maxDirtyX, â˜ƒ);
            this.maxDirtyY = Math.max(this.maxDirtyY, â˜ƒ);
         } else {
            this.dirtyData = true;
            this.minDirtyX = â˜ƒ;
            this.minDirtyY = â˜ƒ;
            this.maxDirtyX = â˜ƒ;
            this.maxDirtyY = â˜ƒ;
         }
      }

      private void markDecorationsDirty() {
         this.dirtyDecorations = true;
      }
   }

   public static class MapPatch {
      public final int startX;
      public final int startY;
      public final int width;
      public final int height;
      public final byte[] mapColors;

      public MapPatch(int var1, int var2, int var3, int var4, byte[] var5) {
         this.startX = â˜ƒ;
         this.startY = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.mapColors = â˜ƒ;
      }

      public void applyToMap(MapItemSavedData var1) {
         for(int â˜ƒ = 0; â˜ƒ < this.width; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < this.height; ++â˜ƒx) {
               â˜ƒ.setColor(this.startX + â˜ƒ, this.startY + â˜ƒx, this.mapColors[â˜ƒ + â˜ƒx * this.width]);
            }
         }
      }
   }
}
