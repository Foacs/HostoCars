import styled from 'styled-components';

import { Paper } from '@material-ui/core';

import { errorColor, errorDarkColor, transparent } from 'resources';

const StyledErrorPanel = styled(Paper)`
    &.ErrorPanel {
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
    
    .ErrorPanel {
        &-Grid {
            &-IconItem {
                &-Icon {
                    color: ${errorColor};
                    height: 80px;
                    width: 80px;
                }
            }
            
            &-LabelItem {
                &-Label {
                    color: ${errorDarkColor};
                    padding-top: 12px;
                    -webkit-user-select: none;
                    -moz-user-select: none;
                }
            }
        }
    }
`;

export default StyledErrorPanel;
