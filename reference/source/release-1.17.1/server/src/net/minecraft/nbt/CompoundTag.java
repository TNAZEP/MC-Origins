package net.minecraft.nbt;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;

public class CompoundTag implements Tag {
   public static final Codec<CompoundTag> CODEC = Codec.PASSTHROUGH.comapFlatMap(var0 -> {
      Tag â˜ƒ = var0.convert(NbtOps.INSTANCE).getValue();
      return â˜ƒ instanceof CompoundTag ? DataResult.success((CompoundTag)â˜ƒ) : DataResult.error("Not a compound tag: " + â˜ƒ);
   }, var0 -> new Dynamic<>(NbtOps.INSTANCE, var0));
   private static final int SELF_SIZE_IN_BITS = 384;
   private static final int MAP_ENTRY_SIZE_IN_BITS = 256;
   public static final TagType<CompoundTag> TYPE = new TagType<CompoundTag>() {
      public CompoundTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(384L);
         if (â˜ƒ > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            Map<String, Tag> â˜ƒ = Maps.newHashMap();

            byte â˜ƒ;
            while((â˜ƒ = CompoundTag.readNamedTagType(â˜ƒ, â˜ƒ)) != 0) {
               String â˜ƒx = CompoundTag.readNamedTagName(â˜ƒ, â˜ƒ);
               â˜ƒ.accountBits((long)(224 + 16 * â˜ƒx.length()));
               Tag â˜ƒxx = CompoundTag.readNamedTagData(TagTypes.getType(â˜ƒ), â˜ƒx, â˜ƒ, â˜ƒ + 1, â˜ƒ);
               if (â˜ƒ.put(â˜ƒx, â˜ƒxx) != null) {
                  â˜ƒ.accountBits(288L);
               }
            }

            return new CompoundTag(â˜ƒ);
         }
      }

