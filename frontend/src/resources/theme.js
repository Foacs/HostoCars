import { createMuiTheme } from '@material-ui/core/styles';

import 'typeface-roboto';

import { backgroundColor } from 'resources';

export const theme = createMuiTheme({
    palette: {
        background: {
            default: backgroundColor
        }
    },
    typography: {
        fontFamily: "\"Roboto\", sans-serif"
    }
});
