import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import {
    changeCurrentPageAction,
    changeSelectedMenuIndexAction,
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

import { Button, Typography } from '@material-ui/core';

import StyledTestPage from './StyledTestPage';

class TestPage extends PureComponent {
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage(
            'Test',
            [{ label: "Accueil", link: "/" }]
        );

        changeSelectedMenuIndex(2);
    }

    render() {
        const {
            contactById,
            contacts,
            deleteContactById,
            getContactById,
            getContacts,
            logUserIn,
            logUserOut,
            isUserLogged,
            saveContact,
            searchContacts,
            searchedContacts,
            updateContactById,
            updateContactPictureById
        } = this.props;

        return (
            <StyledTestPage>
                <Typography variant='h1'>Test</Typography>

                <Button onClick={isUserLogged ? logUserOut : logUserIn}>{isUserLogged ? 'Log out' : 'Log in'}</Button>
                <Typography>{isUserLogged ? 'Logged' : 'Not logged'}</Typography>

                <Button onClick={getContacts}>Get contacts</Button>
                <Typography>Contacts: {contacts.length}</Typography>

                <Button onClick={() => getContactById(1)}>Get contact by ID</Button>
                <Typography>Contact By ID: {contactById ? contactById.name : ""}</Typography>

                <Button onClick={() => searchContacts({ name: 'Brice' })}>Search contacts with name Brice</Button>
                <Typography>Contacts with name Brice: {searchedContacts.length}</Typography>

                <Button onClick={() => saveContact({ name: 'Brice', nickname: 'Brice de Nice', number: 33684581274, favorite: true })}>Save new
                    contact
                </Button>

                <Button onClick={() => updateContactById(1, { name: 'Pas Brice' })}>Update contact</Button>

                <Button onClick={() => updateContactPictureById(2, 'G:/Images/MangaRock/2uxdBf1IK2.png')}>Update contact picture</Button>

                <Button onClick={() => deleteContactById(1)}>Delete contact</Button>
            </StyledTestPage>
        );
    }
}

const mapStateToProps = state => ({
    contactById: state.test.contactById,
    contacts: state.test.contacts,
    isUserLogged: state.test.isUserLogged,
    searchedContacts: state.test.searchedContacts
});

const mapDispatchToProps = dispatch => bindActionCreators({
        changeCurrentPage: changeCurrentPageAction,
        changeSelectedMenuIndex: changeSelectedMenuIndexAction,
        deleteContactById: deleteContactByIdAction,
        getContactById: getContactByIdAction,
        getContacts: getContactsAction,
        logUserIn: logUserInAction,
        logUserOut: logUserOutAction,
        saveContact: saveContactAction,
        searchContacts: searchContactsAction,
        updateContactById: updateContactByIdAction,
        updateContactPictureById: updateContactPictureByIdAction
    }, dispatch
);

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TestPage);
