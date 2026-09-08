package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerAdvancements {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int VISIBILITY_DEPTH = 2;
   private static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(AdvancementProgress.class, new AdvancementProgress.Serializer())
      .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
      .setPrettyPrinting()
      .create();
   private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> TYPE_TOKEN = new TypeToken<Map<ResourceLocation, AdvancementProgress>>() {
   };
   private final DataFixer dataFixer;
   private final PlayerList playerList;
   private final File file;
   private final Map<Advancement, AdvancementProgress> advancements = Maps.<Advancement, AdvancementProgress>newLinkedHashMap();
   private final Set<Advancement> visible = Sets.<Advancement>newLinkedHashSet();
   private final Set<Advancement> visibilityChanged = Sets.<Advancement>newLinkedHashSet();
   private final Set<Advancement> progressChanged = Sets.<Advancement>newLinkedHashSet();
   private ServerPlayer player;
   @Nullable
   private Advancement lastSelectedTab;
   private boolean isFirstPacket = true;

   public PlayerAdvancements(DataFixer var1, PlayerList var2, ServerAdvancementManager var3, File var4, ServerPlayer var5) {
      this.dataFixer = â˜ƒ;
      this.playerList = â˜ƒ;
      this.file = â˜ƒ;
      this.player = â˜ƒ;
      this.load(â˜ƒ);
   }

   public void setPlayer(ServerPlayer var1) {
      this.player = â˜ƒ;
   }

   public void stopListening() {
      for(CriterionTrigger<?> â˜ƒ : CriteriaTriggers.all()) {
         â˜ƒ.removePlayerListeners(this);
      }
   }

   public void reload(ServerAdvancementManager var1) {
      this.stopListening();
      this.advancements.clear();
      this.visible.clear();
      this.visibilityChanged.clear();
      this.progressChanged.clear();
      this.isFirstPacket = true;
      this.lastSelectedTab = null;
      this.load(â˜ƒ);
   }

   private void registerListeners(ServerAdvancementManager var1) {
      for(Advancement â˜ƒ : â˜ƒ.getAllAdvancements()) {
         this.registerListeners(â˜ƒ);
      }
   }

   private void ensureAllVisible() {
      List<Advancement> â˜ƒ = Lists.<Advancement>newArrayList();

      for(Entry<Advancement, AdvancementProgress> â˜ƒx : this.advancements.entrySet()) {
         if (((AdvancementProgress)â˜ƒx.getValue()).isDone()) {
            â˜ƒ.add((Advancement)â˜ƒx.getKey());
            this.progressChanged.add((Advancement)â˜ƒx.getKey());
         }
      }

      for(Advancement â˜ƒx : â˜ƒ) {
         this.ensureVisibility(â˜ƒx);
      }
   }

   private void checkForAutomaticTriggers(ServerAdvancementManager var1) {
      for(Advancement â˜ƒ : â˜ƒ.getAllAdvancements()) {
         if (â˜ƒ.getCriteria().isEmpty()) {
            this.award(â˜ƒ, "");
            â˜ƒ.getRewards().grant(this.player);
         }
      }
   }

   private void load(ServerAdvancementManager var1) {
      if (this.file.isFile()) {
         try {
            JsonReader â˜ƒ = new JsonReader(new StringReader(Files.toString(this.file, StandardCharsets.UTF_8)));

            try {
               â˜ƒ.setLenient(false);
               Dynamic<JsonElement> â˜ƒx = new Dynamic<>(JsonOps.INSTANCE, Streams.parse(â˜ƒ));
               if (!â˜ƒx.get("DataVersion").asNumber().result().isPresent()) {
                  â˜ƒx = â˜ƒx.set("DataVersion", â˜ƒx.createInt(1343));
               }

               â˜ƒx = this.dataFixer
                  .update(DataFixTypes.ADVANCEMENTS.getType(), â˜ƒx, â˜ƒx.get("DataVersion").asInt(0), SharedConstants.getCurrentVersion().getWorldVersion());
               â˜ƒx = â˜ƒx.remove("DataVersion");
               Map<ResourceLocation, AdvancementProgress> â˜ƒx = (Map)GSON.getAdapter(TYPE_TOKEN).fromJsonTree(â˜ƒx.getValue());
               if (â˜ƒx == null) {
                  throw new JsonParseException("Found null for advancements");
               }

               Stream<Entry<ResourceLocation, AdvancementProgress>> â˜ƒx = â˜ƒx.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));

               for(Entry<ResourceLocation, AdvancementProgress> â˜ƒxx : (List)â˜ƒx.collect(Collectors.toList())) {
                  Advancement â˜ƒxxx = â˜ƒ.getAdvancement((ResourceLocation)â˜ƒxx.getKey());
                  if (â˜ƒxxx == null) {
                     LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", â˜ƒxx.getKey(), this.file);
                  } else {
                     this.startProgress(â˜ƒxxx, (AdvancementProgress)â˜ƒxx.getValue());
                  }
               }
            } catch (Throwable var10) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }

               throw var10;
            }

            â˜ƒ.close();
         } catch (JsonParseException var11) {
            LOGGER.error("Couldn't parse player advancements in {}", this.file, var11);
         } catch (IOException var12) {
            LOGGER.error("Couldn't access player advancements in {}", this.file, var12);
         }
      }

      this.checkForAutomaticTriggers(â˜ƒ);
      this.ensureAllVisible();
      this.registerListeners(â˜ƒ);
   }

   public void save() {
      Map<ResourceLocation, AdvancementProgress> â˜ƒ = Maps.<ResourceLocation, AdvancementProgress>newHashMap();

      for(Entry<Advancement, AdvancementProgress> â˜ƒx : this.advancements.entrySet()) {
         AdvancementProgress â˜ƒxx = (AdvancementProgress)â˜ƒx.getValue();
         if (â˜ƒxx.hasProgress()) {
            â˜ƒ.put(((Advancement)â˜ƒx.getKey()).getId(), â˜ƒxx);
         }
      }

      if (this.file.getParentFile() != null) {
         this.file.getParentFile().mkdirs();
      }

      JsonElement â˜ƒx = GSON.toJsonTree(â˜ƒ);
      â˜ƒx.getAsJsonObject().addProperty("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());

      try {
         OutputStream â˜ƒxx = new FileOutputStream(this.file);

         try {
            Writer â˜ƒxxx = new OutputStreamWriter(â˜ƒxx, Charsets.UTF_8.newEncoder());

            try {
               GSON.toJson(â˜ƒx, â˜ƒxxx);
            } catch (Throwable var9) {
               try {
                  â˜ƒxxx.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }

               throw var9;
            }

            â˜ƒxxx.close();
         } catch (Throwable var10) {
            try {
               â˜ƒxx.close();
            } catch (Throwable var7) {
               var10.addSuppressed(var7);
            }

            throw var10;
         }

         â˜ƒxx.close();
      } catch (IOException var11) {
         LOGGER.error("Couldn't save player advancements to {}", this.file, var11);
      }
   }

   public boolean award(Advancement var1, String var2) {
      boolean â˜ƒ = false;
      AdvancementProgress â˜ƒx = this.getOrStartProgress(â˜ƒ);
      boolean â˜ƒxx = â˜ƒx.isDone();
      if (â˜ƒx.grantProgress(â˜ƒ)) {
         this.unregisterListeners(â˜ƒ);
         this.progressChanged.add(â˜ƒ);
         â˜ƒ = true;
         if (!â˜ƒxx && â˜ƒx.isDone()) {
            â˜ƒ.getRewards().grant(this.player);
            if (â˜ƒ.getDisplay() != null
               && â˜ƒ.getDisplay().shouldAnnounceChat()
               && this.player.level.getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS)) {
               this.playerList
                  .broadcastMessage(
                     new TranslatableComponent(
                        "chat.type.advancement." + â˜ƒ.getDisplay().getFrame().getName(), this.player.getDisplayName(), â˜ƒ.getChatComponent()
                     ),
                     ChatType.SYSTEM,
                     Util.NIL_UUID
                  );
            }
         }
      }

      if (â˜ƒx.isDone()) {
         this.ensureVisibility(â˜ƒ);
      }

      return â˜ƒ;
   }

   public boolean revoke(Advancement var1, String var2) {
      boolean â˜ƒ = false;
      AdvancementProgress â˜ƒx = this.getOrStartProgress(â˜ƒ);
      if (â˜ƒx.revokeProgress(â˜ƒ)) {
         this.registerListeners(â˜ƒ);
         this.progressChanged.add(â˜ƒ);
         â˜ƒ = true;
      }

      if (!â˜ƒx.hasProgress()) {
         this.ensureVisibility(â˜ƒ);
      }

      return â˜ƒ;
   }

   private void registerListeners(Advancement var1) {
      AdvancementProgress â˜ƒ = this.getOrStartProgress(â˜ƒ);
      if (!â˜ƒ.isDone()) {
         for(Entry<String, Criterion> â˜ƒx : â˜ƒ.getCriteria().entrySet()) {
            CriterionProgress â˜ƒxx = â˜ƒ.getCriterion((String)â˜ƒx.getKey());
            if (â˜ƒxx != null && !â˜ƒxx.isDone()) {
               CriterionTriggerInstance â˜ƒxxx = ((Criterion)â˜ƒx.getValue()).getTrigger();
               if (â˜ƒxxx != null) {
                  CriterionTrigger<CriterionTriggerInstance> â˜ƒxxxx = CriteriaTriggers.getCriterion(â˜ƒxxx.getCriterion());
                  if (â˜ƒxxxx != null) {
                     â˜ƒxxxx.addPlayerListener(this, new CriterionTrigger.Listener<>(â˜ƒxxx, â˜ƒ, (String)â˜ƒx.getKey()));
                  }
               }
            }
         }
      }
   }

   private void unregisterListeners(Advancement var1) {
      AdvancementProgress â˜ƒ = this.getOrStartProgress(â˜ƒ);

      for(Entry<String, Criterion> â˜ƒx : â˜ƒ.getCriteria().entrySet()) {
         CriterionProgress â˜ƒxx = â˜ƒ.getCriterion((String)â˜ƒx.getKey());
         if (â˜ƒxx != null && (â˜ƒxx.isDone() || â˜ƒ.isDone())) {
            CriterionTriggerInstance â˜ƒxxx = ((Criterion)â˜ƒx.getValue()).getTrigger();
            if (â˜ƒxxx != null) {
               CriterionTrigger<CriterionTriggerInstance> â˜ƒxxxx = CriteriaTriggers.getCriterion(â˜ƒxxx.getCriterion());
               if (â˜ƒxxxx != null) {
                  â˜ƒxxxx.removePlayerListener(this, new CriterionTrigger.Listener<>(â˜ƒxxx, â˜ƒ, (String)â˜ƒx.getKey()));
               }
            }
         }
      }
   }

   public void flushDirty(ServerPlayer var1) {
      if (this.isFirstPacket || !this.visibilityChanged.isEmpty() || !this.progressChanged.isEmpty()) {
         Map<ResourceLocation, AdvancementProgress> â˜ƒ = Maps.<ResourceLocation, AdvancementProgress>newHashMap();
         Set<Advancement> â˜ƒx = Sets.<Advancement>newLinkedHashSet();
         Set<ResourceLocation> â˜ƒxx = Sets.<ResourceLocation>newLinkedHashSet();

         for(Advancement â˜ƒxxx : this.progressChanged) {
            if (this.visible.contains(â˜ƒxxx)) {
               â˜ƒ.put(â˜ƒxxx.getId(), (AdvancementProgress)this.advancements.get(â˜ƒxxx));
            }
         }

         for(Advancement â˜ƒxxx : this.visibilityChanged) {
            if (this.visible.contains(â˜ƒxxx)) {
               â˜ƒx.add(â˜ƒxxx);
            } else {
               â˜ƒxx.add(â˜ƒxxx.getId());
            }
         }

         if (this.isFirstPacket || !â˜ƒ.isEmpty() || !â˜ƒx.isEmpty() || !â˜ƒxx.isEmpty()) {
            â˜ƒ.connection.send(new ClientboundUpdateAdvancementsPacket(this.isFirstPacket, â˜ƒx, â˜ƒxx, â˜ƒ));
            this.visibilityChanged.clear();
            this.progressChanged.clear();
         }
      }

      this.isFirstPacket = false;
   }

   public void setSelectedTab(@Nullable Advancement var1) {
      Advancement â˜ƒ = this.lastSelectedTab;
      if (â˜ƒ != null && â˜ƒ.getParent() == null && â˜ƒ.getDisplay() != null) {
         this.lastSelectedTab = â˜ƒ;
      } else {
         this.lastSelectedTab = null;
      }

      if (â˜ƒ != this.lastSelectedTab) {
         this.player.connection.send(new ClientboundSelectAdvancementsTabPacket(this.lastSelectedTab == null ? null : this.lastSelectedTab.getId()));
      }
   }

   public AdvancementProgress getOrStartProgress(Advancement var1) {
      AdvancementProgress â˜ƒ = (AdvancementProgress)this.advancements.get(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = new AdvancementProgress();
         this.startProgress(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   private void startProgress(Advancement var1, AdvancementProgress var2) {
      â˜ƒ.update(â˜ƒ.getCriteria(), â˜ƒ.getRequirements());
      this.advancements.put(â˜ƒ, â˜ƒ);
   }

   private void ensureVisibility(Advancement var1) {
      boolean â˜ƒ = this.shouldBeVisible(â˜ƒ);
      boolean â˜ƒx = this.visible.contains(â˜ƒ);
      if (â˜ƒ && !â˜ƒx) {
         this.visible.add(â˜ƒ);
         this.visibilityChanged.add(â˜ƒ);
         if (this.advancements.containsKey(â˜ƒ)) {
            this.progressChanged.add(â˜ƒ);
         }
      } else if (!â˜ƒ && â˜ƒx) {
         this.visible.remove(â˜ƒ);
         this.visibilityChanged.add(â˜ƒ);
      }

      if (â˜ƒ != â˜ƒx && â˜ƒ.getParent() != null) {
         this.ensureVisibility(â˜ƒ.getParent());
      }

      for(Advancement â˜ƒ : â˜ƒ.getChildren()) {
         this.ensureVisibility(â˜ƒ);
      }
   }

   private boolean shouldBeVisible(Advancement var1) {
      for(int â˜ƒ = 0; â˜ƒ != null && â˜ƒ <= 2; ++â˜ƒ) {
         if (â˜ƒ == 0 && this.hasCompletedChildrenOrSelf(â˜ƒ)) {
            return true;
         }

         if (â˜ƒ.getDisplay() == null) {
            return false;
         }

         AdvancementProgress â˜ƒx = this.getOrStartProgress(â˜ƒ);
         if (â˜ƒx.isDone()) {
            return true;
         }

         if (â˜ƒ.getDisplay().isHidden()) {
            return false;
         }

         â˜ƒ = â˜ƒ.getParent();
      }

      return false;
   }

   private boolean hasCompletedChildrenOrSelf(Advancement var1) {
      AdvancementProgress â˜ƒ = this.getOrStartProgress(â˜ƒ);
      if (â˜ƒ.isDone()) {
         return true;
      } else {
         for(Advancement â˜ƒ : â˜ƒ.getChildren()) {
            if (this.hasCompletedChildrenOrSelf(â˜ƒ)) {
               return true;
            }
         }

         return false;
      }
   }
}
