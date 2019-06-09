import styled from 'styled-components';

import { backgroundColor } from 'resources';

const StyledNotFoundPage = styled.div`
    & {
        text-align: center;
        & .header {
            background-color: ${backgroundColor};
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-size: calc(10px + 2vmin);
            color: white;
            & .logo {
                animation: logo--spin infinite 20s linear;
                height: 40vmin;
                pointer-events: none;
            }
            @keyframes logo--spin {
                from {
                    transform: rotate(0deg);
                }
                to {
                    transform: rotate(360deg);
                }
            }
        }
    }
`;

export default StyledNotFoundPage;
