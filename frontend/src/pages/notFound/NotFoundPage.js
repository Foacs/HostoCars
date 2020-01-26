import React, { PureComponent } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import { Box, Typography } from '@material-ui/core';
import { SentimentDissatisfiedRounded as SmileyIcon } from '@material-ui/icons';

import { changeCurrentPageAction, changeSelectedMenuIndexAction } from 'actions';

import './NotFoundPage.scss';

/**
 * The not found page component.
 *
 * @param {func} changeCurrentPage
 *     The {@link changeCurrentPageAction} action
 * @param {func} changeSelectedMenuIndex
 *     The {@link changeSelectedMenuIndexAction} action
 *
 * @class
 */
class NotFoundPage extends PureComponent {
    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage('Page introuvable', []);

        changeSelectedMenuIndex(-1);
    }

    /**
     * Render method.
     */
    render() {
        return <Box id='NotFoundPage'>
            <SmileyIcon className='SmileyIcon' />

            <Typography className='Label' variant='h1'>
                Page introuvable
            </Typography>
        </Box>;
    }
}

const mapDispatchToProps = (dispatch) => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction
}, dispatch);

NotFoundPage.propTypes = {
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(null, mapDispatchToProps)(NotFoundPage);
