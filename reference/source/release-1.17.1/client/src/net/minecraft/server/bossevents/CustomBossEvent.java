package net.minecraft.server.bossevents;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;

public class CustomBossEvent extends ServerBossEvent {
   private final ResourceLocation id;
   private final Set<UUID> players = Sets.newHashSet();
   private int value;
   private int max = 100;

   public CustomBossEvent(ResourceLocation var1, Component var2) {
      super(â˜ƒ, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS);
      this.id = â˜ƒ;
      this.setProgress(0.0F);
   }

   public ResourceLocation getTextId() {
      return this.id;
   }

   @Override
   public void addPlayer(ServerPlayer var1) {
      super.addPlayer(â˜ƒ);
      this.players.add(â˜ƒ.getUUID());
   }

   public void addOfflinePlayer(UUID var1) {
      this.players.add(â˜ƒ);
   }

   @Override
   public void removePlayer(ServerPlayer var1) {
      super.removePlayer(â˜ƒ);
      this.players.remove(â˜ƒ.getUUID());
   }

   @Override
   public void removeAllPlayers() {
      super.removeAllPlayers();
      this.players.clear();
   }

   public int getValue() {
      return this.value;
   }

   public int getMax() {
      return this.max;
   }

   public void setValue(int var1) {
      this.value = â˜ƒ;
      this.setProgress(Mth.clamp((float)â˜ƒ / (float)this.max, 0.0F, 1.0F));
   }

   public void setMax(int var1) {
      this.max = â˜ƒ;
      this.setProgress(Mth.clamp((float)this.value / (float)â˜ƒ, 0.0F, 1.0F));
   }

   public final Component getDisplayName() {
      return ComponentUtils.wrapInSquareBrackets(this.getName())
         .withStyle(
            var1 -> var1.withColor(this.getColor().getFormatting())
                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent(this.getTextId().toString())))
                  .withInsertion(this.getTextId().toString())
         );
   }

   public boolean setPlayers(Collection<ServerPlayer> var1) {
      Set<UUID> â˜ƒ = Sets.newHashSet();
      Set<ServerPlayer> â˜ƒx = Sets.<ServerPlayer>newHashSet();

      for(UUID â˜ƒxx : this.players) {
         boolean â˜ƒxxx = false;

         for(ServerPlayer â˜ƒxxxx : â˜ƒ) {
            if (â˜ƒxxxx.getUUID().equals(â˜ƒxx)) {
               â˜ƒxxx = true;
               break;
            }
         }

         if (!â˜ƒxxx) {
            â˜ƒ.add(â˜ƒxx);
         }
      }

      for(ServerPlayer â˜ƒxx : â˜ƒ) {
         boolean â˜ƒxxx = false;

         for(UUID â˜ƒxxxx : this.players) {
            if (â˜ƒxx.getUUID().equals(â˜ƒxxxx)) {
               â˜ƒxxx = true;
               break;
            }
         }

         if (!â˜ƒxxx) {
            â˜ƒx.add(â˜ƒxx);
         }
      }

      for(UUID â˜ƒxx : â˜ƒ) {
         for(ServerPlayer â˜ƒxxx : this.getPlayers()) {
            if (â˜ƒxxx.getUUID().equals(â˜ƒxx)) {
               this.removePlayer(â˜ƒxxx);
               break;
            }
         }

         this.players.remove(â˜ƒxx);
      }

      for(ServerPlayer â˜ƒxx : â˜ƒx) {
         this.addPlayer(â˜ƒxx);
      }

      return !â˜ƒ.isEmpty() || !â˜ƒx.isEmpty();
   }

   public CompoundTag save() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("Name", Component.Serializer.toJson(this.name));
      â˜ƒ.putBoolean("Visible", this.isVisible());
      â˜ƒ.putInt("Value", this.value);
      â˜ƒ.putInt("Max", this.max);
      â˜ƒ.putString("Color", this.getColor().getName());
      â˜ƒ.putString("Overlay", this.getOverlay().getName());
      â˜ƒ.putBoolean("DarkenScreen", this.shouldDarkenScreen());
      â˜ƒ.putBoolean("PlayBossMusic", this.shouldPlayBossMusic());
      â˜ƒ.putBoolean("CreateWorldFog", this.shouldCreateWorldFog());
      ListTag â˜ƒx = new ListTag();

      for(UUID â˜ƒxx : this.players) {
         â˜ƒx.add(NbtUtils.createUUID(â˜ƒxx));
      }

      â˜ƒ.put("Players", â˜ƒx);
      return â˜ƒ;
   }

   public static CustomBossEvent load(CompoundTag var0, ResourceLocation var1) {
      CustomBossEvent â˜ƒ = new CustomBossEvent(â˜ƒ, Component.Serializer.fromJson(â˜ƒ.getString("Name")));
      â˜ƒ.setVisible(â˜ƒ.getBoolean("Visible"));
      â˜ƒ.setValue(â˜ƒ.getInt("Value"));
      â˜ƒ.setMax(â˜ƒ.getInt("Max"));
      â˜ƒ.setColor(BossEvent.BossBarColor.byName(â˜ƒ.getString("Color")));
      â˜ƒ.setOverlay(BossEvent.BossBarOverlay.byName(â˜ƒ.getString("Overlay")));
      â˜ƒ.setDarkenScreen(â˜ƒ.getBoolean("DarkenScreen"));
      â˜ƒ.setPlayBossMusic(â˜ƒ.getBoolean("PlayBossMusic"));
      â˜ƒ.setCreateWorldFog(â˜ƒ.getBoolean("CreateWorldFog"));
      ListTag â˜ƒx = â˜ƒ.getList("Players", 11);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
         â˜ƒ.addOfflinePlayer(NbtUtils.loadUUID(â˜ƒx.get(â˜ƒxx)));
      }

      return â˜ƒ;
   }

   public void onPlayerConnect(ServerPlayer var1) {
      if (this.players.contains(â˜ƒ.getUUID())) {
         this.addPlayer(â˜ƒ);
      }
   }

   public void onPlayerDisconnect(ServerPlayer var1) {
      super.removePlayer(â˜ƒ);
   }
}
