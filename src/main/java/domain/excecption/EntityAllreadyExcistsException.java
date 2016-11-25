package domain.excecption;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 19, 2016 
 */
public class EntityAllreadyExcistsException extends Exception {

    public EntityAllreadyExcistsException() {
        
    }
    
    public EntityAllreadyExcistsException(String msg) {
        super(msg);
    }

}
