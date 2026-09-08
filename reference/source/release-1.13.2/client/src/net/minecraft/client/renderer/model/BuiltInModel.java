package net.minecraft.client.renderer.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BuiltInModel implements IBakedModel {
   private final ItemCameraTransforms field_177557_a;
   private final ItemOverrideList field_188619_b;

   public BuiltInModel(ItemCameraTransforms var1, ItemOverrideList var2) {
      this.field_177557_a = ☃;
      this.field_188619_b = ☃;
   }

   @Override
   public List<BakedQuad> func_200117_a(@Nullable IBlockState var1, @Nullable EnumFacing var2, Random var3) {
      return Collections.emptyList();
   }

   @Override
   public boolean func_177555_b() {
      return false;
   }

   @Override
   public boolean func_177556_c() {
      return true;
   }

   @Override
   public boolean func_188618_c() {
      return true;
   }

   @Override
   public TextureAtlasSprite func_177554_e() {
      return null;
   }

   @Override
   public ItemCameraTransforms func_177552_f() {
      return this.field_177557_a;
   }

   @Override
   public ItemOverrideList func_188617_f() {
      return this.field_188619_b;
   }
}
