import { Box, Typography } from '@material-ui/core';
import { SentimentDissatisfiedRounded as SmileyIcon } from '@material-ui/icons';

import { changeCurrentPageAction, changeSelectedMenuIndexAction } from 'actions';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import './NotFoundPage.scss';

class NotFoundPage extends PureComponent {
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex } = this.props;

        changeCurrentPage('Page introuvable', []);

        changeSelectedMenuIndex(-1);
    }

    render() {
        return <Box id='NotFoundPage'>
            <SmileyIcon className='SmileyIcon' />

            <Typography className='Label' variant='h1'>Page introuvable</Typography>
        </Box>;
    }
}

const mapDispatchToProps = dispatch => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction
}, dispatch);

NotFoundPage.propTypes = {
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(null, mapDispatchToProps)(NotFoundPage);
