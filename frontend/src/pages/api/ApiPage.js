import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import SwaggerUI from 'swagger-ui-react';

import { updateCurrentPageAction, updateMenuItemsAction, updateSelectedMenuIndexAction } from 'actions';
import { Page } from 'components';
import { WEB_SERVICE_BASE_URL } from '../../resources';

import './ApiPage.scss';
import 'swagger-ui-react/swagger-ui.css';

/**
 * The API page component.
 *
 * @param {func} updateCurrentPage
 *     The {@link updateCurrentPageAction} action
 * @param {func} updateMenuItems
 *     The {@link updateMenuItemsAction} action
 * @param {func} updateSelectedMenuIndex
 *     The {@link updateSelectedMenuIndexAction} action
 *
 * @class
 */
class ApiPage extends PureComponent {
    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { updateCurrentPage, updateMenuItems, updateSelectedMenuIndex } = this.props;

        updateCurrentPage('API', []);
        updateMenuItems([]);
        updateSelectedMenuIndex(-1);
    }

    /**
     * Render method.
     */
    render() {
        return (<Page id='ApiPage'>
            <SwaggerUI docExpansion='none' url={`${WEB_SERVICE_BASE_URL}/api.yaml`} />
        </Page>);
    }
}

const mapDispatchToProps = (dispatch) => bindActionCreators({
    updateCurrentPage: updateCurrentPageAction,
    updateMenuItems: updateMenuItemsAction,
    updateSelectedMenuIndex: updateSelectedMenuIndexAction
}, dispatch);

ApiPage.propTypes = {
    updateCurrentPage: PropTypes.func.isRequired,
    updateMenuItems: PropTypes.func.isRequired,
    updateSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(null, mapDispatchToProps)(ApiPage);
