package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

public class HumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
   private final A innerModel;
   private final A outerModel;

   public HumanoidArmorLayer(RenderLayerParent<T, M> var1, A var2, A var3) {
      super(â˜ƒ);
      this.innerModel = â˜ƒ;
      this.outerModel = â˜ƒ;
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      this.renderArmorPiece(â˜ƒ, â˜ƒ, â˜ƒ, EquipmentSlot.CHEST, â˜ƒ, this.getArmorModel(EquipmentSlot.CHEST));
      this.renderArmorPiece(â˜ƒ, â˜ƒ, â˜ƒ, EquipmentSlot.LEGS, â˜ƒ, this.getArmorModel(EquipmentSlot.LEGS));
      this.renderArmorPiece(â˜ƒ, â˜ƒ, â˜ƒ, EquipmentSlot.FEET, â˜ƒ, this.getArmorModel(EquipmentSlot.FEET));
      this.renderArmorPiece(â˜ƒ, â˜ƒ, â˜ƒ, EquipmentSlot.HEAD, â˜ƒ, this.getArmorModel(EquipmentSlot.HEAD));
   }

   private void renderArmorPiece(PoseStack var1, MultiBufferSource var2, T var3, EquipmentSlot var4, int var5, A var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemBySlot(â˜ƒ);
      if (â˜ƒ.getItem() instanceof ArmorItem) {
         ArmorItem â˜ƒx = (ArmorItem)â˜ƒ.getItem();
         if (â˜ƒx.getSlot() == â˜ƒ) {
            this.getParentModel().copyPropertiesTo(â˜ƒ);
            this.setPartVisibility(â˜ƒ, â˜ƒ);
            boolean â˜ƒxx = this.usesInnerModel(â˜ƒ);
            boolean â˜ƒxxx = â˜ƒ.hasFoil();
            if (â˜ƒx instanceof DyeableArmorItem) {
               int â˜ƒxxxx = ((DyeableArmorItem)â˜ƒx).getColor(â˜ƒ);
               float â˜ƒxxxxx = (float)(â˜ƒxxxx >> 16 & 0xFF) / 255.0F;
               float â˜ƒxxxxxx = (float)(â˜ƒxxxx >> 8 & 0xFF) / 255.0F;
               float â˜ƒxxxxxxx = (float)(â˜ƒxxxx & 0xFF) / 255.0F;
               this.renderModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒ, â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, null);
               this.renderModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒ, â˜ƒxx, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
               this.renderModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒ, â˜ƒxx, 1.0F, 1.0F, 1.0F, null);
            }
         }
      }
   }

   protected void setPartVisibility(A var1, EquipmentSlot var2) {
      â˜ƒ.setAllVisible(false);
      switch(â˜ƒ) {
         case HEAD:
            â˜ƒ.head.visible = true;
            â˜ƒ.hat.visible = true;
            break;
         case CHEST:
            â˜ƒ.body.visible = true;
            â˜ƒ.rightArm.visible = true;
            â˜ƒ.leftArm.visible = true;
            break;
         case LEGS:
            â˜ƒ.body.visible = true;
            â˜ƒ.rightLeg.visible = true;
            â˜ƒ.leftLeg.visible = true;
            break;
         case FEET:
            â˜ƒ.rightLeg.visible = true;
            â˜ƒ.leftLeg.visible = true;
      }
   }

   private void renderModel(
      PoseStack var1,
      MultiBufferSource var2,
      int var3,
      ArmorItem var4,
      boolean var5,
      A var6,
      boolean var7,
      float var8,
      float var9,
      float var10,
      @Nullable String var11
   ) {
      VertexConsumer â˜ƒ = ItemRenderer.getArmorFoilBuffer(â˜ƒ, RenderType.armorCutoutNoCull(this.getArmorLocation(â˜ƒ, â˜ƒ, â˜ƒ)), false, â˜ƒ);
      â˜ƒ.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
   }

   private A getArmorModel(EquipmentSlot var1) {
      return (A)(this.usesInnerModel(â˜ƒ) ? this.innerModel : this.outerModel);
   }

   private boolean usesInnerModel(EquipmentSlot var1) {
      return â˜ƒ == EquipmentSlot.LEGS;
   }

   private ResourceLocation getArmorLocation(ArmorItem var1, boolean var2, @Nullable String var3) {
      String â˜ƒ = "textures/models/armor/" + â˜ƒ.getMaterial().getName() + "_layer_" + (â˜ƒ ? 2 : 1) + (â˜ƒ == null ? "" : "_" + â˜ƒ) + ".png";
      return (ResourceLocation)ARMOR_LOCATION_CACHE.computeIfAbsent(â˜ƒ, ResourceLocation::new);
   }
}
