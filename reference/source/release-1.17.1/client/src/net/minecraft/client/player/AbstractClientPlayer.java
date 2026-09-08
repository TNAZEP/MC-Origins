package net.minecraft.client.player;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

public abstract class AbstractClientPlayer extends Player {
   private static final String SKIN_URL_TEMPLATE = "http://skins.minecraft.net/MinecraftSkins/%s.png";
   public static final int SKIN_HEAD_U = 8;
   public static final int SKIN_HEAD_V = 8;
   public static final int SKIN_HEAD_WIDTH = 8;
   public static final int SKIN_HEAD_HEIGHT = 8;
   public static final int SKIN_HAT_U = 40;
   public static final int SKIN_HAT_V = 8;
   public static final int SKIN_HAT_WIDTH = 8;
   public static final int SKIN_HAT_HEIGHT = 8;
   public static final int SKIN_TEX_WIDTH = 64;
   public static final int SKIN_TEX_HEIGHT = 64;
   private PlayerInfo playerInfo;
   public float elytraRotX;
   public float elytraRotY;
   public float elytraRotZ;
   public final ClientLevel clientLevel;

   public AbstractClientPlayer(ClientLevel var1, GameProfile var2) {
      super(â˜ƒ, â˜ƒ.getSharedSpawnPos(), â˜ƒ.getSharedSpawnAngle(), â˜ƒ);
      this.clientLevel = â˜ƒ;
   }

   @Override
   public boolean isSpectator() {
      PlayerInfo â˜ƒ = Minecraft.getInstance().getConnection().getPlayerInfo(this.getGameProfile().getId());
      return â˜ƒ != null && â˜ƒ.getGameMode() == GameType.SPECTATOR;
   }

   @Override
   public boolean isCreative() {
      PlayerInfo â˜ƒ = Minecraft.getInstance().getConnection().getPlayerInfo(this.getGameProfile().getId());
      return â˜ƒ != null && â˜ƒ.getGameMode() == GameType.CREATIVE;
   }

   public boolean isCapeLoaded() {
      return this.getPlayerInfo() != null;
   }

   @Nullable
   protected PlayerInfo getPlayerInfo() {
      if (this.playerInfo == null) {
         this.playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(this.getUUID());
      }

      return this.playerInfo;
   }

   public boolean isSkinLoaded() {
      PlayerInfo â˜ƒ = this.getPlayerInfo();
      return â˜ƒ != null && â˜ƒ.isSkinLoaded();
   }

   public ResourceLocation getSkinTextureLocation() {
      PlayerInfo â˜ƒ = this.getPlayerInfo();
      return â˜ƒ == null ? DefaultPlayerSkin.getDefaultSkin(this.getUUID()) : â˜ƒ.getSkinLocation();
   }

   @Nullable
   public ResourceLocation getCloakTextureLocation() {
      PlayerInfo â˜ƒ = this.getPlayerInfo();
      return â˜ƒ == null ? null : â˜ƒ.getCapeLocation();
   }

   public boolean isElytraLoaded() {
      return this.getPlayerInfo() != null;
   }

   @Nullable
   public ResourceLocation getElytraTextureLocation() {
      PlayerInfo â˜ƒ = this.getPlayerInfo();
      return â˜ƒ == null ? null : â˜ƒ.getElytraLocation();
   }

   public static void registerSkinTexture(ResourceLocation var0, String var1) {
      TextureManager â˜ƒ = Minecraft.getInstance().getTextureManager();
      AbstractTexture â˜ƒx = â˜ƒ.getTexture(â˜ƒ, MissingTextureAtlasSprite.getTexture());
      if (â˜ƒx == MissingTextureAtlasSprite.getTexture()) {
         AbstractTexture var4 = new HttpTexture(
            null,
            String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtil.stripColor(â˜ƒ)),
            DefaultPlayerSkin.getDefaultSkin(createPlayerUUID(â˜ƒ)),
            true,
            null
         );
         â˜ƒ.register(â˜ƒ, var4);
      }
   }

   public static ResourceLocation getSkinLocation(String var0) {
      return new ResourceLocation("skins/" + Hashing.sha1().hashUnencodedChars(StringUtil.stripColor(â˜ƒ)));
   }

   public String getModelName() {
      PlayerInfo â˜ƒ = this.getPlayerInfo();
      return â˜ƒ == null ? DefaultPlayerSkin.getSkinModelName(this.getUUID()) : â˜ƒ.getModelName();
   }

   public float getFieldOfViewModifier() {
      float â˜ƒ = 1.0F;
      if (this.getAbilities().flying) {
         â˜ƒ *= 1.1F;
      }

      â˜ƒ = (float)((double)â˜ƒ * ((this.getAttributeValue(Attributes.MOVEMENT_SPEED) / (double)this.getAbilities().getWalkingSpeed() + 1.0) / 2.0));
      if (this.getAbilities().getWalkingSpeed() == 0.0F || Float.isNaN(â˜ƒ) || Float.isInfinite(â˜ƒ)) {
         â˜ƒ = 1.0F;
      }

      ItemStack â˜ƒ = this.getUseItem();
      if (this.isUsingItem()) {
         if (â˜ƒ.is(Items.BOW)) {
            int â˜ƒx = this.getTicksUsingItem();
            float â˜ƒxx = (float)â˜ƒx / 20.0F;
            if (â˜ƒxx > 1.0F) {
               â˜ƒxx = 1.0F;
            } else {
               â˜ƒxx *= â˜ƒxx;
            }

            â˜ƒ *= 1.0F - â˜ƒxx * 0.15F;
         } else if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && this.isScoping()) {
            return 0.1F;
         }
      }

      return Mth.lerp(Minecraft.getInstance().options.fovEffectScale, 1.0F, â˜ƒ);
   }
}
