export function addNumber(number){
    return{
        type: "ADD",
        payload: number
    }
}

export function substractNumber(number){
    return{
        type: "SUBSTRACT",
        payload: number
    }
}