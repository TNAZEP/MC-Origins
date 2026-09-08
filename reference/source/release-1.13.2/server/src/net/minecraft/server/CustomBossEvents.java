package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CustomBossEvents {
   private final MinecraftServer field_201386_a;
   private final Map<ResourceLocation, CustomBossEvent> field_201387_b = Maps.<ResourceLocation, CustomBossEvent>newHashMap();

   public CustomBossEvents(MinecraftServer var1) {
      this.field_201386_a = ☃;
   }

   @Nullable
   public CustomBossEvent func_201384_a(ResourceLocation var1) {
      return (CustomBossEvent)this.field_201387_b.get(☃);
   }

   public CustomBossEvent func_201379_a(ResourceLocation var1, ITextComponent var2) {
      CustomBossEvent ☃ = new CustomBossEvent(☃, ☃);
      this.field_201387_b.put(☃, ☃);
      return ☃;
   }

   public void func_201385_a(CustomBossEvent var1) {
      this.field_201387_b.remove(☃.func_201364_a());
   }

   public Collection<ResourceLocation> func_201377_a() {
      return this.field_201387_b.keySet();
   }

   public Collection<CustomBossEvent> func_201378_b() {
      return this.field_201387_b.values();
   }

   public NBTTagCompound func_201380_c() {
      NBTTagCompound ☃ = new NBTTagCompound();

      for(CustomBossEvent ☃x : this.field_201387_b.values()) {
         ☃.func_74782_a(☃x.func_201364_a().toString(), ☃x.func_201370_f());
      }

      return ☃;
   }

   public void func_201381_a(NBTTagCompound var1) {
      for(String ☃ : ☃.func_150296_c()) {
         ResourceLocation ☃x = new ResourceLocation(☃);
         this.field_201387_b.put(☃x, CustomBossEvent.func_201371_a(☃.func_74775_l(☃), ☃x));
      }
   }

   public void func_201383_a(EntityPlayerMP var1) {
      for(CustomBossEvent ☃ : this.field_201387_b.values()) {
         ☃.func_201361_c(☃);
      }
   }

   public void func_201382_b(EntityPlayerMP var1) {
      for(CustomBossEvent ☃ : this.field_201387_b.values()) {
         ☃.func_201363_d(☃);
      }
   }
}
