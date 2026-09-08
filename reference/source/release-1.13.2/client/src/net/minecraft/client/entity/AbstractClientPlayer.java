package net.minecraft.client.entity;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.ThreadDownloadImageData;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

public abstract class AbstractClientPlayer extends EntityPlayer {
   private NetworkPlayerInfo field_175157_a;
   public float field_184835_a;
   public float field_184836_b;
   public float field_184837_c;

   public AbstractClientPlayer(World var1, GameProfile var2) {
      super(☃, ☃);
   }

   @Override
   public boolean func_175149_v() {
      NetworkPlayerInfo ☃ = Minecraft.func_71410_x().func_147114_u().func_175102_a(this.func_146103_bH().getId());
      return ☃ != null && ☃.func_178848_b() == GameType.SPECTATOR;
   }

   @Override
   public boolean func_184812_l_() {
      NetworkPlayerInfo ☃ = Minecraft.func_71410_x().func_147114_u().func_175102_a(this.func_146103_bH().getId());
      return ☃ != null && ☃.func_178848_b() == GameType.CREATIVE;
   }

   public boolean func_152122_n() {
      return this.func_175155_b() != null;
   }

   @Nullable
   protected NetworkPlayerInfo func_175155_b() {
      if (this.field_175157_a == null) {
         this.field_175157_a = Minecraft.func_71410_x().func_147114_u().func_175102_a(this.func_110124_au());
      }

      return this.field_175157_a;
   }

   public boolean func_152123_o() {
      NetworkPlayerInfo ☃ = this.func_175155_b();
      return ☃ != null && ☃.func_178856_e();
   }

   public ResourceLocation func_110306_p() {
      NetworkPlayerInfo ☃ = this.func_175155_b();
      return ☃ == null ? DefaultPlayerSkin.func_177334_a(this.func_110124_au()) : ☃.func_178837_g();
   }

   @Nullable
   public ResourceLocation func_110303_q() {
      NetworkPlayerInfo ☃ = this.func_175155_b();
      return ☃ == null ? null : ☃.func_178861_h();
   }

   public boolean func_184833_s() {
      return this.func_175155_b() != null;
   }

   @Nullable
   public ResourceLocation func_184834_t() {
      NetworkPlayerInfo ☃ = this.func_175155_b();
      return ☃ == null ? null : ☃.func_187106_i();
   }

   public static ThreadDownloadImageData func_110304_a(ResourceLocation var0, String var1) {
      TextureManager ☃ = Minecraft.func_71410_x().func_110434_K();
      ITextureObject ☃x = ☃.func_110581_b(☃);
      if (☃x == null) {
         ☃x = new ThreadDownloadImageData(
            null,
            String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.func_76338_a(☃)),
            DefaultPlayerSkin.func_177334_a(func_175147_b(☃)),
            new ImageBufferDownload()
         );
         ☃.func_110579_a(☃, ☃x);
      }

      return (ThreadDownloadImageData)☃x;
   }

   public static ResourceLocation func_110311_f(String var0) {
      return new ResourceLocation("skins/" + Hashing.sha1().hashUnencodedChars(StringUtils.func_76338_a(☃)));
   }

   public String func_175154_l() {
      NetworkPlayerInfo ☃ = this.func_175155_b();
      return ☃ == null ? DefaultPlayerSkin.func_177332_b(this.func_110124_au()) : ☃.func_178851_f();
   }

   public float func_175156_o() {
      float ☃ = 1.0F;
      if (this.field_71075_bZ.field_75100_b) {
         ☃ *= 1.1F;
      }

      IAttributeInstance ☃ = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      ☃ = (float)((double)☃ * ((☃.func_111126_e() / (double)this.field_71075_bZ.func_75094_b() + 1.0) / 2.0));
      if (this.field_71075_bZ.func_75094_b() == 0.0F || Float.isNaN(☃) || Float.isInfinite(☃)) {
         ☃ = 1.0F;
      }

      if (this.func_184587_cr() && this.func_184607_cu().func_77973_b() == Items.field_151031_f) {
         int ☃ = this.func_184612_cw();
         float ☃x = (float)☃ / 20.0F;
         if (☃x > 1.0F) {
            ☃x = 1.0F;
         } else {
            ☃x *= ☃x;
         }

         ☃ *= 1.0F - ☃x * 0.15F;
      }

      return ☃;
   }
}
