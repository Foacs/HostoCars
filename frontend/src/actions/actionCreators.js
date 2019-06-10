import axios from 'axios';

import * as TYPES from './actionTypes';

import { WEB_SERVICE_BASE_URL } from 'resources';

export const logUserInAction = () => dispatch => {
    dispatch({
        type: TYPES.LOG_USER_IN
    });
};

export const logUserOutAction = () => dispatch => {
    dispatch({
        type: TYPES.LOG_USER_OUT
    });
};

export const getContactsAction = () => dispatch => {
    axios.get(`${WEB_SERVICE_BASE_URL}/contacts`)
    .then(res => {
        dispatch({
            type: TYPES.GET_CONTACTS,
            data: res.data
        });
    });
};

export const getContactByIdAction = id => dispatch => {
    axios.get(`${WEB_SERVICE_BASE_URL}/contacts/${id}`)
    .then(res => {
        dispatch({
            type: TYPES.GET_CONTACT_BY_ID,
            data: res.data
        });
    });
};

export const searchContactsAction = data => dispatch => {
    axios.post(`${WEB_SERVICE_BASE_URL}/contacts/search`, data)
    .then(res => {
        dispatch({
            type: TYPES.SEARCH_CONTACTS,
            data: res.data
        });
    });
};

export const saveContactAction = data => dispatch => {
    axios.post(`${WEB_SERVICE_BASE_URL}/contacts/save`, data)
    .then(dispatch({
        type: TYPES.SAVE_CONTACT
    }));
};

export const updateContactByIdAction = (id, data) => dispatch => {
    axios.put(`${WEB_SERVICE_BASE_URL}/contacts/${id}/update`, data)
    .then(dispatch({
        type: TYPES.UPDATE_CONTACT_BY_ID
    }));
};

export const updateContactPictureByIdAction = (id, url) => dispatch => {
    axios.put(`${WEB_SERVICE_BASE_URL}/contacts/${id}/updatePicture`, null, { params: { url }})
    .then(dispatch({
        type: TYPES.UPDATE_CONTACT_PICTURE_BY_ID
    }));
};

export const deleteContactByIdAction = id => dispatch => {
    axios.delete(`${WEB_SERVICE_BASE_URL}/contacts/${id}/delete`)
    .then(dispatch({
        type: TYPES.DELETE_CONTACT_BY_ID
    }));
};
