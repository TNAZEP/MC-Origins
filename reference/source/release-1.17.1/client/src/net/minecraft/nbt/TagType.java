package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;

public interface TagType<T extends Tag> {
   T load(DataInput var1, int var2, NbtAccounter var3) throws IOException;

   default boolean isValue() {
      return false;
   }

   String getName();

   String getPrettyName();

   static TagType<EndTag> createInvalid(final int var0) {
      return new TagType<EndTag>() {
         public EndTag load(DataInput var1, int var2, NbtAccounter var3) {
            throw new IllegalArgumentException("Invalid tag id: " + â˜ƒ);
         }

         @Override
         public String getName() {
            return "INVALID[" + â˜ƒ + "]";
         }

         @Override
         public String getPrettyName() {
            return "UNKNOWN_" + â˜ƒ;
         }
      };
   }
}
