package net.minecraft.world.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;

public class SpawnData extends WeightedEntry.IntrusiveBase {
   public static final int DEFAULT_WEIGHT = 1;
   public static final String DEFAULT_TYPE = "minecraft:pig";
   private final CompoundTag tag;

   public SpawnData() {
      super(1);
      this.tag = new CompoundTag();
      this.tag.putString("id", "minecraft:pig");
   }

   public SpawnData(CompoundTag var1) {
      this(â˜ƒ.contains("Weight", 99) ? â˜ƒ.getInt("Weight") : 1, â˜ƒ.getCompound("Entity"));
   }

   public SpawnData(int var1, CompoundTag var2) {
      super(â˜ƒ);
      this.tag = â˜ƒ;
      ResourceLocation â˜ƒ = ResourceLocation.tryParse(â˜ƒ.getString("id"));
      if (â˜ƒ != null) {
         â˜ƒ.putString("id", â˜ƒ.toString());
      } else {
         â˜ƒ.putString("id", "minecraft:pig");
      }
   }

   public CompoundTag save() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.put("Entity", this.tag);
      â˜ƒ.putInt("Weight", this.getWeight().asInt());
      return â˜ƒ;
   }

   public CompoundTag getTag() {
      return this.tag;
   }
}
