package domain.excecption;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Oct 23, 2016 
 */
public class NonexistentEntityException extends Exception {

    public NonexistentEntityException(String msg) {
        super(msg);
    }
    public NonexistentEntityException() {
        
    }

}
