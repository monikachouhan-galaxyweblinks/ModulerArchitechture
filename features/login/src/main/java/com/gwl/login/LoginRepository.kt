package com.gwl.login

class LoginRepository {
    // region - Public function
   /* val userDao = UlltmaRoomDb.INSTANCE.userDao()

    suspend fun login(loginItem: LoginItem): DBSTATUS {
        userDao.getUserByEmail(loginItem.email)?.also {
            return if (it.password == loginItem.password) DBSTATUS.SUCCESS else DBSTATUS.FAILURE
        } ?: run {
            userDao.addUser(loginItem)
            return DBSTATUS.SUCCESS
        }
        return DBSTATUS.DBERROR
    }*/
    //endregion
}
