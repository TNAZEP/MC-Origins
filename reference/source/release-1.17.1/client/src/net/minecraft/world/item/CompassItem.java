package net.minecraft.world.item;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompassItem extends Item implements Vanishable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final String TAG_LODESTONE_POS = "LodestonePos";
   public static final String TAG_LODESTONE_DIMENSION = "LodestoneDimension";
   public static final String TAG_LODESTONE_TRACKED = "LodestoneTracked";

   public CompassItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   public static boolean isLodestoneCompass(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      return â˜ƒ != null && (â˜ƒ.contains("LodestoneDimension") || â˜ƒ.contains("LodestonePos"));
   }

   @Override
   public boolean isFoil(ItemStack var1) {
      return isLodestoneCompass(â˜ƒ) || super.isFoil(â˜ƒ);
   }

   public static Optional<ResourceKey<Level>> getLodestoneDimension(CompoundTag var0) {
      return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, â˜ƒ.get("LodestoneDimension")).result();
   }

   @Override
   public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
      if (!â˜ƒ.isClientSide) {
         if (isLodestoneCompass(â˜ƒ)) {
            CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
            if (â˜ƒ.contains("LodestoneTracked") && !â˜ƒ.getBoolean("LodestoneTracked")) {
               return;
            }

            Optional<ResourceKey<Level>> â˜ƒ = getLodestoneDimension(â˜ƒ);
            if (â˜ƒ.isPresent() && â˜ƒ.get() == â˜ƒ.dimension() && â˜ƒ.contains("LodestonePos")) {
               BlockPos â˜ƒx = NbtUtils.readBlockPos(â˜ƒ.getCompound("LodestonePos"));
               if (!â˜ƒ.isInWorldBounds(â˜ƒx) || !((ServerLevel)â˜ƒ).getPoiManager().existsAtPosition(PoiType.LODESTONE, â˜ƒx)) {
                  â˜ƒ.remove("LodestonePos");
               }
            }
         }
      }
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      Level â˜ƒx = â˜ƒ.getLevel();
      if (!â˜ƒx.getBlockState(â˜ƒ).is(Blocks.LODESTONE)) {
         return super.useOn(â˜ƒ);
      } else {
         â˜ƒx.playSound(null, â˜ƒ, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
         Player â˜ƒ = â˜ƒ.getPlayer();
         ItemStack â˜ƒx = â˜ƒ.getItemInHand();
         boolean â˜ƒxx = !â˜ƒ.getAbilities().instabuild && â˜ƒx.getCount() == 1;
         if (â˜ƒxx) {
            this.addLodestoneTags(â˜ƒx.dimension(), â˜ƒ, â˜ƒx.getOrCreateTag());
         } else {
            ItemStack â˜ƒ = new ItemStack(Items.COMPASS, 1);
            CompoundTag â˜ƒx = â˜ƒx.hasTag() ? â˜ƒx.getTag().copy() : new CompoundTag();
            â˜ƒ.setTag(â˜ƒx);
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒx.shrink(1);
            }

            this.addLodestoneTags(â˜ƒx.dimension(), â˜ƒ, â˜ƒx);
            if (!â˜ƒ.getInventory().add(â˜ƒ)) {
               â˜ƒ.drop(â˜ƒ, false);
            }
         }

         return InteractionResult.sidedSuccess(â˜ƒx.isClientSide);
      }
   }

   private void addLodestoneTags(ResourceKey<Level> var1, BlockPos var2, CompoundTag var3) {
      â˜ƒ.put("LodestonePos", NbtUtils.writeBlockPos(â˜ƒ));
      Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, â˜ƒ).resultOrPartial(LOGGER::error).ifPresent(var1x -> â˜ƒ.put("LodestoneDimension", var1x));
      â˜ƒ.putBoolean("LodestoneTracked", true);
   }

   @Override
   public String getDescriptionId(ItemStack var1) {
      return isLodestoneCompass(â˜ƒ) ? "item.minecraft.lodestone_compass" : super.getDescriptionId(â˜ƒ);
   }
}
