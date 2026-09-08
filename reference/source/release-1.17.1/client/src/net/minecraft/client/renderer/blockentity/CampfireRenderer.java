package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;

public class CampfireRenderer implements BlockEntityRenderer<CampfireBlockEntity> {
   private static final float SIZE = 0.375F;

   public CampfireRenderer(BlockEntityRendererProvider.Context var1) {
   }

   public void render(CampfireBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      Direction â˜ƒ = â˜ƒ.getBlockState().getValue(CampfireBlock.FACING);
      NonNullList<ItemStack> â˜ƒx = â˜ƒ.getItems();
      int â˜ƒxx = (int)â˜ƒ.getBlockPos().asLong();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx.size(); ++â˜ƒxxx) {
         ItemStack â˜ƒxxxx = â˜ƒx.get(â˜ƒxxx);
         if (â˜ƒxxxx != ItemStack.EMPTY) {
            â˜ƒ.pushPose();
            â˜ƒ.translate(0.5, 0.44921875, 0.5);
            Direction â˜ƒxxxxx = Direction.from2DDataValue((â˜ƒxxx + â˜ƒ.get2DDataValue()) % 4);
            float â˜ƒxxxxxx = -â˜ƒxxxxx.toYRot();
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxxxxxx));
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            â˜ƒ.translate(-0.3125, -0.3125, 0.0);
            â˜ƒ.scale(0.375F, 0.375F, 0.375F);
            Minecraft.getInstance().getItemRenderer().renderStatic(â˜ƒxxxx, ItemTransforms.TransformType.FIXED, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx + â˜ƒxxx);
            â˜ƒ.popPose();
         }
      }
   }
}
