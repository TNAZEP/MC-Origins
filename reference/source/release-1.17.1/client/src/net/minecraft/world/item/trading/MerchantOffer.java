package net.minecraft.world.item.trading;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class MerchantOffer {
   private final ItemStack baseCostA;
   private final ItemStack costB;
   private final ItemStack result;
   private int uses;
   private final int maxUses;
   private boolean rewardExp = true;
   private int specialPriceDiff;
   private int demand;
   private float priceMultiplier;
   private int xp = 1;

   public MerchantOffer(CompoundTag var1) {
      this.baseCostA = ItemStack.of(â˜ƒ.getCompound("buy"));
      this.costB = ItemStack.of(â˜ƒ.getCompound("buyB"));
      this.result = ItemStack.of(â˜ƒ.getCompound("sell"));
      this.uses = â˜ƒ.getInt("uses");
      if (â˜ƒ.contains("maxUses", 99)) {
         this.maxUses = â˜ƒ.getInt("maxUses");
      } else {
         this.maxUses = 4;
      }

      if (â˜ƒ.contains("rewardExp", 1)) {
         this.rewardExp = â˜ƒ.getBoolean("rewardExp");
      }

      if (â˜ƒ.contains("xp", 3)) {
         this.xp = â˜ƒ.getInt("xp");
      }

      if (â˜ƒ.contains("priceMultiplier", 5)) {
         this.priceMultiplier = â˜ƒ.getFloat("priceMultiplier");
      }

      this.specialPriceDiff = â˜ƒ.getInt("specialPrice");
      this.demand = â˜ƒ.getInt("demand");
   }

   public MerchantOffer(ItemStack var1, ItemStack var2, int var3, int var4, float var5) {
      this(â˜ƒ, ItemStack.EMPTY, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public MerchantOffer(ItemStack var1, ItemStack var2, ItemStack var3, int var4, int var5, float var6) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, 0, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public MerchantOffer(ItemStack var1, ItemStack var2, ItemStack var3, int var4, int var5, int var6, float var7) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0);
   }

   public MerchantOffer(ItemStack var1, ItemStack var2, ItemStack var3, int var4, int var5, int var6, float var7, int var8) {
      this.baseCostA = â˜ƒ;
      this.costB = â˜ƒ;
      this.result = â˜ƒ;
      this.uses = â˜ƒ;
      this.maxUses = â˜ƒ;
      this.xp = â˜ƒ;
      this.priceMultiplier = â˜ƒ;
      this.demand = â˜ƒ;
   }

   public ItemStack getBaseCostA() {
      return this.baseCostA;
   }

   public ItemStack getCostA() {
      int â˜ƒ = this.baseCostA.getCount();
      ItemStack â˜ƒx = this.baseCostA.copy();
      int â˜ƒxx = Math.max(0, Mth.floor((float)(â˜ƒ * this.demand) * this.priceMultiplier));
      â˜ƒx.setCount(Mth.clamp(â˜ƒ + â˜ƒxx + this.specialPriceDiff, 1, this.baseCostA.getItem().getMaxStackSize()));
      return â˜ƒx;
   }

   public ItemStack getCostB() {
      return this.costB;
   }

   public ItemStack getResult() {
      return this.result;
   }

   public void updateDemand() {
      this.demand = this.demand + this.uses - (this.maxUses - this.uses);
   }

   public ItemStack assemble() {
      return this.result.copy();
   }

   public int getUses() {
      return this.uses;
   }

   public void resetUses() {
      this.uses = 0;
   }

   public int getMaxUses() {
      return this.maxUses;
   }

   public void increaseUses() {
      ++this.uses;
   }

   public int getDemand() {
      return this.demand;
   }

   public void addToSpecialPriceDiff(int var1) {
      this.specialPriceDiff += â˜ƒ;
   }

   public void resetSpecialPriceDiff() {
      this.specialPriceDiff = 0;
   }

   public int getSpecialPriceDiff() {
      return this.specialPriceDiff;
   }

   public void setSpecialPriceDiff(int var1) {
      this.specialPriceDiff = â˜ƒ;
   }

   public float getPriceMultiplier() {
      return this.priceMultiplier;
   }

   public int getXp() {
      return this.xp;
   }

   public boolean isOutOfStock() {
      return this.uses >= this.maxUses;
   }

   public void setToOutOfStock() {
      this.uses = this.maxUses;
   }

   public boolean needsRestock() {
      return this.uses > 0;
   }

   public boolean shouldRewardExp() {
      return this.rewardExp;
   }

   public CompoundTag createTag() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.put("buy", this.baseCostA.save(new CompoundTag()));
      â˜ƒ.put("sell", this.result.save(new CompoundTag()));
      â˜ƒ.put("buyB", this.costB.save(new CompoundTag()));
      â˜ƒ.putInt("uses", this.uses);
      â˜ƒ.putInt("maxUses", this.maxUses);
      â˜ƒ.putBoolean("rewardExp", this.rewardExp);
      â˜ƒ.putInt("xp", this.xp);
      â˜ƒ.putFloat("priceMultiplier", this.priceMultiplier);
      â˜ƒ.putInt("specialPrice", this.specialPriceDiff);
      â˜ƒ.putInt("demand", this.demand);
      return â˜ƒ;
   }

   public boolean satisfiedBy(ItemStack var1, ItemStack var2) {
      return this.isRequiredItem(â˜ƒ, this.getCostA())
         && â˜ƒ.getCount() >= this.getCostA().getCount()
         && this.isRequiredItem(â˜ƒ, this.costB)
         && â˜ƒ.getCount() >= this.costB.getCount();
   }

   private boolean isRequiredItem(ItemStack var1, ItemStack var2) {
      if (â˜ƒ.isEmpty() && â˜ƒ.isEmpty()) {
         return true;
      } else {
         ItemStack â˜ƒ = â˜ƒ.copy();
         if (â˜ƒ.getItem().canBeDepleted()) {
            â˜ƒ.setDamageValue(â˜ƒ.getDamageValue());
         }

         return ItemStack.isSame(â˜ƒ, â˜ƒ) && (!â˜ƒ.hasTag() || â˜ƒ.hasTag() && NbtUtils.compareNbt(â˜ƒ.getTag(), â˜ƒ.getTag(), false));
      }
   }

   public boolean take(ItemStack var1, ItemStack var2) {
      if (!this.satisfiedBy(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         â˜ƒ.shrink(this.getCostA().getCount());
         if (!this.getCostB().isEmpty()) {
            â˜ƒ.shrink(this.getCostB().getCount());
         }

         return true;
      }
   }
}
