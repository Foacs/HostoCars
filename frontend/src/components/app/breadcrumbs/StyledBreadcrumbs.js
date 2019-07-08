import styled from 'styled-components';

import { Breadcrumbs } from '@material-ui/core';

import { gray, primaryColor } from 'resources';

const StyledBreadcrumbs = styled(Breadcrumbs)`
    .Breadcrumb {
        &-HomeLink {
            color: ${primaryColor};
        }
        
        &-Link {
            color: ${gray};
            &:hover {
                text-decoration: none;
            }
        }
        
        &-CurrentPageLabel {
            color: ${primaryColor};
        }
    }
`;

export default StyledBreadcrumbs;
