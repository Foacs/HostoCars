import React from 'react';
import axios from 'axios';

import { carsActionTypes as types, enqueueNotificationAction } from 'actions';
import { ErrorNotificationContent } from 'components';
import { extractEntityIdFromUrl, NO_CONTENT_STATUS, NOT_FOUND_STATUS, OK_STATUS, WEB_SERVICE_BASE_URL } from 'resources';

const CARS_SERVICE_BASE_URL = `${WEB_SERVICE_BASE_URL}/cars`;

/**
 * Creates a new car and returns the action promise.
 * <br />
 * If the operation is successful, calls the service given in the response location header.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {object} car
 *     The car to create
 *
 * @returns {Promise} the action promise
 */
export const createCarAction = (car) => {
    return (dispatch) => {
        dispatch(createCarStart());

        return axios.post(CARS_SERVICE_BASE_URL, car)
                .then(res => {
                    dispatch(createCarSuccess());
                    dispatch(enqueueNotificationAction({
                        message: 'Voiture ajoutée avec succès.',
                        options: {
                            variant: 'success'
                        }
                    }));
                    return dispatch(getCarByIdAction(extractEntityIdFromUrl(res.headers.location)));
                })
                .catch(e => {
                    dispatch(createCarFailure());
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors de l\'ajout d\'une voiture.',
                        options: {
                            content: <ErrorNotificationContent error={e} timestamp={new Date().toLocaleString()} />,
                            persist: true,
                            variant: 'error'
                        }
                    }));
                });
    };
};

/**
 * Returns the action object for the {@link CREATE_CAR} action type.
 *
 * @returns {object} the action object
 */
const createCarStart = () => ({
    type: types.CREATE_CAR
});

/**
 * Returns the action object for the {@link CREATE_CAR_OK} action type.
 *
 * @returns {object} the action object
 */
const createCarSuccess = () => ({
    type: types.CREATE_CAR_OK
});

/**
 * Returns the action object for the {@link CREATE_CAR_ERROR} action type.
 *
 * @returns {object} the action object
 */
const createCarFailure = () => ({
    type: types.CREATE_CAR_ERROR
});

/**
 * Deletes a car and returns the action promise.
 * <br />
 * If the operation is successful, a success notification is shown.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {number} id
 *     The ID of the car to delete
 *
 * @returns {Promise} the action promise
 */
export const deleteCarAction = (id) => {
    return dispatch => {
        dispatch(deleteCarStart());

        return axios.delete(`${CARS_SERVICE_BASE_URL}/${id}`)
                .then(() => {
                    dispatch(deleteCarSuccess(id));
                    dispatch(enqueueNotificationAction({
                        message: 'Voiture supprimée avec succès.',
                        options: {
                            variant: 'success'
                        }
                    }));
                })
                .catch(e => {
                    dispatch(deleteCarFailure());
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors de la suppression d\'une voiture.',
                        options: {
                            content: <ErrorNotificationContent error={e} />,
                            persist: true,
                            variant: 'error'
                        }
                    }));
                });
    };
};

/**
 * Returns the action object for the {@link DELETE_CAR} action type.
 *
 * @returns {object} the action object
 */
const deleteCarStart = () => ({
    type: types.DELETE_CAR
});

/**
 * Returns the action object for the {@link DELETE_CAR_OK} action type.
 *
 * @param {number} id
 *     The ID of the deleted car
 *
 * @returns {object} the action object
 */
const deleteCarSuccess = (id) => ({
    id,
    type: types.DELETE_CAR_OK
});

/**
 * Returns the action object for the {@link DELETE_CAR_ERROR} action type.
 *
 * @returns {object} the action object
 */
const deleteCarFailure = () => ({
    type: types.DELETE_CAR_ERROR
});

/**
 * Loads an existing car by its ID and returns the action promise.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {number} id
 *     The ID of the car to load
 *
 * @returns {Promise} the action promise
 */
export const getCarByIdAction = (id) => {
    return dispatch => {
        dispatch(getCarByIdStart());

        return axios.get(`${CARS_SERVICE_BASE_URL}/${id}`)
                .then(res => {
                    if (OK_STATUS === res.status) {
                        dispatch(getCarByIdSuccess(res.data));
                    } else if (NOT_FOUND_STATUS === res.status) {
                        dispatch(getCarByIdNotFound(id));
                    }
                })
                .catch(e => {
                    dispatch(getCarByIdFailure());
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors du chargement d\'une voiture.',
                        options: {
                            content: <ErrorNotificationContent error={e} />,
                            persist: true,
                            variant: 'error'
                        }
                    }));
                });
    };
};

