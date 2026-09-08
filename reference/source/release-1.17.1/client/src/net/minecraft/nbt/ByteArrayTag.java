package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class ByteArrayTag extends CollectionTag<ByteTag> {
   private static final int SELF_SIZE_IN_BITS = 192;
   public static final TagType<ByteArrayTag> TYPE = new TagType<ByteArrayTag>() {
      public ByteArrayTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(192L);
         int â˜ƒ = â˜ƒ.readInt();
         â˜ƒ.accountBits(8L * (long)â˜ƒ);
         byte[] â˜ƒx = new byte[â˜ƒ];
         â˜ƒ.readFully(â˜ƒx);
         return new ByteArrayTag(â˜ƒx);
      }

      @Override
      public String getName() {
         return "BYTE[]";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Byte_Array";
      }
   };
   private byte[] data;

   public ByteArrayTag(byte[] var1) {
      this.data = â˜ƒ;
   }

   public ByteArrayTag(List<Byte> var1) {
      this(toArray(â˜ƒ));
   }

   private static byte[] toArray(List<Byte> var0) {
      byte[] â˜ƒ = new byte[â˜ƒ.size()];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         Byte â˜ƒxx = (Byte)â˜ƒ.get(â˜ƒx);
         â˜ƒ[â˜ƒx] = â˜ƒxx == null ? 0 : â˜ƒxx;
      }

      return â˜ƒ;
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeInt(this.data.length);
      â˜ƒ.write(this.data);
   }

   @Override
   public byte getId() {
      return 7;
   }

   @Override
   public TagType<ByteArrayTag> getType() {
      return TYPE;
   }

   @Override
   public String toString() {
      return this.getAsString();
   }

   @Override
   public Tag copy() {
      byte[] â˜ƒ = new byte[this.data.length];
      System.arraycopy(this.data, 0, â˜ƒ, 0, this.data.length);
      return new ByteArrayTag(â˜ƒ);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof ByteArrayTag && Arrays.equals(this.data, ((ByteArrayTag)â˜ƒ).data);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.data);
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitByteArray(this);
   }

   public byte[] getAsByteArray() {
      return this.data;
   }

   public int size() {
      return this.data.length;
   }

   public ByteTag get(int var1) {
      return ByteTag.valueOf(this.data[â˜ƒ]);
   }

   public ByteTag set(int var1, ByteTag var2) {
      byte â˜ƒ = this.data[â˜ƒ];
      this.data[â˜ƒ] = â˜ƒ.getAsByte();
      return ByteTag.valueOf(â˜ƒ);
   }

   public void add(int var1, ByteTag var2) {
      this.data = ArrayUtils.add(this.data, â˜ƒ, â˜ƒ.getAsByte());
   }

   @Override
   public boolean setTag(int var1, Tag var2) {
      if (â˜ƒ instanceof NumericTag) {
         this.data[â˜ƒ] = ((NumericTag)â˜ƒ).getAsByte();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int var1, Tag var2) {
      if (â˜ƒ instanceof NumericTag) {
         this.data = ArrayUtils.add(this.data, â˜ƒ, ((NumericTag)â˜ƒ).getAsByte());
         return true;
      } else {
         return false;
      }
   }

   public ByteTag remove(int var1) {
      byte â˜ƒ = this.data[â˜ƒ];
      this.data = ArrayUtils.remove(this.data, â˜ƒ);
      return ByteTag.valueOf(â˜ƒ);
   }

   @Override
   public byte getElementType() {
      return 1;
   }

   public void clear() {
      this.data = new byte[0];
   }
}
