package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EndTag implements Tag {
   private static final int SELF_SIZE_IN_BITS = 64;
   public static final TagType<EndTag> TYPE = new TagType<EndTag>() {
      public EndTag load(DataInput var1, int var2, NbtAccounter var3) {
         â˜ƒ.accountBits(64L);
         return EndTag.INSTANCE;
      }

      @Override
      public String getName() {
         return "END";
      }

      @Override
      public String getPrettyName() {
         return "TAG_End";
      }

      @Override
      public boolean isValue() {
         return true;
      }
   };
   public static final EndTag INSTANCE = new EndTag();

   private EndTag() {
   }

   @Override
   public void write(DataOutput var1) throws IOException {
   }

   @Override
   public byte getId() {
      return 0;
   }

   @Override
   public TagType<EndTag> getType() {
      return TYPE;
   }

   @Override
   public String toString() {
      return this.getAsString();
   }

   public EndTag copy() {
      return this;
   }

   @Override
   public void accept(TagVisitor var1) {
      â˜ƒ.visitEnd(this);
   }
}
