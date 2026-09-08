package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.world.level.block.state.properties.ChestType;

public class PackResourcesAdapterV4 implements PackResources {
   private static final Map<String, Pair<ChestType, ResourceLocation>> CHESTS = Util.make(Maps.newHashMap(), var0 -> {
      var0.put("textures/entity/chest/normal_left.png", new Pair<>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/normal_double.png")));
      var0.put("textures/entity/chest/normal_right.png", new Pair<>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/normal_double.png")));
      var0.put("textures/entity/chest/normal.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/normal.png")));
      var0.put("textures/entity/chest/trapped_left.png", new Pair<>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/trapped_double.png")));
      var0.put("textures/entity/chest/trapped_right.png", new Pair<>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/trapped_double.png")));
      var0.put("textures/entity/chest/trapped.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/trapped.png")));
      var0.put("textures/entity/chest/christmas_left.png", new Pair<>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/christmas_double.png")));
      var0.put("textures/entity/chest/christmas_right.png", new Pair<>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/christmas_double.png")));
      var0.put("textures/entity/chest/christmas.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/christmas.png")));
      var0.put("textures/entity/chest/ender.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/ender.png")));
   });
   private static final List<String> PATTERNS = Lists.newArrayList(
      "base",
      "border",
      "bricks",
      "circle",
      "creeper",
      "cross",
      "curly_border",
      "diagonal_left",
      "diagonal_right",
      "diagonal_up_left",
      "diagonal_up_right",
      "flower",
      "globe",
      "gradient",
      "gradient_up",
      "half_horizontal",
      "half_horizontal_bottom",
      "half_vertical",
      "half_vertical_right",
      "mojang",
      "rhombus",
      "skull",
      "small_stripes",
      "square_bottom_left",
      "square_bottom_right",
      "square_top_left",
      "square_top_right",
      "straight_cross",
      "stripe_bottom",
      "stripe_center",
      "stripe_downleft",
      "stripe_downright",
      "stripe_left",
      "stripe_middle",
      "stripe_right",
      "stripe_top",
      "triangle_bottom",
      "triangle_top",
      "triangles_bottom",
      "triangles_top"
   );
   private static final Set<String> SHIELDS = (Set<String>)PATTERNS.stream().map(var0 -> "textures/entity/shield/" + var0 + ".png").collect(Collectors.toSet());
   private static final Set<String> BANNERS = (Set<String>)PATTERNS.stream().map(var0 -> "textures/entity/banner/" + var0 + ".png").collect(Collectors.toSet());
   public static final ResourceLocation SHIELD_BASE = new ResourceLocation("textures/entity/shield_base.png");
   public static final ResourceLocation BANNER_BASE = new ResourceLocation("textures/entity/banner_base.png");
   public static final int DEFAULT_CHEST_SIZE = 64;
   public static final int DEFAULT_SHIELD_SIZE = 64;
   public static final int DEFAULT_BANNER_SIZE = 64;
   public static final ResourceLocation OLD_IRON_GOLEM_LOCATION = new ResourceLocation("textures/entity/iron_golem.png");
   public static final String NEW_IRON_GOLEM_PATH = "textures/entity/iron_golem/iron_golem.png";
   private final PackResources pack;

   public PackResourcesAdapterV4(PackResources var1) {
      this.pack = â˜ƒ;
   }

   @Override
   public InputStream getRootResource(String var1) throws IOException {
      return this.pack.getRootResource(â˜ƒ);
   }

   @Override
   public boolean hasResource(PackType var1, ResourceLocation var2) {
      if (!"minecraft".equals(â˜ƒ.getNamespace())) {
         return this.pack.hasResource(â˜ƒ, â˜ƒ);
      } else {
         String â˜ƒ = â˜ƒ.getPath();
         if ("textures/misc/enchanted_item_glint.png".equals(â˜ƒ)) {
            return false;
         } else if ("textures/entity/iron_golem/iron_golem.png".equals(â˜ƒ)) {
            return this.pack.hasResource(â˜ƒ, OLD_IRON_GOLEM_LOCATION);
         } else if ("textures/entity/conduit/wind.png".equals(â˜ƒ) || "textures/entity/conduit/wind_vertical.png".equals(â˜ƒ)) {
            return false;
         } else if (SHIELDS.contains(â˜ƒ)) {
            return this.pack.hasResource(â˜ƒ, SHIELD_BASE) && this.pack.hasResource(â˜ƒ, â˜ƒ);
         } else if (!BANNERS.contains(â˜ƒ)) {
            Pair<ChestType, ResourceLocation> â˜ƒ = (Pair)CHESTS.get(â˜ƒ);
            return â˜ƒ != null && this.pack.hasResource(â˜ƒ, â˜ƒ.getSecond()) ? true : this.pack.hasResource(â˜ƒ, â˜ƒ);
         } else {
            return this.pack.hasResource(â˜ƒ, BANNER_BASE) && this.pack.hasResource(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public InputStream getResource(PackType var1, ResourceLocation var2) throws IOException {
      if (!"minecraft".equals(â˜ƒ.getNamespace())) {
         return this.pack.getResource(â˜ƒ, â˜ƒ);
      } else {
         String â˜ƒ = â˜ƒ.getPath();
         if ("textures/entity/iron_golem/iron_golem.png".equals(â˜ƒ)) {
            return this.pack.getResource(â˜ƒ, OLD_IRON_GOLEM_LOCATION);
         } else {
            if (SHIELDS.contains(â˜ƒ)) {
               InputStream â˜ƒ = fixPattern(this.pack.getResource(â˜ƒ, SHIELD_BASE), this.pack.getResource(â˜ƒ, â˜ƒ), 64, 2, 2, 12, 22);
               if (â˜ƒ != null) {
                  return â˜ƒ;
               }
            } else if (BANNERS.contains(â˜ƒ)) {
               InputStream â˜ƒ = fixPattern(this.pack.getResource(â˜ƒ, BANNER_BASE), this.pack.getResource(â˜ƒ, â˜ƒ), 64, 0, 0, 42, 41);
               if (â˜ƒ != null) {
                  return â˜ƒ;
               }
            } else {
               if ("textures/entity/enderdragon/dragon.png".equals(â˜ƒ) || "textures/entity/enderdragon/dragon_exploding.png".equals(â˜ƒ)) {
                  ByteArrayInputStream var15;
                  try (NativeImage â˜ƒ = NativeImage.read(this.pack.getResource(â˜ƒ, â˜ƒ))) {
                     int â˜ƒx = â˜ƒ.getWidth() / 256;

                     for(int â˜ƒxx = 88 * â˜ƒx; â˜ƒxx < 200 * â˜ƒx; ++â˜ƒxx) {
                        for(int â˜ƒxxx = 56 * â˜ƒx; â˜ƒxxx < 112 * â˜ƒx; ++â˜ƒxxx) {
                           â˜ƒ.setPixelRGBA(â˜ƒxxx, â˜ƒxx, 0);
                        }
                     }

                     var15 = new ByteArrayInputStream(â˜ƒ.asByteArray());
                  }

                  return var15;
               }

               if ("textures/entity/conduit/closed_eye.png".equals(â˜ƒ) || "textures/entity/conduit/open_eye.png".equals(â˜ƒ)) {
                  return fixConduitEyeTexture(this.pack.getResource(â˜ƒ, â˜ƒ));
               }

               Pair<ChestType, ResourceLocation> â˜ƒ = (Pair)CHESTS.get(â˜ƒ);
               if (â˜ƒ != null) {
                  ChestType â˜ƒx = â˜ƒ.getFirst();
                  InputStream â˜ƒxx = this.pack.getResource(â˜ƒ, â˜ƒ.getSecond());
                  if (â˜ƒx == ChestType.SINGLE) {
                     return fixSingleChest(â˜ƒxx);
                  }

                  if (â˜ƒx == ChestType.LEFT) {
                     return fixLeftChest(â˜ƒxx);
                  }

                  if (â˜ƒx == ChestType.RIGHT) {
                     return fixRightChest(â˜ƒxx);
                  }
               }
            }

            return this.pack.getResource(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Nullable
   public static InputStream fixPattern(InputStream var0, InputStream var1, int var2, int var3, int var4, int var5, int var6) throws IOException {
      try (
         NativeImage â˜ƒ = NativeImage.read(â˜ƒ);
         NativeImage â˜ƒx = NativeImage.read(â˜ƒ);
      ) {
         int â˜ƒxx = â˜ƒ.getWidth();
         int â˜ƒxxx = â˜ƒ.getHeight();
         if (â˜ƒxx == â˜ƒx.getWidth() && â˜ƒxxx == â˜ƒx.getHeight()) {
            try (NativeImage â˜ƒxxxx = new NativeImage(â˜ƒxx, â˜ƒxxx, true)) {
               int â˜ƒxxxxx = â˜ƒxx / â˜ƒ;

               for(int â˜ƒxxxxxx = â˜ƒ * â˜ƒxxxxx; â˜ƒxxxxxx < â˜ƒ * â˜ƒxxxxx; ++â˜ƒxxxxxx) {
                  for(int â˜ƒxxxxxxx = â˜ƒ * â˜ƒxxxxx; â˜ƒxxxxxxx < â˜ƒ * â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
                     int â˜ƒxxxxxxxx = NativeImage.getR(â˜ƒx.getPixelRGBA(â˜ƒxxxxxxx, â˜ƒxxxxxx));
                     int â˜ƒxxxxxxxxx = â˜ƒ.getPixelRGBA(â˜ƒxxxxxxx, â˜ƒxxxxxx);
                     â˜ƒxxxx.setPixelRGBA(
                        â˜ƒxxxxxxx,
                        â˜ƒxxxxxx,
                        NativeImage.combine(â˜ƒxxxxxxxx, NativeImage.getB(â˜ƒxxxxxxxxx), NativeImage.getG(â˜ƒxxxxxxxxx), NativeImage.getR(â˜ƒxxxxxxxxx))
                     );
                  }
               }

               return new ByteArrayInputStream(â˜ƒxxxx.asByteArray());
            }
         }
      }

      return null;
   }

   public static InputStream fixConduitEyeTexture(InputStream var0) throws IOException {
      ByteArrayInputStream var5;
      try (NativeImage â˜ƒ = NativeImage.read(â˜ƒ)) {
         int â˜ƒx = â˜ƒ.getWidth();
         int â˜ƒxx = â˜ƒ.getHeight();

         try (NativeImage â˜ƒxxx = new NativeImage(2 * â˜ƒx, 2 * â˜ƒxx, true)) {
            copyRect(â˜ƒ, â˜ƒxxx, 0, 0, 0, 0, â˜ƒx, â˜ƒxx, 1, false, false);
            var5 = new ByteArrayInputStream(â˜ƒxxx.asByteArray());
         }
      }

      return var5;
   }

   public static InputStream fixLeftChest(InputStream var0) throws IOException {
      ByteArrayInputStream var6;
      try (NativeImage â˜ƒ = NativeImage.read(â˜ƒ)) {
         int â˜ƒx = â˜ƒ.getWidth();
         int â˜ƒxx = â˜ƒ.getHeight();

         try (NativeImage â˜ƒxxx = new NativeImage(â˜ƒx / 2, â˜ƒxx, true)) {
            int â˜ƒxxxx = â˜ƒxx / 64;
            copyRect(â˜ƒ, â˜ƒxxx, 29, 0, 29, 0, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 59, 0, 14, 0, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 29, 14, 43, 14, 15, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 44, 14, 29, 14, 14, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 58, 14, 14, 14, 15, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 29, 19, 29, 19, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 59, 19, 14, 19, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 29, 33, 43, 33, 15, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 44, 33, 29, 33, 14, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 58, 33, 14, 33, 15, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 2, 0, 2, 0, 1, 1, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 4, 0, 1, 0, 1, 1, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 2, 1, 3, 1, 1, 4, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 3, 1, 2, 1, 1, 4, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 4, 1, 1, 1, 1, 4, â˜ƒxxxx, true, true);
            var6 = new ByteArrayInputStream(â˜ƒxxx.asByteArray());
         }
      }

      return var6;
   }

   public static InputStream fixRightChest(InputStream var0) throws IOException {
      ByteArrayInputStream var6;
      try (NativeImage â˜ƒ = NativeImage.read(â˜ƒ)) {
         int â˜ƒx = â˜ƒ.getWidth();
         int â˜ƒxx = â˜ƒ.getHeight();

         try (NativeImage â˜ƒxxx = new NativeImage(â˜ƒx / 2, â˜ƒxx, true)) {
            int â˜ƒxxxx = â˜ƒxx / 64;
            copyRect(â˜ƒ, â˜ƒxxx, 14, 0, 29, 0, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 44, 0, 14, 0, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 0, 14, 0, 14, 14, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 14, 14, 43, 14, 15, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 73, 14, 14, 14, 15, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 14, 19, 29, 19, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 44, 19, 14, 19, 15, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 0, 33, 0, 33, 14, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 14, 33, 43, 33, 15, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 73, 33, 14, 33, 15, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 1, 0, 2, 0, 1, 1, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 3, 0, 1, 0, 1, 1, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 0, 1, 0, 1, 1, 4, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 1, 1, 3, 1, 1, 4, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 5, 1, 1, 1, 1, 4, â˜ƒxxxx, true, true);
            var6 = new ByteArrayInputStream(â˜ƒxxx.asByteArray());
         }
      }

      return var6;
   }

   public static InputStream fixSingleChest(InputStream var0) throws IOException {
      ByteArrayInputStream var6;
      try (NativeImage â˜ƒ = NativeImage.read(â˜ƒ)) {
         int â˜ƒx = â˜ƒ.getWidth();
         int â˜ƒxx = â˜ƒ.getHeight();

         try (NativeImage â˜ƒxxx = new NativeImage(â˜ƒx, â˜ƒxx, true)) {
            int â˜ƒxxxx = â˜ƒxx / 64;
            copyRect(â˜ƒ, â˜ƒxxx, 14, 0, 28, 0, 14, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 28, 0, 14, 0, 14, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 0, 14, 0, 14, 14, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 14, 14, 42, 14, 14, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 28, 14, 28, 14, 14, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 42, 14, 14, 14, 14, 5, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 14, 19, 28, 19, 14, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 28, 19, 14, 19, 14, 14, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 0, 33, 0, 33, 14, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 14, 33, 42, 33, 14, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 28, 33, 28, 33, 14, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 42, 33, 14, 33, 14, 10, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 1, 0, 3, 0, 2, 1, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 3, 0, 1, 0, 2, 1, â˜ƒxxxx, false, true);
            copyRect(â˜ƒ, â˜ƒxxx, 0, 1, 0, 1, 1, 4, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 1, 1, 4, 1, 2, 4, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 3, 1, 3, 1, 1, 4, â˜ƒxxxx, true, true);
            copyRect(â˜ƒ, â˜ƒxxx, 4, 1, 1, 1, 2, 4, â˜ƒxxxx, true, true);
            var6 = new ByteArrayInputStream(â˜ƒxxx.asByteArray());
         }
      }

      return var6;
   }

   @Override
   public Collection<ResourceLocation> getResources(PackType var1, String var2, String var3, int var4, Predicate<String> var5) {
      return this.pack.getResources(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public Set<String> getNamespaces(PackType var1) {
      return this.pack.getNamespaces(â˜ƒ);
   }

   @Nullable
   @Override
   public <T> T getMetadataSection(MetadataSectionSerializer<T> var1) throws IOException {
      return this.pack.getMetadataSection(â˜ƒ);
   }

   @Override
   public String getName() {
      return this.pack.getName();
   }

   @Override
   public void close() {
      this.pack.close();
   }

   private static void copyRect(
      NativeImage var0, NativeImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, boolean var10
   ) {
      â˜ƒ *= â˜ƒ;
      â˜ƒ *= â˜ƒ;
      â˜ƒ *= â˜ƒ;
      â˜ƒ *= â˜ƒ;
      â˜ƒ *= â˜ƒ;
      â˜ƒ *= â˜ƒ;

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            â˜ƒ.setPixelRGBA(â˜ƒ + â˜ƒx, â˜ƒ + â˜ƒ, â˜ƒ.getPixelRGBA(â˜ƒ + (â˜ƒ ? â˜ƒ - 1 - â˜ƒx : â˜ƒx), â˜ƒ + (â˜ƒ ? â˜ƒ - 1 - â˜ƒ : â˜ƒ)));
         }
      }
   }
}
