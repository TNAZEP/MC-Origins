package net.minecraft.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;

public class DisplayInfo {
   private final ITextComponent field_192300_a;
   private final ITextComponent field_193225_b;
   private final ItemStack field_192301_b;
   private final ResourceLocation field_192302_c;
   private final FrameType field_192303_d;
   private final boolean field_193226_f;
   private final boolean field_193227_g;
   private final boolean field_193228_h;
   private float field_192304_e;
   private float field_192305_f;

   public DisplayInfo(
      ItemStack var1, ITextComponent var2, ITextComponent var3, @Nullable ResourceLocation var4, FrameType var5, boolean var6, boolean var7, boolean var8
   ) {
      this.field_192300_a = ☃;
      this.field_193225_b = ☃;
      this.field_192301_b = ☃;
      this.field_192302_c = ☃;
      this.field_192303_d = ☃;
      this.field_193226_f = ☃;
      this.field_193227_g = ☃;
      this.field_193228_h = ☃;
   }

   public void func_192292_a(float var1, float var2) {
      this.field_192304_e = ☃;
      this.field_192305_f = ☃;
   }

   public ITextComponent func_192297_a() {
      return this.field_192300_a;
   }

   public ITextComponent func_193222_b() {
      return this.field_193225_b;
   }

   public ItemStack func_192298_b() {
      return this.field_192301_b;
   }

   @Nullable
   public ResourceLocation func_192293_c() {
      return this.field_192302_c;
   }

   public FrameType func_192291_d() {
      return this.field_192303_d;
   }

   public float func_192299_e() {
      return this.field_192304_e;
   }

   public float func_192296_f() {
      return this.field_192305_f;
   }

   public boolean func_193223_h() {
      return this.field_193226_f;
   }

   public boolean func_193220_i() {
      return this.field_193227_g;
   }

   public boolean func_193224_j() {
      return this.field_193228_h;
   }

   public static DisplayInfo func_192294_a(JsonObject var0, JsonDeserializationContext var1) {
      ITextComponent ☃ = JsonUtils.func_188174_a(☃, "title", ☃, ITextComponent.class);
      ITextComponent ☃x = JsonUtils.func_188174_a(☃, "description", ☃, ITextComponent.class);
      if (☃ != null && ☃x != null) {
         ItemStack ☃xx = func_193221_a(JsonUtils.func_152754_s(☃, "icon"));
         ResourceLocation ☃xxx = ☃.has("background") ? new ResourceLocation(JsonUtils.func_151200_h(☃, "background")) : null;
         FrameType ☃xxxx = ☃.has("frame") ? FrameType.func_192308_a(JsonUtils.func_151200_h(☃, "frame")) : FrameType.TASK;
         boolean ☃xxxxx = JsonUtils.func_151209_a(☃, "show_toast", true);
         boolean ☃xxxxxx = JsonUtils.func_151209_a(☃, "announce_to_chat", true);
         boolean ☃xxxxxxx = JsonUtils.func_151209_a(☃, "hidden", false);
         return new DisplayInfo(☃xx, ☃, ☃x, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static ItemStack func_193221_a(JsonObject var0) {
      if (!☃.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item ☃ = JsonUtils.func_188180_i(☃, "item");
         if (☃.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            ItemStack ☃ = new ItemStack(☃);
            if (☃.has("nbt")) {
               try {
                  NBTTagCompound ☃x = JsonToNBT.func_180713_a(JsonUtils.func_151206_a(☃.get("nbt"), "nbt"));
                  ☃.func_77982_d(☃x);
               } catch (CommandSyntaxException var4) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + var4.getMessage());
               }
            }

            return ☃;
         }
      }
   }

   public void func_192290_a(PacketBuffer var1) {
      ☃.func_179256_a(this.field_192300_a);
      ☃.func_179256_a(this.field_193225_b);
      ☃.func_150788_a(this.field_192301_b);
      ☃.func_179249_a(this.field_192303_d);
      int ☃ = 0;
      if (this.field_192302_c != null) {
         ☃ |= 1;
      }

      if (this.field_193226_f) {
         ☃ |= 2;
      }

      if (this.field_193228_h) {
         ☃ |= 4;
      }

      ☃.writeInt(☃);
      if (this.field_192302_c != null) {
         ☃.func_192572_a(this.field_192302_c);
      }

      ☃.writeFloat(this.field_192304_e);
      ☃.writeFloat(this.field_192305_f);
   }

   public static DisplayInfo func_192295_b(PacketBuffer var0) {
      ITextComponent ☃ = ☃.func_179258_d();
      ITextComponent ☃x = ☃.func_179258_d();
      ItemStack ☃xx = ☃.func_150791_c();
      FrameType ☃xxx = ☃.func_179257_a(FrameType.class);
      int ☃xxxx = ☃.readInt();
      ResourceLocation ☃xxxxx = (☃xxxx & 1) != 0 ? ☃.func_192575_l() : null;
      boolean ☃xxxxxx = (☃xxxx & 2) != 0;
      boolean ☃xxxxxxx = (☃xxxx & 4) != 0;
      DisplayInfo ☃xxxxxxxx = new DisplayInfo(☃xx, ☃, ☃x, ☃xxxxx, ☃xxx, ☃xxxxxx, false, ☃xxxxxxx);
      ☃xxxxxxxx.func_192292_a(☃.readFloat(), ☃.readFloat());
      return ☃xxxxxxxx;
   }

   public JsonElement func_200290_k() {
      JsonObject ☃ = new JsonObject();
      ☃.add("icon", this.func_200289_l());
      ☃.add("title", ITextComponent.Serializer.func_200528_b(this.field_192300_a));
      ☃.add("description", ITextComponent.Serializer.func_200528_b(this.field_193225_b));
      ☃.addProperty("frame", this.field_192303_d.func_192307_a());
      ☃.addProperty("show_toast", this.field_193226_f);
      ☃.addProperty("announce_to_chat", this.field_193227_g);
      ☃.addProperty("hidden", this.field_193228_h);
      if (this.field_192302_c != null) {
         ☃.addProperty("background", this.field_192302_c.toString());
      }

      return ☃;
   }

   private JsonObject func_200289_l() {
      JsonObject ☃ = new JsonObject();
      ☃.addProperty("item", IRegistry.field_212630_s.func_177774_c(this.field_192301_b.func_77973_b()).toString());
      return ☃;
   }
}
