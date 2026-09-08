package net.minecraft.world.item.context;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class UseOnContext {
   @Nullable
   private final Player player;
   private final InteractionHand hand;
   private final BlockHitResult hitResult;
   private final Level level;
   private final ItemStack itemStack;

   public UseOnContext(Player var1, InteractionHand var2, BlockHitResult var3) {
      this(â˜ƒ.level, â˜ƒ, â˜ƒ, â˜ƒ.getItemInHand(â˜ƒ), â˜ƒ);
   }

   protected UseOnContext(Level var1, @Nullable Player var2, InteractionHand var3, ItemStack var4, BlockHitResult var5) {
      this.player = â˜ƒ;
      this.hand = â˜ƒ;
      this.hitResult = â˜ƒ;
      this.itemStack = â˜ƒ;
      this.level = â˜ƒ;
   }

   protected final BlockHitResult getHitResult() {
      return this.hitResult;
   }

   public BlockPos getClickedPos() {
      return this.hitResult.getBlockPos();
   }

   public Direction getClickedFace() {
      return this.hitResult.getDirection();
   }

   public Vec3 getClickLocation() {
      return this.hitResult.getLocation();
   }

   public boolean isInside() {
      return this.hitResult.isInside();
   }

   public ItemStack getItemInHand() {
      return this.itemStack;
   }

   @Nullable
   public Player getPlayer() {
      return this.player;
   }

   public InteractionHand getHand() {
      return this.hand;
   }

   public Level getLevel() {
      return this.level;
   }

   public Direction getHorizontalDirection() {
      return this.player == null ? Direction.NORTH : this.player.getDirection();
   }

   public boolean isSecondaryUseActive() {
      return this.player != null && this.player.isSecondaryUseActive();
   }

   public float getRotation() {
      return this.player == null ? 0.0F : this.player.getYRot();
   }
}
