import { carsActionTypes as types } from 'actions';

const initialState = {
    cars: [],
    isGetAllInProgress: false,
    isGetAllInError: false,
    isGetInProgress: false,
    isGetInError: false,
    isAddInProgress: false,
    isAddInError: false,
    sortedBy: 'registration',
    currentCar: null
};

const testReducer = (state = initialState, action) => {
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
        case types.GET_CAR:
            return {
                ...state,
                isGetInProgress: true,
                isGetInError: initialState.isGetInError,
                currentCar: initialState.currentCar
            };
        case types.GET_CAR_OK:
            return {
                ...state,
                cars: null === action.car ? state.cars.filter(car => action.id !== car.id) : state.cars.find(car => action.id === car.id)
                    ? state.cars.map(car => action.id === car.id ? action.car : car) : state.cars.concat([ action.car ]),
                isGetInProgress: initialState.isGetInProgress,
                isGetInError: initialState.isGetInError,
                currentCar: action.car
            };
        case types.GET_CAR_ERROR:
            return {
                ...state,
                isGetInProgress: initialState.isGetInProgress,
                isGetInError: true
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
        case types.CHANGE_CARS_SORT_ORDER:
            return {
                ...state,
                sortedBy: action.sortedBy
            };
        case types.CHANGE_CURRENT_CAR:
            return {
                ...state,
                currentCar: action.car
            };
        default:
            return state;
    }
};

export default testReducer;
