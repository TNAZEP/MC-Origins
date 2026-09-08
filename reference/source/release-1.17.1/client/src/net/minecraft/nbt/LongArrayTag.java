package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class LongArrayTag extends CollectionTag<LongTag> {
   private static final int SELF_SIZE_IN_BITS = 192;
   public static final TagType<LongArrayTag> TYPE = new TagType<LongArrayTag>() {
      public LongArrayTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(192L);
         int â˜ƒ = â˜ƒ.readInt();
         â˜ƒ.accountBits(64L * (long)â˜ƒ);
         long[] â˜ƒx = new long[â˜ƒ];

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
            â˜ƒx[â˜ƒxx] = â˜ƒ.readLong();
         }

         return new LongArrayTag(â˜ƒx);
      }

      @Override
      public String getName() {
         return "LONG[]";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Long_Array";
      }
   };
   private long[] data;

   public LongArrayTag(long[] var1) {
      this.data = â˜ƒ;
   }

   public LongArrayTag(LongSet var1) {
      this.data = â˜ƒ.toLongArray();
   }

   public LongArrayTag(List<Long> var1) {
      this(toArray(â˜ƒ));
   }

   private static long[] toArray(List<Long> var0) {
      long[] â˜ƒ = new long[â˜ƒ.size()];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         Long â˜ƒxx = (Long)â˜ƒ.get(â˜ƒx);
         â˜ƒ[â˜ƒx] = â˜ƒxx == null ? 0L : â˜ƒxx;
      }

      return â˜ƒ;
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeInt(this.data.length);

      for(long â˜ƒ : this.data) {
         â˜ƒ.writeLong(â˜ƒ);
      }
   }

   @Override
   public byte getId() {
      return 12;
   }

   @Override
   public TagType<LongArrayTag> getType() {
      return TYPE;
   }

   @Override
   public String toString() {
      return this.getAsString();
   }

   public LongArrayTag copy() {
      long[] â˜ƒ = new long[this.data.length];
      System.arraycopy(this.data, 0, â˜ƒ, 0, this.data.length);
      return new LongArrayTag(â˜ƒ);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof LongArrayTag && Arrays.equals(this.data, ((LongArrayTag)â˜ƒ).data);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.data);
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitLongArray(this);
   }

   public long[] getAsLongArray() {
      return this.data;
   }

   public int size() {
      return this.data.length;
   }

   public LongTag get(int var1) {
      return LongTag.valueOf(this.data[â˜ƒ]);
   }

   public LongTag set(int var1, LongTag var2) {
      long â˜ƒ = this.data[â˜ƒ];
      this.data[â˜ƒ] = â˜ƒ.getAsLong();
      return LongTag.valueOf(â˜ƒ);
   }

   public void add(int var1, LongTag var2) {
      this.data = ArrayUtils.add(this.data, â˜ƒ, â˜ƒ.getAsLong());
   }

   @Override
   public boolean setTag(int var1, Tag var2) {
      if (â˜ƒ instanceof NumericTag) {
         this.data[â˜ƒ] = ((NumericTag)â˜ƒ).getAsLong();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int var1, Tag var2) {
      if (â˜ƒ instanceof NumericTag) {
         this.data = ArrayUtils.add(this.data, â˜ƒ, ((NumericTag)â˜ƒ).getAsLong());
         return true;
      } else {
         return false;
      }
   }

   public LongTag remove(int var1) {
      long â˜ƒ = this.data[â˜ƒ];
      this.data = ArrayUtils.remove(this.data, â˜ƒ);
      return LongTag.valueOf(â˜ƒ);
   }

   @Override
   public byte getElementType() {
      return 4;
   }

   public void clear() {
      this.data = new long[0];
   }
}
