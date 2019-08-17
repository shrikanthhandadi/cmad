const ACTION = {
    USER_ADD_PENDING: "userAddPending",
    USER_ADD_SUCCESS: "userAddSuccess",
    USER_ADD_FAILED: "userAddFailed",

    USER_FETCHALL_PENDING: "userFetchallPending",
    USER_FETCHALL_SUCCESS: "userFetchallSuccess",
    USER_FETCHALL_FAILED: "userFetchallFailed",

    USER_REMOVE_PENDING: "userRemovePending",
    USER_REMOVE_SUCCESS: "userRemoveSuccess",
    USER_REMOVE_FAILED: "userRemoveFailed",

    AUTH_LOGIN_SUCCESS: "authLoginSuccess",
    AUTH_LOGIN_FAILED: "authLoginFailed",

    AUTH_LOGOUT_SUCCESS: "authLogoutSuccess",

    CAR_FETCH_MAKES_PENDING: "carFetchMakesPending",
    CAR_FETCH_MAKES_SUCCESS: "carFetchMakesSuccess",
    CAR_FETCH_MAKES_FAILED: "carFetchMakesFailed",

    CAR_FETCH_MODELS_PENDING: "carFetchModelsPending",
    CAR_FETCH_MODELS_SUCCESS: "carFetchModelsSuccess",
    CAR_FETCH_MODELS_FAILED: "carFetchModelsFailed",

    CAR_SET_MODEL: "carSetModel",

    EVENT_STATS_PENDING: "eventStatsPending",
    EVENT_STATS_SUCCESS: "eventStatsSuccess",
    EVENT_STATS_FAILED: "eventStatsFailed",

    EVENT_FETCHALL_PENDING: "eventFetchallPending",
    EVENT_FETCHALL_SUCCESS: "eventFetchallSuccess",
    EVENT_FETCHALL_FAILED: "eventFetchallFailed",
}

export default ACTION;