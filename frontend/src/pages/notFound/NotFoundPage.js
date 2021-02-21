import React, { PureComponent } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import { Typography } from '@material-ui/core';
import { SentimentDissatisfiedRounded as SmileyIcon } from '@material-ui/icons';

import { updateCurrentPageAction, updateMenuItemsAction, updateSelectedMenuIndexAction } from 'actions';
import { Page } from 'components';

import './NotFoundPage.scss';

/**
 * The not found page component.
 *
 * @param {string} label
 *     The label
 * @param {func} updateCurrentPage
 *     The {@link updateCurrentPageAction} action
 * @param {func} updateMenuItems
 *     The {@link updateMenuItemsAction} action
 * @param {func} updateSelectedMenuIndex
 *     The {@link updateSelectedMenuIndexAction} action
 *
 * @class
 */
class NotFoundPage extends PureComponent {
    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const {
            updateCurrentPage,
            updateMenuItems,
            updateSelectedMenuIndex
        } = this.props;

        updateCurrentPage('Page introuvable', []);
        updateMenuItems([]);
        updateSelectedMenuIndex(-1);
    }

    /**
     * Render method.
     */
    render() {
        const { label } = this.props;

        return <Page id='NotFoundPage'>
            <SmileyIcon className='SmileyIcon' />

            <Typography className='Label' variant='h1'>
                {label}
            </Typography>
        </Page>;
    }
}

const mapDispatchToProps = (dispatch) => bindActionCreators({
    updateCurrentPage: updateCurrentPageAction,
    updateMenuItems: updateMenuItemsAction,
    updateSelectedMenuIndex: updateSelectedMenuIndexAction
}, dispatch);

NotFoundPage.propTypes = {
    label: PropTypes.string,
    updateCurrentPage: PropTypes.func.isRequired,
    updateMenuItems: PropTypes.func.isRequired,
    updateSelectedMenuIndex: PropTypes.func.isRequired
};

NotFoundPage.defaultProps = {
    label: 'Page introuvable'
};

export default connect(null, mapDispatchToProps)(NotFoundPage);
