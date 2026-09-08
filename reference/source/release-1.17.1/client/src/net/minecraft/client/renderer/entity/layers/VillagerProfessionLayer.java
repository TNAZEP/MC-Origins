package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;

public class VillagerProfessionLayer<T extends LivingEntity & VillagerDataHolder, M extends EntityModel<T> & VillagerHeadModel> extends RenderLayer<T, M> {
   private static final Int2ObjectMap<ResourceLocation> LEVEL_LOCATIONS = Util.make(new Int2ObjectOpenHashMap<>(), var0 -> {
      var0.put(1, new ResourceLocation("stone"));
      var0.put(2, new ResourceLocation("iron"));
      var0.put(3, new ResourceLocation("gold"));
      var0.put(4, new ResourceLocation("emerald"));
      var0.put(5, new ResourceLocation("diamond"));
   });
   private final Object2ObjectMap<VillagerType, VillagerMetaDataSection.Hat> typeHatCache = new Object2ObjectOpenHashMap<>();
   private final Object2ObjectMap<VillagerProfession, VillagerMetaDataSection.Hat> professionHatCache = new Object2ObjectOpenHashMap<>();
   private final ResourceManager resourceManager;
   private final String path;

   public VillagerProfessionLayer(RenderLayerParent<T, M> var1, ResourceManager var2, String var3) {
      super(â˜ƒ);
      this.resourceManager = â˜ƒ;
      this.path = â˜ƒ;
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (!â˜ƒ.isInvisible()) {
         VillagerData â˜ƒ = â˜ƒ.getVillagerData();
         VillagerType â˜ƒx = â˜ƒ.getType();
         VillagerProfession â˜ƒxx = â˜ƒ.getProfession();
         VillagerMetaDataSection.Hat â˜ƒxxx = this.getHatData(this.typeHatCache, "type", Registry.VILLAGER_TYPE, â˜ƒx);
         VillagerMetaDataSection.Hat â˜ƒxxxx = this.getHatData(this.professionHatCache, "profession", Registry.VILLAGER_PROFESSION, â˜ƒxx);
         M â˜ƒxxxxx = this.getParentModel();
         â˜ƒxxxxx.hatVisible(
            â˜ƒxxxx == VillagerMetaDataSection.Hat.NONE || â˜ƒxxxx == VillagerMetaDataSection.Hat.PARTIAL && â˜ƒxxx != VillagerMetaDataSection.Hat.FULL
         );
         ResourceLocation â˜ƒxxxxxx = this.getResourceLocation("type", Registry.VILLAGER_TYPE.getKey(â˜ƒx));
         renderColoredCutoutModel(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F);
         â˜ƒxxxxx.hatVisible(true);
         if (â˜ƒxx != VillagerProfession.NONE && !â˜ƒ.isBaby()) {
            ResourceLocation â˜ƒxxxxxxx = this.getResourceLocation("profession", Registry.VILLAGER_PROFESSION.getKey(â˜ƒxx));
            renderColoredCutoutModel(â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F);
            if (â˜ƒxx != VillagerProfession.NITWIT) {
               ResourceLocation â˜ƒxxxxxxxx = this.getResourceLocation(
                  "profession_level", LEVEL_LOCATIONS.get(Mth.clamp(â˜ƒ.getLevel(), 1, LEVEL_LOCATIONS.size()))
               );
               renderColoredCutoutModel(â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   private ResourceLocation getResourceLocation(String var1, ResourceLocation var2) {
      return new ResourceLocation(â˜ƒ.getNamespace(), "textures/entity/" + this.path + "/" + â˜ƒ + "/" + â˜ƒ.getPath() + ".png");
   }

   public <K> VillagerMetaDataSection.Hat getHatData(Object2ObjectMap<K, VillagerMetaDataSection.Hat> var1, String var2, DefaultedRegistry<K> var3, K var4) {
      return (VillagerMetaDataSection.Hat)â˜ƒ.computeIfAbsent(â˜ƒ, var4x -> {
         try {
            Resource â˜ƒ = this.resourceManager.getResource(this.getResourceLocation(â˜ƒ, â˜ƒ.getKey(â˜ƒ)));

            VillagerMetaDataSection.Hat var7;
            label49: {
               try {
                  VillagerMetaDataSection â˜ƒx = â˜ƒ.getMetadata(VillagerMetaDataSection.SERIALIZER);
                  if (â˜ƒx != null) {
                     var7 = â˜ƒx.getHat();
                     break label49;
                  }
               } catch (Throwable var9) {
                  if (â˜ƒ != null) {
                     try {
                        â˜ƒ.close();
                     } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                     }
                  }

                  throw var9;
               }

               if (â˜ƒ != null) {
                  â˜ƒ.close();
               }

               return VillagerMetaDataSection.Hat.NONE;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }

            return var7;
         } catch (IOException var10) {
            return VillagerMetaDataSection.Hat.NONE;
         }
      });
   }
}
