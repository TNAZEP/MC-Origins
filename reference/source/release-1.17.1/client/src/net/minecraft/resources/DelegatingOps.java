package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class DelegatingOps<T> implements DynamicOps<T> {
   protected final DynamicOps<T> delegate;

   protected DelegatingOps(DynamicOps<T> var1) {
      this.delegate = â˜ƒ;
   }

   @Override
   public T empty() {
      return this.delegate.empty();
   }

   @Override
   public <U> U convertTo(DynamicOps<U> var1, T var2) {
      return this.delegate.convertTo(â˜ƒ, â˜ƒ);
   }

   @Override
   public DataResult<Number> getNumberValue(T var1) {
      return this.delegate.getNumberValue(â˜ƒ);
   }

   @Override
   public T createNumeric(Number var1) {
      return this.delegate.createNumeric(â˜ƒ);
   }

   @Override
   public T createByte(byte var1) {
      return this.delegate.createByte(â˜ƒ);
   }

   @Override
   public T createShort(short var1) {
      return this.delegate.createShort(â˜ƒ);
   }

   @Override
   public T createInt(int var1) {
      return this.delegate.createInt(â˜ƒ);
   }

   @Override
   public T createLong(long var1) {
      return this.delegate.createLong(â˜ƒ);
   }

   @Override
   public T createFloat(float var1) {
      return this.delegate.createFloat(â˜ƒ);
   }

   @Override
   public T createDouble(double var1) {
      return this.delegate.createDouble(â˜ƒ);
   }

   @Override
   public DataResult<Boolean> getBooleanValue(T var1) {
      return this.delegate.getBooleanValue(â˜ƒ);
   }

   @Override
   public T createBoolean(boolean var1) {
      return this.delegate.createBoolean(â˜ƒ);
   }

   @Override
   public DataResult<String> getStringValue(T var1) {
      return this.delegate.getStringValue(â˜ƒ);
   }

   @Override
   public T createString(String var1) {
      return this.delegate.createString(â˜ƒ);
   }

   @Override
   public DataResult<T> mergeToList(T var1, T var2) {
      return this.delegate.mergeToList(â˜ƒ, â˜ƒ);
   }

   @Override
   public DataResult<T> mergeToList(T var1, List<T> var2) {
      return this.delegate.mergeToList(â˜ƒ, â˜ƒ);
   }

   @Override
   public DataResult<T> mergeToMap(T var1, T var2, T var3) {
      return this.delegate.mergeToMap(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public DataResult<T> mergeToMap(T var1, MapLike<T> var2) {
      return this.delegate.mergeToMap(â˜ƒ, â˜ƒ);
   }

   @Override
   public DataResult<Stream<Pair<T, T>>> getMapValues(T var1) {
      return this.delegate.getMapValues(â˜ƒ);
   }

   @Override
   public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T var1) {
      return this.delegate.getMapEntries(â˜ƒ);
   }

   @Override
   public T createMap(Stream<Pair<T, T>> var1) {
      return this.delegate.createMap(â˜ƒ);
   }

   @Override
   public DataResult<MapLike<T>> getMap(T var1) {
      return this.delegate.getMap(â˜ƒ);
   }

   @Override
   public DataResult<Stream<T>> getStream(T var1) {
      return this.delegate.getStream(â˜ƒ);
   }

   @Override
   public DataResult<Consumer<Consumer<T>>> getList(T var1) {
      return this.delegate.getList(â˜ƒ);
   }

   @Override
   public T createList(Stream<T> var1) {
      return this.delegate.createList(â˜ƒ);
   }

   @Override
   public DataResult<ByteBuffer> getByteBuffer(T var1) {
      return this.delegate.getByteBuffer(â˜ƒ);
   }

   @Override
   public T createByteList(ByteBuffer var1) {
      return this.delegate.createByteList(â˜ƒ);
   }

   @Override
   public DataResult<IntStream> getIntStream(T var1) {
      return this.delegate.getIntStream(â˜ƒ);
   }

   @Override
   public T createIntList(IntStream var1) {
      return this.delegate.createIntList(â˜ƒ);
   }

   @Override
   public DataResult<LongStream> getLongStream(T var1) {
      return this.delegate.getLongStream(â˜ƒ);
   }

   @Override
   public T createLongList(LongStream var1) {
      return this.delegate.createLongList(â˜ƒ);
   }

   @Override
   public T remove(T var1, String var2) {
      return this.delegate.remove(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean compressMaps() {
      return this.delegate.compressMaps();
   }

   @Override
   public ListBuilder<T> listBuilder() {
      return this.delegate.listBuilder();
   }

   @Override
   public RecordBuilder<T> mapBuilder() {
      return this.delegate.mapBuilder();
   }
}
