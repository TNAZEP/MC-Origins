package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NbtPathArgument implements ArgumentType<NbtPathArgument.NbtPath> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
   public static final SimpleCommandExceptionType ERROR_INVALID_NODE = new SimpleCommandExceptionType(
      new TranslatableComponent("arguments.nbtpath.node.invalid")
   );
   public static final DynamicCommandExceptionType ERROR_NOTHING_FOUND = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("arguments.nbtpath.nothing_found", var0)
   );
   private static final char INDEX_MATCH_START = '[';
   private static final char INDEX_MATCH_END = ']';
   private static final char KEY_MATCH_START = '{';
   private static final char KEY_MATCH_END = '}';
   private static final char QUOTED_KEY_START = '"';

   public static NbtPathArgument nbtPath() {
      return new NbtPathArgument();
   }

   public static NbtPathArgument.NbtPath getPath(CommandContext<CommandSourceStack> var0, String var1) {
      return â˜ƒ.getArgument(â˜ƒ, NbtPathArgument.NbtPath.class);
   }

   public NbtPathArgument.NbtPath parse(StringReader var1) throws CommandSyntaxException {
      List<NbtPathArgument.Node> â˜ƒ = Lists.<NbtPathArgument.Node>newArrayList();
      int â˜ƒx = â˜ƒ.getCursor();
      Object2IntMap<NbtPathArgument.Node> â˜ƒxx = new Object2IntOpenHashMap<>();
      boolean â˜ƒxxx = true;

      while(â˜ƒ.canRead() && â˜ƒ.peek() != ' ') {
         NbtPathArgument.Node â˜ƒxxxx = parseNode(â˜ƒ, â˜ƒxxx);
         â˜ƒ.add(â˜ƒxxxx);
         â˜ƒxx.put(â˜ƒxxxx, â˜ƒ.getCursor() - â˜ƒx);
         â˜ƒxxx = false;
         if (â˜ƒ.canRead()) {
            char â˜ƒxxxxx = â˜ƒ.peek();
            if (â˜ƒxxxxx != ' ' && â˜ƒxxxxx != '[' && â˜ƒxxxxx != '{') {
               â˜ƒ.expect('.');
            }
         }
      }

      return new NbtPathArgument.NbtPath(
         â˜ƒ.getString().substring(â˜ƒx, â˜ƒ.getCursor()), (NbtPathArgument.Node[])â˜ƒ.toArray(new NbtPathArgument.Node[0]), â˜ƒxx
      );
   }

   private static NbtPathArgument.Node parseNode(StringReader var0, boolean var1) throws CommandSyntaxException {
      switch(â˜ƒ.peek()) {
         case '"': {
            String â˜ƒ = â˜ƒ.readString();
            return readObjectNode(â˜ƒ, â˜ƒ);
         }
         case '[': {
            â˜ƒ.skip();
            int â˜ƒ = â˜ƒ.peek();
            if (â˜ƒ == 123) {
               CompoundTag â˜ƒx = new TagParser(â˜ƒ).readStruct();
               â˜ƒ.expect(']');
               return new NbtPathArgument.MatchElementNode(â˜ƒx);
            } else {
               if (â˜ƒ == 93) {
                  â˜ƒ.skip();
                  return NbtPathArgument.AllElementsNode.INSTANCE;
               }

               int â˜ƒx = â˜ƒ.readInt();
               â˜ƒ.expect(']');
               return new NbtPathArgument.IndexedElementNode(â˜ƒx);
            }
         }
         case '{': {
            if (!â˜ƒ) {
               throw ERROR_INVALID_NODE.createWithContext(â˜ƒ);
            }

            CompoundTag â˜ƒ = new TagParser(â˜ƒ).readStruct();
            return new NbtPathArgument.MatchRootObjectNode(â˜ƒ);
         }
         default: {
            String â˜ƒ = readUnquotedName(â˜ƒ);
            return readObjectNode(â˜ƒ, â˜ƒ);
         }
      }
   }

   private static NbtPathArgument.Node readObjectNode(StringReader var0, String var1) throws CommandSyntaxException {
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '{') {
         CompoundTag â˜ƒ = new TagParser(â˜ƒ).readStruct();
         return new NbtPathArgument.MatchObjectNode(â˜ƒ, â˜ƒ);
      } else {
         return new NbtPathArgument.CompoundChildNode(â˜ƒ);
      }
   }

   private static String readUnquotedName(StringReader var0) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();

      while(â˜ƒ.canRead() && isAllowedInUnquotedName(â˜ƒ.peek())) {
         â˜ƒ.skip();
      }

      if (â˜ƒ.getCursor() == â˜ƒ) {
         throw ERROR_INVALID_NODE.createWithContext(â˜ƒ);
      } else {
         return â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor());
      }
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   private static boolean isAllowedInUnquotedName(char var0) {
      return â˜ƒ != ' ' && â˜ƒ != '"' && â˜ƒ != '[' && â˜ƒ != ']' && â˜ƒ != '.' && â˜ƒ != '{' && â˜ƒ != '}';
   }

   static Predicate<Tag> createTagPredicate(CompoundTag var0) {
      return var1 -> NbtUtils.compareNbt(â˜ƒ, var1, true);
   }

   static class AllElementsNode implements NbtPathArgument.Node {
      public static final NbtPathArgument.AllElementsNode INSTANCE = new NbtPathArgument.AllElementsNode();

      private AllElementsNode() {
      }

      @Override
      public void getTag(Tag var1, List<Tag> var2) {
         if (â˜ƒ instanceof CollectionTag) {
            â˜ƒ.addAll((CollectionTag)â˜ƒ);
         }
      }

      @Override
      public void getOrCreateTag(Tag var1, Supplier<Tag> var2, List<Tag> var3) {
         if (â˜ƒ instanceof CollectionTag â˜ƒ) {
            if (â˜ƒ.isEmpty()) {
               Tag â˜ƒx = (Tag)â˜ƒ.get();
               if (â˜ƒ.addTag(0, â˜ƒx)) {
                  â˜ƒ.add(â˜ƒx);
               }
            } else {
               â˜ƒ.addAll(â˜ƒ);
            }
         }
      }

      @Override
      public Tag createPreferredParentTag() {
         return new ListTag();
      }

      @Override
      public int setTag(Tag var1, Supplier<Tag> var2) {
         if (!(â˜ƒ instanceof CollectionTag)) {
            return 0;
         } else {
            CollectionTag<?> â˜ƒ = (CollectionTag)â˜ƒ;
            int â˜ƒx = â˜ƒ.size();
            if (â˜ƒx == 0) {
               â˜ƒ.addTag(0, (Tag)â˜ƒ.get());
               return 1;
            } else {
               Tag â˜ƒ = (Tag)â˜ƒ.get();
               int â˜ƒx = â˜ƒx - (int)â˜ƒ.stream().filter(â˜ƒ::equals).count();
               if (â˜ƒx == 0) {
                  return 0;
               } else {
                  â˜ƒ.clear();
                  if (!â˜ƒ.addTag(0, â˜ƒ)) {
                     return 0;
                  } else {
                     for(int â˜ƒ = 1; â˜ƒ < â˜ƒx; ++â˜ƒ) {
                        â˜ƒ.addTag(â˜ƒ, (Tag)â˜ƒ.get());
                     }

                     return â˜ƒx;
                  }
               }
            }
         }
      }

      @Override
      public int removeTag(Tag var1) {
         if (â˜ƒ instanceof CollectionTag â˜ƒ) {
            int â˜ƒx = â˜ƒ.size();
            if (â˜ƒx > 0) {
               â˜ƒ.clear();
               return â˜ƒx;
            }
         }

         return 0;
      }
   }

   static class CompoundChildNode implements NbtPathArgument.Node {
      private final String name;

      public CompoundChildNode(String var1) {
         this.name = â˜ƒ;
      }

      @Override
      public void getTag(Tag var1, List<Tag> var2) {
         if (â˜ƒ instanceof CompoundTag) {
            Tag â˜ƒ = ((CompoundTag)â˜ƒ).get(this.name);
            if (â˜ƒ != null) {
               â˜ƒ.add(â˜ƒ);
            }
         }
      }

      @Override
      public void getOrCreateTag(Tag var1, Supplier<Tag> var2, List<Tag> var3) {
         if (â˜ƒ instanceof CompoundTag â˜ƒ) {
            Tag â˜ƒx;
            if (â˜ƒ.contains(this.name)) {
               â˜ƒx = â˜ƒ.get(this.name);
            } else {
               â˜ƒx = (Tag)â˜ƒ.get();
               â˜ƒ.put(this.name, â˜ƒx);
            }

            â˜ƒ.add(â˜ƒx);
         }
      }

      @Override
      public Tag createPreferredParentTag() {
         return new CompoundTag();
      }

      @Override
      public int setTag(Tag var1, Supplier<Tag> var2) {
         if (â˜ƒ instanceof CompoundTag â˜ƒ) {
            Tag â˜ƒx = (Tag)â˜ƒ.get();
            Tag â˜ƒxx = â˜ƒ.put(this.name, â˜ƒx);
            if (!â˜ƒx.equals(â˜ƒxx)) {
               return 1;
            }
         }

         return 0;
      }

      @Override
      public int removeTag(Tag var1) {
         if (â˜ƒ instanceof CompoundTag â˜ƒ && â˜ƒ.contains(this.name)) {
            â˜ƒ.remove(this.name);
            return 1;
         }

         return 0;
      }
   }

   static class IndexedElementNode implements NbtPathArgument.Node {
      private final int index;

      public IndexedElementNode(int var1) {
         this.index = â˜ƒ;
      }

      @Override
      public void getTag(Tag var1, List<Tag> var2) {
         if (â˜ƒ instanceof CollectionTag â˜ƒ) {
            int â˜ƒx = â˜ƒ.size();
            int â˜ƒxx = this.index < 0 ? â˜ƒx + this.index : this.index;
            if (0 <= â˜ƒxx && â˜ƒxx < â˜ƒx) {
               â˜ƒ.add((Tag)â˜ƒ.get(â˜ƒxx));
            }
         }
      }

      @Override
      public void getOrCreateTag(Tag var1, Supplier<Tag> var2, List<Tag> var3) {
         this.getTag(â˜ƒ, â˜ƒ);
      }

      @Override
      public Tag createPreferredParentTag() {
         return new ListTag();
      }

      @Override
      public int setTag(Tag var1, Supplier<Tag> var2) {
         if (â˜ƒ instanceof CollectionTag â˜ƒ) {
            int â˜ƒx = â˜ƒ.size();
            int â˜ƒxx = this.index < 0 ? â˜ƒx + this.index : this.index;
            if (0 <= â˜ƒxx && â˜ƒxx < â˜ƒx) {
               Tag â˜ƒxxx = (Tag)â˜ƒ.get(â˜ƒxx);
               Tag â˜ƒxxxx = (Tag)â˜ƒ.get();
               if (!â˜ƒxxxx.equals(â˜ƒxxx) && â˜ƒ.setTag(â˜ƒxx, â˜ƒxxxx)) {
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int removeTag(Tag var1) {
         if (â˜ƒ instanceof CollectionTag â˜ƒ) {
            int â˜ƒx = â˜ƒ.size();
            int â˜ƒxx = this.index < 0 ? â˜ƒx + this.index : this.index;
            if (0 <= â˜ƒxx && â˜ƒxx < â˜ƒx) {
               â˜ƒ.remove(â˜ƒxx);
               return 1;
            }
         }

         return 0;
      }
   }

   static class MatchElementNode implements NbtPathArgument.Node {
      private final CompoundTag pattern;
      private final Predicate<Tag> predicate;

      public MatchElementNode(CompoundTag var1) {
         this.pattern = â˜ƒ;
         this.predicate = NbtPathArgument.createTagPredicate(â˜ƒ);
      }

      @Override
      public void getTag(Tag var1, List<Tag> var2) {
         if (â˜ƒ instanceof ListTag â˜ƒ) {
            â˜ƒ.stream().filter(this.predicate).forEach(â˜ƒ::add);
         }
      }

      @Override
      public void getOrCreateTag(Tag var1, Supplier<Tag> var2, List<Tag> var3) {
         MutableBoolean â˜ƒx = new MutableBoolean();
         if (â˜ƒ instanceof ListTag â˜ƒ) {
            â˜ƒ.stream().filter(this.predicate).forEach(var2x -> {
               â˜ƒ.add(var2x);
               â˜ƒ.setTrue();
            });
            if (â˜ƒx.isFalse()) {
               CompoundTag â˜ƒxx = this.pattern.copy();
               â˜ƒ.add(â˜ƒxx);
               â˜ƒ.add(â˜ƒxx);
            }
         }
      }

      @Override
      public Tag createPreferredParentTag() {
         return new ListTag();
      }

      @Override
      public int setTag(Tag var1, Supplier<Tag> var2) {
         int â˜ƒx = 0;
         if (â˜ƒ instanceof ListTag â˜ƒ) {
            int â˜ƒxx = â˜ƒ.size();
            if (â˜ƒxx == 0) {
               â˜ƒ.add((Tag)â˜ƒ.get());
               ++â˜ƒx;
            } else {
               for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒxx; ++â˜ƒxx) {
                  Tag â˜ƒxxx = â˜ƒ.get(â˜ƒxx);
                  if (this.predicate.test(â˜ƒxxx)) {
                     Tag â˜ƒxxxx = (Tag)â˜ƒ.get();
                     if (!â˜ƒxxxx.equals(â˜ƒxxx) && â˜ƒ.setTag(â˜ƒxx, â˜ƒxxxx)) {
                        ++â˜ƒx;
                     }
                  }
               }
            }
         }

         return â˜ƒx;
      }

      @Override
      public int removeTag(Tag var1) {
         int â˜ƒx = 0;
         if (â˜ƒ instanceof ListTag â˜ƒ) {
            for(int â˜ƒxx = â˜ƒ.size() - 1; â˜ƒxx >= 0; --â˜ƒxx) {
               if (this.predicate.test(â˜ƒ.get(â˜ƒxx))) {
                  â˜ƒ.remove(â˜ƒxx);
                  ++â˜ƒx;
               }
            }
         }

         return â˜ƒx;
      }
   }

   static class MatchObjectNode implements NbtPathArgument.Node {
      private final String name;
      private final CompoundTag pattern;
      private final Predicate<Tag> predicate;

      public MatchObjectNode(String var1, CompoundTag var2) {
         this.name = â˜ƒ;
         this.pattern = â˜ƒ;
         this.predicate = NbtPathArgument.createTagPredicate(â˜ƒ);
      }

      @Override
      public void getTag(Tag var1, List<Tag> var2) {
         if (â˜ƒ instanceof CompoundTag) {
            Tag â˜ƒ = ((CompoundTag)â˜ƒ).get(this.name);
            if (this.predicate.test(â˜ƒ)) {
               â˜ƒ.add(â˜ƒ);
            }
         }
      }

      @Override
      public void getOrCreateTag(Tag var1, Supplier<Tag> var2, List<Tag> var3) {
         if (â˜ƒ instanceof CompoundTag â˜ƒ) {
            Tag â˜ƒx = â˜ƒ.get(this.name);
            if (â˜ƒx == null) {
               Tag var6 = this.pattern.copy();
               â˜ƒ.put(this.name, var6);
               â˜ƒ.add(var6);
            } else if (this.predicate.test(â˜ƒx)) {
               â˜ƒ.add(â˜ƒx);
            }
         }
      }

      @Override
      public Tag createPreferredParentTag() {
         return new CompoundTag();
      }

      @Override
      public int setTag(Tag var1, Supplier<Tag> var2) {
         if (â˜ƒ instanceof CompoundTag â˜ƒ) {
            Tag â˜ƒx = â˜ƒ.get(this.name);
            if (this.predicate.test(â˜ƒx)) {
               Tag â˜ƒxx = (Tag)â˜ƒ.get();
               if (!â˜ƒxx.equals(â˜ƒx)) {
                  â˜ƒ.put(this.name, â˜ƒxx);
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int removeTag(Tag var1) {
         if (â˜ƒ instanceof CompoundTag â˜ƒ) {
            Tag â˜ƒx = â˜ƒ.get(this.name);
            if (this.predicate.test(â˜ƒx)) {
               â˜ƒ.remove(this.name);
               return 1;
            }
         }

         return 0;
      }
   }

   static class MatchRootObjectNode implements NbtPathArgument.Node {
      private final Predicate<Tag> predicate;

      public MatchRootObjectNode(CompoundTag var1) {
         this.predicate = NbtPathArgument.createTagPredicate(â˜ƒ);
      }

      @Override
      public void getTag(Tag var1, List<Tag> var2) {
         if (â˜ƒ instanceof CompoundTag && this.predicate.test(â˜ƒ)) {
            â˜ƒ.add(â˜ƒ);
         }
      }

      @Override
      public void getOrCreateTag(Tag var1, Supplier<Tag> var2, List<Tag> var3) {
         this.getTag(â˜ƒ, â˜ƒ);
      }

      @Override
      public Tag createPreferredParentTag() {
         return new CompoundTag();
      }

      @Override
      public int setTag(Tag var1, Supplier<Tag> var2) {
         return 0;
      }

      @Override
      public int removeTag(Tag var1) {
         return 0;
      }
   }

   public static class NbtPath {
      private final String original;
      private final Object2IntMap<NbtPathArgument.Node> nodeToOriginalPosition;
      private final NbtPathArgument.Node[] nodes;

      public NbtPath(String var1, NbtPathArgument.Node[] var2, Object2IntMap<NbtPathArgument.Node> var3) {
         this.original = â˜ƒ;
         this.nodes = â˜ƒ;
         this.nodeToOriginalPosition = â˜ƒ;
      }

      public List<Tag> get(Tag var1) throws CommandSyntaxException {
         List<Tag> â˜ƒ = Collections.singletonList(â˜ƒ);

         for(NbtPathArgument.Node â˜ƒx : this.nodes) {
            â˜ƒ = â˜ƒx.get(â˜ƒ);
            if (â˜ƒ.isEmpty()) {
               throw this.createNotFoundException(â˜ƒx);
            }
         }

         return â˜ƒ;
      }

      public int countMatching(Tag var1) {
         List<Tag> â˜ƒ = Collections.singletonList(â˜ƒ);

         for(NbtPathArgument.Node â˜ƒx : this.nodes) {
            â˜ƒ = â˜ƒx.get(â˜ƒ);
            if (â˜ƒ.isEmpty()) {
               return 0;
            }
         }

         return â˜ƒ.size();
      }

      private List<Tag> getOrCreateParents(Tag var1) throws CommandSyntaxException {
         List<Tag> â˜ƒ = Collections.singletonList(â˜ƒ);

         for(int â˜ƒx = 0; â˜ƒx < this.nodes.length - 1; ++â˜ƒx) {
            NbtPathArgument.Node â˜ƒxx = this.nodes[â˜ƒx];
            int â˜ƒxxx = â˜ƒx + 1;
            â˜ƒ = â˜ƒxx.getOrCreate(â˜ƒ, this.nodes[â˜ƒxxx]::createPreferredParentTag);
            if (â˜ƒ.isEmpty()) {
               throw this.createNotFoundException(â˜ƒxx);
            }
         }

         return â˜ƒ;
      }

      public List<Tag> getOrCreate(Tag var1, Supplier<Tag> var2) throws CommandSyntaxException {
         List<Tag> â˜ƒ = this.getOrCreateParents(â˜ƒ);
         NbtPathArgument.Node â˜ƒx = this.nodes[this.nodes.length - 1];
         return â˜ƒx.getOrCreate(â˜ƒ, â˜ƒ);
      }

      private static int apply(List<Tag> var0, Function<Tag, Integer> var1) {
         return â˜ƒ.stream().map(â˜ƒ).reduce(0, (var0x, var1x) -> var0x + var1x);
      }

      public int set(Tag var1, Tag var2) throws CommandSyntaxException {
         return this.set(â˜ƒ, â˜ƒ::copy);
      }

      public int set(Tag var1, Supplier<Tag> var2) throws CommandSyntaxException {
         List<Tag> â˜ƒ = this.getOrCreateParents(â˜ƒ);
         NbtPathArgument.Node â˜ƒx = this.nodes[this.nodes.length - 1];
         return apply(â˜ƒ, var2x -> â˜ƒ.setTag(var2x, â˜ƒ));
      }

      public int remove(Tag var1) {
         List<Tag> â˜ƒ = Collections.singletonList(â˜ƒ);

         for(int â˜ƒx = 0; â˜ƒx < this.nodes.length - 1; ++â˜ƒx) {
            â˜ƒ = this.nodes[â˜ƒx].get(â˜ƒ);
         }

         NbtPathArgument.Node â˜ƒx = this.nodes[this.nodes.length - 1];
         return apply(â˜ƒ, â˜ƒx::removeTag);
      }

      private CommandSyntaxException createNotFoundException(NbtPathArgument.Node var1) {
         int â˜ƒ = this.nodeToOriginalPosition.getInt(â˜ƒ);
         return NbtPathArgument.ERROR_NOTHING_FOUND.create(this.original.substring(0, â˜ƒ));
      }

      public String toString() {
         return this.original;
      }
   }

   interface Node {
      void getTag(Tag var1, List<Tag> var2);

      void getOrCreateTag(Tag var1, Supplier<Tag> var2, List<Tag> var3);

      Tag createPreferredParentTag();

      int setTag(Tag var1, Supplier<Tag> var2);

      int removeTag(Tag var1);

      default List<Tag> get(List<Tag> var1) {
         return this.collect(â˜ƒ, this::getTag);
      }

      default List<Tag> getOrCreate(List<Tag> var1, Supplier<Tag> var2) {
         return this.collect(â˜ƒ, (var2x, var3) -> this.getOrCreateTag(var2x, â˜ƒ, var3));
      }

      default List<Tag> collect(List<Tag> var1, BiConsumer<Tag, List<Tag>> var2) {
         List<Tag> â˜ƒ = Lists.<Tag>newArrayList();

         for(Tag â˜ƒx : â˜ƒ) {
            â˜ƒ.accept(â˜ƒx, â˜ƒ);
         }

         return â˜ƒ;
      }
   }
}
