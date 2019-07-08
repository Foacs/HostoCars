import styled from 'styled-components';

import { Box } from '@material-ui/core';

import { primaryColor } from 'resources';

const StyledNotFoundPage = styled(Box)`
    &.NotFoundPage {
        color: ${primaryColor};
        left: calc(50% - 128px);
        overflow: hidden;
        pointer-events: none;
        position: absolute;
        text-align: center;
        top: 20%;
    }
    
    .NotFoundPage {
        &-SmileyIcon {
            animation: pendulum infinite 3s ease-in-out 1s both;
            font-size: 512px;
        }
        
        &-Label {
            font-weight: normal;
            line-height: 1.25;
        }
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
`;

export default StyledNotFoundPage;
