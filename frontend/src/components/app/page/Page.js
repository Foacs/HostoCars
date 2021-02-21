import React from 'react';
import PropTypes from 'prop-types';

import { Box } from '@material-ui/core';

import './Page.scss';

/**
 * The application page component.
 *
 * @param {node} children
 *     The page children
 * @param {string} id
 *     The page ID
 *
 * @constructor
 */
function Page({
    children,
    id
}) {
    return (<Box className='Page' id={id}>
        {children}
    </Box>);
}

Page.propTypes = {
    children: PropTypes.node.isRequired,
    id: PropTypes.string
};

export default Page;
