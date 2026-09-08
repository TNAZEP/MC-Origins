package net.minecraft.client.gui.narration;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;

public interface NarrationElementOutput {
   default void add(NarratedElementType var1, Component var2) {
      this.add(â˜ƒ, NarrationThunk.from(â˜ƒ.getString()));
   }

   default void add(NarratedElementType var1, String var2) {
      this.add(â˜ƒ, NarrationThunk.from(â˜ƒ));
   }

   default void add(NarratedElementType var1, Component... var2) {
      this.add(â˜ƒ, NarrationThunk.from(ImmutableList.copyOf(â˜ƒ)));
   }

   void add(NarratedElementType var1, NarrationThunk<?> var2);

   NarrationElementOutput nest();
}
