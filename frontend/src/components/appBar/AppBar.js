import React from 'react';

import StyledAppBar from './StyledAppBar';

import { Toolbar } from '@material-ui/core';

import { Breadcrumbs } from "components";

function AppBar() {
    return (
        <StyledAppBar position="fixed">
            <Toolbar>
                <Breadcrumbs />
            </Toolbar>
        </StyledAppBar>
    );
}

export default AppBar;
