package net.minecraft.client.renderer.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.IRegistry;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
   public static final ResourceLocation field_207763_a = new ResourceLocation("block/fire_0");
   public static final ResourceLocation field_207764_b = new ResourceLocation("block/fire_1");
   public static final ResourceLocation field_207766_d = new ResourceLocation("block/lava_flow");
   public static final ResourceLocation field_207768_f = new ResourceLocation("block/water_flow");
   public static final ResourceLocation field_207769_g = new ResourceLocation("block/water_overlay");
   public static final ResourceLocation field_207770_h = new ResourceLocation("block/destroy_stage_0");
   public static final ResourceLocation field_207771_i = new ResourceLocation("block/destroy_stage_1");
   public static final ResourceLocation field_207772_j = new ResourceLocation("block/destroy_stage_2");
   public static final ResourceLocation field_207773_k = new ResourceLocation("block/destroy_stage_3");
   public static final ResourceLocation field_207774_l = new ResourceLocation("block/destroy_stage_4");
   public static final ResourceLocation field_207775_m = new ResourceLocation("block/destroy_stage_5");
   public static final ResourceLocation field_207776_n = new ResourceLocation("block/destroy_stage_6");
   public static final ResourceLocation field_207777_o = new ResourceLocation("block/destroy_stage_7");
   public static final ResourceLocation field_207778_p = new ResourceLocation("block/destroy_stage_8");
   public static final ResourceLocation field_207779_q = new ResourceLocation("block/destroy_stage_9");
   private static final Set<ResourceLocation> field_177602_b = Sets.<ResourceLocation>newHashSet(
      field_207768_f,
      field_207766_d,
      field_207769_g,
      field_207763_a,
      field_207764_b,
      field_207770_h,
      field_207771_i,
      field_207772_j,
      field_207773_k,
      field_207774_l,
      field_207775_m,
      field_207776_n,
      field_207777_o,
      field_207778_p,
      field_207779_q,
      new ResourceLocation("item/empty_armor_slot_helmet"),
      new ResourceLocation("item/empty_armor_slot_chestplate"),
      new ResourceLocation("item/empty_armor_slot_leggings"),
      new ResourceLocation("item/empty_armor_slot_boots"),
      new ResourceLocation("item/empty_armor_slot_shield")
   );
   private static final Logger field_177603_c = LogManager.getLogger();
   public static final ModelResourceLocation field_177604_a = new ModelResourceLocation("builtin/missing", "missing");
   @VisibleForTesting
   public static final String field_188641_d = ("{    'textures': {       'particle': '"
         + MissingTextureSprite.func_195677_a().func_195668_m().func_110623_a()
         + "',       'missingno': '"
         + MissingTextureSprite.func_195677_a().func_195668_m().func_110623_a()
         + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
      .replace('\'', '"');
   private static final Map<String, String> field_177600_d = Maps.newHashMap(ImmutableMap.of("missing", field_188641_d));
   private static final Splitter field_209611_w = Splitter.on(',');
   private static final Splitter field_209612_x = Splitter.on('=').limit(2);
   public static final ModelBlock field_177606_o = Util.func_200696_a(ModelBlock.func_178294_a("{}"), var0 -> var0.field_178317_b = "generation marker");
   public static final ModelBlock field_177616_r = Util.func_200696_a(ModelBlock.func_178294_a("{}"), var0 -> var0.field_178317_b = "block entity marker");
   private static final StateContainer<Block, IBlockState> field_209613_y = new StateContainer.Builder<Block, IBlockState>(Blocks.field_150350_a)
      .func_206894_a(BooleanProperty.func_177716_a("map"))
      .func_206893_a(BlockState::new);
   private final IResourceManager field_177598_f;
   private final TextureMap field_177609_j;
   private final Map<ModelResourceLocation, IBakedModel> field_177605_n = Maps.<ModelResourceLocation, IBakedModel>newHashMap();
   private static final Map<ResourceLocation, StateContainer<Block, IBlockState>> field_209607_C = ImmutableMap.of(
      new ResourceLocation("item_frame"), field_209613_y
   );
   private final Map<ResourceLocation, IUnbakedModel> field_209608_D = Maps.<ResourceLocation, IUnbakedModel>newHashMap();
   private final Set<ResourceLocation> field_209609_E = Sets.<ResourceLocation>newHashSet();
   private final ModelBlockDefinition.ContainerHolder field_209610_F = new ModelBlockDefinition.ContainerHolder();

   public ModelBakery(IResourceManager var1, TextureMap var2) {
      this.field_177598_f = ☃;
      this.field_177609_j = ☃;
   }

   private static Predicate<IBlockState> func_209605_a(StateContainer<Block, IBlockState> var0, String var1) {
      Map<IProperty<?>, Comparable<?>> ☃ = Maps.newHashMap();

      for(String ☃x : field_209611_w.split(☃)) {
         Iterator<String> ☃xx = field_209612_x.split(☃x).iterator();
         if (☃xx.hasNext()) {
            String ☃xxx = (String)☃xx.next();
            IProperty<?> ☃xxxx = ☃.func_185920_a(☃xxx);
            if (☃xxxx != null && ☃xx.hasNext()) {
               String ☃xxxxx = (String)☃xx.next();
               Comparable<?> ☃xxxxxx = func_209592_a(☃xxxx, ☃xxxxx);
               if (☃xxxxxx == null) {
                  throw new RuntimeException("Unknown value: '" + ☃xxxxx + "' for blockstate property: '" + ☃xxx + "' " + ☃xxxx.func_177700_c());
               }

               ☃.put(☃xxxx, ☃xxxxxx);
            } else if (!☃xxx.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + ☃xxx + "'");
            }
         }
      }

      Block ☃x = ☃.func_177622_c();
      return var2x -> {
         if (var2x != null && ☃ == var2x.func_177230_c()) {
            for(Entry<IProperty<?>, Comparable<?>> ☃ : ☃.entrySet()) {
               if (!Objects.equals(var2x.func_177229_b((IProperty)☃.getKey()), ☃.getValue())) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      };
   }

   @Nullable
   static <T extends Comparable<T>> T func_209592_a(IProperty<T> var0, String var1) {
      return (T)☃.func_185929_b(☃).orElse(null);
   }

   public IUnbakedModel func_209597_a(ResourceLocation var1) {
      if (this.field_209608_D.containsKey(☃)) {
         return (IUnbakedModel)this.field_209608_D.get(☃);
      } else if (this.field_209609_E.contains(☃)) {
         throw new IllegalStateException("Circular reference while loading " + ☃);
      } else {
         this.field_209609_E.add(☃);
         IUnbakedModel ☃ = (IUnbakedModel)this.field_209608_D.get(field_177604_a);

         while(!this.field_209609_E.isEmpty()) {
            ResourceLocation ☃x = (ResourceLocation)this.field_209609_E.iterator().next();

            try {
               if (!this.field_209608_D.containsKey(☃x)) {
                  this.func_209598_b(☃x);
               }
            } catch (ModelBakery.BlockStateDefinitionException var9) {
               field_177603_c.warn(var9.getMessage());
               this.field_209608_D.put(☃x, ☃);
            } catch (Exception var10) {
               field_177603_c.warn("Unable to load model: '{}' referenced from: {}: {}", ☃x, ☃, var10);
               this.field_209608_D.put(☃x, ☃);
            } finally {
               this.field_209609_E.remove(☃x);
            }
         }

         return (IUnbakedModel)this.field_209608_D.getOrDefault(☃, ☃);
      }
   }

   private void func_209598_b(ResourceLocation var1) throws Exception {
      if (!(☃ instanceof ModelResourceLocation)) {
         this.func_209593_a(☃, this.func_177594_c(☃));
      } else {
         ModelResourceLocation ☃ = (ModelResourceLocation)☃;
         if (Objects.equals(☃.func_177518_c(), "inventory")) {
            ResourceLocation ☃x = new ResourceLocation(☃.func_110624_b(), "item/" + ☃.func_110623_a());
            ModelBlock ☃xx = this.func_177594_c(☃x);
            this.func_209593_a(☃, ☃xx);
            this.field_209608_D.put(☃x, ☃xx);
         } else {
            ResourceLocation ☃ = new ResourceLocation(☃.func_110624_b(), ☃.func_110623_a());
            StateContainer<Block, IBlockState> ☃x = (StateContainer)Optional.ofNullable(field_209607_C.get(☃))
               .orElseGet(() -> IRegistry.field_212618_g.func_82594_a(☃).func_176194_O());
            this.field_209610_F.func_209573_a(☃x);
            ImmutableList<IBlockState> ☃xx = ☃x.func_177619_a();
            Map<ModelResourceLocation, IBlockState> ☃xxx = Maps.<ModelResourceLocation, IBlockState>newHashMap();
            ☃xx.forEach(var2x -> BlockModelShapes.func_209553_a(☃, var2x));
            Map<IBlockState, IUnbakedModel> ☃xxxx = Maps.<IBlockState, IUnbakedModel>newHashMap();
            ResourceLocation ☃xxxxx = new ResourceLocation(☃.func_110624_b(), "blockstates/" + ☃.func_110623_a() + ".json");

            try {
               List<Pair<String, ModelBlockDefinition>> ☃;
               try {
                  ☃ = (List)this.field_177598_f
                     .func_199004_b(☃xxxxx)
                     .stream()
                     .map(
                        var1x -> {
                           try {
                              InputStream ☃ = var1x.func_199027_b();
                              Throwable var3xx = null;
      
                              Pair var4x;
                              try {
                                 var4x = Pair.of(
                                    var1x.func_199026_d(),
                                    ModelBlockDefinition.func_209577_a(this.field_209610_F, new InputStreamReader(☃, StandardCharsets.UTF_8))
                                 );
                              } catch (Throwable var14xx) {
                                 var3xx = var14xx;
                                 throw var14xx;
                              } finally {
                                 if (☃ != null) {
                                    if (var3xx != null) {
                                       try {
                                          ☃.close();
                                       } catch (Throwable var13xx) {
                                          var3xx.addSuppressed(var13xx);
                                       }
                                    } else {
                                       ☃.close();
                                    }
                                 }
                              }
      
                              return var4x;
                           } catch (Exception var16) {
                              throw new ModelBakery.BlockStateDefinitionException(
                                 String.format(
                                    "Exception loading blockstate definition: '%s' in resourcepack: '%s': %s",
                                    var1x.func_199029_a(),
                                    var1x.func_199026_d(),
                                    var16.getMessage()
                                 )
                              );
                           }
                        }
                     )
                     .collect(Collectors.toList());
               } catch (IOException var22) {
                  field_177603_c.warn("Exception loading blockstate definition: {}: {}", ☃xxxxx, var22);
                  return;
               }

               for(Pair<String, ModelBlockDefinition> ☃xxxxxx : ☃) {
                  ModelBlockDefinition ☃xxxxxxxx = ☃xxxxxx.getSecond();
                  Map<IBlockState, IUnbakedModel> ☃xxxxxxxxx = Maps.<IBlockState, IUnbakedModel>newIdentityHashMap();
                  IUnbakedModel ☃xxxxxxx;
                  if (☃xxxxxxxx.func_188002_b()) {
                     ☃xxxxxxx = ☃xxxxxxxx.func_188001_c();
                     ☃xx.forEach(var2x -> {
                     });
                  } else {
                     ☃xxxxxxx = null;
                  }

                  ☃xxxxxxxx.func_209578_a()
                     .forEach(
                        (var8x, var9x) -> {
                           try {
                              ☃.stream()
                                 .filter(func_209605_a(☃, var8x))
                                 .forEach(
                                    var5x -> {
                                       IUnbakedModel ☃ = (IUnbakedModel)☃.put(var5x, var9x);
                                       if (☃ != null && ☃ != ☃) {
                                          ☃.put(var5x, this.field_209608_D.get(field_177604_a));
                                          throw new RuntimeException(
                                             "Overlapping definition with: "
                                                + (String)((Entry)☃.func_209578_a()
                                                      .entrySet()
                                                      .stream()
                                                      .filter(var1x -> var1x.getValue() == ☃)
                                                      .findFirst()
                                                      .get())
                                                   .getKey()
                                          );
                                       }
                                    }
                                 );
                           } catch (Exception var11xx) {
                              field_177603_c.warn(
                                 "Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}",
                                 ☃,
                                 ☃.getFirst(),
                                 var8x,
                                 var11xx.getMessage()
                              );
                           }
                        }
                     );
                  ☃xxxx.putAll(☃xxxxxxxxx);
               }
            } catch (ModelBakery.BlockStateDefinitionException var23) {
               throw var23;
            } catch (Exception var24) {
               throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s': %s", ☃xxxxx, var24));
            } finally {
               for(Entry<ModelResourceLocation, IBlockState> ☃xxxxxx : ☃xxx.entrySet()) {
                  this.func_209593_a(
                     (ResourceLocation)☃xxxxxx.getKey(), (IUnbakedModel)☃xxxx.getOrDefault(☃xxxxxx.getValue(), this.field_209608_D.get(field_177604_a))
                  );
               }
            }
         }
      }
   }

   private void func_209593_a(ResourceLocation var1, IUnbakedModel var2) {
      this.field_209608_D.put(☃, ☃);
      this.field_209609_E.addAll(☃.func_187965_e());
   }

   private void func_209594_a(Map<ModelResourceLocation, IUnbakedModel> var1, ModelResourceLocation var2) {
      ☃.put(☃, this.func_209597_a(☃));
   }

   public Map<ModelResourceLocation, IBakedModel> func_177570_a() {
      Map<ModelResourceLocation, IUnbakedModel> ☃ = Maps.<ModelResourceLocation, IUnbakedModel>newHashMap();

      try {
         this.field_209608_D.put(field_177604_a, this.func_177594_c(field_177604_a));
         this.func_209594_a(☃, field_177604_a);
      } catch (IOException var4) {
         field_177603_c.error("Error loading missing model, should never happen :(", var4);
         throw new RuntimeException(var4);
      }

      field_209607_C.forEach((var2, var3x) -> var3x.func_177619_a().forEach(var3xx -> this.func_209594_a(☃, BlockModelShapes.func_209553_a(var2, var3xx))));

      for(Block ☃x : IRegistry.field_212618_g) {
         ☃x.func_176194_O().func_177619_a().forEach(var2 -> this.func_209594_a(☃, BlockModelShapes.func_209554_c(var2)));
      }

      for(ResourceLocation ☃x : IRegistry.field_212630_s.func_148742_b()) {
         this.func_209594_a(☃, new ModelResourceLocation(☃x, "inventory"));
      }

      this.func_209594_a(☃, new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      Set<String> ☃x = Sets.newLinkedHashSet();
      Set<ResourceLocation> ☃xx = (Set)☃.values().stream().flatMap(var2 -> var2.func_209559_a(this::func_209597_a, ☃).stream()).collect(Collectors.toSet());
      ☃xx.addAll(field_177602_b);
      ☃x.forEach(var0 -> field_177603_c.warn("Unable to resolve texture reference: {}", var0));
      this.field_177609_j.func_195426_a(this.field_177598_f, ☃xx);
      ☃.forEach((var1x, var2) -> {
         IBakedModel ☃ = null;

         try {
            ☃ = var2.func_209558_a(this::func_209597_a, this.field_177609_j::func_195424_a, ModelRotation.X0_Y0, false);
         } catch (Exception var5) {
            field_177603_c.warn("Unable to bake model: '{}': {}", var1x, var5);
         }

         if (☃ != null) {
            this.field_177605_n.put(var1x, ☃);
         }
      });
      return this.field_177605_n;
   }

   private ModelBlock func_177594_c(ResourceLocation var1) throws IOException {
      Reader ☃ = null;
      IResource ☃x = null;

      ModelBlock ☃;
      try {
         String ☃xx = ☃.func_110623_a();
         if ("builtin/generated".equals(☃xx)) {
            return field_177606_o;
         }

         if (!"builtin/entity".equals(☃xx)) {
            if (☃xx.startsWith("builtin/")) {
               String ☃xx = ☃xx.substring("builtin/".length());
               String ☃xxx = (String)field_177600_d.get(☃xx);
               if (☃xxx == null) {
                  throw new FileNotFoundException(☃.toString());
               }

               ☃ = new StringReader(☃xxx);
            } else {
               ☃x = this.field_177598_f.func_199002_a(new ResourceLocation(☃.func_110624_b(), "models/" + ☃.func_110623_a() + ".json"));
               ☃ = new InputStreamReader(☃x.func_199027_b(), StandardCharsets.UTF_8);
            }

            ☃ = ModelBlock.func_178307_a(☃);
            ☃.field_178317_b = ☃.toString();
            return ☃;
         }

         ☃ = field_177616_r;
      } finally {
         IOUtils.closeQuietly(☃);
         IOUtils.closeQuietly(☃x);
      }

      return ☃;
   }

   static class BlockStateDefinitionException extends RuntimeException {
      public BlockStateDefinitionException(String var1) {
         super(☃);
      }
   }
}
