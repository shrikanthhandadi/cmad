const userReducer = (state = {
    name: "Shrikanth",
    age: 22
}, action) => {
    switch (action.type) {
        case "SET_NAME_FULFILLED":
            state = {
                ...state,
                name: action.payload
            }
            break;
        case "SET_AGE":
            state = {
                ...state,
                age: action.payload
            }
            break;
        default:
            // console.log("unknown type ", action.type);
            break;
    }
    return state;
}

export default userReducer;

