package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Util;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class OpenGlHelper {
   public static boolean field_153197_d;
   public static boolean field_181063_b;
   public static int field_153198_e;
   public static int field_153199_f;
   public static int field_153200_g;
   public static int field_153201_h;
   public static int field_153202_i;
   public static int field_153203_j;
   public static int field_153204_k;
   public static int field_153205_l;
   public static int field_153206_m;
   private static OpenGlHelper.FboMode field_153212_w;
   public static boolean field_148823_f;
   private static boolean field_153213_x;
   private static boolean field_153214_y;
   public static int field_153207_o;
   public static int field_153208_p;
   public static int field_153209_q;
   public static int field_153210_r;
   private static boolean field_153215_z;
   public static int field_77478_a;
   public static int field_77476_b;
   public static int field_176096_r;
   private static boolean field_176088_V;
   public static int field_176095_s;
   public static int field_176094_t;
   public static int field_176093_u;
   public static int field_176092_v;
   public static int field_176091_w;
   public static int field_176099_x;
   public static int field_176098_y;
   public static int field_176097_z;
   public static int field_176080_A;
   public static int field_176081_B;
   public static int field_176082_C;
   public static int field_176076_D;
   public static int field_176077_E;
   public static int field_176078_F;
   public static int field_176079_G;
   public static int field_176084_H;
   public static int field_176085_I;
   public static int field_176086_J;
   public static int field_176087_K;
   private static boolean field_148828_i;
   public static boolean field_153211_u;
   public static boolean field_148827_a;
   public static boolean field_148824_g;
   private static String field_153196_B = "";
   private static String field_183030_aa;
   public static boolean field_176083_O;
   public static boolean field_181062_Q;
   private static boolean field_176090_Y;
   public static int field_176089_P;
   public static int field_148826_e;
   private static final Map<Integer, String> field_195919_ac = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      var0.put(0, "No error");
      var0.put(1280, "Enum parameter is invalid for this function");
      var0.put(1281, "Parameter is invalid for this function");
      var0.put(1282, "Current state is invalid for this function");
      var0.put(1283, "Stack overflow");
      var0.put(1284, "Stack underflow");
      var0.put(1285, "Out of memory");
      var0.put(1286, "Operation on incomplete framebuffer");
      var0.put(1286, "Operation on incomplete framebuffer");
   });

   public static void func_77474_a() {
      GLCapabilities ☃ = GL.getCapabilities();
      field_153215_z = ☃.GL_ARB_multitexture && !☃.OpenGL13;
      field_176088_V = ☃.GL_ARB_texture_env_combine && !☃.OpenGL13;
      if (field_153215_z) {
         field_153196_B = field_153196_B + "Using ARB_multitexture.\n";
         field_77478_a = 33984;
         field_77476_b = 33985;
         field_176096_r = 33986;
      } else {
         field_153196_B = field_153196_B + "Using GL 1.3 multitexturing.\n";
         field_77478_a = 33984;
         field_77476_b = 33985;
         field_176096_r = 33986;
      }

      if (field_176088_V) {
         field_153196_B = field_153196_B + "Using ARB_texture_env_combine.\n";
         field_176095_s = 34160;
         field_176094_t = 34165;
         field_176093_u = 34167;
         field_176092_v = 34166;
         field_176091_w = 34168;
         field_176099_x = 34161;
         field_176098_y = 34176;
         field_176097_z = 34177;
         field_176080_A = 34178;
         field_176081_B = 34192;
         field_176082_C = 34193;
         field_176076_D = 34194;
         field_176077_E = 34162;
         field_176078_F = 34184;
         field_176079_G = 34185;
         field_176084_H = 34186;
         field_176085_I = 34200;
         field_176086_J = 34201;
         field_176087_K = 34202;
      } else {
         field_153196_B = field_153196_B + "Using GL 1.3 texture combiners.\n";
         field_176095_s = 34160;
         field_176094_t = 34165;
         field_176093_u = 34167;
         field_176092_v = 34166;
         field_176091_w = 34168;
         field_176099_x = 34161;
         field_176098_y = 34176;
         field_176097_z = 34177;
         field_176080_A = 34178;
         field_176081_B = 34192;
         field_176082_C = 34193;
         field_176076_D = 34194;
         field_176077_E = 34162;
         field_176078_F = 34184;
         field_176079_G = 34185;
         field_176084_H = 34186;
         field_176085_I = 34200;
         field_176086_J = 34201;
         field_176087_K = 34202;
      }

      field_153211_u = ☃.GL_EXT_blend_func_separate && !☃.OpenGL14;
      field_148828_i = ☃.OpenGL14 || ☃.GL_EXT_blend_func_separate;
      field_148823_f = field_148828_i && (☃.GL_ARB_framebuffer_object || ☃.GL_EXT_framebuffer_object || ☃.OpenGL30);
      if (field_148823_f) {
         field_153196_B = field_153196_B + "Using framebuffer objects because ";
         if (☃.OpenGL30) {
            field_153196_B = field_153196_B + "OpenGL 3.0 is supported and separate blending is supported.\n";
            field_153212_w = OpenGlHelper.FboMode.BASE;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153203_j = 36054;
            field_153204_k = 36055;
            field_153205_l = 36059;
            field_153206_m = 36060;
         } else if (☃.GL_ARB_framebuffer_object) {
            field_153196_B = field_153196_B + "ARB_framebuffer_object is supported and separate blending is supported.\n";
            field_153212_w = OpenGlHelper.FboMode.ARB;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153204_k = 36055;
            field_153203_j = 36054;
            field_153205_l = 36059;
            field_153206_m = 36060;
         } else if (☃.GL_EXT_framebuffer_object) {
            field_153196_B = field_153196_B + "EXT_framebuffer_object is supported.\n";
            field_153212_w = OpenGlHelper.FboMode.EXT;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153204_k = 36055;
            field_153203_j = 36054;
            field_153205_l = 36059;
            field_153206_m = 36060;
         }
      } else {
         field_153196_B = field_153196_B + "Not using framebuffer objects because ";
         field_153196_B = field_153196_B + "OpenGL 1.4 is " + (☃.OpenGL14 ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "EXT_blend_func_separate is " + (☃.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "OpenGL 3.0 is " + (☃.OpenGL30 ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "ARB_framebuffer_object is " + (☃.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
         field_153196_B = field_153196_B + "EXT_framebuffer_object is " + (☃.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
      }

      field_148827_a = ☃.OpenGL21;
      field_153213_x = field_148827_a || ☃.GL_ARB_vertex_shader && ☃.GL_ARB_fragment_shader && ☃.GL_ARB_shader_objects;
      field_153196_B = field_153196_B + "Shaders are " + (field_153213_x ? "" : "not ") + "available because ";
      if (field_153213_x) {
         if (☃.OpenGL21) {
            field_153196_B = field_153196_B + "OpenGL 2.1 is supported.\n";
            field_153214_y = false;
            field_153207_o = 35714;
            field_153208_p = 35713;
            field_153209_q = 35633;
            field_153210_r = 35632;
         } else {
            field_153196_B = field_153196_B + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
            field_153214_y = true;
            field_153207_o = 35714;
            field_153208_p = 35713;
            field_153209_q = 35633;
            field_153210_r = 35632;
         }
      } else {
         field_153196_B = field_153196_B + "OpenGL 2.1 is " + (☃.OpenGL21 ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "ARB_shader_objects is " + (☃.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "ARB_vertex_shader is " + (☃.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
         field_153196_B = field_153196_B + "ARB_fragment_shader is " + (☃.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
      }

      field_148824_g = field_148823_f && field_153213_x;
      String ☃ = GL11.glGetString(7936).toLowerCase(Locale.ROOT);
      field_153197_d = ☃.contains("nvidia");
      field_176090_Y = !☃.OpenGL15 && ☃.GL_ARB_vertex_buffer_object;
      field_176083_O = ☃.OpenGL15 || field_176090_Y;
      field_153196_B = field_153196_B + "VBOs are " + (field_176083_O ? "" : "not ") + "available because ";
      if (field_176083_O) {
         if (field_176090_Y) {
            field_153196_B = field_153196_B + "ARB_vertex_buffer_object is supported.\n";
            field_148826_e = 35044;
            field_176089_P = 34962;
         } else {
            field_153196_B = field_153196_B + "OpenGL 1.5 is supported.\n";
            field_148826_e = 35044;
            field_176089_P = 34962;
         }
      }

      field_181063_b = ☃.contains("ati");
      if (field_181063_b) {
         if (field_176083_O) {
            field_181062_Q = true;
         } else {
            GameSettings.Options.RENDER_DISTANCE.func_148263_a(16.0F);
         }
      }

      try {
         Processor[] ☃ = new SystemInfo().getHardware().getProcessors();
         field_183030_aa = String.format("%dx %s", ☃.length, ☃[0]).replaceAll("\\s+", " ");
      } catch (Throwable var3) {
      }
   }

   public static boolean func_153193_b() {
      return field_148824_g;
   }

   public static String func_153172_c() {
      return field_153196_B;
   }

   public static int func_153175_a(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(☃, ☃) : GL20.glGetProgrami(☃, ☃);
   }

   public static void func_153178_b(int var0, int var1) {
      if (field_153214_y) {
         ARBShaderObjects.glAttachObjectARB(☃, ☃);
      } else {
         GL20.glAttachShader(☃, ☃);
      }
   }

   public static void func_153180_a(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glDeleteObjectARB(☃);
      } else {
         GL20.glDeleteShader(☃);
      }
   }

   public static int func_153195_b(int var0) {
      return field_153214_y ? ARBShaderObjects.glCreateShaderObjectARB(☃) : GL20.glCreateShader(☃);
   }

   public static void func_195918_a(int var0, CharSequence var1) {
      if (field_153214_y) {
         ARBShaderObjects.glShaderSourceARB(☃, ☃);
      } else {
         GL20.glShaderSource(☃, ☃);
      }
   }

   public static void func_153170_c(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glCompileShaderARB(☃);
      } else {
         GL20.glCompileShader(☃);
      }
   }

   public static int func_153157_c(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(☃, ☃) : GL20.glGetShaderi(☃, ☃);
   }

   public static String func_153158_d(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetInfoLogARB(☃, ☃) : GL20.glGetShaderInfoLog(☃, ☃);
   }

   public static String func_153166_e(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetInfoLogARB(☃, ☃) : GL20.glGetProgramInfoLog(☃, ☃);
   }

   public static void func_153161_d(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glUseProgramObjectARB(☃);
      } else {
         GL20.glUseProgram(☃);
      }
   }

   public static int func_153183_d() {
      return field_153214_y ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
   }

   public static void func_153187_e(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glDeleteObjectARB(☃);
      } else {
         GL20.glDeleteProgram(☃);
      }
   }

   public static void func_153179_f(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glLinkProgramARB(☃);
      } else {
         GL20.glLinkProgram(☃);
      }
   }

   public static int func_153194_a(int var0, CharSequence var1) {
      return field_153214_y ? ARBShaderObjects.glGetUniformLocationARB(☃, ☃) : GL20.glGetUniformLocation(☃, ☃);
   }

   public static void func_153181_a(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1ivARB(☃, ☃);
      } else {
         GL20.glUniform1iv(☃, ☃);
      }
   }

   public static void func_153163_f(int var0, int var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1iARB(☃, ☃);
      } else {
         GL20.glUniform1i(☃, ☃);
      }
   }

   public static void func_153168_a(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1fvARB(☃, ☃);
      } else {
         GL20.glUniform1fv(☃, ☃);
      }
   }

   public static void func_153182_b(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform2ivARB(☃, ☃);
      } else {
         GL20.glUniform2iv(☃, ☃);
      }
   }

   public static void func_153177_b(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform2fvARB(☃, ☃);
      } else {
         GL20.glUniform2fv(☃, ☃);
      }
   }

   public static void func_153192_c(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform3ivARB(☃, ☃);
      } else {
         GL20.glUniform3iv(☃, ☃);
      }
   }

   public static void func_153191_c(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform3fvARB(☃, ☃);
      } else {
         GL20.glUniform3fv(☃, ☃);
      }
   }

   public static void func_153162_d(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform4ivARB(☃, ☃);
      } else {
         GL20.glUniform4iv(☃, ☃);
      }
   }

   public static void func_153159_d(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform4fvARB(☃, ☃);
      } else {
         GL20.glUniform4fv(☃, ☃);
      }
   }

   public static void func_153173_a(int var0, boolean var1, FloatBuffer var2) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix2fvARB(☃, ☃, ☃);
      } else {
         GL20.glUniformMatrix2fv(☃, ☃, ☃);
      }
   }

   public static void func_153189_b(int var0, boolean var1, FloatBuffer var2) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix3fvARB(☃, ☃, ☃);
      } else {
         GL20.glUniformMatrix3fv(☃, ☃, ☃);
      }
   }

   public static void func_153160_c(int var0, boolean var1, FloatBuffer var2) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix4fvARB(☃, ☃, ☃);
      } else {
         GL20.glUniformMatrix4fv(☃, ☃, ☃);
      }
   }

   public static int func_153164_b(int var0, CharSequence var1) {
      return field_153214_y ? ARBVertexShader.glGetAttribLocationARB(☃, ☃) : GL20.glGetAttribLocation(☃, ☃);
   }

   public static int func_176073_e() {
      return field_176090_Y ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
   }

   public static void func_176072_g(int var0, int var1) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glBindBufferARB(☃, ☃);
      } else {
         GL15.glBindBuffer(☃, ☃);
      }
   }

   public static void func_176071_a(int var0, ByteBuffer var1, int var2) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glBufferDataARB(☃, ☃, ☃);
      } else {
         GL15.glBufferData(☃, ☃, ☃);
      }
   }

   public static void func_176074_g(int var0) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glDeleteBuffersARB(☃);
      } else {
         GL15.glDeleteBuffers(☃);
      }
   }

   public static boolean func_176075_f() {
      return field_176083_O && Minecraft.func_71410_x().field_71474_y.field_178881_t;
   }

   public static void func_153171_g(int var0, int var1) {
      if (field_148823_f) {
         switch(field_153212_w) {
            case BASE:
               GL30.glBindFramebuffer(☃, ☃);
               break;
            case ARB:
               ARBFramebufferObject.glBindFramebuffer(☃, ☃);
               break;
            case EXT:
               EXTFramebufferObject.glBindFramebufferEXT(☃, ☃);
         }
      }
   }

   public static void func_153176_h(int var0, int var1) {
      if (field_148823_f) {
         switch(field_153212_w) {
            case BASE:
               GL30.glBindRenderbuffer(☃, ☃);
               break;
            case ARB:
               ARBFramebufferObject.glBindRenderbuffer(☃, ☃);
               break;
            case EXT:
               EXTFramebufferObject.glBindRenderbufferEXT(☃, ☃);
         }
      }
   }

   public static void func_153184_g(int var0) {
      if (field_148823_f) {
         switch(field_153212_w) {
            case BASE:
               GL30.glDeleteRenderbuffers(☃);
               break;
            case ARB:
               ARBFramebufferObject.glDeleteRenderbuffers(☃);
               break;
            case EXT:
               EXTFramebufferObject.glDeleteRenderbuffersEXT(☃);
         }
      }
   }

   public static void func_153174_h(int var0) {
      if (field_148823_f) {
         switch(field_153212_w) {
            case BASE:
               GL30.glDeleteFramebuffers(☃);
               break;
            case ARB:
               ARBFramebufferObject.glDeleteFramebuffers(☃);
               break;
            case EXT:
               EXTFramebufferObject.glDeleteFramebuffersEXT(☃);
         }
      }
   }

   public static int func_153165_e() {
      if (!field_148823_f) {
         return -1;
      } else {
         switch(field_153212_w) {
            case BASE:
               return GL30.glGenFramebuffers();
            case ARB:
               return ARBFramebufferObject.glGenFramebuffers();
            case EXT:
               return EXTFramebufferObject.glGenFramebuffersEXT();
            default:
               return -1;
         }
      }
   }

   public static int func_153185_f() {
      if (!field_148823_f) {
         return -1;
      } else {
         switch(field_153212_w) {
            case BASE:
               return GL30.glGenRenderbuffers();
            case ARB:
               return ARBFramebufferObject.glGenRenderbuffers();
            case EXT:
               return EXTFramebufferObject.glGenRenderbuffersEXT();
            default:
               return -1;
         }
      }
   }

   public static void func_153186_a(int var0, int var1, int var2, int var3) {
      if (field_148823_f) {
         switch(field_153212_w) {
            case BASE:
               GL30.glRenderbufferStorage(☃, ☃, ☃, ☃);
               break;
            case ARB:
               ARBFramebufferObject.glRenderbufferStorage(☃, ☃, ☃, ☃);
               break;
            case EXT:
               EXTFramebufferObject.glRenderbufferStorageEXT(☃, ☃, ☃, ☃);
         }
      }
   }

   public static void func_153190_b(int var0, int var1, int var2, int var3) {
      if (field_148823_f) {
         switch(field_153212_w) {
            case BASE:
               GL30.glFramebufferRenderbuffer(☃, ☃, ☃, ☃);
               break;
            case ARB:
               ARBFramebufferObject.glFramebufferRenderbuffer(☃, ☃, ☃, ☃);
               break;
            case EXT:
               EXTFramebufferObject.glFramebufferRenderbufferEXT(☃, ☃, ☃, ☃);
         }
      }
   }

   public static int func_153167_i(int var0) {
      if (!field_148823_f) {
         return -1;
      } else {
         switch(field_153212_w) {
            case BASE:
               return GL30.glCheckFramebufferStatus(☃);
            case ARB:
               return ARBFramebufferObject.glCheckFramebufferStatus(☃);
            case EXT:
               return EXTFramebufferObject.glCheckFramebufferStatusEXT(☃);
            default:
               return -1;
         }
      }
   }

   public static void func_153188_a(int var0, int var1, int var2, int var3, int var4) {
      if (field_148823_f) {
         switch(field_153212_w) {
            case BASE:
               GL30.glFramebufferTexture2D(☃, ☃, ☃, ☃, ☃);
               break;
            case ARB:
               ARBFramebufferObject.glFramebufferTexture2D(☃, ☃, ☃, ☃, ☃);
               break;
            case EXT:
               EXTFramebufferObject.glFramebufferTexture2DEXT(☃, ☃, ☃, ☃, ☃);
         }
      }
   }

   public static void func_77473_a(int var0) {
      if (field_153215_z) {
         ARBMultitexture.glActiveTextureARB(☃);
      } else {
         GL13.glActiveTexture(☃);
      }
   }

   public static void func_77472_b(int var0) {
      if (field_153215_z) {
         ARBMultitexture.glClientActiveTextureARB(☃);
      } else {
         GL13.glClientActiveTexture(☃);
      }
   }

   public static void func_77475_a(int var0, float var1, float var2) {
      if (field_153215_z) {
         ARBMultitexture.glMultiTexCoord2fARB(☃, ☃, ☃);
      } else {
         GL13.glMultiTexCoord2f(☃, ☃, ☃);
      }
   }

   public static void func_148821_a(int var0, int var1, int var2, int var3) {
      if (field_148828_i) {
         if (field_153211_u) {
            EXTBlendFuncSeparate.glBlendFuncSeparateEXT(☃, ☃, ☃, ☃);
         } else {
            GL14.glBlendFuncSeparate(☃, ☃, ☃, ☃);
         }
      } else {
         GL11.glBlendFunc(☃, ☃);
      }
   }

   public static boolean func_148822_b() {
      return field_148823_f && Minecraft.func_71410_x().field_71474_y.field_151448_g;
   }

   public static String func_183029_j() {
      return field_183030_aa == null ? "<unknown>" : field_183030_aa;
   }

   public static void func_188785_m(int var0) {
      func_203094_a(☃, true, true, true);
   }

   public static void func_203094_a(int var0, boolean var1, boolean var2, boolean var3) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      GL11.glLineWidth(4.0F);
      ☃x.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      if (☃) {
         ☃x.func_181662_b(0.0, 0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
         ☃x.func_181662_b((double)☃, 0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
      }

      if (☃) {
         ☃x.func_181662_b(0.0, 0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
         ☃x.func_181662_b(0.0, (double)☃, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
      }

      if (☃) {
         ☃x.func_181662_b(0.0, 0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
         ☃x.func_181662_b(0.0, 0.0, (double)☃).func_181669_b(0, 0, 0, 255).func_181675_d();
      }

      ☃.func_78381_a();
      GL11.glLineWidth(2.0F);
      ☃x.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      if (☃) {
         ☃x.func_181662_b(0.0, 0.0, 0.0).func_181669_b(255, 0, 0, 255).func_181675_d();
         ☃x.func_181662_b((double)☃, 0.0, 0.0).func_181669_b(255, 0, 0, 255).func_181675_d();
      }

      if (☃) {
         ☃x.func_181662_b(0.0, 0.0, 0.0).func_181669_b(0, 255, 0, 255).func_181675_d();
         ☃x.func_181662_b(0.0, (double)☃, 0.0).func_181669_b(0, 255, 0, 255).func_181675_d();
      }

      if (☃) {
         ☃x.func_181662_b(0.0, 0.0, 0.0).func_181669_b(127, 127, 255, 255).func_181675_d();
         ☃x.func_181662_b(0.0, 0.0, (double)☃).func_181669_b(127, 127, 255, 255).func_181675_d();
      }

      ☃.func_78381_a();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
   }

   public static String func_195917_n(int var0) {
      return (String)field_195919_ac.get(☃);
   }

   static enum FboMode {
      BASE,
      ARB,
      EXT;
   }
}
