import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { Box } from '@material-ui/core';

import { changeCurrentPageAction, changeSelectedMenuIndexAction } from 'actions';

import './InterventionsOverviewPage.scss';

/**
 * The interventions overview page component.
 *
 * @class
 */
class InterventionsOverviewPage extends PureComponent {
    /**
     * Constructor.
     *
     * @param {object} props
     *     The component props
     *
     * @constructor
     */
    constructor(props) {
        super(props);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage('Interventions', []);
        changeSelectedMenuIndex(1);
    }

    /**
     * Render method.
     */
    render() {
        return (<Box id='InterventionsOverviewPage' />);
    }
}

const mapDispatchToProps = (dispatch) => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction
}, dispatch);

export default connect(null, mapDispatchToProps)(InterventionsOverviewPage);
