package com.mpinlock.security.callbacks;


import com.mpinlock.security.PFResult;

public interface PFPinCodeHelperCallback<T> {
    void onResult(PFResult<T> result);
}
