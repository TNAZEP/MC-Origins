package net.minecraft.nbt;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ListTag extends CollectionTag<Tag> {
   private static final int SELF_SIZE_IN_BITS = 296;
   public static final TagType<ListTag> TYPE = new TagType<ListTag>() {
      public ListTag load(DataInput var1, int var2, NbtAccounter var3) throws IOException {
         â˜ƒ.accountBits(296L);
         if (â˜ƒ > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            byte â˜ƒ = â˜ƒ.readByte();
            int â˜ƒx = â˜ƒ.readInt();
            if (â˜ƒ == 0 && â˜ƒx > 0) {
               throw new RuntimeException("Missing type on ListTag");
            } else {
               â˜ƒ.accountBits(32L * (long)â˜ƒx);
               TagType<?> â˜ƒ = TagTypes.getType(â˜ƒ);
               List<Tag> â˜ƒx = Lists.<Tag>newArrayListWithCapacity(â˜ƒx);

               for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
                  â˜ƒx.add(â˜ƒ.load(â˜ƒ, â˜ƒ + 1, â˜ƒ));
               }

               return new ListTag(â˜ƒx, â˜ƒ);
            }
         }
      }

      @Override
      public String getName() {
         return "LIST";
      }

      @Override
      public String getPrettyName() {
         return "TAG_List";
      }
   };
   private final List<Tag> list;
   private byte type;

   ListTag(List<Tag> var1, byte var2) {
      this.list = â˜ƒ;
      this.type = â˜ƒ;
   }

   public ListTag() {
      this(Lists.<Tag>newArrayList(), (byte)0);
   }

   @Override
   public void write(DataOutput var1) throws IOException {
      if (this.list.isEmpty()) {
         this.type = 0;
      } else {
         this.type = ((Tag)this.list.get(0)).getId();
      }

      â˜ƒ.writeByte(this.type);
      â˜ƒ.writeInt(this.list.size());

      for(Tag â˜ƒ : this.list) {
         â˜ƒ.write(â˜ƒ);
      }
   }

   @Override
   public byte getId() {
      return 9;
   }

   @Override
   public TagType<ListTag> getType() {
      return TYPE;
   }

   @Override
   public String toString() {
      return this.getAsString();
   }

   private void updateTypeAfterRemove() {
      if (this.list.isEmpty()) {
         this.type = 0;
      }
   }

   @Override
   public Tag remove(int var1) {
      Tag â˜ƒ = (Tag)this.list.remove(â˜ƒ);
      this.updateTypeAfterRemove();
      return â˜ƒ;
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public CompoundTag getCompound(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 10) {
            return (CompoundTag)â˜ƒ;
         }
      }

      return new CompoundTag();
   }

   public ListTag getList(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 9) {
            return (ListTag)â˜ƒ;
         }
      }

      return new ListTag();
   }

   public short getShort(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 2) {
            return ((ShortTag)â˜ƒ).getAsShort();
         }
      }

      return 0;
   }

   public int getInt(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 3) {
            return ((IntTag)â˜ƒ).getAsInt();
         }
      }

      return 0;
   }

   public int[] getIntArray(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 11) {
            return ((IntArrayTag)â˜ƒ).getAsIntArray();
         }
      }

      return new int[0];
   }

   public long[] getLongArray(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 11) {
            return ((LongArrayTag)â˜ƒ).getAsLongArray();
         }
      }

      return new long[0];
   }

   public double getDouble(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 6) {
            return ((DoubleTag)â˜ƒ).getAsDouble();
         }
      }

      return 0.0;
   }

   public float getFloat(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         if (â˜ƒ.getId() == 5) {
            return ((FloatTag)â˜ƒ).getAsFloat();
         }
      }

      return 0.0F;
   }

   public String getString(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.list.size()) {
         Tag â˜ƒ = (Tag)this.list.get(â˜ƒ);
         return â˜ƒ.getId() == 8 ? â˜ƒ.getAsString() : â˜ƒ.toString();
      } else {
         return "";
      }
   }

   public int size() {
      return this.list.size();
   }

   public Tag get(int var1) {
      return (Tag)this.list.get(â˜ƒ);
   }

   @Override
   public Tag set(int var1, Tag var2) {
      Tag â˜ƒ = this.get(â˜ƒ);
      if (!this.setTag(â˜ƒ, â˜ƒ)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", â˜ƒ.getId(), this.type));
      } else {
         return â˜ƒ;
      }
   }

   @Override
   public void add(int var1, Tag var2) {
      if (!this.addTag(â˜ƒ, â˜ƒ)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", â˜ƒ.getId(), this.type));
      }
   }

   @Override
   public boolean setTag(int var1, Tag var2) {
      if (this.updateType(â˜ƒ)) {
         this.list.set(â˜ƒ, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int var1, Tag var2) {
      if (this.updateType(â˜ƒ)) {
         this.list.add(â˜ƒ, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   private boolean updateType(Tag var1) {
      if (â˜ƒ.getId() == 0) {
         return false;
      } else if (this.type == 0) {
         this.type = â˜ƒ.getId();
         return true;
      } else {
         return this.type == â˜ƒ.getId();
      }
   }

   public ListTag copy() {
      Iterable<Tag> â˜ƒ = (Iterable<Tag>)(TagTypes.getType(this.type).isValue() ? this.list : Iterables.transform(this.list, Tag::copy));
      List<Tag> â˜ƒx = Lists.<Tag>newArrayList(â˜ƒ);
      return new ListTag(â˜ƒx, this.type);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof ListTag && Objects.equals(this.list, ((ListTag)â˜ƒ).list);
      }
   }

   public int hashCode() {
      return this.list.hashCode();
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitList(this);
   }

   @Override
   public byte getElementType() {
      return this.type;
   }

   public void clear() {
      this.list.clear();
      this.type = 0;
   }
}
