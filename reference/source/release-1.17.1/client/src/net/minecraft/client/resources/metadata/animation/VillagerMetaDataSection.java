package net.minecraft.client.resources.metadata.animation;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class VillagerMetaDataSection {
   public static final VillagerMetadataSectionSerializer SERIALIZER = new VillagerMetadataSectionSerializer();
   public static final String SECTION_NAME = "villager";
   private final VillagerMetaDataSection.Hat hat;

   public VillagerMetaDataSection(VillagerMetaDataSection.Hat var1) {
      this.hat = â˜ƒ;
   }

   public VillagerMetaDataSection.Hat getHat() {
      return this.hat;
   }

   public static enum Hat {
      NONE("none"),
      PARTIAL("partial"),
      FULL("full");

      private static final Map<String, VillagerMetaDataSection.Hat> BY_NAME = (Map<String, VillagerMetaDataSection.Hat>)Arrays.stream(values())
         .collect(Collectors.toMap(VillagerMetaDataSection.Hat::getName, var0 -> var0));
      private final String name;

      private Hat(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public static VillagerMetaDataSection.Hat getByName(String var0) {
         return (VillagerMetaDataSection.Hat)BY_NAME.getOrDefault(â˜ƒ, NONE);
      }
   }
}
