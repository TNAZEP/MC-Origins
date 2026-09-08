package net.minecraft.nbt;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class NBTDynamicOps implements DynamicOps<INBTBase> {
   public static final NBTDynamicOps field_210820_a = new NBTDynamicOps();

   protected NBTDynamicOps() {
   }

   public INBTBase empty() {
      return new NBTTagEnd();
   }

   public Type<?> getType(INBTBase var1) {
      switch(☃.func_74732_a()) {
         case 0:
            return DSL.nilType();
         case 1:
            return DSL.byteType();
         case 2:
            return DSL.shortType();
         case 3:
            return DSL.intType();
         case 4:
            return DSL.longType();
         case 5:
            return DSL.floatType();
         case 6:
            return DSL.doubleType();
         case 7:
            return DSL.list(DSL.byteType());
         case 8:
            return DSL.string();
         case 9:
            return DSL.list(DSL.remainderType());
         case 10:
            return DSL.compoundList(DSL.remainderType(), DSL.remainderType());
         case 11:
            return DSL.list(DSL.intType());
         case 12:
            return DSL.list(DSL.longType());
         default:
            return DSL.remainderType();
      }
   }

   public Optional<Number> getNumberValue(INBTBase var1) {
      return ☃ instanceof NBTPrimitive ? Optional.of(((NBTPrimitive)☃).func_209908_j()) : Optional.empty();
   }

   public INBTBase createNumeric(Number var1) {
      return new NBTTagDouble(☃.doubleValue());
   }

   public INBTBase createByte(byte var1) {
      return new NBTTagByte(☃);
   }

   public INBTBase createShort(short var1) {
      return new NBTTagShort(☃);
   }

   public INBTBase createInt(int var1) {
      return new NBTTagInt(☃);
   }

   public INBTBase createLong(long var1) {
      return new NBTTagLong(☃);
   }

   public INBTBase createFloat(float var1) {
      return new NBTTagFloat(☃);
   }

   public INBTBase createDouble(double var1) {
      return new NBTTagDouble(☃);
   }

   public Optional<String> getStringValue(INBTBase var1) {
      return ☃ instanceof NBTTagString ? Optional.of(☃.func_150285_a_()) : Optional.empty();
   }

   public INBTBase createString(String var1) {
      return new NBTTagString(☃);
   }

   public INBTBase mergeInto(INBTBase var1, INBTBase var2) {
      if (☃ instanceof NBTTagEnd) {
         return ☃;
      } else if (!(☃ instanceof NBTTagCompound)) {
         if (☃ instanceof NBTTagEnd) {
            throw new IllegalArgumentException("mergeInto called with a null input.");
         } else if (☃ instanceof NBTTagCollection) {
            NBTTagCollection<INBTBase> ☃ = new NBTTagList();
            NBTTagCollection<?> ☃x = (NBTTagCollection)☃;
            ☃.addAll(☃x);
            ☃.add(☃);
            return ☃;
         } else {
            return ☃;
         }
      } else if (!(☃ instanceof NBTTagCompound)) {
         return ☃;
      } else {
         NBTTagCompound ☃ = new NBTTagCompound();
         NBTTagCompound ☃x = (NBTTagCompound)☃;

         for(String ☃xx : ☃x.func_150296_c()) {
            ☃.func_74782_a(☃xx, ☃x.func_74781_a(☃xx));
         }

         NBTTagCompound ☃xx = (NBTTagCompound)☃;

         for(String ☃xxx : ☃xx.func_150296_c()) {
            ☃.func_74782_a(☃xxx, ☃xx.func_74781_a(☃xxx));
         }

         return ☃;
      }
   }

   public INBTBase mergeInto(INBTBase var1, INBTBase var2, INBTBase var3) {
      NBTTagCompound ☃;
      if (☃ instanceof NBTTagEnd) {
         ☃ = new NBTTagCompound();
      } else {
         if (!(☃ instanceof NBTTagCompound)) {
            return ☃;
         }

         NBTTagCompound ☃ = (NBTTagCompound)☃;
         ☃ = new NBTTagCompound();
         ☃.func_150296_c().forEach(var2x -> ☃.func_74782_a(var2x, ☃.func_74781_a(var2x)));
      }

      ☃.func_74782_a(☃.func_150285_a_(), ☃);
      return ☃;
   }

   public INBTBase merge(INBTBase var1, INBTBase var2) {
      if (☃ instanceof NBTTagEnd) {
         return ☃;
      } else if (☃ instanceof NBTTagEnd) {
         return ☃;
      } else {
         if (☃ instanceof NBTTagCompound && ☃ instanceof NBTTagCompound) {
            NBTTagCompound ☃ = (NBTTagCompound)☃;
            NBTTagCompound ☃x = (NBTTagCompound)☃;
            NBTTagCompound ☃xx = new NBTTagCompound();
            ☃.func_150296_c().forEach(var2x -> ☃.func_74782_a(var2x, ☃.func_74781_a(var2x)));
            ☃x.func_150296_c().forEach(var2x -> ☃.func_74782_a(var2x, ☃.func_74781_a(var2x)));
         }

         if (☃ instanceof NBTTagCollection && ☃ instanceof NBTTagCollection) {
            NBTTagList ☃ = new NBTTagList();
            ☃.addAll((NBTTagCollection)☃);
            ☃.addAll((NBTTagCollection)☃);
            return ☃;
         } else {
            throw new IllegalArgumentException("Could not merge " + ☃ + " and " + ☃);
         }
      }
   }

   public Optional<Map<INBTBase, INBTBase>> getMapValues(INBTBase var1) {
      if (☃ instanceof NBTTagCompound) {
         NBTTagCompound ☃ = (NBTTagCompound)☃;
         return Optional.of(
            ☃.func_150296_c()
               .stream()
               .map(var2x -> Pair.of(this.createString(var2x), ☃.func_74781_a(var2x)))
               .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
         );
      } else {
         return Optional.empty();
      }
   }

   public INBTBase createMap(Map<INBTBase, INBTBase> var1) {
      NBTTagCompound ☃ = new NBTTagCompound();

      for(Entry<INBTBase, INBTBase> ☃x : ☃.entrySet()) {
         ☃.func_74782_a(((INBTBase)☃x.getKey()).func_150285_a_(), (INBTBase)☃x.getValue());
      }

      return ☃;
   }

   public Optional<Stream<INBTBase>> getStream(INBTBase var1) {
      return ☃ instanceof NBTTagCollection ? Optional.of(((NBTTagCollection)☃).stream().map(var0 -> var0)) : Optional.empty();
   }

   public Optional<ByteBuffer> getByteBuffer(INBTBase var1) {
      return ☃ instanceof NBTTagByteArray ? Optional.of(ByteBuffer.wrap(((NBTTagByteArray)☃).func_150292_c())) : DynamicOps.super.getByteBuffer(☃);
   }

   public INBTBase createByteList(ByteBuffer var1) {
      return new NBTTagByteArray(DataFixUtils.toArray(☃));
   }

   public Optional<IntStream> getIntStream(INBTBase var1) {
      return ☃ instanceof NBTTagIntArray ? Optional.of(Arrays.stream(((NBTTagIntArray)☃).func_150302_c())) : DynamicOps.super.getIntStream(☃);
   }

   public INBTBase createIntList(IntStream var1) {
      return new NBTTagIntArray(☃.toArray());
   }

   public Optional<LongStream> getLongStream(INBTBase var1) {
      return ☃ instanceof NBTTagLongArray ? Optional.of(Arrays.stream(((NBTTagLongArray)☃).func_197652_h())) : DynamicOps.super.getLongStream(☃);
   }

   public INBTBase createLongList(LongStream var1) {
      return new NBTTagLongArray(☃.toArray());
   }

   public INBTBase createList(Stream<INBTBase> var1) {
      PeekingIterator<INBTBase> ☃ = Iterators.peekingIterator(☃.iterator());
      if (!☃.hasNext()) {
         return new NBTTagList();
      } else {
         INBTBase ☃ = ☃.peek();
         if (☃ instanceof NBTTagByte) {
            ArrayList<Byte> ☃x = Lists.newArrayList(Iterators.transform(☃, var0 -> ((NBTTagByte)var0).func_150290_f()));
            return new NBTTagByteArray(☃x);
         } else if (☃ instanceof NBTTagInt) {
            ArrayList<Integer> ☃ = Lists.newArrayList(Iterators.transform(☃, var0 -> ((NBTTagInt)var0).func_150287_d()));
            return new NBTTagIntArray(☃);
         } else if (☃ instanceof NBTTagLong) {
            ArrayList<Long> ☃ = Lists.newArrayList(Iterators.transform(☃, var0 -> ((NBTTagLong)var0).func_150291_c()));
            return new NBTTagLongArray(☃);
         } else {
            NBTTagList ☃ = new NBTTagList();

            while(☃.hasNext()) {
               INBTBase ☃x = ☃.next();
               if (!(☃x instanceof NBTTagEnd)) {
                  ☃.add(☃x);
               }
            }

            return ☃;
         }
      }
   }

   public INBTBase remove(INBTBase var1, String var2) {
      if (☃ instanceof NBTTagCompound) {
         NBTTagCompound ☃ = (NBTTagCompound)☃;
         NBTTagCompound ☃x = new NBTTagCompound();
         ☃.func_150296_c().stream().filter(var1x -> !Objects.equals(var1x, ☃)).forEach(var2x -> ☃.func_74782_a(var2x, ☃.func_74781_a(var2x)));
         return ☃x;
      } else {
         return ☃;
      }
   }

   public String toString() {
      return "NBT";
   }
}
