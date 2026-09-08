package net.minecraft.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

public class BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
   private static final ShulkerBoxBlockEntity[] SHULKER_BOXES = (ShulkerBoxBlockEntity[])Arrays.stream(DyeColor.values())
      .sorted(Comparator.comparingInt(DyeColor::getId))
      .map(var0 -> new ShulkerBoxBlockEntity(var0, BlockPos.ZERO, Blocks.SHULKER_BOX.defaultBlockState()))
      .toArray(var0 -> new ShulkerBoxBlockEntity[var0]);
   private static final ShulkerBoxBlockEntity DEFAULT_SHULKER_BOX = new ShulkerBoxBlockEntity(BlockPos.ZERO, Blocks.SHULKER_BOX.defaultBlockState());
   private final ChestBlockEntity chest = new ChestBlockEntity(BlockPos.ZERO, Blocks.CHEST.defaultBlockState());
   private final ChestBlockEntity trappedChest = new TrappedChestBlockEntity(BlockPos.ZERO, Blocks.TRAPPED_CHEST.defaultBlockState());
   private final EnderChestBlockEntity enderChest = new EnderChestBlockEntity(BlockPos.ZERO, Blocks.ENDER_CHEST.defaultBlockState());
   private final BannerBlockEntity banner = new BannerBlockEntity(BlockPos.ZERO, Blocks.WHITE_BANNER.defaultBlockState());
   private final BedBlockEntity bed = new BedBlockEntity(BlockPos.ZERO, Blocks.RED_BED.defaultBlockState());
   private final ConduitBlockEntity conduit = new ConduitBlockEntity(BlockPos.ZERO, Blocks.CONDUIT.defaultBlockState());
   private ShieldModel shieldModel;
   private TridentModel tridentModel;
   private Map<SkullBlock.Type, SkullModelBase> skullModels;
   private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
   private final EntityModelSet entityModelSet;

   public BlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher var1, EntityModelSet var2) {
      this.blockEntityRenderDispatcher = â˜ƒ;
      this.entityModelSet = â˜ƒ;
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      this.shieldModel = new ShieldModel(this.entityModelSet.bakeLayer(ModelLayers.SHIELD));
      this.tridentModel = new TridentModel(this.entityModelSet.bakeLayer(ModelLayers.TRIDENT));
      this.skullModels = SkullBlockRenderer.createSkullRenderers(this.entityModelSet);
   }

   public void renderByItem(ItemStack var1, ItemTransforms.TransformType var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      Item â˜ƒ = â˜ƒ.getItem();
      if (â˜ƒ instanceof BlockItem) {
         Block â˜ƒx = ((BlockItem)â˜ƒ).getBlock();
         if (â˜ƒx instanceof AbstractSkullBlock) {
            GameProfile â˜ƒxx = null;
            if (â˜ƒ.hasTag()) {
               CompoundTag â˜ƒxxx = â˜ƒ.getTag();
               if (â˜ƒxxx.contains("SkullOwner", 10)) {
                  â˜ƒxx = NbtUtils.readGameProfile(â˜ƒxxx.getCompound("SkullOwner"));
               } else if (â˜ƒxxx.contains("SkullOwner", 8) && !StringUtils.isBlank(â˜ƒxxx.getString("SkullOwner"))) {
                  â˜ƒxx = new GameProfile(null, â˜ƒxxx.getString("SkullOwner"));
                  â˜ƒxxx.remove("SkullOwner");
                  SkullBlockEntity.updateGameprofile(â˜ƒxx, var1x -> â˜ƒ.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), var1x)));
               }
            }

            SkullBlock.Type â˜ƒxx = ((AbstractSkullBlock)â˜ƒx).getType();
            SkullModelBase â˜ƒxxx = (SkullModelBase)this.skullModels.get(â˜ƒxx);
            RenderType â˜ƒxxxx = SkullBlockRenderer.getRenderType(â˜ƒxx, â˜ƒxx);
            SkullBlockRenderer.renderSkull(null, 180.0F, 0.0F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx);
         } else {
            BlockState â˜ƒxx = â˜ƒx.defaultBlockState();
            BlockEntity â˜ƒx;
            if (â˜ƒx instanceof AbstractBannerBlock) {
               this.banner.fromItem(â˜ƒ, ((AbstractBannerBlock)â˜ƒx).getColor());
               â˜ƒx = this.banner;
            } else if (â˜ƒx instanceof BedBlock) {
               this.bed.setColor(((BedBlock)â˜ƒx).getColor());
               â˜ƒx = this.bed;
            } else if (â˜ƒxx.is(Blocks.CONDUIT)) {
               â˜ƒx = this.conduit;
            } else if (â˜ƒxx.is(Blocks.CHEST)) {
               â˜ƒx = this.chest;
            } else if (â˜ƒxx.is(Blocks.ENDER_CHEST)) {
               â˜ƒx = this.enderChest;
            } else if (â˜ƒxx.is(Blocks.TRAPPED_CHEST)) {
               â˜ƒx = this.trappedChest;
            } else {
               if (!(â˜ƒx instanceof ShulkerBoxBlock)) {
                  return;
               }

               DyeColor â˜ƒx = ShulkerBoxBlock.getColorFromItem(â˜ƒ);
               if (â˜ƒx == null) {
                  â˜ƒx = DEFAULT_SHULKER_BOX;
               } else {
                  â˜ƒx = SHULKER_BOXES[â˜ƒx.getId()];
               }
            }

            this.blockEntityRenderDispatcher.renderItem(â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      } else {
         if (â˜ƒ.is(Items.SHIELD)) {
            boolean â˜ƒ = â˜ƒ.getTagElement("BlockEntityTag") != null;
            â˜ƒ.pushPose();
            â˜ƒ.scale(1.0F, -1.0F, -1.0F);
            Material â˜ƒx = â˜ƒ ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;
            VertexConsumer â˜ƒxx = â˜ƒx.sprite()
               .wrap(ItemRenderer.getFoilBufferDirect(â˜ƒ, this.shieldModel.renderType(â˜ƒx.atlasLocation()), true, â˜ƒ.hasFoil()));
            this.shieldModel.handle().render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
            if (â˜ƒ) {
               List<Pair<BannerPattern, DyeColor>> â˜ƒxxx = BannerBlockEntity.createPatterns(ShieldItem.getColor(â˜ƒ), BannerBlockEntity.getItemPatterns(â˜ƒ));
               BannerRenderer.renderPatterns(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.shieldModel.plate(), â˜ƒx, false, â˜ƒxxx, â˜ƒ.hasFoil());
            } else {
               this.shieldModel.plate().render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            â˜ƒ.popPose();
         } else if (â˜ƒ.is(Items.TRIDENT)) {
            â˜ƒ.pushPose();
            â˜ƒ.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer â˜ƒ = ItemRenderer.getFoilBufferDirect(â˜ƒ, this.tridentModel.renderType(TridentModel.TEXTURE), false, â˜ƒ.hasFoil());
            this.tridentModel.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
            â˜ƒ.popPose();
         }
      }
   }
}
