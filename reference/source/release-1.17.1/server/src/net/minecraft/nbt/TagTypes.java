package net.minecraft.nbt;

public class TagTypes {
   private static final TagType<?>[] TYPES = new TagType[]{
      EndTag.TYPE,
      ByteTag.TYPE,
      ShortTag.TYPE,
      IntTag.TYPE,
      LongTag.TYPE,
      FloatTag.TYPE,
      DoubleTag.TYPE,
      ByteArrayTag.TYPE,
      StringTag.TYPE,
      ListTag.TYPE,
      CompoundTag.TYPE,
      IntArrayTag.TYPE,
      LongArrayTag.TYPE
   };

   public static TagType<?> getType(int var0) {
      return â˜ƒ >= 0 && â˜ƒ < TYPES.length ? TYPES[â˜ƒ] : TagType.createInvalid(â˜ƒ);
   }
}
