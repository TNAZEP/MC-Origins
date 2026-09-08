package net.minecraft.world.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ItemStack {
   public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Registry.ITEM.fieldOf("id").forGetter(var0x -> var0x.item),
               Codec.INT.fieldOf("Count").forGetter(var0x -> var0x.count),
               CompoundTag.CODEC.optionalFieldOf("tag").forGetter(var0x -> Optional.ofNullable(var0x.tag))
            )
            .apply(var0, ItemStack::new)
   );
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ItemStack EMPTY = new ItemStack((Item)null);
   public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(
      new DecimalFormat("#.##"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   public static final String TAG_ENCH = "Enchantments";
   public static final String TAG_DISPLAY = "display";
   public static final String TAG_DISPLAY_NAME = "Name";
   public static final String TAG_LORE = "Lore";
   public static final String TAG_DAMAGE = "Damage";
   public static final String TAG_COLOR = "color";
   private static final String TAG_UNBREAKABLE = "Unbreakable";
   private static final String TAG_REPAIR_COST = "RepairCost";
   private static final String TAG_CAN_DESTROY_BLOCK_LIST = "CanDestroy";
   private static final String TAG_CAN_PLACE_ON_BLOCK_LIST = "CanPlaceOn";
   private static final String TAG_HIDE_FLAGS = "HideFlags";
   private static final int DONT_HIDE_TOOLTIP = 0;
   private static final Style LORE_STYLE = Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withItalic(true);
   private int count;
   private int popTime;
   @Deprecated
   private final Item item;
   private CompoundTag tag;
   private boolean emptyCacheFlag;
   private Entity entityRepresentation;
   private BlockInWorld cachedBreakBlock;
   private boolean cachedBreakBlockResult;
   private BlockInWorld cachedPlaceBlock;
   private boolean cachedPlaceBlockResult;

   public Optional<TooltipComponent> getTooltipImage() {
      return this.getItem().getTooltipImage(this);
   }

   public ItemStack(ItemLike var1) {
      this(â˜ƒ, 1);
   }

   private ItemStack(ItemLike var1, int var2, Optional<CompoundTag> var3) {
      this(â˜ƒ, â˜ƒ);
      â˜ƒ.ifPresent(this::setTag);
   }

   public ItemStack(ItemLike var1, int var2) {
      this.item = â˜ƒ == null ? null : â˜ƒ.asItem();
      this.count = â˜ƒ;
      if (this.item != null && this.item.canBeDepleted()) {
         this.setDamageValue(this.getDamageValue());
      }

      this.updateEmptyCacheFlag();
   }

   private void updateEmptyCacheFlag() {
      this.emptyCacheFlag = false;
      this.emptyCacheFlag = this.isEmpty();
   }

   private ItemStack(CompoundTag var1) {
      this.item = Registry.ITEM.get(new ResourceLocation(â˜ƒ.getString("id")));
      this.count = â˜ƒ.getByte("Count");
      if (â˜ƒ.contains("tag", 10)) {
         this.tag = â˜ƒ.getCompound("tag");
         this.getItem().verifyTagAfterLoad(this.tag);
      }

      if (this.getItem().canBeDepleted()) {
         this.setDamageValue(this.getDamageValue());
      }

      this.updateEmptyCacheFlag();
   }

   public static ItemStack of(CompoundTag var0) {
      try {
         return new ItemStack(â˜ƒ);
      } catch (RuntimeException var2) {
         LOGGER.debug("Tried to load invalid item: {}", â˜ƒ, var2);
         return EMPTY;
      }
   }

   public boolean isEmpty() {
      if (this == EMPTY) {
         return true;
      } else if (this.getItem() == null || this.is(Items.AIR)) {
         return true;
      } else {
         return this.count <= 0;
      }
   }

   public ItemStack split(int var1) {
      int â˜ƒ = Math.min(â˜ƒ, this.count);
      ItemStack â˜ƒx = this.copy();
      â˜ƒx.setCount(â˜ƒ);
      this.shrink(â˜ƒ);
      return â˜ƒx;
   }

   public Item getItem() {
      return this.emptyCacheFlag ? Items.AIR : this.item;
   }

   public boolean is(Tag<Item> var1) {
      return â˜ƒ.contains(this.getItem());
   }

   public boolean is(Item var1) {
      return this.getItem() == â˜ƒ;
   }

   public InteractionResult useOn(UseOnContext var1) {
      Player â˜ƒ = â˜ƒ.getPlayer();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockInWorld â˜ƒxx = new BlockInWorld(â˜ƒ.getLevel(), â˜ƒx, false);
      if (â˜ƒ != null && !â˜ƒ.getAbilities().mayBuild && !this.hasAdventureModePlaceTagForBlock(â˜ƒ.getLevel().getTagManager(), â˜ƒxx)) {
         return InteractionResult.PASS;
      } else {
         Item â˜ƒ = this.getItem();
         InteractionResult â˜ƒx = â˜ƒ.useOn(â˜ƒ);
         if (â˜ƒ != null && â˜ƒx.shouldAwardStats()) {
            â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒ));
         }

         return â˜ƒx;
      }
   }

   public float getDestroySpeed(BlockState var1) {
      return this.getItem().getDestroySpeed(this, â˜ƒ);
   }

   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      return this.getItem().use(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ItemStack finishUsingItem(Level var1, LivingEntity var2) {
      return this.getItem().finishUsingItem(this, â˜ƒ, â˜ƒ);
   }

   public CompoundTag save(CompoundTag var1) {
      ResourceLocation â˜ƒ = Registry.ITEM.getKey(this.getItem());
      â˜ƒ.putString("id", â˜ƒ == null ? "minecraft:air" : â˜ƒ.toString());
      â˜ƒ.putByte("Count", (byte)this.count);
      if (this.tag != null) {
         â˜ƒ.put("tag", this.tag.copy());
      }

      return â˜ƒ;
   }

   public int getMaxStackSize() {
      return this.getItem().getMaxStackSize();
   }

   public boolean isStackable() {
      return this.getMaxStackSize() > 1 && (!this.isDamageableItem() || !this.isDamaged());
   }

   public boolean isDamageableItem() {
      if (!this.emptyCacheFlag && this.getItem().getMaxDamage() > 0) {
         CompoundTag â˜ƒ = this.getTag();
         return â˜ƒ == null || !â˜ƒ.getBoolean("Unbreakable");
      } else {
         return false;
      }
   }

   public boolean isDamaged() {
      return this.isDamageableItem() && this.getDamageValue() > 0;
   }

   public int getDamageValue() {
      return this.tag == null ? 0 : this.tag.getInt("Damage");
   }

   public void setDamageValue(int var1) {
      this.getOrCreateTag().putInt("Damage", Math.max(0, â˜ƒ));
   }

   public int getMaxDamage() {
      return this.getItem().getMaxDamage();
   }

   public boolean hurt(int var1, Random var2, @Nullable ServerPlayer var3) {
      if (!this.isDamageableItem()) {
         return false;
      } else {
         if (â˜ƒ > 0) {
            int â˜ƒ = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, this);
            int â˜ƒx = 0;

            for(int â˜ƒxx = 0; â˜ƒ > 0 && â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
               if (DigDurabilityEnchantment.shouldIgnoreDurabilityDrop(this, â˜ƒ, â˜ƒ)) {
                  ++â˜ƒx;
               }
            }

            â˜ƒ -= â˜ƒx;
            if (â˜ƒ <= 0) {
               return false;
            }
         }

         if (â˜ƒ != null && â˜ƒ != 0) {
            CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(â˜ƒ, this, this.getDamageValue() + â˜ƒ);
         }

         int â˜ƒ = this.getDamageValue() + â˜ƒ;
         this.setDamageValue(â˜ƒ);
         return â˜ƒ >= this.getMaxDamage();
      }
   }

   public <T extends LivingEntity> void hurtAndBreak(int var1, T var2, Consumer<T> var3) {
      if (!â˜ƒ.level.isClientSide && (!(â˜ƒ instanceof Player) || !((Player)â˜ƒ).getAbilities().instabuild)) {
         if (this.isDamageableItem()) {
            if (this.hurt(â˜ƒ, â˜ƒ.getRandom(), â˜ƒ instanceof ServerPlayer ? (ServerPlayer)â˜ƒ : null)) {
               â˜ƒ.accept(â˜ƒ);
               Item â˜ƒ = this.getItem();
               this.shrink(1);
               if (â˜ƒ instanceof Player) {
                  ((Player)â˜ƒ).awardStat(Stats.ITEM_BROKEN.get(â˜ƒ));
               }

               this.setDamageValue(0);
            }
         }
      }
   }

   public boolean isBarVisible() {
      return this.item.isBarVisible(this);
   }

   public int getBarWidth() {
      return this.item.getBarWidth(this);
   }

   public int getBarColor() {
      return this.item.getBarColor(this);
   }

   public boolean overrideStackedOnOther(Slot var1, ClickAction var2, Player var3) {
      return this.getItem().overrideStackedOnOther(this, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public boolean overrideOtherStackedOnMe(ItemStack var1, Slot var2, ClickAction var3, Player var4, SlotAccess var5) {
      return this.getItem().overrideOtherStackedOnMe(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void hurtEnemy(LivingEntity var1, Player var2) {
      Item â˜ƒ = this.getItem();
      if (â˜ƒ.hurtEnemy(this, â˜ƒ, â˜ƒ)) {
         â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒ));
      }
   }

   public void mineBlock(Level var1, BlockState var2, BlockPos var3, Player var4) {
      Item â˜ƒ = this.getItem();
      if (â˜ƒ.mineBlock(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒ));
      }
   }

   public boolean isCorrectToolForDrops(BlockState var1) {
      return this.getItem().isCorrectToolForDrops(â˜ƒ);
   }

   public InteractionResult interactLivingEntity(Player var1, LivingEntity var2, InteractionHand var3) {
      return this.getItem().interactLivingEntity(this, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ItemStack copy() {
      if (this.isEmpty()) {
         return EMPTY;
      } else {
         ItemStack â˜ƒ = new ItemStack(this.getItem(), this.count);
         â˜ƒ.setPopTime(this.getPopTime());
         if (this.tag != null) {
            â˜ƒ.tag = this.tag.copy();
         }

         return â˜ƒ;
      }
   }

   public static boolean tagMatches(ItemStack var0, ItemStack var1) {
      if (â˜ƒ.isEmpty() && â˜ƒ.isEmpty()) {
         return true;
      } else if (â˜ƒ.isEmpty() || â˜ƒ.isEmpty()) {
         return false;
      } else if (â˜ƒ.tag == null && â˜ƒ.tag != null) {
         return false;
      } else {
         return â˜ƒ.tag == null || â˜ƒ.tag.equals(â˜ƒ.tag);
      }
   }

   public static boolean matches(ItemStack var0, ItemStack var1) {
      if (â˜ƒ.isEmpty() && â˜ƒ.isEmpty()) {
         return true;
      } else {
         return !â˜ƒ.isEmpty() && !â˜ƒ.isEmpty() ? â˜ƒ.matches(â˜ƒ) : false;
      }
   }

   private boolean matches(ItemStack var1) {
      if (this.count != â˜ƒ.count) {
         return false;
      } else if (!this.is(â˜ƒ.getItem())) {
         return false;
      } else if (this.tag == null && â˜ƒ.tag != null) {
         return false;
      } else {
         return this.tag == null || this.tag.equals(â˜ƒ.tag);
      }
   }

   public static boolean isSame(ItemStack var0, ItemStack var1) {
      if (â˜ƒ == â˜ƒ) {
         return true;
      } else {
         return !â˜ƒ.isEmpty() && !â˜ƒ.isEmpty() ? â˜ƒ.sameItem(â˜ƒ) : false;
      }
   }

   public static boolean isSameIgnoreDurability(ItemStack var0, ItemStack var1) {
      if (â˜ƒ == â˜ƒ) {
         return true;
      } else {
         return !â˜ƒ.isEmpty() && !â˜ƒ.isEmpty() ? â˜ƒ.sameItemStackIgnoreDurability(â˜ƒ) : false;
      }
   }

   public boolean sameItem(ItemStack var1) {
      return !â˜ƒ.isEmpty() && this.is(â˜ƒ.getItem());
   }

   public boolean sameItemStackIgnoreDurability(ItemStack var1) {
      if (!this.isDamageableItem()) {
         return this.sameItem(â˜ƒ);
      } else {
         return !â˜ƒ.isEmpty() && this.is(â˜ƒ.getItem());
      }
   }

   public static boolean isSameItemSameTags(ItemStack var0, ItemStack var1) {
      return â˜ƒ.is(â˜ƒ.getItem()) && tagMatches(â˜ƒ, â˜ƒ);
   }

   public String getDescriptionId() {
      return this.getItem().getDescriptionId(this);
   }

   public String toString() {
      return this.count + " " + this.getItem();
   }

   public void inventoryTick(Level var1, Entity var2, int var3, boolean var4) {
      if (this.popTime > 0) {
         --this.popTime;
      }

      if (this.getItem() != null) {
         this.getItem().inventoryTick(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void onCraftedBy(Level var1, Player var2, int var3) {
      â˜ƒ.awardStat(Stats.ITEM_CRAFTED.get(this.getItem()), â˜ƒ);
      this.getItem().onCraftedBy(this, â˜ƒ, â˜ƒ);
   }

   public int getUseDuration() {
      return this.getItem().getUseDuration(this);
   }

   public UseAnim getUseAnimation() {
      return this.getItem().getUseAnimation(this);
   }

   public void releaseUsing(Level var1, LivingEntity var2, int var3) {
      this.getItem().releaseUsing(this, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public boolean useOnRelease() {
      return this.getItem().useOnRelease(this);
   }

   public boolean hasTag() {
      return !this.emptyCacheFlag && this.tag != null && !this.tag.isEmpty();
   }

   @Nullable
   public CompoundTag getTag() {
      return this.tag;
   }

   public CompoundTag getOrCreateTag() {
      if (this.tag == null) {
         this.setTag(new CompoundTag());
      }

      return this.tag;
   }

   public CompoundTag getOrCreateTagElement(String var1) {
      if (this.tag != null && this.tag.contains(â˜ƒ, 10)) {
         return this.tag.getCompound(â˜ƒ);
      } else {
         CompoundTag â˜ƒ = new CompoundTag();
         this.addTagElement(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }
   }

   @Nullable
   public CompoundTag getTagElement(String var1) {
      return this.tag != null && this.tag.contains(â˜ƒ, 10) ? this.tag.getCompound(â˜ƒ) : null;
   }

   public void removeTagKey(String var1) {
      if (this.tag != null && this.tag.contains(â˜ƒ)) {
         this.tag.remove(â˜ƒ);
         if (this.tag.isEmpty()) {
            this.tag = null;
         }
      }
   }

   public ListTag getEnchantmentTags() {
      return this.tag != null ? this.tag.getList("Enchantments", 10) : new ListTag();
   }

   public void setTag(@Nullable CompoundTag var1) {
      this.tag = â˜ƒ;
      if (this.getItem().canBeDepleted()) {
         this.setDamageValue(this.getDamageValue());
      }

      if (â˜ƒ != null) {
         this.getItem().verifyTagAfterLoad(â˜ƒ);
      }
   }

   public Component getHoverName() {
      CompoundTag â˜ƒ = this.getTagElement("display");
      if (â˜ƒ != null && â˜ƒ.contains("Name", 8)) {
         try {
            Component â˜ƒx = Component.Serializer.fromJson(â˜ƒ.getString("Name"));
            if (â˜ƒx != null) {
               return â˜ƒx;
            }

            â˜ƒ.remove("Name");
         } catch (JsonParseException var3) {
            â˜ƒ.remove("Name");
         }
      }

      return this.getItem().getName(this);
   }

   public ItemStack setHoverName(@Nullable Component var1) {
      CompoundTag â˜ƒ = this.getOrCreateTagElement("display");
      if (â˜ƒ != null) {
         â˜ƒ.putString("Name", Component.Serializer.toJson(â˜ƒ));
      } else {
         â˜ƒ.remove("Name");
      }

      return this;
   }

   public void resetHoverName() {
      CompoundTag â˜ƒ = this.getTagElement("display");
      if (â˜ƒ != null) {
         â˜ƒ.remove("Name");
         if (â˜ƒ.isEmpty()) {
            this.removeTagKey("display");
         }
      }

      if (this.tag != null && this.tag.isEmpty()) {
         this.tag = null;
      }
   }

   public boolean hasCustomHoverName() {
      CompoundTag â˜ƒ = this.getTagElement("display");
      return â˜ƒ != null && â˜ƒ.contains("Name", 8);
   }

   public List<Component> getTooltipLines(@Nullable Player var1, TooltipFlag var2) {
      List<Component> â˜ƒ = Lists.<Component>newArrayList();
      MutableComponent â˜ƒx = new TextComponent("").append(this.getHoverName()).withStyle(this.getRarity().color);
      if (this.hasCustomHoverName()) {
         â˜ƒx.withStyle(ChatFormatting.ITALIC);
      }

      â˜ƒ.add(â˜ƒx);
      if (!â˜ƒ.isAdvanced() && !this.hasCustomHoverName() && this.is(Items.FILLED_MAP)) {
         Integer â˜ƒ = MapItem.getMapId(this);
         if (â˜ƒ != null) {
            â˜ƒ.add(new TextComponent("#" + â˜ƒ).withStyle(ChatFormatting.GRAY));
         }
      }

      int â˜ƒ = this.getHideFlags();
      if (shouldShowInTooltip(â˜ƒ, ItemStack.TooltipPart.ADDITIONAL)) {
         this.getItem().appendHoverText(this, â˜ƒ == null ? null : â˜ƒ.level, â˜ƒ, â˜ƒ);
      }

      if (this.hasTag()) {
         if (shouldShowInTooltip(â˜ƒ, ItemStack.TooltipPart.ENCHANTMENTS)) {
            appendEnchantmentNames(â˜ƒ, this.getEnchantmentTags());
         }

         if (this.tag.contains("display", 10)) {
            CompoundTag â˜ƒ = this.tag.getCompound("display");
            if (shouldShowInTooltip(â˜ƒ, ItemStack.TooltipPart.DYE) && â˜ƒ.contains("color", 99)) {
               if (â˜ƒ.isAdvanced()) {
                  â˜ƒ.add(new TranslatableComponent("item.color", String.format("#%06X", â˜ƒ.getInt("color"))).withStyle(ChatFormatting.GRAY));
               } else {
                  â˜ƒ.add(new TranslatableComponent("item.dyed").withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
               }
            }

            if (â˜ƒ.getTagType("Lore") == 9) {
               ListTag â˜ƒ = â˜ƒ.getList("Lore", 8);

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
                  String â˜ƒxx = â˜ƒ.getString(â˜ƒx);

                  try {
                     MutableComponent â˜ƒxxx = Component.Serializer.fromJson(â˜ƒxx);
                     if (â˜ƒxxx != null) {
                        â˜ƒ.add(ComponentUtils.mergeStyles(â˜ƒxxx, LORE_STYLE));
                     }
                  } catch (JsonParseException var19) {
                     â˜ƒ.remove("Lore");
                  }
               }
            }
         }
      }

      if (shouldShowInTooltip(â˜ƒ, ItemStack.TooltipPart.MODIFIERS)) {
         for(EquipmentSlot â˜ƒ : EquipmentSlot.values()) {
            Multimap<Attribute, AttributeModifier> â˜ƒx = this.getAttributeModifiers(â˜ƒ);
            if (!â˜ƒx.isEmpty()) {
               â˜ƒ.add(TextComponent.EMPTY);
               â˜ƒ.add(new TranslatableComponent("item.modifiers." + â˜ƒ.getName()).withStyle(ChatFormatting.GRAY));

               for(Entry<Attribute, AttributeModifier> â˜ƒxx : â˜ƒx.entries()) {
                  AttributeModifier â˜ƒxxx = (AttributeModifier)â˜ƒxx.getValue();
                  double â˜ƒxxxx = â˜ƒxxx.getAmount();
                  boolean â˜ƒxxxxx = false;
                  if (â˜ƒ != null) {
                     if (â˜ƒxxx.getId() == Item.BASE_ATTACK_DAMAGE_UUID) {
                        â˜ƒxxxx += â˜ƒ.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                        â˜ƒxxxx += (double)EnchantmentHelper.getDamageBonus(this, MobType.UNDEFINED);
                        â˜ƒxxxxx = true;
                     } else if (â˜ƒxxx.getId() == Item.BASE_ATTACK_SPEED_UUID) {
                        â˜ƒxxxx += â˜ƒ.getAttributeBaseValue(Attributes.ATTACK_SPEED);
                        â˜ƒxxxxx = true;
                     }
                  }

                  double â˜ƒxxx;
                  if (â˜ƒxxx.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE || â˜ƒxxx.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL
                     )
                   {
                     â˜ƒxxx = â˜ƒxxxx * 100.0;
                  } else if (((Attribute)â˜ƒxx.getKey()).equals(Attributes.KNOCKBACK_RESISTANCE)) {
                     â˜ƒxxx = â˜ƒxxxx * 10.0;
                  } else {
                     â˜ƒxxx = â˜ƒxxxx;
                  }

                  if (â˜ƒxxxxx) {
                     â˜ƒ.add(
                        new TextComponent(" ")
                           .append(
                              new TranslatableComponent(
                                 "attribute.modifier.equals." + â˜ƒxxx.getOperation().toValue(),
                                 ATTRIBUTE_MODIFIER_FORMAT.format(â˜ƒxxx),
                                 new TranslatableComponent(((Attribute)â˜ƒxx.getKey()).getDescriptionId())
                              )
                           )
                           .withStyle(ChatFormatting.DARK_GREEN)
                     );
                  } else if (â˜ƒxxxx > 0.0) {
                     â˜ƒ.add(
                        new TranslatableComponent(
                              "attribute.modifier.plus." + â˜ƒxxx.getOperation().toValue(),
                              ATTRIBUTE_MODIFIER_FORMAT.format(â˜ƒxxx),
                              new TranslatableComponent(((Attribute)â˜ƒxx.getKey()).getDescriptionId())
                           )
                           .withStyle(ChatFormatting.BLUE)
                     );
                  } else if (â˜ƒxxxx < 0.0) {
                     â˜ƒxxx *= -1.0;
                     â˜ƒ.add(
                        new TranslatableComponent(
                              "attribute.modifier.take." + â˜ƒxxx.getOperation().toValue(),
                              ATTRIBUTE_MODIFIER_FORMAT.format(â˜ƒxxx),
                              new TranslatableComponent(((Attribute)â˜ƒxx.getKey()).getDescriptionId())
                           )
                           .withStyle(ChatFormatting.RED)
                     );
                  }
               }
            }
         }
      }

      if (this.hasTag()) {
         if (shouldShowInTooltip(â˜ƒ, ItemStack.TooltipPart.UNBREAKABLE) && this.tag.getBoolean("Unbreakable")) {
            â˜ƒ.add(new TranslatableComponent("item.unbreakable").withStyle(ChatFormatting.BLUE));
         }

         if (shouldShowInTooltip(â˜ƒ, ItemStack.TooltipPart.CAN_DESTROY) && this.tag.contains("CanDestroy", 9)) {
            ListTag â˜ƒ = this.tag.getList("CanDestroy", 8);
            if (!â˜ƒ.isEmpty()) {
               â˜ƒ.add(TextComponent.EMPTY);
               â˜ƒ.add(new TranslatableComponent("item.canBreak").withStyle(ChatFormatting.GRAY));

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
                  â˜ƒ.addAll(expandBlockState(â˜ƒ.getString(â˜ƒx)));
               }
            }
         }

         if (shouldShowInTooltip(â˜ƒ, ItemStack.TooltipPart.CAN_PLACE) && this.tag.contains("CanPlaceOn", 9)) {
            ListTag â˜ƒ = this.tag.getList("CanPlaceOn", 8);
            if (!â˜ƒ.isEmpty()) {
               â˜ƒ.add(TextComponent.EMPTY);
               â˜ƒ.add(new TranslatableComponent("item.canPlace").withStyle(ChatFormatting.GRAY));

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
                  â˜ƒ.addAll(expandBlockState(â˜ƒ.getString(â˜ƒx)));
               }
            }
         }
      }

      if (â˜ƒ.isAdvanced()) {
         if (this.isDamaged()) {
            â˜ƒ.add(new TranslatableComponent("item.durability", this.getMaxDamage() - this.getDamageValue(), this.getMaxDamage()));
         }

         â˜ƒ.add(new TextComponent(Registry.ITEM.getKey(this.getItem()).toString()).withStyle(ChatFormatting.DARK_GRAY));
         if (this.hasTag()) {
            â˜ƒ.add(new TranslatableComponent("item.nbt_tags", this.tag.getAllKeys().size()).withStyle(ChatFormatting.DARK_GRAY));
         }
      }

      return â˜ƒ;
   }

   private static boolean shouldShowInTooltip(int var0, ItemStack.TooltipPart var1) {
      return (â˜ƒ & â˜ƒ.getMask()) == 0;
   }

   private int getHideFlags() {
      return this.hasTag() && this.tag.contains("HideFlags", 99) ? this.tag.getInt("HideFlags") : 0;
   }

   public void hideTooltipPart(ItemStack.TooltipPart var1) {
      CompoundTag â˜ƒ = this.getOrCreateTag();
      â˜ƒ.putInt("HideFlags", â˜ƒ.getInt("HideFlags") | â˜ƒ.getMask());
   }

   public static void appendEnchantmentNames(List<Component> var0, ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound(â˜ƒ);
         Registry.ENCHANTMENT
            .getOptional(EnchantmentHelper.getEnchantmentId(â˜ƒx))
            .ifPresent(var2x -> â˜ƒ.add(var2x.getFullname(EnchantmentHelper.getEnchantmentLevel(â˜ƒ))));
      }
   }

   private static Collection<Component> expandBlockState(String var0) {
      try {
         BlockStateParser â˜ƒ = new BlockStateParser(new StringReader(â˜ƒ), true).parse(true);
         BlockState â˜ƒx = â˜ƒ.getState();
         ResourceLocation â˜ƒxx = â˜ƒ.getTag();
         boolean â˜ƒxxx = â˜ƒx != null;
         boolean â˜ƒxxxx = â˜ƒxx != null;
         if (â˜ƒxxx || â˜ƒxxxx) {
            if (â˜ƒxxx) {
               return Lists.<Component>newArrayList(â˜ƒx.getBlock().getName().withStyle(ChatFormatting.DARK_GRAY));
            }

            Tag<Block> â˜ƒxxxxx = BlockTags.getAllTags().getTag(â˜ƒxx);
            if (â˜ƒxxxxx != null) {
               Collection<Block> â˜ƒxxxxxx = â˜ƒxxxxx.getValues();
               if (!â˜ƒxxxxxx.isEmpty()) {
                  return (Collection<Component>)â˜ƒxxxxxx.stream()
                     .map(Block::getName)
                     .map(var0x -> var0x.withStyle(ChatFormatting.DARK_GRAY))
                     .collect(Collectors.toList());
               }
            }
         }
      } catch (CommandSyntaxException var8) {
      }

      return Lists.<Component>newArrayList(new TextComponent("missingno").withStyle(ChatFormatting.DARK_GRAY));
   }

   public boolean hasFoil() {
      return this.getItem().isFoil(this);
   }

   public Rarity getRarity() {
      return this.getItem().getRarity(this);
   }

   public boolean isEnchantable() {
      if (!this.getItem().isEnchantable(this)) {
         return false;
      } else {
         return !this.isEnchanted();
      }
   }

   public void enchant(Enchantment var1, int var2) {
      this.getOrCreateTag();
      if (!this.tag.contains("Enchantments", 9)) {
         this.tag.put("Enchantments", new ListTag());
      }

      ListTag â˜ƒ = this.tag.getList("Enchantments", 10);
      â˜ƒ.add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId(â˜ƒ), (byte)â˜ƒ));
   }

   public boolean isEnchanted() {
      if (this.tag != null && this.tag.contains("Enchantments", 9)) {
         return !this.tag.getList("Enchantments", 10).isEmpty();
      } else {
         return false;
      }
   }

   public void addTagElement(String var1, net.minecraft.nbt.Tag var2) {
      this.getOrCreateTag().put(â˜ƒ, â˜ƒ);
   }

   public boolean isFramed() {
      return this.entityRepresentation instanceof ItemFrame;
   }

   public void setEntityRepresentation(@Nullable Entity var1) {
      this.entityRepresentation = â˜ƒ;
   }

   @Nullable
   public ItemFrame getFrame() {
      return this.entityRepresentation instanceof ItemFrame ? (ItemFrame)this.getEntityRepresentation() : null;
   }

   @Nullable
   public Entity getEntityRepresentation() {
      return !this.emptyCacheFlag ? this.entityRepresentation : null;
   }

   public int getBaseRepairCost() {
      return this.hasTag() && this.tag.contains("RepairCost", 3) ? this.tag.getInt("RepairCost") : 0;
   }

   public void setRepairCost(int var1) {
      this.getOrCreateTag().putInt("RepairCost", â˜ƒ);
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot var1) {
      Multimap<Attribute, AttributeModifier> â˜ƒ;
      if (this.hasTag() && this.tag.contains("AttributeModifiers", 9)) {
         â˜ƒ = HashMultimap.create();
         ListTag â˜ƒx = this.tag.getList("AttributeModifiers", 10);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
            if (!â˜ƒxxx.contains("Slot", 8) || â˜ƒxxx.getString("Slot").equals(â˜ƒ.getName())) {
               Optional<Attribute> â˜ƒxxxx = Registry.ATTRIBUTE.getOptional(ResourceLocation.tryParse(â˜ƒxxx.getString("AttributeName")));
               if (â˜ƒxxxx.isPresent()) {
                  AttributeModifier â˜ƒxxxxx = AttributeModifier.load(â˜ƒxxx);
                  if (â˜ƒxxxxx != null && â˜ƒxxxxx.getId().getLeastSignificantBits() != 0L && â˜ƒxxxxx.getId().getMostSignificantBits() != 0L) {
                     â˜ƒ.put((Attribute)â˜ƒxxxx.get(), â˜ƒxxxxx);
                  }
               }
            }
         }
      } else {
         â˜ƒ = this.getItem().getDefaultAttributeModifiers(â˜ƒ);
      }

      return â˜ƒ;
   }

   public void addAttributeModifier(Attribute var1, AttributeModifier var2, @Nullable EquipmentSlot var3) {
      this.getOrCreateTag();
      if (!this.tag.contains("AttributeModifiers", 9)) {
         this.tag.put("AttributeModifiers", new ListTag());
      }

      ListTag â˜ƒ = this.tag.getList("AttributeModifiers", 10);
      CompoundTag â˜ƒx = â˜ƒ.save();
      â˜ƒx.putString("AttributeName", Registry.ATTRIBUTE.getKey(â˜ƒ).toString());
      if (â˜ƒ != null) {
         â˜ƒx.putString("Slot", â˜ƒ.getName());
      }

      â˜ƒ.add(â˜ƒx);
   }

   public Component getDisplayName() {
      MutableComponent â˜ƒ = new TextComponent("").append(this.getHoverName());
      if (this.hasCustomHoverName()) {
         â˜ƒ.withStyle(ChatFormatting.ITALIC);
      }

      MutableComponent â˜ƒ = ComponentUtils.wrapInSquareBrackets(â˜ƒ);
      if (!this.emptyCacheFlag) {
         â˜ƒ.withStyle(this.getRarity().color)
            .withStyle(var1x -> var1x.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(this))));
      }

      return â˜ƒ;
   }

   private static boolean areSameBlocks(BlockInWorld var0, @Nullable BlockInWorld var1) {
      if (â˜ƒ == null || â˜ƒ.getState() != â˜ƒ.getState()) {
         return false;
      } else if (â˜ƒ.getEntity() == null && â˜ƒ.getEntity() == null) {
         return true;
      } else {
         return â˜ƒ.getEntity() != null && â˜ƒ.getEntity() != null
            ? Objects.equals(â˜ƒ.getEntity().save(new CompoundTag()), â˜ƒ.getEntity().save(new CompoundTag()))
            : false;
      }
   }

   public boolean hasAdventureModeBreakTagForBlock(TagContainer var1, BlockInWorld var2) {
      if (areSameBlocks(â˜ƒ, this.cachedBreakBlock)) {
         return this.cachedBreakBlockResult;
      } else {
         this.cachedBreakBlock = â˜ƒ;
         if (this.hasTag() && this.tag.contains("CanDestroy", 9)) {
            ListTag â˜ƒ = this.tag.getList("CanDestroy", 8);

            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
               String â˜ƒxx = â˜ƒ.getString(â˜ƒx);

               try {
                  Predicate<BlockInWorld> â˜ƒxxx = BlockPredicateArgument.blockPredicate().parse(new StringReader(â˜ƒxx)).create(â˜ƒ);
                  if (â˜ƒxxx.test(â˜ƒ)) {
                     this.cachedBreakBlockResult = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.cachedBreakBlockResult = false;
         return false;
      }
   }

   public boolean hasAdventureModePlaceTagForBlock(TagContainer var1, BlockInWorld var2) {
      if (areSameBlocks(â˜ƒ, this.cachedPlaceBlock)) {
         return this.cachedPlaceBlockResult;
      } else {
         this.cachedPlaceBlock = â˜ƒ;
         if (this.hasTag() && this.tag.contains("CanPlaceOn", 9)) {
            ListTag â˜ƒ = this.tag.getList("CanPlaceOn", 8);

            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
               String â˜ƒxx = â˜ƒ.getString(â˜ƒx);

               try {
                  Predicate<BlockInWorld> â˜ƒxxx = BlockPredicateArgument.blockPredicate().parse(new StringReader(â˜ƒxx)).create(â˜ƒ);
                  if (â˜ƒxxx.test(â˜ƒ)) {
                     this.cachedPlaceBlockResult = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.cachedPlaceBlockResult = false;
         return false;
      }
   }

   public int getPopTime() {
      return this.popTime;
   }

   public void setPopTime(int var1) {
      this.popTime = â˜ƒ;
   }

   public int getCount() {
      return this.emptyCacheFlag ? 0 : this.count;
   }

   public void setCount(int var1) {
      this.count = â˜ƒ;
      this.updateEmptyCacheFlag();
   }

   public void grow(int var1) {
      this.setCount(this.count + â˜ƒ);
   }

   public void shrink(int var1) {
      this.grow(-â˜ƒ);
   }

   public void onUseTick(Level var1, LivingEntity var2, int var3) {
      this.getItem().onUseTick(â˜ƒ, â˜ƒ, this, â˜ƒ);
   }

   public void onDestroyed(ItemEntity var1) {
      this.getItem().onDestroyed(â˜ƒ);
   }

   public boolean isEdible() {
      return this.getItem().isEdible();
   }

   public SoundEvent getDrinkingSound() {
      return this.getItem().getDrinkingSound();
   }

   public SoundEvent getEatingSound() {
      return this.getItem().getEatingSound();
   }

   @Nullable
   public SoundEvent getEquipSound() {
      return this.getItem().getEquipSound();
   }

   public static enum TooltipPart {
      ENCHANTMENTS,
      MODIFIERS,
      UNBREAKABLE,
      CAN_DESTROY,
      CAN_PLACE,
      ADDITIONAL,
      DYE;

      private final int mask = 1 << this.ordinal();

      public int getMask() {
         return this.mask;
      }
   }
}
