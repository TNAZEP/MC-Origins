package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;

public class SPacketAdvancementInfo implements Packet<INetHandlerPlayClient> {
   private boolean field_192605_a;
   private Map<ResourceLocation, Advancement.Builder> field_192606_b;
   private Set<ResourceLocation> field_192607_c;
   private Map<ResourceLocation, AdvancementProgress> field_192608_d;

   public SPacketAdvancementInfo() {
   }

   public SPacketAdvancementInfo(boolean var1, Collection<Advancement> var2, Set<ResourceLocation> var3, Map<ResourceLocation, AdvancementProgress> var4) {
      this.field_192605_a = ☃;
      this.field_192606_b = Maps.<ResourceLocation, Advancement.Builder>newHashMap();

      for(Advancement ☃ : ☃) {
         this.field_192606_b.put(☃.func_192067_g(), ☃.func_192075_a());
      }

      this.field_192607_c = ☃;
      this.field_192608_d = Maps.<ResourceLocation, AdvancementProgress>newHashMap(☃);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_191981_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_192605_a = ☃.readBoolean();
      this.field_192606_b = Maps.<ResourceLocation, Advancement.Builder>newHashMap();
      this.field_192607_c = Sets.<ResourceLocation>newLinkedHashSet();
      this.field_192608_d = Maps.<ResourceLocation, AdvancementProgress>newHashMap();
      int ☃ = ☃.func_150792_a();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         ResourceLocation ☃xx = ☃.func_192575_l();
         Advancement.Builder ☃xxx = Advancement.Builder.func_192060_b(☃);
         this.field_192606_b.put(☃xx, ☃xxx);
      }

      ☃ = ☃.func_150792_a();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         ResourceLocation ☃xx = ☃.func_192575_l();
         this.field_192607_c.add(☃xx);
      }

      ☃ = ☃.func_150792_a();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         ResourceLocation ☃xx = ☃.func_192575_l();
         this.field_192608_d.put(☃xx, AdvancementProgress.func_192100_b(☃));
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeBoolean(this.field_192605_a);
      ☃.func_150787_b(this.field_192606_b.size());

      for(Entry<ResourceLocation, Advancement.Builder> ☃ : this.field_192606_b.entrySet()) {
         ResourceLocation ☃x = (ResourceLocation)☃.getKey();
         Advancement.Builder ☃xx = (Advancement.Builder)☃.getValue();
         ☃.func_192572_a(☃x);
         ☃xx.func_192057_a(☃);
      }

      ☃.func_150787_b(this.field_192607_c.size());

      for(ResourceLocation ☃ : this.field_192607_c) {
         ☃.func_192572_a(☃);
      }

      ☃.func_150787_b(this.field_192608_d.size());

      for(Entry<ResourceLocation, AdvancementProgress> ☃ : this.field_192608_d.entrySet()) {
         ☃.func_192572_a((ResourceLocation)☃.getKey());
         ((AdvancementProgress)☃.getValue()).func_192104_a(☃);
      }
   }
}
