const validator = store => next => action =>{
    console.log('first & second ', action.params.first, action.params.second);
    if(action.params.first < 0){
        action.params.first = 0;
    }
    if(action.params.second < 0){
        action.params.second = 0;
    }
	return next(action);
}

export default validator;