
 package control;

 /**
  * Type.java
  * @author Team007
  *
  * Class to define different bike types.
  * The type name can be changed
  */
public class Type {
    private String name;

    public Type(String name) {
        if (name == null) throw new IllegalArgumentException("The type has to have a name");
        this.name = name;
    }//end constructor

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if (newName == null) throw new IllegalArgumentException("Error at Type.java, the argument is null");
        name = newName;
    }//end method

    //Equals method.
    //Created to avoid indifference between lower and upper case characters

    /**
     * Override of the standard equals()-method to indifference upper and lower case letters when comparing Type-objects.
     * @param b
     * @return
     * @author Team 007
     */
    @Override
    public boolean equals(Object b) {
        if (b == null) throw new IllegalArgumentException("Error at Type.java, object not created");
        if (!(b instanceof Type)) throw new IllegalArgumentException("Object must be instance of Type.java");
        Type a = (Type) b;
        if (name.toLowerCase().equals(a.getName().toLowerCase())) return true;
        else return false;
    }//end method

    /**
     * Override of the standard toString()-method to make it only return the name of the type.
     * @return
     */
    @Override
    public String toString() {
        return name;
    }//end method
}//end class