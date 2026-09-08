package net.minecraft.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {
   private static final String SYMMETRIC_ALGORITHM = "AES";
   private static final int SYMMETRIC_BITS = 128;
   private static final String ASYMMETRIC_ALGORITHM = "RSA";
   private static final int ASYMMETRIC_BITS = 1024;
   private static final String BYTE_ENCODING = "ISO_8859_1";
   private static final String HASH_ALGORITHM = "SHA-1";

   public static SecretKey generateSecretKey() throws CryptException {
      try {
         KeyGenerator â˜ƒ = KeyGenerator.getInstance("AES");
         â˜ƒ.init(128);
         return â˜ƒ.generateKey();
      } catch (Exception var1) {
         throw new CryptException(var1);
      }
   }

   public static KeyPair generateKeyPair() throws CryptException {
      try {
         KeyPairGenerator â˜ƒ = KeyPairGenerator.getInstance("RSA");
         â˜ƒ.initialize(1024);
         return â˜ƒ.generateKeyPair();
      } catch (Exception var1) {
         throw new CryptException(var1);
      }
   }

   public static byte[] digestData(String var0, PublicKey var1, SecretKey var2) throws CryptException {
      try {
         return digestData(â˜ƒ.getBytes("ISO_8859_1"), â˜ƒ.getEncoded(), â˜ƒ.getEncoded());
      } catch (Exception var4) {
         throw new CryptException(var4);
      }
   }

   private static byte[] digestData(byte[]... var0) throws Exception {
      MessageDigest â˜ƒ = MessageDigest.getInstance("SHA-1");

      for(byte[] â˜ƒx : â˜ƒ) {
         â˜ƒ.update(â˜ƒx);
      }

      return â˜ƒ.digest();
   }

   public static PublicKey byteToPublicKey(byte[] var0) throws CryptException {
      try {
         EncodedKeySpec â˜ƒ = new X509EncodedKeySpec(â˜ƒ);
         KeyFactory â˜ƒx = KeyFactory.getInstance("RSA");
         return â˜ƒx.generatePublic(â˜ƒ);
      } catch (Exception var3) {
         throw new CryptException(var3);
      }
   }

   public static SecretKey decryptByteToSecretKey(PrivateKey var0, byte[] var1) throws CryptException {
      byte[] â˜ƒ = decryptUsingKey(â˜ƒ, â˜ƒ);

      try {
         return new SecretKeySpec(â˜ƒ, "AES");
      } catch (Exception var4) {
         throw new CryptException(var4);
      }
   }

   public static byte[] encryptUsingKey(Key var0, byte[] var1) throws CryptException {
      return cipherData(1, â˜ƒ, â˜ƒ);
   }

   public static byte[] decryptUsingKey(Key var0, byte[] var1) throws CryptException {
      return cipherData(2, â˜ƒ, â˜ƒ);
   }

   private static byte[] cipherData(int var0, Key var1, byte[] var2) throws CryptException {
      try {
         return setupCipher(â˜ƒ, â˜ƒ.getAlgorithm(), â˜ƒ).doFinal(â˜ƒ);
      } catch (Exception var4) {
         throw new CryptException(var4);
      }
   }

   private static Cipher setupCipher(int var0, String var1, Key var2) throws Exception {
      Cipher â˜ƒ = Cipher.getInstance(â˜ƒ);
      â˜ƒ.init(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public static Cipher getCipher(int var0, Key var1) throws CryptException {
      try {
         Cipher â˜ƒ = Cipher.getInstance("AES/CFB8/NoPadding");
         â˜ƒ.init(â˜ƒ, â˜ƒ, new IvParameterSpec(â˜ƒ.getEncoded()));
         return â˜ƒ;
      } catch (Exception var3) {
         throw new CryptException(var3);
      }
   }
}
