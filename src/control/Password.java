
package control;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;

/**
 * Password.java
 * @author Team007
 *
 * This class is created to control password protection.
 * The class will take a client chosen text, add a random salt and hash, then store the
 * hash and salt in the database. The authentication will take the text and add the salt
 * from the account information. The login will be successful if the input that is hashed
 * equates the stored hash password in their account.
 * This code is created with insperation from stackoverflow.com user @Martin Konicek
 */
public class Password {
    private static final int iterations = 20*1000; //More iterations = More expensive computing the hash(For us and attackers)
    private static final int saltLen = 32; //The salt will be 32 random bytes
    private static final int desiredKeyLen = 64; //Completed hash bytes. Set down to match resources

    public Password(){}//Default constructor

    /**
     * Computes a salted PBKDF2 hash of given password(text).
     * The hash's first part is the salt we use to hash the password with.
     * This and the hashed result is what we store in the database.
     * The salt and the hashed result are separated by a char of our choice
     * (here we use "$").
     * @param password is an object of String.java
     * @return String of the salt and hashed result
     */
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

    /**
     * Public method to check if password corresponds
     * to a stored salted hash of the password.
     * @param password is an object of String.java
     * @param stored is an object of String.java
     * @return boolean true if the passwords correspond.
     */
    public static boolean check(String password, String stored){
        if(password == null || password.length() == 0 ||
                stored == null || stored.length() == 0)throw new IllegalArgumentException("Password cannot" +
                " be empty!");
        String[] findSaltAndHash = stored.split("\\$");
        if(findSaltAndHash.length != 2){ //protection against the runtime exception
            throw new IllegalStateException("Stored password has the form 'salt$hash'.");
        }//end if

        //Hashing the written password with the same salt as the stored password.
        //If the result is the stored hash the password is correct.
        try{
            String hashIn = hash(password,Base64.decodeBase64(findSaltAndHash[0]));
            return hashIn.equals(findSaltAndHash[1]);
        }//end try
        catch (Exception e){
            e.printStackTrace();
            return false;
        }//end catch
    }//end method


    /**
     * Private method to generate a hash,
     * using PBKDF2 algorithm from Sun.
     * @param password is an object of String.java
     * @param salt is an array of bytes.
     * @return String, which is the hash.
     */
    private static String hash(String password, byte[] salt){
        if(password == null || password.length() == 0)throw new
                IllegalArgumentException("Password cannot be empty");
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
