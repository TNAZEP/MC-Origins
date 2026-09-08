package net.minecraft.stats;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.RecipeBookType;

public final class RecipeBookSettings {
   private static final Map<RecipeBookType, Pair<String, String>> TAG_FIELDS = ImmutableMap.of(
      RecipeBookType.CRAFTING,
      Pair.of("isGuiOpen", "isFilteringCraftable"),
      RecipeBookType.FURNACE,
      Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"),
      RecipeBookType.BLAST_FURNACE,
      Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"),
      RecipeBookType.SMOKER,
      Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable")
   );
   private final Map<RecipeBookType, RecipeBookSettings.TypeSettings> states;

   private RecipeBookSettings(Map<RecipeBookType, RecipeBookSettings.TypeSettings> var1) {
      this.states = â˜ƒ;
   }

   public RecipeBookSettings() {
      this(Util.make(Maps.newEnumMap(RecipeBookType.class), var0 -> {
         for(RecipeBookType â˜ƒ : RecipeBookType.values()) {
            var0.put(â˜ƒ, new RecipeBookSettings.TypeSettings(false, false));
         }
      }));
   }

   public boolean isOpen(RecipeBookType var1) {
      return ((RecipeBookSettings.TypeSettings)this.states.get(â˜ƒ)).open;
   }

   public void setOpen(RecipeBookType var1, boolean var2) {
      ((RecipeBookSettings.TypeSettings)this.states.get(â˜ƒ)).open = â˜ƒ;
   }

   public boolean isFiltering(RecipeBookType var1) {
      return ((RecipeBookSettings.TypeSettings)this.states.get(â˜ƒ)).filtering;
   }

   public void setFiltering(RecipeBookType var1, boolean var2) {
      ((RecipeBookSettings.TypeSettings)this.states.get(â˜ƒ)).filtering = â˜ƒ;
   }

   public static RecipeBookSettings read(FriendlyByteBuf var0) {
      Map<RecipeBookType, RecipeBookSettings.TypeSettings> â˜ƒ = Maps.newEnumMap(RecipeBookType.class);

      for(RecipeBookType â˜ƒx : RecipeBookType.values()) {
         boolean â˜ƒxx = â˜ƒ.readBoolean();
         boolean â˜ƒxxx = â˜ƒ.readBoolean();
         â˜ƒ.put(â˜ƒx, new RecipeBookSettings.TypeSettings(â˜ƒxx, â˜ƒxxx));
      }

      return new RecipeBookSettings(â˜ƒ);
   }

   public void write(FriendlyByteBuf var1) {
      for(RecipeBookType â˜ƒ : RecipeBookType.values()) {
         RecipeBookSettings.TypeSettings â˜ƒx = (RecipeBookSettings.TypeSettings)this.states.get(â˜ƒ);
         if (â˜ƒx == null) {
            â˜ƒ.writeBoolean(false);
            â˜ƒ.writeBoolean(false);
         } else {
            â˜ƒ.writeBoolean(â˜ƒx.open);
            â˜ƒ.writeBoolean(â˜ƒx.filtering);
         }
      }
   }

   public static RecipeBookSettings read(CompoundTag var0) {
      Map<RecipeBookType, RecipeBookSettings.TypeSettings> â˜ƒ = Maps.newEnumMap(RecipeBookType.class);
      TAG_FIELDS.forEach((var2, var3) -> {
         boolean â˜ƒ = â˜ƒ.getBoolean((String)var3.getFirst());
         boolean â˜ƒx = â˜ƒ.getBoolean((String)var3.getSecond());
         â˜ƒ.put(var2, new RecipeBookSettings.TypeSettings(â˜ƒ, â˜ƒx));
      });
      return new RecipeBookSettings(â˜ƒ);
   }

   public void write(CompoundTag var1) {
      TAG_FIELDS.forEach((var2, var3) -> {
         RecipeBookSettings.TypeSettings â˜ƒ = (RecipeBookSettings.TypeSettings)this.states.get(var2);
         â˜ƒ.putBoolean((String)var3.getFirst(), â˜ƒ.open);
         â˜ƒ.putBoolean((String)var3.getSecond(), â˜ƒ.filtering);
      });
   }

   public RecipeBookSettings copy() {
      Map<RecipeBookType, RecipeBookSettings.TypeSettings> â˜ƒ = Maps.newEnumMap(RecipeBookType.class);

      for(RecipeBookType â˜ƒx : RecipeBookType.values()) {
         RecipeBookSettings.TypeSettings â˜ƒxx = (RecipeBookSettings.TypeSettings)this.states.get(â˜ƒx);
         â˜ƒ.put(â˜ƒx, â˜ƒxx.copy());
      }

      return new RecipeBookSettings(â˜ƒ);
   }

   public void replaceFrom(RecipeBookSettings var1) {
      this.states.clear();

      for(RecipeBookType â˜ƒ : RecipeBookType.values()) {
         RecipeBookSettings.TypeSettings â˜ƒx = (RecipeBookSettings.TypeSettings)â˜ƒ.states.get(â˜ƒ);
         this.states.put(â˜ƒ, â˜ƒx.copy());
      }
   }

   public boolean equals(Object var1) {
      return this == â˜ƒ || â˜ƒ instanceof RecipeBookSettings && this.states.equals(((RecipeBookSettings)â˜ƒ).states);
   }

   public int hashCode() {
      return this.states.hashCode();
   }

   static final class TypeSettings {
      boolean open;
      boolean filtering;

      public TypeSettings(boolean var1, boolean var2) {
         this.open = â˜ƒ;
         this.filtering = â˜ƒ;
      }

      public RecipeBookSettings.TypeSettings copy() {
         return new RecipeBookSettings.TypeSettings(this.open, this.filtering);
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (!(â˜ƒ instanceof RecipeBookSettings.TypeSettings)) {
            return false;
         } else {
            RecipeBookSettings.TypeSettings â˜ƒ = (RecipeBookSettings.TypeSettings)â˜ƒ;
            return this.open == â˜ƒ.open && this.filtering == â˜ƒ.filtering;
         }
      }

      public int hashCode() {
         int â˜ƒ = this.open ? 1 : 0;
         return 31 * â˜ƒ + (this.filtering ? 1 : 0);
      }

      public String toString() {
         return "[open=" + this.open + ", filtering=" + this.filtering + "]";
      }
   }
}
