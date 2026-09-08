package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.trading.MerchantOffers;

public class ClientboundMerchantOffersPacket implements Packet<ClientGamePacketListener> {
   private final int containerId;
   private final MerchantOffers offers;
   private final int villagerLevel;
   private final int villagerXp;
   private final boolean showProgress;
   private final boolean canRestock;

   public ClientboundMerchantOffersPacket(int var1, MerchantOffers var2, int var3, int var4, boolean var5, boolean var6) {
      this.containerId = â˜ƒ;
      this.offers = â˜ƒ;
      this.villagerLevel = â˜ƒ;
      this.villagerXp = â˜ƒ;
      this.showProgress = â˜ƒ;
      this.canRestock = â˜ƒ;
   }

   public ClientboundMerchantOffersPacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readVarInt();
      this.offers = MerchantOffers.createFromStream(â˜ƒ);
      this.villagerLevel = â˜ƒ.readVarInt();
      this.villagerXp = â˜ƒ.readVarInt();
      this.showProgress = â˜ƒ.readBoolean();
      this.canRestock = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.containerId);
      this.offers.writeToStream(â˜ƒ);
      â˜ƒ.writeVarInt(this.villagerLevel);
      â˜ƒ.writeVarInt(this.villagerXp);
      â˜ƒ.writeBoolean(this.showProgress);
      â˜ƒ.writeBoolean(this.canRestock);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleMerchantOffers(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   public MerchantOffers getOffers() {
      return this.offers;
   }

   public int getVillagerLevel() {
      return this.villagerLevel;
   }

   public int getVillagerXp() {
      return this.villagerXp;
   }

   public boolean showProgress() {
      return this.showProgress;
   }

   public boolean canRestock() {
      return this.canRestock;
   }
}
