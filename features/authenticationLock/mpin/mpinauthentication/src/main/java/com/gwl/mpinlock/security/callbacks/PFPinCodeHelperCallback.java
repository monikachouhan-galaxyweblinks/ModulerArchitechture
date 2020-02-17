package com.gwl.mpinlock.security.callbacks;


import com.gwl.mpinlock.security.PFResult;

public interface PFPinCodeHelperCallback<T> {
    void onResult(PFResult<T> result);
}
