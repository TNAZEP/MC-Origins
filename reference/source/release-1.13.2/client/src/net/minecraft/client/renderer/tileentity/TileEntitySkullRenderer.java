package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockAbstractSkull;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSkullWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelDragonHead;
import net.minecraft.client.renderer.entity.model.ModelHumanoidHead;
import net.minecraft.client.renderer.entity.model.ModelSkeletonHead;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class TileEntitySkullRenderer extends TileEntityRenderer<TileEntitySkull> {
   public static TileEntitySkullRenderer field_147536_b;
   private static final Map<BlockSkull.ISkullType, ModelBase> field_199358_e = Util.func_200696_a(
      Maps.<BlockSkull.ISkullType, ModelBase>newHashMap(), var0 -> {
         ModelSkeletonHead ☃ = new ModelSkeletonHead(0, 0, 64, 32);
         ModelSkeletonHead ☃x = new ModelHumanoidHead();
         ModelDragonHead ☃xx = new ModelDragonHead(0.0F);
         var0.put(BlockSkull.Types.SKELETON, ☃);
         var0.put(BlockSkull.Types.WITHER_SKELETON, ☃);
         var0.put(BlockSkull.Types.PLAYER, ☃x);
         var0.put(BlockSkull.Types.ZOMBIE, ☃x);
         var0.put(BlockSkull.Types.CREEPER, ☃);
         var0.put(BlockSkull.Types.DRAGON, ☃xx);
      }
   );
   private static final Map<BlockSkull.ISkullType, ResourceLocation> field_199357_d = Util.func_200696_a(
      Maps.<BlockSkull.ISkullType, ResourceLocation>newHashMap(), var0 -> {
         var0.put(BlockSkull.Types.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
         var0.put(BlockSkull.Types.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
         var0.put(BlockSkull.Types.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
         var0.put(BlockSkull.Types.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
         var0.put(BlockSkull.Types.DRAGON, new ResourceLocation("textures/entity/enderdragon/dragon.png"));
         var0.put(BlockSkull.Types.PLAYER, DefaultPlayerSkin.func_177335_a());
      }
   );

   public void func_199341_a(TileEntitySkull var1, double var2, double var4, double var6, float var8, int var9) {
      float ☃ = ☃.func_184295_a(☃);
      IBlockState ☃x = ☃.func_195044_w();
      boolean ☃xx = ☃x.func_177230_c() instanceof BlockSkullWall;
      EnumFacing ☃xxx = ☃xx ? ☃x.func_177229_b(BlockSkullWall.field_196302_a) : null;
      float ☃xxxx = 22.5F * (float)(☃xx ? (2 + ☃xxx.func_176736_b()) * 4 : ☃x.func_177229_b(BlockSkull.field_196294_a));
      this.func_199355_a((float)☃, (float)☃, (float)☃, ☃xxx, ☃xxxx, ((BlockAbstractSkull)☃x.func_177230_c()).func_196292_N_(), ☃.func_152108_a(), ☃, ☃);
   }

   @Override
   public void func_147497_a(TileEntityRendererDispatcher var1) {
      super.func_147497_a(☃);
      field_147536_b = this;
   }

   public void func_199355_a(
      float var1, float var2, float var3, @Nullable EnumFacing var4, float var5, BlockSkull.ISkullType var6, @Nullable GameProfile var7, int var8, float var9
   ) {
      ModelBase ☃ = (ModelBase)field_199358_e.get(☃);
      if (☃ >= 0) {
         this.func_147499_a(field_178460_a[☃]);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(4.0F, 2.0F, 1.0F);
         GlStateManager.func_179109_b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.func_179128_n(5888);
      } else {
         this.func_147499_a(this.func_199356_a(☃, ☃));
      }

      GlStateManager.func_179094_E();
      GlStateManager.func_179129_p();
      if (☃ == null) {
         GlStateManager.func_179109_b(☃ + 0.5F, ☃, ☃ + 0.5F);
      } else {
         switch(☃) {
            case NORTH:
               GlStateManager.func_179109_b(☃ + 0.5F, ☃ + 0.25F, ☃ + 0.74F);
               break;
            case SOUTH:
               GlStateManager.func_179109_b(☃ + 0.5F, ☃ + 0.25F, ☃ + 0.26F);
               break;
            case WEST:
               GlStateManager.func_179109_b(☃ + 0.74F, ☃ + 0.25F, ☃ + 0.5F);
               break;
            case EAST:
            default:
               GlStateManager.func_179109_b(☃ + 0.26F, ☃ + 0.25F, ☃ + 0.5F);
         }
      }

      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
      GlStateManager.func_179141_d();
      if (☃ == BlockSkull.Types.PLAYER) {
         GlStateManager.func_187408_a(GlStateManager.Profile.PLAYER_SKIN);
      }

      ☃.func_78088_a(null, ☃, 0.0F, 0.0F, ☃, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
      if (☃ >= 0) {
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5888);
      }
   }

   private ResourceLocation func_199356_a(BlockSkull.ISkullType var1, @Nullable GameProfile var2) {
      ResourceLocation ☃ = (ResourceLocation)field_199357_d.get(☃);
      if (☃ == BlockSkull.Types.PLAYER && ☃ != null) {
         Minecraft ☃x = Minecraft.func_71410_x();
         Map<Type, MinecraftProfileTexture> ☃xx = ☃x.func_152342_ad().func_152788_a(☃);
         if (☃xx.containsKey(Type.SKIN)) {
            ☃ = ☃x.func_152342_ad().func_152792_a((MinecraftProfileTexture)☃xx.get(Type.SKIN), Type.SKIN);
         } else {
            ☃ = DefaultPlayerSkin.func_177334_a(EntityPlayer.func_146094_a(☃));
         }
      }

      return ☃;
   }
}
