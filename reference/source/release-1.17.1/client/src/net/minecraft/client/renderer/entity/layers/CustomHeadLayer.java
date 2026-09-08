package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;

public class CustomHeadLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
   private final float scaleX;
   private final float scaleY;
   private final float scaleZ;
   private final Map<SkullBlock.Type, SkullModelBase> skullModels;

   public CustomHeadLayer(RenderLayerParent<T, M> var1, EntityModelSet var2) {
      this(â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F);
   }

   public CustomHeadLayer(RenderLayerParent<T, M> var1, EntityModelSet var2, float var3, float var4, float var5) {
      super(â˜ƒ);
      this.scaleX = â˜ƒ;
      this.scaleY = â˜ƒ;
      this.scaleZ = â˜ƒ;
      this.skullModels = SkullBlockRenderer.createSkullRenderers(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      ItemStack â˜ƒ = â˜ƒ.getItemBySlot(EquipmentSlot.HEAD);
      if (!â˜ƒ.isEmpty()) {
         Item â˜ƒx = â˜ƒ.getItem();
         â˜ƒ.pushPose();
         â˜ƒ.scale(this.scaleX, this.scaleY, this.scaleZ);
         boolean â˜ƒxx = â˜ƒ instanceof Villager || â˜ƒ instanceof ZombieVillager;
         if (â˜ƒ.isBaby() && !(â˜ƒ instanceof Villager)) {
            float â˜ƒxxx = 2.0F;
            float â˜ƒxxxx = 1.4F;
            â˜ƒ.translate(0.0, 0.03125, 0.0);
            â˜ƒ.scale(0.7F, 0.7F, 0.7F);
            â˜ƒ.translate(0.0, 1.0, 0.0);
         }

         this.getParentModel().getHead().translateAndRotate(â˜ƒ);
         if (â˜ƒx instanceof BlockItem && ((BlockItem)â˜ƒx).getBlock() instanceof AbstractSkullBlock) {
            float â˜ƒx = 1.1875F;
            â˜ƒ.scale(1.1875F, -1.1875F, -1.1875F);
            if (â˜ƒxx) {
               â˜ƒ.translate(0.0, 0.0625, 0.0);
            }

            GameProfile â˜ƒx = null;
            if (â˜ƒ.hasTag()) {
               CompoundTag â˜ƒxx = â˜ƒ.getTag();
               if (â˜ƒxx.contains("SkullOwner", 10)) {
                  â˜ƒx = NbtUtils.readGameProfile(â˜ƒxx.getCompound("SkullOwner"));
               }
            }

            â˜ƒ.translate(-0.5, 0.0, -0.5);
            SkullBlock.Type â˜ƒx = ((AbstractSkullBlock)((BlockItem)â˜ƒx).getBlock()).getType();
            SkullModelBase â˜ƒxx = (SkullModelBase)this.skullModels.get(â˜ƒx);
            RenderType â˜ƒxxx = SkullBlockRenderer.getRenderType(â˜ƒx, â˜ƒx);
            SkullBlockRenderer.renderSkull(null, 180.0F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx);
         } else if (!(â˜ƒx instanceof ArmorItem) || ((ArmorItem)â˜ƒx).getSlot() != EquipmentSlot.HEAD) {
            translateToHead(â˜ƒ, â˜ƒxx);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(â˜ƒ, â˜ƒ, ItemTransforms.TransformType.HEAD, false, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         â˜ƒ.popPose();
      }
   }

   public static void translateToHead(PoseStack var0, boolean var1) {
      float â˜ƒ = 0.625F;
      â˜ƒ.translate(0.0, -0.25, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
      â˜ƒ.scale(0.625F, -0.625F, -0.625F);
      if (â˜ƒ) {
         â˜ƒ.translate(0.0, 0.1875, 0.0);
      }
   }
}
