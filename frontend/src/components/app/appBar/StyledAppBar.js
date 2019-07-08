import styled from 'styled-components';

import { AppBar } from '@material-ui/core';

import { white } from 'resources';

const StyledAppBar = styled(AppBar)`
    &.AppBar {
        background-color: ${white};
        height: 60px;
        margin-left: 256px;
        overflow: hidden;
        width: calc(100% - 256px);
    }
`;

export default StyledAppBar;
