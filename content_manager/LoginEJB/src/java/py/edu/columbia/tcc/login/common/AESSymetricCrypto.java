package py.edu.columbia.tcc.login.common;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class AESSymetricCrypto {

    public static void main(String[] args) throws Exception {
        try {
            String a = AESSymetricCrypto.encriptInSHA512HEX("123");

            System.out.println("Encript 1: " + a  + "   " +  a.length());
            
            /*String s = AESSymetricCrypto.encript(AESSymetricCrypto.createLink());
            System.out.println(s);
            System.out.println(s.length());

            String b = AESSymetricCrypto.decript(s);
            System.out.println(b);

            String c = AESSymetricCrypto.encript("sql$");
            System.out.println("clave de mi correo: " + c);
            String d = AESSymetricCrypto.decript(c);
            System.out.println("clave de mi correo: " + d);
            //String[] v = b.split(ResourceManager.getInstance().getMailSetup().getSeparatorLinkField());
            //System.out.println(v[0] + "\n" + v[1] + "\n" + v[2]);*/
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * Encripta un texto con una frase-clave la misma es necesaria para
     * desencriptar.
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String encript(String str) throws Exception {
        String passPhrase = "miFraseClave"; //ResourceManager.getInstance().getAppSetup().getPassPhrase();

        // Generamos una clave de 128 bits adecuada para AES
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        //Key key = keyGenerator.generateKey();

        // Alternativamente, una clave que queramos que tenga al menos 16 bytes
        // y nos quedamos con los bytes 0 a 15
        Key key = new SecretKeySpec(passPhrase.getBytes(), 0, 16, "AES");

        // Ver como se puede guardar esta clave en un fichero y recuperarla
        // posteriormente en la clase AESSymetricCrypto.java
        // Se obtiene un cifrador AES
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Se inicializa para encriptacion y se encripta el texto,
        // que debemos pasar como bytes.
        aes.init(Cipher.ENCRYPT_MODE, key);
        byte[] encriptado = aes.doFinal(str.getBytes());

        return Hex.encodeHexString(encriptado);
    }

    /**
     * Desencripta un texto con una frase-clave.
     *
     * @param str
     * @return
     * @throws java.lang.Exception
     */
    public static String decript(String str) throws Exception {
        try {
            String passPhrase = "miFraseClave"; //ResourceManager.getInstance().getAppSetup().getPassPhrase();
            // Generamos una clave de 128 bits adecuada para AES
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            //Key key = keyGenerator.generateKey();

            // Alternativamente, una clave que queramos que tenga al menos 16 bytes
            // y nos quedamos con los bytes 0 a 15
            Key key = new SecretKeySpec(passPhrase.getBytes(), 0, 16, "AES");

            // Se iniciliza el cifrador para desencriptar, con la
            // misma clave y se desencripta
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

            aes.init(Cipher.DECRYPT_MODE, key);

            byte[] desencriptado = aes.doFinal(Hex.decodeHex(str.toCharArray()));

            // Texto obtenido, igual al original.
            return new String(desencriptado, "UTF-8");
        } catch (Exception ex) {
            throw new Exception("No se pudo desencriptar al texto.", ex);
        }
    }

    /**
     * Método test para crear el link, y probar la encriptación/desencriptación.
     *
     * @return
     * @throws PMCConfigException
     */
    private static String createLink() throws Exception {
        /*
         MailUserConfirmSetup ms = ResourceManager.getInstance().getMailUserConfirmSetup();
         StringBuilder sb = new StringBuilder();
        
         Calendar c = Calendar.getInstance();
         //sb.append(c.getTimeInMillis()).append(ms.getSeparatorLinkField());
         sb.append(140 * ms.getValueToMultiplyIdUser()).append(ms.getSeparatorLinkField());
         sb.append(UUID.randomUUID()).append(ms.getSeparatorLinkField());
         sb.append("micorreoelectronico@gmail.com");
         //sb.append(UUID.randomUUID());
        
         return sb.toString();
         */

        return null;
    }

    /**
     * Método para dar de alta usuarios y sea compatible con la autenticación
     * REALM de Glassfish.
     *
     * @param value cadena a encriptar
     *
     * @return cadena encriptada en SHA-512 Hex
     * @throws edu.columbia.login.exception.Exception
     */
    public static String encriptInSHA512HEX(String value) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(value.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            //BigInteger bigInt = new BigInteger(1, digest);
            String output = Hex.encodeHexString(digest);//bigInt.toString(16);

            return output;
        } catch (Exception ex) {
            throw new Exception("No se pudo encriptar.", ex);
        }
    }

    /**
     * Método para dar de alta usuarios y sea compatible con la autenticación
     * REALM de Glassfish.
     *
     * @param value cadena a encriptar
     *
     * @return cadena encriptada en SHA-512 Hex
     * @throws edu.columbia.login.exception.Exception
     */
    public static String encriptInSHA512HEX2(String value) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(value.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            return output;
        } catch (Exception ex) {
            throw new Exception("No se pudo encriptar.", ex);
        }
    }
}
