package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemModelShaper {
   public final Int2ObjectMap<ModelResourceLocation> shapes = new Int2ObjectOpenHashMap<>(256);
   private final Int2ObjectMap<BakedModel> shapesCache = new Int2ObjectOpenHashMap<>(256);
   private final ModelManager modelManager;

   public ItemModelShaper(ModelManager var1) {
      this.modelManager = â˜ƒ;
   }

   public TextureAtlasSprite getParticleIcon(ItemLike var1) {
      return this.getParticleIcon(new ItemStack(â˜ƒ));
   }

   public TextureAtlasSprite getParticleIcon(ItemStack var1) {
      BakedModel â˜ƒ = this.getItemModel(â˜ƒ);
      return â˜ƒ == this.modelManager.getMissingModel() && â˜ƒ.getItem() instanceof BlockItem
         ? this.modelManager.getBlockModelShaper().getParticleIcon(((BlockItem)â˜ƒ.getItem()).getBlock().defaultBlockState())
         : â˜ƒ.getParticleIcon();
   }

   public BakedModel getItemModel(ItemStack var1) {
      BakedModel â˜ƒ = this.getItemModel(â˜ƒ.getItem());
      return â˜ƒ == null ? this.modelManager.getMissingModel() : â˜ƒ;
   }

   @Nullable
   public BakedModel getItemModel(Item var1) {
      return this.shapesCache.get(getIndex(â˜ƒ));
   }

   private static int getIndex(Item var0) {
      return Item.getId(â˜ƒ);
   }

   public void register(Item var1, ModelResourceLocation var2) {
      this.shapes.put(getIndex(â˜ƒ), â˜ƒ);
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   public void rebuildCache() {
      this.shapesCache.clear();

      for(Entry<Integer, ModelResourceLocation> â˜ƒ : this.shapes.entrySet()) {
         this.shapesCache.put((Integer)â˜ƒ.getKey(), this.modelManager.getModel((ModelResourceLocation)â˜ƒ.getValue()));
      }
   }
}
