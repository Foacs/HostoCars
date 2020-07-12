import React from 'react';
import axios from 'axios';

import { carsActionTypes as types, enqueueNotificationAction } from 'actions';
import { ErrorNotificationContent } from 'components';
import { NO_CONTENT_STATUS, OK_STATUS, WEB_SERVICE_BASE_URL } from 'resources';

/**
 * Adds a new car and returns the action promise.
 * <br />
 * If the operation is successful, a success notification is shown and the list of cars is updated.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {object} car
 *     The car to add
 *
 * @returns {Promise} the action promise
 */
export const addCarAction = (car) => {
    return (dispatch) => {
        dispatch(addCarStart());

        return axios.post(`${WEB_SERVICE_BASE_URL}/cars/save`, car)
                .then(() => {
                    dispatch(addCarSuccess());
                    dispatch(enqueueNotificationAction({
                        message: 'Voiture ajoutée avec succès.',
                        options: {
                            variant: 'success'
                        }
                    }));
                    return dispatch(getCarsAction());
                })
                .catch(e => {
                    dispatch(addCarFailure());
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors de l\'ajout d\'une voiture.',
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
 * Returns the action object for the {@link ADD_CAR} action type.
 *
 * @returns {object} the action object
 */
const addCarStart = () => ({
    type: types.ADD_CAR
});

/**
 * Returns the action object for the {@link ADD_CAR_OK} action type.
 *
 * @returns {object} the action object
 */
const addCarSuccess = () => ({
    type: types.ADD_CAR_OK
});

/**
 * Returns the action object for the {@link ADD_CAR_ERROR} action type.
 *
 * @returns {object} the action object
 */
const addCarFailure = () => ({
    type: types.ADD_CAR_ERROR
});

/**
 * Changes the cars sort order and updates the list of cars.
 *
 * @param {string} sortedBy
 *     The new sort order
 */
export const changeCarsSortOrderAction = (sortedBy) => {
    return dispatch => {
        dispatch(changeCarsSortOrder(sortedBy));
        getCarsAction()(dispatch, () => {
            return { cars: { sortedBy } };
        });
    };
};

/**
 * Returns the action object for the {@link CHANGE_CARS_SORT_ORDER} action type.
 *
 * @param {string | string[]} sortedBy
 *     The new sort order
 *
 * @returns {object} the action object
 */
const changeCarsSortOrder = (sortedBy) => ({
    type: types.CHANGE_CARS_SORT_ORDER,
    sortedBy
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

        return axios.delete(`${WEB_SERVICE_BASE_URL}/cars/${id}/delete`)
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
 * Edits an existing car and returns the action promise.
 * <br />
 * If the operation is successful, a success notification is shown and the car is reloaded.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {object} car
 *     The car to edit
 *
 * @returns {Promise} the action promise
 */
export const editCarAction = (car) => {
    return dispatch => {
        dispatch(editCarStart());

        return axios.put(`${WEB_SERVICE_BASE_URL}/cars/update`, car)
                .then(() => {
                    dispatch(editCarSuccess());
                    dispatch(enqueueNotificationAction({
                        message: 'Voiture éditée avec succès.',
                        options: {
                            variant: 'success'
                        }
                    }));
                    return dispatch(getCarAction(car.id));
                })
                .catch(e => {
                    dispatch(editCarFailure());
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
 * Returns the action object for the {@link EDIT_CAR} action type.
 *
 * @returns {object} the action object
 */
const editCarStart = () => ({
    type: types.EDIT_CAR
});

/**
 * Returns the action object for the {@link EDIT_CAR_OK} action type.
 *
 * @returns {object} the action object
 */
const editCarSuccess = () => ({
    type: types.EDIT_CAR_OK
});

/**
 * Returns the action object for the {@link EDIT_CAR_ERROR} action type.
 *
 * @returns {object} the action object
 */
const editCarFailure = () => ({
    type: types.EDIT_CAR_ERROR
});

/**
 * Loads an existing car and returns the action promise.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {number} id
 *     The ID of the car to load
 *
 * @returns {Promise} the action promise
 */
export const getCarAction = (id) => {
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
                .catch(e => {
                    dispatch(getCarFailure());
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
 * Returns the action object for the {@link GET_CAR} action type.
 *
 * @returns {object} the action object
 */
const getCarStart = () => ({
    type: types.GET_CAR
});

/**
 * Returns the action object for the {@link GET_CAR_NO_CONTENT} action type.
 *
 * @param {number} id
 *     The ID of the inexistant car
 *
 * @returns {object} the action object
 */
const getCarNoContent = (id) => ({
    id,
    type: types.GET_CAR_NO_CONTENT
});

/**
 * Returns the action object for the {@link GET_CAR_OK} action type.
 *
 * @param {object} car
 *     The loaded car
 *
 * @returns {object} the action object
 */
const getCarSuccess = (car) => ({
    car,
    type: types.GET_CAR_OK
});

/**
 * Returns the action object for the {@link GET_CAR_ERROR} action type.
 *
 * @returns {object} the action object
 */
const getCarFailure = () => ({
    type: types.GET_CAR_ERROR
});

/**
 * Loads all existing cars and returns the action promise.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @returns {Promise} the action promise
 */
export const getCarRegistrationsAction = () => {
    return dispatch => {
        dispatch(getCarRegistrationsStart());

        return axios.get(`${WEB_SERVICE_BASE_URL}/cars/registrations`)
                .then(res => {
                    if (OK_STATUS === res.status) {
                        dispatch(getCarRegistrationsSuccess(res.data));
                    } else if (NO_CONTENT_STATUS === res.status) {
                        dispatch(getCarRegistrationsNoContent());
                    }
                })
                .catch(e => {
                    dispatch(getCarRegistrationsFailure());
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors du chargement des numéros d\'immatriculation.',
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
 * Returns the action object for the {@link GET_CAR_REGISTRATIONS} action type.
 *
 * @returns {object} the action object
 */
const getCarRegistrationsStart = () => ({
    type: types.GET_CAR_REGISTRATIONS
});

/**
 * Returns the action object for the {@link GET_CAR_REGISTRATIONS_NO_CONTENT} action type.
 *
 * @returns {object} the action object
 */
const getCarRegistrationsNoContent = () => ({
    type: types.GET_CAR_REGISTRATIONS_NO_CONTENT
});

/**
 * Returns the action object for the {@link GET_CAR_REGISTRATIONS_OK} action type.
 *
 * @param {string[]} registrations
 *     The loaded car registration numbers
 *
 * @returns {object} the action object
 */
const getCarRegistrationsSuccess = (registrations) => ({
    registrations,
    type: types.GET_CAR_REGISTRATIONS_OK
});

/**
 * Returns the action object for the {@link GET_CAR_REGISTRATIONS_ERROR} action type.
 *
 * @returns {object} the action object
 */
const getCarRegistrationsFailure = () => ({
    type: types.GET_CAR_REGISTRATIONS_ERROR
});

/**
 * Loads all existing cars and returns the action promise.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @returns {Promise} the action promise
 */
export const getCarsAction = () => {
    return (dispatch, getState) => {
        dispatch(getCarsStart());

        return axios.get(`${WEB_SERVICE_BASE_URL}/cars/all?sortedBy=${getState().cars.sortedBy}`)
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
