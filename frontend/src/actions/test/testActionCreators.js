import axios from 'axios';

import { testActionTypes as types } from 'actions';

import { WEB_SERVICE_BASE_URL } from 'resources';

export const logUserInAction = () => dispatch => {
    dispatch({
        type: types.LOG_USER_IN
    });
};

export const logUserOutAction = () => dispatch => {
    dispatch({
        type: types.LOG_USER_OUT
    });
};

export const getContactsAction = () => dispatch => {
    axios.get(`${WEB_SERVICE_BASE_URL}/contacts`)
    .then(res => {
        dispatch({
            type: types.GET_CONTACTS,
            data: res.data
        });
    });
};

export const getContactByIdAction = id => dispatch => {
    axios.get(`${WEB_SERVICE_BASE_URL}/contacts/${id}`)
    .then(res => {
        dispatch({
            type: types.GET_CONTACT_BY_ID,
            data: res.data
        });
    });
};

export const searchContactsAction = data => dispatch => {
    axios.post(`${WEB_SERVICE_BASE_URL}/contacts/search`, data)
    .then(res => {
        dispatch({
            type: types.SEARCH_CONTACTS,
            data: res.data
        });
    });
};

export const saveContactAction = data => dispatch => {
    axios.post(`${WEB_SERVICE_BASE_URL}/contacts/save`, data)
    .then(dispatch({
        type: types.SAVE_CONTACT
    }));
};

export const updateContactByIdAction = (id, data) => dispatch => {
    axios.put(`${WEB_SERVICE_BASE_URL}/contacts/${id}/update`, data)
    .then(dispatch({
        type: types.UPDATE_CONTACT_BY_ID
    }));
};

export const updateContactPictureByIdAction = (id, url) => dispatch => {
    axios.put(`${WEB_SERVICE_BASE_URL}/contacts/${id}/updatePicture`, null, { params: { url } })
    .then(dispatch({
        type: types.UPDATE_CONTACT_PICTURE_BY_ID
    }));
};

export const deleteContactByIdAction = id => dispatch => {
    axios.delete(`${WEB_SERVICE_BASE_URL}/contacts/${id}/delete`)
    .then(dispatch({
        type: types.DELETE_CONTACT_BY_ID
    }));
};
