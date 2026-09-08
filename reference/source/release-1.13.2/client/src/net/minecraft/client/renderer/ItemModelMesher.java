package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class ItemModelMesher {
   public final Int2ObjectMap<ModelResourceLocation> field_199313_a = new Int2ObjectOpenHashMap<>(256);
   private final Int2ObjectMap<IBakedModel> field_199314_b = new Int2ObjectOpenHashMap<>(256);
   private final ModelManager field_178090_d;

   public ItemModelMesher(ModelManager var1) {
      this.field_178090_d = ☃;
   }

   public TextureAtlasSprite func_199934_a(IItemProvider var1) {
      return this.func_199309_a(new ItemStack(☃));
   }

   public TextureAtlasSprite func_199309_a(ItemStack var1) {
      IBakedModel ☃ = this.func_178089_a(☃);
      return (☃ == this.field_178090_d.func_174951_a() || ☃.func_188618_c()) && ☃.func_77973_b() instanceof ItemBlock
         ? this.field_178090_d.func_174954_c().func_178122_a(((ItemBlock)☃.func_77973_b()).func_179223_d().func_176223_P())
         : ☃.func_177554_e();
   }

   public IBakedModel func_178089_a(ItemStack var1) {
      IBakedModel ☃ = this.func_199312_b(☃.func_77973_b());
      return ☃ == null ? this.field_178090_d.func_174951_a() : ☃;
   }

   @Nullable
   public IBakedModel func_199312_b(Item var1) {
      return this.field_199314_b.get(func_199310_c(☃));
   }

   private static int func_199310_c(Item var0) {
      return Item.func_150891_b(☃);
   }

   public void func_199311_a(Item var1, ModelResourceLocation var2) {
      this.field_199313_a.put(func_199310_c(☃), ☃);
      this.field_199314_b.put(func_199310_c(☃), this.field_178090_d.func_174953_a(☃));
   }

   public ModelManager func_178083_a() {
      return this.field_178090_d;
   }

   public void func_178085_b() {
      this.field_199314_b.clear();

      for(Entry<Integer, ModelResourceLocation> ☃ : this.field_199313_a.entrySet()) {
         this.field_199314_b.put((Integer)☃.getKey(), this.field_178090_d.func_174953_a((ModelResourceLocation)☃.getValue()));
      }
   }
}
