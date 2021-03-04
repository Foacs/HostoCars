import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box } from '@material-ui/core';

import { getCarsAction } from 'actions';

import './Application.scss';

/**
 * The application component.
 *
 * @param {func} getCars
 *     The {@link getCarsAction} action
 *
 * @class
 */
class Application extends PureComponent {
    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { getCars } = this.props;

        getCars();
    }

    /**
     * Render method.
     */
    render() {
        const { children } = this.props;

        return (<Box id='Application'>
            {children}
        </Box>);
    }
}

const mapDispatchToProps = (dispatch) => bindActionCreators({
    getCars: getCarsAction
}, dispatch);

Application.propTypes = {
    getCars: PropTypes.func.isRequired
};

export default connect(null, mapDispatchToProps)(Application);
