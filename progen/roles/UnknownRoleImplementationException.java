package progen.roles;

import progen.kernel.error.Error;

public class UnknownRoleImplementationException extends RuntimeException {
    
    public UnknownRoleImplementationException(String msg){
	super(Error.get(38)+"["+msg+"]");
    }

}
