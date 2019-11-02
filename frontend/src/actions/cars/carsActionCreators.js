import { carsActionTypes as types } from 'actions';
import axios from 'axios';

import { NO_CONTENT_STATUS, OK_STATUS, WEB_SERVICE_BASE_URL } from 'resources';

export const getCarsAction = sortedBy => {
    return dispatch => {
        dispatch(getCarsStart());

        return axios.get(`${WEB_SERVICE_BASE_URL}/cars/all?sortedBy=${sortedBy}`)
                    .then(res => {
                        if (res.status === OK_STATUS) {
                            dispatch(getCarsSuccess(res.data));
                        } else if (res.status === NO_CONTENT_STATUS) {
                            dispatch(getCarsSuccess([]));
                        }
                    })
                    .catch(() => {
                        dispatch(getCarsFailure());
             });
    };
};

const getCarsStart = () => ({
    type: types.GET_CARS
});

const getCarsSuccess = cars => ({
    type: types.GET_CARS_OK,
    cars: cars
});

const getCarsFailure = () => ({
    type: types.GET_CARS_ERROR
});

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

const addCarStart = () => ({
    type: types.ADD_CAR
});

const addCarSuccess = () => ({
    type: types.ADD_CAR_OK
});

const addCarFailure = () => ({
    type: types.ADD_CAR_ERROR
});

export const changeCarsSortOrderAction = sortedBy => {
    return dispatch => {
        dispatch(changeCarsSortOrder(sortedBy));
    };
};

const changeCarsSortOrder = sortedBy => ({
    type: types.CHANGE_CARS_SORT_ORDER,
    sortedBy
});
