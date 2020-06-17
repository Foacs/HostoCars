import React from 'react';
import axios from 'axios';

import { enqueueNotificationAction, interventionsActionTypes as types } from 'actions';
import { ErrorNotificationContent } from 'components';
import { NO_CONTENT_STATUS, OK_STATUS, WEB_SERVICE_BASE_URL } from 'resources';

/**
 * Loads all existing interventions and returns the action promise.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @returns {Promise} the action promise
 */
export const getInterventionsAction = () => {
    return (dispatch, getState) => {
        dispatch(getInterventionsStart());

        return axios.get(`${WEB_SERVICE_BASE_URL}/interventions/all?sortedBy=${getState().interventions.sortedBy}`)
                .then(res => {
                    if (OK_STATUS === res.status) {
                        dispatch(getInterventionsSuccess(res.data));
                    } else if (NO_CONTENT_STATUS === res.status) {
                        dispatch(getInterventionsNoContent());
                    }
                })
                .catch(e => {
                    dispatch(getInterventionsFailure());
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
 * Returns the action object for the {@link GET_INTERVENTIONS} action type.
 *
 * @returns {object} the action object
 */
const getInterventionsStart = () => ({
    type: types.GET_INTERVENTIONS
});

/**
 * Returns the action object for the {@link GET_INTERVENTIONS_NO_CONTENT} action type.
 *
 * @returns {object} the action object
 */
const getInterventionsNoContent = () => ({
    type: types.GET_INTERVENTIONS_NO_CONTENT
});

/**
 * Returns the action object for the {@link GET_INTERVENTIONS_OK} action type.
 *
 * @param {object[]} interventions
 *     The loaded interventions
 *
 * @returns {object} the action object
 */
const getInterventionsSuccess = (interventions) => ({
    type: types.GET_INTERVENTIONS_OK,
    interventions
});

/**
 * Returns the action object for the {@link GET_INTERVENTIONS_ERROR} action type.
 *
 * @returns {object} the action object
 */
const getInterventionsFailure = () => ({
    type: types.GET_INTERVENTIONS_ERROR
});
