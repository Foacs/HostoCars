import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box } from '@material-ui/core';
import SwaggerUI from 'swagger-ui-react';

import { changeCurrentPageAction, changeSelectedMenuIndexAction } from 'actions';
import { WEB_SERVICE_BASE_URL } from '../../resources';

import './ApiPage.scss';
import 'swagger-ui-react/swagger-ui.css';

/**
 * The API page component.
 *
 * @param {func} changeCurrentPage
 *     The {@link changeCurrentPageAction} action
 * @param {func} changeSelectedMenuIndex
 *     The {@link changeSelectedMenuIndexAction} action
 *
 * @class
 */
class ApiPage extends PureComponent {
    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage('API', []);
        changeSelectedMenuIndex(-1);
    }

    /**
     * Render method.
     */
    render() {
        return (<Box id='ApiPage'>
            <SwaggerUI docExpansion='none' url={`${WEB_SERVICE_BASE_URL}/api.yaml`} />
        </Box>);
    }
}

const mapDispatchToProps = (dispatch) => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction
}, dispatch);

ApiPage.propTypes = {
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(null, mapDispatchToProps)(ApiPage);
