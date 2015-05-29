package com.jabely.framework.result;

import java.io.Serializable;

import com.jabely.framework.result.error.AbstractErrorResult;

public class BooleanResult extends AbstractErrorResult implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -3354216689707109512L;

	public BooleanResult() {
        super();
    }

    public BooleanResult(String errorCode, String errorMessage) {
        super();
        addErrorMsg(errorCode, errorMessage);
    }
}
