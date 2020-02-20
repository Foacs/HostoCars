import { createMuiTheme } from '@material-ui/core/styles';

import {
    backgroundColor, backgroundLightColor, errorColor, errorDarkColor, errorLightColor, primaryColor, primaryDarkColor, primaryLightColor,
    secondaryColor, secondaryDarkColor, secondaryLightColor
} from 'resources';

import 'typeface-roboto';

/**
 * Application's theme.
 *
 * @type {object}
 */
export const theme = createMuiTheme({
    palette: {
        primary: {
            light: primaryLightColor,
            main: primaryColor,
            dark: primaryDarkColor
        },
        secondary: {
            light: secondaryLightColor,
            main: secondaryColor,
            dark: secondaryDarkColor
        },
        error: {
            light: errorLightColor,
            main: errorColor,
            dark: errorDarkColor
        },
        background: {
            paper: backgroundLightColor,
            default: backgroundColor
        }
    },
    typography: {
        fontFamily: '"Roboto", sans-serif'
    }
});
