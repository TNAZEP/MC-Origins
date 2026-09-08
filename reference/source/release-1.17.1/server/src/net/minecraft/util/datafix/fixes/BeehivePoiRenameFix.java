package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;

public class BeehivePoiRenameFix extends PoiTypeRename {
   public BeehivePoiRenameFix(Schema var1) {
      super(â˜ƒ, false);
   }

   @Override
   protected String rename(String var1) {
      return â˜ƒ.equals("minecraft:bee_hive") ? "minecraft:beehive" : â˜ƒ;
   }
}
