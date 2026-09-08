package net.minecraft.network.protocol.game;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.protocol.Packet;

public class ClientboundCommandSuggestionsPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   private final Suggestions suggestions;

   public ClientboundCommandSuggestionsPacket(int var1, Suggestions var2) {
      this.id = â˜ƒ;
      this.suggestions = â˜ƒ;
   }

   public ClientboundCommandSuggestionsPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      int â˜ƒ = â˜ƒ.readVarInt();
      int â˜ƒx = â˜ƒ.readVarInt();
      StringRange â˜ƒxx = StringRange.between(â˜ƒ, â˜ƒ + â˜ƒx);
      List<Suggestion> â˜ƒxxx = â˜ƒ.readList(var1x -> {
         String â˜ƒ = var1x.readUtf();
         Component â˜ƒx = var1x.readBoolean() ? var1x.readComponent() : null;
         return new Suggestion(â˜ƒ, â˜ƒ, â˜ƒx);
      });
      this.suggestions = new Suggestions(â˜ƒxx, â˜ƒxxx);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeVarInt(this.suggestions.getRange().getStart());
      â˜ƒ.writeVarInt(this.suggestions.getRange().getLength());
      â˜ƒ.writeCollection(this.suggestions.getList(), (var0, var1x) -> {
         var0.writeUtf(var1x.getText());
         var0.writeBoolean(var1x.getTooltip() != null);
         if (var1x.getTooltip() != null) {
            var0.writeComponent(ComponentUtils.fromMessage(var1x.getTooltip()));
         }
      });
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleCommandSuggestions(this);
   }

   public int getId() {
      return this.id;
   }

   public Suggestions getSuggestions() {
      return this.suggestions;
   }
}
