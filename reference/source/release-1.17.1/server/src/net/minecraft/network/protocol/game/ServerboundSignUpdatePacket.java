package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundSignUpdatePacket implements Packet<ServerGamePacketListener> {
   private static final int MAX_STRING_LENGTH = 384;
   private final BlockPos pos;
   private final String[] lines;

   public ServerboundSignUpdatePacket(BlockPos var1, String var2, String var3, String var4, String var5) {
      this.pos = â˜ƒ;
      this.lines = new String[]{â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ};
   }

   public ServerboundSignUpdatePacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.lines = new String[4];

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         this.lines[â˜ƒ] = â˜ƒ.readUtf(384);
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         â˜ƒ.writeUtf(this.lines[â˜ƒ]);
      }
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSignUpdate(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public String[] getLines() {
      return this.lines;
   }
}
