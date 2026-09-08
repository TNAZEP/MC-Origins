package net.minecraft.client.renderer.vertex;

public class DefaultVertexFormats {
   public static final VertexFormatElement field_181713_m = new VertexFormatElement(
      0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3
   );
   public static final VertexFormatElement field_181714_n = new VertexFormatElement(
      0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4
   );
   public static final VertexFormatElement field_181715_o = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2);
   public static final VertexFormatElement field_181716_p = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
   public static final VertexFormatElement field_181717_q = new VertexFormatElement(
      0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3
   );
   public static final VertexFormatElement field_181718_r = new VertexFormatElement(
      0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1
   );
   public static final VertexFormat field_176600_a = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181714_n)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181716_p);
   public static final VertexFormat field_176599_b = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181714_n)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181717_q)
      .func_181721_a(field_181718_r);
   public static final VertexFormat field_181703_c = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181717_q)
      .func_181721_a(field_181718_r);
   public static final VertexFormat field_181704_d = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181714_n)
      .func_181721_a(field_181716_p);
   public static final VertexFormat field_181705_e = new VertexFormat().func_181721_a(field_181713_m);
   public static final VertexFormat field_181706_f = new VertexFormat().func_181721_a(field_181713_m).func_181721_a(field_181714_n);
   public static final VertexFormat field_181707_g = new VertexFormat().func_181721_a(field_181713_m).func_181721_a(field_181715_o);
   public static final VertexFormat field_181708_h = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181717_q)
      .func_181721_a(field_181718_r);
   public static final VertexFormat field_181709_i = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181714_n);
   public static final VertexFormat field_181710_j = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181717_q)
      .func_181721_a(field_181718_r);
   public static final VertexFormat field_181711_k = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181716_p)
      .func_181721_a(field_181714_n);
   public static final VertexFormat field_181712_l = new VertexFormat()
      .func_181721_a(field_181713_m)
      .func_181721_a(field_181715_o)
      .func_181721_a(field_181714_n)
      .func_181721_a(field_181717_q)
      .func_181721_a(field_181718_r);
}
