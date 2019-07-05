import styled from 'styled-components';

import { Card } from '@material-ui/core';

import { primaryColor, primaryLightColor, white } from 'resources';

const StyledCarCard = styled(Card)`
    &{
        & [class*='CardActionArea-root'] {
            width: 100%;
            padding-top: 100%;
            position: relative;
            & img {
                width: 100%;
                position: absolute;
                margin: auto;
                top: 0;
                left: 0;
                bottom: 0;
                right: 0;
                &.defaultCarPicture {
                    width: 80%;
                }
            }
        }
        & [class*='MuiCardActions-root'] {
            padding: 0;
            & [class*='MuiButton-root'] {
                border-radius: 0px;
                background: radial-gradient(circle at center, ${primaryLightColor}, ${primaryColor});
                color: ${white};
                height: 100%;
                width: 100%;
            }
        }
    }
`;

export default StyledCarCard;
