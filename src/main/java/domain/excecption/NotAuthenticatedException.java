package domain.excecption;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 18, 2016 
 */
public class NotAuthenticatedException extends Exception {

    public NotAuthenticatedException() {
    }
    public NotAuthenticatedException(String msg) {
        super(msg);
    }

}
