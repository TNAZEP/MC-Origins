package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRenderers {
   private static final Map<BlockEntityType<?>, BlockEntityRendererProvider<?>> PROVIDERS = Maps.<BlockEntityType<?>, BlockEntityRendererProvider<?>>newHashMap(
      
   );

   private static <T extends BlockEntity> void register(BlockEntityType<? extends T> var0, BlockEntityRendererProvider<T> var1) {
      PROVIDERS.put(â˜ƒ, â˜ƒ);
   }

   public static Map<BlockEntityType<?>, BlockEntityRenderer<?>> createEntityRenderers(BlockEntityRendererProvider.Context var0) {
      Builder<BlockEntityType<?>, BlockEntityRenderer<?>> â˜ƒ = ImmutableMap.builder();
      PROVIDERS.forEach((var2, var3) -> {
         try {
            â˜ƒ.put(var2, var3.create(â˜ƒ));
         } catch (Exception var5) {
            throw new IllegalStateException("Failed to create model for " + Registry.BLOCK_ENTITY_TYPE.getKey(var2), var5);
         }
      });
      return â˜ƒ.build();
   }

   static {
      register(BlockEntityType.SIGN, SignRenderer::new);
      register(BlockEntityType.MOB_SPAWNER, SpawnerRenderer::new);
      register(BlockEntityType.PISTON, PistonHeadRenderer::new);
      register(BlockEntityType.CHEST, ChestRenderer::new);
      register(BlockEntityType.ENDER_CHEST, ChestRenderer::new);
      register(BlockEntityType.TRAPPED_CHEST, ChestRenderer::new);
      register(BlockEntityType.ENCHANTING_TABLE, EnchantTableRenderer::new);
      register(BlockEntityType.LECTERN, LecternRenderer::new);
      register(BlockEntityType.END_PORTAL, TheEndPortalRenderer::new);
      register(BlockEntityType.END_GATEWAY, TheEndGatewayRenderer::new);
      register(BlockEntityType.BEACON, BeaconRenderer::new);
      register(BlockEntityType.SKULL, SkullBlockRenderer::new);
      register(BlockEntityType.BANNER, BannerRenderer::new);
      register(BlockEntityType.STRUCTURE_BLOCK, StructureBlockRenderer::new);
      register(BlockEntityType.SHULKER_BOX, ShulkerBoxRenderer::new);
      register(BlockEntityType.BED, BedRenderer::new);
      register(BlockEntityType.CONDUIT, ConduitRenderer::new);
      register(BlockEntityType.BELL, BellRenderer::new);
      register(BlockEntityType.CAMPFIRE, CampfireRenderer::new);
   }
}
