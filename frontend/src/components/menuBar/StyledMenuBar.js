import styled from 'styled-components';

import { Drawer } from '@material-ui/core';

import { primaryDarkColor, primaryLightColor, white } from 'resources';

const StyledMenuBar = styled(Drawer)`
    & [class*='MuiDrawer-paper'] {
        background: linear-gradient(60deg, ${primaryLightColor} 15%, ${primaryDarkColor} 75%);
        overflow: hidden;
        width: 256px;
        & img {
            animation: rotation infinite 10s linear;
            padding-bottom: 50px;
            padding-top: 50px;
        }
        & [class*='MuiList-root'] {
            & [class*='MuiListItem-root'] {
                & [class*='MuiListItemIcon-root'] {
                    color: ${white};
                    margin-left: 24px;
                }
                & [class*='MuiListItemText-root'] {
                    color: ${white};
                }
            }
        }
        & p {
            bottom: 12px;
            color: ${white};
            position: absolute;
            text-align: center;
            width: 100%;
        }
    }
    @keyframes rotation {
        from {
            transform: rotate(0deg);
        }
        to {
            transform: rotate(360deg);
        }
    }
`;

export default StyledMenuBar;
