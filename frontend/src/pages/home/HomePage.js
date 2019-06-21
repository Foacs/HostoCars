import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { changeCurrentPageAction, changeSelectedMenuIndexAction } from "actions";

import StyledHomePage from './StyledHomePage';

class HomePage extends PureComponent {
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage('Accueil', []);

        changeSelectedMenuIndex(0)
    }

    render() {
        return (
            <StyledHomePage />
        );
    }
}

const mapDispatchToProps = dispatch => bindActionCreators({
        changeCurrentPage: changeCurrentPageAction,
        changeSelectedMenuIndex: changeSelectedMenuIndexAction,
    }, dispatch
);

export default connect(
    null,
    mapDispatchToProps
)(HomePage);
