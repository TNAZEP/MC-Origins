package net.minecraft.world.level.block.entity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SkullBlockEntity extends BlockEntity {
   public static final String TAG_SKULL_OWNER = "SkullOwner";
   @Nullable
   private static GameProfileCache profileCache;
   @Nullable
   private static MinecraftSessionService sessionService;
   @Nullable
   private static Executor mainThreadExecutor;
   @Nullable
   private GameProfile owner;
   private int mouthTickCount;
   private boolean isMovingMouth;

   public SkullBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.SKULL, â˜ƒ, â˜ƒ);
   }

   public static void setProfileCache(GameProfileCache var0) {
      profileCache = â˜ƒ;
   }

   public static void setSessionService(MinecraftSessionService var0) {
      sessionService = â˜ƒ;
   }

   public static void setMainThreadExecutor(Executor var0) {
      mainThreadExecutor = â˜ƒ;
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (this.owner != null) {
         CompoundTag â˜ƒ = new CompoundTag();
         NbtUtils.writeGameProfile(â˜ƒ, this.owner);
         â˜ƒ.put("SkullOwner", â˜ƒ);
      }

      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      if (â˜ƒ.contains("SkullOwner", 10)) {
         this.setOwner(NbtUtils.readGameProfile(â˜ƒ.getCompound("SkullOwner")));
      } else if (â˜ƒ.contains("ExtraType", 8)) {
         String â˜ƒ = â˜ƒ.getString("ExtraType");
         if (!StringUtil.isNullOrEmpty(â˜ƒ)) {
            this.setOwner(new GameProfile(null, â˜ƒ));
         }
      }
   }

   public static void dragonHeadAnimation(Level var0, BlockPos var1, BlockState var2, SkullBlockEntity var3) {
      if (â˜ƒ.hasNeighborSignal(â˜ƒ)) {
         â˜ƒ.isMovingMouth = true;
         ++â˜ƒ.mouthTickCount;
      } else {
         â˜ƒ.isMovingMouth = false;
      }
   }

   public float getMouthAnimation(float var1) {
      return this.isMovingMouth ? (float)this.mouthTickCount + â˜ƒ : (float)this.mouthTickCount;
   }

   @Nullable
   public GameProfile getOwnerProfile() {
      return this.owner;
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 4, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   public void setOwner(@Nullable GameProfile var1) {
      synchronized(this) {
         this.owner = â˜ƒ;
      }

      this.updateOwnerProfile();
   }

   private void updateOwnerProfile() {
      updateGameprofile(this.owner, var1 -> {
         this.owner = var1;
         this.setChanged();
      });
   }

   public static void updateGameprofile(@Nullable GameProfile var0, Consumer<GameProfile> var1) {
      if (â˜ƒ != null
         && !StringUtil.isNullOrEmpty(â˜ƒ.getName())
         && (!â˜ƒ.isComplete() || !â˜ƒ.getProperties().containsKey("textures"))
         && profileCache != null
         && sessionService != null) {
         profileCache.getAsync(â˜ƒ.getName(), var2 -> Util.backgroundExecutor().execute(() -> Util.ifElse(var2, var1x -> {
                  Property â˜ƒ = Iterables.getFirst(var1x.getProperties().get("textures"), null);
                  if (â˜ƒ == null) {
                     var1x = sessionService.fillProfileProperties(var1x, true);
                  }

                  GameProfile â˜ƒ = var1x;
                  mainThreadExecutor.execute(() -> {
                     profileCache.add(â˜ƒ);
                     â˜ƒ.accept(â˜ƒ);
                  });
               }, () -> mainThreadExecutor.execute(() -> â˜ƒ.accept(â˜ƒ)))));
      } else {
         â˜ƒ.accept(â˜ƒ);
      }
   }
}
