package net.minecraft.commands.arguments.selector;

import com.google.common.primitives.Doubles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntitySelectorParser {
   public static final char SYNTAX_SELECTOR_START = '@';
   private static final char SYNTAX_OPTIONS_START = '[';
   private static final char SYNTAX_OPTIONS_END = ']';
   public static final char SYNTAX_OPTIONS_KEY_VALUE_SEPARATOR = '=';
   private static final char SYNTAX_OPTIONS_SEPARATOR = ',';
   public static final char SYNTAX_NOT = '!';
   public static final char SYNTAX_TAG = '#';
   private static final char SELECTOR_NEAREST_PLAYER = 'p';
   private static final char SELECTOR_ALL_PLAYERS = 'a';
   private static final char SELECTOR_RANDOM_PLAYERS = 'r';
   private static final char SELECTOR_CURRENT_ENTITY = 's';
   private static final char SELECTOR_ALL_ENTITIES = 'e';
   public static final SimpleCommandExceptionType ERROR_INVALID_NAME_OR_UUID = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.invalid")
   );
   public static final DynamicCommandExceptionType ERROR_UNKNOWN_SELECTOR_TYPE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.entity.selector.unknown", var0)
   );
   public static final SimpleCommandExceptionType ERROR_SELECTORS_NOT_ALLOWED = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.selector.not_allowed")
   );
   public static final SimpleCommandExceptionType ERROR_MISSING_SELECTOR_TYPE = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.selector.missing")
   );
   public static final SimpleCommandExceptionType ERROR_EXPECTED_END_OF_OPTIONS = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.entity.options.unterminated")
   );
   public static final DynamicCommandExceptionType ERROR_EXPECTED_OPTION_VALUE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.entity.options.valueless", var0)
   );
   public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_ARBITRARY = (var0, var1) -> {
   };
   public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_NEAREST = (var0, var1) -> var1.sort(
         (var1x, var2) -> Doubles.compare(var1x.distanceToSqr(var0), var2.distanceToSqr(var0))
      );
   public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_FURTHEST = (var0, var1) -> var1.sort(
         (var1x, var2) -> Doubles.compare(var2.distanceToSqr(var0), var1x.distanceToSqr(var0))
      );
   public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_RANDOM = (var0, var1) -> Collections.shuffle(var1);
   public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> SUGGEST_NOTHING = (var0, var1) -> var0.buildFuture(
         
      );
   private final StringReader reader;
   private final boolean allowSelectors;
   private int maxResults;
   private boolean includesEntities;
   private boolean worldLimited;
   private MinMaxBounds.Doubles distance = MinMaxBounds.Doubles.ANY;
   private MinMaxBounds.Ints level = MinMaxBounds.Ints.ANY;
   @Nullable
   private Double x;
   @Nullable
   private Double y;
   @Nullable
   private Double z;
   @Nullable
   private Double deltaX;
   @Nullable
   private Double deltaY;
   @Nullable
   private Double deltaZ;
   private WrappedMinMaxBounds rotX = WrappedMinMaxBounds.ANY;
   private WrappedMinMaxBounds rotY = WrappedMinMaxBounds.ANY;
   private Predicate<Entity> predicate = var0 -> true;
   private BiConsumer<Vec3, List<? extends Entity>> order = ORDER_ARBITRARY;
   private boolean currentEntity;
   @Nullable
   private String playerName;
   private int startPosition;
   @Nullable
   private UUID entityUUID;
   private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> suggestions = SUGGEST_NOTHING;
   private boolean hasNameEquals;
   private boolean hasNameNotEquals;
   private boolean isLimited;
   private boolean isSorted;
   private boolean hasGamemodeEquals;
   private boolean hasGamemodeNotEquals;
   private boolean hasTeamEquals;
   private boolean hasTeamNotEquals;
   @Nullable
   private EntityType<?> type;
   private boolean typeInverse;
   private boolean hasScores;
   private boolean hasAdvancements;
   private boolean usesSelectors;

   public EntitySelectorParser(StringReader var1) {
      this(â˜ƒ, true);
   }

   public EntitySelectorParser(StringReader var1, boolean var2) {
      this.reader = â˜ƒ;
      this.allowSelectors = â˜ƒ;
   }

   public EntitySelector getSelector() {
      AABB â˜ƒ;
      if (this.deltaX == null && this.deltaY == null && this.deltaZ == null) {
         if (this.distance.getMax() != null) {
            double â˜ƒx = this.distance.getMax();
            â˜ƒ = new AABB(-â˜ƒx, -â˜ƒx, -â˜ƒx, â˜ƒx + 1.0, â˜ƒx + 1.0, â˜ƒx + 1.0);
         } else {
            â˜ƒ = null;
         }
      } else {
         â˜ƒ = this.createAabb(this.deltaX == null ? 0.0 : this.deltaX, this.deltaY == null ? 0.0 : this.deltaY, this.deltaZ == null ? 0.0 : this.deltaZ);
      }

      Function<Vec3, Vec3> â˜ƒ;
      if (this.x == null && this.y == null && this.z == null) {
         â˜ƒ = var0 -> var0;
      } else {
         â˜ƒ = var1x -> new Vec3(this.x == null ? var1x.x : this.x, this.y == null ? var1x.y : this.y, this.z == null ? var1x.z : this.z);
      }

      return new EntitySelector(
         this.maxResults,
         this.includesEntities,
         this.worldLimited,
         this.predicate,
         this.distance,
         â˜ƒ,
         â˜ƒ,
         this.order,
         this.currentEntity,
         this.playerName,
         this.entityUUID,
         this.type,
         this.usesSelectors
      );
   }

   private AABB createAabb(double var1, double var3, double var5) {
      boolean â˜ƒ = â˜ƒ < 0.0;
      boolean â˜ƒx = â˜ƒ < 0.0;
      boolean â˜ƒxx = â˜ƒ < 0.0;
      double â˜ƒxxx = â˜ƒ ? â˜ƒ : 0.0;
      double â˜ƒxxxx = â˜ƒx ? â˜ƒ : 0.0;
      double â˜ƒxxxxx = â˜ƒxx ? â˜ƒ : 0.0;
      double â˜ƒxxxxxx = (â˜ƒ ? 0.0 : â˜ƒ) + 1.0;
      double â˜ƒxxxxxxx = (â˜ƒx ? 0.0 : â˜ƒ) + 1.0;
      double â˜ƒxxxxxxxx = (â˜ƒxx ? 0.0 : â˜ƒ) + 1.0;
      return new AABB(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
   }

   private void finalizePredicates() {
      if (this.rotX != WrappedMinMaxBounds.ANY) {
         this.predicate = this.predicate.and(this.createRotationPredicate(this.rotX, Entity::getXRot));
      }

      if (this.rotY != WrappedMinMaxBounds.ANY) {
         this.predicate = this.predicate.and(this.createRotationPredicate(this.rotY, Entity::getYRot));
      }

      if (!this.level.isAny()) {
         this.predicate = this.predicate.and(var1 -> !(var1 instanceof ServerPlayer) ? false : this.level.matches(((ServerPlayer)var1).experienceLevel));
      }
   }

   private Predicate<Entity> createRotationPredicate(WrappedMinMaxBounds var1, ToDoubleFunction<Entity> var2) {
      double â˜ƒ = (double)Mth.wrapDegrees(â˜ƒ.getMin() == null ? 0.0F : â˜ƒ.getMin());
      double â˜ƒx = (double)Mth.wrapDegrees(â˜ƒ.getMax() == null ? 359.0F : â˜ƒ.getMax());
      return var5x -> {
         double â˜ƒ = Mth.wrapDegrees(â˜ƒ.applyAsDouble(var5x));
         if (â˜ƒ > â˜ƒ) {
            return â˜ƒ >= â˜ƒ || â˜ƒ <= â˜ƒ;
         } else {
            return â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ;
         }
      };
   }

   protected void parseSelector() throws CommandSyntaxException {
      this.usesSelectors = true;
      this.suggestions = this::suggestSelector;
      if (!this.reader.canRead()) {
         throw ERROR_MISSING_SELECTOR_TYPE.createWithContext(this.reader);
      } else {
         int â˜ƒ = this.reader.getCursor();
         char â˜ƒx = this.reader.read();
         if (â˜ƒx == 'p') {
            this.maxResults = 1;
            this.includesEntities = false;
            this.order = ORDER_NEAREST;
            this.limitToType(EntityType.PLAYER);
         } else if (â˜ƒx == 'a') {
            this.maxResults = Integer.MAX_VALUE;
            this.includesEntities = false;
            this.order = ORDER_ARBITRARY;
            this.limitToType(EntityType.PLAYER);
         } else if (â˜ƒx == 'r') {
            this.maxResults = 1;
            this.includesEntities = false;
            this.order = ORDER_RANDOM;
            this.limitToType(EntityType.PLAYER);
         } else if (â˜ƒx == 's') {
            this.maxResults = 1;
            this.includesEntities = true;
            this.currentEntity = true;
         } else {
            if (â˜ƒx != 'e') {
               this.reader.setCursor(â˜ƒ);
               throw ERROR_UNKNOWN_SELECTOR_TYPE.createWithContext(this.reader, "@" + â˜ƒx);
            }

            this.maxResults = Integer.MAX_VALUE;
            this.includesEntities = true;
            this.order = ORDER_ARBITRARY;
            this.predicate = Entity::isAlive;
         }

         this.suggestions = this::suggestOpenOptions;
         if (this.reader.canRead() && this.reader.peek() == '[') {
            this.reader.skip();
            this.suggestions = this::suggestOptionsKeyOrClose;
            this.parseOptions();
         }
      }
   }

   protected void parseNameOrUUID() throws CommandSyntaxException {
      if (this.reader.canRead()) {
         this.suggestions = this::suggestName;
      }

      int â˜ƒ = this.reader.getCursor();
      String â˜ƒx = this.reader.readString();

      try {
         this.entityUUID = UUID.fromString(â˜ƒx);
         this.includesEntities = true;
      } catch (IllegalArgumentException var4) {
         if (â˜ƒx.isEmpty() || â˜ƒx.length() > 16) {
            this.reader.setCursor(â˜ƒ);
            throw ERROR_INVALID_NAME_OR_UUID.createWithContext(this.reader);
         }

         this.includesEntities = false;
         this.playerName = â˜ƒx;
      }

      this.maxResults = 1;
   }

   protected void parseOptions() throws CommandSyntaxException {
      this.suggestions = this::suggestOptionsKey;
      this.reader.skipWhitespace();

      while(this.reader.canRead() && this.reader.peek() != ']') {
         this.reader.skipWhitespace();
         int â˜ƒ = this.reader.getCursor();
         String â˜ƒx = this.reader.readString();
         EntitySelectorOptions.Modifier â˜ƒxx = EntitySelectorOptions.get(this, â˜ƒx, â˜ƒ);
         this.reader.skipWhitespace();
         if (!this.reader.canRead() || this.reader.peek() != '=') {
            this.reader.setCursor(â˜ƒ);
            throw ERROR_EXPECTED_OPTION_VALUE.createWithContext(this.reader, â˜ƒx);
         }

         this.reader.skip();
         this.reader.skipWhitespace();
         this.suggestions = SUGGEST_NOTHING;
         â˜ƒxx.handle(this);
         this.reader.skipWhitespace();
         this.suggestions = this::suggestOptionsNextOrClose;
         if (this.reader.canRead()) {
            if (this.reader.peek() != ',') {
               if (this.reader.peek() != ']') {
                  throw ERROR_EXPECTED_END_OF_OPTIONS.createWithContext(this.reader);
               }
               break;
            }

            this.reader.skip();
            this.suggestions = this::suggestOptionsKey;
         }
      }

      if (this.reader.canRead()) {
         this.reader.skip();
         this.suggestions = SUGGEST_NOTHING;
      } else {
         throw ERROR_EXPECTED_END_OF_OPTIONS.createWithContext(this.reader);
      }
   }

   public boolean shouldInvertValue() {
      this.reader.skipWhitespace();
      if (this.reader.canRead() && this.reader.peek() == '!') {
         this.reader.skip();
         this.reader.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public boolean isTag() {
      this.reader.skipWhitespace();
      if (this.reader.canRead() && this.reader.peek() == '#') {
         this.reader.skip();
         this.reader.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public StringReader getReader() {
      return this.reader;
   }

   public void addPredicate(Predicate<Entity> var1) {
      this.predicate = this.predicate.and(â˜ƒ);
   }

   public void setWorldLimited() {
      this.worldLimited = true;
   }

   public MinMaxBounds.Doubles getDistance() {
      return this.distance;
   }

   public void setDistance(MinMaxBounds.Doubles var1) {
      this.distance = â˜ƒ;
   }

   public MinMaxBounds.Ints getLevel() {
      return this.level;
   }

   public void setLevel(MinMaxBounds.Ints var1) {
      this.level = â˜ƒ;
   }

   public WrappedMinMaxBounds getRotX() {
      return this.rotX;
   }

   public void setRotX(WrappedMinMaxBounds var1) {
      this.rotX = â˜ƒ;
   }

   public WrappedMinMaxBounds getRotY() {
      return this.rotY;
   }

   public void setRotY(WrappedMinMaxBounds var1) {
      this.rotY = â˜ƒ;
   }

   @Nullable
   public Double getX() {
      return this.x;
   }

   @Nullable
   public Double getY() {
      return this.y;
   }

   @Nullable
   public Double getZ() {
      return this.z;
   }

   public void setX(double var1) {
      this.x = â˜ƒ;
   }

   public void setY(double var1) {
      this.y = â˜ƒ;
   }

   public void setZ(double var1) {
      this.z = â˜ƒ;
   }

   public void setDeltaX(double var1) {
      this.deltaX = â˜ƒ;
   }

   public void setDeltaY(double var1) {
      this.deltaY = â˜ƒ;
   }

   public void setDeltaZ(double var1) {
      this.deltaZ = â˜ƒ;
   }

   @Nullable
   public Double getDeltaX() {
      return this.deltaX;
   }

   @Nullable
   public Double getDeltaY() {
      return this.deltaY;
   }

   @Nullable
   public Double getDeltaZ() {
      return this.deltaZ;
   }

   public void setMaxResults(int var1) {
      this.maxResults = â˜ƒ;
   }

   public void setIncludesEntities(boolean var1) {
      this.includesEntities = â˜ƒ;
   }

   public BiConsumer<Vec3, List<? extends Entity>> getOrder() {
      return this.order;
   }

   public void setOrder(BiConsumer<Vec3, List<? extends Entity>> var1) {
      this.order = â˜ƒ;
   }

   public EntitySelector parse() throws CommandSyntaxException {
      this.startPosition = this.reader.getCursor();
      this.suggestions = this::suggestNameOrSelector;
      if (this.reader.canRead() && this.reader.peek() == '@') {
         if (!this.allowSelectors) {
            throw ERROR_SELECTORS_NOT_ALLOWED.createWithContext(this.reader);
         }

         this.reader.skip();
         this.parseSelector();
      } else {
         this.parseNameOrUUID();
      }

      this.finalizePredicates();
      return this.getSelector();
   }

   private static void fillSelectorSuggestions(SuggestionsBuilder var0) {
      â˜ƒ.suggest("@p", new TranslatableComponent("argument.entity.selector.nearestPlayer"));
      â˜ƒ.suggest("@a", new TranslatableComponent("argument.entity.selector.allPlayers"));
      â˜ƒ.suggest("@r", new TranslatableComponent("argument.entity.selector.randomPlayer"));
      â˜ƒ.suggest("@s", new TranslatableComponent("argument.entity.selector.self"));
      â˜ƒ.suggest("@e", new TranslatableComponent("argument.entity.selector.allEntities"));
   }

   private CompletableFuture<Suggestions> suggestNameOrSelector(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      â˜ƒ.accept(â˜ƒ);
      if (this.allowSelectors) {
         fillSelectorSuggestions(â˜ƒ);
      }

      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestName(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      SuggestionsBuilder â˜ƒ = â˜ƒ.createOffset(this.startPosition);
      â˜ƒ.accept(â˜ƒ);
      return â˜ƒ.add(â˜ƒ).buildFuture();
   }

   private CompletableFuture<Suggestions> suggestSelector(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      SuggestionsBuilder â˜ƒ = â˜ƒ.createOffset(â˜ƒ.getStart() - 1);
      fillSelectorSuggestions(â˜ƒ);
      â˜ƒ.add(â˜ƒ);
      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOpenOptions(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      â˜ƒ.suggest(String.valueOf('['));
      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOptionsKeyOrClose(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      â˜ƒ.suggest(String.valueOf(']'));
      EntitySelectorOptions.suggestNames(this, â˜ƒ);
      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOptionsKey(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      EntitySelectorOptions.suggestNames(this, â˜ƒ);
      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOptionsNextOrClose(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      â˜ƒ.suggest(String.valueOf(','));
      â˜ƒ.suggest(String.valueOf(']'));
      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestEquals(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      â˜ƒ.suggest(String.valueOf('='));
      return â˜ƒ.buildFuture();
   }

   public boolean isCurrentEntity() {
      return this.currentEntity;
   }

   public void setSuggestions(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> var1) {
      this.suggestions = â˜ƒ;
   }

   public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      return (CompletableFuture<Suggestions>)this.suggestions.apply(â˜ƒ.createOffset(this.reader.getCursor()), â˜ƒ);
   }

   public boolean hasNameEquals() {
      return this.hasNameEquals;
   }

   public void setHasNameEquals(boolean var1) {
      this.hasNameEquals = â˜ƒ;
   }

   public boolean hasNameNotEquals() {
      return this.hasNameNotEquals;
   }

   public void setHasNameNotEquals(boolean var1) {
      this.hasNameNotEquals = â˜ƒ;
   }

   public boolean isLimited() {
      return this.isLimited;
   }

   public void setLimited(boolean var1) {
      this.isLimited = â˜ƒ;
   }

   public boolean isSorted() {
      return this.isSorted;
   }

   public void setSorted(boolean var1) {
      this.isSorted = â˜ƒ;
   }

   public boolean hasGamemodeEquals() {
      return this.hasGamemodeEquals;
   }

   public void setHasGamemodeEquals(boolean var1) {
      this.hasGamemodeEquals = â˜ƒ;
   }

   public boolean hasGamemodeNotEquals() {
      return this.hasGamemodeNotEquals;
   }

   public void setHasGamemodeNotEquals(boolean var1) {
      this.hasGamemodeNotEquals = â˜ƒ;
   }

   public boolean hasTeamEquals() {
      return this.hasTeamEquals;
   }

   public void setHasTeamEquals(boolean var1) {
      this.hasTeamEquals = â˜ƒ;
   }

   public boolean hasTeamNotEquals() {
      return this.hasTeamNotEquals;
   }

   public void setHasTeamNotEquals(boolean var1) {
      this.hasTeamNotEquals = â˜ƒ;
   }

   public void limitToType(EntityType<?> var1) {
      this.type = â˜ƒ;
   }

   public void setTypeLimitedInversely() {
      this.typeInverse = true;
   }

   public boolean isTypeLimited() {
      return this.type != null;
   }

   public boolean isTypeLimitedInversely() {
      return this.typeInverse;
   }

   public boolean hasScores() {
      return this.hasScores;
   }

   public void setHasScores(boolean var1) {
      this.hasScores = â˜ƒ;
   }

   public boolean hasAdvancements() {
      return this.hasAdvancements;
   }

   public void setHasAdvancements(boolean var1) {
      this.hasAdvancements = â˜ƒ;
   }
}
