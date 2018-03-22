/*
* Password.java
* @Team007
*
* This class is created to control password protection.
* The class will take a client chosen text, add a random salt and hash, then store the
* hash and salt in the database. The authentication will take the text and add the salt
* from the account information. The login will be successful if the input that is hashed
* equates the stored hash password in their account.
 */
package control;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;
public class Password {
    private static final int iterations = 20*1000; //More iterations = More expensive computing the hash(For us and attackers)
    private static final int saltLen = 32; //The salt will be 32 random bytes
    private static final int desiredKeyLen = 256;

    public Password(){}//Default constructor

    //Computes a salted PBKDF2 hash of given password(text).
    //This is the hash we store in the database.
    public static String hashPassword(String password){
        try {
            byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
            //Storing the salt with password
            return Base64.encodeBase64String(salt) + "$" + hash(password,salt);
        }//end try
        catch(Exception e){
            e.printStackTrace();
            return null;
        }//end catch
    }//end method

    //public method to check if password corresponds
    //to a stored salted hash of the password.
    public static boolean check(String password, String stored){
        String[] findSaltAndHash = stored.split("\\$");
        if(findSaltAndHash.length != 2){
            throw new IllegalStateException("Lagret passord har formen 'salt$hash'.");
        }//end if
        try{
            String hashIn = hash(password,Base64.decodeBase64(findSaltAndHash[0]));
            return hashIn.equals(findSaltAndHash[1]);
        }//end try
        catch (Exception e){
            e.printStackTrace();
            return false;
        }//end catch
    }//end method

    //private method to generate a hash,
    //using PBKDF2 from Sun..
    private static String hash(String password, byte[] salt){
        if(password == null || password.length() == 0)throw new
                IllegalArgumentException("Passord kan ikke være tomt");
        try{
            PBEKeySpec pbe = new PBEKeySpec(password.toCharArray(),salt, iterations,desiredKeyLen);//What we want hashed
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");//Hash algorithm
            SecretKey key = factory.generateSecret(pbe);
            return Base64.encodeBase64String(key.getEncoded());
        }//end try
        catch(Exception e){
            e.printStackTrace();
            return null;
        }//end catch
    }//end method
}//end class
