import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Typography } from '@material-ui/core';
import { SentimentDissatisfiedRounded as SmileyIcon } from '@material-ui/icons';

import StyledNotFoundPage from './StyledNotFoundPage';

import { changeCurrentPageAction, changeSelectedMenuIndexAction } from 'actions';

class NotFoundPage extends PureComponent {
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage('Page introuvable', []);

        changeSelectedMenuIndex(-1);
    }

    render() {
        return (
            <StyledNotFoundPage className='NotFoundPage'>
                <SmileyIcon className='NotFoundPage-SmileyIcon' />

                <Typography className='NotFoundPage-Label' variant='h1'>Page introuvable</Typography>
            </StyledNotFoundPage>
        );
    }
}

const mapDispatchToProps = dispatch => bindActionCreators({
        changeCurrentPage: changeCurrentPageAction,
        changeSelectedMenuIndex: changeSelectedMenuIndexAction
    }, dispatch
);

NotFoundPage.propTypes = {
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(
    null,
    mapDispatchToProps
)(NotFoundPage);
