import styled from 'styled-components';

import { primaryColor } from 'resources';

const StyledTestPage = styled.div`
    & {
        color: ${primaryColor};
    }
`;

export default StyledTestPage;
