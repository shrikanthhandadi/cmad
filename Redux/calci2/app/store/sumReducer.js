import TYPES from '../actions/types';

export default function sumReducer(state = {
    sum: 0
}, action) {
    switch (action.type) {

        case TYPES.ADD:
            return {
                sum: Number(action.params.first) + Number(action.params.second)
            }

        default:
            return 99;
    }
}
