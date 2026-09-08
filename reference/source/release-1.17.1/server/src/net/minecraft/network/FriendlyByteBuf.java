package net.minecraft.network;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DataResult.PartialResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FriendlyByteBuf extends ByteBuf {
   private static final int MAX_VARINT_SIZE = 5;
   private static final int MAX_VARLONG_SIZE = 10;
   private static final int DEFAULT_NBT_QUOTA = 2097152;
   private final ByteBuf source;
   public static final short MAX_STRING_LENGTH = 32767;
   public static final int MAX_COMPONENT_STRING_LENGTH = 262144;

   public FriendlyByteBuf(ByteBuf var1) {
      this.source = â˜ƒ;
   }

   public static int getVarIntSize(int var0) {
      for(int â˜ƒ = 1; â˜ƒ < 5; ++â˜ƒ) {
         if ((â˜ƒ & -1 << â˜ƒ * 7) == 0) {
            return â˜ƒ;
         }
      }

      return 5;
   }

   public static int getVarLongSize(long var0) {
      for(int â˜ƒ = 1; â˜ƒ < 10; ++â˜ƒ) {
         if ((â˜ƒ & -1L << â˜ƒ * 7) == 0L) {
            return â˜ƒ;
         }
      }

      return 10;
   }

   public <T> T readWithCodec(Codec<T> var1) {
      CompoundTag â˜ƒ = this.readAnySizeNbt();
      DataResult<T> â˜ƒx = â˜ƒ.parse(NbtOps.INSTANCE, â˜ƒ);
      â˜ƒx.error().ifPresent(var1x -> {
         throw new EncoderException("Failed to decode: " + var1x.message() + " " + â˜ƒ);
      });
      return (T)â˜ƒx.result().get();
   }

   public <T> void writeWithCodec(Codec<T> var1, T var2) {
      DataResult<Tag> â˜ƒ = â˜ƒ.encodeStart(NbtOps.INSTANCE, â˜ƒ);
      â˜ƒ.error().ifPresent(var1x -> {
         throw new EncoderException("Failed to encode: " + var1x.message() + " " + â˜ƒ);
      });
      this.writeNbt((CompoundTag)â˜ƒ.result().get());
   }

   public static <T> IntFunction<T> limitValue(IntFunction<T> var0, int var1) {
      return var2 -> {
         if (var2 > â˜ƒ) {
            throw new DecoderException("Value " + var2 + " is larger than limit " + â˜ƒ);
         } else {
            return â˜ƒ.apply(var2);
         }
      };
   }

   public <T, C extends Collection<T>> C readCollection(IntFunction<C> var1, Function<FriendlyByteBuf, T> var2) {
      int â˜ƒ = this.readVarInt();
      C â˜ƒx = (C)â˜ƒ.apply(â˜ƒ);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         â˜ƒx.add(â˜ƒ.apply(this));
      }

      return â˜ƒx;
   }

   public <T> void writeCollection(Collection<T> var1, BiConsumer<FriendlyByteBuf, T> var2) {
      this.writeVarInt(â˜ƒ.size());

      for(T â˜ƒ : â˜ƒ) {
         â˜ƒ.accept(this, â˜ƒ);
      }
   }

   public <T> List<T> readList(Function<FriendlyByteBuf, T> var1) {
      return this.readCollection(Lists::newArrayListWithCapacity, â˜ƒ);
   }

   public IntList readIntIdList() {
      int â˜ƒ = this.readVarInt();
      IntList â˜ƒx = new IntArrayList();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         â˜ƒx.add(this.readVarInt());
      }

      return â˜ƒx;
   }

   public void writeIntIdList(IntList var1) {
      this.writeVarInt(â˜ƒ.size());
      â˜ƒ.forEach(this::writeVarInt);
   }

   public <K, V, M extends Map<K, V>> M readMap(IntFunction<M> var1, Function<FriendlyByteBuf, K> var2, Function<FriendlyByteBuf, V> var3) {
      int â˜ƒ = this.readVarInt();
      M â˜ƒx = (M)â˜ƒ.apply(â˜ƒ);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         K â˜ƒxxx = (K)â˜ƒ.apply(this);
         V â˜ƒxxxx = (V)â˜ƒ.apply(this);
         â˜ƒx.put(â˜ƒxxx, â˜ƒxxxx);
      }

      return â˜ƒx;
   }

   public <K, V> Map<K, V> readMap(Function<FriendlyByteBuf, K> var1, Function<FriendlyByteBuf, V> var2) {
      return this.readMap(Maps::newHashMapWithExpectedSize, â˜ƒ, â˜ƒ);
   }

   public <K, V> void writeMap(Map<K, V> var1, BiConsumer<FriendlyByteBuf, K> var2, BiConsumer<FriendlyByteBuf, V> var3) {
      this.writeVarInt(â˜ƒ.size());
      â˜ƒ.forEach((var3x, var4) -> {
         â˜ƒ.accept(this, var3x);
         â˜ƒ.accept(this, var4);
      });
   }

   public void readWithCount(Consumer<FriendlyByteBuf> var1) {
      int â˜ƒ = this.readVarInt();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         â˜ƒ.accept(this);
      }
   }

   public <T> void writeOptional(Optional<T> var1, BiConsumer<FriendlyByteBuf, T> var2) {
      if (â˜ƒ.isPresent()) {
         this.writeBoolean(true);
         â˜ƒ.accept(this, â˜ƒ.get());
      } else {
         this.writeBoolean(false);
      }
   }

   public <T> Optional<T> readOptional(Function<FriendlyByteBuf, T> var1) {
      return this.readBoolean() ? Optional.of(â˜ƒ.apply(this)) : Optional.empty();
   }

   public byte[] readByteArray() {
      return this.readByteArray(this.readableBytes());
   }

   public FriendlyByteBuf writeByteArray(byte[] var1) {
      this.writeVarInt(â˜ƒ.length);
      this.writeBytes(â˜ƒ);
      return this;
   }

   public byte[] readByteArray(int var1) {
      int â˜ƒ = this.readVarInt();
      if (â˜ƒ > â˜ƒ) {
         throw new DecoderException("ByteArray with size " + â˜ƒ + " is bigger than allowed " + â˜ƒ);
      } else {
         byte[] â˜ƒ = new byte[â˜ƒ];
         this.readBytes(â˜ƒ);
         return â˜ƒ;
      }
   }

   public FriendlyByteBuf writeVarIntArray(int[] var1) {
      this.writeVarInt(â˜ƒ.length);

      for(int â˜ƒ : â˜ƒ) {
         this.writeVarInt(â˜ƒ);
      }

      return this;
   }

   public int[] readVarIntArray() {
      return this.readVarIntArray(this.readableBytes());
   }

   public int[] readVarIntArray(int var1) {
      int â˜ƒ = this.readVarInt();
      if (â˜ƒ > â˜ƒ) {
         throw new DecoderException("VarIntArray with size " + â˜ƒ + " is bigger than allowed " + â˜ƒ);
      } else {
         int[] â˜ƒ = new int[â˜ƒ];

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
            â˜ƒ[â˜ƒx] = this.readVarInt();
         }

         return â˜ƒ;
      }
   }

   public FriendlyByteBuf writeLongArray(long[] var1) {
      this.writeVarInt(â˜ƒ.length);

      for(long â˜ƒ : â˜ƒ) {
         this.writeLong(â˜ƒ);
      }

      return this;
   }

   public long[] readLongArray() {
      return this.readLongArray(null);
   }

   public long[] readLongArray(@Nullable long[] var1) {
      return this.readLongArray(â˜ƒ, this.readableBytes() / 8);
   }

   public long[] readLongArray(@Nullable long[] var1, int var2) {
      int â˜ƒ = this.readVarInt();
      if (â˜ƒ == null || â˜ƒ.length != â˜ƒ) {
         if (â˜ƒ > â˜ƒ) {
            throw new DecoderException("LongArray with size " + â˜ƒ + " is bigger than allowed " + â˜ƒ);
         }

         â˜ƒ = new long[â˜ƒ];
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; ++â˜ƒ) {
         â˜ƒ[â˜ƒ] = this.readLong();
      }

      return â˜ƒ;
   }

   @VisibleForTesting
   public byte[] accessByteBufWithCorrectSize() {
      int â˜ƒ = this.writerIndex();
      byte[] â˜ƒx = new byte[â˜ƒ];
      this.getBytes(0, â˜ƒx);
      return â˜ƒx;
   }

   public BlockPos readBlockPos() {
      return BlockPos.of(this.readLong());
   }

   public FriendlyByteBuf writeBlockPos(BlockPos var1) {
      this.writeLong(â˜ƒ.asLong());
      return this;
   }

   public ChunkPos readChunkPos() {
      return new ChunkPos(this.readLong());
   }

   public FriendlyByteBuf writeChunkPos(ChunkPos var1) {
      this.writeLong(â˜ƒ.toLong());
      return this;
   }

   public SectionPos readSectionPos() {
      return SectionPos.of(this.readLong());
   }

   public FriendlyByteBuf writeSectionPos(SectionPos var1) {
      this.writeLong(â˜ƒ.asLong());
      return this;
   }

   public Component readComponent() {
      return Component.Serializer.fromJson(this.readUtf(262144));
   }

   public FriendlyByteBuf writeComponent(Component var1) {
      return this.writeUtf(Component.Serializer.toJson(â˜ƒ), 262144);
   }

   public <T extends Enum<T>> T readEnum(Class<T> var1) {
      return (T)â˜ƒ.getEnumConstants()[this.readVarInt()];
   }

   public FriendlyByteBuf writeEnum(Enum<?> var1) {
      return this.writeVarInt(â˜ƒ.ordinal());
   }

   public int readVarInt() {
      int â˜ƒ = 0;
      int â˜ƒx = 0;

      byte â˜ƒ;
      do {
         â˜ƒ = this.readByte();
         â˜ƒ |= (â˜ƒ & 127) << â˜ƒx++ * 7;
         if (â˜ƒx > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while((â˜ƒ & 128) == 128);

      return â˜ƒ;
   }

   public long readVarLong() {
      long â˜ƒ = 0L;
      int â˜ƒx = 0;

      byte â˜ƒ;
      do {
         â˜ƒ = this.readByte();
         â˜ƒ |= (long)(â˜ƒ & 127) << â˜ƒx++ * 7;
         if (â˜ƒx > 10) {
            throw new RuntimeException("VarLong too big");
         }
      } while((â˜ƒ & 128) == 128);

      return â˜ƒ;
   }

   public FriendlyByteBuf writeUUID(UUID var1) {
      this.writeLong(â˜ƒ.getMostSignificantBits());
      this.writeLong(â˜ƒ.getLeastSignificantBits());
      return this;
   }

   public UUID readUUID() {
      return new UUID(this.readLong(), this.readLong());
   }

   public FriendlyByteBuf writeVarInt(int var1) {
      while((â˜ƒ & -128) != 0) {
         this.writeByte(â˜ƒ & 127 | 128);
         â˜ƒ >>>= 7;
      }

      this.writeByte(â˜ƒ);
      return this;
   }

   public FriendlyByteBuf writeVarLong(long var1) {
      while((â˜ƒ & -128L) != 0L) {
         this.writeByte((int)(â˜ƒ & 127L) | 128);
         â˜ƒ >>>= 7;
      }

      this.writeByte((int)â˜ƒ);
      return this;
   }

   public FriendlyByteBuf writeNbt(@Nullable CompoundTag var1) {
      if (â˜ƒ == null) {
         this.writeByte(0);
      } else {
         try {
            NbtIo.write(â˜ƒ, new ByteBufOutputStream(this));
         } catch (IOException var3) {
            throw new EncoderException(var3);
         }
      }

      return this;
   }

   @Nullable
   public CompoundTag readNbt() {
      return this.readNbt(new NbtAccounter(2097152L));
   }

   @Nullable
   public CompoundTag readAnySizeNbt() {
      return this.readNbt(NbtAccounter.UNLIMITED);
   }

   @Nullable
   public CompoundTag readNbt(NbtAccounter var1) {
      int â˜ƒ = this.readerIndex();
      byte â˜ƒx = this.readByte();
      if (â˜ƒx == 0) {
         return null;
      } else {
         this.readerIndex(â˜ƒ);

         try {
            return NbtIo.read(new ByteBufInputStream(this), â˜ƒ);
         } catch (IOException var5) {
            throw new EncoderException(var5);
         }
      }
   }

   public FriendlyByteBuf writeItem(ItemStack var1) {
      if (â˜ƒ.isEmpty()) {
         this.writeBoolean(false);
      } else {
         this.writeBoolean(true);
         Item â˜ƒ = â˜ƒ.getItem();
         this.writeVarInt(Item.getId(â˜ƒ));
         this.writeByte(â˜ƒ.getCount());
         CompoundTag â˜ƒx = null;
         if (â˜ƒ.canBeDepleted() || â˜ƒ.shouldOverrideMultiplayerNbt()) {
            â˜ƒx = â˜ƒ.getTag();
         }

         this.writeNbt(â˜ƒx);
      }

      return this;
   }

   public ItemStack readItem() {
      if (!this.readBoolean()) {
         return ItemStack.EMPTY;
      } else {
         int â˜ƒ = this.readVarInt();
         int â˜ƒx = this.readByte();
         ItemStack â˜ƒxx = new ItemStack(Item.byId(â˜ƒ), â˜ƒx);
         â˜ƒxx.setTag(this.readNbt());
         return â˜ƒxx;
      }
   }

   public String readUtf() {
      return this.readUtf(32767);
   }

   public String readUtf(int var1) {
      int â˜ƒ = this.readVarInt();
      if (â˜ƒ > â˜ƒ * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + â˜ƒ + " > " + â˜ƒ * 4 + ")");
      } else if (â˜ƒ < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String â˜ƒ = this.toString(this.readerIndex(), â˜ƒ, StandardCharsets.UTF_8);
         this.readerIndex(this.readerIndex() + â˜ƒ);
         if (â˜ƒ.length() > â˜ƒ) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + â˜ƒ + " > " + â˜ƒ + ")");
         } else {
            return â˜ƒ;
         }
      }
   }

   public FriendlyByteBuf writeUtf(String var1) {
      return this.writeUtf(â˜ƒ, 32767);
   }

   public FriendlyByteBuf writeUtf(String var1, int var2) {
      byte[] â˜ƒ = â˜ƒ.getBytes(StandardCharsets.UTF_8);
      if (â˜ƒ.length > â˜ƒ) {
         throw new EncoderException("String too big (was " + â˜ƒ.length + " bytes encoded, max " + â˜ƒ + ")");
      } else {
         this.writeVarInt(â˜ƒ.length);
         this.writeBytes(â˜ƒ);
         return this;
      }
   }

   public ResourceLocation readResourceLocation() {
      return new ResourceLocation(this.readUtf(32767));
   }

   public FriendlyByteBuf writeResourceLocation(ResourceLocation var1) {
      this.writeUtf(â˜ƒ.toString());
      return this;
   }

   public Date readDate() {
      return new Date(this.readLong());
   }

   public FriendlyByteBuf writeDate(Date var1) {
      this.writeLong(â˜ƒ.getTime());
      return this;
   }

   public BlockHitResult readBlockHitResult() {
      BlockPos â˜ƒ = this.readBlockPos();
      Direction â˜ƒx = this.readEnum(Direction.class);
      float â˜ƒxx = this.readFloat();
      float â˜ƒxxx = this.readFloat();
      float â˜ƒxxxx = this.readFloat();
      boolean â˜ƒxxxxx = this.readBoolean();
      return new BlockHitResult(
         new Vec3((double)â˜ƒ.getX() + (double)â˜ƒxx, (double)â˜ƒ.getY() + (double)â˜ƒxxx, (double)â˜ƒ.getZ() + (double)â˜ƒxxxx), â˜ƒx, â˜ƒ, â˜ƒxxxxx
      );
   }

   public void writeBlockHitResult(BlockHitResult var1) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      this.writeBlockPos(â˜ƒ);
      this.writeEnum(â˜ƒ.getDirection());
      Vec3 â˜ƒx = â˜ƒ.getLocation();
      this.writeFloat((float)(â˜ƒx.x - (double)â˜ƒ.getX()));
      this.writeFloat((float)(â˜ƒx.y - (double)â˜ƒ.getY()));
      this.writeFloat((float)(â˜ƒx.z - (double)â˜ƒ.getZ()));
      this.writeBoolean(â˜ƒ.isInside());
   }

   public BitSet readBitSet() {
      return BitSet.valueOf(this.readLongArray());
   }

   public void writeBitSet(BitSet var1) {
      this.writeLongArray(â˜ƒ.toLongArray());
   }

   @Override
   public int capacity() {
      return this.source.capacity();
   }

   @Override
   public ByteBuf capacity(int var1) {
      return this.source.capacity(â˜ƒ);
   }

   @Override
   public int maxCapacity() {
      return this.source.maxCapacity();
   }

   @Override
   public ByteBufAllocator alloc() {
      return this.source.alloc();
   }

   @Override
   public ByteOrder order() {
      return this.source.order();
   }

   @Override
   public ByteBuf order(ByteOrder var1) {
      return this.source.order(â˜ƒ);
   }

   @Override
   public ByteBuf unwrap() {
      return this.source.unwrap();
   }

   @Override
   public boolean isDirect() {
      return this.source.isDirect();
   }

   @Override
   public boolean isReadOnly() {
      return this.source.isReadOnly();
   }

   @Override
   public ByteBuf asReadOnly() {
      return this.source.asReadOnly();
   }

   @Override
   public int readerIndex() {
      return this.source.readerIndex();
   }

   @Override
   public ByteBuf readerIndex(int var1) {
      return this.source.readerIndex(â˜ƒ);
   }

   @Override
   public int writerIndex() {
      return this.source.writerIndex();
   }

   @Override
   public ByteBuf writerIndex(int var1) {
      return this.source.writerIndex(â˜ƒ);
   }

   @Override
   public ByteBuf setIndex(int var1, int var2) {
      return this.source.setIndex(â˜ƒ, â˜ƒ);
   }

   @Override
   public int readableBytes() {
      return this.source.readableBytes();
   }

   @Override
   public int writableBytes() {
      return this.source.writableBytes();
   }

   @Override
   public int maxWritableBytes() {
      return this.source.maxWritableBytes();
   }

   @Override
   public boolean isReadable() {
      return this.source.isReadable();
   }

   @Override
   public boolean isReadable(int var1) {
      return this.source.isReadable(â˜ƒ);
   }

   @Override
   public boolean isWritable() {
      return this.source.isWritable();
   }

   @Override
   public boolean isWritable(int var1) {
      return this.source.isWritable(â˜ƒ);
   }

   @Override
   public ByteBuf clear() {
      return this.source.clear();
   }

   @Override
   public ByteBuf markReaderIndex() {
      return this.source.markReaderIndex();
   }

   @Override
   public ByteBuf resetReaderIndex() {
      return this.source.resetReaderIndex();
   }

   @Override
   public ByteBuf markWriterIndex() {
      return this.source.markWriterIndex();
   }

   @Override
   public ByteBuf resetWriterIndex() {
      return this.source.resetWriterIndex();
   }

   @Override
   public ByteBuf discardReadBytes() {
      return this.source.discardReadBytes();
   }

   @Override
   public ByteBuf discardSomeReadBytes() {
      return this.source.discardSomeReadBytes();
   }

   @Override
   public ByteBuf ensureWritable(int var1) {
      return this.source.ensureWritable(â˜ƒ);
   }

   @Override
   public int ensureWritable(int var1, boolean var2) {
      return this.source.ensureWritable(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean getBoolean(int var1) {
      return this.source.getBoolean(â˜ƒ);
   }

   @Override
   public byte getByte(int var1) {
      return this.source.getByte(â˜ƒ);
   }

   @Override
   public short getUnsignedByte(int var1) {
      return this.source.getUnsignedByte(â˜ƒ);
   }

   @Override
   public short getShort(int var1) {
      return this.source.getShort(â˜ƒ);
   }

   @Override
   public short getShortLE(int var1) {
      return this.source.getShortLE(â˜ƒ);
   }

   @Override
   public int getUnsignedShort(int var1) {
      return this.source.getUnsignedShort(â˜ƒ);
   }

   @Override
   public int getUnsignedShortLE(int var1) {
      return this.source.getUnsignedShortLE(â˜ƒ);
   }

   @Override
   public int getMedium(int var1) {
      return this.source.getMedium(â˜ƒ);
   }

   @Override
   public int getMediumLE(int var1) {
      return this.source.getMediumLE(â˜ƒ);
   }

   @Override
   public int getUnsignedMedium(int var1) {
      return this.source.getUnsignedMedium(â˜ƒ);
   }

   @Override
   public int getUnsignedMediumLE(int var1) {
      return this.source.getUnsignedMediumLE(â˜ƒ);
   }

   @Override
   public int getInt(int var1) {
      return this.source.getInt(â˜ƒ);
   }

   @Override
   public int getIntLE(int var1) {
      return this.source.getIntLE(â˜ƒ);
   }

   @Override
   public long getUnsignedInt(int var1) {
      return this.source.getUnsignedInt(â˜ƒ);
   }

   @Override
   public long getUnsignedIntLE(int var1) {
      return this.source.getUnsignedIntLE(â˜ƒ);
   }

   @Override
   public long getLong(int var1) {
      return this.source.getLong(â˜ƒ);
   }

   @Override
   public long getLongLE(int var1) {
      return this.source.getLongLE(â˜ƒ);
   }

   @Override
   public char getChar(int var1) {
      return this.source.getChar(â˜ƒ);
   }

   @Override
   public float getFloat(int var1) {
      return this.source.getFloat(â˜ƒ);
   }

   @Override
   public double getDouble(int var1) {
      return this.source.getDouble(â˜ƒ);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuf var2) {
      return this.source.getBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuf var2, int var3) {
      return this.source.getBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.source.getBytes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf getBytes(int var1, byte[] var2) {
      return this.source.getBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf getBytes(int var1, byte[] var2, int var3, int var4) {
      return this.source.getBytes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuffer var2) {
      return this.source.getBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf getBytes(int var1, OutputStream var2, int var3) throws IOException {
      return this.source.getBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getBytes(int var1, GatheringByteChannel var2, int var3) throws IOException {
      return this.source.getBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getBytes(int var1, FileChannel var2, long var3, int var5) throws IOException {
      return this.source.getBytes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public CharSequence getCharSequence(int var1, int var2, Charset var3) {
      return this.source.getCharSequence(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setBoolean(int var1, boolean var2) {
      return this.source.setBoolean(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setByte(int var1, int var2) {
      return this.source.setByte(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setShort(int var1, int var2) {
      return this.source.setShort(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setShortLE(int var1, int var2) {
      return this.source.setShortLE(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setMedium(int var1, int var2) {
      return this.source.setMedium(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setMediumLE(int var1, int var2) {
      return this.source.setMediumLE(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setInt(int var1, int var2) {
      return this.source.setInt(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setIntLE(int var1, int var2) {
      return this.source.setIntLE(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setLong(int var1, long var2) {
      return this.source.setLong(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setLongLE(int var1, long var2) {
      return this.source.setLongLE(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setChar(int var1, int var2) {
      return this.source.setChar(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setFloat(int var1, float var2) {
      return this.source.setFloat(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setDouble(int var1, double var2) {
      return this.source.setDouble(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuf var2) {
      return this.source.setBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuf var2, int var3) {
      return this.source.setBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.source.setBytes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setBytes(int var1, byte[] var2) {
      return this.source.setBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setBytes(int var1, byte[] var2, int var3, int var4) {
      return this.source.setBytes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuffer var2) {
      return this.source.setBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public int setBytes(int var1, InputStream var2, int var3) throws IOException {
      return this.source.setBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int setBytes(int var1, ScatteringByteChannel var2, int var3) throws IOException {
      return this.source.setBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int setBytes(int var1, FileChannel var2, long var3, int var5) throws IOException {
      return this.source.setBytes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf setZero(int var1, int var2) {
      return this.source.setZero(â˜ƒ, â˜ƒ);
   }

   @Override
   public int setCharSequence(int var1, CharSequence var2, Charset var3) {
      return this.source.setCharSequence(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean readBoolean() {
      return this.source.readBoolean();
   }

   @Override
   public byte readByte() {
      return this.source.readByte();
   }

   @Override
   public short readUnsignedByte() {
      return this.source.readUnsignedByte();
   }

   @Override
   public short readShort() {
      return this.source.readShort();
   }

   @Override
   public short readShortLE() {
      return this.source.readShortLE();
   }

   @Override
   public int readUnsignedShort() {
      return this.source.readUnsignedShort();
   }

   @Override
   public int readUnsignedShortLE() {
      return this.source.readUnsignedShortLE();
   }

   @Override
   public int readMedium() {
      return this.source.readMedium();
   }

   @Override
   public int readMediumLE() {
      return this.source.readMediumLE();
   }

   @Override
   public int readUnsignedMedium() {
      return this.source.readUnsignedMedium();
   }

   @Override
   public int readUnsignedMediumLE() {
      return this.source.readUnsignedMediumLE();
   }

   @Override
   public int readInt() {
      return this.source.readInt();
   }

   @Override
   public int readIntLE() {
      return this.source.readIntLE();
   }

   @Override
   public long readUnsignedInt() {
      return this.source.readUnsignedInt();
   }

   @Override
   public long readUnsignedIntLE() {
      return this.source.readUnsignedIntLE();
   }

   @Override
   public long readLong() {
      return this.source.readLong();
   }

   @Override
   public long readLongLE() {
      return this.source.readLongLE();
   }

   @Override
   public char readChar() {
      return this.source.readChar();
   }

   @Override
   public float readFloat() {
      return this.source.readFloat();
   }

   @Override
   public double readDouble() {
      return this.source.readDouble();
   }

   @Override
   public ByteBuf readBytes(int var1) {
      return this.source.readBytes(â˜ƒ);
   }

   @Override
   public ByteBuf readSlice(int var1) {
      return this.source.readSlice(â˜ƒ);
   }

   @Override
   public ByteBuf readRetainedSlice(int var1) {
      return this.source.readRetainedSlice(â˜ƒ);
   }

   @Override
   public ByteBuf readBytes(ByteBuf var1) {
      return this.source.readBytes(â˜ƒ);
   }

   @Override
   public ByteBuf readBytes(ByteBuf var1, int var2) {
      return this.source.readBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf readBytes(ByteBuf var1, int var2, int var3) {
      return this.source.readBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf readBytes(byte[] var1) {
      return this.source.readBytes(â˜ƒ);
   }

   @Override
   public ByteBuf readBytes(byte[] var1, int var2, int var3) {
      return this.source.readBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf readBytes(ByteBuffer var1) {
      return this.source.readBytes(â˜ƒ);
   }

   @Override
   public ByteBuf readBytes(OutputStream var1, int var2) throws IOException {
      return this.source.readBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public int readBytes(GatheringByteChannel var1, int var2) throws IOException {
      return this.source.readBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public CharSequence readCharSequence(int var1, Charset var2) {
      return this.source.readCharSequence(â˜ƒ, â˜ƒ);
   }

   @Override
   public int readBytes(FileChannel var1, long var2, int var4) throws IOException {
      return this.source.readBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf skipBytes(int var1) {
      return this.source.skipBytes(â˜ƒ);
   }

   @Override
   public ByteBuf writeBoolean(boolean var1) {
      return this.source.writeBoolean(â˜ƒ);
   }

   @Override
   public ByteBuf writeByte(int var1) {
      return this.source.writeByte(â˜ƒ);
   }

   @Override
   public ByteBuf writeShort(int var1) {
      return this.source.writeShort(â˜ƒ);
   }

   @Override
   public ByteBuf writeShortLE(int var1) {
      return this.source.writeShortLE(â˜ƒ);
   }

   @Override
   public ByteBuf writeMedium(int var1) {
      return this.source.writeMedium(â˜ƒ);
   }

   @Override
   public ByteBuf writeMediumLE(int var1) {
      return this.source.writeMediumLE(â˜ƒ);
   }

   @Override
   public ByteBuf writeInt(int var1) {
      return this.source.writeInt(â˜ƒ);
   }

   @Override
   public ByteBuf writeIntLE(int var1) {
      return this.source.writeIntLE(â˜ƒ);
   }

   @Override
   public ByteBuf writeLong(long var1) {
      return this.source.writeLong(â˜ƒ);
   }

   @Override
   public ByteBuf writeLongLE(long var1) {
      return this.source.writeLongLE(â˜ƒ);
   }

   @Override
   public ByteBuf writeChar(int var1) {
      return this.source.writeChar(â˜ƒ);
   }

   @Override
   public ByteBuf writeFloat(float var1) {
      return this.source.writeFloat(â˜ƒ);
   }

   @Override
   public ByteBuf writeDouble(double var1) {
      return this.source.writeDouble(â˜ƒ);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf var1) {
      return this.source.writeBytes(â˜ƒ);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf var1, int var2) {
      return this.source.writeBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf var1, int var2, int var3) {
      return this.source.writeBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf writeBytes(byte[] var1) {
      return this.source.writeBytes(â˜ƒ);
   }

   @Override
   public ByteBuf writeBytes(byte[] var1, int var2, int var3) {
      return this.source.writeBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf writeBytes(ByteBuffer var1) {
      return this.source.writeBytes(â˜ƒ);
   }

   @Override
   public int writeBytes(InputStream var1, int var2) throws IOException {
      return this.source.writeBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public int writeBytes(ScatteringByteChannel var1, int var2) throws IOException {
      return this.source.writeBytes(â˜ƒ, â˜ƒ);
   }

   @Override
   public int writeBytes(FileChannel var1, long var2, int var4) throws IOException {
      return this.source.writeBytes(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf writeZero(int var1) {
      return this.source.writeZero(â˜ƒ);
   }

   @Override
   public int writeCharSequence(CharSequence var1, Charset var2) {
      return this.source.writeCharSequence(â˜ƒ, â˜ƒ);
   }

   @Override
   public int indexOf(int var1, int var2, byte var3) {
      return this.source.indexOf(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int bytesBefore(byte var1) {
      return this.source.bytesBefore(â˜ƒ);
   }

   @Override
   public int bytesBefore(int var1, byte var2) {
      return this.source.bytesBefore(â˜ƒ, â˜ƒ);
   }

   @Override
   public int bytesBefore(int var1, int var2, byte var3) {
      return this.source.bytesBefore(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int forEachByte(ByteProcessor var1) {
      return this.source.forEachByte(â˜ƒ);
   }

   @Override
   public int forEachByte(int var1, int var2, ByteProcessor var3) {
      return this.source.forEachByte(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int forEachByteDesc(ByteProcessor var1) {
      return this.source.forEachByteDesc(â˜ƒ);
   }

   @Override
   public int forEachByteDesc(int var1, int var2, ByteProcessor var3) {
      return this.source.forEachByteDesc(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf copy() {
      return this.source.copy();
   }

   @Override
   public ByteBuf copy(int var1, int var2) {
      return this.source.copy(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf slice() {
      return this.source.slice();
   }

   @Override
   public ByteBuf retainedSlice() {
      return this.source.retainedSlice();
   }

   @Override
   public ByteBuf slice(int var1, int var2) {
      return this.source.slice(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf retainedSlice(int var1, int var2) {
      return this.source.retainedSlice(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuf duplicate() {
      return this.source.duplicate();
   }

   @Override
   public ByteBuf retainedDuplicate() {
      return this.source.retainedDuplicate();
   }

   @Override
   public int nioBufferCount() {
      return this.source.nioBufferCount();
   }

   @Override
   public ByteBuffer nioBuffer() {
      return this.source.nioBuffer();
   }

   @Override
   public ByteBuffer nioBuffer(int var1, int var2) {
      return this.source.nioBuffer(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuffer internalNioBuffer(int var1, int var2) {
      return this.source.internalNioBuffer(â˜ƒ, â˜ƒ);
   }

   @Override
   public ByteBuffer[] nioBuffers() {
      return this.source.nioBuffers();
   }

   @Override
   public ByteBuffer[] nioBuffers(int var1, int var2) {
      return this.source.nioBuffers(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean hasArray() {
      return this.source.hasArray();
   }

   @Override
   public byte[] array() {
      return this.source.array();
   }

   @Override
   public int arrayOffset() {
      return this.source.arrayOffset();
   }

   @Override
   public boolean hasMemoryAddress() {
      return this.source.hasMemoryAddress();
   }

   @Override
   public long memoryAddress() {
      return this.source.memoryAddress();
   }

   @Override
   public String toString(Charset var1) {
      return this.source.toString(â˜ƒ);
   }

   @Override
   public String toString(int var1, int var2, Charset var3) {
      return this.source.toString(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int hashCode() {
      return this.source.hashCode();
   }

   @Override
   public boolean equals(Object var1) {
      return this.source.equals(â˜ƒ);
   }

   @Override
   public int compareTo(ByteBuf var1) {
      return this.source.compareTo(â˜ƒ);
   }

   @Override
   public String toString() {
      return this.source.toString();
   }

   @Override
   public ByteBuf retain(int var1) {
      return this.source.retain(â˜ƒ);
   }

   @Override
   public ByteBuf retain() {
      return this.source.retain();
   }

   @Override
   public ByteBuf touch() {
      return this.source.touch();
   }

   @Override
   public ByteBuf touch(Object var1) {
      return this.source.touch(â˜ƒ);
   }

   @Override
   public int refCnt() {
      return this.source.refCnt();
   }

   @Override
   public boolean release() {
      return this.source.release();
   }

   @Override
   public boolean release(int var1) {
      return this.source.release(â˜ƒ);
   }
}