      @Override
      public String getName() {
         return "COMPOUND";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Compound";
      }
   };
   private final Map<String, Tag> tags;

   protected CompoundTag(Map<String, Tag> var1) {
      this.tags = â˜ƒ;
   }

   public CompoundTag() {
      this(Maps.newHashMap());
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      for(String â˜ƒ : this.tags.keySet()) {
         Tag â˜ƒx = (Tag)this.tags.get(â˜ƒ);
         writeNamedTag(â˜ƒ, â˜ƒx, â˜ƒ);
      }

      â˜ƒ.writeByte(0);
   }

   public Set<String> getAllKeys() {
      return this.tags.keySet();
   }

   @Override
   public byte getId() {
      return 10;
   }

   @Override
   public TagType<CompoundTag> getType() {
      return TYPE;
   }

   public int size() {
      return this.tags.size();
   }

   @Nullable
   public Tag put(String var1, Tag var2) {
      return (Tag)this.tags.put(â˜ƒ, â˜ƒ);
   }

   public void putByte(String var1, byte var2) {
      this.tags.put(â˜ƒ, ByteTag.valueOf(â˜ƒ));
   }

   public void putShort(String var1, short var2) {
      this.tags.put(â˜ƒ, ShortTag.valueOf(â˜ƒ));
   }

   public void putInt(String var1, int var2) {
      this.tags.put(â˜ƒ, IntTag.valueOf(â˜ƒ));
   }

   public void putLong(String var1, long var2) {
      this.tags.put(â˜ƒ, LongTag.valueOf(â˜ƒ));
   }

   public void putUUID(String var1, UUID var2) {
      this.tags.put(â˜ƒ, NbtUtils.createUUID(â˜ƒ));
   }

   public UUID getUUID(String var1) {
      return NbtUtils.loadUUID(this.get(â˜ƒ));
   }

   public boolean hasUUID(String var1) {
      Tag â˜ƒ = this.get(â˜ƒ);
      return â˜ƒ != null && â˜ƒ.getType() == IntArrayTag.TYPE && ((IntArrayTag)â˜ƒ).getAsIntArray().length == 4;
   }

   public void putFloat(String var1, float var2) {
      this.tags.put(â˜ƒ, FloatTag.valueOf(â˜ƒ));
   }

   public void putDouble(String var1, double var2) {
      this.tags.put(â˜ƒ, DoubleTag.valueOf(â˜ƒ));
   }

   public void putString(String var1, String var2) {
      this.tags.put(â˜ƒ, StringTag.valueOf(â˜ƒ));
   }

   public void putByteArray(String var1, byte[] var2) {
      this.tags.put(â˜ƒ, new ByteArrayTag(â˜ƒ));
   }

   public void putByteArray(String var1, List<Byte> var2) {
      this.tags.put(â˜ƒ, new ByteArrayTag(â˜ƒ));
   }

   public void putIntArray(String var1, int[] var2) {
      this.tags.put(â˜ƒ, new IntArrayTag(â˜ƒ));
   }

   public void putIntArray(String var1, List<Integer> var2) {
      this.tags.put(â˜ƒ, new IntArrayTag(â˜ƒ));
   }

   public void putLongArray(String var1, long[] var2) {
      this.tags.put(â˜ƒ, new LongArrayTag(â˜ƒ));
   }

   public void putLongArray(String var1, List<Long> var2) {
      this.tags.put(â˜ƒ, new LongArrayTag(â˜ƒ));
   }

   public void putBoolean(String var1, boolean var2) {
      this.tags.put(â˜ƒ, ByteTag.valueOf(â˜ƒ));
   }

   @Nullable
   public Tag get(String var1) {
      return (Tag)this.tags.get(â˜ƒ);
   }

   public byte getTagType(String var1) {
      Tag â˜ƒ = (Tag)this.tags.get(â˜ƒ);
      return â˜ƒ == null ? 0 : â˜ƒ.getId();
   }

   public boolean contains(String var1) {
      return this.tags.containsKey(â˜ƒ);
   }

   public boolean contains(String var1, int var2) {
      int â˜ƒ = this.getTagType(â˜ƒ);
      if (â˜ƒ == â˜ƒ) {
         return true;
      } else if (â˜ƒ != 99) {
         return false;
      } else {
         return â˜ƒ == 1 || â˜ƒ == 2 || â˜ƒ == 3 || â˜ƒ == 4 || â˜ƒ == 5 || â˜ƒ == 6;
      }
   }

   public byte getByte(String var1) {
      try {
         if (this.contains(â˜ƒ, 99)) {
            return ((NumericTag)this.tags.get(â˜ƒ)).getAsByte();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public short getShort(String var1) {
      try {
         if (this.contains(â˜ƒ, 99)) {
            return ((NumericTag)this.tags.get(â˜ƒ)).getAsShort();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public int getInt(String var1) {
      try {
         if (this.contains(â˜ƒ, 99)) {
            return ((NumericTag)this.tags.get(â˜ƒ)).getAsInt();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public long getLong(String var1) {
      try {
         if (this.contains(â˜ƒ, 99)) {
            return ((NumericTag)this.tags.get(â˜ƒ)).getAsLong();
         }
      } catch (ClassCastException var3) {
      }

      return 0L;
   }

   public float getFloat(String var1) {
      try {
         if (this.contains(â˜ƒ, 99)) {
            return ((NumericTag)this.tags.get(â˜ƒ)).getAsFloat();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0F;
   }

   public double getDouble(String var1) {
      try {
         if (this.contains(â˜ƒ, 99)) {
            return ((NumericTag)this.tags.get(â˜ƒ)).getAsDouble();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0;
   }

   public String getString(String var1) {
      try {
         if (this.contains(â˜ƒ, 8)) {
            return ((Tag)this.tags.get(â˜ƒ)).getAsString();
         }
      } catch (ClassCastException var3) {
      }

      return "";
   }

   public byte[] getByteArray(String var1) {
      try {
         if (this.contains(â˜ƒ, 7)) {
            return ((ByteArrayTag)this.tags.get(â˜ƒ)).getAsByteArray();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createReport(â˜ƒ, ByteArrayTag.TYPE, var3));
      }

      return new byte[0];
   }

   public int[] getIntArray(String var1) {
      try {
         if (this.contains(â˜ƒ, 11)) {
            return ((IntArrayTag)this.tags.get(â˜ƒ)).getAsIntArray();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createReport(â˜ƒ, IntArrayTag.TYPE, var3));
      }

      return new int[0];
   }

   public long[] getLongArray(String var1) {
      try {
         if (this.contains(â˜ƒ, 12)) {
            return ((LongArrayTag)this.tags.get(â˜ƒ)).getAsLongArray();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createReport(â˜ƒ, LongArrayTag.TYPE, var3));
      }

      return new long[0];
   }

   public CompoundTag getCompound(String var1) {
      try {
         if (this.contains(â˜ƒ, 10)) {
            return (CompoundTag)this.tags.get(â˜ƒ);
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createReport(â˜ƒ, TYPE, var3));
      }

      return new CompoundTag();
   }

   public ListTag getList(String var1, int var2) {
      try {
         if (this.getTagType(â˜ƒ) == 9) {
            ListTag â˜ƒ = (ListTag)this.tags.get(â˜ƒ);
            if (!â˜ƒ.isEmpty() && â˜ƒ.getElementType() != â˜ƒ) {
               return new ListTag();
            }

            return â˜ƒ;
         }
      } catch (ClassCastException var4) {
         throw new ReportedException(this.createReport(â˜ƒ, ListTag.TYPE, var4));
      }

      return new ListTag();
   }

   public boolean getBoolean(String var1) {
      return this.getByte(â˜ƒ) != 0;
   }

   public void remove(String var1) {
      this.tags.remove(â˜ƒ);
   }

   @Override
   public String toString() {
      return this.getAsString();
   }

   public boolean isEmpty() {
      return this.tags.isEmpty();
   }

   private CrashReport createReport(String var1, TagType<?> var2, ClassCastException var3) {
      CrashReport â˜ƒ = CrashReport.forThrowable(â˜ƒ, "Reading NBT data");
      CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Corrupt NBT tag", 1);
      â˜ƒx.setDetail("Tag type found", (CrashReportDetail<String>)(() -> ((Tag)this.tags.get(â˜ƒ)).getType().getName()));
      â˜ƒx.setDetail("Tag type expected", â˜ƒ::getName);
      â˜ƒx.setDetail("Tag name", â˜ƒ);
      return â˜ƒ;
   }

   public CompoundTag copy() {
      Map<String, Tag> â˜ƒ = Maps.newHashMap(Maps.transformValues(this.tags, Tag::copy));
      return new CompoundTag(â˜ƒ);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof CompoundTag && Objects.equals(this.tags, ((CompoundTag)â˜ƒ).tags);
      }
   }

   public int hashCode() {
      return this.tags.hashCode();
   }

   private static void writeNamedTag(String var0, Tag var1, DataOutput var2) throws IOException {
      â˜ƒ.writeByte(â˜ƒ.getId());
      if (â˜ƒ.getId() != 0) {
         â˜ƒ.writeUTF(â˜ƒ);
         â˜ƒ.write(â˜ƒ);
      }
   }

   static byte readNamedTagType(DataInput var0, NbtAccounter var1) throws IOException {
      return â˜ƒ.readByte();
   }

   static String readNamedTagName(DataInput var0, NbtAccounter var1) throws IOException {
      return â˜ƒ.readUTF();
   }

   static Tag readNamedTagData(TagType<?> var0, String var1, DataInput var2, int var3, NbtAccounter var4) {
      try {
         return â˜ƒ.load(â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (IOException var8) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var8, "Loading NBT data");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("NBT Tag");
         â˜ƒx.setDetail("Tag name", â˜ƒ);
         â˜ƒx.setDetail("Tag type", â˜ƒ.getName());
         throw new ReportedException(â˜ƒ);
      }
   }

   public CompoundTag merge(CompoundTag var1) {
      for(String â˜ƒ : â˜ƒ.tags.keySet()) {
         Tag â˜ƒx = (Tag)â˜ƒ.tags.get(â˜ƒ);
         if (â˜ƒx.getId() == 10) {
            if (this.contains(â˜ƒ, 10)) {
               CompoundTag â˜ƒxx = this.getCompound(â˜ƒ);
               â˜ƒxx.merge((CompoundTag)â˜ƒx);
            } else {
               this.put(â˜ƒ, â˜ƒx.copy());
            }
         } else {
            this.put(â˜ƒ, â˜ƒx.copy());
         }
      }

      return this;
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitCompound(this);
   }

   protected Map<String, Tag> entries() {
      return Collections.unmodifiableMap(this.tags);
   }
}
