import axios from 'axios';

import { carsActionTypes as types } from 'actions';
import { NO_CONTENT_STATUS, OK_STATUS, WEB_SERVICE_BASE_URL } from 'resources';

/**
 * Adds a new car.
 *
 * @param car
 *     The car to add
 *
 * @returns {function(): Promise} the action's promise
 */
export const addCarAction = car => {
    return (dispatch, getState) => {
        dispatch(addCarStart());

        return axios.post(`${WEB_SERVICE_BASE_URL}/cars/save`, car)
            .then(() => {
                dispatch(addCarSuccess());
                return dispatch(getCarsAction(getState().cars.sortedBy));
            })
            .catch(() => {
                dispatch(addCarFailure());
            });
    };
};

/**
 * Returns the action object for the ADD_CAR_ERROR action type.
 *
 * @returns {{type}} the action's object
 */
const addCarFailure = () => ({
    type: types.ADD_CAR_ERROR
});

/**
 * Returns the action object for the ADD_CAR action type.
 *
 * @returns {{type}} the action's object
 */
const addCarStart = () => ({
    type: types.ADD_CAR
});

/**
 * Returns the action object for the ADD_CAR_OK action type.
 *
 * @returns {{type}} the action's object
 */
const addCarSuccess = () => ({
    type: types.ADD_CAR_OK
});

/**
 * Returns the action object for the CHANGE_CARS_SORT_ORDER action type.
 *
 * @param sortedBy
 *     The new sort order
 *
 * @returns {{sortedBy, type}} the action's object
 */
const changeCarsSortOrder = sortedBy => ({
    type: types.CHANGE_CARS_SORT_ORDER,
    sortedBy
});

/**
 * Changes the cars sort order.
 *
 * @param sortedBy
 *     The new sort order
 *
 * @returns {function()} the action's function
 */
export const changeCarsSortOrderAction = sortedBy => {
    return dispatch => {
        dispatch(changeCarsSortOrder(sortedBy));
    };
};

/**
 * Deletes an existing car.
 *
 * @param id
 *     The car's ID to delete
 * @param history
 *     The navigator's history
 *
 * @returns {function(): Promise} the action's promise
 */
export const deleteCarAction = (id, history) => {
    return dispatch => {
        dispatch(deleteCarStart());

        return axios.delete(`${WEB_SERVICE_BASE_URL}/cars/${id}/delete`)
            .then(() => {
                dispatch(deleteCarSuccess(id));
                history.push('/cars');
            })
            .catch(() => {
                dispatch(deleteCarFailure());
            });
    };
};

/**
 * Returns the action object for the DELETE_CAR_ERROR action type.
 *
 * @returns {{type}} the action's object
 */
const deleteCarFailure = () => ({
    type: types.DELETE_CAR_ERROR
});

/**
 * Returns the action object for the DELETE_CAR action type.
 *
 * @returns {{type}} the action's object
 */
const deleteCarStart = () => ({
    type: types.DELETE_CAR
});

/**
 *Returns the action object for the DELETE_CAR_OK action type.
 *
 * @param id
 *     The deleted car's ID
 *
 * @returns {{id, type}} the action's object
 */
const deleteCarSuccess = id => ({
    id,
    type: types.DELETE_CAR_OK
});

/**
 * Edits an existing car.
 *
 * @param car
 *     The car to edit
 *
 * @returns {function(): Promise} the action's promise
 */
export const editCarAction = car => {
    return dispatch => {
        dispatch(editCarStart());

        return axios.put(`${WEB_SERVICE_BASE_URL}/cars/${car.id}/update`, car)
            .then(() => {
                dispatch(editCarSuccess());

                return dispatch(getCarAction(car.id));
            })
            .catch(() => {
                dispatch(editCarFailure());
            });
    };
};

/**
 * Returns the action object for the EDIT_CAR_ERROR action type.
 *
 * @returns {{type}} the action's object
 */
const editCarFailure = () => ({
    type: types.EDIT_CAR_ERROR
});

/**
 * Returns the action object for the EDIT_CAR action type.
 *
 * @returns {{type}} the action's object
 */
const editCarStart = () => ({
    type: types.EDIT_CAR
});

/**
 * Returns the action object for the EDIT_CAR_OK action type.
 *
 * @returns {{car, type}} the action's object
 */
const editCarSuccess = () => ({
    type: types.EDIT_CAR_OK
});

/**
 * Returns the car corresponding to the given ID.
 *
 * @param id
 *     The ID of the car to load
 *
 * @returns {function(): Promise} the action's promise
 */
export const getCarAction = id => {
    return dispatch => {
        dispatch(getCarStart());

        return axios.get(`${WEB_SERVICE_BASE_URL}/cars/${id}`)
            .then(res => {
                if (OK_STATUS === res.status) {
                    dispatch(getCarSuccess(res.data));
                } else if (NO_CONTENT_STATUS === res.status) {
                    dispatch(getCarNoContent(id));
                }
            })
            .catch(() => {
                dispatch(getCarFailure());
            });
    };
};

/**
 * Returns the action object for the GET_CAR_ERROR action type.
 *
 * @returns {{type}} the action's object
 */
const getCarFailure = () => ({
    type: types.GET_CAR_ERROR
});

/**
 * Returns the action object for the GET_CAR_NO_CONTENT action type.
 *
 * @returns {{id, type}} the action's object
 */
const getCarNoContent = id => ({
    id,
    type: types.GET_CAR_NO_CONTENT
});

/**
 * Returns the action object for the GET_CAR action type.
 *
 * @returns {{type}} the action's object
 */
const getCarStart = () => ({
    type: types.GET_CAR
});

/**
 * Returns the action object for the GET_CAR_OK action type.
 *
 * @param car
 *     The loaded car
 *
 * @returns {{car, type}} the action's object
 */
const getCarSuccess = car => ({
    car,
    type: types.GET_CAR_OK
});

/**
 * Returns the list of all cars.
 *
 * @param sortedBy
 *     The sort clause
 *
 * @returns {function(): Promise} the action's promise
 */
export const getCarsAction = sortedBy => {
    return dispatch => {
        dispatch(getCarsStart());

        return axios.get(`${WEB_SERVICE_BASE_URL}/cars/all?sortedBy=${sortedBy}`)
            .then(res => {
                if (OK_STATUS === res.status) {
                    dispatch(getCarsSuccess(res.data));
                } else if (NO_CONTENT_STATUS === res.status) {
                    dispatch(getCarsSuccess([]));
                }
            })
            .catch(() => {
                dispatch(getCarsFailure());
            });
    };
};

/**
 * Returns the action object for the GET_CARS_ERROR action type.
 *
 * @returns {{type}} the action's object
 */
const getCarsFailure = () => ({
    type: types.GET_CARS_ERROR
});

/**
 * Returns the action object for the GET_CARS action type.
 *
 * @returns {{type}} the action's object
 */
const getCarsStart = () => ({
    type: types.GET_CARS
});

/**
 * Returns the action object for the GET_CARS_OK action type.
 *
 * @param cars
 *     The loaded cars
 *
 * @returns {{cars, type}} the action's object
 */
const getCarsSuccess = cars => ({
    type: types.GET_CARS_OK,
    cars
});
