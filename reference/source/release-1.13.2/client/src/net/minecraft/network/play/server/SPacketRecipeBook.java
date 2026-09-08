package net.minecraft.network.play.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;

public class SPacketRecipeBook implements Packet<INetHandlerPlayClient> {
   private SPacketRecipeBook.State field_193646_a;
   private List<ResourceLocation> field_192596_a;
   private List<ResourceLocation> field_193647_c;
   private boolean field_192598_c;
   private boolean field_192599_d;
   private boolean field_202494_f;
   private boolean field_202495_g;

   public SPacketRecipeBook() {
   }

   public SPacketRecipeBook(
      SPacketRecipeBook.State var1,
      Collection<ResourceLocation> var2,
      Collection<ResourceLocation> var3,
      boolean var4,
      boolean var5,
      boolean var6,
      boolean var7
   ) {
      this.field_193646_a = ☃;
      this.field_192596_a = ImmutableList.copyOf(☃);
      this.field_193647_c = ImmutableList.copyOf(☃);
      this.field_192598_c = ☃;
      this.field_192599_d = ☃;
      this.field_202494_f = ☃;
      this.field_202495_g = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_191980_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_193646_a = ☃.func_179257_a(SPacketRecipeBook.State.class);
      this.field_192598_c = ☃.readBoolean();
      this.field_192599_d = ☃.readBoolean();
      this.field_202494_f = ☃.readBoolean();
      this.field_202495_g = ☃.readBoolean();
      int ☃ = ☃.func_150792_a();
      this.field_192596_a = Lists.<ResourceLocation>newArrayList();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         this.field_192596_a.add(☃.func_192575_l());
      }

      if (this.field_193646_a == SPacketRecipeBook.State.INIT) {
         ☃ = ☃.func_150792_a();
         this.field_193647_c = Lists.<ResourceLocation>newArrayList();

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            this.field_193647_c.add(☃.func_192575_l());
         }
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179249_a(this.field_193646_a);
      ☃.writeBoolean(this.field_192598_c);
      ☃.writeBoolean(this.field_192599_d);
      ☃.writeBoolean(this.field_202494_f);
      ☃.writeBoolean(this.field_202495_g);
      ☃.func_150787_b(this.field_192596_a.size());

      for(ResourceLocation ☃ : this.field_192596_a) {
         ☃.func_192572_a(☃);
      }

      if (this.field_193646_a == SPacketRecipeBook.State.INIT) {
         ☃.func_150787_b(this.field_193647_c.size());

         for(ResourceLocation ☃ : this.field_193647_c) {
            ☃.func_192572_a(☃);
         }
      }
   }

   public List<ResourceLocation> func_192595_a() {
      return this.field_192596_a;
   }

   public List<ResourceLocation> func_193644_b() {
      return this.field_193647_c;
   }

   public boolean func_192593_c() {
      return this.field_192598_c;
   }

   public boolean func_192594_d() {
      return this.field_192599_d;
   }

   public boolean func_202492_e() {
      return this.field_202494_f;
   }

   public boolean func_202493_f() {
      return this.field_202495_g;
   }

   public SPacketRecipeBook.State func_194151_e() {
      return this.field_193646_a;
   }

   public static enum State {
      INIT,
      ADD,
      REMOVE;
   }
}
