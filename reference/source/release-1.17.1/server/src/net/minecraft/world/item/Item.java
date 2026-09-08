package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Item implements ItemLike {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Map<Block, Item> BY_BLOCK = Maps.<Block, Item>newHashMap();
   protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
   public static final int MAX_STACK_SIZE = 64;
   public static final int EAT_DURATION = 32;
   public static final int MAX_BAR_WIDTH = 13;
   protected final CreativeModeTab category;
   private final Rarity rarity;
   private final int maxStackSize;
   private final int maxDamage;
   private final boolean isFireResistant;
   private final Item craftingRemainingItem;
   @Nullable
   private String descriptionId;
   @Nullable
   private final FoodProperties foodProperties;

   public static int getId(Item var0) {
      return â˜ƒ == null ? 0 : Registry.ITEM.getId(â˜ƒ);
   }

   public static Item byId(int var0) {
      return Registry.ITEM.byId(â˜ƒ);
   }

   @Deprecated
   public static Item byBlock(Block var0) {
      return (Item)BY_BLOCK.getOrDefault(â˜ƒ, Items.AIR);
   }

   public Item(Item.Properties var1) {
      this.category = â˜ƒ.category;
      this.rarity = â˜ƒ.rarity;
      this.craftingRemainingItem = â˜ƒ.craftingRemainingItem;
      this.maxDamage = â˜ƒ.maxDamage;
      this.maxStackSize = â˜ƒ.maxStackSize;
      this.foodProperties = â˜ƒ.foodProperties;
      this.isFireResistant = â˜ƒ.isFireResistant;
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         String â˜ƒ = this.getClass().getSimpleName();
         if (!â˜ƒ.endsWith("Item")) {
            LOGGER.error("Item classes should end with Item and {} doesn't.", â˜ƒ);
         }
      }
   }

   public void onUseTick(Level var1, LivingEntity var2, ItemStack var3, int var4) {
   }

   public void onDestroyed(ItemEntity var1) {
   }

   public void verifyTagAfterLoad(CompoundTag var1) {
   }

   public boolean canAttackBlock(BlockState var1, Level var2, BlockPos var3, Player var4) {
      return true;
   }

   @Override
   public Item asItem() {
      return this;
   }

   public InteractionResult useOn(UseOnContext var1) {
      return InteractionResult.PASS;
   }

   public float getDestroySpeed(ItemStack var1, BlockState var2) {
      return 1.0F;
   }

   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      if (this.isEdible()) {
         ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
         if (â˜ƒ.canEat(this.getFoodProperties().canAlwaysEat())) {
            â˜ƒ.startUsingItem(â˜ƒ);
            return InteractionResultHolder.consume(â˜ƒ);
         } else {
            return InteractionResultHolder.fail(â˜ƒ);
         }
      } else {
         return InteractionResultHolder.pass(â˜ƒ.getItemInHand(â˜ƒ));
      }
   }

   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      return this.isEdible() ? â˜ƒ.eat(â˜ƒ, â˜ƒ) : â˜ƒ;
   }

   public final int getMaxStackSize() {
      return this.maxStackSize;
   }

   public final int getMaxDamage() {
      return this.maxDamage;
   }

   public boolean canBeDepleted() {
      return this.maxDamage > 0;
   }

   public boolean isBarVisible(ItemStack var1) {
      return â˜ƒ.isDamaged();
   }

   public int getBarWidth(ItemStack var1) {
      return Math.round(13.0F - (float)â˜ƒ.getDamageValue() * 13.0F / (float)this.maxDamage);
   }

   public int getBarColor(ItemStack var1) {
      float â˜ƒ = Math.max(0.0F, ((float)this.maxDamage - (float)â˜ƒ.getDamageValue()) / (float)this.maxDamage);
      return Mth.hsvToRgb(â˜ƒ / 3.0F, 1.0F, 1.0F);
   }

   public boolean overrideStackedOnOther(ItemStack var1, Slot var2, ClickAction var3, Player var4) {
      return false;
   }

   public boolean overrideOtherStackedOnMe(ItemStack var1, ItemStack var2, Slot var3, ClickAction var4, Player var5, SlotAccess var6) {
      return false;
   }

   public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
      return false;
   }

   public boolean mineBlock(ItemStack var1, Level var2, BlockState var3, BlockPos var4, LivingEntity var5) {
      return false;
   }

   public boolean isCorrectToolForDrops(BlockState var1) {
      return false;
   }

   public InteractionResult interactLivingEntity(ItemStack var1, Player var2, LivingEntity var3, InteractionHand var4) {
      return InteractionResult.PASS;
   }

   public Component getDescription() {
      return new TranslatableComponent(this.getDescriptionId());
   }

   public String toString() {
      return Registry.ITEM.getKey(this).getPath();
   }

   protected String getOrCreateDescriptionId() {
      if (this.descriptionId == null) {
         this.descriptionId = Util.makeDescriptionId("item", Registry.ITEM.getKey(this));
      }

      return this.descriptionId;
   }

   public String getDescriptionId() {
      return this.getOrCreateDescriptionId();
   }

   public String getDescriptionId(ItemStack var1) {
      return this.getDescriptionId();
   }

   public boolean shouldOverrideMultiplayerNbt() {
      return true;
   }

   @Nullable
   public final Item getCraftingRemainingItem() {
      return this.craftingRemainingItem;
   }

   public boolean hasCraftingRemainingItem() {
      return this.craftingRemainingItem != null;
   }

   public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
   }

   public void onCraftedBy(ItemStack var1, Level var2, Player var3) {
   }

   public boolean isComplex() {
      return false;
   }

   public UseAnim getUseAnimation(ItemStack var1) {
      return â˜ƒ.getItem().isEdible() ? UseAnim.EAT : UseAnim.NONE;
   }

   public int getUseDuration(ItemStack var1) {
      if (â˜ƒ.getItem().isEdible()) {
         return this.getFoodProperties().isFastFood() ? 16 : 32;
      } else {
         return 0;
      }
   }

   public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
   }

   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
   }

   public Optional<TooltipComponent> getTooltipImage(ItemStack var1) {
      return Optional.empty();
   }

   public Component getName(ItemStack var1) {
      return new TranslatableComponent(this.getDescriptionId(â˜ƒ));
   }

   public boolean isFoil(ItemStack var1) {
      return â˜ƒ.isEnchanted();
   }

   public Rarity getRarity(ItemStack var1) {
      if (!â˜ƒ.isEnchanted()) {
         return this.rarity;
      } else {
         switch(this.rarity) {
            case COMMON:
            case UNCOMMON:
               return Rarity.RARE;
            case RARE:
               return Rarity.EPIC;
            case EPIC:
            default:
               return this.rarity;
         }
      }
   }

   public boolean isEnchantable(ItemStack var1) {
      return this.getMaxStackSize() == 1 && this.canBeDepleted();
   }

   protected static BlockHitResult getPlayerPOVHitResult(Level var0, Player var1, ClipContext.Fluid var2) {
      float â˜ƒ = â˜ƒ.getXRot();
      float â˜ƒx = â˜ƒ.getYRot();
      Vec3 â˜ƒxx = â˜ƒ.getEyePosition();
      float â˜ƒxxx = Mth.cos(-â˜ƒx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float â˜ƒxxxx = Mth.sin(-â˜ƒx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float â˜ƒxxxxx = -Mth.cos(-â˜ƒ * (float) (Math.PI / 180.0));
      float â˜ƒxxxxxx = Mth.sin(-â˜ƒ * (float) (Math.PI / 180.0));
      float â˜ƒxxxxxxx = â˜ƒxxxx * â˜ƒxxxxx;
      float â˜ƒxxxxxxxx = â˜ƒxxx * â˜ƒxxxxx;
      double â˜ƒxxxxxxxxx = 5.0;
      Vec3 â˜ƒxxxxxxxxxx = â˜ƒxx.add((double)â˜ƒxxxxxxx * 5.0, (double)â˜ƒxxxxxx * 5.0, (double)â˜ƒxxxxxxxx * 5.0);
      return â˜ƒ.clip(new ClipContext(â˜ƒxx, â˜ƒxxxxxxxxxx, ClipContext.Block.OUTLINE, â˜ƒ, â˜ƒ));
   }

   public int getEnchantmentValue() {
      return 0;
   }

   public void fillItemCategory(CreativeModeTab var1, NonNullList<ItemStack> var2) {
      if (this.allowdedIn(â˜ƒ)) {
         â˜ƒ.add(new ItemStack(this));
      }
   }

   protected boolean allowdedIn(CreativeModeTab var1) {
      CreativeModeTab â˜ƒ = this.getItemCategory();
      return â˜ƒ != null && (â˜ƒ == CreativeModeTab.TAB_SEARCH || â˜ƒ == â˜ƒ);
   }

   @Nullable
   public final CreativeModeTab getItemCategory() {
      return this.category;
   }

   public boolean isValidRepairItem(ItemStack var1, ItemStack var2) {
      return false;
   }

   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot var1) {
      return ImmutableMultimap.of();
   }

   public boolean useOnRelease(ItemStack var1) {
      return false;
   }

   public ItemStack getDefaultInstance() {
      return new ItemStack(this);
   }

   public boolean isEdible() {
      return this.foodProperties != null;
   }

   @Nullable
   public FoodProperties getFoodProperties() {
      return this.foodProperties;
   }

   public SoundEvent getDrinkingSound() {
      return SoundEvents.GENERIC_DRINK;
   }

   public SoundEvent getEatingSound() {
      return SoundEvents.GENERIC_EAT;
   }

   public boolean isFireResistant() {
      return this.isFireResistant;
   }

   public boolean canBeHurtBy(DamageSource var1) {
      return !this.isFireResistant || !â˜ƒ.isFire();
   }

   @Nullable
   public SoundEvent getEquipSound() {
      return null;
   }

   public boolean canFitInsideContainerItems() {
      return true;
   }

   public static class Properties {
      int maxStackSize = 64;
      int maxDamage;
      Item craftingRemainingItem;
      CreativeModeTab category;
      Rarity rarity = Rarity.COMMON;
      FoodProperties foodProperties;
      boolean isFireResistant;

      public Item.Properties food(FoodProperties var1) {
         this.foodProperties = â˜ƒ;
         return this;
      }

      public Item.Properties stacksTo(int var1) {
         if (this.maxDamage > 0) {
            throw new RuntimeException("Unable to have damage AND stack.");
         } else {
            this.maxStackSize = â˜ƒ;
            return this;
         }
      }

      public Item.Properties defaultDurability(int var1) {
         return this.maxDamage == 0 ? this.durability(â˜ƒ) : this;
      }

      public Item.Properties durability(int var1) {
         this.maxDamage = â˜ƒ;
         this.maxStackSize = 1;
         return this;
      }

      public Item.Properties craftRemainder(Item var1) {
         this.craftingRemainingItem = â˜ƒ;
         return this;
      }

      public Item.Properties tab(CreativeModeTab var1) {
         this.category = â˜ƒ;
         return this;
      }

      public Item.Properties rarity(Rarity var1) {
         this.rarity = â˜ƒ;
         return this;
      }

      public Item.Properties fireResistant() {
         this.isFireResistant = true;
         return this;
      }
   }
}
