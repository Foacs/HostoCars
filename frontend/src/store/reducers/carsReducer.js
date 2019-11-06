import { carsActionTypes as types } from 'actions';

const initialState = {
    cars: [],
    isGetAllInProgress: false,
    isGetAllInError: false,
    isGetInProgress: false,
    isGetInError: false,
    isAddInProgress: false,
    isAddInError: false,
    isEditInProgress: false,
    isEditInError: false,
    isDeleteInProgress: false,
    isDeleteInError: false,
    sortedBy: 'registration'
};

const carsReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.GET_CARS:
            return {
                ...state,
                cars: initialState.cars,
                isGetAllInProgress: true,
                isGetAllInError: initialState.isGetAllInError
            };
        case types.GET_CARS_OK:
            return {
                ...state,
                cars: action.cars,
                isGetAllInProgress: initialState.isGetAllInProgress,
                isGetAllInError: initialState.isGetAllInError
            };
        case types.GET_CARS_ERROR:
            return {
                ...state,
                cars: initialState.cars,
                isGetAllInProgress: initialState.isGetAllInProgress,
                isGetAllInError: true
            };
        case types.ADD_CAR:
            return {
                ...state,
                isAddInProgress: true,
                isAddInError: initialState.isAddInError
            };
        case types.ADD_CAR_OK:
            return {
                ...state,
                isAddInProgress: initialState.isAddInProgress,
                isAddInError: initialState.isAddInError
            };
        case types.ADD_CAR_ERROR:
            return {
                ...state,
                isAddInProgress: initialState.isAddInProgress,
                isAddInError: true
            };
        case types.EDIT_CAR:
            return {
                ...state,
                isEditInProgress: true,
                isEditInError: initialState.isEditInError
            };
        case types.EDIT_CAR_OK:
            return {
                ...state,
                isEditInProgress: initialState.isEditInProgress,
                isEditInError: initialState.isEditInError
            };
        case types.EDIT_CAR_ERROR:
            return {
                ...state,
                isEditInProgress: initialState.isEditInProgress,
                isEditInError: true
            };
        case types.DELETE_CAR:
            return {
                ...state,
                isDeleteInProgress: true,
                isDeleteInError: initialState.isDeleteInError
            };
        case types.DELETE_CAR_OK:
            return {
                ...state,
                cars: state.cars.filter(car => action.id !== car.id),
                isDeleteInProgress: initialState.isDeleteInProgress,
                isDeleteInError: initialState.isDeleteInError
            };
        case types.DELETE_CAR_ERROR:
            return {
                ...state,
                isDeleteInProgress: initialState.isDeleteInProgress,
                isDeleteInError: true
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

export default carsReducer;
