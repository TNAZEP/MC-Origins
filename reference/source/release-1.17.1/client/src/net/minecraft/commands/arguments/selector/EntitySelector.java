package net.minecraft.commands.arguments.selector;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntitySelector {
   public static final int INFINITE = Integer.MAX_VALUE;
   private static final EntityTypeTest<Entity, ?> ANY_TYPE = new EntityTypeTest<Entity, Entity>() {
      public Entity tryCast(Entity var1) {
         return â˜ƒ;
      }

      @Override
      public Class<? extends Entity> getBaseClass() {
         return Entity.class;
      }
   };
   private final int maxResults;
   private final boolean includesEntities;
   private final boolean worldLimited;
   private final Predicate<Entity> predicate;
   private final MinMaxBounds.Doubles range;
   private final Function<Vec3, Vec3> position;
   @Nullable
   private final AABB aabb;
   private final BiConsumer<Vec3, List<? extends Entity>> order;
   private final boolean currentEntity;
   @Nullable
   private final String playerName;
   @Nullable
   private final UUID entityUUID;
   private EntityTypeTest<Entity, ?> type;
   private final boolean usesSelector;

   public EntitySelector(
      int var1,
      boolean var2,
      boolean var3,
      Predicate<Entity> var4,
      MinMaxBounds.Doubles var5,
      Function<Vec3, Vec3> var6,
      @Nullable AABB var7,
      BiConsumer<Vec3, List<? extends Entity>> var8,
      boolean var9,
      @Nullable String var10,
      @Nullable UUID var11,
      @Nullable EntityType<?> var12,
      boolean var13
   ) {
      this.maxResults = â˜ƒ;
      this.includesEntities = â˜ƒ;
      this.worldLimited = â˜ƒ;
      this.predicate = â˜ƒ;
      this.range = â˜ƒ;
      this.position = â˜ƒ;
      this.aabb = â˜ƒ;
      this.order = â˜ƒ;
      this.currentEntity = â˜ƒ;
      this.playerName = â˜ƒ;
      this.entityUUID = â˜ƒ;
      this.type = (EntityTypeTest<Entity, ?>)(â˜ƒ == null ? ANY_TYPE : â˜ƒ);
      this.usesSelector = â˜ƒ;
   }

   public int getMaxResults() {
      return this.maxResults;
   }

   public boolean includesEntities() {
      return this.includesEntities;
   }

   public boolean isSelfSelector() {
      return this.currentEntity;
   }

   public boolean isWorldLimited() {
      return this.worldLimited;
   }

   public boolean usesSelector() {
      return this.usesSelector;
   }

   private void checkPermissions(CommandSourceStack var1) throws CommandSyntaxException {
      if (this.usesSelector && !â˜ƒ.hasPermission(2)) {
         throw EntityArgument.ERROR_SELECTORS_NOT_ALLOWED.create();
      }
   }

   public Entity findSingleEntity(CommandSourceStack var1) throws CommandSyntaxException {
      this.checkPermissions(â˜ƒ);
      List<? extends Entity> â˜ƒ = this.findEntities(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         throw EntityArgument.NO_ENTITIES_FOUND.create();
      } else if (â˜ƒ.size() > 1) {
         throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
      } else {
         return (Entity)â˜ƒ.get(0);
      }
   }

   public List<? extends Entity> findEntities(CommandSourceStack var1) throws CommandSyntaxException {
      this.checkPermissions(â˜ƒ);
      if (!this.includesEntities) {
         return this.findPlayers(â˜ƒ);
      } else if (this.playerName != null) {
         ServerPlayer â˜ƒ = â˜ƒ.getServer().getPlayerList().getPlayerByName(this.playerName);
         return (List<? extends Entity>)(â˜ƒ == null ? Collections.emptyList() : Lists.newArrayList(â˜ƒ));
      } else if (this.entityUUID != null) {
         for(ServerLevel â˜ƒ : â˜ƒ.getServer().getAllLevels()) {
            Entity â˜ƒx = â˜ƒ.getEntity(this.entityUUID);
            if (â˜ƒx != null) {
               return Lists.newArrayList(â˜ƒx);
            }
         }

         return Collections.emptyList();
      } else {
         Vec3 â˜ƒ = (Vec3)this.position.apply(â˜ƒ.getPosition());
         Predicate<Entity> â˜ƒx = this.getPredicate(â˜ƒ);
         if (this.currentEntity) {
            return (List<? extends Entity>)(â˜ƒ.getEntity() != null && â˜ƒx.test(â˜ƒ.getEntity())
               ? Lists.newArrayList(â˜ƒ.getEntity())
               : Collections.emptyList());
         } else {
            List<Entity> â˜ƒ = Lists.<Entity>newArrayList();
            if (this.isWorldLimited()) {
               this.addEntities(â˜ƒ, â˜ƒ.getLevel(), â˜ƒ, â˜ƒx);
            } else {
               for(ServerLevel â˜ƒ : â˜ƒ.getServer().getAllLevels()) {
                  this.addEntities(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
               }
            }

            return this.sortAndLimit(â˜ƒ, â˜ƒ);
         }
      }
   }

   private void addEntities(List<Entity> var1, ServerLevel var2, Vec3 var3, Predicate<Entity> var4) {
      if (this.aabb != null) {
         â˜ƒ.addAll(â˜ƒ.getEntities(this.type, this.aabb.move(â˜ƒ), â˜ƒ));
      } else {
         â˜ƒ.addAll(â˜ƒ.getEntities(this.type, â˜ƒ));
      }
   }

   public ServerPlayer findSinglePlayer(CommandSourceStack var1) throws CommandSyntaxException {
      this.checkPermissions(â˜ƒ);
      List<ServerPlayer> â˜ƒ = this.findPlayers(â˜ƒ);
      if (â˜ƒ.size() != 1) {
         throw EntityArgument.NO_PLAYERS_FOUND.create();
      } else {
         return (ServerPlayer)â˜ƒ.get(0);
      }
   }

   public List<ServerPlayer> findPlayers(CommandSourceStack var1) throws CommandSyntaxException {
      this.checkPermissions(â˜ƒ);
      if (this.playerName != null) {
         ServerPlayer â˜ƒ = â˜ƒ.getServer().getPlayerList().getPlayerByName(this.playerName);
         return (List<ServerPlayer>)(â˜ƒ == null ? Collections.emptyList() : Lists.<ServerPlayer>newArrayList(â˜ƒ));
      } else if (this.entityUUID != null) {
         ServerPlayer â˜ƒ = â˜ƒ.getServer().getPlayerList().getPlayer(this.entityUUID);
         return (List<ServerPlayer>)(â˜ƒ == null ? Collections.emptyList() : Lists.<ServerPlayer>newArrayList(â˜ƒ));
      } else {
         Vec3 â˜ƒ = (Vec3)this.position.apply(â˜ƒ.getPosition());
         Predicate<Entity> â˜ƒx = this.getPredicate(â˜ƒ);
         if (this.currentEntity) {
            if (â˜ƒ.getEntity() instanceof ServerPlayer â˜ƒxx && â˜ƒx.test(â˜ƒxx)) {
               return Lists.<ServerPlayer>newArrayList(â˜ƒxx);
            }

            return Collections.emptyList();
         } else {
            List<ServerPlayer> â˜ƒ;
            if (this.isWorldLimited()) {
               â˜ƒ = â˜ƒ.getLevel().getPlayers(â˜ƒx);
            } else {
               â˜ƒ = Lists.<ServerPlayer>newArrayList();

               for(ServerPlayer â˜ƒ : â˜ƒ.getServer().getPlayerList().getPlayers()) {
                  if (â˜ƒx.test(â˜ƒ)) {
                     â˜ƒ.add(â˜ƒ);
                  }
               }
            }

            return this.sortAndLimit(â˜ƒ, â˜ƒ);
         }
      }
   }

   private Predicate<Entity> getPredicate(Vec3 var1) {
      Predicate<Entity> â˜ƒ = this.predicate;
      if (this.aabb != null) {
         AABB â˜ƒx = this.aabb.move(â˜ƒ);
         â˜ƒ = â˜ƒ.and(var1x -> â˜ƒ.intersects(var1x.getBoundingBox()));
      }

      if (!this.range.isAny()) {
         â˜ƒ = â˜ƒ.and(var2x -> this.range.matchesSqr(var2x.distanceToSqr(â˜ƒ)));
      }

      return â˜ƒ;
   }

   private <T extends Entity> List<T> sortAndLimit(Vec3 var1, List<T> var2) {
      if (â˜ƒ.size() > 1) {
         this.order.accept(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ.subList(0, Math.min(this.maxResults, â˜ƒ.size()));
   }

   public static Component joinNames(List<? extends Entity> var0) {
      return ComponentUtils.formatList(â˜ƒ, Entity::getDisplayName);
   }
}
