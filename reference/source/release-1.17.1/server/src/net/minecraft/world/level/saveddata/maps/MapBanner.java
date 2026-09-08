package net.minecraft.world.level.saveddata.maps;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MapBanner {
   private final BlockPos pos;
   private final DyeColor color;
   @Nullable
   private final Component name;

   public MapBanner(BlockPos var1, DyeColor var2, @Nullable Component var3) {
      this.pos = â˜ƒ;
      this.color = â˜ƒ;
      this.name = â˜ƒ;
   }

   public static MapBanner load(CompoundTag var0) {
      BlockPos â˜ƒ = NbtUtils.readBlockPos(â˜ƒ.getCompound("Pos"));
      DyeColor â˜ƒx = DyeColor.byName(â˜ƒ.getString("Color"), DyeColor.WHITE);
      Component â˜ƒxx = â˜ƒ.contains("Name") ? Component.Serializer.fromJson(â˜ƒ.getString("Name")) : null;
      return new MapBanner(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   @Nullable
   public static MapBanner fromWorld(BlockGetter var0, BlockPos var1) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof BannerBlockEntity â˜ƒ) {
         DyeColor â˜ƒxx = â˜ƒ.getBaseColor();
         Component â˜ƒxxx = â˜ƒ.hasCustomName() ? â˜ƒ.getCustomName() : null;
         return new MapBanner(â˜ƒ, â˜ƒxx, â˜ƒxxx);
      } else {
         return null;
      }
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public DyeColor getColor() {
      return this.color;
   }

   public MapDecoration.Type getDecoration() {
      switch(this.color) {
         case WHITE:
            return MapDecoration.Type.BANNER_WHITE;
         case ORANGE:
            return MapDecoration.Type.BANNER_ORANGE;
         case MAGENTA:
            return MapDecoration.Type.BANNER_MAGENTA;
         case LIGHT_BLUE:
            return MapDecoration.Type.BANNER_LIGHT_BLUE;
         case YELLOW:
            return MapDecoration.Type.BANNER_YELLOW;
         case LIME:
            return MapDecoration.Type.BANNER_LIME;
         case PINK:
            return MapDecoration.Type.BANNER_PINK;
         case GRAY:
            return MapDecoration.Type.BANNER_GRAY;
         case LIGHT_GRAY:
            return MapDecoration.Type.BANNER_LIGHT_GRAY;
         case CYAN:
            return MapDecoration.Type.BANNER_CYAN;
         case PURPLE:
            return MapDecoration.Type.BANNER_PURPLE;
         case BLUE:
            return MapDecoration.Type.BANNER_BLUE;
         case BROWN:
            return MapDecoration.Type.BANNER_BROWN;
         case GREEN:
            return MapDecoration.Type.BANNER_GREEN;
         case RED:
            return MapDecoration.Type.BANNER_RED;
         case BLACK:
         default:
            return MapDecoration.Type.BANNER_BLACK;
      }
   }

   @Nullable
   public Component getName() {
      return this.name;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         MapBanner â˜ƒ = (MapBanner)â˜ƒ;
         return Objects.equals(this.pos, â˜ƒ.pos) && this.color == â˜ƒ.color && Objects.equals(this.name, â˜ƒ.name);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.pos, this.color, this.name});
   }

   public CompoundTag save() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.put("Pos", NbtUtils.writeBlockPos(this.pos));
      â˜ƒ.putString("Color", this.color.getName());
      if (this.name != null) {
         â˜ƒ.putString("Name", Component.Serializer.toJson(this.name));
      }

      return â˜ƒ;
   }

   public String getId() {
      return "banner-" + this.pos.getX() + "," + this.pos.getY() + "," + this.pos.getZ();
   }
}
