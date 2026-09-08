package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
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
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class PacketBuffer extends ByteBuf {
   private final ByteBuf field_150794_a;

   public PacketBuffer(ByteBuf var1) {
      this.field_150794_a = ☃;
   }

   public static int func_150790_a(int var0) {
      for(int ☃ = 1; ☃ < 5; ++☃) {
         if ((☃ & -1 << ☃ * 7) == 0) {
            return ☃;
         }
      }

      return 5;
   }

   public PacketBuffer func_179250_a(byte[] var1) {
      this.func_150787_b(☃.length);
      this.writeBytes(☃);
      return this;
   }

   public byte[] func_179251_a() {
      return this.func_189425_b(this.readableBytes());
   }

   public byte[] func_189425_b(int var1) {
      int ☃ = this.func_150792_a();
      if (☃ > ☃) {
         throw new DecoderException("ByteArray with size " + ☃ + " is bigger than allowed " + ☃);
      } else {
         byte[] ☃ = new byte[☃];
         this.readBytes(☃);
         return ☃;
      }
   }

   public PacketBuffer func_186875_a(int[] var1) {
      this.func_150787_b(☃.length);

      for(int ☃ : ☃) {
         this.func_150787_b(☃);
      }

      return this;
   }

   public int[] func_186863_b() {
      return this.func_189424_c(this.readableBytes());
   }

   public int[] func_189424_c(int var1) {
      int ☃ = this.func_150792_a();
      if (☃ > ☃) {
         throw new DecoderException("VarIntArray with size " + ☃ + " is bigger than allowed " + ☃);
      } else {
         int[] ☃ = new int[☃];

         for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
            ☃[☃x] = this.func_150792_a();
         }

         return ☃;
      }
   }

   public PacketBuffer func_186865_a(long[] var1) {
      this.func_150787_b(☃.length);

      for(long ☃ : ☃) {
         this.writeLong(☃);
      }

      return this;
   }

   public BlockPos func_179259_c() {
      return BlockPos.func_177969_a(this.readLong());
   }

   public PacketBuffer func_179255_a(BlockPos var1) {
      this.writeLong(☃.func_177986_g());
      return this;
   }

   public ITextComponent func_179258_d() {
      return ITextComponent.Serializer.func_150699_a(this.func_150789_c(262144));
   }

   public PacketBuffer func_179256_a(ITextComponent var1) {
      return this.func_211400_a(ITextComponent.Serializer.func_150696_a(☃), 262144);
   }

   public <T extends Enum<T>> T func_179257_a(Class<T> var1) {
      return (T)☃.getEnumConstants()[this.func_150792_a()];
   }

   public PacketBuffer func_179249_a(Enum<?> var1) {
      return this.func_150787_b(☃.ordinal());
   }

   public int func_150792_a() {
      int ☃ = 0;
      int ☃x = 0;

      byte ☃;
      do {
         ☃ = this.readByte();
         ☃ |= (☃ & 127) << ☃x++ * 7;
         if (☃x > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while((☃ & 128) == 128);

      return ☃;
   }

   public long func_179260_f() {
      long ☃ = 0L;
      int ☃x = 0;

      byte ☃;
      do {
         ☃ = this.readByte();
         ☃ |= (long)(☃ & 127) << ☃x++ * 7;
         if (☃x > 10) {
            throw new RuntimeException("VarLong too big");
         }
      } while((☃ & 128) == 128);

      return ☃;
   }

   public PacketBuffer func_179252_a(UUID var1) {
      this.writeLong(☃.getMostSignificantBits());
      this.writeLong(☃.getLeastSignificantBits());
      return this;
   }

   public UUID func_179253_g() {
      return new UUID(this.readLong(), this.readLong());
   }

   public PacketBuffer func_150787_b(int var1) {
      while((☃ & -128) != 0) {
         this.writeByte(☃ & 127 | 128);
         ☃ >>>= 7;
      }

      this.writeByte(☃);
      return this;
   }

   public PacketBuffer func_179254_b(long var1) {
      while((☃ & -128L) != 0L) {
         this.writeByte((int)(☃ & 127L) | 128);
         ☃ >>>= 7;
      }

      this.writeByte((int)☃);
      return this;
   }

   public PacketBuffer func_150786_a(@Nullable NBTTagCompound var1) {
      if (☃ == null) {
         this.writeByte(0);
      } else {
         try {
            CompressedStreamTools.func_74800_a(☃, new ByteBufOutputStream(this));
         } catch (IOException var3) {
            throw new EncoderException(var3);
         }
      }

      return this;
   }

   @Nullable
   public NBTTagCompound func_150793_b() {
      int ☃ = this.readerIndex();
      byte ☃x = this.readByte();
      if (☃x == 0) {
         return null;
      } else {
         this.readerIndex(☃);

         try {
            return CompressedStreamTools.func_152456_a(new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
         } catch (IOException var4) {
            throw new EncoderException(var4);
         }
      }
   }

   public PacketBuffer func_150788_a(ItemStack var1) {
      if (☃.func_190926_b()) {
         this.writeBoolean(false);
      } else {
         this.writeBoolean(true);
         Item ☃ = ☃.func_77973_b();
         this.func_150787_b(Item.func_150891_b(☃));
         this.writeByte(☃.func_190916_E());
         NBTTagCompound ☃x = null;
         if (☃.func_77645_m() || ☃.func_77651_p()) {
            ☃x = ☃.func_77978_p();
         }

         this.func_150786_a(☃x);
      }

      return this;
   }

   public ItemStack func_150791_c() {
      if (!this.readBoolean()) {
         return ItemStack.field_190927_a;
      } else {
         int ☃ = this.func_150792_a();
         int ☃x = this.readByte();
         ItemStack ☃xx = new ItemStack(Item.func_150899_d(☃), ☃x);
         ☃xx.func_77982_d(this.func_150793_b());
         return ☃xx;
      }
   }

   public String func_150789_c(int var1) {
      int ☃ = this.func_150792_a();
      if (☃ > ☃ * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + ☃ + " > " + ☃ * 4 + ")");
      } else if (☃ < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String ☃ = this.toString(this.readerIndex(), ☃, StandardCharsets.UTF_8);
         this.readerIndex(this.readerIndex() + ☃);
         if (☃.length() > ☃) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + ☃ + " > " + ☃ + ")");
         } else {
            return ☃;
         }
      }
   }

   public PacketBuffer func_180714_a(String var1) {
      return this.func_211400_a(☃, 32767);
   }

   public PacketBuffer func_211400_a(String var1, int var2) {
      byte[] ☃ = ☃.getBytes(StandardCharsets.UTF_8);
      if (☃.length > ☃) {
         throw new EncoderException("String too big (was " + ☃.length + " bytes encoded, max " + ☃ + ")");
      } else {
         this.func_150787_b(☃.length);
         this.writeBytes(☃);
         return this;
      }
   }

   public ResourceLocation func_192575_l() {
      return new ResourceLocation(this.func_150789_c(32767));
   }

   public PacketBuffer func_192572_a(ResourceLocation var1) {
      this.func_180714_a(☃.toString());
      return this;
   }

   public Date func_192573_m() {
      return new Date(this.readLong());
   }

   public PacketBuffer func_192574_a(Date var1) {
      this.writeLong(☃.getTime());
      return this;
   }

   @Override
   public int capacity() {
      return this.field_150794_a.capacity();
   }

   @Override
   public ByteBuf capacity(int var1) {
      return this.field_150794_a.capacity(☃);
   }

   @Override
   public int maxCapacity() {
      return this.field_150794_a.maxCapacity();
   }

   @Override
   public ByteBufAllocator alloc() {
      return this.field_150794_a.alloc();
   }

   @Override
   public ByteOrder order() {
      return this.field_150794_a.order();
   }

   @Override
   public ByteBuf order(ByteOrder var1) {
      return this.field_150794_a.order(☃);
   }

   @Override
   public ByteBuf unwrap() {
      return this.field_150794_a.unwrap();
   }

   @Override
   public boolean isDirect() {
      return this.field_150794_a.isDirect();
   }

   @Override
   public boolean isReadOnly() {
      return this.field_150794_a.isReadOnly();
   }

   @Override
   public ByteBuf asReadOnly() {
      return this.field_150794_a.asReadOnly();
   }

   @Override
   public int readerIndex() {
      return this.field_150794_a.readerIndex();
   }

   @Override
   public ByteBuf readerIndex(int var1) {
      return this.field_150794_a.readerIndex(☃);
   }

   @Override
   public int writerIndex() {
      return this.field_150794_a.writerIndex();
   }

   @Override
   public ByteBuf writerIndex(int var1) {
      return this.field_150794_a.writerIndex(☃);
   }

   @Override
   public ByteBuf setIndex(int var1, int var2) {
      return this.field_150794_a.setIndex(☃, ☃);
   }

   @Override
   public int readableBytes() {
      return this.field_150794_a.readableBytes();
   }

   @Override
   public int writableBytes() {
      return this.field_150794_a.writableBytes();
   }

   @Override
   public int maxWritableBytes() {
      return this.field_150794_a.maxWritableBytes();
   }

   @Override
   public boolean isReadable() {
      return this.field_150794_a.isReadable();
   }

   @Override
   public boolean isReadable(int var1) {
      return this.field_150794_a.isReadable(☃);
   }

   @Override
   public boolean isWritable() {
      return this.field_150794_a.isWritable();
   }

   @Override
   public boolean isWritable(int var1) {
      return this.field_150794_a.isWritable(☃);
   }

   @Override
   public ByteBuf clear() {
      return this.field_150794_a.clear();
   }

   @Override
   public ByteBuf markReaderIndex() {
      return this.field_150794_a.markReaderIndex();
   }

   @Override
   public ByteBuf resetReaderIndex() {
      return this.field_150794_a.resetReaderIndex();
   }

   @Override
   public ByteBuf markWriterIndex() {
      return this.field_150794_a.markWriterIndex();
   }

   @Override
   public ByteBuf resetWriterIndex() {
      return this.field_150794_a.resetWriterIndex();
   }

   @Override
   public ByteBuf discardReadBytes() {
      return this.field_150794_a.discardReadBytes();
   }

   @Override
   public ByteBuf discardSomeReadBytes() {
      return this.field_150794_a.discardSomeReadBytes();
   }

   @Override
   public ByteBuf ensureWritable(int var1) {
      return this.field_150794_a.ensureWritable(☃);
   }

   @Override
   public int ensureWritable(int var1, boolean var2) {
      return this.field_150794_a.ensureWritable(☃, ☃);
   }

   @Override
   public boolean getBoolean(int var1) {
      return this.field_150794_a.getBoolean(☃);
   }

   @Override
   public byte getByte(int var1) {
      return this.field_150794_a.getByte(☃);
   }

   @Override
   public short getUnsignedByte(int var1) {
      return this.field_150794_a.getUnsignedByte(☃);
   }

   @Override
   public short getShort(int var1) {
      return this.field_150794_a.getShort(☃);
   }

   @Override
   public short getShortLE(int var1) {
      return this.field_150794_a.getShortLE(☃);
   }

   @Override
   public int getUnsignedShort(int var1) {
      return this.field_150794_a.getUnsignedShort(☃);
   }

   @Override
   public int getUnsignedShortLE(int var1) {
      return this.field_150794_a.getUnsignedShortLE(☃);
   }

   @Override
   public int getMedium(int var1) {
      return this.field_150794_a.getMedium(☃);
   }

   @Override
   public int getMediumLE(int var1) {
      return this.field_150794_a.getMediumLE(☃);
   }

   @Override
   public int getUnsignedMedium(int var1) {
      return this.field_150794_a.getUnsignedMedium(☃);
   }

   @Override
   public int getUnsignedMediumLE(int var1) {
      return this.field_150794_a.getUnsignedMediumLE(☃);
   }

   @Override
   public int getInt(int var1) {
      return this.field_150794_a.getInt(☃);
   }

   @Override
   public int getIntLE(int var1) {
      return this.field_150794_a.getIntLE(☃);
   }

   @Override
   public long getUnsignedInt(int var1) {
      return this.field_150794_a.getUnsignedInt(☃);
   }

   @Override
   public long getUnsignedIntLE(int var1) {
      return this.field_150794_a.getUnsignedIntLE(☃);
   }

   @Override
   public long getLong(int var1) {
      return this.field_150794_a.getLong(☃);
   }

   @Override
   public long getLongLE(int var1) {
      return this.field_150794_a.getLongLE(☃);
   }

   @Override
   public char getChar(int var1) {
      return this.field_150794_a.getChar(☃);
   }

   @Override
   public float getFloat(int var1) {
      return this.field_150794_a.getFloat(☃);
   }

   @Override
   public double getDouble(int var1) {
      return this.field_150794_a.getDouble(☃);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuf var2) {
      return this.field_150794_a.getBytes(☃, ☃);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuf var2, int var3) {
      return this.field_150794_a.getBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.field_150794_a.getBytes(☃, ☃, ☃, ☃);
   }

   @Override
   public ByteBuf getBytes(int var1, byte[] var2) {
      return this.field_150794_a.getBytes(☃, ☃);
   }

   @Override
   public ByteBuf getBytes(int var1, byte[] var2, int var3, int var4) {
      return this.field_150794_a.getBytes(☃, ☃, ☃, ☃);
   }

   @Override
   public ByteBuf getBytes(int var1, ByteBuffer var2) {
      return this.field_150794_a.getBytes(☃, ☃);
   }

   @Override
   public ByteBuf getBytes(int var1, OutputStream var2, int var3) throws IOException {
      return this.field_150794_a.getBytes(☃, ☃, ☃);
   }

   @Override
   public int getBytes(int var1, GatheringByteChannel var2, int var3) throws IOException {
      return this.field_150794_a.getBytes(☃, ☃, ☃);
   }

   @Override
   public int getBytes(int var1, FileChannel var2, long var3, int var5) throws IOException {
      return this.field_150794_a.getBytes(☃, ☃, ☃, ☃);
   }

   @Override
   public CharSequence getCharSequence(int var1, int var2, Charset var3) {
      return this.field_150794_a.getCharSequence(☃, ☃, ☃);
   }

   @Override
   public ByteBuf setBoolean(int var1, boolean var2) {
      return this.field_150794_a.setBoolean(☃, ☃);
   }

   @Override
   public ByteBuf setByte(int var1, int var2) {
      return this.field_150794_a.setByte(☃, ☃);
   }

   @Override
   public ByteBuf setShort(int var1, int var2) {
      return this.field_150794_a.setShort(☃, ☃);
   }

   @Override
   public ByteBuf setShortLE(int var1, int var2) {
      return this.field_150794_a.setShortLE(☃, ☃);
   }

   @Override
   public ByteBuf setMedium(int var1, int var2) {
      return this.field_150794_a.setMedium(☃, ☃);
   }

   @Override
   public ByteBuf setMediumLE(int var1, int var2) {
      return this.field_150794_a.setMediumLE(☃, ☃);
   }

   @Override
   public ByteBuf setInt(int var1, int var2) {
      return this.field_150794_a.setInt(☃, ☃);
   }

   @Override
   public ByteBuf setIntLE(int var1, int var2) {
      return this.field_150794_a.setIntLE(☃, ☃);
   }

   @Override
   public ByteBuf setLong(int var1, long var2) {
      return this.field_150794_a.setLong(☃, ☃);
   }

   @Override
   public ByteBuf setLongLE(int var1, long var2) {
      return this.field_150794_a.setLongLE(☃, ☃);
   }

   @Override
   public ByteBuf setChar(int var1, int var2) {
      return this.field_150794_a.setChar(☃, ☃);
   }

   @Override
   public ByteBuf setFloat(int var1, float var2) {
      return this.field_150794_a.setFloat(☃, ☃);
   }

   @Override
   public ByteBuf setDouble(int var1, double var2) {
      return this.field_150794_a.setDouble(☃, ☃);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuf var2) {
      return this.field_150794_a.setBytes(☃, ☃);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuf var2, int var3) {
      return this.field_150794_a.setBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.field_150794_a.setBytes(☃, ☃, ☃, ☃);
   }

   @Override
   public ByteBuf setBytes(int var1, byte[] var2) {
      return this.field_150794_a.setBytes(☃, ☃);
   }

   @Override
   public ByteBuf setBytes(int var1, byte[] var2, int var3, int var4) {
      return this.field_150794_a.setBytes(☃, ☃, ☃, ☃);
   }

   @Override
   public ByteBuf setBytes(int var1, ByteBuffer var2) {
      return this.field_150794_a.setBytes(☃, ☃);
   }

   @Override
   public int setBytes(int var1, InputStream var2, int var3) throws IOException {
      return this.field_150794_a.setBytes(☃, ☃, ☃);
   }

   @Override
   public int setBytes(int var1, ScatteringByteChannel var2, int var3) throws IOException {
      return this.field_150794_a.setBytes(☃, ☃, ☃);
   }

   @Override
   public int setBytes(int var1, FileChannel var2, long var3, int var5) throws IOException {
      return this.field_150794_a.setBytes(☃, ☃, ☃, ☃);
   }

   @Override
   public ByteBuf setZero(int var1, int var2) {
      return this.field_150794_a.setZero(☃, ☃);
   }

   @Override
   public int setCharSequence(int var1, CharSequence var2, Charset var3) {
      return this.field_150794_a.setCharSequence(☃, ☃, ☃);
   }

   @Override
   public boolean readBoolean() {
      return this.field_150794_a.readBoolean();
   }

   @Override
   public byte readByte() {
      return this.field_150794_a.readByte();
   }

   @Override
   public short readUnsignedByte() {
      return this.field_150794_a.readUnsignedByte();
   }

   @Override
   public short readShort() {
      return this.field_150794_a.readShort();
   }

   @Override
   public short readShortLE() {
      return this.field_150794_a.readShortLE();
   }

   @Override
   public int readUnsignedShort() {
      return this.field_150794_a.readUnsignedShort();
   }

   @Override
   public int readUnsignedShortLE() {
      return this.field_150794_a.readUnsignedShortLE();
   }

   @Override
   public int readMedium() {
      return this.field_150794_a.readMedium();
   }

   @Override
   public int readMediumLE() {
      return this.field_150794_a.readMediumLE();
   }

   @Override
   public int readUnsignedMedium() {
      return this.field_150794_a.readUnsignedMedium();
   }

   @Override
   public int readUnsignedMediumLE() {
      return this.field_150794_a.readUnsignedMediumLE();
   }

   @Override
   public int readInt() {
      return this.field_150794_a.readInt();
   }

   @Override
   public int readIntLE() {
      return this.field_150794_a.readIntLE();
   }

   @Override
   public long readUnsignedInt() {
      return this.field_150794_a.readUnsignedInt();
   }

   @Override
   public long readUnsignedIntLE() {
      return this.field_150794_a.readUnsignedIntLE();
   }

   @Override
   public long readLong() {
      return this.field_150794_a.readLong();
   }

   @Override
   public long readLongLE() {
      return this.field_150794_a.readLongLE();
   }

   @Override
   public char readChar() {
      return this.field_150794_a.readChar();
   }

   @Override
   public float readFloat() {
      return this.field_150794_a.readFloat();
   }

   @Override
   public double readDouble() {
      return this.field_150794_a.readDouble();
   }

   @Override
   public ByteBuf readBytes(int var1) {
      return this.field_150794_a.readBytes(☃);
   }

   @Override
   public ByteBuf readSlice(int var1) {
      return this.field_150794_a.readSlice(☃);
   }

   @Override
   public ByteBuf readRetainedSlice(int var1) {
      return this.field_150794_a.readRetainedSlice(☃);
   }

   @Override
   public ByteBuf readBytes(ByteBuf var1) {
      return this.field_150794_a.readBytes(☃);
   }

   @Override
   public ByteBuf readBytes(ByteBuf var1, int var2) {
      return this.field_150794_a.readBytes(☃, ☃);
   }

   @Override
   public ByteBuf readBytes(ByteBuf var1, int var2, int var3) {
      return this.field_150794_a.readBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf readBytes(byte[] var1) {
      return this.field_150794_a.readBytes(☃);
   }

   @Override
   public ByteBuf readBytes(byte[] var1, int var2, int var3) {
      return this.field_150794_a.readBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf readBytes(ByteBuffer var1) {
      return this.field_150794_a.readBytes(☃);
   }

   @Override
   public ByteBuf readBytes(OutputStream var1, int var2) throws IOException {
      return this.field_150794_a.readBytes(☃, ☃);
   }

   @Override
   public int readBytes(GatheringByteChannel var1, int var2) throws IOException {
      return this.field_150794_a.readBytes(☃, ☃);
   }

   @Override
   public CharSequence readCharSequence(int var1, Charset var2) {
      return this.field_150794_a.readCharSequence(☃, ☃);
   }

   @Override
   public int readBytes(FileChannel var1, long var2, int var4) throws IOException {
      return this.field_150794_a.readBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf skipBytes(int var1) {
      return this.field_150794_a.skipBytes(☃);
   }

   @Override
   public ByteBuf writeBoolean(boolean var1) {
      return this.field_150794_a.writeBoolean(☃);
   }

   @Override
   public ByteBuf writeByte(int var1) {
      return this.field_150794_a.writeByte(☃);
   }

   @Override
   public ByteBuf writeShort(int var1) {
      return this.field_150794_a.writeShort(☃);
   }

   @Override
   public ByteBuf writeShortLE(int var1) {
      return this.field_150794_a.writeShortLE(☃);
   }

   @Override
   public ByteBuf writeMedium(int var1) {
      return this.field_150794_a.writeMedium(☃);
   }

   @Override
   public ByteBuf writeMediumLE(int var1) {
      return this.field_150794_a.writeMediumLE(☃);
   }

   @Override
   public ByteBuf writeInt(int var1) {
      return this.field_150794_a.writeInt(☃);
   }

   @Override
   public ByteBuf writeIntLE(int var1) {
      return this.field_150794_a.writeIntLE(☃);
   }

   @Override
   public ByteBuf writeLong(long var1) {
      return this.field_150794_a.writeLong(☃);
   }

   @Override
   public ByteBuf writeLongLE(long var1) {
      return this.field_150794_a.writeLongLE(☃);
   }

   @Override
   public ByteBuf writeChar(int var1) {
      return this.field_150794_a.writeChar(☃);
   }

   @Override
   public ByteBuf writeFloat(float var1) {
      return this.field_150794_a.writeFloat(☃);
   }

   @Override
   public ByteBuf writeDouble(double var1) {
      return this.field_150794_a.writeDouble(☃);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf var1) {
      return this.field_150794_a.writeBytes(☃);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf var1, int var2) {
      return this.field_150794_a.writeBytes(☃, ☃);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf var1, int var2, int var3) {
      return this.field_150794_a.writeBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf writeBytes(byte[] var1) {
      return this.field_150794_a.writeBytes(☃);
   }

   @Override
   public ByteBuf writeBytes(byte[] var1, int var2, int var3) {
      return this.field_150794_a.writeBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf writeBytes(ByteBuffer var1) {
      return this.field_150794_a.writeBytes(☃);
   }

   @Override
   public int writeBytes(InputStream var1, int var2) throws IOException {
      return this.field_150794_a.writeBytes(☃, ☃);
   }

   @Override
   public int writeBytes(ScatteringByteChannel var1, int var2) throws IOException {
      return this.field_150794_a.writeBytes(☃, ☃);
   }

   @Override
   public int writeBytes(FileChannel var1, long var2, int var4) throws IOException {
      return this.field_150794_a.writeBytes(☃, ☃, ☃);
   }

   @Override
   public ByteBuf writeZero(int var1) {
      return this.field_150794_a.writeZero(☃);
   }

   @Override
   public int writeCharSequence(CharSequence var1, Charset var2) {
      return this.field_150794_a.writeCharSequence(☃, ☃);
   }

   @Override
   public int indexOf(int var1, int var2, byte var3) {
      return this.field_150794_a.indexOf(☃, ☃, ☃);
   }

   @Override
   public int bytesBefore(byte var1) {
      return this.field_150794_a.bytesBefore(☃);
   }

   @Override
   public int bytesBefore(int var1, byte var2) {
      return this.field_150794_a.bytesBefore(☃, ☃);
   }

   @Override
   public int bytesBefore(int var1, int var2, byte var3) {
      return this.field_150794_a.bytesBefore(☃, ☃, ☃);
   }

   @Override
   public int forEachByte(ByteProcessor var1) {
      return this.field_150794_a.forEachByte(☃);
   }

   @Override
   public int forEachByte(int var1, int var2, ByteProcessor var3) {
      return this.field_150794_a.forEachByte(☃, ☃, ☃);
   }

   @Override
   public int forEachByteDesc(ByteProcessor var1) {
      return this.field_150794_a.forEachByteDesc(☃);
   }

   @Override
   public int forEachByteDesc(int var1, int var2, ByteProcessor var3) {
      return this.field_150794_a.forEachByteDesc(☃, ☃, ☃);
   }

   @Override
   public ByteBuf copy() {
      return this.field_150794_a.copy();
   }

   @Override
   public ByteBuf copy(int var1, int var2) {
      return this.field_150794_a.copy(☃, ☃);
   }

   @Override
   public ByteBuf slice() {
      return this.field_150794_a.slice();
   }

   @Override
   public ByteBuf retainedSlice() {
      return this.field_150794_a.retainedSlice();
   }

   @Override
   public ByteBuf slice(int var1, int var2) {
      return this.field_150794_a.slice(☃, ☃);
   }

   @Override
   public ByteBuf retainedSlice(int var1, int var2) {
      return this.field_150794_a.retainedSlice(☃, ☃);
   }

   @Override
   public ByteBuf duplicate() {
      return this.field_150794_a.duplicate();
   }

   @Override
   public ByteBuf retainedDuplicate() {
      return this.field_150794_a.retainedDuplicate();
   }

   @Override
   public int nioBufferCount() {
      return this.field_150794_a.nioBufferCount();
   }

   @Override
   public ByteBuffer nioBuffer() {
      return this.field_150794_a.nioBuffer();
   }

   @Override
   public ByteBuffer nioBuffer(int var1, int var2) {
      return this.field_150794_a.nioBuffer(☃, ☃);
   }

   @Override
   public ByteBuffer internalNioBuffer(int var1, int var2) {
      return this.field_150794_a.internalNioBuffer(☃, ☃);
   }

   @Override
   public ByteBuffer[] nioBuffers() {
      return this.field_150794_a.nioBuffers();
   }

   @Override
   public ByteBuffer[] nioBuffers(int var1, int var2) {
      return this.field_150794_a.nioBuffers(☃, ☃);
   }

   @Override
   public boolean hasArray() {
      return this.field_150794_a.hasArray();
   }

   @Override
   public byte[] array() {
      return this.field_150794_a.array();
   }

   @Override
   public int arrayOffset() {
      return this.field_150794_a.arrayOffset();
   }

   @Override
   public boolean hasMemoryAddress() {
      return this.field_150794_a.hasMemoryAddress();
   }

   @Override
   public long memoryAddress() {
      return this.field_150794_a.memoryAddress();
   }

   @Override
   public String toString(Charset var1) {
      return this.field_150794_a.toString(☃);
   }

   @Override
   public String toString(int var1, int var2, Charset var3) {
      return this.field_150794_a.toString(☃, ☃, ☃);
   }

   @Override
   public int hashCode() {
      return this.field_150794_a.hashCode();
   }

   @Override
   public boolean equals(Object var1) {
      return this.field_150794_a.equals(☃);
   }

   @Override
   public int compareTo(ByteBuf var1) {
      return this.field_150794_a.compareTo(☃);
   }

   @Override
   public String toString() {
      return this.field_150794_a.toString();
   }

   @Override
   public ByteBuf retain(int var1) {
      return this.field_150794_a.retain(☃);
   }

   @Override
   public ByteBuf retain() {
      return this.field_150794_a.retain();
   }

   @Override
   public ByteBuf touch() {
      return this.field_150794_a.touch();
   }

   @Override
   public ByteBuf touch(Object var1) {
      return this.field_150794_a.touch(☃);
   }

   @Override
   public int refCnt() {
      return this.field_150794_a.refCnt();
   }

   @Override
   public boolean release() {
      return this.field_150794_a.release();
   }

   @Override
   public boolean release(int var1) {
      return this.field_150794_a.release(☃);
   }
}
