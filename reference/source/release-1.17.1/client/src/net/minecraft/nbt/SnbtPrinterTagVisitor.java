package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.Util;

public class SnbtPrinterTagVisitor implements TagVisitor {
   private static final Map<String, List<String>> KEY_ORDER = Util.make(Maps.newHashMap(), var0 -> {
      var0.put("{}", Lists.newArrayList("DataVersion", "author", "size", "data", "entities", "palette", "palettes"));
      var0.put("{}.data.[].{}", Lists.newArrayList("pos", "state", "nbt"));
      var0.put("{}.entities.[].{}", Lists.newArrayList("blockPos", "pos"));
   });
   private static final Set<String> NO_INDENTATION = Sets.newHashSet("{}.size.[]", "{}.data.[].{}", "{}.palette.[].{}", "{}.entities.[].{}");
   private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
   private static final String NAME_VALUE_SEPARATOR = String.valueOf(':');
   private static final String ELEMENT_SEPARATOR = String.valueOf(',');
   private static final String LIST_OPEN = "[";
   private static final String LIST_CLOSE = "]";
   private static final String LIST_TYPE_SEPARATOR = ";";
   private static final String ELEMENT_SPACING = " ";
   private static final String STRUCT_OPEN = "{";
   private static final String STRUCT_CLOSE = "}";
   private static final String NEWLINE = "\n";
   private final String indentation;
   private final int depth;
   private final List<String> path;
   private String result;

   public SnbtPrinterTagVisitor() {
      this("    ", 0, Lists.newArrayList());
   }

   public SnbtPrinterTagVisitor(String var1, int var2, List<String> var3) {
      this.indentation = â˜ƒ;
      this.depth = â˜ƒ;
      this.path = â˜ƒ;
   }

   public String visit(Tag var1) {
      â˜ƒ.accept(this);
      return this.result;
   }

   @Override
   public void visitString(StringTag var1) {
      this.result = StringTag.quoteAndEscape(â˜ƒ.getAsString());
   }

   @Override
   public void visitByte(ByteTag var1) {
      this.result = â˜ƒ.getAsNumber() + "b";
   }

   @Override
   public void visitShort(ShortTag var1) {
      this.result = â˜ƒ.getAsNumber() + "s";
   }

   @Override
   public void visitInt(IntTag var1) {
      this.result = String.valueOf(â˜ƒ.getAsNumber());
   }

   @Override
   public void visitLong(LongTag var1) {
      this.result = â˜ƒ.getAsNumber() + "L";
   }

   @Override
   public void visitFloat(FloatTag var1) {
      this.result = â˜ƒ.getAsFloat() + "f";
   }

   @Override
   public void visitDouble(DoubleTag var1) {
      this.result = â˜ƒ.getAsDouble() + "d";
   }

   @Override
   public void visitByteArray(ByteArrayTag var1) {
      StringBuilder â˜ƒ = new StringBuilder("[").append("B").append(";");
      byte[] â˜ƒx = â˜ƒ.getAsByteArray();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.length; ++â˜ƒxx) {
         â˜ƒ.append(" ").append(â˜ƒx[â˜ƒxx]).append("B");
         if (â˜ƒxx != â˜ƒx.length - 1) {
            â˜ƒ.append(ELEMENT_SEPARATOR);
         }
      }

