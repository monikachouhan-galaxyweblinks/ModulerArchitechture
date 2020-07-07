package com.aucto.mpinlock.security.callbacks;


import com.aucto.mpinlock.security.PFResult;

public interface PFPinCodeHelperCallback<T> {
    void onResult(PFResult<T> result);
}
