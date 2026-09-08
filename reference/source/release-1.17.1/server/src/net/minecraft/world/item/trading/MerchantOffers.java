package net.minecraft.world.item.trading;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class MerchantOffers extends ArrayList<MerchantOffer> {
   public MerchantOffers() {
   }

   public MerchantOffers(CompoundTag var1) {
      ListTag â˜ƒ = â˜ƒ.getList("Recipes", 10);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         this.add(new MerchantOffer(â˜ƒ.getCompound(â˜ƒx)));
      }
   }

   @Nullable
   public MerchantOffer getRecipeFor(ItemStack var1, ItemStack var2, int var3) {
      if (â˜ƒ > 0 && â˜ƒ < this.size()) {
         MerchantOffer â˜ƒ = (MerchantOffer)this.get(â˜ƒ);
         return â˜ƒ.satisfiedBy(â˜ƒ, â˜ƒ) ? â˜ƒ : null;
      } else {
         for(int â˜ƒ = 0; â˜ƒ < this.size(); ++â˜ƒ) {
            MerchantOffer â˜ƒx = (MerchantOffer)this.get(â˜ƒ);
            if (â˜ƒx.satisfiedBy(â˜ƒ, â˜ƒ)) {
               return â˜ƒx;
            }
         }

         return null;
      }
   }

   public void writeToStream(FriendlyByteBuf var1) {
      â˜ƒ.writeByte((byte)(this.size() & 0xFF));

      for(int â˜ƒ = 0; â˜ƒ < this.size(); ++â˜ƒ) {
         MerchantOffer â˜ƒx = (MerchantOffer)this.get(â˜ƒ);
         â˜ƒ.writeItem(â˜ƒx.getBaseCostA());
         â˜ƒ.writeItem(â˜ƒx.getResult());
         ItemStack â˜ƒxx = â˜ƒx.getCostB();
         â˜ƒ.writeBoolean(!â˜ƒxx.isEmpty());
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ.writeItem(â˜ƒxx);
         }

         â˜ƒ.writeBoolean(â˜ƒx.isOutOfStock());
         â˜ƒ.writeInt(â˜ƒx.getUses());
         â˜ƒ.writeInt(â˜ƒx.getMaxUses());
         â˜ƒ.writeInt(â˜ƒx.getXp());
         â˜ƒ.writeInt(â˜ƒx.getSpecialPriceDiff());
         â˜ƒ.writeFloat(â˜ƒx.getPriceMultiplier());
         â˜ƒ.writeInt(â˜ƒx.getDemand());
      }
   }

   public static MerchantOffers createFromStream(FriendlyByteBuf var0) {
      MerchantOffers â˜ƒ = new MerchantOffers();
      int â˜ƒx = â˜ƒ.readByte() & 255;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.readItem();
         ItemStack â˜ƒxxxx = â˜ƒ.readItem();
         ItemStack â˜ƒxxxxx = ItemStack.EMPTY;
         if (â˜ƒ.readBoolean()) {
            â˜ƒxxxxx = â˜ƒ.readItem();
         }

         boolean â˜ƒxxx = â˜ƒ.readBoolean();
         int â˜ƒxxxx = â˜ƒ.readInt();
         int â˜ƒxxxxx = â˜ƒ.readInt();
         int â˜ƒxxxxxx = â˜ƒ.readInt();
         int â˜ƒxxxxxxx = â˜ƒ.readInt();
         float â˜ƒxxxxxxxx = â˜ƒ.readFloat();
         int â˜ƒxxxxxxxxx = â˜ƒ.readInt();
         MerchantOffer â˜ƒxxxxxxxxxx = new MerchantOffer(â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
         if (â˜ƒxxx) {
            â˜ƒxxxxxxxxxx.setToOutOfStock();
         }

         â˜ƒxxxxxxxxxx.setSpecialPriceDiff(â˜ƒxxxxxxx);
         â˜ƒ.add(â˜ƒxxxxxxxxxx);
      }

      return â˜ƒ;
   }

   public CompoundTag createTag() {
      CompoundTag â˜ƒ = new CompoundTag();
      ListTag â˜ƒx = new ListTag();

      for(int â˜ƒxx = 0; â˜ƒxx < this.size(); ++â˜ƒxx) {
         MerchantOffer â˜ƒxxx = (MerchantOffer)this.get(â˜ƒxx);
         â˜ƒx.add(â˜ƒxxx.createTag());
      }

      â˜ƒ.put("Recipes", â˜ƒx);
      return â˜ƒ;
   }
}
