package net.minecraft.item.crafting;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class RecipeSerializers {
   private static final Map<String, IRecipeSerializer<?>> field_199590_p = Maps.newHashMap();
   public static final IRecipeSerializer<ShapedRecipe> field_199575_a = func_199573_a(new ShapedRecipe.Serializer());
   public static final IRecipeSerializer<ShapelessRecipe> field_199576_b = func_199573_a(new ShapelessRecipe.Serializer());
   public static final RecipeSerializers.SimpleSerializer<RecipesArmorDyes> field_199577_c = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_armordye", RecipesArmorDyes::new)
   );
   public static final RecipeSerializers.SimpleSerializer<RecipeBookCloning> field_199578_d = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_bookcloning", RecipeBookCloning::new)
   );
   public static final RecipeSerializers.SimpleSerializer<RecipesMapCloning> field_199579_e = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_mapcloning", RecipesMapCloning::new)
   );
   public static final RecipeSerializers.SimpleSerializer<RecipesMapExtending> field_199580_f = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_mapextending", RecipesMapExtending::new)
   );
   public static final RecipeSerializers.SimpleSerializer<FireworkRocketRecipe> field_199581_g = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_firework_rocket", FireworkRocketRecipe::new)
   );
   public static final RecipeSerializers.SimpleSerializer<FireworkStarRecipe> field_199582_h = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_firework_star", FireworkStarRecipe::new)
   );
   public static final RecipeSerializers.SimpleSerializer<FireworkStarFadeRecipe> field_199583_i = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_firework_star_fade", FireworkStarFadeRecipe::new)
   );
   public static final RecipeSerializers.SimpleSerializer<RecipeRepairItem> field_199584_j = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_repairitem", RecipeRepairItem::new)
   );
   public static final RecipeSerializers.SimpleSerializer<RecipeTippedArrow> field_199585_k = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_tippedarrow", RecipeTippedArrow::new)
   );
   public static final RecipeSerializers.SimpleSerializer<BannerDuplicateRecipe> field_199586_l = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_bannerduplicate", BannerDuplicateRecipe::new)
   );
   public static final RecipeSerializers.SimpleSerializer<BannerAddPatternRecipe> field_199587_m = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_banneraddpattern", BannerAddPatternRecipe::new)
   );
   public static final RecipeSerializers.SimpleSerializer<ShieldRecipes> field_199588_n = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_shielddecoration", ShieldRecipes::new)
   );
   public static final RecipeSerializers.SimpleSerializer<ShulkerBoxColoringRecipe> field_199589_o = func_199573_a(
      new RecipeSerializers.SimpleSerializer<>("crafting_special_shulkerboxcoloring", ShulkerBoxColoringRecipe::new)
   );
   public static final IRecipeSerializer<FurnaceRecipe> field_201839_p = func_199573_a(new FurnaceRecipe.Serializer());

   public static <S extends IRecipeSerializer<T>, T extends IRecipe> S func_199573_a(S var0) {
      if (field_199590_p.containsKey(☃.func_199567_a())) {
         throw new IllegalArgumentException("Duplicate recipe serializer " + ☃.func_199567_a());
      } else {
         field_199590_p.put(☃.func_199567_a(), ☃);
         return ☃;
      }
   }

   public static IRecipe func_199572_a(ResourceLocation var0, JsonObject var1) {
      String ☃ = JsonUtils.func_151200_h(☃, "type");
      IRecipeSerializer<?> ☃x = (IRecipeSerializer)field_199590_p.get(☃);
      if (☃x == null) {
         throw new JsonSyntaxException("Invalid or unsupported recipe type '" + ☃ + "'");
      } else {
         return ☃x.func_199425_a_(☃, ☃);
      }
   }

   public static IRecipe func_199571_a(PacketBuffer var0) {
      ResourceLocation ☃ = ☃.func_192575_l();
      String ☃x = ☃.func_150789_c(32767);
      IRecipeSerializer<?> ☃xx = (IRecipeSerializer)field_199590_p.get(☃x);
      if (☃xx == null) {
         throw new IllegalArgumentException("Unknown recipe serializer " + ☃x);
      } else {
         return ☃xx.func_199426_a_(☃, ☃);
      }
   }

   public static <T extends IRecipe> void func_199574_a(T var0, PacketBuffer var1) {
      ☃.func_192572_a(☃.func_199560_c());
      ☃.func_180714_a(☃.func_199559_b().func_199567_a());
      IRecipeSerializer<T> ☃ = ☃.func_199559_b();
      ☃.func_199427_a_(☃, ☃);
   }

   public static final class SimpleSerializer<T extends IRecipe> implements IRecipeSerializer<T> {
      private final String field_199569_a;
      private final Function<ResourceLocation, T> field_199570_b;

      public SimpleSerializer(String var1, Function<ResourceLocation, T> var2) {
         this.field_199569_a = ☃;
         this.field_199570_b = ☃;
      }

      @Override
      public T func_199425_a_(ResourceLocation var1, JsonObject var2) {
         return (T)this.field_199570_b.apply(☃);
      }

      @Override
      public T func_199426_a_(ResourceLocation var1, PacketBuffer var2) {
         return (T)this.field_199570_b.apply(☃);
      }

      @Override
      public void func_199427_a_(PacketBuffer var1, T var2) {
      }

      @Override
      public String func_199567_a() {
         return this.field_199569_a;
      }
   }
}
