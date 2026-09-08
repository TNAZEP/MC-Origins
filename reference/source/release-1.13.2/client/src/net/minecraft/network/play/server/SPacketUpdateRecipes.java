package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeSerializers;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketUpdateRecipes implements Packet<INetHandlerPlayClient> {
   private List<IRecipe> field_199617_a;

   public SPacketUpdateRecipes() {
   }

   public SPacketUpdateRecipes(Collection<IRecipe> var1) {
      this.field_199617_a = Lists.<IRecipe>newArrayList(☃);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_199525_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_199617_a = Lists.<IRecipe>newArrayList();
      int ☃ = ☃.func_150792_a();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         this.field_199617_a.add(RecipeSerializers.func_199571_a(☃));
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_199617_a.size());

      for(IRecipe ☃ : this.field_199617_a) {
         RecipeSerializers.func_199574_a(☃, ☃);
      }
   }

   public List<IRecipe> func_199616_a() {
      return this.field_199617_a;
   }
}
