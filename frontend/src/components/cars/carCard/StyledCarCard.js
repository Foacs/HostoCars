import styled from 'styled-components';

import { Card } from '@material-ui/core';

import { gray, transparent } from 'resources';

const StyledCarCard = styled(Card)`
    .CarCard {
        &-ActionArea {
            &-PictureBox {
                overflow:hidden;
                padding-top: 56.25%;
                position: relative;
                width: 100%;
                
                &-Picture {
                    bottom: 0;
                    height: 100%;
                    left: 0;
                    margin: auto;
                    object-fit: cover;
                    position: absolute;
                    right: 0;
                    top: 0;
                    width: 100%;
                    &_default {
                        height: 80%;
                        object-fit: none;
                        width: 80%;
                    }
                }
            }
            
            &-TitleBox {
                padding: 12px;
                
                &-RegistrationTitle, &-OwnerSubTitle, &-ModelSubTitle {
                    overflow: visible;
                }
                
                &-RegistrationTitle {
                    font-size: calc(16px + 10 * ((100vw - 280px) / (1600 - 280)));
                    line-height: 1.2;
                }
                
                &-OwnerSubTitle {
                    font-size: calc(12px + 8 * ((100vw - 280px) / (1600 - 280)));
                    line-height: 1;
                }
                
                &-ModelSubTitle {
                    color: ${gray};
                    font-size: calc(10px + 6 * ((100vw - 280px) / (1600 - 280)));
                    font-style: italic;
                    line-height: 1.4;
                    
                    &_missing {
                        color: ${transparent};
                    }
                }
            }
        }
    }
`;

export default StyledCarCard;
