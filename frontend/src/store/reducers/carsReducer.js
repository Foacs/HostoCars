import { carsActionTypes as types } from 'actions';

// The reducer's initial state
const initialState = {
    cars: [],
    isAddInError: false,
    isAddInProgress: false,
    isDeleteInError: false,
    isDeleteInProgress: false,
    isEditInError: false,
    isEditInProgress: false,
    isGetAllInError: false,
    isGetAllInProgress: false,
    isGetInError: false,
    isGetInProgress: false,
    sortedBy: 'registration'
};

/**
 * Returns the next reducer's state after the current action.
 *
 * @param {string} action
 *     The action
 * @param {object} [state = initialState]
 *     The current reducer's state
 *
 * @returns {object} the next reducer's state
 */
const carsReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.ADD_CAR:
            return {
                ...state,
                isAddInError: initialState.isAddInError,
                isAddInProgress: true
            };
        case types.ADD_CAR_ERROR:
            return {
                ...state,
                isAddInError: true,
                isAddInProgress: initialState.isAddInProgress
            };
        case types.ADD_CAR_OK:
            return {
                ...state,
                isAddInError: initialState.isAddInError,
                isAddInProgress: initialState.isAddInProgress
            };
        case types.CHANGE_CARS_SORT_ORDER:
            return {
                ...state,
                sortedBy: action.sortedBy
            };
        case types.DELETE_CAR:
            return {
                ...state,
                isDeleteInError: initialState.isDeleteInError,
                isDeleteInProgress: true
            };
        case types.DELETE_CAR_ERROR:
            return {
                ...state,
                isDeleteInError: true,
                isDeleteInProgress: initialState.isDeleteInProgress
            };
        case types.DELETE_CAR_OK:
            return {
                ...state,
                cars: state.cars.filter(car => action.id !== car.id),
                isDeleteInError: initialState.isDeleteInError,
                isDeleteInProgress: initialState.isDeleteInProgress
            };
        case types.EDIT_CAR:
            return {
                ...state,
                isEditInError: initialState.isEditInError,
                isEditInProgress: true
            };
        case types.EDIT_CAR_ERROR:
            return {
                ...state,
                isEditInError: true,
                isEditInProgress: initialState.isEditInProgress
            };
        case types.EDIT_CAR_OK:
            return {
                ...state,
                isEditInError: initialState.isEditInError,
                isEditInProgress: initialState.isEditInProgress
            };
        case types.GET_CAR:
            return {
                ...state,
                isGetInError: initialState.isGetInError,
                isGetInProgress: true
            };
        case types.GET_CAR_ERROR:
            return {
                ...state,
                isGetInError: true,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CAR_NO_CONTENT:
            return {
                ...state,
                cars: state.cars.filter(car => action.id !== car.id),
                isGetInError: true,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CAR_OK:
            return {
                ...state,
                cars: state.cars.map(car => action.car.id === car.id ? action.car : car),
                isGetInError: initialState.isGetInError,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CARS:
            return {
                ...state,
                cars: initialState.cars,
                isGetAllInError: initialState.isGetAllInError,
                isGetAllInProgress: true
            };
        case types.GET_CARS_ERROR:
            return {
                ...state,
                cars: initialState.cars,
                isGetAllInError: true,
                isGetAllInProgress: initialState.isGetAllInProgress
            };
        case types.GET_CARS_OK:
            return {
                ...state,
                cars: action.cars,
                isGetAllInError: initialState.isGetAllInError,
                isGetAllInProgress: initialState.isGetAllInProgress
            };
        default:
            return state;
    }
};

export default carsReducer;
