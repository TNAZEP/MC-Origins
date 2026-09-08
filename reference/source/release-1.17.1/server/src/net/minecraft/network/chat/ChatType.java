package net.minecraft.network.chat;

public enum ChatType {
   CHAT((byte)0, false),
   SYSTEM((byte)1, true),
   GAME_INFO((byte)2, true);

   private final byte index;
   private final boolean interrupt;

   private ChatType(byte var3, boolean var4) {
      this.index = â˜ƒ;
      this.interrupt = â˜ƒ;
   }

   public byte getIndex() {
      return this.index;
   }

   public static ChatType getForIndex(byte var0) {
      for(ChatType â˜ƒ : values()) {
         if (â˜ƒ == â˜ƒ.index) {
            return â˜ƒ;
         }
      }

      return CHAT;
   }

   public boolean shouldInterrupt() {
      return this.interrupt;
   }
}
