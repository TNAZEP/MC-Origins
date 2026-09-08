package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SerializableUUID;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NbtUtils {
   private static final Comparator<ListTag> YXZ_LISTTAG_INT_COMPARATOR = Comparator.comparingInt(var0 -> var0.getInt(1))
      .thenComparingInt(var0 -> var0.getInt(0))
      .thenComparingInt(var0 -> var0.getInt(2));
   private static final Comparator<ListTag> YXZ_LISTTAG_DOUBLE_COMPARATOR = Comparator.comparingDouble(var0 -> var0.getDouble(1))
      .thenComparingDouble(var0 -> var0.getDouble(0))
      .thenComparingDouble(var0 -> var0.getDouble(2));
   public static final String SNBT_DATA_TAG = "data";
   private static final char PROPERTIES_START = '{';
   private static final char PROPERTIES_END = '}';
   private static final String ELEMENT_SEPARATOR = ",";
   private static final char KEY_VALUE_SEPARATOR = ':';
   private static final Splitter COMMA_SPLITTER = Splitter.on(",");
   private static final Splitter COLON_SPLITTER = Splitter.on(':').limit(2);
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int INDENT = 2;
   private static final int NOT_FOUND = -1;

   private NbtUtils() {
   }

   @Nullable
   public static GameProfile readGameProfile(CompoundTag var0) {
      String â˜ƒ = null;
      UUID â˜ƒx = null;
      if (â˜ƒ.contains("Name", 8)) {
         â˜ƒ = â˜ƒ.getString("Name");
      }

      if (â˜ƒ.hasUUID("Id")) {
         â˜ƒx = â˜ƒ.getUUID("Id");
      }

      try {
         GameProfile â˜ƒ = new GameProfile(â˜ƒx, â˜ƒ);
         if (â˜ƒ.contains("Properties", 10)) {
            CompoundTag â˜ƒx = â˜ƒ.getCompound("Properties");

            for(String â˜ƒxx : â˜ƒx.getAllKeys()) {
               ListTag â˜ƒxxx = â˜ƒx.getList(â˜ƒxx, 10);

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx.size(); ++â˜ƒxxxx) {
                  CompoundTag â˜ƒxxxxx = â˜ƒxxx.getCompound(â˜ƒxxxx);
                  String â˜ƒxxxxxx = â˜ƒxxxxx.getString("Value");
                  if (â˜ƒxxxxx.contains("Signature", 8)) {
                     â˜ƒ.getProperties().put(â˜ƒxx, new com.mojang.authlib.properties.Property(â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxxx.getString("Signature")));
                  } else {
                     â˜ƒ.getProperties().put(â˜ƒxx, new com.mojang.authlib.properties.Property(â˜ƒxx, â˜ƒxxxxxx));
                  }
               }
            }
         }

         return â˜ƒ;
      } catch (Throwable var11) {
         return null;
      }
   }

   public static CompoundTag writeGameProfile(CompoundTag var0, GameProfile var1) {
      if (!StringUtil.isNullOrEmpty(â˜ƒ.getName())) {
         â˜ƒ.putString("Name", â˜ƒ.getName());
      }

      if (â˜ƒ.getId() != null) {
         â˜ƒ.putUUID("Id", â˜ƒ.getId());
      }

      if (!â˜ƒ.getProperties().isEmpty()) {
         CompoundTag â˜ƒ = new CompoundTag();

         for(String â˜ƒx : â˜ƒ.getProperties().keySet()) {
            ListTag â˜ƒxx = new ListTag();

            for(com.mojang.authlib.properties.Property â˜ƒxxx : â˜ƒ.getProperties().get(â˜ƒx)) {
               CompoundTag â˜ƒxxxx = new CompoundTag();
               â˜ƒxxxx.putString("Value", â˜ƒxxx.getValue());
               if (â˜ƒxxx.hasSignature()) {
                  â˜ƒxxxx.putString("Signature", â˜ƒxxx.getSignature());
               }

               â˜ƒxx.add(â˜ƒxxxx);
            }

            â˜ƒ.put(â˜ƒx, â˜ƒxx);
         }

         â˜ƒ.put("Properties", â˜ƒ);
      }

      return â˜ƒ;
   }

   @VisibleForTesting
   public static boolean compareNbt(@Nullable Tag var0, @Nullable Tag var1, boolean var2) {
      if (â˜ƒ == â˜ƒ) {
         return true;
      } else if (â˜ƒ == null) {
         return true;
      } else if (â˜ƒ == null) {
         return false;
      } else if (!â˜ƒ.getClass().equals(â˜ƒ.getClass())) {
         return false;
      } else if (â˜ƒ instanceof CompoundTag â˜ƒ) {
         CompoundTag â˜ƒx = (CompoundTag)â˜ƒ;

         for(String â˜ƒxx : â˜ƒ.getAllKeys()) {
            Tag â˜ƒxxx = â˜ƒ.get(â˜ƒxx);
            if (!compareNbt(â˜ƒxxx, â˜ƒx.get(â˜ƒxx), â˜ƒ)) {
               return false;
            }
         }

         return true;
      } else if (â˜ƒ instanceof ListTag â˜ƒ && â˜ƒ) {
         ListTag â˜ƒx = (ListTag)â˜ƒ;
         if (â˜ƒ.isEmpty()) {
            return â˜ƒx.isEmpty();
         } else {
            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
               Tag â˜ƒxx = â˜ƒ.get(â˜ƒx);
               boolean â˜ƒxxx = false;

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒx.size(); ++â˜ƒxxxx) {
                  if (compareNbt(â˜ƒxx, â˜ƒx.get(â˜ƒxxxx), â˜ƒ)) {
                     â˜ƒxxx = true;
                     break;
                  }
               }

               if (!â˜ƒxxx) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return â˜ƒ.equals(â˜ƒ);
      }
   }

   public static IntArrayTag createUUID(UUID var0) {
      return new IntArrayTag(SerializableUUID.uuidToIntArray(â˜ƒ));
   }

   public static UUID loadUUID(Tag var0) {
      if (â˜ƒ.getType() != IntArrayTag.TYPE) {
         throw new IllegalArgumentException("Expected UUID-Tag to be of type " + IntArrayTag.TYPE.getName() + ", but found " + â˜ƒ.getType().getName() + ".");
      } else {
         int[] â˜ƒ = ((IntArrayTag)â˜ƒ).getAsIntArray();
         if (â˜ƒ.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + â˜ƒ.length + ".");
         } else {
            return SerializableUUID.uuidFromIntArray(â˜ƒ);
         }
      }
   }

   public static BlockPos readBlockPos(CompoundTag var0) {
      return new BlockPos(â˜ƒ.getInt("X"), â˜ƒ.getInt("Y"), â˜ƒ.getInt("Z"));
   }

   public static CompoundTag writeBlockPos(BlockPos var0) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putInt("X", â˜ƒ.getX());
      â˜ƒ.putInt("Y", â˜ƒ.getY());
      â˜ƒ.putInt("Z", â˜ƒ.getZ());
      return â˜ƒ;
   }

   public static BlockState readBlockState(CompoundTag var0) {
      if (!â˜ƒ.contains("Name", 8)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         Block â˜ƒ = Registry.BLOCK.get(new ResourceLocation(â˜ƒ.getString("Name")));
         BlockState â˜ƒx = â˜ƒ.defaultBlockState();
         if (â˜ƒ.contains("Properties", 10)) {
            CompoundTag â˜ƒxx = â˜ƒ.getCompound("Properties");
            StateDefinition<Block, BlockState> â˜ƒxxx = â˜ƒ.getStateDefinition();

            for(String â˜ƒxxxx : â˜ƒxx.getAllKeys()) {
               Property<?> â˜ƒxxxxx = â˜ƒxxx.getProperty(â˜ƒxxxx);
               if (â˜ƒxxxxx != null) {
                  â˜ƒx = setValueHelper(â˜ƒx, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxx, â˜ƒ);
               }
            }
         }

         return â˜ƒx;
      }
   }

   private static <S extends StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(
      S var0, Property<T> var1, String var2, CompoundTag var3, CompoundTag var4
   ) {
      Optional<T> â˜ƒ = â˜ƒ.getValue(â˜ƒ.getString(â˜ƒ));
      if (â˜ƒ.isPresent()) {
         return â˜ƒ.setValue(â˜ƒ, (Comparable)â˜ƒ.get());
      } else {
         LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", â˜ƒ, â˜ƒ.getString(â˜ƒ), â˜ƒ.toString());
         return â˜ƒ;
      }
   }

   public static CompoundTag writeBlockState(BlockState var0) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("Name", Registry.BLOCK.getKey(â˜ƒ.getBlock()).toString());
      ImmutableMap<Property<?>, Comparable<?>> â˜ƒx = â˜ƒ.getValues();
      if (!â˜ƒx.isEmpty()) {
         CompoundTag â˜ƒxx = new CompoundTag();

         for(Entry<Property<?>, Comparable<?>> â˜ƒxxx : â˜ƒx.entrySet()) {
            Property<?> â˜ƒxxxx = (Property)â˜ƒxxx.getKey();
            â˜ƒxx.putString(â˜ƒxxxx.getName(), getName(â˜ƒxxxx, (Comparable<?>)â˜ƒxxx.getValue()));
         }

         â˜ƒ.put("Properties", â˜ƒxx);
      }

      return â˜ƒ;
   }

   public static CompoundTag writeFluidState(FluidState var0) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("Name", Registry.FLUID.getKey(â˜ƒ.getType()).toString());
      ImmutableMap<Property<?>, Comparable<?>> â˜ƒx = â˜ƒ.getValues();
      if (!â˜ƒx.isEmpty()) {
         CompoundTag â˜ƒxx = new CompoundTag();

         for(Entry<Property<?>, Comparable<?>> â˜ƒxxx : â˜ƒx.entrySet()) {
            Property<?> â˜ƒxxxx = (Property)â˜ƒxxx.getKey();
            â˜ƒxx.putString(â˜ƒxxxx.getName(), getName(â˜ƒxxxx, (Comparable<?>)â˜ƒxxx.getValue()));
         }

         â˜ƒ.put("Properties", â˜ƒxx);
      }

      return â˜ƒ;
   }

   private static <T extends Comparable<T>> String getName(Property<T> var0, Comparable<?> var1) {
      return â˜ƒ.getName((T)â˜ƒ);
   }

   public static String prettyPrint(Tag var0) {
      return prettyPrint(â˜ƒ, false);
   }

   public static String prettyPrint(Tag var0, boolean var1) {
      return prettyPrint(new StringBuilder(), â˜ƒ, 0, â˜ƒ).toString();
   }

   public static StringBuilder prettyPrint(StringBuilder var0, Tag var1, int var2, boolean var3) {
      switch(â˜ƒ.getId()) {
         case 0:
            break;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 8:
            â˜ƒ.append(â˜ƒ);
            break;
         case 7:
            ByteArrayTag â˜ƒ = (ByteArrayTag)â˜ƒ;
            byte[] â˜ƒx = â˜ƒ.getAsByteArray();
            int â˜ƒxx = â˜ƒx.length;
            indent(â˜ƒ, â˜ƒ).append("byte[").append(â˜ƒxx).append("] {\n");
            if (â˜ƒ) {
               indent(â˜ƒ + 1, â˜ƒ);

               for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx.length; ++â˜ƒxxx) {
                  if (â˜ƒxxx != 0) {
                     â˜ƒ.append(',');
                  }

                  if (â˜ƒxxx % 16 == 0 && â˜ƒxxx / 16 > 0) {
                     â˜ƒ.append('\n');
                     if (â˜ƒxxx < â˜ƒx.length) {
                        indent(â˜ƒ + 1, â˜ƒ);
                     }
                  } else if (â˜ƒxxx != 0) {
                     â˜ƒ.append(' ');
                  }

                  â˜ƒ.append(String.format("0x%02X", â˜ƒx[â˜ƒxxx] & 255));
               }
            } else {
               indent(â˜ƒ + 1, â˜ƒ).append(" // Skipped, supply withBinaryBlobs true");
            }

            â˜ƒ.append('\n');
            indent(â˜ƒ, â˜ƒ).append('}');
            break;
         case 9:
            ListTag â˜ƒ = (ListTag)â˜ƒ;
            int â˜ƒx = â˜ƒ.size();
            int â˜ƒxx = â˜ƒ.getElementType();
            String â˜ƒxxx = â˜ƒxx == 0 ? "undefined" : TagTypes.getType(â˜ƒxx).getPrettyName();
            indent(â˜ƒ, â˜ƒ).append("list<").append(â˜ƒxxx).append(">[").append(â˜ƒx).append("] [");
            if (â˜ƒx != 0) {
               â˜ƒ.append('\n');
            }

            for(int â˜ƒ = 0; â˜ƒ < â˜ƒx; ++â˜ƒ) {
               if (â˜ƒ != 0) {
                  â˜ƒ.append(",\n");
               }

               indent(â˜ƒ + 1, â˜ƒ);
               prettyPrint(â˜ƒ, â˜ƒ.get(â˜ƒ), â˜ƒ + 1, â˜ƒ);
            }

            if (â˜ƒx != 0) {
               â˜ƒ.append('\n');
            }

            indent(â˜ƒ, â˜ƒ).append(']');
            break;
         case 10:
            CompoundTag â˜ƒ = (CompoundTag)â˜ƒ;
            List<String> â˜ƒx = Lists.newArrayList(â˜ƒ.getAllKeys());
            Collections.sort(â˜ƒx);
            indent(â˜ƒ, â˜ƒ).append('{');
            if (â˜ƒ.length() - â˜ƒ.lastIndexOf("\n") > 2 * (â˜ƒ + 1)) {
               â˜ƒ.append('\n');
               indent(â˜ƒ + 1, â˜ƒ);
            }

            int â˜ƒ = â˜ƒx.stream().mapToInt(String::length).max().orElse(0);
            String â˜ƒx = Strings.repeat(" ", â˜ƒ);

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
               if (â˜ƒxx != 0) {
                  â˜ƒ.append(",\n");
               }

               String â˜ƒxxx = (String)â˜ƒx.get(â˜ƒxx);
               indent(â˜ƒ + 1, â˜ƒ).append('"').append(â˜ƒxxx).append('"').append(â˜ƒx, 0, â˜ƒx.length() - â˜ƒxxx.length()).append(": ");
               prettyPrint(â˜ƒ, â˜ƒ.get(â˜ƒxxx), â˜ƒ + 1, â˜ƒ);
            }

            if (!â˜ƒx.isEmpty()) {
               â˜ƒ.append('\n');
            }

            indent(â˜ƒ, â˜ƒ).append('}');
            break;
         case 11:
            IntArrayTag â˜ƒ = (IntArrayTag)â˜ƒ;
            int[] â˜ƒx = â˜ƒ.getAsIntArray();
            int â˜ƒxx = 0;

            for(int â˜ƒxxx : â˜ƒx) {
               â˜ƒxx = Math.max(â˜ƒxx, String.format("%X", â˜ƒxxx).length());
            }

            int â˜ƒxxx = â˜ƒx.length;
            indent(â˜ƒ, â˜ƒ).append("int[").append(â˜ƒxxx).append("] {\n");
            if (â˜ƒ) {
               indent(â˜ƒ + 1, â˜ƒ);

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒx.length; ++â˜ƒxxxx) {
                  if (â˜ƒxxxx != 0) {
                     â˜ƒ.append(',');
                  }

                  if (â˜ƒxxxx % 16 == 0 && â˜ƒxxxx / 16 > 0) {
                     â˜ƒ.append('\n');
                     if (â˜ƒxxxx < â˜ƒx.length) {
                        indent(â˜ƒ + 1, â˜ƒ);
                     }
                  } else if (â˜ƒxxxx != 0) {
                     â˜ƒ.append(' ');
                  }

                  â˜ƒ.append(String.format("0x%0" + â˜ƒxx + "X", â˜ƒx[â˜ƒxxxx]));
               }
            } else {
               indent(â˜ƒ + 1, â˜ƒ).append(" // Skipped, supply withBinaryBlobs true");
            }

            â˜ƒ.append('\n');
            indent(â˜ƒ, â˜ƒ).append('}');
            break;
         case 12:
            LongArrayTag â˜ƒ = (LongArrayTag)â˜ƒ;
            long[] â˜ƒx = â˜ƒ.getAsLongArray();
            long â˜ƒxx = 0L;

            for(long â˜ƒxxx : â˜ƒx) {
               â˜ƒxx = Math.max(â˜ƒxx, (long)String.format("%X", â˜ƒxxx).length());
            }

            long â˜ƒxxx = (long)â˜ƒx.length;
            indent(â˜ƒ, â˜ƒ).append("long[").append(â˜ƒxxx).append("] {\n");
            if (â˜ƒ) {
               indent(â˜ƒ + 1, â˜ƒ);

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒx.length; ++â˜ƒxxxx) {
                  if (â˜ƒxxxx != 0) {
                     â˜ƒ.append(',');
                  }

                  if (â˜ƒxxxx % 16 == 0 && â˜ƒxxxx / 16 > 0) {
                     â˜ƒ.append('\n');
                     if (â˜ƒxxxx < â˜ƒx.length) {
                        indent(â˜ƒ + 1, â˜ƒ);
                     }
                  } else if (â˜ƒxxxx != 0) {
                     â˜ƒ.append(' ');
                  }

                  â˜ƒ.append(String.format("0x%0" + â˜ƒxx + "X", â˜ƒx[â˜ƒxxxx]));
               }
            } else {
               indent(â˜ƒ + 1, â˜ƒ).append(" // Skipped, supply withBinaryBlobs true");
            }

            â˜ƒ.append('\n');
            indent(â˜ƒ, â˜ƒ).append('}');
            break;
         default:
            â˜ƒ.append("<UNKNOWN :(>");
      }

      return â˜ƒ;
   }

   private static StringBuilder indent(int var0, StringBuilder var1) {
      int â˜ƒ = â˜ƒ.lastIndexOf("\n") + 1;
      int â˜ƒx = â˜ƒ.length() - â˜ƒ;

      for(int â˜ƒxx = 0; â˜ƒxx < 2 * â˜ƒ - â˜ƒx; ++â˜ƒxx) {
         â˜ƒ.append(' ');
      }

      return â˜ƒ;
   }

   public static CompoundTag update(DataFixer var0, DataFixTypes var1, CompoundTag var2, int var3) {
      return update(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, SharedConstants.getCurrentVersion().getWorldVersion());
   }

   public static CompoundTag update(DataFixer var0, DataFixTypes var1, CompoundTag var2, int var3, int var4) {
      return â˜ƒ.update(â˜ƒ.getType(), new Dynamic<>(NbtOps.INSTANCE, â˜ƒ), â˜ƒ, â˜ƒ).getValue();
   }

   public static Component toPrettyComponent(Tag var0) {
      return new TextComponentTagVisitor("", 0).visit(â˜ƒ);
   }

   public static String structureToSnbt(CompoundTag var0) {
      return new SnbtPrinterTagVisitor().visit(packStructureTemplate(â˜ƒ));
   }

   public static CompoundTag snbtToStructure(String var0) throws CommandSyntaxException {
      return unpackStructureTemplate(TagParser.parseTag(â˜ƒ));
   }

   @VisibleForTesting
   static CompoundTag packStructureTemplate(CompoundTag var0) {
      boolean â˜ƒx = â˜ƒ.contains("palettes", 9);
      ListTag â˜ƒ;
      if (â˜ƒx) {
         â˜ƒ = â˜ƒ.getList("palettes", 9).getList(0);
      } else {
         â˜ƒ = â˜ƒ.getList("palette", 10);
      }

      ListTag â˜ƒ = (ListTag)â˜ƒ.stream()
         .map(CompoundTag.class::cast)
         .map(NbtUtils::packBlockState)
         .map(StringTag::valueOf)
         .collect(Collectors.toCollection(ListTag::new));
      â˜ƒ.put("palette", â˜ƒ);
      if (â˜ƒx) {
         ListTag â˜ƒx = new ListTag();
         ListTag â˜ƒxx = â˜ƒ.getList("palettes", 9);
         â˜ƒxx.stream().map(ListTag.class::cast).forEach(var2x -> {
            CompoundTag â˜ƒ = new CompoundTag();

            for(int â˜ƒx = 0; â˜ƒx < var2x.size(); ++â˜ƒx) {
               â˜ƒ.putString(â˜ƒ.getString(â˜ƒx), packBlockState(var2x.getCompound(â˜ƒx)));
            }

            â˜ƒ.add(â˜ƒ);
         });
         â˜ƒ.put("palettes", â˜ƒx);
      }

      if (â˜ƒ.contains("entities", 10)) {
         ListTag â˜ƒ = â˜ƒ.getList("entities", 10);
         ListTag â˜ƒx = (ListTag)â˜ƒ.stream()
            .map(CompoundTag.class::cast)
            .sorted(Comparator.comparing(var0x -> var0x.getList("pos", 6), YXZ_LISTTAG_DOUBLE_COMPARATOR))
            .collect(Collectors.toCollection(ListTag::new));
         â˜ƒ.put("entities", â˜ƒx);
      }

      ListTag â˜ƒ = (ListTag)â˜ƒ.getList("blocks", 10)
         .stream()
         .map(CompoundTag.class::cast)
         .sorted(Comparator.comparing(var0x -> var0x.getList("pos", 3), YXZ_LISTTAG_INT_COMPARATOR))
         .peek(var1x -> var1x.putString("state", â˜ƒ.getString(var1x.getInt("state"))))
         .collect(Collectors.toCollection(ListTag::new));
      â˜ƒ.put("data", â˜ƒ);
      â˜ƒ.remove("blocks");
      return â˜ƒ;
   }

   @VisibleForTesting
   static CompoundTag unpackStructureTemplate(CompoundTag var0) {
      ListTag â˜ƒ = â˜ƒ.getList("palette", 8);
      Map<String, Tag> â˜ƒx = (Map)â˜ƒ.stream()
         .map(StringTag.class::cast)
         .map(StringTag::getAsString)
         .collect(ImmutableMap.toImmutableMap(Function.identity(), NbtUtils::unpackBlockState));
      if (â˜ƒ.contains("palettes", 9)) {
         â˜ƒ.put(
            "palettes",
            (Tag)â˜ƒ.getList("palettes", 10)
               .stream()
               .map(CompoundTag.class::cast)
               .map(
                  var1x -> (ListTag)â˜ƒ.keySet().stream().map(var1x::getString).map(NbtUtils::unpackBlockState).collect(Collectors.toCollection(ListTag::new))
               )
               .collect(Collectors.toCollection(ListTag::new))
         );
         â˜ƒ.remove("palette");
      } else {
         â˜ƒ.put("palette", (Tag)â˜ƒx.values().stream().collect(Collectors.toCollection(ListTag::new)));
      }

      if (â˜ƒ.contains("data", 9)) {
         Object2IntMap<String> â˜ƒ = new Object2IntOpenHashMap();
         â˜ƒ.defaultReturnValue(-1);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            â˜ƒ.put(â˜ƒ.getString(â˜ƒx), â˜ƒx);
         }

         ListTag â˜ƒx = â˜ƒ.getList("data", 10);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
            String â˜ƒxxxx = â˜ƒxxx.getString("state");
            int â˜ƒxxxxx = â˜ƒ.getInt(â˜ƒxxxx);
            if (â˜ƒxxxxx == -1) {
               throw new IllegalStateException("Entry " + â˜ƒxxxx + " missing from palette");
            }

            â˜ƒxxx.putInt("state", â˜ƒxxxxx);
         }

         â˜ƒ.put("blocks", â˜ƒx);
         â˜ƒ.remove("data");
      }

      return â˜ƒ;
   }

   @VisibleForTesting
   static String packBlockState(CompoundTag var0) {
      StringBuilder â˜ƒ = new StringBuilder(â˜ƒ.getString("Name"));
      if (â˜ƒ.contains("Properties", 10)) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound("Properties");
         String â˜ƒxx = (String)â˜ƒx.getAllKeys().stream().sorted().map(var1x -> var1x + ":" + â˜ƒ.get(var1x).getAsString()).collect(Collectors.joining(","));
         â˜ƒ.append('{').append(â˜ƒxx).append('}');
      }

      return â˜ƒ.toString();
   }

   @VisibleForTesting
   static CompoundTag unpackBlockState(String var0) {
      CompoundTag â˜ƒx = new CompoundTag();
      int â˜ƒxx = â˜ƒ.indexOf(123);
      String â˜ƒ;
      if (â˜ƒxx >= 0) {
         â˜ƒ = â˜ƒ.substring(0, â˜ƒxx);
         CompoundTag â˜ƒxxx = new CompoundTag();
         if (â˜ƒxx + 2 <= â˜ƒ.length()) {
            String â˜ƒxxxx = â˜ƒ.substring(â˜ƒxx + 1, â˜ƒ.indexOf(125, â˜ƒxx));
            COMMA_SPLITTER.split(â˜ƒxxxx).forEach(var2x -> {
               List<String> â˜ƒ = COLON_SPLITTER.splitToList(var2x);
               if (â˜ƒ.size() == 2) {
                  â˜ƒ.putString((String)â˜ƒ.get(0), (String)â˜ƒ.get(1));
               } else {
                  LOGGER.error("Something went wrong parsing: '{}' -- incorrect gamedata!", â˜ƒ);
               }
            });
            â˜ƒx.put("Properties", â˜ƒxxx);
         }
      } else {
         â˜ƒ = â˜ƒ;
      }

      â˜ƒx.putString("Name", â˜ƒ);
      return â˜ƒx;
   }
}
