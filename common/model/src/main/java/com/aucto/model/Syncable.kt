package com.aucto.model

interface Syncable {

    var syncStatus: SyncState

    //Use this to make api call and Pass Result
    //fun remoteRequest(): SyncState

    //fun <T>cloneForRemote() : T
}