      â˜ƒ.append("]");
      this.result = â˜ƒ.toString();
   }

   @Override
   public void visitIntArray(IntArrayTag var1) {
      StringBuilder â˜ƒ = new StringBuilder("[").append("I").append(";");
      int[] â˜ƒx = â˜ƒ.getAsIntArray();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.length; ++â˜ƒxx) {
         â˜ƒ.append(" ").append(â˜ƒx[â˜ƒxx]);
         if (â˜ƒxx != â˜ƒx.length - 1) {
            â˜ƒ.append(ELEMENT_SEPARATOR);
         }
      }

      â˜ƒ.append("]");
      this.result = â˜ƒ.toString();
   }

   @Override
   public void visitLongArray(LongArrayTag var1) {
      String â˜ƒ = "L";
      StringBuilder â˜ƒx = new StringBuilder("[").append("L").append(";");
      long[] â˜ƒxx = â˜ƒ.getAsLongArray();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.length; ++â˜ƒxxx) {
         â˜ƒx.append(" ").append(â˜ƒxx[â˜ƒxxx]).append("L");
         if (â˜ƒxxx != â˜ƒxx.length - 1) {
            â˜ƒx.append(ELEMENT_SEPARATOR);
         }
      }

      â˜ƒx.append("]");
      this.result = â˜ƒx.toString();
   }

   @Override
   public void visitList(ListTag var1) {
      if (â˜ƒ.isEmpty()) {
         this.result = "[]";
      } else {
         StringBuilder â˜ƒ = new StringBuilder("[");
         this.pushPath("[]");
         String â˜ƒx = NO_INDENTATION.contains(this.pathString()) ? "" : this.indentation;
         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.append("\n");
         }

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
            â˜ƒ.append(Strings.repeat(â˜ƒx, this.depth + 1));
            â˜ƒ.append(new SnbtPrinterTagVisitor(â˜ƒx, this.depth + 1, this.path).visit(â˜ƒ.get(â˜ƒ)));
            if (â˜ƒ != â˜ƒ.size() - 1) {
               â˜ƒ.append(ELEMENT_SEPARATOR).append(â˜ƒx.isEmpty() ? " " : "\n");
            }
         }

         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.append("\n").append(Strings.repeat(â˜ƒx, this.depth));
         }

         â˜ƒ.append("]");
         this.result = â˜ƒ.toString();
         this.popPath();
      }
   }

   @Override
   public void visitCompound(CompoundTag var1) {
      if (â˜ƒ.isEmpty()) {
         this.result = "{}";
      } else {
         StringBuilder â˜ƒ = new StringBuilder("{");
         this.pushPath("{}");
         String â˜ƒx = NO_INDENTATION.contains(this.pathString()) ? "" : this.indentation;
         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.append("\n");
         }

         Collection<String> â˜ƒ = this.getKeys(â˜ƒ);
         Iterator<String> â˜ƒx = â˜ƒ.iterator();

         while(â˜ƒx.hasNext()) {
            String â˜ƒxx = (String)â˜ƒx.next();
            Tag â˜ƒxxx = â˜ƒ.get(â˜ƒxx);
            this.pushPath(â˜ƒxx);
            â˜ƒ.append(Strings.repeat(â˜ƒx, this.depth + 1))
               .append(handleEscapePretty(â˜ƒxx))
               .append(NAME_VALUE_SEPARATOR)
               .append(" ")
               .append(new SnbtPrinterTagVisitor(â˜ƒx, this.depth + 1, this.path).visit(â˜ƒxxx));
            this.popPath();
            if (â˜ƒx.hasNext()) {
               â˜ƒ.append(ELEMENT_SEPARATOR).append(â˜ƒx.isEmpty() ? " " : "\n");
            }
         }

         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.append("\n").append(Strings.repeat(â˜ƒx, this.depth));
         }

         â˜ƒ.append("}");
         this.result = â˜ƒ.toString();
         this.popPath();
      }
   }

   private void popPath() {
      this.path.remove(this.path.size() - 1);
   }

   private void pushPath(String var1) {
      this.path.add(â˜ƒ);
   }

   protected List<String> getKeys(CompoundTag var1) {
      Set<String> â˜ƒ = Sets.newHashSet(â˜ƒ.getAllKeys());
      List<String> â˜ƒx = Lists.newArrayList();
      List<String> â˜ƒxx = (List)KEY_ORDER.get(this.pathString());
      if (â˜ƒxx != null) {
         for(String â˜ƒxxx : â˜ƒxx) {
            if (â˜ƒ.remove(â˜ƒxxx)) {
               â˜ƒx.add(â˜ƒxxx);
            }
         }

         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.stream().sorted().forEach(â˜ƒx::add);
         }
      } else {
         â˜ƒx.addAll(â˜ƒ);
         Collections.sort(â˜ƒx);
      }

      return â˜ƒx;
   }

   public String pathString() {
      return String.join(".", this.path);
   }

   protected static String handleEscapePretty(String var0) {
      return SIMPLE_VALUE.matcher(â˜ƒ).matches() ? â˜ƒ : StringTag.quoteAndEscape(â˜ƒ);
   }

   @Override
   public void visitEnd(EndTag var1) {
   }
}
