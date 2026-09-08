package net.minecraft.world.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRules {
   public static final int DEFAULT_RANDOM_TICK_SPEED = 3;
   static final Logger LOGGER = LogManager.getLogger();
   private static final Map<GameRules.Key<?>, GameRules.Type<?>> GAME_RULE_TYPES = Maps.newTreeMap(Comparator.comparing(var0 -> var0.id));
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DOFIRETICK = register(
      "doFireTick", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_MOBGRIEFING = register(
      "mobGriefing", GameRules.Category.MOBS, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_KEEPINVENTORY = register(
      "keepInventory", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DOMOBSPAWNING = register(
      "doMobSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DOMOBLOOT = register(
      "doMobLoot", GameRules.Category.DROPS, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DOBLOCKDROPS = register(
      "doTileDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DOENTITYDROPS = register(
      "doEntityDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_COMMANDBLOCKOUTPUT = register(
      "commandBlockOutput", GameRules.Category.CHAT, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_NATURAL_REGENERATION = register(
      "naturalRegeneration", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DAYLIGHT = register(
      "doDaylightCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_LOGADMINCOMMANDS = register(
      "logAdminCommands", GameRules.Category.CHAT, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_SHOWDEATHMESSAGES = register(
      "showDeathMessages", GameRules.Category.CHAT, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.IntegerValue> RULE_RANDOMTICKING = register(
      "randomTickSpeed", GameRules.Category.UPDATES, GameRules.IntegerValue.create(3)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_SENDCOMMANDFEEDBACK = register(
      "sendCommandFeedback", GameRules.Category.CHAT, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_REDUCEDDEBUGINFO = register(
      "reducedDebugInfo", GameRules.Category.MISC, GameRules.BooleanValue.create(false, (var0, var1) -> {
         byte â˜ƒ = (byte)(var1.get() ? 22 : 23);
   
         for(ServerPlayer â˜ƒx : var0.getPlayerList().getPlayers()) {
            â˜ƒx.connection.send(new ClientboundEntityEventPacket(â˜ƒx, â˜ƒ));
         }
      })
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_SPECTATORSGENERATECHUNKS = register(
      "spectatorsGenerateChunks", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.IntegerValue> RULE_SPAWN_RADIUS = register(
      "spawnRadius", GameRules.Category.PLAYER, GameRules.IntegerValue.create(10)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DISABLE_ELYTRA_MOVEMENT_CHECK = register(
      "disableElytraMovementCheck", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false)
   );
   public static final GameRules.Key<GameRules.IntegerValue> RULE_MAX_ENTITY_CRAMMING = register(
      "maxEntityCramming", GameRules.Category.MOBS, GameRules.IntegerValue.create(24)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_WEATHER_CYCLE = register(
      "doWeatherCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_LIMITED_CRAFTING = register(
      "doLimitedCrafting", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false)
   );
   public static final GameRules.Key<GameRules.IntegerValue> RULE_MAX_COMMAND_CHAIN_LENGTH = register(
      "maxCommandChainLength", GameRules.Category.MISC, GameRules.IntegerValue.create(65536)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_ANNOUNCE_ADVANCEMENTS = register(
      "announceAdvancements", GameRules.Category.CHAT, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DISABLE_RAIDS = register(
      "disableRaids", GameRules.Category.MOBS, GameRules.BooleanValue.create(false)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DOINSOMNIA = register(
      "doInsomnia", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DO_IMMEDIATE_RESPAWN = register(
      "doImmediateRespawn", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false, (var0, var1) -> {
         for(ServerPlayer â˜ƒ : var0.getPlayerList().getPlayers()) {
            â˜ƒ.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.IMMEDIATE_RESPAWN, var1.get() ? 1.0F : 0.0F));
         }
      })
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DROWNING_DAMAGE = register(
      "drowningDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_FALL_DAMAGE = register(
      "fallDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_FIRE_DAMAGE = register(
      "fireDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_FREEZE_DAMAGE = register(
      "freezeDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DO_PATROL_SPAWNING = register(
      "doPatrolSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_DO_TRADER_SPAWNING = register(
      "doTraderSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_FORGIVE_DEAD_PLAYERS = register(
      "forgiveDeadPlayers", GameRules.Category.MOBS, GameRules.BooleanValue.create(true)
   );
   public static final GameRules.Key<GameRules.BooleanValue> RULE_UNIVERSAL_ANGER = register(
      "universalAnger", GameRules.Category.MOBS, GameRules.BooleanValue.create(false)
   );
   public static final GameRules.Key<GameRules.IntegerValue> RULE_PLAYERS_SLEEPING_PERCENTAGE = register(
      "playersSleepingPercentage", GameRules.Category.PLAYER, GameRules.IntegerValue.create(100)
   );
   private final Map<GameRules.Key<?>, GameRules.Value<?>> rules;

   private static <T extends GameRules.Value<T>> GameRules.Key<T> register(String var0, GameRules.Category var1, GameRules.Type<T> var2) {
      GameRules.Key<T> â˜ƒ = new GameRules.Key<>(â˜ƒ, â˜ƒ);
      GameRules.Type<?> â˜ƒx = (GameRules.Type)GAME_RULE_TYPES.put(â˜ƒ, â˜ƒ);
      if (â˜ƒx != null) {
         throw new IllegalStateException("Duplicate game rule registration for " + â˜ƒ);
      } else {
         return â˜ƒ;
      }
   }

   public GameRules(DynamicLike<?> var1) {
      this();
      this.loadFromTag(â˜ƒ);
   }

   public GameRules() {
      this.rules = (Map)GAME_RULE_TYPES.entrySet()
         .stream()
         .collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ((GameRules.Type)var0.getValue()).createRule()));
   }

   private GameRules(Map<GameRules.Key<?>, GameRules.Value<?>> var1) {
      this.rules = â˜ƒ;
   }

   public <T extends GameRules.Value<T>> T getRule(GameRules.Key<T> var1) {
      return (T)this.rules.get(â˜ƒ);
   }

   public CompoundTag createTag() {
      CompoundTag â˜ƒ = new CompoundTag();
      this.rules.forEach((var1x, var2) -> â˜ƒ.putString(var1x.id, var2.serialize()));
      return â˜ƒ;
   }

   private void loadFromTag(DynamicLike<?> var1) {
      this.rules.forEach((var1x, var2) -> â˜ƒ.get(var1x.id).asString().result().ifPresent(var2::deserialize));
   }

   public GameRules copy() {
      return new GameRules(
         (Map<GameRules.Key<?>, GameRules.Value<?>>)this.rules
            .entrySet()
            .stream()
            .collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ((GameRules.Value)var0.getValue()).copy()))
      );
   }

   public static void visitGameRuleTypes(GameRules.GameRuleTypeVisitor var0) {
      GAME_RULE_TYPES.forEach((var1, var2) -> callVisitorCap(â˜ƒ, var1, var2));
   }

   private static <T extends GameRules.Value<T>> void callVisitorCap(GameRules.GameRuleTypeVisitor var0, GameRules.Key<?> var1, GameRules.Type<?> var2) {
      â˜ƒ.visit(â˜ƒ, â˜ƒ);
      â˜ƒ.callVisitor(â˜ƒ, â˜ƒ);
   }

   public void assignFrom(GameRules var1, @Nullable MinecraftServer var2) {
      â˜ƒ.rules.keySet().forEach(var3 -> this.assignCap(var3, â˜ƒ, â˜ƒ));
   }

   private <T extends GameRules.Value<T>> void assignCap(GameRules.Key<T> var1, GameRules var2, @Nullable MinecraftServer var3) {
      T â˜ƒ = â˜ƒ.getRule(â˜ƒ);
      this.<T>getRule(â˜ƒ).setFrom(â˜ƒ, â˜ƒ);
   }

   public boolean getBoolean(GameRules.Key<GameRules.BooleanValue> var1) {
      return this.getRule(â˜ƒ).get();
   }

   public int getInt(GameRules.Key<GameRules.IntegerValue> var1) {
      return this.getRule(â˜ƒ).get();
   }

   public static class BooleanValue extends GameRules.Value<GameRules.BooleanValue> {
      private boolean value;

      static GameRules.Type<GameRules.BooleanValue> create(boolean var0, BiConsumer<MinecraftServer, GameRules.BooleanValue> var1) {
         return new GameRules.Type<>(BoolArgumentType::bool, var1x -> new GameRules.BooleanValue(var1x, â˜ƒ), â˜ƒ, GameRules.GameRuleTypeVisitor::visitBoolean);
      }

      static GameRules.Type<GameRules.BooleanValue> create(boolean var0) {
         return create(â˜ƒ, (var0x, var1) -> {
         });
      }

      public BooleanValue(GameRules.Type<GameRules.BooleanValue> var1, boolean var2) {
         super(â˜ƒ);
         this.value = â˜ƒ;
      }

      @Override
      protected void updateFromArgument(CommandContext<CommandSourceStack> var1, String var2) {
         this.value = BoolArgumentType.getBool(â˜ƒ, â˜ƒ);
      }

      public boolean get() {
         return this.value;
      }

      public void set(boolean var1, @Nullable MinecraftServer var2) {
         this.value = â˜ƒ;
         this.onChanged(â˜ƒ);
      }

      @Override
      public String serialize() {
         return Boolean.toString(this.value);
      }

      @Override
      protected void deserialize(String var1) {
         this.value = Boolean.parseBoolean(â˜ƒ);
      }

      @Override
      public int getCommandResult() {
         return this.value ? 1 : 0;
      }

      protected GameRules.BooleanValue getSelf() {
         return this;
      }

      protected GameRules.BooleanValue copy() {
         return new GameRules.BooleanValue(this.type, this.value);
      }

      public void setFrom(GameRules.BooleanValue var1, @Nullable MinecraftServer var2) {
         this.value = â˜ƒ.value;
         this.onChanged(â˜ƒ);
      }
   }

   public static enum Category {
      PLAYER("gamerule.category.player"),
      MOBS("gamerule.category.mobs"),
      SPAWNING("gamerule.category.spawning"),
      DROPS("gamerule.category.drops"),
      UPDATES("gamerule.category.updates"),
      CHAT("gamerule.category.chat"),
      MISC("gamerule.category.misc");

      private final String descriptionId;

      private Category(String var3) {
         this.descriptionId = â˜ƒ;
      }

      public String getDescriptionId() {
         return this.descriptionId;
      }
   }

   public interface GameRuleTypeVisitor {
      default <T extends GameRules.Value<T>> void visit(GameRules.Key<T> var1, GameRules.Type<T> var2) {
      }

      default void visitBoolean(GameRules.Key<GameRules.BooleanValue> var1, GameRules.Type<GameRules.BooleanValue> var2) {
      }

      default void visitInteger(GameRules.Key<GameRules.IntegerValue> var1, GameRules.Type<GameRules.IntegerValue> var2) {
      }
   }

   public static class IntegerValue extends GameRules.Value<GameRules.IntegerValue> {
      private int value;

      private static GameRules.Type<GameRules.IntegerValue> create(int var0, BiConsumer<MinecraftServer, GameRules.IntegerValue> var1) {
         return new GameRules.Type<>(
            IntegerArgumentType::integer, var1x -> new GameRules.IntegerValue(var1x, â˜ƒ), â˜ƒ, GameRules.GameRuleTypeVisitor::visitInteger
         );
      }

      static GameRules.Type<GameRules.IntegerValue> create(int var0) {
         return create(â˜ƒ, (var0x, var1) -> {
         });
      }

      public IntegerValue(GameRules.Type<GameRules.IntegerValue> var1, int var2) {
         super(â˜ƒ);
         this.value = â˜ƒ;
      }

      @Override
      protected void updateFromArgument(CommandContext<CommandSourceStack> var1, String var2) {
         this.value = IntegerArgumentType.getInteger(â˜ƒ, â˜ƒ);
      }

      public int get() {
         return this.value;
      }

      public void set(int var1, @Nullable MinecraftServer var2) {
         this.value = â˜ƒ;
         this.onChanged(â˜ƒ);
      }

      @Override
      public String serialize() {
         return Integer.toString(this.value);
      }

      @Override
      protected void deserialize(String var1) {
         this.value = safeParse(â˜ƒ);
      }

      public boolean tryDeserialize(String var1) {
         try {
            this.value = Integer.parseInt(â˜ƒ);
            return true;
         } catch (NumberFormatException var3) {
            return false;
         }
      }

      private static int safeParse(String var0) {
         if (!â˜ƒ.isEmpty()) {
            try {
               return Integer.parseInt(â˜ƒ);
            } catch (NumberFormatException var2) {
               GameRules.LOGGER.warn("Failed to parse integer {}", â˜ƒ);
            }
         }

         return 0;
      }

      @Override
      public int getCommandResult() {
         return this.value;
      }

      protected GameRules.IntegerValue getSelf() {
         return this;
      }

      protected GameRules.IntegerValue copy() {
         return new GameRules.IntegerValue(this.type, this.value);
      }

      public void setFrom(GameRules.IntegerValue var1, @Nullable MinecraftServer var2) {
         this.value = â˜ƒ.value;
         this.onChanged(â˜ƒ);
      }
   }

   public static final class Key<T extends GameRules.Value<T>> {
      final String id;
      private final GameRules.Category category;

      public Key(String var1, GameRules.Category var2) {
         this.id = â˜ƒ;
         this.category = â˜ƒ;
      }

      public String toString() {
         return this.id;
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else {
            return â˜ƒ instanceof GameRules.Key && ((GameRules.Key)â˜ƒ).id.equals(this.id);
         }
      }

      public int hashCode() {
         return this.id.hashCode();
      }

      public String getId() {
         return this.id;
      }

      public String getDescriptionId() {
         return "gamerule." + this.id;
      }

      public GameRules.Category getCategory() {
         return this.category;
      }
   }

   public static class Type<T extends GameRules.Value<T>> {
      private final Supplier<ArgumentType<?>> argument;
      private final Function<GameRules.Type<T>, T> constructor;
      final BiConsumer<MinecraftServer, T> callback;
      private final GameRules.VisitorCaller<T> visitorCaller;

      Type(Supplier<ArgumentType<?>> var1, Function<GameRules.Type<T>, T> var2, BiConsumer<MinecraftServer, T> var3, GameRules.VisitorCaller<T> var4) {
         this.argument = â˜ƒ;
         this.constructor = â˜ƒ;
         this.callback = â˜ƒ;
         this.visitorCaller = â˜ƒ;
      }

      public RequiredArgumentBuilder<CommandSourceStack, ?> createArgument(String var1) {
         return Commands.argument(â˜ƒ, (ArgumentType<T>)this.argument.get());
      }

      public T createRule() {
         return (T)this.constructor.apply(this);
      }

      public void callVisitor(GameRules.GameRuleTypeVisitor var1, GameRules.Key<T> var2) {
         this.visitorCaller.call(â˜ƒ, â˜ƒ, this);
      }
   }

   public abstract static class Value<T extends GameRules.Value<T>> {
      protected final GameRules.Type<T> type;

      public Value(GameRules.Type<T> var1) {
         this.type = â˜ƒ;
      }

      protected abstract void updateFromArgument(CommandContext<CommandSourceStack> var1, String var2);

      public void setFromArgument(CommandContext<CommandSourceStack> var1, String var2) {
         this.updateFromArgument(â˜ƒ, â˜ƒ);
         this.onChanged(â˜ƒ.getSource().getServer());
      }

      protected void onChanged(@Nullable MinecraftServer var1) {
         if (â˜ƒ != null) {
            this.type.callback.accept(â˜ƒ, this.getSelf());
         }
      }

      protected abstract void deserialize(String var1);

      public abstract String serialize();

      public String toString() {
         return this.serialize();
      }

      public abstract int getCommandResult();

      protected abstract T getSelf();

      protected abstract T copy();

      public abstract void setFrom(T var1, @Nullable MinecraftServer var2);
   }

   interface VisitorCaller<T extends GameRules.Value<T>> {
      void call(GameRules.GameRuleTypeVisitor var1, GameRules.Key<T> var2, GameRules.Type<T> var3);
   }
}
