package com.edustream.security;

import java.security.*;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class Passwords {
  private Passwords() {}

  public static String hash(String password) {
    byte[] salt = new byte[16];
    new SecureRandom().nextBytes(salt);
    return "pbkdf2$210000$"
        + Base64.getEncoder().encodeToString(salt)
        + "$"
        + Base64.getEncoder().encodeToString(derive(password, salt, 210000));
  }

  private static byte[] derive(String password, byte[] salt, int rounds) {
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, rounds, 256);
    try {
      return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    } catch (GeneralSecurityException ex) {
      throw new IllegalStateException("No se pudo proteger la contraseña.", ex);
    } finally {
      spec.clearPassword();
    }
  }

  public static boolean verify(String password, String stored) {
    try {
      String[] p = stored.split("\\$");
      if (p.length != 4 || !p[0].equals("pbkdf2")) return false;
      int rounds = Integer.parseInt(p[1]);
      if (rounds < 10000 || rounds > 1000000) return false;
      return MessageDigest.isEqual(
          Base64.getDecoder().decode(p[3]),
          derive(password, Base64.getDecoder().decode(p[2]), rounds));
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }
}
