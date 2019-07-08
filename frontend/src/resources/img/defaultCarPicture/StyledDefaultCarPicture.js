import styled from 'styled-components';

import { defaultCarPictureBackgroundColor, defaultCarPictureStrokeColor, white } from 'resources';

const StyledDefaultCarPicture = styled.svg`
    .DefaultCarPicture {
        &-Background {
            fill: ${defaultCarPictureBackgroundColor};
        }
        
        &-Outline, &-Windshield, &-Headlight, &-Smile {
            stroke-linecap: round;
            stroke-linejoin: round;
            stroke-width: 2px;
        }
        
        &-Outline, &-Smile {
            fill: none;
            stroke: ${defaultCarPictureStrokeColor};
        }
        
        &-Windshield, &-Headlight {
            fill: ${white};
            stroke: ${defaultCarPictureStrokeColor};
        }
    }
`;

export default StyledDefaultCarPicture;