/**
 * Returns the action object for the {@link GET_CAR_BY_ID} action type.
 *
 * @returns {object} the action object
 */
const getCarByIdStart = () => ({
    type: types.GET_CAR_BY_ID
});

/**
 * Returns the action object for the {@link GET_CAR_BY_ID_OK} action type.
 *
 * @param {object} car
 *     The loaded car
 *
 * @returns {object} the action object
 */
const getCarByIdSuccess = (car) => ({
    car,
    type: types.GET_CAR_BY_ID_OK
});

/**
 * Returns the action object for the {@link GET_CAR_BY_ID_NOT_FOUND} action type.
 *
 * @param {number} id
 *     The ID of the inexistant car
 *
 * @returns {object} the action object
 */
const getCarByIdNotFound = (id) => ({
    id,
    type: types.GET_CAR_BY_ID_NOT_FOUND
});

/**
 * Returns the action object for the {@link GET_CAR_BY_ID_ERROR} action type.
 *
 * @returns {object} the action object
 */
const getCarByIdFailure = () => ({
    type: types.GET_CAR_BY_ID_ERROR
});

/**
 * Loads all existing cars and returns the action promise.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @returns {Promise} the action promise
 */
export const getCarsAction = () => {
    return dispatch => {
        dispatch(getCarsStart());

        return axios.get(CARS_SERVICE_BASE_URL)
                .then(res => {
                    if (OK_STATUS === res.status) {
                        dispatch(getCarsSuccess(res.data));
                    } else if (NO_CONTENT_STATUS === res.status) {
                        dispatch(getCarsNoContent());
                    }
                })
                .catch(e => {
                    dispatch(getCarsFailure());
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors du chargement des voitures.',
                        options: {
                            content: <ErrorNotificationContent error={e} />,
                            persist: true,
                            variant: 'error'
                        }
                    }));
                });
    };
};

/**
 * Returns the action object for the {@link GET_CARS} action type.
 *
 * @returns {object} the action object
 */
const getCarsStart = () => ({
    type: types.GET_CARS
});

/**
 * Returns the action object for the {@link GET_CARS_NO_CONTENT} action type.
 *
 * @returns {object} the action object
 */
const getCarsNoContent = () => ({
    type: types.GET_CARS_NO_CONTENT
});

/**
 * Returns the action object for the {@link GET_CARS_OK} action type.
 *
 * @param {object[]} cars
 *     The loaded cars
 *
 * @returns {object} the action object
 */
const getCarsSuccess = (cars) => ({
    type: types.GET_CARS_OK,
    cars
});

/**
 * Returns the action object for the {@link GET_CARS_ERROR} action type.
 *
 * @returns {object} the action object
 */
const getCarsFailure = () => ({
    type: types.GET_CARS_ERROR
});

/**
 * Updates an existing car and returns the action promise.
 * <br />
 * If the operation is successful, a success notification is shown and the car is reloaded.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {object} car
 *     The car to update
 *
 * @returns {Promise} the action promise
 */
export const updateCarAction = (car) => {
    return dispatch => {
        dispatch(updateCarStart());

        return axios.put(CARS_SERVICE_BASE_URL, car)
                .then(() => {
                    dispatch(updateCarSuccess());
                    dispatch(enqueueNotificationAction({
                        message: 'Voiture éditée avec succès.',
                        options: {
                            variant: 'success'
                        }
                    }));
                    return dispatch(getCarByIdAction(car.id));
                })
                .catch(e => {
                    dispatch(updateCarFailure());
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors de l\'édition d\'une voiture.',
                        options: {
                            content: <ErrorNotificationContent error={e} />,
                            persist: true,
                            variant: 'error'
                        }
                    }));
                });
    };
};

/**
 * Returns the action object for the {@link UPDATE_CAR} action type.
 *
 * @returns {object} the action object
 */
const updateCarStart = () => ({
    type: types.UPDATE_CAR
});

/**
 * Returns the action object for the {@link UPDATE_CAR_OK} action type.
 *
 * @returns {object} the action object
 */
const updateCarSuccess = () => ({
    type: types.UPDATE_CAR_OK
});

/**
 * Returns the action object for the {@link UPDATE_CAR_ERROR} action type.
 *
 * @returns {object} the action object
 */
const updateCarFailure = () => ({
    type: types.UPDATE_CAR_ERROR
});
