import styled from 'styled-components';

import { Breadcrumbs } from '@material-ui/core';

import { primaryColor, gray } from 'resources';

const StyledBreadcrumbs = styled(Breadcrumbs)`
    & [class*='MuiTypography-root'] {
        color: ${gray};
        cursor: default;
        &[class*='MuiLink-root'] {
            cursor: pointer;
            &:hover {
                text-decoration: none;
            }
        }
    }
    & p[class*='MuiTypography-root'] {
        color: ${primaryColor};
    }
`;

export default StyledBreadcrumbs;
