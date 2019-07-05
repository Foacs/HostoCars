import { carsActionTypes as types } from 'actions';

const initialState = {
    cars: [],
    isLoading: false,
    isInError: false
};

const testReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.GET_CARS:
            return {
                ...state,
                cars: [],
                isLoading: true,
                isInError: false
            };
        case types.GET_CARS_OK:
            return {
                ...state,
                cars: action.data,
                isLoading: false,
                isInError: false
            };
        case types.GET_CARS_ERROR:
            return {
                ...state,
                cars: [],
                isLoading: false,
                isInError: true
            };
        default:
            return state;
    }
};

export default testReducer;
