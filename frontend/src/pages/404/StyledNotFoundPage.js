import styled from 'styled-components';

import { primaryColor } from 'resources';

const StyledNotFoundPage = styled.div`
    & {
        color: ${primaryColor};
        left: calc(50% - 128px);
        overflow: hidden;
        pointer-events: none;
        position: absolute;
        text-align: center;
        top: 20%;
        & [class*='MuiSvgIcon-root'] {
            animation: pendulum infinite 3s ease-in-out 1s both;
            font-size: 512px;
        }
        & [class*='MuiTypography-root'] {
            line-height: 1.25;
        }
        @keyframes pendulum {
            0% {
                transform: rotate(0deg);
            }
            21% {
                transform: rotate(30deg);
            }
            49% {
                transform: rotate(-30deg);
            }
            70% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(0deg);
            }
        }
    }
`;

export default StyledNotFoundPage;
