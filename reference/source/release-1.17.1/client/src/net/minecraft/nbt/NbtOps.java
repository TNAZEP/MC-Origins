package net.minecraft.nbt;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.RecordBuilder.AbstractStringBuilder;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class NbtOps implements DynamicOps<Tag> {
   public static final NbtOps INSTANCE = new NbtOps();

   protected NbtOps() {
   }

   public Tag empty() {
      return EndTag.INSTANCE;
   }

   public <U> U convertTo(DynamicOps<U> var1, Tag var2) {
      switch(â˜ƒ.getId()) {
         case 0:
            return â˜ƒ.empty();
         case 1:
            return â˜ƒ.createByte(((NumericTag)â˜ƒ).getAsByte());
         case 2:
            return â˜ƒ.createShort(((NumericTag)â˜ƒ).getAsShort());
         case 3:
            return â˜ƒ.createInt(((NumericTag)â˜ƒ).getAsInt());
         case 4:
            return â˜ƒ.createLong(((NumericTag)â˜ƒ).getAsLong());
         case 5:
            return â˜ƒ.createFloat(((NumericTag)â˜ƒ).getAsFloat());
         case 6:
            return â˜ƒ.createDouble(((NumericTag)â˜ƒ).getAsDouble());
         case 7:
            return â˜ƒ.createByteList(ByteBuffer.wrap(((ByteArrayTag)â˜ƒ).getAsByteArray()));
         case 8:
            return â˜ƒ.createString(â˜ƒ.getAsString());
         case 9:
            return this.convertList(â˜ƒ, â˜ƒ);
         case 10:
            return this.convertMap(â˜ƒ, â˜ƒ);
         case 11:
            return â˜ƒ.createIntList(Arrays.stream(((IntArrayTag)â˜ƒ).getAsIntArray()));
         case 12:
            return â˜ƒ.createLongList(Arrays.stream(((LongArrayTag)â˜ƒ).getAsLongArray()));
         default:
            throw new IllegalStateException("Unknown tag type: " + â˜ƒ);
      }
   }

   public DataResult<Number> getNumberValue(Tag var1) {
      return â˜ƒ instanceof NumericTag ? DataResult.success(((NumericTag)â˜ƒ).getAsNumber()) : DataResult.error("Not a number");
   }

   public Tag createNumeric(Number var1) {
      return DoubleTag.valueOf(â˜ƒ.doubleValue());
   }

   public Tag createByte(byte var1) {
      return ByteTag.valueOf(â˜ƒ);
   }

   public Tag createShort(short var1) {
      return ShortTag.valueOf(â˜ƒ);
   }

   public Tag createInt(int var1) {
      return IntTag.valueOf(â˜ƒ);
   }

   public Tag createLong(long var1) {
      return LongTag.valueOf(â˜ƒ);
   }

   public Tag createFloat(float var1) {
      return FloatTag.valueOf(â˜ƒ);
   }

   public Tag createDouble(double var1) {
      return DoubleTag.valueOf(â˜ƒ);
   }

   public Tag createBoolean(boolean var1) {
      return ByteTag.valueOf(â˜ƒ);
   }

   public DataResult<String> getStringValue(Tag var1) {
      return â˜ƒ instanceof StringTag ? DataResult.success(â˜ƒ.getAsString()) : DataResult.error("Not a string");
   }

   public Tag createString(String var1) {
      return StringTag.valueOf(â˜ƒ);
   }

   private static CollectionTag<?> createGenericList(byte var0, byte var1) {
      if (typesMatch(â˜ƒ, â˜ƒ, (byte)4)) {
         return new LongArrayTag(new long[0]);
      } else if (typesMatch(â˜ƒ, â˜ƒ, (byte)1)) {
         return new ByteArrayTag(new byte[0]);
      } else {
         return (CollectionTag<?>)(typesMatch(â˜ƒ, â˜ƒ, (byte)3) ? new IntArrayTag(new int[0]) : new ListTag());
      }
   }

   private static boolean typesMatch(byte var0, byte var1, byte var2) {
      return â˜ƒ == â˜ƒ && (â˜ƒ == â˜ƒ || â˜ƒ == 0);
   }

   private static <T extends Tag> void fillOne(CollectionTag<T> var0, Tag var1, Tag var2) {
      if (â˜ƒ instanceof CollectionTag â˜ƒ) {
         â˜ƒ.forEach(var1x -> â˜ƒ.add(var1x));
      }

      â˜ƒ.add(â˜ƒ);
   }

   private static <T extends Tag> void fillMany(CollectionTag<T> var0, Tag var1, List<Tag> var2) {
      if (â˜ƒ instanceof CollectionTag â˜ƒ) {
         â˜ƒ.forEach(var1x -> â˜ƒ.add(var1x));
      }

      â˜ƒ.forEach(var1x -> â˜ƒ.add(var1x));
   }

   public DataResult<Tag> mergeToList(Tag var1, Tag var2) {
      if (!(â˜ƒ instanceof CollectionTag) && !(â˜ƒ instanceof EndTag)) {
         return DataResult.error("mergeToList called with not a list: " + â˜ƒ, â˜ƒ);
      } else {
         CollectionTag<?> â˜ƒ = createGenericList(â˜ƒ instanceof CollectionTag ? ((CollectionTag)â˜ƒ).getElementType() : 0, â˜ƒ.getId());
         fillOne(â˜ƒ, â˜ƒ, â˜ƒ);
         return DataResult.success(â˜ƒ);
      }
   }

   public DataResult<Tag> mergeToList(Tag var1, List<Tag> var2) {
      if (!(â˜ƒ instanceof CollectionTag) && !(â˜ƒ instanceof EndTag)) {
         return DataResult.error("mergeToList called with not a list: " + â˜ƒ, â˜ƒ);
      } else {
         CollectionTag<?> â˜ƒ = createGenericList(
            â˜ƒ instanceof CollectionTag ? ((CollectionTag)â˜ƒ).getElementType() : 0, â˜ƒ.stream().findFirst().map(Tag::getId).orElse((byte)0)
         );
         fillMany(â˜ƒ, â˜ƒ, â˜ƒ);
         return DataResult.success(â˜ƒ);
      }
   }

   public DataResult<Tag> mergeToMap(Tag var1, Tag var2, Tag var3) {
      if (!(â˜ƒ instanceof CompoundTag) && !(â˜ƒ instanceof EndTag)) {
         return DataResult.error("mergeToMap called with not a map: " + â˜ƒ, â˜ƒ);
      } else if (!(â˜ƒ instanceof StringTag)) {
         return DataResult.error("key is not a string: " + â˜ƒ, â˜ƒ);
      } else {
         CompoundTag â˜ƒx = new CompoundTag();
         if (â˜ƒ instanceof CompoundTag â˜ƒ) {
            â˜ƒ.getAllKeys().forEach(var2x -> â˜ƒ.put(var2x, â˜ƒ.get(var2x)));
         }

         â˜ƒx.put(â˜ƒ.getAsString(), â˜ƒ);
         return DataResult.success(â˜ƒx);
      }
   }

   public DataResult<Tag> mergeToMap(Tag var1, MapLike<Tag> var2) {
      if (!(â˜ƒ instanceof CompoundTag) && !(â˜ƒ instanceof EndTag)) {
         return DataResult.error("mergeToMap called with not a map: " + â˜ƒ, â˜ƒ);
      } else {
         CompoundTag â˜ƒx = new CompoundTag();
         if (â˜ƒ instanceof CompoundTag â˜ƒ) {
            â˜ƒ.getAllKeys().forEach(var2x -> â˜ƒ.put(var2x, â˜ƒ.get(var2x)));
         }

         List<Tag> â˜ƒ = Lists.<Tag>newArrayList();
         â˜ƒ.entries().forEach(var2x -> {
            Tag â˜ƒ = (Tag)var2x.getFirst();
            if (!(â˜ƒ instanceof StringTag)) {
               â˜ƒ.add(â˜ƒ);
            } else {
               â˜ƒ.put(â˜ƒ.getAsString(), (Tag)var2x.getSecond());
            }
         });
         return !â˜ƒ.isEmpty() ? DataResult.error("some keys are not strings: " + â˜ƒ, â˜ƒx) : DataResult.success(â˜ƒx);
      }
   }

   public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag var1) {
      if (!(â˜ƒ instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + â˜ƒ);
      } else {
         CompoundTag â˜ƒ = (CompoundTag)â˜ƒ;
         return DataResult.success(â˜ƒ.getAllKeys().stream().map(var2x -> Pair.of(this.createString(var2x), â˜ƒ.get(var2x))));
      }
   }

   public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag var1) {
      if (!(â˜ƒ instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + â˜ƒ);
      } else {
         CompoundTag â˜ƒ = (CompoundTag)â˜ƒ;
         return DataResult.success((Consumer)var2x -> â˜ƒ.getAllKeys().forEach(var3 -> var2x.accept(this.createString(var3), â˜ƒ.get(var3))));
      }
   }

   public DataResult<MapLike<Tag>> getMap(Tag var1) {
      if (!(â˜ƒ instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + â˜ƒ);
      } else {
         final CompoundTag â˜ƒ = (CompoundTag)â˜ƒ;
         return DataResult.success(new MapLike<Tag>() {
            @Nullable
            public Tag get(Tag var1) {
               return â˜ƒ.get(â˜ƒ.getAsString());
            }

            @Nullable
            public Tag get(String var1) {
               return â˜ƒ.get(â˜ƒ);
            }

            @Override
            public Stream<Pair<Tag, Tag>> entries() {
               return â˜ƒ.getAllKeys().stream().map(var2x -> Pair.of(NbtOps.this.createString(var2x), â˜ƒ.get(var2x)));
            }

            public String toString() {
               return "MapLike[" + â˜ƒ + "]";
            }
         });
      }
   }

   public Tag createMap(Stream<Pair<Tag, Tag>> var1) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.forEach(var1x -> â˜ƒ.put(((Tag)var1x.getFirst()).getAsString(), (Tag)var1x.getSecond()));
      return â˜ƒ;
   }

   public DataResult<Stream<Tag>> getStream(Tag var1) {
      return â˜ƒ instanceof CollectionTag ? DataResult.success(((CollectionTag)â˜ƒ).stream().map(var0 -> var0)) : DataResult.error("Not a list");
   }

   public DataResult<Consumer<Consumer<Tag>>> getList(Tag var1) {
      return â˜ƒ instanceof CollectionTag â˜ƒ ? DataResult.success(â˜ƒ::forEach) : DataResult.error("Not a list: " + â˜ƒ);
   }

   public DataResult<ByteBuffer> getByteBuffer(Tag var1) {
      return â˜ƒ instanceof ByteArrayTag ? DataResult.success(ByteBuffer.wrap(((ByteArrayTag)â˜ƒ).getAsByteArray())) : DynamicOps.super.getByteBuffer(â˜ƒ);
   }

   public Tag createByteList(ByteBuffer var1) {
      return new ByteArrayTag(DataFixUtils.toArray(â˜ƒ));
   }

   public DataResult<IntStream> getIntStream(Tag var1) {
      return â˜ƒ instanceof IntArrayTag ? DataResult.success(Arrays.stream(((IntArrayTag)â˜ƒ).getAsIntArray())) : DynamicOps.super.getIntStream(â˜ƒ);
   }

   public Tag createIntList(IntStream var1) {
      return new IntArrayTag(â˜ƒ.toArray());
   }

   public DataResult<LongStream> getLongStream(Tag var1) {
      return â˜ƒ instanceof LongArrayTag ? DataResult.success(Arrays.stream(((LongArrayTag)â˜ƒ).getAsLongArray())) : DynamicOps.super.getLongStream(â˜ƒ);
   }

   public Tag createLongList(LongStream var1) {
      return new LongArrayTag(â˜ƒ.toArray());
   }

   public Tag createList(Stream<Tag> var1) {
      PeekingIterator<Tag> â˜ƒ = Iterators.peekingIterator(â˜ƒ.iterator());
      if (!â˜ƒ.hasNext()) {
         return new ListTag();
      } else {
         Tag â˜ƒ = â˜ƒ.peek();
         if (â˜ƒ instanceof ByteTag) {
            List<Byte> â˜ƒx = Lists.newArrayList(Iterators.transform(â˜ƒ, var0 -> ((ByteTag)var0).getAsByte()));
            return new ByteArrayTag(â˜ƒx);
         } else if (â˜ƒ instanceof IntTag) {
            List<Integer> â˜ƒ = Lists.newArrayList(Iterators.transform(â˜ƒ, var0 -> ((IntTag)var0).getAsInt()));
            return new IntArrayTag(â˜ƒ);
         } else if (â˜ƒ instanceof LongTag) {
            List<Long> â˜ƒ = Lists.newArrayList(Iterators.transform(â˜ƒ, var0 -> ((LongTag)var0).getAsLong()));
            return new LongArrayTag(â˜ƒ);
         } else {
            ListTag â˜ƒ = new ListTag();

            while(â˜ƒ.hasNext()) {
               Tag â˜ƒx = â˜ƒ.next();
               if (!(â˜ƒx instanceof EndTag)) {
                  â˜ƒ.add(â˜ƒx);
               }
            }

            return â˜ƒ;
         }
      }
   }

   public Tag remove(Tag var1, String var2) {
      if (â˜ƒ instanceof CompoundTag â˜ƒ) {
         CompoundTag â˜ƒx = new CompoundTag();
         â˜ƒ.getAllKeys().stream().filter(var1x -> !Objects.equals(var1x, â˜ƒ)).forEach(var2x -> â˜ƒ.put(var2x, â˜ƒ.get(var2x)));
         return â˜ƒx;
      } else {
         return â˜ƒ;
      }
   }

   public String toString() {
      return "NBT";
   }

   @Override
   public RecordBuilder<Tag> mapBuilder() {
      return new NbtOps.NbtRecordBuilder();
   }

   class NbtRecordBuilder extends AbstractStringBuilder<Tag, CompoundTag> {
      protected NbtRecordBuilder() {
         super(NbtOps.this);
      }

      protected CompoundTag initBuilder() {
         return new CompoundTag();
      }

      protected CompoundTag append(String var1, Tag var2, CompoundTag var3) {
         â˜ƒ.put(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }

      protected DataResult<Tag> build(CompoundTag var1, Tag var2) {
         if (â˜ƒ == null || â˜ƒ == EndTag.INSTANCE) {
            return DataResult.success(â˜ƒ);
         } else if (!(â˜ƒ instanceof CompoundTag)) {
            return DataResult.error("mergeToMap called with not a map: " + â˜ƒ, â˜ƒ);
         } else {
            CompoundTag â˜ƒ = new CompoundTag(Maps.newHashMap(((CompoundTag)â˜ƒ).entries()));

            for(Entry<String, Tag> â˜ƒx : â˜ƒ.entries().entrySet()) {
               â˜ƒ.put((String)â˜ƒx.getKey(), (Tag)â˜ƒx.getValue());
            }

            return DataResult.success(â˜ƒ);
         }
      }
   }
}
