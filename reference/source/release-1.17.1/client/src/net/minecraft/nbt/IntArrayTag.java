package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class IntArrayTag extends CollectionTag<IntTag> {
   private static final int SELF_SIZE_IN_BITS = 192;
   public static final TagType<IntArrayTag> TYPE = new TagType<IntArrayTag>() {
      public IntArrayTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(192L);
         int â˜ƒ = â˜ƒ.readInt();
         â˜ƒ.accountBits(32L * (long)â˜ƒ);
         int[] â˜ƒx = new int[â˜ƒ];

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
            â˜ƒx[â˜ƒxx] = â˜ƒ.readInt();
         }

         return new IntArrayTag(â˜ƒx);
      }

      @Override
      public String getName() {
         return "INT[]";
      }

      @Override
      public String getPrettyName() {
         return "TAG_Int_Array";
      }
   };
   private int[] data;

   public IntArrayTag(int[] var1) {
      this.data = â˜ƒ;
   }

   public IntArrayTag(List<Integer> var1) {
      this(toArray(â˜ƒ));
   }

   private static int[] toArray(List<Integer> var0) {
      int[] â˜ƒ = new int[â˜ƒ.size()];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         Integer â˜ƒxx = (Integer)â˜ƒ.get(â˜ƒx);
         â˜ƒ[â˜ƒx] = â˜ƒxx == null ? 0 : â˜ƒxx;
      }

      return â˜ƒ;
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      â˜ƒ.writeInt(this.data.length);

      for(int â˜ƒ : this.data) {
         â˜ƒ.writeInt(â˜ƒ);
      }
   }

   @Override
   public byte getId() {
      return 11;
   }

   @Override
   public TagType<IntArrayTag> getType() {
      return TYPE;
   }

   @Override
   public String toString() {
      return this.getAsString();
   }

   public IntArrayTag copy() {
      int[] â˜ƒ = new int[this.data.length];
      System.arraycopy(this.data, 0, â˜ƒ, 0, this.data.length);
      return new IntArrayTag(â˜ƒ);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof IntArrayTag && Arrays.equals(this.data, ((IntArrayTag)â˜ƒ).data);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.data);
   }

   public int[] getAsIntArray() {
      return this.data;
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitIntArray(this);
   }

   public int size() {
      return this.data.length;
   }

   public IntTag get(int var1) {
      return IntTag.valueOf(this.data[â˜ƒ]);
   }

   public IntTag set(int var1, IntTag var2) {
      int â˜ƒ = this.data[â˜ƒ];
      this.data[â˜ƒ] = â˜ƒ.getAsInt();
      return IntTag.valueOf(â˜ƒ);
   }

   public void add(int var1, IntTag var2) {
      this.data = ArrayUtils.add(this.data, â˜ƒ, â˜ƒ.getAsInt());
   }

   @Override
   public boolean setTag(int var1, Tag var2) {
      if (â˜ƒ instanceof NumericTag) {
         this.data[â˜ƒ] = ((NumericTag)â˜ƒ).getAsInt();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int var1, Tag var2) {
      if (â˜ƒ instanceof NumericTag) {
         this.data = ArrayUtils.add(this.data, â˜ƒ, ((NumericTag)â˜ƒ).getAsInt());
         return true;
      } else {
         return false;
      }
   }

   public IntTag remove(int var1) {
      int â˜ƒ = this.data[â˜ƒ];
      this.data = ArrayUtils.remove(this.data, â˜ƒ);
      return IntTag.valueOf(â˜ƒ);
   }

   @Override
   public byte getElementType() {
      return 3;
   }

   public void clear() {
      this.data = new int[0];
   }
}
