package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;

public class SPacketTabComplete implements Packet<INetHandlerPlayClient> {
   private int field_197690_a;
   private Suggestions field_197691_b;

   public SPacketTabComplete() {
   }

   public SPacketTabComplete(int var1, Suggestions var2) {
      this.field_197690_a = ☃;
      this.field_197691_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_197690_a = ☃.func_150792_a();
      int ☃ = ☃.func_150792_a();
      int ☃x = ☃.func_150792_a();
      StringRange ☃xx = StringRange.between(☃, ☃ + ☃x);
      int ☃xxx = ☃.func_150792_a();
      List<Suggestion> ☃xxxx = Lists.<Suggestion>newArrayListWithCapacity(☃xxx);

      for(int ☃xxxxx = 0; ☃xxxxx < ☃xxx; ++☃xxxxx) {
         String ☃xxxxxx = ☃.func_150789_c(32767);
         ITextComponent ☃xxxxxxx = ☃.readBoolean() ? ☃.func_179258_d() : null;
         ☃xxxx.add(new Suggestion(☃xx, ☃xxxxxx, ☃xxxxxxx));
      }

      this.field_197691_b = new Suggestions(☃xx, ☃xxxx);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_197690_a);
      ☃.func_150787_b(this.field_197691_b.getRange().getStart());
      ☃.func_150787_b(this.field_197691_b.getRange().getLength());
      ☃.func_150787_b(this.field_197691_b.getList().size());

      for(Suggestion ☃ : this.field_197691_b.getList()) {
         ☃.func_180714_a(☃.getText());
         ☃.writeBoolean(☃.getTooltip() != null);
         if (☃.getTooltip() != null) {
            ☃.func_179256_a(TextComponentUtils.func_202465_a(☃.getTooltip()));
         }
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_195510_a(this);
   }

   public int func_197689_a() {
      return this.field_197690_a;
   }

   public Suggestions func_197687_b() {
      return this.field_197691_b;
   }
}
