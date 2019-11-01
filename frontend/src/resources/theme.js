import { createMuiTheme } from '@material-ui/core/styles';

import { backgroundColor } from 'resources';

import 'typeface-roboto';

export const theme = createMuiTheme({
    palette: {
        background: {
            default: backgroundColor
        }
    },
    typography: {
        fontFamily: '"Roboto", sans-serif'
    }
});
