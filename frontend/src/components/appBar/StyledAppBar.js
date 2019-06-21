import styled from 'styled-components';

import { AppBar } from '@material-ui/core';

import { white } from 'resources';

const StyledAppBar = styled(AppBar)`
    &[class*='MuiAppBar-root'] {
        height: 60px;
        margin-left: 256px;
        overflow: hidden;
        width: calc(100% - 256px);
        &[class*='MuiAppBar-colorPrimary'] {
            background-color: ${white};
        }
    }
`;

export default StyledAppBar;
