package net.minecraft.client.multiplayer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.AdvancementToast;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientAdvancementManager {
   private static final Logger field_192800_a = LogManager.getLogger();
   private final Minecraft field_192801_b;
   private final AdvancementList field_192802_c = new AdvancementList();
   private final Map<Advancement, AdvancementProgress> field_192803_d = Maps.<Advancement, AdvancementProgress>newHashMap();
   @Nullable
   private ClientAdvancementManager.IListener field_192804_e;
   @Nullable
   private Advancement field_194231_f;

   public ClientAdvancementManager(Minecraft var1) {
      this.field_192801_b = ☃;
   }

   public void func_192799_a(SPacketAdvancementInfo var1) {
      if (☃.func_192602_d()) {
         this.field_192802_c.func_192087_a();
         this.field_192803_d.clear();
      }

      this.field_192802_c.func_192085_a(☃.func_192600_b());
      this.field_192802_c.func_192083_a(☃.func_192603_a());

      for(Entry<ResourceLocation, AdvancementProgress> ☃ : ☃.func_192604_c().entrySet()) {
         Advancement ☃x = this.field_192802_c.func_192084_a((ResourceLocation)☃.getKey());
         if (☃x != null) {
            AdvancementProgress ☃xx = (AdvancementProgress)☃.getValue();
            ☃xx.func_192099_a(☃x.func_192073_f(), ☃x.func_192074_h());
            this.field_192803_d.put(☃x, ☃xx);
            if (this.field_192804_e != null) {
               this.field_192804_e.func_191933_a(☃x, ☃xx);
            }

            if (!☃.func_192602_d() && ☃xx.func_192105_a() && ☃x.func_192068_c() != null && ☃x.func_192068_c().func_193223_h()) {
               this.field_192801_b.func_193033_an().func_192988_a(new AdvancementToast(☃x));
            }
         } else {
            field_192800_a.warn("Server informed client about progress for unknown advancement {}", ☃.getKey());
         }
      }
   }

   public AdvancementList func_194229_a() {
      return this.field_192802_c;
   }

   public void func_194230_a(@Nullable Advancement var1, boolean var2) {
      NetHandlerPlayClient ☃ = this.field_192801_b.func_147114_u();
      if (☃ != null && ☃ != null && ☃) {
         ☃.func_147297_a(CPacketSeenAdvancements.func_194163_a(☃));
      }

      if (this.field_194231_f != ☃) {
         this.field_194231_f = ☃;
         if (this.field_192804_e != null) {
            this.field_192804_e.func_193982_e(☃);
         }
      }
   }

   public void func_192798_a(@Nullable ClientAdvancementManager.IListener var1) {
      this.field_192804_e = ☃;
      this.field_192802_c.func_192086_a(☃);
      if (☃ != null) {
         for(Entry<Advancement, AdvancementProgress> ☃ : this.field_192803_d.entrySet()) {
            ☃.func_191933_a((Advancement)☃.getKey(), (AdvancementProgress)☃.getValue());
         }

         ☃.func_193982_e(this.field_194231_f);
      }
   }

   public interface IListener extends AdvancementList.Listener {
      void func_191933_a(Advancement var1, AdvancementProgress var2);

      void func_193982_e(@Nullable Advancement var1);
   }
}
