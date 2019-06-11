import React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import logo from '../../resources/logo.svg';

import {
    deleteContactByIdAction,
    getContactByIdAction,
    getContactsAction,
    logUserInAction,
    logUserOutAction,
    saveContactAction,
    searchContactsAction,
    updateContactByIdAction,
    updateContactPictureByIdAction
} from 'actions';

import StyledTestPage from './StyledTestPage';

function TestPage({ isUserLogged, contacts, contactById, searchedContacts, logUserIn, logUserOut, getContacts, getContactById, searchContacts, saveContact, updateContactById, updateContactPictureById, deleteContactById }) {
    return (
        <StyledTestPage>
            <header className="header">
                <img src={logo} className="logo" alt="logo" />
                <p>Test</p>

                <button onClick={isUserLogged ? logUserOut : logUserIn}>{isUserLogged ? 'Log out' : 'Log in'}</button>
                <span>{isUserLogged ? 'Logged' : 'Not logged'}</span>

                <button onClick={getContacts}>Get contacts</button>
                <p>Contacts: {contacts.length}</p>

                <button onClick={() => getContactById(1)}>Get contact by ID</button>
                <p>Contact By ID: {contactById ? contactById.name : ""}</p>

                <button onClick={() => searchContacts({ name: 'Brice' })}>Search contacts with name Brice</button>
                <p>Contacts with name Brice: {searchedContacts.length}</p>

                <button onClick={() => saveContact({ name: 'Brice', nickname: 'Brice de Nice', number: 33684581274, favorite: true })}>Save new
                    contact
                </button>

                <button onClick={() => updateContactById(1, { name: 'Tamère' })}>Update contact</button>

                <button onClick={() => updateContactPictureById(2, 'G:/Images/Hinata-chan.png')}>Update contact picture</button>

                <button onClick={() => deleteContactById(1)}>Delete contact</button>
            </header>
        </StyledTestPage>
    );
}

const mapStateToProps = state => ({
    isUserLogged: state.isUserLogged,
    contacts: state.contacts,
    contactById: state.contactById,
    searchedContacts: state.searchedContacts
});

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            logUserIn: logUserInAction,
            logUserOut: logUserOutAction,
            getContacts: getContactsAction,
            getContactById: getContactByIdAction,
            searchContacts: searchContactsAction,
            saveContact: saveContactAction,
            updateContactById: updateContactByIdAction,
            updateContactPictureById: updateContactPictureByIdAction,
            deleteContactById: deleteContactByIdAction
        },
        dispatch,
    );

export default connect(
    mapStateToProps,
    mapDispatchToProps,
)(TestPage);
