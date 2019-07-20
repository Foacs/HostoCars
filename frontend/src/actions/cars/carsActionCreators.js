import axios from 'axios';

import { carsActionTypes as types } from 'actions';

import { WEB_SERVICE_BASE_URL } from 'resources';

export const getCarsAction = sortedBy => dispatch => {
    dispatch({
        type: types.GET_CARS
    });

    axios.get(`${WEB_SERVICE_BASE_URL}/cars/all?sortedBy=${sortedBy}`)
    .then(res => {
        dispatch({
            type: types.GET_CARS_OK,
            data: res.data
        });
    })
    .catch(() => {
        dispatch({
            type: types.GET_CARS_ERROR
        });
    });
};

export const changeCarsSortOrderAction = sortedBy => dispatch => {
    dispatch({
        type: types.CHANGE_CARS_SORT_ORDER,
        sortedBy
    });
};
