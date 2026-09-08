package net.minecraft.commands.arguments.selector.options;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public class EntitySelectorOptions {
   private static final Map<String, EntitySelectorOptions.Option> OPTIONS = Maps.newHashMap();
   public static final DynamicCommandExceptionType ERROR_UNKNOWN_OPTION = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.entity.options.unknown", var0)
   );
   public static final DynamicCommandExceptionType ERROR_INAPPLICABLE_OPTION = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.entity.options.inapplicable", var0)
   );
   public static final SimpleCommandExceptionType ERROR_RANGE_NEGATIVE = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.options.distance.negative")
   );
   public static final SimpleCommandExceptionType ERROR_LEVEL_NEGATIVE = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.options.level.negative")
   );
   public static final SimpleCommandExceptionType ERROR_LIMIT_TOO_SMALL = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.options.limit.toosmall")
   );
   public static final DynamicCommandExceptionType ERROR_SORT_UNKNOWN = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.entity.options.sort.irreversible", var0)
   );
   public static final DynamicCommandExceptionType ERROR_GAME_MODE_INVALID = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.entity.options.mode.invalid", var0)
   );
   public static final DynamicCommandExceptionType ERROR_ENTITY_TYPE_INVALID = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.entity.options.type.invalid", var0)
   );

   private static void register(String var0, EntitySelectorOptions.Modifier var1, Predicate<EntitySelectorParser> var2, Component var3) {
      OPTIONS.put(â˜ƒ, new EntitySelectorOptions.Option(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static void bootStrap() {
      if (OPTIONS.isEmpty()) {
         register("name", var0 -> {
            int â˜ƒ = var0.getReader().getCursor();
            boolean â˜ƒx = var0.shouldInvertValue();
            String â˜ƒxx = var0.getReader().readString();
            if (var0.hasNameNotEquals() && !â˜ƒx) {
               var0.getReader().setCursor(â˜ƒ);
               throw ERROR_INAPPLICABLE_OPTION.createWithContext(var0.getReader(), "name");
            } else {
               if (â˜ƒx) {
                  var0.setHasNameNotEquals(true);
               } else {
                  var0.setHasNameEquals(true);
               }

               var0.addPredicate(var2x -> var2x.getName().getString().equals(â˜ƒ) != â˜ƒ);
            }
         }, var0 -> !var0.hasNameEquals(), new TranslatableComponent("argument.entity.options.name.description"));
         register("distance", var0 -> {
            int â˜ƒ = var0.getReader().getCursor();
            MinMaxBounds.Doubles â˜ƒx = MinMaxBounds.Doubles.fromReader(var0.getReader());
            if ((â˜ƒx.getMin() == null || !(â˜ƒx.getMin() < 0.0)) && (â˜ƒx.getMax() == null || !(â˜ƒx.getMax() < 0.0))) {
               var0.setDistance(â˜ƒx);
               var0.setWorldLimited();
            } else {
               var0.getReader().setCursor(â˜ƒ);
               throw ERROR_RANGE_NEGATIVE.createWithContext(var0.getReader());
            }
         }, var0 -> var0.getDistance().isAny(), new TranslatableComponent("argument.entity.options.distance.description"));
         register("level", var0 -> {
            int â˜ƒ = var0.getReader().getCursor();
            MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromReader(var0.getReader());
            if ((â˜ƒx.getMin() == null || â˜ƒx.getMin() >= 0) && (â˜ƒx.getMax() == null || â˜ƒx.getMax() >= 0)) {
               var0.setLevel(â˜ƒx);
               var0.setIncludesEntities(false);
            } else {
               var0.getReader().setCursor(â˜ƒ);
               throw ERROR_LEVEL_NEGATIVE.createWithContext(var0.getReader());
            }
         }, var0 -> var0.getLevel().isAny(), new TranslatableComponent("argument.entity.options.level.description"));
         register("x", var0 -> {
            var0.setWorldLimited();
            var0.setX(var0.getReader().readDouble());
         }, var0 -> var0.getX() == null, new TranslatableComponent("argument.entity.options.x.description"));
         register("y", var0 -> {
            var0.setWorldLimited();
            var0.setY(var0.getReader().readDouble());
         }, var0 -> var0.getY() == null, new TranslatableComponent("argument.entity.options.y.description"));
         register("z", var0 -> {
            var0.setWorldLimited();
            var0.setZ(var0.getReader().readDouble());
         }, var0 -> var0.getZ() == null, new TranslatableComponent("argument.entity.options.z.description"));
         register("dx", var0 -> {
            var0.setWorldLimited();
            var0.setDeltaX(var0.getReader().readDouble());
         }, var0 -> var0.getDeltaX() == null, new TranslatableComponent("argument.entity.options.dx.description"));
         register("dy", var0 -> {
            var0.setWorldLimited();
            var0.setDeltaY(var0.getReader().readDouble());
         }, var0 -> var0.getDeltaY() == null, new TranslatableComponent("argument.entity.options.dy.description"));
         register("dz", var0 -> {
            var0.setWorldLimited();
            var0.setDeltaZ(var0.getReader().readDouble());
         }, var0 -> var0.getDeltaZ() == null, new TranslatableComponent("argument.entity.options.dz.description"));
         register(
            "x_rotation",
            var0 -> var0.setRotX(WrappedMinMaxBounds.fromReader(var0.getReader(), true, Mth::wrapDegrees)),
            var0 -> var0.getRotX() == WrappedMinMaxBounds.ANY,
            new TranslatableComponent("argument.entity.options.x_rotation.description")
         );
         register(
            "y_rotation",
            var0 -> var0.setRotY(WrappedMinMaxBounds.fromReader(var0.getReader(), true, Mth::wrapDegrees)),
            var0 -> var0.getRotY() == WrappedMinMaxBounds.ANY,
            new TranslatableComponent("argument.entity.options.y_rotation.description")
         );
         register("limit", var0 -> {
            int â˜ƒ = var0.getReader().getCursor();
            int â˜ƒx = var0.getReader().readInt();
            if (â˜ƒx < 1) {
               var0.getReader().setCursor(â˜ƒ);
               throw ERROR_LIMIT_TOO_SMALL.createWithContext(var0.getReader());
            } else {
               var0.setMaxResults(â˜ƒx);
               var0.setLimited(true);
            }
         }, var0 -> !var0.isCurrentEntity() && !var0.isLimited(), new TranslatableComponent("argument.entity.options.limit.description"));
         register("sort", var0 -> {
            int â˜ƒx = var0.getReader().getCursor();
            String â˜ƒxx = var0.getReader().readUnquotedString();
            var0.setSuggestions((var0x, var1x) -> SharedSuggestionProvider.suggest(Arrays.asList("nearest", "furthest", "random", "arbitrary"), var0x));
            BiConsumer<Vec3, List<? extends Entity>> â˜ƒ;
            switch(â˜ƒxx) {
               case "nearest":
                  â˜ƒ = EntitySelectorParser.ORDER_NEAREST;
                  break;
               case "furthest":
                  â˜ƒ = EntitySelectorParser.ORDER_FURTHEST;
                  break;
               case "random":
                  â˜ƒ = EntitySelectorParser.ORDER_RANDOM;
                  break;
               case "arbitrary":
                  â˜ƒ = EntitySelectorParser.ORDER_ARBITRARY;
                  break;
               default:
                  var0.getReader().setCursor(â˜ƒx);
                  throw ERROR_SORT_UNKNOWN.createWithContext(var0.getReader(), â˜ƒxx);
            }

            var0.setOrder(â˜ƒ);
            var0.setSorted(true);
         }, var0 -> !var0.isCurrentEntity() && !var0.isSorted(), new TranslatableComponent("argument.entity.options.sort.description"));
         register("gamemode", var0 -> {
            var0.setSuggestions((var1x, var2x) -> {
               String â˜ƒ = var1x.getRemaining().toLowerCase(Locale.ROOT);
               boolean â˜ƒx = !var0.hasGamemodeNotEquals();
               boolean â˜ƒxx = true;
               if (!â˜ƒ.isEmpty()) {
                  if (â˜ƒ.charAt(0) == '!') {
                     â˜ƒx = false;
                     â˜ƒ = â˜ƒ.substring(1);
                  } else {
                     â˜ƒxx = false;
                  }
               }

               for(GameType â˜ƒ : GameType.values()) {
                  if (â˜ƒ.getName().toLowerCase(Locale.ROOT).startsWith(â˜ƒ)) {
                     if (â˜ƒxx) {
                        var1x.suggest("!" + â˜ƒ.getName());
                     }

                     if (â˜ƒx) {
                        var1x.suggest(â˜ƒ.getName());
                     }
                  }
               }

               return var1x.buildFuture();
            });
            int â˜ƒ = var0.getReader().getCursor();
            boolean â˜ƒx = var0.shouldInvertValue();
            if (var0.hasGamemodeNotEquals() && !â˜ƒx) {
               var0.getReader().setCursor(â˜ƒ);
               throw ERROR_INAPPLICABLE_OPTION.createWithContext(var0.getReader(), "gamemode");
            } else {
               String â˜ƒ = var0.getReader().readUnquotedString();
               GameType â˜ƒx = GameType.byName(â˜ƒ, null);
               if (â˜ƒx == null) {
                  var0.getReader().setCursor(â˜ƒ);
                  throw ERROR_GAME_MODE_INVALID.createWithContext(var0.getReader(), â˜ƒ);
               } else {
                  var0.setIncludesEntities(false);
                  var0.addPredicate(var2x -> {
                     if (!(var2x instanceof ServerPlayer)) {
                        return false;
                     } else {
                        GameType â˜ƒ = ((ServerPlayer)var2x).gameMode.getGameModeForPlayer();
                        return â˜ƒ ? â˜ƒ != â˜ƒ : â˜ƒ == â˜ƒ;
                     }
                  });
                  if (â˜ƒx) {
                     var0.setHasGamemodeNotEquals(true);
                  } else {
                     var0.setHasGamemodeEquals(true);
                  }
               }
            }
         }, var0 -> !var0.hasGamemodeEquals(), new TranslatableComponent("argument.entity.options.gamemode.description"));
         register("team", var0 -> {
            boolean â˜ƒ = var0.shouldInvertValue();
            String â˜ƒx = var0.getReader().readUnquotedString();
            var0.addPredicate(var2x -> {
               if (!(var2x instanceof LivingEntity)) {
                  return false;
               } else {
                  Team â˜ƒ = var2x.getTeam();
                  String â˜ƒx = â˜ƒ == null ? "" : â˜ƒ.getName();
                  return â˜ƒx.equals(â˜ƒ) != â˜ƒ;
               }
            });
            if (â˜ƒ) {
               var0.setHasTeamNotEquals(true);
            } else {
               var0.setHasTeamEquals(true);
            }
         }, var0 -> !var0.hasTeamEquals(), new TranslatableComponent("argument.entity.options.team.description"));
         register(
            "type",
            var0 -> {
               var0.setSuggestions((var1x, var2x) -> {
                  SharedSuggestionProvider.suggestResource(Registry.ENTITY_TYPE.keySet(), var1x, String.valueOf('!'));
                  SharedSuggestionProvider.suggestResource(EntityTypeTags.getAllTags().getAvailableTags(), var1x, "!#");
                  if (!var0.isTypeLimitedInversely()) {
                     SharedSuggestionProvider.suggestResource(Registry.ENTITY_TYPE.keySet(), var1x);
                     SharedSuggestionProvider.suggestResource(EntityTypeTags.getAllTags().getAvailableTags(), var1x, String.valueOf('#'));
                  }
   
                  return var1x.buildFuture();
               });
               int â˜ƒ = var0.getReader().getCursor();
               boolean â˜ƒx = var0.shouldInvertValue();
               if (var0.isTypeLimitedInversely() && !â˜ƒx) {
                  var0.getReader().setCursor(â˜ƒ);
                  throw ERROR_INAPPLICABLE_OPTION.createWithContext(var0.getReader(), "type");
               } else {
                  if (â˜ƒx) {
                     var0.setTypeLimitedInversely();
                  }
   
                  if (var0.isTag()) {
                     ResourceLocation â˜ƒ = ResourceLocation.read(var0.getReader());
                     var0.addPredicate(
                        var2x -> var2x.getType().is(var2x.getServer().getTags().getOrEmpty(Registry.ENTITY_TYPE_REGISTRY).getTagOrEmpty(â˜ƒ)) != â˜ƒ
                     );
                  } else {
                     ResourceLocation â˜ƒ = ResourceLocation.read(var0.getReader());
                     EntityType<?> â˜ƒx = (EntityType)Registry.ENTITY_TYPE.getOptional(â˜ƒ).orElseThrow(() -> {
                        var0.getReader().setCursor(â˜ƒ);
                        return ERROR_ENTITY_TYPE_INVALID.createWithContext(var0.getReader(), â˜ƒ.toString());
                     });
                     if (Objects.equals(EntityType.PLAYER, â˜ƒx) && !â˜ƒx) {
                        var0.setIncludesEntities(false);
                     }
   
                     var0.addPredicate(var2x -> Objects.equals(â˜ƒ, var2x.getType()) != â˜ƒ);
                     if (!â˜ƒx) {
                        var0.limitToType(â˜ƒx);
                     }
                  }
               }
            },
            var0 -> !var0.isTypeLimited(),
            new TranslatableComponent("argument.entity.options.type.description")
         );
         register("tag", var0 -> {
            boolean â˜ƒ = var0.shouldInvertValue();
            String â˜ƒx = var0.getReader().readUnquotedString();
            var0.addPredicate(var2x -> {
               if ("".equals(â˜ƒ)) {
                  return var2x.getTags().isEmpty() != â˜ƒ;
               } else {
                  return var2x.getTags().contains(â˜ƒ) != â˜ƒ;
               }
            });
         }, var0 -> true, new TranslatableComponent("argument.entity.options.tag.description"));
         register("nbt", var0 -> {
            boolean â˜ƒ = var0.shouldInvertValue();
            CompoundTag â˜ƒx = new TagParser(var0.getReader()).readStruct();
            var0.addPredicate(var2x -> {
               CompoundTag â˜ƒ = var2x.saveWithoutId(new CompoundTag());
               if (var2x instanceof ServerPlayer) {
                  ItemStack â˜ƒx = ((ServerPlayer)var2x).getInventory().getSelected();
                  if (!â˜ƒx.isEmpty()) {
                     â˜ƒ.put("SelectedItem", â˜ƒx.save(new CompoundTag()));
                  }
               }

               return NbtUtils.compareNbt(â˜ƒ, â˜ƒ, true) != â˜ƒ;
            });
         }, var0 -> true, new TranslatableComponent("argument.entity.options.nbt.description"));
         register("scores", var0 -> {
            StringReader â˜ƒ = var0.getReader();
            Map<String, MinMaxBounds.Ints> â˜ƒx = Maps.newHashMap();
            â˜ƒ.expect('{');
            â˜ƒ.skipWhitespace();

            while(â˜ƒ.canRead() && â˜ƒ.peek() != '}') {
               â˜ƒ.skipWhitespace();
               String â˜ƒxx = â˜ƒ.readUnquotedString();
               â˜ƒ.skipWhitespace();
               â˜ƒ.expect('=');
               â˜ƒ.skipWhitespace();
               MinMaxBounds.Ints â˜ƒxxx = MinMaxBounds.Ints.fromReader(â˜ƒ);
               â˜ƒx.put(â˜ƒxx, â˜ƒxxx);
               â˜ƒ.skipWhitespace();
               if (â˜ƒ.canRead() && â˜ƒ.peek() == ',') {
                  â˜ƒ.skip();
               }
            }

            â˜ƒ.expect('}');
            if (!â˜ƒx.isEmpty()) {
               var0.addPredicate(var1x -> {
                  Scoreboard â˜ƒ = var1x.getServer().getScoreboard();
                  String â˜ƒx = var1x.getScoreboardName();

                  for(Entry<String, MinMaxBounds.Ints> â˜ƒxx : â˜ƒ.entrySet()) {
                     Objective â˜ƒxxx = â˜ƒ.getObjective((String)â˜ƒxx.getKey());
                     if (â˜ƒxxx == null) {
                        return false;
                     }

                     if (!â˜ƒ.hasPlayerScore(â˜ƒx, â˜ƒxxx)) {
                        return false;
                     }

                     Score â˜ƒxxx = â˜ƒ.getOrCreatePlayerScore(â˜ƒx, â˜ƒxxx);
                     int â˜ƒxxxx = â˜ƒxxx.getScore();
                     if (!((MinMaxBounds.Ints)â˜ƒxx.getValue()).matches(â˜ƒxxxx)) {
                        return false;
                     }
                  }

                  return true;
               });
            }

            var0.setHasScores(true);
         }, var0 -> !var0.hasScores(), new TranslatableComponent("argument.entity.options.scores.description"));
         register("advancements", var0 -> {
            StringReader â˜ƒ = var0.getReader();
            Map<ResourceLocation, Predicate<AdvancementProgress>> â˜ƒx = Maps.newHashMap();
            â˜ƒ.expect('{');
            â˜ƒ.skipWhitespace();

            while(â˜ƒ.canRead() && â˜ƒ.peek() != '}') {
               â˜ƒ.skipWhitespace();
               ResourceLocation â˜ƒxx = ResourceLocation.read(â˜ƒ);
               â˜ƒ.skipWhitespace();
               â˜ƒ.expect('=');
               â˜ƒ.skipWhitespace();
               if (â˜ƒ.canRead() && â˜ƒ.peek() == '{') {
                  Map<String, Predicate<CriterionProgress>> â˜ƒxxx = Maps.newHashMap();
                  â˜ƒ.skipWhitespace();
                  â˜ƒ.expect('{');
                  â˜ƒ.skipWhitespace();

                  while(â˜ƒ.canRead() && â˜ƒ.peek() != '}') {
                     â˜ƒ.skipWhitespace();
                     String â˜ƒxxxx = â˜ƒ.readUnquotedString();
                     â˜ƒ.skipWhitespace();
                     â˜ƒ.expect('=');
                     â˜ƒ.skipWhitespace();
                     boolean â˜ƒxxxxx = â˜ƒ.readBoolean();
                     â˜ƒxxx.put(â˜ƒxxxx, (Predicate)var1x -> var1x.isDone() == â˜ƒ);
                     â˜ƒ.skipWhitespace();
                     if (â˜ƒ.canRead() && â˜ƒ.peek() == ',') {
                        â˜ƒ.skip();
                     }
                  }

                  â˜ƒ.skipWhitespace();
                  â˜ƒ.expect('}');
                  â˜ƒ.skipWhitespace();
                  â˜ƒx.put(â˜ƒxx, (Predicate)var1x -> {
                     for(Entry<String, Predicate<CriterionProgress>> â˜ƒ : â˜ƒ.entrySet()) {
                        CriterionProgress â˜ƒx = var1x.getCriterion((String)â˜ƒ.getKey());
                        if (â˜ƒx == null || !((Predicate)â˜ƒ.getValue()).test(â˜ƒx)) {
                           return false;
                        }
                     }

                     return true;
                  });
               } else {
                  boolean â˜ƒxx = â˜ƒ.readBoolean();
                  â˜ƒx.put(â˜ƒxx, (Predicate)var1x -> var1x.isDone() == â˜ƒ);
               }

               â˜ƒ.skipWhitespace();
               if (â˜ƒ.canRead() && â˜ƒ.peek() == ',') {
                  â˜ƒ.skip();
               }
            }

            â˜ƒ.expect('}');
            if (!â˜ƒx.isEmpty()) {
               var0.addPredicate(var1x -> {
                  if (!(var1x instanceof ServerPlayer)) {
                     return false;
                  } else {
                     ServerPlayer â˜ƒ = (ServerPlayer)var1x;
                     PlayerAdvancements â˜ƒx = â˜ƒ.getAdvancements();
                     ServerAdvancementManager â˜ƒxx = â˜ƒ.getServer().getAdvancements();

                     for(Entry<ResourceLocation, Predicate<AdvancementProgress>> â˜ƒxxx : â˜ƒ.entrySet()) {
                        Advancement â˜ƒxxxx = â˜ƒxx.getAdvancement((ResourceLocation)â˜ƒxxx.getKey());
                        if (â˜ƒxxxx == null || !((Predicate)â˜ƒxxx.getValue()).test(â˜ƒx.getOrStartProgress(â˜ƒxxxx))) {
                           return false;
                        }
                     }

                     return true;
                  }
               });
               var0.setIncludesEntities(false);
            }

            var0.setHasAdvancements(true);
         }, var0 -> !var0.hasAdvancements(), new TranslatableComponent("argument.entity.options.advancements.description"));
         register(
            "predicate",
            var0 -> {
               boolean â˜ƒ = var0.shouldInvertValue();
               ResourceLocation â˜ƒx = ResourceLocation.read(var0.getReader());
               var0.addPredicate(
                  var2x -> {
                     if (!(var2x.level instanceof ServerLevel)) {
                        return false;
                     } else {
                        ServerLevel â˜ƒ = (ServerLevel)var2x.level;
                        LootItemCondition â˜ƒx = â˜ƒ.getServer().getPredicateManager().get(â˜ƒ);
                        if (â˜ƒx == null) {
                           return false;
                        } else {
                           LootContext â˜ƒ = new LootContext.Builder(â˜ƒ)
                              .withParameter(LootContextParams.THIS_ENTITY, var2x)
                              .withParameter(LootContextParams.ORIGIN, var2x.position())
                              .create(LootContextParamSets.SELECTOR);
                           return â˜ƒ ^ â˜ƒx.test(â˜ƒ);
                        }
                     }
                  }
               );
            },
            var0 -> true,
            new TranslatableComponent("argument.entity.options.predicate.description")
         );
      }
   }

   public static EntitySelectorOptions.Modifier get(EntitySelectorParser var0, String var1, int var2) throws CommandSyntaxException {
      EntitySelectorOptions.Option â˜ƒ = (EntitySelectorOptions.Option)OPTIONS.get(â˜ƒ);
      if (â˜ƒ != null) {
         if (â˜ƒ.predicate.test(â˜ƒ)) {
            return â˜ƒ.modifier;
         } else {
            throw ERROR_INAPPLICABLE_OPTION.createWithContext(â˜ƒ.getReader(), â˜ƒ);
         }
      } else {
         â˜ƒ.getReader().setCursor(â˜ƒ);
         throw ERROR_UNKNOWN_OPTION.createWithContext(â˜ƒ.getReader(), â˜ƒ);
      }
   }

   public static void suggestNames(EntitySelectorParser var0, SuggestionsBuilder var1) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);

      for(Entry<String, EntitySelectorOptions.Option> â˜ƒx : OPTIONS.entrySet()) {
         if (((EntitySelectorOptions.Option)â˜ƒx.getValue()).predicate.test(â˜ƒ) && ((String)â˜ƒx.getKey()).toLowerCase(Locale.ROOT).startsWith(â˜ƒ)) {
            â˜ƒ.suggest((String)â˜ƒx.getKey() + "=", ((EntitySelectorOptions.Option)â˜ƒx.getValue()).description);
         }
      }
   }

   public interface Modifier {
      void handle(EntitySelectorParser var1) throws CommandSyntaxException;
   }

   static class Option {
      public final EntitySelectorOptions.Modifier modifier;
      public final Predicate<EntitySelectorParser> predicate;
      public final Component description;

      Option(EntitySelectorOptions.Modifier var1, Predicate<EntitySelectorParser> var2, Component var3) {
         this.modifier = â˜ƒ;
         this.predicate = â˜ƒ;
         this.description = â˜ƒ;
      }
   }
}
