import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { changeCurrentPageAction, changeSelectedMenuIndexAction } from "actions";

import { Typography } from '@material-ui/core';
import { SentimentDissatisfiedRounded } from '@material-ui/icons';

import StyledNotFoundPage from './StyledNotFoundPage';

class NotFoundPage extends PureComponent {
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage(
            'Page introuvable',
            []
        );

        changeSelectedMenuIndex(-1);
    }

    render() {
        return (
            <StyledNotFoundPage>
                <SentimentDissatisfiedRounded />

                <Typography variant="h1">Page introuvable</Typography>
            </StyledNotFoundPage>
        );
    }
}

const mapDispatchToProps = dispatch => bindActionCreators({
        changeCurrentPage: changeCurrentPageAction,
        changeSelectedMenuIndex: changeSelectedMenuIndexAction
    }, dispatch
);

export default connect(
    null,
    mapDispatchToProps
)(NotFoundPage);
