import { carsActionTypes as types } from 'actions';
import axios from 'axios';

import { NO_CONTENT_STATUS, OK_STATUS, WEB_SERVICE_BASE_URL } from 'resources';

export const getCarsAction = sortedBy => dispatch => {
    dispatch({
        type: types.GET_CARS
    });

    axios.get(`${WEB_SERVICE_BASE_URL}/cars/all?sortedBy=${sortedBy}`)
         .then(res => {
             if (res.status === OK_STATUS) {
                 dispatch({
                     type: types.GET_CARS_OK,
                     data: res.data
                 });
             } else if (res.status === NO_CONTENT_STATUS) {
                 dispatch({
                     type: types.GET_CARS_OK,
                     data: []
                 });
             }
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
