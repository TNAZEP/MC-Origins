package net.minecraft.commands.arguments.blocks;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockStateParser {
   public static final SimpleCommandExceptionType ERROR_NO_TAGS_ALLOWED = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.block.tag.disallowed")
   );
   public static final DynamicCommandExceptionType ERROR_UNKNOWN_BLOCK = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("argument.block.id.invalid", var0)
   );
   public static final Dynamic2CommandExceptionType ERROR_UNKNOWN_PROPERTY = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("argument.block.property.unknown", var0, var1)
   );
   public static final Dynamic2CommandExceptionType ERROR_DUPLICATE_PROPERTY = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("argument.block.property.duplicate", var1, var0)
   );
   public static final Dynamic3CommandExceptionType ERROR_INVALID_VALUE = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new TranslatableComponent("argument.block.property.invalid", var0, var2, var1)
   );
   public static final Dynamic2CommandExceptionType ERROR_EXPECTED_VALUE = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("argument.block.property.novalue", var0, var1)
   );
   public static final SimpleCommandExceptionType ERROR_EXPECTED_END_OF_PROPERTIES = new SimpleCommandExceptionType(
      new TranslatableComponent("argument.block.property.unclosed")
   );
   private static final char SYNTAX_START_PROPERTIES = '[';
   private static final char SYNTAX_START_NBT = '{';
   private static final char SYNTAX_END_PROPERTIES = ']';
   private static final char SYNTAX_EQUALS = '=';
   private static final char SYNTAX_PROPERTY_SEPARATOR = ',';
   private static final char SYNTAX_TAG = '#';
   private static final BiFunction<SuggestionsBuilder, TagCollection<Block>, CompletableFuture<Suggestions>> SUGGEST_NOTHING = (var0, var1) -> var0.buildFuture(
         
      );
   private final StringReader reader;
   private final boolean forTesting;
   private final Map<Property<?>, Comparable<?>> properties = Maps.newHashMap();
   private final Map<String, String> vagueProperties = Maps.newHashMap();
   private ResourceLocation id = new ResourceLocation("");
   private StateDefinition<Block, BlockState> definition;
   private BlockState state;
   @Nullable
   private CompoundTag nbt;
   private ResourceLocation tag = new ResourceLocation("");
   private int tagCursor;
   private BiFunction<SuggestionsBuilder, TagCollection<Block>, CompletableFuture<Suggestions>> suggestions = SUGGEST_NOTHING;

   public BlockStateParser(StringReader var1, boolean var2) {
      this.reader = â˜ƒ;
      this.forTesting = â˜ƒ;
   }

   public Map<Property<?>, Comparable<?>> getProperties() {
      return this.properties;
   }

   @Nullable
   public BlockState getState() {
      return this.state;
   }

   @Nullable
   public CompoundTag getNbt() {
      return this.nbt;
   }

   @Nullable
   public ResourceLocation getTag() {
      return this.tag;
   }

   public BlockStateParser parse(boolean var1) throws CommandSyntaxException {
      this.suggestions = this::suggestBlockIdOrTag;
      if (this.reader.canRead() && this.reader.peek() == '#') {
         this.readTag();
         this.suggestions = this::suggestOpenVaguePropertiesOrNbt;
         if (this.reader.canRead() && this.reader.peek() == '[') {
            this.readVagueProperties();
            this.suggestions = this::suggestOpenNbt;
         }
      } else {
         this.readBlock();
         this.suggestions = this::suggestOpenPropertiesOrNbt;
         if (this.reader.canRead() && this.reader.peek() == '[') {
            this.readProperties();
            this.suggestions = this::suggestOpenNbt;
         }
      }

      if (â˜ƒ && this.reader.canRead() && this.reader.peek() == '{') {
         this.suggestions = SUGGEST_NOTHING;
         this.readNbt();
      }

      return this;
   }

   private CompletableFuture<Suggestions> suggestPropertyNameOrEnd(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (â˜ƒ.getRemaining().isEmpty()) {
         â˜ƒ.suggest(String.valueOf(']'));
      }

      return this.suggestPropertyName(â˜ƒ, â˜ƒ);
   }

   private CompletableFuture<Suggestions> suggestVaguePropertyNameOrEnd(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (â˜ƒ.getRemaining().isEmpty()) {
         â˜ƒ.suggest(String.valueOf(']'));
      }

      return this.suggestVaguePropertyName(â˜ƒ, â˜ƒ);
   }

   private CompletableFuture<Suggestions> suggestPropertyName(SuggestionsBuilder var1, TagCollection<Block> var2) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);

      for(Property<?> â˜ƒx : this.state.getProperties()) {
         if (!this.properties.containsKey(â˜ƒx) && â˜ƒx.getName().startsWith(â˜ƒ)) {
            â˜ƒ.suggest(â˜ƒx.getName() + "=");
         }
      }

      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestVaguePropertyName(SuggestionsBuilder var1, TagCollection<Block> var2) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);
      if (this.tag != null && !this.tag.getPath().isEmpty()) {
         Tag<Block> â˜ƒx = â˜ƒ.getTag(this.tag);
         if (â˜ƒx != null) {
            for(Block â˜ƒxx : â˜ƒx.getValues()) {
               for(Property<?> â˜ƒxxx : â˜ƒxx.getStateDefinition().getProperties()) {
                  if (!this.vagueProperties.containsKey(â˜ƒxxx.getName()) && â˜ƒxxx.getName().startsWith(â˜ƒ)) {
                     â˜ƒ.suggest(â˜ƒxxx.getName() + "=");
                  }
               }
            }
         }
      }

      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOpenNbt(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (â˜ƒ.getRemaining().isEmpty() && this.hasBlockEntity(â˜ƒ)) {
         â˜ƒ.suggest(String.valueOf('{'));
      }

      return â˜ƒ.buildFuture();
   }

   private boolean hasBlockEntity(TagCollection<Block> var1) {
      if (this.state != null) {
         return this.state.hasBlockEntity();
      } else {
         if (this.tag != null) {
            Tag<Block> â˜ƒ = â˜ƒ.getTag(this.tag);
            if (â˜ƒ != null) {
               for(Block â˜ƒx : â˜ƒ.getValues()) {
                  if (â˜ƒx.defaultBlockState().hasBlockEntity()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private CompletableFuture<Suggestions> suggestEquals(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (â˜ƒ.getRemaining().isEmpty()) {
         â˜ƒ.suggest(String.valueOf('='));
      }

      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestNextPropertyOrEnd(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (â˜ƒ.getRemaining().isEmpty()) {
         â˜ƒ.suggest(String.valueOf(']'));
      }

      if (â˜ƒ.getRemaining().isEmpty() && this.properties.size() < this.state.getProperties().size()) {
         â˜ƒ.suggest(String.valueOf(','));
      }

      return â˜ƒ.buildFuture();
   }

   private static <T extends Comparable<T>> SuggestionsBuilder addSuggestions(SuggestionsBuilder var0, Property<T> var1) {
      for(T â˜ƒ : â˜ƒ.getPossibleValues()) {
         if (â˜ƒ instanceof Integer) {
            â˜ƒ.suggest((Integer)â˜ƒ);
         } else {
            â˜ƒ.suggest(â˜ƒ.getName(â˜ƒ));
         }
      }

      return â˜ƒ;
   }

   private CompletableFuture<Suggestions> suggestVaguePropertyValue(SuggestionsBuilder var1, TagCollection<Block> var2, String var3) {
      boolean â˜ƒ = false;
      if (this.tag != null && !this.tag.getPath().isEmpty()) {
         Tag<Block> â˜ƒx = â˜ƒ.getTag(this.tag);
         if (â˜ƒx != null) {
            for(Block â˜ƒxx : â˜ƒx.getValues()) {
               Property<?> â˜ƒxxx = â˜ƒxx.getStateDefinition().getProperty(â˜ƒ);
               if (â˜ƒxxx != null) {
                  addSuggestions(â˜ƒ, â˜ƒxxx);
               }

               if (!â˜ƒ) {
                  for(Property<?> â˜ƒxxx : â˜ƒxx.getStateDefinition().getProperties()) {
                     if (!this.vagueProperties.containsKey(â˜ƒxxx.getName())) {
                        â˜ƒ = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      if (â˜ƒ) {
         â˜ƒ.suggest(String.valueOf(','));
      }

      â˜ƒ.suggest(String.valueOf(']'));
      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOpenVaguePropertiesOrNbt(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (â˜ƒ.getRemaining().isEmpty()) {
         Tag<Block> â˜ƒ = â˜ƒ.getTag(this.tag);
         if (â˜ƒ != null) {
            boolean â˜ƒx = false;
            boolean â˜ƒxx = false;

            for(Block â˜ƒxxx : â˜ƒ.getValues()) {
               â˜ƒx |= !â˜ƒxxx.getStateDefinition().getProperties().isEmpty();
               â˜ƒxx |= â˜ƒxxx.defaultBlockState().hasBlockEntity();
               if (â˜ƒx && â˜ƒxx) {
                  break;
               }
            }

            if (â˜ƒx) {
               â˜ƒ.suggest(String.valueOf('['));
            }

            if (â˜ƒxx) {
               â˜ƒ.suggest(String.valueOf('{'));
            }
         }
      }

      return this.suggestTag(â˜ƒ, â˜ƒ);
   }

   private CompletableFuture<Suggestions> suggestOpenPropertiesOrNbt(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (â˜ƒ.getRemaining().isEmpty()) {
         if (!this.state.getBlock().getStateDefinition().getProperties().isEmpty()) {
            â˜ƒ.suggest(String.valueOf('['));
         }

         if (this.state.hasBlockEntity()) {
            â˜ƒ.suggest(String.valueOf('{'));
         }
      }

      return â˜ƒ.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestTag(SuggestionsBuilder var1, TagCollection<Block> var2) {
      return SharedSuggestionProvider.suggestResource(â˜ƒ.getAvailableTags(), â˜ƒ.createOffset(this.tagCursor).add(â˜ƒ));
   }

   private CompletableFuture<Suggestions> suggestBlockIdOrTag(SuggestionsBuilder var1, TagCollection<Block> var2) {
      if (this.forTesting) {
         SharedSuggestionProvider.suggestResource(â˜ƒ.getAvailableTags(), â˜ƒ, String.valueOf('#'));
      }

      SharedSuggestionProvider.suggestResource(Registry.BLOCK.keySet(), â˜ƒ);
      return â˜ƒ.buildFuture();
   }

   public void readBlock() throws CommandSyntaxException {
      int â˜ƒ = this.reader.getCursor();
      this.id = ResourceLocation.read(this.reader);
      Block â˜ƒx = (Block)Registry.BLOCK.getOptional(this.id).orElseThrow(() -> {
         this.reader.setCursor(â˜ƒ);
         return ERROR_UNKNOWN_BLOCK.createWithContext(this.reader, this.id.toString());
      });
      this.definition = â˜ƒx.getStateDefinition();
      this.state = â˜ƒx.defaultBlockState();
   }

   public void readTag() throws CommandSyntaxException {
      if (!this.forTesting) {
         throw ERROR_NO_TAGS_ALLOWED.create();
      } else {
         this.suggestions = this::suggestTag;
         this.reader.expect('#');
         this.tagCursor = this.reader.getCursor();
         this.tag = ResourceLocation.read(this.reader);
      }
   }

   public void readProperties() throws CommandSyntaxException {
      this.reader.skip();
      this.suggestions = this::suggestPropertyNameOrEnd;
      this.reader.skipWhitespace();

      while(this.reader.canRead() && this.reader.peek() != ']') {
         this.reader.skipWhitespace();
         int â˜ƒ = this.reader.getCursor();
         String â˜ƒx = this.reader.readString();
         Property<?> â˜ƒxx = this.definition.getProperty(â˜ƒx);
         if (â˜ƒxx == null) {
            this.reader.setCursor(â˜ƒ);
            throw ERROR_UNKNOWN_PROPERTY.createWithContext(this.reader, this.id.toString(), â˜ƒx);
         }

         if (this.properties.containsKey(â˜ƒxx)) {
            this.reader.setCursor(â˜ƒ);
            throw ERROR_DUPLICATE_PROPERTY.createWithContext(this.reader, this.id.toString(), â˜ƒx);
         }

         this.reader.skipWhitespace();
         this.suggestions = this::suggestEquals;
         if (!this.reader.canRead() || this.reader.peek() != '=') {
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader, this.id.toString(), â˜ƒx);
         }

         this.reader.skip();
         this.reader.skipWhitespace();
         this.suggestions = (var1x, var2x) -> addSuggestions(var1x, â˜ƒ).buildFuture();
         int â˜ƒ = this.reader.getCursor();
         this.setValue(â˜ƒxx, this.reader.readString(), â˜ƒ);
         this.suggestions = this::suggestNextPropertyOrEnd;
         this.reader.skipWhitespace();
         if (this.reader.canRead()) {
            if (this.reader.peek() != ',') {
               if (this.reader.peek() != ']') {
                  throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
               }
               break;
            }

            this.reader.skip();
            this.suggestions = this::suggestPropertyName;
         }
      }

      if (this.reader.canRead()) {
         this.reader.skip();
      } else {
         throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
      }
   }

   public void readVagueProperties() throws CommandSyntaxException {
      this.reader.skip();
      this.suggestions = this::suggestVaguePropertyNameOrEnd;
      int â˜ƒ = -1;
      this.reader.skipWhitespace();

      while(this.reader.canRead() && this.reader.peek() != ']') {
         this.reader.skipWhitespace();
         int â˜ƒx = this.reader.getCursor();
         String â˜ƒxx = this.reader.readString();
         if (this.vagueProperties.containsKey(â˜ƒxx)) {
            this.reader.setCursor(â˜ƒx);
            throw ERROR_DUPLICATE_PROPERTY.createWithContext(this.reader, this.id.toString(), â˜ƒxx);
         }

         this.reader.skipWhitespace();
         if (!this.reader.canRead() || this.reader.peek() != '=') {
            this.reader.setCursor(â˜ƒx);
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader, this.id.toString(), â˜ƒxx);
         }

         this.reader.skip();
         this.reader.skipWhitespace();
         this.suggestions = (var2x, var3x) -> this.suggestVaguePropertyValue(var2x, var3x, â˜ƒ);
         â˜ƒ = this.reader.getCursor();
         String â˜ƒx = this.reader.readString();
         this.vagueProperties.put(â˜ƒxx, â˜ƒx);
         this.reader.skipWhitespace();
         if (this.reader.canRead()) {
            â˜ƒ = -1;
            if (this.reader.peek() != ',') {
               if (this.reader.peek() != ']') {
                  throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
               }
               break;
            }

            this.reader.skip();
            this.suggestions = this::suggestVaguePropertyName;
         }
      }

      if (this.reader.canRead()) {
         this.reader.skip();
      } else {
         if (â˜ƒ >= 0) {
            this.reader.setCursor(â˜ƒ);
         }

         throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
      }
   }

   public void readNbt() throws CommandSyntaxException {
      this.nbt = new TagParser(this.reader).readStruct();
   }

   private <T extends Comparable<T>> void setValue(Property<T> var1, String var2, int var3) throws CommandSyntaxException {
      Optional<T> â˜ƒ = â˜ƒ.getValue(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         this.state = this.state.setValue(â˜ƒ, (Comparable)â˜ƒ.get());
         this.properties.put(â˜ƒ, (Comparable)â˜ƒ.get());
      } else {
         this.reader.setCursor(â˜ƒ);
         throw ERROR_INVALID_VALUE.createWithContext(this.reader, this.id.toString(), â˜ƒ.getName(), â˜ƒ);
      }
   }

   public static String serialize(BlockState var0) {
      StringBuilder â˜ƒ = new StringBuilder(Registry.BLOCK.getKey(â˜ƒ.getBlock()).toString());
      if (!â˜ƒ.getProperties().isEmpty()) {
         â˜ƒ.append('[');
         boolean â˜ƒx = false;

         for(Entry<Property<?>, Comparable<?>> â˜ƒxx : â˜ƒ.getValues().entrySet()) {
            if (â˜ƒx) {
               â˜ƒ.append(',');
            }

            appendProperty(â˜ƒ, (Property)â˜ƒxx.getKey(), (Comparable<?>)â˜ƒxx.getValue());
            â˜ƒx = true;
         }

         â˜ƒ.append(']');
      }

      return â˜ƒ.toString();
   }

   private static <T extends Comparable<T>> void appendProperty(StringBuilder var0, Property<T> var1, Comparable<?> var2) {
      â˜ƒ.append(â˜ƒ.getName());
      â˜ƒ.append('=');
      â˜ƒ.append(â˜ƒ.getName((T)â˜ƒ));
   }

   public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder var1, TagCollection<Block> var2) {
      return (CompletableFuture<Suggestions>)this.suggestions.apply(â˜ƒ.createOffset(this.reader.getCursor()), â˜ƒ);
   }

   public Map<String, String> getVagueProperties() {
      return this.vagueProperties;
   }
}
