import styled from 'styled-components';

import { Box } from '@material-ui/core';

const StyledCarsPage = styled(Box)`
    &.CarsPage {
        padding: 32px;
    }
    
    .CarsPage {
        &-ErrorPanel, &-LoadingPanel {
            left: calc(50% + 128px);
        }
    
        &-CarsGrid {
            &-Item {
                &-CarCard {
                    min-width: 280px;
                }
            }
        }
    }
`;

export default StyledCarsPage;
