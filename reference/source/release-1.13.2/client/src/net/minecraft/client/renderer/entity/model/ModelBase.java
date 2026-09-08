package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class ModelBase {
   public float field_78095_p;
   public boolean field_78093_q;
   public boolean field_78091_s = true;
   public List<ModelRenderer> field_78092_r = Lists.<ModelRenderer>newArrayList();
   private final Map<String, TextureOffset> field_78094_a = Maps.newHashMap();
   public int field_78090_t = 64;
   public int field_78089_u = 32;

   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
   }

   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
   }

   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
   }

   public ModelRenderer func_85181_a(Random var1) {
      return (ModelRenderer)this.field_78092_r.get(☃.nextInt(this.field_78092_r.size()));
   }

   protected void func_78085_a(String var1, int var2, int var3) {
      this.field_78094_a.put(☃, new TextureOffset(☃, ☃));
   }

   public TextureOffset func_78084_a(String var1) {
      return (TextureOffset)this.field_78094_a.get(☃);
   }

   public static void func_178685_a(ModelRenderer var0, ModelRenderer var1) {
      ☃.field_78795_f = ☃.field_78795_f;
      ☃.field_78796_g = ☃.field_78796_g;
      ☃.field_78808_h = ☃.field_78808_h;
      ☃.field_78800_c = ☃.field_78800_c;
      ☃.field_78797_d = ☃.field_78797_d;
      ☃.field_78798_e = ☃.field_78798_e;
   }

   public void func_178686_a(ModelBase var1) {
      this.field_78095_p = ☃.field_78095_p;
      this.field_78093_q = ☃.field_78093_q;
      this.field_78091_s = ☃.field_78091_s;
   }
}
