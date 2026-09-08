package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.DSL.TypeReference;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.IProperty;
import net.minecraft.state.IStateHolder;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NBTUtil {
   private static final Logger field_193591_a = LogManager.getLogger();

   @Nullable
   public static GameProfile func_152459_a(NBTTagCompound var0) {
      String ☃ = null;
      String ☃x = null;
      if (☃.func_150297_b("Name", 8)) {
         ☃ = ☃.func_74779_i("Name");
      }

      if (☃.func_150297_b("Id", 8)) {
         ☃x = ☃.func_74779_i("Id");
      }

      try {
         UUID ☃;
         try {
            ☃ = UUID.fromString(☃x);
         } catch (Throwable var12) {
            ☃ = null;
         }

         GameProfile ☃ = new GameProfile(☃, ☃);
         if (☃.func_150297_b("Properties", 10)) {
            NBTTagCompound ☃x = ☃.func_74775_l("Properties");

            for(String ☃xx : ☃x.func_150296_c()) {
               NBTTagList ☃xxx = ☃x.func_150295_c(☃xx, 10);

               for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
                  NBTTagCompound ☃xxxxx = ☃xxx.func_150305_b(☃xxxx);
                  String ☃xxxxxx = ☃xxxxx.func_74779_i("Value");
                  if (☃xxxxx.func_150297_b("Signature", 8)) {
                     ☃.getProperties().put(☃xx, new Property(☃xx, ☃xxxxxx, ☃xxxxx.func_74779_i("Signature")));
                  } else {
                     ☃.getProperties().put(☃xx, new Property(☃xx, ☃xxxxxx));
                  }
               }
            }
         }

         return ☃;
      } catch (Throwable var13) {
         return null;
      }
   }

   public static NBTTagCompound func_180708_a(NBTTagCompound var0, GameProfile var1) {
      if (!StringUtils.func_151246_b(☃.getName())) {
         ☃.func_74778_a("Name", ☃.getName());
      }

      if (☃.getId() != null) {
         ☃.func_74778_a("Id", ☃.getId().toString());
      }

      if (!☃.getProperties().isEmpty()) {
         NBTTagCompound ☃ = new NBTTagCompound();

         for(String ☃x : ☃.getProperties().keySet()) {
            NBTTagList ☃xx = new NBTTagList();

            for(Property ☃xxx : ☃.getProperties().get(☃x)) {
               NBTTagCompound ☃xxxx = new NBTTagCompound();
               ☃xxxx.func_74778_a("Value", ☃xxx.getValue());
               if (☃xxx.hasSignature()) {
                  ☃xxxx.func_74778_a("Signature", ☃xxx.getSignature());
               }

               ☃xx.add((INBTBase)☃xxxx);
            }

            ☃.func_74782_a(☃x, ☃xx);
         }

         ☃.func_74782_a("Properties", ☃);
      }

      return ☃;
   }

   @VisibleForTesting
   public static boolean func_181123_a(@Nullable INBTBase var0, @Nullable INBTBase var1, boolean var2) {
      if (☃ == ☃) {
         return true;
      } else if (☃ == null) {
         return true;
      } else if (☃ == null) {
         return false;
      } else if (!☃.getClass().equals(☃.getClass())) {
         return false;
      } else if (☃ instanceof NBTTagCompound) {
         NBTTagCompound ☃ = (NBTTagCompound)☃;
         NBTTagCompound ☃x = (NBTTagCompound)☃;

         for(String ☃xx : ☃.func_150296_c()) {
            INBTBase ☃xxx = ☃.func_74781_a(☃xx);
            if (!func_181123_a(☃xxx, ☃x.func_74781_a(☃xx), ☃)) {
               return false;
            }
         }

         return true;
      } else if (☃ instanceof NBTTagList && ☃) {
         NBTTagList ☃ = (NBTTagList)☃;
         NBTTagList ☃x = (NBTTagList)☃;
         if (☃.isEmpty()) {
            return ☃x.isEmpty();
         } else {
            for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
               INBTBase ☃x = ☃.get(☃);
               boolean ☃xx = false;

               for(int ☃xxx = 0; ☃xxx < ☃x.size(); ++☃xxx) {
                  if (func_181123_a(☃x, ☃x.get(☃xxx), ☃)) {
                     ☃xx = true;
                     break;
                  }
               }

               if (!☃xx) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return ☃.equals(☃);
      }
   }

   public static NBTTagCompound func_186862_a(UUID var0) {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74772_a("M", ☃.getMostSignificantBits());
      ☃.func_74772_a("L", ☃.getLeastSignificantBits());
      return ☃;
   }

   public static UUID func_186860_b(NBTTagCompound var0) {
      return new UUID(☃.func_74763_f("M"), ☃.func_74763_f("L"));
   }

   public static BlockPos func_186861_c(NBTTagCompound var0) {
      return new BlockPos(☃.func_74762_e("X"), ☃.func_74762_e("Y"), ☃.func_74762_e("Z"));
   }

   public static NBTTagCompound func_186859_a(BlockPos var0) {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74768_a("X", ☃.func_177958_n());
      ☃.func_74768_a("Y", ☃.func_177956_o());
      ☃.func_74768_a("Z", ☃.func_177952_p());
      return ☃;
   }

   public static IBlockState func_190008_d(NBTTagCompound var0) {
      if (!☃.func_150297_b("Name", 8)) {
         return Blocks.field_150350_a.func_176223_P();
      } else {
         Block ☃ = IRegistry.field_212618_g.func_82594_a(new ResourceLocation(☃.func_74779_i("Name")));
         IBlockState ☃x = ☃.func_176223_P();
         if (☃.func_150297_b("Properties", 10)) {
            NBTTagCompound ☃xx = ☃.func_74775_l("Properties");
            StateContainer<Block, IBlockState> ☃xxx = ☃.func_176194_O();

            for(String ☃xxxx : ☃xx.func_150296_c()) {
               IProperty<?> ☃xxxxx = ☃xxx.func_185920_a(☃xxxx);
               if (☃xxxxx != null) {
                  ☃x = func_193590_a(☃x, ☃xxxxx, ☃xxxx, ☃xx, ☃);
               }
            }
         }

         return ☃x;
      }
   }

   private static <S extends IStateHolder<S>, T extends Comparable<T>> S func_193590_a(
      S var0, IProperty<T> var1, String var2, NBTTagCompound var3, NBTTagCompound var4
   ) {
      Optional<T> ☃ = ☃.func_185929_b(☃.func_74779_i(☃));
      if (☃.isPresent()) {
         return ☃.func_206870_a(☃, (Comparable)☃.get());
      } else {
         field_193591_a.warn("Unable to read property: {} with value: {} for blockstate: {}", ☃, ☃.func_74779_i(☃), ☃.toString());
         return ☃;
      }
   }

   public static NBTTagCompound func_190009_a(IBlockState var0) {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74778_a("Name", IRegistry.field_212618_g.func_177774_c(☃.func_177230_c()).toString());
      ImmutableMap<IProperty<?>, Comparable<?>> ☃x = ☃.func_206871_b();
      if (!☃x.isEmpty()) {
         NBTTagCompound ☃xx = new NBTTagCompound();

         for(Entry<IProperty<?>, Comparable<?>> ☃xxx : ☃x.entrySet()) {
            IProperty<?> ☃xxxx = (IProperty)☃xxx.getKey();
            ☃xx.func_74778_a(☃xxxx.func_177701_a(), func_190010_a(☃xxxx, (Comparable<?>)☃xxx.getValue()));
         }

         ☃.func_74782_a("Properties", ☃xx);
      }

      return ☃;
   }

   private static <T extends Comparable<T>> String func_190010_a(IProperty<T> var0, Comparable<?> var1) {
      return ☃.func_177702_a((T)☃);
   }

   public static NBTTagCompound func_210822_a(DataFixer var0, TypeReference var1, NBTTagCompound var2, int var3) {
      return func_210821_a(☃, ☃, ☃, ☃, 1631);
   }

   public static NBTTagCompound func_210821_a(DataFixer var0, TypeReference var1, NBTTagCompound var2, int var3, int var4) {
      return ☃.update(☃, new Dynamic<>(NBTDynamicOps.field_210820_a, ☃), ☃, ☃).getValue();
   }
}
