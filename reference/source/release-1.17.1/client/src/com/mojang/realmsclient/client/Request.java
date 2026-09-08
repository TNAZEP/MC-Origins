package com.mojang.realmsclient.client;

import com.mojang.realmsclient.exception.RealmsHttpException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public abstract class Request<T extends Request<T>> {
   protected HttpURLConnection connection;
   private boolean connected;
   protected String url;
   private static final int DEFAULT_READ_TIMEOUT = 60000;
   private static final int DEFAULT_CONNECT_TIMEOUT = 5000;

   public Request(String var1, int var2, int var3) {
      try {
         this.url = â˜ƒ;
         Proxy â˜ƒ = RealmsClientConfig.getProxy();
         if (â˜ƒ != null) {
            this.connection = (HttpURLConnection)new URL(â˜ƒ).openConnection(â˜ƒ);
         } else {
            this.connection = (HttpURLConnection)new URL(â˜ƒ).openConnection();
         }

         this.connection.setConnectTimeout(â˜ƒ);
         this.connection.setReadTimeout(â˜ƒ);
      } catch (MalformedURLException var5) {
         throw new RealmsHttpException(var5.getMessage(), var5);
      } catch (IOException var6) {
         throw new RealmsHttpException(var6.getMessage(), var6);
      }
   }

   public void cookie(String var1, String var2) {
      cookie(this.connection, â˜ƒ, â˜ƒ);
   }

   public static void cookie(HttpURLConnection var0, String var1, String var2) {
      String â˜ƒ = â˜ƒ.getRequestProperty("Cookie");
      if (â˜ƒ == null) {
         â˜ƒ.setRequestProperty("Cookie", â˜ƒ + "=" + â˜ƒ);
      } else {
         â˜ƒ.setRequestProperty("Cookie", â˜ƒ + ";" + â˜ƒ + "=" + â˜ƒ);
      }
   }

   public T header(String var1, String var2) {
      this.connection.addRequestProperty(â˜ƒ, â˜ƒ);
      return (T)this;
   }

   public int getRetryAfterHeader() {
      return getRetryAfterHeader(this.connection);
   }

   public static int getRetryAfterHeader(HttpURLConnection var0) {
      String â˜ƒ = â˜ƒ.getHeaderField("Retry-After");

      try {
         return Integer.valueOf(â˜ƒ);
      } catch (Exception var3) {
         return 5;
      }
   }

   public int responseCode() {
      try {
         this.connect();
         return this.connection.getResponseCode();
      } catch (Exception var2) {
         throw new RealmsHttpException(var2.getMessage(), var2);
      }
   }

   public String text() {
      try {
         this.connect();
         String â˜ƒ = null;
         if (this.responseCode() >= 400) {
            â˜ƒ = this.read(this.connection.getErrorStream());
         } else {
            â˜ƒ = this.read(this.connection.getInputStream());
         }

         this.dispose();
         return â˜ƒ;
      } catch (IOException var2) {
         throw new RealmsHttpException(var2.getMessage(), var2);
      }
   }

   private String read(InputStream var1) throws IOException {
      if (â˜ƒ == null) {
         return "";
      } else {
         InputStreamReader â˜ƒ = new InputStreamReader(â˜ƒ, "UTF-8");
         StringBuilder â˜ƒx = new StringBuilder();

         for(int â˜ƒxx = â˜ƒ.read(); â˜ƒxx != -1; â˜ƒxx = â˜ƒ.read()) {
            â˜ƒx.append((char)â˜ƒxx);
         }

         return â˜ƒx.toString();
      }
   }

   private void dispose() {
      byte[] â˜ƒ = new byte[1024];

      try {
         InputStream â˜ƒx = this.connection.getInputStream();

         while(â˜ƒx.read(â˜ƒ) > 0) {
         }

         â˜ƒx.close();
         return;
      } catch (Exception var9) {
         try {
            InputStream â˜ƒx = this.connection.getErrorStream();
            if (â˜ƒx != null) {
               while(â˜ƒx.read(â˜ƒ) > 0) {
               }

               â˜ƒx.close();
               return;
            }
         } catch (IOException var8) {
            return;
         }
      } finally {
         if (this.connection != null) {
            this.connection.disconnect();
         }
      }
   }

   protected T connect() {
      if (this.connected) {
         return (T)this;
      } else {
         T â˜ƒ = this.doConnect();
         this.connected = true;
         return â˜ƒ;
      }
   }

   protected abstract T doConnect();

   public static Request<?> get(String var0) {
      return new Request.Get(â˜ƒ, 5000, 60000);
   }

   public static Request<?> get(String var0, int var1, int var2) {
      return new Request.Get(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static Request<?> post(String var0, String var1) {
      return new Request.Post(â˜ƒ, â˜ƒ, 5000, 60000);
   }

   public static Request<?> post(String var0, String var1, int var2, int var3) {
      return new Request.Post(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static Request<?> delete(String var0) {
      return new Request.Delete(â˜ƒ, 5000, 60000);
   }

   public static Request<?> put(String var0, String var1) {
      return new Request.Put(â˜ƒ, â˜ƒ, 5000, 60000);
   }

   public static Request<?> put(String var0, String var1, int var2, int var3) {
      return new Request.Put(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public String getHeader(String var1) {
      return getHeader(this.connection, â˜ƒ);
   }

   public static String getHeader(HttpURLConnection var0, String var1) {
      try {
         return â˜ƒ.getHeaderField(â˜ƒ);
      } catch (Exception var3) {
         return "";
      }
   }

   public static class Delete extends Request<Request.Delete> {
      public Delete(String var1, int var2, int var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public Request.Delete doConnect() {
         try {
            this.connection.setDoOutput(true);
            this.connection.setRequestMethod("DELETE");
            this.connection.connect();
            return this;
         } catch (Exception var2) {
            throw new RealmsHttpException(var2.getMessage(), var2);
         }
      }
   }

   public static class Get extends Request<Request.Get> {
      public Get(String var1, int var2, int var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public Request.Get doConnect() {
         try {
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("GET");
            return this;
         } catch (Exception var2) {
            throw new RealmsHttpException(var2.getMessage(), var2);
         }
      }
   }

   public static class Post extends Request<Request.Post> {
      private final String content;

      public Post(String var1, String var2, int var3, int var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.content = â˜ƒ;
      }

      public Request.Post doConnect() {
         try {
            if (this.content != null) {
               this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("POST");
            OutputStream â˜ƒ = this.connection.getOutputStream();
            OutputStreamWriter â˜ƒx = new OutputStreamWriter(â˜ƒ, "UTF-8");
            â˜ƒx.write(this.content);
            â˜ƒx.close();
            â˜ƒ.flush();
            return this;
         } catch (Exception var3) {
            throw new RealmsHttpException(var3.getMessage(), var3);
         }
      }
   }

   public static class Put extends Request<Request.Put> {
      private final String content;

      public Put(String var1, String var2, int var3, int var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.content = â˜ƒ;
      }

      public Request.Put doConnect() {
         try {
            if (this.content != null) {
               this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.connection.setDoOutput(true);
            this.connection.setDoInput(true);
            this.connection.setRequestMethod("PUT");
            OutputStream â˜ƒ = this.connection.getOutputStream();
            OutputStreamWriter â˜ƒx = new OutputStreamWriter(â˜ƒ, "UTF-8");
            â˜ƒx.write(this.content);
            â˜ƒx.close();
            â˜ƒ.flush();
            return this;
         } catch (Exception var3) {
            throw new RealmsHttpException(var3.getMessage(), var3);
         }
      }
   }
}
