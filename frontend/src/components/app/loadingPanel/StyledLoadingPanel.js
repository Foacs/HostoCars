import styled from 'styled-components';

import { Paper } from '@material-ui/core';

import { primaryColor, primaryDarkColor, transparent } from 'resources';

const StyledLoadingPanel = styled(Paper)`
    &.LoadingPanel {
        left: 50%;
        margin-right: -50%;
        padding: 24px 48px;
        position: absolute;
        top: 50%;
        transform: translate(-50%, -50%);
        
        &_transparent {
            background: ${transparent};
            box-shadow: none;
        }
    }
    
    .LoadingPanel {
        &-Grid {
            &-ProgressItem {
                &-Progress {
                    color: ${primaryColor};
                }
            }
            
            &-LabelItem {
                &-Label {
                    color:${primaryDarkColor};
                    padding-top: 12px;
                    -webkit-user-select: none;
                    -moz-user-select: none;
                }
            }
        }
    }
`;

export default StyledLoadingPanel;
