import { carsActionTypes as types } from 'actions';

const initialState = {
    cars: [],
    isGetInProgress: false,
    isGetInError: false,
    isAddInProgress: false,
    isAddInError: false,
    sortedBy: 'registration'
};

const testReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.GET_CARS:
            return {
                ...state,
                cars: [],
                isGetInProgress: true,
                isGetInError: false
            };
        case types.GET_CARS_OK:
            return {
                ...state,
                cars: action.cars,
                isGetInProgress: false,
                isGetInError: false
            };
        case types.GET_CARS_ERROR:
            return {
                ...state,
                cars: [],
                isGetInProgress: false,
                isGetInError: true
            };
        case types.ADD_CAR:
            return {
                ...state,
                isAddInProgress: true,
                isAddInError: false
            };
        case types.ADD_CAR_OK:
            return {
                ...state,
                isAddInProgress: false,
                isAddInError: false
            };
        case types.ADD_CAR_ERROR:
            return {
                ...state,
                isAddInProgress: false,
                isAddInError: true
            };
        case types.CHANGE_CARS_SORT_ORDER:
            return {
                ...state,
                sortedBy: action.sortedBy
            };
        default:
            return state;
    }
};

export default testReducer;
