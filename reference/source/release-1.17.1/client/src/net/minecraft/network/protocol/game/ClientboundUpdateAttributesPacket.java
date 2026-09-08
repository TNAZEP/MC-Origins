package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ClientboundUpdateAttributesPacket implements Packet<ClientGamePacketListener> {
   private final int entityId;
   private final List<ClientboundUpdateAttributesPacket.AttributeSnapshot> attributes;

   public ClientboundUpdateAttributesPacket(int var1, Collection<AttributeInstance> var2) {
      this.entityId = â˜ƒ;
      this.attributes = Lists.<ClientboundUpdateAttributesPacket.AttributeSnapshot>newArrayList();

      for(AttributeInstance â˜ƒ : â˜ƒ) {
         this.attributes.add(new ClientboundUpdateAttributesPacket.AttributeSnapshot(â˜ƒ.getAttribute(), â˜ƒ.getBaseValue(), â˜ƒ.getModifiers()));
      }
   }

   public ClientboundUpdateAttributesPacket(FriendlyByteBuf var1) {
      this.entityId = â˜ƒ.readVarInt();
      this.attributes = â˜ƒ.readList(
         var0 -> {
            ResourceLocation â˜ƒ = var0.readResourceLocation();
            Attribute â˜ƒx = Registry.ATTRIBUTE.get(â˜ƒ);
            double â˜ƒxx = var0.readDouble();
            List<AttributeModifier> â˜ƒxxx = var0.readList(
               var0x -> new AttributeModifier(
                     var0x.readUUID(), "Unknown synced attribute modifier", var0x.readDouble(), AttributeModifier.Operation.fromValue(var0x.readByte())
                  )
            );
            return new ClientboundUpdateAttributesPacket.AttributeSnapshot(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }
      );
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entityId);
      â˜ƒ.writeCollection(this.attributes, (var0, var1x) -> {
         var0.writeResourceLocation(Registry.ATTRIBUTE.getKey(var1x.getAttribute()));
         var0.writeDouble(var1x.getBase());
         var0.writeCollection(var1x.getModifiers(), (var0x, var1xx) -> {
            var0x.writeUUID(var1xx.getId());
            var0x.writeDouble(var1xx.getAmount());
            var0x.writeByte(var1xx.getOperation().toValue());
         });
      });
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleUpdateAttributes(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public List<ClientboundUpdateAttributesPacket.AttributeSnapshot> getValues() {
      return this.attributes;
   }

   public static class AttributeSnapshot {
      private final Attribute attribute;
      private final double base;
      private final Collection<AttributeModifier> modifiers;

      public AttributeSnapshot(Attribute var1, double var2, Collection<AttributeModifier> var4) {
         this.attribute = â˜ƒ;
         this.base = â˜ƒ;
         this.modifiers = â˜ƒ;
      }

      public Attribute getAttribute() {
         return this.attribute;
      }

      public double getBase() {
         return this.base;
      }

      public Collection<AttributeModifier> getModifiers() {
         return this.modifiers;
      }
   }
}
