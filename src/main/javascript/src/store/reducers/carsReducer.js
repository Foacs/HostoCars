import { carsActionTypes as types } from 'actions';

// The reducer's initial state
const initialState = {
    cars: [],
    isCreateInError: false,
    isCreateInProgress: false,
    isDeleteInError: false,
    isDeleteInProgress: false,
    isGetInError: false,
    isGetInProgress: false,
    isGetByIdInError: false,
    isGetByIdInProgress: false,
    isUpdateInError: false,
    isUpdateInProgress: false
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
        case types.CREATE_CAR:
            return {
                ...state,
                isCreateInError: initialState.isCreateInError,
                isCreateInProgress: true
            };
        case types.CREATE_CAR_ERROR:
            return {
                ...state,
                isCreateInError: true,
                isCreateInProgress: initialState.isCreateInProgress
            };
        case types.CREATE_CAR_OK:
            return {
                ...state,
                isCreateInError: initialState.isCreateInError,
                isCreateInProgress: initialState.isCreateInProgress
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
        case types.GET_CAR_BY_ID:
            return {
                ...state,
                isGetInError: initialState.isGetInError,
                isGetInProgress: true
            };
        case types.GET_CAR_BY_ID_ERROR:
            return {
                ...state,
                isGetInError: true,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CAR_BY_ID_NOT_FOUND:
            return {
                ...state,
                cars: state.cars.filter(car => action.id !== car.id),
                isGetInError: initialState.isGetInError,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CAR_BY_ID_OK:
            return {
                ...state,
                cars: state.cars.map(car => car.id)
                        .includes(action.car.id)
                        ? state.cars.map(car => action.car.id === car.id ? action.car : car)
                        : [ ...state.cars, action.car ],
                isGetInError: initialState.isGetInError,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CARS:
            return {
                ...state,
                cars: initialState.cars,
                isGetInError: initialState.isGetInError,
                isGetInProgress: true
            };
        case types.GET_CARS_ERROR:
            return {
                ...state,
                cars: initialState.cars,
                isGetInError: true,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CARS_NO_CONTENT:
            return {
                ...state,
                cars: initialState.cars,
                isGetInError: initialState.isGetInError,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.GET_CARS_OK:
            return {
                ...state,
                cars: action.cars,
                isGetInError: initialState.isGetInError,
                isGetInProgress: initialState.isGetInProgress
            };
        case types.UPDATE_CAR:
            return {
                ...state,
                isUpdateInError: initialState.isUpdateInError,
                isUpdateInProgress: true
            };
        case types.UPDATE_CAR_ERROR:
            return {
                ...state,
                isUpdateInError: true,
                isUpdateInProgress: initialState.isUpdateInProgress
            };
        case types.UPDATE_CAR_OK:
            return {
                ...state,
                isUpdateInError: initialState.isUpdateInError,
                isUpdateInProgress: initialState.isUpdateInProgress
            };
        default:
            return state;
    }
};

export default carsReducer;
