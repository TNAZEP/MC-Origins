package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BannerBlockEntity extends BlockEntity implements Nameable {
   public static final int MAX_PATTERNS = 6;
   public static final String TAG_PATTERNS = "Patterns";
   public static final String TAG_PATTERN = "Pattern";
   public static final String TAG_COLOR = "Color";
   @Nullable
   private Component name;
   private DyeColor baseColor;
   @Nullable
   private ListTag itemPatterns;
   private boolean receivedData;
   @Nullable
   private List<Pair<BannerPattern, DyeColor>> patterns;

   public BannerBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.BANNER, â˜ƒ, â˜ƒ);
      this.baseColor = ((AbstractBannerBlock)â˜ƒ.getBlock()).getColor();
   }

   public BannerBlockEntity(BlockPos var1, BlockState var2, DyeColor var3) {
      this(â˜ƒ, â˜ƒ);
      this.baseColor = â˜ƒ;
   }

   @Nullable
   public static ListTag getItemPatterns(ItemStack var0) {
      ListTag â˜ƒ = null;
      CompoundTag â˜ƒx = â˜ƒ.getTagElement("BlockEntityTag");
      if (â˜ƒx != null && â˜ƒx.contains("Patterns", 9)) {
         â˜ƒ = â˜ƒx.getList("Patterns", 10).copy();
      }

      return â˜ƒ;
   }

   public void fromItem(ItemStack var1, DyeColor var2) {
      this.itemPatterns = getItemPatterns(â˜ƒ);
      this.baseColor = â˜ƒ;
      this.patterns = null;
      this.receivedData = true;
      this.name = â˜ƒ.hasCustomHoverName() ? â˜ƒ.getHoverName() : null;
   }

   @Override
   public Component getName() {
      return (Component)(this.name != null ? this.name : new TranslatableComponent("block.minecraft.banner"));
   }

   @Nullable
   @Override
   public Component getCustomName() {
      return this.name;
   }

   public void setCustomName(Component var1) {
      this.name = â˜ƒ;
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (this.itemPatterns != null) {
         â˜ƒ.put("Patterns", this.itemPatterns);
      }

      if (this.name != null) {
         â˜ƒ.putString("CustomName", Component.Serializer.toJson(this.name));
      }

      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      if (â˜ƒ.contains("CustomName", 8)) {
         this.name = Component.Serializer.fromJson(â˜ƒ.getString("CustomName"));
      }

      this.itemPatterns = â˜ƒ.getList("Patterns", 10);
      this.patterns = null;
      this.receivedData = true;
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 6, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   public static int getPatternCount(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("BlockEntityTag");
      return â˜ƒ != null && â˜ƒ.contains("Patterns") ? â˜ƒ.getList("Patterns", 10).size() : 0;
   }

   public List<Pair<BannerPattern, DyeColor>> getPatterns() {
      if (this.patterns == null && this.receivedData) {
         this.patterns = createPatterns(this.baseColor, this.itemPatterns);
      }

      return this.patterns;
   }

   public static List<Pair<BannerPattern, DyeColor>> createPatterns(DyeColor var0, @Nullable ListTag var1) {
      List<Pair<BannerPattern, DyeColor>> â˜ƒ = Lists.<Pair<BannerPattern, DyeColor>>newArrayList();
      â˜ƒ.add(Pair.of(BannerPattern.BASE, â˜ƒ));
      if (â˜ƒ != null) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
            BannerPattern â˜ƒxxx = BannerPattern.byHash(â˜ƒxx.getString("Pattern"));
            if (â˜ƒxxx != null) {
               int â˜ƒxxxx = â˜ƒxx.getInt("Color");
               â˜ƒ.add(Pair.of(â˜ƒxxx, DyeColor.byId(â˜ƒxxxx)));
            }
         }
      }

      return â˜ƒ;
   }

   public static void removeLastPattern(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("BlockEntityTag");
      if (â˜ƒ != null && â˜ƒ.contains("Patterns", 9)) {
         ListTag â˜ƒx = â˜ƒ.getList("Patterns", 10);
         if (!â˜ƒx.isEmpty()) {
            â˜ƒx.remove(â˜ƒx.size() - 1);
            if (â˜ƒx.isEmpty()) {
               â˜ƒ.removeTagKey("BlockEntityTag");
            }
         }
      }
   }

   public ItemStack getItem() {
      ItemStack â˜ƒ = new ItemStack(BannerBlock.byColor(this.baseColor));
      if (this.itemPatterns != null && !this.itemPatterns.isEmpty()) {
         â˜ƒ.getOrCreateTagElement("BlockEntityTag").put("Patterns", this.itemPatterns.copy());
      }

      if (this.name != null) {
         â˜ƒ.setHoverName(this.name);
      }

      return â˜ƒ;
   }

   public DyeColor getBaseColor() {
      return this.baseColor;
   }
}
