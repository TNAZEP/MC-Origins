package net.minecraft.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptManager {
   private static final Logger field_180198_a = LogManager.getLogger();

   public static SecretKey func_75890_a() {
      try {
         KeyGenerator ☃ = KeyGenerator.getInstance("AES");
         ☃.init(128);
         return ☃.generateKey();
      } catch (NoSuchAlgorithmException var1) {
         throw new Error(var1);
      }
   }

   public static KeyPair func_75891_b() {
      try {
         KeyPairGenerator ☃ = KeyPairGenerator.getInstance("RSA");
         ☃.initialize(1024);
         return ☃.generateKeyPair();
      } catch (NoSuchAlgorithmException var1) {
         var1.printStackTrace();
         field_180198_a.error("Key pair generation failed!");
         return null;
      }
   }

   public static byte[] func_75895_a(String var0, PublicKey var1, SecretKey var2) {
      try {
         return func_75893_a("SHA-1", ☃.getBytes("ISO_8859_1"), ☃.getEncoded(), ☃.getEncoded());
      } catch (UnsupportedEncodingException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static byte[] func_75893_a(String var0, byte[]... var1) {
      try {
         MessageDigest ☃ = MessageDigest.getInstance(☃);

         for(byte[] ☃x : ☃) {
            ☃.update(☃x);
         }

         return ☃.digest();
      } catch (NoSuchAlgorithmException var7) {
         var7.printStackTrace();
         return null;
      }
   }

   public static PublicKey func_75896_a(byte[] var0) {
      try {
         EncodedKeySpec ☃ = new X509EncodedKeySpec(☃);
         KeyFactory ☃x = KeyFactory.getInstance("RSA");
         return ☃x.generatePublic(☃);
      } catch (NoSuchAlgorithmException var3) {
      } catch (InvalidKeySpecException var4) {
      }

      field_180198_a.error("Public key reconstitute failed!");
      return null;
   }

   public static SecretKey func_75887_a(PrivateKey var0, byte[] var1) {
      return new SecretKeySpec(func_75889_b(☃, ☃), "AES");
   }

   public static byte[] func_75894_a(Key var0, byte[] var1) {
      return func_75885_a(1, ☃, ☃);
   }

   public static byte[] func_75889_b(Key var0, byte[] var1) {
      return func_75885_a(2, ☃, ☃);
   }

   private static byte[] func_75885_a(int var0, Key var1, byte[] var2) {
      try {
         return func_75886_a(☃, ☃.getAlgorithm(), ☃).doFinal(☃);
      } catch (IllegalBlockSizeException var4) {
         var4.printStackTrace();
      } catch (BadPaddingException var5) {
         var5.printStackTrace();
      }

      field_180198_a.error("Cipher data failed!");
      return null;
   }

   private static Cipher func_75886_a(int var0, String var1, Key var2) {
      try {
         Cipher ☃ = Cipher.getInstance(☃);
         ☃.init(☃, ☃);
         return ☃;
      } catch (InvalidKeyException var4) {
         var4.printStackTrace();
      } catch (NoSuchAlgorithmException var5) {
         var5.printStackTrace();
      } catch (NoSuchPaddingException var6) {
         var6.printStackTrace();
      }

      field_180198_a.error("Cipher creation failed!");
      return null;
   }

   public static Cipher func_151229_a(int var0, Key var1) {
      try {
         Cipher ☃ = Cipher.getInstance("AES/CFB8/NoPadding");
         ☃.init(☃, ☃, new IvParameterSpec(☃.getEncoded()));
         return ☃;
      } catch (GeneralSecurityException var3) {
         throw new RuntimeException(var3);
      }
   }
}
